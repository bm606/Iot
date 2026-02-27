<template>
  <div class="data-container">
    <h2>数据导入</h2>
    <div class="data-content">
      <div class="import-panel">
        <div class="panel-header">
          <h3>导入数据</h3>
          <p class="panel-desc">支持导入降雨量、水位、流量等监测数据，支持Excel、CSV格式文件</p>
        </div>
        
        <div class="import-tabs">
          <button 
            v-for="tab in importTabs" 
            :key="tab.id"
            :class="['tab-btn', { active: activeTab === tab.id }]"
            @click="activeTab = tab.id"
          >
            {{ tab.name }}
          </button>
        </div>
        
        <div class="import-content">
          <div v-if="activeTab === 'rainfall'" class="tab-content">
            <div class="file-upload">
              <h4>降雨量数据导入</h4>
              <div class="upload-area" :class="{ dragover: isDragover }" @drop="handleDrop" @dragover.prevent="isDragover = true" @dragleave.prevent="isDragover = false">
                <input type="file" ref="fileInput" class="file-input" @change="handleFileSelect" accept=".xlsx,.xls,.csv">
                <div class="upload-hint">
                  <div class="hint-icon">📁</div>
                  <p>点击或拖拽文件到此处上传</p>
                  <p class="hint-small">支持 Excel (.xlsx, .xls) 和 CSV 格式</p>
                  <p class="hint-small">文件大小不超过 10MB</p>
                </div>
              </div>
              <div v-if="selectedFile" class="file-info">
                <span>{{ selectedFile.name }}</span>
                <button class="btn-remove" @click="removeFile">移除</button>
              </div>
            </div>
            
            <div class="import-options">
              <h4>导入选项</h4>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.override">
                  覆盖已有数据
                </label>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.validate">
                  数据验证
                </label>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.notify">
                  导入完成后通知
                </label>
              </div>
            </div>
            
            <div class="import-actions">
              <button class="btn btn-primary" @click="startImport" :disabled="!selectedFile">开始导入</button>
              <button class="btn btn-secondary" @click="downloadTemplate">下载模板</button>
              <button class="btn btn-outline" @click="previewData">预览数据</button>
            </div>
          </div>
          
          <div v-else-if="activeTab === 'water'" class="tab-content">
            <div class="file-upload">
              <h4>水位数据导入</h4>
              <div class="upload-area" :class="{ dragover: isDragover }" @drop="handleDrop" @dragover.prevent="isDragover = true" @dragleave.prevent="isDragover = false">
                <input type="file" ref="fileInput" class="file-input" @change="handleFileSelect" accept=".xlsx,.xls,.csv">
                <div class="upload-hint">
                  <div class="hint-icon">📁</div>
                  <p>点击或拖拽文件到此处上传</p>
                  <p class="hint-small">支持 Excel (.xlsx, .xls) 和 CSV 格式</p>
                  <p class="hint-small">文件大小不超过 10MB</p>
                </div>
              </div>
              <div v-if="selectedFile" class="file-info">
                <span>{{ selectedFile.name }}</span>
                <button class="btn-remove" @click="removeFile">移除</button>
              </div>
            </div>
            
            <div class="import-options">
              <h4>导入选项</h4>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.override">
                  覆盖已有数据
                </label>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.validate">
                  数据验证
                </label>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.notify">
                  导入完成后通知
                </label>
              </div>
            </div>
            
            <div class="import-actions">
              <button class="btn btn-primary" @click="startImport" :disabled="!selectedFile">开始导入</button>
              <button class="btn btn-secondary" @click="downloadTemplate">下载模板</button>
              <button class="btn btn-outline" @click="previewData">预览数据</button>
            </div>
          </div>
          
          <div v-else-if="activeTab === 'flow'" class="tab-content">
            <div class="file-upload">
              <h4>流量数据导入</h4>
              <div class="upload-area" :class="{ dragover: isDragover }" @drop="handleDrop" @dragover.prevent="isDragover = true" @dragleave.prevent="isDragover = false">
                <input type="file" ref="fileInput" class="file-input" @change="handleFileSelect" accept=".xlsx,.xls,.csv">
                <div class="upload-hint">
                  <div class="hint-icon">📁</div>
                  <p>点击或拖拽文件到此处上传</p>
                  <p class="hint-small">支持 Excel (.xlsx, .xls) 和 CSV 格式</p>
                  <p class="hint-small">文件大小不超过 10MB</p>
                </div>
              </div>
              <div v-if="selectedFile" class="file-info">
                <span>{{ selectedFile.name }}</span>
                <button class="btn-remove" @click="removeFile">移除</button>
              </div>
            </div>
            
            <div class="import-options">
              <h4>导入选项</h4>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.override">
                  覆盖已有数据
                </label>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.validate">
                  数据验证
                </label>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.notify">
                  导入完成后通知
                </label>
              </div>
            </div>
            
            <div class="import-actions">
              <button class="btn btn-primary" @click="startImport" :disabled="!selectedFile">开始导入</button>
              <button class="btn btn-secondary" @click="downloadTemplate">下载模板</button>
              <button class="btn btn-outline" @click="previewData">预览数据</button>
            </div>
          </div>
          
          <div v-else-if="activeTab === 'other'" class="tab-content">
            <div class="file-upload">
              <h4>其他数据导入</h4>
              <div class="upload-area" :class="{ dragover: isDragover }" @drop="handleDrop" @dragover.prevent="isDragover = true" @dragleave.prevent="isDragover = false">
                <input type="file" ref="fileInput" class="file-input" @change="handleFileSelect" accept=".xlsx,.xls,.csv">
                <div class="upload-hint">
                  <div class="hint-icon">📁</div>
                  <p>点击或拖拽文件到此处上传</p>
                  <p class="hint-small">支持 Excel (.xlsx, .xls) 和 CSV 格式</p>
                  <p class="hint-small">文件大小不超过 10MB</p>
                </div>
              </div>
              <div v-if="selectedFile" class="file-info">
                <span>{{ selectedFile.name }}</span>
                <button class="btn-remove" @click="removeFile">移除</button>
              </div>
            </div>
            
            <div class="import-options">
              <h4>导入选项</h4>
              <div class="form-group">
                <label>数据类型</label>
                <select v-model="otherDataType">
                  <option value="pressure">压力数据</option>
                  <option value="temperature">温度数据</option>
                  <option value="humidity">湿度数据</option>
                  <option value="wind">风速风向</option>
                  <option value="other">其他数据</option>
                </select>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.override">
                  覆盖已有数据
                </label>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.validate">
                  数据验证
                </label>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="importOptions.notify">
                  导入完成后通知
                </label>
              </div>
            </div>
            
            <div class="import-actions">
              <button class="btn btn-primary" @click="startImport" :disabled="!selectedFile">开始导入</button>
              <button class="btn btn-secondary" @click="downloadTemplate">下载模板</button>
              <button class="btn btn-outline" @click="previewData">预览数据</button>
            </div>
          </div>
        </div>
        
        <div v-if="importProgress > 0" class="import-progress">
          <div class="progress-bar">
            <div class="progress-fill" :style="{ width: importProgress + '%' }"></div>
          </div>
          <div class="progress-text">
            导入进度: {{ importProgress }}% - {{ importStatus }}
          </div>
        </div>
        
        <div v-if="importHistory.length > 0" class="import-history">
          <h4>导入历史</h4>
          <div class="history-list">
            <div v-for="(history, index) in importHistory" :key="index" class="history-item">
              <div class="history-info">
                <span class="history-type">{{ history.type }}</span>
                <span class="history-file">{{ history.fileName }}</span>
                <span class="history-time">{{ history.time }}</span>
              </div>
              <div class="history-status" :class="history.status">
                {{ history.status === 'success' ? '成功' : history.status === 'failed' ? '失败' : '进行中' }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const activeTab = ref('rainfall')
const isDragover = ref(false)
const selectedFile = ref(null)
const importProgress = ref(0)
const importStatus = ref('')
const otherDataType = ref('pressure')

const importTabs = [
  { id: 'rainfall', name: '降雨量' },
  { id: 'water', name: '水位' },
  { id: 'flow', name: '流量' },
  { id: 'other', name: '其他' }
]

const importOptions = ref({
  override: false,
  validate: true,
  notify: false
})

const importHistory = ref([
  {
    type: '降雨量',
    fileName: 'rainfall_data_202401.xlsx',
    time: '2024-01-20 10:30:00',
    status: 'success'
  },
  {
    type: '水位',
    fileName: 'water_level_data_202401.csv',
    time: '2024-01-19 16:45:00',
    status: 'success'
  },
  {
    type: '流量',
    fileName: 'flow_data_202401.xlsx',
    time: '2024-01-18 09:15:00',
    status: 'failed'
  }
])

const handleFileSelect = (event) => {
  const file = event.target.files[0]
  if (file) {
    selectedFile.value = file
  }
}

const handleDrop = (event) => {
  event.preventDefault()
  isDragover.value = false
  const file = event.dataTransfer.files[0]
  if (file) {
    selectedFile.value = file
  }
}

const removeFile = () => {
  selectedFile.value = null
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}

const startImport = () => {
  if (!selectedFile.value) return
  
  importProgress.value = 0
  importStatus.value = '开始导入...'
  
  // 模拟导入过程
  const interval = setInterval(() => {
    importProgress.value += 10
    if (importProgress.value < 50) {
      importStatus.value = '正在解析文件...'
    } else if (importProgress.value < 80) {
      importStatus.value = '正在验证数据...'
    } else if (importProgress.value < 95) {
      importStatus.value = '正在保存数据...'
    } else {
      importStatus.value = '导入完成'
    }
    
    if (importProgress.value >= 100) {
      clearInterval(interval)
      setTimeout(() => {
        importProgress.value = 0
        importStatus.value = ''
        removeFile()
        
        // 添加到历史记录
        importHistory.value.unshift({
          type: importTabs.find(tab => tab.id === activeTab.value).name,
          fileName: selectedFile.value.name,
          time: new Date().toLocaleString('zh-CN'),
          status: 'success'
        })
        
        alert('数据导入成功！')
      }, 1000)
    }
  }, 300)
}

const downloadTemplate = () => {
  // 模拟下载模板功能
  alert(`下载${importTabs.find(tab => tab.id === activeTab.value).name}数据模板`)
}

const previewData = () => {
  // 模拟预览数据功能
  alert('预览数据功能已触发')
}
</script>

<style scoped>
.data-container {
  padding: 20px;
}

h2 {
  color: var(--primary-color);
  margin-bottom: 20px;
  font-size: 24px;
}

.data-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.import-panel {
  padding: 20px;
}

.panel-header {
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid var(--border-color);
}

.panel-header h3 {
  color: var(--secondary-color);
  font-size: 18px;
  margin: 0 0 5px 0;
}

.panel-desc {
  color: var(--text-muted);
  font-size: 14px;
  margin: 0;
}

.import-tabs {
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

.import-content {
  margin-bottom: 20px;
}

.tab-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.file-upload h4,
.import-options h4,
.import-history h4 {
  color: var(--text-color);
  font-size: 16px;
  margin: 0 0 15px 0;
}

.upload-area {
  border: 2px dashed var(--border-color);
  border-radius: 8px;
  padding: 40px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
}

.upload-area:hover {
  border-color: var(--primary-color);
  background-color: rgba(33, 150, 243, 0.05);
}

.upload-area.dragover {
  border-color: var(--primary-color);
  background-color: rgba(33, 150, 243, 0.1);
}

.file-input {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
}

.upload-hint {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.hint-icon {
  font-size: 48px;
  opacity: 0.5;
}

.upload-hint p {
  margin: 0;
  color: var(--text-color);
}

.hint-small {
  font-size: 12px;
  color: var(--text-muted);
}

.file-info {
  margin-top: 15px;
  padding: 10px;
  background-color: var(--light-bg);
  border-radius: 4px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.btn-remove {
  padding: 4px 12px;
  border: 1px solid var(--border-color);
  background-color: white;
  border-radius: 4px;
  cursor: pointer;
  font-size: 12px;
  transition: all 0.3s ease;
}

.btn-remove:hover {
  background-color: #ffebee;
  border-color: #f44336;
  color: #f44336;
}

.import-options {
  background-color: var(--light-bg);
  padding: 20px;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--text-color);
  font-size: 14px;
  cursor: pointer;
}

.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  font-size: 14px;
  margin-top: 5px;
}

.import-actions {
  display: flex;
  gap: 10px;
  margin-top: 10px;
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

.btn-primary:hover:not(:disabled) {
  background-color: var(--primary-dark);
}

.btn-primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
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

.import-progress {
  margin: 20px 0;
  padding: 20px;
  background-color: var(--light-bg);
  border-radius: 8px;
}

.progress-bar {
  height: 20px;
  background-color: white;
  border-radius: 10px;
  overflow: hidden;
  margin-bottom: 10px;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
}

.progress-fill {
  height: 100%;
  background-color: var(--primary-color);
  transition: width 0.3s ease;
}

.progress-text {
  color: var(--text-color);
  font-size: 14px;
  text-align: center;
}

.import-history {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid var(--border-color);
}

.history-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.history-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: var(--light-bg);
  border-radius: 4px;
}

.history-info {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.history-type {
  padding: 2px 8px;
  background-color: #e3f2fd;
  color: #1976d2;
  border-radius: 12px;
  font-size: 12px;
}

.history-file {
  color: var(--text-color);
  font-size: 14px;
}

.history-time {
  color: var(--text-muted);
  font-size: 12px;
}

.history-status {
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.history-status.success {
  background-color: #e8f5e8;
  color: #2e7d32;
}

.history-status.failed {
  background-color: #ffebee;
  color: #f44336;
}

.history-status.processing {
  background-color: #fff3e0;
  color: #ff9800;
}

@media (max-width: 768px) {
  .import-tabs {
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
  
  .import-actions {
    flex-direction: column;
  }
  
  .btn {
    width: 100%;
  }
  
  .history-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .history-info {
    flex-direction: column;
    gap: 5px;
  }
}
</style>