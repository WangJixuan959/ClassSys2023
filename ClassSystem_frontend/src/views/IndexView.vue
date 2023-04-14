<template>
  <div>
    欢迎进入基于课堂视频的教师运动轨迹和姿态分析工具
  </div>
  <div>
    <el-button @click="logout()" type="danger" plain>退出登录</el-button>
  </div>
</template>

<script setup>
import {ElMessage} from "element-plus";
import router from "../router";
import {get} from "../utils";

const logout = () => {
  get('/api/auth/logout', (message) => {
    ElMessage.success(message)
    router.push('/')
  })
}
</script>

<style scoped>

</style>