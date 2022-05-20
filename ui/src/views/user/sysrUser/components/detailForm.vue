<template>
  <el-form ref="form" status-icon :model="formItem" label-width="150px" label-suffix=":">
    <el-row>
      <el-col :span="12">
        <el-form-item v-if="superAdmin" :label="$t('ec.user.tenant')" prop="sysrUser.tenantId">
          <ec-tenant v-model="formItem.sysrUser.tenantId" :readonly="method==='view' " />
        </el-form-item>
      </el-col>
      <el-col v-if="formItem.sysrUser.tenantId" :span="12">
        <el-form-item :label="$t('ec.user.org')" prop="orgIdList">
          <ec-org-select
            v-model="formItem.orgIdList"
            :data="{ tenantId: formItem.sysrUser.tenantId }"
            multiple
            check-strictly
            filterable
            default-expand-all
            :readonly="method==='view'"
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item v-if="superAdmin" :label="$t('ec.app.app')" prop="sysrUser.appId">
          <ec-register-app v-model="formItem.sysrUser.appId" :readonly="method==='view'" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item prop="sysrUser.deptId" :label="$t('ec.user.dept')" :rules="[{ validator: validateDepts, trigger: 'change'}]">
          <ec-app-dept-select
            ref="appDeptSelect"
            v-model="formItem.sysrUser.deptId"
            multiple
            ban-root
            check-strictly
            :readonly="method==='view'"
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item
          :label="$t('ec.user.userName')"
          prop="sysrUser.username"
          :rules="[{ required: true, message: $t('ec.user.userName') + $t('ec.table.emptyError'), trigger: 'blur'}, { validator: validateAccount, trigger: 'blur'}]"
        >
          <span v-if="method === 'view'">{{ formItem.sysrUser.username }}</span>
          <el-input
            v-else
            v-model="formItem.sysrUser.username"
            type="text"
            :placeholder="$t('ec.user.userName')"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item
          label="直接上级"
          prop="sysrUser.superior"
        >
          <ec-user-modal v-model="formItem.sysrUser.superior" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row v-if="method !== 'view'">
      <el-col :span="12">
        <el-form-item
          :label="$t('ec.login.password')"
          prop="sysrUser.loginPwd"
          :rules="method === 'edit' ? [] : [{ required: true, message: $t('ec.login.password') + $t('ec.table.emptyError'), trigger: 'blur'},
                                            { validator: validateNewPassword, trigger: 'blur'}]"
        >
          <el-input v-model="formItem.sysrUser.loginPwd" type="password" :placeholder="$t('ec.login.password')" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item
          :label="$t('ec.login.pwd.confirm')"
          prop="sysrUser.confirm"
          :rules="method === 'edit' ? [{validator: validateConfirm, trigger: 'blur'}] :
            [{ required: true, message: $t('ec.login.pwd.confirm') + $t('ec.table.emptyError'), trigger: 'blur'},
             {validator: validateConfirm, trigger: 'blur'}]"
        >
          <el-input v-model="formItem.sysrUser.confirm" type="password" :placeholder="$t('ec.login.pwd.confirm')" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item
          :label="$t('ec.user.realName')"
          prop="sysrUser.realName"
          :rules="[{ required: true, message: $t('ec.user.realName') + $t('ec.table.emptyError'), trigger: 'blur'}]">
          <span v-if="method==='view'">{{ formItem.sysrUser.realName }}</span>
          <el-input v-else v-model="formItem.sysrUser.realName" type="text" :placeholder="$t('ec.user.realName')" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.sex')" prop="sysrUser.sex">
          <ec-radio-group v-model="formItem.sysrUser.sex" dict-type="sex" :readonly="method==='view'" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-form-item :label="$t('ec.user.accessibleApp')" prop="sysrUser.accessibleAppIds">
      <template v-if="method==='view'">
        <template v-for="item in accessibleAppList">
          <span v-if="formItem.sysrUser.accessibleAppIds.indexOf(item.appId) > -1" :key="item.appId">
            {{ item.appName }}{{ formItem.sysrUser.accessibleAppIds.indexOf(item.appId)===formItem.sysrUser.accessibleAppIds.length-1 ? '': '、' }}
          </span>
        </template>
      </template>
      <el-select v-else v-model="formItem.sysrUser.accessibleAppIds" style="display: block;" clearable multiple :placeholder="$t('ec.user.accessibleApp')">
        <el-option
          v-for="item in accessibleAppList"
          :key="item.appId"
          :label="item.appName"
          :value="item.appId"
        />
      </el-select>
    </el-form-item>
    <el-form-item
      v-if="formItem.sysrUser.accessibleAppIds && formItem.sysrUser.accessibleAppIds.length > 0"
      :label="$t('ec.user.role')"
      prop="sysrUser.roleIds"
    >
      <template v-if="method==='view'">
        <template v-for="item in roleList">
          <span v-if="formItem.sysrUser.roleIds.indexOf(item.id) > -1" :key="item.id">
            {{ item.roleName }}{{ formItem.sysrUser.roleIds.indexOf(item.id)===formItem.sysrUser.roleIds.length-1 ? '': '、' }}
          </span>
        </template>
      </template>
      <el-select v-else v-model="formItem.sysrUser.roleIds" style="display: block;" clearable multiple :placeholder="$t('ec.user.role')">
        <el-option
          v-for="item in roleList"
          :key="item.id"
          :label="item.roleName"
          :value="item.id"
        />
      </el-select>
    </el-form-item>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.birthday')" prop="sysrUser.birthday">
          <span v-if="method==='view'">{{ formItem.sysrUser.birthday }}</span>
          <ec-date v-else v-model="formItem.sysrUser.birthday" format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.age')" prop="sysrUser.age" :rules="[]">
          <span v-if="method==='view'">{{ formItem.sysrUser.age }}</span>
          <el-input v-else v-model="formItem.sysrUser.age" type="number" :placeholder="$t('ec.user.age')" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.sms.phone')" prop="sysrUser.phone">
          <span v-if="method==='view'">{{ formItem.sysrUser.phone }}</span>
          <el-input v-else v-model="formItem.sysrUser.phone" type="text" :placeholder="$t('ec.sms.phone')" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.officePhone')" prop="sysrUser.officeTel">
          <span v-if="method==='view'">{{ formItem.sysrUser.officeTel }}</span>
          <el-input v-else v-model="formItem.sysrUser.officeTel" type="text" :placeholder="$t('ec.user.officePhone')" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.email.address')" prop="sysrUser.email">
          <span v-if="method==='view'">{{ formItem.sysrUser.email }}</span>
          <el-input v-else v-model="formItem.sysrUser.email" type="text" :placeholder="$t('ec.email.address')" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.entryDate')" prop="sysrUser.entryDate" :rules="[]">
          <span v-if="method==='view'">{{ formItem.sysrUser.entryDate }}</span>
          <ec-date v-else v-model="formItem.sysrUser.entryDate" format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.certType')" prop="sysrUser.typeCert">
          <ec-select v-model="formItem.sysrUser.typeCert" dict-type="typeCert" :readonly="method==='view'" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.certNo')" prop="sysrUser.certNo">
          <span v-if="method==='view'">{{ formItem.sysrUser.certNo }}</span>
          <el-input v-else v-model="formItem.sysrUser.certNo" type="text" :placeholder="$t('ec.user.certNo')" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.position')" prop="sysrUser.position" :rules="[]">
          <span v-if="method==='view'">{{ formItem.sysrUser.position }}</span>
          <el-input v-else v-model="formItem.sysrUser.position" type="text" :placeholder="$t('ec.user.position')" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.staffNo')" prop="sysrUser.staffNo" :rules="[]">
          <span v-if="method==='view'">{{ formItem.sysrUser.staffNo }}</span>
          <el-input v-else v-model="formItem.sysrUser.staffNo" type="text" :placeholder="$t('ec.user.staffNo')" />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.job')" prop="sysrUser.positionIds" :rules="[]">
          <ec-select v-model="formItem.sysrUser.positionIds" :ec-data="positionList" value-field="id" attr-name="name"
                     multiple :str-result="false" :readonly="method==='view'" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.address')" prop="sysrUser.address" :rules="[]">
          <span v-if="method==='view'">{{ formItem.sysrUser.address }}</span>
          <el-input v-else v-model="formItem.sysrUser.address" type="text" placeholder="" />
        </el-form-item>
      </el-col>
    </el-row>

    <el-form-item :label="$t('ec.user.avatar')" prop="sysrUser.avatar" :rules="[]">
      <img
        v-if="method==='view'"
        :src="formItem.sysrUser.avatar ? formItem.sysrUser.avatar : require('@/assets/img/avatar.png')"
        width="40px"
        :alt="$t('ec.user.avatar')"
      >
      <el-input v-else v-model="formItem.sysrUser.avatar" type="text" placeholder="" />
    </el-form-item>

    <el-form-item :label="$t('ec.settings.theme')" prop="sysrUser.utheme" :rules="[]">
      <span v-if="method==='view'">{{ formItem.sysrUser.utheme }}</span>
      <el-input v-else v-model="formItem.sysrUser.utheme" type="text" placeholder="" />
    </el-form-item>

    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.verifyEmail')" prop="sysrUser.mailValid" :rules="[{ required: true, message: $t('ec.table.emptyError'), trigger: 'blur'}]">
          <span v-if="method==='view'">{{ formItem.sysrUser.mailValid ? $t('ec.table.yes') : $t('ec.table.no') }}</span>
          <el-switch v-else v-model="formItem.sysrUser.mailValid" active-color="#13ce66" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.verifyPhone')" prop="sysrUser.phoneValid" :rules="[{ required: true, message: $t('ec.table.emptyError'), trigger: 'blur'}]">
          <span v-if="method==='view'">{{ formItem.sysrUser.phoneValid ? $t('ec.table.yes') : $t('ec.table.no') }}</span>
          <el-switch v-else v-model="formItem.sysrUser.phoneValid" active-color="#13ce66" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.ustatus')" prop="sysrUser.ustatus" :rules="[{ required: true, message: $t('ec.table.emptyError'), trigger: 'blur'}]">
          <ec-radio-group
            v-model="formItem.sysrUser.ustatus"
            :ec-data="[{ value: 0, label: $t('ec.user.ustatusInit') }, { value: 1, label: $t('ec.user.ustatusNormal') }, { value:2, label: $t('ec.user.ustatusFrozen') }]"
            :readonly="method === 'view'"
          />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.insider')" prop="sysrUser.insider" :rules="[{ required: true, message: $t('ec.table.emptyError'), trigger: 'blur'}]">
          <span v-if="method==='view'">{{ formItem.sysrUser.insider ? $t('ec.table.yes') : $t('ec.table.no') }}</span>
          <el-switch v-else v-model="formItem.sysrUser.insider" active-color="#13ce66" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.wxBind')" prop="sysrUser.weixinBind" :rules="[]">
          <span v-if="method==='view'">{{ formItem.sysrUser.weixinBind ? $t('ec.table.yes') : $t('ec.table.no') }}</span>
          <el-switch v-else v-model="formItem.sysrUser.weixinBind" active-color="#13ce66" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.wxPubOpenId')" prop="sysrUser.weixinMpOpenId" :rules="[]">
          <span v-if="method==='view'">{{ formItem.sysrUser.weixinMpOpenId }}</span>
          <el-input v-else v-model="formItem.sysrUser.weixinMpOpenId" type="text" placeholder="" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.table.version')" prop="sysrUser.rsvn" :rules="[{ validator: validator.number, trigger: 'blur'}]">
          <span v-if="method==='view'">{{ formItem.sysrUser.rsvn }}</span>
          <el-input v-else v-model="formItem.sysrUser.rsvn" type="text" placeholder="" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.wxProOpenId')" prop="sysrUser.weixinOpenId" :rules="[]">
          <span v-if="method==='view'">{{ formItem.sysrUser.weixinOpenId }}</span>
          <el-input v-else v-model="formItem.sysrUser.weixinOpenId" type="text" placeholder="" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.table.sort')" prop="sysrUser.sortNum">
          <span v-if="method==='view'">{{ formItem.sysrUser.sortNum }}</span>
          <el-input-number v-else v-model="formItem.sysrUser.sortNum" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('ec.user.wxUnionId')" prop="sysrUser.weixinUnionId" :rules="[]">
          <span v-if="method==='view'">{{ formItem.sysrUser.weixinUnionId }}</span>
          <el-input v-else v-model="formItem.sysrUser.weixinUnionId" type="text" placeholder="" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item :label="$t('ec.table.remarks')" prop="sysrUser.remark">
          <span v-if="method==='view'">{{ formItem.sysrUser.remark }}</span>
          <el-input v-else v-model="formItem.sysrUser.remark" type="textarea" :autosize="{minRows: 3, maxRows: 5}" />
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>

import mixin from 'ecip-web/utils/common-mixin'
import { cloneDeep } from 'ecip-web/utils'
import { getById, save, update, checkAccount } from 'ecip-web/api/vue/user/sysrUserApi'
import { findTenantAccessApps } from 'ecip-web/api/vue/app/sysrRegisterAppApi'
import { findRolesByAppIds } from 'ecip-web/api/vue/role/sysrRoleApi'
import { findPositions } from 'ecip-web/api/vue/position/sysrPositionApi'
import { password } from '@/settings'

export default {
  name: 'ModulesSystemSysrUserAloneDetailForm',
  mixins: [...mixin],
  data() {
    return {
      method: '',
      id: '',
      positionList: [],
      accessibleAppList: [],
      roleList: [],
      formItem: {
        orgIdList: '',
        sysrUser: {
          id: '',
          username: '',
          loginPwd: '',
          confirm: '',
          salt: '',
          deptId: '',
          realName: '',
          sex: '0',
          age: null,
          birthday: null,
          email: '',
          superior: null,
          entryDate: null,
          position: '',
          staffNo: '',
          typeCert: '',
          certNo: '',
          phone: '',
          officeTel: '',
          address: '',
          utheme: '',
          ustatus: 1,
          createTime: '',
          sortNum: '',
          remark: '',
          rsvn: '',
          appId: '',
          tenantId: '',
          mailValid: true,
          phoneValid: true,
          weixinBind: false,
          insider: false,
          avatar: '',
          weixinOpenId: '',
          weixinMpOpenId: '',
          weixinUnionId: '',
          accessibleAppIds: [],
          roleIds: [],
          positionIds: []
        },
        userAppDeptList: []
      }
    }
  },
  watch: {
    'formItem.sysrUser.tenantId': {
      handler(val) {
        const accessAppIds = this.formItem.sysrUser.accessibleAppIds
        if (accessAppIds && accessAppIds.length > 0) {
          this.findRolesByAppIds(val, accessAppIds)
        }
        this.findTenantAccessApps(val)
        this.findPositionList()
      },
      deep: true,
      immediate: true
    },
    'formItem.sysrUser.accessibleAppIds': {
      handler(val) {
        if (val && val.length > 0) {
          this.findRolesByAppIds(this.formItem.sysrUser.tenantId, this.formItem.sysrUser.accessibleAppIds)
        } else {
          this.roleList = []
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    async init(method, id) {
      this.method = method
      this.id = id
      this.$refs.form.clearValidate()
      Object.assign(this.$data.formItem, this.$options.data().formItem)
      if (this.method === 'edit' || this.method === 'view') { // 修改或者查看
        await this.getDataById(this.id)
      }
    },
    async findTenantAccessApps(tenantId) {
      const { data } = await findTenantAccessApps(tenantId)
      this.accessibleAppList = data
    },
    async findRolesByAppIds(tenantId, appIds) {
      const { data } = await findRolesByAppIds(tenantId, appIds)
      this.roleList = data
    },
    async findPositionList(tenantId) {
      const { data } = await findPositions({tenantId})
      this.positionList = data
    },
    async getDataById(id) {
      this.loading = true
      const { data } = await getById(id)
      cloneDeep(this.formItem, data)
      // this.formItem = data
      this.formItem.orgIdList = this.formItem.orgIdList.join()
      this.formItem.sysrUser.deptId = this.formItem.userAppDeptList.map(item => item.deptId).join()
      // 不手动set的话,el-select无法渲染回显
      this.$set(this.formItem.sysrUser, 'accessibleAppIds', data.sysrUser.accessibleAppIds)
      this.$set(this.formItem.sysrUser, 'positionIds', data.sysrUser.positionIds)
      // cloneDeep(this.formItem.sysrUser, data)
      this.loading = false //  关闭遮罩层
    },
    deptChoose(node, Node) {
      if (Node.level && Node.level !== 1) {
        this.formItem.sysrUser.deptId = node.id
      }
    },
    validateDepts(rule, value, callback) {
      const deptSet = new Set()
      const checkedNodes = this.$refs.appDeptSelect.getCheckedNodes()
      for (let i = 0; i < checkedNodes.length; i++) {
        if (deptSet.has(checkedNodes[i].appId)) {
          callback(new Error(this.$t('ec.user.deptError')))
          return
        }
        deptSet.add(checkedNodes[i].appId)
      }
      callback()
    },
    validateNewPassword(rule, value, callback) {
      if (password && password.reg) {
        if (!password.reg.test(value)) {
          callback(new Error(this.$t(password.msg) || this.$t('ec.login.pwd.regError') + password.reg))
          return
        }
      } else {
        if (value && value.length > 0 && value.length < 6) {
          callback(new Error(this.$t('ec.login.pwd.lengthError')))
          return
        }
      }
      callback()
    },
    validateConfirm(rule, value, callback) {
      if (value && !this.formItem.sysrUser.loginPwd) {
        callback(new Error(this.$t('ec.login.pwd.empty')))
      }
      if (this.formItem.sysrUser.loginPwd && !value) {
        callback(new Error(this.$t('ec.login.pwd.confirmEmpty')))
      } else if (value !== this.formItem.sysrUser.loginPwd) {
        callback(new Error(this.$t('ec.login.pwd.confirmError')))
      } else {
        callback()
      }
    },
    async validateAccount(rule, value, callback) {
      const { data } = await checkAccount(this.id || '', value)
      if (data.valid) {
        callback()
      } else {
        callback(new Error(this.$t('ec.user.userName') + this.$t('ec.table.emptyError')))
      }
    },
    async save() {
      this.formItem.userAppDeptList = []
      this.$refs.appDeptSelect.getCheckedNodes().forEach(item => {
        this.formItem.userAppDeptList.push({ appId: item.appId, deptId: item.id })
      })
      const submitResult = Object.assign({}, this.formItem)

      submitResult.orgIdList = submitResult.orgIdList ? submitResult.orgIdList.split(',') : []
      submitResult.sysrUser.deptId = ''
      const valid = await this.$refs.form.validate()
      if (valid) {
        try {
          this.saveLoading = true
          if (this.method === 'edit') {
            await update(this.id, submitResult)
          } else {
            await save(submitResult)
          }
          this.$emit('refreshData')
          this.$notify.success(this.$t('ec.table.saveSuccess'))
          return true
        } catch {
          return false
        }
      }
      return false
    }
  }
}
</script>

<style scoped>

</style>
