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
          label="加入日期"
          prop="inDate"
        >
          <ec-date-range v-model="queryParams" begin-key='beginInDate' end-key='endInDate' format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>
        <el-form-item
          label="下拉选择测试"
          prop="selectTest"
        >
          <ec-select v-model="queryParams.selectTest" dict-type="vehicleType" />
        </el-form-item>
        <el-form-item
          label="用户选择测试"
          prop="userTest.id"
        >
          <ec-user-modal
            v-model="queryParams.userTest.id"
            value-field="id"
          />
        </el-form-item>
        <el-form-item class="searchItem" :label-width="'50px'">
          <el-button type="primary" size="mini" @click="getTreeData">查询</el-button>
          <el-button type="default" size="mini" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <vxe-virtual-tree
      ref="vxe"
      row-key
      row-id="treeId"
      show-overflow
      show-header-overflow
      max-height="1000"
      resizable
      border
      highlight-hover-row
      class="vxe-table-element"
      sync-resize
      auto-resize
      :loading="loading"
      :toolbar="tableToolbar"
      :columns="columns"
      :data="treeData"
      :tree-config="{ children: 'children' }"
      :checkbox-config="{ checkStrictly: !checkStrictly, checkField: 'checked', halfField: 'indeterminate'}"
    >
      <template v-slot:toolbar_buttons>
        <el-button :disabled="treeData.length === 0" @click="toggleTree">展开/收起全部</el-button>
        <el-button v-permission="['testTreeTB:add']" type="primary" @click="add()">新增</el-button>
        <el-button v-permission="['testTreeTB:edit']" type="warning" :disabled="!onlyOne" @click="edit()">编辑</el-button>
        <el-button v-permission="['testTreeTB:delete']" type="danger" :disabled="!oneMore" @click="deleteByIds()">删除</el-button>
        <el-button v-permission="['testTreeTB:remove']" type="warning" :disabled="!oneMore" @click="removeByIds()">移除</el-button>
        <el-button v-permission="['testTreeTB:import']" type="success" @click="importVisible = true">导入</el-button>
        <el-button v-permission="['testTreeTB:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button @click="getData">刷新</el-button>
        <el-button @click="showSearch = !showSearch">搜索</el-button>
      </template>

      <template v-slot:radio_test="{ row }">
        <ec-radio-group :value="row.radioTest" dict-type="enableState" readonly/>
      </template>
      <template v-slot:checkbox_test="{ row }">
        <ec-checkbox-group :value="row.checkboxTest" dict-type="typeNode" readonly/>
      </template>
      <template v-slot:select_test="{ row }">
        <ec-select :value="row.selectTest" dict-type="vehicleType" readonly />
      </template>
      <template v-slot:user_test="{ row }">
        <ec-user-modal
          :value="row.userTest && row.userTest.id"
          value-field="id"
          readonly
        />
      </template>
      <template v-slot:dept_test="{ row }">
        <ec-app-dept-select
          :value="row.deptTest && row.deptTest.id"
          value-field="id"
          readonly
        />
      </template>
      <template v-slot:file_test="{ row }">
        <ec-file-modal :value="row.fileTest" readonly />
      </template>
      <template v-slot:grid_test="{ row }">
        <ec-grid-modal
          :value="row.gridTest && row.gridTest.testId"
          title="grid选择测试选择"
          url="api/v1/test/testsingletbdemo1/testSingleTBDemo1/data"
          fieldKeys="testId|name|code|validateType"
          fieldLabels="主键|名称|编码|校验类型"
          searchKeys="name|code"
          searchLabels="名称|编码"
          value-field="testId"
          value-column="test_id"
          readonly
        />
      </template>
      <template v-slot:area_test="{ row }">
        <ec-area-select
          :value="row.areaTest && row.areaTest.id"
          value-field="id"
          readonly
        />
      </template>

      <template v-slot:operation="{ row }">
        <el-button v-permission="['testTreeTB:edit']" size="mini" type="warning" @click="edit(row)">编辑</el-button>
        <el-button v-permission="['testTreeTB:delete']" size="mini" type="danger" @click="deleteByIds(row.treeId)">删除</el-button>
        <el-dropdown
          v-permission="['testTreeTB:remove', 'testTreeTB:add']"
          trigger="click"
          style="padding-left:10px;padding-right:10px;"
          @command="command => dropDownCmd(command, row)"
        >
          <el-button type="text" icon="el-icon-arrow-down" />
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-permission="['testTreeTB:add']" command="addChild">添加下级</el-dropdown-item>
            <el-dropdown-item v-permission="['testTreeTB:remove']" command="removeById">移除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>
    </vxe-virtual-tree>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="树示例数据导入"
      :visible="importVisible"
      @onSuccess="getTreeData"
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
    <detail ref="testTreeTBDialog" @refreshData="getTreeData" />
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getTreeData, removeByIds, deleteByIds } from '@/api/modules/test/testtree/testTreeTBApi'
import Detail from './components/detail'
import { treeEach } from 'ecip-web/utils/tableUtil'
import { download } from 'ecip-web/utils'

export default {
  name: 'ModulesTestTesttreeTestTreeTBView',
  components: { Detail },
  mixins: [permissionMixin],
  data() {
    return {
      expand: false,
      importVisible: false,
      showSearch: false,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/test/testtree/testTreeTB/importExcel',
      tplUrl: 'api/v1/test/testtree/testTreeTB/excelTemplate',
      exportUrl: 'api/v1/test/testtree/testTreeTB/exportExcel',
      tableToolbar: {
        id: 'modules.test.testtree.testTreeTBView-toolbar',
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
      queryParams: {
        name: '',
        inDate: '',
        selectTest: '',
        userTest: {
          id: ''
        }
      },
      selections: [],
      treeData: [],
      columns: [
        { type: 'checkbox', width: 40, fixed: 'left', align: 'center' },
        { title: '名称', field: 'name', minWidth: 180, treeNode: true },
        { title: '加入日期', field: 'inDate', minWidth: 180, formatter: ['date', 'yyyy-MM-dd HH:mm:ss'] },
        { title: '验证类型', field: 'radioTest', minWidth: 180, slots: { default: 'radio_test' }},
        { title: 'checkbox测试', field: 'checkboxTest', minWidth: 180, slots: { default: 'checkbox_test' }},
        { title: '下拉选择测试', field: 'selectTest', minWidth: 180, slots: { default: 'select_test' }},
        { title: '用户选择测试', field: 'userTest', minWidth: 180, slots: { default: 'user_test' }},
        { title: '组织选择测试', field: 'deptTest', minWidth: 180, slots: { default: 'dept_test' }},
        { title: '文件上传测试', field: 'fileTest', minWidth: 180, slots: { default: 'file_test' }},
        { title: 'grid选择测试', field: 'gridTest', minWidth: 180, slots: { default: 'grid_test' }},
        { title: '区域选择测试', field: 'areaTest', minWidth: 180, slots: { default: 'area_test' }},
        { title: '省市区三级联动测试', field: 'cityTest', minWidth: 180 },
        { title: '排序编号', field: 'sort', minWidth: 180 },
        { title: '操作', width: 180, fixed: 'right', align: 'center', slots: { default: 'operation' }}
      ]
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
    this.getTreeData()
  },
  methods: {
    async getTreeData() {
      this.selections = []
      await this.$nextTick()
      const expands = this.$refs.vxe.getTreeExpandRecords().map(item => item.id)
      try {
        this.loading = true
        const { data } = await getTreeData(this.queryParams)
        this.treeData = data
        await this.$nextTick()
        const result = []
        treeEach(this.treeData, node => {
          if (expands.includes(node.id)) {
            result.push(node)
          }
        })
        this.$refs.vxe.setTreeExpand(result, true)
      } finally {
        this.loading = false
      }
    },
    // sortMethod({ column, property, order }) {
    //   this.page.field = property
    //   this.page.order = order
    //   this.getTreeData()
    // },
    dropDownCmd(command, row) {
      switch (command) {
        case 'addChild':
          this.add(row)
          break
        case 'removeById':
          this.removeByIds(row)
          break
      }
    },
    add(parent) {
      this.$refs.testTreeTBDialog.init('add', '', parent)
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.testTreeTBDialog.init('edit', record)
    },
    removeByIds(rowId) {
      const additionMsg = rowId ? '是否移除此节点及其子节点' : '是否移除选中节点'
      this.$confirm(`${additionMsg}?(逻辑删除,数据库数据不删除)`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        const removeIds = []
        if (rowId) {
          removeIds.push(rowId)
        } else {
          removeIds.push(...this.selections.map(row => row.treeId))
        }
        await removeByIds(removeIds)
        await this.getTreeData()
        this.$notify.success('移除成功')
      })
    },
    deleteByIds(rowId) {
      const additionMsg = rowId ? '是否删除此节点及其子节点' : '是否删除选中节点'
      this.$confirm(`${additionMsg}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async() => {
        const deleteIds = []
        if (rowId) {
          deleteIds.push(rowId)
        } else {
          deleteIds.push(...this.selections.map(row => row.treeId))
        }
        await deleteByIds(deleteIds)
        await this.getTreeData()
        this.$notify.success('删除成功')
      })
    },
    toggleTree() {
      this.expand = !this.expand
      this.$refs.vxe.setAllTreeExpansion(this.expand)
    },
    async exportExcel() {
      this.loading = true
      await download({ url: this.exportUrl, method: 'post', data: this.queryParams })
      this.loading = false
    },
    handleReset() {
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.getTreeData()
    }
  }
}
</script>

<style scoped>

</style>