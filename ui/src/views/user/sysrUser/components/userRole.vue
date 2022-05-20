<template>
  <el-dialog
    :title="`${user.realName}-` + $t('ec.user.role')"
    :visible.sync="visible"
    width="700"
    class="chooseDialog"
  >
    <el-form>
      <el-form-item :label="$t('ec.user.role')" label-width="100px">
        <el-select v-model="userRoleList" multiple clearable filterable :placeholder="$t('ec.user.role')">
          <el-option
            v-for="item in roleList"
            :key="item.id"
            :label="item.roleName"
            :value="item.id"
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

import { findRolesByUserId } from 'ecip-web/api/vue/role/sysrRoleApi'
import { findUserRoles, saveUserRoles } from 'ecip-web/api/vue/user/sysrUserApi'

export default {
  name: 'UserRole',
  data() {
    return {
      visible: false,
      loading: false,
      saveLoading: false,
      user: {},
      userRoleList: [],
      roleList: []
    }
  },
  methods: {
    init(user) {
      if (!user) {
        this.$notify.error(this.$t('ec.user.roleUserEmpty'))
      }
      this.visible = true
      this.user = user
      this.$nextTick(() => {
        this.loading = true
        Promise.all([findUserRoles(user.id), findRolesByUserId(user.id)]).then(res => {
          const [userRoleList, roleList] = res
          this.userRoleList = userRoleList.data
          this.roleList = roleList.data
          this.loading = false
        })
      })
    },
    async confirm() {
      this.saveLoading = true
      await saveUserRoles(this.user.id, this.userRoleList)
      this.saveLoading = false
      this.$notify.success(this.$t('ec.user.assignRoleSuccess'))
      this.$emit('close')
      this.visible = false
    }
  }
}
</script>

<style scoped>

</style>
