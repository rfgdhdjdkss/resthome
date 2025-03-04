<template>
  <div class="header">
    <span class="back-arrow" @click="router.push({name:'Home_app'})">←</span>
    <h4>老人档案</h4>
  </div>
  <div class="elderly-archive-page">
    <div class="archive-list">
      <div class="archive-card" v-for="elder in elderlyArchives" >
        <div class="card-header">
          <h2>{{ elder.elderlyName }}</h2>
        </div>
        <div class="card-content">
          <p><strong>性别：</strong>{{ elder.elderlySex }}</p>
          <p><strong>年龄：</strong>{{ calcAge(elder.elderlyIdCard) }}</p>
          <p><strong>身份证号：</strong>{{ elder.elderlyIdCard }}</p>
          <p><strong>健康状况：</strong>{{ elder.isHealth }}</p>
          <p><strong>入住状态：</strong>{{ elder.isCheckined }}</p>
          <p><strong>房间号：</strong>{{ elder.bedroom || '暂无' }}</p>
        </div>
      </div>
    </div>
    <div>
      <button class="new-address-button" @click="gotoCreateNewDocumentPage">
        新增老人档案</button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from '@/api/request';
import { definedUser } from '@/stores';
let loginUser = definedUser()
let router = useRouter()
// 模拟老人档案数据
const elderlyArchives = ref([
  {
    id: 1,
    name: '张三',
    gender: '男',
    age: 75,
    idCard: '110101195001011234',
    healthStatus: '良好',
    isCheckedIn: true,
    roomNumber: '201'
  },
  {
    id: 2,
    name: '李四',
    gender: '女',
    age: 80,
    idCard: '110102194502022345',
    healthStatus: '有慢性病',
    isCheckedIn: false,
    roomNumber: null
  },
  {
    id: 3,
    name: '王五',
    gender: '男',
    age: 70,
    idCard: '110103195503033456',
    healthStatus: '健康',
    isCheckedIn: true,
    roomNumber: '302'
  }
]);
const fetchData = async () => {
  const response = await axios.get(`/elderly/selectAllElderlyByUid/${loginUser.uid}`)
  const handelElderly = response.data.data.map(item => ({
    ...item,
    elderlySex: item.elderlySex === 'male' ? '男' : '女',
    isHealth: item.isHealth === 'healthy' ? '健康' : '有疾病史或有其他健康问题',
    isCheckined: item.isCheckined === 1 ? '已入住' : '未入住'
  }));
  //清空原数组，加入响应回的数组
  elderlyArchives.value = handelElderly;
  console.log(elderlyArchives.value);

}

//根据身份证计算年龄
const calcAge = (idCard) => {
  // 检查 idCard 是否为有效的字符串
  if (typeof idCard !== 'string' || idCard.length !== 18) {
    // 如果不是有效的身份证号，返回一个默认值，比如 0
    return 0;
  }
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
const gotoCreateNewDocumentPage=()=>{
  router.push({name:'CreateNewDocument_app'})
}
onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.elderly-archive-page {
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
  color: #000;
  height: 55px;
  background-color: #f5f5f5;
  border-bottom: 1px solid #ebeef5;
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

.archive-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.archive-card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  width: 100%;
  transition: transform 0.3s ease;
}

.archive-card:hover {
  transform: translateY(-5px);
}

.card-header {
  background-color: #00bfa5;
  color: #fff;
  padding: 10px 20px;
}

.card-header h2 {
  margin: 0;
  font-size: 20px;
}

.card-content {
  padding: 20px;
}

.card-content p {
  margin: 10px 0;
  color: #555;
}

.card-content p strong {
  color: #333;
}

.new-address-button {
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
</style>