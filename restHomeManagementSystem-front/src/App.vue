<script setup>
import Header from './components/Header.vue'
import Menu from './components/Menu.vue'
import Footer from './components/Footer.vue'

import { computed } from "vue"
import { useRoute } from 'vue-router'
const route = useRoute() // 路由信息对象

import { definedUser } from './stores/index'
let loginUser = definedUser()

// 判断是否登录
const showLogin = computed(() => {
  return loginUser.token == '' && route.name == 'Login';
})
const showMain = computed(() => {
  return loginUser.token !== '' && route.name !== 'Login'
})
//注册
const showRegister = computed(() => {
  return loginUser.token == '' && route.name == 'Register'
})


</script>

<template>
  <!-- <Menu id="menu" v-show="showMain"></Menu>
  <Header id="header" v-show="showMain"></Header>
  <div id="main" v-show="showMain">
    <router-view></router-view>
  </div>
  <Footer id="footer" v-show="showMain"></Footer>
  <div v-show="showLogin">
    <router-view></router-view>
  </div>
  <div v-show="showRegister">
    <router-view></router-view>
  </div> -->
<router-view></router-view>

</template>

<style scoped>
/* #menu {
  display: flex;
  float: left;
  flex-direction: column;
  width: 17%;
  height: 100vh;
  position: fixed;
  border-right: 1px solid var(--el-menu-border-color);
  background-color: rgb(58, 61, 72);
} */

/* #header {
  display: flex;
  float: right;
  height: 70px;
  width: 83%;
  justify-content: space-between;
  border-bottom: 1px solid var(--el-border-color);
  position: fixed;
  right: 0px;
  z-index: 9;
  background-color: white;
}

#main {
  width: 82%;
  height: auto;
  float: right;
  position: relative;
  top: 80px;
  margin-right: 10px;
  z-index: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: rgb(243, 244, 246);
  border-radius: 10px;
}

#footer {
  display: flex;
  float: right;
  width: 83%;
  height: 40px;
  position: fixed;
  right: 0;
  bottom: 0;
  z-index: 9;
  border-top: 1px solid var(--el-border-color);
  justify-items: center;
  align-items: center;
  background-color: rgb(238, 238, 238);
} */
</style>
