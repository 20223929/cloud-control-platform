<template>
  <div class="app-container">
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="70%"
    >
      <div v-show="showSearch" class="search-form">
        <el-form ref="form" v-loading="loading" class="form-full" :model="queryParams" label-width="100px"
                 label-suffix=" : ">
        <el-row>
          <el-col :span="6">
              <el-form-item label="交易流水号" prop="transactionId">
                <el-input v-model="queryParams.transactionId" type="text" />
              </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车牌号码" prop="plateNum">
              <el-input v-model="queryParams.plateNum" type="text" :disabled="isDisable"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="卡号" prop="etcCardId">
              <el-input v-model="queryParams.etcCardId" type="text" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="交易模式" prop="transModel">
              <ec-select v-model="queryParams.transModel"
                         :ec-data="[
       { value: '1', label: 'ETC交易' },
       { value: '2', label: '协议付' }]"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="searchItem" :label-width="'50px'">
          <el-button type="primary" @click="queryData">查询</el-button>

          <el-button type="default" @click="handleReset">重置</el-button>
        </el-form-item>
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
          <el-button type="primary" @click="exportExcel">导出</el-button>
          <el-button @click="getData">刷新</el-button>
          <el-button @click="showSearch = !showSearch">搜索</el-button>
        </template>
      </vxe-grid>

      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { download } from 'ecip-web/utils'
import {getData} from '@/api/modules/bankbillcheck/tbMerchantBillCheckDetailApi'
export default {
  name: "TbMerchantBillCheckDetail",
  mixins: [permissionMixin],
  data(){
    return {
      persistent: {
        transDate: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
      },
      importVisible: false,
      showSearch: false,
      onlyOne: true,
      oneMore: true,
      tplUrl: 'api/v1/bankbillcheck/TbMerchantBillCheckDetail/excelTemplate',
      exportUrl: 'api/v1/bankbillcheck/TbMerchantBillCheckDetail/exportExcel',
      tableToolbar: {
        id: 'modules.bankbillcheck.tbmerchantbillcheckexpplatformetc.TbMerchantBillCheckDetail-toolbar',
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
        },
        zoom: true,
        resizable: {
          storage: true
        },
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
          tableField: 'merchant_group_id',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '运营方', field: 'merchantIdStr', tableField: 'merchant_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '场景', field: 'siteIdStr', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '车牌号码', field: 'plateNum', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '车牌颜色', field: 'plateColorStr', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: 'ETC卡号', field: 'etcCardId', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '交易流水号', field: 'transactionId', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '订单号', field: 'bankDeductionOrderId', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '交易模式', field: 'transModelStr', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '入场时间', field: 'enTimeStr', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '出场时间', field: 'exTimeStr', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '停放时长(小时)', field: 'stopTimeStr', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {title: '交易金额(元)', field: 'amountStr', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},
        {
          title: '交易时间',
          field: 'transTimeStr',
          tableField: 'trans_date',
          minWidth: 180,
          sortable: false,
          align: 'center'
        },
        {title: '备注', field: 'remark', tableField: 'site_id', minWidth: 180, sortable: false, align: 'center'},

      ],
      isDisable:false,
      queryParams: {
        transDate: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        transactionId:'',
        transModel: '',
        plateNum:'',
        etcCardId:'',
      },
      title:'异常流水详情',
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
    }
  },
  created() {
    //this.getData()
  },
  methods: {
    async init(transDate,merchantGroupId,merchantId,siteId){
      this.queryParams.transDate = transDate
      this.queryParams.merchantGroupId = merchantGroupId
      this.queryParams.merchantId = merchantId
      this.queryParams.siteId = siteId
      this.persistent.transDate = transDate
      this.persistent.merchantGroupId = merchantGroupId
      this.persistent.merchantId = merchantId
      this.persistent.siteId = siteId
      this.visible = true
      this.loading = false
      await this.getData();
    },
    async getData() {
      // 每次查询初始化checkbox selections
      this.selections = []
      this.loading = true
      const { data } = await getData(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage, field: this.page.field, order: this.page.order }), this.queryParams)
      this.tableData = data.records
      this.page.total = data.total
      this.loading = false
    },
    handlePageChange({ currentPage, pageSize }) {
      this.page.currentPage = currentPage
      this.page.pageSize = pageSize
      this.getData()
    },
    sortMethod({ column, property, order }) {
      this.page.field = (column.own.tableField && 'a.'.concat(column.own.tableField)) || property
      this.page.order = order
      this.getData()
    },
    async exportExcel() {
      this.loading = true
      await download({ url: this.exportUrl, method: 'post', data: this.queryParams })
      this.loading = false
    },
    queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.$data.queryParams = {}
      this.queryParams.transDate = this.persistent.transDate
      this.queryParams.merchantGroupId = this.persistent.merchantGroupId
      this.queryParams.merchantId = this.persistent.merchantId
      this.queryParams.siteId = this.persistent.siteId
      this.getData()
    },
  }
}
</script>

<style scoped>

</style>
