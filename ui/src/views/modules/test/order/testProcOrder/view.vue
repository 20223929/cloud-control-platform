<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline :label-width="'100px'">
        <el-form-item label="订单号" prop="code">
          <el-input v-model="queryParams.code" type="text" placeholder="订单号" />
        </el-form-item>
        <el-form-item label="订单名称" prop="name">
          <el-input v-model="queryParams.name" type="text" placeholder="订单名称" />
        </el-form-item>
        <el-form-item class="searchItem" :label-width="'50px'">
          <el-button type="primary" @click="getData">查询</el-button>
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
      max-height="1000"
      class="vxe-table-element"
      :loading="loading"
      :start-index="(page.currentPage - 1) * page.pageSize"
      :pager-config="page"
      :columns="columns"
      :data="tableData"
      :toolbar="tableToolbar"
      @page-change="handlePageChange"
      @checkbox-all="({ selection }) => { selections = selection }"
      @checkbox-change="({ selection }) => { selections = selection }"
    >
      <template v-slot:toolbar_buttons>
        <el-button v-permission="['testProcOrder:add']" type="primary" @click="add()">新增</el-button>
        <el-button v-permission="['testProcOrder:edit']" type="warning" :disabled="banState1" @click="edit()">编辑</el-button>
        <el-button v-permission="['testProcOrder:remove']" type="danger" :disabled="banState2" @click="remove()">删除</el-button>
        <el-button @click="handleReset">刷新</el-button>
        <el-button @click="showSearch = !showSearch">搜索</el-button>
        <el-button v-permission="['testProcOrder:import']" @click="importVisible = true">导入</el-button>
        <el-button v-permission="['testProcOrder:export']" @click="exportExcel">导出</el-button>
      </template>

      <template v-slot:operation="{ row }">
        <el-button v-permission="['testProcOrder:edit']" type="warning" @click="edit(row)">编辑</el-button>
        <el-button v-permission="['testProcOrder:remove']" type="danger" @click="remove(row.id)">删除</el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="订单管理数据导入"
      :visible="importVisible"
      @onSuccess="getData"
      @close="importVisible = false"
    />

    <modal ref="testProcOrderDialog" @refreshData="getData" />

  </div>
</template>

<script>
import mixin from 'ecip-web/utils/common-mixin'
import { getData, remove } from '@/api/modules/test/order/testProcOrderApi'
import { getToken } from '@/utils/auth'
import Modal from './components/modal'

export default {
  name: 'ModulesTestOrderTestProcOrderView',
  components: { Modal },
  mixins: [...mixin],
  data() {
    return {
      importVisible: false,
      showSearch: false,
      loading: false,
      banState1: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      banState2: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/test/order/testProcOrder/importExcel',
      tplUrl: 'api/v1/test/order/testProcOrder/excelTemplate',
      exportUrl: `api/v1/test/order/testProcOrder/exportExcel?token=${getToken()}`,
      tableToolbar: {
        id: 'modules.test.order.testProcOrderView-toolbar',
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
        { title: '订单号', key: 'code', field: 'code', minWidth: 100, showOverflow: true },
        { title: '订单名称', key: 'name', field: 'name', minWidth: 100, showOverflow: true },
        { title: '订单类型', key: 'type', field: 'type', minWidth: 100, showOverflow: true },
        { title: '价格', key: 'price', field: 'price', minWidth: 100, showOverflow: true },
        { title: '备注信息', key: 'remarks', field: 'remarks', minWidth: 100, showOverflow: true },
        { title: '操作', width: 150, fixed: 'right', align: 'center', slots: { default: 'operation' }}
      ],
      queryParams: {
        code: '',
        name: ''
      }
    }
  },
  watch: {
    selections(val) {
      this.banState1 = val.length !== 1
      this.banState2 = val.length === 0
    }
  },
  created() {
    this.getData()
  },
  methods: {
    async getData() {
      // 每次查询初始化checkbox selections
      this.selections = []
      this.loading = true
      const { data } = await getData(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage }), this.queryParams)
      this.tableData = data.records
      this.page.total = data.total
      this.loading = false
    },
    handlePageChange({ currentPage, pageSize }) {
      this.page.currentPage = currentPage
      this.page.pageSize = pageSize
      this.getData()
    },
    add() {
      this.$refs.testProcOrderDialog.init('add')
      // this.$router.push(`/testProcOrderAdd`)
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.testProcOrderDialog.init('edit', record)
      // this.$router.push(`/testProcOrderEdit/${row.id}`)
    },
    remove(id) {
      this.$confirm(`是否删除${id ? '此条记录' : '选中记录'}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let deleteIds = []
        if (id) {
          deleteIds.push(id)
        } else {
          deleteIds = this.selections.map(item => item.id)
        }
        remove(deleteIds).then(res => {
          this.getData()
          this.$notify.success('删除成功')
        })
      })
    },
    exportExcel() {
      window.open(this.exportUrl)
    },
    handleReset() {
      this.$refs.searchForm.resetFields()
      this.getData()
    }
  }
}
</script>

<style scoped>

</style>
