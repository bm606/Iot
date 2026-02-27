<script setup>
import MapComponent from '../../components/MapComponent.vue'
import { ref, onMounted } from 'vue'

// 模拟数据 - 水情预报
const waterForecast = ref([
  {
    station: '长沙站',
    currentLevel: 12.5,
    forecastLevel: 13.2,
    trend: 'up',
    warningLevel: 15.0
  },
  {
    station: '株洲站',
    currentLevel: 10.8,
    forecastLevel: 11.0,
    trend: 'up',
    warningLevel: 13.0
  },
  {
    station: '湘潭站',
    currentLevel: 9.6,
    forecastLevel: 9.8,
    trend: 'up',
    warningLevel: 12.0
  },
  {
    station: '衡阳站',
    currentLevel: 11.2,
    forecastLevel: 12.0,
    trend: 'up',
    warningLevel: 14.0
  },
  {
    station: '邵阳站',
    currentLevel: 8.9,
    forecastLevel: 9.0,
    trend: 'up',
    warningLevel: 11.0
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('水情预报数据加载完成')
})
</script>

<template>
  <div class="water-forecast">

    
    <!-- 主要内容区域 -->
    <div class="content-grid">
      <!-- 地图区域 -->
      <div class="map-section">
        <div class="section-header">
          <h2>水情预报地图</h2>
          <p>可视化展示水位预报分布</p>
        </div>
        <div class="map-container">
          <MapComponent />
        </div>
      </div>
      
      <!-- 预报数据区域 -->
      <div class="forecast-section">
        <div class="section-header">
          <h2>站点水情预报</h2>
          <p>主要站点水位预报</p>
        </div>
        <div class="forecast-list">
          <div 
            v-for="(item, index) in waterForecast" 
            :key="index"
            class="forecast-item"
          >
            <div class="station-name">{{ item.station }}</div>
            <div class="water-data">
              <div class="level-info">
                <span class="label">当前水位:</span>
                <span class="value">{{ item.currentLevel }} m</span>
              </div>
              <div class="level-info">
                <span class="label">预报水位:</span>
                <span class="value">{{ item.forecastLevel }} m</span>
                <span 
                  class="trend-icon"
                  :class="item.trend"
                >
                  {{ item.trend === 'up' ? '↑' : '↓' }}
                </span>
              </div>
              <div class="level-info">
                <span class="label">警戒水位:</span>
                <span class="value warning">{{ item.warningLevel }} m</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.water-forecast {
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

/* 预报数据区域 */
.forecast-section {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.forecast-list {
  flex: 1;
  padding: 1rem;
  overflow-y: auto;
}

.forecast-item {
  padding: 1rem;
  background-color: var(--background-light);
  border-radius: 6px;
  margin-bottom: 0.75rem;
}

.station-name {
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.75rem;
}

.water-data {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.level-info {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.label {
  font-size: 0.85rem;
  color: var(--text-secondary);
  width: 80px;
}

.value {
  font-weight: 600;
  color: var(--primary-color);
}

.value.warning {
  color: var(--warning-color);
}

.trend-icon {
  font-weight: 600;
  margin-left: 0.5rem;
}

.trend-icon.up {
  color: var(--danger-color);
}

.trend-icon.down {
  color: var(--success-color);
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
  .page-header h1 {
    font-size: 1.5rem;
  }
  
  .section-header h2 {
    font-size: 1.1rem;
  }
}
</style>
