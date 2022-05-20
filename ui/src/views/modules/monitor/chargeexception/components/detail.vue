<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="30%"
    >
      <el-form ref="form" v-loading="loading" label-position="right" class="form-full" :model="formItem" label-width="120px" label-suffix=" : ">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="确认时间"
              prop="confirmTime"
              :rules="[
                { required: true, message: '确认时间不能为空', trigger: 'blur' }
              ]"
            >
              <el-date-picker
                v-model="formItem.confirmTime"
                type="datetime"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                disabled="true"
                size="small"
                >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="20">
            <el-form-item
              label="确认人"
              prop="confirmMan"
              :rules="[
                { required: true, message: '确认人不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.confirmMan" type="text" placeholder="确认人" disabled="true" size="small"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="20">
            <el-form-item
              label="批注"
              prop="remark"
            >
              <el-input v-model="formItem.remark" type="textarea" placeholder="批注" rows="10" maxlength="400" show-word-limit/>
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

import { update } from '@/api/modules/monitor/chargeExcepionApi'

export default {
  name: 'ModulesMonitorChargeExcepionForm',
  components: {},
  data() {
    return {
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      record: [],
      formItem: {
        confirmTime: '',
        confirmMan: '',
        remark: ''
      }
    }
  },
  methods: {
    async init(method,record) {
      Object.assign(this.$data, this.$options.data())
      this.formItem.confirmMan = this.$store.getters.userInfo.username
      this.formItem.confirmTime = new Date()
      this.method = method
      record instanceof Array ? this.record = record : this.record.push(record)
      console.log(this.record)
      this.title="收费异常确认处理"
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.$refs.form.clearValidate()
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            const that = this
            const firstRecord = this.record[0]
            firstRecord.confirmTime=that.$moment(that.formItem.confirmTime).format('YYYY-MM-DD HH:mm:ss')
            firstRecord.confirmMan=that.formItem.confirmMan
            firstRecord.remark=that.formItem.remark
            this.saveLoading = true
            await update(this.record)
            this.$notify.success('确认成功')
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
