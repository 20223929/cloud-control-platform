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
              label="商品名称"
              prop="name"
              :rules="[
                { required: true, message: '商品名称不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.name" type="text" placeholder="商品名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="所属类型"
              prop="category.id"
              :rules="[
                { required: true, message: '所属类型不能为空', trigger: 'blur' }
              ]"
            >
              <ec-select-tree
                v-model="formItem.category.id"
                title="所属类型选择"
                url="api/v1/testCategory"
                value-field="id"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="价格"
              prop="price"
              :rules="[
                { required: true, message: '价格不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.price" type="text" placeholder="价格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="备注信息"
              prop="remarks"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.remarks" type="textarea" :autosize="{minRows: 1, maxRows: 5}" placeholder="请填写备注信息"     />
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

import { findByPrimaryKey, save, update } from '@/api/modules/test/customtree/testGoodsApi'

export default {
  name: 'ModulesTestCustomtreeTestGoodsForm',
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
        category: {
          id: ''
        },
        price: '',
        remarks: ''
      }
    }
  },
  methods: {
    init(method, record) {
      Object.assign(this.$data, this.$options.data())
      
      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建商品`
      } else if (method === 'edit') {
        this.title = '修改商品'
      } else if (method === 'view') {
        this.title = '查看商品'
      }
      this.visible = true
      this.loading = false
      this.$nextTick(async() => {
        this.$refs.form.clearValidate()
        if (method === 'edit' || method === 'view') { // 修改或者查看
          this.loading = true
          const { data } = await findByPrimaryKey(record.id)
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
              await update(this.record.id, this.formItem)
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