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
              label="确认状态"
              prop="confirmState"
            >
              <ec-select v-model="queryParams.confirmState"
                         :ec-data="[
       { value: '0', label: '待确认' },
       { value: '1', label: '已确认' },
       { value: '2', label: '待补确认' },
        { value: '3', label: '已补确认' }
     ]"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="交易日期"
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
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button type="primary" @click="batchConfirm" :disabled="!oneMore">批量确认</el-button>
        <el-button type="primary" @click="batchSecondConfirm" :disabled="!oneMore">批量补确认</el-button>
        <el-button v-permission="['tbCenterBillCheck:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button v-permission="['tbCenterBillCheck:export']" type="primary" @click="exportEexitExcel">导出明细</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['tbCenterBillCheck:edit']" v-if="row.confirmState===0" type="success"
                   @click="myConfrim(row)" style="float: left">确认
        </el-button>
        <el-button v-permission="['tbCenterBillCheck:edit']" v-if="row.confirmState===2" type="danger"
                   @click="mySecondConfrim(row)" style="float: left">补确认
        </el-button>
        <el-button v-permission="['tbBankBillCheck:delete']" v-if="row.transDate!==null && row.transDate!==undefined"
                   type="success" @click="details(row)" style="float: left">详情
        </el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="联网中心对账核对表数据导入"
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

    <detail ref="tbCenterBillCheckDialog" @refreshData="getData"/>
    <check-detail ref="checkDetailDialog" @refreshData="getData"/>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, deleteByIds} from '@/api/modules/bankbillcheck/tbCenterBillCheckApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'
import CheckDetail from "@/views/modules/bankbillcheck/tbCenterBillCheck/components/checkDetail";

export default {
  name: 'ModulesBankbillcheckTbCenterBillCheckView',
  components: {Detail, CheckDetail},
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/bankbillcheck/tbCenterBillCheck/importExcel',
      tplUrl: 'api/v1/bankbillcheck/tbCenterBillCheck/excelTemplate',
      exportUrl: 'api/v1/bankbillcheck/tbCenterBillCheck/exportExcel',
      tplEexitUrl: 'api/v1/bankbillcheck/tbCenterBillCheck/excelTemplateEexit',
      exportEexitUrl: 'api/v1/bankbillcheck/tbCenterBillCheck/exportExcelEexit',
      tableToolbar: {
        id: 'modules.bankbillcheck.tbCenterBillCheckView-toolbar',
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
          field: 'merchantGroupIdName',
          tableField: 'merchant_group_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '运营方',
          field: 'merchantIdName',
          tableField: 'merchant_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '场景', field: 'siteIdName', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '联网中心流水数量',
          field: 'centerTotalCount',
          tableField: 'confirm_total_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '联网中心流水金额(元)',
          field: 'centerTotalAmountStr',
          tableField: 'confirm_total_amount',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '用户确认流水数量',
          field: 'confirmTotalCount',
          tableField: 'confirm_total_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '用户确认流水金额(元)',
          field: 'confirmTotalAmountStr',
          tableField: 'confirm_total_amount',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '确认状态',
          field: 'confirmStateStr',
          tableField: 'confirm_man',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '确认人', field: 'confirmMan', tableField: 'confirm_man', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '确认时间',
          field: 'confirmTime',
          tableField: 'confirm_time',
          minWidth: 180,
          sortable: false,
          formatter: ['date', 'yyyy-MM-dd'],
          align: 'center'
        },
        {title: '备注', field: 'remark', tableField: 'remark', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '补确认人',
          field: 'secondConfirmMan',
          tableField: 'second_confirm_man',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '补确认时间',
          field: 'secondConfirmTime',
          tableField: 'second_confirm_time',
          minWidth: 180,
          sortable: false,
          formatter: ['date', 'yyyy-MM-dd'],
          align: 'center'
        },
        {
          title: '补确认备注',
          field: 'secondConfirmRemark',
          tableField: 'second_confirm_remark',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '操作', width: 160, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        transDate: '',
        searchId: '',
        nodeLevel: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: ''
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
    checCheckboxkMethod2({row}) {
      return row.confirmState === 0 || row.confirmState === 2
    },
    myConfrim(row) {
      const record = row || this.selections[0]
      this.$refs.tbCenterBillCheckDialog.init('confirm', record)
    },
    mySecondConfrim(row) {
      const record = row || this.selections[0]
      this.$refs.tbCenterBillCheckDialog.init('secondConfirm', record)
    },
    details(row) {
      debugger
      this.$refs.checkDetailDialog.init(row)
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
    add() {
      this.$refs.tbCenterBillCheckDialog.init('add')
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.tbCenterBillCheckDialog.init('edit', record)
    },
    batchConfirm() {
      this.$confirm(`所选记录是否已核对无异常流水`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(async () => {
        this.$refs.tbCenterBillCheckDialog.init('batchConfirm', this.selections)
      })
    },
    batchSecondConfirm() {
      this.$confirm(`所选记录是否已核对无异常流水`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(async () => {
        this.$refs.tbCenterBillCheckDialog.init('batchSecondConfirm', this.selections)
      })
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
    async exportEexitExcel() {
      this.loading = true
      await download({url: this.exportEexitUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      debugger
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
      console.log(_columnIndex)
      if ((row.merchantGroupIdName === '小计' || row.merchantGroupIdName === '合计') && _columnIndex === 1) {
        return {rowspan: 0, colspan: 0}
      } else if ((row.merchantGroupIdName === '小计' || row.merchantGroupIdName === '合计') && _columnIndex === 2) {
        return {rowspan: 1, colspan: 4}
      } else if ((row.merchantGroupIdName === '小计' || row.merchantGroupIdName === '合计') && (_columnIndex >= 3 && _columnIndex <= 4))
        return {
          rowspan: 0,
          colspan: 0
        }
    }
  }
}
</script>

<style scoped>

</style>
