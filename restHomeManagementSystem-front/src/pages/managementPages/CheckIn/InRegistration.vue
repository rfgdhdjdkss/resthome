<!-- 来访登记 -->
<template>
    <div id="box">
        <h2 style="float: left;">来访登记</h2>
        <el-form ref="ruleFormRef" style="width:50%; margin-top:30px" :model="ruleForm" :rules="rules"
            class="demo-ruleForm" :size="formSize" status-icon>
            <!-- 老人姓名 -->
            <el-form-item label="老人姓名" prop="name">
                <el-input v-model="ruleForm.name" />
            </el-form-item>
            <!-- 性别 -->
            <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="ruleForm.sex">
                    <el-radio value="male">男</el-radio>
                    <el-radio value="female">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <!-- 联系电话 -->
            <el-form-item label="联系电话" prop="phone">
                <el-input v-model="ruleForm.phone" />
            </el-form-item>
            <!-- 预约人姓名 -->
            <el-form-item label="来访人姓名" prop="bookerName">
                <el-input v-model="ruleForm.bookerName" />
            </el-form-item>
            <!-- 来访时间 -->
            <el-form-item label="来访时间" prop="inTime">
                <el-date-picker v-model="ruleForm.inTime" type="datetime" aria-label="请选择来访时间" placeholder="请选择来访时间"
                    style="width: 100%" />
            </el-form-item>
            <!-- 备注信息 -->
            <el-form-item label="备注信息" prop="note">
                <el-input v-model="ruleForm.note" type="textarea" />
            </el-form-item>
            <el-form-item style="margin-left: 300px;">
                <el-button type="primary" @click="submitForm(ruleFormRef)">
                    提交
                </el-button>
                <el-button @click="resetForm(ruleFormRef)">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script lang="ts" setup>

import { reactive, ref } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { definedUser } from '@/stores/index.js';

import axios from '@/api/request.js';
let loginUser = definedUser()

//RuleForm接口，定义一个预定的所有属性
interface RuleForm {
    name: string
    sex: string
    phone: string
    bookerName: string
    outTime: string
    returnTime: string
    note: string
}
//定义表单大小
const formSize = ref<ComponentSize>('large')
//定义 FormInstance，用来操作 form 实例
const ruleFormRef = ref<FormInstance>()
//定义 ruleForm 初始值
const ruleForm = reactive<RuleForm>({
    name: "",
    sex: "",
    phone: "",
    bookerName: "",
    inTime: "",
    note: ""
})

//定义 rules，用来定义 form 验证规则
const rules = reactive<FormRules<RuleForm>>({
    name: [
        { required: true, message: '请填写老人姓名', trigger: 'blur' },
        { min: 2, max: 5, message: '请确保姓名长度在2-7个之间', trigger: 'blur' },
        { pattern: /^[\u4e00-\u9fa5]{0,}$/, message: '请填写汉字', trigger: 'change' },
    ],
    sex: [
        {
            required: true,
            message: '请选择老人的性别',
            trigger: 'change',
        },
    ],
    phone: [
        {
            required: true,
            pattern: /^.{11}$/,
            message: '请填写11位的手机号',
            trigger: 'blur',
        },
    ],
    bookerName: [
        {
            required: true,
            message: '请填写预定人姓名',
            trigger: 'blur',
        },
        { min: 2, max: 5, message: '请确保姓名长度在2-7个之间', trigger: 'blur' },
        { pattern: /^[\u4e00-\u9fa5]{0,}$/, message: '请填写汉字', trigger: 'change' },
    ],
    inTime: [
        { type: 'date', required: true, message: '请选择预定时间', trigger: 'change' },
    ],
    note: [
        {
            required: false,
        }]
})
//外出请求提交事件
function applyForIn() {
    //发送post请求
    axios.post("/inRegistration/add", {
        //请求参数：
        name: ruleForm.name,
        sex: ruleForm.sex,
        phone: ruleForm.phone,
        bookerName: ruleForm.bookerName,
        inTime: ruleForm.inTime,
        note: ruleForm.note,
        uid: loginUser.uid,
    }).then(function (response) {
        console.log(response);
        //响应成功弹出提交成功
        ElMessage({
            message: '提交成功',
            type: 'success',
        })
    }).catch(function (error) {
        console.log(error);
        //响应失败弹出提交失败
        ElMessage.error('提交失败')
    })
}

//提交按钮点击事件
const submitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            // 发送post请求
            applyForIn()
            console.log('submit!')
        } else {
            console.log('error submit!', fields)
        }
    })
}
//重置按钮点击事件
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    //重置 form表单并清空错误状态
    formEl.resetFields()
}


</script>

<style scoped>
.reserve-container {
    min-height: 90vh;
    background-color: #fff;
}

.header {
    justify-content: center;
    position: relative;
    display: flex;
    align-items: center;
    top: 0;
    z-index: 10;
    background-image: url(@/assets/images/home_app_background.jpg);
    color: #fff;
}

.back-arrow {
    position: absolute;
    left: 16px;
}

.header h4 {
    margin: 0;
    font-size: 18px;
    color: #000;
}

.form-wrapper {
    background-color: white;
    border-radius: 8px;
    padding: 15px;
}

.reserve-form {
    width: 100%;
}

.reserve-form :deep(.el-form-item) {
    margin-bottom: 15px;
}

.reserve-form :deep(.el-input__wrapper) {
    border-radius: 8px;
}

.reserve-form :deep(.el-select) {
    width: 100%;
}

.reserve-form :deep(.el-date-picker) {
    width: 100%;
}

.button-group {
    display: flex;
    margin-top: 20px;
    justify-content: space-between;
}

.button-group :deep(.el-button) {
    min-width: 120px;
    border-radius: 20px;
    margin-left: 50px;
}

.button-group :deep(.el-button--primary) {
    background-color: #56b4b0;
    border-color: #56b4b0;
}

.button-group :deep(.el-button--primary:hover) {
    background-color: #4ca19d;
    border-color: #4ca19d;
}

@media screen and (max-width: 768px) {
    .reserve-form :deep(.el-form-item__label) {
        float: none;
        display: block;
        text-align: left;
        margin-bottom: 8px;
        margin-top: 10px;
    }

    .reserve-form :deep(.el-form-item__content) {
        margin-left: 0 !important;
        margin-top: 10px;
    }
}
</style>
