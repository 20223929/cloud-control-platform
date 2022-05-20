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
                v-model="transTime"
                :type="dateType"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="value"
                :picker-options="pickerOptions"
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
        <el-button v-permission="['tbEtcTransactionEexit:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, deleteByIds} from '@/api/modules/report/tbEtcTransactionEexitApi'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesDemoTbEtcTransactionEexitView',
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
      value: '4',
      dateType: 'datetimerange',
      importVisible: false,
      showSearch: true,
      showFooter: '',
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      exportUrl: 'api/v1/demo/tbEtcTransactionEexit/exportExcel',
      tableToolbar: {
        id: 'modules.demo.tbEtcTransactionEexitView-toolbar',
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
      columns: [
        // { type: 'checkbox', width: 40, fixed: 'left', align: 'center' },
        {title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center'},
        {title: '运营方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, align: 'center'},
        {title: '场景', field: 'siteName', tableField: 'site_name', minWidth: 180, align: 'center'},
        {title: '交易日期', field: 'tradeDay', tableField: 'trade_day', minWidth: 180, align: 'center'},
        {title: '已结算流水数', field: 'settlementCount', tableField: 'SETTLEMENT_COUNT', minWidth: 180, align: 'center'},
        {
          title: '已结算商户应收总金额(元)',
          field: 'settlementMerchantTotalFee',
          tableField: 'SETTLEMENT_MERCHANT_TOTAL_FEE',
          minWidth: 180,
          align: 'center'
        },
        {title: '未结算流水数', field: 'unsettlementCount', tableField: 'UNSETTLEMENT_COUNT', minWidth: 180, align: 'center'},
        {
          title: '未结算商户应收总金额(元)',
          field: 'unsettlementMerchantTotalFee',
          tableField: 'UNSETTLEMENT_MERCHANT_TOTAL_FEE',
          minWidth: 180,
          align: 'center'
        },
        {title: '联网中心总流水数', field: 'etcTotalCount', tableField: 'ETC_TOTAL_COUNT', minWidth: 180, align: 'center'},
        {
          title: '商户应收联网中心总金额(元)',
          field: 'etcMerchantTotalFee',
          tableField: 'ETC_MERCHANT_TOTAL_FEE',
          minWidth: 180,
          align: 'center'
        },
        {title: '银联总流水数', field: 'bankTotalCount', tableField: 'BANK_TOTAL_COUNT', minWidth: 180, align: 'center'},
        {
          title: '商户应收银联总金额(元)',
          field: 'bankMerchantTotalFee',
          tableField: 'BANK_MERCHANT_TOTAL_FEE',
          minWidth: 180,
          align: 'center'
        },
        {title: '银联退费流水数', field: 'bankRefundCount', tableField: 'BANK_REFUND_COUNT', minWidth: 180, align: 'center'},
        {
          title: '商户应扣银联总退费金额(元)',
          field: 'bankMerchantRefundTotalFee',
          tableField: 'BANK_MERCHANT_REFUND_TOTAL_FEE',
          minWidth: 180,
          align: 'center'
        },
        {title: '总流水数', field: 'totalCount', tableField: 'TOTAL_COUNT', minWidth: 180, align: 'center'},
        {
          title: '商户应收总金额(元)',
          field: 'merchantTotalFee',
          tableField: 'MERCHANT_TOTAL_FEE',
          minWidth: 180,
          align: 'center'
        }
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
  mounted() {
    this.$watch(this.$refs.vxe.getCheckboxRecords, (newValue, oldValue) => {
      this.selections = newValue
      this.onlyOne = this.selections.length === 1
      this.oneMore = this.selections.length > 0
    })
  },
  created() {
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
        item.settlementMerchantTotalFee = (item.settlementMerchantTotalFee / 100).toFixed(2);
        item.unsettlementMerchantTotalFee = (item.unsettlementMerchantTotalFee / 100).toFixed(2);
        item.etcMerchantTotalFee = (item.etcMerchantTotalFee / 100).toFixed(2);
        item.bankMerchantTotalFee = (item.bankMerchantTotalFee / 100).toFixed(2);
        item.bankMerchantRefundTotalFee = (item.bankMerchantRefundTotalFee / 100).toFixed(2);
        item.merchantTotalFee = (item.merchantTotalFee / 100).toFixed(2);
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
          this.dateType = 'datetimerange'
          break;
      }
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
      this.transTime = this.startDate = ''
      this.getData()
    },
    footerMethod({columns, data}) {
      if (this.footerSum.merchantAccountSum == undefined) return []
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return '合计'
          }
          if (['merchantGroupName', 'merchantName', 'siteName', 'tradeDay'].includes(column.property)) {
            return []
          }
          if (column.property.indexOf("Fee") > -1) {
            return (this.footerSum.merchantAccountSum[column.property] / 100).toFixed(2)
          }
          return this.footerSum.merchantAccountSum[column.property]
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
    }
  }
}
</script>

<style scoped>

</style>
