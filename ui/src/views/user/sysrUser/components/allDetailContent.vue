<template>
  <div class="app-container">
    <el-card shadow="never">
      <el-tabs v-model="activeTab">
        <el-tab-pane :label="$t('ec.user.basicInfo')" name="1">
          <!--          <detail-form ref="detailForm" :form-item="formItem" :method="method" />-->
          <user-info ref="userInfo" :form-item="formItem" />
        </el-tab-pane>

        <el-tab-pane :label="$t('ec.user.useBehavior')" name="2">

          <el-row v-loading="loginStatLoading" style="background:#fff;padding:16px 16px 0;">
            <div style="color: #344b58;font-size: 20px">
              <i class="el-icon-s-custom" />
              {{ formItem.sysrUser && formItem.sysrUser.realName || '' }}{{ $t('ec.user.loginTrend') }}
            </div>

            <aside style="margin: 10px 0">
              <div>
                <span>{{ $t('ec.user.recentLoginDesc', {days: $moment(loginStatParams.searchDate[1]).diff($moment(loginStatParams.searchDate[0]), 'days') + 1, loginDays: loginStat.allCount, lastLoginDate: loginStat.yesterday }) }}</span>
              </div>
            </aside>

            <div>
              <!--<el-checkbox v-model="loginStatParams.filterWorkDay" style="margin-right: 10px" @change="loadLoginStat">不看周末</el-checkbox>-->
              <el-date-picker
                v-model="loginStatParams.searchDate"
                type="daterange"
                align="left"
                unlink-panels
                :range-separator="$t('ec.date.rangeSeparator')"
                :start-placeholder="$t('ec.date.startDate')"
                :end-placeholder="$t('ec.date.endDate')"
                :clearable="false"
                :picker-options="pickerOptions"
                @change="loadLoginStat"
              />
            </div>

            <login-line-chart v-if="activeTab === '2'" ref="chart" :total-name="$t('ec.user.loginPv')" height="300px" />
          </el-row>

          <el-row style="padding: 16px 16px 30px;">
            <div style="color: #344b58;font-size: 20px">
              <i class="fa fa-eye" />
              {{ formItem.sysrUser && formItem.sysrUser.realName || '' }}{{ $t('ec.user.visitHistory') }}
            </div>
            <div style="margin-top: 10px">
              <el-date-picker
                v-model="visitStatSearchDate"
                type="daterange"
                align="left"
                unlink-panels
                :range-separator="$t('ec.date.rangeSeparator')"
                :start-placeholder="$t('ec.date.startDate')"
                :end-placeholder="$t('ec.date.endDate')"
                :clearable="false"
                :picker-options="pickerOptions"
                @change="getVisitStat"
              />
            </div>
            <div v-infinite-scroll="loadVisitStat" class="infinite-scroll-div" style="height: 600px">
              <el-timeline>
                <el-timeline-item
                  v-for="(value, key) in visitStat"
                  :key="key"
                  :timestamp="key"
                  placement="top"
                  color="#0088fe"
                >
                  <el-card>
                    <p v-for="(item, index) in value" :key="index" style="margin: 5px 0 5px">
                      <label style="margin-right: 30px;text-align: right;font-weight: normal;display: inline-block;">
                        {{ item.createDate.substring(11) }}
                      </label>
                      <span>{{ $t('ec.user.visitDetail', {appName: item.appName, module: item.name}) }}</span>
                    </p>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </div>
          </el-row>

        </el-tab-pane>

        <el-tab-pane :label="$t('ec.user.infoUpdateHistory')" name="3">
          <div style="margin-top: 20px">
            <el-date-picker
              v-model="operationSearchDate"
              type="daterange"
              align="left"
              unlink-panels
              :range-separator="$t('ec.date.rangeSeparator')"
              :start-placeholder="$t('ec.date.startDate')"
              :end-placeholder="$t('ec.date.endDate')"
              :clearable="false"
              :picker-options="pickerOptions"
              @change="getOperation"
            />
          </div>
          <div v-infinite-scroll="loadOperation" class="infinite-scroll-div">
            <el-timeline>
              <el-timeline-item
                v-for="(value, key) in operation"
                :key="key"
                :timestamp="key"
                placement="top"
                color="#0088fe"
              >
                <el-card>
                  <p v-for="(item, index) in value" :key="index" style="margin: 5px 0 5px">
                    <label style="margin-right: 30px;text-align: right;font-weight: normal;display: inline-block;">
                      {{ item.optTime.substring(11) }}
                    </label>
                    <span>{{ item.realName }}</span>
                    <span style="margin-left: 20px;">{{ item.description }}</span>
                  </p>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>

import DetailForm from './detailForm'
import UserInfo from './userInfo'
import LoginLineChart from 'ecip-web/views/dashboard/admin/components/LoginLineChart'
import { getById } from 'ecip-web/api/vue/user/sysrUserApi'
import { userLoginStat } from 'ecip-web/api/dashboard'
import { getUserData as getVisitStat } from 'ecip-web/api/web/stat/visit/ecipVisitStatApi'
import { getData as getOperation } from 'ecip-web/api/vue/log/oprationLogApi'

export default {
  name: 'ModulesSystemSysrUserAloneAllDetail',
  components: { DetailForm, UserInfo, LoginLineChart },
  data() {
    return {
      id: '',
      activeTab: '1',
      formItem: {},
      pickerOptions: {
        shortcuts: [{
          text: this.$t('ec.date.today'),
          onClick(picker) {
            const start = new Date(new Date(new Date().toLocaleDateString()).getTime()) // 00:00:00
            const end = new Date(start.getTime() + 24 * 60 * 60 * 1000 - 1) // 23:59:59
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('ec.date.yesterday'),
          onClick(picker) {
            const end = new Date(new Date(new Date().toLocaleDateString()).getTime() - 1)
            const start = new Date(end.getTime() + 1 - 24 * 60 * 60 * 1000)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('ec.date.past7Days'),
          onClick(picker) {
            const end = new Date()
            const start = new Date(end.getTime() - 3600 * 1000 * 24 * 6)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('ec.date.past14Days'),
          onClick(picker) {
            const end = new Date()
            const start = new Date(end.getTime() - 3600 * 1000 * 24 * 13)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('ec.date.past30Days'),
          onClick(picker) {
            const end = new Date()
            const start = new Date(end.getTime() - 3600 * 1000 * 24 * 29)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('ec.date.past90Days'),
          onClick(picker) {
            const end = new Date()
            const start = new Date(end.getTime() - 3600 * 1000 * 24 * 89)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('ec.date.past180Days'),
          onClick(picker) {
            const end = new Date()
            const start = new Date(end.getTime() - 3600 * 1000 * 24 * 179)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      loginStatLoading: true,
      loginStatParams: {
        filterWorkDay: false,
        searchDate: []
      },
      loginStat: {
        date: [],
        pc: [],
        app: [],
        total: [],
        yesterday: '',
        allCount: 0
      },
      visitStatCurPage: 1,
      visitStatSearchDate: [],
      visitStatEnd: false,
      visitStat: {},
      operationCurPage: 1,
      operationSearchDate: [],
      operationEnd: false,
      operation: {}
    }
  },
  watch: {
    activeTab(val) {
      if (val === '2') {
        this.loadLoginStat()
        this.getVisitStat()
      } else if (val === '3') {
        this.getOperation()
      }
    }
  },
  created() {
    this.id = this.$route.query.id
    if (this.id) {
      this.getBasicInfo()
      this.initDate()
    }
  },
  methods: {
    tabClick(tab) {
      if (tab.name === '2') {
        this.loadLoginStat()
      }
    },
    async getBasicInfo() {
      const { data } = await getById(this.id)
      this.formItem = data
      // this.$refs.detailForm.init('view', this.id)
      this.$refs.userInfo.init(data)
    },
    initDate() {
      const end = new Date()
      const start = this.$moment().subtract(29, 'days').toDate()
      this.loginStatParams.searchDate = [start, end]
      this.visitStatSearchDate = [start, end]
      this.operationSearchDate = [start, end]
    },
    loadLoginStat() {
      const start = this.$moment(this.loginStatParams.searchDate[0]).format('YYYY-MM-DD')
      const end = this.$moment(this.loginStatParams.searchDate[1]).format('YYYY-MM-DD')
      this.loginStatLoading = true
      userLoginStat({
        start: start,
        end: end,
        filterWorkDay: this.loginStatParams.filterWorkDay,
        userId: this.id
      }).then(res => {
        this.loginStatLoading = false
        this.loginStat = res.data
        this.$refs.chart.setData(res.data)
      })
    },
    loadVisitStat() {
      if (this.visitStatEnd) {
        return
      }
      this.visitStatCurPage += 1
      this.getVisitStat()
    },
    getVisitStat() {
      const start = this.$moment(this.visitStatSearchDate[0]).format('YYYY-MM-DD')
      const end = this.$moment(this.visitStatSearchDate[1]).format('YYYY-MM-DD')
      getVisitStat({ size: 10, current: this.visitStatCurPage, userId: this.id, start, end }).then(res => {
        this.visitStatEnd = !res.data.hasNext
        res.data.records.forEach(item => {
          const date = item.createDate.substring(0, 10)
          if (!(date in this.visitStat)) {
            this.$set(this.visitStat, date, [])
          }
          this.visitStat[date].push(item)
        })
      })
    },
    loadOperation() {
      if (this.operationEnd) {
        return
      }
      this.operationCurPage += 1
      this.getOperation()
    },
    getOperation() {
      const params = {
        size: 20,
        current: this.operationCurPage,
        field: 'createDate',
        order: 'desc'
      }
      const requestBody = {
        beginOptTime: this.$moment(this.operationSearchDate[0]).format('YYYY-MM-DD HH:mm:ss'),
        endOptTime: this.$moment(this.operationSearchDate[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss'),
        recordId: this.id
      }
      getOperation(params, requestBody).then(res => {
        this.operationEnd = !res.data.hasNext
        res.data.records.forEach(item => {
          const date = item.optTime.substring(0, 10)
          if (!(date in this.operation)) {
            this.$set(this.operation, date, [])
          }
          this.operation[date].push(item)
        })
      })
    }
  }
}
</script>

<style scoped>
::v-deep .el-timeline-item__timestamp.is-top{
  color: #303133;
  font-size: 18px;
}
.infinite-scroll-div {
  overflow: auto;
  height: 65vh;
  margin-top: 10px;
  padding: 20px
}
</style>
