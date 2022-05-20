<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="90%"
    >
      <div v-show="showSearch" class="search-form">
        <el-form ref="searchForm" :model="formItem" inline label-width="100px">
          <el-row :gutter="24">
            <el-col :span="10">
              <el-form-item
                label="交易时间"
                prop="transTime"
              >
                <el-date-picker
                  ref="timeScopeChoose"
                  v-model="formItem.timeScope"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :key="formItem.timeScope"
                  align="right" value-format="yyyy-MM-dd HH:mm:ss" disabled="true">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="运营方" prop="merchantId">
                <ec-select-tree
                  ref="merchantTreeData"
                  v-model="formItem.siteId"
                  url="api/v1/merchant/tbMerchant/data"
                  value-field="id"
                  attr-name="name"
                  disabled="true"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="交易流水号" prop="transactionId">
                <el-input type="text" v-model="formItem.transactionId" clearable/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="车牌号码" prop="vehicleNumber" clearable>
                <el-input type="text" v-model="formItem.vehicleNumber" clearable/>
              </el-form-item>
              <el-form-item class="searchItem" :label-width="'50px'">
                <el-button type="primary" @click="queryData">查询</el-button>
                <el-button type="default" @click="handleReset">重置</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <vxe-grid
        ref="vxe"
        highlight-hover-row
        border
        resizable
        sync-resize
        auto-resize
        show-overflow
        show-header-overflow
        max-height="1000"
        class="vxe-table-element"
        :loading="loading"
        :start-index="(page.currentPage - 1) * page.pageSize"
        :sort-config="{ trigger: 'cell' }"
        :pager-config="page"
        :columns="columns"
        :data="tableData"
        :toolbar="tableToolbar"
        @sort-change="sortMethod"
        @page-change="handlePageChange"
      >
        <template v-slot:toolbar_buttons>
          <el-button v-permission="['bankCheck:exception:export']" type="primary" @click="exportExceptionExcel">导出</el-button>
          <el-button @click="getData">刷新</el-button>
          <el-button @click="showSearch = !showSearch">搜索</el-button>
        </template>
      </vxe-grid>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import {getData, getExceptionData} from '@/api/modules/bankbillcheck/bankCheck'
import { download } from 'ecip-web/utils'

export default {
  name: 'exceptionDetail',
  components: {},
  data() {
    return {
      page: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        align: 'left',
        pageSizes: [10, 20, 50, 100, 200, 500],
        layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
        perfect: true,
      },
      exportUrl: 'api/v1/bankbillcheck/bankCheck/exportExceptionExcel',
      tableToolbar: {
        id: 'ExceptionDetailView-toolbar',
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
        },
        zoom: true, // 最大化按钮
        // 列宽操作记录
        resizable: {
          storage: true
        },
        // 列操作记录
        setting: {
          storage: true
        }
      },
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      showSearch: false,
      title: '',
      record: {},
      tableData: [],
      columns: [
        { title: '车牌号码', field: 'vehicleNumber', tableField: 'vehicleNumber', minWidth: 180, align: 'center'},
        { title: '车牌颜色', field: 'vehicleColorName', tableField: 'vehicleColorName', minWidth: 180, align: 'center' },
        { title: '交易流水号', field: 'transactionId', tableField: 'transactionId', minWidth: 180, align: 'center' },
        { title: '订单号', field: 'orderId', tableField: 'orderId', minWidth: 180, align: 'center'},
        { title: '交易模式', field: 'tradeModeName', tableField: 'tradeModeName', minWidth: 180, align: 'center'},
        { title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center' },
        { title: '运营方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, align: 'center' },
        { title: '场景', field: 'siteName', tableField: 'site_id', minWidth: 180, align: 'center'},
        { title: '入场时间', field: 'enTime', tableField: 'enTime', minWidth: 180, align: 'center'},
        { title: '出场时间', field: 'exTime', tableField: 'exTime', minWidth: 180, align: 'center'},
        { title: '停放时长', field: 'parkTime', tableField: 'parkTime', minWidth: 180, align: 'center'},
        { title: '交易金额(元)', field: 'fee', tableField: 'fee', minWidth: 180, align: 'center'},
        { title: '交易时间', field: 'tradeTime', tableField: 'tradeTime', minWidth: 180, align: 'center'},
        { title: '状态', field: 'statusName', tableField: 'statusName', minWidth: 180, align: 'center'}
      ],
      formItem: {
        siteId: '',
        timeScope: '',
        transactionId: '',
        vehicleNumber: ''
      }
    }
  },
  methods: {
    async init(record) {
      Object.assign(this.$data, this.$options.data())
      this.record = record
      this.title = '异常流水明细'
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.formItem.siteId = record.siteId
      let timeScope = [];
      timeScope.push(this.$moment(record.tradeDay).format("YYYY-MM-DD HH:mm:ss"))
      timeScope.push(this.$moment(record.tradeDay).endOf("day").format("YYYY-MM-DD HH:mm:ss"))
      this.formItem.timeScope = timeScope
      this.loading = true
      const { data } = await getExceptionData(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage, field: this.page.field, order: this.page.order }), this.formItem)
      this.tableData = data.records
      this.tableData.forEach((item,array,index) => {
        item.fee = (item.fee/100).toFixed(2)
      })
      this.loading = false
    },
    handlePageChange({ currentPage, pageSize }) {
      this.page.currentPage = currentPage
      this.page.pageSize = pageSize
      this.getData()
    },
    sortMethod({ column, property, order }) {
      this.page.field = (column.own.tableField && 'a.'.concat(column.own.tableField)) || property
      this.page.order = order
      this.getData()
    },
    async exportExceptionExcel() {
      this.loading = true
      await download({ url: this.exportUrl, method: 'post', data: this.formItem })
      this.loading = false
    },
    async queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      this.loading = true
      const { data } = await getExceptionData(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage, field: this.page.field, order: this.page.order }), this.formItem)
      this.tableData = data.records
      this.tableData = data.records
      this.tableData.forEach((item,array,index) => {
        item.fee = (item.fee/100).toFixed(2)
      })
      this.loading = false
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      this.formItem.vehicleNumber = ''
      this.formItem.transactionId = ''
      this.queryData()
    },
  }
}
</script>

<style lang="scss" scoped>

</style>
