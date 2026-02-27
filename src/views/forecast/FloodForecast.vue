<script setup>
import MapComponent from '../../components/MapComponent.vue'
import { ref, onMounted } from 'vue'

// 模拟数据 - 洪水预报
const floodForecast = ref([
  {
    river: '湘江',
    section: '长沙段',
    currentFlow: 1250,
    forecastFlow: 1500,
    warningFlow: 2000,
    riskLevel: '低'
  },
  {
    river: '资江',
    section: '邵阳段',
    currentFlow: 850,
    forecastFlow: 1000,
    warningFlow: 1500,
    riskLevel: '低'
  },
  {
    river: '沅江',
    section: '常德段',
    currentFlow: 1000,
    forecastFlow: 1200,
    warningFlow: 1800,
    riskLevel: '低'
  },
  {
    river: '澧水',
    section: '张家界段',
    currentFlow: 750,
    forecastFlow: 900,
    warningFlow: 1400,
    riskLevel: '低'
  },
  {
    river: '汨罗江',
    section: '岳阳段',
    currentFlow: 650,
    forecastFlow: 850,
    warningFlow: 1200,
    riskLevel: '低'
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('洪水预报数据加载完成')
})
</script>

<template>
  <div class="flood-forecast">

    
    <!-- 主要内容区域 -->
    <div class="content-grid">
      <!-- 地图区域 -->
      <div class="map-section">
        <div class="section-header">
          <h2>洪水预报地图</h2>
          <p>可视化展示洪水预报分布</p>
        </div>
        <div class="map-container">
          <MapComponent />
        </div>
      </div>
      
      <!-- 预报数据区域 -->
      <div class="forecast-section">
        <div class="section-header">
          <h2>河道洪水预报</h2>
          <p>主要河道洪水预报</p>
        </div>
        <div class="forecast-list">
          <div 
            v-for="(item, index) in floodForecast" 
            :key="index"
            class="forecast-item"
          >
            <div class="river-info">
              <div class="river-name">{{ item.river }}</div>
              <div class="river-section">{{ item.section }}</div>
            </div>
            <div class="flow-data">
              <div class="flow-info">
                <span class="label">当前流量:</span>
                <span class="value">{{ item.currentFlow }} m³/s</span>
              </div>
              <div class="flow-info">
                <span class="label">预报流量:</span>
                <span class="value">{{ item.forecastFlow }} m³/s</span>
              </div>
              <div class="flow-info">
                <span class="label">警戒流量:</span>
                <span class="value warning">{{ item.warningFlow }} m³/s</span>
              </div>
              <div class="risk-info">
                <span class="label">风险等级:</span>
                <span 
                  class="risk-level"
                  :class="item.riskLevel.toLowerCase()"
                >
                  {{ item.riskLevel }}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.flood-forecast {
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

.river-info {
  margin-bottom: 0.75rem;
}

.river-name {
  font-weight: 600;
  color: var(--text-primary);
}

.river-section {
  font-size: 0.85rem;
  color: var(--text-secondary);
}

.flow-data {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.flow-info,
.risk-info {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.label {
  font-size: 0.85rem;
  color: var(--text-secondary);
  width: 90px;
}

.value {
  font-weight: 600;
  color: var(--primary-color);
}

.value.warning {
  color: var(--warning-color);
}

.risk-level {
  font-size: 0.8rem;
  font-weight: 600;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  color: white;
}

.risk-level.低 {
  background-color: var(--success-color);
}

.risk-level.中 {
  background-color: var(--warning-color);
}

.risk-level.高 {
  background-color: var(--danger-color);
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
