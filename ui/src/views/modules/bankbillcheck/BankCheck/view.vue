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
            <el-form-item label="核对状态" prop="checkStatus">
              <el-select v-model="queryParams.checkStatus" clearable>
                <el-option
                  v-for="item in checkStatus"
                  :value="item.value"
                  :label="item.label"
                  :key="queryParams.checkStatus"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="服务类型" prop="checkStatus" clearable>
              <el-select v-model="queryParams.serviceType">
                <el-option
                  v-for="item in serviceType"
                  :value="item.value"
                  :label="item.label"
                  :key="queryParams.serviceType"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="交易日期"
              prop="transTime"
            >
              <el-date-picker
                ref="timeScopeChoose"
                v-model="transTime"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="transTime"
                align="right" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd">
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
      :show-footer="showFooter"
      max-height="1000"
      class="vxe-table-element"
      :loading="loading"
      :start-index="(page.currentPage - 1) * page.pageSize"
      :sort-config="{ trigger: 'cell' }"
      :pager-config="page"
      :columns="columns"
      :data="tableData"
      :toolbar="tableToolbar"
      :footer-span-method="footerRowspanMethod"
      :footer-method="footerMethod"
      :checkbox-config="{ checkMethod: checCheckboxkMethod}"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
      :row-style="rowStyle"
    >
      <template v-slot:toolbar_buttons><el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['bankCheck:batchConfirm']" type="primary" @click="batchConfirm" :disabled="!oneMore">
          批量确认
        </el-button>
        <el-button v-permission="['bankCheck:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button v-permission="['bankCheck:exportDetail']" type="primary" @click="exportDetailExcel">导出流水明细
        </el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['bankCheck:detail']" type="primary" @click="detail(row)" style="float: left">详情
        </el-button>
        <el-button v-permission="['bankCheck:exceptionDetail']"
                   v-if="!row.totalCheckCount || row.differCount !== 0 || row.differAmount !== 0" type="danger"
                   @click="exceptionDetail(row)" style="float: left">异常明细
        </el-button>
        <el-button v-permission="['bankCheck:exceptionRegister']"
                   v-if="(!row.totalCheckCount || row.differCount !== 0 || row.differAmount !== 0) && row.checkStatus !== 3 && row.checkStatus !== 4"
                   type="warning" @click="confirm(row,1)" style="float: left">异常登记
        </el-button>
        <el-button v-permission="['bankCheck:confirm']"
                   v-if="row.checkStatus !== 4 && (row.differCount !== 0 || row.differAmount !== 0)" type="success"
                   @click="confirm(row,'')" style="float: left">确认
        </el-button>
      </template>
    </vxe-grid>

    <exception-detail ref="exceptionDetail"/>
    <BankCheckDetail ref="detail"/>
    <confirm ref="confirm" @refreshData="getData"/>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import ExceptionDetail from './components/exceptionDetail'
import BankCheckDetail from './components/detail'
import Confirm from './components/confirm'
import {getData} from '@/api/modules/bankbillcheck/bankCheck'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesBanCheckView',
  mixins: [permissionMixin],
  components: {ExceptionDetail, BankCheckDetail, Confirm},
  data() {
    var that = this
    return {
      checkStatus: [
        {
          value: 1,
          label: '待核对'
        },
        {
          value: 2,
          label: '已核对'
        },
        {
          value: 3,
          label: '异常登记'
        },
        {
          value: 4,
          label: '已确认'
        }
      ],
      serviceType: [
        {
          value: 2,
          label: '停车场'
        },
        {
          value: 3,
          label: '加油站'
        },
        {
          value: 4,
          label: '服务区'
        },
        {
          value: 5,
          label: '市政拓展'
        },
        {
          value: 6,
          label: '充电桩'
        }
      ],
      showFooter: '',
      showSearch: true,
      loading: false,
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      exportUrl: 'api/v1/bankbillcheck/bankCheck/exportExcel',
      exportDetailUrl: 'api/v1/bankbillcheck/bankCheck/exportDetail',
      tableToolbar: {
        id: 'BanCheckView-toolbar',
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
        {title: '序号', type: 'seq', minWidth: 80, align: 'center'},
        {title: '交易日期', field: 'tradeDay', tableField: 'tradeDay', minWidth: 180, align: 'center', sortable: true},
        {title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center'},
        {title: '运营方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, align: 'center'},
        {title: '场景', field: 'siteName', tableField: 'site_id', minWidth: 180, align: 'center'},
        {title: '银行流水总数', field: 'totalCheckCount', tableField: 'total_check_count', minWidth: 180, align: 'center'},
        {
          title: '银行实扣总金额(元)',
          field: 'totalCheckAmount',
          tableField: 'total_check_amount',
          minWidth: 180,
          align: 'center'
        },
        {title: '本系统流水总数', field: 'totalCount', tableField: 'total_count', minWidth: 180, align: 'center'},
        {title: '本系统实收总金额(元)', field: 'totalAmount', tableField: 'total_amount', minWidth: 180, align: 'center'},
        {title: '流水总数差额', field: 'differCount', tableField: 'differ_count', minWidth: 180, align: 'center'},
        {title: '流水实收总金额差额(元)', field: 'differAmount', tableField: 'differ_amount', minWidth: 180, align: 'center'},
        {title: '对账模式', field: 'checkModeName', tableField: 'check_mode_name', minWidth: 180, align: 'center'},
        {title: '核对状态', field: 'checkStatusName', tableField: 'check_status_name', minWidth: 180, align: 'center'},
        {
          title: '确认后流水总数',
          field: 'totalConfirmCount',
          tableField: 'total_confirm_count',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '确认后实收总金额(元)',
          field: 'totalConfirmAmount',
          tableField: 'total_confirm_amount',
          minWidth: 180,
          align: 'center'
        },
        {title: '备注', field: 'remark', tableField: 'remark', minWidth: 180, align: 'center'},
        {title: '核对时间', field: 'checkTime', tableField: 'check_time', minWidth: 180, align: 'center'},
        {title: '操作', width: 330, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      transTime: '',
      queryParams: {
        timeScope: [],
        merchantId: '',
        nodeLevel: '',
        checkStatus: 1,
        serviceType: ''
      },
      footerSum: ''
    }
  },
  created() {
    this.defaultScope()
    this.getData()
  },
  mounted() {
    this.$watch(this.$refs.vxe.getCheckboxRecords, (newValue, oldValue) => {
      this.selections = newValue
      this.oneMore = this.selections.length > 0
    })
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
      this.tableData.forEach((item, array, index) => {
        if (item.totalCheckAmount) item.totalCheckAmount = (item.totalCheckAmount / 100).toFixed(2)
        if (item.totalAmount) item.totalAmount = (item.totalAmount / 100).toFixed(2)
        if (item.differAmount) item.differAmount = (item.differAmount / 100).toFixed(2)
        if (item.totalConfirmAmount) item.totalConfirmAmount = (item.totalConfirmAmount / 100).toFixed(2)
      })
      this.footerSum = data.dataMap
      this.showFooter = (this.tableData.length > 0);
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
      this.paramSetting()
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    async exportDetailExcel() {
      this.loading = true
      this.paramSetting()
      await download({url: this.exportDetailUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      this.paramSetting()
      this.getData()
    },
    paramSetting() {
      this.queryParams.timeScope = []
      if (this.transTime != null && this.transTime.length > 0) {
        this.queryParams.timeScope = this.transTime
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf("day").format("YYYY-MM-DD HH:mm:ss")
      }
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        this.queryParams.nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
      } else {
        this.queryParams.nodeLevel = ''
      }
    },
    exceptionDetail(row) {
      this.$refs.exceptionDetail.init(row)
    },
    detail(row) {
      this.$refs.detail.init(row)
    },
    confirm(row, operation) {
      this.$refs.confirm.init(row, operation)
    },
    batchConfirm() {
      this.$refs.confirm.init(this.selections, '')
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams, {value: this.value})
      // this.transTime = this.startDate = ''
      this.defaultScope()
      this.getData()
    },
    checCheckboxkMethod({row}) {
      return row.checkStatus !== 4
    },
    footerMethod({columns, data}) {
      if (this.footerSum.sum == undefined) return []
      const footData = [
        columns.map((column, columnIndex) => {
          if (columnIndex === 1) {
            return '小计'
          }
          if (column.property && (column.property.indexOf("Amount") > -1 || column.property.indexOf("Count") > -1)) {
            if (['totalConfirmCount', 'totalConfirmAmount'].includes(column.property)) {
              return []
            }
            return column.property.indexOf("Amount") > -1 ? (XEUtils.sum(data, column.property) === 0 ? [] : (XEUtils.sum(data, column.property)).toFixed(2)) : (XEUtils.sum(data, column.property) === 0 ? [] : XEUtils.sum(data, column.property))
          } else {
            return []
          }
        }),
        columns.map((column, columnIndex) => {
          if (columnIndex === 1) {
            return '合计'
          }
          if (['merchantGroupName', 'merchantName', 'siteName', 'tradeDay'].includes(column.property)) {
            return []
          }
          if (column.property && column.property.indexOf("Amount") > -1 && !(['totalConfirmCount', 'totalConfirmAmount'].includes(column.property))) {
            return this.footerSum.sum[column.property] ? (this.footerSum.sum[column.property] / 100).toFixed(2) : []
          }
          return this.footerSum.sum[column.property]
        })
      ]
      return footData
    },
    footerRowspanMethod({_rowIndex, _columnIndex}) {
      if (_columnIndex === 1) {
        return {rowspan: 1, colspan: 5}
      } else if (_columnIndex > 1 && _columnIndex <= 5) {
        return {rowspan: 0, colspan: 0}
      }
    },
    defaultScope() {
      var timeScope = []
      var now = new Date();
      var before = 24 * 60 * 60 * 1000
      timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
      timeScope.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.transTime = timeScope
      this.queryParams.timeScope = timeScope
    },
    rowStyle(row) {
      if (row.row.differCount != 0) {
        return 'color:red!important;';
      }
    }
  }
}
</script>

<style scoped>

</style>
