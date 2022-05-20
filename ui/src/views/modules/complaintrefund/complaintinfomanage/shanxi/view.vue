<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-position="right" label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="searchId">
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
              label="投诉单号"
              prop="complaintId"
            >
              <el-input v-model="queryParams.complaintId" type="text" placeholder="投诉单号"/>
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
              prop="vehicleNumber"
            >
              <el-input v-model="queryParams.vehicleNumber" type="text" placeholder="车牌号码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="投诉通道" prop="complaintChannel">
              <el-select v-model="queryParams.complaintChannel" clearable placeholder="请选择">
                <el-option
                  v-for="item in channel"
                  :value="item.value"
                  :label="item.label"
                  :key="item.value"
                >

                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="退费形式" prop="refundType">
              <el-select v-model="queryParams.refundType" clearable placeholder="请选择">
                <el-option
                  v-for="item in refundType"
                  :value="item.value"
                  :label="item.label"
                  :key="item.value"
                >

                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="退费状态" prop="refundStatus">
              <el-select v-model="queryParams.refundStatus" clearable placeholder="请选择">
                <el-option
                  v-for="item in refundStatus"
                  :value="item.value"
                  :label="item.label"
                  :key="item.value"
                >

                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="投诉单状态" prop="refundApplyHandlerResult">
              <el-select v-model="queryParams.refundApplyHandlerResult" clearable placeholder="请选择">
                <el-option
                  v-for="item in refundApplyHandlerResult"
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
          <el-col :span="12">
            <el-form-item
              label="投诉时间"
              prop="timeScope"
            >
              <el-date-picker
                v-model="queryParams.timeScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="queryParams.timeScope"
                align="right" format="yyyy-MM-dd" value-format="yyyy-MM-dd HH:mm:ss">
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
      :show-footer="showFoot"
      max-height="1000"
      class="vxe-table-element"
      :loading="loading"
      :start-index="(page.currentPage - 1) * page.pageSize"
      :sort-config="{ trigger: 'cell' }"
      :pager-config="page"
      :columns="columns"
      :data="tableData"
      :toolbar="tableToolbar"
      :footer-method="footerMethod"
      :footer-span-method="footerColspanMethod"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['complaintRefund:add']" type="primary" @click="add()">创建</el-button>
        <el-button v-permission="['complaintRefund:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
        <!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['complaintRefund:handle']" v-show="row.refundApplyHandlerResult === 0" type="success"
                   @click="edit(row.sysId)" style="float: left;margin-left: 0px;">修改
        </el-button>
        <el-button v-permission="['complaintRefund:handle']" v-show="row.refundApplyHandlerResult === 0" type="success"
                   @click="del(row.sysId)" style="float: left;">删除
        </el-button>
        <el-button v-permission="['complaintRefund:handle']" v-show="row.refundApplyHandlerResult === 1" type="success"
                   @click="audit(row.sysId)" style="float: left;margin-left: 0px;">审核
        </el-button>
        <el-button v-permission="['complaintRefund:detail']" v-show="row.refundApplyHandlerResult > 1" type="success"
                   @click="detail(row.sysId)" style="float: left;margin-left: 0px;">详情
        </el-button>
        <el-button v-permission="['complaintRefund:handle']"
                   v-show="row.complaintChannel !== 1 && row.complaintChannel !== 4 && row.refundApplyHandlerResult === 2 && row.refundStatus === 1" type="success"
                   @click="searchRefundResult(row.sysId)" style="float: left;">更新退费结果
        </el-button>
      </template>
    </vxe-grid>

    <detail ref="tbComplaintRefundDialog" @refreshData="getData"/>

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, deleteDraft, searchRefundResult} from '@/api/modules/complaintrefund/shanxi/tbComplaintRefundApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesComplaintrefundTbComplaintRefundView',
  components: {Detail},
  mixins: [permissionMixin],
  data() {
    return {
      channel: [
        {
          value: 1,
          label: '后台自动退费'
        },
        {
          value: 2,
          label: '业务员录入'
        },
        {
          value: 3,
          label: '小程序申请'
        },
        {
          value: 4,
          label: '在线实时退费'
        }
      ],
      refundType: [
        {
          value: 1,
          label: '银联线上退费'
        },
        {
          value: 2,
          label: '商户线下退费'
        }
      ],
      refundStatus: [
        {
          value: 0,
          label: '待退费'
        },
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
        },
        {
          value: 4,
          label: '不退费'
        }
      ],
      refundApplyHandlerResult: [
        {
          value: 0,
          label: '草稿'
        },
        {
          value: 1,
          label: '待审核'
        },
        {
          value: 2,
          label: '已审核'
        }
      ],
      showSearch: true,
      loading: false,
      exportUrl: 'api/v1/complaintrefund/tbComplaintRefund/exportExcel',
      tableToolbar: {
        id: 'modules.complaintrefund.tbComplaintRefundView-toolbar',
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
        {
          title: '投诉时间',
          field: 'complaintTime',
          tableField: 'complaint_time',
          minWidth: 180,
          align: 'center',
          sortable: true
        },
        {
          title: '退费处理时间',
          field: 'applyTime',
          tableField: 'apply_time',
          minWidth: 180,
          align: 'center',
          sortable: false
        },
        {title: '投诉单号', field: 'complaintId', tableField: 'complaint_id', minWidth: 180, align: 'center'},
        {
          title: '投诉通道',
          field: 'complaintChannelDesc',
          tableField: 'complaint_channel_desc',
          minWidth: 180,
          align: 'center'
        },
        {title: '车牌号码', field: 'vehicleNumber', tableField: 'vehicle_number', minWidth: 180, align: 'center'},
        {title: '车牌颜色', field: 'vehicleColorDesc', tableField: 'vehicle_color_desc', minWidth: 180, align: 'center'},
        {title: '服务类型', field: 'serviceTypeDesc', tableField: 'service_type_desc', minWidth: 180, align: 'center'},
        {title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center'},
        {title: '服务方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, align: 'center'},
        {title: '场景', field: 'siteName', tableField: 'site_name', minWidth: 180, align: 'center'},
        {title: '投诉单状态', field: 'refundFlagDesc', tableField: 'refund_flag_desc', minWidth: 180, align: 'center'},
        {title: '退费形式', field: 'refundTypeDesc', tableField: 'refund_type_desc', minWidth: 180, align: 'center'},
        {title: '退费结果', field: 'refundStatusDesc', tableField: 'refund_status_desc', minWidth: 180, align: 'center'},
        {title: '交易流水号', field: 'transactionId', tableField: 'transaction_id', minWidth: 180, align: 'center'},
        {title: '客户实付金额(元)', field: 'actualPayFee', tableField: 'actual_pay_fee', minWidth: 180, align: 'center'},
        {title: '退费金额(元)', field: 'refundFee', tableField: 'refund_fee', minWidth: 180, align: 'center'},
        {title: '操作', width: 240, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        complaintId: '',
        vehicleNumber: '',
        transactionId: '',
        timeScope: [],
        searchId: '',
        nodeLevel: '',
        refundFlag: '',
        refundType: '',
        refundStatus: '',
        complaintChannel: ''
      },
      footSum: '',
      showFoot: ''
    }
  },
  async created() {
    this.defaultScope()
    await this.getData()
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
      this.tableData.forEach((item, index, array) => {
        item.actualPayFee = item.actualPayFee ? (item.actualPayFee / 100).toFixed(2) : item.actualPayFee
        item.refundFee = item.refundFee ? (item.refundFee / 100).toFixed(2) : item.refundFee
      })
      this.showFoot = (this.tableData.length > 0)
      this.footSum = data.dataMap
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
      this.$refs.tbComplaintRefundDialog.init('add')
    },
    detail(row) {
      this.$refs.tbComplaintRefundDialog.init('view', row)
    },
    audit(row) {
      this.$refs.tbComplaintRefundDialog.init('audit', row)
    },
    edit(row) {
      this.$refs.tbComplaintRefundDialog.init('edit', row)
    },
    async del(row) {
      this.$confirm(`确定删除这条草稿记录吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async () => {
        try {
          this.loading = true
          const resuslt = await deleteDraft(row)
          this.$notify.success(resuslt.message)
        } finally {
          this.getData()
          this.loading = false
        }
      })
    },
    async searchRefundResult(row) {
      try {
        this.loading = true
        const result = await searchRefundResult(row)
        this.$notify.success(result.message)
      } finally {
        this.getData()
        this.loading = false
      }
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
      if (this.queryParams.timeScope.length > 0) {
        this.queryParams.timeScope[0] = this.$moment(this.queryParams.timeScope[0]).startOf('day').format('YYYY-MM-DD HH:mm:ss');
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss');
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.defaultScope()
      this.getData()
    },
    footerMethod({columns, data}) {
      if (this.footSum.sum == undefined) return []
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 1) {
            return '小计'
          }
          if (column.property && column.property.indexOf("Fee") > -1) {
            return XEUtils.sum(data, column.property).toFixed(2)
          } else {
            return []
          }
        }),
        columns.map((column, columnIndex) => {
          if (columnIndex === 1) {
            return '合计'
          }
          if (column.property && column.property.indexOf("Fee") > -1) {
            return (this.footSum.sum[column.property] / 100).toFixed(2)
          } else {
            return []
          }
        })
      ]
      return footData
    },
    footerColspanMethod({_columnIndex}) {
      if (_columnIndex == 1) return {rowspan: 1, colspan: 14}
      else if (_columnIndex > 1 && _columnIndex <= 14) return {rowspan: 0, colspan: 0}
    },
    defaultScope() {
      var timeScope = []
      var now = new Date();
      var before = 29 * 24 * 60 * 60 * 1000
      timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
      timeScope.push(this.$moment(now).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.queryParams.timeScope = timeScope
    },
  }
}
</script>

<style scoped>

</style>
