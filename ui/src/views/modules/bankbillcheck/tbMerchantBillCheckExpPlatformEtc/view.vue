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
          <el-col :span="6">
            <el-form-item
              label="对账模式"
              prop="confirmType"
            >
              <ec-select v-model="queryParams.confirmType"
                         :ec-data="[
       { value: '自动对账', label: '自动对账' },
       { value: '人工对账', label: '人工对账' }
     ]"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="核对状态"
              prop="confirmState"
            >
              <ec-select v-model="queryParams.confirmState"
                         :ec-data="[
       { value: '0', label: '待核对' },
       { value: '1', label: '异常登记' },
       { value: '2', label: '已确认' }
     ]" :multiple="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="服务类型"
              prop="serviceType"
            >
              <ec-select v-model="queryParams.serviceType" :ec-data="[
       { value: '2', label: '停车场' },
       { value: '3', label: '加油站' },
       { value: '4', label: '服务区' },
       { value: '5', label: '市政拓展' },
     ]"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="交易日期"
              prop="transDate"
            >
              <el-date-picker
                v-model="queryParams.timeScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                :key="queryParams.timeScope"
                align="right" value-format="yyyy-MM-dd HH:mm:ss" :disabled="searchDisable">
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
      max-height="1000"
      class="vxe-table-element"
      :loading="loading"
      :start-index="(page.currentPage - 1) * page.pageSize"
      :sort-config="{ trigger: 'cell' }"
      :pager-config="page"
      :columns="columns"
      :data="tableData"
      :toolbar="tableToolbar"
      :checkbox-config="{ checkMethod: checCheckboxkMethod2}"
      @sort-change="sortMethod"
      :span-method="spanMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button type="primary" @click="batchConfirmRegister" :disabled="!oneMore">批量确认</el-button>
        <el-button type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
        <!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button type="primary"
                   v-if="row.eexitCount > 0 && row.merchantGroupIdStr != '小计' && row.merchantGroupIdStr != '合计' "
                   @click="showEexit(row)" style="float: left">流水明细
        </el-button>
        <el-button type="primary"
                   v-if="row.refundCount > 0 && row.merchantGroupIdStr != '小计' && row.merchantGroupIdStr != '合计' "
                   @click="showRefundEexit(row)" style="float: left">退费流水明细
        </el-button>
        <el-button type="danger" v-if="showExceptionDetailButton(row)" @click="showTbMerchantBillCheckDetail(row)"
                   style="float: left">
          异常明细
        </el-button>
        <el-button type="warning" v-if="showExceptionButton(row)" @click="errorRegister(row)" style="float: left">异常登记
        </el-button>
        <el-button type="success"
                   v-if="(row.confirmState===0 || row.confirmState===1) && (row.merchantGroupIdStr != '小计' && row.merchantGroupIdStr != '合计' )"
                   @click="confirmRegister(row)" style="float: left">确认
        </el-button>
      </template>
    </vxe-grid>

    <detail ref="tbMerchantBillCheckExpPlatformEtcDialog" @refreshData="getData"/>
    <tbMerchantBillCheckDetail ref="tbMerchantBillCheckDetailDialog" @refreshData="getData"/>
    <park-eexit-search ref="parkEexitSearchDialog" @refreshData="getData"/>
    <gassation-eexit-search ref="gasstationEexitSearchDialog" @refreshData="getData"/>
    <refund-eexit-search ref="refundEexitSearchDialog" @refreshData="getData"/>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/bankbillcheck/tbMerchantBillCheckExpPlatformEtcApi'
import Detail from './components/detail'
import tbMerchantBillCheckDetail
  from "@/views/modules/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/components/tbMerchantBillCheckDetail";
import {download} from 'ecip-web/utils'
import ParkEexitSearch from "@/views/modules/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/components/parkEexitSearch"
import GassationEexitSearch
  from "@/views/modules/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/components/gasstationEexitSearch"
import RefundEexitSearch
  from '@/views/modules/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/components/refundEexitSearch'

export default {
  name: 'ModulesBankbillcheckTbmerchantbillcheckexpplatformetcTbMerchantBillCheckExpPlatformEtcView',
  components: {GassationEexitSearch, ParkEexitSearch, RefundEexitSearch, Detail, tbMerchantBillCheckDetail},
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      tplUrl: 'api/v1/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/excelTemplate',
      exportUrl: 'api/v1/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/exportExcel',
      tableToolbar: {
        id: 'modules.bankbillcheck.tbmerchantbillcheckexpplatformetc.tbMerchantBillCheckExpPlatformEtcView-toolbar',
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
        {type: 'checkbox', width: 40, fixed: 'left', align: 'center'},
        {
          title: '交易日期',
          field: 'transDate',
          tableField: 'trans_date',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '拓展方',
          field: 'merchantGroupIdStr',
          tableField: 'merchant_group_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '序号', type: 'seq', width: 80, fixed: 'left', align: 'center'},
        {
          title: '运营方',
          field: 'merchantIdStr',
          tableField: 'merchant_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '场景', field: 'siteIdStr', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '本系统总流水数',
          field: 'systemTotalCount',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '本系统流水应收总金额(元)',
          field: 'systemTotalAmountStr',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '对账接口总流水数',
          field: 'otherTotalCount',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '对账接口流水应收总金额(元)',
          field: 'otherTotalAmountStr',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '流水差异数',
          field: 'diffTotalCount',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '流水差异总金额(元)',
          field: 'diffTotalAmountStr',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '服务类型',
          field: 'serviceTypeStr',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '对账模式', field: 'confirmType', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '核对状态',
          field: 'confirmStateStr',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '确认后流水总数', field: 'totalCount', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '确认后实收总金额(元)',
          field: 'totalAmountStr',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '核对时间',
          field: 'confirmTimeStr',
          tableField: 'site_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '备注', field: 'remark', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '确认人', field: 'confirmMan', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '操作', width: 510, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        timeScope: [],
        transDate: '',
        searchId: '',
        nodeLevel: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        confirmState: "0,1",
        confirmType: '',
        serviceType: ''
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
    this.defaultScope()
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
      if (this.queryParams.timeScope.length > 0) {
        this.queryParams.timeScope[0] = this.$moment(this.queryParams.timeScope[0]).startOf('day').format('YYYY-MM-DD HH:mm:ss');
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss');
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.defaultScope()
      this.getData()
    },
    spanMethod({row, _rowIndex, _columnIndex}) {
      console.log(_columnIndex)
      if ((row.merchantGroupIdStr === '小计' || row.merchantGroupIdStr === '合计') && _columnIndex === 1) {
        return {rowspan: 0, colspan: 0}
      } else if ((row.merchantGroupIdStr === '小计' || row.merchantGroupIdStr === '合计') && _columnIndex === 3) {
        return {rowspan: 1, colspan: 4}
      } else if ((row.merchantGroupIdStr === '小计' || row.merchantGroupIdStr === '合计') && (_columnIndex >= 4 && _columnIndex <= 5))
        return {
          rowspan: 0,
          colspan: 0
        }
    },
    errorRegister(row) {
      this.$refs.tbMerchantBillCheckExpPlatformEtcDialog.init("errorRegister", row);
    },
    confirmRegister(row) {
      this.$refs.tbMerchantBillCheckExpPlatformEtcDialog.init("confirmRegister", row);
    },
    batchConfirmRegister() {
      this.$confirm(`是否确认所选记录`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(async () => {
        this.$refs.tbMerchantBillCheckExpPlatformEtcDialog.init('batchConfirmRegister', this.selections)
      })

    },
    checCheckboxkMethod2({row}) {
      return (row.confirmState === 0 || row.confirmState === 1) && (row.merchantGroupIdStr != '小计' && row.merchantGroupIdStr != '合计')
    },
    showTbMerchantBillCheckDetail(row) {
      this.$refs.tbMerchantBillCheckDetailDialog.init(row.transDate, row.merchantGroupId, row.merchantId, row.siteId);
    },
    showEexit(row) {
      if (row.serviceType == 2) {
        this.$refs.parkEexitSearchDialog.init(row.siteId, 3, row.transDate + " 00:00:00", row.transDate + " 23:59:59")
      } else if (row.serviceType == 3) {
        this.$refs.gasstationEexitSearchDialog.init(row.siteId, 3, row.transDate + " 00:00:00", row.transDate + " 23:59:59")
      }
    },
    showRefundEexit(row) {
      this.$refs.refundEexitSearchDialog.init(row.siteId, 3, row.serviceType, row.transDate + " 00:00:00", row.transDate + " 23:59:59")
    },
    defaultScope() {
      var timeScope = []
      var now = new Date();
      var before = 24 * 60 * 60 * 1000
      timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
      timeScope.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.queryParams.timeScope = timeScope
    },
    showExceptionDetailButton(row) {
      return row.confirmType == '自动对账' && (row.diffTotalCount && row.diffTotalCount > 0) && (row.merchantGroupIdStr != '小计' && row.merchantGroupIdStr != '合计')
    },
    showExceptionButton(row) {
      return row.confirmState < 2 && (row.merchantGroupIdStr != '小计' && row.merchantGroupIdStr != '合计')
    }
  }
}
</script>

<style scoped>

</style>
