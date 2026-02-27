<script setup>
import { ref, onMounted } from 'vue'

// 查询参数
const queryParams = ref({
  station: '',
  startTime: '2026-02-09',
  endTime: '2026-02-10'
})

// 模拟数据 - 水文查询结果
const hydrologyData = ref([
  {
    id: 1,
    station: '长沙站',
    time: '2026-02-10 14:00',
    waterLevel: 12.5,
    flow: 125,
    temperature: 18.5
  },
  {
    id: 2,
    station: '长沙站',
    time: '2026-02-10 13:00',
    waterLevel: 12.3,
    flow: 120,
    temperature: 18.3
  },
  {
    id: 3,
    station: '长沙站',
    time: '2026-02-10 12:00',
    waterLevel: 12.1,
    flow: 115,
    temperature: 18.0
  },
  {
    id: 4,
    station: '长沙站',
    time: '2026-02-10 11:00',
    waterLevel: 11.9,
    flow: 110,
    temperature: 17.8
  },
  {
    id: 5,
    station: '长沙站',
    time: '2026-02-10 10:00',
    waterLevel: 11.7,
    flow: 105,
    temperature: 17.5
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('水文查询数据加载完成')
})

// 执行查询
const executeQuery = () => {
  // 模拟查询过程
  console.log('执行水文查询', queryParams.value)
  // 这里可以添加实际的查询逻辑
}
</script>

<template>
  <div class="hydrology-query">

    
    <!-- 查询表单 -->
    <div class="query-form-section">
      <div class="section-header">
        <h2>查询条件</h2>
        <p>设置水文数据查询条件</p>
      </div>
      <div class="query-form">
        <div class="form-row">
          <div class="form-group">
            <label for="station">站点名称</label>
            <select id="station" v-model="queryParams.station">
              <option value="">全部站点</option>
              <option value="长沙站">长沙站</option>
              <option value="株洲站">株洲站</option>
              <option value="湘潭站">湘潭站</option>
              <option value="衡阳站">衡阳站</option>
              <option value="邵阳站">邵阳站</option>
            </select>
          </div>
          <div class="form-group">
            <label for="startTime">开始时间</label>
            <input 
              type="date" 
              id="startTime" 
              v-model="queryParams.startTime"
            />
          </div>
          <div class="form-group">
            <label for="endTime">结束时间</label>
            <input 
              type="date" 
              id="endTime" 
              v-model="queryParams.endTime"
            />
          </div>
          <div class="form-group submit-group">
            <button class="query-btn" @click="executeQuery">
              执行查询
            </button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 查询结果 -->
    <div class="result-section">
      <div class="section-header">
        <h2>查询结果</h2>
        <p>水文数据查询结果</p>
      </div>
      <div class="result-table">
        <table>
          <thead>
            <tr>
              <th>站点名称</th>
              <th>时间</th>
              <th>水位 (m)</th>
              <th>流量 (m³/s)</th>
              <th>水温 (℃)</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in hydrologyData" :key="item.id">
              <td>{{ item.station }}</td>
              <td>{{ item.time }}</td>
              <td>{{ item.waterLevel }}</td>
              <td>{{ item.flow }}</td>
              <td>{{ item.temperature }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.hydrology-query {
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

/* 查询表单区域 */
.query-form-section {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
}

.section-header {
  margin-bottom: 1rem;
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

.form-row {
  display: flex;
  gap: 1rem;
  align-items: flex-end;
}

.form-group {
  flex: 1;
}

.submit-group {
  flex: 0 0 auto;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: var(--text-primary);
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 0.9rem;
  transition: border-color 0.2s ease;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 2px var(--primary-lighter);
}

.query-btn {
  width: 100%;
  padding: 0.5rem;
  background-color: var(--primary-color);
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.query-btn:hover {
  background-color: var(--primary-dark);
}

/* 查询结果区域 */
.result-section {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.result-section .section-header {
  padding: 1rem 1.25rem;
  border-bottom: 1px solid var(--border-color);
  margin-bottom: 0;
}

.result-table {
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

/* 响应式设计 */
@media (max-width: 768px) {
  .page-header h1 {
    font-size: 1.5rem;
  }
  
  .section-header h2 {
    font-size: 1.1rem;
  }
  
  .form-row {
    flex-direction: column;
    align-items: stretch;
  }
  
  .submit-group {
    margin-top: 1rem;
  }
  
  table {
    font-size: 0.85rem;
  }
  
  th, td {
    padding: 0.5rem;
  }
}
</style>
