<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMenuStore } from './store/menu'
import { useAuthStore } from './store/auth'

const route = useRoute()
const router = useRouter()
const menuStore = useMenuStore()
const authStore = useAuthStore()

// 响应式侧边栏宽度
const sidebarWidth = ref('250px')
const isSidebarCollapsed = ref(false)

// 标签页相关状态
const tabs = ref([])
const activeTab = ref('')

// 切换侧边栏折叠状态
const toggleSidebar = () => {
  isSidebarCollapsed.value = !isSidebarCollapsed.value
  sidebarWidth.value = isSidebarCollapsed.value ? '60px' : '250px'
}

// 计算当前激活的菜单项
const currentMenu = computed(() => {
  const path = route.path.split('/')[1]
  return path || 'monitoring'
})

// 计算当前激活的子菜单项
const currentSubMenu = computed(() => {
  const pathParts = route.path.split('/')
  return pathParts[2] || 'overview'
})

// 计算是否应该显示菜单布局
const shouldShowMenu = computed(() => {
  // 检查是否为登录页面
  if (route.path === '/login') {
    return false
  }
  // 检查用户是否已认证
  return authStore.isAuthenticated
})

// 菜单项配置
const menuItems = [
  {
    id: 'monitoring',
    title: '综合监视',
    icon: '📊',
    path: '/monitoring',
    children: [
      { id: 'overview', title: '监视概览', path: '/monitoring/overview' },
      { id: 'hydrology', title: '水文数据', path: '/monitoring/hydrology' },
      { id: 'rainfall', title: '降雨数据', path: '/monitoring/rainfall' }
    ]
  },
  {
    id: 'forecast',
    title: '预报',
    icon: '🔮',
    path: '/forecast',
    children: [
      { id: 'rainfall', title: '降雨预报', path: '/forecast/rainfall' },
      { id: 'water', title: '水情预报', path: '/forecast/water' },
      { id: 'flood', title: '洪水预报', path: '/forecast/flood' }
    ]
  },
  {
    id: 'warning',
    title: '预警',
    icon: '⚠️',
    path: '/warning',
    children: [
      { id: 'overview', title: '预警概览', path: '/warning/overview' },
      { id: 'levels', title: '预警等级', path: '/warning/levels' },
      { id: 'history', title: '预警历史', path: '/warning/history' }
    ]
  },
  {
    id: 'simulation',
    title: '预演',
    icon: '🎬',
    path: '/simulation',
    children: [
      { id: 'flood', title: '洪水预演', path: '/simulation/flood' },
      { id: 'inundation', title: '淹没分析', path: '/simulation/inundation' },
      { id: 'risk', title: '风险评估', path: '/simulation/risk' }
    ]
  },
  {
    id: 'plan',
    title: '预案',
    icon: '📋',
    path: '/plan',
    children: [
      { id: 'overview', title: '预案概览', path: '/plan/overview' },
      { id: 'detail', title: '预案详情', path: '/plan/detail' },
      { id: 'edit', title: '预案编辑', path: '/plan/edit' }
    ]
  },
  {
    id: 'query',
    title: '查询',
    icon: '🔍',
    path: '/query',
    children: [
      { id: 'hydrology', title: '水文查询', path: '/query/hydrology' },
      { id: 'rainfall', title: '降雨查询', path: '/query/rainfall' },
      { id: 'warning', title: '预警查询', path: '/query/warning' },
      { id: 'rainfall-data', title: '降雨数据查询', path: '/query/rainfall-data' },
      { id: 'survey',    title: '调查成果' , path: '/query/summary' }
    ]
  },
  {
    id: 'data',
    title: '数据维护',
    icon: '💾',
    path: '/data',
    children: [
      { id: 'station', title: '站点管理', path: '/data/station' },
      { id: 'sensor', title: '传感器管理', path: '/data/sensor' },
      { id: 'import', title: '数据导入', path: '/data/import' }
    ]
  },
  {
    id: 'system',
    title: '系统管理',
    icon: '⚙️',
    path: '/system',
    children: [
      { id: 'user', title: '用户管理', path: '/system/user' },
      { id: 'role', title: '角色管理', path: '/system/role' },
      { id: 'config', title: '系统配置', path: '/system/config' }
    ]
  }
]

// 根据路径获取菜单和子菜单信息
const getMenuInfoByPath = (path) => {
  for (const menu of menuItems) {
    for (const submenu of menu.children) {
      if (submenu.path === path) {
        return {
          menuTitle: menu.title,
          submenuTitle: submenu.title,
          menuId: menu.id,
          submenuId: submenu.id
        }
      }
    }
  }
  return {
    menuTitle: '综合监视',
    submenuTitle: '监视概览',
    menuId: 'monitoring',
    submenuId: 'overview'
  }
}

// 添加标签页
const addTab = (path) => {
  const menuInfo = getMenuInfoByPath(path)
  const existingTab = tabs.value.find(tab => tab.path === path)
  
  if (!existingTab) {
    tabs.value.push({
      id: `tab-${Date.now()}`,
      path,
      menuTitle: menuInfo.menuTitle,
      submenuTitle: menuInfo.submenuTitle,
      fullTitle: `${menuInfo.menuTitle} - ${menuInfo.submenuTitle}`
    })
  }
  
  activeTab.value = path
}

// 切换标签页
const switchTab = (path) => {
  activeTab.value = path
  router.push(path)
}

// 关闭标签页
const closeTab = (tabId, event) => {
  event.stopPropagation()
  
  const tabIndex = tabs.value.findIndex(tab => tab.id === tabId)
  if (tabIndex === -1) return
  
  const tabToClose = tabs.value[tabIndex]
  
  // 如果关闭的是当前激活的标签页，需要切换到其他标签页
  if (tabToClose.path === activeTab.value) {
    // 尝试切换到前一个标签页
    if (tabIndex > 0) {
      switchTab(tabs.value[tabIndex - 1].path)
    } 
    // 或者后一个标签页
    else if (tabs.value.length > 1) {
      switchTab(tabs.value[tabIndex + 1].path)
    }
  }
  
  // 移除标签页
  tabs.value.splice(tabIndex, 1)
}

// 切换菜单项展开/收起
const toggleMenu = (menuId) => {
  menuStore.toggleMenu(menuId)
}

// 导航到菜单项
const navigateTo = (path, menuId, subMenuId) => {
  router.push(path)
  menuStore.setActiveMenu(menuId)
  menuStore.setActiveSubMenu(subMenuId)
  addTab(path)
}

// 监听路由变化，更新激活菜单和标签页
const updateActiveMenu = () => {
  const pathParts = route.path.split('/')
  const menuId = pathParts[1] || 'monitoring'
  const subMenuId = pathParts[2] || 'overview'
  
  menuStore.setActiveMenu(menuId)
  menuStore.setActiveSubMenu(subMenuId)
  addTab(route.path)
}

// 退出登录方法
const handleLogout = () => {
  // 清除认证状态
  authStore.logout()
  // 清空标签页
  tabs.value = []
  activeTab.value = ''
  // 重定向到登录页面
  router.push('/login')
}

// 监听路由变化
watch(
  () => route.path,
  (newPath) => {
    updateActiveMenu()
  }
)

// 生命周期钩子
onMounted(() => {
  // 初始化时添加当前页面到标签页
  addTab(route.path)
  updateActiveMenu()
  
  // 监听路由变化
  router.afterEach(() => {
    updateActiveMenu()
  })
})
</script>

<template>
  <!-- 登录页面：单独存在，不包含菜单 -->
  <div v-if="!shouldShowMenu" class="login-layout">
    <router-view v-slot="{ Component }">
      <transition name="fade" mode="out-in">
        <component :is="Component" />
      </transition>
    </router-view>
  </div>
  
  <!-- 系统主布局：包含侧边栏和顶部导航栏 -->
  <div v-else class="app-container">
    <!-- 侧边栏 -->
    <aside 
      class="sidebar" 
      :style="{ width: sidebarWidth }"
    >
      <!-- 系统标题 -->
      <div class="sidebar-header">
        <div class="system-logo">
          <span class="logo-icon">🌊</span>
          <h1 v-if="!isSidebarCollapsed" class="system-title">山洪四预系统</h1>
        </div>
        <button class="collapse-btn" @click="toggleSidebar">
          {{ isSidebarCollapsed ? '▶' : '◀' }}
        </button>
      </div>
      
      <!-- 菜单项 -->
      <nav class="sidebar-nav">
        <ul class="menu-list">
          <li 
            v-for="menu in menuItems" 
            :key="menu.id"
            class="menu-item"
            :class="{ active: menu.id === currentMenu }"
          >
            <!-- 菜单项头部 -->
            <div 
              class="menu-header"
              @click="toggleMenu(menu.id)"
            >
              <span class="menu-icon">{{ menu.icon }}</span>
              <span v-if="!isSidebarCollapsed" class="menu-title">{{ menu.title }}</span>
              <span class="menu-arrow">
                {{ menuStore.isMenuExpanded(menu.id) ? '▼' : '▶' }}
              </span>
            </div>
            
            <!-- 子菜单项 -->
            <ul 
              v-if="menuStore.isMenuExpanded(menu.id)"
              class="submenu-list"
            >
              <li 
                v-for="submenu in menu.children" 
                :key="submenu.id"
                class="submenu-item"
                :class="{ active: menu.id === currentMenu && submenu.id === currentSubMenu }"
              >
                <a 
                  href="javascript:void(0)"
                  @click="navigateTo(submenu.path, menu.id, submenu.id)"
                  class="submenu-link"
                >
                  <span v-if="!isSidebarCollapsed" class="submenu-title">{{ submenu.title }}</span>
                  <span v-else class="submenu-tooltip">{{ submenu.title }}</span>
                </a>
              </li>
            </ul>
          </li>
        </ul>
      </nav>
    </aside>
    
    <!-- 主内容区域 -->
    <main class="main-content">
      <!-- 顶部导航栏 -->
      <header class="top-nav">
        <div class="nav-left">
          <!-- 标签页导航 -->
          <div class="tabs-container">
            <div class="tabs-wrapper">
              <div 
                v-for="tab in tabs" 
                :key="tab.id"
                class="tab-item"
                :class="{ active: tab.path === activeTab }"
                @click="switchTab(tab.path)"
              >
                <span class="tab-title">{{ tab.fullTitle }}</span>
                <button 
                  class="tab-close-btn"
                  @click="closeTab(tab.id, $event)"
                  title="关闭标签页"
                >
                  ×
                </button>
              </div>
            </div>
          </div>
        </div>
        <div class="nav-right">
          <div class="user-info">
            <span class="user-name">管理员</span>
            <span class="user-avatar">👤</span>
            <button class="logout-btn" @click="handleLogout">退出</button>
          </div>
        </div>
      </header>
      
      <!-- 路由视图 -->
      <div class="content-wrapper">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* 登录页面布局 */
.login-layout {
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.app-container {
  width: 100%;
  height: 100vh;
  display: flex;
  overflow: hidden;
}

/* 侧边栏样式 */
.sidebar {
  background-color: #f8f9fa;
  color: #495057;
  height: 100%;
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: hidden;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.08);
  border-right: 1px solid #e9ecef;
  position: relative;
}

.sidebar::before {
  content: '';
  position: absolute;
  top: 0;
  right: -10px;
  width: 10px;
  height: 100%;
  background: linear-gradient(to left, rgba(0, 0, 0, 0.05), transparent);
  opacity: 0;
  transition: opacity 0.3s ease;
  pointer-events: none;
}

.sidebar:hover::before {
  opacity: 1;
}

.sidebar-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem;
  border-bottom: 1px solid #e9ecef;
  background-color: #ffffff;
  border-radius: 0 0 8px 0;
}

.system-logo {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.5rem 0;
}

.logo-icon {
  font-size: 1.6rem;
  background: linear-gradient(135deg, #007bff, #0056b3);
  border-radius: 8px;
  padding: 0.5rem;
  color: white;
  box-shadow: 0 2px 4px rgba(0, 123, 255, 0.2);
  transition: transform 0.2s ease;
}

.logo-icon:hover {
  transform: scale(1.05);
}

.system-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin: 0;
  color: #2c3e50;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  letter-spacing: -0.02em;
}

.collapse-btn {
  background: none;
  border: none;
  color: #495057;
  font-size: 1.1rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 6px;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 36px;
  height: 36px;
}

.collapse-btn:hover {
  background-color: #e3f2fd;
  color: #007bff;
  transform: scale(1.05);
}

.collapse-btn:active {
  transform: scale(0.95);
}

.sidebar-nav {
  padding: 1rem 0.5rem;
}

.menu-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.menu-item {
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.2s ease;
}

.menu-item:hover {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
}

.menu-header {
  display: flex;
  align-items: center;
  padding: 0.875rem 1rem;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border-radius: 8px;
  background-color: #ffffff;
  border: 1px solid #e9ecef;
  margin: 0 0.25rem;
  position: relative;
  overflow: hidden;
}

.menu-header::before {
  content: '';
  position: absolute;
  left: -100%;
  top: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.5s ease;
}

.menu-header:hover::before {
  left: 100%;
}

.menu-header:hover {
  background-color: #e3f2fd;
  border-color: #bbdefb;
  transform: translateX(6px);
  box-shadow: 0 3px 6px rgba(0, 123, 255, 0.15);
}

.menu-item.active .menu-header {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
  box-shadow: 0 4px 12px rgba(0, 123, 255, 0.4);
  transform: translateX(4px);
}

.menu-item.active .menu-header::before {
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
}

.menu-icon {
  font-size: 1.1rem;
  margin-right: 0.875rem;
  width: 24px;
  text-align: center;
  transition: transform 0.2s ease;
}

.menu-item:hover .menu-icon {
  transform: scale(1.1);
}

.menu-title {
  flex: 1;
  font-size: 0.95rem;
  font-weight: 500;
  letter-spacing: 0.01em;
}

.menu-arrow {
  font-size: 0.85rem;
  transition: all 0.2s ease;
  font-weight: 600;
}

.menu-item.active .menu-arrow {
  transform: rotate(180deg);
}

.submenu-list {
  list-style: none;
  padding: 0.5rem 0;
  margin: 0;
  background-color: #ffffff;
  border-radius: 0 0 8px 8px;
  border: 1px solid #e9ecef;
  border-top: none;
  margin: 0 0.25rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.submenu-item {
  margin: 0 0.5rem;
  border-radius: 6px;
  overflow: hidden;
}

.submenu-link {
  display: block;
  padding: 0.625rem 1rem 0.625rem 3rem;
  color: #495057;
  text-decoration: none;
  font-size: 0.875rem;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  border-radius: 6px;
  overflow: hidden;
}

.submenu-link::before {
  content: '';
  position: absolute;
  left: -100%;
  top: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(0, 123, 255, 0.1), transparent);
  transition: left 0.5s ease;
}

.submenu-link:hover::before {
  left: 100%;
}

.submenu-link:hover {
  background-color: #f8f9fa;
  padding-left: 3.375rem;
  color: #007bff;
  transform: translateX(4px);
}

.submenu-item.active .submenu-link {
  background-color: #e3f2fd;
  color: #007bff;
  border-left: 3px solid #007bff;
  font-weight: 500;
  box-shadow: 0 2px 4px rgba(0, 123, 255, 0.2);
}

.submenu-item.active .submenu-link::before {
  background: linear-gradient(90deg, transparent, rgba(0, 123, 255, 0.2), transparent);
}

.submenu-tooltip {
  position: absolute;
  left: 100%;
  top: 50%;
  transform: translateY(-50%);
  background-color: #2c3e50;
  color: white;
  padding: 0.375rem 0.75rem;
  border-radius: 6px;
  font-size: 0.85rem;
  white-space: nowrap;
  opacity: 0;
  visibility: hidden;
  transition: all 0.2s ease;
  margin-left: 0.75rem;
  z-index: 1000;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.submenu-item:hover .submenu-tooltip {
  opacity: 1;
  visibility: visible;
  transform: translateY(-50%) translateX(4px);
}

/* 子菜单展开动画 */
.submenu-list {
  transition: all 0.3s ease;
}

/* 由于v-if条件渲染，子菜单的动画由Vue的transition组件处理 */
/* 这里保持基础样式，动画效果通过Vue的条件渲染实现 */

/* 主内容区域样式 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

/* 顶部导航栏样式 */
.top-nav {
  background-color: var(--header-bg);
  color: var(--header-text);
  padding: 0 1.5rem;
  min-height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 100;
}

/* 标签页导航样式 */
.tabs-container {
  flex: 1;
  overflow: hidden;
  margin-right: 1rem;
  background-color: rgba(240, 106, 245, 0.1);
}

.tabs-wrapper {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  overflow-x: auto;
  scrollbar-width: none;
  -ms-overflow-style: none;
  /** 添加背景色 **/
  background-color: rgba(21, 133, 213, 0.1);
}

.tabs-wrapper::-webkit-scrollbar {
  display: none;
}

.tab-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  background-color: rgba(255, 255, 255, 0.1);
  border-radius: 4px 4px 0 0;
  cursor: pointer;
  transition: all 0.2s ease;
  white-space: nowrap;
  border: 1px solid transparent;
  border-bottom: none;
}

.tab-item:hover {
  background-color: rgba(255, 255, 255, 0.15);
}

.tab-item.active {
  background-color: var(--background-light);
  color: var(--foreground);
  border-color: rgba(0, 0, 0, 0.1);
  box-shadow: 0 2px 0 var(--background-light);
}

.tab-title {
  font-size: 0.95rem;
  font-weight: 500;
}

.tab-close-btn {
  background: none;
  border: none;
  color: inherit;
  font-size: 1.1rem;
  cursor: pointer;
  padding: 0;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: all 0.2s ease;
  line-height: 1;
}

.tab-close-btn:hover {
  background-color: rgba(0, 0, 0, 0.1);
  transform: scale(1.1);
}

.tab-item.active .tab-close-btn:hover {
  background-color: rgba(255, 77, 77, 0.1);
  color: #ff4d4f;
}

.page-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin: 0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.user-name {
  font-size: 0.9rem;
}

.user-avatar {
  font-size: 1.2rem;
}

.logout-btn {
  margin-left: 1rem;
  padding: 0.5rem 1rem;
  background-color: rgba(255, 77, 77, 0.1);
  color: #ff4d4f;
  border: 1px solid rgba(255, 77, 77, 0.3);
  border-radius: 4px;
  font-size: 0.85rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.logout-btn:hover {
  background-color: rgba(255, 77, 77, 0.2);
  border-color: #ff4d4f;
}

.logout-btn:active {
  background-color: rgba(255, 77, 77, 0.3);
}

/* 内容包装器样式 */
.content-wrapper {
  flex: 1;
  padding: 0.9rem;
  overflow-y: auto;
  background-color: var(--background-light);
}

/* 路由切换动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .sidebar {
    width: 220px !important;
  }
  
  .menu-header {
    padding: 0.75rem 0.875rem;
  }
  
  .menu-title {
    font-size: 0.9rem;
  }
}

@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    left: 0;
    top: 0;
    z-index: 1000;
    height: 100vh;
    box-shadow: 2px 0 12px rgba(0, 0, 0, 0.15);
    transform: translateX(-100%);
    transition: transform 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  }
  
  .sidebar:not(.collapsed) {
    transform: translateX(0);
  }
  
  .main-content {
    margin-left: 0;
    width: 100%;
  }
  
  .top-nav {
    padding: 0 1rem;
  }
  
  .content-wrapper {
    padding: 1rem;
  }
}

@media (max-width: 480px) {
  .sidebar {
    width: 200px !important;
  }
  
  .sidebar-header {
    padding: 0.75rem;
    border-radius: 0;
  }
  
  .system-logo {
    gap: 0.5rem;
  }
  
  .logo-icon {
    font-size: 1.4rem;
    padding: 0.4rem;
  }
  
  .system-title {
    font-size: 1.1rem;
  }
  
  .sidebar-nav {
    padding: 0.75rem 0.25rem;
  }
  
  .menu-header {
    padding: 0.75rem;
    margin: 0 0.125rem;
  }
  
  .menu-icon {
    font-size: 1rem;
    margin-right: 0.75rem;
  }
  
  .menu-title {
    font-size: 0.875rem;
  }
  
  .submenu-item {
    margin: 0 0.25rem;
  }
  
  .submenu-link {
    padding: 0.5rem 0.75rem 0.5rem 2.5rem;
    font-size: 0.8rem;
  }
  
  .submenu-link:hover {
    padding-left: 2.75rem;
  }
  
  .top-nav {
    padding: 0 0.75rem;
    min-height: 56px;
  }
  
  .user-name {
    font-size: 0.8rem;
  }
  
  .user-avatar {
    font-size: 1rem;
  }
  
  .content-wrapper {
    padding: 0.75rem;
  }
  
  /* 触摸友好设计 */
  .menu-header,
  .submenu-link,
  .collapse-btn {
    min-height: 44px;
    display: flex;
    align-items: center;
  }
  
  .collapse-btn {
    min-width: 44px;
    height: 44px;
  }
}
</style>
