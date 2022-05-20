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
              label="报警时间"
              prop="alarmTime"
              :rules="[
                { required: true, message: '报警时间不能为空', trigger: 'blur' }
              ]"
            >
              <ec-date v-model="formItem.alarmTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="报警类型"
              prop="alarmType"
              :rules="[
                { required: true, message: '报警类型，数据字典配置，1-黑名单异常、2-对账异常、3-收费异常、4-服务器异常、5-网络异常、6-应用异常、7-车道监控异常不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.alarmType" type="text" placeholder="报警类型" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="报警级别"
              prop="alarmLevel"
              :rules="[
                { required: true, message: '报警级别，1-一般、2-严重不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.alarmLevel" type="text" placeholder="报警级别"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="报警信息"
              prop="alarmInfo"
              :rules="[
                { required: true, message: '报警信息不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.alarmInfo" type="text" placeholder="报警信息" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="处理状态"
              prop="dealState"
              :rules="[
                { required: true, message: '处理状态，0-未处理、1-已处理不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.dealState" type="text" placeholder="处理状态"  />
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

import { findByPrimaryKey, save, update } from '@/api/modules/monitor/tbAbnormalAlarmApi'

export default {
  name: 'ModulesMonitorTbAbnormalAlarmForm',
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
        alarmType: '',
        alarmTime: '',
        alarmLevel: '',
        alarmInfo: '',
        dealState: '',
        dealTime: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建异常报警管理`
      } else if (method === 'edit') {
        this.title = '修改异常报警管理'
      } else if (method === 'view') {
        this.title = '查看异常报警管理'
      }
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const { data } = await findByPrimaryKey(record.sysId)
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
              await update(this.record.sysId, this.formItem)
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
