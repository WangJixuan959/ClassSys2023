<template>
  <el-form :model="form" :rules="rules" label-width="120px" style="margin-top: 10px;width: 700px" ref="formRef">
    <el-form-item label="课程名称" prop="course_name">
      <el-input v-model="form.course_name" placeholder="计算机网络"/>
    </el-form-item>
    <el-form-item label="教师姓名" prop="teacher_name">
      <el-input v-model="form.teacher_name" placeholder="王**"/>
    </el-form-item>
    <el-form-item label="教室编号" prop="classroom_id">
      <el-input v-model="form.classroom_id" placeholder="如A101"/>
    </el-form-item>
    <!--    暂时采用输入本地路径的方式，后续改为上传-->
    <el-form-item label="视频路径" prop="video_path">
      <el-input v-model="form.video_path" placeholder="C:\\fakepath\\***"/>
    </el-form-item>
    <el-form-item label="创建时间" prop="date2">
      <el-col :span="11">
        <el-date-picker
            v-model="form.date1"
            type="date"
            placeholder="日期"
            style="width: 100%"
        />
      </el-col>
      <el-col :span="2" class="text-center">
        <span>-</span>
      </el-col>
      <el-col :span="11">
        <el-time-picker
            v-model="form.date2"
            placeholder="时间"
            style="width: 100%"
            arrow-control
        />
      </el-col>
    </el-form-item>
    <el-form-item label="项目状态" prop="status">
      <el-radio-group v-model="form.status" disabled>
        <el-radio label="未分析" />
        <el-radio label="正在分析" />
        <el-radio label="已分析" />
      </el-radio-group>
    </el-form-item>
    <el-form-item label="项目描述" prop="desc">
      <el-input v-model="form.desc" type="textarea" placeholder="备注" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">创建</el-button>
      <el-button @click="cancel">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive, ref } from 'vue'
import router from "../../router";
import {post} from "../../utils";
import {ElMessage} from "element-plus";
// import type { UploadInstance } from 'element-plus'

const form = reactive({
  course_name: '',
  teacher_name: '',
  classroom_id: '',
  date1: '',
  date2: '',
  video_path: '',
  status: '未分析',
  desc: '',
})
const formRef = ref()

const onSubmit = () => {
  // 提交表单
  // console.log(form)
  formRef.value.validate((isValid) => {
    if (isValid){
      post("/api/course/new", {
        course_name: form.course_name,
        teacher_name: form.teacher_name,
        classroom_id: form.classroom_id,
        date1: form.date1,
        date2: form.date2,
        video_path: form.video_path,
        status: form.status,
        desc: form.desc
      }, (message) => {
        ElMessage.success(message)
        router.push("/index")
      })
    } else {
      ElMessage.warning('请正确填写项目信息')
    }
  })
}

const cancel = () => {
  router.push('/index')
}

// 表单验证规则
const rules = {
  course_name: [
    {required: true, min: 1, max: 16, trigger: ['blur', 'change'], message: '请正确填写课程名称'}
  ],
  teacher_name: [
    {required: true, min: 1, max: 6, trigger: ['blur', 'change'], message: '请正确填写教师'}
  ],
  classroom_id: [
    {required: true, trigger: ['blur', 'change'], message: '请正确填写教室编号'}
  ],
  video_path: [
    {required: true, message: '请输入需要解析的课堂视频地址', trigger: 'blur'}
  ]
}

</script>

<style scoped>

</style>