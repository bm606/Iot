<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue'
import { Map, View } from 'ol'
import { Tile as TileLayer } from 'ol/layer'
import { XYZ } from 'ol/source'
import { Zoom, Rotate, Attribution } from 'ol/control'
import { fromLonLat } from 'ol/proj'
import { useMapStore } from '../store/map'

// 地图容器引用
const mapContainer = ref(null)
// 地图实例
let map = null
// 地图图层
const layers = ref({})
// 地图状态管理
const mapStore = useMapStore()

// 初始化地图
const initMap = () => {
  if (!mapContainer.value) return
  
  // 设置地图加载状态
  mapStore.setLoading(true)
  
  // 创建天地图卫星云图底图
  const satelliteLayer = new TileLayer({
    source: new XYZ({
      url: 'http://t0.tianditu.gov.cn/img_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=img&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk=5c12efb41f685220439aee145275f41e',
      crossOrigin: 'anonymous'
    }),
    visible: true,
    name: 'satellite'
  })
  
  // 创建天地图标注图层
  const annotationLayer = new TileLayer({
    source: new XYZ({
      url: 'http://t0.tianditu.gov.cn/cia_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cia&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk=5c12efb41f685220439aee145275f41e',
      crossOrigin: 'anonymous'
    }),
    visible: true,
    name: 'annotation'
  })
  
  // 存储图层引用
  layers.value = {
    satellite: satelliteLayer,
    annotation: annotationLayer
  }
  
  // 创建地图实例
  map = new Map({
    target: mapContainer.value,
    layers: [satelliteLayer, annotationLayer],
    view: new View({
      center: fromLonLat([112.982279, 28.19409]),
      zoom: mapStore.zoom,
      minZoom: 3,
      maxZoom: 18
    }),
    controls: [
      new Zoom(),
      new Rotate(),
      new Attribution({
        collapsible: false
      })
    ]
  })
  
  // 存储地图实例到状态管理
  mapStore.setMapInstance(map)
  
  // 监听地图变化
  map.on('moveend', () => {
    const view = map.getView()
    mapStore.updateCenter(view.getCenter())
    mapStore.updateZoom(view.getZoom())
  })
  
  // 设置地图加载完成状态
  setTimeout(() => {
    mapStore.setLoading(false)
  }, 1000)
}

// 切换图层显示状态
const toggleLayer = (layerName) => {
  if (layers.value[layerName]) {
    const layer = layers.value[layerName]
    const currentVisible = layer.getVisible()
    layer.setVisible(!currentVisible)
    mapStore.setLayerStatus(layerName, !currentVisible)
  }
}

// 监听图层状态变化
watch(
  () => mapStore.layers,
  (newLayers) => {
    Object.keys(newLayers).forEach(layerName => {
      if (layers.value[layerName]) {
        layers.value[layerName].setVisible(newLayers[layerName])
      }
    })
  },
  { deep: true }
)

// 生命周期钩子
onMounted(() => {
  initMap()
})

onUnmounted(() => {
  if (map) {
    map.dispose()
  }
})
</script>

<template>
  <div class="map-component">
    <!-- 地图容器 -->
    <div 
      ref="mapContainer" 
      class="map-container"
      :class="{ 'loading': mapStore.loading }"
    >
      <!-- 加载动画 -->
      <div v-if="mapStore.loading" class="map-loading">
        <div class="loading-spinner"></div>
        <p>地图加载中...</p>
      </div>
    </div>
    
    <!-- 图层控制 -->
    <div class="layer-control">
      <h3>图层控制</h3>
      <div class="layer-item">
        <input 
          type="checkbox" 
          id="layer-satellite" 
          v-model="mapStore.layers.satellite"
          @change="toggleLayer('satellite')"
        />
        <label for="layer-satellite">卫星云图</label>
      </div>
      <div class="layer-item">
        <input 
          type="checkbox" 
          id="layer-rainfall" 
          v-model="mapStore.layers.rainfall"
          @change="toggleLayer('rainfall')"
        />
        <label for="layer-rainfall">降雨图层</label>
      </div>
      <div class="layer-item">
        <input 
          type="checkbox" 
          id="layer-waterLevel" 
          v-model="mapStore.layers.waterLevel"
          @change="toggleLayer('waterLevel')"
        />
        <label for="layer-waterLevel">水位图层</label>
      </div>
      <div class="layer-item">
        <input 
          type="checkbox" 
          id="layer-warning" 
          v-model="mapStore.layers.warning"
          @change="toggleLayer('warning')"
        />
        <label for="layer-warning">预警图层</label>
      </div>
    </div>
  </div>
</template>

<style scoped>
.map-component {
  position: relative;
  width: 100%;
  height: 100%;
}

.map-container {
  width: 100%;
  height: 100%;
  border-radius: 8px;
  overflow: hidden;
  position: relative;
}

.map-container.loading {
  background-color: rgba(255, 255, 255, 0.8);
}

.map-loading {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  z-index: 1000;
}

.loading-spinner {
  border: 4px solid rgba(30, 136, 229, 0.3);
  border-radius: 50%;
  border-top: 4px solid var(--primary-color);
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin: 0 auto 10px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.layer-control {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 8px;
  padding: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 100;
  min-width: 150px;
}

.layer-control h3 {
  font-size: 0.9rem;
  font-weight: 600;
  margin: 0 0 10px 0;
  color: var(--text-primary);
}

.layer-item {
  margin-bottom: 8px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.layer-item input[type="checkbox"] {
  width: 16px;
  height: 16px;
  cursor: pointer;
}

.layer-item label {
  font-size: 0.85rem;
  color: var(--text-secondary);
  cursor: pointer;
  user-select: none;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .layer-control {
    top: 50px;
    right: 10px;
    min-width: 130px;
    padding: 10px;
    background-color: rgba(255, 255, 255, 0.95);
  }
  
  .layer-control h3 {
    font-size: 0.8rem;
    margin-bottom: 8px;
  }
  
  .layer-item label {
    font-size: 0.8rem;
  }
  
  .map-container {
    min-height: 300px;
  }
}

@media (max-width: 480px) {
  .layer-control {
    top: 40px;
    right: 5px;
    min-width: 110px;
    padding: 8px;
  }
  
  .layer-control h3 {
    font-size: 0.75rem;
    margin-bottom: 6px;
  }
  
  .layer-item {
    margin-bottom: 6px;
  }
  
  .layer-item label {
    font-size: 0.75rem;
  }
  
  .map-container {
    min-height: 250px;
  }
}
</style>
