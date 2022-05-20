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
            <el-form-item
              label="服务类型"
              prop="serviceType"
            >
              <ec-select v-model="queryParams.serviceType" :ec-data="[
       { value: '2', label: '停车场' },
       { value: '3', label: '加油站' },
       { value: '4', label: '服务区' },
       { value: '5', label: '市政拓展' },
     ]"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="确认状态"
              prop="confirmState">
              <ec-select v-model="queryParams.confirmState" :ec-data="[
       { value: '1', label: '待确认' },
       { value: '2', label: '已确认' },
     ]"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="对账交易日期"
              prop="transDate"
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
      :checkbox-config="{ checkMethod: checCheckboxkMethod2}"
      :span-method="spanMethod"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['tbCenterSettlement:edit']" type="primary" @click="batchConfirm()"
                   :disabled="!oneMore">批量确认
        </el-button>
        <el-button v-permission="['tbCenterSettlement:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['tbCenterSettlement:edit']"
                   v-if="row.merchantGroupIdName != '小计'&& row.merchantGroupIdName != '合计'" type="primary"
                   @click="details(row)" style="float: left">详情
        </el-button>
        <el-button v-permission="['tbCenterSettlement:edit']" v-if="row.confirmState===1" type="success"
                   @click="oneConfirm(row)" style="float: left">确认
        </el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="联网中心结算数据导入"
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

    <detail ref="tbCenterSettlementDialog" @refreshData="getData"/>
    <check-detail ref="checkDetailDialog" @refreshData="getData"/>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, myConfirm, batchConfirm} from '@/api/modules/settlement/tbCenterSettlementApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'
import {deleteByIds} from "@/api/modules/settlement/tbBankSettlementApi";
import CheckDetail from "@/views/modules/settlement/tbCenterSettlement/components/checkDetail";

export default {
  name: 'ModulesSettlementTbCenterSettlementView',
  components: {Detail, CheckDetail},
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/settlement/tbCenterSettlement/importExcel',
      tplUrl: 'api/v1/settlement/tbCenterSettlement/excelTemplate',
      exportUrl: 'api/v1/settlement/tbCenterSettlement/exportExcel',
      tableToolbar: {
        id: 'modules.settlement.tbCenterSettlementView-toolbar',
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
        {
          title: '对账日',
          field: 'settlementDay',
          tableField: 'settlement_day',
          minWidth: 180,
          align: 'center',
          sortable: true
        },
        {title: '服务类型', field: 'serviceTypeStr', tableField: 'service_type', minWidth: 180, align: 'center'},
        {title: '拓展方', field: 'merchantGroupIdName', tableField: 'merchant_group_id', minWidth: 180, align: 'center'},
        {title: '运营方', field: 'merchantIdName', tableField: 'merchant_id', minWidth: 180, align: 'center'},
        {title: '场景', field: 'siteIdName', tableField: 'site_id', minWidth: 180, align: 'center'},
        {
          title: '客户应付总金额(元)',
          field: 'payTotalAmountStr',
          tableField: 'real_total_amount',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '客户实付总金额(元)',
          field: 'realTotalAmountStr',
          tableField: 'real_total_amount',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '应付商户总金额(元)',
          field: 'merchantTotalAmountStr',
          tableField: 'real_total_amount',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '清分服务费(元）',
          field: 'clearServiceTotalFeeStr',
          tableField: 'clear_service_total_fee',
          minWidth: 180,
          align: 'center'
        },
        {title: '确认状态', field: 'confirmStateStr', tableField: 'confirm_state', minWidth: 180, align: 'center'},
        {title: '确认人', field: 'confirmMan', tableField: 'confirm_man', minWidth: 180, align: 'center'},
        {title: '确认时间', field: 'confirmTime', tableField: 'confirm_time', minWidth: 180, align: 'center'},
        {title: '操作', width: 160, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        timeScope: [],
        transDate: '',
        searchId: '',
        nodeLevel: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        confirmState: ''
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
    timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD'))
    timeScope.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD'))
    this.queryParams.timeScope = timeScope
    this.getData()
  },
  methods: {
    checCheckboxkMethod2({row}) {
      return row.confirmState === 1
    },
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
        this.queryParams.timeScope[0] = this.$moment(this.queryParams.timeScope[0]).startOf('day').format('YYYY-MM-DD');
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD');
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      var timeScope = []
      var now = new Date()
      var before = 24 * 60 * 60 * 1000
      timeScope.push(this.$moment(now - before).startOf('day').format('YYYY-MM-DD'))
      timeScope.push(this.$moment(now - before).endOf('day').format('YYYY-MM-DD'))
      this.queryParams.timeScope = timeScope
      this.getData()
    },
    batchConfirm() {
      this.$confirm(`确定批量确认?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async () => {
        const x = await batchConfirm(this.selections)
        if (x.code === 200) {
          this.$notify.success(x.data)
          await this.getData()
        } else
          this.$notify.error(x.message)
      })
    },
    async oneConfirm(row) {
      const x = await myConfirm(row)
      if (x.code === 200) {
        this.$notify.success(x.data)
        await this.getData()
      } else
        this.$notify.error(x.message)

    },
    details(row) {
      this.$refs.checkDetailDialog.init(row)
    },
    spanMethod({row, _rowIndex, _columnIndex}) {
      if ((row.merchantGroupIdName === '小计' || row.merchantGroupIdName === '合计') && _columnIndex >= 1 && _columnIndex <= 2) {
        return {rowspan: 0, colspan: 0}
      } else if ((row.merchantGroupIdName === '小计' || row.merchantGroupIdName === '合计') && _columnIndex === 4) {
        return {rowspan: 1, colspan: 5}
      } else if ((row.merchantGroupIdName === '小计' || row.merchantGroupIdName === '合计') && (_columnIndex >= 4 && _columnIndex <= 6))
        return {
          rowspan: 0,
          colspan: 0
        }
    }
  }
}
</script>

<style scoped>

</style>
