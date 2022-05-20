import Vue from 'vue'
import { getStorageVal } from 'ecip-web/utils'

import { ACCESS_TOKEN } from '@/store/mutation-types'

export function getToken() {
  const token = getStorageVal(ACCESS_TOKEN, '')
  // console.log(token)
  if (token) {
    return JSON.parse(token).value
  }
  return ''
}

export function setToken(token) {
  return Vue.ls.set(ACCESS_TOKEN, token, 7 * 24 * 60 * 60 * 1000)
}

export function removeToken() {
  // store.commit('resetStore')
  return Vue.ls.remove(ACCESS_TOKEN)
}

// / ////////////动态路由
/**
 * @description 将后端菜单树转换为路由树
 * @param {Array} menus
 * @param {Array} allIframeList
 * @returns {Array}
 */
export const backendMenusToRouters = (menus, allIframeList) => {
  const routers = []
  menus.forEach((menu) => {
    // 将后端数据转换成路由数据
    const route = backendMenuToRoute(menu)
    // 如果后端数据有下级，则递归处理下级
    if (menu.children && menu.children.length !== 0) {
      route.children = backendMenusToRouters(menu.children, allIframeList)
    }
    if (route.meta && route.meta.isIframe) {
      route.path = `/${route.id}`
      // const iframeUrl = route.meta.iframeUrl
      // if (!isExternal(iframeUrl)) {
      //   route.meta.iframeUrl = `${process.env.VUE_APP_SERVER_URL}${iframeUrl}`
      // }
      allIframeList.push(route)
      return
    }
    routers.push(route)
  })
  return routers
}

/**
 * @description 将后端菜单转换为路由
 * @param {Object} menu
 * @returns {Object}
 */
const backendMenuToRoute = (menu) => {
  // 具体内容根据自己的数据结构来定，这里需要注意的一点是
  // 原先routers写法是component: () => import('@/view/error-page/404.vue')
  // 经过json数据转换，这里会丢失，所以需要按照上面提过的做转换，下面只写了核心点，其他自行处理
  // if (menu.component.startsWith('ecip/ecip-web')) {
  //   menu.component = '@' + menu.component
  // }
  const route = Object.assign({}, menu)
  if (menu.topParentId) {
    route.meta.topParentId = menu.topParentId
  }
  // console.log(menu.component)
  if (menu.component) {
    route.meta.component = menu.component
    if (menu.component.startsWith('@ecip/ecip-web')) {
      const component = menu.component.substring(15)
      route.component = (resolve) => require(['ecip-web/' + `${component}`], resolve)
    } else if (menu.component.startsWith('@ecip/ecip-bpm')) {
      const component = menu.component.substring(15)
      route.component = (resolve) => require(['ecip-bpm/' + `${component}`], resolve)
    } /* else if (menu.component.startsWith('@ecip/ecip-datasource')) {
      const component = menu.component.substring(22)
      route.component = (resolve) => require(['ecip-datasource/' + `${component}`], resolve)
    }*/else if (menu.component.startsWith('@ecip/ecip-codegen')) {
      const component = menu.component.substring(19)
      route.component = (resolve) => require(['ecip-codegen/' + `${component}`], resolve)
    } else if (menu.component === './layout' || menu.component === 'layout') {
      // console.log('@/' + menu.component)
      route.component = (resolve) => require(['ecip-web/' + 'layout'], resolve)
    } else if (menu.component.startsWith('layout/')) {
      // console.log('@/' + menu.component)
      const component = menu.component.substring(7)
      // console.log(component)
      route.component = (resolve) => require(['ecip-web/' + `${component}`], resolve)
    } else {
      // console.log('@/' + menu.component)
      route.component = (resolve) => require(['@/' + menu.component], resolve)
    }
  } else {
    route.component = () => {}
  }

  // const route = Object.assign({}, menu)
  // if (menu.topParentId) {
  //   route.meta.topParentId = menu.topParentId
  // }
  // // console.log(component)
  // // if (component.indexOf('layout') < 0 && !component.startWith('views')) {
  // //   component = 'views/' + component
  // // }
  // // if (!component.endsWith('.vue')) {
  // //   component += '.vue'
  // // }
  // console.log(route)
  // if (menu.component) {
  //   route.component = (resolve) => require(['@/' + menu.component], resolve)
  // } else {
  //   // console.log(menu)
  //   // route.component = () => {}
  // }
  /* if (menu.meta && menu.meta.isForm) {
    const title = route.meta.title
    route.meta.title = (route) => {
      if (!route.params.id) {
        return '新增' + title
      }
      return '编辑' + title
    }
  }*/
  return route
}
