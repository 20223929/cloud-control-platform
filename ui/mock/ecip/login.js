const Mock = require('mockjs')

const loginCaptcha = {
  "code": 200,
  "message": "操作成功",
  "data": {
    "key": "739402b1f9dfb0086df015f2cad64012",
    "captcha": "data:image/png;base64,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"
  },
  "success": true
}

const loginSuccess = {
  "code": 200,
  "message": "登陆成功",
  "data": {
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6NTUsImV4cCI6MTYxMTY2NTAzNiwidXNlcm5hbWUiOiJhZG1pbiJ9.pZNCr8gZojYjWomlsgHincGlJyvc0HYvaD2xWU7SQl8"
  },
  "success": true
}

const loginUserInfo = {
  "code": 200,
  "message": "操作成功",
  "data": {
    "userInfo": {
      "id": "-1",
      "username": "admin",
      "loginPwd": "7c2b4d2be8d60944b9bf8c970c525478",
      "salt": "1ae869d148a826004abb7113681574f6",
      "isSuperUser": true,
      "realName": "admin",
      "sex": "0",
      "email": "songlubiao@qq.com",
      "position": "收费员1",
      "phone": "15889930671",
      "createTime": "2018-08-27 15:00:40",
      "appId": -1,
      "loginFlag": true,
      "tenantId": "1249628559274672128",
      "childTenantIdList": [],
      "mailValid": true,
      "phoneValid": true
    },
    "sex": 0,
    "roles": ["oprationLog:print", "oprationLog:download", "singleTableComponents:add", "singleTableComponents:remove", "singleTableComponents:edit", "singleTableComponents:view", "singleTableComponents:import", "singleTableComponents:export", "singleTableComponents:print", "singleTableComponents:download", "singleTableComponents:upload", "singleTableComponents:delete", "testSingleTBDemo1:add", "testSingleTBDemo1:remove", "testSingleTBDemo1:edit", "testSingleTBDemo1:view", "testSingleTBDemo1:import", "testSingleTBDemo1:export", "testSingleTBDemo1:print", "testSingleTBDemo1:download", "testSingleTBDemo1:upload", "testSingleTBDemo1:delete", "multiPkSingleTable:add", "multiPkSingleTable:remove", "multiPkSingleTable:edit", "multiPkSingleTable:view", "multiPkSingleTable:import", "multiPkSingleTable:export", "multiPkSingleTable:print", "multiPkSingleTable:download", "multiPkSingleTable:upload", "multiPkSingleTable:delete", "testMainTB:add", "testMainTB:remove", "testMainTB:edit", "testMainTB:view", "testMainTB:import", "testMainTB:export", "testMainTB:print", "testMainTB:download", "testMainTB:upload", "testMainTB:delete", "testTreeTB:add", "testTreeTB:remove", "testTreeTB:edit", "testTreeTB:view", "testTreeTB:import", "testTreeTB:export", "testTreeTB:print", "testTreeTB:download", "testTreeTB:upload", "testTreeTB:delete", "testLeftTree:add", "testLeftTree:remove", "testLeftTree:edit", "testLeftTree:view", "testLeftTree:import", "testLeftTree:export", "testLeftTree:print", "testLeftTree:download", "testLeftTree:upload", "testLeftTree:delete", "testRightTb:add", "testRightTb:remove", "testRightTb:edit", "testRightTb:view", "testRightTb:import", "testRightTb:export", "testRightTb:print", "testRightTb:download", "testRightTb:upload", "testRightTb:delete", "role:add", "role:remove", "role:edit", "role:view", "role:import", "role:export", "role:print", "role:download", "role:upload", "role:delete", "resource:add", "resource:remove", "resource:edit", "resource:view", "resource:import", "resource:export", "resource:print", "resource:download", "resource:upload", "resource:delete", "dict:add", "dict:remove", "dict:edit", "dict:view", "dict:import", "dict:export", "dict:print", "dict:download", "dict:upload", "dict:delete", "area:add", "area:remove", "area:edit", "area:view", "area:import", "area:export", "area:print", "area:download", "area:upload", "area:delete", "role:user", "app:add", "app:remove", "app:edit", "app:view", "app:import", "app:export", "app:print", "app:download", "app:upload", "app:delete", "resource:sync", "resource:copy", "datarule:add", "datarule:remove", "datarule:edit", "datarule:view", "datarule:import", "datarule:export", "datarule:print", "datarule:download", "datarule:upload", "datarule:delete", "resource:route", "resource:defaultPermission", "role:copy", "role:resource:assign", "role:resource:cancel", "role:dept:assign", "role:datarule:assign", "role:org:assign", "app:tenant", "resource:fixPath", "tenant:add", "tenant:remove", "tenant:edit", "tenant:view", "tenant:import", "tenant:export", "tenant:print", "tenant:download", "tenant:upload", "tenant:delete", "org:add", "org:remove", "org:edit", "org:view", "org:import", "org:export", "org:print", "org:download", "org:upload", "org:delete", "dept:add", "dept:remove", "dept:edit", "dept:view", "dept:import", "dept:export", "dept:print", "dept:download", "dept:upload", "dept:delete", "user:add", "user:remove", "user:edit", "user:view", "user:import", "user:export", "user:print", "user:download", "user:upload", "user:delete", "tenant:copy", "user:permission", "tenant:app", "user:role", "user:dept", "user:org", "user:reset", "user:app", "admin"],
    "userId": "-1",
    "dictList": {
      "readStatus": [{
        "value": "unread",
        "label": "未读",
        "checked": false
      }, {
        "value": "read",
        "label": "已读",
        "checked": false
      }],
      "exSpecialType": [{
        "value": "1",
        "label": "OBU电量低",
        "extra": "2",
        "checked": false
      }, {
        "value": "2",
        "label": "OBU拆卸",
        "extra": "1",
        "checked": false
      }, {
        "value": "3",
        "label": "OBU过期",
        "extra": "2",
        "checked": false
      }, {
        "value": "4",
        "label": "OBU未启用",
        "extra": "2",
        "checked": false
      }, {
        "value": "5",
        "label": "OBU无卡",
        "extra": "1",
        "checked": false
      }, {
        "value": "6",
        "label": "OBU在状态名单里",
        "extra": "2",
        "checked": false
      }, {
        "value": "7",
        "label": "OBU已锁",
        "extra": "2",
        "checked": false
      }, {
        "value": "8",
        "label": "OBU发行方无效",
        "extra": "2",
        "checked": false
      }, {
        "value": "9",
        "label": "OBU车型不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "10",
        "label": "OBU EF04内前缀异常",
        "extra": "1",
        "checked": false
      }, {
        "value": "11",
        "label": "OBU EF04内标签无卡次数大于0",
        "extra": "1",
        "checked": false
      }, {
        "value": "12",
        "label": "OBU EF04内或单片式OBU收费站入口无效",
        "extra": "1",
        "checked": false
      }, {
        "value": "13",
        "label": "OBU EF04内通行省份数量为0",
        "extra": "2",
        "checked": false
      }, {
        "value": "14",
        "label": "OBU总累计应收金额异常（大）",
        "extra": "1",
        "checked": false
      }, {
        "value": "15",
        "label": "OBU总累计应收金额异常（小）",
        "extra": "1",
        "checked": false
      }, {
        "value": "16",
        "label": "出口省OBU本省实收累计金额异常",
        "extra": "1",
        "checked": false
      }, {
        "value": "17",
        "label": "OBU EF04内无本省入口编码信息",
        "extra": "2",
        "checked": false
      }, {
        "value": "18",
        "label": "OBU/CPC卡累计计费里程异常",
        "extra": "1",
        "checked": false
      }, {
        "value": "21",
        "label": "ETC卡过期",
        "extra": "2",
        "checked": false
      }, {
        "value": "22",
        "label": "ETC卡未启用",
        "extra": "2",
        "checked": false
      }, {
        "value": "23",
        "label": "ETC卡已锁",
        "extra": "2",
        "checked": false
      }, {
        "value": "24",
        "label": "ETC卡在状态名单内",
        "extra": "2",
        "checked": false
      }, {
        "value": "25",
        "label": "ETC卡发行方无效",
        "extra": "2",
        "checked": false
      }, {
        "value": "26",
        "label": "ETC/CPC卡入口无效",
        "extra": "1",
        "checked": false
      }, {
        "value": "27",
        "label": "ETC卡片储值卡余额不足",
        "extra": "2",
        "checked": false
      }, {
        "value": "28",
        "label": "ETC卡片余额为0",
        "extra": "2",
        "checked": false
      }, {
        "value": "29",
        "label": "ETC/CPC卡累计金额异常（大）",
        "extra": "1",
        "checked": false
      }, {
        "value": "30",
        "label": "ETC卡累计金额异常（小）",
        "extra": "1",
        "checked": false
      }, {
        "value": "31",
        "label": "ETC卡类型非法",
        "extra": "2",
        "checked": false
      }, {
        "value": "32",
        "label": "ETC卡车辆状态标识非法（货车）",
        "extra": "2",
        "checked": false
      }, {
        "value": "33",
        "label": "CPC卡电量过低",
        "extra": "2",
        "checked": false
      }, {
        "value": "34",
        "label": "CPC卡密钥UK1锁定",
        "extra": "2",
        "checked": false
      }, {
        "value": "35",
        "label": "CPC卡损坏",
        "extra": "1",
        "checked": false
      }, {
        "value": "36",
        "label": "无CPC卡",
        "extra": "1",
        "checked": false
      }, {
        "value": "37",
        "label": "入口站信息错误",
        "extra": "1",
        "checked": false
      }, {
        "value": "38",
        "label": "CPC 卡发行归属地异常",
        "extra": "1",
        "checked": false
      }, {
        "value": "39",
        "label": "CPC 卡状态异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "41",
        "label": "ETC卡与OBU车牌（含颜色）不符",
        "extra": "1",
        "checked": false
      }, {
        "value": "42",
        "label": "ETC卡与OBU车型不符",
        "extra": "1",
        "checked": false
      }, {
        "value": "43",
        "label": "ETC卡与OBU卡发行方不符",
        "extra": "2",
        "checked": false
      }, {
        "value": "44",
        "label": "ETC卡与OBU EF04内卡片文件不一致",
        "extra": "2",
        "checked": false
      }, {
        "value": "45",
        "label": "ETC卡与OBU EF04内入口信息不一致（入口站或者入口时间）",
        "extra": "1",
        "checked": false
      }, {
        "value": "46",
        "label": "ETC卡与OBU EF04内上个门架编号信息不一致（门架编号或通行时间）",
        "extra": "2",
        "checked": false
      }, {
        "value": "51",
        "label": "出入口车辆车型不符",
        "extra": "1",
        "checked": false
      }, {
        "value": "52",
        "label": "出入口车牌（含颜色）不符",
        "extra": "1",
        "checked": false
      }, {
        "value": "53",
        "label": "出入口车辆状态标识（货车ETC）不符",
        "extra": "1",
        "checked": false
      }, {
        "value": "54",
        "label": "入口时间异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "55",
        "label": "入口流通状态有误（非0x01/0x03/0x10）",
        "extra": "1",
        "checked": false
      }, {
        "value": "56",
        "label": "入口标签无卡或读卡出错或入口储值卡余额为0",
        "extra": "2",
        "checked": false
      }, {
        "value": "57",
        "label": "门架代写入口",
        "extra": "2",
        "checked": false
      }, {
        "value": "58",
        "label": "从进入路网到离开路网超时（未拦截无需记录，即按正常交易看待）",
        "extra": "1",
        "checked": false
      }, {
        "value": "59",
        "label": "路径不可通达",
        "extra": "1",
        "checked": false
      }, {
        "value": "60",
        "label": "入出口轴数不一致",
        "extra": "1",
        "checked": false
      }, {
        "value": "61",
        "label": "计费模块初始化失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "62",
        "label": "获取计费模块版本失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "63",
        "label": "计费模块查询费率返回失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "64",
        "label": "计费模块查询费率超时",
        "extra": "2",
        "checked": false
      }, {
        "value": "65",
        "label": "计费服务计费请求失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "66",
        "label": "路径不可通达",
        "extra": "1",
        "checked": false
      }, {
        "value": "67",
        "label": "拟合门架数过多",
        "extra": "1",
        "checked": false
      }, {
        "value": "68",
        "label": "计费金额异常",
        "extra": "1",
        "checked": false
      }, {
        "value": "71",
        "label": "无DSRC数据返回（超时）",
        "extra": "2",
        "checked": false
      }, {
        "value": "72",
        "label": "读取OBU车辆信息文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "73",
        "label": "解密OBU车辆信息失败(双片式OBU)",
        "extra": "2",
        "checked": false
      }, {
        "value": "74",
        "label": "读取ETC卡片文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "75",
        "label": "复合消费初始化失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "76",
        "label": "更新记录文件缓存失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "77",
        "label": "复合消费失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "78",
        "label": "获取TAC码失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "79",
        "label": "读取OBU EF04文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "80",
        "label": "更新OBU EF04文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "81",
        "label": "OBU外部认证失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "82",
        "label": "读取车辆信息文件失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "83",
        "label": "读取入/出口信息文件失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "84",
        "label": "更新入/出口信息文件失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "85",
        "label": "信息鉴别码无效（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "86",
        "label": "MAC1无效（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "87",
        "label": "MAC2无效（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "88",
        "label": "天线复位失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "89",
        "label": "未读取到各省累计计费信息",
        "extra": "2",
        "checked": false
      }, {
        "value": "91",
        "label": "PSAM卡黑名单",
        "extra": "1",
        "checked": false
      }, {
        "value": "92",
        "label": "PSAM已锁卡",
        "extra": "2",
        "checked": false
      }, {
        "value": "93",
        "label": "PSAM卡复位异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "94",
        "label": "授权服务请求异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "95",
        "label": "PSAM签到异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "96",
        "label": "PSAM操作异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "97",
        "label": "PSAM卡未授权",
        "extra": "1",
        "checked": false
      }, {
        "value": "101",
        "label": "交易流程不完整",
        "extra": "2",
        "checked": false
      }, {
        "value": "102",
        "label": "车道参数有误",
        "extra": "1",
        "checked": false
      }, {
        "value": "103",
        "label": "车道系统对时有误",
        "extra": "1",
        "checked": false
      }, {
        "value": "104",
        "label": "车道系统黑名单版本有误",
        "extra": "1",
        "checked": false
      }, {
        "value": "111",
        "label": "U型车拦截（未拦截无需记录）",
        "extra": "1",
        "checked": false
      }, {
        "value": "112",
        "label": "大客车限时通行（未拦截无需记录）",
        "extra": "2",
        "checked": false
      }, {
        "value": "113",
        "label": "最近门架HEX码不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "114",
        "label": "收费车道反向车辆",
        "extra": "2",
        "checked": false
      }, {
        "value": "115",
        "label": "邻道干扰",
        "extra": "2",
        "checked": false
      }, {
        "value": "116",
        "label": "节假日免费",
        "extra": "1",
        "checked": false
      }, {
        "value": "117",
        "label": "车辆闯关",
        "extra": "1",
        "checked": false
      }, {
        "value": "118",
        "label": "车牌在追缴黑名单",
        "extra": "1",
        "checked": false
      }, {
        "value": "119",
        "label": "车辆滞留车道时间过长",
        "extra": "1",
        "checked": false
      }, {
        "value": "120",
        "label": "砸车",
        "extra": "1",
        "checked": false
      }, {
        "value": "121",
        "label": "跟车逃费",
        "extra": "1",
        "checked": false
      }, {
        "value": "122",
        "label": "交易未完成（交易超时）",
        "extra": "1",
        "checked": false
      }, {
        "value": "123",
        "label": "无标签车辆",
        "extra": "1",
        "checked": false
      }, {
        "value": "124",
        "label": "一车多标",
        "extra": "1",
        "checked": false
      }, {
        "value": "125",
        "label": "入出口通行超时",
        "extra": "1",
        "checked": false
      }, {
        "value": "126",
        "label": "绿通查验不合格",
        "extra": "1",
        "checked": false
      }, {
        "value": "127",
        "label": "车牌在追缴灰名单",
        "extra": "1",
        "checked": false
      }, {
        "value": "129",
        "label": "无此车辆入口信息",
        "extra": "1",
        "checked": false
      }, {
        "value": "130",
        "label": "计费金额小于入出口可达路径最小费额",
        "extra": "2",
        "checked": false
      }, {
        "value": "131",
        "label": "跨区作业运输车查验不合格",
        "extra": "2",
        "checked": false
      }, {
        "value": "132",
        "label": "跨区作业运输车现场免费",
        "extra": "2",
        "checked": false
      }],
      "deviceLocationType": [{
        "value": "1",
        "label": "区域",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "2",
        "label": "监控点",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "3",
        "label": "库房",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "validateType": [{
        "value": "string",
        "label": "字符串",
        "checked": false
      }, {
        "value": "email",
        "label": "电子邮件",
        "checked": false
      }, {
        "value": "website",
        "label": "网址",
        "checked": false
      }, {
        "value": "date",
        "label": "日期",
        "checked": false
      }, {
        "value": "dateISO",
        "label": "日期（ISO）",
        "checked": false
      }, {
        "value": "cardNumber",
        "label": "信用卡号",
        "checked": false
      }, {
        "value": "phoneNumber",
        "label": "电话号码",
        "checked": false
      }, {
        "value": "mobilePhoneNumber",
        "label": "手机号码",
        "checked": false
      }, {
        "value": "mobilePhoneOrPhone",
        "label": "手机/电话",
        "checked": false
      }, {
        "value": "qqNumber",
        "label": "QQ号码",
        "checked": false
      }, {
        "value": "idNumber",
        "label": "身份证号码",
        "checked": false
      }, {
        "value": "number",
        "label": "数字",
        "checked": false
      }, {
        "value": "integer",
        "label": "整数",
        "checked": false
      }, {
        "value": "positiveInteger",
        "label": "整数（大于0）",
        "checked": false
      }, {
        "value": "nonNegativeInteger",
        "label": "整数（大于等于0）",
        "checked": false
      }, {
        "value": "negativeInteger",
        "label": "整数（小于0）",
        "checked": false
      }, {
        "value": "nonPositiveInteger",
        "label": "整数（小于等于0）",
        "checked": false
      }, {
        "value": "positiveFloatingPoint",
        "label": "浮点数（大于0）",
        "checked": false
      }, {
        "value": "nonNegativeFloatingPoint",
        "label": "浮点数（大于等于0）",
        "checked": false
      }, {
        "value": "negativeFloatingPoint",
        "label": "浮点数（小于0）",
        "checked": false
      }, {
        "value": "nonPositiveFloatingPoint",
        "label": "浮点数（小于等于0）",
        "checked": false
      }, {
        "value": "postalCode",
        "label": "邮政编码",
        "checked": false
      }, {
        "value": "password",
        "label": "密码",
        "checked": false
      }, {
        "value": "chineseEnglishNumbersUnderlines",
        "label": "中文/英文/数字/下划线",
        "checked": false
      }, {
        "value": "english",
        "label": "英语",
        "checked": false
      }, {
        "value": "chinese",
        "label": "汉字",
        "checked": false
      }, {
        "value": "englishOrChineseCharacters",
        "label": "英汉字符",
        "checked": false
      }, {
        "value": "legalCharacters",
        "label": "合法字符",
        "checked": false
      }],
      "huxiaolong": [{
        "value": "1",
        "label": "仪宇哥",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "datacenterCloudServerType": [{
        "value": "program",
        "label": "应用程序",
        "checked": false
      }, {
        "value": "database",
        "label": "数据库",
        "checked": false
      }, {
        "value": "middleware",
        "label": "中间件",
        "checked": false
      }],
      "editStatus": [{
        "value": "draft",
        "label": "草稿",
        "checked": false
      }, {
        "value": "sent",
        "label": "发送",
        "checked": false
      }],
      "executionEventType": [{
        "value": "start",
        "label": "start",
        "tenantId": "",
        "checked": false
      }, {
        "value": "end",
        "label": "end",
        "tenantId": "",
        "checked": false
      }, {
        "value": "take",
        "label": "take",
        "tenantId": "",
        "checked": false
      }],
      "form_type": [{
        "value": "table",
        "label": "关联表单",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "no_table",
        "label": "不关联表单",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "businesstype": [{
        "value": "1",
        "label": "验证码",
        "checked": false
      }, {
        "value": "2",
        "label": "通知",
        "checked": false
      }, {
        "value": "99",
        "label": "其他",
        "checked": false
      }],
      "dataResSys_dbType": [{
        "value": "mysql",
        "label": "MySQL",
        "checked": false
      }, {
        "value": "hbase",
        "label": "HBase",
        "checked": false
      }, {
        "value": "es",
        "label": "ES",
        "extra": "",
        "checked": false
      }],
      "orgType": [{
        "value": "1",
        "label": "路段",
        "checked": false
      }, {
        "value": "2",
        "label": "门架",
        "checked": false
      }, {
        "value": "3",
        "label": "收费站",
        "checked": false
      }],
      "monitorType": [{
        "value": "executionListener",
        "label": "执行监听器",
        "checked": false
      }, {
        "value": "taskListener",
        "label": "任务监听器",
        "tenantId": "",
        "checked": false
      }],
      "FeeMng_VersionType": [{
        "value": "1",
        "label": "路径参数",
        "checked": false
      }, {
        "value": "2",
        "label": "费率参数",
        "checked": false
      }],
      "payType": [{
        "value": "1",
        "label": "现金",
        "checked": false
      }, {
        "value": "2",
        "label": "其他第三方账户支付",
        "checked": false
      }, {
        "value": "3",
        "label": "银联卡支付",
        "checked": false
      }, {
        "value": "4",
        "label": "ETC 用户卡",
        "checked": false
      }, {
        "value": "6",
        "label": "支付宝",
        "checked": false
      }, {
        "value": "7",
        "label": "微信",
        "checked": false
      }],
      "isVehBlack": [{
        "value": "0",
        "label": "不是",
        "checked": false
      }, {
        "value": "1",
        "label": "是",
        "checked": false
      }],
      "FeeMng_ParamStatus": [{
        "value": "0",
        "label": "启用",
        "checked": false
      }, {
        "value": "1",
        "label": "停用",
        "checked": false
      }],
      "valueType": [{
        "value": "class",
        "label": "类",
        "checked": false
      }, {
        "value": "expression",
        "label": "表达式",
        "checked": false
      }, {
        "value": "delegateExpression",
        "label": "委托表达式",
        "checked": false
      }],
      "showType": [{
        "value": "input",
        "label": "单行文本",
        "checked": false
      }, {
        "value": "textarea",
        "label": "多行文本",
        "checked": false
      }, {
        "value": "umeditor",
        "label": "富文本编辑器",
        "checked": false
      }, {
        "value": "select",
        "label": "下拉选项",
        "checked": false
      }, {
        "value": "radiobox",
        "label": "单选按钮",
        "checked": false
      }, {
        "value": "checkbox",
        "label": "复选框",
        "checked": false
      }, {
        "value": "dateselect",
        "label": "日期选择",
        "checked": false
      }, {
        "value": "userselect",
        "label": "用户选择",
        "checked": false
      }, {
        "value": "deptselect",
        "label": "部门选择",
        "checked": false
      }, {
        "value": "cityselect",
        "label": "省市区三级联动",
        "checked": false
      }, {
        "value": "treeselect",
        "label": "树选择控件",
        "checked": false
      }, {
        "value": "fileselect",
        "label": "文件上传",
        "checked": false
      }, {
        "value": "gridselect",
        "label": "grid选择框",
        "checked": false
      }, {
        "value": "areaselect",
        "label": "区域选择",
        "checked": false
      }],
      "FeeMng_NodeType": [{
        "value": "1",
        "label": "普通收费单元",
        "checked": false
      }, {
        "value": "2",
        "label": "省界收费单元",
        "checked": false
      }, {
        "value": "3",
        "label": "收费站",
        "checked": false
      }],
      "vehicleType": [{
        "value": "1",
        "label": "一型客车",
        "checked": false
      }, {
        "value": "2",
        "label": "二型客车",
        "checked": false
      }, {
        "value": "3",
        "label": "三型客车",
        "checked": false
      }, {
        "value": "4",
        "label": "四型客车",
        "checked": false
      }, {
        "value": "11",
        "label": "一型货车",
        "checked": false
      }, {
        "value": "12",
        "label": "二型货车",
        "checked": false
      }, {
        "value": "13",
        "label": "三型货车",
        "checked": false
      }, {
        "value": "14",
        "label": "四型货车",
        "checked": false
      }, {
        "value": "15",
        "label": "五型货车",
        "checked": false
      }, {
        "value": "16",
        "label": "六型货车",
        "checked": false
      }, {
        "value": "21",
        "label": "一型专项作业车",
        "checked": false
      }, {
        "value": "22",
        "label": "二型专项作业车",
        "checked": false
      }, {
        "value": "23",
        "label": "三型专项作业车",
        "checked": false
      }, {
        "value": "24",
        "label": "四型专项作业车",
        "checked": false
      }, {
        "value": "25",
        "label": "五型专项作业车",
        "checked": false
      }, {
        "value": "26",
        "label": "六型专项作业车",
        "checked": false
      }],
      "deviceType": [{
        "value": "web",
        "label": "浏览器",
        "checked": false
      }, {
        "value": "app",
        "label": "App",
        "checked": false
      }],
      "typeApp": [{
        "value": "1",
        "label": "WEB应用",
        "checked": false
      }, {
        "value": "2",
        "label": "Restful API接口",
        "checked": false
      }, {
        "value": "3",
        "label": "报表系统",
        "checked": false
      }, {
        "value": "4",
        "label": "外部链接应用",
        "checked": false
      }, {
        "value": "0",
        "label": "其他通用",
        "checked": false
      }],
      "PrimaryKeyStrategy": [{
        "value": "1",
        "label": "自增长",
        "checked": false
      }, {
        "value": "2",
        "label": "UUID",
        "checked": false
      }],
      "FeeMng_CalcCode": [{
        "value": "200",
        "label": "成功",
        "checked": false
      }, {
        "value": "502",
        "label": "错误网关",
        "checked": false
      }, {
        "value": "701",
        "label": "文件操作异常",
        "checked": false
      }, {
        "value": "704",
        "label": "部中心：业务校验失败",
        "checked": false
      }, {
        "value": "716",
        "label": "部中心：响应失败",
        "checked": false
      }, {
        "value": "947",
        "label": "请求数据错误",
        "checked": false
      }, {
        "value": "951",
        "label": "文件md5校验失败",
        "checked": false
      }, {
        "value": "952",
        "label": "格式校验失败",
        "checked": false
      }, {
        "value": "994",
        "label": "上游系统数据异常",
        "checked": false
      }, {
        "value": "996",
        "label": "路径还原失败",
        "checked": false
      }, {
        "value": "997",
        "label": "计费模块计费失败",
        "checked": false
      }, {
        "value": "998",
        "label": "自行计费失败",
        "checked": false
      }, {
        "value": "999",
        "label": "系统异常",
        "checked": false
      }, {
        "value": "2001",
        "label": "服务禁用",
        "checked": false
      }, {
        "value": "2002",
        "label": "封装交易信息异常",
        "checked": false
      }, {
        "value": "2003",
        "label": "收费单元转化门架异常",
        "checked": false
      }, {
        "value": "2005",
        "label": "查询最短路径异常",
        "checked": false
      }, {
        "value": "2006",
        "label": "出口车道跨省特情计费失败",
        "checked": false
      }, {
        "value": "2007",
        "label": "在线计费计费不准确",
        "checked": false
      }, {
        "value": "2008",
        "label": "在线计费计费失败",
        "checked": false
      }, {
        "value": "2009",
        "label": "查询最小费额异常",
        "checked": false
      }],
      "module": [{
        "value": "sysr_user",
        "label": "用户",
        "checked": false
      }, {
        "value": "sysr_dept",
        "label": "组织",
        "checked": false
      }, {
        "value": "sysr_resource",
        "label": "资源",
        "checked": false
      }, {
        "value": "sysr_role",
        "label": "角色",
        "checked": false
      }, {
        "value": "ecip_dict",
        "label": "数据字典",
        "checked": false
      }, {
        "value": "sysr_user_role",
        "label": "用户角色",
        "checked": false
      }, {
        "value": "sysr_role_resource",
        "label": "角色资源",
        "checked": false
      }, {
        "value": "sys_role_data",
        "label": "角色数据",
        "checked": false
      }, {
        "value": "sysr_register_app",
        "label": "应用系统",
        "checked": false
      }, {
        "value": "data_rule",
        "label": "数据规则",
        "checked": false
      }, {
        "value": "sysr_datasource",
        "label": "数据源",
        "checked": false
      }, {
        "value": "custom_obj",
        "label": "自定义java对象",
        "checked": false
      }, {
        "value": "test_note",
        "label": "富文本测试",
        "checked": false
      }, {
        "value": "gencode_scheme",
        "label": "生成方案",
        "checked": false
      }, {
        "value": "gencode_template_group",
        "label": "代码生成器模板",
        "checked": false
      }, {
        "value": "test_pic",
        "label": "图片管理",
        "checked": false
      }, {
        "value": "gencode_table",
        "label": "表单配置",
        "checked": false
      }, {
        "value": "sysr_user_app",
        "label": "用户应用",
        "checked": false
      }, {
        "value": "\tuser_dept",
        "label": "用户组织",
        "checked": false
      }, {
        "value": "sysr_app_version",
        "label": "应用版本",
        "checked": false
      }, {
        "value": "saas_org",
        "label": "租户机构",
        "checked": false
      }],
      "dataType": [{
        "value": "1",
        "label": "入口通行流水",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "2",
        "label": "出口ETC交易流水",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "3",
        "label": "出口其他交易流水",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "4",
        "label": "承载门架流水",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "5",
        "label": "收费站车牌识别数据",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "6",
        "label": "入口站处理合计数",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "7",
        "label": "出口站处理合计数",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "syncIdType": [{
        "value": "src",
        "label": "跟随源记录",
        "checked": false
      }, {
        "value": "auto",
        "label": "自动生成新主键",
        "checked": false
      }],
      "JavaType": [{
        "value": "String",
        "label": "String",
        "checked": false
      }, {
        "value": "Long",
        "label": "Long",
        "checked": false
      }, {
        "value": "Integer",
        "label": "Integer",
        "checked": false
      }, {
        "value": "Double",
        "label": "Double",
        "checked": false
      }, {
        "value": "Date",
        "label": "Date",
        "checked": false
      }, {
        "value": "com.hgsoft.ecip.web.rbac.entity.SysrDept",
        "label": "Dept",
        "checked": false
      }, {
        "value": "com.hgsoft.ecip.example.test_common.entity.TestArea",
        "label": "Area",
        "checked": false
      }, {
        "value": "com.hgsoft.ecip.framework.shiro.ShiroUser",
        "label": "User",
        "checked": false
      }, {
        "value": "com.hgsoft.ecip.web.rbac.entity.SysrFile",
        "label": "File",
        "checked": false
      }, {
        "value": "This",
        "label": "ThisObj",
        "checked": false
      }, {
        "value": "com.hgsoft.modules.testdatamain.entity.TestDataMainForm",
        "label": "TestDataMainForm",
        "checked": false
      }, {
        "value": "com.hgsoft.modules.treedemo.entity.TreeDemo",
        "label": "TreeDemo",
        "checked": false
      }, {
        "value": "com.hgsoft.ecip.web.rbac.entity.SysrArea",
        "label": "Area",
        "checked": false
      }, {
        "value": "com.hgsoft.ecip.api.vo.SysrDeptVo",
        "label": "DeptVo",
        "checked": false
      }, {
        "value": "com.hgsoft.ecip.api.vo.FileVo",
        "label": "FileVo",
        "checked": false
      }, {
        "value": "com.hgsoft.ecip.api.vo.AreaVo",
        "label": "AreaVo",
        "checked": false
      }, {
        "value": "com.hgsoft.yfzx.tibms.laneenlist.entity.LaneEnList",
        "label": "LaneEnList",
        "checked": false
      }, {
        "value": "com.hgsoft.modules.null.entity.TestOrder",
        "label": "TestOrder",
        "checked": false
      }, {
        "value": "com.hgsoft.modules.null.entity.TestOrder",
        "label": "TestOrder",
        "tenantId": "",
        "checked": false
      }],
      "gantrySpecialType": [{
        "value": "101",
        "label": "标签拆卸",
        "extra": "2",
        "checked": false
      }, {
        "value": "102",
        "label": "标签无卡",
        "extra": "2",
        "checked": false
      }, {
        "value": "103",
        "label": "标签锁定",
        "extra": "2",
        "checked": false
      }, {
        "value": "104",
        "label": "标签未到启用日期",
        "extra": "2",
        "checked": false
      }, {
        "value": "105",
        "label": "标签有效期已过",
        "extra": "2",
        "checked": false
      }, {
        "value": "106",
        "label": "标签未联网",
        "extra": "2",
        "checked": false
      }, {
        "value": "107",
        "label": "标签无车牌（全0或者全F）",
        "extra": "2",
        "checked": false
      }, {
        "value": "108",
        "label": "标签车牌不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "109",
        "label": "标签车型不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "110",
        "label": "标签预激活",
        "extra": "2",
        "checked": false
      }, {
        "value": "111",
        "label": "标签无入口",
        "extra": "2",
        "checked": false
      }, {
        "value": "112",
        "label": "标签无卡号",
        "extra": "2",
        "checked": false
      }, {
        "value": "113",
        "label": "标签内省份数量有误",
        "extra": "2",
        "checked": false
      }, {
        "value": "114",
        "label": "标签低电量",
        "extra": "2",
        "checked": false
      }, {
        "value": "116",
        "label": "卡片未到启用日期",
        "extra": "2",
        "checked": false
      }, {
        "value": "117",
        "label": "卡片有效期已过",
        "extra": "2",
        "checked": false
      }, {
        "value": "118",
        "label": "卡片错误",
        "extra": "2",
        "checked": false
      }, {
        "value": "119",
        "label": "非联网区域卡",
        "extra": "2",
        "checked": false
      }, {
        "value": "120",
        "label": "卡片无车牌（全0或者全F）",
        "extra": "2",
        "checked": false
      }, {
        "value": "121",
        "label": "卡片车牌不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "122",
        "label": "卡片车型不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "123",
        "label": "卡片类型不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "124",
        "label": "CPC卡电量低",
        "extra": "2",
        "checked": false
      }, {
        "value": "130",
        "label": "卡签发行属地不一致",
        "extra": "2",
        "checked": false
      }, {
        "value": "131",
        "label": "卡签车牌不一致",
        "extra": "2",
        "checked": false
      }, {
        "value": "132",
        "label": "卡签车型不一致",
        "extra": "2",
        "checked": false
      }, {
        "value": "133",
        "label": "卡签入口信息不一致",
        "extra": "2",
        "checked": false
      }, {
        "value": "134",
        "label": "卡签卡号不一致",
        "extra": "2",
        "checked": false
      }, {
        "value": "135",
        "label": "无入口网络编号（全0或全F）",
        "extra": "2",
        "checked": false
      }, {
        "value": "136",
        "label": "无入口收费站信息（全0或全F）",
        "extra": "2",
        "checked": false
      }, {
        "value": "137",
        "label": "入口流通状态有误（非0x01/0x03/0x10）",
        "extra": "2",
        "checked": false
      }, {
        "value": "138",
        "label": "无入口车道号（全0或全F）",
        "extra": "2",
        "checked": false
      }, {
        "value": "139",
        "label": "入口车型不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "140",
        "label": "入口时间不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "141",
        "label": "入口车轴不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "142",
        "label": "入口车辆状态标识不合法(ETC)",
        "extra": "2",
        "checked": false
      }, {
        "value": "143",
        "label": "入口车种不合法(CPC)",
        "extra": "2",
        "checked": false
      }, {
        "value": "145",
        "label": "计费模块初始化失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "146",
        "label": "计费模块释放资源失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "147",
        "label": "获取计费模块版本失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "148",
        "label": "获取计费参数版本失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "149",
        "label": "计费模块查询费率返回失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "150",
        "label": "计费模块查询费率超时",
        "extra": "2",
        "checked": false
      }, {
        "value": "151",
        "label": "计费服务计费请求失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "152",
        "label": "卡片累计金额异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "153",
        "label": "标签累计金额异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "154",
        "label": "反向干扰（计费模块判断）",
        "extra": "2",
        "checked": false
      }, {
        "value": "155",
        "label": "读卡/标签无DSRC数据返回（超时）",
        "extra": "2",
        "checked": false
      }, {
        "value": "156",
        "label": "上行DSRC数据解码错误",
        "extra": "2",
        "checked": false
      }, {
        "value": "157",
        "label": "找不到文件",
        "extra": "2",
        "checked": false
      }, {
        "value": "158",
        "label": "解密OBU车辆信息失败（校验码对比失败）",
        "extra": "2",
        "checked": false
      }, {
        "value": "159",
        "label": "OBU外部认证失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "160",
        "label": "OBU读取0002文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "161",
        "label": "OBU读取0015文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "162",
        "label": "读取0019文件返回失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "163",
        "label": "读取入/出口信息文件失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "164",
        "label": "复合消费初始化失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "165",
        "label": "更新记录文件缓存失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "166",
        "label": "复合消费失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "167",
        "label": "更新交易记录文件失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "168",
        "label": "CPC卡外部认证失败（PSAM返回）",
        "extra": "2",
        "checked": false
      }, {
        "value": "169",
        "label": "CPC卡读DF01/EF02文件失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "170",
        "label": "CPC卡读DF01/EF04文件失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "171",
        "label": "CPC卡写过站信息失败，写计费信息成功",
        "extra": "2",
        "checked": false
      }, {
        "value": "172",
        "label": "CPC卡写过站信息成功，写计费信息失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "173",
        "label": "CPC卡写过站信息、写计费信息失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "174",
        "label": "CPC卡写计费信息失败（省界出口ETC门架）",
        "extra": "2",
        "checked": false
      }, {
        "value": "175",
        "label": "CPC卡读过站信息文件（DF01/EF02）失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "176",
        "label": "CPC卡更新过站信息文件失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "177",
        "label": "PSAM卡外部认证失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "178",
        "label": "获取TAC码失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "179",
        "label": "PSAM卡MAC1计算失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "180",
        "label": "MAC2 校验失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "181",
        "label": "计算TAC失败（双片式OBU、单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "182",
        "label": "CPC卡外部认证失败（CPC 卡返回）",
        "extra": "2",
        "checked": false
      }, {
        "value": "183",
        "label": "写卡/标签无DSRC数据返回（超时）",
        "extra": "2",
        "checked": false
      }, {
        "value": "184",
        "label": "解密OBU车辆信息失败(PSAM 卡解密失败)",
        "extra": "2",
        "checked": false
      }, {
        "value": "185",
        "label": "前序已完成过卡片复合交易",
        "extra": "2",
        "checked": false
      }, {
        "value": "186",
        "label": "反向干扰（门架软件判断）",
        "extra": "2",
        "checked": false
      }, {
        "value": "187",
        "label": "余额不足",
        "extra": "2",
        "checked": false
      }, {
        "value": "188",
        "label": "节假日免费",
        "extra": "2",
        "checked": false
      }, {
        "value": "189",
        "label": "疑似持CPC的ETC无效用户",
        "extra": "2",
        "checked": false
      }, {
        "value": "190",
        "label": "前序已完成过标签交易",
        "extra": "2",
        "checked": false
      }, {
        "value": "191",
        "label": "需要重清过站信息（仅用于CPC卡省界出口）",
        "extra": "2",
        "checked": false
      }, {
        "value": "192",
        "label": "交易流程不完整（天线无响应导致流程不完整）",
        "extra": "2",
        "checked": false
      }, {
        "value": "193",
        "label": "前排已经处理，查询共享后拒绝处理。",
        "extra": "2",
        "checked": false
      }, {
        "value": "194",
        "label": "车辆跨省",
        "extra": "2",
        "checked": false
      }, {
        "value": "200",
        "label": "EF04内信息与加密摘要不匹配",
        "extra": "2",
        "checked": false
      }, {
        "value": "201",
        "label": "生成加密摘要失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "202",
        "label": "其他异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "210",
        "label": "标签无卡、选择/读取 EF04 文件成功（双片式 OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "211",
        "label": "标签无卡、选择/读取 EF04 文件失败（双片式 OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "212",
        "label": "标签有卡、读取 ETC 卡片文件成功，选择/读取 EF04 文件失败（双片式 OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "213",
        "label": "标签有卡、读取 ETC 卡片文件失败，选择/读取 EF04 文件失败（双片式 OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "214",
        "label": "标签有卡、读取 ETC 卡片文件失败，选择/读取 EF04 文件成功（双片式 OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "215",
        "label": "更新 EF04 文件失败（双片式 OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "216",
        "label": "复合消费失败（双片式 OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "217",
        "label": "更新 EF04 文件成功、复合消费失败（双片式 OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "218",
        "label": "更新 EF04 文件失败、复合消费失败（双片式 OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "219",
        "label": "更新 EF04 文件失败、复合消费成功（双片式 OBU）",
        "extra": "2",
        "checked": false
      }],
      "FeeMng_DownStatus": [{
        "value": "0",
        "label": "未下发",
        "checked": false
      }, {
        "value": "1",
        "label": "已下发",
        "checked": false
      }],
      "mo-heartBeatStatus": [{
        "value": "normal",
        "label": "正常",
        "checked": false
      }, {
        "value": "abnormal",
        "label": "异常",
        "checked": false
      }],
      "vehiclePlateColor": [{
        "value": "0",
        "label": "蓝色",
        "checked": false
      }, {
        "value": "1",
        "label": "黄色",
        "checked": false
      }, {
        "value": "2",
        "label": "黑色",
        "checked": false
      }, {
        "value": "3",
        "label": "白色",
        "checked": false
      }, {
        "value": "4",
        "label": "渐变绿色",
        "checked": false
      }, {
        "value": "5",
        "label": " 黄绿双拼色",
        "checked": false
      }, {
        "value": "6",
        "label": "蓝白渐变色",
        "checked": false
      }, {
        "value": "7",
        "label": "临时牌照",
        "checked": false
      }, {
        "value": "11",
        "label": "绿色",
        "checked": false
      }, {
        "value": "12",
        "label": "红色",
        "checked": false
      }],
      "FeeMng_CalcResult": [{
        "value": "1",
        "label": "成功",
        "checked": false
      }, {
        "value": "0",
        "label": "失败",
        "checked": false
      }],
      "dataRuleCondition": [{
        "value": "=",
        "label": "等于",
        "checked": false
      }, {
        "value": "!=",
        "label": "不等于",
        "checked": false
      }, {
        "value": "<",
        "label": "小于",
        "checked": false
      }, {
        "value": "<=",
        "label": "小于等于",
        "checked": false
      }, {
        "value": ">=",
        "label": "大于等于",
        "checked": false
      }, {
        "value": "like",
        "label": "模糊匹配",
        "checked": false
      }, {
        "value": "RightLike",
        "label": "RightLike",
        "checked": false
      }, {
        "value": "LeftLike",
        "label": "LeftLike",
        "checked": false
      }],
      "syncType": [{
        "value": "user",
        "label": "同步用户",
        "checked": false
      }, {
        "value": "dept",
        "label": "同步组织",
        "checked": false
      }, {
        "value": "other",
        "label": "自定义",
        "checked": false
      }],
      "FeeMng_UpStatus": [{
        "value": "0",
        "label": "未上传",
        "checked": false
      }, {
        "value": "1",
        "label": "已上传",
        "checked": false
      }],
      "FeeMng_VersionStatus": [{
        "value": "1",
        "label": "已发布",
        "checked": false
      }, {
        "value": "0",
        "label": "待发布",
        "checked": false
      }, {
        "value": "2",
        "label": "发布中",
        "checked": false
      }],
      "versionStatus": [{
        "value": "1",
        "label": "待审核",
        "checked": false
      }, {
        "value": "2",
        "label": "待发布",
        "checked": false
      }, {
        "value": "3",
        "label": "已发布",
        "checked": false
      }],
      "131": [{
        "value": "13123",
        "label": "3123",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "tableType": [{
        "value": "singleTable",
        "label": "单表",
        "checked": false
      }, {
        "value": "masterTable",
        "label": "主表",
        "checked": false
      }, {
        "value": "attachTable",
        "label": "附表",
        "checked": false
      }, {
        "value": "treeStructureTable",
        "label": "树结构表",
        "checked": false
      }, {
        "value": "leftTreeMainTable",
        "label": "左树(主表)",
        "checked": false
      }, {
        "value": "rightTableSchedule",
        "label": "右表（附表）",
        "checked": false
      }, {
        "value": "workflowForms",
        "label": "工作流表单",
        "checked": false
      }],
      "tbDeviceType": [{
        "value": "1",
        "label": "天线",
        "checked": false
      }, {
        "value": "2",
        "label": "车牌识别设备",
        "checked": false
      }, {
        "value": "3",
        "label": "动环工控机",
        "checked": false
      }, {
        "value": "4",
        "label": "机柜",
        "checked": false
      }, {
        "value": "5",
        "label": "电源",
        "checked": false
      }, {
        "value": "6",
        "label": "空调",
        "checked": false
      }, {
        "value": "7",
        "label": "防雷",
        "checked": false
      }, {
        "value": "8",
        "label": "工控机",
        "checked": false
      }, {
        "value": "9",
        "label": "服务器",
        "checked": false
      }, {
        "value": "10",
        "label": "读卡器",
        "checked": false
      }, {
        "value": "11",
        "label": "移动支付",
        "checked": false
      }, {
        "value": "12",
        "label": "车检器",
        "checked": false
      }, {
        "value": "13",
        "label": "轴型检测器",
        "checked": false
      }, {
        "value": "14",
        "label": "光栅",
        "checked": false
      }, {
        "value": "15",
        "label": "车道摄像机",
        "checked": false
      }, {
        "value": "16",
        "label": "费额显示屏",
        "checked": false
      }, {
        "value": "17",
        "label": "信息提示屏",
        "checked": false
      }, {
        "value": "18",
        "label": "通行信号灯",
        "checked": false
      }, {
        "value": "19",
        "label": "ETC情报板",
        "checked": false
      }, {
        "value": "20",
        "label": "入口治超称重设备",
        "checked": false
      }, {
        "value": "21",
        "label": "入口治超车牌识别",
        "checked": false
      }, {
        "value": "22",
        "label": "入口治超前身抓拍",
        "checked": false
      }, {
        "value": "23",
        "label": "入口治超尾部抓拍",
        "checked": false
      }, {
        "value": "24",
        "label": "入口治超RSU",
        "checked": false
      }, {
        "value": "25",
        "label": "入口治超轮廓仪",
        "checked": false
      }, {
        "value": "26",
        "label": "入口治超费显显示屏",
        "checked": false
      }, {
        "value": "27",
        "label": "入口治超通行信号灯",
        "checked": false
      }, {
        "value": "28",
        "label": "网关/交换机",
        "extra": "",
        "checked": false
      }],
      "enExType": [{
        "value": "1",
        "label": "入口",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "2",
        "label": "出口",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "jdbcType": [{
        "value": "varchar(64)",
        "label": "varchar(64)",
        "checked": false
      }, {
        "value": "nvarchar(64)",
        "label": "nvarchar(64)",
        "checked": false
      }, {
        "value": "integer",
        "label": "integer",
        "checked": false
      }, {
        "value": "double",
        "label": "double",
        "checked": false
      }, {
        "value": "datetime",
        "label": "datetime",
        "checked": false
      }, {
        "value": "longblob",
        "label": "longblob",
        "checked": false
      }, {
        "value": "longtext",
        "label": "longtext",
        "checked": false
      }],
      "areaType": [{
        "value": "country",
        "label": "国家",
        "checked": false
      }, {
        "value": "city",
        "label": "市",
        "checked": false
      }, {
        "value": "district",
        "label": "区(县)",
        "checked": false
      }, {
        "value": "town",
        "label": "镇(乡)",
        "checked": false
      }, {
        "value": "village",
        "label": "村",
        "checked": false
      }],
      "vehicleClass": [{
        "value": "0",
        "label": "普通",
        "checked": false
      }, {
        "value": "8",
        "label": "军警",
        "checked": false
      }, {
        "value": "10",
        "label": "紧急",
        "checked": false
      }, {
        "value": "14",
        "label": "车队",
        "checked": false
      }, {
        "value": "21",
        "label": "绿通车",
        "checked": false
      }, {
        "value": "22",
        "label": "联合收割机",
        "checked": false
      }, {
        "value": "23",
        "label": "抢险救灾",
        "checked": false
      }, {
        "value": "24",
        "label": "集装箱",
        "checked": false
      }, {
        "value": "25",
        "label": "大件运输",
        "checked": false
      }, {
        "value": "26",
        "label": "应急车",
        "checked": false
      }],
      "enSpecialType": [{
        "value": "1",
        "label": "OBU电量低",
        "extra": "2",
        "checked": false
      }, {
        "value": "2",
        "label": "OBU拆卸",
        "extra": "1",
        "checked": false
      }, {
        "value": "3",
        "label": "OBU过期",
        "extra": "2",
        "checked": false
      }, {
        "value": "4",
        "label": "OBU未启用",
        "extra": "2",
        "checked": false
      }, {
        "value": "5",
        "label": "OBU无卡",
        "extra": "1",
        "checked": false
      }, {
        "value": "6",
        "label": "OBU在状态名单里",
        "extra": "2",
        "checked": false
      }, {
        "value": "7",
        "label": "OBU已锁",
        "extra": "2",
        "checked": false
      }, {
        "value": "8",
        "label": "OBU发行方无效",
        "extra": "2",
        "checked": false
      }, {
        "value": "9",
        "label": "OBU车型不合法",
        "extra": "2",
        "checked": false
      }, {
        "value": "21",
        "label": "ETC卡过期",
        "extra": "2",
        "checked": false
      }, {
        "value": "22",
        "label": "ETC卡未启用",
        "extra": "2",
        "checked": false
      }, {
        "value": "23",
        "label": "ETC卡已锁",
        "extra": "2",
        "checked": false
      }, {
        "value": "24",
        "label": "ETC卡在状态名单内",
        "extra": "2",
        "checked": false
      }, {
        "value": "25",
        "label": "ETC卡发行方无效",
        "extra": "2",
        "checked": false
      }, {
        "value": "28",
        "label": "ETC卡片余额为0",
        "extra": "2",
        "checked": false
      }, {
        "value": "31",
        "label": "ETC卡类型非法",
        "extra": "2",
        "checked": false
      }, {
        "value": "33",
        "label": "CPC卡电量过低",
        "extra": "2",
        "checked": false
      }, {
        "value": "41",
        "label": "ETC卡与OBU车牌（含颜色）不符",
        "extra": "1",
        "checked": false
      }, {
        "value": "42",
        "label": "ETC卡与OBU车型不符",
        "extra": "1",
        "checked": false
      }, {
        "value": "43",
        "label": "ETC卡与OBU卡发行方不符",
        "extra": "2",
        "checked": false
      }, {
        "value": "71",
        "label": "无DSRC数据返回（超时）",
        "extra": "2",
        "checked": false
      }, {
        "value": "72",
        "label": "读取OBU车辆信息文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "73",
        "label": "解密OBU车辆信息失败(双片式OBU)",
        "extra": "2",
        "checked": false
      }, {
        "value": "74",
        "label": "读取ETC卡片文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "75",
        "label": "复合消费初始化失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "76",
        "label": "更新记录文件缓存失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "77",
        "label": "复合消费失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "78",
        "label": "获取TAC码失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "79",
        "label": "读取OBU EF04文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "80",
        "label": "更新OBU EF04文件失败（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "81",
        "label": "OBU外部认证失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "82",
        "label": "读取车辆信息文件失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "83",
        "label": "读取入/出口信息文件失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "84",
        "label": "更新入/出口信息文件失败（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "85",
        "label": "信息鉴别码无效（单片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "86",
        "label": "MAC1无效（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "87",
        "label": "MAC2无效（双片式OBU）",
        "extra": "2",
        "checked": false
      }, {
        "value": "88",
        "label": "天线复位失败",
        "extra": "2",
        "checked": false
      }, {
        "value": "91",
        "label": "PSAM卡黑名单",
        "extra": "1",
        "checked": false
      }, {
        "value": "92",
        "label": "PSAM已锁卡",
        "extra": "2",
        "checked": false
      }, {
        "value": "93",
        "label": "PSAM卡复位异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "94",
        "label": "授权服务请求异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "95",
        "label": "PSAM签到异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "96",
        "label": "PSAM操作异常",
        "extra": "2",
        "checked": false
      }, {
        "value": "97",
        "label": "PSAM卡未授权",
        "extra": "1",
        "checked": false
      }, {
        "value": "101",
        "label": "交易流程不完整",
        "extra": "2",
        "checked": false
      }, {
        "value": "102",
        "label": "车道参数有误",
        "extra": "1",
        "checked": false
      }, {
        "value": "103",
        "label": "车道系统对时有误",
        "extra": "1",
        "checked": false
      }, {
        "value": "104",
        "label": "车道系统黑名单版本有误",
        "extra": "1",
        "checked": false
      }, {
        "value": "112",
        "label": "大客车限时通行（未拦截无需记录）",
        "extra": "2",
        "checked": false
      }, {
        "value": "114",
        "label": "收费车道反向车辆",
        "extra": "2",
        "checked": false
      }, {
        "value": "115",
        "label": "邻道干扰",
        "extra": "2",
        "checked": false
      }, {
        "value": "116",
        "label": "节假日免费",
        "extra": "1",
        "checked": false
      }, {
        "value": "117",
        "label": "车辆闯关",
        "extra": "1",
        "checked": false
      }, {
        "value": "118",
        "label": "车牌在追缴黑名单",
        "extra": "1",
        "checked": false
      }, {
        "value": "119",
        "label": "车辆滞留车道时间过长",
        "extra": "1",
        "checked": false
      }, {
        "value": "120",
        "label": "砸车",
        "extra": "1",
        "checked": false
      }, {
        "value": "121",
        "label": "跟车逃费",
        "extra": "1",
        "checked": false
      }, {
        "value": "122",
        "label": "交易未完成（交易超时）",
        "extra": "1",
        "checked": false
      }, {
        "value": "123",
        "label": "无标签车辆",
        "extra": "1",
        "checked": false
      }, {
        "value": "124",
        "label": "一车多标",
        "extra": "1",
        "checked": false
      }, {
        "value": "127",
        "label": "车牌在追缴灰名单",
        "extra": "1",
        "checked": false
      }],
      "datacenterMonitorType": [{
        "value": "ExtractApplication",
        "label": "指令采集服务",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "instruction",
        "label": "IP拨测服务",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "authType": [{
        "value": "1",
        "label": "Cookie-Session",
        "checked": false
      }, {
        "value": "2",
        "label": "JWT Token",
        "checked": false
      }, {
        "value": "3",
        "label": "OAuth认证",
        "checked": false
      }],
      "retention": [{
        "value": "0",
        "label": "等于0",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "1",
        "label": "大于0",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "laneType": [{
        "value": "1",
        "label": "ETC车道",
        "checked": false
      }, {
        "value": "2",
        "label": "MTC车道",
        "checked": false
      }, {
        "value": "3",
        "label": "混合车道",
        "checked": false
      }, {
        "value": "4",
        "label": "复式混合车道",
        "checked": false
      }, {
        "value": "5",
        "label": "虚拟车道（通过便携机收费）",
        "checked": false
      }],
      "enableState": [{
        "value": "1",
        "label": "启用",
        "tenantId": "",
        "checked": false
      }, {
        "value": "0",
        "label": "未启用",
        "tenantId": "",
        "checked": false
      }],
      "genIdType": [{
        "value": "1",
        "label": "UUID",
        "checked": false
      }, {
        "value": "2",
        "label": "IdWorker",
        "checked": false
      }],
      "FeeMng_CalcType": [{
        "value": "1",
        "label": "通行费计费",
        "checked": false
      }, {
        "value": "2",
        "label": "无入口特情计费",
        "checked": false
      }, {
        "value": "3",
        "label": "出入口信息不符特情计费",
        "checked": false
      }, {
        "value": "4",
        "label": "OBU及ETC特情计费",
        "checked": false
      }, {
        "value": "5",
        "label": "本省省界触发计费",
        "checked": false
      }, {
        "value": "6",
        "label": "非本省出口计费",
        "checked": false
      }, {
        "value": "7",
        "label": "ETC计费",
        "checked": false
      }, {
        "value": "8",
        "label": "门架计费拆分",
        "checked": false
      }, {
        "value": "9",
        "label": "全网最短路径计费",
        "checked": false
      }, {
        "value": "10",
        "label": "出口车道跨省特情计费",
        "checked": false
      }, {
        "value": "11",
        "label": "跨省入口站查询",
        "checked": false
      }, {
        "value": "12",
        "label": "全网最小费额计费",
        "checked": false
      }, {
        "value": "13",
        "label": "非本省出口计费",
        "extra": "",
        "checked": false
      }],
      "sex": [{
        "value": "0",
        "label": "男",
        "checked": false
      }, {
        "value": "1",
        "label": "女",
        "checked": false
      }],
      "FeeMng_ModelDirection": [{
        "value": "1",
        "label": "有向",
        "checked": false
      }, {
        "value": "2",
        "label": "无向",
        "checked": false
      }],
      "dbType": [{
        "value": "mysql",
        "label": "MySql",
        "checked": false
      }, {
        "value": "oracle",
        "label": "Oracle",
        "checked": false
      }, {
        "value": "sqlserver",
        "label": "SqlServer",
        "checked": false
      }, {
        "value": "db2",
        "label": "DB2",
        "checked": false
      }],
      "noticeType": [{
        "value": "system",
        "label": "系统消息",
        "checked": false
      }, {
        "value": "flow",
        "label": "工单消息",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "alarm",
        "label": "告警消息",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "typeSrc": [{
        "value": "1",
        "label": "WEB",
        "tenantId": "",
        "checked": false
      }, {
        "value": "2",
        "label": "API",
        "tenantId": "",
        "checked": false
      }, {
        "value": "3",
        "label": "报表资源",
        "tenantId": "",
        "checked": false
      }],
      "taskLisenerEventType": [{
        "value": "start",
        "label": "start",
        "checked": false
      }, {
        "value": "assignment",
        "label": "assignment",
        "checked": false
      }, {
        "value": "complete",
        "label": "complete",
        "checked": false
      }, {
        "value": "delete",
        "label": "delete",
        "checked": false
      }],
      "queryType": [{
        "value": "=",
        "label": "=",
        "checked": false
      }, {
        "value": "!=",
        "label": "!=",
        "checked": false
      }, {
        "value": ">",
        "label": ">",
        "checked": false
      }, {
        "value": ">=",
        "label": ">=",
        "checked": false
      }, {
        "value": "<",
        "label": "<",
        "checked": false
      }, {
        "value": "<=",
        "label": "<=",
        "checked": false
      }, {
        "value": "between",
        "label": "Between",
        "checked": false
      }, {
        "value": "Like",
        "label": "Like",
        "checked": false
      }, {
        "value": "LeftLike",
        "label": "Left Like",
        "checked": false
      }, {
        "value": "RightLike",
        "label": "Right Like",
        "checked": false
      }],
      "tplCategory": [{
        "value": "1",
        "label": "单表",
        "checked": false
      }, {
        "value": "2",
        "label": "主附表",
        "checked": false
      }, {
        "value": "3",
        "label": "自定义",
        "checked": false
      }],
      "optType": [{
        "value": "add",
        "label": "添加",
        "checked": false
      }, {
        "value": "remove",
        "label": "删除",
        "checked": false
      }, {
        "value": "edit",
        "label": "编辑",
        "checked": false
      }, {
        "value": "edit_password",
        "label": "修改密码",
        "checked": false
      }, {
        "value": "reset_password",
        "label": "重置密码",
        "checked": false
      }, {
        "value": "add_default_permission",
        "label": "添加默认权限",
        "checked": false
      }, {
        "value": "login",
        "label": "登录",
        "checked": false
      }, {
        "value": "logout",
        "label": "注销",
        "checked": false
      }, {
        "value": "authorize",
        "label": "授权",
        "checked": false
      }, {
        "value": "remove_authorize",
        "label": "取消授权",
        "checked": false
      }],
      "typeNode": [{
        "value": "1",
        "label": "菜单",
        "checked": false
      }, {
        "value": "2",
        "label": "按钮",
        "checked": false
      }, {
        "value": "3",
        "label": "API接口",
        "checked": false
      }, {
        "value": "4",
        "label": "超链接",
        "checked": false
      }],
      "urlTarget": [{
        "value": "_self",
        "label": "默认",
        "checked": false
      }, {
        "value": "_blank",
        "label": "新窗口",
        "checked": false
      }, {
        "value": "_parent",
        "label": "父窗口",
        "checked": false
      }, {
        "value": "iframe",
        "label": "iframe",
        "checked": false
      }, {
        "value": "_top",
        "label": "top",
        "checked": false
      }],
      "dataResSys_changeType": [{
        "value": "0",
        "label": "新增数据库",
        "checked": false
      }, {
        "value": "1",
        "label": "删除数据库",
        "checked": false
      }, {
        "value": "2",
        "label": "新增数据表",
        "checked": false
      }, {
        "value": "3",
        "label": "删除数据表",
        "checked": false
      }, {
        "value": "4",
        "label": "新增表字段",
        "checked": false
      }, {
        "value": "5",
        "label": "删除表字段",
        "checked": false
      }, {
        "value": "6",
        "label": "表字段属性变更",
        "checked": false
      }],
      "dataResSys_tagType": [{
        "value": "dataType",
        "label": "数据类型",
        "tenantId": "",
        "checked": false
      }, {
        "value": "level",
        "label": "级别",
        "tenantId": "",
        "checked": false
      }, {
        "value": "product",
        "label": "产品",
        "tenantId": "",
        "checked": false
      }],
      "sendStatus": [{
        "value": "-1",
        "label": "发送失败",
        "checked": false
      }, {
        "value": "0",
        "label": "发送中",
        "checked": false
      }, {
        "value": "1",
        "label": "发送成功",
        "checked": false
      }],
      "FeeMng_NodeLinkType": [{
        "value": "1",
        "label": "收费站至收费单元",
        "checked": false
      }, {
        "value": "2",
        "label": "收费单元至收费站",
        "checked": false
      }, {
        "value": "3",
        "label": "收费单元至收费单元",
        "checked": false
      }],
      "sectionType": [{
        "value": "1",
        "label": "经营性",
        "tenantId": "",
        "extra": "",
        "checked": false
      }, {
        "value": "2",
        "label": "还贷性",
        "tenantId": "",
        "extra": "",
        "checked": false
      }],
      "config": [{
        "value": "mo-heartBeat-periodMin",
        "label": "10",
        "tenantId": "",
        "extra": "",
        "checked": false
      }]
    },
    "realName": "admin",
    "phone": "15889930671",
    "isRunAs": false,
    "toChangePwd": false,
    "position": "收费员1",
    "email": "songlubiao@qq.com",
    "username": "admin"
  },
  "success": true
}

const loginUserRouter ={
  "code" : 200,
  "message" : "操作成功",
  "data" : [ {
    "id" : "1334409659951349760",
    "appId" : 55,
    "path" : "/1334409659951349760",
    "meta" : {
      "title" : "系统管理",
      "icon" : "el-icon-setting",
      "breadcrumb" : false,
      "noCache" : false,
      "affix" : false,
      "isForm" : false
    },
    "component" : "@character(\"@\")ecip/ecip-web/layout",
    "hidden" : false,
    "alwaysShow" : true,
    "isBlank" : false,
    "children" : [ {
      "id" : "1334409659951349766",
      "appId" : 55,
      "path" : "/user",
      "name" : "UserRouteView",
      "meta" : {
        "title" : "用户管理",
        "icon" : "user",
        "breadcrumb" : false,
        "noCache" : false,
        "affix" : false,
        "isForm" : false
      },
      "component" : "@character(\"@\")ecip/ecip-web/views/vue/user/RouteView",
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1334409659951349760",
      "children" : [ {
        "id" : "1334409659951349779",
        "appId" : 55,
        "path" : "sysrDeptView",
        "name" : "1334409659951349779",
        "meta" : {
          "title" : "组织机构",
          "icon" : "fa fa-bank",
          "breadcrumb" : true,
          "noCache" : false,
          "affix" : false,
          "isForm" : false
        },
        "component" : "@character(\"@\")ecip/ecip-web/views/vue/dept/sysrDept/view.vue",
        "hidden" : false,
        "alwaysShow" : false,
        "isBlank" : false,
        "topParentId" : "1334409659951349760"
      }, {
        "id" : "1334409659951349778",
        "appId" : 55,
        "path" : "sysrUserView",
        "name" : "ModulesSystemSysrUserAloneView",
        "meta" : {
          "title" : "用户管理",
          "icon" : "user",
          "breadcrumb" : true,
          "noCache" : false,
          "affix" : false,
          "isForm" : false
        },
        "component" : "@character(\"@\")ecip/ecip-web/views/vue/user/sysrUser/view.vue",
        "hidden" : false,
        "alwaysShow" : false,
        "isBlank" : false,
        "topParentId" : "1334409659951349760"
      } ]
    }, {
      "id" : "1334409659951349762",
      "appId" : 55,
      "path" : "/sysrRoleView",
      "name" : "VueRoleSysrRoleView",
      "meta" : {
        "title" : "角色管理",
        "icon" : "fa fa-users",
        "breadcrumb" : true,
        "noCache" : false,
        "affix" : false,
        "isForm" : false
      },
      "component" : "@character(\"@\")ecip/ecip-web/views/vue/role/sysrRole/view.vue",
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1334409659951349760"
    }, {
      "id" : "1334409659951349763",
      "appId" : 55,
      "path" : "/sysrResourceView",
      "name" : "VueResourceSysrResourceView",
      "meta" : {
        "title" : "资源管理",
        "icon" : "list",
        "breadcrumb" : true,
        "noCache" : false,
        "affix" : false,
        "isForm" : false
      },
      "component" : "@character(\"@\")ecip/ecip-web/views/vue/resource/sysrResource/view.vue",
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1334409659951349760"
    }, {
      "id" : "1334409659951349761",
      "appId" : 55,
      "path" : "/ecipDictView",
      "name" : "VueDictEcipDictView",
      "meta" : {
        "title" : "数据字典",
        "icon" : "el-icon-notebook-1",
        "breadcrumb" : true,
        "noCache" : false,
        "affix" : false,
        "isForm" : false
      },
      "component" : "@character(\"@\")ecip/ecip-web/views/vue/dict/ecipDict/view.vue",
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1334409659951349760"
    }, {
      "id" : "1334409659951349764",
      "appId" : 55,
      "path" : "/sysrAreaView",
      "name" : "VueAreaSysrAreaView",
      "meta" : {
        "title" : "区域管理",
        "icon" : "fa fa-map",
        "breadcrumb" : false,
        "noCache" : false,
        "affix" : false,
        "isForm" : false
      },
      "component" : "@character(\"@\")ecip/ecip-web/views/vue/area/sysrArea/view.vue",
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1334409659951349760"
    } ]
  }, {
    "id" : "1318884529515003904",
    "appId" : 55,
    "path" : "/1318884529515003904",
    "meta" : {
      "title" : "测试",
      "icon" : "bug",
      "breadcrumb" : false,
      "noCache" : false,
      "affix" : false,
      "isForm" : false
    },
    "component" : "@character(\"@\")ecip/ecip-web/layout",
    "hidden" : false,
    "alwaysShow" : true,
    "isBlank" : false,
    "children" : [ {
      "id" : "1318884529515003905",
      "appId" : 55,
      "path" : "https://www.baidu.com",
      "name" : "baidu",
      "meta" : {
        "title" : "外链",
        "icon" : "fa fa-internet-explorer",
        "breadcrumb" : false,
        "noCache" : false,
        "affix" : false,
        "iframeUrl" : "https://www.baidu.com",
        "isIframe" : true,
        "isForm" : false
      },
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1318884529515003904"
    }, {
      "id" : "1354255581690462210",
      "appId" : 55,
      "path" : "/testProcOrderView",
      "name" : "ModulesTestOrderTestProcOrderView",
      "meta" : {
        "title" : "单表工作流",
        "icon" : "bug",
        "breadcrumb" : false,
        "noCache" : false,
        "affix" : false,
        "isForm" : false
      },
      "component" : "views/modules/test/order/testProcOrder/view.vue",
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1318884529515003904"
    }, {
      "id" : "1354255581690462225",
      "appId" : 55,
      "path" : "/testProcOrderAdd",
      "name" : "ModulesTestOrderTestProcOrderAdd",
      "meta" : {
        "title" : "新增单表工作流",
        "breadcrumb" : false,
        "noCache" : false,
        "affix" : false,
        "isForm" : false
      },
      "component" : "views/modules/test/order/testProcOrder/add.vue",
      "hidden" : true,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1318884529515003904"
    }, {
      "id" : "1354255581690462226",
      "appId" : 55,
      "path" : "/testProcOrderEdit/:id(.*)?",
      "name" : "ModulesTestOrderTestProcOrderEdit",
      "meta" : {
        "title" : "编辑单表工作流",
        "breadcrumb" : false,
        "noCache" : false,
        "affix" : false,
        "isForm" : false
      },
      "component" : "views/modules/test/order/testProcOrder/edit.vue",
      "hidden" : true,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1318884529515003904"
    }, {
      "id" : "1354255581690462212",
      "appId" : 55,
      "path" : "/ureport/preview?_u=file%3A用户性别分析.ureport.xml&_i=1&_r=1",
      "name" : "user-report",
      "meta" : {
        "title" : "用户分析报表",
        "icon" : "user",
        "breadcrumb" : false,
        "noCache" : false,
        "affix" : false,
        "iframeUrl" : "/ureport/preview?_u=file%3A用户性别分析.ureport.xml&_i=1&_r=1",
        "isIframe" : true,
        "isForm" : false
      },
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1318884529515003904"
    }, {
      "id" : "1354255581690462214",
      "appId" : 55,
      "path" : "/1354255581690462214",
      "name" : "1354255581690462214",
      "meta" : {
        "title" : "ceshi",
        "breadcrumb" : false,
        "noCache" : false,
        "affix" : false,
        "isForm" : false
      },
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : false,
      "topParentId" : "1318884529515003904"
    }, {
      "id" : "1354255581690462213",
      "appId" : 55,
      "path" : "http://www.baidu.com",
      "name" : "1354255581690462213",
      "meta" : {
        "title" : "外部窗口",
        "icon" : "fa fa-facebook-official",
        "breadcrumb" : false,
        "noCache" : false,
        "affix" : false,
        "isExternal" : true,
        "isForm" : false
      },
      "hidden" : false,
      "alwaysShow" : false,
      "isBlank" : true,
      "topParentId" : "1318884529515003904"
    } ]
  } ],
  "success" : true
}

const noticeData = {
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [{
      "id": "1353988759804182528",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#发起回单（工单号:A-QXJKZX-210126-785，位置点:七星监控中心）",
      "editStatus": "sent",
      "businessId": "A-QXJKZX-210126-785",
      "href": "{\"monitorId\":\"七星监控中心\",\"faultOrderId\":\"A-QXJKZX-210126-785\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:50:51",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }, {
      "id": "1353988759804182528",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#发起回单（工单号:A-QXJKZX-210126-785，位置点:七星监控中心）",
      "editStatus": "sent",
      "businessId": "A-QXJKZX-210126-785",
      "href": "{\"monitorId\":\"七星监控中心\",\"faultOrderId\":\"A-QXJKZX-210126-785\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:50:51",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }, {
      "id": "1353988759804182528",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#发起回单（工单号:A-QXJKZX-210126-785，位置点:七星监控中心）",
      "editStatus": "sent",
      "businessId": "A-QXJKZX-210126-785",
      "href": "{\"monitorId\":\"七星监控中心\",\"faultOrderId\":\"A-QXJKZX-210126-785\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:50:51",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }, {
      "id": "1353988693857140736",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#确认工单完成，已接单（工单号:A-QXJKZX-210126-785，位置点:七星监控中心）",
      "editStatus": "sent",
      "businessId": "A-QXJKZX-210126-785",
      "href": "{\"monitorId\":\"七星监控中心\",\"faultOrderId\":\"A-QXJKZX-210126-785\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:50:35",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }, {
      "id": "1353988693857140736",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#确认工单完成，已接单（工单号:A-QXJKZX-210126-785，位置点:七星监控中心）",
      "editStatus": "sent",
      "businessId": "A-QXJKZX-210126-785",
      "href": "{\"monitorId\":\"七星监控中心\",\"faultOrderId\":\"A-QXJKZX-210126-785\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:50:35",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }, {
      "id": "1353988693857140736",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#确认工单完成，已接单（工单号:A-QXJKZX-210126-785，位置点:七星监控中心）",
      "editStatus": "sent",
      "businessId": "A-QXJKZX-210126-785",
      "href": "{\"monitorId\":\"七星监控中心\",\"faultOrderId\":\"A-QXJKZX-210126-785\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:50:35",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }, {
      "id": "1353981615788785664",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#确认工单完成，已接单（工单号:A-eTest2-210126-882，位置点:测试库房2）",
      "editStatus": "sent",
      "businessId": "A-eTest2-210126-882",
      "href": "{\"monitorId\":\"测试库房2\",\"faultOrderId\":\"A-eTest2-210126-882\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:22:28",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }, {
      "id": "1353981615788785664",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#确认工单完成，已接单（工单号:A-eTest2-210126-882，位置点:测试库房2）",
      "editStatus": "sent",
      "businessId": "A-eTest2-210126-882",
      "href": "{\"monitorId\":\"测试库房2\",\"faultOrderId\":\"A-eTest2-210126-882\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:22:28",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }, {
      "id": "1353981615788785664",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#确认工单完成，已接单（工单号:A-eTest2-210126-882，位置点:测试库房2）",
      "editStatus": "sent",
      "businessId": "A-eTest2-210126-882",
      "href": "{\"monitorId\":\"测试库房2\",\"faultOrderId\":\"A-eTest2-210126-882\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:22:28",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }, {
      "id": "1353980345648676864",
      "tenantId": "1249628559274672128",
      "userId": "-1",
      "userName": "admin",
      "title": "工单消息",
      "content": "#桂阳路#确认工单完成，已接单（工单号:A-QXJKZX-210126-085，位置点:七星监控中心）",
      "editStatus": "sent",
      "businessId": "A-QXJKZX-210126-085",
      "href": "{\"monitorId\":\"七星监控中心\",\"faultOrderId\":\"A-QXJKZX-210126-085\",\"autoCreateFlag\":0,\"assign\":\"桂阳路\"}",
      "type": "flow",
      "sendWay": 1,
      "sendWayText": "站内信",
      "sendTime": "2021-01-26 16:17:25",
      "sysrNoticeDetailList": [],
      "sysrNoticeDetailDeleteIds": [],
      "sysrNoticeDetail": {}
    }],
    "total": 11789,
    "size": 10,
    "current": 1
  },
  "success": true
}

const loginStat = {
  "code": 200,
  "message": "操作成功",
  "data": {
    "date": ["1227", "1228", "1229", "1230", "1231", "0101", "0102", "0103", "0104", "0105", "0106", "0107", "0108", "0109", "0110", "0111", "0112", "0113", "0114", "0115", "0116", "0117", "0118", "0119", "0120", "0121", "0122", "0123", "0124", "0125", "0126"],
    "pc": [3, 9, 8, 6, 6, 1, 1, 1, 5, 5, 6, 4, 5, 2, 1, 8, 8, 7, 6, 6, 2, 1, 8, 12, 11, 12, 7, 4, 3, 11, 8],
    "app": [0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1, 1, 0, 0, 0, 2, 1],
    "total": [3, 9, 8, 7, 6, 1, 1, 1, 5, 5, 6, 5, 5, 2, 1, 8, 8, 7, 6, 6, 2, 1, 10, 14, 12, 13, 7, 4, 3, 13, 9],
    "yesterday": "2021-01-25",
    "yesterdayCount": 13,
    "allCount": 188,
    "averageCount": 6
  },
  "success": true
}

const registerUserCount = {
  "code": 200,
  "message": "操作成功",
  "data": {
    "valueList": [471, 476, 477, 477, 477, 477, 477, 477, 477, 477, 477, 477, 477, 477, 477, 477, 479, 479, 480, 480, 480, 480, 481, 481, 481, 481, 482, 482, 482, 482, 482],
    "dayList": ["1227", "1228", "1229", "1230", "1231", "0101", "0102", "0103", "0104", "0105", "0106", "0107", "0108", "0109", "0110", "0111", "0112", "0113", "0114", "0115", "0116", "0117", "0118", "0119", "0120", "0121", "0122", "0123", "0124", "0125", "0126"]
  },
  "success": true
}

const hotData = {
  "code": 200,
  "message": "操作成功",
  "data": {
    "resourceName": ["运营", "运维", "收费", "监测", "智能", "定制", "加盖", "办公"],
    "count": [2303, 1627, 1440, 40, 33, 21, 21, 6]
  },
  "success": true
}

const hotDataTrend = {
  "code": 200,
  "message": "操作成功",
  "data": {
    "dates": ["01/19", "01/20", "01/21", "01/22", "01/23", "01/24", "01/25", "01/26"],
    "records": [{
      "resourceName": "办公",
      "counts": [0, 0, 0, 0, 0, 0, 6, 0]
    }, {
      "resourceName": "监测",
      "counts": [11, 10, 13, 1, 0, 0, 1, 4]
    }, {
      "resourceName": "运营",
      "counts": [421, 104, 216, 242, 274, 132, 407, 507]
    }, {
      "resourceName": "运维",
      "counts": [297, 146, 322, 232, 0, 3, 190, 437]
    }, {
      "resourceName": "定制",
      "counts": [0, 0, 0, 21, 0, 0, 0, 0]
    }, {
      "resourceName": "加盖",
      "counts": [3, 18, 0, 0, 0, 0, 0, 0]
    }, {
      "resourceName": "收费",
      "counts": [344, 185, 209, 352, 10, 0, 197, 143]
    }, {
      "resourceName": "智能",
      "counts": [0, 0, 31, 0, 0, 0, 2, 0]
    }]
  },
  "success": true
}

module.exports = [
  // 获取验证码
  {
    url: '/api/auth/captcha',
    type: 'get',
    response: _ => {
      return loginCaptcha
    }
  },
  // 登陆成功
  {
    url: '/api/v1/login',
    type: 'post',
    response: _ => {
      return loginSuccess
    }
  },
  // 登陆成功获取用户信息
  {
    url: '/api/v1/user/info',
    type: 'get',
    response: _ => {
      return loginUserInfo
    }
  },
  // 登陆成功获取路由
  {
    url: '/api/v1/system/router',
    type: 'get',
    response: _ => {
      return loginUserRouter/*{
        "code": 200,
        "message": "操作成功",
        "data": [],m
        "success": true
      }*/
    }
  }, {
    url: '/api/v1/notice/sysrNotice/receivedData',
    type: 'post',
    response: _ => {
      return noticeData
    }
  }, {
    url: '/api/v1/system/loginStat',
    type: 'get',
    response: _ => {
      return loginStat
    }
  }, {
    url: '/api/v1/system/countRegisterUser',
    type: 'get',
    response: _ => {
      return registerUserCount
    }
  }, {
    url: '/api/v1/stat/visit/ecipVisitStat/hotData',
    type: 'post',
    response: _ => {
      return hotData
    }
  }, {
    url: '/api/v1/stat/visit/ecipVisitStat/hotDataTrend',
    type: 'post',
    response: _ => {
      return hotDataTrend
    }
  }]
