<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
        <h4>收货地址</h4>
    </div>
    <div class="edit-profile-container">

        <div v-if="receiveAddressList">
            <div class="address-item" v-for="item in receiveAddressList" 
            @click="goBack(item.receiveAddressId)">
                <div class="name-phone-item">
                    <div class="receive-name">
                        {{ item.receiveName }}
                    </div>
                    <div class="receive-phone">
                        {{ item.receivePhone }}

                    </div>
                </div>
                <div class="addressInfo-item">
                    <div class="receive-address">
                        {{ item.receiveAddress }}
                    </div>

                </div>
                <div class="tag-item" v-if="item.receiveTag != null">
                    <div class="receive-tag">
                        <el-tag type="success" effect="dark"> {{ item.receiveTag }}
                        </el-tag>
                    </div>
                </div>
                <div class="edit" @click="gotoShippingAddressDetailPage(item.receiveAddressId, $event)">
                    <span>编辑</span>
                </div>
            </div>

        </div>
        <div v-else class="address-empty">
            <img src="@/assets/images/address_empty.jpeg" alt="">
        </div>
        <div>
            <button class="new-address-button" @click="gotoCreateNewAddressPage">
                新增收货地址</button>
        </div>
    </div>





</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';

import { useRouter, useRoute } from 'vue-router';
import { ArrowRight, Plus } from '@element-plus/icons-vue'
import { definedUser } from '@/stores/index.js';
import { ElMessage } from 'element-plus'

import axios from '@/api/request';
let loginUser = definedUser()
let router = useRouter()
let route = useRoute()


const receiveAddressList = ref([])
const fetchData = async () => {
    const response = await axios.get(`/receiveAddress/getReceiveAddressList/${loginUser.uid}`)
    receiveAddressList.value = response.data.data

}
const gotoCreateNewAddressPage = () => {
    router.push({ name: 'CreateNewAddress_app' })
}
const gotoShippingAddressDetailPage = (addressid, event) => {
    event.stopPropagation();
    router.push({
        name: 'ShippingAddressDetail_app',
        params: {
            raid: addressid
        }
    })
}
const goBack = (receiveAddressId) => {
    localStorage.setItem('receiveAddressId', receiveAddressId);
    router.go(-1)

}
onMounted(() => {
    fetchData()
})

</script>

<style scoped>
.edit-profile-container {
    padding: 16px;
    background-color: #f5f5f5;
    min-height: 91vh;
    padding-top: 70px;

}


.header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    justify-content: center;
    display: flex;
    align-items: center;
    z-index: 10;
    color: #000;
    height: 55px;
    background-color: #f5f5f5;
}

.back-arrow {
    position: absolute;
    left: 15px;
    margin: 10px;
    top: 10px;
}

.header h4 {
    margin: 0;
    font-size: 15px;
}


.address-item {
    position: relative;
    height: auto;
    display: flex;
    flex-direction: column;
    background-color: white;
    padding: 16px;
    border-radius: 20px;
    margin-bottom: 16px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    gap: 10px;
}

.name-phone-item {
    display: flex;
    align-items: center;
    gap: 30px;
}

.receive-phone {
    color: #9b9b9b;
}

.addressInfo-item {
    display: flex;
    align-items: center;
    justify-content: space-between;

}

.receive-address {
    font-size: 14px;
    color: #777777;
}

.receive-tag {
    color: #fff;
}

.new-address-button {
    position: fixed;
    bottom: 0;
    margin: 10px 0;
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

.address-empty {
    width: 100%;
    background-color: #f5f5f5;
    padding-top: 65px;
}

.address-empty img {
    max-width: 100%;
}

.edit {
    background-color: red;
    position: absolute;
    bottom: 5px;
    right: 10px;
    padding: 5px 7px 5px 7px;
    font-size: 12px;
    color: #fff;
    border-radius: 20px;
    z-index: 99;
}
</style>