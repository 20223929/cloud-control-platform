<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="60%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" label-width="120px" label-suffix=" : " >
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="退费日期"
              prop="applyTime"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.applyTime" type="text" placeholder="退费日期" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="拓展方"
              prop="merchantGroupIdName"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.merchantGroupIdName" type="text" placeholder="拓展方" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="运营方"
              prop="merchantIdName"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.merchantIdName" type="text" placeholder="运营方" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="场景"
              prop="siteIdName"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.siteIdName" type="text" placeholder="场景" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="退费类型"
              prop="refundTypeName"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.refundTypeName" type="text" placeholder="退费类型" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="服务类型"
              prop="serviceTypeName"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.serviceTypeName" type="text" placeholder="服务类型" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="退费状态"
              prop="refundStateName"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.refundStateName" type="text" placeholder="退费状态" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="退费流水号"
              prop="refundTransId"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.refundTransId" type="text" placeholder="退费流水号" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="交易流水号"
              prop="transactionId"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.transactionId" type="text" placeholder="交易流水号" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="原交易时间"
              prop="transTime"
              :rules="[
              ]"
            >
              <ec-date v-model="formItem.transTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="优惠金额"
              prop="merchantDiscountFee"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.merchantDiscountFee" type="text" placeholder="优惠金额" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="退费订单号"
              prop="refundOrderId"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.refundOrderId" type="text" placeholder="退费订单号" readonly />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="退费金额"
              prop="refundFee"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.refundFee" type="text" placeholder="退费金额" readonly />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
<!--        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="save">确定</el-button>-->
      </span>
    </el-dialog>
  </div>
</template>

<script>

import { findByPrimaryKey, save, update, info } from '@/api/modules/querymanage/tbOnlineRefundEexitApi'

export default {
  name: 'ModulesQuerymanageTbOnlineRefundEexitForm',
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
        applyTime: '',
        merchantGroupIdName: '',
        merchantIdName: '',
        siteIdName: '',
        refundType: '',
        refundTypeName: '',
        serviceTypeName: '',
        refundTransId: '',
        transactionId: '',
        transTime: '',
        merchantDiscountFee: '',
        refundOrderId: '',
        refundFee: '',
        refundStateName: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建银行退费流水`
      } else if (method === 'edit') {
        this.title = '修改银行退费流水'
      } else if (method === 'view') {
        this.title = '查看银行退费流水'
      } else if (method === 'info') {
        this.title = '查看银行退费流水'
      }
      this.visible = true
      this.loading = true
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'edit' || method === 'view' || method === 'info') { // 修改或者查看
        this.loading = true
        const { data } = await info(record.tbRefundApplySysId)
        this.formItem = Object.assign({}, this.formItem, data)
      }
      this.loading = false
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            if (this.method === 'edit') {
              await update(this.record.sysId, this.formItem)
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
