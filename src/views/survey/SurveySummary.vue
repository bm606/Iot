<template>
  <div class="survey-summary">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
      <div class="sidebar-header">
        <h3>选择政区</h3>
      </div>
      <div class="search-box">
        <input 
          type="text" 
          v-model="searchKeyword" 
          placeholder="输入关键字进行过滤"
          class="search-input"
        >
      </div>
      <div class="region-tree">
        <ul class="tree-list">
          <li class="tree-item">
            <div 
              class="tree-node" 
              @click="toggleRegion('hunan')"
            >
              <span class="tree-toggle" :class="{ expanded: expandedRegions.includes('hunan') }">
                {{ expandedRegions.includes('hunan') ? '▼' : '►' }}
              </span>
              <span class="region-name">湖南省</span>
            </div>
            <ul v-if="expandedRegions.includes('hunan')" class="tree-children">
              <li 
                v-for="city in cities" 
                :key="city.id"
                class="tree-child-item"
                :class="{ active: selectedRegion === city.id }"
              >
                <div 
                  class="tree-node"
                  @click="selectRegion(city.id)"
                >
                  <span class="region-name">{{ city.name }}</span>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>

    <!-- 右侧主内容区 -->
    <div class="main-content">
      <!-- 顶部标题和切换选项 -->
      <div class="content-header">
        <h1>调查成果汇总</h1>
        <div class="view-toggle">
          <span class="toggle-label">结果展示：</span>
          <div class="toggle-buttons">
            <button 
              class="toggle-btn" 
              :class="{ active: viewMode === 'chart' }"
              @click="viewMode = 'chart'"
            >
              图表
            </button>
            <button 
              class="toggle-btn" 
              :class="{ active: viewMode === 'list' }"
              @click="viewMode = 'list'"
            >
              列表
            </button>
          </div>
        </div>
      </div>

      <!-- 上半部分：行政区基本调查情况 -->
      <div class="chart-section">
        <h2>行政区基本调查情况</h2>
        <div class="chart-container" ref="basicChartRef"></div>
      </div>

      <!-- 下半部分：调查成果 -->
      <div class="chart-section">
        <h2>调查成果</h2>
        <div class="chart-container" ref="resultChartRef"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue'
import * as echarts from 'echarts'

// 状态管理
const searchKeyword = ref('')
const expandedRegions = ref(['hunan'])
const selectedRegion = ref('')
const viewMode = ref('chart')
const basicChartRef = ref(null)
const resultChartRef = ref(null)
let basicChart = null
let resultChart = null

// 湖南省地级市数据
const cities = ref([
  { id: 'changsha', name: '长沙市' },
  { id: 'zhuzhou', name: '株洲市' },
  { id: 'xiangtan', name: '湘潭市' },
  { id: 'hengyang', name: '衡阳市' },
  { id: 'shaoyang', name: '邵阳市' },
  { id: 'yueyang', name: '岳阳市' },
  { id: 'changde', name: '常德市' },
  { id: 'zhangjiajie', name: '张家界市' },
  { id: 'yiyang', name: '益阳市' },
  { id: 'chenzhou', name: '郴州市' },
  { id: 'yongzhou', name: '永州市' },
  { id: 'huaihua', name: '怀化市' },
  { id: 'loudi', name: '娄底市' },
  { id: 'xiangxi', name: '湘西土家族苗族自治州' }
])

// 模拟数据 - 行政区基本调查情况
const basicSurveyData = {
  cities: ['长春市', '吉林市', '四平市', '辽源市', '通化市', '白山市', '松原市', '白城市', '延边州'],
  population: [2400000, 1800000, 1200000, 800000, 1000000, 600000, 1100000, 900000, 700000],
  households: [900000, 700000, 450000, 300000, 380000, 220000, 420000, 350000, 260000],
  generalZones: [65, 55, 45, 35, 40, 30, 42, 38, 32],
  keyZones: [25, 20, 15, 10, 12, 8, 14, 11, 9]
}

// 模拟数据 - 调查成果
const surveyResultData = {
  cities: ['长春市', '吉林市', '四平市', '辽源市', '通化市', '白山市', '松原市', '白城市', '延边州'],
  riversideVillages: [3200, 2800, 2100, 1800, 2000, 1600, 1900, 1700, 1500],
  dangerZones: [2800, 2400, 1900, 1600, 1800, 1400, 1700, 1500, 1300],
  importantTowns: [2500, 2200, 1700, 1400, 1600, 1200, 1500, 1300, 1100],
  enterprises: [2000, 1800, 1400, 1200, 1300, 1000, 1200, 1100, 900],
  historicalDisasters: [1400, 1200, 900, 700, 800, 600, 850, 750, 650],
  crossSections: [1200, 1000, 800, 600, 700, 500, 750, 650, 550],
  longitudinalSections: [1000, 900, 700, 500, 600, 400, 650, 550, 450]
}

// 方法
const toggleRegion = (regionId) => {
  if (expandedRegions.value.includes(regionId)) {
    expandedRegions.value = expandedRegions.value.filter(id => id !== regionId)
  } else {
    expandedRegions.value.push(regionId)
  }
}

const selectRegion = (regionId) => {
  selectedRegion.value = regionId
  // 当选择区域变化时，重新渲染图表
  renderCharts()
}

const initCharts = () => {
  if (basicChartRef.value) {
    basicChart = echarts.init(basicChartRef.value)
  }
  if (resultChartRef.value) {
    resultChart = echarts.init(resultChartRef.value)
  }
  renderCharts()
}

const renderCharts = () => {
  if (basicChart) {
    const basicOption = {
      title: {
        text: '行政区基本调查情况',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      legend: {
        data: ['人口', '户数', '一般防治区', '重点防治区'],
        top: 30
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          data: basicSurveyData.cities,
          axisTick: {
            alignWithLabel: true
          }
        }
      ],
      yAxis: [
        {
          type: 'value',
          name: '人口（人）',
          min: 0,
          max: 2500000,
          axisLabel: {
            formatter: '{value}'
          }
        },
        {
          type: 'value',
          name: '户数/防治区',
          min: 0,
          max: 1000000,
          axisLabel: {
            formatter: '{value}'
          }
        }
      ],
      series: [
        {
          name: '人口',
          type: 'bar',
          data: basicSurveyData.population,
          itemStyle: {
            color: '#1890ff'
          }
        },
        {
          name: '户数',
          type: 'bar',
          data: basicSurveyData.households,
          itemStyle: {
            color: '#722ed1'
          }
        },
        {
          name: '一般防治区',
          type: 'bar',
          data: basicSurveyData.generalZones,
          itemStyle: {
            color: '#fa8c16'
          }
        },
        {
          name: '重点防治区',
          type: 'bar',
          data: basicSurveyData.keyZones,
          itemStyle: {
            color: '#52c41a'
          }
        }
      ]
    }
    basicChart.setOption(basicOption)
  }

  if (resultChart) {
    const resultOption = {
      title: {
        text: '调查成果',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      legend: {
        data: ['沿河村落', '危险区', '重要城镇', '企事业单位', '历史灾害', '横断面', '纵断面'],
        top: 30
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          data: surveyResultData.cities,
          axisTick: {
            alignWithLabel: true
          }
        }
      ],
      yAxis: [
        {
          type: 'value',
          name: '户数',
          min: 0,
          max: 3500,
          axisLabel: {
            formatter: '{value}'
          }
        },
        {
          type: 'value',
          name: '次数',
          min: 0,
          max: 1500,
          axisLabel: {
            formatter: '{value}'
          }
        }
      ],
      series: [
        {
          name: '沿河村落',
          type: 'bar',
          data: surveyResultData.riversideVillages,
          itemStyle: {
            color: '#1890ff'
          }
        },
        {
          name: '危险区',
          type: 'bar',
          data: surveyResultData.dangerZones,
          itemStyle: {
            color: '#722ed1'
          }
        },
        {
          name: '重要城镇',
          type: 'bar',
          data: surveyResultData.importantTowns,
          itemStyle: {
            color: '#fa8c16'
          }
        },
        {
          name: '企事业单位',
          type: 'bar',
          data: surveyResultData.enterprises,
          itemStyle: {
            color: '#52c41a'
          }
        },
        {
          name: '历史灾害',
          type: 'bar',
          data: surveyResultData.historicalDisasters,
          itemStyle: {
            color: '#faad14'
          }
        },
        {
          name: '横断面',
          type: 'bar',
          data: surveyResultData.crossSections,
          itemStyle: {
            color: '#f5222d'
          }
        },
        {
          name: '纵断面',
          type: 'bar',
          data: surveyResultData.longitudinalSections,
          itemStyle: {
            color: '#13c2c2'
          }
        }
      ]
    }
    resultChart.setOption(resultOption)
  }
}

const handleResize = () => {
  if (basicChart) {
    basicChart.resize()
  }
  if (resultChart) {
    resultChart.resize()
  }
}

// 生命周期
onMounted(() => {
  initCharts()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  if (basicChart) {
    basicChart.dispose()
  }
  if (resultChart) {
    resultChart.dispose()
  }
  window.removeEventListener('resize', handleResize)
})

// 监听视图模式变化
watch(() => viewMode.value, () => {
  // 当视图模式变化时，可以添加相应的处理逻辑
  renderCharts()
})
</script>

<style scoped>
.survey-summary {
  display: flex;
  height: 100vh;
  background-color: #f5f5f5;
}

/* 左侧导航栏 */
.sidebar {
  width: 300px;
  background-color: white;
  border-right: 1px solid #e8e8e8;
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.08);
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid #e8e8e8;
}

.sidebar-header h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.search-box {
  padding: 16px 20px;
  border-bottom: 1px solid #e8e8e8;
}

.search-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.3s;
}

.search-input:focus {
  outline: none;
  border-color: #1890ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
}

.region-tree {
  flex: 1;
  overflow-y: auto;
  padding: 16px 0;
}

.tree-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.tree-item {
  margin-bottom: 8px;
}

.tree-node {
  display: flex;
  align-items: center;
  padding: 10px 20px;
  cursor: pointer;
  transition: all 0.3s;
  border-radius: 4px;
  margin: 0 12px;
}

.tree-node:hover {
  background-color: #f0f7ff;
}

.tree-toggle {
  width: 20px;
  font-size: 12px;
  color: #666;
  margin-right: 8px;
}

.region-name {
  font-size: 14px;
  color: #333;
}

.tree-children {
  list-style: none;
  padding: 0;
  margin: 0;
  padding-left: 30px;
}

.tree-child-item {
  margin-bottom: 4px;
}

.tree-child-item.active .tree-node {
  background-color: #e6f7ff;
  color: #1890ff;
}

.tree-child-item.active .region-name {
  color: #1890ff;
  font-weight: 500;
}

/* 右侧主内容区 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
}

.content-header {
  padding: 30px;
  background-color: white;
  border-bottom: 1px solid #e8e8e8;
  text-align: center;
}

.content-header h1 {
  margin: 0 0 20px 0;
  font-size: 28px;
  font-weight: 600;
  color: #333;
}

.view-toggle {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
}

.toggle-label {
  font-size: 14px;
  color: #666;
}

.toggle-buttons {
  display: flex;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  overflow: hidden;
}

.toggle-btn {
  padding: 8px 16px;
  border: none;
  background-color: white;
  color: #666;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s;
}

.toggle-btn:hover {
  color: #1890ff;
}

.toggle-btn.active {
  background-color: #1890ff;
  color: white;
}

.chart-section {
  margin: 20px;
  padding: 24px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.chart-section h2 {
  margin: 0 0 24px 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.chart-container {
  width: 100%;
  height: 400px;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .sidebar {
    width: 260px;
  }
  
  .chart-container {
    height: 350px;
  }
}

@media (max-width: 768px) {
  .survey-summary {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
    height: 300px;
    border-right: none;
    border-bottom: 1px solid #e8e8e8;
  }
  
  .main-content {
    flex: 1;
  }
  
  .content-header {
    padding: 20px;
  }
  
  .content-header h1 {
    font-size: 24px;
  }
  
  .chart-section {
    margin: 12px;
    padding: 16px;
  }
  
  .chart-container {
    height: 300px;
  }
}

@media (max-width: 480px) {
  .sidebar {
    height: 250px;
  }
  
  .sidebar-header {
    padding: 16px;
  }
  
  .sidebar-header h3 {
    font-size: 16px;
  }
  
  .search-box {
    padding: 12px 16px;
  }
  
  .tree-node {
    padding: 8px 16px;
  }
  
  .content-header {
    padding: 16px;
  }
  
  .content-header h1 {
    font-size: 20px;
  }
  
  .view-toggle {
    flex-direction: column;
    align-items: center;
    gap: 8px;
  }
  
  .chart-container {
    height: 250px;
  }
}
</style>