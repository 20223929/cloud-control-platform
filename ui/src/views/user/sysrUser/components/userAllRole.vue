<template>
  <div>
    <el-dialog
      :title="`${record.realName}-` + $t('ec.table.permission')"
      :visible.sync="visible"
      width="80%"
    >
      <el-form v-loading="loading" label-suffix=":">
        <el-form-item :label="$t('ec.user.accessibleApp')" label-width="100px">
          <el-select
            v-model="accessedAppList"
            style="display: block"
            multiple
            clearable
            filterable
            :placeholder="$t('ec.user.accessibleApp')"
            @change="selectChange"
          >
            <el-option
              v-for="item in appList"
              :key="item.appId"
              :label="item.appName"
              :value="item.appId"
            />
          </el-select>
        </el-form-item>
        <div v-loading="loading" style="max-height: 1000px;overflow: auto">
          <el-card v-for="(item, index) in appRoles" :key="item.appId" style="margin-top: 20px">
            <div slot="header" class="clearfix">
              <span><strong>{{ item.appName }}</strong></span>
            </div>
            <div v-if="item.roleList && item.roleList.length === 0">
              <el-alert :closable="false" type="info" :title="$t('ec.app.noRole')" show-icon />
            </div>
            <div v-else>
              <el-form-item :label="$t('ec.user.role')" :inline="true" style="margin-bottom: 0">
                <el-checkbox-group v-model="appRoles[index].userRoles" style="display: inline-block;">
                  <el-checkbox v-for="role in item.roleList" :key="role.id" :label="role.id">{{ role.roleName }}</el-checkbox>
                </el-checkbox-group>
              </el-form-item>

            </div>
          </el-card>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visible = false">{{ $t('ec.table.close') }}</el-button>
        <el-button type="primary" :loading="saveLoading" @click="save">{{ $t('ec.table.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { findTenantAccessApps } from 'ecip-web/api/vue/app/sysrRegisterAppApi'
import { findUserApps, findAppRoles, saveUserApps, saveUserRoles } from 'ecip-web/api/vue/user/sysrUserApi'
export default {
  name: 'UserAllRoleVue',
  data() {
    return {
      appLoading: false,
      saveLoading: false,
      visible: false,
      loading: false,
      record: {},
      appRoles: [],
      accessedAppList: [],
      appList: []
    }
  },
  methods: {
    init(row) {
      this.visible = true
      this.record = row
      this.$nextTick(async() => {
        this.appLoading = true
        await this.findTenantAccessApps(row.tenantId)
        await this.findUserApps(row.id)
        await this.findAppRoles(row.id, row.tenantId, this.accessedAppList)
        this.loading = false
      })
    },
    async findTenantAccessApps(tenantId) {
      const { data } = await findTenantAccessApps(tenantId)
      this.appList = data
    },
    async findUserApps(userId) {
      const { data } = await findUserApps(userId)
      this.accessedAppList = data
    },
    async findAppRoles(userId, tenantId, accessedAppList) {
      try {
        this.loading = true
        const { data } = await findAppRoles(userId, tenantId, accessedAppList)
        this.appRoles = data
      } finally {
        this.loading = false
      }
    },
    async selectChange(e) {
      // 过滤已存在的
      const existedAppRoles = this.appRoles.filter(item => e.includes(item.appId))
      const existedAppIds = existedAppRoles.map(item => item.appId)

      const unExistedAppIds = e.filter(appId => !existedAppIds.includes(appId))

      // 封装新增选中
      if (unExistedAppIds && unExistedAppIds.length > 0) {
        this.loading = true
        const { data } = await findAppRoles(this.record.id, this.record.tenantId, unExistedAppIds)
        this.loading = false
        this.appRoles = existedAppRoles.concat(data)
        return
      }
      // 设置结果
      this.appRoles = existedAppRoles
    },
    async save() {
      const appIds = []
      let roleIds = []
      this.appRoles.forEach(item => {
        appIds.push(item.appId)
        roleIds = roleIds.concat(item.userRoles)
      })
      this.saveLoading = true
      await saveUserApps(this.record.id, appIds)
      await saveUserRoles(this.record.id, roleIds)
      this.$emit('change', appIds, roleIds)
      this.$notify.success(this.$t('ec.user.userPermissionSuccess'))
      this.visible = false
      this.saveLoading = false
    }
  }
}
</script>

<style scoped>

</style>
