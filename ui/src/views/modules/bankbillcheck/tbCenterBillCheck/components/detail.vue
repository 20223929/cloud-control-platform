<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="60%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" label-width="100px"
               label-suffix=" : ">
        <el-row :gutter="15">
          <el-col :span="12" v-if="method==='confirm' || method==='batchConfirm'">
            <el-form-item
              label="备注"
              prop="remark"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.remark" type="text" placeholder="备注"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="method==='secondConfirm' || method==='batchSecondConfirm'">
            <el-form-item
              label="补确认备注"
              prop="secondConfirmRemark"
              :rules="[
              ]"
            >
              <el-input v-model="formItem.secondConfirmRemark" type="text" placeholder="补确认备注"/>
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

import {
  findByPrimaryKey,
  save,
  update,
  myConfirm,
  mySecondConfirm,
  batchSecondConfirm as bsconfirm,batchConfirm as bconfirm
} from '@/api/modules/bankbillcheck/tbCenterBillCheckApi'

export default {
  name: 'ModulesBankbillcheckTbCenterBillCheckForm',
  components: {},
  data() {
    return {
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      record: {},
      confirmType: "",
      formItem: {
        remark: '',
        secondConfirmRemark: '',
        transDate: '',
        merchantGroupId: '',
        merchantId: '',
        siteId: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())
      debugger
      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建联网中心对账核对`
      } else if (method === 'edit') {
        this.title = '修改联网中心对账核对'
      } else if (method === 'view') {
        this.title = '查看联网中心对账核对'
      } else if (method === 'confirm') {
        this.title = '确认'
        this.confirmType = '确认'
      } else if (method === 'secondConfirm') {
        this.title = '补确认'
        this.confirmType = '补确认'
      } else if (method === 'batchConfirm') {
        this.title = '批量确认'
        this.confirmType = '批量确认'
      } else if (method === 'batchSecondConfirm') {
        this.title = '批量补确认'
        this.confirmType = '批量补确认'
      }
      //this.formItem = Object.assign({}, this.formItem, record.transDate, record.merchantGroupId, record.merchantId, record.siteId)
      this.formItem.siteId =record.siteId
      this.formItem.merchantGroupId = record.merchantGroupId
      this.formItem.merchantId = record.merchantId
      this.formItem.transDate = record.transDate
      this.visible = true
      this.loading = false
      this.record = record
      await this.$nextTick()
      this.$refs.form.clearValidate()
      if (method === 'edit' || method === 'view' || method === 'confirm' || method === 'secondConfirm'|| method === 'batchConfirm'|| method === 'batchSecondConfirm') { // 修改或者查看
        this.loading = true
        debugger
        //const {data} = await findByPrimaryKey(record.transDate, record.merchantGroupId, record.merchantId, record.siteId)
        //this.formItem = Object.assign({}, this.formItem, record.transDate, record.merchantGroupId, record.merchantId, record.siteId)
        this.loading = false
      }
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            switch (this.method) {
              case 'edit':
                await update(this.record.transDate, this.record.merchantGroupId, this.record.merchantId, this.record.siteId, this.formItem)
                break
              case 'add':
                await save(this.formItem)
                break
              case 'confirm':
                debugger
                await myConfirm(this.formItem)
                break
              case 'secondConfirm':
                await mySecondConfirm(this.formItem)
                break
              case 'batchConfirm':
                let checkresult = 1;
                this.record.forEach(c=>{if(c.confirmState!==0){
                  checkresult = 0;
                }
                })
                if(!checkresult){
                  this.$notify.error("所选记录必须为待核对状态,请重新选择!")
                  return
                }
                this.record.forEach(c=>{c.remark = this.formItem.remark})
                await bconfirm(this.record);
                break;
              case 'batchSecondConfirm':
                let checkresult2 = 1
                this.record.forEach(c=>{if(c.confirmState!==2){
                  checkresult2 = 0;
                }
                })
                if(!checkresult2){
                  this.$notify.error("所选记录必须为待补核对状态,请重新选择!")
                  return
                }
                this.record.forEach((c)=>{c.secondConfirmRemark = this.formItem.secondConfirmRemark})
                await bsconfirm(this.record);
                break;
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
