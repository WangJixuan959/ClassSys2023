<template>
  <el-row class="tac">
    <el-col :span="12">
      <el-menu
          active-text-color="#5a9cf8"
          background-color="#576a83"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#f5f5f5"
          @open="handleOpen"
          @close="handleClose"
          @select="handleSelect"
          style="width: 200px;
			    height: calc(100vh - 60px);
			"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><IconMenu /></el-icon>
            <span>基础功能</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="1-1" @click="newCourse">新建分析</el-menu-item>
            <el-menu-item index="1-2" @click="courseStatus">状态查询</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
      </el-menu>
    </el-col>
  </el-row>
</template>

<script setup>
import {
  Menu as IconMenu,
} from '@element-plus/icons-vue'
import router from "../../router";


const handleOpen = () => {
  // console.log("open")
}
const handleClose = () => {
  // console.log("close")
}
const handleSelect = () => {
  // console.log("select")
}

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