<template>
  <div>
    <div id="breadcrumb">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: item.path }" v-for="item in breadcrumbList" :key="item.path">
          {{ item.meta.title }}
        </el-breadcrumb-item>
      </el-breadcrumb>


    </div>
    <div id="id_head_portrait">

      <div class="userPortrait">
        <el-dropdown style="">
          <div class="el-dropdown-link" style="border-radius: 25px;  border: none ">
            <el-avatar size="large" :src="avatarUrl" />
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="toUserInfo">
                个人信息
              </el-dropdown-item>
              <el-dropdown-item @click="toMyApplication">
                我的申请
              </el-dropdown-item>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <div class="username">
        <h4 v-text="nickname"></h4>
      </div>
    </div>

  </div>
</template>

<script lang="ts" setup>
import { computed, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { definedUser } from '../stores';
import { useRouter } from 'vue-router';
import axios from '../api/request.js';

let router = useRouter();
let loginUser = definedUser();
const route = useRoute();
const breadcrumbList = computed(() => {
  return route.matched;
});
const nickname = ref(loginUser.nickname)
const headImgUrl = ref(loginUser.headImgUrl)
const avatarUrl = ref(`http://localhost:8999/images/upload/${loginUser.headImgUrl}`)

watch(loginUser, () => {
  axios.get(`/user/findUser/${loginUser.uid}`).
    then(function (response) {
      nickname.value = response.data.data.nickname;
      headImgUrl.value = response.data.data.headImgUrl
      avatarUrl.value = `http://localhost:8999/images/upload/` + headImgUrl.value
    }).catch(function (error) {
      console.log(error);

    });
},
  { deep: true })

function logout() {

  // 销毁token
  loginUser.$reset();
  // 跳转到登录页
  router.push({ name: 'Login' });

}
const toUserInfo = () => {
  router.push({ name: 'PersonalCenter' });
}
const toMyApplication = () => {
  router.push({ name: 'MyApplication' });
}

</script>

<style>
#breadcrumb {
  width: 300px;
  display: flex;
  align-items: center;
  margin-top: 20px;
  margin-left: 10px;
  font-size: 20px;
}

.el-breadcrumb {
  font-size: 16px;
}

#id_head_portrait {
  width: 200px;
  height: 65px;
  display: flex;
}

.username {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 10px;

}

.userPortrait {
  margin-top: 10px;
  width: 70px;
  height: 55px;
}

.el-dropdown {
  height: 55px;
  border: none;
}

h4 {
  margin: 0;
}
</style>
