import Vue from 'vue'
import App from './App.vue'
import Storage from 'vue-ls'
import router from './router'
import store from './store'
import SSO from 'ecip-web/cas/sso'
import i18n from 'ecip-web/lang' // internationalization

// import Cookies from 'js-cookie'

// import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import Element from 'element-ui'
import 'ecip-web/styles/element-variables.scss'

import 'ecip-web/styles/index.scss' // global css

// import Storage from 'vue-ls'

import config from '@/settings'
Vue.use(Storage, config.storageOptions)

// import App from './App'

// import store from './store'
import INIT from './init'
store.commit('user/SET_TOKEN', Vue.ls.get(ACCESS_TOKEN))

// import router from './router'

import './icons' // icon
import 'ecip-web/permission' // permission control
import 'ecip-web/utils/error-log' // error log

import * as filters from 'ecip-web/filters' // global filters

import moment from 'moment'
import 'moment/locale/zh-cn'
// import 'font-awesome/css/font-awesome.min.css'
import { deepClone } from 'ecip-web/utils'
import * as auth from '@/utils/auth'
import elDragDialog from 'ecip-web/directive/el-drag-dialog' // base on element-ui

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
// if (process.env.NODE_ENV === 'production') {
//   const { mockXHR } = require('../mock')
//   mockXHR()
// }

Vue.use(Element, {
  size: config.size, // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})

Vue.use(elDragDialog)

// register global utility filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

import lodash from 'lodash/object'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import dictUtils from 'ecip-web/utils/dictUtils'
import validators from 'ecip-web/utils/validators'
import vxeValidators from 'ecip-web/utils/vxe-validators'
import * as utils from 'ecip-web/utils'

Vue.prototype.$lodash = lodash
Vue.prototype.$moment = moment
Vue.prototype.$deepClone = deepClone
Vue.prototype.$utils = utils
Vue.prototype.$events = new Vue()
Vue.prototype.$dictUtils = dictUtils
Vue.prototype.$http = request
Vue.prototype.$auth = auth
Vue.prototype.$validator = validators.validator
Vue.prototype.$vxeValidator = vxeValidators.vxeValidator

import request from 'ecip-web/utils/request'

// 导入组件库
import '@ecip/ecip-components/dist/EcComponents.css'
import EcComponents from '@ecip/ecip-components/dist/EcComponents.common'
// 使用组件库
Vue.use(EcComponents, { http: request })

// 导入组件库
// import EcWeb from '@ecip/ecip-web/dist/EcWeb.common'
// // 使用组件库
// Vue.use(EcWeb, { http: request })

import './plugins/table'

// import FormMaking from '@ecip/ecip-form-make/dist/EcFormMaking.common'
// import '@ecip/ecip-form-make/dist/EcFormMaking.css'
//
// Vue.use(FormMaking, { lang: 'zh-CN' })

Vue.config.productionTip = false

import VueSafeIframe from 'vue-safe-iframe'

Vue.use(VueSafeIframe)

if (config.cas) {
  // 以下代码需要单点登录时开启
  SSO.init(() => {
    main()
  })
} else {
  main()
}

function main() {
  new Vue({
    router,
    store,
    i18n,
    mounted() {
      INIT.init()
      const chooseLanguage = utils.getStorageVal('language', '')
      if (chooseLanguage) i18n.locale = JSON.parse(chooseLanguage).value
    },
    render: h => h(App)
  }).$mount('#app')
}
