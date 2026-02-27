# Iot
大模型智能问答111

# 山洪四预系统

## 项目介绍

山洪四预系统是一个基于 Vue 3 + Vite 开发的前端应用，用于山洪灾害的监测、预警、预报和预演。系统采用现代化的前端技术栈，提供直观、专业的用户界面，支持多维度的数据展示和分析。

## 工程搭建及环境配置

### 环境要求

在开始之前，请确保您的开发环境满足以下要求：

- **Node.js**：建议版本 18.x 或更高
- **npm**：建议版本 9.x 或更高
- **操作系统**：Windows、macOS 或 Linux
- **浏览器**：Chrome、Firefox、Safari 或 Edge 的最新版本

### 检查环境

首先检查您的 Node.js 和 npm 版本：

```bash
node --version
npm --version
```

如果版本不符合要求，请先升级 Node.js 和 npm。

### 创建项目

#### 方法一：使用 Vite 创建新项目（推荐）

```bash
# 创建 Vue 3 + Vite 项目
npm create vite@latest hn_shsy_web -- --template vue

# 进入项目目录
cd hn_shsy_web
```

#### 方法二：手动创建项目结构

如果您已经有项目目录，可以按照以下步骤手动配置：

```bash
# 初始化项目
npm init -y

# 安装核心依赖
npm install vue@^3.5.25 vue-router@^4.6.4 pinia@^3.0.4

# 安装开发依赖
npm install -D vite@^7.3.1 @vitejs/plugin-vue@^6.0.2

# 安装第三方库
npm install echarts@^6.0.0 ol@^10.7.0
```

### 项目配置

#### 1. 创建 `vite.config.js`

在项目根目录创建 `vite.config.js` 文件：

```javascript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
})
```

#### 2. 创建 `index.html`

在项目根目录创建 `index.html` 文件：

```html
<!doctype html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8" />
    <link rel="icon" type="image/svg+xml" href="/vite.svg" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>山洪四预系统</title>
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.js"></script>
  </body>
</html>
```

#### 3. 创建项目目录结构

```bash
# 创建主要目录
mkdir -p src/components src/router src/store src/views

# 创建视图子目录
mkdir -p src/views/auth src/views/data src/views/forecast
mkdir -p src/views/monitoring src/views/plan src/views/query
mkdir -p src/views/simulation src/views/survey src/views/system
mkdir -p src/views/warning

# 创建资源目录
mkdir -p src/assets
```

#### 4. 创建入口文件 `src/main.js`

```javascript
import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import pinia from './store'
import { useAuthStore } from './store/auth'

const app = createApp(App)

app.use(router)
app.use(pinia)

const authStore = useAuthStore()
authStore.restoreAuthState()

app.mount('#app')
```

#### 5. 创建全局样式 `src/style.css`

创建全局样式文件，定义 CSS 变量和基础样式：

```css
:root {
  /* 主色调 - 蓝色系（代表水） */
  --primary-color: #1e88e5;
  --primary-dark: #0d47a1;
  --primary-light: #64b5f6;
  --primary-lighter: #bbdefb;
  
  /* 辅助色 - 绿色系（代表生态） */
  --secondary-color: #4caf50;
  --secondary-dark: #1b5e20;
  --secondary-light: #81c784;
  --secondary-lighter: #c8e6c9;
  
  /* 预警颜色 */
  --warning-color: #ff9800;
  --danger-color: #f44336;
  --info-color: #00bcd4;
  --success-color: #4caf50;
  
  /* 中性色 */
  --text-primary: #333333;
  --text-secondary: #666666;
  --text-light: #999999;
  --background-light: #f5f5f5;
  --background-white: #ffffff;
  --border-color: #e0e0e0;
  --sidebar-bg: #2c3e50;
  --sidebar-text: #ecf0f1;
  --header-bg: #ffffff;
  --header-text: #333333;
  
  /* 字体和间距 */
  font-family: 'Microsoft YaHei', Arial, sans-serif;
  line-height: 1.5;
  font-weight: 400;
  font-synthesis: none;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  margin: 0;
  min-width: 320px;
  min-height: 100vh;
  color: var(--text-primary);
  background-color: var(--background-light);
}

#app {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
}
```

#### 6. 创建 Pinia Store `src/store/index.js`

```javascript
import { createPinia } from 'pinia'

const pinia = createPinia()

export default pinia
```

#### 7. 创建认证 Store `src/store/auth.js`

```javascript
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isAuthenticated: false,
    user: null,
    token: null
  }),
  
  actions: {
    login(user, token) {
      this.isAuthenticated = true
      this.user = user
      this.token = token
      localStorage.setItem('auth', JSON.stringify({
        isAuthenticated: true,
        user,
        token
      }))
    },
    
    logout() {
      this.isAuthenticated = false
      this.user = null
      this.token = null
      localStorage.removeItem('auth')
    },
    
    restoreAuthState() {
      const savedAuth = localStorage.getItem('auth')
      if (savedAuth) {
        const auth = JSON.parse(savedAuth)
        this.isAuthenticated = auth.isAuthenticated
        this.user = auth.user
        this.token = auth.token
      }
    }
  }
})
```

#### 8. 创建菜单 Store `src/store/menu.js`

```javascript
import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menu', {
  state: () => ({
    activeMenu: 'monitoring',
    activeSubMenu: 'overview',
    expandedMenus: new Set(['monitoring'])
  }),
  
  actions: {
    setActiveMenu(menuId) {
      this.activeMenu = menuId
    },
    
    setActiveSubMenu(subMenuId) {
      this.activeSubMenu = subMenuId
    },
    
    toggleMenu(menuId) {
      if (this.expandedMenus.has(menuId)) {
        this.expandedMenus.delete(menuId)
      } else {
        this.expandedMenus.add(menuId)
      }
    },
    
    isMenuExpanded(menuId) {
      return this.expandedMenus.has(menuId)
    }
  }
})
```

#### 9. 创建路由配置 `src/router/index.js`

```javascript
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../store/auth'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/Login.vue'),
    meta: {
      title: '用户登录',
      requiresAuth: false
    }
  },
  {
    path: '/monitoring',
    name: 'Monitoring',
    component: () => import('../views/MonitoringView.vue'),
    meta: {
      title: '综合监视',
      icon: '📊'
    },
    children: [
      {
        path: 'overview',
        name: 'MonitoringOverview',
        component: () => import('../views/monitoring/Overview.vue'),
        meta: {
          title: '监视概览'
        }
      },
      {
        path: 'hydrology',
        name: 'HydrologyData',
        component: () => import('../views/monitoring/HydrologyData.vue'),
        meta: {
          title: '水文数据'
        }
      },
      {
        path: 'rainfall',
        name: 'RainfallData',
        component: () => import('../views/monitoring/RainfallData.vue'),
        meta: {
          title: '降雨数据'
        }
      }
    ]
  }
  // 其他路由配置...
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title || '山洪四预系统'} - 山洪四预系统`
  
  const requiresAuth = to.meta.requiresAuth !== false
  const authStore = useAuthStore()
  authStore.restoreAuthState()
  
  if (requiresAuth && !authStore.isAuthenticated) {
    next('/login')
  } else if (to.path === '/login' && authStore.isAuthenticated) {
    next('/monitoring')
  } else {
    next()
  }
})

export default router
```

#### 10. 创建根组件 `src/App.vue`

创建包含侧边栏、标签页导航和主内容区域的根组件。

#### 11. 更新 `package.json` 脚本

确保 `package.json` 中包含以下脚本：

```json
{
  "name": "hn_shsy_web",
  "private": true,
  "version": "0.0.0",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "echarts": "^6.0.0",
    "ol": "^10.7.0",
    "pinia": "^3.0.4",
    "vue": "^3.5.25",
    "vue-router": "^4.6.4"
  },
  "devDependencies": {
    "@vitejs/plugin-vue": "^6.0.2",
    "vite": "^7.3.1"
  }
}
```

### 创建 `.gitignore` 文件

在项目根目录创建 `.gitignore` 文件：

```gitignore
# Logs
logs
*.log
npm-debug.log*
yarn-debug.log*
yarn-error.log*
pnpm-debug.log*
lerna-debug.log*

node_modules
dist
dist-ssr
*.local

# Editor directories and files
.vscode/*
!.vscode/extensions.json
.idea
.DS_Store
*.suo
*.ntvs*
*.njsproj
*.sln
*.sw?
```

### 安装依赖并启动项目

```bash
# 安装项目依赖
npm install

# 启动开发服务器
npm run dev
```

开发服务器启动后，在浏览器中访问 `http://localhost:5173` 即可查看项目。

### 构建生产版本

```bash
# 构建生产版本
npm run build

# 预览生产版本
npm run preview
```

### 常见问题解决

#### 1. 端口被占用

如果默认端口 5173 被占用，可以指定其他端口：

```bash
npm run dev -- --port 3000
```

#### 2. 依赖安装失败

清除缓存后重新安装：

```bash
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

#### 3. 构建失败

检查 Node.js 版本是否符合要求，或尝试更新依赖：

```bash
npm update
```

### 开发工具推荐

- **VS Code**：推荐的代码编辑器
- **Vue DevTools**：Vue 浏览器开发者工具
- **ESLint**：代码质量检查工具
- **Prettier**：代码格式化工具

### 项目初始化完成

完成以上步骤后，您的山洪四预系统项目就已经搭建完成。接下来可以：

1. 创建各个功能模块的视图组件
2. 实现业务逻辑和数据处理
3. 添加数据可视化图表
4. 集成地图功能
5. 完善用户认证和权限管理

## 技术栈

### 核心技术
- **Vue 3.5.25** - 采用 Composition API 和 `<script setup>` 语法
- **Vue Router 4.6.4** - 路由管理
- **Pinia 3.0.4** - 状态管理
- **Vite 7.3.1** - 构建工具

### 第三方库
- **ECharts 6.0.0** - 数据可视化图表
- **OpenLayers (ol) 10.7.0** - 地图可视化

## 功能模块

### 1. 综合监视
- 监视概览
- 水文数据
- 降雨数据

### 2. 预报
- 降雨预报
- 水情预报
- 洪水预报

### 3. 预警
- 预警概览
- 预警等级
- 预警历史

### 4. 预演
- 洪水预演
- 淹没分析
- 风险评估

### 5. 预案
- 预案概览
- 预案详情
- 预案编辑

### 6. 查询
- 水文查询
- 降雨查询
- 预警查询
- 降雨数据查询
- 成果汇总

### 7. 数据维护
- 站点管理
- 传感器管理
- 数据导入

### 8. 系统管理
- 用户管理
- 角色管理
- 系统配置

## 样式标准

### 设计风格
- **浅色系设计**：使用 #f8f9fa 作为侧边栏背景，#ffffff 作为卡片背景
- **蓝色主题**：主色调为 #007bff，用于激活状态和关键操作
- **现代化布局**：采用卡片式设计，带有适当的阴影和圆角

### 响应式设计
- **桌面端**：完整的侧边栏和标签页导航
- **平板端**：自适应的布局和字体大小
- **移动端**：折叠式侧边栏，触摸友好的交互

### 动画效果
- **平滑过渡**：页面切换使用淡入淡出效果
- **悬停反馈**：菜单项和按钮有悬停动画
- **展开/收起**：子菜单和侧边栏有平滑的展开/收起动画

### 交互体验
- **标签页导航**：支持多标签页并存，可手动关闭
- **面包屑导航**：显示当前位置
- **响应式菜单**：支持折叠和展开
- **触摸友好**：移动端优化，按钮和可点击区域大小适合触摸

## 项目结构

```
src/
├── assets/          # 静态资源
├── components/      # 组件
├── router/          # 路由配置
├── store/           # 状态管理
├── views/           # 视图组件
│   ├── auth/        # 认证相关
│   ├── data/        # 数据维护
│   ├── forecast/    # 预报
│   ├── monitoring/  # 综合监视
│   ├── plan/        # 预案
│   ├── query/       # 查询
│   ├── simulation/  # 预演
│   ├── survey/      # 调查成果
│   ├── system/      # 系统管理
│   └── warning/     # 预警
├── App.vue          # 根组件
├── main.js          # 入口文件
└── style.css        # 全局样式
```

## 开发指南

### 安装依赖
```bash
npm install
```

### 启动开发服务器
```bash
npm run dev
```

### 构建生产版本
```bash
npm run build
```

### 预览生产版本
```bash
npm run preview
```

## 系统特点

1. **专业的水文监测界面**：直观展示水文和降雨数据
2. **实时预警系统**：及时发布山洪预警信息
3. **智能预报模型**：基于历史数据进行洪水预报
4. **可视化预演**：模拟洪水过程和淹没分析
5. **完善的预案管理**：支持预案的创建、编辑和查看
6. **强大的数据查询**：多维度的数据检索和分析
7. **便捷的数据维护**：站点和传感器管理
8. **安全的用户管理**：基于角色的权限控制

## 技术亮点

- **现代化前端架构**：Vue 3 Composition API + Pinia + Vue Router
- **高性能**：Vite 构建工具，快速的开发和构建体验
- **数据可视化**：集成 ECharts 实现丰富的图表展示
- **地图集成**：使用 OpenLayers 实现地理信息可视化
- **响应式设计**：适配不同屏幕尺寸的设备
- **用户友好**：直观的界面设计和流畅的交互体验

## 浏览器支持

- Chrome (最新版本)
- Firefox (最新版本)
- Safari (最新版本)
- Edge (最新版本)

## 许可证

本项目为内部使用系统，未经授权不得用于商业用途。
