<template>
    <div class="modify-phone-container">
        <div class="header">
            <span class="back-arrow" @click="goBack">←</span>
        </div>
        <div class="input-container">
            <h2>绑定手机号</h2>
            <div class="phone-input-button">
                <input type="text" id="phone" v-model="inputPhone" placeholder="请输入手机号" class="phone-input" />
                <button class=" send-code-button" :class="{ 'active-button': inputPhone.length == 11 }"
                    @click="sendCode">{{ countdown > 0 ? `${countdown}秒后重试` : '发送验证码' }}</button>
            </div>
            <transition name="el-zoom-in-top">
                <div style="display: flex; flex-direction: column; align-items:center " v-if="inputPhone.length == 11">
                    <input type="text" id="Code" v-model="inputCode" placeholder="请输入验证码" class="code-input" />
                    <button class="bindPhone" :class="{ 'active-bindButton': inputCode.length == 6 }"
                        @click="handleBindPhone">绑定手机号</button>
                </div>
            </transition>

        </div>
        <div class="phone-status" v-if="loginUser.phone">
            <span>已绑定手机号 {{ loginUser.phone }}</span>
        </div>
    </div>
    <div class="footer-text">
        要养老，上养老生活 ®
    </div>t
</template>

<script setup>
import { ref, watch } from 'vue';
import { definedUser } from '@/stores/index.js';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'
import axios from '@/api/request.js';
let loginUser = definedUser()
let router = useRouter()
const inputPhone = ref('');
const inputCode = ref('');
const countdown = ref(0)
const code = ref()
const goBack = () => {
    router.go(-1)
};
const legalPhone = ref(false)
watch(() => inputPhone.value, (newVal) => {
    legalPhone.value = !/^[0-9]{11}$/.test(newVal);
})
const sendCode = () => {
    // 发送验证码的逻辑
    if (legalPhone.value) {
        ElMessage.warning('请输入正确的手机号')
    }
    else {
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
};
const handleBindPhone = () => {

    if (inputCode.value != code.value) {
        ElMessage.error('验证码错误')
    } else {
        axios.put('/user/modifyPhone', {
            uid: loginUser.uid,
            phone: inputPhone.value
        }).then(function (response) {
            console.log(response);
            if (response.data.code === 508) {
                ElMessage.error(response.data.message)
            }
            if (response.data.code === 200) {
                ElMessage.success('绑定成功')
                loginUser.phone = inputPhone.value
            }

        }).catch(function (error) {
            console.log(error);

        })
    }

}
</script>

<style scoped>
.modify-phone-container {
    padding: 20px;
    background-color: #fff;
    min-height: 100vh;
}

.header {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
    position: sticky;
    top: 0;
    z-index: 10;
    padding: 20px;
    padding-bottom: 10px;

}

.back-arrow {
    font-size: 24px;
    cursor: pointer;
}

.input-container {
    padding-left: 20px;
}

.phone-input {
    border: none;
    border-bottom: 1px solid #ccc;
    border-radius: 0;
    font-size: 16px;
    color: #333;
    transition: border-bottom 0.3s;
    margin-top: 30px;
    width: 230px;
    padding: 13px;
    padding-left: 0;
}

.phone-input::placeholder {
    color: #999;
    font-size: 14px;
}

.phone-input-button {
    display: flex;
    align-items: center;
    justify-content: start;
    gap: 20px;
}

.send-code-button {
    margin-top: 30px;
    background-color: #fff;
    color: #999;
    font-size: 12px;
    border: 1px solid #999;
    border-radius: 4px;
    cursor: pointer;
    width: 100px;
    height: 30px;
    transition: all 0.3s;
}

.send-code-button.active-button {
    color: #00bfa5;
    border: 1px solid #00bfa5;
}

.bindPhone.active-bindButton {
    background-color: #00bfa5;
}

.code-input {
    border: none;
    border-bottom: 1px solid #ccc;
    border-radius: 0;
    font-size: 16px;
    color: #333;
    transition: border-bottom 0.3s;
    margin-top: 20px;
    width: 96%;
    padding: 13px;
    padding-left: 0;
}

.code-input::placeholder {
    color: #999;
    font-size: 14px;
}

.phone-status {
    margin-top: 30px;
    padding-left: 20px;
    color: red;
    font-size: 14px;
}


.bindPhone {
    width: 90%;
    margin-top: 30px;
    padding: 10px;
    border: none;
    color: #fff;
    background-color: #b3b3b3;
    font-size: 13px;
    transition: all 0.3s;

}

.phone-input:focus {
    border-bottom: 1px solid #00bfa5;
}

.code-input:focus {
    border-bottom: 1px solid #00bfa5;
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
