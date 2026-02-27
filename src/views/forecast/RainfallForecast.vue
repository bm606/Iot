<script setup>
import MapComponent from '../../components/MapComponent.vue'
import { ref, onMounted } from 'vue'

// 模拟数据 - 降雨预报
const rainfallForecast = ref([
  {
    time: '2026-02-10 15:00',
    rainfall: 15.2,
    level: '中雨'
  },
  {
    time: '2026-02-10 16:00',
    rainfall: 22.5,
    level: '大雨'
  },
  {
    time: '2026-02-10 17:00',
    rainfall: 18.8,
    level: '中雨'
  },
  {
    time: '2026-02-10 18:00',
    rainfall: 10.5,
    level: '小雨'
  },
  {
    time: '2026-02-10 19:00',
    rainfall: 5.2,
    level: '小雨'
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('降雨预报数据加载完成')
})
</script>

<template>
  <div class="rainfall-forecast">

    
    <!-- 主要内容区域 -->
    <div class="content-grid">
      <!-- 地图区域 -->
      <div class="map-section">
        <div class="section-header">
          <h2>降雨预报地图</h2>
          <p>可视化展示降雨预报分布</p>
        </div>
        <div class="map-container">
          <MapComponent />
        </div>
      </div>
      
      <!-- 预报数据区域 -->
      <div class="forecast-section">
        <div class="section-header">
          <h2>逐时降雨预报</h2>
          <p>未来5小时降雨预报</p>
        </div>
        <div class="forecast-list">
          <div 
            v-for="(item, index) in rainfallForecast" 
            :key="index"
            class="forecast-item"
          >
            <div class="forecast-time">{{ item.time }}</div>
            <div class="forecast-data">
              <div class="rainfall-value">{{ item.rainfall }} mm</div>
              <div 
                class="rainfall-level"
                :class="item.level.toLowerCase()"
              >
                {{ item.level }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.rainfall-forecast {
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
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background-color: var(--background-light);
  border-radius: 6px;
  margin-bottom: 0.75rem;
}

.forecast-time {
  font-weight: 600;
  color: var(--text-primary);
}

.forecast-data {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.rainfall-value {
  font-weight: 600;
  color: var(--primary-color);
}

.rainfall-level {
  font-size: 0.8rem;
  font-weight: 600;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  color: white;
}

.rainfall-level.小雨 {
  background-color: var(--info-color);
}

.rainfall-level.中雨 {
  background-color: var(--primary-color);
}

.rainfall-level.大雨 {
  background-color: var(--warning-color);
}

.rainfall-level.暴雨 {
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
