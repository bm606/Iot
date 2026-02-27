<template>
  <div class="data-container">
    <h2>传感器管理</h2>
    <div class="data-content">
      <div class="management-panel">
        <div class="panel-header">
          <h3>传感器列表</h3>
          <div class="panel-actions">
            <button class="btn btn-primary" @click="addSensor">添加传感器</button>
            <button class="btn btn-secondary" @click="importSensors">导入传感器</button>
            <button class="btn btn-outline" @click="exportSensors">导出传感器</button>
          </div>
        </div>
        
        <div class="search-filter">
          <div class="search-box">
            <input type="text" v-model="searchQuery" placeholder="搜索传感器名称、编号或站点">
            <button class="btn-search">🔍</button>
          </div>
          <div class="filter-box">
            <select v-model="filterParams.type">
              <option value="all">全部类型</option>
              <option value="rainfall">降雨量</option>
              <option value="water">水位</option>
              <option value="flow">流量</option>
              <option value="pressure">压力</option>
            </select>
            <select v-model="filterParams.status">
              <option value="all">全部状态</option>
              <option value="normal">正常</option>
              <option value="fault">故障</option>
              <option value="offline">离线</option>
            </select>
          </div>
        </div>
        
        <div class="sensor-table">
          <table>
            <thead>
              <tr>
                <th><input type="checkbox" v-model="selectAll"></th>
                <th>编号</th>
                <th>名称</th>
                <th>类型</th>
                <th>所属站点</th>
                <th>安装位置</th>
                <th>状态</th>
                <th>最后数据时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="sensor in filteredSensors" :key="sensor.id" :class="sensor.status">
                <td><input type="checkbox" v-model="selectedSensors" :value="sensor.id"></td>
                <td>{{ sensor.code }}</td>
                <td>{{ sensor.name }}</td>
                <td>{{ getSensorTypeText(sensor.type) }}</td>
                <td>{{ sensor.station }}</td>
                <td>{{ sensor.location }}</td>
                <td>
                  <span class="status-tag" :class="sensor.status">
                    {{ getStatusText(sensor.status) }}
                  </span>
                </td>
                <td>{{ sensor.lastDataTime }}</td>
                <td>
                  <button class="btn btn-sm btn-primary" @click="editSensor(sensor)">编辑</button>
                  <button class="btn btn-sm btn-danger" @click="deleteSensor(sensor.id)">删除</button>
                  <button class="btn btn-sm btn-outline" @click="viewData(sensor.id)">查看数据</button>
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
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const searchQuery = ref('')
const filterParams = ref({
  type: 'all',
  status: 'all'
})
const selectAll = ref(false)
const selectedSensors = ref([])
const currentPage = ref(1)
const pageSize = ref(10)

const sensorData = ref([
  {
    id: 1,
    code: 'S001',
    name: '降雨量传感器1',
    type: 'rainfall',
    station: '站点1',
    location: '山顶',
    status: 'normal',
    lastDataTime: '2024-01-20 10:30:00'
  },
  {
    id: 2,
    code: 'S002',
    name: '水位传感器1',
    type: 'water',
    station: '站点1',
    location: '河边',
    status: 'normal',
    lastDataTime: '2024-01-20 10:25:00'
  },
  {
    id: 3,
    code: 'S003',
    name: '流量传感器1',
    type: 'flow',
    station: '站点2',
    location: '河道',
    status: 'fault',
    lastDataTime: '2024-01-20 08:15:00'
  },
  {
    id: 4,
    code: 'S004',
    name: '降雨量传感器2',
    type: 'rainfall',
    station: '站点2',
    location: '山腰',
    status: 'offline',
    lastDataTime: '2024-01-19 16:45:00'
  },
  {
    id: 5,
    code: 'S005',
    name: '水位传感器2',
    type: 'water',
    station: '站点3',
    location: '水库',
    status: 'normal',
    lastDataTime: '2024-01-20 10:20:00'
  }
])

const filteredSensors = computed(() => {
  let result = sensorData.value.filter(sensor => {
    const matchesSearch = 
      sensor.name.includes(searchQuery.value) ||
      sensor.code.includes(searchQuery.value) ||
      sensor.station.includes(searchQuery.value)
    const matchesType = filterParams.value.type === 'all' || sensor.type === filterParams.value.type
    const matchesStatus = filterParams.value.status === 'all' || sensor.status === filterParams.value.status
    return matchesSearch && matchesType && matchesStatus
  })
  
  // 分页
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return result.slice(start, end)
})

const totalPages = computed(() => {
  let filteredCount = sensorData.value.filter(sensor => {
    const matchesSearch = 
      sensor.name.includes(searchQuery.value) ||
      sensor.code.includes(searchQuery.value) ||
      sensor.station.includes(searchQuery.value)
    const matchesType = filterParams.value.type === 'all' || sensor.type === filterParams.value.type
    const matchesStatus = filterParams.value.status === 'all' || sensor.status === filterParams.value.status
    return matchesSearch && matchesType && matchesStatus
  }).length
  return Math.ceil(filteredCount / pageSize.value)
})

const getSensorTypeText = (type) => {
  const typeMap = {
    rainfall: '降雨量',
    water: '水位',
    flow: '流量',
    pressure: '压力'
  }
  return typeMap[type] || type
}

const getStatusText = (status) => {
  const statusMap = {
    normal: '正常',
    fault: '故障',
    offline: '离线'
  }
  return statusMap[status] || status
}

const addSensor = () => {
  // 模拟添加传感器功能
  alert('添加传感器功能已触发')
}

const importSensors = () => {
  // 模拟导入传感器功能
  alert('导入传感器功能已触发')
}

const exportSensors = () => {
  // 模拟导出传感器功能
  alert('导出传感器功能已触发')
}

const editSensor = (sensor) => {
  // 模拟编辑传感器功能
  alert(`编辑传感器：${sensor.name}`)
}

const deleteSensor = (id) => {
  // 模拟删除传感器功能
  if (confirm('确定要删除该传感器吗？')) {
    sensorData.value = sensorData.value.filter(sensor => sensor.id !== id)
    alert('传感器已删除')
  }
}

const viewData = (id) => {
  // 模拟查看传感器数据功能
  alert(`查看传感器 ${id} 的数据`)
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
</script>

<style scoped>
.data-container {
  padding: 20px;
}

h2 {
  color: var(--primary-color);
  margin-bottom: 20px;
  font-size: 24px;
}

.data-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.management-panel {
  padding: 20px;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid var(--border-color);
}

.panel-header h3 {
  color: var(--secondary-color);
  font-size: 18px;
  margin: 0;
}

.panel-actions {
  display: flex;
  gap: 10px;
}

.btn {
  padding: 8px 16px;
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

.btn-secondary {
  background-color: var(--secondary-color);
  color: white;
}

.btn-secondary:hover {
  background-color: #1976d2;
}

.btn-outline {
  background-color: transparent;
  border: 1px solid var(--border-color);
  color: var(--text-color);
}

.btn-outline:hover {
  background-color: var(--light-bg);
}

.btn-danger {
  background-color: #f44336;
  color: white;
}

.btn-danger:hover {
  background-color: #d32f2f;
}

.btn-sm {
  padding: 4px 12px;
  font-size: 12px;
}

.search-filter {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 10px;
}

.search-box {
  display: flex;
  align-items: center;
  flex: 1;
  max-width: 400px;
}

.search-box input {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid var(--border-color);
  border-radius: 4px 0 0 4px;
  font-size: 14px;
}

.btn-search {
  padding: 8px 16px;
  border: 1px solid var(--border-color);
  border-left: none;
  background-color: var(--light-bg);
  border-radius: 0 4px 4px 0;
  cursor: pointer;
  font-size: 14px;
}

.filter-box {
  display: flex;
  gap: 10px;
  align-items: center;
}

.filter-box select {
  padding: 8px 12px;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 14px;
}

.sensor-table {
  overflow-x: auto;
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid var(--border-color);
}

th {
  background-color: var(--light-bg);
  font-weight: 600;
  color: var(--primary-color);
  white-space: nowrap;
}

tr:hover {
  background-color: var(--light-bg);
}

tr.normal {
  background-color: rgba(76, 175, 80, 0.05);
}

tr.fault {
  background-color: rgba(255, 152, 0, 0.05);
}

tr.offline {
  background-color: rgba(244, 67, 54, 0.05);
}

.status-tag {
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.normal {
  background-color: #e8f5e8;
  color: #2e7d32;
}

.status-tag.fault {
  background-color: #fff3e0;
  color: #ff9800;
}

.status-tag.offline {
  background-color: #ffebee;
  color: #f44336;
}

.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}

.btn-outline:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

@media (max-width: 768px) {
  .panel-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .panel-actions {
    width: 100%;
    justify-content: space-between;
  }
  
  .search-filter {
    flex-direction: column;
    align-items: stretch;
  }
  
  .search-box {
    max-width: none;
  }
  
  .filter-box {
    justify-content: space-between;
  }
  
  .pagination {
    flex-direction: column;
    gap: 10px;
  }
}
</style>