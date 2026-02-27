<template>
  <div class="query-container">
    <h2>预警查询</h2>
    <div class="query-content">
      <div class="query-panel">
        <h3>查询参数</h3>
        <div class="form-group">
          <label>预警等级</label>
          <select v-model="queryParams.level">
            <option value="all">全部等级</option>
            <option value="blue">蓝色</option>
            <option value="yellow">黄色</option>
            <option value="orange">橙色</option>
            <option value="red">红色</option>
          </select>
        </div>
        <div class="form-group">
          <label>预警状态</label>
          <select v-model="queryParams.status">
            <option value="all">全部状态</option>
            <option value="active">生效中</option>
            <option value="expired">已过期</option>
            <option value="canceled">已取消</option>
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
          <label>区域选择</label>
          <select v-model="queryParams.area">
            <option value="all">全部区域</option>
            <option value="north">北部区域</option>
            <option value="south">南部区域</option>
            <option value="east">东部区域</option>
            <option value="west">西部区域</option>
          </select>
        </div>
        <button class="btn btn-primary" @click="queryData">查询数据</button>
        <button class="btn btn-secondary" @click="exportData">导出数据</button>
      </div>
      
      <div class="result-panel">
        <h3>查询结果</h3>
        <div v-if="loading" class="loading">
          数据加载中...
        </div>
        <div v-else-if="warningData.length > 0" class="result-content">
          <div class="warning-stats">
            <div class="stat-item">
              <span class="stat-label">总预警数</span>
              <span class="stat-value">{{ warningData.length }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">蓝色预警</span>
              <span class="stat-value">{{ getWarningCount('blue') }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">黄色预警</span>
              <span class="stat-value">{{ getWarningCount('yellow') }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">橙色预警</span>
              <span class="stat-value">{{ getWarningCount('orange') }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">红色预警</span>
              <span class="stat-value">{{ getWarningCount('red') }}</span>
            </div>
          </div>
          <div class="data-table">
            <h4>详细预警信息</h4>
            <table>
              <thead>
                <tr>
                  <th>预警时间</th>
                  <th>预警等级</th>
                  <th>预警区域</th>
                  <th>预警内容</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in warningData" :key="index">
                  <td>{{ item.time }}</td>
                  <td>
                    <span class="level-tag" :class="item.level">
                      {{ getLevelText(item.level) }}
                    </span>
                  </td>
                  <td>{{ item.area }}</td>
                  <td>{{ item.content }}</td>
                  <td>
                    <span class="status-tag" :class="item.status">
                      {{ getStatusText(item.status) }}
                    </span>
                  </td>
                  <td>
                    <button class="btn btn-sm" @click="viewDetail(item)">查看详情</button>
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
  level: 'all',
  status: 'all',
  startDate: '2024-01-01',
  endDate: '2024-01-31',
  area: 'all'
})

const loading = ref(false)
const warningData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)

const totalPages = computed(() => {
  return Math.ceil(warningData.value.length / pageSize.value)
})

const getLevelText = (level) => {
  const levelMap = {
    blue: '蓝色',
    yellow: '黄色',
    orange: '橙色',
    red: '红色'
  }
  return levelMap[level] || level
}

const getStatusText = (status) => {
  const statusMap = {
    active: '生效中',
    expired: '已过期',
    canceled: '已取消'
  }
  return statusMap[status] || status
}

const getWarningCount = (level) => {
  return warningData.value.filter(item => item.level === level).length
}

const queryData = () => {
  loading.value = true
  // 模拟查询数据
  setTimeout(() => {
    const mockData = []
    const levels = ['blue', 'yellow', 'orange', 'red']
    const statuses = ['active', 'expired', 'canceled']
    const areas = ['北部区域', '南部区域', '东部区域', '西部区域']
    const contents = [
      '预计未来6小时内降雨量将达到50mm以上，可能引发山洪灾害',
      '预计未来12小时内降雨量将达到100mm以上，可能引发严重山洪灾害',
      '预计未来24小时内降雨量将达到150mm以上，可能引发特别严重山洪灾害',
      '预计未来3小时内降雨量将达到200mm以上，可能引发极其严重山洪灾害'
    ]
    
    for (let i = 0; i < 20; i++) {
      const date = new Date()
      date.setDate(date.getDate() - Math.floor(Math.random() * 30))
      
      mockData.push({
        time: date.toISOString().split('T')[0] + ' ' + Math.floor(Math.random() * 24) + ':' + String(Math.floor(Math.random() * 60)).padStart(2, '0'),
        level: levels[Math.floor(Math.random() * levels.length)],
        area: areas[Math.floor(Math.random() * areas.length)],
        content: contents[Math.floor(Math.random() * contents.length)],
        status: statuses[Math.floor(Math.random() * statuses.length)]
      })
    }
    
    warningData.value = mockData
    currentPage.value = 1
    loading.value = false
  }, 1000)
}

const exportData = () => {
  // 模拟导出功能
  alert('数据导出功能已触发')
}

const viewDetail = (warning) => {
  // 模拟查看详情功能
  alert(`查看预警详情：${warning.content}`)
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

.btn-sm {
  padding: 4px 12px;
  font-size: 12px;
  width: auto;
  margin-bottom: 0;
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

.warning-stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 15px;
}

.stat-item {
  background-color: white;
  padding: 15px;
  border-radius: 4px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.stat-label {
  display: block;
  color: var(--text-muted);
  font-size: 14px;
  margin-bottom: 5px;
}

.stat-value {
  display: block;
  font-size: 24px;
  font-weight: 600;
  color: var(--primary-color);
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

.level-tag {
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.level-tag.blue {
  background-color: #e3f2fd;
  color: #1976d2;
}

.level-tag.yellow {
  background-color: #fff3e0;
  color: #ff9800;
}

.level-tag.orange {
  background-color: #fff3e0;
  color: #ff5722;
}

.level-tag.red {
  background-color: #ffebee;
  color: #f44336;
}

.status-tag {
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.active {
  background-color: #e8f5e8;
  color: #2e7d32;
}

.status-tag.expired {
  background-color: #f5f5f5;
  color: #616161;
}

.status-tag.canceled {
  background-color: #ffebee;
  color: #c62828;
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
  
  .warning-stats {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .pagination {
    flex-direction: column;
    gap: 10px;
  }
}
</style>