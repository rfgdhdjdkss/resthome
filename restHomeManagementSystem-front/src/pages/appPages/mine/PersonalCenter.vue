<template>
    <div class="edit-profile-container">
        <div class="header">
            <span class="back-arrow" @click="router.push('Mine_app')">←</span>
            <h4>编辑资料</h4>
        </div>
        <div class="avatar-item" @click="editAvatar">
            <span>头像</span>
            <div class="item-container">
                <el-upload class="avatar-uploader" :action="getActionUrl()" :show-file-list="false"
                    :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                    <img v-if="headImgUrl" :src="headImgUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>
                <el-icon>
                    <ArrowRight />
                </el-icon>
            </div>
        </div>
        <div class="profile-item">

            <div class="p-i" @click="editNickname">
                <span>昵称</span>
                <div class="item-container">
                    <span class="item-info">{{ nickname }}</span>
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="p-i" style="border-top: 1px solid #ebeef5;border-bottom: 1px solid #ebeef5;"
                @click="editGender">
                <span>性别</span>
                <div class="item-container">
                    <span class="item-info">{{ sex === '' ? '点此填写性别' : sex }}</span>
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="p-i" @click="editBirthday">
                <span>余额</span>
                <div class="item-container">
                    <span class="item-info">{{ balance }}</span>
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
        </div>

        <div class="address-item" @click="gotoShippingAddressListPage">
            <span>收货地址</span>
            <div class="item-container">
                <span class="item-info">{{ address }}</span>
                <el-icon>
                    <ArrowRight />
                </el-icon>
            </div>
        </div>
        <transition name="el-zoom-in-bottom">
            <div class="modify-nickname" v-if="showModifyNickname">
                <input v-model="newNickname" placeholder="请输入内容" class="nickname-input" />
                <button type="success" @click="saveNickname" class="nickname-button">立即保存</button>
            </div>
        </transition>
        <transition name="el-zoom-in-bottom">
            <div class="modify-sex" v-if="showModifySex">
                <div @click="confirmGender(`男`)">
                    <span>男</span>
                </div>
                <div @click="confirmGender(`女`)">
                    <span>女</span>
                </div>
            </div>
        </transition>
        <div v-show="showOverlay" class="overlay" @click="closeOverlay"></div>
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
const nickname = ref(loginUser.nickname);
const sex = ref(loginUser.sex);
const balance = ref(loginUser.balance);
const address = ref('修改/添加');

const showModifyNickname = ref(false);
const newNickname = ref(loginUser.nickname);
const showOverlay = ref(false)
const showModifySex = ref(false);

const editNickname = () => {
    showModifyNickname.value = true;
    showOverlay.value = true
};

const saveNickname = () => {
    nickname.value = newNickname.value;
    axios.put('/user/modifyNickname', {
        uid: loginUser.uid,
        nickname: newNickname.value
    }).then(function (response) {
        console.log(response);
        if (response.data.code === 200) {
            ElMessage.success('修改成功')
            loginUser.nickname = newNickname.value
            nickname.value = newNickname.value
            showModifyNickname.value = false;
            showOverlay.value = false
        }
        else {
            ElMessage.error(response.data.message)
        }
    }).catch(function (error) {
        console.log(error);

    })
};

const editGender = () => {
    showModifySex.value = true
    showOverlay.value = true

};
const confirmGender = async (updateSex) => {
    const response = await axios.put('/user/modifySex', {
        uid: loginUser.uid,
        sex: updateSex
    })
    console.log(response);
    if (response.data.code === 200) {
        ElMessage.success('修改成功')
        showModifySex.value = false;
        showOverlay.value = false
        sex.value=response.data.data
        loginUser.sex=response.data.data
    }
    else {
        ElMessage.error(response.data.message)
    }
}
const editBirthday = () => {
    console.log('编辑生日');
};

const gotoShippingAddressListPage = () => {
    router.push({name:'ShippingAddressList_app'})
};
const closeOverlay = () => {
    showOverlay.value = false;
    showModifyNickname.value = false
    showModifySex.value=false
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
    return `http://localhost:8999/files/upload/${loginUser.uid}`
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
    font-size: 14px;
}

.avatar-uploader .avatar {
    width: 70px;
    height: 70px;
    display: block;
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

.modify-nickname {
    position: fixed;
    bottom: 0;
    left: 0;
    background-color: #fff;
    box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 15px;
    padding: 10px;
    height: 150px;
    z-index: 10;
    width: 95.5%;
    border-radius: 20px 20px 0 0;
}

.nickname-input {
    padding: 15px;
    height: 20px;
    width: 80%;
    background-color: #d9dddc;
    border: none;
    font-size: 14px;
    border-radius: 20px;
}

.nickname-button {
    border-radius: 20px;
    background-color: #00bfa5;
    border: none;
    height: 40px;
    width: 85%;
    color: #fff;
    font-size: 14px;
}

.modify-sex {
    position: fixed;
    bottom: 0;
    left: 0;
    background-color: #fff;
    box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 15px;
    padding: 10px;
    height: 150px;
    z-index: 10;
    width: 95.5%;
    border-radius: 20px 20px 0 0;
}

.modify-sex div {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 9;
}
</style>