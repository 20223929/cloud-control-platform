<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="60%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" label-width="120px" label-suffix=" : ">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="流水类型"
              prop="eexitType"
              :rules="[
                { required: true, message: '流水类型不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.eexitType" type="text" placeholder="流水类型" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="银行交易日期"
              prop="bankTransDate"
              :rules="[
                { required: true, message: '银行交易日期不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.bankTransDate" type="text" placeholder="银行交易日期" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="流水号"
              prop="transactionId"
              :rules="[
                { required: true, message: '流水号不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.transactionId" type="text" placeholder="流水号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="银行交易检索号"
              prop="deductionOrderId"
              :rules="[
                { required: true, message: '银行交易检索号不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.deductionOrderId" type="text" placeholder="银行交易检索号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="车牌号码"
              prop="vehiclePlate"
              :rules="[
                { required: true, message: '车牌号码不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.vehiclePlate" type="text" placeholder="车牌号码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="车牌颜色"
              prop="vehicleColor"
              :rules="[
                { required: true, message: '车牌颜色不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.vehicleColor" type="text" placeholder="车牌颜色"  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="拓展方"
              prop="merchantGroupId"
              :rules="[
                { required: true, message: '拓展方不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.merchantGroupId" type="text" placeholder="拓展方" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="运营方"
              prop="merchantId"
              :rules="[
                { required: true, message: '运营方不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.merchantId" type="text" placeholder="运营方" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="场景"
              prop="siteId"
              :rules="[
                { required: true, message: '场景不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.siteId" type="text" placeholder="场景" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="服务类型"
              prop="serviceType"
              :rules="[
                { required: true, message: '服务类型不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.serviceType" type="text" placeholder="服务类型"  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="客户实付金额"
              prop="merchantRealFee"
              :rules="[
                { required: true, message: '客户实付金额不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.merchantRealFee" type="text" placeholder="客户实付金额"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="优惠金额"
              prop="merchantDiscountFee"
              :rules="[
                { required: true, message: '优惠金额不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.merchantDiscountFee" type="text" placeholder="优惠金额"  />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="save">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import { findByPrimaryKey, save, update } from '@/api/modules/settlement/tbBankSettlementDetailApi'

export default {
  name: 'ModulesSettlementTbBankSettlementDetailForm',
  components: {},
  data() {
    return {
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      record: {},
      formItem: {
        eexitType: '',
        bankTransDate: '',
        transactionId: '',
        deductionOrderId: '',
        vehiclePlate: '',
        vehicleColor: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        serviceType: '',
        merchantRealFee: '',
        merchantDiscountFee: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建银行预结算明细`
      } else if (method === 'edit') {
        this.title = '修改银行预结算明细'
      } else if (method === 'view') {
        this.title = '查看银行预结算明细'
      }
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const { data } = await findByPrimaryKey(record.transactionId)
        this.formItem = Object.assign({}, this.formItem, data)
        this.loading = false
      }
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            if (this.method === 'edit') {
              await update(this.record.transactionId, this.formItem)
            } else {
              await save(this.formItem)
            }
            this.$notify.success('保存成功')
            this.$emit('refreshData')
            this.visible = false
          } finally {
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
