<template>
    <div class="nav-content">
        <div class="search-box">
            <input type="text" placeholder="输入餐品名搜索" v-model="searchKeyword" @input="handleSearch" />
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, onUpdated } from 'vue';
import axios from '@/api/request';
import { definedUser } from '@/stores';
import { ElMessage } from 'element-plus';

const searchKeyword = ref(''); // 搜索关键词

// 搜索功能
const filteredGroupedDishes = computed(() => {
    if (!searchKeyword.value) {
        return groupedDishes.value;
    }
    return groupedDishes.value.map(group => ({
        ...group,
        items: group.items.filter(item =>
            item.title.toLowerCase().includes(searchKeyword.value.toLowerCase())
        )
    })).filter(group => group.items.length > 0);
});

// 处理搜索输入
const handleSearch = () => {
    // 搜索逻辑可以在这里扩展，比如发送请求到后端
    console.log('搜索关键词:', searchKeyword.value);
};

// 商品分类
const categories = ref([
    { id: 1, name: '主食', icon: '🍚' },
    { id: 2, name: '荤菜', icon: '🍖' },
    { id: 3, name: '素菜', icon: '🥬' },
    { id: 4, name: '汤品', icon: '🍲' },
    { id: 5, name: '特色小吃', icon: '🥟' },
]);

// 当前显示的商品
const dishesList = ref([]);

// 获取菜品数据
const fetchDishes = async () => {
    const response = await axios.get('/dishes/findAllDishes');
    dishesList.value = response.data.data;
};

// 分组菜品
const groupedDishes = computed(() => {
    const groups = new Map();
    categories.value.forEach(category => {
        groups.set(category.id, {
            category,
            items: dishesList.value.filter(d => d.tab === category.name)
        });
    });
    return Array.from(groups.values());
});

// 处理分类点击
const handleCategoryChange = (category) => {
    const el = document.getElementById(`category-${category.id}`);
    if (el) {
        el.scrollIntoView({
            behavior: 'smooth',
            block: 'start'
        });
    }
};

// 初始化数据
onMounted(() => {
    fetchDishes();
});
</script>

<style scoped>
.nav-content {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    background: white;
    z-index: 8;
    padding: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.search-box {
    max-width: 400px;
    margin: 0 auto;
    position: relative;
}

.search-box input {
    width: 92%;
    padding: 10px 15px;
    border-radius: 20px;
    border: 1px solid #ddd;
    background-color: #f5f5f5;
    font-size: 14px;
    outline: none;
}

.search-box input:focus {
    border-color: #5cd387;
    box-shadow: 0 0 5px rgba(92, 211, 135, 0.5);
}
</style>