<template>
  <div style="text-align: center; margin: 0 20px">
    <div style="margin-top: 100px">
      <div style="font-size: 25px;font-weight: bold">注册新用户</div>
      <div style="font-size: 14px;color: gray">欢迎注册基于课堂视频的教师运动轨迹和姿态分析工具系统</div>
    </div>
    <div style="margin-top: 50px">
      <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" :maxlength="8" type="text" placeholder="用户名">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" :maxlength="16" type="password" placeholder="输入密码" style="margin-top: 10px">
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password_repeat">
          <el-input v-model="form.password_repeat" :maxlength="16" type="password" placeholder="重复输入密码" style="margin-top: 10px">
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="form.email" type="email" placeholder="电子邮件地址" style="margin-top: 10px">
            <template #prefix>
              <el-icon><Message /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-row :gutter="10" style="width: 100%; margin-top: 10px">
            <el-col :span="17">
              <el-input v-model="form.code" :maxlength="6" type="text" placeholder="请输入验证码">
                <template #prefix>
                  <el-icon><Promotion /></el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :span="5">
              <el-button type="success" style="color: green" :disabled="!isEmailValid || coldTime > 0"
                         @click="validateEmail">{{coldTime > 0 ? "请稍后" + coldTime + "秒" : "获取验证码"}}</el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin-top: 80px">
      <el-button style="width: 250px;" type="warning" plain @click="register">立即注册</el-button>
    </div>
    <div style="margin-top: 20px">
      <span style="font-size: 14px; line-height: 14px; color: gray">已有账号?</span>
      <el-link @click="router.push('/')" style="translate: 0 -1px" type="primary">立即登录</el-link>
    </div>
  </div>
</template>

<script setup>
import {User, Lock, Message, Promotion} from "@element-plus/icons-vue"
import router from "../../router";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {post} from "../../utils";

// 表单数据
const form = reactive({
  username: '',
  password: '',
  password_repeat: '',
  email: '',
  code: ''
})

// 是否发送邮件
const coldTime = ref(0)

// username验证
const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if(/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback()
  } else {
    callback(new Error('用户名只能包含中文/英文，不能包含特殊字符'))
  }
}
// 密码二次验证
const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error("两次输入密码不一致"))
  } else {
    callback()
  }
}

// email验证
const isEmailValid = ref(false)

// 当email输入合法时允许点击获取验证码
const onValidate = (prop, isValid) => {
  if(prop === 'email' && form.email !== ''){
    isEmailValid.value = isValid
    // console.log(isEmailValid.value)
  }
}

const formRef = ref()
// 注册提交表单
const register = () => {
  formRef.value.validate((isValid) => {
    if (isValid){
      post("/api/auth/register", {
        username: form.username,
        password: form.password,
        email: form.email,
        code: form.code
      }, (message) => {
        ElMessage.success(message)
        router.push("/")
      })
    } else {
      ElMessage.warning('请完整填写注册内容信息')
    }
  })
}

// 发送邮箱验证码
const validateEmail = () => {
  coldTime.value = 60
  post("/api/auth/valid-register-email", {
    email: form.email
  }, (message) => {
    ElMessage.success(message)
    setInterval(() => coldTime.value--, 1000)
  }, (message) => {
    ElMessage.warning(message)
    coldTime.value = 0
  })
}

// 表单验证规则
const rules = {
  username: [
    {validator: validateUsername, trigger: ['blur', 'change']},
    {min: 2, max: 8, trigger: ['blur', 'change'], message: '用户名的长度必须在2-8个字符之间'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 16, trigger: ['blur', 'change'], message: '密码的长度必须在6-16个字符之间'}
  ],
  password_repeat: [
    {validator: validatePassword, trigger: ['blur', 'change']}
  ],
  email: [
    {required: true, message: '请输入邮件地址', trigger: 'blur'},
    {type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']}
  ],
  code: [
    {required: true, message: '请输入获取的验证码', trigger: 'blur'}
  ]
}
</script>

<style scoped>

</style>