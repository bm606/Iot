<template>
  <div class="risk-container">
    <h2>风险评估</h2>
    <div class="risk-content">
      <div class="assessment-panel">
        <h3>评估参数</h3>
        <div class="form-group">
          <label>评估区域</label>
          <select v-model="selectedArea">
            <option value="all">全部区域</option>
            <option value="village">村庄</option>
            <option value="town">乡镇</option>
            <option value="city">城区</option>
          </select>
        </div>
        <div class="form-group">
          <label>评估类型</label>
          <select v-model="assessmentType">
            <option value="rainfall">降雨风险</option>
            <option value="flood">洪水风险</option>
            <option value="inundation">淹没风险</option>
            <option value="comprehensive">综合风险</option>
          </select>
        </div>
        <div class="form-group">
          <label>时间范围</label>
          <div class="date-range">
            <input type="date" v-model="startDate">
            <span>至</span>
            <input type="date" v-model="endDate">
          </div>
        </div>
        <div class="form-group">
          <label>风险等级阈值</label>
          <input type="range" v-model.number="riskThreshold" min="1" max="5" step="0.5">
          <span>{{ riskThreshold }} 级</span>
        </div>
        <button class="btn btn-primary" @click="startAssessment">开始评估</button>
      </div>
      <div class="result-panel">
        <h3>评估结果</h3>
        <div v-if="assessmentResult" class="result-content">
          <div class="risk-overview">
            <div class="risk-level" :class="`level-${Math.floor(assessmentResult.overallRisk)}`">
              <span class="level-label">综合风险等级</span>
              <span class="level-value">{{ assessmentResult.overallRisk }}</span>
              <span class="level-desc">{{ getRiskDescription(assessmentResult.overallRisk) }}</span>
            </div>
          </div>
          <div class="risk-details">
            <h4>风险构成</h4>
            <div class="risk-factors">
              <div class="risk-factor" v-for="(factor, index) in assessmentResult.factors" :key="index">
                <label>{{ factor.name }}</label>
                <div class="factor-bar">
                  <div class="factor-fill" :style="{ width: `${factor.value * 20}%` }" :class="`level-${Math.floor(factor.value)}`"></div>
                </div>
                <span>{{ factor.value }}</span>
              </div>
            </div>
          </div>
          <div class="risk-map">
            <h4>风险分布</h4>
            <div class="map-placeholder"></div>
          </div>
        </div>
        <div v-else class="no-result">
          请设置参数并点击开始评估
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const selectedArea = ref('all')
const assessmentType = ref('comprehensive')
const startDate = ref('2024-01-01')
const endDate = ref('2024-12-31')
const riskThreshold = ref(3)
const assessmentResult = ref(null)

const getRiskDescription = (level) => {
  const descriptions = {
    1: '低风险',
    2: '较低风险',
    3: '中等风险',
    4: '较高风险',
    5: '高风险'
  }
  return descriptions[Math.floor(level)] || '未知风险'
}

const startAssessment = () => {
  // 模拟评估过程
  setTimeout(() => {
    assessmentResult.value = {
      overallRisk: (Math.random() * 2 + riskThreshold.value - 1).toFixed(1),
      factors: [
        { name: '降雨因素', value: (Math.random() * 2 + 2).toFixed(1) },
        { name: '地形因素', value: (Math.random() * 2 + 2).toFixed(1) },
        { name: '人口因素', value: (Math.random() * 2 + 2).toFixed(1) },
        { name: '资产因素', value: (Math.random() * 2 + 2).toFixed(1) },
        { name: '防护因素', value: (Math.random() * 2 + 2).toFixed(1) }
      ]
    }
  }, 1000)
}
</script>

<style scoped>
.risk-container {
  padding: 20px;
}

h2 {
  color: var(--primary-color);
  margin-bottom: 20px;
  font-size: 24px;
}

.risk-content {
  display: grid;
  grid-template-columns: 300px 1fr;
  gap: 20px;
}

.assessment-panel {
  background-color: var(--light-bg);
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.assessment-panel h3 {
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
.form-group input[type="date"] {
  width: 100%;
  padding: 8px;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 14px;
}

.date-range {
  display: flex;
  align-items: center;
  gap: 10px;
}

.date-range input {
  flex: 1;
}

.date-range span {
  color: var(--text-muted);
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
  width: 100%;
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

.risk-overview {
  margin-bottom: 20px;
}

.risk-level {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.level-label {
  display: block;
  color: var(--text-muted);
  font-size: 14px;
  margin-bottom: 10px;
}

.level-value {
  display: block;
  font-size: 48px;
  font-weight: 700;
  margin-bottom: 10px;
}

.level-desc {
  display: block;
  font-size: 16px;
  font-weight: 500;
}

.level-1 {
  border-left: 4px solid #4CAF50;
}

.level-1 .level-value {
  color: #4CAF50;
}

.level-2 {
  border-left: 4px solid #8BC34A;
}

.level-2 .level-value {
  color: #8BC34A;
}

.level-3 {
  border-left: 4px solid #FFC107;
}

.level-3 .level-value {
  color: #FFC107;
}

.level-4 {
  border-left: 4px solid #FF9800;
}

.level-4 .level-value {
  color: #FF9800;
}

.level-5 {
  border-left: 4px solid #F44336;
}

.level-5 .level-value {
  color: #F44336;
}

.risk-details {
  margin-bottom: 20px;
}

.risk-details h4 {
  color: var(--text-color);
  margin-bottom: 15px;
  font-size: 16px;
}

.risk-factors {
  display: grid;
  gap: 10px;
}

.risk-factor {
  background-color: white;
  padding: 10px;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.risk-factor label {
  display: block;
  margin-bottom: 5px;
  color: var(--text-color);
  font-size: 14px;
}

.factor-bar {
  height: 20px;
  background-color: var(--light-bg);
  border-radius: 10px;
  overflow: hidden;
  margin-bottom: 5px;
}

.factor-fill {
  height: 100%;
  transition: width 0.5s ease;
}

.factor-fill.level-1 {
  background-color: #4CAF50;
}

.factor-fill.level-2 {
  background-color: #8BC34A;
}

.factor-fill.level-3 {
  background-color: #FFC107;
}

.factor-fill.level-4 {
  background-color: #FF9800;
}

.factor-fill.level-5 {
  background-color: #F44336;
}

.risk-map {
  margin-top: 20px;
}

.risk-map h4 {
  color: var(--text-color);
  margin-bottom: 10px;
  font-size: 16px;
}

.map-placeholder {
  height: 300px;
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
  .risk-content {
    grid-template-columns: 1fr;
  }
}
</style>