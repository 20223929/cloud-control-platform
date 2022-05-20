<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="queryParams.searchId">
              <ec-select-tree
                ref="merchantTreeData"
                v-model="queryParams.searchId"
                url="api/v1/merchant/tbMerchant/data"
                value-field="id"
                attr-name="name"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务类型" prop="serviceType">
              <ec-select v-model="queryParams.serviceType" :ec-data="[
       { value: '2', label: '停车场' },
       { value: '3', label: '加油站' },
       { value: '4', label: '服务区' },
       { value: '5', label: '市政拓展' },]"/>
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
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {download} from 'ecip-web/utils'
import {getData} from '@/api/modules/monitor/TbLaneHeartBeatAndEexitMonitorApi'

export default {
  name: 'ModulesTbLaneHeartBeatAndEexitMonitorView',
  components: {},
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      tplUrl: 'api/v1/monitor/tbLaneHeartBeatAndEexitMonitor/excelTemplate',
      exportUrl: 'api/v1/monitor/tbLaneHeartBeatAndEexitMonitor/exportExcel',
      tableToolbar: {
        id: 'modules.monitor.TbLaneHeartBeatAndEexitMonitorView-toolbar',
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
      selections: [],
      tableData: [],
      columns: [
        {
          title: '拓展方',
          field: 'merchantGroupIdStr',
          tableField: 'merchant_group_id_str',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '运营方',
          field: 'merchantIdStr',
          tableField: 'merchant_id_str',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '服务类型',
          field: 'serviceTypeStr',
          tableField: 'service_type_str',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '接入场景数量',
          field: 'allSiteCount',
          tableField: 'all_site_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '接入前端软件数量',
          field: 'allSoftCount',
          tableField: 'all_soft_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '停用数量',
          field: 'disableSoftCount',
          tableField: 'disable_soft_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '启用数量',
          field: 'enableSoftCount',
          tableField: 'enable_soft_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '心跳正常',
          field: 'heartBeatNormal',
          tableField: 'heart_beat_normal',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '心跳丢失超12小时',
          field: 'heartBeatLoseOver12h',
          tableField: 'heart_beat_lose_over12h',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '心跳丢失超24小时',
          field: 'heartBeatLoseOver24h',
          tableField: 'heart_beat_lose_over24h',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '心跳丢失超7天',
          field: 'heartBeatLoseOver7d',
          tableField: 'heart_beat_lose_over7d',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '未上传省中心流水数',
          field: 'unUploadEexitCount',
          tableField: 'un_upload_eexit_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '未上传省中心流水金额',
          field: 'unUploadEexitAmountStr',
          tableField: 'un_upload_eexit_amount_str',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '已上传省中心流水数',
          field: 'uploadEexitCount',
          tableField: 'upload_eexit_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '已上传省中心流水金额',
          field: 'uploadEexitAmountStr',
          tableField: 'upload_eexit_amount_str',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '省中心接收流水数',
          field: 'provinceRecvEexitCount',
          tableField: 'province_recv_eexit_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '省中心接收流水金额(元)',
          field: 'provinceRecvEexitAmountStr',
          tableField: 'province_recv_eexit_amount_str',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '本系统与省中心接收流水数量顺差',
          field: 'diffCount',
          tableField: 'diff_count',
          minWidth: 180,
          sortable: false,
          align: 'center'
        }
      ],
      queryParams: {
        transDate: '',
        searchId: '',
        nodeLevel: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        confirmState: '0'
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
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        let nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
        switch (nodeLevel) {
          case 1:
            this.queryParams.merchantGroupId = this.queryParams.searchId;
            break;
          case 2:
            this.queryParams.merchantId = this.queryParams.searchId;
            break;
          case 3:
            this.queryParams.siteId = this.queryParams.searchId;
            break;
        }
      } else {
        this.queryParams.nodeLevel = ''
        this.queryParams.siteId = ''
        this.queryParams.merchantId = ''
        this.queryParams.merchantGroupId = ''
      }
      Object.assign(this.$data.page, this.$options.data().page)
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.getData()
    },
  }
}
</script>

<style scoped>

</style>
