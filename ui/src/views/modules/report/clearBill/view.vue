<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="时间段统计" prop="value">
              <el-select v-model="value" placeholder="请选择" @change="dateTypeChange">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              label="交易时间"
              prop="transTime"
            >
              <el-date-picker
                ref="timeScopeChoose"
                v-model="transTime"
                :type="dateType"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="value"
                :picker-options="pickerOptions"
                @change="timeChange"
                align="right" value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="运营方名称"
              prop="merchantId"
            >
              <ec-select-tree
                ref="merchantTreeData"
                v-model="queryParams.searchId"
                url="api/v1/merchant/tbMerchant/data"
                value-field="id"
                attr-name="name"
                :disabled="searchDisable"
                placeholder="运营方"
              />
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
      :show-footer="showFoot"
      :footer-method="footerMethod"
      :footer-span-method="footerRowspanMethod"
      :cell-style="cellStyle"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['clearBill:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getData } from '@/api/modules/report/clearBillApi'
import { download } from 'ecip-web/utils'

export default {
  name: 'ModulesReportClearBillView',
  components: { },
  mixins: [permissionMixin],
  data() {
    var that = this
    return {
      options: [{
        value: '1',
        label: '年统计'
      }, {
        value: '2',
        label: '月统计'
      }, {
        value: '3',
        label: '日统计'
      }, {
        value: '4',
        label: '时间段统计'
      }],
      startDate: "",
      pickerOptions: {
        onPick(times) {
          if (that.value === '4') that.startDate = times.minDate
        },
        disabledDate(time) {
          if (that.value === '4' && that.startDate) {
            let maxTime = new Date(that.$moment(that.startDate).endOf('year').format("YYYY-MM-DD HH:mm:ss")).getTime()
            let minTime = new Date(that.$moment(that.startDate).startOf('year').format("YYYY-MM-DD HH:mm:ss")).getTime()
            return time.getTime() > maxTime || time.getTime() < minTime
          }
        }
      },
      value: '4',
      dateType: 'daterange',
      importVisible: false,
      showSearch: true,
      showFooter: '',
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      exportUrl: 'api/v1/report/clearBill/exportExcel',
      tableToolbar: {
        id: 'modules.report.clearBillView-toolbar',
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
        {title: '序号', type: 'seq', width: 80,  align: 'center'},
        { title: '交易日期', field: 'trxDate', tableField: 'trx_date', minWidth: 180, sortable: true, align: 'center' },
        { title: '运营方名称', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, sortable: true, align: 'center' },
        { title: '服务方名称', field: 'siteName', tableField: 'site_name', minWidth: 180, sortable: true, align: 'center' },
        { title: '结算周期', field: 'tollSettlementIntervalTxt', tableField: 'toll_settlement_interval', minWidth: 180, sortable: true, align: 'center' },
        { title: '交易流水数', field: 'totalTransCnt', tableField: 'total_trans_cnt', minWidth: 180, sortable: true, align: 'center' },
        { title: '交易流水金额(元)', field: 'totalTransAmt', tableField: 'total_trans_amt', minWidth: 180, sortable: true, align: 'center' },
        { title: '结算流水数', field: 'totalSettlementCnt', tableField: 'total_settlement_cnt', minWidth: 180, sortable: true, align: 'center' },
        { title: '结算金额(元)', field: 'totalSettlementAmt', tableField: 'total_settlement_amt', minWidth: 180, sortable: true, align: 'center' },
        { title: '已清分数', field: 'totalClearCnt', tableField: 'total_clear_cnt', minWidth: 180, sortable: true, align: 'center' },
        { title: '已清分总金额(元)', field: 'totalClearAmt', tableField: 'total_clear_amt', minWidth: 180, sortable: true, align: 'center' },
        { title: '待清分数', field: 'totalUnclearCnt', tableField: 'total_unclear_cnt', minWidth: 180, sortable: true, align: 'center' },
        { title: '待清分总金额(元)', field: 'totalUnclearAmt', tableField: 'total_unclear_amt', minWidth: 180, sortable: true, align: 'center' },
        { title: '清分异常', align: 'center',
          children: [
            { title: '记账异常流水数', field: 'clearExceptionCnt', tableField: 'clear_exception_cnt', minWidth: 180, sortable: true, align: 'center' },
            { title: '记账异常总金额(元)', field: 'clearExceptionAmt', tableField: 'clear_exception_amt', minWidth: 180, sortable: true, align: 'center' },
            { title: '坏账流水数', field: 'clearBadCnt', tableField: 'clear_bad_cnt', minWidth: 180, sortable: true, align: 'center' },
            { title: '坏账总金额(元)', field: 'clearBadAmt', tableField: 'clear_bad_amt', minWidth: 180, sortable: true, align: 'center' }
          ]}
      ],
      transTime: '',
      queryParams: {
        merchantGroupId: '',
        timeScope: [],
        merchantId: '',
        siteId: '',
        nodeLevel: '',
        searchId: '',
        value: ''
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
 created() {
    var transTime = []
    var now = new Date();
    var before = 24 * 60 * 60 * 1000
    transTime.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
    transTime.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
   this.transTime = transTime
   this.queryParams.timeScope = this.transTime
   this.merchantInfo();
    this.getData()
  },

  methods: {
    timeChange() {
      if (!this.transTime) this.startDate = ''
    },
    async merchantInfo() {
      const merchantInfo = await getMerchantTreeData()
      this.ecData = merchantInfo.data
    },
    async getData() {
      // 每次查询初始化checkbox selections
      this.selections = []
      this.queryParams.value = this.value
      this.loading = true
      const { data } = await getData(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage, field: this.page.field, order: this.page.order }), this.queryParams)
      this.tableData = data.records
      this.showFooter = (this.tableData.length > 0);
      this.tableData.forEach(item => {
        item.totalTransAmt = (item.totalTransAmt / 100).toFixed(2);
        item.totalSettlementAmt = (item.totalSettlementAmt / 100).toFixed(2);
        item.totalClearAmt = (item.totalClearAmt / 100).toFixed(2);
        item.totalUnclearAmt = (item.totalUnclearAmt / 100).toFixed(2);
        item.clearExceptionAmt = (item.clearExceptionAmt / 100).toFixed(2);
        item.clearBadAmt = (item.clearBadAmt / 100).toFixed(2);
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
      this.queryParams.timeScope = []
      if (this.transTime != null && this.transTime.length > 0) {
        switch (this.value) {
          case '1':
            this.queryParams.timeScope.push(this.transTime)
            this.queryParams.timeScope.push(this.$moment(this.transTime).endOf('year').format('YYYY-MM-DD HH:mm:ss'))
            break;
          case '2':
            this.queryParams.timeScope.push(this.transTime)
            this.queryParams.timeScope.push(this.$moment(this.transTime).endOf('month').format('YYYY-MM-DD HH:mm:ss'))
            break;
          case '3':
            this.queryParams.timeScope.push(this.transTime)
            this.queryParams.timeScope.push(this.$moment(this.transTime).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
            break;
          case '4':
            this.queryParams.timeScope.push(this.$moment(this.transTime[0]).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
            this.queryParams.timeScope.push(this.$moment(this.transTime[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
            break;
        }
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams, {value: this.value})
      this.value = '4'
      this.dateType = 'daterange'
      var transTime = []
      var now = new Date();
      var before = 24 * 60 * 60 * 1000
      transTime.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
      transTime.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.transTime = transTime
      this.queryParams.timeScope = this.transTime
      this.getData()
    },
    dateTypeChange(value) {
      switch (value) {
        case '1':
          this.transTime = ''
          this.dateType = 'year'
          break;
        case '2':
          this.transTime = ''
          this.dateType = 'month'
          break;
        case '3':
          this.transTime = ''
          this.dateType = 'date'
          break;
        case '4':
          this.dateType = 'daterange'
          break;
      }
    },
    footerMethod({ columns, data }) {
      if (this.footerSum.sum == undefined) return []
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return '小计'
          }
          if (['totalTransAmt', 'totalSettlementAmt', 'totalClearAmt', 'totalUnclearAmt', 'clearExceptionAmt', 'clearBadAmt'].includes(column.property)) {
            return XEUtils.sum(data, column.property).toFixed(2)
          } else if (['tollSettlementIntervalTxt'].includes(column.property)) {
            return '-'
          } else if (['totalTransCnt', 'totalSettlementCnt', 'totalClearCnt', 'totalUnclearCnt', 'clearExceptionCnt', 'clearBadCnt'].includes(column.property)) {
            return (XEUtils.sum(data, column.property))
          }
          return []
        }),
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return '合计'
          }
          if (['totalTransAmt', 'totalSettlementAmt', 'totalClearAmt', 'totalUnclearAmt', 'clearExceptionAmt', 'clearBadAmt'].includes(column.property)) {
            return (this.footerSum.sum[column.property]/100).toFixed(2)
          } else if (['tollSettlementIntervalTxt'].includes(column.property)) {
            return '-'
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
      }else if(_columnIndex >=0 && _columnIndex <=4){
        return { rowspan: 0, colspan: 0 }
      }
    },
    cellStyle({row, column, rowIndex, columnIndex}) {
      /**
       * 改变某一列的单元格颜色
       */
      var cellStyle
      if (row.clearExceptionCnt > 0 || row.clearBadCnt > 0) {
        cellStyle = 'background: #ff4949'
      }
      if (column.property == 'clearExceptionCnt' || column.property == 'clearExceptionAmt' ||
        column.property == 'clearBadCnt' || column.property == 'clearBadAmt') {
        return cellStyle
      }
    }
  }
}
</script>

<style scoped>

</style>
