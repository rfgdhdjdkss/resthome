<template>
    <div class="header">
        <span class="back-arrow" @click="router.push({ name: 'MyOrder_app', query: { tab: 'all' } })">←</span>
        <h4>支付订单</h4>
    </div>
    <div class="payment-order-container">
        <div style="display: flex;flex-direction: column; align-items: center;gap: 30px; margin-bottom: 30px;">
            <div class="time-remaining">
                支付剩余时间 {{ remainingTimeText }}
            </div>
            <div class="total-amount">
                <span class="currency"></span><span class="amount">￥{{ totalAmount }}</span>
            </div>
            <ul class="order-items">
                <li v-for="(item, index) in orderInfo.goodsList" :key="index">
                    <div class="order-items-li">
                        <div>
                            {{ item.title }}
                        </div>
                        <div>
                            x{{ item.quantity }}
                        </div>
                    </div>
                </li>
            </ul>
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
        <div>
            <button class="confirm-button" @click="confirmPayment">确认支付</button>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, computed, onUnmounted, watch } from 'vue';
import { createAlipayPayment, submitAlipayForm } from '@/api/alipay';

import { useRouter, useRoute } from 'vue-router';
import { definedUser } from '@/stores';
import axios from '@/api/request';

let loginUser = definedUser()
let router = useRouter()
let route = useRoute()
const oid = route.params.oid
const orderInfo = ref({})

// 用于存储当前时间
const currentTime = ref(Date.now());

// 计算剩余时间（毫秒）
const remainingTime = computed(() => {
    if (orderInfo.value.createTime) {
        const elapsed = currentTime.value - orderInfo.value.createTime;
        // 最长 15 分钟，剩余时间不能为负数
        return Math.max(0, 15 * 60 * 1000 - elapsed);
    }
    return 0;
});

// 格式化剩余时间为 `mm:ss` 格式
const remainingTimeText = computed(() => {
    const minutes = Math.floor(remainingTime.value / 1000 / 60);
    const seconds = Math.floor((remainingTime.value / 1000) % 60);
    return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
});

// 定时器 ID，用于清除定时器
let timerId;

onMounted(() => {
    fetchOrderInfo();
    // 启动定时器每秒更新当前时间
    timerId = setInterval(() => {
        currentTime.value = Date.now();
    }, 1000);
});

const fetchOrderInfo = async () => {
    const response = await axios.get(`/order/getOrderInfoByOid/${oid}`)
    console.log(response);
    orderInfo.value = response.data.data
    console.log(orderInfo.value);
    console.log(orderInfo.value.goodsList);
}

const totalAmount = computed(() => {
    if (!orderInfo.value.goodsList) {
        return 0;
    }
    return orderInfo.value.goodsList.reduce((total, item) => {
        return total + (item.price * item.quantity);
    }, 0);
});
// 支付方式，初始为微信支付
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
// 确认支付的方法
const confirmPayment = async () => {
    if (paymentMethod.value === 'alipay') {

        try {
            const orderId = `${orderInfo.value.orderNo}`;
            const amount = totalAmount.value;
            const subject = `${loginUser.nickname}购买的商品订单`;
            const htmlData = await createAlipayPayment(orderId, amount, subject);
            submitAlipayForm(htmlData);

        } catch (error) {
            console.error('支付失败:', error);
        }
    }
    console.log('确认支付，支付方式：', paymentMethod.value);
    // 这里可以添加实际的支付请求逻辑
};

// 监听剩余时间的变化
watch(remainingTime, (newValue) => {
    if (newValue <= 0) {
        console.log("<<<<<<0");

        // 剩余时间小于等于 0，发送请求更新订单状态
        updateOrderStatus('cancelled');
    }
});

const updateOrderStatus = async (status) => {
    try {
        const response = await axios.put(`/order/updateOrderStatus/${oid}`, {
            orderStatus: status // 假设更新为已取消状态
        });
        console.log('订单状态更新成功', response.data);
        // 可以在这里添加一些提示信息或跳转逻辑
    } catch (error) {
        console.error('订单状态更新失败', error);
    }
};

// 组件卸载时清除定时器
onUnmounted(() => {
    clearInterval(timerId);
});

</script>

<style scoped>
.payment-order-container {
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
    border-bottom: 1px solid #ebeef5;
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

.time-remaining {
    color: #00bfa5;
    margin-bottom: 20px;
    font-size: 14px;
    font-weight: 900;
    margin-top: 30px;
}

.total-amount {
    font-size: 32px;
    margin-bottom: 20px;
    font-weight: 700;
}

.currency {
    font-size: 20px;
}

.order-items {
    list-style: none;
    padding: 0;
    margin-bottom: 20px;
    flex-direction: column;
    align-items: center;
}

.order-items-li {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 20px;
}

.order-items li {
    margin-bottom: 10px;
}

.payment-methods {
    display: flex;
    flex-direction: column;
    border-radius: 10px;
    border: none;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    padding: 10px;
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

.confirm-button {
    position: fixed;
    bottom: 0;
    margin: 10px 0;
    border: none;
    border-radius: 10px;
    color: #fff;
    background-color: #f09f3f;
    height: 40px;
    width: 93%;
    font-size: 14px;
}
</style>