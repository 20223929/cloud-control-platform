<template>
  <div>
    <keep-alive>
      <router-view v-if="!noCache" />
    </keep-alive>
    <router-view v-if="noCache" />
  </div>
</template>

<script>
export default {
  name: 'ReportRouteView',
  computed: {
    noCache() {
      return this.$route.meta.noCache
    },
    cachedViews() {
      return this.$store.state.tagsView.cachedViews
    },
    key() {
      return this.$route.path
    }
  },
  // watch: {
  //   // $route() {
  //   //   // const route = this.$route.matched.find(item => item.meta && item.meta.component.endsWith('/RouteView'))
  //   //   const route = this.$route.matched.find(item => item.name === this.$options.name)
  //   //   // if (route) this.$store.dispatch('tagsView/addTempCachedView', route)
  //   //   if (route) this.$store.dispatch('tagsView/addCachedView', route)
  //   // }
  // },
  created() { // 加入缓存 来源: https://github.com/PanJiaChen/vue-element-admin/commit/9ad96525af3a26c2f4e8d4e9016f801c44df1f80
    // const route = this.$route.matched.find(item => item.meta && item.meta.component.endsWith('/RouteView'))
    const route = this.$route.matched.find(item => item.name === this.$options.name)
    // // if (route) this.$store.dispatch('tagsView/addTempCachedView', route)
    if (route) this.$store.dispatch('tagsView/addCachedView', route)
  }
}
</script>
