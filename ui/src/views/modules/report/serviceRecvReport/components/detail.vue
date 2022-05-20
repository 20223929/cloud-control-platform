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
              label="交易日期"
              prop="trxDate"
              :rules="[
                { required: true, message: '交易日期不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.trxDate" type="text" placeholder="交易日期" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="通行费结算日期"
              prop="tollSettlementDate"
              :rules="[
                { required: true, message: '通行费结算日期不能为空', trigger: 'blur' }
              ]"
            >
              <ec-date v-model="formItem.tollSettlementDate" type="datetime" format="yyyy-MM-dd" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="运营方编码"
              prop="merchantId"
              :rules="[
                { required: true, message: '运营方编码不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.merchantId" type="text" placeholder="运营方编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="服务方编码"
              prop="siteId"
              :rules="[
                { required: true, message: '服务方编码不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.siteId" type="text" placeholder="服务方编码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="通行费结算周期，1-T+1；2-T+4；3-月结"
              prop="tollSettlementInterval"
              :rules="[
              ]"
            >
              <el-input-number v-model="formItem.tollSettlementInterval" type="text" placeholder="通行费结算周期，1-T+1；2-T+4；3-月结"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="服务费，actual_settlement_amt * service_rate，单位分"
              prop="serviceAmt"
              :rules="[
                { required: true, message: '服务费，actual_settlement_amt * service_rate，单位分不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.serviceAmt" type="text" placeholder="服务费，actual_settlement_amt * service_rate，单位分"  />
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

import { findByPrimaryKey, save, update } from '@/api/modules/report/serviceRecvReportApi'

export default {
  name: 'ModulesReportServiceRecvReportForm',
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
        trxDate: '',
        tollSettlementDate: '',
        merchantId: '',
        siteId: '',
        tollSettlementInterval: '',
        serviceAmt: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建服务方应收报表`
      } else if (method === 'edit') {
        this.title = '修改服务方应收报表'
      } else if (method === 'view') {
        this.title = '查看服务方应收报表'
      }
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const { data } = await findByPrimaryKey(record.sysId)
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
