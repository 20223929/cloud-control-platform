<template>
  <el-dialog
    :title="`${user.realName}-权限管理`"
    :visible.sync="visible"
    width="1400"
    class="userApp"
  >
    <el-card>
      <div slot="header" class="clearfix">
        <span>个人信息</span>
      </div>
      <el-form>
        <el-row>
          <el-col :span="8">
            <el-form-item label="头像">
              <img src="http://localhost:8899/portal/image/avatar-1253166395286355968-49681358366001.jpg">
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="真实姓名">
              <span>{{ }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户名">
              <span>{{ }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别">
              <span>{{ }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属组织">
              <span>{{ }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属机构">
              <span>{{ }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="职位">
              <span>{{ }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <el-card />
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>

import { getById } from 'ecip-web/api/vue/user/sysrUserApi'

export default {
  name: 'PermissionView',
  data() {
    return {
      user: {}
    }
  },
  method: {
    async getDataById(id) {
      this.loading = true
      const { data } = await getById(id)
      this.user = data
      this.loading = false //  关闭遮罩层
    }
  }
}
</script>

<style scoped>

</style>
