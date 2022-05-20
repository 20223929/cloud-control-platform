<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="90%"
    >
      <el-form inline>
         <span style="color: red;font-size: large">
           请输入正确的流水总数及金额
         </span>
      </el-form>
      <vxe-grid
        border
        resizable
        keep-source
        ref="vxe"
        max-height="1000"
        :loading="loading"
        :columns="columns"
        :data="confirmData"
        :edit-rules="valueRules"
        :edit-config="{trigger: 'click', mode: 'cell', showStatus: true, icon: 'fa fa-file-text-o'}">
      </vxe-grid>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
         <el-button @click="saveConfirmAndRefresh">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import {saveConfirmData} from '@/api/modules/bankbillcheck/bankCheck'

export default {
  name: 'Confirm',
  components: {},
  data() {
    return {
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      showSearch: false,
      operation: '',
      title: '',
      record: {},
      confirmData: [],
      columns: [
        { title: '交易日期', field: 'tradeDay', tableField: 'trade_day', minWidth: 180, align: 'center'},
        { title: '拓展方', field: 'merchantGroupName', tableField: 'merchant_group_name', minWidth: 180, align: 'center' },
        { title: '运营方', field: 'merchantName', tableField: 'merchant_name', minWidth: 180, align: 'center' },
        { title: '场景', field: 'siteName', tableField: 'site_id', minWidth: 180, align: 'center'},
        { title: '确认后流水总数', field: 'totalConfirmCount', tableField: 'total_confirm_count', minWidth: 180, align: 'center',editRender: {name: '$input', props: {type: 'text'}}},
        { title: '确认后实收总金额(元)', field: 'totalConfirmAmount', tableField: 'total_confirm_amount', minWidth: 180, align: 'center',editRender: {name: '$input', props: {type: 'text'}}},
        { title: '批注', field: 'remark', tableField: 'remark', minWidth: 180, align: 'center',editRender: {name: '$input', props: {type: 'text'}}},
      ]
    }
  },
  methods: {
    async init(record,operation) {
      debugger
      Object.assign(this.$data, this.$options.data())
      let array = []
      if(record instanceof Array){
        for(var i in record){
          array.push(Object.assign({},record[i]))
        }
        this.record = array
      }else{
        array.push(Object.assign({},record))
        this.record =  array
      }
      this.operation = operation
      this.title = '确认'
      this.visible = true
      this.loading = false
      await this.$nextTick()
      this.record.forEach((item,array,index) => {
        item.confirmState = operation ? 3 : 4
      })
      this.confirmData = this.record
    },
    async saveConfirmAndRefresh() {
      await this.$refs.vxe.validate()
      const { data } = await saveConfirmData(this.confirmData)
      if(data.code === 200){
        this.$notify.success(data.msg)
        this.$emit('refreshData')
        this.visible = false
      }else{
        this.$notify.error(data.msg)
      }
    }
  },
  computed: {
    valueRules() {
      if(this.operation === 1){
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
        totalConfirmCount: [
          { required: true, min: 0, message: '确认后流水总数不能为空' },
          { validator: totalConfirmCountValid}
        ],
        totalConfirmAmount: [
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
