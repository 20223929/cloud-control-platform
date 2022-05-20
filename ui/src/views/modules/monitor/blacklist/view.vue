<template>
  <div class="app-container">
    <vxe-grid
      ref="vxe"
      highlight-hover-row
      border
      resizable
      sync-resize
      auto-resize
      show-overflow
      show-header-overflow
      max-height="1000"
      class="vxe-table-element"
      :loading="loading"
      :sort-config="{ trigger: 'cell' }"
      :columns="columns"
      :data="tableData"
      :toolbar="tableToolbar"
    >
      <template v-slot:toolbar_buttons>
        <el-button @click="getData">刷新</el-button>
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import { getData } from '@/api/modules/monitor/blacklistVersionApi'
import { download } from 'ecip-web/utils'

export default {
  name: 'ModulesMonitorBlacklistVersionView',
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: false,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      exportUrl: 'api/v1/monitor/blacklistVersion/exportExcel',
      tableToolbar: {
        id: 'modules.monitor.blacklistVersionView-toolbar',
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
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
      tableData: [],
      columns: [
        {
          title: '全量ETC卡黑名单',align: 'center',minWidth: 180,
          children: [
            {
              title: '版本号',
              field: 'fullEtcBlackVersion',
              align: 'center'
            },
            {
              title: '最新更新时间',
              field: 'fullEtcBlackUpdateTime',
              align: 'center'
            },
          ]
        },
        {
          title: '全量OBU黑名单',align: 'center',minWidth: 180,
          children: [
            {
              title: '版本号',
              field: 'fullObuBlackVersion',
              align: 'center'
            },
            {
              title: '最新更新时间',
              field: 'fullObuBlackUpdateTime',
              align: 'center'
            },
          ]
        },
        {
          title: '增量ETC卡黑名单',align: 'center',minWidth: 180,
          children: [
            {
              title: '版本号',
              field: 'incrementEtcBlackVersion',
              align: 'center'
            },
            {
              title: '最新更新时间',
              field: 'incrementEtcBlackUpdateTime',
              align: 'center'
            },
          ]
        },
        {
          title: '增量OBU黑名单',align: 'center',minWidth: 180,
          children: [
            {
              title: '版本号',
              field: 'incrementObuBlackVersion',
              align: 'center'
            },
            {
              title: '最新更新时间',
              field: 'incrementObuBlackUpdateTime',
              align: 'center'
            },
          ]
        }
      ],
      queryParams: {
      }
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
    this.getData()
  },
  methods: {
    async getData() {
      this.loading = true
      const { data } = await getData(Object.assign({}, {}), this.queryParams)
      this.tableData = data
      this.loading = false
    },
    async exportExcel() {
      this.loading = true
      await download({ url: this.exportUrl, method: 'post', data: this.queryParams })
      this.loading = false
    },
    queryData() {
      this.getData()
    }
  }
}
</script>

<style scoped>

</style>
