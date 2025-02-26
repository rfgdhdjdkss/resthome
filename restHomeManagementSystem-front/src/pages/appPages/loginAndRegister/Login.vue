<template>
    <div class="login-page">
        <div class="header">
            <span class="back-arrow" @click="router.go(-1)">←</span>
        </div>
        <h1 class="title">欢迎来到养老生活</h1>
        <p class="slogan">帮助每个人更简单享老</p>

        <div v-if="isPhoneLogin">
            <input type="text" placeholder="请输入手机号" v-model="phoneLoginUser.phone" />
            <input type="text" placeholder="请输入验证码" v-model="phoneLoginUser.code" v-if="showCode" />

            <div class="button-group">
                <button :disabled="disabled || countdown > 0" @click="getCode" :class="{ 'half-width': showCode }">
                    {{ countdown > 0 ? `${countdown}秒后重试` : '获取验证码' }}
                </button>
                <button v-if="showCode" @click="loginWithCode" class="half-width">登录</button>
            </div>

            <div class="agreement">
                <input type="checkbox" id="agreement" v-model="agree" />
                <label for="agreement">我同意
                    <a @click="router.push({ name: 'PensionAgreement' })">《养老用户协议》</a> 及
                    <a @click="router.push({ name: 'PrivacyPolicy' })">《养老隐私政策》</a>
                </label>
            </div>

            <div style="display: flex;gap: 30px;">
                <h5 @click="toUserNameLogin">账号密码登录</h5>
                <h5 @click="toRegister">注册</h5>
            </div>
        </div>

        <div v-else>
            <form action="">
                <input type="text" placeholder="请输入账号" v-model="usernameLoginUser.username" />
                <input type="password" placeholder="请输入密码" v-model="usernameLoginUser.password" />

                <button @click="login">登录</button>
            </form>

            <div class="agreement">
                <input type="checkbox" id="agreement" v-model="agree" />
                <label for="agreement">我同意
                    <a @click="router.push({ name: 'PensionAgreement' })">《养老用户协议》</a> 及
                    <a @click="router.push({ name: 'PrivacyPolicy' })">《养老隐私政策》</a>
                </label>
            </div>

            <div style="display: flex;gap: 30px;">
                <h5 @click="toPhoneLogin">手机号登录</h5>
                <h5 @click="toRegister">注册</h5>
            </div>
        </div>

        <div class="other-login">
            <p>其他登录方式</p>
            <div class="quick-login">
                <div class="huojian">
                    <img src="@/assets/images/huojian.png" alt="" width="30px" height="30px">
                </div>
                <p class="quick-login-p">快捷登录</p>
            </div>
        </div>
    </div>
</template>
<script setup>
import { ref, watch } from 'vue';
import { ElMessage } from 'element-plus'
import axios from '@/api/request.js';
import { useRouter } from 'vue-router';
import { definedUser } from '@/stores/index';

let router = useRouter()
let loginUser = definedUser()

const isPhoneLogin = ref(true)
const toUserNameLogin = () => {
    isPhoneLogin.value = false;
    phoneLoginUser.value.phone = ''
    phoneLoginUser.value.code = ''
}
const toPhoneLogin = () => {
    isPhoneLogin.value = true;
    usernameLoginUser.value.username = ''
    usernameLoginUser.value.password = ''

}
const phoneLoginUser = ref({
    phone: '',
    code: '',
})
const usernameLoginUser = ref({
    username: '',
    password: '',
})
const disabled = ref(true)
watch(() => phoneLoginUser.value.phone, (newVal) => {
    disabled.value = !/^[0-9]{11}$/.test(newVal);
})
const agree = ref(false)
const showCode = ref(false)
const countdown = ref(0)
const code = ref()
const getCode = () => {
    if (!agree.value) {
        ElMessage.warning('请先阅读协议并勾选同意')
        return;
    }
    showCode.value = true
    code.value = Math.floor(100000 + Math.random() * 900000);
    console.log('验证码:', code.value);
    ElMessage.success('验证码:' + code.value)

    countdown.value = 60
    const interval = setInterval(() => {
        countdown.value -= 1
        if (countdown.value <= 0) {
            clearInterval(interval)
        }
    }, 1000)
}

const loginWithCode = () => {
    if (phoneLoginUser.value.code === '') {
        ElMessage.warning('请输入验证码');
        return;
    }
    // 验证码登录逻辑
    console.log(code.value);
    console.log(phoneLoginUser.value.code);

    if (code.value == phoneLoginUser.value.code) {

        axios.post("/user/loginByPhone", {
            phone: phoneLoginUser.value.phone,
            code: phoneLoginUser.value.code,
        }).then(function (response) {
            console.log(response);
            if (response.data.code == 200) {
                ElMessage.success('登录成功');
                localStorage.setItem('token', response.data.data.token);
                loginUser.uid = response.data.data.uid;
                loginUser.token = response.data.data.token;
                loginUser.username = response.data.data.username;
                loginUser.nickname = response.data.data.nickname;
                loginUser.permission = response.data.data.permission;
                loginUser.balance = response.data.data.balance;
                loginUser.headImgUrl = response.data.data.headImgUrl;
                loginUser.phone = response.data.data.phone;
                loginUser.sex = response.data.data.sex;
                router.push({ name: 'Mine_app' })
            }
            else if (response.code = 506) {
                ElMessage.error(response.data.message);
            }
        }).catch(function (error) {
            ElMessage.error('登录失败');
        })
    }
    else {
        ElMessage.error('验证码错误');
    }
}

const login = () => {
    if (!agree.value) {
        ElMessage.warning('请先阅读协议并勾选同意')
        return;
    }
    if (usernameLoginUser.value.username === '') {
        ElMessage.warning('请输入账号')
        return;
    }
    if (usernameLoginUser.value.password === '') {
        ElMessage.warning('请输入密码')
        return;
    }
    // 登录逻辑
    axios.post("/user/loginByUsername", {
        username: usernameLoginUser.value.username,
        password: usernameLoginUser.value.password,
    }).then(function (response) {
        console.log(response);
        if (response.data.code == 200) {
            ElMessage.success('登录成功');
            localStorage.setItem('token', response.data.data.token);
            loginUser.uid = response.data.data.uid;
            loginUser.token = response.data.data.token;
            loginUser.username = response.data.data.username;
            loginUser.nickname = response.data.data.nickname;
            loginUser.permission = response.data.data.permission;
            loginUser.balance = response.data.data.balance;
            loginUser.headImgUrl = response.data.data.headImgUrl;
            loginUser.phone = response.data.data.phone;
            loginUser.sex = response.data.data.sex;
            router.push({ name: 'Mine_app' })
        }
        else if (response.code = 506) {
            ElMessage.error(response.data.message);
        }
    }).catch(function (error) {
        ElMessage.error('登录失败')
    })
}
const toRegister = () => {
    router.push({ name: 'Register_app' })
}
</script>
<style scoped>
.login-page {
    padding: 5%;
    box-sizing: border-box;
}

.header {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
}

.back-arrow {
    font-size: 24px;
    cursor: pointer;
    margin-top: 10px;
}

input[type="text"],
input[type="password"] {
    width: 90%;
    padding: 10px;
    margin: 10px 0;
    border: none;
    border-bottom: 1px solid #000;
}

.agreement {
    display: flex;
    align-items: center;
    margin: 10px 10px;
}

.button-group {
    display: flex;
    justify-content: space-between;
    transition: all 0.3s ease;
}

button {
    background-color: #00bfa5;
    color: white;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    margin: 20px 0;
    width: 95%;
    transition: all 0.3s ease;
}

button.half-width {
    width: 45%;
}

button:disabled {
    background-color: #ccc;
}

.warning {
    color: #ff5722;
    margin-top: 10px;
}

.other-login {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    color: #9f9f9f;
    font-size: 13px;
    position: fixed;
    bottom: 100px;
    left: 40%;
}

.quick-login {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.quick-login-p {
    color: #ff5722;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    border-radius: 50%;
    font-size: 14px;
}

.title {
    margin-bottom: 0;
    margin-top: 50px
}

.slogan {
    color: #666;
    font-size: 16px;
    margin-bottom: 40px;
}

.huojian {
    width: 60px;
    height: 60px;
    border: 1px #e15240 solid;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 30px;
}

input::-webkit-input-placeholder {
    color: #9f9f9f;
}

a {
    text-decoration: none;
    color: #00bfa5;
}
</style>