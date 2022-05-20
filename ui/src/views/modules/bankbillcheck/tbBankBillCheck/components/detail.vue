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
              label="备注"
              prop="remark"
              :rules="[
                { required: true, message: '备注不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.remark" type="text" placeholder="备注" />
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

import { findByPrimaryKey, save, update,batchConfirm } from '@/api/modules/bankbillcheck/tbBankBillCheckApi'

export default {
  name: 'ModulesBankbillcheckTbBankBillCheckForm',
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
        confirmState: '',
        remark: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建银行对账核对表`
      } else if (method === 'edit') {
        this.title = '银行对账核对'
      } else if (method === 'view') {
        this.title = '查看银行对账核对表'
      } else if(method === 'batchConfirm'){
        this.title = '批量确认'
      }
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.$refs.form.clearValidate()
      this.record = record
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const { data } = await findByPrimaryKey(record.transDate, record.merchantGroupId, record.merchantId, record.siteId)
        this.formItem = Object.assign({}, this.formItem, data)
        this.loading = false
      }
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            if(this.method === 'batchConfirm'){
              this.record.forEach(c=>{c.remark = this.formItem.remark})
              await batchConfirm(this.record)
            }
            else if (this.method === 'edit') {
              await update(this.record.transDate, this.record.merchantGroupId, this.record.merchantId, this.record.siteId, this.formItem)
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
