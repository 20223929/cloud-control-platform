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
          <el-col :span="6">
            <el-form-item label="服务类型" prop="serviceType">
              <ec-select v-model="queryParams.serviceType" :ec-data="[
       { value: '2', label: '停车场' },
       { value: '3', label: '加油站' },
       { value: '4', label: '服务区' },
       { value: '5', label: '市政拓展' },]"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车牌号码" prop="vehiclePlate">
              <el-input type="text" v-model="queryParams.vehiclePlate"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="卡号" prop="etcCardId">
              <el-input type="text" v-model="queryParams.etcCardId"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="采集日期" prop="updateTime">
              <el-date-picker
                v-model="queryParams.timeScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                :key="queryParams.timeScope"
                align="right" value-format="yyyy-MM-dd HH:mm:ss" :disabled="searchDisable">
              </el-date-picker>
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
import {getData} from '@/api/modules/querymanage/tbEtcVehicleEexitApi'

export default {
  name: 'ModulesVehicleSearchView',
  components: {},
  mixins: [permissionMixin],
  data() {
    return {
      searchDisable: false,
      importVisible: false,
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      tplUrl: 'api/v1/querymanage/tbEtcVehicleEexit/excelTemplate',
      exportUrl: 'api/v1/querymanage/tbEtcVehicleEexit/exportExcel',
      tableToolbar: {
        id: 'modules.querymanage.tbEtcVehicleEexitView-toolbar',
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
        {title: '序号', type: 'seq', width: 80, fixed: 'left', align: 'center'},
        {
          title: '服务类型',
          field: 'serviceTypeStr',
          tableField: 'trans_date',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '车牌号码',
          field: 'vehiclePlate',
          tableField: 'trans_date',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '车牌颜色',
          field: 'vehicleColorStr',
          tableField: 'trans_date',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: 'ETC卡号',
          field: 'etcCardId',
          tableField: 'trans_date',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '计费金额(元)',
          field: 'merchantRealFeeStr',
          tableField: 'trans_date',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '拓展方',
          field: 'merchantGroupIdStr',
          tableField: 'merchant_group_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '运营方',
          field: 'merchantIdStr',
          tableField: 'merchant_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '场景', field: 'siteIdStr', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '入场时间',
          field: 'enTime',
          tableField: 'merchant_group_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '出场时间',
          field: 'exTime',
          tableField: 'merchant_group_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {
          title: '采集时间',
          field: 'updateTime',
          tableField: 'merchant_group_id',
          minWidth: 180,
          sortable: true,
          align: 'center'
        },
      ],
      queryParams: {
        timeScope: [],
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
    var timeScope = []
    var now = new Date()
    timeScope.push(this.$moment(now).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
    timeScope.push(this.$moment(now).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
    this.queryParams.timeScope = timeScope
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
      if (this.queryParams.timeScope.length > 0) {
        this.queryParams.timeScope[0] = this.$moment(this.queryParams.timeScope[0]).startOf('day').format('YYYY-MM-DD HH:mm:ss');
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss');
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      if (this.queryParams.timeScope.length <= 0) {
        var timeScope = []
        var now = new Date()
        timeScope.push(this.$moment(now).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
        timeScope.push(this.$moment(now).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
        this.queryParams.timeScope = timeScope
      }
      this.getData()
    },
  }
}
</script>

<style scoped>

</style>
