<template>
    <div id="box">
        <h1>养老院管理系统</h1>
        <div class="form-container">
            <div class="login-img">
                <img src="@/images/login-img.png" alt="" style="width: 100%;">
            </div>
            <div class="form-div">

                <h2 style="width: 300px;">注册</h2>
                <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" class="demo-ruleForm"
                    size="large" label-position="top">
                    <el-form-item class="el_input" label="用户名" prop="username" name="username">
                        <el-input v-model="ruleForm.username" type="" autocomplete="off" />
                        <div class="el-form-item__error" v-if="ruleForm.usernameFlag">用户名被占用</div>
                    </el-form-item>
                    <el-form-item class="el_input" label="密码" prop="password" name="password">
                        <el-input v-model="ruleForm.password" type="password" autocomplete="off" show-password />
                    </el-form-item>
                    <el-form-item class="el_input" label="重复密码" prop="password2" name="password2">
                        <el-input v-model="ruleForm.password2" type="password" autocomplete="off" show-password />
                    </el-form-item>
                    <el-form-item label="我是" label-position="left" prop="permission">
                        <el-radio-group v-model="ruleForm.permission">
                            <el-radio value="普通用户">普通用户</el-radio>
                            <el-radio value="员工">员工</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="register">
                            注册
                        </el-button>
                        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
                        <el-button @click="toLogin">去登录</el-button>

                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import axios from '@/api/request.js';

import { definedUser } from '@/stores/index';
import { useRouter } from 'vue-router';
let router = useRouter()
let registerUser = definedUser()
const ruleFormRef = ref<FormInstance>()

const ruleForm = reactive({
    username: '',
    password: '',
    password2: '',
    permission: '',
    code: '',
    usernameFlag: false,
})
//密码校验规则
const checkPassword = (rule: any, value: any, callback: any) => {
    if (ruleForm.password2 == '') {
        callback(new Error('请再次输入密码'));
    }
    else if (ruleForm.password !== ruleForm.password2) {
        callback(new Error('两次输入的密码不一致'))
    }
    else {
        callback();
    }
}
//监听在用户名被占用情况下，用户名发生改变后不显示错误信息
watch(
    () => ruleForm.username,
    (newValue, oldValue) => {
        if (oldValue !== newValue) {
            ruleForm.usernameFlag = false;
        }
    }
);
//监听在用户名被占用情况下，显示错误信息
watch(
    () => ruleForm.code,
    () => {
        if (ruleForm.code == 505) {
            ruleForm.usernameFlag = true;
            ruleForm.code = ''
        }
    }
);

const rules = reactive<FormRules<typeof ruleForm>>({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, message: '密码长度不少于6位', trigger: 'blur' }
    ],
    password2: [
        { required: true, validator: checkPassword, trigger: 'blur' }
    ],
    permission: [
        { required: true, message: '请选择您的身份', trigger: 'change' }
    ],
})

async function register() {
    await axios.post("/user/register", {
        username: ruleForm.username,
        password: ruleForm.password,
        permission: ruleForm.permission
    }).then(function (response) {
        // 如果服务器返回200，则跳转至登录页
        if (response.data.code == 200) {
            router.push('/login');
        }
        else if (response.data.code == 505) {
            ruleForm.code = response.data.code
        }
        return response;
    })
        .catch(function (error) {
            console.log(error);
        });

}

function toLogin() {
    router.push({ name: 'Login' });
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

.form-div {
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