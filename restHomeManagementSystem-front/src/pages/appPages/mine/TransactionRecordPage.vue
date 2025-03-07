<template>
    <div class="header">
        <span class="back-arrow" @click="router.push('Mine_app')">←</span>
        <h4>账单</h4>
    </div>
    <div class="transaction-record-page">
        <div class="search-bar">
            <el-input v-model="searchKey" placeholder="搜索交易记录" prefix-icon="Search" clearable @clear="handleSearch"
                @input="handleSearch" />
        </div>
        <div class="transaction-list">
            <div v-for="record in filteredRecords" :key="record.id" class="transaction-item">
                <div>
                    <img src="@/images/AliPay.png" width="50" height="50" alt="" style="border-radius: 50%;" v-if="record.transactionType==='支付宝'">
                    <img  src="@/assets/images/logo.jpg" width="50" height="50" alt="" style="border-radius: 50%;" v-else>
                </div>
                <div class="transaction-info">
                    <div>
                        <span>{{ record.description }}</span>
                    </div>
                    <div class="type-time">
                        <span> {{ record.transactionType }}</span>
                    </div>
                    <div class="type-time">
                        <span>{{ formatTime(record.transactionTime) }}</span>
                    </div>
                </div>
                <div class="transaction-money">
                    <span :class="record.inOrOut === 1 ? 'positive' : 'negative'">
                        {{ record.inOrOut === 1 ? '+' : '-' }}{{ record.transactionMoney }}元
                    </span>
                </div>
            </div>
        </div>
        <p v-if="filteredRecords.length === 0">暂无交易记录</p>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from '@/api/request';
import { definedUser } from '@/stores';
import { useRouter } from 'vue-router';
import dayjs from 'dayjs';

let loginUser = definedUser();
let router = useRouter();
const transactionRecords = ref([]);
const searchKey = ref('');

// 获取用户交易记录的函数
const fetchTransactionRecords = async () => {
    try {
        const response = await axios.get(`/transaction/findAllTransactionRecord/${loginUser.uid}`);
        if (response.data.code === 200) {
            // 获取交易记录并排序
            const records = response.data.data;
            records.sort((a, b) => new Date(b.transactionTime) - new Date(a.transactionTime));
            transactionRecords.value = records;
            console.log(transactionRecords.value);
        } else {
            console.error('获取交易记录失败:', response.data.message);
        }
    } catch (error) {
        console.error('请求出错:', error);
    }
};

// 格式化时间
const formatTime = (time) => {
    return dayjs(time).format('MM-DD HH:mm');
};

// 计算过滤后的交易记录
const filteredRecords = computed(() => {
    if (!searchKey.value) return transactionRecords.value;

    return transactionRecords.value.filter(record => {
        const searchValue = searchKey.value.toLowerCase();
        return (
            (record.transactionType && record.transactionType.toLowerCase().includes(searchValue)) ||
            (record.description && record.description.toLowerCase().includes(searchValue))
        );
    });
});
// 搜索处理
const handleSearch = () => {
    // 可以添加额外的搜索逻辑
};
// 在组件挂载时获取交易记录
onMounted(() => {
    fetchTransactionRecords();
});
</script>

<style scoped>
.transaction-record-page {
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

.search-bar {
    margin-bottom: 20px;
}

.transaction-list {
    display: flex;
    flex-direction: column;
    background-color: #fff;
    border-radius: 5px;
}


.transaction-item {
    padding: 10px 20px;
    border-bottom: 1px solid #ddd;
    display: flex;
    align-items: center;
    gap: 20px;
    justify-content: space-between;
}

.transaction-info {
    width: 60%;
    display: flex;
    flex-direction: column;
    justify-content: start;
}

.transaction-info div {
    display: flex;
    align-items: center;
    gap: 10px;
}

.transaction-info .type-time {
    color: #7f7f7f;
    font-size: 13px;
}

.transaction-money {
    width: 100px;
    text-align: right;
    font-weight: bold;
}


.type-time {
    color: #7f7f7f;
    font-size: 13px;
}

.positive {
    color: green;
}

.negative {
    color: red;
}
</style>
