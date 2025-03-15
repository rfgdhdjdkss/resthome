<template>
    <div class="catering-page">
        <TopNavbar />
        <div class="main-container">
            <CategorySidebar :categories="categories" @category-change="handleCategoryChange" />
            <div class="product-list">
                <!-- 分组展示 -->
                <div v-for="(group, index) in groupedDishes" :key="index" :id="`category-${group.category.id}`"
                    class="category-group">
                    <h3 class="category-title">{{ group.category.name }}</h3>
                    <DishesItem v-for="dishes in group.items" :key="dishes.dishId" :dishes="dishes"
                        @add-to-cart="addToCart" />
                </div>
            </div>
        </div>
        <ShoppingCart :cartItems="cartItems" @remove-from-cart="removeFromCart" @clear-cart="clearCart"
            @update-quantity="updateQuantity" :cartQuantitySum="cartQuantitySum" />
    </div>

    <Footer :menuType="menuType" />
</template>

<script setup>
import TopNavbar from './components/TopNavbar.vue'
import CategorySidebar from './components/CategorySidebar.vue'
import DishesItem from './components/DishesItem.vue'
import ShoppingCart from './components/ShoppingCart.vue'
import Footer from '../components/Footer.vue';

import { ref, computed, onMounted, onUpdated } from 'vue'
import axios from '@/api/request';
import { definedUser } from '@/stores';
import { ElMessage } from 'element-plus';
let loginUser = definedUser()
const menuType = ref("catering")

// 商品分类
const categories = ref([
    { id: 1, name: '主食', icon: '🍚' },
    { id: 2, name: '荤菜', icon: '🍖' },
    { id: 3, name: '素菜', icon: '🥬' },
    { id: 4, name: '汤品', icon: '🍲' },
    { id: 5, name: '特色小吃', icon: '🥟' },
])

// 当前显示的商品
const dishesList = ref([]);
// 添加商品到购物车
const addToCart = async (product) => {
    const response = await axios.post("/dCart/addDishes", {
        uid: loginUser.uid,
        dishId: product.dishId,
        quantity: 1
    })
    if (response.data.code == 200) {
        ElMessage.success('加入购物车成功')
    }
    fetchCartInfo()
    fetchCartQuantity()
};
//购物车餐品总数
const cartQuantitySum = ref()
// 查询购物车餐品数量
const fetchCartQuantity = async () => {
    const response = await axios.get(`/dCart/getDishCartQuantity/${loginUser.uid}`)
    cartQuantitySum.value = response.data.data
}
// 从购物车移除商品
const removeFromCart = async (dishId) => {
    try {
        const requestBody = {
            uid: loginUser.uid,
            dishId: dishId
        };

        // 发送删除请求
        await axios.delete('/dCart/delete', {
            data: requestBody // axios的delete请求需要用data传递body
        });

        // 更新本地数据
        cartItems.value = cartItems.value.filter(item => item.dishId !== dishId);
    } catch (error) {
        console.error('删除商品失败:', error);
        // 可以添加错误提示
    }
};

// 清空购物车
const clearCart = async () => {
    const response = await axios.delete(`/dCart/clearByUid/${loginUser.uid}`)
    if (response.data.code == 200) {
        cartItems.value = [];
        fetchCartQuantity()
    }
};
const fetchDishes = async () => {
    const response = await axios.get('/dishes/findAllDishes')
    dishesList.value = response.data.data
}
const groupedDishes = computed(() => {
    const groups = new Map()
    categories.value.forEach(category => {
        groups.set(category.id, {
            category,
            items: dishesList.value.filter(d => d.tab === category.name)
        })
    })
    return Array.from(groups.values())
})

// 处理分类点击
const handleCategoryChange = (category) => {
    const el = document.getElementById(`category-${category.id}`)
    if (el) {
        // 使用平滑滚动
        el.scrollIntoView({
            behavior: 'smooth',
            block: 'start'
        })
    }
}
const updateQuantity = ({ id, quantity }) => {
    const item = cartItems.value.find(item => item.dishId === id)
    if (item) {
        item.quantity = quantity
        axios.put("/dCart/updateQuantity", {
            dishId: item.dishId,
            uid: item.uid,
            quantity: item.quantity
        })
    }
}
const cartItems = ref([]) // 强制初始化为数组

const fetchCartInfo = async () => {
    try {
        const res = await axios.get(`/dCart/getDishCartInfoByUid/${loginUser.uid}`)
        cartItems.value = res.data?.data?.length ? res.data.data : []
    } catch (e) {
        console.error('购物车加载失败:', e)
        cartItems.value = []
    }
}
onMounted(() => {
    fetchDishes()
    fetchCartQuantity()
    fetchCartInfo()
})
onUpdated(() => {
    fetchCartQuantity()

})
</script>
<style scoped>
.main-container {
    display: flex;
    margin-top: 60px;
    /* 给顶部导航栏留出空间 */
    padding-top: 0;
    position: relative;
}

.product-list {
    flex: 1;
    overflow-y: auto;
    height: calc(100vh - 120px);
    /* 根据实际高度调整 */
    margin-left: 90px;
}

.category-group {
    margin-bottom: 10px;
}

.category-title {
    font-size: 14px;
    position: sticky;
    top: 0;
    background: #fff;
    padding: 10px 0 10px 20px;
    margin: 0;
    z-index: 1;
}
</style>