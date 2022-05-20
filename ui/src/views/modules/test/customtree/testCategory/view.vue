<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-form-item
          label="类型名"
          prop="name"
        >
          <el-input v-model="queryParams.name" type="text" placeholder="类型名" />
        </el-form-item>
        <el-form-item class="searchItem" :label-width="'50px'">
          <el-button type="primary" size="mini" @click="queryData">查询</el-button>
          <el-button type="default" size="mini" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <vxe-virtual-tree
      ref="vxe"
      row-key
      row-id="id"
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
        <el-button v-permission="['testCategory:add']" type="primary" @click="add()">新增</el-button>
        <el-button v-permission="['testCategory:edit']" type="warning" :disabled="!onlyOne" @click="edit()">编辑</el-button>
        <el-button v-permission="['testCategory:delete']" type="danger" :disabled="!oneMore" @click="deleteByIds()">删除</el-button>
        <el-button v-permission="['testCategory:remove']" type="warning" :disabled="!oneMore" @click="removeByIds()">移除</el-button>
        <el-button v-permission="['testCategory:import']" type="success" @click="importVisible = true">导入</el-button>
        <el-button v-permission="['testCategory:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button @click="getData">刷新</el-button>
        <el-button @click="showSearch = !showSearch">搜索</el-button>
      </template>


      <template v-slot:operation="{ row }">
        <el-button v-permission="['testCategory:edit']" size="mini" type="warning" @click="edit(row)">编辑</el-button>
        <el-button v-permission="['testCategory:delete']" size="mini" type="danger" @click="deleteByIds(row.id)">删除</el-button>
        <el-dropdown
          v-permission="['testCategory:remove', 'testCategory:add']"
          trigger="click"
          style="padding-left:10px;padding-right:10px;"
          @command="command => dropDownCmd(command, row)"
        >
          <el-button type="text" icon="el-icon-arrow-down" />
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-permission="['testCategory:add']" command="addChild">添加下级</el-dropdown-item>
            <el-dropdown-item v-permission="['testCategory:remove']" command="removeById">移除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>
    </vxe-virtual-tree>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="商品类型数据导入"
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
    <detail ref="testCategoryDialog" @refreshData="getTreeData" />
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getTreeData, removeByIds, deleteByIds } from '@/api/modules/test/customtree/testCategoryApi'
import Detail from './components/detail'
import { treeEach } from 'ecip-web/utils/tableUtil'
import { download } from 'ecip-web/utils'

export default {
  name: 'ModulesTestCustomtreeTestCategoryView',
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
      importUrl: 'api/v1/test/customtree/testCategory/importExcel',
      tplUrl: 'api/v1/test/customtree/testCategory/excelTemplate',
      exportUrl: 'api/v1/test/customtree/testCategory/exportExcel',
      tableToolbar: {
        id: 'modules.test.customtree.testCategoryView-toolbar',
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
        name: ''
      },
      selections: [],
      treeData: [],
      columns: [
        { type: 'checkbox', width: 40, fixed: 'left', align: 'center' },
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
      this.$refs.testCategoryDialog.init('add', '', parent)
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.testCategoryDialog.init('edit', record)
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
          removeIds.push(...this.selections.map(row => row.id))
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
          deleteIds.push(...this.selections.map(row => row.id))
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