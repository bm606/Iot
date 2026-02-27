<template>
  <div class="plan-edit-container">
    <h2>预案编辑</h2>
    <div class="plan-edit-content">
      <form class="plan-form">
        <div class="form-section">
          <h3>基本信息</h3>
          <div class="form-row">
            <div class="form-group">
              <label>预案名称 *</label>
              <input type="text" v-model="planForm.title" placeholder="请输入预案名称" required>
            </div>
            <div class="form-group">
              <label>预案编号 *</label>
              <input type="text" v-model="planForm.code" placeholder="请输入预案编号" required>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>发布日期 *</label>
              <input type="date" v-model="planForm.publishDate" required>
            </div>
            <div class="form-group">
              <label>版本号 *</label>
              <input type="text" v-model="planForm.version" placeholder="请输入版本号" required>
            </div>
          </div>
          <div class="form-group">
            <label>状态</label>
            <select v-model="planForm.status">
              <option value="active">启用</option>
              <option value="inactive">停用</option>
            </select>
          </div>
        </div>
        
        <div class="form-section">
          <h3>预案概述</h3>
          <div class="form-group">
            <textarea v-model="planForm.overview" placeholder="请输入预案概述" rows="4"></textarea>
          </div>
        </div>
        
        <div class="form-section">
          <h3>适用范围</h3>
          <div class="scope-list">
            <div v-for="(scope, index) in planForm.scope" :key="index" class="scope-item">
                <input type="text" v-model="planForm.scope[index]" placeholder="请输入适用范围">
                <button type="button" class="btn-remove" @click="removeScope(index)">-</button>
              </div>
            <button type="button" class="btn-add" @click="addScope">+ 添加适用范围</button>
          </div>
        </div>
        
        <div class="form-section">
          <h3>组织机构</h3>
          <div class="org-form">
            <div class="form-row">
              <div class="form-group">
                <label>总指挥</label>
                <input type="text" v-model="planForm.organization.commander" placeholder="请输入总指挥姓名及职务">
              </div>
              <div class="form-group">
                <label>副总指挥</label>
                <input type="text" v-model="planForm.organization.deputyCommander" placeholder="请输入副总指挥姓名及职务">
              </div>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>技术组</label>
                <input type="text" v-model="planForm.organization.technicalTeam" placeholder="请输入技术组负责人">
              </div>
              <div class="form-group">
                <label>抢险组</label>
                <input type="text" v-model="planForm.organization.rescueTeam" placeholder="请输入抢险组负责人">
              </div>
              <div class="form-group">
                <label>后勤组</label>
                <input type="text" v-model="planForm.organization.logisticsTeam" placeholder="请输入后勤组负责人">
              </div>
            </div>
          </div>
        </div>
        
        <div class="form-section">
          <h3>预警响应</h3>
          <div class="response-levels">
            <div v-for="(level, index) in planForm.responseLevels" :key="index" class="response-level">
              <h4>响应等级 {{ index + 1 }}</h4>
              <div class="form-row">
                <div class="form-group">
                  <label>等级名称</label>
                  <input type="text" v-model="level.name" placeholder="如：Ⅳ级（一般）">
                </div>
              </div>
              <div class="form-group">
                <label>描述</label>
                <textarea v-model="level.description" placeholder="请输入等级描述" rows="2"></textarea>
              </div>
              <div class="form-group">
                <label>措施</label>
                <div class="measure-list">
                  <div v-for="(measure, idx) in level.measures" :key="idx" class="measure-item">
                    <input type="text" v-model="level.measures[idx]" placeholder="请输入响应措施">
                    <button type="button" class="btn-remove" @click="removeMeasure(index, idx)">-</button>
                  </div>
                  <button type="button" class="btn-add" @click="addMeasure(index)">+ 添加措施</button>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="form-section">
          <h3>应急处置</h3>
          <div class="disposal-steps">
            <div v-for="(step, index) in planForm.disposalSteps" :key="index" class="disposal-step">
              <h4>处置步骤 {{ index + 1 }}</h4>
              <div class="form-row">
                <div class="form-group">
                  <label>步骤名称</label>
                  <input type="text" v-model="step.title" placeholder="如：信息报告">
                </div>
              </div>
              <div class="form-group">
                <label>描述</label>
                <textarea v-model="step.description" placeholder="请输入步骤描述" rows="3"></textarea>
              </div>
              <div class="step-actions">
                <button type="button" class="btn-remove" @click="removeStep(index)">删除步骤</button>
                <button type="button" class="btn-add" @click="addStep(index + 1)">在下方添加步骤</button>
              </div>
            </div>
            <button type="button" class="btn-add" @click="addStep()">+ 添加处置步骤</button>
          </div>
        </div>
        
        <div class="form-section">
          <h3>保障措施</h3>
          <div class="support-list">
            <div v-for="(measure, index) in planForm.supportMeasures" :key="index" class="support-item">
              <input type="text" v-model="planForm.supportMeasures[index]" placeholder="请输入保障措施">
              <button type="button" class="btn-remove" @click="removeSupportMeasure(index)">-</button>
            </div>
            <button type="button" class="btn-add" @click="addSupportMeasure">+ 添加保障措施</button>
          </div>
        </div>
        
        <div class="form-section">
          <h3>附则</h3>
          <div class="form-group">
            <textarea v-model="planForm.appendix" placeholder="请输入附则内容" rows="3"></textarea>
          </div>
        </div>
        
        <div class="form-actions">
          <button type="button" class="btn btn-secondary">取消</button>
          <button type="button" class="btn btn-primary">保存预案</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const planForm = ref({
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

const addScope = () => {
  planForm.value.scope.push('')
}

const removeScope = (index) => {
  planForm.value.scope.splice(index, 1)
}

const addMeasure = (levelIndex) => {
  planForm.value.responseLevels[levelIndex].measures.push('')
}

const removeMeasure = (levelIndex, measureIndex) => {
  planForm.value.responseLevels[levelIndex].measures.splice(measureIndex, 1)
}

const addStep = (index = planForm.value.disposalSteps.length) => {
  planForm.value.disposalSteps.splice(index, 0, {
    title: '',
    description: ''
  })
}

const removeStep = (index) => {
  planForm.value.disposalSteps.splice(index, 1)
}

const addSupportMeasure = () => {
  planForm.value.supportMeasures.push('')
}

const removeSupportMeasure = (index) => {
  planForm.value.supportMeasures.splice(index, 1)
}
</script>

<style scoped>
.plan-edit-container {
  padding: 20px;
}

h2 {
  color: var(--primary-color);
  margin-bottom: 20px;
  font-size: 24px;
}

.plan-edit-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.plan-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-section {
  border: 1px solid var(--border-color);
  border-radius: 8px;
  padding: 20px;
}

.form-section h3 {
  color: var(--primary-color);
  margin-bottom: 15px;
  font-size: 18px;
}

.form-row {
  display: flex;
  gap: 20px;
  margin-bottom: 15px;
  flex-wrap: wrap;
}

.form-group {
  flex: 1;
  min-width: 200px;
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: var(--text-color);
  font-weight: 500;
  font-size: 14px;
}

.form-group input[type="text"],
.form-group input[type="date"],
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 14px;
  font-family: inherit;
}

.form-group textarea {
  resize: vertical;
}

.scope-list,
.measure-list,
.support-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.scope-item,
.measure-item,
.support-item {
  display: flex;
  gap: 10px;
  align-items: center;
}

.scope-item input,
.measure-item input,
.support-item input {
  flex: 1;
}

.btn-remove {
  width: 30px;
  height: 30px;
  border: 1px solid var(--border-color);
  background-color: white;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.btn-remove:hover {
  background-color: #ffebee;
  border-color: #f44336;
  color: #f44336;
}

.btn-add {
  padding: 8px 16px;
  border: 1px solid var(--primary-color);
  background-color: white;
  color: var(--primary-color);
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
  align-self: flex-start;
}

.btn-add:hover {
  background-color: var(--primary-color);
  color: white;
}

.org-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.response-levels {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.response-level {
  border: 1px solid var(--border-color);
  border-radius: 4px;
  padding: 15px;
}

.response-level h4 {
  color: var(--secondary-color);
  margin-bottom: 10px;
  font-size: 16px;
}

.disposal-steps {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.disposal-step {
  border: 1px solid var(--border-color);
  border-radius: 4px;
  padding: 15px;
}

.disposal-step h4 {
  color: var(--secondary-color);
  margin-bottom: 10px;
  font-size: 16px;
}

.step-actions {
  display: flex;
  gap: 10px;
  margin-top: 10px;
  flex-wrap: wrap;
}

.form-actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  padding-top: 20px;
  border-top: 1px solid var(--border-color);
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
  background-color: var(--light-bg);
  color: var(--text-color);
  border: 1px solid var(--border-color);
}

.btn-secondary:hover {
  background-color: #e0e0e0;
}

@media (max-width: 768px) {
  .form-row {
    flex-direction: column;
    gap: 0;
  }
  
  .form-group {
    min-width: 100%;
  }
  
  .step-actions {
    flex-direction: column;
  }
  
  .btn-add {
    align-self: stretch;
  }
  
  .form-actions {
    flex-direction: column;
  }
  
  .btn {
    width: 100%;
  }
}
</style>