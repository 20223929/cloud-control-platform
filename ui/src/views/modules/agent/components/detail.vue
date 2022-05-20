<template>
  <div class="app-container">
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="30%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" label-width="140px" :disabled="method === 'view'">
        <el-row>
          <el-col :span="24">
            <el-form-item label="代理商编号" prop="agentNo">
              <el-input type="text" v-model="formItem.agentNo" placeholder="代理商编号(新增后台自动生成)" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="代理商名称" prop="agentName" :rules="[{ required: true, message: '代理商名称不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.agentName" placeholder="代理商名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="证件类型" prop="certificateType" :rules="[{ required: true, message: '证件类型不能为空', trigger: 'change' }]">
              <ec-select dictType="certificateType" v-model="formItem.certificateType"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="纳税人识别号" prop="taxpayerCode" :rules="[{ required: true, message: '纳税人识别号不能为空', trigger: 'blur' }]">
              <el-input v-model="formItem.taxpayerCode" type="text" placeholder="纳税人识别号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="统一社会信用代码/组织机构代码证号" prop="creditCode" :rules="[{ required: true, message: '统一社会信用代码/组织机构代码证号不能为空', trigger: 'blur' }]">
              <el-input v-model="formItem.creditCode" style="line-height: 50px;" type="text" placeholder="统一社会信用代码/组织机构代码证号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="开户行" prop="bank">
              <el-input type="text" v-model="formItem.bank" placeholder="开户行"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="开户行地址" prop="bankAddr">
              <el-input type="textarea" v-model="formItem.bankAddr" placeholder="开户行地址"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="开户行账号" prop="bankAccount">
              <el-input type="text" v-model="formItem.bankAccount" placeholder="开户行账号"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="save">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import { findDataById,save } from '@/api/modules/agent/agentApi'

export default {
  name: 'agentDetailView',
  components: {},
  data() {
    return {
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      formItem: {

      }
    }
  },
  methods: {
    async init(method, id) {
      console.log("id:"+id)
      Object.assign(this.$data, this.$options.data())
      this.method = method
      if (method === 'add') {
        this.title = `新增代理商`
      } else if (method === 'edit') {
        this.title = '编辑代理商'
      } else if (method === 'view'){
        this.title = "代理商详情"
      }
      this.visible = true
      this.loading = true
      await this.$nextTick()
      this.$refs.form.clearValidate()

      if (method === 'edit' || method === 'view') { // 编辑需要加载数据
        this.loading = true
        const { data } = await findDataById(id)
        this.formItem = Object.assign({}, this.formItem, data)
      }
      this.loading = false
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            const result = await save(this.formItem)
            if(result.code == 200){
              this.visible = false
              this.$notify.success(result.data)
              this.$emit('refreshData')
            }else{
              this.$notify.error(result.message)
            }
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
