<script setup>
import { ref, onMounted } from 'vue'

// 模拟数据 - 站点列表
const stations = ref([
  {
    id: 1,
    name: '长沙站',
    type: '水文站',
    location: '长沙市岳麓区',
    longitude: 112.982279,
    latitude: 28.19409,
    status: '正常',
    installTime: '2025-01-01'
  },
  {
    id: 2,
    name: '株洲站',
    type: '水文站',
    location: '株洲市天元区',
    longitude: 113.122768,
    latitude: 27.839749,
    status: '正常',
    installTime: '2025-01-05'
  },
  {
    id: 3,
    name: '湘潭站',
    type: '水文站',
    location: '湘潭市雨湖区',
    longitude: 112.914063,
    latitude: 27.881192,
    status: '正常',
    installTime: '2025-01-10'
  },
  {
    id: 4,
    name: '衡阳站',
    type: '水文站',
    location: '衡阳市蒸湘区',
    longitude: 112.601496,
    latitude: 26.898266,
    status: '维护中',
    installTime: '2025-01-15'
  },
  {
    id: 5,
    name: '邵阳站',
    type: '水文站',
    location: '邵阳市大祥区',
    longitude: 111.469681,
    latitude: 27.231284,
    status: '正常',
    installTime: '2025-01-20'
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('站点管理数据加载完成')
})

// 添加站点
const addStation = () => {
  // 模拟添加站点
  console.log('添加站点')
  // 这里可以添加实际的添加逻辑
}

// 编辑站点
const editStation = (stationId) => {
  // 模拟编辑站点
  console.log('编辑站点', stationId)
  // 这里可以添加实际的编辑逻辑
}

// 删除站点
const deleteStation = (stationId) => {
  // 模拟删除站点
  console.log('删除站点', stationId)
  // 这里可以添加实际的删除逻辑
}
</script>

<template>
  <div class="station-management">

    
    <!-- 操作按钮 -->
    <div class="action-buttons">
      <button class="add-btn" @click="addStation">
        添加站点
      </button>
    </div>
    
    <!-- 站点列表 -->
    <div class="station-section">
      <div class="section-header">
        <h2>站点列表</h2>
        <p>水文站点信息</p>
      </div>
      <div class="station-list">
        <table>
          <thead>
            <tr>
              <th>站点名称</th>
              <th>类型</th>
              <th>位置</th>
              <th>经度</th>
              <th>纬度</th>
              <th>状态</th>
              <th>安装时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="station in stations" :key="station.id">
              <td>{{ station.name }}</td>
              <td>{{ station.type }}</td>
              <td>{{ station.location }}</td>
              <td>{{ station.longitude }}</td>
              <td>{{ station.latitude }}</td>
              <td>
                <span 
                  class="status"
                  :class="station.status.toLowerCase()"
                >
                  {{ station.status }}
                </span>
              </td>
              <td>{{ station.installTime }}</td>
              <td>
                <div class="action-buttons">
                  <button class="btn edit-btn" @click="editStation(station.id)">
                    编辑
                  </button>
                  <button class="btn delete-btn" @click="deleteStation(station.id)">
                    删除
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.station-management {
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

/* 操作按钮 */
.action-buttons {
  display: flex;
  gap: 1rem;
}

.add-btn {
  padding: 0.5rem 1rem;
  background-color: var(--primary-color);
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.add-btn:hover {
  background-color: var(--primary-dark);
}

/* 站点列表区域 */
.station-section {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex: 1;
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

.station-list {
  flex: 1;
  padding: 1rem;
  overflow: auto;
}

/* 表格样式 */
table {
  width: 100%;
  border-collapse: collapse;
  background-color: var(--background-white);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
}

th, td {
  padding: 0.75rem;
  text-align: left;
  border-bottom: 1px solid var(--border-color);
}

th {
  background-color: var(--primary-lighter);
  font-weight: 600;
  color: var(--primary-dark);
  position: sticky;
  top: 0;
  z-index: 10;
}

tr:hover {
  background-color: var(--background-light);
}

/* 状态样式 */
.status {
  font-size: 0.8rem;
  font-weight: 600;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  color: white;
}

.status.正常 {
  background-color: var(--success-color);
}

.status.维护中 {
  background-color: var(--warning-color);
}

.status.故障 {
  background-color: var(--danger-color);
}

/* 操作按钮 */
.action-buttons {
  display: flex;
  gap: 0.5rem;
}

.btn {
  font-size: 0.8rem;
  padding: 0.25rem 0.5rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.edit-btn {
  background-color: var(--secondary-color);
  color: white;
}

.edit-btn:hover {
  background-color: var(--secondary-dark);
}

.delete-btn {
  background-color: var(--danger-color);
  color: white;
}

.delete-btn:hover {
  background-color: #d32f2f;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .page-header h1 {
    font-size: 1.5rem;
  }
  
  .section-header h2 {
    font-size: 1.1rem;
  }
  
  table {
    font-size: 0.85rem;
  }
  
  th, td {
    padding: 0.5rem;
  }
  
  .action-buttons {
    flex-direction: column;
  }
  
  .btn {
    font-size: 0.75rem;
    padding: 0.25rem;
  }
}
</style>
