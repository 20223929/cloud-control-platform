<template>
  <div class="app-container">
    <el-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
    >
      <detail :id="record && record.id" ref="orderDetail" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="save">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import mixin from 'ecip-web/utils/common-mixin'
import { save, update } from '@/api/modules/test/order/testProcOrderApi'
import Detail from './detail'
export default {
  name: 'ModulesTestOrderTestProcOrderForm',
  components: { Detail },
  mixins: [...mixin],
  data() {
    return {
      visible: false,
      method: '',
      title: '',
      record: {},
      saveLoading: false //  保存加载
    }
  },
  methods: {
    init(method, row) {
      this.method = method
      this.record = row
      if (method === 'add') {
        this.title = `新建订单管理测试`
      } else if (method === 'edit') {
        this.title = `修改${row.name}`
      } else if (method === 'view') {
        this.title = `查看${row.name}`
      }
      this.visible = true
    },
    async save() {
      const valid = this.$refs.orderDetail.formValid()
      if (!valid) {
        return
      }
      const formItem = this.$refs.orderDetail.getFormData()
      this.saveLoading = true
      if (this.method === 'edit') {
        await update(this.record.id, formItem)
      } else {
        await save(formItem)
      }
      this.$notify.success('保存成功')
      this.$emit('refreshData')
      this.visible = false
      this.saveLoading = false
    }
  }
}
</script>

<style scoped>

</style>
