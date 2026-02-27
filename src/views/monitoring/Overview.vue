<script setup>
import MapComponent from '../../components/MapComponent.vue'
import { ref, onMounted } from 'vue'

// 模拟数据 - 关键指标
const keyIndicators = ref([
  {
    title: '当前降雨量',
    value: '25.6',
    unit: 'mm',
    trend: '+5.2',
    trendType: 'up',
    color: 'var(--info-color)'
  },
  {
    title: '平均水位',
    value: '12.3',
    unit: 'm',
    trend: '-0.8',
    trendType: 'down',
    color: 'var(--primary-color)'
  },
  {
    title: '预警站点数',
    value: '5',
    unit: '个',
    trend: '+2',
    trendType: 'up',
    color: 'var(--warning-color)'
  },
  {
    title: '河道流量',
    value: '125',
    unit: 'm³/s',
    trend: '+15',
    trendType: 'up',
    color: 'var(--primary-light)'
  }
])

// 模拟数据 - 最新预警
const latestWarnings = ref([
  {
    id: 1,
    station: '长沙站',
    level: '橙色',
    time: '2026-02-10 14:30',
    content: '预计未来6小时降雨量将达50mm以上'
  },
  {
    id: 2,
    station: '株洲站',
    level: '黄色',
    time: '2026-02-10 13:45',
    content: '预计未来12小时降雨量将达30mm以上'
  },
  {
    id: 3,
    station: '湘潭站',
    level: '蓝色',
    time: '2026-02-10 12:20',
    content: '预计未来24小时降雨量将达20mm以上'
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('监视概览数据加载完成')
})
</script>

<template>
  <div class="monitoring-overview">

    
    <!-- 关键指标卡片 -->
    <div class="indicators-grid">
      <div 
        v-for="(indicator, index) in keyIndicators" 
        :key="index"
        class="indicator-card"
      >
        <div class="indicator-header">
          <h3>{{ indicator.title }}</h3>
          <span 
            class="trend"
            :class="indicator.trendType"
          >
            {{ indicator.trend }}
          </span>
        </div>
        <div class="indicator-value" :style="{ color: indicator.color }">
          {{ indicator.value }}<span class="unit">{{ indicator.unit }}</span>
        </div>
      </div>
    </div>
    
    <!-- 主要内容区域 -->
    <div class="content-grid">
      <!-- 地图区域 -->
      <div class="map-section">
        <div class="section-header">
          <h2>区域地图</h2>
          <p>实时展示卫星云图和预警信息</p>
        </div>
        <div class="map-container">
          <MapComponent />
        </div>
      </div>
      
      <!-- 预警信息区域 -->
      <div class="warning-section">
        <div class="section-header">
          <h2>最新预警</h2>
          <p>近期山洪灾害预警信息</p>
        </div>
        <div class="warning-list">
          <div 
            v-for="warning in latestWarnings" 
            :key="warning.id"
            class="warning-item"
          >
            <div class="warning-header">
              <span class="station">{{ warning.station }}</span>
              <span 
                class="level"
                :class="warning.level.toLowerCase()"
              >
                {{ warning.level }}预警
              </span>
            </div>
            <div class="warning-content">
              {{ warning.content }}
            </div>
            <div class="warning-time">
              {{ warning.time }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.monitoring-overview {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 页面标题 */
.page-header {
  margin-bottom: 0.5rem;
}

.page-header h1 {
  font-size: 1.8rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.25rem;
}

.page-header p {
  font-size: 1rem;
  color: var(--text-secondary);
  margin: 0;
}

/* 关键指标卡片 */
.indicators-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}

.indicator-card {
  background-color: var(--background-white);
  border-radius: 8px;
  padding: 1.25rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.indicator-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.indicator-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
}

.indicator-header h3 {
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--text-secondary);
  margin: 0;
}

.trend {
  font-size: 0.8rem;
  font-weight: 500;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
}

.trend.up {
  color: var(--danger-color);
  background-color: rgba(244, 67, 54, 0.1);
}

.trend.down {
  color: var(--success-color);
  background-color: rgba(76, 175, 80, 0.1);
}

.indicator-value {
  font-size: 2rem;
  font-weight: 700;
  margin: 0;
}

.indicator-value .unit {
  font-size: 1rem;
  font-weight: 500;
  margin-left: 0.25rem;
}

/* 主要内容区域 */
.content-grid {
  flex: 1;
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 1.5rem;
  min-height: 0;
}

/* 地图区域 */
.map-section {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.section-header {
  padding: 1rem 1.25rem;
  border-bottom: 1px solid var(--border-color);
}

.section-header h2 {
  font-size: 1.2rem;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0 0 0.25rem 0;
}

.section-header p {
  font-size: 0.85rem;
  color: var(--text-secondary);
  margin: 0;
}

.map-container {
  flex: 1;
  min-height: 400px;
}

/* 预警信息区域 */
.warning-section {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.warning-list {
  flex: 1;
  padding: 1rem;
  overflow-y: auto;
}

.warning-item {
  background-color: var(--background-light);
  border-radius: 6px;
  padding: 1rem;
  margin-bottom: 0.75rem;
  border-left: 4px solid var(--warning-color);
}

.warning-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
}

.station {
  font-weight: 600;
  color: var(--text-primary);
}

.level {
  font-size: 0.8rem;
  font-weight: 600;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  color: white;
}

.level.red {
  background-color: var(--danger-color);
}

.level.orange {
  background-color: var(--warning-color);
}

.level.yellow {
  background-color: var(--warning-color);
}

.level.blue {
  background-color: var(--info-color);
}

.warning-content {
  font-size: 0.9rem;
  color: var(--text-secondary);
  margin-bottom: 0.5rem;
  line-height: 1.4;
}

.warning-time {
  font-size: 0.8rem;
  color: var(--text-light);
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .content-grid {
    grid-template-columns: 1fr;
  }
  
  .map-container {
    min-height: 500px;
  }
}

@media (max-width: 768px) {
  .indicators-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .page-header h1 {
    font-size: 1.5rem;
  }
  
  .indicator-value {
    font-size: 1.5rem;
  }
}

@media (max-width: 480px) {
  .indicators-grid {
    grid-template-columns: 1fr;
  }
}
</style>
