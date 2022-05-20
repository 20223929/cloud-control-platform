<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="130px">
        <el-row>
          <el-col :span="7">
            <el-form-item label="代理商名称" prop="name" style="line-height: 50px;">
              <el-input v-model="queryParams.agentName" type="text" style="line-height: 50px;" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item
              label="证件类型"
              prop="certificateType"
              style="line-height: 50px;"
            >
              <ec-select dictType="certificateType" v-model="queryParams.certificateType" style="line-height: 50px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item
              label="统一社会信用代码/组织机构代码证号"
              prop="creditCode"
            >
              <el-input v-model="queryParams.creditCode" style="line-height: 50px;" type="text" clearable/>
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
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" v-permission="['agent:add']" @click="add()">新建代理商</el-button>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['agent:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['agent:detail']" type="primary" @click="detail(row)" style="float: left">
          详情
        </el-button>
        <el-button v-permission="['agent:edit']" type="primary" @click="edit(row)" style="float: left">
          编辑
        </el-button>
      </template>
    </vxe-grid>
    <detail ref="agentDetailDialog" @refreshData="getData"></detail>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/agent/agentApi'
import detail from './components/detail'
import {download} from 'ecip-web/utils'

export default {
  name: 'AgentView',
  mixins: [permissionMixin],
  components: {detail},
  props: {
    param: {
      type: Object,
      default: () => {
      }
    }
  },
  data() {
    return {
      showSearch: true,
      loading: false,
      exportUrl: 'api/v1/agent/exportExcel',
      tableToolbar: {
        id: 'modules.agent.agentView-toolbar',
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
      tableData: [],
      columns: [
        {title: '序号', type: 'seq', width: 80, fixed: 'left', align: 'center'},
        {title: '代理商编号', field: 'agentNo', tableField: 'agent_no', minWidth: 180, align: 'center'},
        {title: '代理商名称', field: 'agentName', tableField: 'agent_name', minWidth: 180, align: 'center'},
        {title: '证件类型', field: 'certificateTypeDesc', tableField: 'certificate_type', minWidth: 180, align: 'center'},
        {title: '纳税人识别号', field: 'taxpayerCode', tableField: 'taxpayer_code', minWidth: 180, align: 'center'},
        {title: '统一社会信用代码/组织机构代码证号', field: 'creditCode', tableField: 'credit_code', minWidth: 180, align: 'center'},
        {title: '开户行', field: 'bank', tableField: 'bank', minWidth: 180, align: 'center'},
        {title: '开户行地址', field: 'bankAddr', tableField: 'bank_addr', minWidth: 180, align: 'center'},
        {title: '开户行账号', field: 'bankAccount', tableField: 'bank_account', minWidth: 180, align: 'center'},
        {title: '创建时间', field: 'createTime', tableField: 'createtime', minWidth: 180, align: 'center',formatter: ['date', 'yyyy-MM-dd HH:mm:ss'] },
        {title: '更新时间', field: 'modifyTime', tableField: 'modifytime', minWidth: 180, align: 'center',formatter: ['date', 'yyyy-MM-dd HH:mm:ss'] },
        {title: '操作', width: 160, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        agentName: '',
        certificateType: '',
        creditCode: ''
      }
    }
  },
  created() {
    this.getData()
  },
  methods: {
    async getData() {
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
      this.$refs.agentDetailDialog.init('add')
    },
    detail(row){
      this.$refs.agentDetailDialog.init('view', row.sysId)
    },
    edit(row) {
      this.$refs.agentDetailDialog.init('edit', row.sysId)
    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
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
