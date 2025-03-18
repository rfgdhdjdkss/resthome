<template>
    <Header-web></Header-web>
    <Menu-web></Menu-web>
    <Footer-web></Footer-web>
    <div id="box">
        <div id="container1">
            <el-card style="width: 95%;">
                <div id="left">
                    <h3>个人信息</h3>
                    <div id="header_info">
                        <el-upload class="avatar-uploader" :action="getActionUrl()" :show-file-list="false"
                            :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                            <img v-if="headImgUrl" :src="headImgUrl" class="avatar" />
                            <el-icon v-else class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                            <div class="changeHead">修改头像</div>
                        </el-upload>
                        <h2>{{ loginUser.username }}</h2>
                    </div>

                    <el-form :model="loginUser" style="padding-left: 50px; padding-top: 20px ;margin-bottom: 30px;">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="用户名：">
                                    <el-text class="mx-1">{{ userInfo.username }}</el-text>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="权限：">
                                    <el-text class="mx-1">{{ userInfo.permission }}</el-text>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="昵称：">
                                    <el-input v-model="userInfo.nickname" :disabled="modifyDisabled"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="性别：">
                                    <el-radio-group v-model="userInfo.sex" :disabled="modifyDisabled">
                                        <el-radio value="男" size="default">男</el-radio>
                                        <el-radio value="女" size="default">女</el-radio>
                                    </el-radio-group>
                                </el-form-item>

                            </el-col>
                        </el-row>
                        <el-row>

                            <el-col :span="12">
                                <el-form-item label="账户余额：">
                                    <el-text class="mx-1">{{ userInfo.balance }}元</el-text>

                                </el-form-item>
                            </el-col>
                            <el-col :span="2">
                                <el-button @click="modify">修改信息</el-button>
                            </el-col>
                            <el-col :span="2">
                                <el-button @click="modifyPwd">修改密码</el-button>
                            </el-col>
                            <el-col :span="2">
                                <el-button @click="recharge">充值</el-button>
                            </el-col>
                        </el-row>
                        <div style="display: flex; justify-content: space-evenly;" v-if="!modifyDisabled">
                            <el-button type="success" round @click="confirmModify">确认修改</el-button>
                            <el-button type="warning" round @click="cancelModify">取消修改</el-button>
                        </div>

                    </el-form>

                </div>
                <div id="right">
                    <transition name="el-zoom-in-top">
                        <el-card v-show="showRechargeCard">
                            <label style="margin-right: 30px;">请输入充值金额
                            </label>
                            <el-input-number v-model="rechargeMoney" style="width: 200px" :min=0 type="number"
                                placeholder="请输入支付金额" />

                            <div id="paymentContainer">
                                <el-card shadow="hover" v-for="(payment, index) in paymentList" :key="index"
                                    class="paymentBox" :class="{ 'selected-payment': selectedPaymentIndex === index }"
                                    @click="selectPayment(index)">
                                    <el-image style="width: 100px; height: 30px" :src="payment.imgSrc" fit="contain" />
                                </el-card>
                            </div>
                            <div id="rechargeContainer">
                                <el-button type="success" round @click="payHandle">确认充值</el-button>
                                <el-button type="danger" round @click="cancelPay">取消充值</el-button>
                            </div>
                        </el-card>
                    </transition>
                    <transition name="el-zoom-in-top">
                        <el-card v-show="showModifyPwd">
                            <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules"
                                class="demo-ruleForm" size="default" label-position="top">

                                <el-form-item class="el_input" label="旧密码" prop="originalPassword"
                                    name="originalPassword">
                                    <el-input v-model="ruleForm.originalPassword" type="password" autocomplete="off"
                                        show-password />
                                </el-form-item>
                                <el-form-item class="el_input" label="新密码" prop="newPassword1" name="password1">
                                    <el-input v-model="ruleForm.newPassword1" type="password" autocomplete="off"
                                        show-password />
                                </el-form-item>
                                <el-form-item class="el_input" label="重新输入新密码" prop="newPassword2" name="password2">
                                    <el-input v-model="ruleForm.newPassword2" type="password" autocomplete="off"
                                        show-password />
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" @click="confirmModifyPwd">
                                        修改密码
                                    </el-button>
                                    <el-button type="primary" @click="showModifyPwd = false">
                                        取消
                                    </el-button>
                                </el-form-item>
                            </el-form>
                        </el-card>
                    </transition>
                </div>
            </el-card>
        </div>
        <div id="container2">
            <el-card style="width: 45%; ">
                <template #header>
                    <div class="card-header">
                        <span>交易记录</span>
                    </div>
                </template>
                <el-table :data="transactionRecordList" style="width: 100%" size="large"
                    :default-sort="{ prop: 'transactionTime', order: 'descending' }" height="350">
                    <el-table-column prop="transactionTime" label="交易时间" sortable width="180"></el-table-column>
                    <el-table-column prop="transactionMoney" label="交易金额" width="120"></el-table-column>
                    <el-table-column prop="transactionType" label="支付方式" width="120"></el-table-column>
                    <el-table-column prop="description" label="交易说明"></el-table-column>
                </el-table>
            </el-card>
            <el-card style="width: 45%;">
                <template #header>
                    <div class="card-header">
                        <span>账号操作</span>
                    </div>
                </template>
                <div id="operationBox">
                    <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled"
                        icon-color="#626AEF" title="确定要退出登录吗？" @confirm="logout">
                        <template #reference>
                            <el-card class="accountOperation" shadow="hover">
                                <div class="svg_box"><svg t="1731324703409" class="icon" viewBox="0 0 1024 1024"
                                        version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4266" width="48"
                                        height="48">
                                        <path
                                            d="M511.393 937.197l221.281-238.02-64.441-60-110.79 119.22V421.876h-92.16v336.471l-110.795-119.17-64.431 60z"
                                            fill="#409EFF" p-id="4267"></path>
                                        <path
                                            d="M882.262 86.803H141.739C63.463 86.803 0.007 150.259 0.007 228.535v566.929c0 78.276 63.456 141.732 141.732 141.732h157.663v-80H141.739c-34.039 0-61.732-27.693-61.732-61.732V228.536c0-34.04 27.693-61.732 61.732-61.732h740.522c34.04 0 61.732 27.693 61.732 61.732v566.929c0 34.039-27.692 61.732-61.732 61.732H724.6v80h157.662c78.276 0 141.732-63.456 141.732-141.732V228.536c0-78.277-63.456-141.733-141.732-141.733z"
                                            fill="#409EFF" p-id="4268"></path>
                                    </svg></div>

                                <div class="operationText">
                                    <div style="font-size: 18px; font-weight: 500;">退出登录</div>
                                    <div class="bigDiv">再次打开页面将重新登录</div>
                                </div>
                            </el-card>
                        </template>
                    </el-popconfirm>
                    <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled"
                        icon-color="#626AEF" title="确定要注销账号吗？" @confirm="writeOff">
                        <template #reference>
                            <el-card class="accountOperation" shadow="hover">
                                <div class="svg_box">
                                    <svg t="1731325886242" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                        xmlns="http://www.w3.org/2000/svg" p-id="6419" width="48" height="48">
                                        <path
                                            d="M987.52 1003.52a30.72 30.72 0 0 1-21.76-8.32l-277.12-263.68a31.36 31.36 0 0 1 0-44.8 32 32 0 0 1 44.8 0l277.12 263.04a31.36 31.36 0 0 1 0 44.8 32.64 32.64 0 0 1-23.04 8.96z"
                                            fill="#409EFF" p-id="6420"></path>
                                        <path
                                            d="M717.44 1010.56a31.36 31.36 0 0 1-21.76-8.96 30.72 30.72 0 0 1 0-44.16L960 680.32a32 32 0 1 1 46.08 43.52l-263.04 277.12a32.64 32.64 0 0 1-25.6 9.6zM463.36 554.88a274.56 274.56 0 1 1 273.92-273.92 274.56 274.56 0 0 1-273.92 273.92z m0-485.76a211.84 211.84 0 1 0 211.2 211.84 211.84 211.84 0 0 0-211.2-211.84z"
                                            fill="#409EFF" p-id="6421"></path>
                                        <path
                                            d="M586.24 1017.6h-576v-70.4a455.04 455.04 0 0 1 453.76-455.04 31.36 31.36 0 1 1 0 64 392.96 392.96 0 0 0-392.32 391.04v7.04h512a32 32 0 0 1 0 64z"
                                            fill="#409EFF" p-id="6422"></path>
                                    </svg>
                                </div>
                                <div class="operationText">
                                    <div style="font-size: 18px; font-weight: 500;">账号注销</div>
                                    <div class="bigDiv">永久注销，谨慎操作</div>
                                </div>
                            </el-card>
                        </template>
                    </el-popconfirm>
                </div>

            </el-card>
        </div>

    </div>
</template>
<script lang="ts" setup>
import axios from '@/api/request.js';
import { computed, ref, onMounted, reactive, watch, } from 'vue'
import { definedUser } from '@/stores/index.js';
import { ElMessage } from 'element-plus'
import { Plus, InfoFilled } from '@element-plus/icons-vue'
import type { UploadProps } from 'element-plus'
import { useRouter } from 'vue-router';

let router = useRouter();

let loginUser = definedUser()

const showRechargeCard = ref(false)
//存储用户信息
const userInfo = reactive({
    uid: "",
    username: "",
    password: "",
    nickname: "",
    sex: "",
    balance: "",
    permission: ""
})
const fetchUserInfo = () => {
    try {
        axios.get(`/user/findUser/${loginUser.uid}`, {
        }).then(function (response) {
            userInfo.uid = response.data.data.uid
            userInfo.username = response.data.data.username
            userInfo.password = response.data.data.password
            userInfo.nickname = response.data.data.nickname
            loginUser.nickname = userInfo.nickname
            userInfo.sex = response.data.data.sex
            userInfo.balance = response.data.data.balance
            userInfo.permission = response.data.data.permission
        })
    }
    catch (error) {
        ElMessage.error('获取用户信息失败')
    }
}

const modifyDisabled = ref(true)
//修改信息点击事件
const modify = () => {
    modifyDisabled.value = !modifyDisabled.value
}
//确认修改点击事件
const confirmModify = () => {
    try {
        axios.put(`/user/updateUser`, {
            uid: userInfo.uid,
            nickname: userInfo.nickname,
            sex: userInfo.sex,
        }).then(function (response) {
            ElMessage.success('修改成功')
            modifyDisabled.value = true
            fetchUserInfo()
        })
    }
    catch (error) {
        ElMessage.error('修改失败')
    }
}
//取消修改
const cancelModify = () => {
    ElMessage.warning('取消修改')
    modifyDisabled.value = true
    fetchUserInfo()
}

const recharge = () => {
    showModifyPwd.value = false
    setTimeout(() => {
        showRechargeCard.value = !showRechargeCard.value
    }, 300);
}

//充值金额
const rechargeMoney = ref(0)


//支付方式图标列表
const paymentList = ref([
    { imgSrc: "/src/images/AliPay.png", name: '支付宝' },
    { imgSrc: "/src/images/WeChatPay.png", name: '微信支付' },
    { imgSrc: "/src/images/yunShanFuPay.png", name: '云闪付' },
    // { imgSrc: "/src/images/applePay.png", name: '苹果支付' },
    // { imgSrc: "/src/images/paypalPay.png", name: '贝宝支付' },
]);
const selectedPaymentIndex = ref()
const rechargeType = ref()
function selectPayment(index) {
    selectedPaymentIndex.value = index; // 更新当前选中的卡片索引
    rechargeType.value = paymentList.value[index].name;
}

const payHandle = () => {
    if (rechargeMoney.value > 0) {
        if (selectedPaymentIndex.value != null) {
            axios.put("/user/rechargeBalance", {
                uid: userInfo.uid,
                money: rechargeMoney.value,
            }).then(function (response) {
                fetchUserInfo()
                showRechargeCard.value = false
            }).catch(function (error) {
                console.log(error);
            })
            ElMessage({
                message: '充值成功',
                type: 'success',
            })
            axios.post("/transaction/addIn", {
                uid: userInfo.uid,
                transactionMoney: rechargeMoney.value,
                transactionType: rechargeType.value,
                description: `为${userInfo.nickname}账户充值`,
                inOrOut: 1
            }).catch(function (error) {
                console.log(error);
            })
            setTimeout(() => {
                fetchTransactionRecordList()
            }, 20);

        }
        else {
            ElMessage({
                message: '请选择支付方式',
                type: 'error',
            })
        }
    }
    else {
        ElMessage({
            message: '请填写充值金额',
            type: 'error',
        })
    }
}
const cancelPay = () => {
    showRechargeCard.value = false
    ElMessage({
        message: '取消充值',
        type: 'warning',
    })
}

const modifyPwd = () => {
    showRechargeCard.value = false
    setTimeout(() => {
        showModifyPwd.value = !showModifyPwd.value
    }, 300);
}

const showModifyPwd = ref(false)


const ruleForm = reactive({
    originalPassword: '',
    newPassword1: '',
    newPassword2: '',
})
//密码校验规则
const checkPassword = (rule: any, value: any, callback: any) => {
    if (ruleForm.newPassword1 !== ruleForm.newPassword2) {
        callback(new Error('两次输入的密码不一致'))
    }
    else {
        callback();
    }
}
const rules = reactive<FormRules<typeof ruleForm>>({

    originalPassword: [
        { required: true, message: '请输入原密码', trigger: 'blur' },
    ],
    newPassword1: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 6, message: '密码长度不少于6位', trigger: 'blur' }

    ],
    newPassword2: [
        { required: true, validator: checkPassword, trigger: 'blur' }
    ],
})
const confirmModifyPwd = () => {
    axios.put("/user/modifyPwd", {
        uid: userInfo.uid,
        originalPassword: ruleForm.originalPassword,
        newPassword1: ruleForm.newPassword1,
        newPassword2: ruleForm.newPassword2,
    }).then(function (response) {
        const responseCode = response.data.code
        if (responseCode === 510) {
            ElMessage({
                message: response.data.message,
                type: 'error',
            })
        } else if (responseCode == 511) {
            ElMessage({
                message: response.data.message,
                type: 'error',
            })

        }
        else if (responseCode === 200) {
            showModifyPwd.value = false
            ElMessage({
                message: '修改成功',
                type: 'success',
            })
            ruleForm.originalPassword = null
            ruleForm.newPassword1 = null
            ruleForm.newPassword2 = null
        }
    }).catch(function (error) {
        console.log(error);
    })
}


const headImgUrl = ref(`http://localhost:8999/images/upload/headPortrait/${loginUser.headImgUrl}`)
const handleAvatarSuccess = (response) => {

    headImgUrl.value = "http://localhost:8999/images/upload/headPortrait/" + response
    loginUser.headImgUrl = response
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
    if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
        ElMessage.error('上传头像图片只能是 jpg 或者 png 格式')
        return false
    } else if (rawFile.size / 1024 / 1024 > 2) {
        ElMessage.error('上传的头像图片大小不能超过 2MB!')
        return false
    }
    return true
}
const getActionUrl = () => {
    return `http://localhost:8999/files/upload/${userInfo.uid}`
}


const transactionRecordList = reactive<TransactionRecord[]>([])

interface TransactionRecord {
    transactionId: Number;
    uid: Number;
    transactionMoney: String;
    transactionType: String;
    transactionTime: String;
    inOrOut: Number;
    description: String
}
const fetchTransactionRecordList = () => {
    axios.get(`/transaction/findAllTransactionRecord/${loginUser.uid}`).then(function (response) {
        transactionRecordList.value = response.data.data
        const convertedData: TransactionRecord[] = response.data.data.map(item => ({
            ...item,
            transactionMoney: item.inOrOut === 1 ? "+" + item.transactionMoney + "元" : "-" + item.transactionMoney + "元"
        }));
        //清空原数组，加入响应回的数组
        transactionRecordList.splice(0, transactionRecordList.length, ...convertedData);
    }).catch(function (error) {
        console.log(error);
    })
}

const logout = () => {
    // 销毁token
    loginUser.$reset();
    localStorage.removeItem('token')
    // 跳转到登录页
    router.push({ name: 'Login' });
};
const writeOff = () => {
    axios.delete(`/user/deleteUser/${userInfo.uid}`).catch(function (error) {
        console.log(error);
    })
    ElMessage({
        message: '注销成功',
        type: 'success',
    });
    logout()
}


onMounted(() => {
    fetchUserInfo()
    fetchTransactionRecordList()
})
</script>
<style scoped>
#box {
    display: flex;
    flex-direction: column;
    width: 82%;
    height: auto;
    float: right;
    position: relative;
    top: 80px;
    margin-right: 10px;
    z-index: 1;
    display: flex;
    justify-content: center;
    border-radius: 10px;
}

#container1 {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 30px;
    margin-top: 20px;
}

#container2 {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-around;
}

#header_info {
    display: flex;
    align-items: center;
    justify-content: start;
    gap: 20px;
}

#left {
    float: left;
    width: 55%;
}

#right {
    float: right;
    width: 45%;
}

.el-row {
    display: flex;
    gap: 30px;
}

#paymentContainer {
    margin-top: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    flex-wrap: wrap;
}

.paymentBox {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    justify-content: center;
    max-width: 220px;
    text-align: center;
}

#rechargeContainer {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
}

.selected-card {
    border: 2px solid #409EFF;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.selected-payment {
    border: 2px solid #409EFF;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.avatar-uploader .avatar {
    width: 100px;
    height: 100px;
    display: block;
}

.changeHead {
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 60%;
    padding: 5px 0;
    transform: translateX(-50%);
    text-align: center;
    color: #000;
    opacity: 0;
    transition: opacity 0.3s ease;
    font-size: 11px;
    font-weight: 900;
    cursor: pointer;
}

.changeHead:hover {
    opacity: 0.7;

}

.avatar:hover+.changeHead {
    opacity: 0.7;
}
</style>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 50px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    text-align: center;
}

#operationBox {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 40px;
}

.accountOperation {
    width: 80%;
    height: 154px;
    cursor: pointer;
}

.svg_box {
    position: relative;
    display: inline-block;
    top: 30px;
    left: 30px;
}

.operationText {
    position: relative;
    display: inline-block;
    top: 25px;
    left: 55px;

}

.bigDiv {
    font-size: 14px;
    color: #9B9B9B;
}
</style>