import { login, logout, getInfo, validateCasLogin, validateOauthLogin, validateToken, visitStat } from 'ecip-web/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'

const state = {
  id: '',
  token: getToken(),
  name: '',
  username: '',
  tenantId: '',
  avatar: '',
  introduction: '',
  userInfo: {},
  roles: [],
  unReadMessageCount: 0,
  isRunAs: false,
  toChangePwd: false
}

const mutations = {
  SET_ID: (state, id) => {
    state.id = id
  },
  SET_TENANTID: (state, tenantId) => {
    state.tenantId = tenantId
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_USERNAME: (state, username) => {
    state.username = username
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_UN_READ_MESSAGE_COUNT: (state, unReadMessageCount) => {
    state.unReadMessageCount = unReadMessageCount
  },
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = userInfo
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_IS_RUN_AS: (state, isRunAs) => {
    state.isRunAs = isRunAs
  },
  SET_TO_CHANGE_PWD: (state, toChangePwd) => {
    state.toChangePwd = toChangePwd
  }
}

const actions = {

  /* 访问统计 */
  visitStat({ commit }, params) {
    return new Promise((resolve, reject) => {
      visitStat(params).then(response => {
        resolve(response)
      }).catch(error => {
        resolve(error)
      })
    })
  },

  /* cas登录验证*/
  validateCasLogin({ commit }, params) {
    return new Promise((resolve, reject) => {
      validateCasLogin(params).then(response => {
        console.log('----cas 登录--------', response)
        if (response.success) {
          const { data } = response
          commit('SET_TOKEN', data.token)
          setToken(data.token)
          resolve(response)
        } else {
          this.dispatch('user/toCasLogin')
          resolve(response)
        }
      }).catch(error => {
        this.dispatch('user/toCasLogin')
        reject(error)
      })
    })
  },

  /* oauth登录验证*/
  validateOauthLogin({ commit }, params) {
    return new Promise((resolve, reject) => {
      validateOauthLogin(params).then(response => {
        console.log('----oauth 登录--------', response)
        if (response.success) {
          const { data } = response
          commit('SET_TOKEN', data.token)
          setToken(data.token)
          resolve(response)
        } else {
          this.dispatch('user/toOauthLogin')
          resolve(response)
        }
      }).catch(error => {
        this.dispatch('user/toOauthLogin')
        reject(error)
      })
    })
  },

  /* 验证token */
  validateToken({ commit }, params) {
    return validateToken(params)
  },

  // user login
  login({ commit }, userInfo) {
    const { username, password, vcode, code, key, enableTenant } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password, vcode, captcha: code, key, enableTenant }).then(response => {
        const { data } = response
        setToken(data.token)
        commit('SET_TOKEN', data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const { userId, roles, realName, avatar, introduction, tenantId, username, dictList, isRunAs, toChangePwd, userInfo } = data

        // roles must be a non-empty array
        if (!roles || roles.length <= 0) {
          reject('getInfo: roles must be a non-null array!')
        }

        commit('SET_ID', userId)
        commit('SET_ROLES', roles)
        commit('SET_NAME', realName)
        commit('SET_USERNAME', username)
        commit('SET_AVATAR', avatar)
        commit('SET_INTRODUCTION', introduction)
        commit('SET_TENANTID', tenantId)
        commit('SET_IS_RUN_AS', isRunAs)
        commit('SET_USER_INFO', userInfo)
        commit('SET_TO_CHANGE_PWD', toChangePwd)
        sessionStorage.setItem('dictList', JSON.stringify(dictList || '[]'))
        resolve(data)
      }).catch(error => {
        sessionStorage.setItem('dictList', '[]')
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        if (state.isRunAs) {
          resolve()
          return
        }
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resetRouter()

        // reset visited views and cached views
        dispatch('tagsView/delAllViews', null, { root: true })
        if (window._CONFIG['cas']) {
          // this.toCasLogin()
          this.dispatch('user/toCasLogin')
        } else if (window._CONFIG['oauth']) {
          // this.toCasLogin()
          this.dispatch('user/toOauthLogin')
        } else {
          resolve()
        }
      }).catch(error => {
        reject(error)
      })
    })
  },

  toCasLogin() {
    return new Promise((resolve, reject) => {
      if (!window._CONFIG['cas']) {
        resolve()
        return
      }
      const baseUrl = process.env.NODE_ENV === 'development' ? '' : process.env.VUE_APP_BASE_API
      const service = window.location.protocol + '//' + window.location.host + baseUrl + '/?appId=' + window._CONFIG['appId']
      const serviceUrl = encodeURIComponent(service)
      window.location.href = window._CONFIG['casPrefixUrl'] + '/logout?service=' + serviceUrl
      resolve()
    })
  },

  toOauthLogin() {
    return new Promise((resolve, reject) => {
      if (!window._CONFIG['oauth']) {
        resolve()
        return
      }
      const baseUrl = process.env.NODE_ENV === 'development' ? process.env.BASE_URL : process.env.VUE_APP_BASE_API
      const service = window.location.protocol + '//' + window.location.host + baseUrl
      // const serviceUrl = encodeURIComponent(service)
      // debugger
      window.location.href = `${window._CONFIG['oauthPrefixUrl']}/app/common/logout?client_id=${window._CONFIG['oauthClientId']}&redirect=${service}`
      resolve()
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + '-token'

    commit('SET_TOKEN', token)
    setToken(token)

    const { roles } = await dispatch('getInfo')

    resetRouter()

    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
    // dynamically add accessible routes
    router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, { root: true })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
