<template>
  <div class="simulation-container">
    <h2>淹没分析</h2>
    <div class="simulation-content">
      <div class="control-panel">
        <h3>分析参数</h3>
        <div class="form-group">
          <label>区域选择</label>
          <select v-model="selectedArea">
            <option value="all">全部区域</option>
            <option value="north">北部区域</option>
            <option value="south">南部区域</option>
            <option value="east">东部区域</option>
            <option value="west">西部区域</option>
          </select>
        </div>
        <div class="form-group">
          <label>降雨强度 (mm/h)</label>
          <input type="range" v-model.number="rainfallIntensity" min="10" max="200" step="10">
          <span>{{ rainfallIntensity }} mm/h</span>
        </div>
        <div class="form-group">
          <label>持续时间 (h)</label>
          <input type="number" v-model.number="duration" min="1" max="72" step="1">
        </div>
        <div class="form-group">
          <label>地形系数</label>
          <input type="range" v-model.number="terrainFactor" min="0.1" max="2" step="0.1">
          <span>{{ terrainFactor.toFixed(1) }}</span>
        </div>
        <button class="btn btn-primary" @click="startSimulation">开始分析</button>
      </div>
      <div class="result-panel">
        <h3>分析结果</h3>
        <div v-if="simulationResult" class="result-content">
          <div class="result-item">
            <label>淹没面积:</label>
            <span>{{ simulationResult.area }} 平方公里</span>
          </div>
          <div class="result-item">
            <label>最大淹没深度:</label>
            <span>{{ simulationResult.maxDepth }} 米</span>
          </div>
          <div class="result-item">
            <label>淹没人口:</label>
            <span>{{ simulationResult.population }} 人</span>
          </div>
          <div class="result-item">
            <label>淹没资产:</label>
            <span>{{ simulationResult.asset }} 万元</span>
          </div>
          <div class="result-chart">
            <h4>淹没深度分布</h4>
            <div class="chart-placeholder"></div>
          </div>
        </div>
        <div v-else class="no-result">
          请设置参数并点击开始分析
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const selectedArea = ref('all')
const rainfallIntensity = ref(50)
const duration = ref(24)
const terrainFactor = ref(1)
const simulationResult = ref(null)

const startSimulation = () => {
  // 模拟分析过程
  setTimeout(() => {
    simulationResult.value = {
      area: (rainfallIntensity.value * duration.value * terrainFactor.value / 100).toFixed(2),
      maxDepth: (rainfallIntensity.value * terrainFactor.value / 50).toFixed(2),
      population: Math.floor(rainfallIntensity.value * duration.value * 10),
      asset: Math.floor(rainfallIntensity.value * duration.value * terrainFactor.value * 100)
    }
  }, 1000)
}
</script>

<style scoped>
.simulation-container {
  padding: 20px;
}

h2 {
  color: var(--primary-color);
  margin-bottom: 20px;
  font-size: 24px;
}

.simulation-content {
  display: grid;
  grid-template-columns: 300px 1fr;
  gap: 20px;
}

.control-panel {
  background-color: var(--light-bg);
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.control-panel h3 {
  color: var(--secondary-color);
  margin-bottom: 15px;
  font-size: 18px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: var(--text-color);
  font-weight: 500;
}

.form-group select,
.form-group input[type="number"] {
  width: 100%;
  padding: 8px;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 14px;
}

.form-group input[type="range"] {
  width: 100%;
  margin-bottom: 5px;
}

.form-group span {
  display: inline-block;
  margin-left: 10px;
  color: var(--secondary-color);
  font-weight: 500;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-primary {
  background-color: var(--primary-color);
  color: white;
}

.btn-primary:hover {
  background-color: var(--primary-dark);
}

.result-panel {
  background-color: var(--light-bg);
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.result-panel h3 {
  color: var(--secondary-color);
  margin-bottom: 15px;
  font-size: 18px;
}

.result-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 15px;
}

.result-item {
  background-color: white;
  padding: 15px;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.result-item label {
  display: block;
  margin-bottom: 5px;
  color: var(--text-muted);
  font-size: 14px;
}

.result-item span {
  display: block;
  font-size: 18px;
  font-weight: 600;
  color: var(--primary-color);
}

.result-chart {
  grid-column: 1 / -1;
  margin-top: 20px;
}

.result-chart h4 {
  color: var(--text-color);
  margin-bottom: 10px;
  font-size: 16px;
}

.chart-placeholder {
  height: 200px;
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--text-muted);
}

.no-result {
  text-align: center;
  padding: 40px 20px;
  color: var(--text-muted);
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .simulation-content {
    grid-template-columns: 1fr;
  }
}
</style>