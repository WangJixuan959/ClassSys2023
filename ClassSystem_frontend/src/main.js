import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import axios from "axios";
import 'element-plus/dist/index.css'
import locale from 'element-plus/lib/locale/lang/zh-cn'             //引入中文版
import 'dayjs/locale/zh-cn'                                         //导入中文版
import ElementPlus from 'element-plus';                             //引入element-plus

axios.defaults.baseURL = "http://127.0.0.1:8080"
axios.defaults.withCredentials = true

const app = createApp(App).use(ElementPlus,{locale})

app.use(createPinia())
app.use(router)

app.mount('#app')
