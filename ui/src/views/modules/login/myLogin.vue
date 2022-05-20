<template>
  <div
    id="login"
    class="container"
  >
    <theme-picker v-show="false" />
    <div class="login-weaper animate__animated animate__bounceInDown">
      <div class="login-left" :style="`background:${defaultTheme}`">
        <div class="login-time">
          {{ time }}
        </div>
        <!--<img
          class="img"
          src="/img/logo.png"
          alt=""
        >-->
        <svg-icon class="img" icon-class="logo" />
        <p class="title">{{ title }}</p>
      </div>
      <div class="login-border">
        <div class="login-main" style="width: auto">
          <h4 class="login-title">
            {{ $t('ec.login.logIn') + title}}
          </h4>
          <el-tabs v-model="activeName" stretch>
<!--            <el-tab-pane :label="$t('ec.login.logInByPwd')" name="user">-->
              <userLogin />
              <!--<div class="login-menu">
                  <a
                    href="#"
                    @click.stop="activeName='user'"
                  >账号密码</a>
                  <a
                    href="#"
                    @click.stop="activeName='code'"
                  >手机号登录</a>
                </div>-->
<!--            </el-tab-pane>-->
<!--            <el-tab-pane :label="$t('ec.login.logInByPhone')" name="code">-->
<!--              <codeLogin />-->
<!--            </el-tab-pane>-->
          </el-tabs>
        </div>

      </div>
    </div>
  </div>
</template>
<script>
import userLogin from '@ecip/ecip-web/src/views/login/userlogin'
// import codeLogin from '@ecip/ecip-web/src/views/login/codelogin'
// import thirdLogin from './thirdlogin'
import { title } from '@/settings'
import ThemePicker from 'ecip-web/components/ThemePicker'

export default {
  name: 'MyLogin',
  components: {
    userLogin,
    ThemePicker,
    // codeLogin
  },
  props: [],
  data() {
    return {
      time: '',
      activeName: 'user',
      title: window._CONFIG['title'] || title
    }
  },
  computed: {
    defaultTheme() {
      return this.$store.state.settings.defaultTheme
    },
    curPhone() {
      return this.$store.state.user.userInfo && this.$store.state.user.userInfo.phone
    }
  },
  watch: {
    $route() {
      // const params = this.$route.query
      // this.socialForm.state = params.state
      // this.socialForm.code = params.code
      // if (!validatenull(this.socialForm.state)) {
      //   const loading = this.$loading({
      //     lock: true,
      //     text: `${
      //       this.socialForm.state === 'WX' ? '微信' : 'QQ'
      //     }登录中,请稍后。。。`,
      //     spinner: 'el-icon-loading'
      //   })
      //   setTimeout(() => {
      //     loading.close()
      //   }, 2000)
      // }
    }
  },
  created() {
    this.getTime()
    setInterval(() => {
      this.getTime()
    }, 1000)
  },
  mounted() {},
  methods: {
    getTime() {
      this.time = this.$moment().format('YYYY-MM-DD HH:mm:ss')
    }
  }
}
</script>

<style lang="scss">
//::v-deep .el-tabs__nav-wrap.is-top {
//  padding-left: 30px;
//}
@import "ecip-web/styles/login.scss";
</style>
