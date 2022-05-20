<template>
  <div class="app-container">
    <div v-show="showSearch" class="search-form">
      <el-form ref="searchForm" :model="queryParams" inline label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="日期"
              prop="transTime"
            >
              <el-date-picker
                v-model="queryParams.sumDateScope"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                align="right" value-format="yyyyMMdd" format="yyyy-MM-dd">
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
      :show-footer="showFooter"
      max-height="1000"
      class="vxe-table-element"
      :loading="loading"
      :start-index="(page.currentPage - 1) * page.pageSize"
      :sort-config="{ trigger: 'cell' }"
      :pager-config="page"
      :columns="columns"
      :data="tableData"
      :toolbar="tableToolbar"
      :footer-span-method="footerRowspanMethod"
      :footer-method="footerMethod"
      @sort-change="sortMethod"
      @page-change="handlePageChange"
    >
      <template v-slot:toolbar_buttons>
        <el-button type="primary" @click="queryData">查询</el-button>
        <el-button v-permission="['signStatisticReport:export']" type="primary" @click="exportExcel">导出</el-button>
        <el-button type="default" @click="handleReset">重置</el-button>
        <el-button @click="getData">刷新</el-button>
<!--        <el-button @click="showSearch = !showSearch">搜索</el-button>-->
      </template>
    </vxe-grid>
  </div>
</template>

<script>
import permissionMixin from 'ecip-web/utils/permission-mixin'
import {getData, deleteByIds} from '@/api/modules/report/signStatisticReportApi'
import {download} from 'ecip-web/utils'

export default {
  name: 'ModulesReportSignStatisticReportView',
  mixins: [permissionMixin],
  data() {
    return {
      importVisible: false,
      showSearch: true,
      showFooter: '',
      loading: false,
      onlyOne: true, // 工具栏编辑按钮是否禁用，只有当且仅当选择一条记录的时候才可编辑
      oneMore: true, //  工具栏删除按钮是否禁用，当选中条数为0的时候禁用
      exportUrl: 'api/v1/report/signStatisticReport/exportExcel',
      tableToolbar: {
        id: 'modules.report.signStatisticReportView-toolbar',
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
        {title: '序号', type: 'seq', minWidth: 180, align: 'center'},
        {
          title: '日期',
          field: 'statisticsDate',
          tableField: 'statistics_date',
          minWidth: 180,
          sortable: true,
          align: 'center'
        },
        {title: '签约机构', field: 'signInstitution', tableField: 'sign_institution', minWidth: 180, align: 'center'},
        {title: '发卡行', field: 'signBank', tableField: 'sign_bank', minWidth: 180, align: 'center'},
        {title: '新增签约用户数', field: 'increaseCount', tableField: 'increase_count', minWidth: 180, align: 'center'},
        {title: '签约总用户数', field: 'totalCount', tableField: 'total_count', minWidth: 180, align: 'center'},
        {title: '注销用户数', field: 'cancelCount', tableField: 'cancel_count', minWidth: 180, align: 'center'},
        {title: '协议正常用户数', field: 'normalOunt', tableField: 'normal_ount', minWidth: 180, align: 'center'},
      ],
      footerSum: '',
      queryParams: {
        sumDateScope: []
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
    var timeScope = []
    var now = new Date();
    var before = 6 * 24 * 60 * 60 * 1000
    timeScope.push(this.$moment(now - before).startOf('day').format('YYYYMMDD'))
    timeScope.push(this.$moment(now).endOf('day').format('YYYYMMDD'))
    this.queryParams.sumDateScope = timeScope
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
      this.footerSum = data.dataMap;
      this.showFooter = (this.tableData.length > 0);
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
    async exportExcel() {
      this.loading = true
      await download({url: this.exportUrl, method: 'post', data: this.queryParams})
      this.loading = false
    },
    queryData() {
      Object.assign(this.$data.page, this.$options.data().page)
      if (this.queryParams.sumDateScope.length > 0) {
        this.queryParams.sumDateScope[0] = this.$moment(this.queryParams.sumDateScope[0]).startOf('day').format('YYYYMMDD');
        this.queryParams.sumDateScope[1] = this.$moment(this.queryParams.sumDateScope[1]).endOf('day').format('YYYYMMDD');
      }
      this.getData()
    },
    handleReset() {
      Object.assign(this.$data.page, this.$options.data().page)
      Object.assign(this.$data.queryParams, this.$options.data().queryParams)
      var timeScope = []
      var now = new Date();
      var before = 6 * 24 * 60 * 60 * 1000
      timeScope.push(this.$moment(now - before).startOf('day').format('YYYYMMDD'))
      timeScope.push(this.$moment(now).endOf('day').format('YYYYMMDD'))
      this.queryParams.sumDateScope = timeScope
      this.getData()
    },
    footerMethod({columns, data}) {
      if (this.footerSum.sum == undefined) return []
      const footData = [
        // columns.map((column, columnIndex) => {
        //   if (columnIndex === 0) {
        //     return '小计'
        //   }
        //   if (['statisticsDate', 'signInstitution','signBank'].includes(column.property)) {
        //     return []
        //   }
        //   return XEUtils.sum(data, column.property)
        // }),
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return '合计'
          }
          if (['statisticsDate', 'signInstitution', 'signBank'].includes(column.property)) {
            return []
          }
          return this.footerSum.sum[column.property]
        })
      ]
      return footData
    },
    footerRowspanMethod({_rowIndex, _columnIndex}) {
      if (_columnIndex == 0) {
        return {rowspan: 1, colspan: 4}
      } else if (_columnIndex >= 1 && _columnIndex <= 3) {
        return {rowspan: 0, colspan: 0}
      }
    }
  }
}
</script>

<style scoped>

</style>
