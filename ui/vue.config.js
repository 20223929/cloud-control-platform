'use strict'
const path = require('path')
const defaultSettings = require('./src/settings.js')

const webpack = require('webpack')

const externals = {
  'vue': 'Vue',
  'vue-router': 'VueRouter',
  'vuex': 'Vuex',
  'axios': 'axios',
  'element-ui': 'ELEMENT',
  'echarts': 'echarts',
  'vxe-table': 'VXETable',
  'xe-utils': 'XEUtils',
  'vxe-table-plugin-element': 'VXETablePluginElement',
  'vxe-table-plugin-virtual-tree': 'VXETablePluginVirtualTree'
}

const cdn = {
  // 开发环境
  dev: {
    css: [
      `${process.env.VUE_APP_CDN_URL}element-ui/2.13.2/theme-chalk/index.css`,
      `${process.env.VUE_APP_CDN_URL}animate/4.1.0/animate.min.css`,
      `${process.env.VUE_APP_CDN_URL}normalize/7.0.0/normalize.css`,
      `${process.env.VUE_APP_CDN_URL}font-awesome/4.7.0/css/font-awesome.min.css`,
      `${process.env.VUE_APP_CDN_URL}vxe-table/2.9.15/index.css`,
      `${process.env.VUE_APP_CDN_URL}vxe-table-plugin-element/1.8.9/style.min.css`,
      `${process.env.VUE_APP_CDN_URL}vxe-table-plugin-virtual-tree/0.3.1/style.min.css`
    ],
    js: [
      `${process.env.VUE_APP_CDN_URL}vue/2.6.10/vue.min.js`,
      `${process.env.VUE_APP_CDN_URL}vue-router/3.0.1/vue-router.min.js`,
      `${process.env.VUE_APP_CDN_URL}vuex/3.1.1/vuex.min.js`,
      `${process.env.VUE_APP_CDN_URL}axios/0.18.1/axios.min.js`,
      `${process.env.VUE_APP_CDN_URL}element-ui/2.13.2/index.js`,
      `${process.env.VUE_APP_CDN_URL}xe-utils/2.6.5/xe-utils.min.js`,
      `${process.env.VUE_APP_CDN_URL}vxe-table/2.9.15/index.umd.min.js`,
      `${process.env.VUE_APP_CDN_URL}vxe-table-plugin-element/1.8.9/index.min.js`,
      `${process.env.VUE_APP_CDN_URL}vxe-table-plugin-virtual-tree/0.3.1/index.min.js`,
      `${process.env.VUE_APP_CDN_URL}echarts/4.2.1/echarts.min.js`
    ]
  },
  // 生产环境
  build: {
    css: [
      `${process.env.VUE_APP_CDN_URL}element-ui/2.13.2/theme-chalk/index.css`,
      `${process.env.VUE_APP_CDN_URL}animate/4.1.0/animate.min.css`,
      `${process.env.VUE_APP_CDN_URL}normalize/7.0.0/normalize.css`,
      `${process.env.VUE_APP_CDN_URL}font-awesome/4.7.0/css/font-awesome.min.css`,
      `${process.env.VUE_APP_CDN_URL}vxe-table/2.9.15/index.css`,
      `${process.env.VUE_APP_CDN_URL}vxe-table-plugin-element/1.8.9/style.min.css`,
      `${process.env.VUE_APP_CDN_URL}vxe-table-plugin-virtual-tree/0.3.1/style.min.css`
    ],
    js: [
      `${process.env.VUE_APP_CDN_URL}vue/2.6.10/vue.min.js`,
      `${process.env.VUE_APP_CDN_URL}vue-router/3.0.1/vue-router.min.js`,
      `${process.env.VUE_APP_CDN_URL}vuex/3.1.1/vuex.min.js`,
      `${process.env.VUE_APP_CDN_URL}axios/0.18.1/axios.min.js`,
      `${process.env.VUE_APP_CDN_URL}element-ui/2.13.2/index.js`,
      `${process.env.VUE_APP_CDN_URL}xe-utils/2.6.5/xe-utils.min.js`,
      `${process.env.VUE_APP_CDN_URL}vxe-table/2.9.15/index.umd.min.js`,
      `${process.env.VUE_APP_CDN_URL}vxe-table-plugin-element/1.8.9/index.min.js`,
      `${process.env.VUE_APP_CDN_URL}vxe-table-plugin-virtual-tree/0.3.1/index.min.js`,
      `${process.env.VUE_APP_CDN_URL}echarts/4.2.1/echarts.min.js`
    ]
  }
}

function resolve(dir) {
  return path.join(__dirname, dir)
}

const name = defaultSettings.title || 'vue Element Admin' // page title

// If your port is set to 80,
// use administrator privileges to execute the command line.
// For example, Mac: sudo npm run
// You can change the port by the following method:
// port = 9527 npm run dev OR npm run dev --port = 9527
const port = process.env.port || process.env.npm_config_port || 9091 // dev port

const routeProxy = {
  '/druid': {
    target: `${process.env.VUE_APP_SERVER_URL}/druid`,
    // changeOrigin: true,
    secure: 'false',
    pathRewrite: {
      '^/druid': ''
    }
  },
  '/reports': {
    target: `${process.env.VUE_APP_REPORT_URL}/reports`,
    // changeOrigin: true,
    secure: 'false',
    pathRewrite: {
      '^/reports': ''
    }
  },
  '/ureport': {
    target: `${process.env.VUE_APP_REPORT_URL}/ureport`,
    // changeOrigin: true,
    secure: 'false',
    pathRewrite: {
      '^/ureport': ''
    }
  },
  '/api/v1/flow': {
    target: `${process.env.VUE_APP_PROXY_FLOW}/api/v1/flow`,
    // changeOrigin: true,
    secure: 'false',
    pathRewrite: {
      '^/api/v1/flow': ''
    }
  },
  '/api': {
    target: process.env.PROXY_API,
    // changeOrigin: true,
    secure: 'false',
    pathRewrite: {
      '^/api': ''
    }
  },
  '/modeler': {
    target: `${process.env.VUE_APP_PROXY_FLOW}/modeler`,
    // changeOrigin: true,
    secure: 'false',
    pathRewrite: {
      '^/modeler': ''
    }
  },
  '/app': {
    target: `${process.env.VUE_APP_PROXY_FLOW}/app`,
    // changeOrigin: true,
    secure: 'false',
    pathRewrite: {
      '^/app': ''
    }
  }
  // change xxx-api/login => mock/login
  // detail: https://cli.vuejs.org/config/#devserver-proxy
  /* [process.env.VUE_APP_BASE_API]: {
    target: `http://127.0.0.1:${port}/mock`,
    changeOrigin: true,
    pathRewrite: {
      ['^' + process.env.VUE_APP_BASE_API]: ''
    }
  }*/
}

const mock = require('./mock/mock-server.js')

const config = {
  /**
   * You will need to set publicPath if you plan to deploy your site under a sub path,
   * for example GitHub Pages. If you plan to deploy your site to https://foo.github.io/bar/,
   * then publicPath should be set to "/bar/".
   * In most cases please use '/' !!!
   * Detail: https://cli.vuejs.org/config/#publicpath
   */
  publicPath: process.env.BASE_URL,
  outputDir: 'dist',
  assetsDir: 'static',
  lintOnSave: process.env.NODE_ENV === 'development',
  productionSourceMap: false,
  devServer: {
    port: port,
    open: false,
    overlay: {
      warnings: false,
      errors: true
    }
    // Mock的情况下需要注释proxy
  },
  configureWebpack: {
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    name: name,
    resolve: {
      alias: {
        '@': resolve('src'),
        'ecip-web': path.resolve(
          __dirname,
          'node_modules/@ecip/ecip-web/src'
        ),
        'ecip-bpm': path.resolve(
          __dirname,
          'node_modules/@ecip/ecip-bpm/src'
        ),
        'ecip-codegen': path.resolve(
          __dirname,
          'node_modules/@ecip/ecip-codegen/src'
        )
      }
    }
  },
  chainWebpack(config) {
    config.externals(externals)
    config.plugin('html')
      .tap(args => {
        // console.log('config', config)
        // console.log('args', args)
        if (process.env.NODE_ENV === 'development') {
          args[0].cdn = cdn.dev
        } else {
          args[0].cdn = cdn.build
        }
        console.log('args', args)
        console.log('cdn', JSON.stringify(cdn))
        return args
      })
    config
      .plugin('ignore')
      .use(
        new webpack.ContextReplacementPlugin(/moment[/\\]locale$/, /zh-cn$/)
      )
    // it can improve the speed of the first screen, it is recommended to turn on preload
    // it can improve the speed of the first screen, it is recommended to turn on preload
    config.plugin('preload').tap(() => [
      {
        rel: 'preload',
        // to ignore runtime.js
        // https://github.com/vuejs/vue-cli/blob/dev/packages/@vue/cli-service/lib/config/app.js#L171
        fileBlacklist: [/\.map$/, /hot-update\.js$/, /runtime\..*\.js$/],
        include: 'initial'
      }
    ])

    // when there are many pages, it will cause too many meaningless requests
    config.plugins.delete('prefetch')

    // set svg-sprite-loader
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()

    // set preserveWhitespace
    config.module
      .rule('vue')
      .use('vue-loader')
      .loader('vue-loader')
      .tap(options => {
        options.compilerOptions.preserveWhitespace = true
        return options
      })
      .end()

    config
      .when(process.env.NODE_ENV !== 'development',
        config => {
          config
            .plugin('ScriptExtHtmlWebpackPlugin')
            .after('html')
            .use('script-ext-html-webpack-plugin', [{
              // `runtime` must same as runtimeChunk name. default is `runtime`
              inline: /runtime\..*\.js$/
            }])
            .end()
          config
            .optimization.splitChunks({
              chunks: 'all',
              cacheGroups: {
                libs: {
                  name: 'chunk-libs',
                  test: /[\\/]node_modules[\\/]/,
                  priority: 10,
                  chunks: 'initial' // only package third parties that are initially dependent
                },
                elementUI: {
                  name: 'chunk-elementUI', // split elementUI into a single package
                  priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
                  test: /[\\/]node_modules[\\/]_?element-ui(.*)/ // in order to adapt to cnpm
                },
                echarts: {
                  name: 'chunk-echarts',
                  test: /[\\/]node_modules[\\/](vue-)?echarts[\\/]/,
                  chunks: 'all',
                  priority: 4,
                  reuseExistingChunk: true,
                  enforce: true
                },
                vexTable: {
                  name: 'chunk-vexTable', // split formMake into a single package
                  test: /[\\/]node_modules[\\/](vue-)?vxe-table[\\/]/, // in order to adapt to cnpm
                  chunks: 'all',
                  priority: 4,
                  reuseExistingChunk: true,
                  enforce: true
                },
                coreJs: {
                  name: 'chunk-coreJs', // split formMake into a single package
                  test: /[\\/]node_modules[\\/](vue-)?core-js[\\/]/, // in order to adapt to cnpm
                  chunks: 'all',
                  priority: 4,
                  reuseExistingChunk: true,
                  enforce: true
                },
                // ecipWeb: {
                //   name: 'chunk-ecipWeb', // split formMake into a single package
                //   test: /[\\/]node_modules[\\/](vue-)?@ecip[\\/]ecip-web[\\/]/, // in order to adapt to cnpm
                //   chunks: 'all',
                //   priority: 6,
                //   reuseExistingChunk: true,
                //   enforce: true
                // },
                // ecip: {
                //   name: 'chunk-ecip', // split formMake into a single package
                //   test: /node_modules[\\/]@ecip\//, // in order to adapt to cnpm
                //   chunks: 'all',
                //   priority: 1,
                //   reuseExistingChunk: true,
                //   enforce: true
                // },
                commons: {
                  name: 'chunk-commons',
                  test: resolve('src/components'), // can customize your rules
                  minChunks: 3, //  minimum common number
                  priority: 7,
                  reuseExistingChunk: true
                }
              }
            })
          // https:// webpack.js.org/configuration/optimization/#optimizationruntimechunk
          config.optimization.runtimeChunk('single')
        }
      )
  }
}

if (process.env.MOCK_DATA === 'true') {
  config.devServer.before = mock
} else {
  config.devServer.proxy = routeProxy
}

// const exportConfig = process.env.MOCK_DATA  ? Object.assign(config, {devServer: {before: mock}}) : Object.assign(config, {devServer: {proxy: routeProxy}})

// All configuration item explanations can be find in https://cli.vuejs.org/config/
module.exports = config
