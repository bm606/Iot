<script setup>
import { ref, onMounted } from 'vue'

// 模拟数据 - 预案列表
const plans = ref([
  {
    id: 1,
    name: '山洪灾害防御预案',
    level: '市级',
    version: 'v2.0',
    createTime: '2026-01-15',
    updateTime: '2026-02-10',
    status: '生效中'
  },
  {
    id: 2,
    name: '河流洪水防御预案',
    level: '市级',
    version: 'v1.5',
    createTime: '2025-12-20',
    updateTime: '2026-01-30',
    status: '生效中'
  },
  {
    id: 3,
    name: '水库防汛预案',
    level: '县级',
    version: 'v1.0',
    createTime: '2026-01-10',
    updateTime: '2026-01-10',
    status: '生效中'
  },
  {
    id: 4,
    name: '城市内涝防御预案',
    level: '市级',
    version: 'v1.2',
    createTime: '2025-11-05',
    updateTime: '2026-01-20',
    status: '生效中'
  },
  {
    id: 5,
    name: '山洪灾害应急处置预案',
    level: '县级',
    version: 'v1.0',
    createTime: '2026-01-05',
    updateTime: '2026-01-05',
    status: '生效中'
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('预案概览数据加载完成')
})
</script>

<template>
  <div class="plan-overview">

    
    <!-- 预案列表 -->
    <div class="plan-section">
      <div class="section-header">
        <h2>预案列表</h2>
        <p>山洪灾害相关预案</p>
      </div>
      <div class="plan-list">
        <table>
          <thead>
            <tr>
              <th>预案名称</th>
              <th>级别</th>
              <th>版本</th>
              <th>创建时间</th>
              <th>更新时间</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="plan in plans" :key="plan.id">
              <td>{{ plan.name }}</td>
              <td>{{ plan.level }}</td>
              <td>{{ plan.version }}</td>
              <td>{{ plan.createTime }}</td>
              <td>{{ plan.updateTime }}</td>
              <td>
                <span class="status">{{ plan.status }}</span>
              </td>
              <td>
                <div class="action-buttons">
                  <button class="btn view-btn">查看</button>
                  <button class="btn edit-btn">编辑</button>
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
.plan-overview {
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

/* 预案列表区域 */
.plan-section {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  flex: 1;
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

.plan-list {
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
  background-color: var(--success-color);
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

.view-btn {
  background-color: var(--primary-color);
  color: white;
}

.view-btn:hover {
  background-color: var(--primary-dark);
}

.edit-btn {
  background-color: var(--secondary-color);
  color: white;
}

.edit-btn:hover {
  background-color: var(--secondary-dark);
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
