<template>
    <div class="edit-profile-container">
        <div class="header">
            <span class="back-arrow" @click="router.push('Mine_app')">←</span>
            <h4>申请</h4>
        </div>
        <div class="profile-item">
            <div class="p-i" @click="gotoInRegistrationPage">
                <span>来访登记申请</span>
                <div class="item-container">
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="p-i" @click="gotoOutRegistrationPage">

                <span>外出登记申请</span>
                <div class="item-container">
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>

        </div>
        <div class="profile-item">
            <div class="p-i" @click="gotoMyApplicationPage">

                <span>我的申请</span>
                <div class="item-container">
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
        </div>

    </div>
    <div class="footer-text">
        要养老，上养老生活 ®
    </div>

</template>

<script lang="ts" setup>
import { ref } from 'vue';

import { useRouter } from 'vue-router';
import { ArrowRight, Plus } from '@element-plus/icons-vue'
import { definedUser } from '@/stores/index.js';
import { ElMessage } from 'element-plus'

import axios from '@/api/request';
let loginUser = definedUser()
let router = useRouter()

const gotoInRegistrationPage = () => {
    router.push({ name: 'InRegistration_app' })
}
const gotoOutRegistrationPage = () => {
    router.push({ name: 'OutRegistration_app' })
}

const gotoMyApplicationPage = () => {
    router.push({ name: 'MyApplication_app' })
}

</script>

<style scoped>
.edit-profile-container {
    background-color: #f5f5f5;
    min-height: 100vh;
}

.header {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
}

.back-arrow {
    position: absolute;
    left: 16px;
}

.profile-item {
    background-color: white;
    padding: 16px;
    border-radius: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    margin: 16px;
    margin-bottom: 50px;

}

.p-i {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: space-between;

}


.item-container {
    display: flex;
    align-items: center;

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