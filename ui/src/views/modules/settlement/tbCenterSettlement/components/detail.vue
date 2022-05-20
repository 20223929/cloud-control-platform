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
              label="对账日"
              prop="settlementDay"
              :rules="[
                { required: true, message: '对账日不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.settlementDay" type="text" placeholder="对账日" />
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
              <el-input v-model="formItem.serviceType" type="text" placeholder="服务类型" />
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
              label="应付商户金额(单位元)"
              prop="realTotalAmount"
              :rules="[
                { required: true, message: '应付商户金额(单位元)不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.realTotalAmount" type="text" placeholder="应付商户金额(单位元)"  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="清分服务费(单位元）"
              prop="clearServiceTotalFee"
              :rules="[
                { required: true, message: '清分服务费(单位元）不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.clearServiceTotalFee" type="text" placeholder="清分服务费(单位元）"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="确认状态"
              prop="confirmState"
              :rules="[
                { required: true, message: '确认状态不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.confirmState" type="text" placeholder="确认状态"  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="确认人"
              prop="confirmMan"
              :rules="[
                { required: true, message: '确认人不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.confirmMan" type="text" placeholder="确认人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="确认时间"
              prop="confirmTime"
              :rules="[
                { required: true, message: '确认时间不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.confirmTime" type="text" placeholder="确认时间" />
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

import { findByPrimaryKey, save, update } from '@/api/modules/settlement/tbCenterSettlementApi'

export default {
  name: 'ModulesSettlementTbCenterSettlementForm',
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
        settlementDay: '',
        serviceType: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: '',
        realTotalAmount: '',
        clearServiceTotalFee: '',
        confirmState: '',
        confirmMan: '',
        confirmTime: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建联网中心结算`
      } else if (method === 'edit') {
        this.title = '修改联网中心结算'
      } else if (method === 'view') {
        this.title = '查看联网中心结算'
      }
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const { data } = await findByPrimaryKey(record.settlementDay, record.merchantGroupId, record.merchantId, record.siteId)
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
              await update(this.record.settlementDay, this.record.merchantGroupId, this.record.merchantId, this.record.siteId, this.formItem)
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
