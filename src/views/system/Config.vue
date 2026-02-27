<template>
  <div class="system-container">
    <h2>系统配置</h2>
    <div class="system-content">
      <div class="config-panel">
        <div class="config-tabs">
          <button 
            v-for="tab in configTabs" 
            :key="tab.id"
            :class="['tab-btn', { active: activeTab === tab.id }]"
            @click="activeTab = tab.id"
          >
            {{ tab.name }}
          </button>
        </div>
        
        <div class="config-content">
          <div v-if="activeTab === 'basic'" class="tab-content">
            <h3>基本配置</h3>
            <div class="form-group">
              <label>系统名称</label>
              <input type="text" v-model="basicConfig.systemName" placeholder="请输入系统名称">
            </div>
            <div class="form-group">
              <label>系统版本</label>
              <input type="text" v-model="basicConfig.systemVersion" placeholder="请输入系统版本">
            </div>
            <div class="form-group">
              <label>系统描述</label>
              <textarea v-model="basicConfig.systemDescription" placeholder="请输入系统描述" rows="3"></textarea>
            </div>
            <div class="form-group">
              <label>联系邮箱</label>
              <input type="email" v-model="basicConfig.contactEmail" placeholder="请输入联系邮箱">
            </div>
            <div class="form-group">
              <label>联系电话</label>
              <input type="tel" v-model="basicConfig.contactPhone" placeholder="请输入联系电话">
            </div>
            <div class="form-actions">
              <button class="btn btn-primary" @click="saveBasicConfig">保存配置</button>
              <button class="btn btn-outline" @click="resetBasicConfig">重置</button>
            </div>
          </div>
          
          <div v-else-if="activeTab === 'map'" class="tab-content">
            <h3>地图配置</h3>
            <div class="form-group">
              <label>默认地图类型</label>
              <select v-model="mapConfig.defaultMapType">
                <option value="satellite">卫星地图</option>
                <option value="street">街道地图</option>
                <option value="terrain">地形地图</option>
              </select>
            </div>
            <div class="form-group">
              <label>默认缩放级别</label>
              <input type="number" v-model.number="mapConfig.defaultZoom" min="1" max="20" step="1">
            </div>
            <div class="form-group">
              <label>默认中心点经度</label>
              <input type="number" v-model.number="mapConfig.defaultCenterLng" step="0.000001">
            </div>
            <div class="form-group">
              <label>默认中心点纬度</label>
              <input type="number" v-model.number="mapConfig.defaultCenterLat" step="0.000001">
            </div>
            <div class="form-group">
              <label>
                <input type="checkbox" v-model="mapConfig.showScale">
                显示比例尺
              </label>
            </div>
            <div class="form-group">
              <label>
                <input type="checkbox" v-model="mapConfig.showCompass">
                显示指南针
              </label>
            </div>
            <div class="form-actions">
              <button class="btn btn-primary" @click="saveMapConfig">保存配置</button>
              <button class="btn btn-outline" @click="resetMapConfig">重置</button>
            </div>
          </div>
          
          <div v-else-if="activeTab === 'alert'" class="tab-content">
            <h3>预警配置</h3>
            <div class="form-group">
              <label>预警默认有效期 (小时)</label>
              <input type="number" v-model.number="alertConfig.defaultDuration" min="1" max="72" step="1">
            </div>
            <div class="form-group">
              <label>预警重复发送间隔 (分钟)</label>
              <input type="number" v-model.number="alertConfig.repeatInterval" min="5" max="1440" step="5">
            </div>
            <div class="form-group">
              <label>预警声音</label>
              <select v-model="alertConfig.alertSound">
                <option value="default">默认声音</option>
                <option value="warning">警告声音</option>
                <option value="alarm">警报声音</option>
                <option value="none">无声音</option>
              </select>
            </div>
            <div class="form-group">
              <label>
                <input type="checkbox" v-model="alertConfig.enableEmailAlert">
                启用邮件预警
              </label>
            </div>
            <div class="form-group">
              <label>
                <input type="checkbox" v-model="alertConfig.enableSmsAlert">
                启用短信预警
              </label>
            </div>
            <div class="form-actions">
              <button class="btn btn-primary" @click="saveAlertConfig">保存配置</button>
              <button class="btn btn-outline" @click="resetAlertConfig">重置</button>
            </div>
          </div>
          
          <div v-else-if="activeTab === 'data'" class="tab-content">
            <h3>数据配置</h3>
            <div class="form-group">
              <label>数据采集间隔 (分钟)</label>
              <input type="number" v-model.number="dataConfig.collectInterval" min="1" max="60" step="1">
            </div>
            <div class="form-group">
              <label>数据保存期限 (天)</label>
              <input type="number" v-model.number="dataConfig.saveDays" min="1" max="3650" step="1">
            </div>
            <div class="form-group">
              <label>数据备份间隔 (小时)</label>
              <input type="number" v-model.number="dataConfig.backupInterval" min="1" max="72" step="1">
            </div>
            <div class="form-group">
              <label>数据导出格式</label>
              <select v-model="dataConfig.exportFormat">
                <option value="excel">Excel</option>
                <option value="csv">CSV</option>
                <option value="pdf">PDF</option>
              </select>
            </div>
            <div class="form-actions">
              <button class="btn btn-primary" @click="saveDataConfig">保存配置</button>
              <button class="btn btn-outline" @click="resetDataConfig">重置</button>
            </div>
          </div>
          
          <div v-else-if="activeTab === 'security'" class="tab-content">
            <h3>安全配置</h3>
            <div class="form-group">
              <label>登录超时时间 (分钟)</label>
              <input type="number" v-model.number="securityConfig.loginTimeout" min="1" max="480" step="5">
            </div>
            <div class="form-group">
              <label>密码复杂度要求</label>
              <select v-model="securityConfig.passwordComplexity">
                <option value="low">低</option>
                <option value="medium">中</option>
                <option value="high">高</option>
              </select>
            </div>
            <div class="form-group">
              <label>密码有效期 (天)</label>
              <input type="number" v-model.number="securityConfig.passwordExpiry" min="1" max="365" step="1">
            </div>
            <div class="form-group">
              <label>
                <input type="checkbox" v-model="securityConfig.enableIpRestriction">
                启用IP限制
              </label>
            </div>
            <div class="form-group">
              <label>
                <input type="checkbox" v-model="securityConfig.enableTwoFactorAuth">
                启用双因素认证
              </label>
            </div>
            <div class="form-actions">
              <button class="btn btn-primary" @click="saveSecurityConfig">保存配置</button>
              <button class="btn btn-outline" @click="resetSecurityConfig">重置</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const activeTab = ref('basic')

const configTabs = [
  { id: 'basic', name: '基本配置' },
  { id: 'map', name: '地图配置' },
  { id: 'alert', name: '预警配置' },
  { id: 'data', name: '数据配置' },
  { id: 'security', name: '安全配置' }
]

const basicConfig = ref({
  systemName: '山洪四预系统',
  systemVersion: '1.0.0',
  systemDescription: '山洪灾害监测预警预报系统',
  contactEmail: 'admin@example.com',
  contactPhone: '12345678900'
})

const mapConfig = ref({
  defaultMapType: 'satellite',
  defaultZoom: 10,
  defaultCenterLng: 112.9834,
  defaultCenterLat: 28.1171,
  showScale: true,
  showCompass: true
})

const alertConfig = ref({
  defaultDuration: 24,
  repeatInterval: 30,
  alertSound: 'default',
  enableEmailAlert: false,
  enableSmsAlert: false
})

const dataConfig = ref({
  collectInterval: 5,
  saveDays: 365,
  backupInterval: 24,
  exportFormat: 'excel'
})

const securityConfig = ref({
  loginTimeout: 30,
  passwordComplexity: 'medium',
  passwordExpiry: 90,
  enableIpRestriction: false,
  enableTwoFactorAuth: false
})

const saveBasicConfig = () => {
  // 模拟保存配置功能
  alert('基本配置已保存')
}

const resetBasicConfig = () => {
  basicConfig.value = {
    systemName: '山洪四预系统',
    systemVersion: '1.0.0',
    systemDescription: '山洪灾害监测预警预报系统',
    contactEmail: 'admin@example.com',
    contactPhone: '12345678900'
  }
}

const saveMapConfig = () => {
  // 模拟保存配置功能
  alert('地图配置已保存')
}

const resetMapConfig = () => {
  mapConfig.value = {
    defaultMapType: 'satellite',
    defaultZoom: 10,
    defaultCenterLng: 112.9834,
    defaultCenterLat: 28.1171,
    showScale: true,
    showCompass: true
  }
}

const saveAlertConfig = () => {
  // 模拟保存配置功能
  alert('预警配置已保存')
}

const resetAlertConfig = () => {
  alertConfig.value = {
    defaultDuration: 24,
    repeatInterval: 30,
    alertSound: 'default',
    enableEmailAlert: false,
    enableSmsAlert: false
  }
}

const saveDataConfig = () => {
  // 模拟保存配置功能
  alert('数据配置已保存')
}

const resetDataConfig = () => {
  dataConfig.value = {
    collectInterval: 5,
    saveDays: 365,
    backupInterval: 24,
    exportFormat: 'excel'
  }
}

const saveSecurityConfig = () => {
  // 模拟保存配置功能
  alert('安全配置已保存')
}

const resetSecurityConfig = () => {
  securityConfig.value = {
    loginTimeout: 30,
    passwordComplexity: 'medium',
    passwordExpiry: 90,
    enableIpRestriction: false,
    enableTwoFactorAuth: false
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

.config-panel {
  padding: 20px;
}

.config-tabs {
  display: flex;
  margin-bottom: 20px;
  border-bottom: 1px solid var(--border-color);
}

.tab-btn {
  padding: 10px 20px;
  border: none;
  background-color: transparent;
  color: var(--text-muted);
  cursor: pointer;
  border-bottom: 2px solid transparent;
  transition: all 0.3s ease;
  font-size: 14px;
  font-weight: 500;
}

.tab-btn:hover {
  color: var(--primary-color);
}

.tab-btn.active {
  color: var(--primary-color);
  border-bottom-color: var(--primary-color);
  background-color: rgba(33, 150, 243, 0.05);
}

.config-content {
  min-height: 400px;
}

.tab-content h3 {
  color: var(--secondary-color);
  margin-bottom: 20px;
  font-size: 18px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  color: var(--text-color);
  font-weight: 500;
  font-size: 14px;
}

.form-group input[type="text"],
.form-group input[type="email"],
.form-group input[type="tel"],
.form-group input[type="number"],
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 14px;
  font-family: inherit;
}

.form-group textarea {
  resize: vertical;
}

.form-group input[type="checkbox"] {
  margin-right: 8px;
}

.form-actions {
  margin-top: 30px;
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

.btn-outline {
  background-color: transparent;
  border: 1px solid var(--border-color);
  color: var(--text-color);
}

.btn-outline:hover {
  background-color: var(--light-bg);
}

@media (max-width: 768px) {
  .config-tabs {
    flex-direction: column;
  }
  
  .tab-btn {
    text-align: left;
    border-bottom: none;
    border-left: 2px solid transparent;
  }
  
  .tab-btn.active {
    border-left-color: var(--primary-color);
    border-bottom-color: transparent;
  }
  
  .form-actions {
    flex-direction: column;
  }
  
  .btn {
    width: 100%;
  }
}
</style>