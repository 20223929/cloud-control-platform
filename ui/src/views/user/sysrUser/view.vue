<template>
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :span="showTree ? 6 : 0">
        <el-card shadow="never" style="max-height: 830px; overflow: auto">
          <el-input
            v-if="deptTreeList.length > 0"
            v-model="deptFilterText"
            style="margin-bottom: 15px"
            clearable
            :placeholder="$t('ec.user.filterName')"
          />
          <el-tree
            ref="deptTree"
            :props="{ label: 'name', children: 'children'} "
            :data="deptTreeList"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            node-key="id"
            highlight-current
            @node-click="deptClick"
          />
        </el-card>
      </el-col>

      <el-col :span="showTree ? 18 : 24">
        <el-card shadow="never">
          <el-form ref="searchForm" :model="queryParams" inline :label-width="'100px'">
            <el-form-item v-if="superAdmin" :label="$t('ec.user.tenant')" prop="tenantId">
              <ec-tenant v-model="queryParams.tenantId" />
            </el-form-item>
            <el-form-item :label="$t('ec.user.userName')" prop="username">
              <el-input v-model="queryParams.username" type="text" :placeholder="$t('ec.user.userName')" @keyup.enter.native="handleSearch" />
            </el-form-item>
            <el-form-item :label="$t('ec.user.realName')" prop="realName">
              <el-input v-model="queryParams.realName" type="text" :placeholder="$t('ec.user.realName')" @keyup.enter.native="handleSearch" />
            </el-form-item>
            <el-form-item :label="$t('ec.sms.phone')" prop="phone">
              <el-input v-model="queryParams.phone" type="text" :placeholder="$t('ec.sms.phone')" @keyup.enter.native="handleSearch" />
            </el-form-item>
            <el-form-item :label="$t('ec.email.address')" prop="email">
              <el-input v-model="queryParams.email" type="text" :placeholder="$t('ec.email.address')" @keyup.enter.native="handleSearch" />
            </el-form-item>
            <el-form-item :label="$t('ec.user.insider')" prop="insider">
              <el-select v-model="queryParams.insider" :placeholder="$t('ec.log.viewAll')" clearable>
                <el-option :label="$t('ec.log.onlyView') + $t('ec.user.insider')" :value="true" />
                <el-option :label="$t('ec.log.notView') + $t('ec.user.insider')" :value="false" />
              </el-select>
            </el-form-item>
            <el-form-item class="searchItem">
              <el-button type="primary" @click="handleSearch">{{ $t('ec.table.query') }}</el-button>
              <el-button type="default" @click="handleReset">{{ $t('ec.table.reset') }}</el-button>
            </el-form-item>
          </el-form>

          <vxe-grid
            id="modules.system.sysrUserAloneView-toolbar"
            ref="vxe"
            highlight-hover-row
            border
            show-overflow
            show-header-overflow
            resizable
            sync-resize
            auto-resize
            class="vxe-table-element"
            :loading="loading"
            :start-index="(page.currentPage - 1) * page.pageSize"
            :pager-config="page"
            :sort-config="{ trigger: 'cell' }"
            :columns="columns"
            :data="tableData"
            :toolbar="tableToolbar"
            @sort-change="sortMethod"
            @page-change="handlePageChange"
          >
            <template v-slot:user_toolbar_btns>
              <el-button v-permission="['user:add']" type="primary" @click="add()">{{ $t('ec.table.add') }}</el-button>
              <el-button v-permission="['user:edit']" :disabled="!onlyOne" type="warning" @click="edit()">{{ $t('ec.table.edit') }}</el-button>
              <el-button v-permission="['user:delete']" type="danger" :disabled="!oneMore" @click="remove()">{{ $t('ec.table.delete') }}</el-button>
              <el-button type="primary" :disabled="!oneMore" @click="updateSuperior">批量设置直接上级</el-button>
              <el-button @click="getData">{{ $t('ec.table.refresh') }}</el-button>
              <el-button v-permission="['user:import']" type="primary" @click="importVisible = true">{{ $t('ec.table.import') }}</el-button>
              <el-button v-permission="['user:export']" type="success" @click="exportExcel">{{ $t('ec.table.export') }}</el-button>
              <el-button @click="showTree = !showTree">{{ (showTree ? $t('ec.route.hide') : $t('ec.route.show')) + $t('ec.user.leftApp') }}</el-button>
            </template>

            <template v-slot:avatar="{ row }">
              <img class="avatar" :src="row.avatar ? row.avatar : require('@/assets/img/avatar.png')">
            </template>

            <template v-slot:insider="{ row }">
              <!--              <span v-if="">{{ row.insider ? '是' : '否' }}</span>-->
              <el-switch
                v-model="row.insider"
                :disabled="!checkPermission(['user:edit'])"
                active-color="#13ce66"
                @change="updateInsider(row)"
              />
            </template>

            <template v-slot:tenant_id="{ row }">
              <ec-tenant :value="row.tenantId" readonly />
            </template>

            <template v-slot:user_operation="{ row }">
              <!--              <el-button size="mini" type="text" @click="view(row)">查看</el-button>-->
              <el-button v-permission="['user:view']" size="mini" type="text" @click="allDetail(row.id)">{{ $t('ec.table.detail') }}</el-button>
              <el-button v-permission="['user:permission']" size="mini" type="text" @click="$refs.userAllRoleDialog.init(row)">
                {{ $t('ec.table.permission') }}
              </el-button>
              <el-button v-permission="['user:edit']" size="mini" type="text" @click="edit(row)">{{ $t('ec.table.edit') }}</el-button>
              <el-dropdown
                v-permission="['user:edit', 'user:app', 'user:role', 'user:org', 'user:dept', 'user:reset']"
                trigger="click"
                style="padding-left:10px;padding-right:10px;"
                @command="command => dropDownCmd(command, row)"
              >
                <el-button type="text" icon="el-icon-arrow-down" size="mini" />
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item v-permission="['user:delete']" command="delete">{{ $t('ec.table.delete') }}</el-dropdown-item>
                  <el-dropdown-item v-if="superAdmin" v-permission="['user:app']" command="system">{{ $t('ec.user.accessibleApp') }}</el-dropdown-item>
                  <el-dropdown-item v-permission="['user:role']" command="role">{{ $t('ec.user.assignRole') }}</el-dropdown-item>
                  <el-dropdown-item v-permission="['user:role']" command="merchant">所属商户</el-dropdown-item>
                  <el-dropdown-item v-if="superAdmin" v-permission="['user:org']" command="org">{{ $t('ec.role.orgAuth') }}</el-dropdown-item>
                  <el-dropdown-item v-permission="['user:dept']" command="dept">{{ $t('ec.role.deptAuth') }}</el-dropdown-item>
                  <el-dropdown-item v-permission="['user:reset']" command="resetPwd">{{ $t('ec.user.resetPwd') }}</el-dropdown-item>
                  <el-dropdown-item v-permission="['user:impersonate']" command="impersonate">{{ $t('ec.user.switchId') }}</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </vxe-grid>
        </el-card>

      </el-col>
    </el-row>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      :title="$t('ec.table.import')"
      :visible="importVisible"
      :upload-params="{ isNewPk: false, strategy: 'ignore' }"
      @onSuccess="getData"
      @close="importVisible = false"
    >
      <template v-slot:extra_params="{ extraParams }">
        <el-form-item :label="$t('ec.import.isNewPk')">
          <ec-radio-group v-model="extraParams.isNewPk" :ec-data="[ {value: false, label: $t('ec.table.no')}, {value: true, label: $t('ec.table.yes') } ]" />
        </el-form-item>
        <el-form-item :label="$t('ec.import.strategy')">
          <ec-radio-group v-model="extraParams.strategy" :ec-data="[ {value: 'ignore', label: $t('ec.import.ignore')}, {value: 'overwrite', label: $t('ec.import.overwrite') } ]" />
        </el-form-item>
      </template>
    </ec-import>

    <detail ref="userEditDialog" @refreshData="getData" />

    <user-role ref="userRoleDialog" />

    <user-all-role ref="userAllRoleDialog" />

    <user-app ref="userAppDialog" />

    <user-org-permission ref="userOrgPermissionDialog" />

    <user-dept-permission ref="userDeptPermissionDialog" />

    <user-superior ref="userSuperiorDialog" />

    <user-merchant ref="userMerchantDialog" @refresh="init"/>
  </div>
</template>

<script>
import mixin from 'ecip-web/utils/common-mixin'
import { deleteByIds, resetPassword, impersonate, updateInsider } from 'ecip-web/api/vue/user/sysrUserApi'
import { findAppDeptTree } from 'ecip-web/api/vue/dept/sysrDeptApi'
import { filterNode } from 'ecip-web/utils/tableUtil'
import Detail from './components/detail'
import UserRole from './components/userRole'
import UserApp from './components/userApp'
import UserOrgPermission from './components/userOrgPermission'
import UserDeptPermission from './components/userDeptPermission'
import UserAllRole from './components/userAllRole'
import UserSuperior from './components/userSuperior'
import { download } from 'ecip-web/utils'

import UserMerchant from './components/userMerchant'
import { userPage } from '@/api/modules/merchant/sysUserMerchantApi'

export default {
  name: 'ModulesSystemSysrUserAloneView',
  components: { Detail, UserRole, UserAllRole, UserApp, UserOrgPermission, UserDeptPermission, UserSuperior, UserMerchant },
  mixins: [...mixin],
  data() {
    return {
      loading: false,
      showTree: false,
      importVisible: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      deptFilterText: '', //  组织搜索过滤条件
      importUrl: 'api/v1/user/sysrUser/importExcel',
      tplUrl: 'api/v1/user/sysrUser/excelTemplate',
      exportUrl: 'api/v1/user/sysrUser/exportExcel',
      queryParams: {
        tempDept: '', //  只用作防止报错
        tenantId: '',
        merchant: '',
        appId: '',
        queryAppId: '',
        queryDeptId: '',
        username: '',
        realName: '',
        phone: '',
        email: '',
        insider: ''
      },
      deptTreeList: [], //  系统组织树
      tableData: [], //  用户列表
      selections: [], // 已选用户
      tableToolbar: {
        id: 'modules.system.sysrUserAloneView-toolbar',
        custom: true,
        slots: {
          buttons: 'user_toolbar_btns'
        },
        zoom: true, // 最大化按钮
        // 列宽操作记录
        resizable: {
          storage: true
        },
        // 列操作记录
        setting: {
          storage: true
        }
      },
      page: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        align: 'left',
        pageSizes: [10, 20, 50, 100, 200, 500],
        layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
        perfect: true,
        field: 'createTime',
        order: 'desc'
      },
      columns: [
        { type: 'checkbox', width: 40, fixed: 'left', align: 'center' },
        { title: 'ec.user.avatar', field: 'avatar', width: 55, slots: { default: 'avatar' }},
        { title: 'ec.user.userName', field: 'username', minWidth: 150, sortable: true },
        { title: 'ec.user.realName', field: 'realName', minWidth: 150, sortable: true },
        { title: 'ec.user.insider', field: 'insider', minWidth: 80, slots: { default: 'insider' }},
        { title: 'ec.sms.phone', field: 'phone', minWidth: 120, sortable: true },
        // { title: '所属商户', field: 'merchantName', minWidth: 120 },
        { title: 'ec.user.tenant', field: 'tenantId', minWidth: 150, slots: { default: 'tenant_id' }, sortable: true },
        { title: 'ec.user.org', field: 'orgName', minWidth: 120 },
        { title: 'ec.user.sex', field: 'sex', minWidth: 100, formatter: ['dict', 'sex'] },
        { title: 'ec.email.address', field: 'email', minWidth: 100 },
        { title: 'ec.table.createDate', field: 'createTime', minWidth: 150, sortable: true },
        { title: 'ec.table.actions', fixed: 'right', minWidth: 180, align: 'center', slots: { default: 'user_operation' }}
      ]
    }
  },
  computed: {
    appDeptParams() {
      return { appId: this.queryParams.queryAppId, id: this.queryParams.queryDeptId }
    }
  },
  watch: {
    // selections(val) {
    //   this.banState1 = val.length !== 1
    //   this.banState2 = val.length === 0
    // },
    deptFilterText(val) {
      this.$refs.deptTree.filter(val)
    }
  },
  mounted() {
    this.$watch(this.$refs.vxe.getCheckboxRecords, (newValue, oldValue) => {
      this.selections = newValue
      this.onlyOne = this.selections.length === 1
      this.oneMore = this.selections.length > 0
    })
  },
  created() {
    if (!this.superAdmin) {
      this.columns.splice(5, 1)
    }
    this.init()
  },
  methods: {
    init() {
      this.getData()
      this.getDeptTree()
    },
    async getDeptTree() {
      const { data } = await findAppDeptTree()
      this.deptTreeList = data
    },
    async getData() {
      this.loading = true
      const { data } = await userPage(Object.assign({},
        { size: this.page.pageSize,
          current: this.page.currentPage,
          queryDeptId: this.queryParams.queryDeptId,
          queryAppId: this.queryParams.queryAppId,
          field: this.page.field,
          order: this.page.order }),
      this.queryParams)

      this.tableData = data.records
      this.page.total = data.total
      this.loading = false
    },
    async updateInsider(row) {
      try {
        this.loading = true
        const { message } = await updateInsider(row.id, { insider: row.insider })
        this.$notify.success(message)
        await this.getData()
      } finally {
        this.loading = false
      }
    },
    deptClick(node, Node) { //  node 是当前节点数据，Node 是elmenet tree 节点对象实例
      if (Node.level && Node.level === 1) {
        this.queryParams.queryDeptId = null
        this.queryParams.queryAppId = node.id
        this.getData()
      }
      if (Node.level && Node.level !== 1) {
        this.queryParams.queryAppId = null
        this.queryParams.queryDeptId = node.id
        this.getData()
      }
    },
    // deptChoose(node, Node) {
    //   if (Node.level && Node.level === 1) {
    //     this.queryParams.queryDeptId = undefined
    //     this.queryParams.queryAppId = node.id
    //   }
    //   if (Node.level && Node.level !== 1) {
    //     this.queryParams.queryAppId = undefined
    //     this.queryParams.queryDeptId = node.id
    //   }
    // },
    deptChoose(value, Node) {
      if (value) {
        if (Node.appId) {
          this.queryParams.deptId = value
          this.queryParams.appId = ''
        } else {
          this.queryParams.appId = value
          this.queryParams.deptId = ''
        }
      } else {
        this.queryParams.deptId = ''
        this.queryParams.appId = ''
      }
    },
    filterNode(value, data, node) {
      return filterNode(value, data, node, 'name')
    },
    // 下拉菜单点击事件
    async dropDownCmd(command, row) {
      switch (command) {
        case 'delete':
          this.remove(row.id)
          break
        case 'system':
          this.$refs.userAppDialog.init(row)
          break
        case 'role':
          this.$refs.userRoleDialog.init(row)
          break
        case 'merchant':
          this.$refs.userMerchantDialog.init(row)
          break
        case 'dept':
          this.$refs.userDeptPermissionDialog.init(row)
          break
        case 'org':
          this.$refs.userOrgPermissionDialog.init(row)
          break
        case 'resetPwd':
          this.resetPwd(row.id)
          break
        case 'impersonate':
          this.impersonate(row)
          break
      }
    },
    resetPwd(id) {
      this.$confirm(this.$t('ec.user.resetPwdConfirm'), this.$t('ec.message.prompt'), {
        confirmButtonText: this.$t('ec.message.confirm'),
        cancelButtonText: this.$t('ec.message.cancel'),
        type: 'warning'
      }).then(async() => {
        await resetPassword(id)
      })
    },
    impersonate(row) {
      this.$confirm(this.$t('ec.user.switchConfirm', { name: row.realName ? row.realName : row.username }), this.$t('ec.message.prompt'), {
        confirmButtonText: this.$t('ec.message.confirm'),
        cancelButtonText: this.$t('ec.message.cancel'),
        type: 'warning'
      }).then(async() => {
        await impersonate(row.id)
        // window.location.reload()
        if (window._CONFIG['portalUrl']) {
          window.open(window._CONFIG['portalUrl'], '_blank')
        }
        window.location.replace(process.env.BASE_URL || '/')
      })
    },
    sortMethod({ column, property, order }) {
      this.page.field = property
      this.page.order = order
      this.getData()
    },
    handlePageChange({ currentPage, pageSize }) {
      this.page.currentPage = currentPage
      this.page.pageSize = pageSize
      this.getData()
    },
    add() {
      this.$refs.userEditDialog.init('add')
    },
    edit(row) {
      const user = row || this.selections[0]
      this.$refs.userEditDialog.init('edit', user)
    },
    view(row) {
      const user = row || this.selections[0]
      this.$refs.userEditDialog.init('view', user)
    },
    updateSuperior() {
      const userIds = []
      this.$refs.vxe.getCheckboxRecords().forEach(row => {
        const id = row.id
        userIds.push(id)
      })
      this.$refs.userSuperiorDialog.init(userIds)
    },
    allDetail(id) {
      this.$router.push({ path: '/user/sysrUserDetail', query: { id }})
    },
    remove(id) {
      const deleteIds = []
      if (id) {
        deleteIds.push(id)
      } else {
        this.$refs.vxe.getCheckboxRecords().forEach(row => {
          const id = row.id
          deleteIds.push(id)
        })
      }
      if (deleteIds.length > 0) {
        this.$confirm(this.$t('ec.message.delConfirm'), this.$t('ec.message.prompt'), {
          confirmButtonText: this.$t('ec.message.confirm'),
          cancelButtonText: this.$t('ec.message.cancel'),
          type: 'warning'
        }).then(async() => {
          await deleteByIds(deleteIds)
          await this.getData()
          this.$notify.success(this.$t('ec.message.delSuccess'))
        })
      }
    },
    async exportExcel() {
      this.loading = true
      await download({ url: this.exportUrl, method: 'post',
        data: Object.assign(this.queryParams, { appId: this.queryParams.appId || this.queryParams.queryAppId }),
        params: { queryDeptId: this.queryParams.queryDeptId }})
      this.loading = false
    },
    handleSearch() {
      Object.assign(this.$data.page, this.$options.data().page)
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      this.getData()
    }
  }
}
</script>

<style scoped>
  .chooseDialog >>> .el-select {
    display: block;
  }
  .vxe-table-element ::v-deep .avatar {
    width: 30px;
    display: block;
    margin: 0 auto;
  }
</style>
