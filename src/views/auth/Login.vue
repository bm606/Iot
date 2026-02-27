<template>
  <div class="login-page">
    <!-- 平台Logo与名称 -->
    <div class="platform-header">
      <div class="logo-container">
        <div class="logo-icon">🌊</div>
        <h1 class="platform-name">湖南省·四预平台</h1>
      </div>
    </div>

    <!-- 主内容区 -->
    <div class="login-container">
      <!-- 左侧登录卡片 -->
      <div class="login-card">
        <div class="card-content">
          <h2 class="login-title">用户登录</h2>
          
          <form @submit.prevent="handleLogin" class="login-form">
            <!-- 用户名输入框 -->
            <div class="form-group">
              <div class="input-wrapper">
                <span class="input-icon">👤</span>
                <input 
                  type="text" 
                  v-model="loginForm.username" 
                  placeholder="请输入用户名"
                  class="form-input"
                  required
                >
              </div>
            </div>

            <!-- 密码输入框 -->
            <div class="form-group">
              <div class="input-wrapper">
                <span class="input-icon">🔒</span>
                <input 
                  type="password" 
                  v-model="loginForm.password" 
                  placeholder="请输入密码"
                  class="form-input"
                  required
                >
              </div>
            </div>

            <!-- 登录按钮 -->
            <button type="submit" class="login-button" :disabled="isLoading">
              <span v-if="!isLoading">登录</span>
              <span v-else class="loading-spinner"></span>
            </button>

            <!-- 开发单位 -->
            <div class="developer-info">
              <span>开发单位：xxx科技有限公司</span>
            </div>
          </form>
        </div>
      </div>

      <!-- 右侧等距插画 -->
      <div class="illustration-section">
        <div class="isometric-illustration">
          <!-- 等距科技插画 -->
          <div class="illustration-content">
            <!-- 中央平台基座 -->
            <div class="platform-base">
              <div class="base-platform"></div>
              <div class="base-support"></div>
            </div>

            <!-- 多屏交互界面 -->
            <div class="screen-system">
              <div class="main-screen">
                <div class="screen-content"></div>
                <div class="screen-frame"></div>
              </div>
              <div class="side-screen">
                <div class="screen-content"></div>
                <div class="screen-frame"></div>
              </div>
              <div class="data-screen">
                <div class="screen-content"></div>
                <div class="screen-frame"></div>
              </div>
            </div>

            <!-- 操作人物 -->
            <div class="operator">
              <div class="operator-body"></div>
              <div class="operator-head"></div>
              <div class="operator-arms"></div>
            </div>

            <!-- 数据模块 -->
            <div class="data-modules">
              <div class="data-module"></div>
              <div class="data-module"></div>
              <div class="data-module"></div>
            </div>

            <!-- 智能机器人 -->
            <div class="robot">
              <div class="robot-body"></div>
              <div class="robot-head"></div>
              <div class="robot-arms"></div>
            </div>

            <!-- 连接节点 -->
            <div class="connection-nodes">
              <div class="node"></div>
              <div class="node"></div>
              <div class="node"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../store/auth'

const router = useRouter()
const store = useAuthStore()

// 登录表单数据
const loginForm = ref({
  username: '',
  password: ''
})

// 加载状态
const isLoading = ref(false)

// 登录处理
const handleLogin = async () => {
  if (!loginForm.value.username || !loginForm.value.password) {
    return
  }

  isLoading.value = true

  try {
    // 模拟登录请求
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    // 登录成功，保存用户信息
    await store.login({
      username: loginForm.value.username,
      token: 'mock-token-' + Date.now()
    })

    // 重定向到综合监视下的监视概览页面
    router.push('/monitoring/overview')
  } catch (error) {
    console.error('登录失败:', error)
    alert('登录失败，请检查用户名和密码')
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
  position: relative;
  overflow: hidden;
}

/* 背景曲线分割 */
.login-page::before {
  content: '';
  position: absolute;
  top: 0;
  right: 0;
  width: 60%;
  height: 100%;
  background: linear-gradient(120deg, rgba(24, 144, 255, 0.05) 0%, rgba(114, 46, 209, 0.05) 100%);
  border-radius: 30% 0 0 30%;
  z-index: 0;
}

/* 平台Logo与名称 */
.platform-header {
  position: relative;
  z-index: 1;
  padding: 20px 40px;
}

.logo-container {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-icon {
  font-size: 32px;
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #1890ff, #69c0ff);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.platform-name {
  margin: 0;
  font-size: 20px;
  font-weight: 700;
  color: #1890ff;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* 主内容区 */
.login-container {
  position: relative;
  z-index: 1;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 60px;
  align-items: center;
  justify-items: center;
  padding: 40px;
  min-height: calc(100vh - 100px);
}

/* 左侧登录卡片 */
.login-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  overflow: hidden;
  transform: translateY(0);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.login-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
}

.card-content {
  padding: 40px;
}

.login-title {
  margin: 0 0 30px 0;
  font-size: 24px;
  font-weight: 600;
  color: #333;
  text-align: center;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  margin-bottom: 0;
}

.input-wrapper {
  position: relative;
  background: #f5f5f5;
  border-radius: 10px;
  transition: all 0.3s ease;
}

.input-wrapper:focus-within {
  background: #e6f7ff;
  box-shadow: 0 0 0 3px rgba(24, 144, 255, 0.1);
}

.input-icon {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 16px;
  color: #999;
  z-index: 1;
}

.form-input {
  width: 100%;
  padding: 16px 16px 16px 48px;
  border: none;
  border-radius: 10px;
  font-size: 14px;
  background: transparent;
  color: #333;
  transition: all 0.3s ease;
}

.form-input:focus {
  outline: none;
}

.form-input::placeholder {
  color: #999;
}

.login-button {
  background: linear-gradient(135deg, #1890ff, #69c0ff);
  color: white;
  border: none;
  border-radius: 10px;
  padding: 16px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.login-button:hover:not(:disabled) {
  background: linear-gradient(135deg, #40a9ff, #69c0ff);
  box-shadow: 0 4px 16px rgba(24, 144, 255, 0.4);
  transform: translateY(-2px);
}

.login-button:active:not(:disabled) {
  transform: translateY(0);
}

.login-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.loading-spinner {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top-color: white;
  animation: spin 1s ease-in-out infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.developer-info {
  text-align: center;
  margin-top: 24px;
  font-size: 12px;
  color: #999;
}

/* 右侧等距插画 */
.illustration-section {
  width: 100%;
  max-width: 500px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.isometric-illustration {
  width: 100%;
  height: 400px;
  position: relative;
}

.illustration-content {
  width: 100%;
  height: 100%;
  position: relative;
  transform: perspective(1000px) rotateX(10deg) rotateY(-10deg);
}

/* 中央平台基座 */
.platform-base {
  position: absolute;
  bottom: 50px;
  left: 50%;
  transform: translateX(-50%);
  width: 200px;
  height: 120px;
}

.base-platform {
  width: 100%;
  height: 80px;
  background: linear-gradient(135deg, #f0f9ff, #e0f2fe);
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.base-support {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #93c5fd, #60a5fa);
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 4px;
}

/* 多屏交互界面 */
.screen-system {
  position: absolute;
  bottom: 130px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 20px;
}

.main-screen {
  width: 80px;
  height: 60px;
  background: linear-gradient(135deg, #1890ff, #69c0ff);
  border-radius: 8px;
  position: relative;
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.side-screen {
  width: 60px;
  height: 45px;
  background: linear-gradient(135deg, #722ed1, #b37feb);
  border-radius: 6px;
  position: relative;
  box-shadow: 0 4px 12px rgba(114, 46, 209, 0.3);
  align-self: flex-end;
  margin-bottom: 10px;
}

.data-screen {
  width: 60px;
  height: 45px;
  background: linear-gradient(135deg, #fa8c16, #ffc53d);
  border-radius: 6px;
  position: relative;
  box-shadow: 0 4px 12px rgba(250, 140, 22, 0.3);
  align-self: flex-end;
  margin-bottom: 10px;
}

.screen-content {
  width: 80%;
  height: 60%;
  background: rgba(255, 255, 255, 0.2);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 4px;
}

.screen-frame {
  width: 90%;
  height: 80%;
  border: 2px solid rgba(255, 255, 255, 0.3);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 4px;
}

/* 操作人物 */
.operator {
  position: absolute;
  bottom: 140px;
  right: 100px;
  width: 40px;
  height: 60px;
}

.operator-body {
  width: 30px;
  height: 40px;
  background: linear-gradient(135deg, #1890ff, #69c0ff);
  border-radius: 15px;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
}

.operator-head {
  width: 20px;
  height: 20px;
  background: linear-gradient(135deg, #ffd591, #ff9a9e);
  border-radius: 50%;
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
}

.operator-arms {
  width: 40px;
  height: 10px;
  background: linear-gradient(135deg, #69c0ff, #1890ff);
  border-radius: 5px;
  position: absolute;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
}

/* 数据模块 */
.data-modules {
  position: absolute;
  bottom: 80px;
  left: 100px;
  display: flex;
  gap: 15px;
}

.data-module {
  width: 30px;
  height: 30px;
  background: linear-gradient(135deg, #f0f9ff, #e0f2fe);
  border-radius: 6px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 智能机器人 */
.robot {
  position: absolute;
  bottom: 100px;
  right: 150px;
  width: 35px;
  height: 45px;
}

.robot-body {
  width: 25px;
  height: 30px;
  background: linear-gradient(135deg, #93c5fd, #60a5fa);
  border-radius: 12px;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
}

.robot-head {
  width: 15px;
  height: 15px;
  background: linear-gradient(135deg, #f0f9ff, #e0f2fe);
  border-radius: 50%;
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
}

.robot-arms {
  width: 35px;
  height: 8px;
  background: linear-gradient(135deg, #60a5fa, #3b82f6);
  border-radius: 4px;
  position: absolute;
  top: 15px;
  left: 50%;
  transform: translateX(-50%);
}

/* 连接节点 */
.connection-nodes {
  position: absolute;
  bottom: 100px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 40px;
}

.node {
  width: 8px;
  height: 8px;
  background: linear-gradient(135deg, #1890ff, #69c0ff);
  border-radius: 50%;
  box-shadow: 0 0 12px rgba(24, 144, 255, 0.6);
  animation: pulse 2s ease-in-out infinite;
}

.node:nth-child(2) {
  animation-delay: 0.5s;
}

.node:nth-child(3) {
  animation-delay: 1s;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.5);
    opacity: 0.7;
  }
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .login-container {
    gap: 40px;
  }
  
  .isometric-illustration {
    height: 350px;
  }
}

@media (max-width: 768px) {
  .login-container {
    grid-template-columns: 1fr;
    padding: 20px;
  }
  
  .illustration-section {
    display: none;
  }
  
  .login-card {
    max-width: 400px;
    margin: 0 auto;
  }
  
  .platform-header {
    padding: 16px 24px;
  }
  
  .platform-name {
    font-size: 18px;
  }
}

@media (max-width: 480px) {
  .card-content {
    padding: 30px 24px;
  }
  
  .login-title {
    font-size: 20px;
  }
  
  .form-input {
    padding: 14px 14px 14px 44px;
  }
  
  .login-button {
    padding: 14px;
  }
  
  .platform-header {
    padding: 12px 16px;
  }
  
  .logo-icon {
    width: 40px;
    height: 40px;
    font-size: 24px;
  }
  
  .platform-name {
    font-size: 16px;
  }
}
</style>