<script setup>
import { ref, onMounted } from 'vue'

// 模拟数据 - 用户列表
const users = ref([
  {
    id: 1,
    username: 'admin',
    name: '管理员',
    role: '超级管理员',
    status: '启用',
    lastLogin: '2026-02-10 14:30',
    createTime: '2025-01-01'
  },
  {
    id: 2,
    username: 'operator',
    name: '操作员',
    role: '普通用户',
    status: '启用',
    lastLogin: '2026-02-10 13:45',
    createTime: '2025-01-05'
  },
  {
    id: 3,
    username: 'viewer',
    name: '查看员',
    role: '只读用户',
    status: '启用',
    lastLogin: '2026-02-10 12:20',
    createTime: '2025-01-10'
  },
  {
    id: 4,
    username: 'test',
    name: '测试用户',
    role: '普通用户',
    status: '禁用',
    lastLogin: '2026-02-01 10:00',
    createTime: '2025-01-15'
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('用户管理数据加载完成')
})

// 添加用户
const addUser = () => {
  // 模拟添加用户
  console.log('添加用户')
  // 这里可以添加实际的添加逻辑
}

// 编辑用户
const editUser = (userId) => {
  // 模拟编辑用户
  console.log('编辑用户', userId)
  // 这里可以添加实际的编辑逻辑
}

// 删除用户
const deleteUser = (userId) => {
  // 模拟删除用户
  console.log('删除用户', userId)
  // 这里可以添加实际的删除逻辑
}

// 切换用户状态
const toggleUserStatus = (userId) => {
  // 模拟切换用户状态
  console.log('切换用户状态', userId)
  // 这里可以添加实际的切换逻辑
}
</script>

<template>
  <div class="user-management">

    
    <!-- 操作按钮 -->
    <div class="action-buttons">
      <button class="add-btn" @click="addUser">
        添加用户
      </button>
    </div>
    
    <!-- 用户列表 -->
    <div class="user-section">
      <div class="section-header">
        <h2>用户列表</h2>
        <p>系统用户信息</p>
      </div>
      <div class="user-list">
        <table>
          <thead>
            <tr>
              <th>用户名</th>
              <th>姓名</th>
              <th>角色</th>
              <th>状态</th>
              <th>最后登录</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.username }}</td>
              <td>{{ user.name }}</td>
              <td>{{ user.role }}</td>
              <td>
                <span 
                  class="status"
                  :class="user.status.toLowerCase()"
                >
                  {{ user.status }}
                </span>
              </td>
              <td>{{ user.lastLogin }}</td>
              <td>{{ user.createTime }}</td>
              <td>
                <div class="action-buttons">
                  <button class="btn edit-btn" @click="editUser(user.id)">
                    编辑
                  </button>
                  <button 
                    class="btn status-btn"
                    :class="user.status.toLowerCase()"
                    @click="toggleUserStatus(user.id)"
                  >
                    {{ user.status === '启用' ? '禁用' : '启用' }}
                  </button>
                  <button class="btn delete-btn" @click="deleteUser(user.id)">
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
.user-management {
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

/* 用户列表区域 */
.user-section {
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

.user-list {
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

.status.启用 {
  background-color: var(--success-color);
}

.status.禁用 {
  background-color: var(--text-light);
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

.status-btn.启用 {
  background-color: var(--warning-color);
  color: white;
}

.status-btn.启用:hover {
  background-color: #f57c00;
}

.status-btn.禁用 {
  background-color: var(--success-color);
  color: white;
}

.status-btn.禁用:hover {
  background-color: #388e3c;
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
