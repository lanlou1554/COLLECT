
module.exports  = {
  transpileDependencies: true,
  devServer: {
    proxy: {
      "^/api": {
/*        pathRewrite: {
          '^/api/': '/', // remove base path
        },*/
        //target: "http://120.77.16.147:8080/",
        target: "http://1.15.174.191:8080/",
        //target: "http://localhost:8086/",
        ws: true,
        changeOrigin: true,

      },

      "^/filetest": {
        pathRewrite: {
          '^/filetest/': '/', // remove base path
        },
        target: 'http://121.36.108.137:8080/',
        ws: true,
        changeOrigin: true,
      }

    },
    allowedHosts: '*'
  },
  configureWebpack: {
    externals: {
      '@antv/g6': 'G6',
      'echarts': 'echarts'
    }
  }
}

