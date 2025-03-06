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
                        提交退住申请
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog v-model="dialogVisible" title="退住申请">
            <div>
                <el-text class="mx-1" size="default">正在为{{ checkOutElderlyName }}提交退住申请</el-text>
                <el-form style="margin-top:20px ;" ref="ruleFormRef" :model="ruleForm" class="demo-ruleForm"
                    size="large">
                    <!-- 姓名 -->
                    <el-form-item label="姓名" prop="elderlyName">
                        <el-input v-model="ruleForm.elderlyName" disabled />
                    </el-form-item>
                    <!-- 性别 -->
                    <el-form-item label="性别" prop="elderlySex">
                        <el-radio-group v-model="ruleForm.elderlySex" disabled>
                            <el-radio value="male">男</el-radio>
                            <el-radio value="female">女</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <!-- 出生年月 -->
                    <el-form-item label="出生年月" prop="elderlyBirth">
                        <el-date-picker v-model="ruleForm.elderlyBirth" type="date" aria-label="请选择出生年月"
                            placeholder="请选择出生年月" style="width: 100%" disabled />
                    </el-form-item>
                    <!-- 身份证号码 -->
                    <el-form-item label="身份证号码" prop="elderlyIdCard">
                        <el-input v-model="ruleForm.elderlyIdCard" disabled />
                    </el-form-item>
                    <!-- 账户余额 -->
                    <el-form-item label="账户余额" prop="balance">
                        <el-input v-model="ruleForm.balance" disabled />
                    </el-form-item>
                    <!-- 健康状况 -->
                    <el-form-item label="健康状况" prop="isHealth">
                        <el-select v-model="ruleForm.isHealth" placeholder="请选择健康状况" disabled>
                            <el-option label="健康" value="healthy" />
                            <el-option label="有疾病史或有其他健康问题" value="subHealthy" />
                        </el-select>
                    </el-form-item>
                    <el-form-item style="margin-left: 350px;">
                        <el-button type="info" @click="cancel()">
                            返回
                        </el-button>
                        <el-button type="primary" @click="submitForm(ruleFormRef)">
                            提交
                        </el-button>
                        <el-text style="margin-left: 40px;" class="mx-1" size="default">({{ checkOutElderlyName }}的账户余额将退回至登录账户{{ loginUser.nickname
                            }}中)</el-text>
                    </el-form-item>
                </el-form>
            </div>

            <!-- <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" round @click="cancelPay">取消充值</el-button>
                    <el-button type="success" round @click="rechargeHandler" :disabled="disabledValue">确认充值</el-button>
                </div>
            </template> -->
        </el-dialog>
    </div>

</template>

<script lang="ts" setup>

import axios from '@/api/request.js';

import { computed, ref, onMounted, reactive, watch, } from 'vue'
import { definedUser } from '@/stores/index.js';
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
//充值按钮，单击打开退住框
const recharge = (index: number, row: User) => {
    console.log(row);
    ruleForm.elderlyName = row.elderlyName
    ruleForm.elderlySex = row.elderlySex
    ruleForm.elderlyBirth = row.elderlyBirth
    ruleForm.elderlyIdCard = row.elderlyIdCard
    ruleForm.eid = row.eid
    ruleForm.isHealth = row.isHealth
    ruleForm.uid = row.uid
    ruleForm.balance = row.balance + "元"
    //老人姓名赋值
    checkOutElderlyName.value = row.elderlyName
    //eid赋值
    checkOutEid.value = row.eid
    dialogVisible.value = true;
}

const tableData = reactive<Elderly[]>([])
//获取已经入住的老人信息
const fetchData = () => {
    axios.get(`/elderly/selectIsCheckinElderlyByUid/${loginUser.uid}`, {
    }).then(function (response) {
        const convertedData: Elderly[] = response.data.data.map(item => ({
            ...item,
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
const ruleFormRef = ref<FormInstance>()
const checkOutElderlyName = ref() //退住账户老人姓名
const checkOutEid = ref() //退住的账户老人id
const ruleForm = reactive<Elderly>({
    bookerName: "",
    bookerTime: "",
    eid: "",
    elderlyAddress: "",
    elderlyBirth: "",
    elderlyIdCard: "",
    elderlyName: "",
    elderlyPhone: "",
    elderlySex: "",
    isHealth: "",
    isCheckined: "",
    isVoluntaryOccupancy: "",
    note: "",
    uid: "",
    balance: "",
})
const cancel=()=>{
    dialogVisible.value=false
    ElMessage.info("取消提交申请")
}
onMounted(() => {
    fetchData()
})
</script>
<style scoped>
#box {
    background-color: white;
    display: flex;
    align-items: center;
    flex-direction: column;
}
</style>