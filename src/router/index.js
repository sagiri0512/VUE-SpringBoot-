import { createRouter, createWebHashHistory } from 'vue-router';
import store from '../store'; // 引入 Vuex store
import SuperRoot from '../components/SuperRoot.vue';
import RootPage from '../components/RootPage.vue';
import RootLogin from '../components/RootLogin.vue';
import SuperRootLogin from '../components/SuperRootLogin.vue';

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/', // 设置默认路由的路径为根路径
            redirect: '/SuperRootLogin' // 将根路径重定向到 '/SuperRootLogin'
        },
        {
            path: '/SuperRootLogin',
            name: 'SuperRootLogin',
            component: SuperRootLogin,
        },
        {
            path: '/RootLogin',
            name: 'RootLogin',
            component: RootLogin,
        },
        {
            path: '/SuperRoot',
            name: 'SuperRoot',
            component: SuperRoot,
            meta: {
                requiresAuth: true,
                role: 'superRoot' // 需要超级管理员权限
            }
        },
        {
            path: '/RootPage',
            name: 'RootPage',
            component: RootPage,
            meta: {
                requiresAuth: true,
                role: 'root' // 需要管理员权限
            }
        },
    ]
});

router.beforeEach((to, from, next) => {
    const requiresAuth = to.meta.requiresAuth;
    const role = to.meta.role;

    // 如果路由需要认证
    if (requiresAuth) {
        let requiredRole = '';

        if (role === 'superRoot') {
            requiredRole = store.getters.getSuperRoot;
        } else if (role === 'root') {
            requiredRole = store.getters.getRoot;
        }

        // 检查当前用户角色是否符合要求
        if (requiredRole !== '') {
            next(); // 允许进入页面
        } else {
            if(role === 'superRoot'){
                next('/SuperRootLogin'); // 重定向到登录页或其他页面
            }else if(role === 'root'){
                next('/RootLogin');
            }
        }
    } else {
        next(); // 不需要认证，直接进入页面
    }
});

export default router;
