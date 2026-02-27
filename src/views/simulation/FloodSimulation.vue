<script setup>
import MapComponent from '../../components/MapComponent.vue'
import { ref, onMounted } from 'vue'

// 模拟数据 - 洪水预演参数
const simulationParams = ref({
  rainfall: 50,
  duration: 24,
  initialFlow: 1000,
  warningLevel: 15.0
})

// 模拟数据 - 预演结果
const simulationResults = ref({
  maxWaterLevel: 14.2,
  maxFlow: 1850,
  inundationArea: 25.6,
  riskLevel: '中'
})

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('洪水预演数据加载完成')
})

// 执行预演
const runSimulation = () => {
  // 模拟预演过程
  console.log('执行洪水预演')
  // 这里可以添加实际的预演逻辑
}
</script>

<template>
  <div class="flood-simulation">

    
    <!-- 主要内容区域 -->
    <div class="content-grid">
      <!-- 地图区域 -->
      <div class="map-section">
        <div class="section-header">
          <h2>洪水预演地图</h2>
          <p>可视化展示洪水预演结果</p>
        </div>
        <div class="map-container">
          <MapComponent />
        </div>
      </div>
      
      <!-- 预演控制区域 -->
      <div class="simulation-section">
        <div class="section-header">
          <h2>预演参数设置</h2>
          <p>设置洪水预演的相关参数</p>
        </div>
        
        <!-- 预演参数表单 -->
        <div class="simulation-form">
          <div class="form-group">
            <label for="rainfall">降雨量 (mm)</label>
            <input 
              type="number" 
              id="rainfall" 
              v-model.number="simulationParams.rainfall"
              min="0"
              max="200"
              step="1"
            />
          </div>
          
          <div class="form-group">
            <label for="duration">降雨持续时间 (小时)</label>
            <input 
              type="number" 
              id="duration" 
              v-model.number="simulationParams.duration"
              min="1"
              max="72"
              step="1"
            />
          </div>
          
          <div class="form-group">
            <label for="initialFlow">初始流量 (m³/s)</label>
            <input 
              type="number" 
              id="initialFlow" 
              v-model.number="simulationParams.initialFlow"
              min="0"
              max="5000"
              step="50"
            />
          </div>
          
          <div class="form-group">
            <label for="warningLevel">警戒水位 (m)</label>
            <input 
              type="number" 
              id="warningLevel" 
              v-model.number="simulationParams.warningLevel"
              min="0"
              max="30"
              step="0.1"
            />
          </div>
          
          <button class="run-simulation-btn" @click="runSimulation">
            执行预演
          </button>
        </div>
        
        <!-- 预演结果 -->
        <div class="simulation-results">
          <h3>预演结果</h3>
          <div class="result-item">
            <span class="label">最大水位:</span>
            <span class="value">{{ simulationResults.maxWaterLevel }} m</span>
          </div>
          <div class="result-item">
            <span class="label">最大流量:</span>
            <span class="value">{{ simulationResults.maxFlow }} m³/s</span>
          </div>
          <div class="result-item">
            <span class="label">淹没面积:</span>
            <span class="value">{{ simulationResults.inundationArea }} km²</span>
          </div>
          <div class="result-item">
            <span class="label">风险等级:</span>
            <span 
              class="risk-level"
              :class="simulationResults.riskLevel.toLowerCase()"
            >
              {{ simulationResults.riskLevel }}
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.flood-simulation {
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

/* 预演控制区域 */
.simulation-section {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.simulation-form {
  padding: 1rem;
  border-bottom: 1px solid var(--border-color);
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: var(--text-primary);
}

.form-group input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 0.9rem;
  transition: border-color 0.2s ease;
}

.form-group input:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 2px var(--primary-lighter);
}

.run-simulation-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: var(--primary-color);
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.run-simulation-btn:hover {
  background-color: var(--primary-dark);
}

.simulation-results {
  flex: 1;
  padding: 1rem;
  overflow-y: auto;
}

.simulation-results h3 {
  font-size: 1rem;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0 0 1rem 0;
}

.result-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.75rem;
  background-color: var(--background-light);
  border-radius: 4px;
  margin-bottom: 0.75rem;
}

.label {
  font-size: 0.85rem;
  color: var(--text-secondary);
}

.value {
  font-weight: 600;
  color: var(--primary-color);
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
