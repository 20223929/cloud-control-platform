<template>
  <div class="app-container">
    <el-row v-loading="loading" :gutter="18">
      <el-col :span="6">
        <el-card shadow="never" class="tree_table_card">
          <div class="search_input_group">
            <el-input v-model="filterText" style="width: 90%" clearable placeholder="搜索" />
            <span @click="add"><i class="el-icon-circle-plus" /></span>
          </div>
          <div v-if="choosedNode && choosedNode.name" class="tree_node_choosed">
            <span>已选: {{ choosedNode && choosedNode.name }}</span>
            <span @click="cancelChooseNode"><i class="el-icon-close" /></span>
          </div>
          <el-tree
            ref="elTree"
            node-key="treeId"
            default-expand-all
            highlight-current
            empty-text="暂无数据"
            :data="treeData"
            :props="{ label: 'name', children: 'children' }"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            @node-click="nodeClick"
          >
            <span slot-scope="{ node, data }" class="custom-tree-node">
              <span>{{ data.name }}</span>
              <div class="optBtn">
                <el-button v-permission="['testLeftTree:add']" type="text" @click="add(data)"><i class="el-icon-plus" /></el-button>
                <el-button v-permission="['testLeftTree:edit']" type="text" @click="edit(data)"><i class="el-icon-edit-outline" /></el-button>
                <el-button v-permission="['testLeftTree:delete']" type="text" @click="deleteById(data.treeId)"><i class="el-icon-delete" /></el-button>
              </div>
            </span>
          </el-tree>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card shadow="never" class="tree_table_card">
          <test-right-tb ref="testRightTbView" />
        </el-card>
      </el-col>
    </el-row>
    
    <detail ref="testLeftTreeDialog" @refreshData="getTreeData" />
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getTreeData, removeById, deleteById } from '@/api/modules/test/testtreetable/testLeftTreeApi'
import TestRightTb from './testRightTb/view'
import Detail from './components/detail'

export default {
  name: 'ModulesTestTesttreetableTestLeftTreeView',
  components: { TestRightTb, Detail },
  mixins: [permissionMixin],
  data() {
    return {
      loading: false,
      filterText: '',
      choosedNode: {},
      treeData: []
    }
  },
  watch: {
    filterText(val) {
      this.$refs.elTree.filter(val)
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.getTreeData()
      this.initTable()
    },
    async getTreeData() {
      try {
        this.loading = true
        const { data } = await getTreeData()
        this.treeData = data
      } finally {
        this.loading = false
      }
    },
    async initTable(nodeId) {
      await this.$nextTick()
      this.$refs.testRightTbView.init(nodeId)
    },
    add(parent) {
      this.$refs.testLeftTreeDialog.init('add', '', parent)
    },
    edit(row) {
      this.$refs.testLeftTreeDialog.init('edit', row)
    },
    removeById(rowId) {
      const additionMsg = '是否移除当前节点及其子节点'
      this.$confirm(`${additionMsg}?(逻辑删除,数据库数据不删除)`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        await removeById(rowId)
        await this.getTreeData()
        this.$notify.success('移除成功')
      })
    },
    deleteById(rowId) {
      const additionMsg = '是否删除此节点及其子节点'
      this.$confirm(`${additionMsg}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async() => {
        await deleteById(rowId)
        await this.getTreeData()
        this.$notify.success('删除成功')
      })
    },
    nodeClick(node, Node) {
      this.choosedNode = node
      this.initTable(node.treeId)
    },
    cancelChooseNode() {
      this.$refs.elTree.setCurrentKey(null)
      this.choosedNode = {}
      this.initTable()
    },
    filterNode(value, data, node) {
      return this.$utils.filterNode(value, data, node, 'name')
    }
  }
}
</script>

<style lang="scss" scoped>
  .tree_table_card {
    max-height: 830px;
    overflow: auto;
  }
  .custom-tree-node {
    width: 100%;
    .optBtn {
      float: right;
      i {
        font-size: 16px;
      }
    }
    .el-icon-plus {
      color: #46A6FF
    }
    .el-icon-edit-outline {
      color: #FFC833
    }
    .el-icon-delete {
      color: #FF6D6D
    }
  }
  .tree_node_choosed {
    background-color: #fee9ea;
    border-color: #fdd3d5;
    display: inline-block;
    height: 32px;
    padding: 0 10px;
    line-height: 30px;
    font-size: 12px;
    color: #F5222D;
    border-width: 1px;
    border-style: solid;
    border-radius: 4px;
    box-sizing: border-box;
    white-space: nowrap;
    margin-bottom: 10px;
    span {
      cursor: pointer;
      margin-left: 5px;
      i {
        font-size: 12px;
      }
    }
  }
  .search_input_group {
    margin-bottom: 15px;
    span {
      cursor: pointer;
      float: right;
      font-size: 24px;
      color: #46A6FF
    }
  }
</style>