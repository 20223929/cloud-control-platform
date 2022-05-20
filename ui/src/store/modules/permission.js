import { constantRoutes } from '@/router'
import { getRouterReq } from 'ecip-web/api/routers'
import { backendMenusToRouters } from '@/utils/auth'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    // return new Promise(resolve => {
    //   let accessedRoutes
    //   if (roles.includes('admin')) {
    //     accessedRoutes = asyncRoutes || []
    //   } else {
    //     accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
    //   }
    //   commit('SET_ROUTES', accessedRoutes)
    //   resolve(accessedRoutes)
    // })
    return new Promise((resolve, reject) => {
      try {
        getRouterReq().then(res => {
          const allIframeList = []
          const accessedRoutes = backendMenusToRouters(res.data, allIframeList)
          accessedRoutes.push({ path: '*', redirect: '/404', hidden: true })
          commit('SET_ROUTES', accessedRoutes)
          sessionStorage.setItem('allMenuList', JSON.stringify(res.data || '[]'))
          sessionStorage.setItem('allIframeList', JSON.stringify(allIframeList || '[]'))
          // sessionStorage.setItem('permissions', JSON.stringify(data.permissions || '[]'))
          // sessionStorage.setItem('dictList', JSON.stringify(data.dictList || '[]'))
          resolve(accessedRoutes)
        }).catch(err => {
          sessionStorage.setItem('allMenuList', '[]')
          sessionStorage.setItem('allIframeList', '[]')
          // sessionStorage.setItem('permissions', '[]')
          // sessionStorage.setItem('dictList', '[]')
          reject(err)
        })
      } catch (error) {
        sessionStorage.setItem('allMenuList', '[]')
        sessionStorage.setItem('allIframeList', '[]')
        // sessionStorage.setItem('permissions', '[]')
        // sessionStorage.setItem('dictList', '[]')
        reject(error)
      }
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
