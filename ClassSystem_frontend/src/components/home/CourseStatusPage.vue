<template>
  <div style="position: relative">
    <el-table :data="tableData.slice((current_page-1)*page_size, current_page*page_size)" style="width: 100%;height: 85vh;"
              v-loading.body="loading"
              element-loading-text="拼命加载中">
      <el-table-column fixed prop="date1" label="时间" width="150" />
      <el-table-column prop="course_name" label="课堂名称" width="150" />
      <el-table-column prop="teacher_name" label="课堂名称" width="150" />
      <el-table-column prop="classroom_id" label="课堂名称" width="150" />
      <el-table-column prop="date2" label="课堂名称" width="150" />
      <el-table-column prop="status" label="状态" width="150" />
      <el-table-column prop="desc" label="状态" width="150" />
      <el-table-column prop="id" label="状态" width="150" />
      <el-table-column prop="video_path" label="状态" width="150" />
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="showOverview(scope.$index, scope.row)">分析概览</el-button>
          <el-button link type="primary" size="small" @click="editCourseInfo(scope.$index)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="current_page"
        v-model:page-size="page_size"
        :page-sizes="[10, 20, 30, 40, 50]"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="position: absolute; left: 30%; top: 95%"
    />
  </div>
</template>

<script setup>
import router from "../../router"
import {toRaw} from "@vue/reactivity";
import {reactive, ref} from "vue";
import {get, post} from "../../utils";


const showOverview = (id, row) => {
  console.log(id)
  console.log(row)
  console.log(toRaw(row).date)
  // console.log(tableData[id])
}

const editCourseInfo = (id) => {
  console.log(id)
  router.push({
    // path:'/index/course-re-edit/' + id,
    name: 'course-re-edit',
    params: {
      id: id
    }
  })
}

// page
let current_page = ref(1)
let page_size = ref(10)
let total = ref(0)
let loading = ref(false)
let tableData = []

const handleSizeChange = (pageSize) => {
  page_size.value = pageSize
  current_page.value = 1
}

const handleCurrentChange = (currentPage) => {
  showStatusByPage(currentPage)
}

const showStatusByPage = async (currentPage) => {
  loading = true
  if (currentPage) {
    current_page.value = currentPage
  }
  loading = false
  await get('/api/course/all',  (res) => {
    if (res.data){
      total.value = res.data.length
      tableData = res.data
    } else{
      tableData = []
      total.value = 0
    }
  })
}

showStatusByPage(current_page.value)


</script>

<style scoped>

</style>