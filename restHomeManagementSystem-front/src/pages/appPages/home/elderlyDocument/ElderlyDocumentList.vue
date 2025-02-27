<template>
  <div class="header">
    <span class="back-arrow" @click="router.go(-1)">←</span>
    <h4>老人档案</h4>
  </div>
  <div class="archive-page">
    <div class="archive-card" v-for="elderly in elderlyInfoList">
      <div class="card-header">
        <span>{{ elderly.elderlyName }}</span>
        <el-button type="primary" size="small" @click="editArchive">编辑</el-button>
      </div>
      <div class="card-content">
        <p>
          <span>{{ elderly.elderlySex }}</span>
        </p>
        <p>
          <span>{{ calcAge(elderly.elderlyIdCard) }}岁</span>
        </p>
        <p>
          <span>{{ elderly.isHealth }}</span>
        </p>
      </div>
      <div class="card-footer">
        <el-tag :type="elderly.isCheckined ? 'success' : 'warning'">{{ elderly.isCheckined ? '已入住' : '未入住' }}</el-tag>
      </div>
    </div>

  </div>
  <div class="footer-text">
    要养老，上养老生活 ®
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from '@/api/request';
import { definedUser } from '@/stores';
let loginUser = definedUser()
const router = useRouter();
// 模拟老人档案数据
const elderlyInfoList = ref();
const fetchElderList = async () => {
  const response = await axios.get(`/elderly/selectAllElderlyByUid/${loginUser.uid}`)
  const convertedData = response.data.data.map(item => ({
    ...item,
    elderlySex: item.elderlySex === 'male' ? '男' : '女',
    isHealth: item.isHealth === 'healthy' ? '健康' : '有疾病史或有其他健康问题',
    isCheckined: item.isCheckined === 0 ? false : true
  }));
  elderlyInfoList.value = convertedData;
  console.log(response);

}

// 编辑档案方法，这里只是简单模拟跳转，实际可根据需求修改
const editArchive = () => {
  // 假设编辑页面路由为 /edit-archive
  router.push('/edit-archive');
};

// 新增档案方法，这里只是简单模拟跳转，实际可根据需求修改
const addArchive = () => {
  // 假设新增页面路由为 /add-archive
  router.push({ name: 'AddElderlyDocument_app' });
};
//根据身份证计算年龄
const calcAge = (idCard) => {

  const birthYear = parseInt(idCard.slice(6, 10), 10);
  const birthMonth = parseInt(idCard.slice(10, 12), 10);
  const birthDay = parseInt(idCard.slice(12, 14), 10);
  const now = new Date();
  const currentYear = now.getFullYear();
  const currentMonth = now.getMonth() + 1;
  const currentDay = now.getDate();
  let age = currentYear - birthYear;
  if (currentMonth < birthMonth || (currentMonth === birthMonth && currentDay < birthDay)) {
    age--;
  }
  return age;
}
onMounted(() => {
  fetchElderList()
})
</script>

<style scoped>
.archive-page {
  padding: 16px;
  background-color: #f5f5f5;
  min-height: 91vh;
  padding-top: 70px;

}


.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  justify-content: center;
  display: flex;
  align-items: center;
  z-index: 10;
  color: #fff;
  height: 55px;
  background-image: url(@/assets/images/home_app_background.jpg);

}

.back-arrow {
  position: absolute;
  left: 15px;
  margin: 10px;
  top: 10px;
}

.header h4 {
  margin: 0;
  font-size: 15px;
}


.archive-card {
  border: 1px solid #409EFF;
  border-radius: 10px;
  padding: 15px;
  margin-bottom: 15px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.card-content p {
  margin: 5px 0;
  color: #606266;
}

.card-footer {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
}

.add-archive-btn {
  position: fixed;
  bottom: 0;
  margin: 10px 0;
  border: none;
  border-radius: 10px;
  color: #fff;
  background-color: #00bfa5;
  height: 40px;
  width: 93%;
  font-size: 14px;
}

.footer-text {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
  margin: 10px 0;
  font-size: 14px;
  color: #008080;
  padding: 10px 0;
}
</style>