<template>
  <div id="box">
    <el-table :data="tableData" style="width: 100%" size="large">
      <el-table-column label="老人姓名" prop="elderlyName" />
      <el-table-column label="是否入住">
        <template #default="{ row }">
          <el-tag :type="row.isCheckined ? 'success' : 'warning'">{{ row.isCheckined ? '已入住' : '未入住'
            }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="老人账户余额(元)" prop="balance" />
      <el-table-column align="right">
        <template #header>
          <el-input v-model="search" size="default" placeholder="输入老人姓名搜索" />
        </template>
        <template #default="scope">
          <el-button size="default" @click="recharge(scope.$index, scope.row)">
            充值
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" title="充值">
      <div>
        <el-text class="mx-1" size="large">正在为{{ rechargeElderlyName }}充值</el-text> <el-text class="mx-1"
          size="large">,{{
            rechargeElderlyName }}账户余额：{{ ElderlyBalance }} 元</el-text><br>
        <el-text class="mx-1" size="large">当前用户{{ loginUser.username }}账户余额：{{ userBalance }} 元</el-text>
      </div>
      <div class="pay-dialog" style="margin-top: 10px;">
        <div class="pay-dialog-content" style="display: flex; align-items: center;justify-content: center; ">
          <label style="margin-right: 30px;">请输入充值金额
            <el-text class="mx-1" type="info">（将从账户余额扣除）</el-text>
          </label>
          <el-input-number v-model="rechargeValue" style="width: 240px" :min="0" type="number" placeholder="请输入支付金额" />
          <label style="margin-left: 10px;">/元</label>

        </div>
      </div>
      <div>
        <el-text class="mx-1" type="danger" v-if="rechargeValue > userBalance" size="large" @click="toUserInfo"
          style="cursor: pointer;">账户余额不足,请前往个人中心充值（点击前往）</el-text>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="danger" round @click="cancelPay">取消充值</el-button>

          <el-button type="success" round @click="rechargeHandler" :disabled="disabledValue">确认充值</el-button>
        </div>
      </template>
    </el-dialog>
  </div>

</template>

<script lang="ts" setup>

import axios from '../../api/request.js';

import { computed, ref, onMounted, reactive, watch, } from 'vue'
import { definedUser } from '../../stores/index.js';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router';
let router = useRouter();

let loginUser = definedUser()
//控制dialog是否打开
const dialogVisible = ref(false)
//老人信息接口
interface Elderly {
  bookerName: string;
  bookerTime: string;
  eid: number;
  elderlyAddress: string;
  elderlyBirth: string;
  elderlyIdCard: string;
  elderlyName: string;
  elderlyPhone: string;
  elderlySex: string;
  isHealth: string;
  isCheckined: number;
  isVoluntaryOccupancy: number;
  note: string;
  uid: number;
  balance: number;
}
const search = ref('')
//充值按钮，单击打开充值框
const recharge = (index: number, row: User) => {
  //老人姓名赋值
  rechargeElderlyName.value = row.elderlyName
  //eid赋值
  rechargeEid.value = row.eid
  //老人账户余额赋值
  ElderlyBalance.value = row.balance
  dialogVisible.value = true;
}

const tableData = reactive<Elderly[]>([])
//获取已经完成预定的老人信息
const fetchData = () => {
  axios.get(`/elderly/selectAllElderlyByUid/${loginUser.uid}`, {
  }).then(function (response) {
    const convertedData: Elderly[] = response.data.data.map(item => ({
      ...item,
      isCheckined: item.isCheckined === 0 ? false : true
    }));
    tableData.splice(0, tableData.length, ...convertedData);

  }).catch(function (error) {
    console.log(error);
  })
}
//封装搜索框模糊查询事件
function searchTableData(searchValue) {
  axios.get("/elderly/selectForSearch", {
    params: {
      //搜索框的值
      searchValue: searchValue
    }
  }).then(response => {
    const convertedData: Elderly[] = response.data.data.map(item => ({
      ...item,
      isCheckined: item.isCheckined === 0 ? false : true
    }));
    tableData.splice(0, tableData.length, ...convertedData);

  }).catch(error => {
    console.error('查询失败:', error);
  })
}
//监听搜索框是否有输入
watch(search, (newVal, oldVal) => {
  if (newVal != oldVal) {
    console.log(newVal);
    searchTableData(newVal)
  }
})

const rechargeValue = ref(0) // 充值金额
const rechargeElderlyName = ref() //充值的账户老人姓名
const rechargeEid = ref() //充值的账户老人id
const ElderlyBalance = ref() //充值的账户老人id
const disabledValue = ref(true) //控制充值按钮是否能充值
//侦听充值金额
watch(rechargeValue, (newVal, oldVal) => {
  //当充值金额大于0并且大于充值余额时，确认充值按钮才能点击
  if (newVal <= 0 || userBalance.value < rechargeValue.value) {
    disabledValue.value = true;
  }
  else {
    disabledValue.value = false;
  }
});
//存储账户余额的数据
const userBalance = ref()
//获取账户余额
const fetchUserBalance = () => {
  axios.get(`/user/selectBalance/${loginUser.uid}`, {
  }).then(function (response) {
    userBalance.value = response.data.data.balance;
  }).catch(function (error) {
    console.log(error);
  })
}
//扣除账户余额
const deductUserBalance = () => {
  axios.put(`/user/deductBalance`, {
    uid: loginUser.uid,
    money: rechargeValue.value
  }).then(function (response) {
    console.log(response)
  }).catch(function (error) {
    console.log(error);
  })
}
//为老人账户充值
const rechargeElderlyBalance = () => {
  axios.put(`/elderly/rechargeElderlyBalance`, {
    eid: rechargeEid.value,
    money: rechargeValue.value
  }).then(function (response) {
    console.log(response)
  }).catch(function (error) {
    console.log(error);
  })
}
//确认充值事件
const rechargeHandler = () => {
  //关闭dialog框
  dialogVisible.value = false
  if (userBalance.value >= rechargeValue.value) {
    deductUserBalance()
    rechargeElderlyBalance()
    ElMessage({
      message: '充值成功',
      type: 'success',
    })
    transactionDeduct()
    rechargeValue.value = 0
  }
  setTimeout(() => {
    fetchData()
  }, 300);
}
//取消充值事件
const cancelPay = () => {
  dialogVisible.value = false

  ElMessage({
    message: '充值已取消',
    type: 'warning',
  })
}
const toUserInfo = () => {
  router.push({ name: 'PersonalCenter' });
}

const transactionDeduct = () => {
  axios.post("/transaction/addIn", {
    uid: loginUser.uid,
    transactionMoney: rechargeValue.value,
    transactionType: '账户余额',
    description: `为${rechargeElderlyName.value}的老人账户充值`,
    inOrOut: 0
  }).then(function (response) {
    console.log(response);
  }).catch(function (error) {
    console.log(error);
  })
}


onMounted(() => {
  fetchData()
  fetchUserBalance()
})
</script>
<style scoped>
#box {
  background-color: white;
  display: flex;
  align-items: center;
  flex-direction: column;
}

#paymentContainer {
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  flex-wrap: wrap;
}

.selected-payment {
  border: 2px solid #409EFF;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}
</style>