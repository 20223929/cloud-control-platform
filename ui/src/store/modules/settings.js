import Vue from 'vue'
import { initStorage, getStorageVal } from 'ecip-web/utils'

import { defaultTheme, defaultLayout, showSettings, tagsView, fixedHeader, sidebarLogo, logo,
  supportPinyinSearch, title, navbarLayoutType, sidebarLayoutSkin, mainTabs, mainTabsActiveName, storageOptions, language } from '@/settings'

initStorage(storageOptions)

const state = {
  // theme: variables.theme,
  defaultTheme: getStorageVal('defaultTheme', defaultTheme),
  defaultLayout: getStorageVal('defaultLayout', defaultLayout),
  showSettings: getStorageVal('showSettings', showSettings),
  tagsView: getStorageVal('tagsView', tagsView),
  fixedHeader: getStorageVal('fixedHeader', fixedHeader),
  sidebarLogo: getStorageVal('sidebarLogo', sidebarLogo),
  logo: getStorageVal('logo', logo),
  supportPinyinSearch: getStorageVal('supportPinyinSearch', supportPinyinSearch),
  title: getStorageVal('title', title),
  navbarLayoutType: getStorageVal('navbarLayoutType', navbarLayoutType),
  sidebarLayoutSkin: getStorageVal('sidebarLayoutSkin', sidebarLayoutSkin),
  // 主入口标签页
  mainTabs: getStorageVal('mainTabs', mainTabs),
  mainTabsActiveName: getStorageVal('mainTabsActiveName', mainTabsActiveName),

  language: getStorageVal('language', language)
}

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    // eslint-disable-next-line no-prototype-builtins
    if (state.hasOwnProperty(key)) {
      state[key] = value
      Vue.ls.set(key, value)
    }
  }
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

