<template>
  <el-dialog
    :title="`${user.realName}-` + '所属商户'"
    :visible.sync="visible"
    width="700"
    class="chooseDialog"
  >
    <el-form class="form-full" ref="form" :model="formItem" :rules="rules">
      <el-row justify="center">
        <el-col span="12">
          <el-form-item label="商户级别" label-width="100px"  prop="nodeLevel">
            <el-select v-model="formItem.nodeLevel" placeholder="请选择级别" :key="formItem.nodeLevel" @change="nodeLevelChange">
              <el-option
                v-for="item in options"
                :value="item.value"
                :label="item.label"
                :key="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col span="12">
          <el-form-item label="所属商户" label-width="100px" prop="merchantIds">
            <el-select filterable v-model="formItem.merchantIds" placeholder="请选择所属商户" :key="formItem.nodeLevel" :multiple="multiple">
              <el-option
                v-for="item in merchantTreeList"
                :value="item.id"
                :label="item.name"
                :key="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">{{ $t('ec.table.cancel') }}</el-button>
      <el-button type="primary" @click="confirm">{{ $t('ec.table.confirm') }}</el-button>
    </div>
  </el-dialog>

</template>

<script>
import { save } from '@/api/modules/merchant/sysUserMerchantApi'
import { getTreeDataByNodeLevel } from '@/api/modules/merchant/tbMerchantApi'

export default {
  name: 'UserMerchantVue',
  data() {
    return {
      options: [
        {
          value: 1,
          label: '一级'
        },
        {
          value: 2,
          label: '二级'
        }
      ],
      visible: false,
      loading: false,
      checkStrictly: false,
      treeLoading: false,
      saveLoading: false,
      emptyText: this.$t('ec.table.empty'),
      user: {},
      merchantTree: [],
      merchantTreeList: [],
      multiple:false,
      formItem: {
        merchantIds: '',
        nodeLevel: '',
      },
      rules: {
        nodeLevel: [
          {required: true,message: '请选择商户级别',trigger: 'change'}
        ],
        merchantIds: [
          {required: true,message: '请选择所属商户',trigger: 'change'}
        ]
      }
    }
  },
  watch: {
    treeLoading(val) {
      this.emptyText = val ? this.$t('ec.table.loading') : this.$t('ec.table.empty')
    }
  },
  methods: {
    async init(user) {
      if (!user) {
        this.$notify.error(this.$t('ec.user.orgUserEmpty'))
        return
      }
      this.visible = true
      await this.$nextTick()
      this.$refs.form.resetFields()
      this.user = user
      if(user.nodeLevel){
        this.formItem.nodeLevel = user.nodeLevel
        await this.getMerchantTree()
        if(this.formItem.nodeLevel == 1) {
          this.multiple = true
          this.formItem.merchantIds = user.merchantIds
        }else{
          this.multiple = false
          this.formItem.merchantIds = user.merchantIds[0]
        }
      }else{
        this.merchantTreeList = ''
        this.formItem.merchantIds = ''
      }
    },
    async nodeLevelChange() {
      if(this.formItem.nodeLevel){
        if (this.formItem.nodeLevel == 1) this.multiple = true
        else this.multiple = false
        this.formItem.merchantIds = ''
        this.getMerchantTree()
      }else {
        this.formItem.merchantIds = ''
        this.merchantTreeList = ''
      }
    },
    async getMerchantTree() {
      this.treeLoading = true
      const { data } = await getTreeDataByNodeLevel(this.formItem.nodeLevel)
      this.merchantTreeList = data
      this.treeLoading = false
    },
    confirm() {
      this.$refs['form'].validate(async valid => {
        if(valid) {
          this.saveLoading = true
          if(this.formItem.nodeLevel == 2){
            const ids = [];
            ids.push(this.formItem.merchantIds)
            this.formItem.merchantIds = ids
          }
          await save({id: this.user.id, merchantIds: this.formItem.merchantIds, nodeLevel: this.formItem.nodeLevel})
          this.saveLoading = false
          this.$notify.success('保存成功')
          this.$emit('refresh')
          this.visible = false
        }
      });
    }
  }
}
</script>

<style scoped>

</style>
