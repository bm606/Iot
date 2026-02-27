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
  },
  {
    path: '/forecast',
    name: 'Forecast',
    component: () => import('../views/ForecastView.vue'),
    meta: {
      title: '预报',
      icon: '🔮'
    },
    children: [
      {
        path: 'rainfall',
        name: 'RainfallForecast',
        component: () => import('../views/forecast/RainfallForecast.vue'),
        meta: {
          title: '降雨预报'
        }
      },
      {
        path: 'water',
        name: 'WaterForecast',
        component: () => import('../views/forecast/WaterForecast.vue'),
        meta: {
          title: '水情预报'
        }
      },
      {
        path: 'flood',
        name: 'FloodForecast',
        component: () => import('../views/forecast/FloodForecast.vue'),
        meta: {
          title: '洪水预报'
        }
      }
    ]
  },
  {
    path: '/warning',
    name: 'Warning',
    component: () => import('../views/WarningView.vue'),
    meta: {
      title: '预警',
      icon: '⚠️'
    },
    children: [
      {
        path: 'overview',
        name: 'WarningOverview',
        component: () => import('../views/warning/Overview.vue'),
        meta: {
          title: '预警概览'
        }
      },
      {
        path: 'levels',
        name: 'WarningLevels',
        component: () => import('../views/warning/Levels.vue'),
        meta: {
          title: '预警等级'
        }
      },
      {
        path: 'history',
        name: 'WarningHistory',
        component: () => import('../views/warning/History.vue'),
        meta: {
          title: '预警历史'
        }
      }
    ]
  },
  {
    path: '/simulation',
    name: 'Simulation',
    component: () => import('../views/SimulationView.vue'),
    meta: {
      title: '预演',
      icon: '🎬'
    },
    children: [
      {
        path: 'flood',
        name: 'FloodSimulation',
        component: () => import('../views/simulation/FloodSimulation.vue'),
        meta: {
          title: '洪水预演'
        }
      },
      {
        path: 'inundation',
        name: 'InundationSimulation',
        component: () => import('../views/simulation/InundationSimulation.vue'),
        meta: {
          title: '淹没分析'
        }
      },
      {
        path: 'risk',
        name: 'RiskAssessment',
        component: () => import('../views/simulation/RiskAssessment.vue'),
        meta: {
          title: '风险评估'
        }
      }
    ]
  },
  {
    path: '/plan',
    name: 'Plan',
    component: () => import('../views/PlanView.vue'),
    meta: {
      title: '预案',
      icon: '📋'
    },
    children: [
      {
        path: 'overview',
        name: 'PlanOverview',
        component: () => import('../views/plan/Overview.vue'),
        meta: {
          title: '预案概览'
        }
      },
      {
        path: 'detail',
        name: 'PlanDetail',
        component: () => import('../views/plan/Detail.vue'),
        meta: {
          title: '预案详情'
        }
      },
      {
        path: 'edit',
        name: 'PlanEdit',
        component: () => import('../views/plan/Edit.vue'),
        meta: {
          title: '预案编辑'
        }
      }
    ]
  },
  {
    path: '/query',
    name: 'Query',
    component: () => import('../views/QueryView.vue'),
    meta: {
      title: '查询',
      icon: '🔍'
    },
    children: [
      {
        path: 'hydrology',
        name: 'QueryHydrology',
        component: () => import('../views/query/HydrologyQuery.vue'),
        meta: {
          title: '水文查询'
        }
      },
      {
        path: 'rainfall',
        name: 'QueryRainfall',
        component: () => import('../views/query/RainfallQuery.vue'),
        meta: {
          title: '降雨查询'
        }
      },
      {
        path: 'warning',
        name: 'QueryWarning',
        component: () => import('../views/query/WarningQuery.vue'),
        meta: {
          title: '预警查询'
        }
      },
      {
        path: 'rainfall-data',
        name: 'QueryRainfallData',
        component: () => import('../views/query/RainfallDataQuery.vue'),
        meta: {
          title: '降雨数据查询'
        }
      },
      {
        path: 'summary',
        name: 'SurveySummary',
        component: () => import('../views/survey/SurveySummary.vue'),
        meta: {
          title: '成果汇总'
        }        
      }
    ]
  },
  {
    path: '/data',
    name: 'Data',
    component: () => import('../views/DataView.vue'),
    meta: {
      title: '数据维护',
      icon: '💾'
    },
    children: [
      {
        path: 'station',
        name: 'StationManagement',
        component: () => import('../views/data/StationManagement.vue'),
        meta: {
          title: '站点管理'
        }
      },
      {
        path: 'sensor',
        name: 'SensorManagement',
        component: () => import('../views/data/SensorManagement.vue'),
        meta: {
          title: '传感器管理'
        }
      },
      {
        path: 'import',
        name: 'DataImport',
        component: () => import('../views/data/DataImport.vue'),
        meta: {
          title: '数据导入'
        }
      }
    ]
  },
  {
    path: '/system',
    name: 'System',
    component: () => import('../views/SystemView.vue'),
    meta: {
      title: '系统管理',
      icon: '⚙️'
    },
    children: [
      {
        path: 'user',
        name: 'UserManagement',
        component: () => import('../views/system/UserManagement.vue'),
        meta: {
          title: '用户管理'
        }
      },
      {
        path: 'role',
        name: 'RoleManagement',
        component: () => import('../views/system/RoleManagement.vue'),
        meta: {
          title: '角色管理'
        }
      },
      {
        path: 'config',
        name: 'SystemConfig',
        component: () => import('../views/system/Config.vue'),
        meta: {
          title: '系统配置'
        }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  // 设置页面标题
  document.title = `${to.meta.title || '山洪四预系统'} - 山洪四预系统`
  
  // 检查是否需要认证
  const requiresAuth = to.meta.requiresAuth !== false
  
  // 创建临时的auth store实例来检查认证状态
  const authStore = useAuthStore()
  
  // 恢复认证状态
  authStore.restoreAuthState()
  
  if (requiresAuth && !authStore.isAuthenticated) {
    // 未登录，重定向到登录页面
    next('/login')
  } else if (to.path === '/login' && authStore.isAuthenticated) {
    // 已登录，重定向到系统首页
    next('/monitoring')
  } else {
    // 正常访问
    next()
  }
})

export default router
