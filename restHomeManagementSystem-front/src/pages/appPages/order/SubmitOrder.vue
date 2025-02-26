<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
        <h4>提交订单</h4>
    </div>
    <div class="submit-order">
        <div class="address">
            <div>
                <i>
                    <svg t="1740477801844" class="icon" viewBox="0 0 1024 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="5419" width="40" height="40">
                        <path
                            d="M924.549 337.707c-22.553-53.321-54.833-101.202-95.943-142.312-41.11-41.11-88.991-73.39-142.313-95.943-55.221-23.357-113.861-35.2-174.293-35.2S392.928 76.095 337.707 99.451c-53.321 22.553-101.202 54.833-142.312 95.943-41.11 41.11-73.39 88.991-95.943 142.312-23.357 55.222-35.2 113.862-35.2 174.294s11.843 119.072 35.199 174.293c22.553 53.321 54.833 101.202 95.943 142.313 41.11 41.11 88.991 73.391 142.312 95.943 55.221 23.356 113.862 35.199 174.293 35.199s119.072-11.843 174.293-35.199c53.321-22.553 101.202-54.833 142.313-95.943s73.391-88.991 95.943-142.313c23.356-55.221 35.199-113.861 35.199-174.293s-11.842-119.072-35.198-174.293zM512 896.252c-211.877 0-384.252-172.375-384.252-384.252S300.123 127.748 512 127.748 896.252 300.123 896.252 512 723.877 896.252 512 896.252z"
                            fill="#e69e3a" p-id="5420"></path>
                        <path
                            d="M740 480H544V284c0-15.464-14.327-28-32-28s-32 12.536-32 28v196H284c-15.464 0-28 14.327-28 32s12.536 32 28 32h196v196c0 15.464 14.327 28 32 28s32-12.536 32-28V544h196c15.464 0 28-14.327 28-32s-12.536-32-28-32z"
                            fill="#e69e3a" p-id="5421"></path>
                    </svg>
                </i>
            </div>
            <div>
                <span>点击添加收货地址</span>
            </div>
        </div>
        <div class="goods" v-for="item in cartInfo" :key="item.gid">
            <div class="goods-item">
                <div class="goods-image">
                    <img :src="`http://localhost:8999/images/upload/goodsImg/${item.image}`" :alt="item.title">
                </div>
                <div class="goods-info">
                    <div class="goods-title">
                        <span>{{ item.title }}</span>
                    </div>
                    <div class="goods-price-quantity">
                        <div class="goods-price">
                            ¥{{ item.price }}
                        </div>
                    </div>
                </div>
            </div>
            <div class="summary">
                <div class="summary-item">
                    <span>购买数量</span>
                    <div class="goods-quantitiy-button">
                        <button @click="minusGoodsQuantity(item)" class="minusButton"
                            v-if="item.quantity > 1">-</button>
                        <div class="goods-quantity-value">{{ item.quantity }}</div>
                        <button @click="addGoodsQuantity(item)" class="addButton">+</button>
                    </div>
                </div>
                <div class="summary-item">
                    <span>运费</span>
                    <span>免运费</span>
                </div>
                <div class="summary-item total">
                    <span>小计</span>
                    <span>¥{{ cartInfo.reduce((total, item) => total + item.price * item.quantity, 0) }}</span>
                </div>
            </div>
        </div>
    </div>
    <div class="cart">
        <div class="cart-price">
            <div style="display: flex; align-items: center; justify-content: center; gap: 20px;">
                <div>
                    <el-tag effect="dark" type="warning">合计</el-tag>
                </div>
                <div>
                    <span style="color: #e15241;font-size: 20px; font-weight: 900;">
                        ¥</span>
                </div>

            </div>
        </div>
        <div class="cart-button">
            <button class="buy-button">提交订单</button>
        </div>
    </div>
</template>
<script lang="ts" setup>
import axios from '@/api/request';
import { onMounted, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router';
import { definedUser } from '@/stores';
let route = useRoute()
const gidList = JSON.parse(route.query.gidList)

let loginUser = definedUser()
let router = useRouter()
interface CartInfo {
    uid: number,
    gid: number,
    image: string,
    title: string,
    quantity: string,
    price: number,
    checked: boolean
}
const cartInfo = ref<CartInfo[]>([])

const fetchCartInfo = async () => {
    const response = await axios.get(`/cart/getCartInfoByUid/${loginUser.uid}`)
    cartInfo.value = response.data.data
}
const fetchOrderInfo = async () => {
    console.log(gidList);

    const response = await axios.get("/goods/getGoodsInfoByGidList", {
        data: {
            gidList: gidList,
            uid: loginUser.uid
        }
    })


}
onMounted(() => {
    fetchCartInfo()
    fetchOrderInfo()
})
</script>
<style scoped>
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

.submit-order {
    min-height: 90vh;
    background-color: #f5f5f5;
    padding: 16px;
    padding-top: 78px;
}

.address {
    background-color: #fff;
    padding: 16px;
    width: 92%;
    height: 100px;
    border-radius: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: #e69e3a;

}

.goods {
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    border-radius: 20px;
}

.goods-item {
    display: flex;
    align-items: center;
    gap: 30px;
    padding: 15px;
    margin-top: 30px;
    border-bottom: 1px solid #ebeef5;
}

.goods-image {
    width: 70px;
    height: 70px;
}

.goods-image img {
    max-width: 100%;
    height: 70px;
}

.goods-info {
    display: flex;
    flex-direction: column;
    gap: 30px;
}

.goods-title {
    color: #000;
    font-size: 16px;
    font-weight: 300;
}

.goods-price-quantity {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 250px;

}

.goods-price {
    color: #50504e;
    font-size: 16px;
}

.goods-quantitiy-button {
    display: flex;
    gap: 2px;
    align-items: center;
    color: #50504e;
    font-size: 14px;
}

.goods-quantity-value {
    background-color: #e4e4e4;
    border: none;
    height: 30px;
    width: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.goods-quantitiy-button .addButton {
    height: 30px;
    border: none;
    width: 30px;
    background-color: #e4e4e4;
    border-radius: 0 5px 5px 0;
    display: flex;
    align-items: center;
    justify-content: center;

}

.goods-quantitiy-button .minusButton {
    height: 30px;
    border: none;
    width: 30px;
    background-color: #e4e4e4;
    border-radius: 5px 0 0 5px;
    display: flex;
    align-items: center;
    justify-content: center;

}

.summary {
    background-color: #fff;
    padding: 16px;
    border: none;
    border-radius: 20px;
}

.summary-item {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
}

.total {
    font-weight: bold;
    color: #e69e3a;
}

.cart {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 15px 20px;
    background-color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.cart-price {

    display: flex;
    align-items: center;
    gap: 10px;
    justify-content: center;
}

.cart-button .buy-button {
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    font-weight: 400;
    font-size: 13px;
    border: none;
    border-radius: 25px;
    padding: 15px;
    background-color: #e0594c;
    height: 35px;

}

.cart-button .delete-button {
    display: flex;
    align-items: center;
    justify-content: center;
    color: #000;
    font-weight: 400;
    font-size: 13px;
    border: 1px solid #000;
    border-radius: 25px;
    padding: 15px;
    background-color: #fff;
    height: 35px;
}
</style>