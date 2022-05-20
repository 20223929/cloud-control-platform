<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="60%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" label-width="140px" label-suffix=" : ">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="渠道方分成比例"
              prop="channelProportion"
              :rules="[
                { required: true, message: '渠道方分成比例不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.channelProportion" type="text" placeholder="渠道方分成比例" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="平台方分成比例"
              prop="platformProportion"
              :rules="[
                { required: true, message: '平台方分成比例不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.platformProportion" type="text" placeholder="平台方分成比例" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="拓展方分成比例"
              prop="merchantGroupProportion"
              :rules="[
                { required: true, message: '拓展方分成比例不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.merchantGroupProportion" type="text" placeholder="拓展方分成比例" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="版本号"
              prop="version"
              :rules="[
                { required: true, message: '版本号不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.version" type="text" placeholder="版本号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="开始时间"
              prop="startTime"
              :rules="[
                { required: true, message: '开始时间不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.startTime" type="text" placeholder="开始时间" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="结束时间"
              prop="endTime"
              :rules="[
                { required: true, message: '结束时间不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.endTime" type="text" placeholder="结束时间" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="登记人"
              prop="registrant"
              :rules="[
                { required: true, message: '登记人不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.registrant" type="text" placeholder="登记人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="登记时间"
              prop="registerTime"
              :rules="[
                { required: true, message: '登记时间不能为空', trigger: 'blur' }
              ]"
            >
              <el-date-picker
                v-model="formItem.registerTime"
                type="datetime"
                placeholder="选择日期时间"
                :key="formItem.transTime"
                align="right" style="width: 287px;" value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="状态"
              prop="status"
              :rules="[
                { required: true, message: '状态不能为空', trigger: 'blur' }
              ]"
            >
              <el-select v-model="formItem.status">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >

                </el-option>
              </el-select>
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

export default {
  name: 'ModulesServiceproportionTbServiceProportionForm',
  components: {},
  data() {
    return {
      options: [
        {
          value: '',
          label: '全部'
        },
        {
          value: '0',
          label: '启用'
        },
        {
          value: '1',
          label: '禁用'
        }
      ],
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      record: {},
      formItem: {
        channelProportion: '',
        platformProportion: '',
        merchantGroupProportion: '',
        version: '',
        startTime: '',
        endTime: '',
        registrant: '',
        registerTime: '',
        status: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `添加服务费分成信息管理`
      } else if (method === 'edit') {
        this.title = '修改服务费分成信息管理'
      } else if (method === 'view') {
        this.title = '查看服务费分成信息管理'
      }
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        this.formItem = Object.assign({}, this.formItem)
        this.loading = false
      }
    },
    save() {
      this.$notify.success('模拟保存成功')
      this.visible = false
      this.$emit("refreshData")
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
