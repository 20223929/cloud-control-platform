<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      append-to-body="true"
      width="80%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" label-width="120px">
        <el-divider content-position="left">基础信息</el-divider>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="拓展方"
              prop="merchantGroupName"
            >
              <el-input v-model="formItem.merchantGroupName" type="text" placeholder="拓展方" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="运营方"
              prop="merchantName"
            >
              <el-input v-model="formItem.merchantName" type="text" placeholder="运营方" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="场景"
              prop="siteName"
            >
              <el-input v-model="formItem.siteName" type="text" placeholder="场景" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="ETC卡号"
              prop="etcCardId"

            >
              <el-input v-model="formItem.etcCardId" type="text" placeholder="ETC卡号" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="油枪号"
              prop="equipmentId"
            >
              <el-input v-model="formItem.equipmentId" type="text" placeholder="油枪号" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="加油量(升)"
              prop="fuelVolume"
            >
              <el-input v-model="formItem.fuelVolume" type="text" placeholder="加油量(升)" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="交易流水号"
              prop="transactionId"
            >
              <el-input v-model="formItem.transactionId" type="text" placeholder="交易流水号" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="订单号"
              prop="deductionOrderId"
            >
              <el-input v-model="formItem.deductionOrderId" type="text" placeholder="订单号" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="银行订单号"
              prop="bankDeductionOrderId"
            >
              <el-input v-model="formItem.bankDeductionOrderId" type="text" placeholder="银行订单号" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="车型"
              prop="vehicleType"
            >
              <ec-select v-model="formItem.vehicleType" dict-type="vehicleType" disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="车牌号码"
              prop="vehicleNumber"
            >
              <el-input v-model="formItem.vehicleNumber" type="text" placeholder="车牌号码" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="车牌颜色"
              prop="vehicleColorDesc"
            >
              <el-input v-model="formItem.vehicleColorDesc" type="text" placeholder="车牌颜色" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">交易信息</el-divider>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="应收金额(元)"
              prop="payFee"
            >
              <el-input v-model="formItem.payFee" type="text" placeholder="应收金额" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="实收金额(元)"
              prop="tradeFee"
            >
              <el-input v-model="formItem.tradeFee" type="text" placeholder="实收金额" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="优惠金额(元)"
              prop="discountFee"
            >
              <el-input v-model="formItem.discountFee" type="text" placeholder="优惠金额" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="交易模式"
              prop="modelTypeName"

            >
              <el-input v-model="formItem.modelTypeName" type="text" placeholder="交易模式" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="交易时间"
              prop="tradeTime"
            >
              <el-input v-model="formItem.tradeTime" type="text" placeholder="交易时间" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="交易状态"
              prop="status"
            >
              <el-input v-model="formItem.statusName" type="text" placeholder="交易状态" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="签约银行"
              prop="signBank"
            >
              <el-input v-model="formItem.signBank" type="text" placeholder="签约银行" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left" v-if="formItem.msg">异常信息</el-divider>
        <el-row :gutter="24" v-if="formItem.msg">
          <el-col :span="8">
            <el-form-item
              label="异常类型"
              prop="exceptionName"

            >
              <el-input v-model="formItem.exceptionName" type="text" placeholder="异常类型" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="异常描述"
              prop="msg"

            >
              <el-input v-model="formItem.msg" type="text" placeholder="异常描述" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import {findDataById} from '@/api/modules/querymanage/gasStationQueryApi'

export default {
  name: 'Detail',
  components: {},
  data() {
    return {
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      record: {},
      formItem: {}
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())
      this.method = method
      this.record = record
      this.title = '加油站流水详情'
      this.visible = true
      await this.$nextTick()
      this.loading = true
      const {data} = await findDataById({
        transactionId: record.transactionId,
        modelType: record.modelType,
        status: record.status
      })
      data.tradeFee = (data.tradeFee / 100).toFixed(2)
      data.payFee = (data.payFee / 100).toFixed(2)
      data.discountFee = (data.discountFee / 100).toFixed(2)
      this.formItem = Object.assign({}, this.formItem, data, {fuelVolume: data.detailInfo.fuelVolume.toFixed(3)})
      this.loading = false
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
