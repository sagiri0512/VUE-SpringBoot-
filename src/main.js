import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // 引用路由配置文
import ElementPlus from 'element-plus' //全局引入
import 'element-plus/dist/index.css'

import store from './store'; // 导入 Vuex 的 store 实例

createApp(App)
    .use(ElementPlus)
    .use(router) // 使用路由配置
    .use(store)
    .mount('#app')
    
