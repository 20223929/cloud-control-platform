<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="70%"
    >
      <div v-show="showSearch" class="search-form">
        <el-form ref="searchForm" :model="queryParams" inline label-width="120px">
          <el-row :gutter="24">
            <el-col :span="8">
              <el-form-item
                label="交易流水号"
                prop="transactionId"
              >
                <el-input v-model="queryParams.transactionId" type="text"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="银行交易检索号" prop="deductionOrderId">
                <el-input v-model="queryParams.deductionOrderId" type="text"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="运营方" prop="merchantId">
                <ec-select-tree
                  ref="merchantTreeData"
                  v-model="queryParams.merchantId"
                  url="api/v1/merchant/tbMerchant/data"
                  value-field="id"
                  attr-name="name"
                  disabled
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="8">
              <el-form-item label="车牌号码" prop="vehiclePlate">
                <el-input v-model="queryParams.vehiclePlate" type="text"/>
              </el-form-item>
            </el-col>
            <el-col :span="16">
              <el-form-item label="流水类型" prop="eexitType">
                <el-select v-model="queryParams.eexitType" placeholder="请选择">
                  <el-option
                    v-for="item in eexitType"
                    :value="item.value"
                    :label="item.label"
                    :key="item.value"
                  >
                  </el-option>
                </el-select>
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
        :show-footer="showFoot"
        max-height="1000"
        class="vxe-table-element"
        :loading="loading"
        :start-index="(page.currentPage - 1) * page.pageSize"
        :sort-config="{ trigger: 'cell' }"
        :pager-config="page"
        :columns="columns"
        :data="tableData"
        :toolbar="tableToolbar"
        :footer-method="footerMethod"
        :footer-span-method="footerRowspanMethod"
        @sort-change="sortMethod"
        @page-change="handlePageChange"
      >
        <template v-slot:toolbar_buttons>
          <el-button v-permission="['tbBankSettlement:export']" type="primary" @click="exportExcel">导出</el-button>
          <el-button @click="queryData">刷新</el-button>
          <el-button @click="showSearch = !showSearch">搜索</el-button>
        </template>
      </vxe-grid>
    </el-dialog>
  </div>
</template>

<script>

import {getDetail} from '@/api/modules/settlement/tbBankSettlementApi'
import {download} from "@ecip/ecip-web/src/utils";

export default {
  name: 'ModulesSettlementTbBankSettlementForm',
  components: {},
  data() {
    return {
      exportUrl: 'api/v1/settlement/tbBankSettlement/exportDetailExcel',
      eexitType: [
        {
          value: 1,
          label: '支付'
        },
        {
          value: 2,
          label: '退费'
        }
      ],
      tableToolbar: {
        id: "bankSettlementDetail",
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
      page: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        align: 'left',
        pageSizes: [10, 20, 50, 100, 200, 500],
        layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
        perfect: true,
      },
      tableData: [],
      columns: [
        { title: '序号', type: 'seq', minWidth: 80, align: 'center'},
        { title: '流水类型', field: 'eexitType', tableField: 'eexit_type', minWidth: 180, align: 'center' },
        { title: '银行交易时间', field: 'bankTransDate', tableField: 'bank_trans_date', minWidth: 180, align: 'center',sortable: true},
        { title: '交易流水号', field: 'transactionId', tableField: 'transaction_id', minWidth: 180, align: 'center' },
        { title: '银行交易检索号', field: 'deductionOrderId', tableField: 'deduction_order_id', minWidth: 180, align: 'center' },
        { title: '车牌号码', field: 'vehiclePlate', tableField: 'vehicle_number', minWidth: 180, align: 'center' },
        { title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_Group_Name', minWidth: 180, align: 'center' },
        { title: '运营方', field: 'merchantName', tableField: 'merchant_Name', minWidth: 180, align: 'center' },
        { title: '场景', field: 'siteName', tableField: 'site_name', minWidth: 180, align: 'center' },
        { title: '服务类型', field: 'serviceTypeDesc', tableField: 'service_type_desc', minWidth: 180, align: 'center' },
        { title: '客户实付金额(元)', field: 'merchantRealFee', tableField: 'merchant_real_fee', minWidth: 180, align: 'center' },
        { title: '优惠金额(元)', field: 'merchantDiscountFee', tableField: 'merchant_discount_fee', minWidth: 180, align: 'center' }
      ],
      queryParams: {
        transactionId: '',
        deductionOrderId: '',
        vehiclePlate: '',
        eexitType: ''
      },
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      title: '',
      showSearch: false,
      footerSum: '',
      showFoot: ''
    }
  },
  methods: {
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
    async queryData (){
      Object.assign(this.$data.page,this.$options.data().page)
      this.loading = true
      const {data} = await getDetail(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage, field: this.page.field, order: this.page.order }), this.queryParams)
      this.tableData = data.records
      this.tableData.forEach((item,index,array) => {
        item.merchantRealFee = (item.merchantRealFee/100).toFixed(2)
        item.merchantDiscountFee = (item.merchantDiscountFee/100).toFixed(2)
      })
      this.footerSum = data.dataMap;
      this.showFoot = (this.tableData.length > 0)
      this.page.total = data.total
      this.loading = false
    },
    handleReset (){
      Object.assign(this.$data.page,this.$options.data().page)
      this.queryParams.transactionId = ''
      this.queryParams.deductionOrderId = ''
      this.queryParams.vehiclePlate = ''
      this.queryParams.eexitType = ''
      this.queryData()
    },
    async exportExcel() {
      this.loading = true
      await download({ url: this.exportUrl, method: 'post', data: this.queryParams })
      this.loading = false
    },
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())
      this.method = method
      this.record = record
      this.title = '银行预结算明细'
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.queryParams.bankTransDate = record.bankTransDate
      this.queryParams.serviceType = record.serviceType
      this.queryParams.merchantId = record.trxPlace.substr(0,9)
      await this.queryData()
    },
    footerMethod({ columns, data }) {
      if(this.footerSum.sum == undefined) return []
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 1) {
            return '小计'
          }
          if (['merchantRealFee', 'merchantDiscountFee'].includes(column.property)) {
            return XEUtils.sum(data, column.property).toFixed(2)
          }
          return []
        }),
        columns.map((column, columnIndex) => {
          if (columnIndex === 1) {
            return '合计'
          }
          if (['merchantRealFee', 'merchantDiscountFee'].includes(column.property)) {
            return (this.footerSum.sum[column.property]/100).toFixed(2)
          }
          return []
        })
      ]
      return footData
    },
    footerRowspanMethod({ _rowIndex, _columnIndex }){
      if(_columnIndex == 1){
        return { rowspan: 1, colspan: 9 }
      }else if(_columnIndex >=2 && _columnIndex <=9){
        return { rowspan: 0, colspan: 0 }
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
