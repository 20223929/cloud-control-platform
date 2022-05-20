
const tokens = {
  admin: {
    token: 'admin-token'
  },
  editor: {
    token: 'editor-token'
  }
}

const dictList = {
  'readStatus': [
    {
      'value': 'unread',
      'label': '未读'
    },
    {
      'value': 'read',
      'label': '已读'
    }
  ],
  'deviceLocationType': [
    {
      'value': '1',
      'label': '区域',
      'tenantId': '1249628559274672128'
    },
    {
      'value': '2',
      'label': '监控点',
      'tenantId': '1249628559274672128'
    },
    {
      'value': '3',
      'label': '库房',
      'tenantId': '1249628559274672128'
    }
  ],
  'validateType': [
    {
      'value': 'string',
      'label': '字符串'
    },
    {
      'value': 'email',
      'label': '电子邮件'
    },
    {
      'value': 'website',
      'label': '网址'
    },
    {
      'value': 'date',
      'label': '日期'
    },
    {
      'value': 'dateISO',
      'label': '日期（ISO）'
    },
    {
      'value': 'cardNumber',
      'label': '信用卡号'
    },
    {
      'value': 'phoneNumber',
      'label': '电话号码'
    },
    {
      'value': 'mobilePhoneNumber',
      'label': '手机号码'
    },
    {
      'value': 'mobilePhoneOrPhone',
      'label': '手机/电话'
    },
    {
      'value': 'qqNumber',
      'label': 'QQ号码'
    },
    {
      'value': 'idNumber',
      'label': '身份证号码'
    },
    {
      'value': 'number',
      'label': '数字'
    },
    {
      'value': 'integer',
      'label': '整数'
    },
    {
      'value': 'positiveInteger',
      'label': '整数（大于0）'
    },
    {
      'value': 'nonNegativeInteger',
      'label': '整数（大于等于0）'
    },
    {
      'value': 'negativeInteger',
      'label': '整数（小于0）'
    },
    {
      'value': 'nonPositiveInteger',
      'label': '整数（小于等于0）'
    },
    {
      'value': 'positiveFloatingPoint',
      'label': '浮点数（大于0）'
    },
    {
      'value': 'nonNegativeFloatingPoint',
      'label': '浮点数（大于等于0）'
    },
    {
      'value': 'negativeFloatingPoint',
      'label': '浮点数（小于0）'
    },
    {
      'value': 'nonPositiveFloatingPoint',
      'label': '浮点数（小于等于0）'
    },
    {
      'value': 'postalCode',
      'label': '邮政编码'
    },
    {
      'value': 'password',
      'label': '密码'
    },
    {
      'value': 'chineseEnglishNumbersUnderlines',
      'label': '中文/英文/数字/下划线'
    },
    {
      'value': 'english',
      'label': '英语'
    },
    {
      'value': 'chinese',
      'label': '汉字'
    },
    {
      'value': 'englishOrChineseCharacters',
      'label': '英汉字符'
    },
    {
      'value': 'legalCharacters',
      'label': '合法字符'
    }
  ],
  'editStatus': [
    {
      'value': 'draft',
      'label': '草稿'
    },
    {
      'value': 'sent',
      'label': '发送'
    }
  ],
  'businesstype': [
    {
      'value': '1',
      'label': '验证码'
    },
    {
      'value': '2',
      'label': '通知'
    },
    {
      'value': '99',
      'label': '其他'
    }
  ],
  'dataResSys_dbType': [
    {
      'value': 'mysql',
      'label': 'MySQL'
    },
    {
      'value': 'hbase',
      'label': 'HBase'
    }
  ],
  'orgType': [
    {
      'value': '1',
      'label': '路段'
    },
    {
      'value': '2',
      'label': '门架'
    },
    {
      'value': '3',
      'label': '收费站'
    }
  ],
  'FeeMng_VersionType': [
    {
      'value': '1',
      'label': '路径参数'
    },
    {
      'value': '2',
      'label': '费率参数'
    }
  ],
  'payType': [
    {
      'value': '1',
      'label': '现金'
    },
    {
      'value': '2',
      'label': '其他第三方账户支付'
    },
    {
      'value': '3',
      'label': '银联卡支付'
    },
    {
      'value': '4',
      'label': 'ETC 用户卡'
    },
    {
      'value': '6',
      'label': '支付宝'
    },
    {
      'value': '7',
      'label': '微信'
    }
  ],
  'isVehBlack': [
    {
      'value': '0',
      'label': '不是'
    },
    {
      'value': '1',
      'label': '是'
    }
  ],
  'FeeMng_ParamStatus': [
    {
      'value': '0',
      'label': '启用'
    },
    {
      'value': '1',
      'label': '停用'
    }
  ],
  'showType': [
    {
      'value': 'input',
      'label': '单行文本'
    },
    {
      'value': 'textarea',
      'label': '多行文本'
    },
    {
      'value': 'umeditor',
      'label': '富文本编辑器'
    },
    {
      'value': 'select',
      'label': '下拉选项'
    },
    {
      'value': 'radiobox',
      'label': '单选按钮'
    },
    {
      'value': 'checkbox',
      'label': '复选框'
    },
    {
      'value': 'dateselect',
      'label': '日期选择'
    },
    {
      'value': 'userselect',
      'label': '用户选择'
    },
    {
      'value': 'deptselect',
      'label': '部门选择'
    },
    {
      'value': 'cityselect',
      'label': '省市区三级联动'
    },
    {
      'value': 'treeselect',
      'label': '树选择控件'
    },
    {
      'value': 'fileselect',
      'label': '文件上传'
    },
    {
      'value': 'gridselect',
      'label': 'grid选择框'
    },
    {
      'value': 'areaselect',
      'label': '区域选择'
    }
  ],
  'FeeMng_NodeType': [
    {
      'value': '1',
      'label': '普通收费单元'
    },
    {
      'value': '2',
      'label': '省界收费单元'
    },
    {
      'value': '3',
      'label': '收费站'
    }
  ],
  'vehicleType': [
    {
      'value': '1',
      'label': '一型客车'
    },
    {
      'value': '2',
      'label': '二型客车'
    },
    {
      'value': '3',
      'label': '三型客车'
    },
    {
      'value': '4',
      'label': '四型客车'
    },
    {
      'value': '11',
      'label': '一型货车'
    },
    {
      'value': '12',
      'label': '二型货车'
    },
    {
      'value': '13',
      'label': '三型货车'
    },
    {
      'value': '14',
      'label': '四型货车'
    },
    {
      'value': '15',
      'label': '五型货车'
    },
    {
      'value': '16',
      'label': '六型货车'
    },
    {
      'value': '21',
      'label': '一型专项作业车'
    },
    {
      'value': '22',
      'label': '二型专项作业车'
    },
    {
      'value': '23',
      'label': '三型专项作业车'
    },
    {
      'value': '24',
      'label': '四型专项作业车'
    },
    {
      'value': '25',
      'label': '五型专项作业车'
    },
    {
      'value': '26',
      'label': '六型专项作业车'
    }
  ],
  'deviceType': [
    {
      'value': 'web',
      'label': '浏览器'
    },
    {
      'value': 'app',
      'label': 'App'
    }
  ],
  'typeApp': [
    {
      'value': '1',
      'label': 'WEB应用'
    },
    {
      'value': '2',
      'label': 'Restful API接口'
    },
    {
      'value': '3',
      'label': '报表系统'
    },
    {
      'value': '4',
      'label': '外部链接应用'
    },
    {
      'value': '0',
      'label': '其他通用'
    }
  ],
  'PrimaryKeyStrategy': [
    {
      'value': '1',
      'label': '自增长'
    },
    {
      'value': '2',
      'label': 'UUID'
    }
  ],
  'FeeMng_CalcCode': [
    {
      'value': '200',
      'label': '成功'
    },
    {
      'value': '502',
      'label': '错误网关'
    },
    {
      'value': '701',
      'label': '文件操作异常'
    },
    {
      'value': '704',
      'label': '部中心：业务校验失败'
    },
    {
      'value': '716',
      'label': '部中心：响应失败'
    },
    {
      'value': '947',
      'label': '请求数据错误'
    },
    {
      'value': '951',
      'label': '文件md5校验失败'
    },
    {
      'value': '952',
      'label': '格式校验失败'
    },
    {
      'value': '994',
      'label': '上游系统数据异常'
    },
    {
      'value': '996',
      'label': '路径还原失败'
    },
    {
      'value': '997',
      'label': '计费模块计费失败'
    },
    {
      'value': '998',
      'label': '自行计费失败'
    },
    {
      'value': '999',
      'label': '系统异常'
    },
    {
      'value': '2001',
      'label': '服务禁用'
    },
    {
      'value': '2002',
      'label': '封装交易信息异常'
    },
    {
      'value': '2003',
      'label': '收费单元转化门架异常'
    },
    {
      'value': '2005',
      'label': '查询最短路径异常'
    },
    {
      'value': '2006',
      'label': '出口车道跨省特情计费失败'
    },
    {
      'value': '2007',
      'label': '在线计费计费不准确'
    },
    {
      'value': '2008',
      'label': '在线计费计费失败'
    },
    {
      'value': '2009',
      'label': '查询最小费额异常'
    }
  ],
  'module': [
    {
      'value': 'sysr_user',
      'label': '用户'
    },
    {
      'value': 'sysr_dept',
      'label': '组织'
    },
    {
      'value': 'sysr_resource',
      'label': '资源'
    },
    {
      'value': 'sysr_role',
      'label': '角色'
    },
    {
      'value': 'ecip_dict',
      'label': '数据字典'
    },
    {
      'value': 'sysr_user_role',
      'label': '用户角色'
    },
    {
      'value': 'sysr_role_resource',
      'label': '角色资源'
    },
    {
      'value': 'sys_role_data',
      'label': '角色数据'
    },
    {
      'value': 'sysr_register_app',
      'label': '应用系统'
    },
    {
      'value': 'data_rule',
      'label': '数据规则'
    },
    {
      'value': 'sysr_datasource',
      'label': '数据源'
    },
    {
      'value': 'custom_obj',
      'label': '自定义java对象'
    },
    {
      'value': 'test_note',
      'label': '富文本测试'
    },
    {
      'value': 'gencode_scheme',
      'label': '生成方案'
    },
    {
      'value': 'gencode_template_group',
      'label': '代码生成器模板'
    },
    {
      'value': 'test_pic',
      'label': '图片管理'
    },
    {
      'value': 'gencode_table',
      'label': '表单配置'
    },
    {
      'value': 'sysr_user_app',
      'label': '用户应用'
    },
    {
      'value': '\tuser_dept',
      'label': '用户组织'
    },
    {
      'value': 'sysr_app_version',
      'label': '应用版本'
    },
    {
      'value': 'saas_org',
      'label': '租户机构'
    }
  ],
  'syncIdType': [
    {
      'value': 'src',
      'label': '跟随源记录'
    },
    {
      'value': 'auto',
      'label': '自动生成新主键'
    }
  ],
  'JavaType': [
    {
      'value': 'String',
      'label': 'String'
    },
    {
      'value': 'Long',
      'label': 'Long'
    },
    {
      'value': 'Integer',
      'label': 'Integer'
    },
    {
      'value': 'Double',
      'label': 'Double'
    },
    {
      'value': 'Date',
      'label': 'Date'
    },
    {
      'value': 'com.hgsoft.ecip.web.rbac.entity.SysrDept',
      'label': 'Dept'
    },
    {
      'value': 'com.hgsoft.ecip.framework.shiro.ShiroUser',
      'label': 'User'
    },
    {
      'value': 'com.hgsoft.ecip.web.rbac.entity.SysrFile',
      'label': 'File'
    },
    {
      'value': 'This',
      'label': 'ThisObj'
    },
    {
      'value': 'com.hgsoft.modules.testdatamain.entity.TestDataMainForm',
      'label': 'TestDataMainForm'
    },
    {
      'value': 'com.hgsoft.modules.treedemo.entity.TreeDemo',
      'label': 'TreeDemo'
    },
    {
      'value': 'com.hgsoft.ecip.web.rbac.entity.SysrArea',
      'label': 'Area'
    },
    {
      'value': 'com.hgsoft.ecip.api.vo.SysrDeptVo',
      'label': 'DeptVo'
    },
    {
      'value': 'com.hgsoft.ecip.api.vo.FileVo',
      'label': 'FileVo'
    },
    {
      'value': 'com.hgsoft.ecip.api.vo.AreaVo',
      'label': 'AreaVo'
    }
  ],
  'FeeMng_DownStatus': [
    {
      'value': '0',
      'label': '未下发'
    },
    {
      'value': '1',
      'label': '已下发'
    }
  ],
  'vehiclePlateColor': [
    {
      'value': '0',
      'label': '蓝色'
    },
    {
      'value': '1',
      'label': '黄色'
    },
    {
      'value': '2',
      'label': '黑色'
    },
    {
      'value': '3',
      'label': '白色'
    },
    {
      'value': '4',
      'label': '渐变绿色'
    },
    {
      'value': '5',
      'label': ' 黄绿双拼色'
    },
    {
      'value': '6',
      'label': '蓝白渐变色'
    },
    {
      'value': '7',
      'label': '临时牌照'
    },
    {
      'value': '11',
      'label': '绿色'
    },
    {
      'value': '12',
      'label': '红色'
    }
  ],
  'FeeMng_CalcResult': [
    {
      'value': '1',
      'label': '成功'
    },
    {
      'value': '0',
      'label': '失败'
    }
  ],
  'dataRuleCondition': [
    {
      'value': '=',
      'label': '等于'
    },
    {
      'value': '!=',
      'label': '不等于'
    },
    {
      'value': '<',
      'label': '小于'
    },
    {
      'value': '<=',
      'label': '小于等于'
    },
    {
      'value': '>=',
      'label': '大于等于'
    },
    {
      'value': 'like',
      'label': '模糊匹配'
    },
    {
      'value': 'RightLike',
      'label': 'RightLike'
    },
    {
      'value': 'LeftLike',
      'label': 'LeftLike'
    }
  ],
  'syncType': [
    {
      'value': 'user',
      'label': '同步用户'
    },
    {
      'value': 'dept',
      'label': '同步组织'
    },
    {
      'value': 'other',
      'label': '自定义'
    }
  ],
  'FeeMng_UpStatus': [
    {
      'value': '0',
      'label': '未上传'
    },
    {
      'value': '1',
      'label': '已上传'
    }
  ],
  'FeeMng_VersionStatus': [
    {
      'value': '1',
      'label': '已发布'
    },
    {
      'value': '0',
      'label': '待发布'
    },
    {
      'value': '2',
      'label': '发布中'
    }
  ],
  'versionStatus': [
    {
      'value': '1',
      'label': '待审核'
    },
    {
      'value': '2',
      'label': '待发布'
    },
    {
      'value': '3',
      'label': '已发布'
    }
  ],
  'tableType': [
    {
      'value': 'singleTable',
      'label': '单表'
    },
    {
      'value': 'masterTable',
      'label': '主表'
    },
    {
      'value': 'attachTable',
      'label': '附表'
    },
    {
      'value': 'treeStructureTable',
      'label': '树结构表'
    },
    {
      'value': 'leftTreeMainTable',
      'label': '左树(主表)'
    },
    {
      'value': 'rightTableSchedule',
      'label': '右表（附表）'
    },
    {
      'value': 'workflowForms',
      'label': '工作流表单'
    }
  ],
  'jdbcType': [
    {
      'value': 'varchar(64)',
      'label': 'varchar(64)'
    },
    {
      'value': 'nvarchar(64)',
      'label': 'nvarchar(64)'
    },
    {
      'value': 'integer',
      'label': 'integer'
    },
    {
      'value': 'double',
      'label': 'double'
    },
    {
      'value': 'datetime',
      'label': 'datetime'
    },
    {
      'value': 'longblob',
      'label': 'longblob'
    },
    {
      'value': 'longtext',
      'label': 'longtext'
    }
  ],
  'areaType': [
    {
      'value': 'country',
      'label': '国家'
    },
    {
      'value': 'city',
      'label': '市'
    },
    {
      'value': 'district',
      'label': '区(县)'
    },
    {
      'value': 'town',
      'label': '镇(乡)'
    },
    {
      'value': 'village',
      'label': '村'
    }
  ],
  'vehicleClass': [
    {
      'value': '0',
      'label': '普通'
    },
    {
      'value': '8',
      'label': '军警'
    },
    {
      'value': '10',
      'label': '紧急'
    },
    {
      'value': '14',
      'label': '车队'
    },
    {
      'value': '21',
      'label': '绿通车'
    },
    {
      'value': '22',
      'label': '联合收割机'
    },
    {
      'value': '23',
      'label': '抢险救灾'
    },
    {
      'value': '24',
      'label': '集装箱'
    },
    {
      'value': '25',
      'label': '大件运输'
    },
    {
      'value': '26',
      'label': '应急车'
    }
  ],
  'authType': [
    {
      'value': '1',
      'label': 'Cookie-Session'
    },
    {
      'value': '2',
      'label': 'JWT Token'
    },
    {
      'value': '3',
      'label': 'OAuth认证'
    }
  ],
  'enableState': [
    {
      'value': '1',
      'label': '启用'
    },
    {
      'value': '0',
      'label': '未启用'
    }
  ],
  'genIdType': [
    {
      'value': '1',
      'label': 'UUID'
    },
    {
      'value': '2',
      'label': 'IdWorker'
    }
  ],
  'FeeMng_CalcType': [
    {
      'value': '1',
      'label': '通行费计费'
    },
    {
      'value': '2',
      'label': '无入口特情计费'
    },
    {
      'value': '3',
      'label': '出入口信息不符特情计费'
    },
    {
      'value': '4',
      'label': 'OBU及ETC特情计费'
    },
    {
      'value': '5',
      'label': '本省省界触发计费'
    },
    {
      'value': '6',
      'label': '非本省出口计费'
    },
    {
      'value': '7',
      'label': 'ETC计费'
    },
    {
      'value': '8',
      'label': '门架计费拆分'
    },
    {
      'value': '9',
      'label': '全网最短路径计费'
    },
    {
      'value': '10',
      'label': '出口车道跨省特情计费'
    },
    {
      'value': '11',
      'label': '跨省入口站查询'
    },
    {
      'value': '12',
      'label': '全网最小费额计费'
    }
  ],
  'sex': [
    {
      'value': '0',
      'label': '男'
    },
    {
      'value': '1',
      'label': '女'
    }
  ],
  'FeeMng_ModelDirection': [
    {
      'value': '1',
      'label': '有向'
    },
    {
      'value': '2',
      'label': '无向'
    }
  ],
  'dbType': [
    {
      'value': 'mysql',
      'label': 'MySql'
    },
    {
      'value': 'oracle',
      'label': 'Oracle'
    },
    {
      'value': 'sqlserver',
      'label': 'SqlServer'
    },
    {
      'value': 'db2',
      'label': 'DB2'
    }
  ],
  'noticeType': [
    {
      'value': 'system',
      'label': '系统消息'
    },
    {
      'value': 'flow',
      'label': '流程消息'
    },
    {
      'value': 'alarm',
      'label': '报警消息'
    }
  ],
  'typeSrc': [
    {
      'value': '1',
      'label': 'WEB'
    },
    {
      'value': '2',
      'label': 'API'
    },
    {
      'value': '3',
      'label': '报表资源'
    }
  ],
  'queryType': [
    {
      'value': '=',
      'label': '='
    },
    {
      'value': '!=',
      'label': '!='
    },
    {
      'value': '>',
      'label': '>'
    },
    {
      'value': '>=',
      'label': '>='
    },
    {
      'value': '<',
      'label': '<'
    },
    {
      'value': '<=',
      'label': '<='
    },
    {
      'value': 'between',
      'label': 'Between'
    },
    {
      'value': 'Like',
      'label': 'Like'
    },
    {
      'value': 'LeftLike',
      'label': 'Left Like'
    },
    {
      'value': 'RightLike',
      'label': 'Right Like'
    }
  ],
  'tplCategory': [
    {
      'value': '1',
      'label': '单表'
    },
    {
      'value': '2',
      'label': '主附表'
    },
    {
      'value': '3',
      'label': '自定义'
    }
  ],
  'optType': [
    {
      'value': 'add',
      'label': '添加'
    },
    {
      'value': 'remove',
      'label': '删除'
    },
    {
      'value': 'edit',
      'label': '编辑'
    },
    {
      'value': 'edit_password',
      'label': '修改密码'
    },
    {
      'value': 'reset_password',
      'label': '重置密码'
    },
    {
      'value': 'add_default_permission',
      'label': '添加默认权限'
    },
    {
      'value': 'login',
      'label': '登录'
    },
    {
      'value': 'logout',
      'label': '注销'
    },
    {
      'value': 'authorize',
      'label': '授权'
    },
    {
      'value': 'remove_authorize',
      'label': '取消授权'
    }
  ],
  'typeNode': [
    {
      'value': '1',
      'label': '菜单'
    },
    {
      'value': '2',
      'label': '按钮'
    },
    {
      'value': '3',
      'label': 'API接口'
    },
    {
      'value': '4',
      'label': '超链接'
    }
  ],
  'urlTarget': [
    {
      'value': '_self',
      'label': '默认'
    },
    {
      'value': '_blank',
      'label': '新窗口'
    },
    {
      'value': '_parent',
      'label': '父窗口'
    },
    {
      'value': '_top',
      'label': 'top'
    }
  ],
  'dataResSys_changeType': [
    {
      'value': '0',
      'label': '新增数据库'
    },
    {
      'value': '1',
      'label': '删除数据库'
    },
    {
      'value': '2',
      'label': '新增数据表'
    },
    {
      'value': '3',
      'label': '删除数据表'
    },
    {
      'value': '4',
      'label': '新增表字段'
    },
    {
      'value': '5',
      'label': '删除表字段'
    },
    {
      'value': '6',
      'label': '表字段属性变更'
    }
  ],
  'dataResSys_tagType': [
    {
      'value': 'dataType',
      'label': '数据类型',
      'tenantId': ''
    },
    {
      'value': 'level',
      'label': '级别',
      'tenantId': ''
    },
    {
      'value': 'product',
      'label': '产品',
      'tenantId': ''
    }
  ],
  'sendStatus': [
    {
      'value': '-1',
      'label': '发送失败'
    },
    {
      'value': '0',
      'label': '发送中'
    },
    {
      'value': '1',
      'label': '发送成功'
    }
  ],
  'FeeMng_NodeLinkType': [
    {
      'value': '1',
      'label': '收费站至收费单元'
    },
    {
      'value': '2',
      'label': '收费单元至收费站'
    },
    {
      'value': '3',
      'label': '收费单元至收费单元'
    }
  ]
}

const users = {
  'admin-token': {
    roles: ['admin'],
    introduction: 'I am a super administrator',
    avatar: '',
    realName: '管理员',
    userName: 'admin',
    dictList
  },
  'editor-token': {
    roles: ['editor'],
    introduction: 'I am an editor',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    username: 'editor',
    realName: 'Normal Editor',
    dictList
  }
}

module.exports = [
  dictList,
  // user login
  {
    url: '/vue-element-admin/user/login',
    type: 'post',
    response: config => {
      const { username } = config.body
      const token = tokens[username]

      // mock error
      if (!token) {
        return {
          code: 60204,
          message: 'Account and password are incorrect.'
        }
      }

      return {
        code: 200,
        data: token
      }
    }
  },

  // get user info
  {
    url: '/vue-element-admin/user/info\.*',
    type: 'get',
    response: config => {
      const { token } = config.query
      const info = users[token]

      // mock error
      if (!info) {
        return {
          code: 401,
          message: 'Login failed, unable to get user details.'
        }
      }

      return {
        code: 200,
        data: info
      }
    }
  },

  // user logout
  {
    url: '/vue-element-admin/user/logout',
    type: 'post',
    response: _ => {
      return {
        code: 200,
        data: 'success'
      }
    }
  }
]
