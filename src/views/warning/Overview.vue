<script setup>
import MapComponent from '../../components/MapComponent.vue'
import { ref, onMounted } from 'vue'

// 模拟数据 - 预警信息
const warnings = ref([
  {
    id: 1,
    station: '长沙站',
    level: '橙色',
    time: '2026-02-10 14:30',
    content: '预计未来6小时降雨量将达50mm以上',
    status: '生效中'
  },
  {
    id: 2,
    station: '株洲站',
    level: '黄色',
    time: '2026-02-10 13:45',
    content: '预计未来12小时降雨量将达30mm以上',
    status: '生效中'
  },
  {
    id: 3,
    station: '湘潭站',
    level: '蓝色',
    time: '2026-02-10 12:20',
    content: '预计未来24小时降雨量将达20mm以上',
    status: '生效中'
  },
  {
    id: 4,
    station: '衡阳站',
    level: '蓝色',
    time: '2026-02-10 11:15',
    content: '预计未来24小时降雨量将达20mm以上',
    status: '生效中'
  },
  {
    id: 5,
    station: '邵阳站',
    level: '蓝色',
    time: '2026-02-10 10:30',
    content: '预计未来24小时降雨量将达20mm以上',
    status: '生效中'
  }
])

// 模拟数据 - 预警统计
const warningStats = ref({
  total: 5,
  blue: 3,
  yellow: 1,
  orange: 1,
  red: 0
})

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('预警概览数据加载完成')
})
</script>

<template>
  <div class="warning-overview">

    
    <!-- 预警统计卡片 -->
    <div class="stats-grid">
      <div class="stat-card total">
        <h3>总预警数</h3>
        <div class="stat-value">{{ warningStats.total }}</div>
      </div>
      <div class="stat-card blue">
        <h3>蓝色预警</h3>
        <div class="stat-value">{{ warningStats.blue }}</div>
      </div>
      <div class="stat-card yellow">
        <h3>黄色预警</h3>
        <div class="stat-value">{{ warningStats.yellow }}</div>
      </div>
      <div class="stat-card orange">
        <h3>橙色预警</h3>
        <div class="stat-value">{{ warningStats.orange }}</div>
      </div>
      <div class="stat-card red">
        <h3>红色预警</h3>
        <div class="stat-value">{{ warningStats.red }}</div>
      </div>
    </div>
    
    <!-- 主要内容区域 -->
    <div class="content-grid">
      <!-- 地图区域 -->
      <div class="map-section">
        <div class="section-header">
          <h2>预警地图</h2>
          <p>可视化展示预警分布</p>
        </div>
        <div class="map-container">
          <MapComponent />
        </div>
      </div>
      
      <!-- 预警列表区域 -->
      <div class="warning-section">
        <div class="section-header">
          <h2>预警列表</h2>
          <p>最新预警信息</p>
        </div>
        <div class="warning-list">
          <div 
            v-for="warning in warnings" 
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
            <div class="warning-footer">
              <span class="time">{{ warning.time }}</span>
              <span class="status">{{ warning.status }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.warning-overview {
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

/* 预警统计卡片 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 1rem;
}

.stat-card {
  background-color: var(--background-white);
  border-radius: 8px;
  padding: 1.25rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.stat-card h3 {
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--text-secondary);
  margin: 0 0 0.75rem 0;
}

.stat-value {
  font-size: 2rem;
  font-weight: 700;
}

.stat-card.total .stat-value {
  color: var(--text-primary);
}

.stat-card.blue .stat-value {
  color: var(--info-color);
}

.stat-card.yellow .stat-value {
  color: var(--warning-color);
}

.stat-card.orange .stat-value {
  color: var(--warning-color);
}

.stat-card.red .stat-value {
  color: var(--danger-color);
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

/* 预警列表区域 */
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
  padding: 1rem;
  background-color: var(--background-light);
  border-radius: 6px;
  margin-bottom: 0.75rem;
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

.level.blue {
  background-color: var(--info-color);
}

.level.yellow {
  background-color: var(--warning-color);
}

.level.orange {
  background-color: var(--warning-color);
}

.level.red {
  background-color: var(--danger-color);
}

.warning-content {
  font-size: 0.9rem;
  color: var(--text-secondary);
  margin-bottom: 0.5rem;
  line-height: 1.4;
}

.warning-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.time {
  font-size: 0.8rem;
  color: var(--text-light);
}

.status {
  font-size: 0.8rem;
  font-weight: 600;
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
  
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .section-header h2 {
    font-size: 1.1rem;
  }
}

@media (max-width: 480px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }
}
</style>
