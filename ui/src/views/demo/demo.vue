<template>
  <div class="components-container">
    <aside>
      Ecip自定义组件
    </aside>
    <el-form :model="form" label-width="150px" class="demo-form-inline">

      <el-form-item label="日期">
        <el-col :span="12">
          <ec-date v-model="form.date" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.date }}</el-col>
        <el-col :span="24">
          <el-input v-model="form.date" type="text" />
        </el-col>
      </el-form-item>

      <el-form-item label="日期">
        <el-col :span="12">
          <ec-date-range v-model="form" begin-key="beginKey" end-key="endKey" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.beginKey }} --- {{ form.endKey }}</el-col>
        <el-col :span="10">
          <el-input v-model="form.beginKey" type="text" />
        </el-col>
        <el-col :span="10" :offset="2">
          <el-input v-model="form.endKey" type="text" />
        </el-col>
      </el-form-item>

      <el-form-item label="区域选择">
        <el-col :span="12">
          <ec-area-modal v-model="form.area.id" :multiple="params.area.multiple" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.area.id }}</el-col>
        <el-col :span="24">
          <el-switch v-model="params.area.multiple" :active-value="true" :inactive-value="false" active-text="多选" />
        </el-col>
        <el-col :span="24">
          <el-input v-model="form.area.id" type="text" />
        </el-col>
      </el-form-item>

      <el-form-item label="省市区联动选择">
        <el-col :span="12">
          <ec-city-select v-model="form.city" :cascader-props="{ multiple: params.city.multiple }" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.city }}</el-col>
        <el-col :span="24">
          <el-switch v-model="params.city.multiple" :active-value="true" :inactive-value="false" active-text="多选" />
        </el-col>
        <el-col :span="24">
          <el-input v-model="form.city" type="text" />
        </el-col>
      </el-form-item>

      <el-form-item label="组织选择">
        <el-col :span="12">
          <ec-dept-modal v-model="form.tdept.id" :multiple="params.dept.multiple" :default-props="params.dept.defaultProps" attr-name="name" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.tdept.id }}</el-col>
        <el-col :span="24">
          <el-switch v-model="params.dept.multiple" :active-value="true" :inactive-value="false" active-text="多选" />
        </el-col>
        <el-col :span="24">
          <el-input v-model="form.tdept.id" type="text" />
        </el-col>
      </el-form-item>

      <el-form-item label="树选择">
        <el-col :span="12">
          <ec-tree-modal v-model="form.treeDemo.id" url="api/v1/treedemo/treeDemo/data" :multiple="params.tree.multiple" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.treeDemo.id }}</el-col>
        <el-col :span="24">
          <el-switch v-model="params.tree.multiple" :active-value="true" :inactive-value="false" active-text="多选" />
        </el-col>
        <el-col :span="24">
          <el-input v-model="form.treeDemo.id" type="text" />
        </el-col>
      </el-form-item>

      <el-form-item label="用户选择">
        <el-col :span="12">
          <ec-user-modal v-model="form.tuser.id" :multiple="params.user.multiple" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.tuser.id }}</el-col>
        <el-col :span="24">
          <el-switch v-model="params.user.multiple" :active-value="true" :inactive-value="false" active-text="多选" />
        </el-col>
        <el-col :span="24">
          <el-input v-model="form.tuser.id" type="text" />
        </el-col>
      </el-form-item>

      <el-form-item label="grid选择">
        <el-col :span="12">
          <ec-grid-modal
            v-model="form.dataMainDemo.id"
            url="api/v1/sysrUser/data"
            search-fields="username|realName"
            search-labels="用户名|姓名"
            field-keys="username|realName|sexText"
            field-labels="用户名|姓名|性别"
            attr-name="username"
            :multiple="params.grid.multiple"
          />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.dataMainDemo.id }}</el-col>
        <el-col :span="24">
          <el-switch v-model="params.grid.multiple" :active-value="true" :inactive-value="false" active-text="多选" />
        </el-col>
        <el-col :span="24">
          <el-input v-model="form.dataMainDemo.id" type="text" />
        </el-col>
      </el-form-item>

      <!--      <el-form-item label="文件">-->
      <!--        <el-col :span="12">-->
      <!--          <ec-file-modal v-model="form.file.id" />-->
      <!--        </el-col>-->
      <!--        <el-col :span="10" :offset="2">选中值：{{ form.file.id }}</el-col>-->
      <!--      </el-form-item>-->

      <el-form-item label="下拉选择">
        <el-col :span="12">
          <ec-select v-model="form.vehType" dict-type="vehicleType" :multiple="params.select.multiple" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.vehType }}</el-col>
        <el-col :span="24">
          <el-switch v-model="params.select.multiple" :active-value="true" :inactive-value="false" active-text="多选" />
        </el-col>
        <el-col :span="24">
          <el-input v-model="form.vehType" type="text" />
        </el-col>
      </el-form-item>

      <el-form-item label="单选框">
        <el-col :span="12">
          <ec-radio-group v-model="form.authType" dict-type="authType" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.authType }}</el-col>
        <el-col :span="24">
          <el-input v-model="form.authType" type="text" />
        </el-col>
      </el-form-item>

      <el-form-item label="复选框">
        <el-col :span="12">
          <ec-checkbox-group v-model="form.tableType" dict-type="tableType" />
        </el-col>
        <el-col :span="10" :offset="2">选中值：{{ form.tableType }}</el-col>
        <el-col :span="24">
          <el-input v-model="form.tableType" type="text" />
        </el-col>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>

export default {
  name: 'EcipComponentsDemo',
  data() {
    return {
      form: {
        date: '2020-07-15',
        beginKey: '2020-07-01',
        endKey: '2020-08-01',
        tuser: {
          id: '1262647664021864448'
        },
        tdept: {
          id: '1277445869418840064'
        },
        treeDemo: {
          id: '1277445869423034402'
        },
        dataMainDemo: {
          id: '1259759641831669760'
        },
        area: {
          id: '1254679584473874432'
        },
        city: '中国/广州/天河区',
        vehType: '14',
        authType: '2',
        tableType: 'attachTable,masterTable',
        file: {}
      },
      params: {
        user: {
          multiple: false,
          size: 'medium',
          attrName: 'realName',
          params: {}
        },
        dept: {
          multiple: false,
          defaultProps: {
            children: 'children',
            label: 'name'
          }
        },
        area: {
          multiple: false
        },
        city: {
          multiple: false
        },
        tree: {
          multiple: false
        },
        select: {
          multiple: false
        },
        grid: {
          multiple: false
        }
      },
      testDate: '',
      dept: ''
    }
  },
  created() {
    console.log('EcipComponentsDemo')
  },
  methods: {
  }
}
</script>
<style scoped lang="scss">
  .demo-form-inline {
    .el-form-item {
      width: 100%;
      .el-form--inline .el-form-item__content {
        width: 80%;
      }
    }
    .el-input {
      width: 100%;
    }
  }
</style>

