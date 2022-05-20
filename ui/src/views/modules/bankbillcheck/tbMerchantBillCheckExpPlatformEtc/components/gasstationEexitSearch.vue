<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="95%"
    >
      <gassation-eexit-view :param="param" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
<!--        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="save">确定</el-button>-->
      </span>
    </el-dialog>
  </div>
</template>

<script>
import GassationEexitView from "@/views/modules/querymanage/gasstation/view"

export default {
  name: 'GassationEexitSearch',
  components: {
    GassationEexitView
  },
  data() {
    return {
      visible: false,
      loading: false, // 页面加载遮罩
      saveLoading: false, //  保存加载
      method: '',
      title: '',
      record: {},
      param: {}
    }
  },
  methods: {
    async init(searchId,nodeLevel,beginDate,endDate) {
      Object.assign(this.$data, this.$options.data())
      this.param = Object.assign({},{"searchId":searchId,"nodeLevel":nodeLevel,"timeScope":[beginDate,endDate]})
      debugger
      this.visible = true
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
