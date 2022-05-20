<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="merchantId">
              <ec-select-tree
                ref="merchantTreeData"
                v-model="queryParams.merchantId"
                url="api/v1/merchant/tbMerchant/data"
                value-field="id"
                attr-name="name"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="核对状态"
              prop="checkStatus"
            >
              <el-select v-model="queryParams.checkStatus" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >

                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="服务类型"
              prop="serviceType"
            >
              <ec-select v-model="queryParams.serviceType" dict-type="service_type"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="交易时间"
              prop="transTime"
            >
              <el-date-picker
                v-model="queryParams.transTime"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="queryParams.transTime"
                align="right" value-format="yyyy-MM-dd HH:mm:ss">
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
      <template v-slot:toolbar_buttons><el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['etcServiceSplit:add']" type="primary" disabled="oneMore" @click="confirm()">批量结算
        </el-button>
        <el-button v-permission="['etcServiceSplit:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['etcServiceSplit:edit']" type="warning" @click="confirm(row)" style="float: left">确认
        </el-button>
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/settlementmanage/etcservicesplit/shanxi/etcServiceSplitApi'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesSettlementmanageEtcServiceSplitView',
  mixins: [permissionMixin],
  data() {
    return {
      options: [
        {
          value: '0',
          label: '待结算'
        },
        {
          value: '1',
          label: '已结算'
        }
      ],
      showSearch: true,
      loading: false,
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      exportUrl: 'api/v1/settlementmanage/etcServiceSplit/exportExcel',
      tableToolbar: {
        id: 'modules.settlementmanage.etcServiceSplitView-toolbar',
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
        {title: '交易日期', field: 'tradeDay', tableField: 'trade_day', minWidth: 180, align: 'center', sortable: true},
        {title: '总服务费(元)', field: 'totalServiceFee', tableField: 'total_service_fee', minWidth: 180, align: 'center'},
        {title: '渠道方', field: 'channelName', tableField: 'channel_name', minWidth: 180, align: 'center'},
        {
          title: '渠道方服务费(元)',
          field: 'channelServiceFee',
          tableField: 'channel_service_fee',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '渠道方服务费率',
          field: 'channelServiceRate',
          tableField: 'channel_service_rate',
          minWidth: 180,
          align: 'center'
        },
        {title: '平台', field: 'platform', tableField: 'platform', minWidth: 180, align: 'center'},
        {
          title: '平台运营成本(元)',
          field: 'platformOperationCost',
          tableField: 'platform_operation_cost',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '平台运营服务费率',
          field: 'platformServiceRate',
          tableField: 'platform_service_rate',
          minWidth: 180,
          align: 'center'
        },
        {title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center'},
        {
          title: '拓展方服务费(元)',
          field: 'merchantGroupServiceFee',
          tableField: 'merchant_group_service_fee',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '拓拓展方服务费率',
          field: 'merchantGroupServiceRate',
          tableField: 'merchant_group_service_rate',
          minWidth: 180,
          align: 'center'
        },
        {title: '核对状态', field: 'checkStatus', tableField: 'check_status', minWidth: 180, align: 'center'},
        {title: '操作时间', field: 'operationTime', tableField: 'operation_time', minWidth: 180, align: 'center'},
        {title: '操作', width: 80, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        transTime: [],
        merchantId: '',
        nodeLevel: '',
        serviceType: '',
        checkStatus: ''
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
    var now = new Date();
    var before = 24 * 60 * 60 * 1000
    timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
    timeScope.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
    this.queryParams.transTime = timeScope
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
    confirm(row) {
      const record = row || this.selections[0]
      this.$refs.etcServiceSplitDialog.init('edit', record)
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
      if (this.queryParams.transTime.length > 0) {
        this.queryParams.transTime[0] = this.$moment(this.queryParams.transTime[0]).startOf('day').format('YYYY-MM-DD HH:mm:ss');
        this.queryParams.transTime[1] = this.$moment(this.queryParams.transTime[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss');
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      var timeScope = []
      var now = new Date();
      var before = 24 * 60 * 60 * 1000
      timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
      timeScope.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.queryParams.transTime = timeScope
      this.getData()
    }
  }
}
</script>

<style scoped>

</style>
