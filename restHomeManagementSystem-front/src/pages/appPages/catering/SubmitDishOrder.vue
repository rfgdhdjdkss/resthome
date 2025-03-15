<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
        <h4>提交订单</h4>
    </div>
    <div class="submit-order">
        <div class="no-address" v-if="!elderlyList" @click="router.push({ name: 'ElderlyDocumentList_app' })">
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
                <span>点击添加老人档案</span>
            </div>
        </div>

        <div class="have-address" @click="gotoElderlyAddressListPage">
            <i>
                <svg t="1740553270934" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="11644"
                    data-spm-anchor-id="a313x.search_index.0.i12.68f53a81hzCoT3" width="40" height="40">
                    <path
                        d="M512 1024a512 512 0 1 1 512-512 512 512 0 0 1-512 512z m0-435.2a128 128 0 1 0-128-128 128 128 0 0 0 128 128z m0-51.2a76.8 76.8 0 1 1 76.8-76.8 76.8 76.8 0 0 1-76.8 76.8z m17.664 302.848q94.08-89.6 157.696-148.48C763.264 618.752 806.4 540.416 806.4 454.4a288 288 0 0 0-576 0c0 83.2 45.312 169.216 110.592 236.8q53.888 58.88 153.6 149.504L512 856.832zM378.368 656.384C320.896 596.736 281.6 522.112 281.6 454.4a236.8 236.8 0 0 1 473.6 0c0 69.888-36.608 136.32-102.4 200.32q-57.344 53.376-140.8 132.224-85.76-78.08-133.632-130.56z"
                        fill="#e69e3a" p-id="11645" data-spm-anchor-id="a313x.search_index.0.i11.68f53a81hzCoT3"
                        class=""></path>
                </svg>
            </i>
            <div style="display: flex; flex-direction: column; justify-content: center; gap: 10px;">
                <div style="display: flex; align-items: center; gap: 20px;">
                    <div class="address-name">
                        <span>{{ elderlyInfo.elderlyName }}</span>
                    </div>
                    <div class="address-phone">
                        <span>{{ elderlyInfo.elderlyPhone }}</span>
                    </div>
                </div>
                <div class="address-address">
                    <span>房间床位号：{{ elderlyInfo.bedroom }}</span>
                </div>
                <div v-if="elderlyInfo.relationship">
                    <el-tag type="success" effect="dark"> {{ elderlyInfo.relationship }}
                    </el-tag>
                </div>
                <div>
                </div>
            </div>
            <div style="padding-left: 120px;">
                <el-icon>
                    <ArrowRight />
                </el-icon>
            </div>
        </div>

        <div class="dishes" v-for="(item, index) in cartInfo" :key="item.dishId">
            <div class="dishes-item">
                <div class="dishes-image">
                    <img :src="`http://localhost:8999/images/upload/addNewDish/dishImg/${item.dishImg}`"
                        alt="item.dishName" />
                </div>
                <div class="dishes-info">
                    <div class="dishes-title">
                        <span>{{ item.dishName }}</span>
                    </div>
                    <div class="dishes-price-quantity">
                        <div class="dishes-price">
                            ¥{{ item.dishPrice }}
                        </div>
                    </div>
                </div>
            </div>
            <div class="summary">
                <div class="summary-item">
                    <span>购买数量</span>
                    <div class="dishes-quantitiy-button">
                        <button @click="minusdishesQuantity(item)" class="minusButton"
                            v-if="item.quantity > 1">-</button>
                        <div class="dishes-quantity-value">{{ item.quantity }}
                        </div>
                        <button @click="adddishesQuantity(item)" class="addButton">+</button>
                    </div>
                </div>
                <div class="summary-item total">
                    <span>小计</span>
                    <span>¥{{ (item.quantity * item.dishPrice).toFixed(2) }}</span>
                </div>
            </div>
        </div>
        <div class="payment-methods">
            <div v-for="method in paymentMethods" :key="method.value" class="method-item"
                @click="selectPaymentMethod(method.value)" :class="{ selected: paymentMethod === method.value }">
                <div style="display: flex; align-items: center;">
                    <img :src="method.icon" :alt="method.label + '图标'" />
                    <span>{{ method.label }}</span>
                    <span style="margin-left: 10px; font-size: 12px; color: #7f7f7f;"
                        v-if="method.label == '账号余额'">(账户内剩余¥{{ loginUser.balance }}）</span>
                </div>
                <div>
                    <input type="radio" :value="method.value" v-model="paymentMethod" :id="method.value"
                        name="payment-method" />
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
                        ¥{{ totalPrice.toFixed(2) }}</span>
                </div>

            </div>
        </div>
        <div class="cart-button">
            <button class="buy-button" @click="submitOrder">提交订单</button>
        </div>
    </div>
</template>

<script lang="ts" setup>
import axios from '@/api/request';
import { ArrowRight, Plus } from '@element-plus/icons-vue';
import { onMounted, ref, computed, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { definedUser } from '@/stores';
import { createAlipayPayment, submitAlipayForm } from '@/api/alipay';

import { useRouterStore } from '@/stores/routerStore';
const routerStore = useRouterStore();
let loginUser = definedUser()
const route = useRoute();
const router = useRouter();
const dishIdList = JSON.parse(route.query.dishIdList as string);
console.log(dishIdList);
const elderlyInfo = ref({})
const elderlyList = ref([])
const fetchElderly = async () => {
    const res = await axios.get(`/elderly/selectIsCheckinElderlyByUid/${loginUser.uid}`)
    elderlyList.value = res.data.data
    if (localStorage.getItem('receiveAddressId')) {
        elderlyInfo.value = elderlyList.value.find(item => item.eid == localStorage.getItem('receiveAddressId'))
    }
    else {
        elderlyInfo.value = elderlyList.value[0]

    }
    console.log(elderlyInfo.value);

}
const gotoElderlyAddressListPage = () => {
    router.push({ name: 'SelectElderlyAddressList_app' })
}
const cartInfo = ref([]);

const fetchOrderInfo = async () => {
    const response = await axios.post("/dCart/getDishInfoByDishIdList", {
        dishIdList: dishIdList,
        uid: loginUser.uid
    });
    console.log(response);

    cartInfo.value = response.data.data;
    console.log(cartInfo.value);

};
const totalPrice = computed(() => {
    return cartInfo.value.reduce((sum, item) => {
        return sum + item.dishPrice * parseInt(item.quantity);
    }, 0);
});
const minusdishesQuantity = async (dish) => {
    const res = await axios.put('/dCart/minusdishesQuantity', {
        uid: loginUser.uid,
        dishId: dish.dishId,
        quantity: dish.quantity - 1
    })
    if (res.data.code == 200) {
        dish.quantity -= 1
    }
}
const adddishesQuantity = async (dish) => {
    const res = await axios.put('/dCart/adddishesQuantity', {
        uid: loginUser.uid,
        dishId: dish.dishId,
        quantity: dish.quantity + 1
    })
    if (res.data.code == 200) {
        dish.quantity += 1
    }
}
const dishesorders = ref([]);
const subMitOrderNumber = ref();

const submitOrder = async () => {
    cartInfo.value.forEach(item => {
        dishesorders.value.push({
            dishId: item.dishId,
            quantity: parseInt(item.quantity)
        });
    });
    const response = await axios.post("/order/addDishOrder", {
        uid: loginUser.uid,
        dishesorders: dishesorders.value
    });
    if (response.data.code == 200) {
        subMitOrderNumber.value = response.data.data;
        axios.delete(`/dCart/clearByUid/${loginUser.uid}`)
        if (paymentMethod.value === 'alipay') {

            try {
                const orderId = subMitOrderNumber.value;
                const amount = totalPrice.value;
                const subject = `${loginUser.nickname}购买的点餐订单`;
                const htmlData = await createAlipayPayment(orderId, amount, subject);
                submitAlipayForm(htmlData);

            } catch (error) {
                console.error('支付失败:', error);
            }
        }
        console.log('确认支付，支付方式：', paymentMethod.value);
    }
};
const paymentMethod = ref('wechat');

// 支付方式列表
const paymentMethods = ref([
    { label: '微信支付', value: 'wechat', icon: '/src/images/WeChatPay.png' },
    { label: '支付宝支付', value: 'alipay', icon: '/src/images/AliPay.png' },
    { label: '账号余额', value: 'balance', icon: '/src/images/balancePay.png' }
]);

// 选择支付方式的方法
const selectPaymentMethod = (method) => {
    paymentMethod.value = method;
};
onMounted(() => {
    fetchElderly()
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
    border-bottom: 1px solid #ebeef5;
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

.no-address {
    background-color: #fff;
    padding: 16px;
    width: 92%;
    height: 80px;
    border-radius: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: #e69e3a;

}

.have-address {
    background-color: #fff;
    padding: 16px;
    width: 92%;
    height: 80px;
    border-radius: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    display: flex;
    align-items: center;
    gap: 15px;
}

.address-name {
    font-weight: 900;
}

.address-phone {
    color: #6d6d6d;
    font-size: 14px;
}

.address-address {
    color: #6d6d6d;
    font-size: 14px;
}

.dishes {
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    border-radius: 20px;
}

.dishes-item {
    display: flex;
    align-items: center;
    gap: 30px;
    padding: 15px;
    margin-top: 30px;
    border-bottom: 1px solid #ebeef5;
}

.dishes-image {
    width: 70px;
    height: 70px;
}

.dishes-image img {
    max-width: 100%;
    height: 70px;
}

.dishes-info {
    display: flex;
    flex-direction: column;
    gap: 30px;
}

.dishes-title {
    color: #000;
    font-size: 16px;
    font-weight: 300;
}

.dishes-price-quantity {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 250px;

}

.dishes-price {
    color: #50504e;
    font-size: 16px;
}

.dishes-quantitiy-button {
    display: flex;
    gap: 2px;
    align-items: center;
    color: #50504e;
    font-size: 14px;
}

.dishes-quantity-value {
    background-color: #e4e4e4;
    border: none;
    height: 30px;
    width: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.dishes-quantitiy-button .addButton {
    height: 30px;
    border: none;
    width: 30px;
    background-color: #e4e4e4;
    border-radius: 0 5px 5px 0;
    display: flex;
    align-items: center;
    justify-content: center;

}

.dishes-quantitiy-button .minusButton {
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

.payment-methods {
    display: flex;
    flex-direction: column;
    border-radius: 10px;
    border: none;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    background-color: #fff;
    padding: 10px;
    margin-top: 20px;
    margin-bottom: 60px;
}

.method-item {
    display: flex;
    align-items: center;
    padding: 20px 30px 15px 20px;
    cursor: pointer;
    border-bottom: 1px solid #ebeef5;
    justify-content: space-between;
}

.method-item img {
    width: 30px;
    height: 30px;
    margin-right: 10px;
}
</style>