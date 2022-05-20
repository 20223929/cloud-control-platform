// Just a mock data

const constantRoutes = [
  {
    path: '/redirect',
    component: 'layout',
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: 'views/redirect/index'
      }
    ]
  },
  {
    path: '/login',
    component: 'views/login/index',
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: 'views/login/auth-redirect',
    hidden: true
  },
  {
    path: '/404',
    component: 'views/error-page/404',
    hidden: true
  },
  {
    path: '/401',
    component: 'views/error-page/401',
    hidden: true
  }
]

const asyncRoutes6 = [{
  'id': '1249604958995611648',
  'path': '/1249604958995611648',
  'meta': {
    'title': '测试',
    'breadcrumb': false,
    'isForm': false
  },
  'component': 'layout',
  'alwaysShow': true,
  'isBlank': false,
  'isLeaf': false,
  'children': [{
    'id': '1249605138792841216',
    'path': '/1249605138792841216',
    'name': '1249605138792841216',
    'meta': {
      'title': '主副表测试',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1249635915123392512',
    'path': '/1249635915123392512',
    'name': '1249635915123392512',
    'meta': {
      'title': '请假表单表测试',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1250043708942843904',
    'path': '/1250043708942843904',
    'name': '1250043708942843904',
    'meta': {
      'title': '树结构测试',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1250058995406733312',
    'path': '/1250058995406733312',
    'name': '1250058995406733312',
    'meta': {
      'title': '全组件测试实例',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }]
}, {
  'id': '1248169561283362816',
  'path': '/1248169561283362816',
  'meta': {
    'title': '个人中心',
    'breadcrumb': false,
    'isForm': false
  },
  'component': 'layout',
  'alwaysShow': true,
  'isBlank': false,
  'isLeaf': false,
  'children': [{
    'id': '1248170179980951552',
    'path': '/1248170179980951552',
    'name': '1248170179980951552',
    'meta': {
      'title': '我的通知',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1248170295978622976',
    'path': '/1248170295978622976',
    'name': '1248170295978622976',
    'meta': {
      'title': '通知管理',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }]
}, {
  'id': '1245297546004267008',
  'path': '/1245297546004267008',
  'meta': {
    'title': '基础数据',
    'breadcrumb': false,
    'isForm': false
  },
  'component': 'layout',
  'alwaysShow': true,
  'isBlank': false,
  'isLeaf': false,
  'children': [{
    'id': '1245297668331143168',
    'path': '/1245297668331143168',
    'name': '1245297668331143168',
    'meta': {
      'title': '收费单元',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1248431491067674624',
    'path': '/1248431491067674624',
    'name': '1248431491067674624',
    'meta': {
      'title': '收费站',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }]
}, {
  'id': '1272573341613424640',
  'path': '/1272573341613424640',
  'meta': {
    'title': '一级菜单',
    'breadcrumb': false,
    'noCache': false,
    'affix': false,
    'isForm': false
  },
  'component': 'layout',
  'hidden': false,
  'alwaysShow': true,
  'isBlank': false,
  'isLeaf': true,
  'children': [{
    'id': '1272573341613424640',
    'path': '/1272573341613424640',
    'name': '1272573341613424640',
    'meta': {
      'title': '一级菜单',
      'breadcrumb': false,
      'noCache': false,
      'affix': false,
      'isForm': false
    },
    'component': 'views/nested/menu2/index.vue',
    'hidden': false,
    'alwaysShow': false,
    'isBlank': false,
    'isLeaf': true
  }]
}, {
  'id': '1272573742274314240',
  'path': '/1272573742274314240',
  'meta': {
    'title': '两级菜单',
    'breadcrumb': false,
    'noCache': false,
    'affix': false,
    'isForm': false
  },
  'component': 'layout',
  'hidden': false,
  'alwaysShow': false,
  'isBlank': false,
  'isLeaf': false,
  'children': [{
    'id': '1272578124860620800',
    'path': '/test/two',
    'name': '1272578124860620800',
    'meta': {
      'title': '二级',
      'breadcrumb': true,
      'noCache': false,
      'affix': false,
      'isForm': false
    },
    'component': 'views/nested/menu2/index',
    'hidden': false,
    'alwaysShow': false,
    'isBlank': false,
    'isLeaf': true
  }]
}, {
  'id': '1250281553120985088',
  'path': '/1250281553120985088',
  'meta': {
    'title': '短信管理',
    'icon': 'message',
    'breadcrumb': false,
    'isForm': false
  },
  'component': 'layout',
  'alwaysShow': true,
  'isBlank': false,
  'isLeaf': false,
  'children': [{
    'id': '1250281991488667648',
    'path': '/1250281991488667648',
    'name': '1250281991488667648',
    'meta': {
      'title': '短信模板',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1250281895485243392',
    'path': '/1250281895485243392',
    'name': '1250281895485243392',
    'meta': {
      'title': '短信日志',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }]
}, {
  'id': '1250281737586475008',
  'path': '/1250281737586475008',
  'meta': {
    'title': '邮件管理',
    'icon': 'email',
    'breadcrumb': false,
    'isForm': false
  },
  'component': 'layout',
  'alwaysShow': true,
  'isBlank': false,
  'isLeaf': false,
  'children': [{
    'id': '1250317038933508096',
    'path': '/1250317038933508096',
    'name': '1250317038933508096',
    'meta': {
      'title': '邮件模板',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1250317142000140288',
    'path': '/1250317142000140288',
    'name': '1250317142000140288',
    'meta': {
      'title': '邮件日志',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }]
}, {
  'id': '1199205938653822976',
  'path': '/1199205938653822976',
  'meta': {
    'title': '系统管理',
    'icon': 'icon',
    'breadcrumb': false,
    'isForm': false
  },
  'component': 'layout',
  'alwaysShow': true,
  'isBlank': false,
  'isLeaf': false,
  'children': [{
    'id': '1244985151717376000',
    'path': '/1244985151717376000',
    'name': '1244985151717376000',
    'meta': {
      'title': '组织机构',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1199209043499941888',
    'path': '/1199209043499941888',
    'name': '1199209043499941888',
    'meta': {
      'title': '用户管理',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1215475695354904576',
    'path': '/1215475695354904576',
    'name': '1215475695354904576',
    'meta': {
      'title': '数据字典',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1215475777009614848',
    'path': '/1215475777009614848',
    'name': '1215475777009614848',
    'meta': {
      'title': '角色管理',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1215475853039763456',
    'path': '/1215475853039763456',
    'name': '1215475853039763456',
    'meta': {
      'title': '资源管理',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }, {
    'id': '1248154420634451968',
    'path': '/1248154420634451968',
    'name': '1248154420634451968',
    'meta': {
      'title': '组织架构',
      'breadcrumb': true,
      'isForm': false
    },
    'isBlank': false,
    'isLeaf': true
  }]
}]

const asyncRoutes7 = [{
  'path': '',
  'component': 'layout',
  'redirect': 'dashboard',
  'meta': { 'title': 'dashboard', 'icon': 'fa fa-television' },
  'children': [{
    'path': 'dashboard',
    'component': 'views/dashboard/index',
    'name': 'Dashboard',
    'meta': { 'title': 'dashboard', 'icon': 'fa fa-television', 'affix': true },
    'id': '007be243-cf0a-d59a-4f94-8947a3f99674'
  }],
  'id': '2f96ee9d-4105-1db5-7590-9793aa24f535'
}, {
  'path': '/documentation',
  'component': 'layout',
  'meta': { 'title': 'documentation', 'icon': 'documentation' },
  'children': [{
    'path': 'index',
    'component': 'views/documentation/index',
    'name': 'Documentation',
    'meta': { 'title': 'documentation', 'icon': 'documentation', 'affix': true },
    'id': 'c04e19e5-d9e5-20b7-2ed1-53331fa2514b'
  }],
  'id': '443567f9-8bf7-b21d-5633-7c83eac09d17'
}, {
  'path': '/guide',
  'component': 'layout',
  'redirect': '/guide/index',
  'meta': { 'title': 'guide', 'icon': 'guide', 'noCache': true },
  'children': [{
    'path': 'index',
    'component': 'views/guide/index',
    'name': 'Guide',
    'meta': { 'title': 'guide', 'icon': 'guide', 'noCache': true },
    'id': '57eb6e3f-ccde-8c95-ba0e-523c1c173b08'
  }],
  'id': '27829b21-5265-22ce-ddb8-b37e1081945f'
}, {
  'path': '/permission',
  'component': 'layout',
  'redirect': '/permission/index',
  'alwaysShow': true,
  'meta': { 'title': 'permission', 'icon': 'lock', 'roles': ['admin', 'editor'] },
  'children': [{
    'path': '/permission/page',
    'component': 'views/permission/page',
    'name': 'PagePermission',
    'meta': { 'title': 'pagePermission', 'roles': ['admin'] },
    'id': '2a7e58fb-3c21-34c9-cd60-4bb5719d1cd2'
  }, {
    'path': '/permission/directive',
    'component': 'views/permission/directive',
    'name': 'DirectivePermission',
    'meta': { 'title': 'directivePermission' },
    'id': 'e005b2f7-3fcf-b762-41f1-1237436e2c2d'
  }, {
    'path': 'role',
    'component': 'views/permission/role',
    'name': 'RolePermission',
    'meta': { 'title': 'rolePermission', 'roles': ['admin'] },
    'id': '6701000b-3946-baa4-81dd-4b154b769648'
  }],
  'id': 'a0d15fe5-96f8-1221-aa34-8299a2273924'
}, {
  'path': '/icon',
  'component': 'layout',
  'children': [{
    'path': '/icon',
    'component': 'views/icons/index',
    'name': 'Icons',
    'meta': { 'title': 'icons', 'icon': 'icon', 'noCache': true },
    'id': 'fbb27311-61e7-bd23-4be9-c9ab1b273b79'
  }],
  'id': 'aead3975-8499-2920-bf43-8f7522e42316'
}, {
  'path': '/components',
  'component': 'layout',
  'redirect': 'noRedirect',
  'name': 'ComponentDemo',
  'meta': { 'title': 'components', 'icon': 'component' },
  'children': [{
    'path': '/components/tinymce',
    'component': 'views/components-demo/tinymce',
    'name': 'TinymceDemo',
    'meta': { 'title': 'tinymce' },
    'id': '90b1e7c4-4d2b-af01-7e22-4d81c4a37345'
  }, {
    'path': '/components/markdown',
    'component': 'views/components-demo/markdown',
    'name': 'MarkdownDemo',
    'meta': { 'title': 'markdown' },
    'id': 'c7e8dd8c-1d19-aa6a-6171-56f8c1a62e75'
  }, {
    'path': 'json-editor',
    'component': 'views/components-demo/json-editor',
    'name': 'JsonEditorDemo',
    'meta': { 'title': 'jsonEditor' },
    'id': '445a9d65-a963-75c3-bcf9-7d5476719479'
  }, {
    'path': 'split-pane',
    'component': 'views/components-demo/split-pane',
    'name': 'SplitpaneDemo',
    'meta': { 'title': 'splitPane' },
    'id': '9b4d744e-f20b-c331-a95d-b8ea83cbaefe'
  }, {
    'path': 'avatar-upload',
    'component': 'views/components-demo/avatar-upload',
    'name': 'AvatarUploadDemo',
    'meta': { 'title': 'avatarUpload' },
    'id': '43f0d47f-7aa9-de6e-c271-d2b90ce6bc40'
  }, {
    'path': 'dropzone',
    'component': 'views/components-demo/dropzone',
    'name': 'DropzoneDemo',
    'meta': { 'title': 'dropzone' },
    'id': 'a607fbfa-717f-9ae0-dc9c-5d9a9a0d09bd'
  }, {
    'path': 'sticky',
    'component': 'views/components-demo/sticky',
    'name': 'StickyDemo',
    'meta': { 'title': 'sticky' },
    'id': '3c67e40b-04fa-40c1-4d13-7355f30e757c'
  }, {
    'path': 'count-to',
    'component': 'views/components-demo/count-to',
    'name': 'CountToDemo',
    'meta': { 'title': 'countTo' },
    'id': '6e715738-307c-178a-9927-e3d4e7308b0a'
  }, {
    'path': 'mixin',
    'component': 'views/components-demo/mixin',
    'name': 'ComponentMixinDemo',
    'meta': { 'title': 'componentMixin' },
    'id': 'd49745a4-2ef6-8cd0-6127-44e3fbdc9a28'
  }, {
    'path': 'back-to-top',
    'component': 'views/components-demo/back-to-top',
    'name': 'BackToTopDemo',
    'meta': { 'title': 'backToTop' },
    'id': 'eaf317d9-dc77-cd6d-fc62-0c8eb15d148c'
  }, {
    'path': 'drag-dialog',
    'component': 'views/components-demo/drag-dialog',
    'name': 'DragDialogDemo',
    'meta': { 'title': 'dragDialog' },
    'id': '5f9ddf3a-b576-ff40-9469-25d7e11b931c'
  }, {
    'path': 'drag-select',
    'component': 'views/components-demo/drag-select',
    'name': 'DragSelectDemo',
    'meta': { 'title': 'dragSelect' },
    'id': '5b83115d-35d6-b47d-e8e9-361c42f2fa57'
  }, {
    'path': 'dnd-list',
    'component': 'views/components-demo/dnd-list',
    'name': 'DndListDemo',
    'meta': { 'title': 'dndList' },
    'id': '6aaa9dc6-b7ae-bec4-cdf6-634a36c998ae'
  }, {
    'path': 'drag-kanban',
    'component': 'views/components-demo/drag-kanban',
    'name': 'DragKanbanDemo',
    'meta': { 'title': 'dragKanban' },
    'id': '28337928-5e16-bbd0-5c3b-4568794803f7'
  }],
  'id': '74d51575-0807-490a-c3d7-639c0708e76a'
}, {
  'path': '/charts',
  'component': 'layout',
  'redirect': 'noRedirect',
  'name': 'Charts',
  'meta': { 'title': 'charts', 'icon': 'chart' },
  'children': [{
    'path': 'keyboard',
    'component': 'views/charts/keyboard',
    'name': 'KeyboardChart',
    'meta': { 'title': 'keyboardChart', 'noCache': true },
    'id': '1387bd2f-5bc0-9e5e-48a5-9497211c3a1b'
  }, {
    'path': 'line',
    'component': 'views/charts/line',
    'name': 'LineChart',
    'meta': { 'title': 'lineChart', 'noCache': true },
    'id': '22fbc4f1-6fa2-12e6-dda1-bdcc16b21ade'
  }, {
    'path': 'mixchart',
    'component': 'views/charts/mixChart',
    'name': 'MixChart',
    'meta': { 'title': 'mixChart', 'noCache': true },
    'id': 'ad9c5521-7d0b-d83f-a4fd-aecc2664515c'
  }],
  'id': '68d60313-35b6-3c80-8589-c879d6284f28'
}, {
  'path': '/14',
  'component': 'layout',
  'name': 'Nested3',
  'meta': { 'title': '一级菜单', 'icon': 'nested' },
  'alwaysShow': true,
  'children': [{
    'path': '/keyboard',
    'component': 'views/charts/keyboard',
    'name': 'KeyboardChart1',
    'meta': { 'title': '一级菜单dsfjdl', 'noCache': true },
    'id': '3bde2499-61d5-3c30-b330-9422fce662ff'
  }],
  'id': '7c8f55be-ae02-1c98-8347-1cd258bca346'
}, {
  'path': '/11',
  'component': 'layout',
  'name': 'Nested',
  'meta': { 'title': '一级菜单', 'icon': 'nested' },
  'children': [{
    'path': '/22',
    'component': 'layout/RouteView',
    'name': 'Nested',
    'meta': { 'title': '二级菜单', 'icon': 'nested' },
    'children': [{
      'path': '/33',
      'component': 'layout/RouteView',
      'name': 'Nested',
      'alwaysShow': true,
      'meta': { 'title': '三级菜单', 'icon': 'nested' },
      'children': [{
        'path': '/44',
        'name': 'Menu2',
        'component': 'views/nested/menu2/index',
        'meta': { 'title': '四级' },
        'id': '0d97b121-6c5f-1626-1c2b-9f58339938f4'
      }],
      'id': '5f2b25fb-cfef-4d56-f0f6-dd5acac19925'
    }],
    'id': '9aea6139-82c8-eb51-03c2-d65e99d093fa'
  }],
  'id': '26fa79b4-511e-b957-41cd-58802775847d'
}, {
  'path': '/example',
  'component': 'layout',
  'redirect': '/example/list',
  'name': 'Example',
  'meta': { 'title': 'example', 'icon': 'example' },
  'children': [{
    'path': 'create',
    'component': 'views/example/create',
    'name': 'CreateArticle',
    'meta': { 'title': 'createArticle', 'icon': 'edit' },
    'id': '0fcb3266-1e4e-0068-d531-d2d1b77d317e'
  }, {
    'path': 'edit/:id(\\d+)',
    'component': 'views/example/edit',
    'name': 'EditArticle',
    'meta': { 'title': 'editArticle', 'noCache': true },
    'hidden': true,
    'id': 'a3a26c82-aa5c-5037-50bb-2e1b70a039dc'
  }, {
    'path': 'list',
    'component': 'views/example/list',
    'name': 'ArticleList',
    'meta': { 'title': 'articleList', 'icon': 'list' },
    'id': '454328f2-59d6-47f3-6fb2-4661e8851628'
  }],
  'id': '8d2f8cdd-516e-8e6d-e9b1-e374afe82441'
}, {
  'path': '/tab',
  'component': 'layout',
  'children': [{
    'path': 'index',
    'component': 'views/tab/index',
    'name': 'Tab',
    'meta': { 'title': 'tab', 'icon': 'tab' },
    'id': 'f9e23298-83e8-0909-4b96-bf07ff92ae96'
  }],
  'id': '626bc05d-7bef-ee9d-eae8-8b2309ce213e'
}, {
  'path': '/error',
  'component': 'layout',
  'redirect': 'noRedirect',
  'name': 'ErrorPages',
  'meta': { 'title': 'errorPages', 'icon': '404' },
  'children': [{
    'path': '401',
    'component': 'views/error-page/401',
    'name': 'Page401',
    'meta': { 'title': 'page401', 'noCache': true },
    'id': '6594be92-50cf-1bf0-a34f-46e75d9ee34f'
  }, {
    'path': '404',
    'component': 'views/error-page/404',
    'name': 'Page404',
    'meta': { 'title': 'page404', 'noCache': true },
    'id': 'c7a4c7d0-99e2-c799-916a-d567e7571aa4'
  }],
  'id': 'c63a87c2-893a-c2d2-81d6-e421984bdcd8'
}, {
  'path': '/error-log',
  'component': 'layout',
  'redirect': 'noRedirect',
  'children': [{
    'path': 'log',
    'component': 'views/error-log/index',
    'name': 'ErrorLog',
    'meta': { 'title': 'errorLog', 'icon': 'bug' },
    'id': 'dba091ff-53f0-ff21-a103-70efb85e04f7'
  }],
  'id': '1b1ebb5d-6304-4038-4d41-33e1ef21e499'
}, {
  'path': '/excel',
  'component': 'layout',
  'redirect': '/excel/export-excel',
  'name': 'Excel',
  'meta': { 'title': 'excel', 'icon': 'excel' },
  'children': [{
    'path': 'export-excel',
    'component': 'views/excel/export-excel',
    'name': 'ExportExcel',
    'meta': { 'title': 'exportExcel' },
    'id': '329e991b-edb1-ab49-2e30-77f397d68a25'
  }, {
    'path': 'export-selected-excel',
    'component': 'views/excel/select-excel',
    'name': 'SelectExcel',
    'meta': { 'title': 'selectExcel' },
    'id': 'f67b7117-84df-d748-987a-87a02e1da125'
  }, {
    'path': 'export-merge-header',
    'component': 'views/excel/merge-header',
    'name': 'MergeHeader',
    'meta': { 'title': 'mergeHeader' },
    'id': '59b24eaf-3c38-5d18-3e09-74d02d451a5c'
  }, {
    'path': 'upload-excel',
    'component': 'views/excel/upload-excel',
    'name': 'UploadExcel',
    'meta': { 'title': 'uploadExcel' },
    'id': 'a6558363-8921-32a9-b343-3ac82abe838d'
  }],
  'id': '2fd62f88-2407-3365-1df1-148ba982ac41'
}, {
  'path': '/zip',
  'component': 'layout',
  'redirect': '/zip/download',
  'alwaysShow': true,
  'meta': { 'title': 'zip', 'icon': 'zip' },
  'children': [{
    'path': 'download',
    'component': 'views/zip/index',
    'name': 'ExportZip',
    'meta': { 'title': 'exportZip' },
    'id': '4c0a9f3a-37ca-a984-4e4b-a8ad1a8b401f'
  }],
  'id': 'a0587a63-d85e-f4ca-cd23-e748eb2a3762'
}, {
  'path': '/pdf',
  'component': 'layout',
  'redirect': '/pdf/index',
  'children': [{
    'path': 'index',
    'component': 'views/pdf/index',
    'name': 'PDF',
    'meta': { 'title': 'pdf', 'icon': 'pdf' },
    'id': '2ebd91f9-c0f2-5361-2964-ad080c118c07'
  }],
  'id': '1afa9bc5-5591-cd78-f078-990457d8ddab'
}, { 'path': '/pdf/download', 'component': 'views/pdf/download', 'hidden': true, 'id': '1c010bd8-ce2f-1513-7f61-f192ac4a98fb' }, {
  'path': '/theme',
  'component': 'layout',
  'redirect': 'noRedirect',
  'children': [{
    'path': 'index',
    'component': 'views/theme/index',
    'name': 'Theme',
    'meta': { 'title': 'theme', 'icon': 'theme' },
    'id': 'b02f3482-ee1f-fc57-f910-0728e063ff76'
  }],
  'id': 'cbf04bdb-7360-c655-57ad-8071ee19341a'
}, {
  'path': '/clipboard',
  'component': 'layout',
  'redirect': 'noRedirect',
  'children': [{
    'path': 'index',
    'component': 'views/clipboard/index',
    'name': 'ClipboardDemo',
    'meta': { 'title': 'clipboardDemo', 'icon': 'clipboard' },
    'id': '0fdac484-dd9d-8acc-63b0-d637d544d8b9'
  }],
  'id': '28bcc15c-4dc2-03c7-1749-fc13f379dc21'
}, {
  'path': '/i18n',
  'component': 'layout',
  'children': [{
    'path': 'index',
    'component': 'views/i18n-demo/index',
    'name': 'I18n',
    'meta': { 'title': 'i18n', 'icon': 'international' },
    'id': 'e401bd31-9676-ebd2-5455-c69afb890679'
  }],
  'id': '66af2c1e-9013-0cad-07de-0df2847bc01c'
}, {
  'path': 'external-link',
  'component': 'layout',
  'children': [{
    'path': 'https://www.baidu.com',
    'meta': { 'title': 'externalLink', 'icon': 'link' },
    'id': 'ab35efff-b4eb-b56f-e649-dac087eb1048'
  }],
  'id': '25b7c8c7-1e5b-51ea-5d34-e3528485fc28'
}]

// const asyncRoutes2 = [
//   {
//     path: '/layout',
//     component: 'layout',
//     children: [
//       ...asyncRoutes
//     ]
//   }
// ]

const asyncRoutes1 = [
  // todo: 首页路由
  {
    path: '',
    component: 'layout',
    redirect: 'dashboard',
    children: [
      {
        path: 'dashboard',
        component: 'views/dashboard/index',
        name: 'Dashboard',
        meta: { title: 'dashboard', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/documentation',
    component: 'layout',
    children: [
      {
        path: 'index',
        component: 'views/documentation/index',
        name: 'Documentation',
        meta: { title: 'documentation', icon: 'documentation', affix: true }
      }
    ]
  },
  {
    path: '/guide',
    component: 'layout',
    redirect: '/guide/index',
    children: [
      {
        path: 'index',
        component: 'views/guide/index',
        name: 'Guide',
        meta: { title: 'guide', icon: 'guide', noCache: true }
      }
    ]
  },
  {
    path: '/permission',
    component: 'layout',
    redirect: '/permission/index',
    alwaysShow: true,
    meta: {
      title: 'permission',
      icon: 'lock',
      roles: ['admin', 'editor']
    },
    children: [
      {
        path: 'page',
        component: 'views/permission/page',
        name: 'PagePermission',
        meta: {
          title: 'pagePermission',
          roles: ['admin']
        }
      },
      {
        path: 'directive',
        component: 'views/permission/directive',
        name: 'DirectivePermission',
        meta: {
          title: 'directivePermission'
        }
      },
      {
        path: 'role',
        component: 'views/permission/role',
        name: 'RolePermission',
        meta: {
          title: 'rolePermission',
          roles: ['admin']
        }
      }
    ]
  },

  {
    path: '/icon',
    component: 'layout',
    children: [
      {
        path: '',
        component: 'views/icons/index',
        name: 'Icons',
        meta: { title: 'icons', icon: 'icon', noCache: true }
      }
    ]
  },

  {
    path: '/components',
    component: 'layout',
    redirect: 'noRedirect',
    name: 'ComponentDemo',
    meta: {
      title: 'components',
      icon: 'component'
    },
    children: [
      {
        path: 'tinymce',
        component: 'views/components-demo/tinymce',
        name: 'TinymceDemo',
        meta: { title: 'tinymce' }
      },
      {
        path: 'markdown',
        component: 'views/components-demo/markdown',
        name: 'MarkdownDemo',
        meta: { title: 'markdown' }
      },
      {
        path: 'json-editor',
        component: 'views/components-demo/json-editor',
        name: 'JsonEditorDemo',
        meta: { title: 'jsonEditor' }
      },
      {
        path: 'split-pane',
        component: 'views/components-demo/split-pane',
        name: 'SplitpaneDemo',
        meta: { title: 'splitPane' }
      },
      {
        path: 'avatar-upload',
        component: 'views/components-demo/avatar-upload',
        name: 'AvatarUploadDemo',
        meta: { title: 'avatarUpload' }
      },
      {
        path: 'dropzone',
        component: 'views/components-demo/dropzone',
        name: 'DropzoneDemo',
        meta: { title: 'dropzone' }
      },
      {
        path: 'sticky',
        component: 'views/components-demo/sticky',
        name: 'StickyDemo',
        meta: { title: 'sticky' }
      },
      {
        path: 'count-to',
        component: 'views/components-demo/count-to',
        name: 'CountToDemo',
        meta: { title: 'countTo' }
      },
      {
        path: 'mixin',
        component: 'views/components-demo/mixin',
        name: 'ComponentMixinDemo',
        meta: { title: 'componentMixin' }
      },
      {
        path: 'back-to-top',
        component: 'views/components-demo/back-to-top',
        name: 'BackToTopDemo',
        meta: { title: 'backToTop' }
      },
      {
        path: 'drag-dialog',
        component: 'views/components-demo/drag-dialog',
        name: 'DragDialogDemo',
        meta: { title: 'dragDialog' }
      },
      {
        path: 'drag-select',
        component: 'views/components-demo/drag-select',
        name: 'DragSelectDemo',
        meta: { title: 'dragSelect' }
      },
      {
        path: 'dnd-list',
        component: 'views/components-demo/dnd-list',
        name: 'DndListDemo',
        meta: { title: 'dndList' }
      },
      {
        path: 'drag-kanban',
        component: 'views/components-demo/drag-kanban',
        name: 'DragKanbanDemo',
        meta: { title: 'dragKanban' }
      }
    ]
  },
  {
    path: '/charts',
    component: 'layout',
    redirect: 'noRedirect',
    name: 'Charts',
    meta: {
      title: 'charts',
      icon: 'chart'
    },
    children: [
      {
        path: 'keyboard',
        component: 'views/charts/keyboard',
        name: 'KeyboardChart',
        meta: { title: 'keyboardChart', noCache: true }
      },
      {
        path: 'line',
        component: 'views/charts/line',
        name: 'LineChart',
        meta: { title: 'lineChart', noCache: true }
      },
      {
        path: 'mixchart',
        component: 'views/charts/mixChart',
        name: 'MixChart',
        meta: { title: 'mixChart', noCache: true }
      }
    ]
  },
  {
    path: '/14',
    component: 'layout',
    // redirect: '/nested/menu1/menu1-1',
    name: 'Nested3',
    meta: {
      title: '一级菜单',
      icon: 'nested'
    },
    alwaysShow: true,
    children: [
      {
        path: '/keyboard',
        component: 'views/charts/keyboard',
        name: 'KeyboardChart1',
        meta: { title: '一级菜单dsfjdl', noCache: true }
      }
    ]
  },
  {
    path: '/11',
    component: 'layout',
    // redirect: '/nested/menu1/menu1-1',
    name: 'Nested',
    meta: {
      title: '一级菜单',
      icon: 'nested'
    },
    // alwaysShow: true,
    children: [
      // {
      //   path: 'menu1',
      //   component: 'views/nested/menu1/index',
      //   name: 'Menu1',
      //   meta: { title: 'menu1' },
      //   redirect: '/nested/menu1/menu1-1',
      //   children: [
      //     {
      //       path: 'menu1-1',
      //       component: 'views/nested/menu1/menu1-1',
      //       name: 'Menu1-1',
      //       meta: { title: 'menu1-1' }
      //     },
      //     {
      //       path: 'menu1-2',
      //       component: 'views/nested/menu1/menu1-2',
      //       name: 'Menu1-2',
      //       redirect: '/nested/menu1/menu1-2/menu1-2-1',
      //       meta: { title: 'menu1-2' },
      //       children: [
      //         {
      //           path: 'menu1-2-1',
      //           component: 'views/nested/menu1/menu1-2/menu1-2-1',
      //           name: 'Menu1-2-1',
      //           meta: { title: 'menu1-2-1' }
      //         },
      //         {
      //           path: 'menu1-2-2',
      //           component: 'views/nested/menu1/menu1-2/menu1-2-2',
      //           name: 'Menu1-2-2',
      //           meta: { title: 'menu1-2-2' }
      //         }
      //       ]
      //     },
      //     {
      //       path: 'menu1-3',
      //       component: 'views/nested/menu1/menu1-3',
      //       name: 'Menu1-3',
      //       meta: { title: 'menu1-3' }
      //     }
      //   ]
      // },
      {
        path: '/22',
        component: 'layout/RouteView',
        // redirect: '/nested/menu1/menu1-1',
        name: 'Nested',
        // alwaysShow: true,
        meta: {
          title: '二级菜单',
          icon: 'nested'
        },
        children: [
          {
            path: '/33',
            component: 'layout/RouteView',
            // redirect: '/nested/menu1/menu1-1',
            name: 'Nested',
            alwaysShow: true,
            meta: {
              title: '三级菜单',
              icon: 'nested'
            },
            children: [
              {
                path: '44',
                name: 'Menu2',
                component: 'views/nested/menu2/index',
                meta: { title: '四级' }
              }/*,
              {
                path: 'menu1-3',
                component: 'views/nested/menu1/menu1-3',
                name: 'Menu1-3',
                meta: { title: 'menu1-3' }
              }*/
            ]
          }
        ]
      }
    ]
  },
  /* {
    path: '/1',
    component: 'layout',
    // redirect: '/nested/menu1/menu1-1',
    name: 'Nested',
    meta: {
      title: '一级菜单',
      icon: 'nested'
    },
    alwaysShow: true,
    children: [
      {
        path: '/2',
        component: 'layout/RouteView',
        // redirect: '/nested/menu1/menu1-1',
        name: 'Nested',
        alwaysShow: true,
        meta: {
          title: '二级菜单',
          icon: 'nested'
        },
        children: [
          {
            path: '/33',
            component: 'layout/RouteView',
            // redirect: '/nested/menu1/menu1-1',
            name: 'Nested',
            alwaysShow: true,
            meta: {
              title: '三级菜单',
              icon: 'nested'
            },
            children: [
              {
                path: '44',
                name: 'Menu2',
                component: 'views/nested/menu2/index',
                meta: { title: '四级' }
              },
              {
                path: 'menu1-3',
                component: 'views/nested/menu1/menu1-3',
                name: 'Menu1-3',
                meta: { title: 'menu1-3' }
              }
            ]
          }
        ]
      }

    ]
  },*/

  {
    path: '/example',
    component: 'layout',
    redirect: '/example/list',
    name: 'Example',
    meta: {
      title: 'example',
      icon: 'example'
    },
    children: [
      {
        path: 'create',
        component: 'views/example/create',
        name: 'CreateArticle',
        meta: { title: 'createArticle', icon: 'edit' }
      },
      {
        path: 'edit/:id(\\d+)',
        component: 'views/example/edit',
        name: 'EditArticle',
        meta: { title: 'editArticle', noCache: true },
        hidden: true
      },
      {
        path: 'list',
        component: 'views/example/list',
        name: 'ArticleList',
        meta: { title: 'articleList', icon: 'list' }
      }
    ]
  },

  {
    path: '/tab',
    component: 'layout',
    children: [
      {
        path: 'index',
        component: 'views/tab/index',
        name: 'Tab',
        meta: { title: 'tab', icon: 'tab' }
      }
    ]
  },

  {
    path: '/error',
    component: 'layout',
    redirect: 'noRedirect',
    name: 'ErrorPages',
    meta: {
      title: 'errorPages',
      icon: '404'
    },
    children: [
      {
        path: '401',
        component: 'views/error-page/401',
        name: 'Page401',
        meta: { title: 'page401', noCache: true }
      },
      {
        path: '404',
        component: 'views/error-page/404',
        name: 'Page404',
        meta: { title: 'page404', noCache: true }
      }
    ]
  },

  {
    path: '/error-log',
    component: 'layout',
    redirect: 'noRedirect',
    children: [
      {
        path: 'log',
        component: 'views/error-log/index',
        name: 'ErrorLog',
        meta: { title: 'errorLog', icon: 'bug' }
      }
    ]
  },

  {
    path: '/excel',
    component: 'layout',
    redirect: '/excel/export-excel',
    name: 'Excel',
    meta: {
      title: 'excel',
      icon: 'excel'
    },
    children: [
      {
        path: 'export-excel',
        component: 'views/excel/export-excel',
        name: 'ExportExcel',
        meta: { title: 'exportExcel' }
      },
      {
        path: 'export-selected-excel',
        component: 'views/excel/select-excel',
        name: 'SelectExcel',
        meta: { title: 'selectExcel' }
      },
      {
        path: 'export-merge-header',
        component: 'views/excel/merge-header',
        name: 'MergeHeader',
        meta: { title: 'mergeHeader' }
      },
      {
        path: 'upload-excel',
        component: 'views/excel/upload-excel',
        name: 'UploadExcel',
        meta: { title: 'uploadExcel' }
      }
    ]
  },

  {
    path: '/zip',
    component: 'layout',
    redirect: '/zip/download',
    alwaysShow: true,
    meta: { title: 'zip', icon: 'zip' },
    children: [
      {
        path: 'download',
        component: 'views/zip/index',
        name: 'ExportZip',
        meta: { title: 'exportZip' }
      }
    ]
  },

  {
    path: '/pdf',
    component: 'layout',
    redirect: '/pdf/index',
    children: [
      {
        path: 'index',
        component: 'views/pdf/index',
        name: 'PDF',
        meta: { title: 'pdf', icon: 'pdf' }
      }
    ]
  },
  {
    path: '/pdf/download',
    component: 'views/pdf/download',
    hidden: true
  },

  {
    path: '/theme',
    component: 'layout',
    redirect: 'noRedirect',
    children: [
      {
        path: 'index',
        component: 'views/theme/index',
        name: 'Theme',
        meta: { title: 'theme', icon: 'theme' }
      }
    ]
  },

  {
    path: '/clipboard',
    component: 'layout',
    redirect: 'noRedirect',
    children: [
      {
        path: 'index',
        component: 'views/clipboard/index',
        name: 'ClipboardDemo',
        meta: { title: 'clipboardDemo', icon: 'clipboard' }
      }
    ]
  },

  {
    path: '/i18n',
    component: 'layout',
    children: [
      {
        path: 'index',
        component: 'views/i18n-demo/index',
        name: 'I18n',
        meta: { title: 'i18n', icon: 'international' }
      }
    ]
  },

  {
    path: 'external-link',
    component: 'layout',
    children: [
      {
        path: 'https://www.baidu.com',
        meta: { title: 'externalLink', icon: 'link' }
      }
    ]
  }
]

const asyncRoutes = [
  {
    'id': '1276851228160032768',
    'path': '/1276851228160032768',
    'meta': {
      'title': '图标',
      'icon': 'icon',
      'breadcrumb': false,
      'noCache': false,
      'affix': false,
      'isForm': false
    },
    'component': 'layout',
    'hidden': false,
    'alwaysShow': true,
    'isBlank': false,
    'children': [
      {
        'id': '1276851692909887488',
        'path': '/icon',
        'name': '1276851692909887488',
        'meta': {
          'title': '图标',
          'icon': 'icon',
          'breadcrumb': true,
          'noCache': false,
          'affix': false,
          'isForm': false
        },
        'component': 'views/icons/index',
        'hidden': false,
        'alwaysShow': false,
        'isBlank': false,
        'topParentId': '1276851228160032768'
      }
    ]
  },
  {
    'id': '1276851896249745408',
    'path': '/1276851896249745408',
    'meta': {
      'title': '组件',
      'icon': 'component',
      'breadcrumb': false,
      'noCache': false,
      'affix': false,
      'isForm': false
    },
    'component': 'layout',
    'hidden': false,
    'alwaysShow': false,
    'isBlank': false,
    'children': [
      {
        'id': '1276852132976263168',
        'path': '/components/ecip',
        'name': '1276852132976263168',
        'meta': {
          'title': 'Ecip组件',
          'breadcrumb': true,
          'noCache': false,
          'affix': false,
          'isForm': false
        },
        'component': 'views/ecip-components-demo/demo',
        'hidden': false,
        'alwaysShow': false,
        'isBlank': false,
        'topParentId': '1276851896249745408'
      }
    ]
  },
  {
    'id': '1276864627359088640',
    'path': '/1276864627359088640',
    'meta': {
      'title': '外链',
      'icon': 'link',
      'breadcrumb': false,
      'noCache': false,
      'affix': false,
      'isForm': false
    },
    'component': 'layout',
    'hidden': false,
    'alwaysShow': true,
    'isBlank': false,
    'children': [
      {
        'id': '1276864844879888384',
        'path': 'http://www.baidu.com',
        'name': '1276864844879888384',
        'meta': {
          'title': '百度',
          'icon': 'link',
          'breadcrumb': true,
          'noCache': false,
          'affix': false,
          'isForm': false
        },
        'hidden': false,
        'alwaysShow': false,
        'isBlank': false,
        'topParentId': '1276864627359088640'
      }
    ]
  }
]

module.exports = {
  constantRoutes,
  asyncRoutes
}
