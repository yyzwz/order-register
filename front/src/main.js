import Vue from 'vue'
import ViewUI from 'view-design'
import 'view-design/dist/styles/iview.css'
import App from './App'
import { router } from './router/index'
import store from './store'
import Icon from 'vue-awesome/components/Icon'
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios'
import { setStore, getStore, removeStore } from '@/libs/storage'
import { format } from "date-fns";
import util from '@/libs/util'
import hasPermission from '@/libs/hasPermission'
import hasRole from '@/libs/hasRole'
import VueLazyload from 'vue-lazyload'
import VueClipboard from 'vue-clipboard2'
Vue.config.silent = true
Vue.config.productionTip = false
Vue.use(VueLazyload, {
    error: require('./assets/img-error.png'),
    loading: require('./assets/loading2.gif')
})
Vue.use(ViewUI);
Vue.component('icon', Icon);
Vue.use(VueClipboard);
Vue.use(hasPermission);
Vue.use(hasRole);
Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.postBodyRequest = postBodyRequest;
Vue.prototype.getNoAuthRequest = getNoAuthRequest;
Vue.prototype.postNoAuthRequest = postNoAuthRequest;
Vue.prototype.setStore = setStore;
Vue.prototype.getStore = getStore;
Vue.prototype.removeStore = removeStore;
Vue.prototype.format = format;

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App),
    data: {
        currentPageName: ''
    },
    mounted() {
        util.initRouter(this);
        this.currentPageName = this.$route.name;
        this.$store.commit('setOpenedList');
        this.$store.commit('initCachepage');
    }
})
