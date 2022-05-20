<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="120px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="operatorCode">
              <ec-select-tree
                ref="merchantTreeData"
                v-model="queryParams.operatorCode"
                url="api/v1/merchant/tbMerchant/data"
                value-field="id"
                attr-name="name"
                :disabled="searchDisable"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="退费交易流水号"
              prop="refundTransId"
            >
              <el-input v-model="queryParams.refundTransId" type="text" placeholder="退费交易流水号" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="原交易流水号"
              prop="transactionId"
            >
              <el-input v-model="queryParams.transactionId" type="text" placeholder="原交易流水号" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="车牌号码"
              prop="vehiclePlate"
            >
              <el-input v-model="queryParams.vehiclePlate" type="text" placeholder="车牌号码" clearable/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item
              label="服务类型"
              prop="serviceType"
            >
              <el-select v-model="queryParams.serviceType" placeholder="全部" :disabled="searchDisable" clearable>
                <el-option
                  v-for="item in serviceTypes"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="投诉单号"
              prop="refundApplyNo"
            >
              <el-input v-model="queryParams.refundApplyNo" type="text" placeholder="投诉单号" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="状态"
              prop="refundApplyNo"
            >
              <el-select v-model="queryParams.refundState" placeholder="全部" :disabled="searchDisable" clearable>
                <el-option
                  v-for="item in refundState"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="退费日期"
              prop="timeScope"
            >
              <!--          <ec-date-range v-model="queryParams.timeScope" begin-key='beginRefundTime' end-key='endRefundTime' format="yyyy-MM-dd" />-->
              <el-date-picker
                v-model="queryParams.timeScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="queryParams.timeScope"
                align="right" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd" :disabled="searchDisable" clearable>
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
        <el-button v-permission="['tbOnlineRefundEexit:add']" type="primary" @click="add()">新增</el-button>
        <el-button v-permission="['tbOnlineRefundEexit:edit']" type="warning" :disabled="!onlyOne" @click="edit()">编辑
        </el-button>
        <el-button v-permission="['tbOnlineRefundEexit:delete']" type="danger" :disabled="!oneMore"
                   @click="deleteByIds()">删除
        </el-button>
        <el-button v-permission="['tbOnlineRefundEexit:import']" type="success" @click="importVisible = true">导入
        </el-button>
        <el-button v-permission="['tbOnlineRefundEexit:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['tbOnlineRefundEexit:info']" type="success" @click="info(row)" style="float: left">
          投诉信息
        </el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="联机退费流水表数据导入"
      :visible="importVisible"
      @onSuccess="getData"
      @close="importVisible = false"
    >
      <template v-slot:extra_params="{ extraParams }">
        <el-form-item label="是否生成新主键">
          <ec-radio-group v-model="extraParams.isNewPk"
                          :ec-data="[ {value: false, label: '否'}, {value: true, label: '是' } ]"/>
        </el-form-item>
        <el-form-item label="导入策略">
          <ec-radio-group v-model="extraParams.strategy"
                          :ec-data="[ {value: 'ignore', label: '忽略'}, {value: 'update', label: '更新' } ]"/>
        </el-form-item>
      </template>
    </ec-import>

    <detail ref="tbOnlineRefundEexitDialog" @refreshData="getData"/>

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, deleteByIds} from '@/api/modules/querymanage/tbOnlineRefundEexitApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesQuerymanageTbOnlineRefundEexitView',
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
      handler() {
        this.queryParams.operatorCode = this.param.operatorCode
        this.queryParams.nodeLevel = this.param.nodeLevel
        this.queryParams.timeScope = this.param.timeScope
        this.queryParams.serviceType = this.param.serviceType
        this.queryParams.refundState = this.param.refundState
        if (this.param != null && this.param != undefined) {
          this.searchDisable = true
        }
        this.getData();
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
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/querymanage/tbOnlineRefundEexit/importExcel',
      tplUrl: 'api/v1/querymanage/tbOnlineRefundEexit/excelTemplate',
      exportUrl: 'api/v1/querymanage/tbOnlineRefundEexit/exportExcel',
      tableToolbar: {
        id: 'modules.querymanage.tbOnlineRefundEexitView-toolbar',
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
        order: 'desc',
        field: 'applyTime'
      },
      selections: [],
      operationColumn: {title: '操作', width: 105, fixed: 'right', align: 'center', slots: {default: 'operation'}},
      tableData: [],
      columns: [
        {title: '序号', type: 'seq', width: 80, fixed: 'left', align: 'center'},
        {
          title: '拓展方',
          field: 'merchantGroupIdName',
          tableField: 'merchant_group_id_name',
          minWidth: 180,
          sortable: false, align: 'center'
        },
        {title: '运营方', field: 'merchantIdName', tableField: 'merchant_id_name', minWidth: 180, sortable: false, align: 'center'},
        {title: '场景', field: 'siteIdName', tableField: 'site_id_name', minWidth: 180, sortable: false, align: 'center'},
        {title: '车牌号码', field: 'vehicleId', tableField: 'vehicle_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '原交易流水号', field: 'transactionId', tableField: 'transaction_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '退费交易流水号', field: 'refundTransId', tableField: 'refund_trans_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '投诉单号', field: 'refundApplyNo', tableField: 'refund_apply_no', minWidth: 180, sortable: false, align: 'center'},
        {title: '退费类型', field: 'refundTypeName', tableField: 'refund_type_name', minWidth: 180, sortable: false, align: 'center'},
        {title: '服务类型', field: 'serviceTypeName', tableField: 'service_type_name', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '原支付交易金额(元)',
          field: 'merchantRealFee',
          tableField: 'merchant_real_fee',
          minWidth: 180,
          sortable: false,
          format: '#.##', align: 'center'
        },
        {
          title: '优惠金额(元)',
          field: 'merchantDiscountFee',
          tableField: 'merchant_discount_fee',
          minWidth: 180,
          sortable: false, align: 'center'
        },
        {title: '实际退费金额(元)', field: 'refundFee', tableField: 'refund_fee', minWidth: 180, sortable: false, align: 'center'},
        {title: '退费时间', field: 'applyTime', tableField: 'applyTime', minWidth: 180, sortable: true, align: 'center'},
        {title: '交易时间', field: 'transTime', tableField: 'transTime', minWidth: 180, sortable: true, align: 'center'},
        { title: '状态', field: 'refundStateName', tableField: 'refund_state_name', minWidth: 180, sortable: false , align: 'center'},
      ],
      queryParams: {
        refundState: '',
        timeScope: [],
        refundTransId: '',
        transactionId: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        vehiclePlate: '',
        serviceType: '',
        refundApplyNo: '',
        operatorCode: '',
        nodeLevel: ''
      },
      serviceTypes: [{
        value: 2,
        label: '停车场'
      }, {
        value: 3,
        label: '加油站'
      }, {
        value: 4,
        label: '服务区'
      }],
      refundState: [
        {
          value: 1,
          label: '退费中'
        },
        {
          value: 2,
          label: '退费成功'
        },
        {
          value: 3,
          label: '退费失败'
        }
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
    if(this.queryParams.timeScope.length!=2){
      var timeScope = []
      timeScope.push(this.$moment(new Date()).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
      timeScope.push(this.$moment(new Date()).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.queryParams.timeScope = timeScope
    }
    this.tableColumns = this.columns
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
      if(data.dataMap.provinceId !== '51'){
        this.columns.push(this.operationColumn)
      }
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
      this.$refs.tbOnlineRefundEexitDialog.init('add')
    },
    info(row) {
      const record = row || this.selections[0]
      this.$refs.tbOnlineRefundEexitDialog.init('info', record)
    },
    deleteByIds(rowId) {
      const additionMsg = rowId ? '此条记录' : '选中记录'
      this.$confirm(`确定删除${additionMsg}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async () => {
        const deleteIds = []
        if (rowId) {
          deleteIds.push(rowId)
        } else {
          deleteIds.push(...this.selections.map(row => row.id))
        }
        await deleteByIds(deleteIds)
        await this.getData()
        this.$notify.success('删除成功')
      })
    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        this.queryParams.nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
      }
      Object.assign(this.$data.page, this.$options.data().page)
      if (this.queryParams.timeScope.length > 0) {
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss');
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      var timeScope = []
      timeScope.push(this.$moment(new Date()).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
      timeScope.push(this.$moment(new Date()).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.queryParams.timeScope = timeScope
      if (this.param != null && this.param != undefined) {
        this.queryParams.operatorCode = this.param.operatorCode
        this.queryParams.nodeLevel = this.param.nodeLevel
        this.queryParams.timeScope = this.param.timeScope
        this.queryParams.serviceType = this.param.serviceType
        this.queryParams.refundState = this.param.refundState
        this.searchDisable = true
      }
      this.getData()
    }
  }
}
</script>

<style scoped>

</style>
