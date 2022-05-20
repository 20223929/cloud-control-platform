<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-form-item
          label="交易时间"
          prop="trxDate"
        >
          <el-date-picker
            v-model="queryParams.timeScope"
            type="daterange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            :key="queryParams.timeScope"
            align="right" value-format="yyyy-MM-dd" :disabled="searchDisable">
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="服务方类型"
          prop="serviceType"
        >
          <ec-select v-model="queryParams.serviceType" dict-type="service_type" placeholder="服务方类型" />
        </el-form-item>
        <el-form-item
          label="运营方"
          prop="searchId"
        >
          <el-select-tree
            ref="merchantTreeData"
            v-model="queryParams.searchId"
            :data="merchantData"
            :props="props"
            check-strictly
            filterable
          />
        </el-form-item>
        <el-form-item
          label="结算周期"
          prop="tollSettlementInterval"
        >
          <ec-select v-model="queryParams.tollSettlementInterval" dict-type="settlementInterval" placeholder="结算周期" />
        </el-form-item>
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
      :show-footer="showFoot"
      :footer-method="footerMethod"
      :footer-span-method="footerRowspanMethod"
      :cell-style="cellStyle"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['serviceRecvReport:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
      </template>
      <template v-slot:operation="{ row }">
        <el-button type="primary" @click="rejectDetail(row)" :disabled="row.rejectSettlementCnt <= 0">预支抵扣详情</el-button>
      </template>
    </vxe-grid>

    <RejectDetail ref="rejectDetailDialog" @refreshData="getData" />

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getData, getMerchantTreeData } from '@/api/modules/report/serviceRecvReportApi'
import { download } from 'ecip-web/utils'
import RejectDetail from "@/views/modules/settlementReject/view";

export default {
  name: 'ModulesReportServiceRecvReportView',
  components: { RejectDetail },
  mixins: [permissionMixin],
  data() {
    return {
      props: {label: 'name', children: 'children', value: 'id'},
      ecData: [],
      searchDisable: false,
      importVisible: false,
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/report/serviceRecvReport/importExcel',
      tplUrl: 'api/v1/report/serviceRecvReport/excelTemplate',
      exportUrl: 'api/v1/report/serviceRecvReport/exportExcel',
      tableToolbar: {
        id: 'modules.report.serviceRecvReportView-toolbar',
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
      selections: [],
      tableData: [],
      columns: [
        // {title: '序号', type: 'seq', width: 80, fixed: 'left', align: 'center'},
        { title: '运营方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, sortable: false, align: 'center' },
        { title: '服务方', field: 'siteName', tableField: 'site_name', minWidth: 180, sortable: false, align: 'center' },
        { title: '交易日期', field: 'trxDate', tableField: 'trx_date', minWidth: 180, sortable: true, align: 'center' },
        { title: '结算周期', field: 'tollSettlementIntervalTxt', tableField: 'toll_settlement_interval', minWidth: 180, sortable: false, align: 'center' },
        { title: '通行费结算日期', field: 'tollSettlementDate', tableField: 'toll_settlement_date', minWidth: 180, sortable: false, formatter: ['date', 'yyyy-MM-dd'], align: 'center' },
        { title: '应结算交易总量', align: 'center',
          children: [
            { title: '交易笔数', field: 'totalSettlementCnt', tableField: 'total_settlement_cnt', minWidth: 180, sortable: false, align: 'center' },
            { title: '交易金额（元）', field: 'totalSettlementAmt', tableField: 'total_settlement_amt', minWidth: 180, sortable: false, align: 'center' },
        ]},
        { title: '预支抵扣交易总量', align: 'center',
          children: [
            { title: '交易笔数', field: 'rejectSettlementCnt', tableField: 'reject_settlement_cnt', minWidth: 180, sortable: false, align: 'center' },
            { title: '交易金额（元）', field: 'rejectSettlementAmt', tableField: 'reject_settlement_amt', minWidth: 180, sortable: false, align: 'center' },
          ]},
        { title: '应结算总金额（元）', field: 'actualSettlementAmt', tableField: 'actual_settlement_amt', minWidth: 180, sortable: false, align: 'center' },
        { title: '服务费费率', field: 'serviceRate', tableField: 'service_rate', minWidth: 180, sortable: false, align: 'center' },
        { title: '服务费（元）', field: 'serviceAmt', tableField: 'service_rate', minWidth: 180, sortable: false, align: 'center' },
        { title: '服务费结算模式', field: 'serviceSettlementIntervalTxt', tableField: 'service_settlement_interval', minWidth: 180, sortable: false, align: 'center' },
        { title: '转账金额（元）', field: 'bankTransferAmt', tableField: 'bank_transfer_amt', minWidth: 180, sortable: false, align: 'center' },
        { title: '转账状态', field: 'bankTransferStaTxt', tableField: 'bank_transfer_sta', minWidth: 180, sortable: false, align: 'center' },
        { title: '转账时间', field: 'bankTransferTime', tableField: 'bank_transfer_time', minWidth: 180, sortable: false, align: 'center' },
        { title: '操作', width: 150, fixed: 'right', align: 'center', slots: { default: 'operation' }}
      ],
      queryParams: {
        trxDate: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        tollSettlementInterval: '',
        serviceType: '',
        nodeLevel: '',
        searchId: '',
        timeScope: []
      },
      footerSum: '',
      showFoot: ''
    }
  },
  mounted() {
    this.$watch(this.$refs.vxe.getCheckboxRecords, (newValue, oldValue) => {
      this.selections = newValue
      this.onlyOne = this.selections.length === 1
      this.oneMore = this.selections.length > 0
    })
  },
  async created() {
    // this.getData()
    this.queryParams.serviceType = '2'
    const merchantInfo = await getMerchantTreeData()
    this.ecData = merchantInfo.data
  },
  methods: {
    async getData() {
      // 每次查询初始化checkbox selections
      this.selections = []
      this.loading = true
      const { data } = await getData(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage, field: this.page.field, order: this.page.order }), this.queryParams)
      this.tableData = data.records
      this.tableData.forEach((item,index,array) => {
        item.totalSettlementAmt = (item.totalSettlementAmt/100).toFixed(2)
        item.rejectSettlementAmt = (item.rejectSettlementAmt/100).toFixed(2)
        item.actualSettlementAmt = (item.actualSettlementAmt/100).toFixed(2)
        item.serviceAmt = (item.serviceAmt).toFixed(6)
        item.bankTransferAmt = (item.bankTransferAmt).toFixed(2)
      })
      this.footerSum = data.dataMap;
      this.showFoot = (this.tableData.length > 0)
      this.page.total = data.total
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
    add() {
      this.$refs.serviceRecvReportDialog.init('add')
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.serviceRecvReportDialog.init('edit', record)
    },
    async exportExcel() {
      this.loading = true
      await download({ url: this.exportUrl, method: 'post', data: this.queryParams })
      this.loading = false
    },
    queryData() {
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        this.queryParams.nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
      } else {
        this.queryParams.nodeLevel = ''
      }
      Object.assign(this.$data.page, this.$options.data().page)
      this.getData()
    },
    fixParam() {
      if (this.param != null && this.param != undefined) {
        this.queryParams.merchantGroupId = this.param.merchantGroupId
        this.queryParams.merchantId = this.param.merchantId
        this.queryParams.siteId = this.param.siteId
        this.queryParams.nodeLevel = this.param.nodeLevel
        this.queryParams.timeScope = this.param.timeScope
        this.queryParams.serviceType = this.param.serviceType
        this.queryParams.searchId = this.param.searchId
        this.queryParams.tollSettlementInterval = this.param.tollSettlementInterval
        this.searchDisable = true
      }
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.queryParams.serviceType = '2'
      this.fixParam()
      // this.getData()
    },
    footerMethod({ columns, data }) {
      if(this.footerSum.sum == undefined) return []
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return '小计'
          }
          if (['totalSettlementAmt', 'rejectSettlementAmt', 'actualSettlementAmt', 'bankTransferAmt'].includes(column.property)) {
            return (XEUtils.sum(data, column.property)).toFixed(2)
          } else if (['serviceRate', 'serviceSettlementIntervalTxt'].includes(column.property)) {
            return '-'
          } else if (['serviceAmt'].includes(column.property)) {
            return (XEUtils.sum(data, column.property)).toFixed(6)
          } else if (['totalSettlementCnt', 'rejectSettlementCnt'].includes(column.property)) {
            return XEUtils.sum(data, column.property)
          }
          return []
        }),
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return '合计'
          }
          if (['totalSettlementAmt', 'rejectSettlementAmt', 'actualSettlementAmt'].includes(column.property)) {
            return (this.footerSum.sum[column.property]/100).toFixed(2)
          } else if (['serviceRate', 'serviceSettlementIntervalTxt'].includes(column.property)) {
            return '-'
          } else if (['serviceAmt'].includes(column.property)) {
            return (this.footerSum.sum[column.property]).toFixed(6)
          } else if (['bankTransferAmt'].includes(column.property)) {
            return (this.footerSum.sum[column.property]).toFixed(2)
          } else {
            return this.footerSum.sum[column.property]
          }
          return []
        })
      ]
      return footData
    },
    footerRowspanMethod({ _rowIndex, _columnIndex }){
      if(_columnIndex == 0){
        return { rowspan: 1, colspan: 5 }
      }else if(_columnIndex >=1 && _columnIndex <=4){
        return { rowspan: 0, colspan: 0 }
      }
    },
    cloneDataByServiceType(serviceType) {
      if (!serviceType) return this.ecData
      if (this.$refs.merchantTreeData) this.$refs.merchantTreeData.selectedLabel = ''
      return this.cloneDataByDataAndServiceType(this.ecData, serviceType)
    },
    cloneDataByDataAndServiceType(obj, serviceType) {
      var objClone
      if (Array.isArray(obj)) {
        objClone = []
        let key = 0
        for (let origin = 0; origin < obj.length; origin++) {
          if (typeof obj[origin] === 'object') {
            if (obj[origin].nodeLevel === 2 && obj[origin].id.substring(4, 6) !== serviceType.padStart(2, "0")) {
              key--
            } else {
              let cloneData = this.cloneDataByDataAndServiceType(obj[origin], serviceType)
              if (obj[origin].nodeLevel === 1 && (!cloneData.children || cloneData.children.length == 0)) {
                key--;
              } else {
                objClone[key] = cloneData
              }
            }
          } else {
            objClone[key] = obj[origin]
          }
          key++
        }
      } else {
        objClone = {}
        for (let key in obj) {
          if (typeof obj[key] === 'object') {
            objClone[key] = this.cloneDataByDataAndServiceType(obj[key], serviceType)
          } else {
            objClone[key] = obj[key]
          }
        }
      }
      return objClone;
    },
    cellStyle({row, column, rowIndex, columnIndex}) {
      /**
       * 改变某一列的单元格颜色
       */
      var cellStyle
      if (row.rejectSettlementCnt > 0) {
        cellStyle = 'background: #ff4949'
      }
      if (column.property == 'rejectSettlementCnt' || column.property == 'rejectSettlementAmt') {
        return cellStyle
      }
    },
    rejectDetail(row){
      this.$refs.rejectDetailDialog.init({settlementId: row.sysId})
    }
  },
  computed: {
    merchantData() {
      return this.cloneDataByServiceType(this.queryParams.serviceType)
    }
  }
}
</script>

<style scoped>

</style>
