'use strict'
const path = require('path')

function resolve(dir) {
  return path.join(__dirname, '.', dir)
}

module.exports = {
  context: path.resolve(__dirname, './'),
  resolve: {
    extensions: ['.js', '.vue', '.json', '.scss'],
    alias: {
      'config': resolve('config'),
      '@': resolve('src'),
      '@/views': resolve('src/views'),
      '@/styles': resolve('src/styles'),
      '@/comp': resolve('src/components'),
      '@/core': resolve('src/core'),
      '@/utils': resolve('src/utils'),
      '@/entry': resolve('src/entry'),
      '@/router': resolve('src/router'),
      '@/store': resolve('src/store'),
      '@/layout': resolve('src/layout'),
      '@/directive': resolve('src/directive'),
      '@/api': resolve('src/api'),
      '@/assets': resolve('src/assets')
    }
  }
}
