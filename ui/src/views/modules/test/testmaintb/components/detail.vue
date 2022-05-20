<template>
  <div class="app-container">
    <el-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="60%"
    >
      <el-form ref="form" v-loading="loading" :model="formItem" label-width="120px" label-suffix=" : ">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="名称"
              prop="name"
              :rules="[
                { required: true, message: '名称不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.name" type="text" placeholder="名称"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="归属部门"
              prop="office.id"
              :rules="[
              ]"
            >
              <ec-dept-select
                v-model="formItem.office.id"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="负责人"
              prop="principle.id"
              :rules="[
                { required: true, message: '负责人不能为空', trigger: 'change' }
              ]"
            >
              <ec-user-modal
                v-model="formItem.principle.id"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="办公区域"
              prop="officeArea.id"
              :rules="[
                { required: true, message: '办公区域不能为空', trigger: 'change' }
              ]"
            >
              <ec-area-select
                v-model="formItem.officeArea.id"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="办公城市"
              prop="officeCity"
              :rules="[
                { required: true, message: '办公城市不能为空', trigger: 'change' }
              ]"
            >
              <ec-city-select v-model="formItem.officeCity" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="单表示例"
              prop="singleDemo.testId"
              :rules="[
                { required: true, message: '单表示例不能为空', trigger: 'change' }
              ]"
            >
              <ec-grid-modal
                v-model="formItem.singleDemo.testId"
                title="单表示例"
                url="api/v1/test/testsingletbdemo1/testSingleTBDemo1/data"
                fieldKeys="testId|name|code|inDate"
                fieldLabels="主键|名称|编码|加入日期"
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
              label="性别(radio测试)"
              prop="sex"
              :rules="[
                { required: true, message: '性别(radio测试)不能为空', trigger: 'change' }
              ]"
            >
              <ec-radio-group v-model="formItem.sex" dict-type="sex" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="车种(select测试)"
              prop="vehicleClass"
              :rules="[
                { required: true, message: '车种(select测试)不能为空', trigger: 'change' }
              ]"
            >
              <ec-select v-model="formItem.vehicleClass" dict-type="vehicleClass" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="认证方式(checkbox测试)"
              prop="authType"
              :rules="[
                { required: true, message: '认证方式(checkbox测试)不能为空', trigger: 'change' }
              ]"
            >
              <ec-checkbox-group v-model="formItem.authType" dict-type="authType" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="加入日期"
              prop="inDate"
              :rules="[
                { required: true, message: '加入日期不能为空', trigger: 'change' }
              ]"
            >
              <el-input v-model="formItem.inDate" type="text" placeholder="加入日期"  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="排序号"
              prop="sort"
              :rules="[
                { required: true, message: '排序号不能为空', trigger: 'change' }
              ]"
            >
              <el-input-number v-model="formItem.sort" type="text" placeholder="排序号"  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item
              label="附件"
              prop="file"
              :rules="[
                { required: true, message: '附件不能为空', trigger: 'change' }
              ]"
            >
              <ec-file-modal v-model="formItem.file" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-tabs>
        <el-tab-pane label="附表测试">
          <vxe-grid
            ref="vxeTestAttachedTB"
            max-height="800"
            size="mini"
            highlight-hover-row
            border
            sync-resize
            auto-resize
            resizable
            show-overflow
            align="center"
            :loading="loading"
            :toolbar="tableToolbar_0"
            :columns="testAttachedTBColumns"
            :data="formItem.testAttachedTBList"
            :edit-config="{trigger: 'click', mode: 'row', showStatus: true, icon: 'fa fa-file-text-o'}"
            :edit-rules="testAttachedTBRules"
          >
            <template v-slot:toolbar_btns_0>
              <el-button size="mini" type="primary" @click="addNewRow('TestAttachedTB')">新增</el-button>
            </template>



            <template v-slot:edit_stationType_0="{ row }">
              <ec-select v-model="row.stationType" dict-type="deviceLocationType" />
            </template>

            <template v-slot:edit_inDate_0="{ row }">
              <ec-date v-model="row.inDate" format="yyyy-MM-dd HH:mm:ss" />
            </template>
            <template v-slot:default_location_0="{ row }">
              <ec-city-select :value="row.location" readonly />
            </template>

            <template v-slot:edit_location_0="{ row }">
              <ec-city-select v-model="row.location" />
            </template>
            <template v-slot:operation_0="{row, rowIndex}">
              <el-button type="danger" size="mini" :disabled="isView" @click="deleteTestAttachedTB(row.attachedId, rowIndex)">删除</el-button>
            </template>   
          </vxe-grid>
        </el-tab-pane>
      </el-tabs>

      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="save">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getById, update, save } from '@/api/modules/test/testmaintb/testMainTBApi'

export default {
  name: 'ModulesTestTestmaintbTestMainTBForm',
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
        office: {
          id: ''
        },
        principle: {
          id: ''
        },
        officeArea: {
          id: ''
        },
        officeCity: '',
        singleDemo: {
          testId: ''
        },
        sex: '',
        vehicleClass: '',
        authType: '',
        inDate: '',
        sort: '',
        file: '',
        testAttachedTBList: []
      },
      tableToolbar_0: {
        custom: true,
        slots: {
          buttons: 'toolbar_btns_0'
        }
      },
      testAttachedTBColumns: [
        { title: '序号', type: 'index', width: 80 },
        { title: '名称', field: 'name', editRender: { name: 'input' }, minWidth: 180 },
        { title: '站点类型', field: 'stationType', editRender: { type: 'default' }, minWidth: 180, slots: { edit: 'edit_stationType_0' }, formatter: ['dict', 'deviceLocationType'] },
        { title: '加入日期', field: 'inDate', editRender: { type: 'default' }, minWidth: 180, slots: { edit: 'edit_inDate_0' }, formatter: ['date', 'yyyy-MM-dd HH:mm:ss'] },
        { title: '位置', field: 'location', editRender: { type: 'default' }, minWidth: 180, slots: { edit: 'edit_location_0' }},
        { title: '操作', width: 80, fixed: 'right', align: 'center', slots: { default: 'operation_0' }}
      ],
      testAttachedTBRules: {
        'name': [
          {required: true, message: '名称不能为空', trigger: 'blur'}
        ],
        'stationType': [
          {required: true, message: '站点类型不能为空', trigger: 'blur'}
        ],
        'inDate': [
          {required: true, message: '加入日期不能为空', trigger: 'blur'}
        ],
        'location': [
          {required: true, message: '位置不能为空', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data.formItem, this.$options.data().formItem)

      this.method = method
      if (method === 'add') {
        this.title = `新增主表示例(全组件)`
      } else if (method === 'edit') {
        this.title = '修改主表示例(全组件)'
      } else if (method === 'view') {
        this.title = '查看主表示例(全组件)'
      }
      this.visible = true
      this.loading = false
      this.record = record
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const { data } = await getById(record.mainId)
        this.formItem = Object.assign({}, this.formItem, data)
        this.loading = false
      }
    },
    createTestAttachedTBObj() {
      return {
        name: '',
        stationType: '',
        inDate: '',
        location: ''
      }
    },
    addNewRow(type) {
      if (type === 'TestAttachedTB') {
        if (!this.formItem.testAttachedTBList) {
          this.$set(this.formItem, 'testAttachedTBList', [])
        }
        this.formItem.testAttachedTBList.push(this.createTestAttachedTBObj())
      }
    },
    deleteTestAttachedTB(rowId, rowIndex) {
      if (!this.formItem.testAttachedTBList || this.formItem.testAttachedTBList.length === 0) {
        return
      }
      if (rowId) {
        this.formItem.testAttachedTBDeleteIds.push(rowId)
      }
      this.formItem.testAttachedTBList.splice(rowIndex, 1)
    },
    async formEditedValid() { //  表单校验
      let validFlag = false
      await this.$refs.form.validate((valid) => {
        validFlag = valid
        if (!valid) {
          this.$notify.error('表单校验失败！')
        }
      })
      return validFlag
    },
    async gridTbEditedValid() { //  vxe-grid校验
      const h = this.$createElement
      let validFlag = true

      //  获取已经编辑过的行记录
      const editedTestAttachedTBRecords = this.formItem.testAttachedTBList.filter(item => item.edited)

      if (editedTestAttachedTBRecords.length > 0) {
        await this.$refs.vxeTestAttachedTB.fullValidate(editedTestAttachedTBRecords, (errorMap) => {
          if (errorMap) {
            validFlag = false
            const msgList = []
            Object.values(errorMap).forEach(errList => {
              errList.forEach(params => {
                const { row, column, rules, rowIndex } = params
                rules.forEach(rule => {
                  msgList.push(`附表测试 第 ${rowIndex + 1} 行 ${column.title} 校验错误：${rule.message}`)
                })
              })
            })
            this.$message({
              type: 'error',
              message: h('div', null, [
                msgList.map(msg => { return h('p', { style: 'color: red' }, msg) })
              ])
            })
          }
        })
      }
      return validFlag
    },
    async save() {
      const valid = await this.formEditedValid() && await this.gridTbEditedValid()
      if (valid) {
        try {
          this.saveLoading = true
          if (this.method === 'edit') {
            await update(this.record.mainId, this.formItem)
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
    }
  }
}
</script>
<style scoped>
  .form-text-content {
    margin-top: 15px;
  }
</style>