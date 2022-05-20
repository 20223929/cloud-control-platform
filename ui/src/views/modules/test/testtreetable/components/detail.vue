<template>
  <div class="app-container">
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="60%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" label-width="120px" label-suffix=" : ">
        <el-row>
          <el-col :span="12">
            <el-form-item label="父级" prop="parentId">
              <ec-select-tree
                v-model="formItem.parentId"
                title="选择父级"
                url="api/v1/test/testtreetable/testLeftTree/data"
                :params="reqParams"
                value-field="treeId"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="名称"
              prop="name"
              :rules="[
                { required: true, message: '名称不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.name" type="text" placeholder="名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="编码"
              prop="code"
              :rules="[
                { required: true, message: '编码不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.code" type="text" placeholder="编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="排序编号"
              prop="sort"
              :rules="[
              ]"
            >
              <el-input-number v-model="formItem.sort" type="text" placeholder="排序编号"  />
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

import { findByPrimaryKey, save, update } from '@/api/modules/test/testtreetable/testLeftTreeApi'

export default {
  name: 'ModulesTestTesttreetableTestLeftTreeForm',
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
        parentId: '',
        name: '',
        code: '',
        sort: ''
      }
    }
  },
  computed: {
    reqParams() {
      return { excludeId: this.record.treeId }
    }
  },
  methods: {
    async init(method, record, parent) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建左树右表(左树主表)示例`
      } else if (method === 'edit') {
        this.title = '修改左树右表(左树主表)示例'
      } else if (method === 'view') {
        this.title = '查看左树右表(左树主表)示例'
      }
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.$refs.form.clearValidate()

      if (parent && parent.treeId) {
        this.formItem.parentId = parent.treeId
      }
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const { data } = await findByPrimaryKey(record.treeId)
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
              await update(this.record.treeId, this.formItem)
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

<style scoped>

</style>