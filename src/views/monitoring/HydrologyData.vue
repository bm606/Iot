<script setup>
import { ref, onMounted } from 'vue'

// 模拟数据 - 水文站点数据
const hydrologyData = ref([
  {
    id: 1,
    station: '长沙站',
    waterLevel: 12.5,
    flow: 125,
    temperature: 18.5,
    updateTime: '2026-02-10 14:30'
  },
  {
    id: 2,
    station: '株洲站',
    waterLevel: 10.8,
    flow: 98,
    temperature: 19.2,
    updateTime: '2026-02-10 14:25'
  },
  {
    id: 3,
    station: '湘潭站',
    waterLevel: 9.6,
    flow: 85,
    temperature: 18.8,
    updateTime: '2026-02-10 14:20'
  },
  {
    id: 4,
    station: '衡阳站',
    waterLevel: 11.2,
    flow: 110,
    temperature: 20.5,
    updateTime: '2026-02-10 14:15'
  },
  {
    id: 5,
    station: '邵阳站',
    waterLevel: 8.9,
    flow: 75,
    temperature: 19.0,
    updateTime: '2026-02-10 14:10'
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('水文数据加载完成')
})
</script>

<template>
  <div class="hydrology-data">

    
    <!-- 数据表格 -->
    <div class="data-table-section">
      <div class="section-header">
        <h2>站点水文数据</h2>
        <p>最新水文监测数据</p>
      </div>
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>站点名称</th>
              <th>水位 (m)</th>
              <th>流量 (m³/s)</th>
              <th>水温 (℃)</th>
              <th>更新时间</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in hydrologyData" :key="item.id">
              <td>{{ item.station }}</td>
              <td>{{ item.waterLevel }}</td>
              <td>{{ item.flow }}</td>
              <td>{{ item.temperature }}</td>
              <td>{{ item.updateTime }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.hydrology-data {
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

/* 数据表格区域 */
.data-table-section {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  flex: 1;
  display: flex;
  flex-direction: column;
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

.table-container {
  flex: 1;
  overflow: auto;
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
}
</style>
