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
      	<el-row>
          <el-col :span="12">
            <el-form-item label="左树主表外键" prop="leftTree.treeId">
              <ec-select-tree
                v-model="formItem.leftTree.treeId"
                url="api/v1/test/testtreetable/testLeftTree/data"
                value-field="id"
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
              label="多选"
              prop="checkbox"
              :rules="[
                { required: true, message: '多选不能为空', trigger: 'blur' }
              ]"
            >
              <ec-checkbox-group v-model="formItem.checkbox" dict-type="urlTarget" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="类型"
              prop="type"
              :rules="[
                { required: true, message: '类型不能为空', trigger: 'blur' }
              ]"
            >
              <ec-select v-model="formItem.type" dict-type="payType" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="组织"
              prop="dept.id"
              :rules="[
              ]"
            >
              <ec-app-dept-select
                v-model="formItem.dept.id"
                ban-root
                value-field="id"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="测试单表Grid对象"
              prop="testSingleTb.testId"
              :rules="[
              ]"
            >
              <ec-grid-modal
                v-model="formItem.testSingleTb.testId"
                title="测试单表Grid对象"
                url="api/v1/test/testsingletbdemo1/testSingleTBDemo1/data"
                fieldKeys="name|code|validateType|inDate"
                fieldLabels="名称|编码|校验类型|加入日期"
                searchKeys="name|codename|codename|code"
                searchLabels="名称|编码名称|编码名称|编码"
                value-field="testId"
                value-column="test_id"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="负责人"
              prop="principal.id"
              :rules="[
                { required: true, message: '负责人不能为空', trigger: 'change' }
              ]"
            >
              <ec-user-modal
                v-model="formItem.principal.id"
                value-field="id"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="区域"
              prop="area.id"
              :rules="[
                { required: true, message: '区域不能为空', trigger: 'change' }
              ]"
            >
              <ec-area-select
                v-model="formItem.area.id"
                value-field="id"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="省市区"
              prop="city"
              :rules="[
                { required: true, message: '省市区不能为空', trigger: 'change' }
              ]"
            >
              <ec-city-select v-model="formItem.city" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item
              label="文件"
              prop="file"
              :rules="[
                { required: true, message: '文件不能为空', trigger: 'change' }
              ]"
            >
              <ec-file-modal v-model="formItem.file" />
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

import { findByPrimaryKey, save, update } from '@/api/modules/test/testtreetable/testRightTbApi'

export default {
  name: 'ModulesTestTesttreetableTestRightTbForm',
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
        leftTree: {
          treeId: ''
        },
        name: '',
        sex: '',
        checkbox: '',
        type: '',
        dept: {
          id: ''
        },
        testSingleTb: {
          testId: ''
        },
        principal: {
          id: ''
        },
        area: {
          id: ''
        },
        city: '',
        file: '',
        remarks: ''
      }
    }
  },
  methods: {
    init(mainId, method, record) {
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
      this.formItem.leftTree.treeId = mainId
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