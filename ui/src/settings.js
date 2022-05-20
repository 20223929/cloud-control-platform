module.exports = {

  title: process.env.NODE_ENV === 'development' ? '山西省ETC增值服务平台' : '山西省ETC增值服务平台',

  clearVersion: '4',

  appId: 65,

  enableTenant: false,

  loginCaptcha: true,

  loginEncrypt: true,

  loginPublicKey: '-----BEGIN PUBLIC KEY-----\n' +
    'MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgHTsnwwNvd52nzAbi/Llv2fjIyKA\n' +
    'MuOFNU1YWTUZk23Wyg3tEn2344S7BXj1uenOR4Qc+LKjdSFcHu8cfd5lOH5Hq3v2\n' +
    '8ZOxU2dJZUTZRzaPMEW54URnCQSwGzBQiXqaVyNbp/NSAyKSVqxUPvoYc3hsvrMc\n' +
    'VGimK5rrlrG9Y1yhAgMBAAE=\n' +
    '-----END PUBLIC KEY-----',

  // password: { reg: /^[A-Za-z0-9]{8,20}$/, msg: '8-20位字母数字组合' },

  password: {
    reg: /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\W_!@#$%^&*`~()-+=]+$)(?![0-9\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\W_!@#$%^&*`~()-+=]{8,15}$/,
    msg: '8-20位大小写字母数字特殊字符组合'
  },

  loginTitle: '登录',

  superAdmin: false,

  cas: false,

  size: 'small',
  device: 'desktop',
  language: 'zh-CN', // en | zh | es

  sidebar: {
    opened: true,
    withoutAnimation: false
  },

  defaultTheme: '#1890FF',
  defaultLayout: 'top', // top-顶部菜单， left-没有顶部菜单
  // 导航条, 布局皮肤
  navbarLayoutType: 'primary',
  // 侧边栏, 布局皮肤
  sidebarLayoutSkin: 'bright',
  // 主入口标签页
  mainTabs: [{
    path: 'dashboard',
    component: 'views/dashboard/index',
    name: 'Dashboard',
    meta: { title: '首页', icon: 'dashboard', affix: true }}],
  mainTabsActiveName: 'home',
  /**
   * @type {boolean} true | false
   * @description Whether show the settings right-panel
   */
  showSettings: true,

  /**
   * @type {boolean} true | false
   * @description Whether need tagsView
   */
  tagsView: true,

  /**
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: true,

  /**
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: true,

  logo: {
    type: 'svg', // img
    src: 'logo',
    link: 'javascript:void(0)',
    target: '_self'
  },

  /**
   * @type {boolean} true | false
   * @description Whether support pinyin search in headerSearch
   * Bundle size minified 47.3kb,minified + gzipped 63kb
   */
  supportPinyinSearch: true,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production',

  colorList: [
    {
      key: '拂晓蓝（默认）', color: '#1890FF'
    },
    {
      key: '薄暮', color: '#F5222D', label: '1'
    },
    {
      key: '火山', color: '#FA541C', label: '2'
    },
    {
      key: '日暮', color: '#FAAD14'
    },
    {
      key: '明青', color: '#13C2C2'
    },
    {
      key: '极光绿', color: '#52C41A'
    },
    {
      key: '极客蓝', color: '#2F54EB'
    },
    {
      key: '酱紫', color: '#722ED1'
    },
    {
      key: '天空蓝', color: '#3e8df7'
    },
    {
      key: '咖啡色', color: '#9a7b71'
    },
    {
      key: '深湖蓝', color: '#07b2d3'
    },
    {
      key: '原谅绿', color: '#0cc26c'
    },
    {
      key: '古铜灰', color: '#757575'
    },
    {
      key: '珊瑚紫', color: '#6779fa'
    },
    {
      key: '橙黄', color: '#eb6607'
    },
    {
      key: '粉红', color: '#f74584'
    },
    {
      key: '青紫', color: '#9463f7'
    },
    {
      key: '橄榄绿', color: '#16b2a3'
    }
  ],

  // vue-ls options
  storageOptions: {
    namespace: 'ec__', // key prefix
    name: 'ls', // name variable Vue.[ls] or this.[$ls],
    storage: 'local' // storage name session, local, memory
  },

  ecipUrl: process.env.NODE_ENV === 'development' ? 'http://it.hgits.cn/ecip-api' : 'http://zhgly.hgits.cn/ecip-api'

}
