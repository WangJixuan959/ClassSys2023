<template>
  <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
      @select="handleSelect"
      style="height: 60px;width: 100%"
  >
    <el-menu-item index="#" @click="router.push('/index')">基于课堂视频的教师运动轨迹和姿态分析工具</el-menu-item>
    <el-sub-menu index="2" style="margin-left: 65%;">
      <template #title>
        <span>{{userinfo.username}}</span>
        <el-icon><User /></el-icon>
      </template>
<!--      <el-menu-item index="2-1">个人中心</el-menu-item>-->
<!--      <el-menu-item index="2-2">消息中心</el-menu-item>-->
      <el-menu-item index="2-1" @click="logout">退出登录</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import {useStore} from "../../stores";
import {reactive, ref} from "vue";
import { User } from '@element-plus/icons-vue'
import router from "../../router";
import { toRaw } from '@vue/reactivity'
import {get} from "../../utils";
import {ElMessage} from "element-plus";




const store = useStore()
const activeIndex = ref('1')
const userinfo = toRaw(store.auth.user)

const handleSelect = () => {
  // console.log("select")
  // console.log(store.auth.user)
}

const created = () => {
  // console.log("created")
  // console.log(store.auth.user)
}
created()

const logout = () => {
  get('/api/auth/logout', (message) => {
    ElMessage.success(message)
    store.auth.user = null
    router.push('/')
  })
}
</script>

<style scoped>



</style>