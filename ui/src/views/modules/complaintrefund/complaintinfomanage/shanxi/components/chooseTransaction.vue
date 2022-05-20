<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="75%"
    >
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
        :toolbar="detailTableToolbar"
        @sort-change="sortMethod"
        @page-change="handlePageChange"
      >
        <template v-slot:operation="{ row }">
          <el-button type="success" @click="confirmRefund(row)">确定退费</el-button>
        </template>
      </vxe-grid>
    </el-dialog>
  </div>
</template>

<script>

import {getTransactions} from '@/api/modules/complaintrefund/shanxi/tbComplaintRefundApi'

export default {
  name: 'chooseTransaction',
  components: {},
  data() {
    return {
      detailTableToolbar: {
        id: 'modules.complaintrefund.chooseTransaction-toolbar',
        custom: true,
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
        { title: '交易流水号', field: 'transactionId', tableField: 'transaction_id', minWidth: 180, align: 'center' },
        { title: '交易模式', field: 'tradeMode', tableField: 'trade_mode', minWidth: 180, align: 'center' },
        { title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center' },
        { title: '服务方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, align: 'center' },
        { title: '场景', field: 'siteName', tableField: 'site_name', minWidth: 180, align: 'center' },
        { title: '交易时间', field: 'tradeDay', tableField: 'trade_day', minWidth: 180, align: 'center' },
        { title: '车牌号码', field: 'vehiclePlate', tableField: 'vehicle_number', minWidth: 180, align: 'center' },
        { title: '车牌颜色', field: 'vehicleColorDesc', tableField: 'vehicle_color_desc', minWidth: 180, align: 'center' },
        { title: '服务类型', field: 'serviceTypeName', tableField: 'service_type_desc', minWidth: 180, align: 'center' },
        { title: '客户实付金额', field: 'merchantRealFee', tableField: 'actual_pay_fee', minWidth: 180, align: 'center' },
        { title: '优惠总金额', field: 'merchantDiscountFee', tableField: 'actual_pay_fee', minWidth: 180, align: 'center' },
        { title: '客户应付金额', field: 'merchantPayFee', tableField: 'actual_pay_fee', minWidth: 180, align: 'center' },
        { title: '操作', width: 180, fixed: 'right', align: 'center', slots: { default: 'operation' }}
      ],
      queryParams: {

      },
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      title: '',
    }
  },
  methods: {
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
    async initChooseTransaction(record) {
      this.visible = true
      this.title = '选择退费流水'
      await this.$nextTick()
      this.loading = true
      this.queryParams = record;
      try {
        const {data} = await getTransactions(Object.assign({}, {
          size: this.page.pageSize,
          current: this.page.currentPage,
          field: this.page.field,
          order: this.page.order
        }), this.queryParams)
        if(!data.records || data.records.length == 0) {
          this.$notify.error("输入的信息没有对应的原始流水信息,无法进行申请退费操作!")
          this.loading = false
          this.visible = false
          return
        }
        this.tableData = data.records
        this.tableData.forEach((item,index,array) => {
          item.merchantRealFee = item.merchantRealFee ? (item.merchantRealFee/100).toFixed(2) : item.merchantRealFee
          item.merchantDiscountFee = item.merchantDiscountFee ? (item.merchantDiscountFee/100).toFixed(2) : item.merchantDiscountFee
          item.merchantPayFee = item.merchantPayFee ? (item.merchantPayFee/100).toFixed(2) : item.merchantPayFee
        })
        this.page.total = data.total
        this.loading = false
      }catch (e) {
        this.loading = false
        this.visible = false
        this.$notify.error(e.description)
      }
    },
    confirmRefund(row){
      this.$emit("getRefundEexit",row)
      this.visible = false
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
