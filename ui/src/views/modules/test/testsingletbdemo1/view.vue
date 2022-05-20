<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-form-item
          label="名称"
          prop="name"
        >
          <el-input v-model="queryParams.name" type="text" placeholder="名称" />
        </el-form-item>
        <el-form-item
          label="编码"
          prop="code"
        >
          <el-input v-model="queryParams.code" type="text" placeholder="编码" />
        </el-form-item>
        <el-form-item
          label="校验类型"
          prop="validateType"
        >
          <ec-select v-model="queryParams.validateType" dict-type="validateType" />
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
      max-height="1000"
      class="vxe-table-element"
      :loading="loading"
      :start-index="(page.currentPage - 1) * page.pageSize"
      :sort-config="{ trigger: 'cell' }"
      :pager-config="page"
      :columns="columns"
      :data="tableData"
      :toolbar="tableToolbar"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button v-permission="['testSingleTBDemo1:add']" type="primary" @click="add()">新增</el-button>
        <el-button v-permission="['testSingleTBDemo1:edit']" type="warning" :disabled="!onlyOne" @click="edit()">编辑</el-button>
        <el-button v-permission="['testSingleTBDemo1:delete']" type="danger" :disabled="!oneMore" @click="deleteByIds()">删除</el-button>
        <el-button v-permission="['testSingleTBDemo1:import']" type="success" @click="importVisible = true">导入</el-button>
        <el-button v-permission="['testSingleTBDemo1:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button @click="getData">刷新</el-button>
        <el-button @click="showSearch = !showSearch">搜索</el-button>
      </template>
      <template v-slot:validate_type="{ row }">
        <ec-select :value="row.validateType" dict-type="validateType" readonly />
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['testSingleTBDemo1:edit']" type="warning" @click="edit(row)">编辑</el-button>
        <el-button v-permission="['testSingleTBDemo1:delete']" type="danger" @click="deleteByIds(row.id)">删除</el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="单表简单示例1(作为自定义对象)数据导入"
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

    <detail ref="testSingleTBDemo1Dialog" @refreshData="getData" />

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getData, deleteByIds } from '@/api/modules/test/testsingletbdemo1/testSingleTBDemo1Api'
import Detail from './components/detail'
import { download } from 'ecip-web/utils'

export default {
  name: 'ModulesTestTestsingletbdemo1TestSingleTBDemo1View',
  components: { Detail },
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: false,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/test/testsingletbdemo1/testSingleTBDemo1/importExcel',
      tplUrl: 'api/v1/test/testsingletbdemo1/testSingleTBDemo1/excelTemplate',
      exportUrl: 'api/v1/test/testsingletbdemo1/testSingleTBDemo1/exportExcel',
      tableToolbar: {
        id: 'modules.test.testsingletbdemo1.testSingleTBDemo1View-toolbar',
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
        { title: '名称', field: 'name', minWidth: 180, sortable: true },
        { title: '编码', field: 'code', minWidth: 180, sortable: true },
        { title: '校验类型', field: 'validateType', minWidth: 180, sortable: true, slots: { default: 'validate_type' }},
        { title: '加入日期', field: 'inDate', minWidth: 180, sortable: true, formatter: ['date', 'yyyy-MM-dd'] },
        { title: '操作', width: 150, fixed: 'right', align: 'center', slots: { default: 'operation' }}
      ],
      queryParams: {
        name: '',
        code: '',
        validateType: ''
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
    sortMethod({ column, property, order }) {
      this.page.field = property
      this.page.order = order
      this.getData()
    },
    add() {
      this.$refs.testSingleTBDemo1Dialog.init('add')
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.testSingleTBDemo1Dialog.init('edit', record)
    },
    deleteByIds(rowId) {
      const additionMsg = rowId ? '此条记录' : '选中记录'
      this.$confirm(`确定删除${additionMsg}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async() => {
        const deleteIds = []
        if (rowId) {
          deleteIds.push(rowId)
        } else {
          deleteIds.push(...this.selections.map(row => row.id))
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
    }
  }
}
</script>

<style scoped>

</style>