import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menu', {
  state: () => ({
    // 展开的菜单项
    expandedMenus: ['monitoring', 'forecast', 'warning', 'simulation'],
    // 当前激活的菜单项
    activeMenu: 'monitoring',
    // 当前激活的子菜单项
    activeSubMenu: 'overview'
  }),
  
  actions: {
    // 切换菜单展开/收起状态
    toggleMenu(menuName) {
      const index = this.expandedMenus.indexOf(menuName)
      if (index > -1) {
        this.expandedMenus.splice(index, 1)
      } else {
        this.expandedMenus.push(menuName)
      }
    },
    
    // 设置当前激活的菜单项
    setActiveMenu(menuName) {
      this.activeMenu = menuName
    },
    
    // 设置当前激活的子菜单项
    setActiveSubMenu(subMenuName) {
      this.activeSubMenu = subMenuName
    },
    
    // 检查菜单是否展开
    isMenuExpanded(menuName) {
      return this.expandedMenus.includes(menuName)
    }
  }
})
