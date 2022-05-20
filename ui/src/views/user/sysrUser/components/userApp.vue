<template>
  <el-dialog
    :title="`${user.realName}-` + $t('ec.user.accessibleApp')"
    :visible.sync="visible"
    width="700"
    class="chooseDialog"
  >
    <el-form>
      <el-form-item :label="$t('ec.user.accessibleApp')" label-width="100px">
        <el-select v-model="accessedAppList" multiple clearable filterable :placeholder="$t('ec.user.accessibleApp')">
          <el-option
            v-for="item in appList"
            :key="item.appId"
            :label="item.appName"
            :value="item.appId"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">{{ $t('ec.table.cancel') }}</el-button>
      <el-button type="primary" @click="confirm">{{ $t('ec.table.confirm') }}</el-button>
    </div>
  </el-dialog>
</template>

<script>

import { findTenantAccessApps } from 'ecip-web/api/vue/app/sysrRegisterAppApi'
import { findUserApps, saveUserApps } from 'ecip-web/api/vue/user/sysrUserApi'

export default {
  name: 'UserAppVue',
  data() {
    return {
      visible: false,
      loading: false,
      saveLoading: false,
      user: {},
      accessedAppList: [],
      appList: []
    }
  },
  methods: {
    init(user) {
      if (!user) {
        this.$notify.error(this.$t('ec.user.accAppUserEmpty'))
      }
      this.visible = true
      this.user = user
      this.$nextTick(() => {
        this.loading = true
        Promise.all([findUserApps(user.id), findTenantAccessApps(user.tenantId)]).then(res => {
          const [accessedApps, apps] = res
          this.accessedAppList = accessedApps.data
          this.appList = apps.data
          this.loading = false
        })
      })
    },
    async confirm() {
      this.saveLoading = true
      await saveUserApps(this.user.id, this.accessedAppList)
      this.saveLoading = false
      this.$notify.success(this.$t('ec.user.accAppSuccess'))
      this.$emit('close')
      this.visible = false
    }
  }
}
</script>

<style scoped>

</style>
