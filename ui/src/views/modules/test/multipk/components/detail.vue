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
              label="名称"
              prop="name"
              :rules="[
                { required: true, message: '名称不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.name" type="text" placeholder="名称" />
            </el-form-item>
          </el-col>
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
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="性别"
              prop="sex"
              :rules="[
                { required: true, message: '性别不能为空', trigger: 'blur' }
              ]"
            >
              <ec-radio-group v-model="formItem.sex" dict-type="sex" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="生日"
              prop="birthday"
              :rules="[
                { required: true, message: '生日不能为空', trigger: 'blur' }
              ]"
            >
              <ec-date v-model="formItem.birthday" type="datetime" format="yyyy-MM-dd HH:mm:ss" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="手机号"
              prop="phoneNum"
              :rules="[
                { required: true, message: '手机号不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.phoneNum" type="text" placeholder="手机号" />
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

import { findByPrimaryKey, save, update } from '@/api/modules/test/multipk/multiPkSingleTableApi'

export default {
  name: 'ModulesTestMultipkMultiPkSingleTableForm',
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
        code: '',
        sex: '',
        birthday: '',
        phoneNum: ''
      }
    }
  },
  methods: {
    init(method, record) {
      Object.assign(this.$data, this.$options.data())
      
      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建联合主键非ID测试`
      } else if (method === 'edit') {
        this.title = '修改联合主键非ID测试'
      } else if (method === 'view') {
        this.title = '查看联合主键非ID测试'
      }
      this.visible = true
      this.loading = false
      this.$nextTick(async() => {
        this.$refs.form.clearValidate()
        if (method === 'edit' || method === 'view') { // 修改或者查看
          this.loading = true
          const { data } = await findByPrimaryKey(record.idOne, record.idTwo, record.idThree)
          this.formItem = Object.assign({}, this.formItem, data)
          this.loading = false
        }
      })
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            if (this.method === 'edit') {
              await update(this.record.idOne, this.record.idTwo, this.record.idThree, this.formItem)
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