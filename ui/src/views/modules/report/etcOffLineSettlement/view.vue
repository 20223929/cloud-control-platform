<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="queryParams.searchId">
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
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {download} from 'ecip-web/utils'
import {getData} from '@/api/modules/report/shanxi/EtcOffLineSettlement'

export default {
  name: 'ModulesEtcOffLineSettlementSearchView',
  components: {},
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
      transTime: '',
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
      dateType: 'datetimerange',
      importVisible: false,
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      tplUrl: 'api/v1/sx/EtcOffLineSettlementController/excelTemplate',
      exportUrl: 'api/v1/sx/EtcOffLineSettlementController/exportExcel',
      tableToolbar: {
        id: 'modules.api-v1-sx-EtcOffLineSettlementController-toolbar',
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
        {
          title: '运营方',
          field: 'merchantIdStr',
          tableField: 'merchant_id_str',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '场景', field: 'siteIdStr', tableField: 'site_id_str', minWidth: 180, sortable: false, align: 'center'},
        {title: '交易日期', field: 'transDate', tableField: 'trade_date', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '交易流水数',
          field: 'totalCount',
          tableField: 'total_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '交易金额(元)',
          field: 'totalAmountStr',
          tableField: 'total_amount_str',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '已扣款流水数', align: 'center',
          children: [{
            title: '已扣款未结算流水数',
            field: 'deductionCount',
            tableField: 'deduction_count',
            minWidth: 180,
            sortable: false,
            align: 'center'
          },
            {
              title: '已扣款已结算流水数',
              field: 'settlementCount',
              tableField: 'settlement_count',
              minWidth: 180,
              sortable: false,
              align: 'center'
            },
            {title: '小计', field: 'deductionAndSettlementSubCount', minWidth: 180, sortable: false, align: 'center'}]
        },
        {
          title: '已扣款流水金额', align: 'center',
          children: [{
            title: '已扣款未结算流水金额(元)',
            field: 'deductionAmountStr',
            tableField: 'deduction_amount_str',
            minWidth: 180,
            sortable: false,
            align: 'center'
          },
            {
              title: '已扣款已结算流水金额(元)',
              field: 'settlementAmountStr',
              tableField: 'settlement_amount_str',
              minWidth: 180,
              sortable: false,
              align: 'center'
            },
            {title: '小计', field: 'deductionAndSettlementSubAmountStr', minWidth: 180, sortable: false, align: 'center'}]
        },
        {
          title: '未扣款流水数',
          field: 'unDeductionCount',
          tableField: 'un_deduction_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '未扣款流水金额(元)',
          field: 'unDeductionAmountStr',
          tableField: 'un_deduction_amount_str',
          minWidth: 180,
          sortable: false,
          align: 'center'
        }
      ],
      queryParams: {
        transDate: '',
        searchId: '',
        nodeLevel: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        beginSearchDate: '',
        endSearchDate: '',
        searchScope: '4'
      }
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
    timeChange() {
      if (!this.transTime) this.startDate = ''
    },
    async getData() {
      // 每次查询初始化checkbox selections
      this.selections = []
      this.loading = true
      const {data} = await getData(Object.assign({}, {
        size: this.page.pageSize,
        current: this.page.currentPage,
        field: this.page.field,
        order: this.page.order
      }), this.queryParams)
      this.tableData = data.records
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
      this.queryParams.timeScope = []
      this.queryParams.searchScope = this.value
      if (this.transTime != null && this.transTime.length > 0) {
        switch (this.value) {
          case '1':
            this.queryParams.beginSearchDate = this.transTime
            this.queryParams.endSearchDate = this.$moment(this.transTime).endOf('year').format('YYYY-MM-DD HH:mm:ss')
            break;
          case '2':
            this.queryParams.beginSearchDate = this.transTime
            this.queryParams.endSearchDate = this.$moment(this.transTime).endOf('month').format('YYYY-MM-DD HH:mm:ss')
            break;
          case '3':
            this.queryParams.beginSearchDate = this.transTime
            this.queryParams.endSearchDate = this.$moment(this.transTime).endOf('day').format('YYYY-MM-DD HH:mm:ss')
            break;
          case '4':
            this.queryParams.beginSearchDate = this.transTime[0]
            this.queryParams.endSearchDate = this.transTime[1]
            break;
        }
      }
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        let nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
        switch (nodeLevel) {
          case 1:
            this.queryParams.merchantGroupId = this.queryParams.searchId;
            break;
          case 2:
            this.queryParams.merchantId = this.queryParams.searchId;
            break;
          case 3:
            this.queryParams.siteId = this.queryParams.searchId;
            break;
        }
      } else {
        this.queryParams.nodeLevel = ''
        this.queryParams.siteId = ''
        this.queryParams.merchantId = ''
        this.queryParams.merchantGroupId = ''
      }
      Object.assign(this.$data.page, this.$options.data().page)
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.$data.queryParams = {};
      this.transTime = this.startDate = ''
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
    spanMethod({row, _rowIndex, _columnIndex}) {
      console.log(_columnIndex)
      if ((row.transDate === '合计') && _columnIndex === 0) {
        return {rowspan: 0, colspan: 0}
      } else if ((row.transDate === '合计') && (_columnIndex === 1)) {
        return {rowspan: 0, colspan: 0}
      } else if ((row.transDate === '合计') && (_columnIndex == 2))
        return {
          rowspan: 1,
          colspan: 3
        }
    }
  }
}
</script>

<style scoped>

</style>
