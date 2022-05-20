<template>
  <div class="app-container">
    <el-dialog
      :title="title"
      :close-on-click-modal="false"
      width="80%"
      :visible.sync="visible"
    >
      <detail-form ref="detailForm" :method="method" @refreshData="$emit('refreshData')" />

      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="save">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import DetailForm from './detailForm'

export default {
  name: 'ModulesSystemSysrUserAloneForm',
  components: { DetailForm },
  data() {
    return {
      visible: false,
      saveLoading: false,
      title: '',
      method: ''
    }
  },
  methods: {
    async init(method, user) {
      this.method = method
      if (method === 'add') {
        this.title = this.$t('ec.table.add')
      } else if (method === 'edit') {
        this.title = this.$t('ec.table.edit')
      } else if (method === 'view') {
        this.title = this.$t('ec.table.view')
      }
      this.visible = true
      this.loading = false
      this.$nextTick(async() => {
        this.$refs.detailForm.init(method, (user && user.id) || '')
      })
    },
    async save() {
      const result = await this.$refs.detailForm.save()
      if (result) {
        this.visible = false
      }
      this.saveLoading = false
    }
  }
}
</script>

<style scoped>

</style>
