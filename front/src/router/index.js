import Vue from 'vue';
import ViewUI from 'view-design';
import Util from '../libs/util';
import VueRouter from 'vue-router';
import Cookies from 'js-cookie';
import { routers } from './router';

Vue.use(VueRouter);
const routerPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(error=> error)
}

const RouterConfig = {
    // mode: 'history',
    routes: routers
};

export const router = new VueRouter(RouterConfig);

router.beforeEach((to, from, next) => {
    ViewUI.LoadingBar.start();
    Util.title(to.meta.title);
    var name = to.name;
    if (Cookies.get('locking') == '1' && name !== 'locking') {
        next({
            replace: true,
            name: 'locking'
        });
    } else if (Cookies.get('locking') == '0' && name == 'locking') {
        next(false);
    } else {
        if (!Cookies.get('userInfo') && (name != 'login' && name != 'regist')) {
            next({
                name: 'login'
            });
        } else if (Cookies.get('userInfo') && name == 'login') {
            Util.title();
            next({
                name: 'home_index'
            });
        } else {
            Util.toDefaultPage([...routers], name, router, next);
        }
    }
});

router.afterEach((to) => {
    Util.openNewPage(router.app, to.name, to.params, to.query);
    ViewUI.LoadingBar.finish();
    window.scrollTo(0, 0);
});
