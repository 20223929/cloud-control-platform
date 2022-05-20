<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="75%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :rules="rules" :model="formItem" label-position="right"
               label-width="125px" label-suffix=":">
        <el-input v-model="formItem.refundEexit.deductionOrderId" type="hidden"/>
        <el-divider content-position="left">用户信息</el-divider>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="投诉人"
              prop="apply.refundApplyName"
              :rules="[{required: true,message: '投诉人不能为空',trigger: 'blur'}]"
            >
              <el-input v-model="formItem.apply.refundApplyName" type="text" placeholder="投诉人" clearable
                        :disabled="disabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="联系方式"
              prop="apply.refundApplyContact"
              :rules="[{required: true,message: '联系方式不能为空',trigger: 'blur'}]"
            >
              <el-input v-model="formItem.apply.refundApplyContact" type="text" placeholder="联系方式" clearable
                        :disabled="disabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="车牌号码"
              prop="apply.refundApplVehiclePlate"
              :rules="[{required: true,message: '车牌号码不能为空',trigger: 'blur'}]"
            >
              <el-input v-model="formItem.apply.refundApplVehiclePlate" type="text" placeholder="车牌号码" clearable
                        :disabled="disabled" @change="eexitConditionChange"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="车牌颜色"
              prop="apply.refundAppyVehicleColor"
              :rules="[{required: true,message: '车牌颜色不能为空',trigger: 'change'}]"
            >
              <ec-select v-model="formItem.apply.refundAppyVehicleColor" dict-type="vehiclePlateColor"
                         :disabled="disabled" :defaultFirst="ifColor0" @change="eexitConditionChange"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">投诉信息</el-divider>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="投诉单号"
              prop="apply.refundApplyNo"
              :rules="[{required: true,message: '投诉单号不能为空',trigger: 'change'}]"
            >
              <el-input v-model="formItem.apply.refundApplyNo" type="text" placeholder="投诉单号" :disabled="allDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="投诉通道" prop="apply.refundApplyChannel" :rules="[
                { required: true, message: '投诉通道不能为空', trigger: 'change' }
              ]">
              <el-select v-model="formItem.apply.refundApplyChannel" placeholder="请选择" :disabled="disabled">
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
          <el-col :span="8">
            <el-form-item
              label="反馈交易时间"
              prop="apply.feedBackTradeTime"
              :rules="[{required: true,message: '反馈交易时间不能为空',trigger: 'change'}]"
            >
              <el-date-picker
                v-model="formItem.apply.feedBackTradeTime"
                type="date"
                :key="formItem.apply.feedBackTradeTime"
                placeholder="反馈交易时间"
                align="right" value-format="yyyy-MM-dd" :disabled="disabled" @input="feedBackTradeTimeChange"
                style="width: 100%;" clearable>
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="交易场所"
              prop="apply.siteId"
            >
              <ec-select-tree
                ref="merchantTreeData"
                v-model="formItem.apply.siteId"
                url="api/v1/merchant/tbMerchant/data"
                value-field="id"
                attr-name="name"
                :disabled="disabled"
                defaultExpandAll="true"
                @change="eexitConditionChange"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button type="success" v-show="method === 'add' || method == 'edit'" @click="ralationTransaction"
                       :disabled="disabled">点击关联流水
            </el-button>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item
              label="事件描述"
              prop="apply.refundApplyReason"
              :rules="[
                { required: true, message: '事件描述不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.apply.refundApplyReason" type="textarea" placeholder="事件描述" :rows="5"
                        maxlength="400" show-word-limit :disabled="disabled"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">关联流水信息</el-divider>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="交易流水号"
              prop="refundEexit.transactionId"
            >
              <el-input v-model="formItem.refundEexit.transactionId" type="text" placeholder="交易流水号"
                        :disabled="allDisabled"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="交易模式"
              prop="refundEexit.tradeMode"
            >
              <el-input type="text" v-model="formItem.refundEexit.tradeMode" placeholder="交易模式"
                        :disabled="allDisabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="拓展方"
              prop="refundEexit.merchantGroupName"
            >
              <el-input type="text" v-model="formItem.refundEexit.merchantGroupName" placeholder="拓展方"
                        :disabled="allDisabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="运营方"
              prop="refundEexit.merchantName"
            >
              <el-input type="text" v-model="formItem.refundEexit.merchantName" placeholder="运营方"
                        :disabled="allDisabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="场景"
              prop="refundEexit.siteName"
            >
              <el-input type="text" v-model="formItem.refundEexit.siteName" placeholder="场景"
                        :disabled="allDisabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="交易时间"
              prop="refundEexit.tradeDay"
            >
              <el-input type="text" v-model="formItem.refundEexit.tradeDay" placeholder="交易时间"
                        :disabled="allDisabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="车牌号码"
              prop="refundEexit.vehiclePlate"
            >
              <el-input type="text" v-model="formItem.refundEexit.vehiclePlate" placeholder="车牌号码"
                        :disabled="allDisabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="车牌颜色"
              prop="refundEexit.vehicleColor"
            >
              <ec-select v-model="formItem.refundEexit.vehicleColor" dict-type="vehiclePlateColor"
                         :disabled="allDisabled" :defaultFirst="ifColor0"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="服务类型"
              prop="refundEexit.serviceType"
            >
              <ec-select v-model="formItem.refundEexit.serviceType" dict-type="service_type" placeholder="请选择"
                         :disabled="allDisabled"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item
              label="客户实付金额(元)"
              prop="refundEexit.merchantRealFee"
            >
              <el-input type="text" v-model="formItem.refundEexit.merchantRealFee" placeholder="客户实付金额"
                        :disabled="allDisabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="优惠总金额(元)"
              prop="refundEexit.merchantDiscountFee"
            >
              <el-input type="text" v-model="formItem.refundEexit.merchantDiscountFee" placeholder="优惠总金额"
                        :disabled="allDisabled"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="客户应付金额(元)"
              prop="refundEexit.merchantPayFee"
            >
              <el-input type="text" v-model="formItem.refundEexit.merchantPayFee" placeholder="客户应付金额"
                        :disabled="allDisabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
        </el-row>
        <el-divider content-position="left" v-if="method === 'audit' || method === 'view'">处理意见</el-divider>
        <el-row :gutter="24" v-if="method === 'audit' || method === 'view'">
          <el-col :span="24">
            <el-form-item
              label="是否退费"
              prop="apply.refundApplyHandleResult"
              :rules="[
                { required: true, message: '是否退费不能为空', trigger: 'blur' }
              ]"
            >
              <el-radio-group v-model="formItem.apply.refundApplyHandleResult" :disabled="handleDisabled"
                              @change="radioChange">
                <el-radio :label=1>是
                </el-radio>
                <el-radio :label=2>否
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24" v-if="method === 'audit' || method === 'view'">
          <el-col :span="8">
            <el-form-item
              label="退费形式"
              prop="apply.refundWayType"
              :rules="refundWayTypeRule"
            >
              <el-select v-model="formItem.apply.refundWayType" placeholder="请选择" :disabled="refundWayTypeDisabled"
                         clearable>
                <el-option
                  v-for="item in refundType"
                  :value="item.value"
                  :label="item.label"
                  :key="item.value"
                  :disabled="item.disabled"
                >

                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24" v-if="method === 'audit' || method === 'view'">
          <el-col :span="24">
            <el-form-item
              label="处理说明"
              prop="apply.refundApplyHandleMsg"
              :rules="[
                { required: true, message: '处理说明不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.apply.refundApplyHandleMsg" type="textarea" placeholder="处理说明" :rows="5"
                        maxlength="400" show-word-limit :disabled="handleDisabled"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
        <el-button v-if="method === 'add' || method === 'edit'" type="primary" :loading="saveLoading"
                   @click="save">保存</el-button>
        <el-button v-if="method !== 'view' && method !== 'audit'" type="primary" :loading="saveLoading"
                   @click="submitResult">提交</el-button>
        <el-button v-if="method === 'audit'" type="primary" :loading="saveLoading" @click="audit">提交</el-button>
      </span>
    </el-dialog>
    <choose-transaction ref="chooseTransaction" @getRefundEexit="getRefundEexit"/>
  </div>
</template>

<script>

import {
  editDraft,
  getDetail,
  getRefundNo,
  handle,
  saveData,
  saveDraft
} from '@/api/modules/complaintrefund/shanxi/tbComplaintRefundApi'
import chooseTransaction from './chooseTransaction'

export default {
  name: 'ModulesComplaintrefundTbComplaintRefundForm',
  components: {chooseTransaction},
  data() {
    var checkSiteId = (rule, value, callback) => {
      if (!value) return callback(new Error('交易场所不能为空'));
      if (value.length != 13) return callback(new Error('请选择三级交易场所'));
      callback()
    }
    return {
      refundWayTypeRule: [{required: true, message: '退费形式不能为空', trigger: 'change'}],
      isValid: true,
      feedBackTradeTime: '',
      refundType: [
        {
          value: 1,
          label: '银联线上',
          // disabled: true
        },
        {
          value: 2,
          label: '商户线下退费'
        }
      ],
      channel: [
        {
          value: 2,
          label: '业务员录入'
        }
      ],
      disabled: true,
      refundTypeDisabled: true,
      handleDisabled: true,
      refundWayTypeDisabled: true,
      refundTypeShow: false,
      allDisabled: true,
      ifColor0: false,
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      record: {},
      subRecord: {},
      formItem: {
        apply: {
          refundApplyNo: '',
          refundApplyChannel: 2
        },
        refundEexit: {}
      },
      relationParams: {
        transactionId: '',
        timeScope: [],
        vehiclePlate: '',
        vehicleColor: '',
        siteId: ''
      },
      rules: {
        'apply.siteId': [
          {validator: checkSiteId, required: true, trigger: 'change'}
        ],
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())
      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `创建投诉信息`
        this.disabled = false
      } else if (method === 'edit') {
        this.title = `修改投诉信息`
        this.disabled = false
      } else if (method === 'audit') {
        this.handleDisabled = false
        this.title = '处理投诉信息'
      } else if (method === 'view') {
        this.title = '查看投诉信息'
      }
      this.visible = true
      this.loading = true
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'add') {
        const {data} = await getRefundNo()
        this.formItem.apply.refundApplyNo = data
      } else if (method === 'view' || method === 'audit' || method === 'edit') {
        const {data} = await getDetail(this.record, method)
        this.formItem.apply = Object.assign({}, data.apply)
        this.formItem.refundEexit = Object.assign({}, data.refundEexit)
        this.formItem.refundEexit.merchantRealFee = this.formItem.refundEexit.merchantRealFee ? (this.formItem.refundEexit.merchantRealFee / 100).toFixed(2) : this.formItem.refundEexit.merchantRealFee
        this.formItem.refundEexit.merchantDiscountFee = this.formItem.refundEexit.merchantDiscountFee ? (this.formItem.refundEexit.merchantDiscountFee / 100).toFixed(2) : this.formItem.refundEexit.merchantDiscountFee
        this.formItem.refundEexit.merchantPayFee = this.formItem.refundEexit.merchantPayFee ? (this.formItem.refundEexit.merchantPayFee / 100).toFixed(2) : this.formItem.refundEexit.merchantPayFee
        if (this.formItem.apply.refundAppyVehicleColor == 0) this.ifColor0 = true
        if (method === 'audit') {
          if (data.refundEexit.tradeMode === '银联') {
            this.refundWayTypeDisabled = false
          } else {
            this.formItem.apply.refundWayType = 2
          }
        }
      }
      this.loading = false
    },
    ralationTransaction() {
      this.relationParams = Object.assign(this.$data.relationParams, this.$options.data().relationParams)
      if (!this.formItem.refundEexit.transactionId && !((this.formItem.apply.refundApplVehiclePlate && this.formItem.apply.refundAppyVehicleColor) && this.formItem.apply.feedBackTradeTime && this.formItem.apply.siteId)) {
        this.$alert("车牌号码,车牌颜色,交易时间以及交易场所必须填写,才能进行关联流水")
        return false;
      }
      if (this.formItem.apply.siteId.length != 13) {
        this.$alert("请选择三级交易场所")
        return false
      }
      this.relationParams.vehiclePlate = this.formItem.apply.refundApplVehiclePlate
      this.relationParams.vehicleColor = this.formItem.apply.refundAppyVehicleColor
      this.relationParams.siteId = this.formItem.apply.siteId
      this.relationParams.timeScope.push(this.formItem.apply.feedBackTradeTime)
      this.relationParams.timeScope.push(this.$moment(this.formItem.apply.feedBackTradeTime).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
      this.$refs.chooseTransaction.initChooseTransaction(this.relationParams)
    },
    getRefundEexit(record) {
      if (this.formItem.apply.refundAppyVehicleColor == 0) this.ifColor0 = true
      this.formItem.refundEexit = Object.assign({}, record)
      this.isValid = true
    },
    eexitConditionChange() {
      this.isValid = false
    },
    feedBackTradeTimeChange(val) {
      if (val !== this.feedBackTradeTime) {
        this.isValid = false
        this.feedBackTradeTime = val
      }
    },
    radioChange(val) {
      if (val === 2) {
        this.$nextTick(() => {
          this.$refs.form.clearValidate(['apply.refundWayType'])
        })
        this.refundWayTypeRule = [{required: false}]
        this.formItem.apply.refundWayType = ''
        this.refundWayTypeDisabled = true
      } else {
        this.refundWayTypeDisabled = false
        this.refundWayTypeRule = [{required: true, message: '退费形式不能为空', trigger: 'change'}]
      }
    },
    save() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          if (!this.isValid) {
            this.$notify.warning("流水关联条件发生了变化，请重新关联流水信息！")
            return false
          }
          try {
            let apply = this.formItem.apply
            let refundEexit = this.formItem.refundEexit
            apply.transactionId = refundEexit.transactionId
            apply.merchantRealFee = (refundEexit.merchantRealFee * 100).toFixed(0)
            apply.merchantDiscountFee = (refundEexit.merchantDiscountFee * 100).toFixed(0)
            apply.merchantPayFee = (refundEexit.merchantPayFee * 100).toFixed(0)
            apply.serviceType = refundEexit.serviceType
            this.saveLoading = true
            let result
            if (this.method === 'add') {
              result = await saveDraft(apply)
              this.$notify.success(result.message)
            } else {
              apply.sysId = this.record
              result = await editDraft(apply)
              this.$notify.success(result.message)
            }
            this.$emit('refreshData')
            this.visible = false
          } finally {
            this.saveLoading = false
          }
        }
      })
    },
    submitResult() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          if (!this.isValid) {
            this.$notify.warning("流水关联条件发生了变化，请重新关联流水信息！")
            return false
          }
          try {
            this.saveLoading = true
            let result
            if (this.method === 'add') {
              result = await saveData(this.formItem)
            } else if (this.method === 'edit') {
              this.formItem.refundEexit.tbRefundApplySysId = this.record
              result = await saveData(Object.assign({}, {refundEexit: this.formItem.refundEexit}))
            }
            this.$notify.success(result.message)
            this.$emit('refreshData')
            this.visible = false
          } finally {
            this.saveLoading = false
          }
        }
      })
    },
    audit() {
      if (this.formItem.apply.refundApplyHandleResult === 2) {
        this.$nextTick(() => {
          this.$refs.form.clearValidate(['apply.refundWayType'])
        })
      }
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          try {
            this.saveLoading = true
            this.formItem.refundEexit.tbRefundApplySysId = this.record
            this.formItem.apply.sysId = this.record
            const result = await handle(this.formItem)
            this.$notify.success(result.message)
          } finally {
            this.$emit('refreshData')
            this.visible = false
            this.saveLoading = false
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
