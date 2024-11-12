import { createApp } from 'vue'
import './style.css'
import 'element-plus/dist/index.css'
import App from './App.vue'
import ElementPlus from 'element-plus'
import Router from './routers/routes.js'
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import { createPinia } from 'pinia'
// 状态管理持久化
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
// 创建pinia对象
let pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
const app = createApp(App)
app.use(Router)
app.use(pinia)
app.use(ElementPlus, { locale: zhCn });
app.mount('#app')
