<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="服务类型" prop="serviceType">
              <ec-select
                v-model="queryParams.serviceType"
                dict-type="service_type"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="结算周期" prop="interval">
              <ec-select
                v-model="queryParams.interval"
                dict-type="settlementInterval"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="运营方" prop="searchId">
              <el-select-tree
                ref="merchantTreeData"
                v-model="queryParams.searchId"
                :data="merchantData"
                :props="props"
                check-strictly
                filterable
              />
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item
              label="交易日期"
              prop="trxDate"
            >
              <el-date-picker
                v-model="queryParams.transTime"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                align="right" value-format="yyyy-MM-dd">
              </el-date-picker>
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
      :cell-style="cellStyle"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['gdetc:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['gdetc:reject']" type="primary" @click="rejectDetail(row)"
                   :disabled="row.rejectGdetcCnt <= 0">预支抵扣详情
        </el-button>
      </template>
    </vxe-grid>
    <RejectDetail ref="rejectDetailDialog" @refreshData="getData"/>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, getMerchantTreeData} from '@/api/modules/gdetc/gdetc'
import RejectDetail from '../settlementReject/view'
import {download} from 'ecip-web/utils'

export default {
  name: 'gdetcProfitView',
  mixins: [permissionMixin],
  components: {RejectDetail},
  data() {
    return {
      props: {label: 'name', children: 'children', value: 'id'},
      ecData: [],
      showSearch: true,
      loading: false,
      exportUrl: 'api/v1/gdetcprofit/exportExcel',
      tableToolbar: {
        id: 'modules.gdetc.gdetcProfitView-toolbar',
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
        {title: '运营方', field: 'merchantName', tableField: 'merchantName', minWidth: 180, align: 'center'},
        {title: '服务方', field: 'siteName', tableField: 'site_name', minWidth: 180, align: 'center'},
        {title: '交易日期', field: 'trxDate', tableField: 'trx_date', minWidth: 180, align: 'center'},
        {
          title: '结算周期',
          field: 'tollSettlementIntervalFormat',
          tableField: 'toll_settlement_interval',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '通行结算日期',
          field: 'tollSettlementDate',
          tableField: 'toll_settlement_date',
          minWidth: 180,
          align: 'center',
          formatter: ['date', 'yyyy-MM-dd']
        },
        {
          title: '粤通卡交易总量', align: 'center',
          children: [
            {
              title: '交易笔数',
              field: 'totalGdetcCnt',
              tableField: 'total_gdetc_cnt',
              minWidth: 180,
              sortable: false,
              align: 'center'
            },
            {
              title: '交易金额(元)',
              field: 'totalGdetcAmt',
              tableField: 'total_gdetc_amt',
              minWidth: 180,
              sortable: false,
              align: 'center'
            },
          ]
        },
        {
          title: '粤通卡预支抵扣交易总量', align: 'center',
          children: [
            {
              title: '预支抵扣笔数',
              field: 'rejectGdetcCnt',
              tableField: 'reject_gdetc_cnt',
              minWidth: 180,
              sortable: false,
              align: 'center'
            },
            {
              title: '预支抵扣金额(元)',
              field: 'rejectGdetcAmt',
              tableField: 'reject_gdetc_amt',
              minWidth: 180,
              sortable: false,
              align: 'center'
            },
          ]
        },
        {
          title: '应结算总金额(元)',
          field: 'actualGdetcAmt',
          tableField: 'actual_gdetc_amt',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '中心结算服务费率',
          field: 'centerServiceRateFormat',
          tableField: 'center_service_rate',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '联合电服收益(元)',
          field: 'serviceGdetcAmt',
          tableField: 'service_gdetc_amt',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '操作', minWidth: 180, sortable: false, align: 'center', fixed: 'right', slots: {default: 'operation'}}
      ],
      queryParams: {
        transTime: [],
        serviceType: '2',
        searchId: '',
        merchantId: '',
        merchantGroupId: '',
        siteId: '',
        interval: ''
      },
      showFooter: '',
      footerSum: ''
    }
  },
  async created() {
    this.getData()
    const merchantInfo = await getMerchantTreeData()
    this.ecData = merchantInfo.data
  },
  methods: {
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
      this.footerSum = data.dataMap
      this.showFooter = (this.tableData.length > 0);
      this.tableData.forEach(item => {
        item.totalGdetcAmt = (item.totalGdetcAmt / 100).toFixed(2);
        item.rejectGdetcAmt = (item.rejectGdetcAmt / 100).toFixed(2);
        item.actualGdetcAmt = (item.actualGdetcAmt / 100).toFixed(2);
        item.serviceGdetcAmt = item.serviceGdetcAmt.toFixed(6);
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
      this.queryParams.merchantGroupId = ''
      this.queryParams.merchantId = ''
      this.queryParams.siteId = ''
      if (this.transTime && this.transTime.length > 0) {
        this.queryParams.startDate = this.transTime[0]
        this.queryParams.endDate = this.transTime[1]
      }
      if (this.$refs.merchantTreeData.selectedLabel) {
        switch (this.$refs.merchantTreeData.getCurrentNode().nodeLevel) {
          case 1:
            this.queryParams.merchantGroupId = this.queryParams.searchId
            break
          case 2:
            this.queryParams.merchantId = this.queryParams.searchId
            break
          case 3:
            this.queryParams.siteId = this.queryParams.searchId
            break
        }
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams, {serviceType: '2'})
      this.getData()
    },
    footerMethod({columns, data}) {
      if (this.footerSum.sum == undefined) return []
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return '小计'
          }
          if (column.property) {
            if (column.property.indexOf("Amt") > -1) {
              if (column.property === 'serviceGdetcAmt') {
                return XEUtils.sum(data, column.property).toFixed(6)
              }
              return XEUtils.sum(data, column.property).toFixed(2)
            } else if (column.property.indexOf("Cnt") > -1) {
              return XEUtils.sum(data, column.property)
            } else if (column.property.indexOf("centerServiceRateFormat") > -1) {
              return '-'
            } else {
              return []
            }
          }
        }),
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return '合计'
          }
          if (column.property) {
            if (column.property.indexOf("Amt")  > -1) {
              if (column.property === 'serviceGdetcAmt') {
                return this.footerSum.sum[column.property].toFixed(6)
              }
              return (this.footerSum.sum[column.property] / 100).toFixed(2)
            } else{
              return this.footerSum.sum[column.property]
            }
          }
        })
      ]
      return footData
    },
    footerRowspanMethod({_rowIndex, _columnIndex}) {
      if (_columnIndex == 0) {
        return {rowspan: 1, colspan: 5}
      } else if (_columnIndex >= 1 && _columnIndex <= 4) {
        return {rowspan: 0, colspan: 0}
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
    cellStyle({row, column}) {
      if (row.rejectGdetcCnt > 0) {
        if (column.property === 'rejectGdetcCnt' || column.property === 'rejectGdetcAmt') {
          return 'background-color:red!important;'
        }
      }
    },
    rejectDetail(row) {
      this.$refs.rejectDetailDialog.init({settlementId: row.sysId, isGdetc: 1})
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
