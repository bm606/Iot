<template>
  <div class="system-container">
    <h2>角色管理</h2>
    <div class="system-content">
      <div class="management-panel">
        <div class="panel-header">
          <h3>角色列表</h3>
          <div class="panel-actions">
            <button class="btn btn-primary" @click="addRole">添加角色</button>
            <button class="btn btn-outline" @click="exportRoles">导出角色</button>
          </div>
        </div>
        
        <div class="search-box">
          <input type="text" v-model="searchQuery" placeholder="搜索角色名称或描述">
          <button class="btn-search">🔍</button>
        </div>
        
        <div class="role-table">
          <table>
            <thead>
              <tr>
                <th><input type="checkbox" v-model="selectAll"></th>
                <th>角色名称</th>
                <th>角色描述</th>
                <th>创建时间</th>
                <th>修改时间</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="role in filteredRoles" :key="role.id">
                <td><input type="checkbox" v-model="selectedRoles" :value="role.id"></td>
                <td>{{ role.name }}</td>
                <td>{{ role.description }}</td>
                <td>{{ role.createTime }}</td>
                <td>{{ role.updateTime }}</td>
                <td>
                  <span class="status-tag" :class="role.status">
                    {{ role.status === 'active' ? '启用' : '禁用' }}
                  </span>
                </td>
                <td>
                  <button class="btn btn-sm btn-primary" @click="editRole(role)">编辑</button>
                  <button class="btn btn-sm btn-secondary" @click="assignPermissions(role)">权限</button>
                  <button class="btn btn-sm btn-danger" @click="deleteRole(role.id)">删除</button>
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
const selectAll = ref(false)
const selectedRoles = ref([])
const currentPage = ref(1)
const pageSize = ref(10)

const roleData = ref([
  {
    id: 1,
    name: '管理员',
    description: '系统管理员，拥有所有权限',
    createTime: '2024-01-01 00:00:00',
    updateTime: '2024-01-01 00:00:00',
    status: 'active'
  },
  {
    id: 2,
    name: '操作员',
    description: '普通操作员，拥有基本操作权限',
    createTime: '2024-01-02 10:30:00',
    updateTime: '2024-01-02 10:30:00',
    status: 'active'
  },
  {
    id: 3,
    name: '查看员',
    description: '只读权限，只能查看数据',
    createTime: '2024-01-03 14:15:00',
    updateTime: '2024-01-03 14:15:00',
    status: 'active'
  },
  {
    id: 4,
    name: '测试角色',
    description: '测试用角色',
    createTime: '2024-01-04 09:00:00',
    updateTime: '2024-01-04 09:00:00',
    status: 'inactive'
  }
])

const filteredRoles = computed(() => {
  let result = roleData.value.filter(role => {
    return role.name.includes(searchQuery.value) || role.description.includes(searchQuery.value)
  })
  
  // 分页
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return result.slice(start, end)
})

const totalPages = computed(() => {
  let filteredCount = roleData.value.filter(role => {
    return role.name.includes(searchQuery.value) || role.description.includes(searchQuery.value)
  }).length
  return Math.ceil(filteredCount / pageSize.value)
})

const addRole = () => {
  // 模拟添加角色功能
  alert('添加角色功能已触发')
}

const exportRoles = () => {
  // 模拟导出角色功能
  alert('导出角色功能已触发')
}

const editRole = (role) => {
  // 模拟编辑角色功能
  alert(`编辑角色：${role.name}`)
}

const assignPermissions = (role) => {
  // 模拟分配权限功能
  alert(`分配权限给角色：${role.name}`)
}

const deleteRole = (id) => {
  // 模拟删除角色功能
  if (confirm('确定要删除该角色吗？')) {
    roleData.value = roleData.value.filter(role => role.id !== id)
    alert('角色已删除')
  }
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
.system-container {
  padding: 20px;
}

h2 {
  color: var(--primary-color);
  margin-bottom: 20px;
  font-size: 24px;
}

.system-content {
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

.search-box {
  display: flex;
  margin-bottom: 20px;
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

.role-table {
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

.status-tag.inactive {
  background-color: #f5f5f5;
  color: #616161;
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
  
  .search-box {
    max-width: none;
  }
  
  .pagination {
    flex-direction: column;
    gap: 10px;
  }
}
</style>