<script setup>
import { ref, onMounted } from 'vue'

// 模拟数据 - 预警历史
const warningHistory = ref([
  {
    id: 1,
    station: '长沙站',
    level: '橙色',
    time: '2026-02-10 14:30',
    content: '预计未来6小时降雨量将达50mm以上',
    status: '已解除'
  },
  {
    id: 2,
    station: '株洲站',
    level: '黄色',
    time: '2026-02-10 13:45',
    content: '预计未来12小时降雨量将达30mm以上',
    status: '已解除'
  },
  {
    id: 3,
    station: '湘潭站',
    level: '蓝色',
    time: '2026-02-10 12:20',
    content: '预计未来24小时降雨量将达20mm以上',
    status: '已解除'
  },
  {
    id: 4,
    station: '衡阳站',
    level: '蓝色',
    time: '2026-02-09 18:15',
    content: '预计未来24小时降雨量将达20mm以上',
    status: '已解除'
  },
  {
    id: 5,
    station: '邵阳站',
    level: '黄色',
    time: '2026-02-09 15:30',
    content: '预计未来12小时降雨量将达30mm以上',
    status: '已解除'
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('预警历史数据加载完成')
})
</script>

<template>
  <div class="warning-history">
    
    
    <!-- 预警历史列表 -->
    <div class="history-section">
      <div class="section-header">
        <h2>预警历史记录</h2>
        <p>历史预警信息</p>
      </div>
      <div class="history-list">
        <table>
          <thead>
            <tr>
              <th>站点名称</th>
              <th>预警等级</th>
              <th>预警时间</th>
              <th>预警内容</th>
              <th>状态</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in warningHistory" :key="item.id">
              <td>{{ item.station }}</td>
              <td>
                <span 
                  class="level"
                  :class="item.level.toLowerCase()"
                >
                  {{ item.level }}预警
                </span>
              </td>
              <td>{{ item.time }}</td>
              <td>{{ item.content }}</td>
              <td>
                <span class="status">{{ item.status }}</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.warning-history {
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

/* 预警历史列表区域 */
.history-section {
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

.history-list {
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
  background-color: var(--info-color);
}

/* 预警等级样式 */
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
  
  .level,
  .status {
    font-size: 0.75rem;
    padding: 0.2rem 0.4rem;
  }
}
</style>
