<template>
    <div class="login-page">
        <div class="header">
            <span class="back-arrow" @click="router.go(-1)">←</span>

        </div>
        <h1 class="title">欢迎来到养老生活</h1>
        <p class="slogan">帮助每个人更简单享老</p>

        <div>
            <input type="text" placeholder="请输入用户名" v-model="registerUser.username" />
            <input type="text" placeholder="请输入密码" v-model="registerUser.password" />
            <input type="text" placeholder="请输入手机号" v-model="registerUser.phone" />
            <input type="text" placeholder="请输入验证码" v-model="registerUser.code" v-if="showCode" />

            <div class="button-group">
                <button :disabled="disabled || countdown > 0" @click="getCode" :class="{ 'half-width': showCode }">
                    {{ countdown > 0 ? `${countdown}秒后重试` : '获取验证码' }}
                </button>
                <button v-if="showCode" @click="registerWithCode" class="half-width">注册</button>
            </div>

            <div class="agreement">
                <input type="checkbox" id="agreement" v-model="agree" />
                <label for="agreement">我同意
                    <a href="#">《养老用户协议》</a> 及
                    <a href="#">《养老隐私政策》</a>
                </label>
            </div>

            <div style="display: flex;gap: 30px;">
                <h5 @click="toLogin">去登录</h5>
                
            </div>
        </div>

        
        <div class="other-login">
            <p>其他登录方式</p>
            <div class="quick-login">
                <div class="huojian">
                    <img src="../../assets/images/huojian.png" alt="" width="30px" height="30px">
                </div>
                <p class="quick-login-p">快捷登录</p>
            </div>
        </div>
    </div>
</template>
<script setup>
import { ref, watch } from 'vue';
import { ElMessage } from 'element-plus'
import axios from '../../../../api/request.js';
import { useRouter } from 'vue-router';
let router = useRouter()
const registerUser=ref({
    username:'',
    password:'',
    phone:'',
    code:''
})
const disabled = ref(true)
watch(() => registerUser.value.phone, (newVal) => {
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
const registerWithCode=() => {
    if (!agree.value) {
        ElMessage.warning('请先阅读协议并勾选同意')
        return;
    }
    if (code.value!= registerUser.value.code) {
        ElMessage.warning('验证码不正确')
        return;
    }
    axios.post('/user/registerWithApp', {
        username: registerUser.value.username,
        password: registerUser.value.password,
        phone: registerUser.value.phone,
    }).then(res => {
        if (res.data.code === 200) {
            ElMessage.success('注册成功')
            router.push({ name: 'Login_app' })
        } else {
            ElMessage.error(res.data.message)
        }
    }).catch(err => {
        ElMessage.error('注册失败')
    })
}
const toLogin=()=>{
router.push({name:'Login_app'})
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