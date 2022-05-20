import i18n from 'ecip-web/lang' // internationalization
//
// import Vue from 'vue'
// import XEUtils from 'xe-utils'
// import {
//   VXETable,
//   Table,
//   Column,
//   Header,
//   Footer,
//   Filter,
//   Edit,
//   Menu,
//   Export,
//   Keyboard,
//   Validator,
//
//   Grid,
//   Toolbar,
//   Pager,
//   Checkbox,
//   Radio,
//   Input,
//   // Textarea,
//   Button,
//   // Modal,
//   Tooltip,
//   Form,
//   Select,
//   Switch,
//   List
// } from 'vxe-table'
// // import zhCNLocat from 'vxe-table/lib/locale/lang/zh-CN'
//
// // 按需加载的方式默认是不带国际化的，需要自行导入
// VXETable.setup({
//   zIndex: 10002
//   // i18n: (key, value) => XEUtils.get(zhCNLocat, key)
// })
//
// // 先安装依赖模块
// // Vue.use(Icon)
// Vue.use(Column)
// Vue.use(Header)
// Vue.use(Footer)
// Vue.use(Filter)
// Vue.use(Edit)
// Vue.use(Menu)
// Vue.use(Export)
// Vue.use(Keyboard)
// Vue.use(Validator)
//
// Vue.use(Tooltip)
// Vue.use(Grid)
// Vue.use(Toolbar)
// Vue.use(Pager)
// Vue.use(Form)
// Vue.use(Checkbox)
// Vue.use(Radio)
// Vue.use(Switch)
// Vue.use(Input)
// Vue.use(Select)
// Vue.use(Button)
// // Vue.use(Modal)
// Vue.use(List)
//
// // 再安装核心库
// Vue.use(Table)

// 给 vue 实例挂载全局窗口对象，属性名称随意定义，例如：$XModal
// Vue.prototype.$modal = VXETable.modal

import XEUtils from 'xe-utils'
import VXETable from 'vxe-table'

VXETable.setup({
  zIndex: 10002,
  // i18n: (key, value) => XEUtils.get(zhCNLocat, key)
  i18n: (key, args) => i18n.t(key, args),
  // 可选，对参数中的列头、校验提示..等进行自动翻译（只对支持国际化的有效）
  translate(key, args) {
    // 例如，只翻译 "ec." 开头的键值
    if (key && key.indexOf('ec.') > -1) {
      return i18n.t(key, args)
    }
    return key
  }
})

// import 'vxe-table/lib/index.css'

import VXETablePluginElement from 'vxe-table-plugin-element'
// import 'vxe-table-plugin-element/dist/style.css'

import dictUtils from 'ecip-web/utils/dictUtils'

VXETable.formats.add('dict', ({ cellValue }, dictType) => {
  if (cellValue === undefined || cellValue === '') {
    return ''
  }
  return cellValue.toString().split(',').map(value => dictUtils.getDictLabel(dictType, value, '')).join()
})

VXETable.formats.add('date', ({ cellValue }, format) => {
  return XEUtils.toDateString(cellValue, format || 'yyyy-MM-dd')
})

// Vue.use(VXETable)
VXETable.use(VXETablePluginElement)

import VXETablePluginVirtualTree from 'vxe-table-plugin-virtual-tree'
import 'vxe-table-plugin-virtual-tree/dist/style.css'
VXETable.use(VXETablePluginVirtualTree)
