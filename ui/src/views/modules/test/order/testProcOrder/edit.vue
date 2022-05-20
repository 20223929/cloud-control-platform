<template>
  <div class="app-container">
    <div class="btn-group">
      <div class="pull-right">
        <el-button type="primary" @click="save">保存</el-button>
        <el-button type="default" @click="returnBack">关闭</el-button>
      </div>
    </div>
    <detail :id="id" ref="detail" />
  </div>
</template>
<script>
import Detail from './components/detail'
import { update } from '@/api/modules/test/order/testProcOrderApi'
export default {
  name: 'ModulesTestOrderTestProcOrderEdit',
  components: { Detail },
  data() {
    return {
      id: this.$route.query.businessId
    }
  },
  watch: {
    $route() {
      if (this.$route.query.businessId) {
        this.id = this.$route.query.businessId
      }
    }
  },
  methods: {
    async save() {
      const valid = this.$refs.detail.formValid()
      if (!valid) {
        return
      }
      const formItem = this.$refs.detail.getFormData()
      await update(this.id, formItem)
      this.$notify.success('保存成功')
      this.returnBack()
    },
    returnBack() {
      this.$store.dispatch('tagsView/delView', this.$route).then(({ visitedViews }) => {
        this.$router.push(`/testProcOrderView`)
      })
    }
  }
}
</script>
<style>
</style>
