<template>
  <div class="query-container">
    <h2>降雨查询</h2>
    <div class="query-content">
      <div class="query-panel">
        <h3>查询参数</h3>
        <div class="form-group">
          <label>站点选择</label>
          <select v-model="queryParams.station">
            <option value="all">全部站点</option>
            <option value="station1">站点1</option>
            <option value="station2">站点2</option>
            <option value="station3">站点3</option>
            <option value="station4">站点4</option>
          </select>
        </div>
        <div class="form-group">
          <label>时间范围</label>
          <div class="date-range">
            <input type="date" v-model="queryParams.startDate">
            <span>至</span>
            <input type="date" v-model="queryParams.endDate">
          </div>
        </div>
        <div class="form-group">
          <label>时间间隔</label>
          <select v-model="queryParams.interval">
            <option value="hour">小时</option>
            <option value="day">天</option>
            <option value="week">周</option>
            <option value="month">月</option>
          </select>
        </div>
        <div class="form-group">
          <label>降雨量阈值</label>
          <input type="number" v-model.number="queryParams.threshold" placeholder="请输入降雨量阈值" min="0">
          <span>mm</span>
        </div>
        <button class="btn btn-primary" @click="queryData">查询数据</button>
        <button class="btn btn-secondary" @click="exportData">导出数据</button>
      </div>
      
      <div class="result-panel">
        <h3>查询结果</h3>
        <div v-if="loading" class="loading">
          数据加载中...
        </div>
        <div v-else-if="rainfallData.length > 0" class="result-content">
          <div class="chart-container">
            <h4>降雨趋势图</h4>
            <div class="chart-placeholder"></div>
          </div>
          <div class="data-table">
            <h4>详细数据</h4>
            <table>
              <thead>
                <tr>
                  <th>时间</th>
                  <th>站点</th>
                  <th>降雨量 (mm)</th>
                  <th>状态</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in rainfallData" :key="index">
                  <td>{{ item.time }}</td>
                  <td>{{ item.station }}</td>
                  <td>{{ item.rainfall }}</td>
                  <td>
                    <span class="status-tag" :class="getRainfallStatus(item.rainfall)">
                      {{ getRainfallStatusText(item.rainfall) }}
                    </span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="pagination">
            <button class="btn btn-outline" @click="previousPage" :disabled="currentPage === 1">上一页</button>
            <span>第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
            <button class="btn btn-outline" @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
          </div>
        </div>
        <div v-else class="no-result">
          暂无数据，请调整查询参数
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const queryParams = ref({
  station: 'all',
  startDate: '2024-01-01',
  endDate: '2024-01-31',
  interval: 'day',
  threshold: 0
})

const loading = ref(false)
const rainfallData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)

const totalPages = computed(() => {
  return Math.ceil(rainfallData.value.length / pageSize.value)
})

const getRainfallStatus = (rainfall) => {
  if (rainfall < 10) return 'light'
  if (rainfall < 25) return 'moderate'
  if (rainfall < 50) return 'heavy'
  return 'severe'
}

const getRainfallStatusText = (rainfall) => {
  if (rainfall < 10) return '小雨'
  if (rainfall < 25) return '中雨'
  if (rainfall < 50) return '大雨'
  return '暴雨'
}

const queryData = () => {
  loading.value = true
  // 模拟查询数据
  setTimeout(() => {
    const mockData = []
    const stations = ['站点1', '站点2', '站点3', '站点4']
    const start = new Date(queryParams.value.startDate)
    const end = new Date(queryParams.value.endDate)
    
    for (let d = new Date(start); d <= end; d.setDate(d.getDate() + 1)) {
      stations.forEach(station => {
        const rainfall = (Math.random() * 60).toFixed(1)
        if (parseFloat(rainfall) >= queryParams.value.threshold) {
          mockData.push({
            time: d.toISOString().split('T')[0],
            station,
            rainfall
          })
        }
      })
    }
    
    rainfallData.value = mockData
    currentPage.value = 1
    loading.value = false
  }, 1000)
}

const exportData = () => {
  // 模拟导出功能
  alert('数据导出功能已触发')
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

// 初始加载数据
queryData()
</script>

<style scoped>
.query-container {
  padding: 20px;
}

h2 {
  color: var(--primary-color);
  margin-bottom: 20px;
  font-size: 24px;
}

.query-content {
  display: grid;
  grid-template-columns: 300px 1fr;
  gap: 20px;
}

.query-panel {
  background-color: var(--light-bg);
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.query-panel h3 {
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
.form-group input[type="date"],
.form-group input[type="number"] {
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

.form-group span {
  display: inline-block;
  margin-left: 10px;
  color: var(--text-muted);
  font-size: 14px;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s ease;
  width: 100%;
  margin-bottom: 10px;
}

.btn-primary {
  background-color: var(--primary-color);
  color: white;
}

.btn-primary:hover {
  background-color: var(--primary-dark);
}

.btn-secondary {
  background-color: var(--secondary-color);
  color: white;
}

.btn-secondary:hover {
  background-color: #1976d2;
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

.loading {
  text-align: center;
  padding: 40px 20px;
  color: var(--text-muted);
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.result-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.chart-container {
  background-color: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.chart-container h4 {
  color: var(--text-color);
  margin-bottom: 15px;
  font-size: 16px;
}

.chart-placeholder {
  height: 300px;
  background-color: var(--light-bg);
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--text-muted);
}

.data-table {
  background-color: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow-x: auto;
}

.data-table h4 {
  color: var(--text-color);
  margin-bottom: 15px;
  font-size: 16px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid var(--border-color);
}

th {
  background-color: var(--light-bg);
  font-weight: 600;
  color: var(--primary-color);
}

.status-tag {
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.light {
  background-color: #e3f2fd;
  color: #1976d2;
}

.status-tag.moderate {
  background-color: #fff3e0;
  color: #ff9800;
}

.status-tag.heavy {
  background-color: #ffebee;
  color: #f44336;
}

.status-tag.severe {
  background-color: #fce4ec;
  color: #c2185b;
}

.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}

.btn-outline {
  background-color: transparent;
  border: 1px solid var(--border-color);
  color: var(--text-color);
  padding: 8px 16px;
}

.btn-outline:hover:not(:disabled) {
  background-color: var(--light-bg);
}

.btn-outline:disabled {
  opacity: 0.5;
  cursor: not-allowed;
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
  .query-content {
    grid-template-columns: 1fr;
  }
  
  .date-range {
    flex-direction: column;
    align-items: stretch;
  }
  
  .date-range input {
    width: 100%;
  }
  
  .pagination {
    flex-direction: column;
    gap: 10px;
  }
}
</style>