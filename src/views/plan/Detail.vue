<template>
  <div class="plan-detail-container">
    <h2>预案详情</h2>
    <div class="plan-detail-content">
      <div class="plan-header">
        <div class="plan-title">
          <h3>{{ planDetail.title }}</h3>
          <span class="plan-code">预案编号：{{ planDetail.code }}</span>
        </div>
        <div class="plan-meta">
          <span class="plan-status" :class="planDetail.status">
            {{ planDetail.status === 'active' ? '启用' : '停用' }}
          </span>
          <span class="plan-date">发布日期：{{ planDetail.publishDate }}</span>
          <span class="plan-version">版本：{{ planDetail.version }}</span>
        </div>
      </div>
      
      <div class="plan-body">
        <div class="section">
          <h4>1. 预案概述</h4>
          <div class="section-content">
            <p>{{ planDetail.overview }}</p>
          </div>
        </div>
        
        <div class="section">
          <h4>2. 适用范围</h4>
          <div class="section-content">
            <ul>
              <li v-for="(scope, index) in planDetail.scope" :key="index">
                {{ scope }}
              </li>
            </ul>
          </div>
        </div>
        
        <div class="section">
          <h4>3. 组织机构</h4>
          <div class="section-content">
            <div class="org-structure">
              <div class="org-level">
                <div class="org-unit">
                  <h5>总指挥</h5>
                  <p>{{ planDetail.organization.commander }}</p>
                </div>
              </div>
              <div class="org-level">
                <div class="org-unit">
                  <h5>副总指挥</h5>
                  <p>{{ planDetail.organization.deputyCommander }}</p>
                </div>
                <div class="org-unit">
                  <h5>技术组</h5>
                  <p>{{ planDetail.organization.technicalTeam }}</p>
                </div>
                <div class="org-unit">
                  <h5>抢险组</h5>
                  <p>{{ planDetail.organization.rescueTeam }}</p>
                </div>
                <div class="org-unit">
                  <h5>后勤组</h5>
                  <p>{{ planDetail.organization.logisticsTeam }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="section">
          <h4>4. 预警响应</h4>
          <div class="section-content">
            <div class="response-levels">
              <div class="response-level" v-for="(level, index) in planDetail.responseLevels" :key="index">
                <h5>{{ level.name }}</h5>
                <p>{{ level.description }}</p>
                <ul>
                  <li v-for="(measure, idx) in level.measures" :key="idx">
                    {{ measure }}
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        
        <div class="section">
          <h4>5. 应急处置</h4>
          <div class="section-content">
            <ol>
              <li v-for="(step, index) in planDetail.disposalSteps" :key="index">
                <strong>{{ step.title }}</strong>
                <p>{{ step.description }}</p>
              </li>
            </ol>
          </div>
        </div>
        
        <div class="section">
          <h4>6. 保障措施</h4>
          <div class="section-content">
            <ul>
              <li v-for="(measure, index) in planDetail.supportMeasures" :key="index">
                {{ measure }}
              </li>
            </ul>
          </div>
        </div>
        
        <div class="section">
          <h4>7. 附则</h4>
          <div class="section-content">
            <p>{{ planDetail.appendix }}</p>
          </div>
        </div>
      </div>
      
      <div class="plan-actions">
        <button class="btn btn-primary">编辑预案</button>
        <button class="btn btn-secondary">下载预案</button>
        <button class="btn btn-outline" :class="planDetail.status === 'active' ? 'btn-warning' : 'btn-success'">
          {{ planDetail.status === 'active' ? '停用预案' : '启用预案' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const planDetail = ref({
  title: '山洪灾害应急预案',
  code: 'SHSY-2024-001',
  status: 'active',
  publishDate: '2024-01-15',
  version: '1.0',
  overview: '为有效应对山洪灾害，保障人民生命财产安全，制定本预案。本预案适用于本地区发生的山洪灾害应急处置工作。',
  scope: [
    '本地区行政区域内的山洪灾害',
    '可能影响本地区的相邻区域山洪灾害',
    '其他需要本预案响应的山洪灾害事件'
  ],
  organization: {
    commander: '张三（县长）',
    deputyCommander: '李四（副县长）',
    technicalTeam: '王五（水利局总工）',
    rescueTeam: '赵六（应急局局长）',
    logisticsTeam: '孙七（财政局局长）'
  },
  responseLevels: [
    {
      name: 'Ⅳ级（一般）',
      description: '降雨量达到预警阈值，可能发生轻度山洪灾害',
      measures: [
        '加强监测预警',
        '做好转移准备',
        '通知相关部门待命'
      ]
    },
    {
      name: 'Ⅲ级（较重）',
      description: '降雨量超过预警阈值，可能发生中度山洪灾害',
      measures: [
        '启动预警响应',
        '组织危险区域人员转移',
        '调集救援力量待命'
      ]
    },
    {
      name: 'Ⅱ级（严重）',
      description: '降雨量显著超过预警阈值，可能发生严重山洪灾害',
      measures: [
        '启动应急响应',
        '全面组织人员转移',
        '调集各类救援力量'
      ]
    },
    {
      name: 'Ⅰ级（特别严重）',
      description: '降雨量极大，已经发生严重山洪灾害',
      measures: [
        '启动最高级别应急响应',
        '全力组织抢险救援',
        '请求上级支援'
      ]
    }
  ],
  disposalSteps: [
    {
      title: '信息报告',
      description: '发现山洪灾害迹象后，立即向应急指挥中心报告，报告内容包括灾害发生时间、地点、影响范围、初步损失等。'
    },
    {
      title: '先期处置',
      description: '事发地乡镇、村（居）委会立即组织人员开展先期处置，转移危险区域群众，设置警示标志，疏导交通。'
    },
    {
      title: '应急响应',
      description: '根据灾害等级，启动相应级别的应急响应，成立现场指挥部，统一指挥抢险救援工作。'
    },
    {
      title: '抢险救援',
      description: '组织专业救援队伍开展抢险救援，重点做好人员搜救、防洪堤加固、排水除涝等工作。'
    },
    {
      title: '灾后恢复',
      description: '灾害结束后，及时开展灾情评估，组织灾后恢复重建，修复受损基础设施，恢复生产生活秩序。'
    }
  ],
  supportMeasures: [
    '资金保障：设立山洪灾害应急专项资金，确保应急处置需要。',
    '物资保障：建立应急物资储备库，储备必要的抢险救援物资。',
    '人员保障：组建专业抢险救援队伍，定期开展培训和演练。',
    '技术保障：建立山洪灾害监测预警系统，提高预警预报能力。',
    '通信保障：确保应急通信畅通，建立多渠道通信联络机制。'
  ],
  appendix: '本预案由县应急管理局负责解释，自发布之日起施行。'
})
</script>

<style scoped>
.plan-detail-container {
  padding: 20px;
}

h2 {
  color: var(--primary-color);
  margin-bottom: 20px;
  font-size: 24px;
}

.plan-detail-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.plan-header {
  background-color: var(--light-bg);
  padding: 20px;
  border-bottom: 1px solid var(--border-color);
}

.plan-title h3 {
  color: var(--primary-color);
  margin-bottom: 5px;
  font-size: 20px;
}

.plan-code {
  color: var(--text-muted);
  font-size: 14px;
}

.plan-meta {
  display: flex;
  gap: 20px;
  margin-top: 10px;
  flex-wrap: wrap;
}

.plan-status {
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.plan-status.active {
  background-color: #e8f5e8;
  color: #2e7d32;
}

.plan-status.inactive {
  background-color: #ffebee;
  color: #c62828;
}

.plan-date,
.plan-version {
  color: var(--text-muted);
  font-size: 14px;
}

.plan-body {
  padding: 20px;
}

.section {
  margin-bottom: 30px;
}

.section h4 {
  color: var(--secondary-color);
  margin-bottom: 15px;
  font-size: 16px;
  border-left: 4px solid var(--primary-color);
  padding-left: 10px;
}

.section-content {
  color: var(--text-color);
  line-height: 1.6;
}

.section-content p {
  margin-bottom: 10px;
}

.section-content ul,
.section-content ol {
  margin-left: 20px;
  margin-bottom: 15px;
}

.section-content li {
  margin-bottom: 5px;
}

.org-structure {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.org-level {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.org-unit {
  background-color: var(--light-bg);
  padding: 15px;
  border-radius: 4px;
  flex: 1;
  min-width: 200px;
}

.org-unit h5 {
  color: var(--primary-color);
  margin-bottom: 5px;
  font-size: 14px;
}

.org-unit p {
  color: var(--text-color);
  font-size: 14px;
  margin: 0;
}

.response-levels {
  display: grid;
  gap: 15px;
}

.response-level {
  background-color: var(--light-bg);
  padding: 15px;
  border-radius: 4px;
}

.response-level h5 {
  color: var(--primary-color);
  margin-bottom: 10px;
  font-size: 14px;
}

.response-level p {
  margin-bottom: 10px;
  font-size: 14px;
}

.response-level ul {
  margin-left: 20px;
  margin-bottom: 0;
}

.response-level li {
  font-size: 14px;
  margin-bottom: 3px;
}

.plan-actions {
  padding: 20px;
  background-color: var(--light-bg);
  border-top: 1px solid var(--border-color);
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}

.btn {
  padding: 8px 16px;
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

.btn-warning {
  border-color: #ff9800;
  color: #ff9800;
}

.btn-warning:hover {
  background-color: #fff3e0;
}

.btn-success {
  border-color: #4caf50;
  color: #4caf50;
}

.btn-success:hover {
  background-color: #e8f5e8;
}

@media (max-width: 768px) {
  .plan-meta {
    flex-direction: column;
    gap: 5px;
  }
  
  .org-level {
    flex-direction: column;
  }
  
  .org-unit {
    min-width: 100%;
  }
  
  .plan-actions {
    flex-direction: column;
  }
  
  .btn {
    width: 100%;
  }
}
</style>