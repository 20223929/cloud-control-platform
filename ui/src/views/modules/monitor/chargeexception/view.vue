<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="交易日期"
              prop="transTime"
            >
              <el-date-picker
                v-model="queryParams.timeScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                align="right" value-format="yyyy-MM-dd" format="yyyy-MM-dd">
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
        <el-button v-permission="['chargeExcepion:edit']" type="primary" :disabled="!oneMore" @click="confirm()">批量确认
        </el-button>
        <el-button v-permission="['chargeExcepion:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['chargeExcepion:edit']" type="warning" @click="confirm(row)" style="float: left">确认
        </el-button>
      </template>
    </vxe-grid>

    <detail ref="chargeExcepionDialog" @refreshData="getData"/>

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/monitor/chargeExcepionApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesMonitorChargeExcepionView',
  components: {Detail},
  mixins: [permissionMixin],
  data() {
    return {
      showSearch: true,
      loading: false,
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      exportUrl: 'api/v1/monitor/chargeExcepion/exportExcel',
      tableToolbar: {
        id: 'modules.monitor.chargeExcepionView-toolbar',
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
        {type: 'checkbox', width: 40, fixed: 'left', align: 'center'},
        {title: '序号', type: 'seq', width: 80, fixed: 'left', align: 'center'},
        {title: '交易日期', field: 'transDate', tableField: 'trans_date', minWidth: 180, align: 'center', sortable: true},
        {title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center'},
        {title: '运营方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, align: 'center'},
        {title: '场景', field: 'siteName', tableField: 'site_name', minWidth: 180, align: 'center'},
        {title: '设备编号', field: 'equipmentId', tableField: 'equipment_id', minWidth: 180, align: 'center'},
        {title: '前端脱机流水总数', field: 'etcCount', tableField: 'etc_count', minWidth: 180, align: 'center'},
        {title: '后台脱机流水总数', field: 'realEtcCount', tableField: 'real_etc_count', minWidth: 180, align: 'center'},
        {title: '前端脱机实收总金额(元)', field: 'etcAmount', tableField: 'etc_amount', minWidth: 180, align: 'center'},
        {title: '后台脱机实收总金额(元)', field: 'realEtcAmount', tableField: 'real_etc_amount', minWidth: 180, align: 'center'},
        {title: '前端联机流水总数', field: 'onlineCount', tableField: 'online_count', minWidth: 180, align: 'center'},
        {title: '后台联机流水总数', field: 'realOnlineCount', tableField: 'real_online_count', minWidth: 180, align: 'center'},
        {title: '前端联机实收总金额(元)', field: 'onlineAmount', tableField: 'online_amount', minWidth: 180, align: 'center'},
        {
          title: '后台联机实收总金额(元)',
          field: 'realOnlineAmount',
          tableField: 'real_online_amount',
          minWidth: 180,
          align: 'center'
        },
        {title: '前端流水总数', field: 'totalEtcCount', tableField: 'total_etc_count', minWidth: 180, align: 'center'},
        {title: '后台流水总数', field: 'totalOnlineCount', tableField: 'total_online_count', minWidth: 180, align: 'center'},
        {title: '前端实收总金额(元)', field: 'totalEtcAmount', tableField: 'total_etc_amount', minWidth: 180, align: 'center'},
        {
          title: '后台实收总金额(元)',
          field: 'totalOnlineAmount',
          tableField: 'total_online_amount',
          minWidth: 180,
          align: 'center'
        },
        {title: '状态', field: 'status', tableField: 'status', minWidth: 180, align: 'center'},
        {title: '操作', width: 80, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        timeScope: []
      }
    }
  },
  mounted() {
    this.$watch(this.$refs.vxe.getCheckboxRecords, (newValue, oldValue) => {
      this.selections = newValue
      this.oneMore = this.selections.length > 0
    })
  },
  created() {
    this.defaultScope()
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
      this.tableData.forEach(function (item, index, array) {
        item.etcAmount = (item.etcAmount / 100).toFixed(2)
        item.realEtcAmount = (item.realEtcAmount / 100).toFixed(2)
        item.onlineAmount = (item.onlineAmount / 100).toFixed(2)
        item.realOnlineAmount = (item.realOnlineAmount / 100).toFixed(2)
        item.totalEtcAmount = (item.totalEtcAmount / 100).toFixed(2)
        item.totalOnlineAmount = (item.totalOnlineAmount / 100).toFixed(2)
      })
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
    confirm(row) {
      const record = (this.selections.length > 0 ? this.selections : row)
      console.log(record)
      this.$refs.chargeExcepionDialog.init('', record)
    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      if (this.queryParams.timeScope.length > 0) {
        this.queryParams.timeScope[0] = this.$moment(this.queryParams.timeScope[0]).startOf('day').format('YYYY-MM-DD');
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD');
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.defaultScope()
      this.getData()
    },
    defaultScope() {
      var timeScope = []
      var now = new Date();
      var before = 29 * 24 * 60 * 60 * 1000
      timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD'))
      timeScope.push(this.$moment(now).endOf('day').format('YYYY-MM-DD'))
      this.queryParams.timeScope = timeScope
    }
  }
}
</script>

<style scoped>

</style>
