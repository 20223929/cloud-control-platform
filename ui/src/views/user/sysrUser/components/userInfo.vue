<template>
  <div>
    <div class="account_info">
      <p class="user_title">{{ $t('ec.user.accountInfo') }}</p>

      <el-form label-suffix=":" label-width="180px">
        <el-row style="">
          <el-col :span="12">
            <el-form-item :label="$t('ec.user.tenant')">
              <ec-tenant :value="sysrUser.tenantId" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('ec.user.org')">
              {{ sysrUser.orgName }}
              <!--              <ec-org-select :value="orgIdList" multiple readonly />-->
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('ec.user.userName')">
              {{ sysrUser.username }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('ec.user.insider')">
              <el-switch
                v-model="sysrUser.insider"
                :active-value="true"
                :disabled="!checkPermission(['user:edit'])"
                :inactive-value="false"
                @change="value => updatePartInfo({ insider: value })"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

    </div>

    <p class="user_title info_title">{{ $t('ec.user.userInfo') }}</p>

    <div style="margin-bottom: 16px; display: flex; width: 100%">
      <img :src="sysrUser.avatar ? sysrUser.avatar : require('@/assets/img/avatar.png')" style=" width: 200px; float: left;" width="40px">

      <el-form label-suffix=":" label-width="120px" style="font-size: 28px; float: right;width: 100%">
        <el-row style="">
          <el-col :span="12">
            <el-form-item :label="$t('ec.user.realName')">
              {{ sysrUser.realName }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('ec.user.sex')">
              <ec-radio-group
                v-model="sysrUser.sex"
                :disabled="!checkPermission(['user:edit'])"
                dict-type="sex"
                @change="value => updatePartInfo({ sex: value })"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('ec.user.position')">
              {{ sysrUser.position }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('ec.sms.phone')">
              {{ sysrUser.phone || '-' }}
              <i
                :class="sysrUser.phone && sysrUser.phoneValid ? validRes.valid.icon : validRes.invalid.icon "
                :style="sysrUser.phone && sysrUser.phoneValid ? validRes.valid.style : validRes.invalid.style"
              />
              <span>{{ sysrUser.phone && sysrUser.phoneValid ? $t('ec.user.verified') : $t('ec.user.unverified') }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('ec.user.wxBind')">
              <el-switch
                :value="sysrUser.weixinBind"
                :active-value="true"
                :inactive-value="false"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('ec.email.address')">
              {{ sysrUser.email || '-' }}
              <i
                :class="sysrUser.email && sysrUser.mailValid ? validRes.valid.icon : validRes.invalid.icon "
                :style="sysrUser.email && sysrUser.mailValid ? validRes.valid.style : validRes.invalid.style"
              />
              <span>{{ sysrUser.email && sysrUser.mailValid ? $t('ec.user.verified') : $t('ec.user.unverified') }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form label-suffix=":" label-width="120px">
        <el-form-item :label="$t('ec.user.ustatus')">
          <ec-radio-group
            v-model="sysrUser.ustatus"
            :disabled="!checkPermission(['user:edit'])"
            :ec-data="[{ value: 0, label: $t('ec.user.ustatusInit') }, { value: 1, label: $t('ec.user.ustatusNormal') }, { value:2, label: $t('ec.user.ustatusFrozen') }]"
            @change="value => updatePartInfo({ ustatus: value })"
          />
        </el-form-item>

        <el-form-item :label="$t('ec.user.accessibleApp')">
          <div>
            <template v-for="item in accessibleAppList">
              <el-tag v-if="sysrUser.accessibleAppIds.indexOf(item.appId) > -1" :key="item.appId" style="margin-right: 4px">
                {{ item.appName }}
              </el-tag>
            </template>
          </div>
          <!--          <el-tag v-for="app in sss">{{ item.app }}</el-tag>-->
        </el-form-item>
        <div style="display: flex">
          <el-form-item :label="$t('ec.user.role')" style="float: left">
            <template v-for="item in roleList">
              <el-tag v-if="sysrUser.roleIds.indexOf(item.id) > -1" :key="item.id" style="margin-right: 4px" type="success">
                {{ item.roleName }}
              </el-tag>
            </template>

          </el-form-item>
          <el-button
            v-if="checkPermission(['user:permission'])"
            style="margin-left: 30px; position: relative; top: -10px;"
            type="text"
            @click="$refs.userAllRoleDialog.init(sysrUser)"
          >
            {{ $t('ec.table.edit') }}
          </el-button>
        </div>
      </el-form>
    </div>
    <!--    <el-card shadow="never">-->
    <!--      <div slot="header" class="clearfix">-->
    <!--        <span>卡片名称</span>-->
    <!--        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
    <!--      </div>-->
    <!--      <div v-for="o in 4" :key="o" class="text item">-->
    <!--        {{'列表内容 ' + o }}-->
    <!--      </div>-->
    <!--    </el-card>-->
    <!--    <el-card shadow="never">-->
    <!--      <div slot="header" class="clearfix">-->
    <!--        <span>卡片名称</span>-->
    <!--        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
    <!--      </div>-->
    <!--      <div v-for="o in 4" :key="o" class="text item">-->
    <!--        {{'列表内容 ' + o }}-->
    <!--      </div>-->
    <!--    </el-card>-->
    <user-all-role ref="userAllRoleDialog" @change="appRoleChange" />

  </div>
</template>

<script>
import UserAllRole from '../components/userAllRole'

import { updatePartInfo } from 'ecip-web/api/vue/user/sysrUserApi'
import { findTenantAccessApps } from 'ecip-web/api/vue/app/sysrRegisterAppApi'
import { findRolesByAppIds } from 'ecip-web/api/vue/role/sysrRoleApi'
import permissionMixin from 'ecip-web/utils/permission-mixin'

export default {
  name: 'UserInfo',
  components: { UserAllRole },
  mixins: [permissionMixin],
  data() {
    return {
      userId: '',
      sysrUser: {},
      superAdmin: false,
      orgIdList: [],
      userAppDeptList: [],
      accessibleAppList: [],
      roleList: [],
      validRes: {
        valid: {
          icon: 'el-icon-circle-check',
          style: {
            marginLeft: '8px',
            color: '#6cff50'
          }
        },
        invalid: {
          icon: 'el-icon-warning-outline',
          style: {
            marginLeft: '8px',
            color: '#ff621a'
          }
        }
      }
    }
  },
  methods: {
    async init(record) {
      Object.assign(this.$data.sysrUser, this.$options.data().sysrUser)
      const { sysrUser, orgIdList, userAppDeptList, superAdmin } = record
      this.userId = sysrUser.id
      this.sysrUser = sysrUser
      this.superAdmin = superAdmin
      this.orgIdList = orgIdList
      this.userAppDeptList = userAppDeptList
      await this.findTenantAccessApps(sysrUser.tenantId)
      await this.findRolesByAppIds(sysrUser.tenantId, sysrUser.accessibleAppIds)

      console.log(record)
    },
    async findTenantAccessApps(tenantId) {
      const { data } = await findTenantAccessApps(tenantId)
      this.accessibleAppList = data
    },
    async findRolesByAppIds(tenantId, appIds) {
      const { data } = await findRolesByAppIds(tenantId, appIds)
      this.roleList = data
    },
    appRoleChange(appIds, roleIds) {
      this.sysrUser.accessibleAppIds = appIds
      this.sysrUser.roleIds = roleIds
    },
    async updatePartInfo(propObj) {
      try {
        this.loading = true
        await updatePartInfo(this.userId, propObj)
        this.$notify.success(this.$t('ec.message.saveSuccess'))
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.user_title {
  border-bottom: 2px solid #E4E7ED;
  font-size: 20px;
  font-weight: bold;
  margin:  0 0 24px 0;
  padding: 0 0 16px 0;
}

.info_title {
  border-top: 2px solid #E4E7ED;
  padding-top: 16px;
}

</style>
