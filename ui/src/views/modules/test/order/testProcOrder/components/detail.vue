<template>
  <div>
    <el-form ref="dialogForm" v-loading="loading" :model="formItem" label-width="120px">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item
            label="订单号"
            prop="code"
            :rules="[
            { required: true, message:'订单号不能为空', trigger:'change' }
          ]"
          >
            <el-input v-model="formItem.code" type="text" placeholder="订单号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="订单名称"
            prop="name"
            :rules="[
            { required: true, message:'订单名称不能为空', trigger:'change' }
          ]"
          >
            <el-input v-model="formItem.name" type="text" placeholder="订单名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="订单类型"
            prop="type"
            :rules="[
            { required: true, message:'订单类型不能为空', trigger:'change' }
          ]"
          >
            <el-input v-model="formItem.type" type="text" placeholder="订单类型" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="价格"
            prop="price"
            :rules="[
            { required: true, message:'价格不能为空', trigger:'change' }
          ]"
          >
            <el-input v-model="formItem.price" type="text" placeholder="价格" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <flow-info :act="formItem.act"></flow-info>
    <audit-form v-if="isAudit" @submit="audit"></audit-form>
  </div>

</template>

<script>

import mixin from 'ecip-web/utils/common-mixin'
import { getById, submit } from '@/api/modules/test/order/testProcOrderApi'
import FlowInfo from 'ecip-bpm/components/flow/flowInfo'
import AuditForm from 'ecip-bpm/components/flow/auditForm'

export default {
  name: 'ModulesTestOrderTestProcOrderForm',
  components: { FlowInfo, AuditForm },
  mixins: [...mixin],
  props: {
    id: {
      type: String,
      required: false
    }
  },
  data() {
    return {
      isAudit: this.$route.query.isAudit,

      loading: false, // 页面加载遮罩
      formItem: {
        act: {
          procDefId: this.$route.query.procDefId
        },
        code: '',
        name: '',
        type: '',
        price: ''
      }
    }
  },
  watch: {
    id: {
      handler(val) {
        this.getById(val)
      },
      immediate: true
    }
  },
  methods: {
    audit(data) {
      submit(this.formItem.id, data).then(res => {
        this.$notify({
          title: '提示',
          message: res.message || '办理成功',
          type: 'success'
        })
        this.$store.dispatch('tagsView/delView', this.$route).then(({ visitedViews }) => {
          this.$router.push({
            path: `/todoList`
          })
        })
      })
    },
    async getById(id) {
      if (!id) {
        this.$nextTick(() => {
          this.$refs.dialogForm.resetFields()
        })
        return
      }
      this.loading = true
      const { data } = await getById(id)
      Object.assign(this.formItem, data)
      this.loading = false
    },
    async formValid() {
      let validFlag = false
      await this.$refs.dialogForm.validate(valid => {
        if (valid) {
          validFlag = valid
        }
      })
      return validFlag
    },
    getFormData() {
      return this.formItem
    }
  }
}
</script>

<style scoped>

</style>
