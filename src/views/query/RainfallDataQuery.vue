<template>
  <div class="rainfall-data-query">
    <h2>降雨数据查询</h2>
    <div class="query-content">
      <!-- 左侧区域选择 -->
      <div class="region-selection">
        <h3>选择区域</h3>
        <div class="region-tree">
          <div class="tree-header">
            <input type="text" v-model="searchRegion" placeholder="搜索区域..." class="search-input">
          </div>
          <div class="tree-content">
            <ul class="tree">
              <li v-for="region in regions" :key="region.id" class="tree-item">
                <div class="tree-node" @click="toggleRegion(region.id)">
                  <span class="tree-toggle" :class="{ expanded: expandedRegions.includes(region.id) }">
                    {{ expandedRegions.includes(region.id) ? '▼' : '►' }}
                  </span>
                  <input 
                    type="checkbox" 
                    :id="`region-${region.id}`" 
                    :checked="selectedRegions.includes(region.id)"
                    @change="toggleRegionSelection(region.id)"
                  >
                  <label :for="`region-${region.id}`">{{ region.name }}</label>
                </div>
                <ul v-if="region.children && expandedRegions.includes(region.id)" class="tree-children">
                  <li v-for="child in region.children" :key="child.id" class="tree-child-item">
                    <div class="tree-node">
                      <input 
                        type="checkbox" 
                        :id="`region-${child.id}`" 
                        :checked="selectedRegions.includes(child.id)"
                        @change="toggleRegionSelection(child.id)"
                      >
                      <label :for="`region-${child.id}`">{{ child.name }}</label>
                    </div>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
        <div class="selection-actions">
          <button class="btn btn-outline" @click="selectAllRegions">全选</button>
          <button class="btn btn-outline" @click="clearSelection">清空</button>
        </div>
      </div>

      <!-- 右侧数据表格 -->
      <div class="data-content">
        <div class="content-header">
          <h3>降雨数据</h3>
          <div class="header-actions">
            <div class="search-box">
              <input type="text" v-model="searchQuery" placeholder="搜索站点...">
              <button class="btn-search" @click="searchData">🔍</button>
            </div>
            <button class="btn btn-secondary" @click="exportData">导出数据</button>
          </div>
        </div>

        <div v-if="loading" class="loading">
          <div class="loading-spinner"></div>
          <p>数据加载中...</p>
        </div>
        <div v-else-if="rainfallData.length > 0" class="data-table-container">
          <table class="data-table">
            <thead>
              <tr>
                <th>序号</th>
                <th>站点名称</th>
                <th>站点类型</th>
                <th>小六度号</th>
                <th>土壤含水量</th>
                <th>两小时内小计</th>
                <th>降雨量(mm)</th>
                <th>备注</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in paginatedData" :key="index" class="table-row">
                <td>{{ (currentPage - 1) * pageSize + index + 1 }}</td>
                <td>{{ item.stationName }}</td>
                <td>{{ item.stationType }}</td>
                <td>{{ item.stationCode }}</td>
                <td>{{ item.soilMoisture }}</td>
                <td>{{ item.twoHourTotal }}</td>
                <td>{{ item.rainfall }}</td>
                <td>{{ item.remark }}</td>
              </tr>
            </tbody>
          </table>

          <!-- 分页 -->
          <div class="pagination">
            <div class="pagination-info">
              <span>共有降雨时段监测站点 {{ totalItems }} 个</span>
            </div>
            <div class="pagination-controls">
              <span>共 {{ totalPages }} 页 20条/页</span>
              <div class="page-buttons">
                <button 
                  class="page-btn" 
                  @click="goToPage(1)"
                  :disabled="currentPage === 1"
                >
                  首页
                </button>
                <button 
                  class="page-btn" 
                  @click="goToPage(currentPage - 1)"
                  :disabled="currentPage === 1"
                >
                  上一页
                </button>
                <span class="page-info">第 {{ currentPage }} 页</span>
                <button 
                  class="page-btn" 
                  @click="goToPage(currentPage + 1)"
                  :disabled="currentPage === totalPages"
                >
                  下一页
                </button>
                <button 
                  class="page-btn" 
                  @click="goToPage(totalPages)"
                  :disabled="currentPage === totalPages"
                >
                  末页
                </button>
              </div>
              <button class="btn btn-outline">跳转</button>
            </div>
          </div>
        </div>
        <div v-else class="no-data">
          <p>暂无数据，请选择区域或调整搜索条件</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'

// 区域数据
const regions = ref([
  {
    id: 1,
    name: '长沙市',
    children: [
      { id: 11, name: '雨花区' },
      { id: 12, name: '天心区' },
      { id: 13, name: '岳麓区' },
      { id: 14, name: '开福区' },
      { id: 15, name: '芙蓉区' }
    ]
  },
  {
    id: 2,
    name: '株洲市',
    children: [
      { id: 21, name: '荷塘区' },
      { id: 22, name: '芦淞区' },
      { id: 23, name: '石峰区' },
      { id: 24, name: '天元区' }
    ]
  },
  {
    id: 3,
    name: '湘潭市',
    children: [
      { id: 31, name: '雨湖区' },
      { id: 32, name: '岳塘区' }
    ]
  },
  {
    id: 4,
    name: '衡阳市',
    children: [
      { id: 41, name: '珠晖区' },
      { id: 42, name: '雁峰区' },
      { id: 43, name: '石鼓区' },
      { id: 44, name: '蒸湘区' }
    ]
  },
  {
    id: 5,
    name: '邵阳市',
    children: [
      { id: 51, name: '双清区' },
      { id: 52, name: '大祥区' },
      { id: 53, name: '北塔区' }
    ]
  },
  {
    id: 6,
    name: '岳阳市',
    children: [
      { id: 61, name: '岳阳楼区' },
      { id: 62, name: '云溪区' },
      { id: 63, name: '君山区' }
    ]
  },
  {
    id: 7,
    name: '常德市',
    children: [
      { id: 71, name: '武陵区' },
      { id: 72, name: '鼎城区' }
    ]
  },
  {
    id: 8,
    name: '张家界市',
    children: [
      { id: 81, name: '永定区' },
      { id: 82, name: '武陵源区' }
    ]
  },
  {
    id: 9,
    name: '益阳市',
    children: [
      { id: 91, name: '资阳区' },
      { id: 92, name: '赫山区' }
    ]
  },
  {
    id: 10,
    name: '郴州市',
    children: [
      { id: 101, name: '北湖区' },
      { id: 102, name: '苏仙区' }
    ]
  }
])

// 状态管理
const searchRegion = ref('')
const expandedRegions = ref([1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
const selectedRegions = ref([])
const searchQuery = ref('')
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(20)

// 模拟数据
const rainfallData = ref([])

// 计算属性
const totalItems = computed(() => rainfallData.value.length)
const totalPages = computed(() => Math.ceil(totalItems.value / pageSize.value))
const paginatedData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return rainfallData.value.slice(start, end)
})

// 方法
const toggleRegion = (regionId) => {
  if (expandedRegions.value.includes(regionId)) {
    expandedRegions.value = expandedRegions.value.filter(id => id !== regionId)
  } else {
    expandedRegions.value.push(regionId)
  }
}

const toggleRegionSelection = (regionId) => {
  if (selectedRegions.value.includes(regionId)) {
    selectedRegions.value = selectedRegions.value.filter(id => id !== regionId)
  } else {
    selectedRegions.value.push(regionId)
  }
  // 当选择区域变化时，重新加载数据
  loadData()
}

const selectAllRegions = () => {
  selectedRegions.value = regions.value.map(region => region.id)
  loadData()
}

const clearSelection = () => {
  selectedRegions.value = []
  loadData()
}

const searchData = () => {
  loadData()
}

const exportData = () => {
  // 模拟导出功能
  alert('数据导出功能已触发')
}

const goToPage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page
  }
}

const loadData = () => {
  loading.value = true
  // 模拟数据加载
  setTimeout(() => {
    const mockData = []
    const stationTypes = ['政府站', '山洪站', '地质站']
    
    for (let i = 1; i <= 100; i++) {
      mockData.push({
        stationName: `站点${i}`,
        stationType: stationTypes[Math.floor(Math.random() * stationTypes.length)],
        stationCode: `WABT${181121 + Math.floor(Math.random() * 100)}${Math.random().toString(16).substring(2, 8).toUpperCase()}`,
        soilMoisture: `${Math.floor(Math.random() * 100)}%`,
        twoHourTotal: Math.floor(Math.random() * 10),
        rainfall: Math.floor(Math.random() * 100),
        remark: ''
      })
    }
    
    rainfallData.value = mockData
    currentPage.value = 1
    loading.value = false
  }, 500)
}

// 生命周期
onMounted(() => {
  loadData()
})
</script>

<style scoped>
.rainfall-data-query {
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
  height: calc(100vh - 150px);
}

/* 左侧区域选择 */
.region-selection {
  background-color: var(--light-bg);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.region-selection h3 {
  color: var(--secondary-color);
  padding: 15px 20px;
  margin: 0;
  font-size: 18px;
  border-bottom: 1px solid var(--border-color);
}

.region-tree {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.tree-header {
  padding: 10px 20px;
  border-bottom: 1px solid var(--border-color);
}

.search-input {
  width: 100%;
  padding: 8px;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 14px;
}

.tree-content {
  flex: 1;
  overflow-y: auto;
  padding: 10px 0;
}

.tree {
  list-style: none;
  padding: 0;
  margin: 0;
}

.tree-item {
  margin-bottom: 5px;
}

.tree-node {
  display: flex;
  align-items: center;
  padding: 6px 20px;
  cursor: pointer;
  user-select: none;
}

.tree-node:hover {
  background-color: rgba(30, 136, 229, 0.1);
}

.tree-toggle {
  width: 20px;
  font-size: 12px;
  color: var(--text-muted);
}

.tree-node input[type="checkbox"] {
  margin: 0 8px 0 0;
}

.tree-node label {
  flex: 1;
  cursor: pointer;
  font-size: 14px;
  color: var(--text-color);
}

.tree-children {
  list-style: none;
  padding: 0 0 0 20px;
  margin: 0;
}

.tree-child-item {
  margin-bottom: 3px;
}

.selection-actions {
  padding: 15px 20px;
  border-top: 1px solid var(--border-color);
  display: flex;
  gap: 10px;
}

/* 右侧数据内容 */
.data-content {
  background-color: var(--light-bg);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.content-header {
  padding: 15px 20px;
  border-bottom: 1px solid var(--border-color);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.content-header h3 {
  color: var(--secondary-color);
  margin: 0;
  font-size: 18px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.search-box {
  position: relative;
}

.search-box input {
  padding: 8px 30px 8px 10px;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 14px;
  width: 200px;
}

.btn-search {
  position: absolute;
  right: 5px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  font-size: 14px;
}

/* 数据表格 */
.data-table-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.data-table {
  flex: 1;
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  overflow: auto;
}

.data-table th,
.data-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid var(--border-color);
}

.data-table th {
  background-color: var(--primary-color);
  color: white;
  font-weight: 600;
  font-size: 14px;
  position: sticky;
  top: 0;
  z-index: 10;
}

.data-table td {
  font-size: 14px;
  color: var(--text-color);
}

.table-row:hover {
  background-color: rgba(30, 136, 229, 0.05);
}

/* 分页 */
.pagination {
  padding: 15px 20px;
  border-top: 1px solid var(--border-color);
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: white;
}

.pagination-info {
  font-size: 14px;
  color: var(--text-muted);
}

.pagination-controls {
  display: flex;
  align-items: center;
  gap: 15px;
}

.pagination-controls span {
  font-size: 14px;
  color: var(--text-color);
}

.page-buttons {
  display: flex;
  align-items: center;
  gap: 5px;
}

.page-btn {
  padding: 4px 8px;
  border: 1px solid var(--border-color);
  background-color: white;
  border-radius: 4px;
  font-size: 12px;
  cursor: pointer;
  color: var(--text-color);
}

.page-btn:hover:not(:disabled) {
  background-color: var(--light-bg);
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-info {
  margin: 0 10px;
  font-size: 14px;
  font-weight: 500;
}

/* 加载状态 */
.loading {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: white;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid rgba(30, 136, 229, 0.3);
  border-top: 4px solid var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 10px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.loading p {
  color: var(--text-muted);
  font-size: 14px;
}

/* 无数据状态 */
.no-data {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
  color: var(--text-muted);
  font-size: 14px;
}

/* 按钮样式 */
.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-outline {
  background-color: transparent;
  border: 1px solid var(--border-color);
  color: var(--text-color);
}

.btn-outline:hover {
  background-color: var(--light-bg);
}

.btn-secondary {
  background-color: var(--secondary-color);
  color: white;
}

.btn-secondary:hover {
  background-color: #1976d2;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .query-content {
    grid-template-columns: 250px 1fr;
  }
}

@media (max-width: 768px) {
  .query-content {
    grid-template-columns: 1fr;
    grid-template-rows: auto 1fr;
    height: auto;
  }
  
  .region-selection {
    max-height: 400px;
  }
  
  .content-header {
    flex-direction: column;
    align-items: stretch;
    gap: 10px;
  }
  
  .header-actions {
    justify-content: space-between;
  }
  
  .pagination {
    flex-direction: column;
    align-items: stretch;
    gap: 10px;
  }
  
  .pagination-controls {
    justify-content: space-between;
  }
}
</style>