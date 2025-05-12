<template>
    <div class="header">
        <div>
            <span class="back-arrow" @click="router.push({ name: 'Mine_app' })">←</span>
            <h4>全部订单</h4>
        </div>
        <div style="width: 100%; ">
            <div class="order-tabs">
                <span v-for="(tab, index) in orderTabs" :key="index" :class="{ active: currentTab === tab.value }"
                    @click="changeTab(tab.value)">
                    {{ tab.label }}
                </span>
            </div>
        </div>
    </div>
    <div class="order-page">
        <div class="order-list" v-if="filteredOrders.length > 0">
            <div v-for="(order, index) in filteredOrders" :key="index" class="order-item">
                <div class="order-info">
                    <span class="order-id">YL-{{ order.orderNumber }}</span>
                    <span class="order-status">{{ getOrderStatus(order.orderStatus) }}</span>
                </div>
                <div class="order-products">
                    <div v-for="(item, itemIndex) in order.goodsList" :key="itemIndex" class="order-product"
                        @click="gotoGoodsDetailPage(item.gid)">
                        <div
                            style="width: 100px; height: 100px; display: flex;align-items: center;justify-content: center; margin-right: 15px;">
                            <img :src="`http://localhost:8999/images/upload/goodsImg/${item.image}`"
                                class="product-image" />
                        </div>
                        <div class="product-details">
                            <div>
                                <span class="product-name">{{ item.title }}</span>
                            </div>
                            <div style="display: flex;align-items: center; justify-content: space-between; ">
                                <span class="product-price">￥{{ item.price }}</span>
                                <span class="product-quantity">x{{ item.quantity }}</span>
                            </div>
                            <div>
                                <span class="product-quantity"> 交易时间 {{ formatTimestamp(order.createTime) }}</span>
                            </div>
                            <div style="display: flex; align-items: center;justify-content: end;">
                                <button class="repurchase-btn">再次购买</button>
                                <button class="repurchase-btn" v-if="order.orderStatus === 'pending'"
                                    @click.stop="gotoPay(order)">去付款</button>
                                <button v-if="order.orderStatus === 'evaluation'" class="repurchase-btn"
                                    @click.stop="gotoGoodsCommentPage(item.gid, order)">去评价</button>
                                <button v-if="order.orderStatus === 'evaluation'" class="repurchase-btn"
                                    @click="repurchase(order)">去售后</button>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="order-total">合计: ￥{{ calculateTotal(order.goodsList) }}</div>

            </div>
        </div>
        <div v-else class="order-empty">
            <img src="@/assets/images/order-empty.jpg" alt="">
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from '@/api/request';
import { useRouter, useRoute } from 'vue-router';
import { definedUser } from '@/stores';

let route = useRoute()
let router = useRouter();
const loginUser = definedUser();

// 获取订单数据
const fetchData = async () => {
    const response = await axios.get(`/order/getOrdersByUid/${loginUser.uid}`);
    console.log(response);
    orders.value = response.data.data;
};

// 订单数据
const orders = ref([]);


// 订单状态分类标签
const orderTabs = ref([
    { label: '全部订单', value: 'all' },
    { label: '待付款', value: 'pending' },
    { label: '已取消', value: 'cancelled' },
    { label: '待评价', value: 'evaluation' },
    { label: '退款售后', value: 'sales' }
]);
const routeTab = route.query.tab
const currentTab = ref(routeTab);

// 切换标签
const changeTab = (tabValue) => {
    currentTab.value = tabValue;
};

// 根据 createTime 排序，新的排在上面
const filteredOrders = computed(() => {
    let filtered = orders.value.filter(order =>
        !order.orderNumber.startsWith('CATERING') // 使用可选链防止空值
    );
    // 第一步：按标签筛选
    if (currentTab.value !== 'all') {
        filtered = orders.value.filter(order =>
            order.orderStatus === currentTab.value
        );
    }


    // 第三步：时间排序
    return filtered.sort((a, b) => {
        return new Date(b.createTime) - new Date(a.createTime);
    });
});
const formatTimestamp = (timestamp) => {
    const date = new Date(timestamp);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

}
// 再次购买
const repurchase = (order) => {
    console.log('再次购买', order);
    router.push({
        name: 'Pay_app',
        params: { oid: order.oid }
    });
};

const gotoPay = (order) => {
    console.log('再次购买', order);
    router.push({
        name: 'Pay_app',
        params: { oid: order.oid }
    });
};
const gotoGoodsCommentPage = (gid, order) => {

    router.push({ name: 'GoodsComment', query: { gid: gid, oid: order.oid } })
};

// 计算订单总价
const calculateTotal = (goodsList) => {
    return goodsList.reduce((total, item) => total + item.price * item.quantity, 0);
};

// 订单状态映射
const orderStatusMap = {
    all: '全部订单',
    pending: '待付款',
    cancelled: '已取消',
    evaluation: '待评价',
    sales: '退款售后',
    finished: '已完成'
};

// 获取订单状态
const getOrderStatus = (status) => {
    return orderStatusMap[status] || status; // 如果没有匹配的状态，返回原状态
};
const gotoGoodsDetailPage = (gid) => {
    router.push({
        name: 'GoodsDetail_app', params: {
            gid: gid
        }
    })
}

// 组件挂载时获取数据
onMounted(() => {
    fetchData();
});
</script>

<style scoped>
.order-page {
    background-color: #fff;
    min-height: 88vh;
    padding-top: 115px;
}

.header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    flex-direction: column;
    justify-content: center;
    display: flex;
    align-items: center;
    z-index: 10;
    color: #fff;
    background-image: url(@/assets/images/home_app_background.jpg);
    padding-top: 20px;
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

.order-tabs {
    display: flex;
    justify-content: space-between;
    padding: 20px 5px;
    background-image: url(@/assets/images/home_app_background.jpg);
    border-bottom: 1px solid #e0e0e0;
}

.order-tabs span {
    cursor: pointer;
    padding: 5px 10px;
    color: #fff;
}

.order-tabs span.active {
    color: #fff;
    border-bottom: 2px solid #fff;
}

.order-item {
    background-color: white;
    padding: 15px;
    border-bottom: 1px solid #ebeef5;
    position: relative;
}

.order-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
}

.order-id {
    font-weight: 300;
    font-size: 14px;
}

.order-status {
    color: #888;
    font-weight: 500;
    font-size: 14px;
}

.order-product {
    display: flex;
    align-items: center;
    margin-bottom: 5px;
}

.product-image {
    width: 80px;
    height: 80px;
    object-fit: cover;
}

.product-details {
    display: flex;
    flex-direction: column;
    width: 100%;
    padding: 0 10px 0 10pxs;
}

.product-name {
    font-size: 14px;
}

.product-price,
.product-quantity {
    font-size: 14px;
    color: #888;
}

.order-total {
    text-align: right;
    margin-top: 10px;
    font-weight: bold;
    padding-right: 5px;
}

.repurchase-btn {
    display: block;
    padding: 5px 15px;
    border: 1px solid #e47063;
    border-radius: 20px;
    background-color: #fff;
    color: #e47063;
    cursor: pointer;
    font-size: 14px;
    margin-top: 10px;
}

.order-empty {
    width: 100%;
    padding-top: 65px;
}

.order-empty img {
    max-width: 100%;
}
</style>