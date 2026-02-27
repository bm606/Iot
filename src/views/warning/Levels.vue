<script setup>
import { ref, onMounted } from 'vue'

// 模拟数据 - 预警等级
const warningLevels = ref([
  {
    id: 1,
    level: '蓝色',
    description: '预计未来24小时降雨量将达20mm以上',
    action: '密切关注降雨情况，做好防范准备',
    color: '#00bcd4'
  },
  {
    id: 2,
    level: '黄色',
    description: '预计未来12小时降雨量将达30mm以上',
    action: '加强监测，做好防范措施',
    color: '#ff9800'
  },
  {
    id: 3,
    level: '橙色',
    description: '预计未来6小时降雨量将达50mm以上',
    action: '启动应急预案，做好人员转移准备',
    color: '#ff5722'
  },
  {
    id: 4,
    level: '红色',
    description: '预计未来3小时降雨量将达100mm以上',
    action: '立即启动应急预案，组织人员转移',
    color: '#f44336'
  }
])

// 生命周期钩子
onMounted(() => {
  // 模拟数据加载
  console.log('预警等级数据加载完成')
})
</script>

<template>
  <div class="warning-levels">
    
    
    <!-- 预警等级列表 -->
    <div class="levels-grid">
      <div 
        v-for="level in warningLevels" 
        :key="level.id"
        class="level-card"
        :style="{ borderLeftColor: level.color }"
      >
        <div class="level-header">
          <h2 class="level-name">{{ level.level }}预警</h2>
          <div class="level-color" :style="{ backgroundColor: level.color }"></div>
        </div>
        <div class="level-content">
          <div class="level-description">
            <h3>预警标准</h3>
            <p>{{ level.description }}</p>
          </div>
          <div class="level-action">
            <h3>应对措施</h3>
            <p>{{ level.action }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.warning-levels {
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

/* 预警等级列表 */
.levels-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1.5rem;
  flex: 1;
}

.level-card {
  background-color: var(--background-white);
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  border-left: 4px solid;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.level-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.level-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.level-name {
  font-size: 1.3rem;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.level-color {
  width: 30px;
  height: 30px;
  border-radius: 50%;
}

.level-content {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.level-description h3,
.level-action h3 {
  font-size: 1rem;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0 0 0.5rem 0;
}

.level-description p,
.level-action p {
  font-size: 0.9rem;
  color: var(--text-secondary);
  margin: 0;
  line-height: 1.4;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .page-header h1 {
    font-size: 1.5rem;
  }
  
  .levels-grid {
    grid-template-columns: 1fr;
  }
  
  .level-card {
    padding: 1.25rem;
  }
  
  .level-name {
    font-size: 1.1rem;
  }
}
</style>
