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
                  url="api/v1/test/customtree/testCategory/data"
                  :params="reqParams"
                  value-field="id"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="类型名"
                prop="name"
                :rules="[
                    { required: true, message: '类型名不能为空', trigger: 'blur' }
                ]"
              >
                <el-input v-model="formItem.name" type="text" placeholder="类型名" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="15">
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

  import { findByPrimaryKey, save, update } from '@/api/modules/test/customtree/testCategoryApi'

  export default {
    name: 'ModulesTestCustomtreeTestCategoryForm',
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
          sort: '',
          remarks: ''
        }
      }
    },
    computed: {
      reqParams() {
        const params = { excludeId: this.record.id }
        return params
      }
    },
    methods: {
      async init(method, record, parent) {
        Object.assign(this.$data, this.$options.data())
        
        this.method = method
        this.record = record
        if (method === 'add') {
          this.title = `新建商品类型`
        } else if (method === 'edit') {
          this.title = '修改商品类型'
        } else if (method === 'view') {
          this.title = '查看商品类型'
        }
        this.visible = true
        this.loading = false
        await this.$nextTick()
        this.$refs.form.clearValidate()

        if (parent && parent.id) {
          this.formItem.parentId = parent.id
        }
        if (method === 'edit' || method === 'view') { // 修改或者查看
          this.loading = true
          const { data } = await findByPrimaryKey(record.id)
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

  <style scoped>

  </style>