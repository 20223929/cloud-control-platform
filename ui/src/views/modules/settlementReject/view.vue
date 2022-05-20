<template>
  <div class="app-container">
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="85%"
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
      :toolbar="tableToolbar"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button @click="exportExcel">导出</el-button>
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
import {getData} from '@/api/modules/settlementreject/settlementRejectApi'
import { download } from 'ecip-web/utils'

export default {
  name: 'SettlementRejectDetail',
  mixins: [permissionMixin],
  data() {
    return {
      record: '',
      title: '预支抵扣详情',
      visible: false,
      loading: false,
      exportUrl: 'api/v1/settlementreject/settlementReject/exportExcel',
      tableToolbar: {
        id: 'modules.settlementreject.settlementRejectView-toolbar',
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
        { title: '服务方', field: 'siteName', tableField: 'site_name', minWidth: 180, sortable: false, align: 'center' },
        { title: '交易日期', field: 'transTime', tableField: 'trans_time', minWidth: 180, sortable: false, align: 'center' },
        { title: '车牌号码', field: 'vehiclePlate', tableField: 'vehicle_plate', minWidth: 180, sortable: false, align: 'center' },
        { title: '车牌颜色', field: 'vehicleColorTxt', tableField: 'vehicle_color', minWidth: 180, sortable: false, align: 'center' },
        { title: 'ETC卡号', field: 'cardId', tableField: 'card_id', minWidth: 180, sortable: false, align: 'center' },
        { title: 'OBU合同系列号', field: 'obuId', tableField: 'obu_id', minWidth: 180, sortable: false, align: 'center' },
        { title: '交易流水号', field: 'transactionId', tableField: 'transaction_id', minWidth: 180, sortable: false, align: 'center' },
        { title: '交易金额(元)', field: 'fee', tableField: 'fee', minWidth: 180, sortable: false, align: 'center' },
        { title: '交易时间', field: 'transDate', tableField: 'trans_time', minWidth: 180, sortable: false, align: 'center' },
        { title: '流水状态', field: 'transStaTxt', tableField: 'trans_sta', minWidth: 180, sortable: false, align: 'center' }
      ],
    }
  },
  methods: {
    async init(record){
      Object.assign(this.$data, this.$options.data())
      this.visible = true
      this.record = record
      this.loading = true
      await this.$nextTick()
      const { data } = await getData(Object.assign({}, { size: this.page.pageSize, current: this.page.currentPage, field: this.page.field, order: this.page.order }), this.record)
      this.tableData = data.records
      this.page.total = data.total
      this.loading = false
    },
    async exportExcel() {
      this.loading = true
      await download({ url: this.exportUrl, method: 'post', data: this.record })
      this.loading = false
    }
  }
}
</script>

<style scoped>

</style>
