<template>
  <div class="app-container">
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="50%"
    >
      <el-form ref="form" v-loading="loading" class="form-full" :model="formItem" :rules="rules" label-width="140px">
        <el-divider content-position="left">编码信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="省份" prop="provinceId"
                          :rules="[{ required: true, message: '省份不能为空', trigger: 'blur' }]">
              <!--              <el-input v-model="formItem.provinceName" placeholder="请选择省份"/>-->
              <el-select v-model="formItem.provinceId" placeholder="请选择省份" filterable @change="provinceChange">
                <el-option v-for="item in province" :value="item.id" :label="item.name" :key="item.value"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="isGasStation">
            <el-form-item label="所属石化企业" prop="companyNum"
                          :rules="[{ required: true, message: '所属石化企业不能为空', trigger: 'change' }]">
              <el-select v-model="formItem.companyNum" placeholder="请选择所属石化企业" filterable>
                <el-option v-for="item in companyNums" :value="item.value" :label="item.label" :key="item.value"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地市" prop="cityId" :rules="[{ required: true, message: '地市不能为空', trigger: 'change' }]">
              <el-select v-model="formItem.cityId" placeholder="请选择地市" @change="cityChange" filterable>
                <el-option v-for="item in city" :value="item.id" :label="item.name" :key="item.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="县区" prop="countryId"
                          :rules="[{ required: true, message: '县区不能为空', trigger: 'change' }]">
              <el-select v-model="formItem.countryId" placeholder="请选择县区" filterable>
                <el-option v-for="item in country" :value="item.id" :label="item.name" :key="item.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="isGasStation ? '加油站顺序码' : '顺序码'" prop="centerSiteNo">
              <el-input id="centerSiteNo" v-model="formItem.centerSiteNo"
                        :placeholder="isGasStation ? '三位顺序码' : '四位顺序码'" @change="noChange"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部中心编码" prop="centerSiteId"
                          :rules="[{ required: true, message: '部中心编码不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="centerSiteId" placeholder="部中心编码" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="银行场景编码" prop="bankSiteId"
                          :rules="[{ required: true, message: '银行场景编码不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.bankSiteId" placeholder="银行场景编码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">其他信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="`${serviceName}名称`" prop="name"
                          :rules="[{ required: true, message: `${serviceName}名称不能为空`, trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.name" :placeholder="serviceName"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="`${serviceName}地址`" prop="address"
                          :rules="[{ required: true, message: `${serviceName}地址不能为空`, trigger: 'blur' }]">
              <el-input type="textarea" v-model="formItem.address" :placeholder="serviceAddress"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <!--            :rules="[{required: true,message: `${serviceLongitude}不能为空`,trigger: 'blur' }]"-->
            <el-form-item :label="`${serviceName}经度`" prop="lng">
              <el-input type="number" v-model="formItem.lng" :placeholder="serviceLongitude"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <!--            :rules="[{required: true,message: `${serviceLatitude}不能为空`,trigger: 'blur' }]"-->
            <el-form-item :label="`${serviceName}纬度`" prop="lat">
              <el-input type="number" v-model="formItem.lat" :placeholder="serviceLatitude"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="清分机构编码" prop="provinceNum"
                          :rules="[{ required: true, message: '清分机构编码不能为空', trigger: 'blur' }]">
              <el-input type="text" v-model="formItem.provinceNum" placeholder="清分机构编码" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作开始日期" prop="startTime"
                          :rules="[{ required: true, message: '请选择工作开始日期', trigger: 'change' }]">
              <el-date-picker type="date" :key="formItem.startTime" placeholder="选择工作开始日期" v-model="formItem.startTime"
                              style="width: 100%;"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作结束日期" prop="endTime"
                          :rules="[{ required: true, message: '请选择工作结束日期', trigger: 'change' }]">
              <el-date-picker type="date" :key="formItem.endTime" placeholder="选择工作结束日期" v-model="formItem.endTime"
                              style="width: 100%;"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否有代理商" prop="hasAgent"
                          :rules="[{ required: true, message: '请选择是否有代理商', trigger: 'change' }]">
              <ec-select v-model="formItem.hasAgent" dict-type="hasAgent" placeholder="请选择是否有代理商"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="hasAgent">
            <el-form-item label="代理商" prop="agentId"
                          :rules="[{ required: true, message: '请选择代理商', trigger: 'change' }]">
              <el-select v-model="formItem.agentId" placeholder="请选择代理商">
                <el-option v-for="(v,k) in agentMap" :label="v" :value="k" :key="k"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">开户行信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户行" prop="bank">
              <el-input type="text" v-model="formItem.bank" placeholder="开户行"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开户行地址" prop="bankAddr">
              <el-input type="textarea" v-model="formItem.bankAddr" placeholder="开户行地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开户行账号" prop="bankAccount">
              <el-input type="text" v-model="formItem.bankAccount" placeholder="开户行账号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">结算信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="服务费费率" prop="serviceRate">
              <el-input type="number" v-model="serviceRate" placeholder="服务费费率" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="通行费结算周期" prop="tollSettlementInterval"
                          :rules="[{ required: true, message: '请选择通行费结算周期', trigger: 'change' }]">
              <el-select v-model="formItem.tollSettlementInterval" placeholder="请选择通行费结算周期">
                <el-option v-for="item in tollSettlementInterval" :value="item.value" :label="item.label"
                           :key="item.value"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务费结算周期" prop="serviceSettlementInterval"
                          :rules="[{ required: true, message: '请选择服务费结算周期', trigger: 'change' }]">
              <el-select v-model="formItem.serviceSettlementInterval" placeholder="请选择服务费结算周期">
                <el-option v-for="item in serviceInterval" :value="item.value" :label="item.label" :key="item.value"
                           :disabled="item.disabled"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="中心服务费率" prop="centerRate">
              <el-input type="number" v-model="formItem.centerRate" placeholder="中心服务费率" min="0" max="1" step="0.001"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="hasAgent">
            <el-form-item label="代理商佣金费率" prop="agentRate">
              <el-input type="number" v-model="formItem.agentRate" placeholder="代理商佣金费率" min="0" max="1" step="0.001"/>
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

import {findSiteByPrimaryKey, getAgentInfo, saveSite} from '@/api/modules/merchant/tbMerchantApi'
import {areaData} from '@/api/modules/merchant/area'

export default {
  name: 'MerchantGroupForm',
  components: {},
  data() {
    var checkServiceRate = (rule, value, callback) => {
      if (!value && value !== 0) {
        return callback(new Error("服务费费率不能为空"))
      }
      if (value < 0) {
        return callback(new Error("服务费费率不能小于0"))
      }
      if (value > 1) {
        return callback(new Error("服务费费率不能大于1"))
      }
      callback()
    }
    var checkCenterRate = (rule, value, callback) => {
      if (!value && value !== 0) {
        return callback(new Error("中心服务费率不能为空"))
      }
      if (value < 0) {
        return callback(new Error("中心服务费率不能小于0"))
      }
      if (value > 1) {
        return callback(new Error("中心服务费率不能大于1"))
      }
      callback()
    }
    var checkAgentRate = (rule, value, callback) => {
      if (!value && value !== 0) {
        return callback(new Error("代理商佣金费率不能为空"))
      }
      if (value < 0) {
        return callback(new Error("代理商佣金费率不能小于0"))
      }
      if (value > 1) {
        return callback(new Error("代理商佣金费率不能大于1"))
      }
      callback()
    }
    var checkCenterSiteNo = (rule, value, callback) => {
      if (!value) return callback(new Error("顺序码不能为空"))
      if (isNaN(value)) return callback(new Error("请输入数字"))
      if (this._parentVnode.child.isGasStation) {
        if (value.length != 3) return callback(new Error("请输入三位顺序码"))
      } else {
        if (value.length != 4) return callback(new Error("请输入四位顺序码"))
      }
      return callback()
    }
    var checkLng = (rule, value, callback) => {
      if (!value && value !== 0) return callback(new Error("经度不能为空"))
      if (isNaN(value)) return callback(new Error("请输入数字"))
      if (!/^(((\d|[1-9]\d|1[0-7]\d|0{1,3})\.\d{0,6})|(\d|[1-9]\d|1[0-7]\d|0{1,3})|180\.0{0,6}|180)$/.test(value)) return callback(new Error("经度为0-180度，且最多有六位小数"))
      return callback()
    }
    var checkLat = (rule, value, callback) => {
      if (!value && value !== 0) return callback(new Error("纬度不能为空"))
      if (isNaN(value)) return callback(new Error("请输入数字"))
      if (!/^([0-8]?\d{1}\.\d{0,6}|90\.0{0,6}|[0-8]?\d{1}|90)$/.test(value)) return callback(new Error("纬度为0-90度，且最多有六位小数"))
      return callback()
    }
    return {
      agentMap: [],
      tollSettlementInterval: [
        {
          value: 1,
          label: "T+1"
        },
        {
          value: 2,
          label: "T+4"
        },
        {
          value: 3,
          label: "月结"
        }
      ],
      serviceSettlementInterval: [
        {
          value: 1,
          label: "T+1"
        },
        {
          value: 2,
          label: "T+4"
        },
        {
          value: 3,
          label: "月结"
        }
      ],
      companyNums: [
        {
          value: '001',
          label: '中国石油天然气集团公司（中国石油）'
        },
        {
          value: '002',
          label: '中国石油化工集团公司（中国石化）'
        },
        {
          value: '003',
          label: '中国海洋石油总公司（中国海油）'
        },
        {
          value: '004',
          label: '中国中化集团公司（中化）'
        },
        {
          value: '005',
          label: '陕西延长石油（集团）有限责任公司（延长石油）'
        }
      ],
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
      provinceId: '',
      province: [],
      city: [],
      country: [],
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      record: {},
      formItem: {
        companyNum: '',
        platformMerchantId: '',
        provinceId: '',
        cityId: '',
        countryId: '',
        centerSiteId: '',
        centerSiteNo: '',
        tollSettlementInterval: '',
        serviceSettlementInterval: '',
        agentId: '',
        agentRate: ''
      },
      rules: {
        centerSiteNo: [
          {validator: checkCenterSiteNo, required: true, trigger: 'blur'}
        ],
        lng: [
          {validator: checkLng, required: true, trigger: 'blur'}
        ],
        lat: [
          {validator: checkLat, required: true, trigger: 'blur'}
        ],
        centerRate: [
          {validator: checkCenterRate, required: true, trigger: 'blur'}
        ],
        agentRate: [
          {validator: checkAgentRate, required: true, trigger: 'blur'}
        ],
        serviceRate: [
          {validator: checkServiceRate, required: true, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    async init(method, record) {
      Object.assign(this.$data, this.$options.data())
      this.method = method
      this.record = record
      this.provinceId = record.id.substr(0, 2)
      if (method === 'add') {
        this.title = '新增三级商户'
        this.formItem.platformMerchantId = record.id
        this.formItem.provinceNum = this.provinceId.concat('0201')
      } else if (method === 'edit') {
        this.title = '修改三级商户信息'
      } else if (method === 'view') {
        this.title = '查看三级商户信息'
      }
      this.province = areaData.data
      this.visible = true
      this.loading = true
      await this.$nextTick()
      this.$refs.form.clearValidate()
      let res = await getAgentInfo()
      this.agentMap = res.data
      if (method === 'edit' || method === 'view') { // 修改或者查看
        this.loading = true
        const {data} = await findSiteByPrimaryKey(record.id)
        this.formItem = Object.assign({}, this.formItem, data)
        this.formItem.hasAgent = String(this.formItem.hasAgent)
        this.formItem.agentId = String(this.formItem.agentId)
        this.provinceId = data.centerSiteId.substr(0, 2)
        this.province.some(item => {
          if (item.id.substr(0, 2) === this.provinceId) {
            this.formItem.provinceId = item.id
            this.city = item.children
            return true
          }
        })
        if (this.isGasStation) {
          this.formItem.companyNum = data.centerSiteId.substr(2, 3)
          this.formItem.cityId = data.centerSiteId.substr(0, 2).concat(data.centerSiteId.substr(5, 2), '00')
          this.cityChange(this.formItem.cityId)
          this.formItem.countryId = this.formItem.cityId.substr(0, 4).concat(data.centerSiteId.substr(7, 2))
          this.formItem.centerSiteNo = data.centerSiteId.substr(-3)
        } else {
          this.formItem.cityId = data.centerSiteId.substr(0, 4).concat('00')
          this.cityChange(this.formItem.cityId)
          this.formItem.countryId = this.formItem.cityId.substr(0, 4).concat(data.centerSiteId.substr(4, 2))
          this.formItem.centerSiteNo = data.centerSiteId.substr(-4)
        }
      } else {
        this.province.some(item => {
          if (item.id.substr(0, 2) === this.provinceId) {
            this.formItem.provinceId = item.id
            this.city = item.children
            return true
          }
        })
      }
      this.loading = false
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            this.saveLoading = true
            this.formItem.startTime = this.$moment(this.formItem.startTime).format('YYYY-MM-DD')
            this.formItem.endTime = this.$moment(this.formItem.endTime).format('YYYY-MM-DD')
            await saveSite(this.formItem)
            this.$notify.success('保存成功')
            this.$emit('refreshData')
            this.visible = false
          } finally {
            this.saveLoading = false
          }
        }
      })
    },
    provinceChange(provinceId) {
      this.formItem.cityId = ''
      this.city = []
      if (provinceId) {
        this.province.some(item => {
          if (item.id === provinceId) {
            this.city = item.children
            return true
          }
        })
      }
    },
    cityChange(cityId) {
      this.formItem.countryId = ''
      this.country = []
      if (cityId) {
        this.city.some(item => {
          if (item.id === cityId) {
            this.country = item.children
            return true
          }
        })
      }
    }
  },
  computed: {
    serviceInterval() {
      let tollSettlementInterval = this.formItem.tollSettlementInterval
      let serviceSettlementInterval = this.formItem.serviceSettlementInterval
      let that = this
      if (tollSettlementInterval) {
        if (tollSettlementInterval == 3) {
          this.serviceSettlementInterval.some(item => {
            if (item.value == 3) {
              item.disabled = false
            } else {
              item.disabled = true
              if (serviceSettlementInterval != 3) that.formItem.serviceSettlementInterval = ''
            }
          })
        } else {
          this.serviceSettlementInterval.some(item => {
            if (item.value == 3 || item.value == tollSettlementInterval) {
              item.disabled = false
            } else {
              item.disabled = true
              if (serviceSettlementInterval != 3 && serviceSettlementInterval != tollSettlementInterval) that.formItem.serviceSettlementInterval = ''
            }
          })
        }
      }
      return this.serviceSettlementInterval
    },
    centerSiteId() {
      let result = ''
      if (this.isGasStation) {
        result = this.formItem.countryId.substr(0, 2).concat(this.formItem.companyNum, this.formItem.countryId.substr(2), this.formItem.centerSiteNo)
      } else {
        result = this.formItem.countryId.concat(this.formItem.centerSiteNo)
      }
      this.formItem.centerSiteId = result
      return result
    },
    serviceRate() {
      if(!this.formItem.centerRate && !this.formItem.agentRate) {
        this.formItem.serviceRate = 0
        return 0
      }
      if(!this.hasAgent && this.formItem.centerRate){
        if(this.formItem.centerRate === 1 || this.formItem.centerRate === 0) {
          this.formItem.serviceRate = this.formItem.centerRate
          return this.formItem.centerRate
        }
        let centerRateStr = String(this.formItem.centerRate)
        if(centerRateStr.length > 8) {
          let result = Number(centerRateStr.substring(0,8))
          this.formItem.serviceRate = result
          return result
        }
      }
      if(this.hasAgent){
        if(this.formItem.centerRate && !this.formItem.agentRate){
          let centerRateStr = String(this.formItem.centerRate)
          if(this.formItem.centerRate === 1 || this.formItem.centerRate === 0 || centerRateStr.length <= 8) {
            this.formItem.serviceRate = this.formItem.centerRate
            return this.formItem.centerRate
          }
          let result = Number(centerRateStr.substring(0,8))
          this.formItem.serviceRate = result
          return result
        }
        if(!this.formItem.centerRate && this.formItem.agentRate){
          let agentRateStr = String(this.formItem.agentRate)
          if(this.formItem.agentRate === 1 || this.formItem.agentRate === 0 || agentRateStr.length <= 8) {
            this.formItem.serviceRate = this.formItem.agentRate
            return this.formItem.agentRate
          }
          let result = Number(agentRateStr.substring(0,8))
          this.formItem.serviceRate = result
          return result
        }
        let agentRateStr = String(this.formItem.agentRate)
        let centerRateStr = String(this.formItem.centerRate)
        let multieCount = agentRateStr.length > centerRateStr.length ? agentRateStr.length - agentRateStr.indexOf(".") - 1 : centerRateStr.length - centerRateStr.indexOf(".") - 1
        let multipy = Math.pow(10,multieCount)
        let result = Number(String(((this.formItem.centerRate * multipy) + (this.formItem.agentRate * multipy)) / multipy).substring(0,8))
        this.formItem.serviceRate = result
        return result
      }
    },
    hasAgent() {
      if (this.formItem.hasAgent == 1) return true
      else {
        this.formItem.agentId = ''
        this.formItem.agentRate = ''
        return false
      }
    },
    isGasStation() {
      return this.formItem.platformMerchantId && this.formItem.platformMerchantId.substr(5, 1) === '3'
    },
    serviceName() {
      if (this.formItem.platformMerchantId) {
        let target = Number(this.formItem.platformMerchantId.substr(4, 2))
        let result = '未知服务类型'
        this.serviceType.some(item => {
          if (item.value == target) {
            result = item.label
            return true
          }
        })
        return result
      }
    }
  }
}
</script>

<style scoped>

</style>
