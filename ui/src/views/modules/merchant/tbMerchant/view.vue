<template>
  <div class="app-container">
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
      :checkbox-config="{labelField: '名称', highlight: true}"
    >
      <template v-slot:toolbar_buttons>
        <el-button :disabled="treeData.length === 0" @click="toggleTree">展开/收起全部</el-button>
        <el-button v-permission="['tbMerchant:add']" type="primary" @click="addOneLevel()">新增一级商户</el-button>
        <el-button v-permission="['tbMerchant:edit']" type="warning" :disabled="!onlyOne" @click="edit()">编辑</el-button>
        <el-button v-permission="['tbMerchant:download']" type="warning" @click="downloadCer()">下载平台证书</el-button>
        <el-button @click="getTreeData">刷新</el-button>
      </template>

      <template v-slot:bottom>
        <div class="vxe-bottom-container">
          <el-checkbox v-model="checkStrictly">父子级联</el-checkbox>
        </div>
      </template>

      <template v-slot:header="{ row }">
        <div>名称</div>
        <input v-model="filterName" type="type" placeholder="输入名称查找" @keyup="searchMerchantByName">
      </template>

      <template v-slot:operation="{ row }">
        <el-button v-if="row.nodeLevel !== 3" v-permission="['tbMerchant:add']" size="mini" type="primary" @click="add(row)">添加下级</el-button>
        <el-button v-permission="['tbMerchant:edit']" size="mini" type="warning" @click="edit(row)">编辑</el-button>
      </template>
    </vxe-virtual-tree>

    <merchant-group-detail ref="merchantGroupDialog" @refreshData="getTreeData" />
    <merchant-detail ref="merchantDialog" @refreshData="getTreeData"/>
    <site-detail ref="siteDialog" @refreshData="getTreeData"/>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getTreeData } from '@/api/modules/merchant/tbMerchantApi'
import MerchantGroupDetail from './components/merchantGroupDetail'
import MerchantDetail from './components/merchantDetail'
import SiteDetail from './components/siteDetail'
import { treeEach } from 'ecip-web/utils/tableUtil'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesMerchantTbMerchantView',
  components: {MerchantGroupDetail,MerchantDetail,SiteDetail},
  mixins: [permissionMixin],
  data() {
    return {
      filterName: '',
      expand: false,
      importVisible: false,
      checkStrictly: true,
      showSearch: false,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/merchant/tbMerchant/importExcel',
      tplUrl: 'api/v1/merchant/tbMerchant/excelTemplate',
      exportUrl: 'api/v1/merchant/tbMerchant/exportExcel',
      downloadUrl: 'api/v1/merchant/tbMerchant/downloadCert',
      tableToolbar: {
        id: 'modules.merchant.tbMerchantView-toolbar',
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
      selections: [],
      originData: [],
      treeData: [],
      columns: [
        { type: 'checkbox', width: 40, fixed: 'left', align: 'center'},
        { title: '名称', field: 'name', minWidth: 180, treeNode: true,slots: {header: 'header'}},
        { title: '编号', field: 'id', minWidth: 180 },
        { title: '操作', width: 250, fixed: 'right', align: 'center', slots: { default: 'operation' }}
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
        const { data } = await getTreeData()
        this.treeData = data
        this.originData = data
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
    addOneLevel() {
      this.$refs.merchantGroupDialog.init('add','');
    },
    add(parent) {
      if(parent.nodeLevel == 1)this.$refs.merchantDialog.init('add',parent)
      else this.$refs.siteDialog.init('add',parent)
    },
    edit(row) {
      const record = row || this.selections[0]
      if(record.nodeLevel == 1)this.$refs.merchantGroupDialog.init('edit',record)
      else if(record.nodeLevel == 2) this.$refs.merchantDialog.init('edit',record)
      else this.$refs.siteDialog.init('edit',record)
    },
    async downloadCer() {
      this.loading = true
      try{
        await download({url: this.downloadUrl, method: 'post', data: {}})
      }catch (e){
       this.$message.error(e)
      }finally {
        this.loading = false
      }
    },
    toggleTree() {
      this.expand = !this.expand
      this.$refs.vxe.setAllTreeExpansion(this.expand)
    },
    handleSearch () {
      let filterName = this.filterName.toString().trim()
      if (filterName) {
        let options = { children: 'children' }
        let searchProps = ['name']
        this.treeData = XEUtils.searchTree(this.originData, item => searchProps.some(key => item[key].toString().indexOf(filterName) > -1), options)
        // 搜索之后默认展开所有子节点
        this.$nextTick(() => {
          this.$refs.vxe.setAllTreeExpand(true)
        })
      }else{
        this.treeData = this.originData
      }
    },
    searchMerchantByName: XEUtils.debounce(function () {
      this.handleSearch()
    }, 500, { leading: false, trailing: true })
  }
}
</script>

<style scoped>

</style>
