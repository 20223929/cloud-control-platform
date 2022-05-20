<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-position="right" label-width="120px">
        <el-row>
          <el-col :span="6">
            <el-form-item
              label="银行签约流水号"
              prop="signAgreementNo"
            >
              <el-input v-model="queryParams.signAgreementNo" type="text" placeholder="银行签约流水号"/>
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
          <el-col :span="6">
            <el-form-item
              label="车牌颜色"
              prop="vehicleColor"
            >
              <ec-select v-model="queryParams.vehicleColor" dict-type="vehiclePlateColor"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="发行手机号"
              prop="issuerPhone"
            >
              <el-input v-model="queryParams.issuerPhone" type="text" placeholder="发行手机号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item
              label="OBU合同序列号"
              prop="obuId"
            >
              <el-input v-model="queryParams.obuId" type="text" placeholder="OBU合同序列号"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="ETC卡号"
              prop="etcCardId"
            >
              <el-input v-model="queryParams.etcCardId" type="text" placeholder="ETC卡号"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="状态"
              prop="status"
            >
              <el-select v-model="queryParams.status" clearable placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="签约时间"
              prop="timeScope"
            >
              <el-date-picker
                v-model="queryParams.timeScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="queryParams.timeScope"
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
        <el-button v-permission="['paySign:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
        <!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:vehicle_color="{ row }">
        <ec-select :value="row.vehicleColor" dict-type="vehiclePlateColor" readonly/>
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['paySign:edit']" v-show="row.status == 1" type="danger" @click="close(row)">关闭
        </el-button>
        <el-button v-permission="['paySign:edit']" v-show="row.status == 2" type="success" @click="open(row)">开启
        </el-button>
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, update} from '@/api/modules/systemmanage/shanxi/paySignApi'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesManagePaySignView',
  mixins: [permissionMixin],
  data() {
    return {
      options: [
        {
          value: '1',
          label: '开启'
        }, {
          value: '2',
          label: '关闭'
        }],
      showSearch: true,
      loading: false,
      exportUrl: 'api/v1/manage/paySign/exportExcel',
      tableToolbar: {
        id: 'modules.manage.paySignView-toolbar',
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
        {title: '序号', type: 'seq', minWidth: 80, align: 'center'},
        {title: '签约时间', field: 'signTime', tableField: 'sign_time', minWidth: 180, align: 'center'},
        {title: '银行签约流水号', field: 'signAgreementNo', tableField: 'sign_agreement_no', minWidth: 180, align: 'center'},
        {title: '签约机构', field: 'bankName', tableField: 'bank_name', minWidth: 180, align: 'center'},
        // { title: '用户ID', field: 'userId', tableField: 'user_id', minWidth: 180,align: 'center' },
        // { title: '微信用户ID', field: 'wechatUserId', tableField: 'wechat_user_id', minWidth: 180,align: 'center' },
        {title: '车牌号码', field: 'vehicleNumber', tableField: 'vehicle_number', minWidth: 180, align: 'center'},
        {title: '车牌颜色', field: 'vehicleColorDesc', tableField: 'vehicle_color_desc', minWidth: 180, align: 'center'},
        {title: '车型', field: 'vehicleClassDesc', tableField: 'vehicle_class_desc', minWidth: 180, align: 'center'},
        {title: 'ETC卡号', field: 'etcCardId', tableField: 'etc_card_id', minWidth: 180, align: 'center'},
        {title: 'OBU合同序列号', field: 'obuId', tableField: 'obu_id', minWidth: 180, align: 'center'},
        // { title: '发行机构', field: 'issuerBankName', tableField: 'issuer_bank_name', minWidth: 180,align: 'center' },
        {title: '签约卡标识', field: 'bankCardTypeDesc', tableField: 'bank_card_type_desc', minWidth: 180, align: 'center'},
        // { title: '发卡行名称', field: 'issuerCardBankName', tableField: 'issuer_card_bank_name', minWidth: 180,align: 'center' },
        {title: '用户名称', field: 'userName', tableField: 'user_name', minWidth: 180, align: 'center'},
        {title: '用户身份证', field: 'idNo', tableField: 'id_no', minWidth: 180, align: 'center'},
        {title: '发行手机号', field: 'issuerPhone', tableField: 'issuer_phone', minWidth: 180, align: 'center'},
        {title: '绑定手机号', field: 'bindPhone', tableField: 'bind_phone', minWidth: 180, align: 'center'},
        {title: '签约结果', field: 'signStateDesc', tableField: 'sign_state_desc', minWidth: 180, align: 'center'},
        {title: '状态', field: 'statusDesc', tableField: 'status_desc', minWidth: 180, align: 'center'},
        {title: '操作', width: 150, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      transTime: '',
      queryParams: {
        signAgreementNo: '',
        vehicleNumber: '',
        vehicleColor: '',
        etcCardId: '',
        obuId: '',
        issuerPhone: '',
        status: '',
        timeScope: []
      }
    }
  },
  created() {
    var timeScope = []
    timeScope.push(this.$moment(new Date()).startOf('day').format('YYYY-MM-DD HH:mm:ss'))
    timeScope.push(this.$moment(new Date()).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
    this.queryParams.timeScope = timeScope
    this.getData()
  },
  methods: {
    async getData() {
      // 每次查询初始化checkbox selections
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
    async close(row) {
      this.loading = true
      const {data} = await update(row.sysId, {status: 2, vehicleNumber: row.vehicleNumber})
      this.loading = false
      if (data.toString().indexOf('失败') > -1) this.$notify.error(data)
      else this.$notify.success(data)
      this.queryData()
    },
    async open(row) {
      this.loading = true
      const {data} = await update(row.sysId, {status: 1, vehicleNumber: row.vehicleNumber})
      this.loading = false
      if (data.toString().indexOf('失败') > -1) this.$notify.error(data)
      else this.$notify.success(data)
      this.queryData()
    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      if (this.queryParams.timeScope.length > 0) {
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss');
      }
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
