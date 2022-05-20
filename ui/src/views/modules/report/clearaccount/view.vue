<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="merchantId">
              <ec-select-tree
                ref="merchantTreeData"
                v-model="queryParams.merchantId"
                url="api/v1/merchant/tbMerchant/data"
                value-field="id"
                attr-name="name"
              />
            </el-form-item>
          </el-col>
          <el-col :span="18">
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
            <el-form-item>
              <el-select v-model="value" placeholder="请选择" @change="dateTypeChange">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
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
      :show-footer="showFooter"
      max-height="1000"
      class="vxe-table-element"
      :loading="loading"
      :start-index="(page.currentPage - 1) * page.pageSize"
      :sort-config="{ trigger: 'cell' }"
      :pager-config="page"
      :columns="columns"
      :data="tableData"
      :toolbar="tableToolbar"
      :footer-span-method="footerRowspanMethod"
      :footer-method="footerMethod"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['clearAccountReport:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
    </vxe-grid>

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/report/clearAccountReportApi'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesMerchantClearAccountReportView',
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
      showFooter: '',
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      exportUrl: 'api/v1/merchant/clearAccountReport/exportExcel',
      tableToolbar: {
        id: 'modules.merchant.clearAccountReportView-toolbar',
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
        {title: '序号', type: 'seq', width: 80, fixed: 'left', align: 'center'},
        {title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center'},
        {title: '运营方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, align: 'center'},
        {title: '场景', field: 'siteName', tableField: 'site_id', minWidth: 180, align: 'center'},
        {title: '交易日期', field: 'tradeDay', tableField: 'trade_day', minWidth: 180, align: 'center', sortable: true},
        {title: '交易流水数', field: 'totalCount', tableField: 'total_count', minWidth: 180, align: 'center'},
        {title: '交易流水实收金额(元)', field: 'totalActualFee', tableField: 'total_actual_fee', minWidth: 180, align: 'center'},
        {title: '已结算流水数', field: 'settlementCount', tableField: 'settlement_count', minWidth: 180, align: 'center'},
        {
          title: '已结算商户应收总金额额(元)',
          field: 'settlementMerchantTotalFee',
          tableField: 'settlement_merchant_total_fee',
          minWidth: 180,
          align: 'center'
        },
        {title: '已结算与已清分流水数差值', field: 'differCount', tableField: 'differ_count', minWidth: 180, align: 'center'},
        {title: '已结算与已清分金额差额(元)', field: 'differFee', tableField: 'differ_fee', minWidth: 180, align: 'center'},
        {title: '已清分流水数', field: 'clearCount', tableField: 'clear_count', minWidth: 180, align: 'center'},
        {title: '已清分总金额(元)', field: 'clearFee', tableField: 'clear_fee', minWidth: 180, align: 'center'},
        {title: '待清分流水数', field: 'unclearCount', tableField: 'unclear_count', minWidth: 180, align: 'center'},
        {title: '待清分总金额(元)', field: 'unclearFee', tableField: 'unclear_fee', minWidth: 180, align: 'center'},
        {title: '异常流水数', field: 'exceptionCount', tableField: 'exception_count', minWidth: 180, align: 'center'},
        {title: '异常总金额(元)', field: 'exceptionFee', tableField: 'exception_fee', minWidth: 180, align: 'center'}
      ],
      transTime: '',
      queryParams: {
        timeScope: [],
        merchantId: '',
        nodeLevel: '',
        value: ''
      },
      footerSum: ''
    }
  },
  created() {
    var transTime = []
    var now = new Date();
    var before = 24 * 60 * 60 * 1000
    transTime.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
    transTime.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
    this.transTime = transTime
    this.queryParams.timeScope = this.transTime
    this.getData()
  },
  methods: {
    timeChange() {
      if (!this.transTime) this.startDate = ''
    },
    async getData() {
      // 每次查询初始化checkbox selections
      this.selections = []
      this.queryParams.value = this.value
      this.loading = true
      const {data} = await getData(Object.assign({}, {
        size: this.page.pageSize,
        current: this.page.currentPage,
        field: this.page.field,
        order: this.page.order
      }), this.queryParams)
      this.tableData = data.records
      this.footerSum = data.dataMap
      this.showFooter = (this.tableData.length > 0);
      this.page.total = data.total
      this.loading = false
    },
    handlePageChange({currentPage, pageSize}) {
      this.page.currentPage = currentPage
      this.page.pageSize = pageSize
      this.getData()
    },
    sortMethod({column, property, order}) {
      this.page.field = (column.own.tableField && 'a.'.concat(column.own.tableField)) || property
      this.page.order = order
      this.getData()
    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
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
            this.queryParams.timeScope = this.transTime
            break;
        }
      }
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        this.queryParams.nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
      } else {
        this.queryParams.nodeLevel = ''
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams, {value: this.value})
      // this.transTime = this.startDate = ''
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
          this.transTime = ''
          this.dateType = 'daterange'
          break;
      }
    },
    footerMethod({columns, data}) {
      if (this.footerSum.clearAccountReportSum == undefined) return []
      console.log(this.footerSum.clearAccountReportSum)
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 1) {
            return '合计'
          }
          if (['merchantGroupName', 'merchantName', 'siteName', 'tradeDay'].includes(column.property)) {
            return []
          }
          return this.footerSum.clearAccountReportSum[column.property]
        })
      ]
      return footData
    },
    footerRowspanMethod({_rowIndex, _columnIndex}) {
      if (_columnIndex == 1) {
        return {rowspan: 1, colspan: 4}
      } else if (_columnIndex >= 2 && _columnIndex <= 4) {
        return {rowspan: 0, colspan: 0}
      }
    }
  }
}
</script>

<style scoped>

</style>
