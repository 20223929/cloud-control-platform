<template>
  <div class="app-container">
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="40%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" label-width="140px">
        <el-divider content-position="left">联网中心信息</el-divider>
        <el-row>
          <el-col :span="24">
            <el-form-item label="一级运营商名称" prop="name" :rules="[{ required: true, message: '一级商户名称不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.name" placeholder="一级商户名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="联系人名称" prop="contact" :rules="[{ required: true, message: '联系人名称不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.contact" placeholder="联系人名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="联系方式" prop="tel" :rules="[{ required: true, message: '联系方式不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.tel" placeholder="联系方式"/>
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

import { findMerchantGroupByPrimaryKey, saveMerchantGroup } from '@/api/modules/merchant/tbMerchantApi'

export default {
  name: 'MerchantGroupForm',
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
        name: '',
        contact: '',
        tel: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())
      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新增一级商户`
      } else if (method === 'edit') {
        this.title = '修改一级商户信息'
      } else if (method === 'view') {
        this.title = '查看一级商户信息'
      }
      this.visible = true
      this.loading = true
      await this.$nextTick()
      this.$refs.form.clearValidate()

      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const { data } = await findMerchantGroupByPrimaryKey(record.id)
        this.formItem = Object.assign({}, this.formItem, data)
      }
      this.loading = false
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            await saveMerchantGroup(this.formItem)
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

<style scoped>

</style>
