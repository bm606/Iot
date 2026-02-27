import { defineStore } from 'pinia'

export const useMapStore = defineStore('map', {
  state: () => ({
    // 地图实例
    mapInstance: null,
    // 图层控制状态
    layers: {
      satellite: true,  // 卫星云图
      rainfall: false,  // 降雨图层
      waterLevel: false,  // 水位图层
      warning: false  // 预警图层
    },
    // 地图中心坐标
    center: [112.982279, 28.19409],  // 默认长沙坐标
    // 地图缩放级别
    zoom: 8,
    // 地图加载状态
    loading: false
  }),
  
  actions: {
    // 设置地图实例
    setMapInstance(instance) {
      this.mapInstance = instance
    },
    
    // 切换图层显示状态
    toggleLayer(layerName) {
      this.layers[layerName] = !this.layers[layerName]
    },
    
    // 设置图层显示状态
    setLayerStatus(layerName, status) {
      this.layers[layerName] = status
    },
    
    // 更新地图中心
    updateCenter(center) {
      this.center = center
    },
    
    // 更新地图缩放级别
    updateZoom(zoom) {
      this.zoom = zoom
    },
    
    // 设置地图加载状态
    setLoading(status) {
      this.loading = status
    }
  }
})
