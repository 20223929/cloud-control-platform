<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-form-item
          label="流水号"
          prop="vehicleId">
          <el-input v-model="queryParams.transactionId" type="type" placeholder="流水号"/>
        </el-form-item>
        <el-form-item
          label="ETC卡号"
          prop="vehicleId">
          <el-input v-model="queryParams.etcCardId" type="type" placeholder="ETC卡号"/>
        </el-form-item>
        <el-form-item
          label="车牌"
          prop="vehiclePlate">
          <el-input v-model="queryParams.vehiclePlate" type="type" placeholder="车牌"/>
        </el-form-item>
        <el-form-item
          label="车牌颜色"
          prop="vehicleColor">
          <ec-select v-model="queryParams.vehicleColor"
                     :ec-data="[
             { value: '0', label: '蓝色' },
             { value: '1', label: '黄色' },
             { value: '2', label: '黑色' },
             { value: '4', label: '渐变绿色' },
             { value: '5', label: '黄绿双拼色' },
             { value: '6', label: '蓝白渐变色' },
             { value: '11', label: '绿色' },
             { value: '12', label: '红色' },
           ]"
          />
        </el-form-item>


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

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="联网中心结算明细数据导入"
      :visible="importVisible"
      @onSuccess="getData"
      @close="importVisible = false"
    >
      <template v-slot:extra_params="{ extraParams }">
        <el-form-item label="是否生成新主键">
          <ec-radio-group v-model="extraParams.isNewPk" :ec-data="[ {value: false, label: '否'}, {value: true, label: '是' } ]" />
        </el-form-item>
        <el-form-item label="导入策略">
          <ec-radio-group v-model="extraParams.strategy" :ec-data="[ {value: 'ignore', label: '忽略'}, {value: 'update', label: '更新' } ]" />
        </el-form-item>
      </template>
    </ec-import>

    <detail ref="tbCenterSettlementDetailDialog" @refreshData="getData" />

  </div>
</template>
<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getData, deleteByIds } from '@/api/modules/settlement/tbCenterSettlementDetailApi'
import Detail from './components/detail'
import { download } from 'ecip-web/utils'

export default {
  name: 'ModulesSettlementTbCenterSettlementDetailView',
  components: { Detail },
  mixins: [permissionMixin],
  props: {
    param: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      importVisible: false,
      showSearch: false,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/settlement/tbCenterSettlementDetail/importExcel',
      tplUrl: 'api/v1/settlement/tbCenterSettlementDetail/excelTemplate',
      exportUrl: 'api/v1/settlement/tbCenterSettlementDetail/exportExcel',
      tableToolbar: {
        id: 'modules.settlement.tbCenterSettlementDetailView-toolbar',
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
        { title: '序号',type: 'seq', width: 50, fixed: 'left', align: 'center' },
        { title: '服务类型', field: 'serviceTypeStr', tableField: 'service_type', minWidth: 180,  },
        { title: '拓展方', field: 'merchantGroupIdStr', tableField: 'merchant_group_id', minWidth: 180,  },
        { title: '运营方', field: 'merchantIdStr', tableField: 'merchant_id', minWidth: 180,  },
        { title: '场景', field: 'siteIdStr', tableField: 'site_id', minWidth: 180,  },
        { title: '流水号', field: 'transactionId', tableField: 'transaction_id', minWidth: 210,  },
        { title: 'ETC卡号', field: 'etcCardId', tableField: 'etc_card_id', minWidth: 180,  },
        { title: '车牌号码', field: 'vehiclePlate', tableField: 'vehicle_plate', minWidth: 180,  },
        { title: '车牌颜色', field: 'vehicleColorStr', tableField: 'vehicle_color', minWidth: 180,  },
        { title: '交易时间', field: 'transTime', tableField: 'trans_time', minWidth: 180,  },
        { title: '实收金额', field: 'merchantRealFeeStr', tableField: 'merchant_real_fee', minWidth: 180,  },
        { title: '应收金额', field: 'merchantPayFeeStr', tableField: 'merchant_pay_fee', minWidth: 180,  },
        { title: '清分日期', field: 'clearDate', tableField: 'clear_date', minWidth: 180,  },
      ],
      queryParams: {
        clearDate:"",
        vehiclePlate:"",
        vehicleColor:"",
        etcCardId:"",
        transactionId:""
      }
    }
  },
  watch: {
    param: {
      handler() {
        this.getData()
      },
      immediate: false
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
    this.getData()
  },
  methods: {
    async getData() {
      // 每次查询初始化checkbox selections
      debugger
      this.selections = []
      this.loading = true
      console.log(this.param)
      if(this.param===undefined){
        this.loading = false
        return
      }
      else
        this.queryParams.clearDate = this.param.settlementDay
      console.log(this.queryParams.clearDate)
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
    add() {
      this.$refs.tbCenterSettlementDetailDialog.init('add')
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.tbCenterSettlementDetailDialog.init('edit', record)
    },
    deleteByIds(rowId) {
      const additionMsg = rowId ? '此条记录' : '选中记录'
      this.$confirm(`确定删除${additionMsg}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async() => {
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
      this.getData()
    }
  }
}
</script>

<style scoped>

</style>
