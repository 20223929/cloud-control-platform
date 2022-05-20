<template>
  <div>
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-form-item label="左树主表外键" prop="leftTree.treeId">
          <ec-select-tree
            v-model="queryParams.leftTree.treeId"
            url="api/v1/test/testtreetable/testLeftTree/data"
            value-field="id"
          />
        </el-form-item>
        <el-form-item
          label="名称"
           prop="name"
        >
          <el-input v-model="queryParams.name" type="text" placeholder="名称" />
        </el-form-item>
        <el-form-item
          label="性别"
           prop="sex"
        >
          <ec-radio-group v-model="queryParams.sex" dict-type="sex" />
        </el-form-item>
        <el-form-item
          label="多选"
           prop="checkbox"
        >
          <ec-checkbox-group v-model="queryParams.checkbox" dict-type="urlTarget" />
        </el-form-item>
        <el-form-item
          label="类型"
           prop="type"
        >
          <ec-select v-model="queryParams.type" dict-type="payType" />
        </el-form-item>
        <el-form-item
          label="负责人"
           prop="principal.id"
        >
          <ec-user-modal
            v-model="queryParams.principal.id"
            value-field="id"
          />
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
        <el-button v-permission="['testRightTb:add']" type="primary" @click="add()">新增</el-button>
        <el-button v-permission="['testRightTb:edit']" type="warning" :disabled="!onlyOne" @click="edit()">编辑</el-button>
        <el-button v-permission="['testRightTb:delete']" type="danger" :disabled="!oneMore" @click="deleteByIds()">删除</el-button>
        <el-button v-permission="['testRightTb:remove']" type="warning" :disabled="!oneMore" @click="removeByIds()">移除</el-button>
        <el-button v-permission="['testRightTb:import']" type="success" @click="importVisible = true">导入</el-button>
        <el-button v-permission="['testRightTb:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button @click="getData">刷新</el-button>
        <el-button @click="showSearch = !showSearch">搜索</el-button>
      </template>
      <template v-slot:sex="{ row }">
        <ec-radio-group :value="row.sex" dict-type="sex" readonly/>
      </template>
      <template v-slot:checkbox="{ row }">
        <ec-checkbox-group :value="row.checkbox" dict-type="urlTarget" readonly/>
      </template>
      <template v-slot:type="{ row }">
        <ec-select :value="row.type" dict-type="payType" readonly />
      </template>
      <template v-slot:dept="{ row }">
        <ec-app-dept-select
          :value="row.dept && row.dept.id"
          value-field="id"
          readonly
        />
      </template>
      <template v-slot:test_single_tb="{ row }">
        <ec-grid-modal
          :value="row.testSingleTb && row.testSingleTb.testId"
          title="测试单表Grid对象选择"
          url="api/v1/test/testsingletbdemo1/testSingleTBDemo1/data"
          fieldKeys="name|code|validateType|inDate"
          fieldLabels="名称|编码|校验类型|加入日期"
          searchKeys="name|codename|codename|code"
          searchLabels="名称|编码名称|编码名称|编码"
          value-field="testId"
          value-column="test_id"
          readonly
        />
      </template>
      <template v-slot:principal="{ row }">
        <ec-user-modal
          :value="row.principal && row.principal.id"
          value-field="id"
          readonly
        />
      </template>
      <template v-slot:area="{ row }">
        <ec-area-select
          :value="row.area && row.area.id"
          value-field="id"
          readonly
        />
      </template>
      <template v-slot:file="{ row }">
        <ec-file-modal :value="row.file" readonly />
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['testRightTb:edit']" type="warning" @click="edit(row)">编辑</el-button>
        <el-button v-permission="['testRightTb:delete']" type="danger" @click="deleteByIds(row.id)">删除</el-button>
        <el-button v-permission="['testRightTb:remove']" type="warning" @click="removeByIds(row.id)">移除</el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="左树右表(右表附表)数据导入"
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

    <detail ref="testRightTbDialog" @refreshData="getData" />
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getData, removeByIds, deleteByIds } from '@/api/modules/test/testtreetable/testRightTbApi'
import Detail from './components/detail'
import { download } from 'ecip-web/utils'

export default {
  name: 'ModulesTestTesttreetableTestRightTbView',
  components: { Detail },
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: false,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      parentId: '',
      importUrl: 'api/v1/test/testtreetable/testRightTb/importExcel',
      tplUrl: 'api/v1/test/testtreetable/testRightTb/excelTemplate',
      exportUrl: 'api/v1/test/testtreetable/testRightTb/exportExcel',
      tableToolbar: {
        id: 'modules.test.testtreetable.testRightTbView-toolbar',
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
        { title: '性别', field: 'sex', minWidth: 180, sortable: true, slots: { default: 'sex' }},
        { title: '多选', field: 'checkbox', minWidth: 180, sortable: true, slots: { default: 'checkbox' }},
        { title: '类型', field: 'type', minWidth: 180, sortable: true, slots: { default: 'type' }},
        { title: '组织', field: 'dept', minWidth: 180, slots: { default: 'dept' }},
        { title: '测试单表Grid对象', field: 'testSingleTb', minWidth: 180, slots: { default: 'test_single_tb' }},
        { title: '负责人', field: 'principal', minWidth: 180, slots: { default: 'principal' }},
        { title: '区域', field: 'area', minWidth: 180, slots: { default: 'area' }},
        { title: '省市区', field: 'city', minWidth: 180, sortable: true },
        { title: '文件', field: 'file', minWidth: 180, sortable: true, slots: { default: 'file' }},
        { title: '操作', width: 240, fixed: 'right', align: 'center', slots: { default: 'operation' }}
      ],
      queryParams: {
        leftTree: {
          treeId: ''
        },
        queryLeftTree: '',
        name: '',
        sex: '',
        checkbox: '',
        type: '',
        principal: {
          id: ''
        }
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
  methods: {
    init(parentId) {
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      Object.assign(this.$data.page, this.$options.data().page)
      this.parentId = parentId
      this.queryParams.queryLeftTree = parentId
      this.getData()
    },
    async getData() {
      // 每次查询初始化checkbox selections
      this.selections = []
      this.loading = true
      const { data } = await getData(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage, field: this.page.field, order: this.page.order }), this.queryParams)
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
      this.$refs.testRightTbDialog.init(this.parentId, 'add')
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.testRightTbDialog.init(this.parentId,'edit', record)
    },
    removeByIds(rowId) {
      const additionMsg = rowId ? '此条记录' : '选中记录'
      this.$confirm(`确定移除${additionMsg}?(逻辑删除,数据库数据不删除)`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async() => {
        const removeIds = []
        if (rowId) {
          removeIds.push(rowId)
        } else {
          removeIds.push(...this.selections.map(row => row.id))
        }
        await removeByIds(removeIds)
        await this.getData()
        this.$notify.success('移除成功')
      })
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
      this.queryParams.queryLeftTree = this.parentId
      this.getData()
    }
  }
}
</script>

<style scoped>

</style>