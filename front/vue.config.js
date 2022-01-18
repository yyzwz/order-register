const CompressionPlugin = require('compression-webpack-plugin');

module.exports = {
    devServer: {
        https: true,
        host: '0.0.0.0',
        port: 443,
        proxy: {
            '/zwz': {
                target: 'http://127.0.0.1:1688',
                ws: true
            }
        }
    },
    productionSourceMap: false,
    configureWebpack: {
        externals: {
            vue: 'Vue',
            'vue-i18n': 'VueI18n',
            axios: 'axios',
            'vue-router': 'VueRouter',
            vuex: 'Vuex',
            'view-design': 'iview',
            apexcharts: 'ApexCharts',
            'vue-apexcharts': 'VueApexCharts',
            xlsx: 'XLSX',
            dplayer: 'DPlayer',
            'vue-json-pretty': 'VueJsonPretty',
            'vue-lazyload': 'VueLazyload',
            'js-cookie': 'Cookies',
            wangEditor: 'wangEditor',
            quill: 'Quill',
            vuedraggable: 'vuedraggable',
            viewerjs: 'Viewer'
        },
        plugins: [
            new CompressionPlugin({
                test: /\.js$|\.html$|\.css/,
                threshold: 10240
            })
        ]
    }
}