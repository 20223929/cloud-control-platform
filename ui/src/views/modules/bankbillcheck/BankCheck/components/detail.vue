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
            <el-col :span="8">
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
            <el-col :span="16">
              <el-form-item label="车道号" prop="equipmentId" clearable>
                <el-input type="text" v-model="formItem.equipmentId" clearable/>
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
        :sort-config="{ trigger: 'cell' }"
        :columns="columns"
        :data="tableData"
        :toolbar="tableToolbar"
        @sort-change="sortMethod"
        :row-style="rowStyle"
      >
        <template v-slot:toolbar_buttons>
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

import {getDetailData} from '@/api/modules/bankbillcheck/bankCheck'

export default {
  name: 'Detail',
  components: {},
  data() {
    return {
      tableToolbar: {
        id: 'DetailView-toolbar',
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
        { title: '序号',type:'seq',minWidth: 80,align: 'center'},
        { title: '交易日期', field: 'tradeDay', tableField: 'trade_day', minWidth: 180, align: 'center'},
        { title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center' },
        { title: '运营方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, align: 'center' },
        { title: '场景', field: 'siteName', tableField: 'site_id', minWidth: 180, align: 'center'},
        { title: '车道号', field: 'equipmentId', tableField: 'equipment_id', minWidth: 180, align: 'center'},
        { title: '银行流水总数', field: 'totalCheckCount', tableField: 'total_check_count', minWidth: 180, align: 'center'},
        { title: '银行实扣总金额(元)', field: 'totalCheckAmount', tableField: 'total_check_amount', minWidth: 180, align: 'center'},
        { title: '本系统流水总数', field: 'totalCount', tableField: 'total_count', minWidth: 180, align: 'center'},
        { title: '本系统实收总金额(元)', field: 'totalAmount', tableField: 'total_amount', minWidth: 180, align: 'center'},
        { title: '流水总数差额', field: 'differCount', tableField: 'differ_count', minWidth: 180, align: 'center'},
        { title: '流水实收总金额差额(元)', field: 'differAmount', tableField: 'differ_amount', minWidth: 180, align: 'center'}
      ],
      formItem: {
        siteId: '',
        timeScope: '',
        equipmentId: ''
      }
    }
  },
  methods: {
    async init(record) {
      Object.assign(this.$data, this.$options.data())
      this.record = record
      this.title = '详情'
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.formItem.siteId = record.siteId
      let timeScope = [];
      timeScope.push(this.$moment(record.tradeDay).format("YYYY-MM-DD HH:mm:ss"))
      timeScope.push(this.$moment(record.tradeDay).endOf("day").format("YYYY-MM-DD HH:mm:ss"))
      this.formItem.timeScope = timeScope
      this.loading = true
      const { data } = await getDetailData(this.formItem)
      this.tableData = data
      this.tableData.forEach((item,array,index) => {
        if(item.totalCheckAmount) item.totalCheckAmount = (item.totalCheckAmount/100).toFixed(2)
        if(item.totalAmount) item.totalAmount = (item.totalAmount/100).toFixed(2)
        if(item.differAmount) item.differAmount = (item.differAmount/100).toFixed(2)
      })
      this.loading = false
    },
    async queryData() {
      this.loading = true
      const { data } = await getDetailData(this.formItem)
      this.tableData = data
      this.tableData.forEach((item,array,index) => {
        if(item.totalCheckAmount) item.totalCheckAmount = (item.totalCheckAmount/100).toFixed(2)
        if(item.totalAmount) item.totalAmount = (item.totalAmount/100).toFixed(2)
        if(item.differAmount) item.differAmount = (item.differAmount/100).toFixed(2)
      })
      this.loading = false
    },
    handleReset() {
      this.formItem.equipmentId = ''
      this.queryData()
    },
    rowStyle(row){
      if (row.row.differCount != 0) {
        return 'color:red!important;';
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
