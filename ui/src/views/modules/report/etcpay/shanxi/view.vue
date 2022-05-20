<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="searchId">
              <ec-select-tree
                ref="merchantTreeData"
                v-model="queryParams.searchId"
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
                v-model="transTime"
                :type="dateType"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="value"
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
      :span-method="spanMethod"
      :footer-span-method="footerRowspanMethod"
      :footer-method="footerMethod"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['etcPayReport:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/report/shanxi/etcPayReportApi'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesReportEtcPayReportView',
  mixins: [permissionMixin],
  data() {
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
      value: '4',
      dateType: 'daterange',
      showSearch: true,
      loading: false,
      importUrl: 'api/v1/report/etcPayReport/importExcel',
      tplUrl: 'api/v1/report/etcPayReport/excelTemplate',
      exportUrl: 'api/v1/report/etcPayReport/exportExcel',
      tableToolbar: {
        id: 'modules.report.etcPayReportView-toolbar',
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
        {title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center'},
        {title: '运营方', field: 'merchantName', tableField: 'merchantName', minWidth: 180, align: 'center'},
        {title: '场景', field: 'siteName', tableField: 'site_name', minWidth: 180, align: 'center'},
        {title: '交易日期', field: 'tradeDay', tableField: 'trade_day', minWidth: 180, align: 'center'},
        {title: '交易流水数', field: 'totalCount', tableField: 'total_count', minWidth: 180, align: 'center'},
        {title: '交易金额(元)', field: 'totalFee', tableField: 'total_fee', minWidth: 180, align: 'center'},
        {title: '已扣款流水数', field: 'payCount', tableField: 'pay_count', minWidth: 180, align: 'center'},
        {title: '已扣款金额(元)', field: 'payFee', tableField: 'pay_fee', minWidth: 180, align: 'center'},
        {title: '未扣款流水数', field: 'unpayCount', tableField: 'unpay_count', minWidth: 180, align: 'center'},
        {title: '未扣款金额(元)', field: 'unpayFee', tableField: 'unpay_fee', minWidth: 180, align: 'center'}
      ],
      transTime: '',
      queryParams: {
        timeScope: [],
        searchId: '',
        nodeLevel: '',
        value: ''
      },
      showFooter: '',
      footerSum: ''
    }
  },
  created() {
    this.defaultScope()
    this.getData()
  },
  methods: {
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
      this.tableData.forEach(item => {
        item.totalFee = (item.totalFee / 100).toFixed(2);
        item.payFee = (item.payFee / 100).toFixed(2);
        item.unpayFee = (item.unpayFee / 100).toFixed(2);
      })
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
      if (this.transTime != null && this.transTime.length > 0) {
        this.queryParams.timeScope = []
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
            this.transTime[1] = this.$moment(this.transTime[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss')
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
      // this.transTime = ''
      this.value = '4'
      this.dateType = 'daterange'
      this.defaultScope()
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
      if (this.footerSum.sum == undefined) return []
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return '合计'
          }
          if (['merchantGroupName', 'merchantName', 'siteName', 'tradeDay'].includes(column.property)) {
            return []
          }
          if (column.property.indexOf("Fee") > -1) {
            return (this.footerSum.sum[column.property] / 100).toFixed(2)
          }
          return this.footerSum.sum[column.property]
        })
      ]
      return footData
    },
    footerRowspanMethod({_rowIndex, _columnIndex}) {
      if (_columnIndex == 0) {
        return {rowspan: 1, colspan: 4}
      } else if (_columnIndex >= 1 && _columnIndex <= 3) {
        return {rowspan: 0, colspan: 0}
      }
    },
    spanMethod({row, _rowIndex, _columnIndex}) {
      if (row.merchantGroupName == '小计' && _columnIndex == 0) return {rowspan: 1, colspan: 4}
      else if (row.merchantGroupName == '小计' && (_columnIndex >= 1 && _columnIndex <= 3)) return {
        rowspan: 0,
        colspan: 0
      }
    },
    defaultScope() {
      var transTime = []
      var now = new Date();
      var before = 24 * 60 * 60 * 1000
      transTime.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
      transTime.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.transTime = transTime
      this.queryParams.timeScope = this.transTime
    },
  }
}
</script>

<style scoped>

</style>
