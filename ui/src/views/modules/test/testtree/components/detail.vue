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
                url="api/v1/test/testtree/testTreeTB/data"
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
              label="加入日期"
              prop="inDate"
              :rules="[
                { required: true, message: '加入日期不能为空', trigger: 'blur' }
              ]"
            >
              <ec-date v-model="formItem.inDate" type="datetime" format="yyyy-MM-dd HH:mm:ss" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="验证类型"
              prop="radioTest"
              :rules="[
              ]"
            >
              <ec-radio-group v-model="formItem.radioTest" dict-type="enableState" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="checkbox测试"
              prop="checkboxTest"
              :rules="[
              ]"
            >
              <ec-checkbox-group v-model="formItem.checkboxTest" dict-type="typeNode" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="下拉选择测试"
              prop="selectTest"
              :rules="[
              ]"
            >
              <ec-select v-model="formItem.selectTest" dict-type="vehicleType" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="用户选择测试"
              prop="userTest.id"
              :rules="[
              ]"
            >
              <ec-user-modal
                v-model="formItem.userTest.id"
                value-field="id"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="组织选择测试"
              prop="deptTest.id"
              :rules="[
              ]"
            >
              <ec-app-dept-select
                v-model="formItem.deptTest.id"
                ban-root
                value-field="id"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item
              label="文件上传测试"
              prop="fileTest"
              :rules="[
              ]"
            >
              <ec-file-modal v-model="formItem.fileTest" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="grid选择测试"
              prop="gridTest.testId"
              :rules="[
              ]"
            >
              <ec-grid-modal
                v-model="formItem.gridTest.testId"
                title="grid选择测试"
                url="api/v1/test/testsingletbdemo1/testSingleTBDemo1/data"
                fieldKeys="testId|name|code|validateType"
                fieldLabels="主键|名称|编码|校验类型"
                searchKeys="name|code"
                searchLabels="名称|编码"
                value-field="testId"
                value-column="test_id"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="区域选择测试"
              prop="areaTest.id"
              :rules="[
              ]"
            >
              <ec-area-select
                v-model="formItem.areaTest.id"
                value-field="id"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="省市区三级联动测试"
              prop="cityTest"
              :rules="[
              ]"
            >
              <ec-city-select v-model="formItem.cityTest" />
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

import { findByPrimaryKey, save, update } from '@/api/modules/test/testtree/testTreeTBApi'

export default {
  name: 'ModulesTestTesttreeTestTreeTBForm',
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
        inDate: '',
        radioTest: '',
        checkboxTest: '',
        selectTest: '',
        userTest: {
          id: ''
        },
        deptTest: {
          id: ''
        },
        fileTest: '',
        gridTest: {
          testId: ''
        },
        areaTest: {
          id: ''
        },
        cityTest: '',
        sort: '',
        remarks: ''
      }
    }
  },
  computed: {
    reqParams() {
      const params = { excludeId: this.record.treeId }
      return params
    }
  },
  methods: {
    async init(method, record, parent) {
      Object.assign(this.$data, this.$options.data())
      
      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建树示例`
      } else if (method === 'edit') {
        this.title = '修改树示例'
      } else if (method === 'view') {
        this.title = '查看树示例'
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