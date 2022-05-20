<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-form-item
          label="四级设备编号"
          prop="equipmentId"
        >
          <el-input v-model="queryParams.equipmentId" type="text" placeholder="四级设备编号" />
        </el-form-item>
        <el-form-item class="searchItem" :label-width="'50px'">
          <el-button type="primary" @click="queryData">查询</el-button>
          <el-button type="default" @click="handleReset">重置</el-button>
        </el-form-item>
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
      :row-style="rowStyle"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button v-permission="['tbBankBillCheckDetail:add']" type="primary" @click="add()">新增</el-button>
        <el-button v-permission="['tbBankBillCheckDetail:edit']" type="warning" :disabled="!onlyOne" @click="edit()">编辑</el-button>
        <el-button v-permission="['tbBankBillCheckDetail:delete']" type="danger" :disabled="!oneMore" @click="deleteByIds()">删除</el-button>
        <el-button v-permission="['tbBankBillCheckDetail:import']" type="success" @click="importVisible = true">导入</el-button>
        <el-button v-permission="['tbBankBillCheckDetail:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button @click="getData">刷新</el-button>
        <el-button @click="showSearch = !showSearch">搜索</el-button>
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['tbBankBillCheckDetail:edit']" type="warning" @click="edit(row)">编辑</el-button>
        <el-button v-permission="['tbBankBillCheckDetail:delete']" type="danger" @click="deleteByIds(row)">删除</el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="银行对账明细表数据导入"
      :visible="importVisible"
      @onSuccess="getData"
      @close="importVisible = false"
    >
      <template v-slot:extra_params="{ extraParams }">
        <el-form-item label="是否生成新主键">
          <ec-radio-group v-model="extraParams.isNewPk" :ec-data="[ {value: false, label: '否'}, {value: true, label: '是' } ]" />
        </el-form-item>
        <el-form-item label="导入策略">
          <ec-radio-group v-model="extraParams.strategy" :ec-data="[ {value: 'ignore', label: '忽略'}, {value: 'update', label: '更新' } ]" />
        </el-form-item>
      </template>
    </ec-import>

    <detail ref="tbBankBillCheckDetailDialog" @refreshData="getData" />

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getData, deleteByIds } from '@/api/modules/bankbillcheck/tbBankBillCheckDetailApi'
import Detail from './components/detail'
import { download } from 'ecip-web/utils'

export default {
  name: 'ModulesBankbillcheckTbBankBillCheckDetailView',
  components: { Detail },
  mixins: [permissionMixin],
  props: {
    param: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      importVisible: false,
      showSearch: false,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/bankbillcheck/tbBankBillCheckDetail/importExcel',
      tplUrl: 'api/v1/bankbillcheck/tbBankBillCheckDetail/excelTemplate',
      exportUrl: 'api/v1/bankbillcheck/tbBankBillCheckDetail/exportExcel',
      tableToolbar: {
        id: 'modules.bankbillcheck.tbBankBillCheckDetailView-toolbar',
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
        { type: 'checkbox', width: 40, fixed: 'left', align: 'center' },
        { title: '对账交易日期', field: 'transDate', tableField: 'trans_date', minWidth: 180, sortable: true },
        { title: '拓展方', field: 'merchantGroupIdName', tableField: 'merchant_group_id', minWidth: 180, sortable: true },
        { title: '运营方', field: 'merchantIdName', tableField: 'merchant_id', minWidth: 180, sortable: true },
        { title: '场景', field: 'siteIdName', tableField: 'site_id', minWidth: 180, sortable: true },
        { title: '四级设备编号', field: 'equipmentId', tableField: 'equipment_id', minWidth: 180, sortable: true },
        { title: '本系统流水总数', field: 'platformTotalCount', tableField: 'platform_total_count', minWidth: 180, sortable: true },
        { title: '本系统流水总金额(元)', field: 'platformTotalAmount', tableField: 'platform_total_amount', minWidth: 180, sortable: true },
        { title: '银行流水总数', field: 'bankTotalCount', tableField: 'bank_total_count', minWidth: 180, sortable: true },
        { title: '银行流水总金额(元)', field: 'bankTotalAmount', tableField: 'bank_total_amount', minWidth: 180, sortable: true },
        { title: '流水总数差额', field: 'diffTotalCount', tableField: 'diff_total_count', minWidth: 180, sortable: true },
        { title: '流水总金额差额(元)', field: 'diffTotalAmount', tableField: 'diff_total_amount', minWidth: 180, sortable: true },
      ],
      queryParams: {
        equipmentId: ''
      }
    }
  },
  watch: {
    param: {
      handler() {
        this.getData()
      },
      immediate: false
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
      this.loading = true
      const { data } = await getData(Object.assign({},
        { size: this.page.pageSize, current: this.page.currentPage, field: this.page.field, order: this.page.order }),
      { ...this.queryParams, ...this.param })
      this.tableData = data.records
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
    add() {
      this.$refs.tbBankBillCheckDetailDialog.init('add')
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.tbBankBillCheckDetailDialog.init('edit', record)
    },
    deleteByIds(row) {
      const additionMsg = row && row.transDate && row.merchantGroupId && row.merchantId && row.siteId && row.equipmentId ? '此条记录' : '选中记录'
      this.$confirm(`确定删除${additionMsg}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async() => {
        const deleteIds = []
        if (row && row.transDate && row.merchantGroupId && row.merchantId && row.siteId && row.equipmentId) {
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
      await download({ url: this.exportUrl, method: 'post', data: this.queryParams })
      this.loading = false
    },
    queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.getData()
    },
    rowStyle(row){
      if (row.row.diffTotalAmount != 0) {
        return 'color:red!important;';
      }
    }
  }
}
</script>

<style scoped>

</style>
