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
          <el-col :span="6">
            <el-form-item
              label="服务类型"
              prop="serviceType"
            >
              <ec-select v-model="queryParams.serviceType" dict-type="service_type"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="确认状态"
              prop="confirmState"
            >
              <ec-select v-model="queryParams.confirmState" dict-type="bank_bill_check_state"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="对账交易日期"
              prop="transDate"
            >
              <ec-date-range v-model="queryParams" begin-key="beginTransDate" end-key="endTransDate"
                             format="yyyy-MM-dd"/>
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
      :span-method="spanMethod"
      :row-style="rowStyle"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button type="primary" @click="batchConfirm" :disabled="!oneMore">批量确认</el-button>
        <el-button v-permission="['tbBankBillCheck:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button v-permission="['tbBankBillCheck:export']" type="primary" @click="exportExcelDetail">导出流水明细
        </el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:service_type="{ row }">
        <ec-select :value="row.serviceType" dict-type="service_type" readonly/>
      </template>
      <template v-slot:confirm_state="{ row }">
        <ec-select :value="row.confirmState" dict-type="bank_bill_check_state" readonly/>
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['tbBankBillCheck:edit']" v-if="row.confirmState==='0'" type="warning"
                   @click="edit(row)" style="float: left">确认
        </el-button>
        <el-button v-permission="['tbBankBillCheck:delete']" v-if="row.transDate!==null && row.transDate!==undefined"
                   type="success" @click="details(row)" style="float: left">详情
        </el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="银行对账核对表数据导入"
      :visible="importVisible"
      @onSuccess="getData"
      @close="importVisible = false"
    >
      <template v-slot:extra_params="{ extraParams }">
        <el-form-item label="是否生成新主键">
          <ec-radio-group v-model="extraParams.isNewPk"
                          :ec-data="[ {value: false, label: '否'}, {value: true, label: '是' } ]"/>
        </el-form-item>
        <el-form-item label="导入策略">
          <ec-radio-group v-model="extraParams.strategy"
                          :ec-data="[ {value: 'ignore', label: '忽略'}, {value: 'update', label: '更新' } ]"/>
        </el-form-item>
      </template>
    </ec-import>

    <detail ref="tbBankBillCheckDialog" @refreshData="getData"/>
    <check-detail ref="checkDetailDialog" @refreshData="getData"/>

  </div>
</template>

<script>
import CheckDetail from './components/checkDetail'
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, deleteByIds} from '@/api/modules/bankbillcheck/tbBankBillCheckApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesBankbillcheckTbBankBillCheckView',
  components: {Detail, CheckDetail},
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/bankbillcheck/tbBankBillCheck/importExcel',
      tplUrl: 'api/v1/bankbillcheck/tbBankBillCheck/excelTemplate',
      exportUrl: 'api/v1/bankbillcheck/tbBankBillCheck/exportExcel',
      tplEexitUrl: 'api/v1/bankbillcheck/tbBankBillCheck/excelEexitTemplate',
      exportEexitUrl: 'api/v1/bankbillcheck/tbBankBillCheck/exportEexitExcel',
      tableToolbar: {
        id: 'modules.bankbillcheck.tbBankBillCheckView-toolbar',
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
        perfect: true
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
          formatter: ['date', 'yyyy-MM-dd'], align: "center"
        },
        {
          title: '拓展方',
          field: 'merchantGroupIdName',
          tableField: 'merchant_group_id',
          minWidth: 180,
          sortable: false,
          align: "center"
        },
        {
          title: '运营方',
          field: 'merchantIdName',
          tableField: 'merchant_id',
          minWidth: 180,
          sortable: false,
          align: "center"
        },
        {title: '场景', field: 'siteIdName', tableField: 'site_id', minWidth: 180, sortable: false, align: "center"},
        {
          title: '服务类型',
          field: 'serviceTypeName',
          tableField: 'service_type',
          minWidth: 180,
          sortable: false,
          align: "center"
        },
        {
          title: '银行流水总数',
          field: 'bankTotalCount',
          tableField: 'bank_total_count',
          minWidth: 180,
          sortable: false,
          align: "center"
        },
        {
          title: '银行流水总金额(元)',
          field: 'bankTotalAmount',
          tableField: 'bank_total_amount',
          minWidth: 180,
          sortable: false, align: "center"
        },
        {
          title: '本系统流水总数',
          field: 'platformTotalCount',
          tableField: 'platform_total_count',
          minWidth: 180,
          sortable: false, align: "center"
        },
        {
          title: '本系统流水总金额(元）',
          field: 'platformTotalAmount',
          tableField: 'platform_total_amount',
          minWidth: 180,
          sortable: false, align: "center"
        },
        {
          title: '流水总数差额',
          field: 'diffTotalCount',
          tableField: 'diff_total_count',
          minWidth: 180,
          sortable: false,
          align: "center"
        },
        {
          title: '流水总金额差额(元)',
          field: 'diffTotalAmount',
          tableField: 'diff_total_amount',
          minWidth: 180,
          sortable: false, align: "center"
        },
        {
          title: '确认状态',
          field: 'confirmStateName',
          tableField: 'confirm_state',
          minWidth: 180,
          sortable: false,
          align: "center"
        },
        {title: '确认人', field: 'confirmMan', tableField: 'confirm_man', minWidth: 180, sortable: false, align: "center"},
        {
          title: '确认时间',
          field: 'confirmTime',
          tableField: 'confirm_time',
          minWidth: 180,
          sortable: false,
          formatter: ['date', 'yyyy-MM-dd HH:mm:dd'], align: "center"
        },
        {title: '备注', field: 'remark', tableField: 'remark', minWidth: 180, sortable: false, align: "center"},
        {title: '操作', width: 150, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        transDate: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        serviceType: '',
        confirmState: '',
        searchId: '',
        nodeLevel: ''
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
  watch: {//监听路由变化
    '$route.path': {
      immediate: true,
      handler(newVal, oldVal) {
        var confirmState = this.$route.query.confirmState
        // console.log("检测路由参数" + confirmState)
        this.queryParams.confirmState = confirmState
        // if (newVal === '/tbBankBillCheckView') {
        //   this.isBgc = true;
        // } else {
        //   this.isBgc = false;
        // }
      }
    }
  },
  methods: {
    checCheckboxkMethod2({row}) {
      return row.confirmState === '0'
    },
    batchConfirm() {
      this.$refs.tbBankBillCheckDialog.init('batchConfirm', this.selections)
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
      // debugger
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
    add() {
      this.$refs.tbBankBillCheckDialog.init('add')
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.tbBankBillCheckDialog.init('edit', record)
      //this.$refs.checkDetailDialog.init({ a: 'dsjldjf' })
    },
    details(row) {
      debugger
      this.$refs.checkDetailDialog.init(row)
    },
    deleteByIds(row) {
      const additionMsg = row && row.transDate && row.merchantGroupId && row.merchantId && row.siteId ? '此条记录' : '选中记录'
      this.$confirm(`确定删除${additionMsg}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async () => {
        const deleteIds = []
        if (row && row.transDate && row.merchantGroupId && row.merchantId && row.siteId) {
          deleteIds.push(row)
        } else {
          deleteIds.push(this.selections)
        }
        await deleteByIds(deleteIds)
        await this.getData()
        this.$notify.success('删除成功')
      })
    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    async exportExcelDetail() {
      this.loading = true
      await download({url: this.exportEexitUrl, method: 'post', data: this.queryParams})
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
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.getData()
    },
    spanMethod({row, _rowIndex, _columnIndex}) {
      // console.log(_columnIndex)
      if ((row.merchantGroupIdName === '小计' || row.merchantGroupIdName === '合计') && _columnIndex === 1) {
        return {rowspan: 0, colspan: 0}
      } else if ((row.merchantGroupIdName === '小计' || row.merchantGroupIdName === '合计') && _columnIndex === 2) {
        return {rowspan: 1, colspan: 5}
      } else if ((row.merchantGroupIdName === '小计' || row.merchantGroupIdName === '合计') && (_columnIndex >= 3 && _columnIndex <= 5))
        return {
          rowspan: 0,
          colspan: 0
        }
    },
    rowStyle(row) {
      if (row.row.diffTotalAmount != 0) {
        return 'color:red!important;';
      }
    }
  }
}
</script>

<style scoped>

</style>
