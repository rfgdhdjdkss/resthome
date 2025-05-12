<template>
    <div id="box">
        <h1>养老院管理系统</h1>
        <div class="form-container">
            <div class="login-img">
                <img src="@/images/login-img.png" alt="" style="width: 100%;">
            </div>
            <div class="form-div">

                <h2 style="width: 300px;">登录</h2>
                <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" class="demo-ruleForm"
                    size="large" label-position="top">
                    <el-form-item class="el_input" label="用户名" prop="username" name="username">
                        <el-input v-model="ruleForm.username" type="" autocomplete="off" />
                    </el-form-item>
                    <el-form-item class="el_input" label="密码" prop="password" name="password">
                        <el-input v-model="ruleForm.password" type="password" autocomplete="off" show-password />
                    </el-form-item>
                    <el-form-item label="我是" label-position="left" prop="permission">
                        <el-radio-group v-model="ruleForm.permission">
                            <!-- <el-radio value="普通用户">普通用户</el-radio> -->
                            <el-radio value="员工">员工</el-radio>
                            <el-radio value="管理员">管理员</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="login">
                            登录
                        </el-button>
                        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
                        <el-button @click="toRegister">去注册</el-button>
                    </el-form-item>
                </el-form>
            </div>

        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import axios from '@/api/request.js';

import { definedUser } from '@/stores/index';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'

let router = useRouter()
let loginUser = definedUser()
const ruleFormRef = ref<FormInstance>()

const ruleForm = reactive({
    username: '',
    password: '',
    permission: ''
})

const rules = reactive<FormRules<typeof ruleForm>>({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, message: '密码长度不少于6位', trigger: 'blur' }
    ],
    permission: [
        { required: true, message: '请选择您的身份', trigger: 'change' }
    ]
})

async function login() {
    const token = await axios.post("/user/login", {
        username: ruleForm.username,
        password: ruleForm.password,
        permission: ruleForm.permission
    }).then(function (response) {
        console.log(response);

        if (response.data.code == 506) {
            ElMessage({
                message: response.data.message,
                type: 'error',
            })
        }
        else if (response.data.code == 501) {
            ElMessage({
                message: '用户名为空',
                type: 'error',
            })
        }
        else if (response.data.code == 508) {
            ElMessage({
                message: '密码为空',
                type: 'error',
            })
        }
        else if (response.data.code == 503) {
            ElMessage({
                message: '密码错误',
                type: 'error',
            })
        }
        else {
            localStorage.setItem('token', response.data.data.token);
            loginUser.uid = response.data.data.uid;
            loginUser.token = response.data.data.token;
            loginUser.username = response.data.data.username;
            loginUser.nickname = response.data.data.nickname;
            loginUser.permission = response.data.data.permission;
            loginUser.balance = response.data.data.balance;
            loginUser.headImgUrl = response.data.data.headImgUrl;
        }
        if (response.data.code == 200 && loginUser.token != null) {
            router.push('/home');
        }


    })
        .catch(function (error) {
            console.log(error);
        });
}

function toRegister() {
    router.push('/register');
}

const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}

</script>
<style scoped>
#box {
    background-color: #4473d9;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh;
    width: 100%;
}

h1 {
    font-size: 2.5em;
    color: #fff;
    margin-bottom: 20px;
}

.form-container {
    background: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
    width: 900px;
    gap: 60px;
}

h2 {
    text-align: center;
    margin-bottom: 20px;
}

.login-img {
    display: flex;
    align-items: center;
    justify-content: center;
    padding-left: 50px;
    width: 400px;
    height: 300px;


}
.form-div{
    width: 400px;
}
/* 添加动态漂浮圆点 */
.login-background::before {
    content: "";
    position: absolute;
    width: 300%;
    height: 300%;
    background: radial-gradient(circle,
            rgba(255, 255, 255, 0.1) 10%,
            transparent 20%);
    animation: float 20s linear infinite;
}

@keyframes float {
    from {
        transform: translate(-50%, -50%) rotate(0deg);
    }

    to {
        transform: translate(-50%, -50%) rotate(360deg);
    }
}

.el_input {
    width: 300px;
}
</style>