<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="operatorCode">
              <ec-select-tree
                ref="merchantTreeData"
                v-model="queryParams.searchId"
                url="api/v1/merchant/tbMerchant/data"
                value-field="id"
                attr-name="name"
                :disabled="searchDisable"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="ETC卡号"
              prop="etcCardId"
            >
              <el-input v-model="queryParams.etcCardId" type="text" placeholder="ETC卡号"
                        :disabled="queryParams.modelType === 1"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="交易流水号"
              prop="transactionId"
            >
              <el-input v-model="queryParams.transactionId" type="text" placeholder="交易流水号"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="车牌号码"
              prop="vehiclePlate"
            >
              <el-input v-model="queryParams.vehiclePlate" type="text" placeholder="车牌号码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item
              label="银行订单号"
              prop="orderId"
            >
              <el-input v-model="queryParams.orderId" type="text" placeholder="银行订单号"
                        :disabled="queryParams.modelType === 2"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="交易模式"
              prop="modelType"
            >
              <el-select v-model="queryParams.modelType" @change="selectOptions($event)" clearable placeholder="全部"
                         filterable>
                <el-option v-for="item in modelTypes" :label="item.label" :key="item.value"
                           :value="item.value"></el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="状态"
              prop="status"
            >
              <el-select v-model="queryParams.status" clearable placeholder="全部" filterable>
                <el-option v-for="item in statusOptions" :label="item.label" :key="item.value"
                           :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="交易时间"
              prop="transTime"
            >
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
        <el-button v-permission="['parkEexit:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['parkEexit:info']" type="primary" @click="info(row)" style="float: left">详情
        </el-button>
      </template>
    </vxe-grid>

    <detail ref="parkEexitDialog" @refreshData="getData"/>

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/querymanage/parkEexitApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesQuerymanageParkEexitView',
  components: {Detail},
  mixins: [permissionMixin],
  props: {
    param: {
      type: Object,
      default: () => {
      }
    }
  },
  watch: {
    param: {
      deep: true,
      handler(param) {
        this.queryParams.searchId = this.param.searchId
        this.queryParams.nodeLevel = this.param.nodeLevel
        this.queryParams.timeScope = this.param.timeScope
        if (this.param != null && this.param != undefined) {
          this.searchDisable = true
        }
        debugger
        this.queryData();
      },
      immediate: true
    }
  },
  data() {
    return {
      searchDisable: false,
      importVisible: false,
      showSearch: true,
      loading: false,
      exportUrl: 'api/v1/querymanage/parkEexit/exportExcel',
      tableToolbar: {
        id: 'modules.querymanage.parkEexitView-toolbar',
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
        {title: '车牌号码', field: 'vehiclePlate', tableField: 'vehicle_plate', minWidth: 180, align: 'center'},
        {title: '车牌颜色', field: 'vehicleColorName', tableField: 'vehicle_color', minWidth: 180, align: 'center'},
        {title: 'ETC卡号', field: 'etcCardId', tableField: 'etc_card_id', minWidth: 180, align: 'center'},
        {title: '交易流水号', field: 'transactionId', tableField: 'transaction_id', minWidth: 180, align: 'center'},
        {title: '银行订单号', field: 'orderId', minWidth: 180, align: 'center'},
        {title: '交易模式', field: 'modelTypeName', minWidth: 180, align: 'center'},
        {title: '拓展方', field: 'merchantGroupIdName', tableField: 'merchant_group_id', minWidth: 180, align: 'center'},
        {title: '运营方', field: 'merchantIdName', tableField: 'merchant_id', minWidth: 180, align: 'center'},
        {title: '场景', field: 'siteIdName', tableField: 'site_id', minWidth: 180, align: 'center'},
        {title: '入场时间', field: 'enTime', tableField: 'en_time', minWidth: 180, align: 'center'},
        {title: '出场时间', field: 'exTime', tableField: 'ex_time', minWidth: 180, align: 'center'},
        {title: '停放时长', field: 'parkHours', minWidth: 180, align: 'center'},
        {title: '交易金额(元)', field: 'merchantRealFee', tableField: 'merchant_real_fee', minWidth: 180, align: 'center'},
        {title: '交易时间', field: 'transTime', tableField: 'transTime', minWidth: 180, align: 'center', sortable: true},
        {title: '状态', field: 'statusName', minWidth: 180, align: 'center'},
        {title: '操作', width: 80, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        etcCardId: '',
        transactionId: '',
        searchId: '',
        orderId: '',
        modelType: '',
        status: '',
        transTime: '',
        vehiclePlate: '',
        nodeLevel: '',
        timeScope: []
      },
      modelTypes: [{
        value: 1,
        label: '联机'
      }, {
        value: 2,
        label: '脱机'
      }],
      statusOptions: [
        {
          value: 1,
          label: '待记账'
        },
        {
          value: 2,
          label: '待清分'
        },
        {
          value: 3,
          label: '清分校验异常'
        },
        {
          value: 4,
          label: '待结算'
        },
        {
          value: 5,
          label: '已结算'
        },
        {
          value: 6,
          label: '拓展平台校验异常'
        },
        {
          value: 7,
          label: '联机交易成功'
        }
      ]
    }
  },
  created() {
    if(this.queryParams.timeScope.length!=2){
      var timeScope = []
      var now = new Date();
      var y = now.getFullYear()
      var m = now.getMonth()
      var d = now.getDate()
      var begin = y + "-" + m + "-" + d
      begin = this.$moment(now).startOf('day').format('YYYY-MM-DD HH:mm:ss');
      timeScope.push(begin)
      timeScope.push(this.$moment(now).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.queryParams.timeScope = timeScope
    }
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
    info(row) {
      this.$refs.parkEexitDialog.init('info', row)
    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        this.queryParams.nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
      } else if (this.queryParams.searchId == null || this.queryParams.searchId == '') {
        this.queryParams.nodeLevel = ''
      }
      Object.assign(this.$data.page, this.$options.data().page)
      if (this.queryParams.timeScope.length > 0) {
        this.queryParams.timeScope[0] = this.$moment(this.queryParams.timeScope[0]).startOf('day').format('YYYY-MM-DD HH:mm:ss');
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss');
      }
      this.getData()
    },
    fixParam() {
      if (this.param != null && this.param != undefined) {
        this.queryParams.searchId = this.param.searchId
        this.queryParams.nodeLevel = this.param.nodeLevel
        this.queryParams.timeScope = this.param.timeScope
        this.searchDisable = true
      }
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
      this.fixParam()
      this.getData()
    },
    selectOptions(val) {
      if (val) {
        this.statusOptions = [];
        if (val === 1) {
          this.queryParams.etcCardId = ''
          this.statusOptions.push(
            {
              value: 7,
              label: '联机交易成功'
            }
          )
        }
        if (val === 2) {
          this.queryParams.orderId = ''
          this.statusOptions.push(
            {
              value: 1,
              label: '待记账'
            },
            {
              value: 2,
              label: '待清分'
            },
            {
              value: 3,
              label: '清分校验异常'
            },
            {
              value: 4,
              label: '待结算'
            },
            {
              value: 5,
              label: '已结算'
            },
            {
              value: 6,
              label: '拓展平台校验异常'
            }
          )
        }
      } else {
        this.statusOptions = [];
        this.statusOptions.push(
          {
            value: 1,
            label: '待记账'
          },
          {
            value: 2,
            label: '待清分'
          },
          {
            value: 3,
            label: '清分校验异常'
          },
          {
            value: 4,
            label: '待结算'
          },
          {
            value: 5,
            label: '已结算'
          },
          {
            value: 6,
            label: '拓展平台校验异常'
          },
          {
            value: 7,
            label: '联机交易成功'
          }
        )
      }
    },

  }
}
</script>

<style scoped>
</style>
