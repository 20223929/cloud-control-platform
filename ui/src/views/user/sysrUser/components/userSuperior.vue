<template>
  <el-dialog title="批量设置用户直接上级" :visible.sync="visible">
    <el-form>
      <el-form-item label="直接上级">
        <ec-user-modal v-model="superior" placeholder="请选择直接上级，单选" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="save" v-loading="saveLoading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { updateSuperior } from 'ecip-web/api/vue/user/sysrUserApi'

export default {
  name: "userSuperior",
  data() {
    return {
      visible: false,
      saveLoading: false,
      userIds: [],
      superior: '',
    }
  },
  methods: {
    init(userIds) {
      this.visible = true
      this.userIds = userIds
      this.superior = null
      this.saveLoading = false
    },
    async save() {
      this.saveLoading = true
      await updateSuperior(this.superior, this.userIds)
      this.saveLoading = false
      this.$notify.success('批量设置用户直接上级成功!')
      this.visible = false
    }
  }
}
</script>

<style scoped>

</style>
