import Vue from 'vue'
import { SIDEBAR, DEVICE, SIZE, LANGUAGE } from '@/store/mutation-types'
import { superAdmin, sidebar, device, size, language } from '@/settings'

const state = {
  superAdmin,
  sidebar,
  device,
  size,
  language,

  documentClientHeight: 0,
  // 当前选中的top menu的index
  topMenuActiveIndex: '0',
  // 当前活动的tab标签页
  menuActiveName: '',
  // 侧边栏, 菜单
  leftMenuList: [],
  // 侧边栏类型名
  leftMenuCategory: '',
  // 侧边栏类型名
  leftMenuBasePath: ''
}

const mutations = {
  TOGGLE_SIDEBAR: state => {
    state.sidebar.opened = !state.sidebar.opened
    state.sidebar.withoutAnimation = false
    Vue.ls.set(SIDEBAR, state.sidebar)
  },
  SET_SIDEBAR: (state, sidebar) => {
    state.sidebar = sidebar
    Vue.ls.set(SIDEBAR, sidebar)
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
    Vue.ls.set(SIDEBAR, sidebar)
  },
  TOGGLE_DEVICE: (state, device) => {
    state.device = device
    Vue.ls.set(DEVICE, device)
  },
  SET_SIZE: (state, size) => {
    state.size = size
    Vue.ls.set(SIZE, size)
  },
  SET_LANGUAGE: (state, language) => {
    state.language = language
    Vue.ls.set(LANGUAGE, language)
  },
  SET_DOCUMENT_CLIENT_HEIGHT(state, height) {
    state.documentClientHeight = height
  },
  SET_LEFT_MENU_LIST(state, list) {
    state.leftMenuList = list
  },
  SET_MENU_ACTIVE_NAME(state, name) {
    state.menuActiveName = name
  },
  SET_LEFT_MENU_CATEGORY(state, leftMenuCategory) {
    state.leftMenuCategory = leftMenuCategory
  },
  SET_LEFT_MENU_BASE_PATH(state, leftMenuBasePath) {
    state.leftMenuBasePath = leftMenuBasePath
  },
  SET_TOP_MENU_ACTIVE_INDEX(state, topMenuActiveIndex) {
    state.topMenuActiveIndex = topMenuActiveIndex
  }
}

const actions = {
  toggleSideBar({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  toggleDevice({ commit }, device) {
    commit('TOGGLE_DEVICE', device)
  },
  setLanguage({ commit }, language) {
    commit('SET_LANGUAGE', language)
  },
  setSize({ commit }, size) {
    commit('SET_SIZE', size)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
