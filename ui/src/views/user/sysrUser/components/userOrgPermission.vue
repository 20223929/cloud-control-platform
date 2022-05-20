<template>
  <el-dialog
    :title="`${user.realName}-` + $t('ec.role.orgAuth')"
    :visible.sync="visible"
    width="700"
    class="chooseDialog"
  >
    <el-form>
      <el-row justify="center">
        <el-col :span="16" :offset="2">
          <el-form-item :label="$t('ec.user.tenant')" label-width="100px">
            <ec-tenant v-model="tenantId" filterable />
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
              ref="orgTree"
              node-key="key"
              show-checkbox
              default-expand-all
              :empty-text="emptyText"
              :check-strictly="checkStrictly"
              :default-checked-keys="userOrgs"
              :default-expanded-keys="userOrgs"
              :data="orgTreeList"
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
import { findTreeByTenant as findOrgTreeByTenant } from 'ecip-web/api/vue/org/saasOrgApi'
import { findUserOrgs, saveUserOrgs } from 'ecip-web/api/vue/user/sysrUserApi'

export default {
  name: 'UserOrgPermissionVue',
  data() {
    return {
      visible: false,
      loading: false,
      checkStrictly: false,
      treeLoading: false,
      saveLoading: false,
      emptyText: this.$t('ec.table.empty'),
      user: {},
      tenantId: '',
      tenantList: [],
      userOrgs: [],
      orgTreeList: []
    }
  },
  watch: {
    tenantId: {
      handler(val) {
        this.getOrgTree(val)
      },
      immediate: true
    },
    treeLoading(val) {
      this.emptyText = val ? his.$t('ec.table.loading') : this.$t('ec.table.empty')
    }
  },
  methods: {
    init(user) {
      if (!user) {
        this.$notify.error(this.$t('ec.user.orgUserEmpty'))
      }
      this.visible = true
      this.user = user
      this.$nextTick(() => {
        this.$refs.orgTree.setCheckedKeys([])
      })
      this.getUserOrgs()
    },
    async getUserOrgs() {
      this.loading = true
      const { data } = await findUserOrgs(this.user.id)
      this.userOrgs = data
      this.loading = false
    },
    async getOrgTree(tenantId) {
      this.treeLoading = true
      const { data } = await findOrgTreeByTenant({ tenantId: tenantId })
      this.orgTreeList = data
      this.treeLoading = false
    },
    async confirm() {
      const checkedNodes = this.$refs.orgTree.getCheckedNodes()
      this.userOrgs = checkedNodes.map(item => item.key)
      this.saveLoading = true
      await saveUserOrgs(this.user.id, this.userOrgs)
      this.saveLoading = false
      this.$notify.success(this.$t('ec.user.orgAuthSuccess'))
      this.$emit('close')
      this.visible = false
    }
  }
}
</script>

<style scoped>

</style>
