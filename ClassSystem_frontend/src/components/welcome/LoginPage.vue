<template>
  <div style="text-align: center; margin: 0 20px">
    <div style="margin-top: 150px">
      <div style="font-size: 25px;font-weight: bold">登录</div>
      <div style="font-size: 14px;color: gray">在进入系统之前请先输入用户名和密码进行登录</div>
    </div>
    <div style="margin-top: 50px">
      <el-input v-model="form.username" type="text" placeholder="用户名/邮箱">
        <template #prefix>
          <el-icon><User /></el-icon>
        </template>
      </el-input>
      <el-input v-model="form.password" type="password" style="margin-top: 10px" placeholder="密码">
        <template #prefix>
          <el-icon><Lock /></el-icon>
        </template>
      </el-input>
    </div>
    <el-row style="margin-top: 5px">
      <el-col style="text-align: left" :span="12">
        <el-checkbox v-model="form.remember" label="记住我" size="large" />
      </el-col>
      <el-col style="text-align: right" :span="12">
        <el-link @click="router.push('/forget')">忘记密码？</el-link>
      </el-col>
    </el-row>
    <div style="margin-top: 40px">
      <el-button @click="login()" @keyup.enter="enterDown" style="width: 270px;" type="success" plain>立即登录</el-button>
    </div>
    <el-divider>
      <span style="color: gray;font-size: 14px">没有账号？</span>
    </el-divider>
    <div style="margin-top: 20px">
      <el-button @click="router.push('/register')" style="width: 270px;" type="warning" plain>注册账号</el-button>
    </div>
  </div>
</template>

<script setup>
import {User, Lock} from "@element-plus/icons-vue"
import {onMounted, onUnmounted, reactive} from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "../../utils";
import router from "../../router";
import {useStore} from "../../stores";

const form = reactive({
  username: '',
  password: '',
  remember: false
})

const store = useStore()
const login = () => {
  if(!form.username || !form.password){
    ElMessage.warning("请输入用户名和密码！")
  } else {
    post('api/auth/login', {
      username: form.username,
      password: form.password,
      remember: form.remember
    }, (message) => {
      ElMessage.success(message)
      get('/api/user/me', (message) => {
        store.auth.user = message
        router.push('/index')
      }, () => {
        store.auth.user = null
      })
    })
  }
}
onMounted(() => {
  window.addEventListener("keydown", enterDown);
});
const enterDown = (e) => {
  if (e.keyCode === 13) {
    login();
  }
}
onUnmounted(() => {
  window.removeEventListener("keydown", enterDown, false);
});
</script>

<style scoped>

</style>