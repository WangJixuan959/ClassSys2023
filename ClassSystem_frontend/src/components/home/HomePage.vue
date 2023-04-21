<template>
  <div style="margin-top: 50px;margin-left: 50px">
    <el-text  type="primary">欢迎使用基于课堂视频的教师运动轨迹和姿态分析工具</el-text>
    <el-divider />
    <el-text>点击 <el-link style="font-size: 14px;margin-bottom: 2px" @click="newCourse">基础功能->新建分析</el-link> 以创建新项目</el-text>
    <el-divider />
    <el-text>点击 <el-link style="font-size: 14px;margin-bottom: 2px" @click="courseStatus">基础功能->状态查询</el-link> 以查询相关项目状态</el-text>
  </div>
</template>

<script setup>
import router from "../../router";

const newCourse = () => {
  // console.log("new Course")
  router.push('/index/new-course')
}
const courseStatus = () => {
  // console.log("course status")
  router.push('/index/course-status')
}
</script>

<style scoped>

</style>