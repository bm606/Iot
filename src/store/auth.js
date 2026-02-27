import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    // 用户认证状态
    user: null,
    token: null,
    isAuthenticated: false
  }),
  getters: {
    currentUser: (state) => state.user,
    hasToken: (state) => !!state.token,
    isLoggedIn: (state) => state.isAuthenticated
  },
  actions: {
    // 登录方法
    login(userData) {
      this.user = {
        username: userData.username
      }
      this.token = userData.token
      this.isAuthenticated = true
      
      // 保存到本地存储
      localStorage.setItem('user', JSON.stringify(this.user))
      localStorage.setItem('token', this.token)
      localStorage.setItem('isAuthenticated', 'true')
    },
    
    // 登出方法
    logout() {
      this.user = null
      this.token = null
      this.isAuthenticated = false
      
      // 清除本地存储
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      localStorage.removeItem('isAuthenticated')
    },
    
    // 从本地存储恢复状态
    restoreAuthState() {
      const user = localStorage.getItem('user')
      const token = localStorage.getItem('token')
      const isAuthenticated = localStorage.getItem('isAuthenticated')
      
      if (user && token && isAuthenticated === 'true') {
        this.user = JSON.parse(user)
        this.token = token
        this.isAuthenticated = true
      }
    }
  }
})