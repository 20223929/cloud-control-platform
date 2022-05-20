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
              label="服务类型"
              prop="serviceType"
              :rules="[
                { required: true, message: '服务类型不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.serviceType" type="text" placeholder="服务类型"  />
            </el-form-item>
          </el-col>
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
        </el-row>
        <el-row :gutter="15">
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
              label="ETC卡号"
              prop="etcCardId"
              :rules="[
                { required: true, message: 'ETC卡号不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.etcCardId" type="text" placeholder="ETC卡号" />
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
              label="交易时间"
              prop="transTime"
              :rules="[
                { required: true, message: '交易时间不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.transTime" type="text" placeholder="交易时间" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="实收金额"
              prop="merchantRealFee"
              :rules="[
                { required: true, message: '实收金额不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.merchantRealFee" type="text" placeholder="实收金额"  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="应收金额"
              prop="merchantPayFee"
              :rules="[
                { required: true, message: '应收金额不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.merchantPayFee" type="text" placeholder="应收金额"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="清分日期"
              prop="clearDate"
              :rules="[
                { required: true, message: '清分日期不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.clearDate" type="text" placeholder="清分日期" />
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

import { findByPrimaryKey, save, update } from '@/api/modules/settlement/tbCenterSettlementDetailApi'

export default {
  name: 'ModulesSettlementTbCenterSettlementDetailForm',
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
        serviceType: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        transactionId: '',
        etcCardId: '',
        vehiclePlate: '',
        vehicleColor: '',
        transTime: '',
        merchantRealFee: '',
        merchantPayFee: '',
        clearDate: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建联网中心结算明细`
      } else if (method === 'edit') {
        this.title = '修改联网中心结算明细'
      } else if (method === 'view') {
        this.title = '查看联网中心结算明细'
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
