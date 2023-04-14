<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
    <div style="flex: 1;">
      <el-image src="https://wallpapershome.com/images/pages/ico_h/24295.jpg" style="width: 100%;height: 100%" fit="cover"/>
    </div>
    <div class="welcome-title">
      <div style="font-size: 50px; font-weight: bold">基于课堂视频的教师运动轨迹和姿态分析工具</div>
    </div>
    <div style="width: 400px;background-color: white;z-index: 1">
      <router-view v-slot="{ Component }">
        <transition name="el-fade-in-linear" mode="out-in">
          <component :is="Component" style="height: 100%;" />
        </transition>
      </router-view>
    </div>
  </div>
</template>

<script setup>

</script>

<style scoped>
.welcome-title{
  position: absolute;
  bottom: 350px;
  left: 100px;
  color: white;
  text-shadow: 0 0 10px black;
}
</style>