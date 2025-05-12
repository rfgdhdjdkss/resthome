<template>
  <div class="header-components">
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
            <el-avatar size="large" :src="avatarUrl" v-if="loginUser.headImgUrl != null" />
            <i v-else>
              <svg t="1742719412381" class="icon" viewBox="0 0 1024 1024" version="1.1"
                xmlns="http://www.w3.org/2000/svg" p-id="15495" width="50" height="50">
                <path
                  d="M575.802452 773.39589c-35.039421 25.015257-94.371638 25.015257-129.411059 0-7.947085-5.599083-18.874328-3.792927-24.473411 4.063851-5.599083 7.856777-3.792927 18.874328 4.063851 24.47341 23.841256 16.977864 54.455596 25.557104 85.069935 25.557104 30.61434 0 61.22868-8.488932 85.160244-25.557104 7.947085-5.599083 9.753241-16.616633 4.154158-24.47341-5.689391-7.76647-16.616633-9.662933-24.563718-4.063851z m0 0"
                  fill="#e96265" p-id="15496"></path>
                <path
                  d="M755.785872 235.88394l23.66064-80.554546c2.077079-6.9537-0.361231-14.449246-6.140929-18.874327C706.658435 85.340859 611.112797 55.990828 511.142076 55.990828c-99.970721 0-195.606667 29.350031-262.253814 80.464239-5.779698 4.425082-8.218009 11.920628-6.14093 18.874327l23.66064 80.644854c-117.038892 77.12285-194.25205 207.707911-194.25205 355.722374 0 132.571832 65.382838 275.438751 247.985184 282.84399 52.559132 57.616368 119.748126 93.287944 191.00097 93.287944 71.162536 0 138.441838-35.761884 191.00097-93.287944 182.512038-7.495546 247.894876-150.362466 247.894876-282.84399 0-148.014463-77.213158-278.599524-194.25205-355.812682z m-317.612488-52.378516h41.541582v-41.451275c0-17.339095 14.088015-31.42711 31.42711-31.42711 17.339095 0 31.42711 14.088015 31.42711 31.42711v41.451275h41.541582c17.339095 0 31.42711 14.088015 31.42711 31.42711 0 17.339095-14.088015 31.42711-31.42711 31.42711h-41.541582v41.541582c0 17.339095-14.088015 31.42711-31.42711 31.42711-17.339095 0-31.42711-14.088015-31.42711-31.42711v-41.541582h-41.541582c-17.339095 0-31.42711-14.088015-31.42711-31.42711 0-17.339095 14.088015-31.42711 31.42711-31.42711z m72.968692 749.283711c-140.157686 0-263.156892-161.1994-263.156892-345.066055 0-1.806156 0.090308-3.612311 0.180615-5.418468 108.279037-12.010936 204.45683-61.048064 262.976277-135.010142 58.519446 74.052386 154.69724 123.089514 263.066584 135.010142 0 1.806156 0.180616 3.612311 0.180616 5.418468-0.090308 183.866655-123.089514 345.066055-263.2472 345.066055z"
                  fill="#e96265" p-id="15497"></path>
              </svg>
            </i>
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
const avatarUrl = ref(`http://localhost:8999/images/upload/headPortrait/${loginUser.headImgUrl}`)

watch(loginUser, () => {
  if (loginUser.token != "") {
    axios.get(`/user/findUser/${loginUser.uid}`).
      then(function (response) {
        nickname.value = response.data.data.nickname;
        headImgUrl.value = response.data.data.headImgUrl
        avatarUrl.value = `http://localhost:8999/images/upload/headPortrait/` + headImgUrl.value
      }).catch(function (error) {
        console.log(error);

      });
  }

},
  { deep: true })

function logout() {

  // 销毁token
  loginUser.$reset();
  localStorage.removeItem('token')

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

<style scoped>
.header-components {
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
