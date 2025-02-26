<template>
    <div class="modify-password-container">
        <div class="header">
            <span class="back-arrow" @click="goBack">←</span>
        </div>
        <div class="input-container">
            <h2>设置密码</h2>
            <div class="password-input-button">
                <input type="text" id="password1" v-model="originalPassword" placeholder="请输入原密码"
                    class="password-input" />
                <input type="text" id="password2" v-model="newPassword1" placeholder="请设置新密码" class="password-input" />
            </div>
            <div class="password-status">
                <span>密码为6-16位字母、数字和特殊字符</span>
            </div>
            <transition name="el-zoom-in-top">
                <div>
                    <input type="text" id="password3" v-model="newPassword2" placeholder="请再次输入新密码"
                        class="password-input" />
                </div>
            </transition>
            <div style="display: flex; align-items: center;
            justify-content: center;">
                <button class="bindpassword" @click="handleModifyPassword">确认密码</button>
            </div>
        </div>

    </div>
    <div class="footer-text">
        要养老，上养老生活 ®
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { definedUser } from '@/stores/index.js';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'
import axios from '@/api/request.js';
let loginUser = definedUser()
let router = useRouter()
const originalPassword = ref('');
const newPassword1 = ref('');
const newPassword2 = ref('');
const goBack = () => {
    router.go(-1)
};
// 定义密码校验正则表达式
const passwordRegex = /^([A-Za-z]{6,16}|\d{6,16}|[@$!%*#?&]{6,16})$/

const handleModifyPassword = () => {
    if (!passwordRegex.test(newPassword1.value)) {
        ElMessage.error('密码格式错误')
    } else {
        axios.put("/user/modifyPwd", {
            uid: loginUser.uid,
            originalPassword: originalPassword.value,
            newPassword1: newPassword1.value,
            newPassword2: newPassword2.value,
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
                ElMessage({
                    message: '修改成功',
                    type: 'success',
                })
            }
        }).catch(function (error) {
            console.log(error);
        })
    }
}
</script>

<style scoped>
.modify-password-container {
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
    padding: 15px;
    padding-bottom: 10px;

}

.back-arrow {
    font-size: 24px;
    cursor: pointer;
}

.input-container {
    padding-left: 20px;
    padding-right: 20px;
}

.password-input {
    border: none;
    border-bottom: 1px solid #ccc;
    border-radius: 0;
    font-size: 16px;
    color: #333;
    transition: border-bottom 0.3s;
    margin-top: 30px;
    width: 95%;
    padding: 20px;
    padding-left: 0;
}

.password-input::placeholder {
    color: #999;
    font-size: 16px;
}

.password-status {
    margin-top: 10px;
    color: #a4a4a4;
    font-size: 14px;
}

.bindpassword {
    width: 90%;
    margin-top: 30px;
    padding: 10px;
    border: none;
    color: #fff;
    background-color: #00bfa5;
    font-size: 13px;
    transition: all 0.3s;
}

.password-input:focus {
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
