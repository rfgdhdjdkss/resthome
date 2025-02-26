<template>
    <div class="edit-profile-container">
        <div class="header">
            <span class="back-arrow" @click="router.go(-1)">←</span>
            <h4>设置</h4>
        </div>
        <div class="avatar-item" @click="gotoPersonalCenterPage()">
            <span>个人信息</span>
            <div class="item-container">
                <span class="item-info">头像、昵称、收货地址</span>
                <el-icon>
                    <ArrowRight />
                </el-icon>
            </div>
        </div>
        <div class="profile-item">

            <div class="p-i" @click="gotoModifyPhonePage()">
                <span>手机号</span>
                <div class="item-container">
                    <span class="item-info">{{ phone === '' ? '未绑定' : phone }}</span>
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="p-i" style="border-top: 1px solid #ebeef5;border-bottom: 1px solid #ebeef5;"
                @click="editGender">
                <span>微信</span>
                <div class="item-container">
                    <span class="item-info">未绑定</span>
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="p-i" @click="gotoModifyPasswordPage">
                <span>密码</span>
                <div class="item-container">
                    <span class="item-info">设置密码</span>
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
        </div>

        <div class="profile-item">
            <div class="p-i" @click="writeOff">
                <span>注销账号（请谨慎操作！！！）</span>
                <div class="item-container">
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="p-i" style="border-top: 1px solid #ebeef5;border-bottom: 1px solid #ebeef5;"
                @click="gotoAboutResthomePage">
                <span>关于养老生活</span>
                <div class="item-container">
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="p-i" @click="logout">
                <span>退出当前登录</span>
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

<script setup>
import { ref } from 'vue';

import { useRouter } from 'vue-router';
import { ArrowRight } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { definedUser } from '@/stores/index.js';
import axios from '@/api/request';
let loginUser = definedUser()
let router = useRouter()
const phone = loginUser.phone;



const gotoPersonalCenterPage = () => {
    router.push({ name: 'PersonalCenter_app' })
};
const gotoModifyPhonePage = () => {
    router.push({ name: 'ModifyPhone_app' })
}

const gotoModifyPasswordPage = () => {
    router.push({ name: 'ModifyPassword_app' })
}
const editNickname = () => {
    console.log('编辑昵称');
};
const gotoAboutResthomePage = () => {
    router.push({ name: 'AboutResthome_app' })
}

const editGender = () => {
    console.log('编辑性别');
};
const writeOff = async () => {
    try {
        // 弹出确认对话框
        await ElMessageBox.confirm(
            '你确定要注销账号吗？此操作不可恢复！',
            '确认注销',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }
        );
        const response = await axios.delete(`/user/deleteUser/${loginUser.uid}`)
        if (response.data.code === 200) {
            ElMessage({
                message: '注销成功',
                type: 'success',
            });
            loginUser.$reset();
            localStorage.removeItem('token')
            router.push({ name: 'Login_app' });

        }
    } catch (error) {
        if (error === 'cancel') {
            return;
        }
        console.log(error);
    }

}



const editBirthday = () => {
    console.log('编辑生日');
};

const editAddress = () => {
    console.log('编辑收货地址');
};
const logout = () => {
    loginUser.$reset();
    localStorage.removeItem('token')
    // 跳转到登录页
    ElMessage.success('当前账号退出成功')
    router.push({ name: 'Login_app' });
}
</script>

<style scoped>
.edit-profile-container {
    padding: 16px;
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
    margin-bottom: 50px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    color: #38475c;
    font-size: 14px;
}

.p-i {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: space-between;

}

.avatar-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: white;
    padding: 16px;
    border-radius: 20px;
    margin-bottom: 50px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    color: #38475c;
    font-size: 14px;
}

.item-container {
    display: flex;
    align-items: center;

}

.avatar-img {
    width: 70px;
    height: 70px;
    margin-right: 8px;
    border-radius: 50%;
}

.address-item {
    height: 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: white;
    padding: 16px;
    border-radius: 20px;
    margin-bottom: 16px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);

}

.item-info {
    margin-right: 8px;

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