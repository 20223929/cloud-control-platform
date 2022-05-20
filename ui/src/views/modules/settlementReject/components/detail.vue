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
              label="交易时间"
              prop="transTime"
              :rules="[
                { required: true, message: '交易时间不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.transTime" type="text" placeholder="交易时间" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="交易流水号"
              prop="transactionId"
              :rules="[
                { required: true, message: '交易流水号不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.transactionId" type="text" placeholder="交易流水号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="服务方名称"
              prop="siteName"
              :rules="[
                { required: true, message: '服务方名称不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.siteName" type="text" placeholder="服务方名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="车牌号码"
              prop="vehiclePlate"
              :rules="[
                { required: true, message: '车牌号码不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.vehiclePlate" type="text" placeholder="车牌号码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="ETC卡号"
              prop="cardId"
              :rules="[
                { required: true, message: 'ETC卡号不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.cardId" type="text" placeholder="ETC卡号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="OBU合同系列号"
              prop="obuId"
              :rules="[
                { required: true, message: 'OBU合同系列号不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.obuId" type="text" placeholder="OBU合同系列号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="交易金额"
              prop="fee"
              :rules="[
                { required: true, message: '交易金额不能为空', trigger: 'blur' }
              ]"
            >
              <el-input-number v-model="formItem.fee" type="text" placeholder="交易金额"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="流水状态，清分结果，多个用|分割，0-正常记账 1-TAC验证未通过 2-重复交易 3-用户状态变化 4-无效服务类型 5-逾期超过设定值 6-交易数据域错（暂不启用）7-超过最大交易限额 8-卡号不存在 9-卡状态不匹配 10-卡超过有效期 11-不允许的交易（暂不启用）12-卡片CSN不匹配（暂不启用） 13-测试交易 14-卡账不符（仅用于储值卡）（暂不启用） 15-无效卡类型 16-车道对时错误 17-ETC通行记录与过车图像未匹配 18-过车识别图像校核有误 19-路径拟合数据的支撑证据有误"
              prop="transSta"
              :rules="[
                { required: true, message: '流水状态，清分结果，多个用|分割，0-正常记账 1-TAC验证未通过 2-重复交易 3-用户状态变化 4-无效服务类型 5-逾期超过设定值 6-交易数据域错（暂不启用）7-超过最大交易限额 8-卡号不存在 9-卡状态不匹配 10-卡超过有效期 11-不允许的交易（暂不启用）12-卡片CSN不匹配（暂不启用） 13-测试交易 14-卡账不符（仅用于储值卡）（暂不启用） 15-无效卡类型 16-车道对时错误 17-ETC通行记录与过车图像未匹配 18-过车识别图像校核有误 19-路径拟合数据的支撑证据有误不能为空', trigger: 'blur' }
              ]"
            >
              <el-input v-model="formItem.transSta" type="text" placeholder="流水状态，清分结果，多个用|分割，0-正常记账 1-TAC验证未通过 2-重复交易 3-用户状态变化 4-无效服务类型 5-逾期超过设定值 6-交易数据域错（暂不启用）7-超过最大交易限额 8-卡号不存在 9-卡状态不匹配 10-卡超过有效期 11-不允许的交易（暂不启用）12-卡片CSN不匹配（暂不启用） 13-测试交易 14-卡账不符（仅用于储值卡）（暂不启用） 15-无效卡类型 16-车道对时错误 17-ETC通行记录与过车图像未匹配 18-过车识别图像校核有误 19-路径拟合数据的支撑证据有误" />
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

import { findByPrimaryKey, save, update } from '@/api/modules/settlementreject/settlementRejectApi'

export default {
  name: 'ModulesSettlementrejectSettlementRejectForm',
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
        transTime: '',
        transactionId: '',
        siteName: '',
        vehiclePlate: '',
        cardId: '',
        obuId: '',
        fee: '',
        transSta: ''
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())

      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = `新建退费详情表`
      } else if (method === 'edit') {
        this.title = '修改退费详情表'
      } else if (method === 'view') {
        this.title = '查看退费详情表'
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
