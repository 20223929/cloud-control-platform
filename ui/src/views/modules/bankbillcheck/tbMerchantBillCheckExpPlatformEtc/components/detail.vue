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
      </el-form>
      <vxe-table
        ref="vxe"
        border
        show-overflow
        :data="tableData"
        :edit-rules="valueRules"
        :edit-config="{trigger: 'click', mode: 'cell', activeMethod: activeCellMethod}"
        @edit-disabled="editDisabledEvent">
        <vxe-table-column field="transDate" title="交易日期"></vxe-table-column>
        <vxe-table-column field="merchantGroupIdStr" title="拓展方"></vxe-table-column>
        <vxe-table-column field="merchantIdStr" title="拓展方"></vxe-table-column>
        <vxe-table-column field="siteIdStr" title="场景"></vxe-table-column>
        <vxe-table-column field="totalCount" title="确认后流水总数"
                          :edit-render="{name: '$input', props: {type: 'integer'}}"></vxe-table-column>
        <vxe-table-column field="totalAmount" title="确认后实收总金额(元)"
                          :edit-render="{name: '$input', props: {type: 'float', digits: 2}}"></vxe-table-column>
        <vxe-table-column field="remark" title="批注"
                          :edit-render="{name: '$input', props: {type: 'text'}}"></vxe-table-column>
      </vxe-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="doSomeThing">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import {errorRegister, confirmRegister,batchConfirmRegister} from '@/api/modules/bankbillcheck/tbMerchantBillCheckExpPlatformEtcApi'

export default {
  name: 'ModulesBankbillcheckTbmerchantbillcheckexpplatformetcTbMerchantBillCheckExpPlatformEtcForm',
  components: {},
  data() {
    return {
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      tableData: [],
      record: {},
      formItem: {}
    }
  },
  methods: {
    async init(method, record) {
      debugger
      Object.assign(this.$data, this.$options.data())
      this.method = method
      this.record = record
      if (method === 'errorRegister') {
        this.title = '异常登记：请输入确认记录数，确认金额，备注';
      }
      if (method === 'confirmRegister') {
        this.title = '确认登记：请输入确认记录数，确认金额，备注';
      }
      if (method === 'batchConfirmRegister') {
        this.title = '批量确认登记：请输入确认记录数，确认金额，备注';
      }

      let array = []
      if(record instanceof Array){
        for(let i in record){
          array.push(Object.assign({},record[i]))
        }
      }else{
        array.push(Object.assign({},record))
      }
      for(const i of array){
        if(i.totalAmount!=null&&i.totalAmount!=undefined&&i.totalAmount!=''){
          i.totalAmount *= 0.01
        }
      }
      this.tableData = array

      this.visible = true
      this.loading = false
      await this.$nextTick()
      if (method === 'errorRegister' || method === 'confirmRegister' || method === 'batchConfirmRegister') { // 修改或者查看
        this.loading = true
        this.loading = false
      }
    },
    activeCellMethod({column, columnIndex}) {
      if (columnIndex < 4) {
        return false
      }
      return true
    },
    editDisabledEvent({row, column}) {
      //this.$XModal.message({ message: '禁止编辑', status: 'error' })
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            let msg ;
            console.log(this.method)
            if (this.method === 'errorRegister') {
              msg = await errorRegister(this.formItem)
            } else if(this.method === 'confirmRegister'){
              msg = await confirmRegister(this.formItem)
            } else if(this.method === 'batchConfirmRegister'){
              msg = await batchConfirmRegister(this.formItem)
            }
            if(msg.data!='成功'){
              this.$notify.error(msg.data)
            }
            this.$emit('refreshData')
            this.visible = false
          } finally {
            this.saveLoading = false
          }
        }
      })
    },
    async doSomeThing() {
      await this.$refs.vxe.validate()
      if(this.method === 'errorRegister' || this.method === 'confirmRegister' ){
        let formItem = this.formItem
        debugger
        let one = this.tableData[0]
        formItem.transDate = one.transDate
        formItem.merchantGroupId = one.merchantGroupId
        formItem.merchantId = one.merchantId
        formItem.siteId = one.siteId
        formItem.totalCount = one.totalCount
        formItem.totalAmount = one.totalAmount
        formItem.remark = one.remark
        // let checkresult = this.checkInputParams(this.formItem.totalCount,this.formItem.totalAmount,this.formItem.remark);
        // if (checkresult != '') {
        //   this.$notify.error("录入参数错误：" + checkresult)
        //   return
        // }
        debugger
        if(formItem.totalAmount!=null&&formItem.totalAmount!=undefined&&formItem.totalAmount!=''){
          formItem.totalAmount = formItem.totalAmount * 100
        }
        this.save();
      }else if(this.method === 'batchConfirmRegister'){
        this.formItem = [];
        this.tableData.forEach((v,i,arr)=>{
          this.formItem[i] = {};
          let temp = this.formItem[i];
          temp.transDate = v.transDate
          temp.merchantGroupId = v.merchantGroupId
          temp.merchantId = v.merchantId
          temp.siteId = v.siteId
          temp.totalCount = v.totalCount
          temp.totalAmount = v.totalAmount
          temp.remark = v.remark
        })
        for (const temp of this.formItem) {
          debugger
          // const checkResult = this.checkInputParams(temp.totalCount,temp.totalAmount,temp.remark)
          // if(checkResult!== ''){
          //   this.$notify.error("存在录入的内容有错请检查("+checkResult+")")
          //   return;
          // }
          if(temp.totalAmount==null||temp.totalAmount==undefined&&formItem.totalAmount!=''){
            continue
          }
          temp.totalAmount = temp.totalAmount * 100
        }
      this.save()
      }

    }
    // checkInputParams(totalCount,totalAmount,remark){
    //   let checkResult = ''
    //   if (remark === null || remark === undefined)
    //     checkResult += '备注不能为空;'
    //   if ((totalCount == null || totalCount === undefined) && this.method === 'confirmRegister') {
    //     checkResult += '确认操作下确认数量必填'
    //   }
    //   if ((totalAmount == null || totalAmount === undefined) && this.method === 'confirmRegister') {
    //     checkResult += '确认操作下确认金额必填'
    //   }
    //   if (totalCount != null && totalCount != undefined && !/^\d+$/.test(totalCount)){
    //     checkResult += '确认金额必须是整数'
    //   }
    //   if (totalAmount != null && totalAmount != undefined) {
    //     if (!/^(([1-9]{1}\d*)|(0{1}))(\.\d{1,2})?$/.test(totalAmount)) {
    //       checkResult += '金额必须是数字，可以含有两个小数位'
    //     }
    //   }
    //   return checkResult
    // }
  },
  computed: {
    valueRules() {
      if(this.method === 'errorRegister'){
        return {
          remark: [
            { required: true, message: '批注不能为空' }
          ]
        }
      }
      const totalConfirmCountValid = ({ cellValue }) => {
        // 模拟服务端校验
        return new Promise((resolve, reject) => {
          if(cellValue && !(/(^\d*$)/.test(cellValue))){
            reject(new Error("请输入正整数"))
          }else{
            resolve()
          }
        })
      }
      const totalConfirmAmountValid = ({ cellValue }) => {
        // 模拟服务端校验
        return new Promise((resolve, reject) => {
          if(cellValue && !(/^(([1-9]{1}\d*)|(0{1}))(\.\d{1,2})?$/.test(cellValue))){
            reject(new Error("金额只能为正整数且最多保留两位小数"))
          }else{
            resolve()
          }
        })
      }
      return {
        totalCount: [
          { required: true, min: 0, message: '确认后流水总数不能为空' },
          { validator: totalConfirmCountValid}
        ],
        totalAmount: [
          { required: true, min: 0, message: '确认后实收总金额不能为空' },
          { validator: totalConfirmAmountValid}
        ],
        remark: [
          { required: true, message: '批注不能为空' }
        ]
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
