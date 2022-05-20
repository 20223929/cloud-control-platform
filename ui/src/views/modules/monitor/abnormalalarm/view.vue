<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item
              label="报警级别"
              prop="alarmLevel"
            >
              <el-select v-model="queryParams.alarmLevel" placeholder="全部">
                <el-option
                  v-for="item in alarmLevels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="处理状态"
              prop="dealState"
            >
              <el-select v-model="queryParams.dealState" placeholder="全部">
                <el-option
                  v-for="item in dealStates"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="报警时间"
              prop="alarmTime"
            >
              <el-date-picker
                v-model="queryParams.timeScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="queryParams.timeScope"
                align="right" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

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
      :start-index="(page.currentPage - 1) * page.pageSize"
      :sort-config="{ trigger: 'cell' }"
      :pager-config="page"
      :columns="columns"
      :data="tableData"
      :toolbar="tableToolbar"
      :checkbox-config="{ checkMethod: checkboxMethod}"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['abnormalAlarm:deal']" type="primary" :disabled="!oneMore" @click="dealByIds()">批量处理
        </el-button>
        <el-button v-permission="['abnormalAlarm:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['abnormalAlarm:deal']" v-if="row.dealState === 0" type="danger"
                   @click="dealByIds(row)" style="float: left">处理
        </el-button>
        <el-button v-permission="['abnormalAlarm:detail']" v-if="row.alarmType === 1" type="warning"
                   @click="blackAlarm(row)" style="float: left">详情
        </el-button>
        <el-button v-permission="['abnormalAlarm:detail']" v-if="row.alarmType === 2 && row.alarmInfo.includes('银联对账')"
                   type="warning" @click="unionPayAlarm(row)" style="float: left">详情
        </el-button>
        <el-button v-permission="['abnormalAlarm:detail']" v-if="row.alarmType === 3" type="warning"
                   @click="chargeAlarm(row)" style="float: left">详情
        </el-button>
        <el-button v-permission="['abnormalAlarm:detail']" v-if="row.alarmType === 7" type="warning"
                   @click="laneAlarm(row)" style="float: left">详情
        </el-button>
      </template>
    </vxe-grid>

    <ec-import
      :upload-url="importUrl"
      :tpl-url="tplUrl"
      title="异常报警表数据导入"
      :visible="importVisible"
      @onSuccess="getData"
      @close="importVisible = false"
    >
      <template v-slot:extra_params="{ extraParams }">
        <el-form-item label="是否生成新主键">
          <ec-radio-group v-model="extraParams.isNewPk"
                          :ec-data="[ {value: false, label: '否'}, {value: true, label: '是' } ]"/>
        </el-form-item>
        <el-form-item label="导入策略">
          <ec-radio-group v-model="extraParams.strategy"
                          :ec-data="[ {value: 'ignore', label: '忽略'}, {value: 'update', label: '更新' } ]"/>
        </el-form-item>
      </template>
    </ec-import>

    <detail ref="tbAbnormalAlarmDialog" @refreshData="getData"/>

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, dealByIds} from '@/api/modules/monitor/tbAbnormalAlarmApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'
import ChooseTransaction from "@/views/modules/complaintrefund/complaintinfomanage/shanxi/components/chooseTransaction";

export default {
  name: 'ModulesMonitorTbAbnormalAlarmView',
  components: {ChooseTransaction, Detail},
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      importUrl: 'api/v1/monitor/tbAbnormalAlarm/importExcel',
      tplUrl: 'api/v1/monitor/tbAbnormalAlarm/excelTemplate',
      exportUrl: 'api/v1/monitor/tbAbnormalAlarm/exportExcel',
      tableToolbar: {
        id: 'modules.monitor.tbAbnormalAlarmView-toolbar',
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
      page: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        align: 'left',
        pageSizes: [10, 20, 50, 100, 200, 500],
        layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
        perfect: true,
      },
      selections: [],
      tableData: [],
      columns: [
        {type: 'checkbox', width: 40, fixed: 'left', align: 'center'},
        // { title: '系统编码', field: 'sysId', tableField: 'sys_id', minWidth: 180, sortable: false },
        {
          title: '报警时间',
          field: 'alarmTime',
          tableField: 'alarm_time',
          minWidth: 180,
          sortable: true,
          formatter: ['date', 'yyyy-MM-dd HH:mm:ss']
        },
        {title: '报警级别', field: 'alarmLevelName', tableField: 'alarm_level_name', minWidth: 180, sortable: false},
        {title: '报警类型', field: 'alarmTypeName', tableField: 'alarm_type', minWidth: 180, sortable: false},
        // { title: '报警类型', field: 'alarmType', tableField: 'alarm_type', minWidth: 180, sortable: false },
        {title: '报警信息', field: 'alarmInfo', tableField: 'alarm_info', minWidth: 180, sortable: false},
        {title: '处理状态', field: 'dealStateName', tableField: 'deal_state', minWidth: 180, sortable: false},
        {title: '操作', width: 150, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        timeScope: [],
        alarmTime: '',
        alarmLevel: '',
        dealState: ''
      },
      alarmLevels: [{
        value: 1,
        label: '一般'
      }, {
        value: 2,
        label: '严重'
      }],
      dealStates: [{
        value: 0,
        label: '未处理'
      }, {
        value: 1,
        label: '已处理'
      }]
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
    var timeScope = []
    var now = new Date();
    var begin = this.$moment(now).startOf('day').add(-1).startOf('day').format('YYYY-MM-DD HH:mm:ss');

    timeScope.push(begin)
    timeScope.push(this.$moment(now).endOf('day').format('YYYY-MM-DD HH:mm:ss'))
    this.queryParams.timeScope = timeScope
    this.getData()
  },
  methods: {
    checkboxMethod({row}) {
      return row.dealState === 0
    },
    async getData() {
      // 每次查询初始化checkbox selections
      this.selections = []
      this.loading = true
      const {data} = await getData(Object.assign({}, {
        size: this.page.pageSize,
        current: this.page.currentPage,
        field: this.page.field,
        order: this.page.order
      }), this.queryParams)
      this.tableData = data.records
      this.page.total = data.total
      this.loading = false
    },
    handlePageChange({currentPage, pageSize}) {
      this.page.currentPage = currentPage
      this.page.pageSize = pageSize
      this.getData()
    },
    sortMethod({column, property, order}) {
      this.page.field = (column.own.tableField && 'a.'.concat(column.own.tableField)) || property
      this.page.order = order
      this.getData()
    },
    add() {
      this.$refs.tbAbnormalAlarmDialog.init('add')
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.tbAbnormalAlarmDialog.init('edit', record)
    },
    //路由跳转其他页面
    blackAlarm(row) {
      this.$router.push({path: '/blacklistVersionView'})
    },
    unionPayAlarm(row) {
      this.$router.push({path: '/tbBankBillCheckView', query: {confirmState: '0'}})
    },
    chargeAlarm(row) {
      this.$router.push({path: '/chargeExcepionView'})
    },
    laneAlarm(row) {
      this.$router.push({path: '/laneMonitorView'})
    },
    dealByIds(row) {
      const additionMsg = row ? '此条记录' : '选中记录'
      this.$confirm(`确定处理${additionMsg}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(async () => {
        const dealIds = []
        if (row) {
          dealIds.push(row.sysId)
        } else {
          dealIds.push(...this.selections.map(row => row.sysId))
        }
        await dealByIds(dealIds)
        await this.getData()
        this.$notify.success('处理成功')
      })
    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      if (this.queryParams.timeScope.length > 0) {
        this.queryParams.timeScope[1] = this.$moment(this.queryParams.timeScope[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss');
      }
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

</style>
