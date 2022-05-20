<template>
  <div class="taskPlan">
    <iframe :src="reportUrl" frameborder="0" style="width:100%;height:1000px;"/>
  </div>
</template>

<script>

export default {
  name: "ModulesLargescreenView",
  data() {
    return {
      reportUrl: "static/largescreen.html",
    }
  },
  mounted() {
  }
}
</script>

<style scoped>

</style>
