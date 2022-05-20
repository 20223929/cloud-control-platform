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
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="save">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import { findByPrimaryKey, save, update } from '@/api/modules/bankbillcheck/tbCenterBillCheckDetailApi'

export default {
  name: 'ModulesBankbillcheckTbCenterBillCheckDetailForm',
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
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建联网中心对账核对明细`
      } else if (method === 'edit') {
        this.title = '修改联网中心对账核对明细'
      } else if (method === 'view') {
        this.title = '查看联网中心对账核对明细'
      }
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const { data } = await findByPrimaryKey(record.transDate, record.merchantGroupId, record.merchantId, record.siteId, record.equipmentId)
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
              await update(this.record.transDate, this.record.merchantGroupId, this.record.merchantId, this.record.siteId, this.record.equipmentId, this.formItem)
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
