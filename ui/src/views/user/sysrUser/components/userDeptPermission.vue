<template>
  <el-dialog
    :title="`${user.realName}-` + $t('ec.role.deptAuth')"
    :visible.sync="visible"
    width="700"
    class="chooseDialog"
  >
    <el-form>
      <el-row justify="center">
        <el-col :span="16" :offset="2">
          <el-form-item :label="$t('ec.app.app')" label-width="100px">
            <ec-register-app v-model="appId" filterable default-first @hasInit="getDeptTree" @change="getDeptTree" />
          </el-form-item>
        </el-col>
        <el-col :span="5" :offset="1">
          <el-form-item>
            <el-switch v-model="checkStrictly" :active-value="false" :inactive-value="true" :active-text="$t('ec.tree.activeCascade')" />
          </el-form-item>
        </el-col>
        <el-col :span="16" :offset="4">
          <el-form-item>
            <el-tree
              ref="deptTree"
              node-key="key"
              show-checkbox
              default-expand-all
              :empty-text="emptyText"
              :check-strictly="checkStrictly"
              :default-checked-keys="userDepts"
              :default-expanded-keys="userDepts"
              :data="deptTreeList"
              :props="{ label: 'title', children: 'children'} "
            />
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">{{ $t('ec.table.cancel') }}</el-button>
      <el-button type="primary" @click="confirm">{{ $t('ec.table.confirm') }}</el-button>
    </div>
  </el-dialog>

</template>

<script>
import { findTreeByAppTenant } from 'ecip-web/api/vue/dept/sysrDeptApi'
import { findUserDepts, saveUserDepts } from 'ecip-web/api/vue/user/sysrUserApi'

export default {
  name: 'UserDeptPermissionVue',
  data() {
    return {
      visible: false,
      loading: false,
      checkStrictly: false,
      treeLoading: false,
      saveLoading: false,
      emptyText: this.$t('ec.table.empty'),
      user: {},
      appId: -100,
      userDepts: [],
      deptTreeList: []
    }
  },
  watch: {
    treeLoading(val) {
      this.emptyText = val ? this.$t('ec.table.loading') : this.$t('ec.table.empty')
    }
  },
  methods: {
    init(user) {
      if (!user) {
        this.$notify.error(this.$t('ec.user.deptUserEmpty'))
      }
      this.visible = true
      this.user = user
      this.$nextTick(() => {
        this.$refs.deptTree.setCheckedKeys([])
      })
      this.getUserDepts()
    },
    async getUserDepts() {
      this.loading = true
      const { data } = await findUserDepts(this.user.id)
      this.userDepts = data
      this.loading = false
    },
    getDeptTree() {
      this.$nextTick(async() => {
        this.treeLoading = true
        const { data } = await findTreeByAppTenant(this.appId)
        this.deptTreeList = data
        this.treeLoading = false
      })
    },
    async confirm() {
      const checkedNodes = this.$refs.deptTree.getCheckedNodes()
      this.userDepts = checkedNodes.map(item => item.key)
      this.saveLoading = true
      await saveUserDepts(this.user.id, this.userDepts)
      this.saveLoading = false
      this.$notify.success(this.$t('ec.user.deptAuthSuccess'))
      this.$emit('close')
      this.visible = false
    }
  }
}
</script>

<style scoped>

</style>
