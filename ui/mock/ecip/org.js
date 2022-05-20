const orgTrees = {
  "code" : 200,
  "message" : "操作成功",
  "data" : [ {
    "key" : "1251417049217892352",
    "title" : "工程技术部",
    "tenantId" : "1249628559274672128",
    "orgId" : "1251417049217892352",
    "children" : [ {
      "key" : "1254743048399618048",
      "title" : "桂林组",
      "tenantId" : "1249628559274672128",
      "orgId" : "1254743048399618048",
      "isLeaf" : true
    }, {
      "key" : "1254743102216732672",
      "title" : "阳朔组",
      "tenantId" : "1249628559274672128",
      "orgId" : "1254743102216732672",
      "isLeaf" : true
    } ],
    "isLeaf" : false
  }, {
    "key" : "1253160987977056256",
    "title" : "阳朔片区",
    "tenantId" : "1249628559274672128",
    "orgId" : "1253160987977056256",
    "children" : [ {
      "key" : "1253161121011990528",
      "title" : "桂阳路",
      "tenantId" : "1249628559274672128",
      "orgId" : "1253161121011990528",
      "isLeaf" : true
    }, {
      "key" : "1253161235399049216",
      "title" : "阳平路",
      "tenantId" : "1249628559274672128",
      "orgId" : "1253161235399049216",
      "isLeaf" : true
    }, {
      "key" : "1253161343406571520",
      "title" : "桂兴路",
      "tenantId" : "1249628559274672128",
      "orgId" : "1253161343406571520",
      "isLeaf" : true
    }, {
      "key" : "1253161401384435712",
      "title" : "灵三路",
      "tenantId" : "1249628559274672128",
      "orgId" : "1253161401384435712",
      "isLeaf" : true
    }, {
      "key" : "1259042618608713728",
      "title" : "稽查组",
      "tenantId" : "1249628559274672128",
      "orgId" : "1259042618608713728",
      "isLeaf" : true
    }, {
      "key" : "1260409339248640000",
      "title" : "监测运维组",
      "tenantId" : "1249628559274672128",
      "orgId" : "1260409339248640000",
      "children" : [ {
        "key" : "1262647853969309696",
        "title" : "只有收费站组",
        "tenantId" : "1249628559274672128",
        "orgId" : "1262647853969309696",
        "isLeaf" : true
      } ],
      "isLeaf" : false
    }, {
      "key" : "1276392757081931776",
      "title" : "六塘收费站",
      "tenantId" : "1249628559274672128",
      "orgId" : "1276392757081931776",
      "isLeaf" : true
    } ],
    "isLeaf" : false
  }, {
    "key" : "1257593526225469440",
    "title" : "营运管理部",
    "tenantId" : "1249628559274672128",
    "orgId" : "1257593526225469440",
    "isLeaf" : true
  }, {
    "key" : "1258659637545140224",
    "title" : "招商华软",
    "tenantId" : "1249628559274672128",
    "orgId" : "1258659637545140224",
    "isLeaf" : true
  }, {
    "key" : "1260410622437228544",
    "title" : "监测运维开发组",
    "tenantId" : "1249628559274672128",
    "orgId" : "1260410622437228544",
    "children" : [ {
      "key" : "1260526787721428992",
      "title" : "2个路段组",
      "tenantId" : "1249628559274672128",
      "orgId" : "1260526787721428992",
      "isLeaf" : true
    } ],
    "isLeaf" : false
  }, {
    "key" : "1264758481592778752",
    "title" : "公司领导",
    "tenantId" : "1249628559274672128",
    "orgId" : "1264758481592778752",
    "isLeaf" : true
  }, {
    "key" : "1265679329954627584",
    "title" : "收费站",
    "tenantId" : "1249628559274672128",
    "orgId" : "1265679329954627584",
    "children" : [ {
      "key" : "1280700693442723840",
      "title" : "故障上报组",
      "tenantId" : "1249628559274672128",
      "orgId" : "1280700693442723840",
      "isLeaf" : true
    } ],
    "isLeaf" : false
  }, {
    "key" : "1351074739606519808",
    "title" : "九江二桥",
    "tenantId" : "1249628559274672128",
    "orgId" : "1351074739606519808",
    "isLeaf" : true
  } ],
  "success" : true
}


const orgData = {
  "code" : 200,
  "message" : "操作成功",
  "data" : [ {
    "id" : "1264758481592778752",
    "delFlag" : "0",
    "parentId" : "0",
    "parentName" : "",
    "parentIds" : ",0",
    "nodeLevel" : 0,
    "name" : "公司领导",
    "sort" : 1,
    "checked" : false,
    "tenantId" : "1249628559274672128",
    "code" : "001",
    "status" : "1",
    "orgLegalNameList" : [ ],
    "statusText" : "启用",
    "orgTypeText" : "",
    "orgLegalName" : ""
  }, {
    "id" : "1251417049217892352",
    "delFlag" : "0",
    "parentId" : "0",
    "parentName" : "",
    "parentIds" : ",0",
    "nodeLevel" : 0,
    "name" : "工程技术部",
    "sort" : 2,
    "children" : [ {
      "id" : "1254743048399618048",
      "delFlag" : "0",
      "parentId" : "1251417049217892352",
      "parentName" : "",
      "parentIds" : ",1251417049217892352,0",
      "nodeLevel" : 1,
      "name" : "桂林组",
      "sort" : 0,
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "000101",
      "orgLegal" : "1254737526682288128,1260096104578416640,1260096969024471040,1260386123788582912,1260411909585240064,1263086389910568960,1263086390439051264,1265911503500869632,1263086390099312640",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    }, {
      "id" : "1254743102216732672",
      "delFlag" : "0",
      "parentId" : "1251417049217892352",
      "parentName" : "",
      "parentIds" : ",1251417049217892352,0",
      "nodeLevel" : 1,
      "name" : "阳朔组",
      "sort" : 0,
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "000102",
      "orgLegal" : "1254737526682288128,1260096104578416640,1260096336523427840,1260385856158433280,1263086390254501888,1263086390611017728,1282932718371340288,1282946771349143552,1283230435664461824",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    } ],
    "checked" : false,
    "tenantId" : "1249628559274672128",
    "code" : "002",
    "orgLegal" : "1260411909585240064,1265911503500869632,1256191224977555456",
    "status" : "1",
    "orgLegalNameList" : [ ],
    "statusText" : "启用",
    "orgTypeText" : "",
    "orgLegalName" : ""
  }, {
    "id" : "1253160987977056256",
    "delFlag" : "0",
    "parentId" : "0",
    "parentName" : "",
    "parentIds" : ",0",
    "nodeLevel" : 0,
    "name" : "阳朔片区",
    "sort" : 2,
    "children" : [ {
      "id" : "1259042618608713728",
      "delFlag" : "0",
      "parentId" : "1253160987977056256",
      "parentName" : "",
      "parentIds" : ",1253160987977056256,0",
      "nodeLevel" : 1,
      "name" : "稽查组",
      "sort" : 0,
      "children" : [ {
        "id" : "1260095268116758528",
        "delFlag" : "0",
        "parentId" : "1259042618608713728",
        "parentName" : "",
        "parentIds" : ",1259042618608713728,1253160987977056256,0",
        "nodeLevel" : 2,
        "name" : "站稽核",
        "sort" : -4,
        "children" : [ {
          "id" : "1287737101500022784",
          "delFlag" : "0",
          "parentId" : "1260095268116758528",
          "parentName" : "",
          "parentIds" : ",1260095268116758528,1259042618608713728,1253160987977056256,0",
          "nodeLevel" : 3,
          "name" : "ddd",
          "sort" : 30,
          "checked" : false,
          "tenantId" : "1249628559274672128",
          "code" : "dddd",
          "orgType" : "1",
          "orgLegal" : "",
          "status" : "1",
          "orgLegalNameList" : [ ],
          "statusText" : "启用",
          "orgTypeText" : "路段",
          "orgLegalName" : ""
        } ],
        "checked" : false,
        "tenantId" : "1249628559274672128",
        "code" : "station-audit",
        "status" : "0",
        "orgLegalNameList" : [ ],
        "statusText" : "未启用",
        "orgTypeText" : "",
        "orgLegalName" : ""
      } ],
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "audit",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    }, {
      "id" : "1253161121011990528",
      "delFlag" : "0",
      "parentId" : "1253160987977056256",
      "parentName" : "",
      "parentIds" : ",1253160987977056256,0",
      "nodeLevel" : 1,
      "name" : "桂阳路",
      "sort" : 2,
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "10010001",
      "orgLegal" : "1253164792152064000",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    }, {
      "id" : "1260409339248640000",
      "delFlag" : "0",
      "parentId" : "1253160987977056256",
      "parentName" : "",
      "parentIds" : ",1253160987977056256,0",
      "nodeLevel" : 1,
      "name" : "监测运维组",
      "sort" : 2,
      "children" : [ {
        "id" : "1262647853969309696",
        "delFlag" : "0",
        "parentId" : "1260409339248640000",
        "parentName" : "",
        "parentIds" : ",1260409339248640000,1253160987977056256,0",
        "nodeLevel" : 2,
        "name" : "只有收费站组",
        "sort" : 1,
        "checked" : false,
        "tenantId" : "1249628559274672128",
        "code" : "onlyStation",
        "status" : "1",
        "orgLegalNameList" : [ ],
        "statusText" : "启用",
        "orgTypeText" : "",
        "orgLegalName" : ""
      } ],
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "jianceyunwei",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    }, {
      "id" : "1253161235399049216",
      "delFlag" : "0",
      "parentId" : "1253160987977056256",
      "parentName" : "",
      "parentIds" : ",1253160987977056256,0",
      "nodeLevel" : 1,
      "name" : "阳平路",
      "sort" : 3,
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "10010002",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    }, {
      "id" : "1253161343406571520",
      "delFlag" : "0",
      "parentId" : "1253160987977056256",
      "parentName" : "",
      "parentIds" : ",1253160987977056256,0",
      "nodeLevel" : 1,
      "name" : "桂兴路",
      "sort" : 4,
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "10010003",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    }, {
      "id" : "1276392757081931776",
      "delFlag" : "0",
      "parentId" : "1253160987977056256",
      "parentName" : "",
      "parentIds" : ",1253160987977056256,0",
      "nodeLevel" : 1,
      "name" : "六塘收费站",
      "sort" : 4,
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "liutangshoufeizhan",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    }, {
      "id" : "1253161401384435712",
      "delFlag" : "0",
      "parentId" : "1253160987977056256",
      "parentName" : "",
      "parentIds" : ",1253160987977056256,0",
      "nodeLevel" : 1,
      "name" : "灵三路",
      "sort" : 5,
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "10010004",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    } ],
    "checked" : false,
    "tenantId" : "1249628559274672128",
    "code" : "1001",
    "orgType" : "1",
    "orgLegal" : "1253166395286355968,1255490184791719936,1256191224977555456",
    "status" : "1",
    "orgLegalNameList" : [ ],
    "statusText" : "启用",
    "orgTypeText" : "路段",
    "orgLegalName" : ""
  }, {
    "id" : "1257593526225469440",
    "delFlag" : "0",
    "parentId" : "0",
    "parentName" : "",
    "parentIds" : ",0",
    "nodeLevel" : 0,
    "name" : "营运管理部",
    "sort" : 2,
    "checked" : false,
    "tenantId" : "1249628559274672128",
    "code" : "GL001",
    "orgLegal" : "1260094098237292544,1262571685702270976",
    "status" : "1",
    "orgLegalNameList" : [ ],
    "statusText" : "启用",
    "orgTypeText" : "",
    "orgLegalName" : ""
  }, {
    "id" : "1258659637545140224",
    "delFlag" : "0",
    "parentId" : "0",
    "parentName" : "",
    "parentIds" : ",0",
    "nodeLevel" : 0,
    "name" : "招商华软",
    "sort" : 3,
    "checked" : false,
    "tenantId" : "1249628559274672128",
    "code" : "test001",
    "orgLegal" : "",
    "status" : "1",
    "orgLegalNameList" : [ ],
    "statusText" : "启用",
    "orgTypeText" : "",
    "orgLegalName" : ""
  }, {
    "id" : "1260410622437228544",
    "delFlag" : "0",
    "parentId" : "0",
    "parentName" : "",
    "parentIds" : ",0",
    "nodeLevel" : 0,
    "name" : "监测运维开发组",
    "sort" : 3,
    "children" : [ {
      "id" : "1260526787721428992",
      "delFlag" : "0",
      "parentId" : "1260410622437228544",
      "parentName" : "",
      "parentIds" : ",1260410622437228544,0",
      "nodeLevel" : 1,
      "name" : "2个路段组",
      "sort" : 1,
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "twogroup",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    } ],
    "checked" : false,
    "tenantId" : "1249628559274672128",
    "code" : "jianceyunwei",
    "status" : "1",
    "orgLegalNameList" : [ ],
    "statusText" : "启用",
    "orgTypeText" : "",
    "orgLegalName" : ""
  }, {
    "id" : "1265679329954627584",
    "delFlag" : "0",
    "parentId" : "0",
    "parentName" : "",
    "parentIds" : ",0",
    "nodeLevel" : 0,
    "name" : "收费站",
    "sort" : 3,
    "children" : [ {
      "id" : "1280700693442723840",
      "delFlag" : "0",
      "parentId" : "1265679329954627584",
      "parentName" : "",
      "parentIds" : ",1265679329954627584,0",
      "nodeLevel" : 1,
      "name" : "故障上报组",
      "sort" : 5,
      "checked" : false,
      "tenantId" : "1249628559274672128",
      "code" : "005",
      "status" : "1",
      "orgLegalNameList" : [ ],
      "statusText" : "启用",
      "orgTypeText" : "",
      "orgLegalName" : ""
    } ],
    "checked" : false,
    "tenantId" : "1249628559274672128",
    "code" : "003",
    "status" : "1",
    "orgLegalNameList" : [ ],
    "statusText" : "启用",
    "orgTypeText" : "",
    "orgLegalName" : ""
  }, {
    "id" : "1351074739606519808",
    "delFlag" : "0",
    "parentId" : "0",
    "parentName" : "",
    "parentIds" : ",0",
    "nodeLevel" : 0,
    "name" : "九江二桥",
    "sort" : 30,
    "checked" : false,
    "tenantId" : "1249628559274672128",
    "code" : "jiujiang",
    "status" : "1",
    "orgLegalNameList" : [ ],
    "statusText" : "启用",
    "orgTypeText" : "",
    "orgLegalName" : ""
  } ],
  "success" : true
}

const propValues = {
  "code" : 200,
  "message" : "操作成功",
  "data" : [ {
    "id" : "1260409339248640000",
    "delFlag" : "0",
    "parentId" : "1253160987977056256",
    "parentName" : "",
    "parentIds" : ",1253160987977056256,0",
    "nodeLevel" : 1,
    "name" : "监测运维组",
    "sort" : 2,
    "checked" : false,
    "tenantId" : "1249628559274672128",
    "code" : "jianceyunwei",
    "status" : "1",
    "orgLegalNameList" : [ ],
    "orgTypeText" : "",
    "orgLegalName" : "",
    "statusText" : "启用"
  } ],
  "success" : true
}




module.exports = [
  {
    url: '/api/v1/org/saasOrg/data',
    type: 'post',
    response: _ => {
      return orgData
    }
  },
  {
    url: '/api/v1/org/saasOrg/findOrgTree',
    type: 'get',
    response: _ => {
      return orgTrees
    }
  },
  {
    url: '/api/v1/org/saasOrg/findDataByPropValues',
    type: 'post',
    response: _ => {
      return propValues
    }
  }
]
