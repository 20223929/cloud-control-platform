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
          label="性别(radio测试)"
          prop="sex"
        >
          <ec-radio-group v-model="queryParams.sex" dict-type="sex" />
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
      :expand-config="{lazy: true, loadMethod: loadChildren}"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button v-permission="['testMainTB:add']" type="primary" @click="add()">新增</el-button>
        <el-button v-permission="['testMainTB:edit']" type="success" :disabled="!onlyOne" @click="edit()">编辑</el-button>
        <el-button v-permission="['testMainTB:delete']" type="danger" :disabled="!oneMore" @click="deleteByIds()">删除</el-button>
        <el-button v-permission="['testMainTB:remove']" type="warning" :disabled="!oneMore" @click="removeByIds()">移除</el-button>
        <el-button v-permission="['testMainTB:import']" type="success" @click="importVisible = true">导入</el-button>
        <el-button v-permission="['testMainTB:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button @click="getData">刷新</el-button>
        <el-button @click="showSearch = !showSearch">搜索</el-button>
      </template>
      <template v-slot:office="{ row }">
        <ec-dept-select
          :value="row.office && row.office.id"
          readonly          
        />
      </template>
      <template v-slot:principle="{ row }">
        <ec-user-modal
          :value="row.principle && row.principle.id"
          readonly
        />
      </template>
      <template v-slot:office_area="{ row }">
        <ec-area-select
          :value="row.officeArea && row.officeArea.id"
          readonly
        />
      </template>
      <template v-slot:single_demo="{ row }">
        <ec-grid-modal
          :value="row.singleDemo && row.singleDemo.testId"
          title="单表示例选择"
          url="api/v1/test/testsingletbdemo1/testSingleTBDemo1/data"
          fieldKeys="testId|name|code|inDate"
          fieldLabels="主键|名称|编码|加入日期"
          searchKeys="name|code"
          searchLabels="名称|编码"
          value-field="testId"
                value-column="test_id"
          readonly
        />
      </template>
      <template v-slot:sex="{ row }">
        <ec-radio-group :value="row.sex" dict-type="sex" readonly/>
      </template>
      <template v-slot:vehicle_class="{ row }">
        <ec-select :value="row.vehicleClass" dict-type="vehicleClass" readonly />
      </template>
      <template v-slot:auth_type="{ row }">
        <ec-checkbox-group :value="row.authType" dict-type="authType" readonly/>
      </template>
      <template v-slot:file="{ row }">
        <ec-file-modal :value="row.file" readonly />
      </template>

      <template v-slot:detail_content="{ row }">
        <el-tabs>
          <el-tab-pane label="附表测试">
            <vxe-grid
              highlight-hover-row
              border
              resizable
              show-header-overflow
              show-overflow
              sync-resize
              auto-resize
              max-height="1000"
              class="vxe-table-element"
              :loading="innerListLoading"
              :columns="testAttachedTBColumns"
              :data="row.testAttachedTBList"
            >
              <template v-slot:station_type_0="{ row }">
                <ec-select :value="row.stationType" dict-type="deviceLocationType" readonly />
              </template>
            </vxe-grid>
          </el-tab-pane>
        </el-tabs>
      </template>

      <template v-slot:operation="{ row }">
        <el-button v-permission="['testMainTB:edit']" type="warning" size="mini" @click="edit(row)">编辑</el-button>
        <el-button v-permission="['testMainTB:delete']" type="danger" size="mini" @click="deleteByIds(row.mainId)">删除</el-button>
        <el-button v-permission="['testMainTB:remove']" type="warning" size="mini" @click="removeByIds(row.mainId)">移除</el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="主表示例, 全组件数据导入"
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

    <detail ref="testMainTBDialog" @refreshData="getData" />

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getData as findTestMainTBPage, removeByIds, deleteByIds } from '@/api/modules/test/testmaintb/testMainTBApi'
import { listByMaster as findTestAttachedTBList } from '@/api/modules/test/testmaintb/testAttachedTBApi'
import { download } from 'ecip-web/utils'
import Detail from './components/detail'

export default {
  name: 'ModulesTestTestmaintbTestMainTBView',
  components: { Detail },
  mixins: [permissionMixin],
  data() {
    return {
      loading: false,
      innerListLoading: false,
      importVisible: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/test/testmaintb/testMainTB/importExcel',
      tplUrl: 'api/v1/test/testmaintb/testMainTB/excelTemplate',
      exportUrl: 'api/v1/test/testmaintb/testMainTB/exportExcel',
      tableToolbar: {
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
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
        { type: 'checkbox', width: 40, align: 'center' },
        { type: 'expand', width: 60, align: 'center', slots: { content: 'detail_content' }},
        { title: '名称', field: 'name', minWidth: 180, sortable: true },
        { title: '归属部门', field: 'office', minWidth: 180, slots: { default: 'office' }},
        { title: '负责人', field: 'principle', minWidth: 180, slots: { default: 'principle' }},
        { title: '办公区域', field: 'officeArea', minWidth: 180, slots: { default: 'office_area' }},
        { title: '办公城市', field: 'officeCity', minWidth: 180, sortable: true },
        { title: '单表示例', field: 'singleDemo', minWidth: 180, slots: { default: 'single_demo' }},
        { title: '性别(radio测试)', field: 'sex', minWidth: 180, sortable: true, slots: { default: 'sex' }},
        { title: '车种(select测试)', field: 'vehicleClass', minWidth: 180, sortable: true, slots: { default: 'vehicle_class' }},
        { title: '认证方式(checkbox测试)', field: 'authType', minWidth: 180, sortable: true, slots: { default: 'auth_type' }},
        { title: '加入日期', field: 'inDate', minWidth: 180, sortable: true },
        { title: '排序号', field: 'sort', minWidth: 180, sortable: true },
        { title: '附件', field: 'file', minWidth: 180, sortable: true, slots: { default: 'file' }},
        { title: '操作', width: 240, align: 'center', slots: { default: 'operation' }}
      ],
      testAttachedTBColumns: [
        { title: '序号', type: 'seq', width: 60, align: 'center' },
        { title: '名称', field: 'name', minWidth: 180, sortable: true },
        { title: '站点类型', field: 'stationType', minWidth: 180, sortable: true, slots: { default: 'station_type_0' }},
        { title: '加入日期', field: 'inDate', minWidth: 180, sortable: true, formatter:  [ 'date', 'yyyy-MM-dd HH:mm:ss' ] },
        { title: '主表外键', field: 'testMain', minWidth: 180, sortable: true },
        { title: '位置', field: 'location', minWidth: 180, sortable: true },
      ],
      queryParams: {
        name: '',
        sex: ''
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
      const { data } = await findTestMainTBPage(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage }), this.queryParams)
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
    async loadChildren({ row }) {
      try {
        this.innerListLoading = true
        const result_0 = await findTestAttachedTBList({testMain: { mainId: row.mainId }})
        const currRecord = this.tableData.filter(item => item.mainId === row.mainId)[0]
        result_0.data.forEach(it => { it.testMain = currRecord})
        if (currRecord) {
          if (!currRecord.testAttachedTBList) {
            this.$set(currRecord, 'testAttachedTBList', [])
          }
          currRecord.testAttachedTBList = result_0.data
        }
      } finally {
        this.innerListLoading = false
      }
    },
    add() {
      this.$refs.testMainTBDialog.init('add', '')
    },
    edit(row) {
      this.$refs.testMainTBDialog.init('edit', row)
    },
    removeByIds(id) {
      this.$confirm('确定移除所选记录(逻辑删除,不删除数据库数据)?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        const removeIds = []
        if (id) {
          removeIds.push(id)
        } else {
          removeIds.push(...this.selections.map(row => row.mainId))
        }
        await removeByIds(removeIds)
        await this.getData()
        this.$notify.success('移除成功')
      })
    },
    deleteByIds(id) {
      this.$confirm('确定删除所选记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        const deleteIds = []
        if (id) {
          deleteIds.push(id)
        } else {
          deleteIds.push(...this.selections.map(row => row.mainId))
        }
        await deleteByIds(deleteIds)
        await this.getData()
        this.$notify.success('删除成功')
      })
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
    async exportExcel() {
      this.loading = true
      await download({ url: this.exportUrl, method: 'post', data: this.queryParams })
      this.loading = false
    }
  }
}
</script>
<style scoped>
  .form-text-content {
    margin-top: 15px;
  }
</style>