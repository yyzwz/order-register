import Main from '@/views/Main.vue';

export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: '登录 - 基于Vue和SpringBoot的通用管理系统'
    },
    component: () => import('@/views/login.vue')
};

export const registRouter = {
    path: '/regist',
    name: 'regist',
    meta: {
        title: '注册 - 基于Vue和SpringBoot的通用管理系统'
    },
    component: () => import('@/views/regist.vue')
};

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: () => import('@/views/template/404.vue')
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: () => import('@/views/template/403.vue')
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: () => import('@/views/template/500.vue')
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: () => import('@/views/main-components/lockscreen/components/locking-page.vue')
};

export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    component: Main,
    children: [
        { path: 'home', title: '主页', name: 'home_index', component: () => import('@/views/home/home.vue') },
        { path: 'myHome', title: '个人中心', name: 'my_home_index', component: () => import('@/views/home/myHome.vue') },
        { path: 'password', title: '修改密码', name: 'password', component: () => import('@/views/password/password.vue') },
        { path: 'add', title: '添加', name: 'add', component: () => import('@/views/template/add.vue') },
        { path: 'edit', title: '编辑', name: 'edit', component: () => import('@/views/template/edit.vue')}
    ]
};

export const routers = [
    loginRouter,
    registRouter,
    otherRouter,
    locking,
    page500,
    page403
];
