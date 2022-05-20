<template>
  <div class="app-container">
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="60%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :rules="rules" :model="formItem" label-width="150px">
        <el-divider content-position="left">编码信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部中心编码" prop="centerMerchantNo">
              <el-input type="text" v-model="formItem.centerMerchantNo" placeholder="三位运营方顺序码">
                <template slot="prepend">{{ provinceId }}{{ type }}</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="银行商户编号" prop="bankMerchantId"
                          :rules="[{ required: true, message: '银行商户编号不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.bankMerchantId" placeholder="银行商户编号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="服务类型" prop="serviceType"
                          :rules="[{ required: true, message: '服务类型不能为空', trigger: 'change' }]">
              <el-select v-model="formItem.serviceType" placeholder="请选择" :disabled="method == 'edit'">
                <el-option
                  v-for="item in serviceType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <!--              <ec-select v-model="formItem.serviceType" dict-type="service_type" :disabled="method == 'edit'"/>-->
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">识别信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="运营方名称" prop="name"
                          :rules="[{ required: true, message: '运营方名称不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.name" placeholder="运营方名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证件类型" prop="certificateType"
                          :rules="[{ required: true, message: '证件类型不能为空', trigger: 'change' }]">
              <el-select v-model="formItem.certificateType" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="纳税人识别号" prop="taxpayerCode"
                          :rules="[{ required: true, message: '纳税人识别号不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.taxpayerCode" placeholder="纳税人识别号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="统一社会信用代码/组织机构代码证号" prop="creditCode"
                          :rules="[{ required: true, message: '统一社会信用代码/组织机构代码证号不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.creditCode" placeholder="统一社会信用代码/组织机构代码证号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">联系人信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人名称" prop="contact"
                          :rules="[{ required: true, message: '联系人名称不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.contact" placeholder="联系人名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="tel" :rules="[{ required: true, message: '联系方式不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.tel" placeholder="联系方式"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="地址" prop="address" :rules="[{ required: true, message: '地址不能为空', trigger: 'blur' }]">
              <el-input type="textarea" v-model="formItem.address" placeholder="地址"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">开户行信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户行" prop="bank" :rules="[{ required: true, message: '开户行不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.bank" placeholder="开户行"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开户行地址" prop="bankAddr"
                          :rules="[{ required: true, message: '开户行地址不能为空', trigger: 'blur' }]">
              <el-input type="textarea" v-model="formItem.bankAddr" placeholder="开户行地址"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户行账号" prop="bankAccount"
                          :rules="[{ required: true, message: '开户行账号不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.bankAccount" placeholder="开户行账号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">结算信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="银行手续费费率" prop="bankRate">
              <el-input type="number" v-model="formItem.bankRate" placeholder="银行手续费费率" min="0" max="1" step="0.001"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="清分服务费费率" prop="centerRate">
              <el-input type="number" v-model="formItem.centerRate" placeholder="清分服务费费率" min="0" max="1" step="0.001"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!--          <el-col :span="12">-->
          <!--            <el-form-item label="清分服务费结算方式" prop="centerSettlementType" :rules="[{ required: true, message: '清分服务费结算方式不能为空', trigger: 'change' }]" v-show="false">-->
          <!--              <el-radio :label=1 v-model="formItem.centerSettlementType">按比例</el-radio>-->
          <!--&lt;!&ndash;              <el-radio :label=2 v-model="formItem.centerSettlementType">按固定额度</el-radio>&ndash;&gt;-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <el-col :span="12">
            <el-form-item label="结算周期(天)" prop="centerSettlementCycle">
              <el-input type="number" v-model="formItem.centerSettlementCycle" placeholder="结算周期(天)" min="1" step="1"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">证书信息</el-divider>
        <el-row>
          <el-col :span="24">
            <el-form-item label="上传证书信息" prop="certFile">
              <el-upload
                class="upload-cert"
                ref="uploadCert"
                list-type="text"
                action="#"
                :on-change="handleChange"
                :on-remove="handleRemove"
                :auto-upload="false"
                :file-list="fileList"
                accept=".zip">
                <el-button size="small" type="primary">上传</el-button>
                <div slot="tip" class="el-upload__tip" style="color: red;">
                  请上传zip文件，里面必须包含证书文件(证书规范：x.509，证书后缀：cer或crt，公钥算法：rsa，公钥长度：2048位)以及数字签名文件(.txt，第一行是一个源字符串，第二行是源字符串用商户私钥通过sha256withrsa加签后的字符串)
                </div>
              </el-upload>
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

import {findMerchantByPrimaryKey, saveMerchant} from '@/api/modules/merchant/tbMerchantApi'

export default {
  name: 'MerchantForm',
  components: {},
  data() {
    var checkCenterRate = (rule, value, callback) => {
      if (!value && value !== 0) {
        return callback(new Error("清分服务费费率不能为空"))
      }
      if (value < 0) {
        return callback(new Error("清分服务费费率不能小于0"))
      }
      if (value > 1) {
        return callback(new Error("清分服务费费率不能大于1"))
      }
      callback()
    }
    var checkBankRate = (rule, value, callback) => {
      if (!value && value !== 0) {
        return callback(new Error("银行手续费费率不能为空"))
      }
      if (value < 0) {
        return callback(new Error("银行手续费费率不能小于0"))
      }
      if (value > 1) {
        return callback(new Error("银行手续费费率不能大于1"))
      }
      callback()
    }
    var checkCenterSettlementCycle = (rule, value, callback) => {
      if (!value && value !== 0) {
        return callback(new Error("结算周期不能为空"))
      }
      if (value < 1) {
        return callback(new Error("结算周期不能小于1"))
      }
      callback()
    }
    var checkcenterMerchantNo = (rule, value, callback) => {
      if (!value) return callback(new Error("运营方顺序码不能为空"))
      if (isNaN(value)) return callback(new Error("请输入数字"))
      if (value.length != 3) return callback(new Error("请输入三位的运营方顺序码"))
      callback()
    }
    return {
      fileList: [],
      provinceId: '',
      type: '04',
      serviceType: [
        {
          value: 2,
          label: '停车场'
        },
        {
          value: 3,
          label: '加油站'
        },
        {
          value: 4,
          label: '服务区'
        },
        {
          value: 5,
          label: '市政拓展'
        },
        {
          value: 6,
          label: '充电桩'
        }
      ],
      options: [
        {
          value: '1',
          label: '营业执照'
        },
        {
          value: '2',
          label: '组织机构'
        },
      ],
      myForm: new FormData(),
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      record: {},
      formItem: {
        platformMerchantGroupId: '',
        centerSettlementType: 1,
        centerMerchantNo: '',
        certFile: '',
        isNewFile: false
      },
      certFileRules: [
        {required: true, message: '请选择证书文件'}
      ],
      rules: {
        centerRate: [
          {validator: checkCenterRate, required: true, trigger: 'blur'}
        ],
        bankRate: [
          {validator: checkBankRate, required: true, trigger: 'blur'}
        ],
        centerSettlementCycle: [
          {validator: checkCenterSettlementCycle, required: true, trigger: 'blur'}
        ],
        centerMerchantNo: [
          {validator: checkcenterMerchantNo, required: true, trigger: 'blur'}
        ],
        certFile: [
          {required: true, message: '请选择证书文件'}
        ]
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())
      this.method = method
      this.record = record
      if (method === 'add') {
        this.title = '新增二级商户'
        this.formItem.platformMerchantGroupId = record.id
      } else if (method === 'edit') {
        this.title = '修改二级商户信息'
      } else if (method === 'view') {
        this.title = '查看二级商户信息'
      }
      this.provinceId = record.id.substr(0, 2)
      this.visible = true
      this.loading = true
      await this.$nextTick()
      this.$refs.form.clearValidate()

      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const {data} = await findMerchantByPrimaryKey(record.id)
        this.formItem = Object.assign({}, this.formItem, data)
        this.formItem.centerMerchantNo = this.formItem.centerMerchantId.substr(-3)
        let fileName = data.certFileName
        if (fileName) {
          this.fileList = [{name: fileName}]
          this.rules.certFile = []
        }
      }
      this.loading = false
    },
    handleChange(file, fileList) {
      const isExceedSize = file.size / 1024 /1024 > 1
      if(isExceedSize){
        this.$message.error('上传文件过大,请传入1MB以内的压缩包!');
      }
      if (isZip && !isExceedSize) {
        fileList.splice(0)
        fileList.push(file)
        this.$nextTick(() => {
          this.$refs.form.clearValidate(['certFile'])
        })
        this.formItem.certFile = file.raw
        this.rules.certFile = []
        this.formItem.isNewFile = true
      } else {
        fileList.splice(-1)
      }
    },
    handleRemove(file, fileList) {
      this.formItem.certFile = ''
      if (fileList.length == 0) {
        this.rules.certFile = this.certFileRules
        this.$nextTick(() => {
          this.$refs.form.validate()
        })
      }
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            this.formItem.centerMerchantId = this.provinceId.concat(this.type, this.formItem.centerMerchantNo)
            for (var key in this.formItem) {
              this.myForm.set(key, this.formItem[key])
            }
            const result = await saveMerchant(this.myForm)
            this.$notify.success(result.message)
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
