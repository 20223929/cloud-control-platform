<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="运营方" prop="merchantId">
              <ec-select-tree
                ref="merchantTreeData"
                v-model="queryParams.merchantId"
                url="api/v1/merchant/tbMerchant/data"
                value-field="id"
                attr-name="name"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="状态"
              prop="status"
            >
              <el-select v-model="queryParams.status">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >

                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="时间"
              prop="timeScope"
            >
              <el-date-picker
                v-model="queryParams.timeScope"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :key="queryParams.transTime"
                align="right" value-format="yyyy-MM-dd HH:mm:ss">
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
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['tbServiceProportion:add']" type="primary" @click="add()">添加</el-button>
        <el-button v-permission="['tbServiceProportion:edit']" type="warning" :disabled="!onlyOne" @click="edit()">修改
        </el-button>
        <el-button v-permission="['tbServiceProportion:enable']" type="danger" :disabled="!oneMore" @click="close()">
          禁用
        </el-button>
        <el-button v-permission="['tbServiceProportion:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
        <!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
      <template v-slot:operation="{ row }">
        <el-button v-permission="['tbServiceProportion:enable']" v-show="row.status==0" type="warning"
                   @click="close(row)">禁用
        </el-button>
        <el-button v-permission="['tbServiceProportion:enable']" v-show="row.status==1" type="warning"
                   @click="open(row)">启用
        </el-button>
        <el-button v-permission="['tbServiceProportion:edit']" type="warning" @click="edit(row)">修改</el-button>
        <el-button v-permission="['tbServiceProportion:delete']" type="danger" @click="deleteByIds(row)">删除</el-button>
      </template>
    </vxe-grid>

    <detail ref="tbServiceProportionDialog" @refreshData="getData"/>

  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData} from '@/api/modules/systemmanage/shanxi/tbServiceProportionApi'
import Detail from './components/detail'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesServiceproportionTbServiceProportionView',
  components: {Detail},
  mixins: [permissionMixin],
  data() {
    return {
      options: [
        {
          value: '',
          label: '全部'
        },
        {
          value: '0',
          label: '启用'
        },
        {
          value: '1',
          label: '禁用'
        }
      ],
      showSearch: true,
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      exportUrl: 'api/v1/serviceproportion/tbServiceProportion/exportExcel',
      tableToolbar: {
        id: 'modules.serviceproportion.tbServiceProportionView-toolbar',
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
        {title: '序号', type: 'seq', width: 80, fixed: 'left', align: 'center'},
        {
          title: '渠道方分成比例',
          field: 'channelProportion',
          tableField: 'channel_proportion',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '平台方分成比例',
          field: 'platformProportion',
          tableField: 'platform_proportion',
          minWidth: 180,
          align: 'center'
        },
        {
          title: '拓展方分成比例',
          field: 'merchantGroupProportion',
          tableField: 'merchant_group_proportion',
          minWidth: 180,
          align: 'center'
        },
        {title: '版本号', field: 'version', tableField: 'version', minWidth: 180, align: 'center'},
        {title: '开始时间', field: 'startTime', tableField: 'start_time', minWidth: 180, align: 'center'},
        {title: '结束时间', field: 'endTime', tableField: 'end_time', minWidth: 180, align: 'center'},
        {title: '登记人', field: 'registrant', tableField: 'registrant', minWidth: 180, align: 'center'},
        {title: '登记时间', field: 'registerTime', tableField: 'register_time', minWidth: 180, align: 'center'},
        {title: '状态', field: 'status', tableField: 'status', minWidth: 180, align: 'center'},
        {title: '操作', width: 150, fixed: 'right', align: 'center', slots: {default: 'operation'}}
      ],
      queryParams: {
        merchantId: '',
        timeScope: [],
        status: '',
        nodeLevel: ''
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
      this.$refs.tbServiceProportionDialog.init('add')
    },
    edit(row) {
      const record = row || this.selections[0]
      this.$refs.tbServiceProportionDialog.init('edit', record)
    },
    deleteByIds(row) {

    },
    open(row) {

    },
    close(row) {

    },
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      if (this.$refs.merchantTreeData.getCurrentNode() != undefined) {
        this.queryParams.nodeLevel = this.$refs.merchantTreeData.getCurrentNode().nodeLevel
      } else {
        this.queryParams.nodeLevel = ''
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
