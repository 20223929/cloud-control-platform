<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="merchantId">
              <ec-select-tree
                ref="merchantTreeData"
                v-model="queryParams.searchId"
                url="api/v1/merchant/tbMerchant/data"
                value-field="id"
                attr-name="name"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="服务类型"
              prop="serviceType"
            >
              <ec-select v-model="queryParams.serviceType" dict-type="service_type"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="交易日期"
              prop="transTime"
            >
              <el-date-picker
                v-model="queryParams.timeScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                align="right" value-format="yyyy-MM-dd" format="yyyy-MM-dd">
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
      :footer-method="footerMethod"
      :footer-span-method="footerRowspanMethod"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['tbBankSettlement:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['tbBankSettlement:view']" type="success" @click="view(row)" style="float: left">明细
        </el-button>
      </template>
    </vxe-grid>

    <detail ref="tbBankSettlementDialog" @refreshData="getData"/>

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/settlement/tbBankSettlementApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesSettlementTbBankSettlementView',
  components: {Detail},
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: true,
      loading: false,
      exportUrl: 'api/v1/settlement/tbBankSettlement/exportExcel',
      tableToolbar: {
        id: 'modules.settlement.tbBankSettlementView-toolbar',
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
        {title: '序号', type: 'seq', width: 80, fixed: 'left', align: 'center'},
        {
          title: '银行交易日期',
          field: 'bankTransDate',
          tableField: 'bank_trans_date',
          minWidth: 180,
          align: 'center',
          sortable: true
        },
        {title: '服务类型', field: 'serviceTypeDesc', tableField: 'service_type', minWidth: 180, align: 'center'},
        {title: '运营方', field: 'merchantName', tableField: 'merchant_id', minWidth: 180, align: 'center'},
        {
          title: '应付商户总金额(元)',
          field: 'merchantRecvFee',
          tableField: 'merchant_recv_fee',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '支付流水总数',
          field: 'deductionTotalCount',
          tableField: 'deduction_total_count',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '客户实付总金额（元）',
          field: 'deductionTotalFee',
          tableField: 'deduction_total_fee',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '优惠总金额（元）',
          field: 'discountTotalFee',
          tableField: 'discount_total_fee',
          minWidth: 180,
          align: 'center'
        },
        {title: '退费流水总数', field: 'refundTotalCount', tableField: 'refund_total_count', minWidth: 180, align: 'center'},
        {title: '退费流水总金额(元)', field: 'refundTotalFee', tableField: 'refund_total_fee', minWidth: 180, align: 'center'},
        {
          title: '银行手续费(元)',
          field: 'bankServiceTotalFee',
          tableField: 'bank_service_total_fee',
          minWidth: 180,
          align: 'center'
        },
        {title: '操作', width: 80, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        timeScope: [],
        searchId: '',
        nodeLevel: '',
        serviceType: ''
      },
      footerSum: '',
      showFooter: ''
    }
  },
  created() {
    var timeScope = []
    var now = new Date();
    var before = 24 * 60 * 60 * 1000
    timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
    timeScope.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
    this.queryParams.timeScope = timeScope
    this.getData()
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
      this.tableData.forEach((item, index, array) => {
        item.merchantRecvFee = (item.merchantRecvFee / 100).toFixed(2);
        item.deductionTotalFee = (item.deductionTotalFee / 100).toFixed(2);
        item.discountTotalFee = (item.discountTotalFee / 100).toFixed(2);
        item.bankServiceTotalFee = (item.bankServiceTotalFee / 100).toFixed(2);
        item.refundTotalFee = (item.refundTotalFee / 100).toFixed(2);
      })
      this.footerSum = data.dataMap;
      this.showFooter = (this.tableData.length > 0)
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
    view(row) {
      this.$refs.tbBankSettlementDialog.init('view', row)
    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        this.queryParams.nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
      } else {
        this.queryParams.nodeLevel = ''
      }
      if (this.queryParams.timeScope.length > 0) {
        this.queryParams.timeScope[0] = this.$moment(this.queryParams.timeScope[0]).startOf('day').format('YYYY-MM-DD HH:mm:ss');
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss');
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      var timeScope = []
      var now = new Date()
      var before = 24 * 60 * 60 * 1000
      timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
      timeScope.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.queryParams.timeScope = timeScope
      this.getData()
    },
    footerMethod({columns, data}) {
      if (this.footerSum.sum == undefined) return []
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 1) {
            return '小计'
          }
          if (['bankTransDate', 'serviceTypeDesc', 'merchantName'].includes(column.property)) {
            return []
          }
          if (column.property) {
            if (column.property.indexOf("Fee") > -1)
              return XEUtils.sum(data, column.property).toFixed(2)
            else
              return XEUtils.sum(data, column.property)
          }
        }),
        columns.map((column, columnIndex) => {
          if (columnIndex === 1) {
            return '合计'
          }
          if (['bankTransDate', 'serviceTypeDesc', 'merchantName'].includes(column.property)) {
            return []
          }
          if (column.property) {
            if (column.property.indexOf("Fee") > -1)
              return (this.footerSum.sum[column.property] / 100).toFixed(2)
            else
              return this.footerSum.sum[column.property]
          }
        })
      ]
      return footData
    },
    footerRowspanMethod({_rowIndex, _columnIndex}) {
      if (_columnIndex == 1) {
        return {rowspan: 1, colspan: 3}
      } else if (_columnIndex >= 2 && _columnIndex <= 3) {
        return {rowspan: 0, colspan: 0}
      }
    }
  }
}
</script>

<style scoped>

</style>
