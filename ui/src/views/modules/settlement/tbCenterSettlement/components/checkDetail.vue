<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :close-on-click-modal="false"
      :visible.sync="visible"
      width="95%"
    >
      <bill-check-detail :param="param" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">关闭</el-button>
<!--        <el-button v-if="method !== 'view'" type="primary" :loading="saveLoading" @click="save">确定</el-button>-->
      </span>
    </el-dialog>
  </div>
</template>

<script>
import BillCheckDetail from '../../tbCenterSettlementDetail/view'

export default {
  name: 'CheckDetail',
  components: {
    BillCheckDetail
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
    async init(param) {
      Object.assign(this.$data, this.$options.data())
      debugger
      this.param = param
      this.visible = true
      BillCheckDetail.methods.getData();
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
