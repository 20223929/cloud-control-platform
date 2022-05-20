<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="120px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="merchantId">
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
            <el-form-item
              label="车道号"
              prop="equipmentId"
            >
              <el-input v-model="queryParams.equipmentId" type="text" placeholder="车道号"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="天线状态"
              prop="antennaState"
            >
              <el-select v-model="queryParams.antennaState" placeholder="请选择" clearable>
                <el-option
                  v-for="item in antennaState"
                  :value="item.value"
                  :label="item.label"
                  :key="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="车道软件状态"
              prop="siteSoftState"
            >
              <el-select v-model="queryParams.siteSoftState" placeholder="请选择" clearable>
                <el-option
                  v-for="item in siteSoftState"
                  :value="item.value"
                  :label="item.label"
                  :key="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item
              label="车道软件版本号"
              prop="siteSoftVersion"
            >
              <el-input v-model="queryParams.siteSoftVersion" type="text" placeholder="车道软件版本号"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="天线品牌"
              prop="antennaBrand"
            >
              <el-input v-model="queryParams.antennaBrand" type="text" placeholder="天线品牌"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="ETC盒子品牌"
              prop="etcBoxBrand"
            >
              <el-input v-model="queryParams.etcBoxBrand" type="text" placeholder="ETC盒子品牌"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="车道监控状态"
              prop="laneStatus"
            >
              <el-select v-model="queryParams.laneStatus" placeholder="请选择" clearable>
                <el-option
                  v-for="item in laneStatus"
                  :value="item.value"
                  :label="item.label"
                  :key="item.value"
                >
                </el-option>
              </el-select>
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
      :row-style="rowStyle"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['heartbeatMonitor:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/monitor/heartbeatMonitorApi'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesMonitorHeartbeatMonitorView',
  mixins: [permissionMixin],
  data() {
    return {
      antennaState: [
        {
          value: '0',
          label: '异常'
        },
        {
          value: '1',
          label: '正常'
        }
      ],
      siteSoftState: [
        {
          value: '0',
          label: '下班'
        },
        {
          value: '1',
          label: '上班'
        }
      ],
      laneStatus: [
        {
          value: 1,
          label: '正常'
        },
        {
          value: 2,
          label: '异常'
        }
      ],
      importVisible: false,
      showSearch: true,
      loading: false,
      exportUrl: 'api/v1/monitor/heartbeatMonitor/exportExcel',
      tableToolbar: {
        id: 'modules.monitor.heartbeatMonitorView-toolbar',
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
        {title: '拓展方', field: 'merchantGroupName', tableField: 'merchantGroupName', minWidth: 180, align: 'center'},
        {title: '运营方', field: 'merchantName', tableField: 'merchantName', minWidth: 180, align: 'center'},
        {title: '场景', field: 'siteName', tableField: 'siteName', minWidth: 180, align: 'center'},
        {title: '车道号', field: 'equipmentId', tableField: 'equipmentId', minWidth: 180, align: 'center'},
        {title: '服务类型', field: 'serviceTypeName', tableField: 'serviceTypeName', minWidth: 180, align: 'center'},
        {title: '车道软件版本号', field: 'siteSoftVersion', tableField: 'siteSoftVersion', minWidth: 180, align: 'center'},
        {title: '车道软件状态', field: 'siteSoftState', tableField: 'siteSoftState', minWidth: 180, align: 'center'},
        {title: '天线状态', field: 'antennaState', tableField: 'antennaState', minWidth: 180, align: 'center'},
        {title: '天线品牌', field: 'antennaBrand', tableField: 'antennaBrand', minWidth: 180, align: 'center'},
        {title: '天线型号', field: 'antennaModel', tableField: 'antennaModel', minWidth: 180, align: 'center'},
        {title: 'ETC盒子品牌', field: 'etcBoxBrand', tableField: 'etcBoxBrand', minWidth: 180, align: 'center'},
        {title: 'ETC盒子型号', field: 'etcBoxModel', tableField: 'etcBoxModel', minWidth: 180, align: 'center'},
        {
          title: '大额交易配置',
          field: 'maxTransFeeConfigName',
          tableField: 'maxTransFeeConfigName',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '全量ETC卡黑名单版本号',
          field: 'cardBlackAllVersion',
          tableField: 'cardBlackAllVersion',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '增量ETC卡黑名单版本号',
          field: 'cardBlackAddVersion',
          tableField: 'cardBlackAddVersion',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '全量OBU黑名单版本号',
          field: 'obuBlackAllVersion',
          tableField: 'obuBlackAllVersion',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '增量OBU黑名单版本号',
          field: 'obuBlackAddVersion',
          tableField: 'obuBlackAddVersion',
          minWidth: 180,
          align: 'center'
        },
        {title: '心跳流水时间', field: 'createTime', tableField: 'createTime', minWidth: 180, align: 'center'}
      ],
      queryParams: {
        searchId: '',
        equipmentId: '',
        siteSoftVersion: '',
        siteSoftState: '',
        antennaState: '',
        antennaBrand: '',
        etcBoxBrand: '',
        nodeLevel: '',
        laneStatus: ''
      }
    }
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
      Object.assign(this.$data.page, this.$options.data().page)
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        this.queryParams.nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
      } else {
        this.queryParams.nodeLevel = ''
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.getData()
    },
    rowStyle(row) {
      if (row.row.laneStatus === 2) {
        return 'color:red!important;';
      }
    }
  }
}
</script>

<style scoped>

</style>
