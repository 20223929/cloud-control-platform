const app = require('./app')
const area = require('./area')
const dept = require('./dept')
const login = require('./login')
const org = require('./org')
const role = require('./role')
const tenant = require('./tenant')
const user = require('./user')
const visit = require('./visit')
const resource = require('./resource')
const dict = require('./dict')

module.exports = [
  ...app,
  ...area,
  ...dept,
  ...login,
  ...org,
  ...role,
  ...tenant,
  ...user,
  ...visit,
  ...resource,
  ...dict,
]

// // 登录验证码
// const loginCaptcha = {
//   code: 200,
//   data: {
//     captcha: 'data:image/png;base64,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',
//     key: '4580b7d0f66fe304e03284a3ceed02c4'
//   },
//   message: '操作成功',
//   success: true
// }
//
// const loginSuccess = {
//   "code" : 200,
//   "message" : "登陆成功",
//   "data" : {
//     "token" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6LTUsInRlbmFudElkIjoiZmFsc2UiLCJleHAiOjE2MTE3MzgwNTksInVzZXJuYW1lIjoiYWRtaW4ifQ.mu0dFceVbRU1Lmlmg2lbunzjkOpwcfNsWnbuHJ6w0Ts"
//   },
//   "success" : true
// }
//
// const userInfo = {
//   "code" : 200,
//   "message" : "操作成功",
//   "data" : {
//     "userInfo" : {
//       "id" : "-1",
//       "username" : "admin",
//       "loginPwd" : "7c2b4d2be8d60944b9bf8c970c525478",
//       "isSuperUser" : true,
//       "realName" : "admin",
//       "sex" : 0,
//       "email" : "songlubiao@qq.com",
//       "position" : "收费员1",
//       "phone" : "15889930671",
//       "createTime" : "2018-08-27 15:00:40",
//       "appId" : -1,
//       "loginFlag" : true,
//       "tenantId" : "1249628559274672128",
//       "childTenantIdList" : [ ],
//       "orgId" : "1253161121011990528",
//       "mailValid" : true,
//       "phoneValid" : true
//     },
//     "orgName" : "桂阳路",
//     "sex" : 0,
//     "roles" : [ "tenant:copy", "user:permission", "tenant:app", "role:user", "user:role", "user:dept", "user:org", "user:reset", "user:app", "resource:sync", "resource:copy", "resource:route", "resource:defaultPermission", "role:copy", "role:resource:assign", "role:resource:cancel", "role:dept:assign", "role:datarule:assign", "role:org:assign", "app:tenant", "resource:fixPath", "testProcOrder:add", "testProcOrder:remove", "testProcOrder:edit", "testProcOrder:view", "testProcOrder:import", "testProcOrder:export", "testProcOrder:print", "testProcOrder:download", "testProcOrder:upload", "testProcOrder:delete", "testSingleTBDemo1:add", "testSingleTBDemo1:remove", "testSingleTBDemo1:edit", "testSingleTBDemo1:view", "testSingleTBDemo1:import", "testSingleTBDemo1:export", "testSingleTBDemo1:print", "testSingleTBDemo1:download", "testSingleTBDemo1:upload", "testSingleTBDemo1:delete", "multiPkSingleTable:add", "multiPkSingleTable:remove", "multiPkSingleTable:edit", "multiPkSingleTable:view", "multiPkSingleTable:import", "multiPkSingleTable:export", "multiPkSingleTable:print", "multiPkSingleTable:download", "multiPkSingleTable:upload", "multiPkSingleTable:delete", "testMainTB:add", "testMainTB:remove", "testMainTB:edit", "testMainTB:view", "testMainTB:import", "testMainTB:export", "testMainTB:print", "testMainTB:download", "testMainTB:upload", "testMainTB:delete", "singleTableComponents:add", "singleTableComponents:remove", "singleTableComponents:edit", "singleTableComponents:view", "singleTableComponents:import", "singleTableComponents:export", "singleTableComponents:print", "singleTableComponents:download", "singleTableComponents:upload", "singleTableComponents:delete", "ecipVisitStat:add", "ecipVisitStat:remove", "ecipVisitStat:edit", "ecipVisitStat:view", "ecipVisitStat:import", "ecipVisitStat:export", "ecipVisitStat:print", "ecipVisitStat:download", "ecipVisitStat:upload", "ecipVisitStat:delete", "testTreeTB:add", "testTreeTB:remove", "testTreeTB:edit", "testTreeTB:view", "testTreeTB:import", "testTreeTB:export", "testTreeTB:print", "testTreeTB:download", "testTreeTB:upload", "testTreeTB:delete", "testLeftTree:add", "testLeftTree:remove", "testLeftTree:edit", "testLeftTree:view", "testLeftTree:import", "testLeftTree:export", "testLeftTree:print", "testLeftTree:download", "testLeftTree:upload", "testLeftTree:delete", "testRightTb:add", "testRightTb:remove", "testRightTb:edit", "testRightTb:view", "testRightTb:import", "testRightTb:export", "testRightTb:print", "testRightTb:download", "testRightTb:upload", "testRightTb:delete", "testCategory:add", "testCategory:remove", "testCategory:edit", "testCategory:view", "testCategory:import", "testCategory:export", "testCategory:print", "testCategory:download", "testCategory:upload", "testCategory:delete", "testGoods:add", "testGoods:remove", "testGoods:edit", "testGoods:view", "testGoods:import", "testGoods:export", "testGoods:print", "testGoods:download", "testGoods:upload", "testGoods:delete", "testCountry:add", "testCountry:remove", "testCountry:edit", "testCountry:view", "testCountry:import", "testCountry:export", "testCountry:print", "testCountry:download", "testCountry:upload", "testCountry:delete", "testContinent:add", "testContinent:remove", "testContinent:edit", "testContinent:view", "testContinent:import", "testContinent:export", "testContinent:print", "testContinent:download", "testContinent:upload", "testContinent:delete", "testStudent:add", "testStudent:remove", "testStudent:edit", "testStudent:view", "testStudent:import", "testStudent:export", "testStudent:print", "testStudent:download", "testStudent:upload", "testStudent:delete", "testCourse:add", "testCourse:remove", "testCourse:edit", "testCourse:view", "testCourse:import", "testCourse:export", "testCourse:print", "testCourse:download", "testCourse:upload", "testCourse:delete", "testStudentCourse:add", "testStudentCourse:remove", "testStudentCourse:edit", "testStudentCourse:view", "testStudentCourse:import", "testStudentCourse:export", "testStudentCourse:print", "testStudentCourse:download", "testStudentCourse:upload", "testStudentCourse:delete", "genFormCategory:add", "genFormCategory:remove", "genFormCategory:edit", "genFormCategory:view", "genFormCategory:import", "genFormCategory:export", "genFormCategory:print", "genFormCategory:download", "genFormCategory:upload", "genFormCategory:delete", "genFormMain:add", "genFormMain:remove", "genFormMain:edit", "genFormMain:view", "genFormMain:import", "genFormMain:export", "genFormMain:print", "genFormMain:download", "genFormMain:upload", "genFormMain:delete", "ecipVisitStat:repair", "genFormContent:add", "genFormContent:remove", "genFormContent:edit", "genFormContent:view", "genFormContent:import", "genFormContent:export", "genFormContent:print", "genFormContent:download", "genFormContent:upload", "genFormContent:delete", "testEditor:add", "testEditor:remove", "testEditor:edit", "testEditor:view", "testEditor:import", "testEditor:export", "testEditor:print", "testEditor:download", "testEditor:upload", "testEditor:delete", "genFormContentTemplate:add", "genFormContentTemplate:remove", "genFormContentTemplate:edit", "genFormContentTemplate:view", "genFormContentTemplate:import", "genFormContentTemplate:export", "genFormContentTemplate:print", "genFormContentTemplate:download", "genFormContentTemplate:upload", "genFormContentTemplate:delete", "user:impersonate", "oprationLog:print", "oprationLog:download", "tenant:add", "tenant:remove", "tenant:edit", "tenant:view", "tenant:import", "tenant:export", "tenant:print", "tenant:download", "tenant:upload", "tenant:delete", "org:add", "org:remove", "org:edit", "org:view", "org:import", "org:export", "org:print", "org:download", "org:upload", "org:delete", "dept:add", "dept:remove", "dept:edit", "dept:view", "dept:import", "dept:export", "dept:print", "dept:download", "dept:upload", "dept:delete", "user:add", "user:remove", "user:edit", "user:view", "user:import", "user:export", "user:print", "user:download", "user:upload", "user:delete", "role:add", "role:remove", "role:edit", "role:view", "role:import", "role:export", "role:print", "role:download", "role:upload", "role:delete", "resource:add", "resource:remove", "resource:edit", "resource:view", "resource:import", "resource:export", "resource:print", "resource:download", "resource:upload", "resource:delete", "dict:add", "dict:remove", "dict:edit", "dict:view", "dict:import", "dict:export", "dict:print", "dict:download", "dict:upload", "dict:delete", "area:add", "area:remove", "area:edit", "area:view", "area:import", "area:export", "area:print", "area:download", "area:upload", "area:delete", "app:add", "app:remove", "app:edit", "app:view", "app:import", "app:export", "app:print", "app:download", "app:upload", "app:delete", "datarule:add", "datarule:remove", "datarule:edit", "datarule:view", "datarule:import", "datarule:export", "datarule:print", "datarule:download", "datarule:upload", "datarule:delete", "datasource:add", "datasource:remove", "datasource:edit", "datasource:view", "datasource:import", "datasource:export", "datasource:print", "datasource:download", "datasource:upload", "datasource:delete", "syncRule:add", "syncRule:remove", "syncRule:edit", "syncRule:view", "syncRule:import", "syncRule:export", "syncRule:print", "syncRule:download", "syncRule:upload", "syncRule:delete", "genQueryType:add", "genQueryType:remove", "genQueryType:edit", "genQueryType:view", "genQueryType:import", "genQueryType:export", "genQueryType:print", "genQueryType:download", "genQueryType:upload", "genQueryType:delete", "genValidateType:add", "genValidateType:remove", "genValidateType:edit", "genValidateType:view", "genValidateType:import", "genValidateType:export", "genValidateType:print", "genValidateType:download", "genValidateType:upload", "genValidateType:delete", "genDatabaseType:add", "genDatabaseType:remove", "genDatabaseType:edit", "genDatabaseType:view", "genDatabaseType:import", "genDatabaseType:export", "genDatabaseType:print", "genDatabaseType:download", "genDatabaseType:upload", "genDatabaseType:delete", "genTableFieldType:add", "genTableFieldType:remove", "genTableFieldType:edit", "genTableFieldType:view", "genTableFieldType:import", "genTableFieldType:export", "genTableFieldType:print", "genTableFieldType:download", "genTableFieldType:upload", "genTableFieldType:delete", "genShowType:add", "genShowType:remove", "genShowType:edit", "genShowType:view", "genShowType:import", "genShowType:export", "genShowType:print", "genShowType:download", "genShowType:upload", "genShowType:delete", "tableManageConfig:add", "tableManageConfig:remove", "tableManageConfig:edit", "tableManageConfig:view", "tableManageConfig:import", "tableManageConfig:export", "tableManageConfig:print", "tableManageConfig:download", "tableManageConfig:upload", "tableManageConfig:delete", "genCustomObj:add", "genCustomObj:remove", "genCustomObj:edit", "genCustomObj:view", "genCustomObj:import", "genCustomObj:export", "genCustomObj:print", "genCustomObj:download", "genCustomObj:upload", "genCustomObj:delete", "genCustomField:add", "genCustomField:remove", "genCustomField:edit", "genCustomField:view", "genCustomField:import", "genCustomField:export", "genCustomField:print", "genCustomField:download", "genCustomField:upload", "genCustomField:delete", "genScheme", "genScheme:add", "genScheme:remove", "genScheme:edit", "genScheme:view", "genScheme:import", "genScheme:export", "genScheme:print", "genScheme:download", "genScheme:upload", "genScheme:delete", "genTemplate:add", "genTemplate:remove", "genTemplate:edit", "genTemplate:view", "genTemplate:import", "genTemplate:export", "genTemplate:print", "genTemplate:download", "genTemplate:upload", "genTemplate:delete", "ecipWebErrorLog:add", "ecipWebErrorLog:remove", "ecipWebErrorLog:edit", "ecipWebErrorLog:view", "ecipWebErrorLog:import", "ecipWebErrorLog:export", "ecipWebErrorLog:print", "ecipWebErrorLog:download", "ecipWebErrorLog:upload", "ecipWebErrorLog:delete", "app:generateSecret", "app:findSecret", "admin" ],
//     "userId" : "-1",
//     "dictList" : {
//       "readStatus" : [ {
//         "value" : "unread",
//         "label" : "未读",
//         "checked" : false
//       }, {
//         "value" : "read",
//         "label" : "已读",
//         "checked" : false
//       } ],
//       "exSpecialType" : [ {
//         "value" : "1",
//         "label" : "OBU电量低",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "OBU拆卸",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "OBU过期",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "OBU未启用",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "5",
//         "label" : "OBU无卡",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "6",
//         "label" : "OBU在状态名单里",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "7",
//         "label" : "OBU已锁",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "8",
//         "label" : "OBU发行方无效",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "9",
//         "label" : "OBU车型不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "10",
//         "label" : "OBU EF04内前缀异常",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "11",
//         "label" : "OBU EF04内标签无卡次数大于0",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "12",
//         "label" : "OBU EF04内或单片式OBU收费站入口无效",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "13",
//         "label" : "OBU EF04内通行省份数量为0",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "14",
//         "label" : "OBU总累计应收金额异常（大）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "15",
//         "label" : "OBU总累计应收金额异常（小）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "16",
//         "label" : "出口省OBU本省实收累计金额异常",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "17",
//         "label" : "OBU EF04内无本省入口编码信息",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "18",
//         "label" : "OBU/CPC卡累计计费里程异常",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "21",
//         "label" : "ETC卡过期",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "22",
//         "label" : "ETC卡未启用",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "23",
//         "label" : "ETC卡已锁",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "24",
//         "label" : "ETC卡在状态名单内",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "25",
//         "label" : "ETC卡发行方无效",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "26",
//         "label" : "ETC/CPC卡入口无效",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "27",
//         "label" : "ETC卡片储值卡余额不足",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "28",
//         "label" : "ETC卡片余额为0",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "29",
//         "label" : "ETC/CPC卡累计金额异常（大）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "30",
//         "label" : "ETC卡累计金额异常（小）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "31",
//         "label" : "ETC卡类型非法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "32",
//         "label" : "ETC卡车辆状态标识非法（货车）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "33",
//         "label" : "CPC卡电量过低",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "34",
//         "label" : "CPC卡密钥UK1锁定",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "35",
//         "label" : "CPC卡损坏",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "36",
//         "label" : "无CPC卡",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "37",
//         "label" : "入口站信息错误",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "38",
//         "label" : "CPC 卡发行归属地异常",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "39",
//         "label" : "CPC 卡状态异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "41",
//         "label" : "ETC卡与OBU车牌（含颜色）不符",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "42",
//         "label" : "ETC卡与OBU车型不符",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "43",
//         "label" : "ETC卡与OBU卡发行方不符",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "44",
//         "label" : "ETC卡与OBU EF04内卡片文件不一致",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "45",
//         "label" : "ETC卡与OBU EF04内入口信息不一致（入口站或者入口时间）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "46",
//         "label" : "ETC卡与OBU EF04内上个门架编号信息不一致（门架编号或通行时间）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "51",
//         "label" : "出入口车辆车型不符",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "52",
//         "label" : "出入口车牌（含颜色）不符",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "53",
//         "label" : "出入口车辆状态标识（货车ETC）不符",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "54",
//         "label" : "入口时间异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "55",
//         "label" : "入口流通状态有误（非0x01/0x03/0x10）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "56",
//         "label" : "入口标签无卡或读卡出错或入口储值卡余额为0",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "57",
//         "label" : "门架代写入口",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "58",
//         "label" : "从进入路网到离开路网超时（未拦截无需记录，即按正常交易看待）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "59",
//         "label" : "路径不可通达",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "60",
//         "label" : "入出口轴数不一致",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "61",
//         "label" : "计费模块初始化失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "62",
//         "label" : "获取计费模块版本失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "63",
//         "label" : "计费模块查询费率返回失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "64",
//         "label" : "计费模块查询费率超时",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "65",
//         "label" : "计费服务计费请求失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "66",
//         "label" : "路径不可通达",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "67",
//         "label" : "拟合门架数过多",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "68",
//         "label" : "计费金额异常",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "71",
//         "label" : "无DSRC数据返回（超时）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "72",
//         "label" : "读取OBU车辆信息文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "73",
//         "label" : "解密OBU车辆信息失败(双片式OBU)",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "74",
//         "label" : "读取ETC卡片文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "75",
//         "label" : "复合消费初始化失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "76",
//         "label" : "更新记录文件缓存失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "77",
//         "label" : "复合消费失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "78",
//         "label" : "获取TAC码失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "79",
//         "label" : "读取OBU EF04文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "80",
//         "label" : "更新OBU EF04文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "81",
//         "label" : "OBU外部认证失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "82",
//         "label" : "读取车辆信息文件失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "83",
//         "label" : "读取入/出口信息文件失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "84",
//         "label" : "更新入/出口信息文件失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "85",
//         "label" : "信息鉴别码无效（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "86",
//         "label" : "MAC1无效（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "87",
//         "label" : "MAC2无效（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "88",
//         "label" : "天线复位失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "89",
//         "label" : "未读取到各省累计计费信息",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "91",
//         "label" : "PSAM卡黑名单",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "92",
//         "label" : "PSAM已锁卡",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "93",
//         "label" : "PSAM卡复位异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "94",
//         "label" : "授权服务请求异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "95",
//         "label" : "PSAM签到异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "96",
//         "label" : "PSAM操作异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "97",
//         "label" : "PSAM卡未授权",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "101",
//         "label" : "交易流程不完整",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "102",
//         "label" : "车道参数有误",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "103",
//         "label" : "车道系统对时有误",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "104",
//         "label" : "车道系统黑名单版本有误",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "111",
//         "label" : "U型车拦截（未拦截无需记录）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "112",
//         "label" : "大客车限时通行（未拦截无需记录）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "113",
//         "label" : "最近门架HEX码不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "114",
//         "label" : "收费车道反向车辆",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "115",
//         "label" : "邻道干扰",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "116",
//         "label" : "节假日免费",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "117",
//         "label" : "车辆闯关",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "118",
//         "label" : "车牌在追缴黑名单",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "119",
//         "label" : "车辆滞留车道时间过长",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "120",
//         "label" : "砸车",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "121",
//         "label" : "跟车逃费",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "122",
//         "label" : "交易未完成（交易超时）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "123",
//         "label" : "无标签车辆",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "124",
//         "label" : "一车多标",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "125",
//         "label" : "入出口通行超时",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "126",
//         "label" : "绿通查验不合格",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "127",
//         "label" : "车牌在追缴灰名单",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "129",
//         "label" : "无此车辆入口信息",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "130",
//         "label" : "计费金额小于入出口可达路径最小费额",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "131",
//         "label" : "跨区作业运输车查验不合格",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "132",
//         "label" : "跨区作业运输车现场免费",
//         "extra" : "2",
//         "checked" : false
//       } ],
//       "deviceLocationType" : [ {
//         "value" : "1",
//         "label" : "区域",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "监控点",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "库房",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "validateType" : [ {
//         "value" : "string",
//         "label" : "字符串",
//         "checked" : false
//       }, {
//         "value" : "email",
//         "label" : "电子邮件",
//         "checked" : false
//       }, {
//         "value" : "website",
//         "label" : "网址",
//         "checked" : false
//       }, {
//         "value" : "date",
//         "label" : "日期",
//         "checked" : false
//       }, {
//         "value" : "dateISO",
//         "label" : "日期（ISO）",
//         "checked" : false
//       }, {
//         "value" : "cardNumber",
//         "label" : "信用卡号",
//         "checked" : false
//       }, {
//         "value" : "phoneNumber",
//         "label" : "电话号码",
//         "checked" : false
//       }, {
//         "value" : "mobilePhoneNumber",
//         "label" : "手机号码",
//         "checked" : false
//       }, {
//         "value" : "mobilePhoneOrPhone",
//         "label" : "手机/电话",
//         "checked" : false
//       }, {
//         "value" : "qqNumber",
//         "label" : "QQ号码",
//         "checked" : false
//       }, {
//         "value" : "idNumber",
//         "label" : "身份证号码",
//         "checked" : false
//       }, {
//         "value" : "number",
//         "label" : "数字",
//         "checked" : false
//       }, {
//         "value" : "integer",
//         "label" : "整数",
//         "checked" : false
//       }, {
//         "value" : "positiveInteger",
//         "label" : "整数（大于0）",
//         "checked" : false
//       }, {
//         "value" : "nonNegativeInteger",
//         "label" : "整数（大于等于0）",
//         "checked" : false
//       }, {
//         "value" : "negativeInteger",
//         "label" : "整数（小于0）",
//         "checked" : false
//       }, {
//         "value" : "nonPositiveInteger",
//         "label" : "整数（小于等于0）",
//         "checked" : false
//       }, {
//         "value" : "positiveFloatingPoint",
//         "label" : "浮点数（大于0）",
//         "checked" : false
//       }, {
//         "value" : "nonNegativeFloatingPoint",
//         "label" : "浮点数（大于等于0）",
//         "checked" : false
//       }, {
//         "value" : "negativeFloatingPoint",
//         "label" : "浮点数（小于0）",
//         "checked" : false
//       }, {
//         "value" : "nonPositiveFloatingPoint",
//         "label" : "浮点数（小于等于0）",
//         "checked" : false
//       }, {
//         "value" : "postalCode",
//         "label" : "邮政编码",
//         "checked" : false
//       }, {
//         "value" : "password",
//         "label" : "密码",
//         "checked" : false
//       }, {
//         "value" : "chineseEnglishNumbersUnderlines",
//         "label" : "中文/英文/数字/下划线",
//         "checked" : false
//       }, {
//         "value" : "english",
//         "label" : "英语",
//         "checked" : false
//       }, {
//         "value" : "chinese",
//         "label" : "汉字",
//         "checked" : false
//       }, {
//         "value" : "englishOrChineseCharacters",
//         "label" : "英汉字符",
//         "checked" : false
//       }, {
//         "value" : "legalCharacters",
//         "label" : "合法字符",
//         "checked" : false
//       } ],
//       "huxiaolong" : [ {
//         "value" : "1",
//         "label" : "仪宇哥",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "datacenterCloudServerType" : [ {
//         "value" : "program",
//         "label" : "应用程序",
//         "checked" : false
//       }, {
//         "value" : "database",
//         "label" : "数据库",
//         "checked" : false
//       }, {
//         "value" : "middleware",
//         "label" : "中间件",
//         "checked" : false
//       } ],
//       "editStatus" : [ {
//         "value" : "draft",
//         "label" : "草稿",
//         "checked" : false
//       }, {
//         "value" : "sent",
//         "label" : "发送",
//         "checked" : false
//       } ],
//       "executionEventType" : [ {
//         "value" : "start",
//         "label" : "start",
//         "tenantId" : "",
//         "checked" : false
//       }, {
//         "value" : "end",
//         "label" : "end",
//         "tenantId" : "",
//         "checked" : false
//       }, {
//         "value" : "take",
//         "label" : "take",
//         "tenantId" : "",
//         "checked" : false
//       } ],
//       "form_type" : [ {
//         "value" : "table",
//         "label" : "关联表单",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "no_table",
//         "label" : "不关联表单",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "businesstype" : [ {
//         "value" : "1",
//         "label" : "验证码",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "通知",
//         "checked" : false
//       }, {
//         "value" : "99",
//         "label" : "其他",
//         "checked" : false
//       } ],
//       "dataResSys_dbType" : [ {
//         "value" : "mysql",
//         "label" : "MySQL",
//         "checked" : false
//       }, {
//         "value" : "hbase",
//         "label" : "HBase",
//         "checked" : false
//       }, {
//         "value" : "es",
//         "label" : "ES",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "orgType" : [ {
//         "value" : "1",
//         "label" : "路段",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "门架",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "收费站",
//         "checked" : false
//       } ],
//       "monitorType" : [ {
//         "value" : "executionListener",
//         "label" : "执行监听器",
//         "checked" : false
//       }, {
//         "value" : "taskListener",
//         "label" : "任务监听器",
//         "tenantId" : "",
//         "checked" : false
//       } ],
//       "FeeMng_VersionType" : [ {
//         "value" : "1",
//         "label" : "路径参数",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "费率参数",
//         "checked" : false
//       } ],
//       "payType" : [ {
//         "value" : "1",
//         "label" : "现金",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "其他第三方账户支付",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "银联卡支付",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "ETC 用户卡",
//         "checked" : false
//       }, {
//         "value" : "6",
//         "label" : "支付宝",
//         "checked" : false
//       }, {
//         "value" : "7",
//         "label" : "微信",
//         "checked" : false
//       } ],
//       "isVehBlack" : [ {
//         "value" : "0",
//         "label" : "不是",
//         "checked" : false
//       }, {
//         "value" : "1",
//         "label" : "是",
//         "checked" : false
//       } ],
//       "FeeMng_ParamStatus" : [ {
//         "value" : "0",
//         "label" : "启用",
//         "checked" : false
//       }, {
//         "value" : "1",
//         "label" : "停用",
//         "checked" : false
//       } ],
//       "valueType" : [ {
//         "value" : "class",
//         "label" : "类",
//         "checked" : false
//       }, {
//         "value" : "expression",
//         "label" : "表达式",
//         "checked" : false
//       }, {
//         "value" : "delegateExpression",
//         "label" : "委托表达式",
//         "checked" : false
//       } ],
//       "showType" : [ {
//         "value" : "input",
//         "label" : "单行文本",
//         "checked" : false
//       }, {
//         "value" : "textarea",
//         "label" : "多行文本",
//         "checked" : false
//       }, {
//         "value" : "umeditor",
//         "label" : "富文本编辑器",
//         "checked" : false
//       }, {
//         "value" : "select",
//         "label" : "下拉选项",
//         "checked" : false
//       }, {
//         "value" : "radiobox",
//         "label" : "单选按钮",
//         "checked" : false
//       }, {
//         "value" : "checkbox",
//         "label" : "复选框",
//         "checked" : false
//       }, {
//         "value" : "dateselect",
//         "label" : "日期选择",
//         "checked" : false
//       }, {
//         "value" : "userselect",
//         "label" : "用户选择",
//         "checked" : false
//       }, {
//         "value" : "deptselect",
//         "label" : "部门选择",
//         "checked" : false
//       }, {
//         "value" : "cityselect",
//         "label" : "省市区三级联动",
//         "checked" : false
//       }, {
//         "value" : "treeselect",
//         "label" : "树选择控件",
//         "checked" : false
//       }, {
//         "value" : "fileselect",
//         "label" : "文件上传",
//         "checked" : false
//       }, {
//         "value" : "gridselect",
//         "label" : "grid选择框",
//         "checked" : false
//       }, {
//         "value" : "areaselect",
//         "label" : "区域选择",
//         "checked" : false
//       } ],
//       "FeeMng_NodeType" : [ {
//         "value" : "1",
//         "label" : "普通收费单元",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "省界收费单元",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "收费站",
//         "checked" : false
//       } ],
//       "vehicleType" : [ {
//         "value" : "1",
//         "label" : "一型客车",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "二型客车",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "三型客车",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "四型客车",
//         "checked" : false
//       }, {
//         "value" : "11",
//         "label" : "一型货车",
//         "checked" : false
//       }, {
//         "value" : "12",
//         "label" : "二型货车",
//         "checked" : false
//       }, {
//         "value" : "13",
//         "label" : "三型货车",
//         "checked" : false
//       }, {
//         "value" : "14",
//         "label" : "四型货车",
//         "checked" : false
//       }, {
//         "value" : "15",
//         "label" : "五型货车",
//         "checked" : false
//       }, {
//         "value" : "16",
//         "label" : "六型货车",
//         "checked" : false
//       }, {
//         "value" : "21",
//         "label" : "一型专项作业车",
//         "checked" : false
//       }, {
//         "value" : "22",
//         "label" : "二型专项作业车",
//         "checked" : false
//       }, {
//         "value" : "23",
//         "label" : "三型专项作业车",
//         "checked" : false
//       }, {
//         "value" : "24",
//         "label" : "四型专项作业车",
//         "checked" : false
//       }, {
//         "value" : "25",
//         "label" : "五型专项作业车",
//         "checked" : false
//       }, {
//         "value" : "26",
//         "label" : "六型专项作业车",
//         "checked" : false
//       } ],
//       "deviceType" : [ {
//         "value" : "web",
//         "label" : "浏览器",
//         "checked" : false
//       }, {
//         "value" : "app",
//         "label" : "App",
//         "checked" : false
//       } ],
//       "typeApp" : [ {
//         "value" : "1",
//         "label" : "WEB应用",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "Restful API接口",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "报表系统",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "外部链接应用",
//         "checked" : false
//       }, {
//         "value" : "0",
//         "label" : "其他通用",
//         "checked" : false
//       } ],
//       "PrimaryKeyStrategy" : [ {
//         "value" : "1",
//         "label" : "自增长",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "UUID",
//         "checked" : false
//       } ],
//       "FeeMng_CalcCode" : [ {
//         "value" : "200",
//         "label" : "成功",
//         "checked" : false
//       }, {
//         "value" : "502",
//         "label" : "错误网关",
//         "checked" : false
//       }, {
//         "value" : "701",
//         "label" : "文件操作异常",
//         "checked" : false
//       }, {
//         "value" : "704",
//         "label" : "部中心：业务校验失败",
//         "checked" : false
//       }, {
//         "value" : "716",
//         "label" : "部中心：响应失败",
//         "checked" : false
//       }, {
//         "value" : "947",
//         "label" : "请求数据错误",
//         "checked" : false
//       }, {
//         "value" : "951",
//         "label" : "文件md5校验失败",
//         "checked" : false
//       }, {
//         "value" : "952",
//         "label" : "格式校验失败",
//         "checked" : false
//       }, {
//         "value" : "994",
//         "label" : "上游系统数据异常",
//         "checked" : false
//       }, {
//         "value" : "996",
//         "label" : "路径还原失败",
//         "checked" : false
//       }, {
//         "value" : "997",
//         "label" : "计费模块计费失败",
//         "checked" : false
//       }, {
//         "value" : "998",
//         "label" : "自行计费失败",
//         "checked" : false
//       }, {
//         "value" : "999",
//         "label" : "系统异常",
//         "checked" : false
//       }, {
//         "value" : "2001",
//         "label" : "服务禁用",
//         "checked" : false
//       }, {
//         "value" : "2002",
//         "label" : "封装交易信息异常",
//         "checked" : false
//       }, {
//         "value" : "2003",
//         "label" : "收费单元转化门架异常",
//         "checked" : false
//       }, {
//         "value" : "2005",
//         "label" : "查询最短路径异常",
//         "checked" : false
//       }, {
//         "value" : "2006",
//         "label" : "出口车道跨省特情计费失败",
//         "checked" : false
//       }, {
//         "value" : "2007",
//         "label" : "在线计费计费不准确",
//         "checked" : false
//       }, {
//         "value" : "2008",
//         "label" : "在线计费计费失败",
//         "checked" : false
//       }, {
//         "value" : "2009",
//         "label" : "查询最小费额异常",
//         "checked" : false
//       } ],
//       "module" : [ {
//         "value" : "sysr_user",
//         "label" : "用户",
//         "checked" : false
//       }, {
//         "value" : "sysr_dept",
//         "label" : "组织",
//         "checked" : false
//       }, {
//         "value" : "sysr_resource",
//         "label" : "资源",
//         "checked" : false
//       }, {
//         "value" : "sysr_role",
//         "label" : "角色",
//         "checked" : false
//       }, {
//         "value" : "ecip_dict",
//         "label" : "数据字典",
//         "checked" : false
//       }, {
//         "value" : "sysr_user_role",
//         "label" : "用户角色",
//         "checked" : false
//       }, {
//         "value" : "sysr_role_resource",
//         "label" : "角色资源",
//         "checked" : false
//       }, {
//         "value" : "sys_role_data",
//         "label" : "角色数据",
//         "checked" : false
//       }, {
//         "value" : "sysr_register_app",
//         "label" : "应用系统",
//         "checked" : false
//       }, {
//         "value" : "data_rule",
//         "label" : "数据规则",
//         "checked" : false
//       }, {
//         "value" : "sysr_datasource",
//         "label" : "数据源",
//         "checked" : false
//       }, {
//         "value" : "custom_obj",
//         "label" : "自定义java对象",
//         "checked" : false
//       }, {
//         "value" : "test_note",
//         "label" : "富文本测试",
//         "checked" : false
//       }, {
//         "value" : "gencode_scheme",
//         "label" : "生成方案",
//         "checked" : false
//       }, {
//         "value" : "gencode_template_group",
//         "label" : "代码生成器模板",
//         "checked" : false
//       }, {
//         "value" : "test_pic",
//         "label" : "图片管理",
//         "checked" : false
//       }, {
//         "value" : "gencode_table",
//         "label" : "表单配置",
//         "checked" : false
//       }, {
//         "value" : "sysr_user_app",
//         "label" : "用户应用",
//         "checked" : false
//       }, {
//         "value" : "\tuser_dept",
//         "label" : "用户组织",
//         "checked" : false
//       }, {
//         "value" : "sysr_app_version",
//         "label" : "应用版本",
//         "checked" : false
//       }, {
//         "value" : "saas_org",
//         "label" : "租户机构",
//         "checked" : false
//       } ],
//       "dataType" : [ {
//         "value" : "1",
//         "label" : "入口通行流水",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "出口ETC交易流水",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "出口其他交易流水",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "承载门架流水",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "5",
//         "label" : "收费站车牌识别数据",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "6",
//         "label" : "入口站处理合计数",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "7",
//         "label" : "出口站处理合计数",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "syncIdType" : [ {
//         "value" : "src",
//         "label" : "跟随源记录",
//         "checked" : false
//       }, {
//         "value" : "auto",
//         "label" : "自动生成新主键",
//         "checked" : false
//       } ],
//       "JavaType" : [ {
//         "value" : "String",
//         "label" : "String",
//         "checked" : false
//       }, {
//         "value" : "Long",
//         "label" : "Long",
//         "checked" : false
//       }, {
//         "value" : "Integer",
//         "label" : "Integer",
//         "checked" : false
//       }, {
//         "value" : "Double",
//         "label" : "Double",
//         "checked" : false
//       }, {
//         "value" : "Date",
//         "label" : "Date",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.ecip.web.rbac.entity.SysrDept",
//         "label" : "Dept",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.ecip.example.test_common.entity.TestArea",
//         "label" : "Area",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.ecip.framework.shiro.ShiroUser",
//         "label" : "User",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.ecip.web.rbac.entity.SysrFile",
//         "label" : "File",
//         "checked" : false
//       }, {
//         "value" : "This",
//         "label" : "ThisObj",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.modules.testdatamain.entity.TestDataMainForm",
//         "label" : "TestDataMainForm",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.modules.treedemo.entity.TreeDemo",
//         "label" : "TreeDemo",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.ecip.web.rbac.entity.SysrArea",
//         "label" : "Area",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.ecip.api.vo.SysrDeptVo",
//         "label" : "DeptVo",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.ecip.api.vo.FileVo",
//         "label" : "FileVo",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.ecip.api.vo.AreaVo",
//         "label" : "AreaVo",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.yfzx.tibms.laneenlist.entity.LaneEnList",
//         "label" : "LaneEnList",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.modules.null.entity.TestOrder",
//         "label" : "TestOrder",
//         "checked" : false
//       }, {
//         "value" : "com.hgsoft.modules.null.entity.TestOrder",
//         "label" : "TestOrder",
//         "tenantId" : "",
//         "checked" : false
//       } ],
//       "gantrySpecialType" : [ {
//         "value" : "101",
//         "label" : "标签拆卸",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "102",
//         "label" : "标签无卡",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "103",
//         "label" : "标签锁定",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "104",
//         "label" : "标签未到启用日期",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "105",
//         "label" : "标签有效期已过",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "106",
//         "label" : "标签未联网",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "107",
//         "label" : "标签无车牌（全0或者全F）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "108",
//         "label" : "标签车牌不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "109",
//         "label" : "标签车型不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "110",
//         "label" : "标签预激活",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "111",
//         "label" : "标签无入口",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "112",
//         "label" : "标签无卡号",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "113",
//         "label" : "标签内省份数量有误",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "114",
//         "label" : "标签低电量",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "116",
//         "label" : "卡片未到启用日期",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "117",
//         "label" : "卡片有效期已过",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "118",
//         "label" : "卡片错误",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "119",
//         "label" : "非联网区域卡",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "120",
//         "label" : "卡片无车牌（全0或者全F）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "121",
//         "label" : "卡片车牌不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "122",
//         "label" : "卡片车型不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "123",
//         "label" : "卡片类型不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "124",
//         "label" : "CPC卡电量低",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "130",
//         "label" : "卡签发行属地不一致",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "131",
//         "label" : "卡签车牌不一致",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "132",
//         "label" : "卡签车型不一致",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "133",
//         "label" : "卡签入口信息不一致",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "134",
//         "label" : "卡签卡号不一致",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "135",
//         "label" : "无入口网络编号（全0或全F）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "136",
//         "label" : "无入口收费站信息（全0或全F）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "137",
//         "label" : "入口流通状态有误（非0x01/0x03/0x10）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "138",
//         "label" : "无入口车道号（全0或全F）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "139",
//         "label" : "入口车型不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "140",
//         "label" : "入口时间不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "141",
//         "label" : "入口车轴不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "142",
//         "label" : "入口车辆状态标识不合法(ETC)",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "143",
//         "label" : "入口车种不合法(CPC)",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "145",
//         "label" : "计费模块初始化失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "146",
//         "label" : "计费模块释放资源失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "147",
//         "label" : "获取计费模块版本失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "148",
//         "label" : "获取计费参数版本失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "149",
//         "label" : "计费模块查询费率返回失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "150",
//         "label" : "计费模块查询费率超时",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "151",
//         "label" : "计费服务计费请求失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "152",
//         "label" : "卡片累计金额异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "153",
//         "label" : "标签累计金额异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "154",
//         "label" : "反向干扰（计费模块判断）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "155",
//         "label" : "读卡/标签无DSRC数据返回（超时）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "156",
//         "label" : "上行DSRC数据解码错误",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "157",
//         "label" : "找不到文件",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "158",
//         "label" : "解密OBU车辆信息失败（校验码对比失败）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "159",
//         "label" : "OBU外部认证失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "160",
//         "label" : "OBU读取0002文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "161",
//         "label" : "OBU读取0015文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "162",
//         "label" : "读取0019文件返回失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "163",
//         "label" : "读取入/出口信息文件失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "164",
//         "label" : "复合消费初始化失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "165",
//         "label" : "更新记录文件缓存失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "166",
//         "label" : "复合消费失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "167",
//         "label" : "更新交易记录文件失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "168",
//         "label" : "CPC卡外部认证失败（PSAM返回）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "169",
//         "label" : "CPC卡读DF01/EF02文件失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "170",
//         "label" : "CPC卡读DF01/EF04文件失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "171",
//         "label" : "CPC卡写过站信息失败，写计费信息成功",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "172",
//         "label" : "CPC卡写过站信息成功，写计费信息失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "173",
//         "label" : "CPC卡写过站信息、写计费信息失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "174",
//         "label" : "CPC卡写计费信息失败（省界出口ETC门架）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "175",
//         "label" : "CPC卡读过站信息文件（DF01/EF02）失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "176",
//         "label" : "CPC卡更新过站信息文件失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "177",
//         "label" : "PSAM卡外部认证失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "178",
//         "label" : "获取TAC码失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "179",
//         "label" : "PSAM卡MAC1计算失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "180",
//         "label" : "MAC2 校验失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "181",
//         "label" : "计算TAC失败（双片式OBU、单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "182",
//         "label" : "CPC卡外部认证失败（CPC 卡返回）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "183",
//         "label" : "写卡/标签无DSRC数据返回（超时）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "184",
//         "label" : "解密OBU车辆信息失败(PSAM 卡解密失败)",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "185",
//         "label" : "前序已完成过卡片复合交易",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "186",
//         "label" : "反向干扰（门架软件判断）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "187",
//         "label" : "余额不足",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "188",
//         "label" : "节假日免费",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "189",
//         "label" : "疑似持CPC的ETC无效用户",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "190",
//         "label" : "前序已完成过标签交易",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "191",
//         "label" : "需要重清过站信息（仅用于CPC卡省界出口）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "192",
//         "label" : "交易流程不完整（天线无响应导致流程不完整）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "193",
//         "label" : "前排已经处理，查询共享后拒绝处理。",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "194",
//         "label" : "车辆跨省",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "200",
//         "label" : "EF04内信息与加密摘要不匹配",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "201",
//         "label" : "生成加密摘要失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "202",
//         "label" : "其他异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "210",
//         "label" : "标签无卡、选择/读取 EF04 文件成功（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "211",
//         "label" : "标签无卡、选择/读取 EF04 文件失败（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "212",
//         "label" : "标签有卡、读取 ETC 卡片文件成功，选择/读取 EF04 文件失败（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "213",
//         "label" : "标签有卡、读取 ETC 卡片文件失败，选择/读取 EF04 文件失败（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "214",
//         "label" : "标签有卡、读取 ETC 卡片文件失败，选择/读取 EF04 文件成功（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "215",
//         "label" : "更新 EF04 文件失败（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "216",
//         "label" : "复合消费失败（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "217",
//         "label" : "更新 EF04 文件成功、复合消费失败（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "218",
//         "label" : "更新 EF04 文件失败、复合消费失败（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "219",
//         "label" : "更新 EF04 文件失败、复合消费成功（双片式 OBU）",
//         "extra" : "2",
//         "checked" : false
//       } ],
//       "FeeMng_DownStatus" : [ {
//         "value" : "0",
//         "label" : "未下发",
//         "checked" : false
//       }, {
//         "value" : "1",
//         "label" : "已下发",
//         "checked" : false
//       } ],
//       "mo-heartBeatStatus" : [ {
//         "value" : "normal",
//         "label" : "正常",
//         "checked" : false
//       }, {
//         "value" : "abnormal",
//         "label" : "异常",
//         "checked" : false
//       } ],
//       "vehiclePlateColor" : [ {
//         "value" : "0",
//         "label" : "蓝色",
//         "checked" : false
//       }, {
//         "value" : "1",
//         "label" : "黄色",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "黑色",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "白色",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "渐变绿色",
//         "checked" : false
//       }, {
//         "value" : "5",
//         "label" : " 黄绿双拼色",
//         "checked" : false
//       }, {
//         "value" : "6",
//         "label" : "蓝白渐变色",
//         "checked" : false
//       }, {
//         "value" : "7",
//         "label" : "临时牌照",
//         "checked" : false
//       }, {
//         "value" : "11",
//         "label" : "绿色",
//         "checked" : false
//       }, {
//         "value" : "12",
//         "label" : "红色",
//         "checked" : false
//       } ],
//       "FeeMng_CalcResult" : [ {
//         "value" : "1",
//         "label" : "成功",
//         "checked" : false
//       }, {
//         "value" : "0",
//         "label" : "失败",
//         "checked" : false
//       } ],
//       "dataRuleCondition" : [ {
//         "value" : "=",
//         "label" : "等于",
//         "checked" : false
//       }, {
//         "value" : "!=",
//         "label" : "不等于",
//         "checked" : false
//       }, {
//         "value" : "<",
//         "label" : "小于",
//         "checked" : false
//       }, {
//         "value" : "<=",
//         "label" : "小于等于",
//         "checked" : false
//       }, {
//         "value" : ">=",
//         "label" : "大于等于",
//         "checked" : false
//       }, {
//         "value" : "like",
//         "label" : "模糊匹配",
//         "checked" : false
//       }, {
//         "value" : "RightLike",
//         "label" : "RightLike",
//         "checked" : false
//       }, {
//         "value" : "LeftLike",
//         "label" : "LeftLike",
//         "checked" : false
//       } ],
//       "syncType" : [ {
//         "value" : "user",
//         "label" : "同步用户",
//         "checked" : false
//       }, {
//         "value" : "dept",
//         "label" : "同步组织",
//         "checked" : false
//       }, {
//         "value" : "other",
//         "label" : "自定义",
//         "checked" : false
//       } ],
//       "FeeMng_UpStatus" : [ {
//         "value" : "0",
//         "label" : "未上传",
//         "checked" : false
//       }, {
//         "value" : "1",
//         "label" : "已上传",
//         "checked" : false
//       } ],
//       "FeeMng_VersionStatus" : [ {
//         "value" : "1",
//         "label" : "已发布",
//         "checked" : false
//       }, {
//         "value" : "0",
//         "label" : "待发布",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "发布中",
//         "checked" : false
//       } ],
//       "versionStatus" : [ {
//         "value" : "1",
//         "label" : "待审核",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "待发布",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "已发布",
//         "checked" : false
//       } ],
//       "131" : [ {
//         "value" : "13123",
//         "label" : "3123",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "tableType" : [ {
//         "value" : "singleTable",
//         "label" : "单表",
//         "checked" : false
//       }, {
//         "value" : "masterTable",
//         "label" : "主表",
//         "checked" : false
//       }, {
//         "value" : "attachTable",
//         "label" : "附表",
//         "checked" : false
//       }, {
//         "value" : "treeStructureTable",
//         "label" : "树结构表",
//         "checked" : false
//       }, {
//         "value" : "leftTreeMainTable",
//         "label" : "左树(主表)",
//         "checked" : false
//       }, {
//         "value" : "rightTableSchedule",
//         "label" : "右表（附表）",
//         "checked" : false
//       }, {
//         "value" : "workflowForms",
//         "label" : "工作流表单",
//         "checked" : false
//       } ],
//       "tbDeviceType" : [ {
//         "value" : "1",
//         "label" : "天线",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "车牌识别设备",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "动环工控机",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "机柜",
//         "checked" : false
//       }, {
//         "value" : "5",
//         "label" : "电源",
//         "checked" : false
//       }, {
//         "value" : "6",
//         "label" : "空调",
//         "checked" : false
//       }, {
//         "value" : "7",
//         "label" : "防雷",
//         "checked" : false
//       }, {
//         "value" : "8",
//         "label" : "工控机",
//         "checked" : false
//       }, {
//         "value" : "9",
//         "label" : "服务器",
//         "checked" : false
//       }, {
//         "value" : "10",
//         "label" : "读卡器",
//         "checked" : false
//       }, {
//         "value" : "11",
//         "label" : "移动支付",
//         "checked" : false
//       }, {
//         "value" : "12",
//         "label" : "车检器",
//         "checked" : false
//       }, {
//         "value" : "13",
//         "label" : "轴型检测器",
//         "checked" : false
//       }, {
//         "value" : "14",
//         "label" : "光栅",
//         "checked" : false
//       }, {
//         "value" : "15",
//         "label" : "车道摄像机",
//         "checked" : false
//       }, {
//         "value" : "16",
//         "label" : "费额显示屏",
//         "checked" : false
//       }, {
//         "value" : "17",
//         "label" : "信息提示屏",
//         "checked" : false
//       }, {
//         "value" : "18",
//         "label" : "通行信号灯",
//         "checked" : false
//       }, {
//         "value" : "19",
//         "label" : "ETC情报板",
//         "checked" : false
//       }, {
//         "value" : "20",
//         "label" : "入口治超称重设备",
//         "checked" : false
//       }, {
//         "value" : "21",
//         "label" : "入口治超车牌识别",
//         "checked" : false
//       }, {
//         "value" : "22",
//         "label" : "入口治超前身抓拍",
//         "checked" : false
//       }, {
//         "value" : "23",
//         "label" : "入口治超尾部抓拍",
//         "checked" : false
//       }, {
//         "value" : "24",
//         "label" : "入口治超RSU",
//         "checked" : false
//       }, {
//         "value" : "25",
//         "label" : "入口治超轮廓仪",
//         "checked" : false
//       }, {
//         "value" : "26",
//         "label" : "入口治超费显显示屏",
//         "checked" : false
//       }, {
//         "value" : "27",
//         "label" : "入口治超通行信号灯",
//         "checked" : false
//       }, {
//         "value" : "28",
//         "label" : "网关/交换机",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "enExType" : [ {
//         "value" : "1",
//         "label" : "入口",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "出口",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "jdbcType" : [ {
//         "value" : "varchar(64)",
//         "label" : "varchar(64)",
//         "checked" : false
//       }, {
//         "value" : "nvarchar(64)",
//         "label" : "nvarchar(64)",
//         "checked" : false
//       }, {
//         "value" : "integer",
//         "label" : "integer",
//         "checked" : false
//       }, {
//         "value" : "double",
//         "label" : "double",
//         "checked" : false
//       }, {
//         "value" : "datetime",
//         "label" : "datetime",
//         "checked" : false
//       }, {
//         "value" : "longblob",
//         "label" : "longblob",
//         "checked" : false
//       }, {
//         "value" : "longtext",
//         "label" : "longtext",
//         "checked" : false
//       } ],
//       "areaType" : [ {
//         "value" : "country",
//         "label" : "国家",
//         "checked" : false
//       }, {
//         "value" : "city",
//         "label" : "市",
//         "checked" : false
//       }, {
//         "value" : "district",
//         "label" : "区(县)",
//         "checked" : false
//       }, {
//         "value" : "town",
//         "label" : "镇(乡)",
//         "checked" : false
//       }, {
//         "value" : "village",
//         "label" : "村",
//         "checked" : false
//       } ],
//       "vehicleClass" : [ {
//         "value" : "0",
//         "label" : "普通",
//         "checked" : false
//       }, {
//         "value" : "8",
//         "label" : "军警",
//         "checked" : false
//       }, {
//         "value" : "10",
//         "label" : "紧急",
//         "checked" : false
//       }, {
//         "value" : "14",
//         "label" : "车队",
//         "checked" : false
//       }, {
//         "value" : "21",
//         "label" : "绿通车",
//         "checked" : false
//       }, {
//         "value" : "22",
//         "label" : "联合收割机",
//         "checked" : false
//       }, {
//         "value" : "23",
//         "label" : "抢险救灾",
//         "checked" : false
//       }, {
//         "value" : "24",
//         "label" : "集装箱",
//         "checked" : false
//       }, {
//         "value" : "25",
//         "label" : "大件运输",
//         "checked" : false
//       }, {
//         "value" : "26",
//         "label" : "应急车",
//         "checked" : false
//       } ],
//       "enSpecialType" : [ {
//         "value" : "1",
//         "label" : "OBU电量低",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "OBU拆卸",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "OBU过期",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "OBU未启用",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "5",
//         "label" : "OBU无卡",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "6",
//         "label" : "OBU在状态名单里",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "7",
//         "label" : "OBU已锁",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "8",
//         "label" : "OBU发行方无效",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "9",
//         "label" : "OBU车型不合法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "21",
//         "label" : "ETC卡过期",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "22",
//         "label" : "ETC卡未启用",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "23",
//         "label" : "ETC卡已锁",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "24",
//         "label" : "ETC卡在状态名单内",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "25",
//         "label" : "ETC卡发行方无效",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "28",
//         "label" : "ETC卡片余额为0",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "31",
//         "label" : "ETC卡类型非法",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "33",
//         "label" : "CPC卡电量过低",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "41",
//         "label" : "ETC卡与OBU车牌（含颜色）不符",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "42",
//         "label" : "ETC卡与OBU车型不符",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "43",
//         "label" : "ETC卡与OBU卡发行方不符",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "71",
//         "label" : "无DSRC数据返回（超时）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "72",
//         "label" : "读取OBU车辆信息文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "73",
//         "label" : "解密OBU车辆信息失败(双片式OBU)",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "74",
//         "label" : "读取ETC卡片文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "75",
//         "label" : "复合消费初始化失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "76",
//         "label" : "更新记录文件缓存失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "77",
//         "label" : "复合消费失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "78",
//         "label" : "获取TAC码失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "79",
//         "label" : "读取OBU EF04文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "80",
//         "label" : "更新OBU EF04文件失败（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "81",
//         "label" : "OBU外部认证失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "82",
//         "label" : "读取车辆信息文件失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "83",
//         "label" : "读取入/出口信息文件失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "84",
//         "label" : "更新入/出口信息文件失败（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "85",
//         "label" : "信息鉴别码无效（单片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "86",
//         "label" : "MAC1无效（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "87",
//         "label" : "MAC2无效（双片式OBU）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "88",
//         "label" : "天线复位失败",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "91",
//         "label" : "PSAM卡黑名单",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "92",
//         "label" : "PSAM已锁卡",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "93",
//         "label" : "PSAM卡复位异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "94",
//         "label" : "授权服务请求异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "95",
//         "label" : "PSAM签到异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "96",
//         "label" : "PSAM操作异常",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "97",
//         "label" : "PSAM卡未授权",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "101",
//         "label" : "交易流程不完整",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "102",
//         "label" : "车道参数有误",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "103",
//         "label" : "车道系统对时有误",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "104",
//         "label" : "车道系统黑名单版本有误",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "112",
//         "label" : "大客车限时通行（未拦截无需记录）",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "114",
//         "label" : "收费车道反向车辆",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "115",
//         "label" : "邻道干扰",
//         "extra" : "2",
//         "checked" : false
//       }, {
//         "value" : "116",
//         "label" : "节假日免费",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "117",
//         "label" : "车辆闯关",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "118",
//         "label" : "车牌在追缴黑名单",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "119",
//         "label" : "车辆滞留车道时间过长",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "120",
//         "label" : "砸车",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "121",
//         "label" : "跟车逃费",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "122",
//         "label" : "交易未完成（交易超时）",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "123",
//         "label" : "无标签车辆",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "124",
//         "label" : "一车多标",
//         "extra" : "1",
//         "checked" : false
//       }, {
//         "value" : "127",
//         "label" : "车牌在追缴灰名单",
//         "extra" : "1",
//         "checked" : false
//       } ],
//       "datacenterMonitorType" : [ {
//         "value" : "ExtractApplication",
//         "label" : "指令采集服务",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "instruction",
//         "label" : "IP拨测服务",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "authType" : [ {
//         "value" : "1",
//         "label" : "Cookie-Session",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "JWT Token",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "OAuth认证",
//         "checked" : false
//       } ],
//       "retention" : [ {
//         "value" : "0",
//         "label" : "等于0",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "1",
//         "label" : "大于0",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "laneType" : [ {
//         "value" : "1",
//         "label" : "ETC车道",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "MTC车道",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "混合车道",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "复式混合车道",
//         "checked" : false
//       }, {
//         "value" : "5",
//         "label" : "虚拟车道（通过便携机收费）",
//         "checked" : false
//       } ],
//       "enableState" : [ {
//         "value" : "1",
//         "label" : "启用",
//         "tenantId" : "",
//         "checked" : false
//       }, {
//         "value" : "0",
//         "label" : "未启用",
//         "tenantId" : "",
//         "checked" : false
//       } ],
//       "genIdType" : [ {
//         "value" : "1",
//         "label" : "UUID",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "IdWorker",
//         "checked" : false
//       } ],
//       "FeeMng_CalcType" : [ {
//         "value" : "1",
//         "label" : "通行费计费",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "无入口特情计费",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "出入口信息不符特情计费",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "OBU及ETC特情计费",
//         "checked" : false
//       }, {
//         "value" : "5",
//         "label" : "本省省界触发计费",
//         "checked" : false
//       }, {
//         "value" : "6",
//         "label" : "非本省出口计费",
//         "checked" : false
//       }, {
//         "value" : "7",
//         "label" : "ETC计费",
//         "checked" : false
//       }, {
//         "value" : "8",
//         "label" : "门架计费拆分",
//         "checked" : false
//       }, {
//         "value" : "9",
//         "label" : "全网最短路径计费",
//         "checked" : false
//       }, {
//         "value" : "10",
//         "label" : "出口车道跨省特情计费",
//         "checked" : false
//       }, {
//         "value" : "11",
//         "label" : "跨省入口站查询",
//         "checked" : false
//       }, {
//         "value" : "12",
//         "label" : "全网最小费额计费",
//         "checked" : false
//       }, {
//         "value" : "13",
//         "label" : "非本省出口计费",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "sex" : [ {
//         "value" : "0",
//         "label" : "男",
//         "checked" : false
//       }, {
//         "value" : "1",
//         "label" : "女",
//         "checked" : false
//       } ],
//       "FeeMng_ModelDirection" : [ {
//         "value" : "1",
//         "label" : "有向",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "无向",
//         "checked" : false
//       } ],
//       "dbType" : [ {
//         "value" : "mysql",
//         "label" : "MySql",
//         "checked" : false
//       }, {
//         "value" : "oracle",
//         "label" : "Oracle",
//         "checked" : false
//       }, {
//         "value" : "sqlserver",
//         "label" : "SqlServer",
//         "checked" : false
//       }, {
//         "value" : "db2",
//         "label" : "DB2",
//         "checked" : false
//       } ],
//       "noticeType" : [ {
//         "value" : "system",
//         "label" : "系统消息",
//         "checked" : false
//       }, {
//         "value" : "flow",
//         "label" : "工单消息",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "alarm",
//         "label" : "告警消息",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "typeSrc" : [ {
//         "value" : "1",
//         "label" : "WEB",
//         "tenantId" : "",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "API",
//         "tenantId" : "",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "报表资源",
//         "tenantId" : "",
//         "checked" : false
//       } ],
//       "taskLisenerEventType" : [ {
//         "value" : "start",
//         "label" : "start",
//         "checked" : false
//       }, {
//         "value" : "assignment",
//         "label" : "assignment",
//         "checked" : false
//       }, {
//         "value" : "complete",
//         "label" : "complete",
//         "checked" : false
//       }, {
//         "value" : "delete",
//         "label" : "delete",
//         "checked" : false
//       } ],
//       "queryType" : [ {
//         "value" : "=",
//         "label" : "=",
//         "checked" : false
//       }, {
//         "value" : "!=",
//         "label" : "!=",
//         "checked" : false
//       }, {
//         "value" : ">",
//         "label" : ">",
//         "checked" : false
//       }, {
//         "value" : ">=",
//         "label" : ">=",
//         "checked" : false
//       }, {
//         "value" : "<",
//         "label" : "<",
//         "checked" : false
//       }, {
//         "value" : "<=",
//         "label" : "<=",
//         "checked" : false
//       }, {
//         "value" : "between",
//         "label" : "Between",
//         "checked" : false
//       }, {
//         "value" : "Like",
//         "label" : "Like",
//         "checked" : false
//       }, {
//         "value" : "LeftLike",
//         "label" : "Left Like",
//         "checked" : false
//       }, {
//         "value" : "RightLike",
//         "label" : "Right Like",
//         "checked" : false
//       } ],
//       "tplCategory" : [ {
//         "value" : "1",
//         "label" : "单表",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "主附表",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "自定义",
//         "checked" : false
//       } ],
//       "optType" : [ {
//         "value" : "add",
//         "label" : "添加",
//         "checked" : false
//       }, {
//         "value" : "remove",
//         "label" : "删除",
//         "checked" : false
//       }, {
//         "value" : "edit",
//         "label" : "编辑",
//         "checked" : false
//       }, {
//         "value" : "edit_password",
//         "label" : "修改密码",
//         "checked" : false
//       }, {
//         "value" : "reset_password",
//         "label" : "重置密码",
//         "checked" : false
//       }, {
//         "value" : "add_default_permission",
//         "label" : "添加默认权限",
//         "checked" : false
//       }, {
//         "value" : "login",
//         "label" : "登录",
//         "checked" : false
//       }, {
//         "value" : "logout",
//         "label" : "注销",
//         "checked" : false
//       }, {
//         "value" : "authorize",
//         "label" : "授权",
//         "checked" : false
//       }, {
//         "value" : "remove_authorize",
//         "label" : "取消授权",
//         "checked" : false
//       } ],
//       "typeNode" : [ {
//         "value" : "1",
//         "label" : "菜单",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "按钮",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "API接口",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "超链接",
//         "checked" : false
//       } ],
//       "urlTarget" : [ {
//         "value" : "_self",
//         "label" : "默认",
//         "checked" : false
//       }, {
//         "value" : "_blank",
//         "label" : "新窗口",
//         "checked" : false
//       }, {
//         "value" : "_parent",
//         "label" : "父窗口",
//         "checked" : false
//       }, {
//         "value" : "iframe",
//         "label" : "iframe",
//         "checked" : false
//       }, {
//         "value" : "_top",
//         "label" : "top",
//         "checked" : false
//       } ],
//       "dataResSys_changeType" : [ {
//         "value" : "0",
//         "label" : "新增数据库",
//         "checked" : false
//       }, {
//         "value" : "1",
//         "label" : "删除数据库",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "新增数据表",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "删除数据表",
//         "checked" : false
//       }, {
//         "value" : "4",
//         "label" : "新增表字段",
//         "checked" : false
//       }, {
//         "value" : "5",
//         "label" : "删除表字段",
//         "checked" : false
//       }, {
//         "value" : "6",
//         "label" : "表字段属性变更",
//         "checked" : false
//       } ],
//       "dataResSys_tagType" : [ {
//         "value" : "dataType",
//         "label" : "数据类型",
//         "tenantId" : "",
//         "checked" : false
//       }, {
//         "value" : "level",
//         "label" : "级别",
//         "tenantId" : "",
//         "checked" : false
//       }, {
//         "value" : "product",
//         "label" : "产品",
//         "tenantId" : "",
//         "checked" : false
//       } ],
//       "sendStatus" : [ {
//         "value" : "-1",
//         "label" : "发送失败",
//         "checked" : false
//       }, {
//         "value" : "0",
//         "label" : "发送中",
//         "checked" : false
//       }, {
//         "value" : "1",
//         "label" : "发送成功",
//         "checked" : false
//       } ],
//       "FeeMng_NodeLinkType" : [ {
//         "value" : "1",
//         "label" : "收费站至收费单元",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "收费单元至收费站",
//         "checked" : false
//       }, {
//         "value" : "3",
//         "label" : "收费单元至收费单元",
//         "checked" : false
//       } ],
//       "sectionType" : [ {
//         "value" : "1",
//         "label" : "经营性",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       }, {
//         "value" : "2",
//         "label" : "还贷性",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ],
//       "config" : [ {
//         "value" : "mo-heartBeat-periodMin",
//         "label" : "10",
//         "tenantId" : "",
//         "extra" : "",
//         "checked" : false
//       } ]
//     },
//     "realName" : "admin",
//     "phone" : "15889930671",
//     "isRunAs" : false,
//     "roleName" : "",
//     "toChangePwd" : false,
//     "position" : "收费员1",
//     "email" : "songlubiao@qq.com",
//     "username" : "admin"
//   },
//   "success" : true
// }
//
// const router = {}
//
// // const Mock = require('mockjs')
// const depts = require('./dept.js')
// const users = require('./user.js')
// const areas = require('./area.js')
//
//
//
// module.exports = [
//   // mock get all routes form server
//   // 获取验证码
//   {
//     url: '/api/auth/captcha',
//     type: 'get',
//     response: _ => {
//       return loginCaptcha
//     }
//   },
//   // 登陆成功
//   {
//     url: '/api/v1/login',
//     type: 'post',
//     response: _ => {
//       return loginSuccess
//     }
//   },
//   // 登陆成功
//   {
//     url: '/api/v1/user/info',
//     type: 'get',
//     response: _ => {
//       return userInfo
//     }
//   },
//   // 登陆成功
//   {
//     url: '/api/v1/system/router',
//     type: 'get',
//     response: _ => {
//       return userInfo
//     }
//   },
//   {
//     url: '/api/v1/dept/sysrDept/data',
//     type: 'get',
//     response: _ => {
//       return depts
//     }
//   },
//   {
//     url: '/api/v1/system/dept/tree',
//     type: 'get',
//     response: _ => {
//       return depts
//     }
//   },
//   {
//     url: '/api/v1/treedemo/treeDemo/data',
//     type: 'post',
//     response: _ => {
//       return depts
//     }
//   },
//   {
//     url: '/api/v1/sysrUser/page',
//     type: 'get',
//     response: _ => {
//       return users
//     }
//   },
//   {
//     url: '/api/v1/sysrUser/data',
//     type: 'post',
//     response: _ => {
//       return users
//     }
//   },
//   {
//     url: '/api/v1/sysrUser/findDataByIds',
//     type: 'post',
//     response: _ => {
//       return {
//         'code': 200,
//         'message': '操作成功',
//         'data': [
//           {
//             'id': '1276391174197739520',
//             'username': 'liutang',
//             'realName': '临桂六塘收费站',
//             'sex': '0',
//             'ustatus': 1,
//             'createTime': '2020-06-26T00:45:45.000+0800',
//             'tenantId': '1249628559274672128',
//             'sexText': '男',
//             'deptName': '',
//             'orgName': '六塘收费站'
//           }
//         ],
//         'success': true
//       }
//     }
//   },
//   {
//     url: '/api/v1/user/sysrUser/findDataByIds',
//     type: 'post',
//     response: _ => {
//       return {
//         'code': 200,
//         'message': '操作成功',
//         'data': [
//           {
//             'id': '1276391174197739520',
//             'username': 'liutang',
//             'realName': '临桂六塘收费站',
//             'sex': '0',
//             'ustatus': 1,
//             'createTime': '2020-06-26T00:45:45.000+0800',
//             'tenantId': '1249628559274672128',
//             'sexText': '男',
//             'deptName': '',
//             'orgName': '六塘收费站'
//           }
//         ],
//         'success': true
//       }
//     }
//   },
//   {
//     url: '/api/v1/area/sysrArea/data',
//     type: 'get',
//     response: _ => {
//       return areas
//     }
//   }
//
// ]
