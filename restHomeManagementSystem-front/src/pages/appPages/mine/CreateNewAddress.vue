<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
        <h4>创建地址</h4>
    </div>
    <div class="create-new-address-page">
        <div class="info-container">
            <div class="info-item">
                <div class="info-label">
                    <span>收货人</span>
                </div>
                <div>
                    <input type="text" placeholder="请填写收货人姓名" class="info-input" style="padding-left: 18px;"
                        v-model="newReceiveAddressName">
                </div>
            </div>
            <div class="info-item">
                <div class="info-label">
                    <span>收货地址</span>
                </div>
                <div>
                    <input type="text" placeholder="详细地址，具体到门牌号" class="info-input" v-model="newReceiveAddress">
                </div>
            </div>
            <div class="info-item">
                <div class="info-label">
                    <span>手机号码</span>
                </div>
                <div>
                    <input type="text" placeholder="请填写收货人手机号码" class="info-input" v-model="newReceiveAddressPhone">
                </div>
            </div>
            <div class="info-item" style="margin-top: 20px; border: none;">
                <div class="info-label">
                    <span>地址标签</span>
                </div>
                <div class="info-tag-list">
                    <el-check-tag v-for="item in tagList" type="success" effect="plain" round :checked="item.checked"
                        @change="onChange($event, item)">
                        {{ item.name }}
                    </el-check-tag>

                </div>
            </div>
            <div>
                <button class="save-address-button" @click="saveAddress">保存地址</button>
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
import axios from '@/api/request';
import { ElMessage } from 'element-plus';
import { definedUser } from '@/stores';
let router = useRouter()
let loginUser = definedUser()
const onChange = (status: boolean, selectedItem) => {
    if (status) {
        // 当标签被选中时
        selectedItem.checked = true
        newReceiveAddressTag.value = selectedItem.name
        tagList.value.forEach(item => {
            if (item !== selectedItem) {
                item.checked = false;
            }
        });
    }
    else {
        tagList.value.forEach(item => {
            item.checked = false;
        });
        newReceiveAddressTag.value = null
    }
}
const tagList = ref([
    {
        name: '家',
        checked: false
    }, {
        name: '父母',
        checked: false

    }, {
        name: '子女',
        checked: false

    }, {
        name: '长辈',
        checked: false

    }, {
        name: '养老院',
        checked: false

    }, {
        name: '公司',
        checked: false
        ,
    }, {
        name: '学校',
        checked: false

    },
])
const newReceiveAddressName = ref()
const newReceiveAddress = ref()
const newReceiveAddressPhone = ref()
const newReceiveAddressTag = ref()
const saveAddress = async () => {
    const response = await axios.post('/receiveAddress/addNewReceiveAddress', {
        uid: loginUser.uid,
        receiveName: newReceiveAddressName.value,
        receiveAddress: newReceiveAddress.value,
        receivePhone: newReceiveAddressPhone.value,
        receiveTag: newReceiveAddressTag.value,
    })
    console.log(response);

    if (response.data.code == 200) {
        ElMessage.success('保存成功')
        router.go(-1)
    }
    else {
        ElMessage.error(response.data.message)
    }

}
</script>
<style scoped>
.create-new-address-page {
    padding: 16px;
    background-color: #fff;
    min-height: 100vh;
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


.info-item {
    display: flex;
    align-items: center;
    gap: 30px;
    height: 50px;
    padding: 15px;
    border-top: 1px solid #ebeef5;
}

.info-label {
    color: #797979;
    font-size: 16px;
}

.info-input {
    border: none;
}

.info-input::placeholder {
    color: #bbbbbb;
    font-size: 14px;
}

.info-tag-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.save-address-button {

    position: fixed;
    margin-top: 30px;
    border: none;
    border-radius: 10px;
    color: #fff;
    background-color: #00bfa5;
    height: 40px;
    width: 93%;
    font-size: 14px;
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