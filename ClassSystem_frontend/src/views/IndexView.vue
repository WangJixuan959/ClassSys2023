<template>
  <div>
    <Header/>
  </div>

  <div>
    <div style="display: flex">
      <LeftMenu/>
      <router-view/>
    </div>
  </div>
</template>

<script setup>
import {ElMessage} from "element-plus";
import router from "../router";
import {get} from "../utils";
import {useStore} from "../stores";
import { ref } from 'vue'
import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'
import Header from "../components/common/Header.vue";
import LeftMenu from "../components/common/LeftMenu.vue";
import Footer from "../components/common/Footer.vue";



const store = useStore()

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