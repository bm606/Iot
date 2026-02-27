import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import pinia from './store'
import { useAuthStore } from './store/auth'

const app = createApp(App)

app.use(router)
app.use(pinia)

// 恢复认证状态
const authStore = useAuthStore()
authStore.restoreAuthState()

app.mount('#app')
