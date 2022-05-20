<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      append-to-body="true"
      width="70%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" label-width="130px"
               label-suffix=" : ">
        <el-divider content-position="left">基础信息</el-divider>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="拓展方"
              prop="merchantGroupIdName"

            >
              <el-input v-model="formItem.merchantGroupIdName" type="text" placeholder="拓展方" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="运营方"
              prop="merchantIdName"

            >
              <el-input v-model="formItem.merchantIdName" type="text" placeholder="运营方" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="场景"
              prop="siteIdName"

            >
              <el-input v-model="formItem.siteIdName" type="text" placeholder="场景" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="出口车道号"
              prop="equipmentId"

            >
              <el-input v-model="formItem.equipmentId" type="text" placeholder="出口车道号" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="入场时间"
              prop="enTime"

            >
              <el-input v-model="formItem.enTime" type="text" placeholder="入场时间" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="出场时间"
              prop="exTime"

            >
              <el-input v-model="formItem.exTime" type="text" placeholder="出场时间" readonly/>
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
              prop="orderId"

            >
              <el-input v-model="formItem.orderId" type="text" placeholder="银行订单号" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="车型"
              prop="orderId"

            >
              <ec-select v-model="formItem.vehicleType" dict-type="vehicleType" placeholder="车型" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="车牌号码"
              prop="vehiclePlate"

            >
              <el-input v-model="formItem.vehiclePlate" type="text" placeholder="车牌号码" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="车牌颜色"
              prop="vehicleColorName"

            >
              <el-input v-model="formItem.vehicleColorName" type="text" placeholder="车牌颜色" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="停放时长"
              prop="parkHours"

            >
              <el-input v-model="formItem.parkHours" type="text" placeholder="停放时长" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">交易信息</el-divider>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="应收金额(元)"
              prop="merchantPayFee"

            >
              <el-input v-model="formItem.merchantPayFee" type="text" placeholder="应收金额" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="实收金额(元)"
              prop="merchantRealFee"

            >
              <el-input v-model="formItem.merchantRealFee" type="text" placeholder="实收金额" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="优惠金额(元)"
              prop="merchantDiscountFee"

            >
              <el-input v-model="formItem.merchantDiscountFee" type="text" placeholder="优惠金额" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="交易时间"
              prop="transTime"

            >
              <el-input v-model="formItem.transTime" type="text" placeholder="交易时间" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="交易状态"
              prop="statusName"

            >
              <el-input v-model="formItem.statusName" type="text" placeholder="交易状态" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="ETC卡号"
              prop="etcCardId"

            >
              <el-input v-model="formItem.etcCardId" type="text" placeholder="ETC卡号" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="消费TAC码"
              prop="tac"

            >
              <el-input v-model="formItem.tac" type="text" placeholder="消费TAC码" readonly/>
            </el-form-item>
          </el-col>
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
              label="消费前卡余额(元)"
              prop="preBalance"

            >
              <el-input v-model="formItem.preBalance" type="text" placeholder="消费前卡余额" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="消费后卡余额(元)"
              prop="postBalance"

            >
              <el-input v-model="formItem.postBalance" type="text" placeholder="消费后卡余额" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="终端机编号"
              prop="terminalNo"

            >
              <el-input v-model="formItem.terminalNo" type="text" placeholder="终端机编号" readonly/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="终端交易序号"
              prop="terminalTransNo"

            >
              <el-input v-model="formItem.terminalTransNo" type="text" placeholder="终端交易序号" readonly/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
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

import {info} from '@/api/modules/querymanage/parkEexitApi'

export default {
  name: 'ModulesQuerymanageParkEexitForm',
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
      this.title = '停车场流水详情'
      this.visible = true
      this.loading = true
      await this.$nextTick()
      this.$refs.form.clearValidate()
      this.loading = true
      const {data} = await info({sysId: record.sysId, modelType: record.modelType, status: record.status})
      this.formItem = Object.assign({}, this.formItem, data)
      if (data.msg) {
        this.formItem.exceptionName = data.statusName
      }
      this.loading = false
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
