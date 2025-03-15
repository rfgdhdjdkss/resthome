<template>
    <div class="shopping-cart">
        <!-- 浮动购物车图标 -->
        <div class="cart-trigger" @click="toggleCart">
            <span class="cart-icon">🛒</span>
            <span class="cart-badge">{{ cartQuantitySum }}</span>
        </div>
        <!-- 购物车内容 -->
        <transition name="slide-up">
            <div v-show="isCartOpen" class="cart-panel">
                <div class="cart-header">
                    <h3>购物车 ({{ cartQuantitySum }})</h3>
                    <div class="header-actions">
                        <div>
                            <button class="clear-btn" @click="clearCart">清空</button>
                        </div>
                    </div>
                </div>
                <div class="cart-body">
                    <div v-if="cartQuantitySum > 0" class="cart-items">
                        <div v-for="item in cartItems" :key="item.dishId" class="cart-item"
                            :class="{ 'item-removing': item.removing }">
                            <img :src="`http://localhost:8999/images/upload/addNewDish/dishImg/${item.dishImg}`"
                                class="item-image">

                            <div>
                                <div class="item-name">{{ item.dishName }}</div>
                                <div class="item-info">
                                    <div class="item-price">¥{{ item.dishPrice.toFixed(2) }}</div>
                                    <div class="quantity-control">
                                        <button class="qty-btn" @click="decreaseQuantity(item)">
                                            −
                                        </button>
                                        <span class="quantity">{{ item.quantity }}</span>
                                        <button class="qty-btn" @click="increaseQuantity(item)">+</button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                    <div v-else class="empty-cart">
                        <div class="empty-icon">🛒</div>
                        <p>购物车空空如也</p>
                    </div>
                </div>

                <div v-if="cartQuantitySum > 0" class="cart-footer">
                    <div class="total-section">
                        <div style="display: flex; align-items: center;justify-content: center;">
                            <span>合计：</span>

                        </div>
                        <div style="display: flex; align-items: center;justify-content: center;">
                            <span class="total-price">¥{{ total.toFixed(2) }}</span>

                        </div>
                    </div>
                    <div style="display: flex; align-items: center;justify-content: center;">
                        <button class="checkout-btn" @click="goToSubmitOrder">
                            去结算 ({{ cartQuantitySum }})
                        </button>
                    </div>

                </div>
            </div>
        </transition>
    </div>
    <div v-show="isCartOpen" class="overlay" @click="closeOverlay"></div>

</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from '@/api/request';
import { definedUser } from '@/stores';
import { useRouter } from 'vue-router';
let router = useRouter()
let loginUser = definedUser()

const props = defineProps({
    cartItems: {
        type: Array,
    },
    cartQuantitySum: {  // 新增prop定义
        type: Number,
        default: 0
    }
}
);
console.log(props.cartItems);

const emit = defineEmits(['remove-from-cart', 'clear-cart', 'update-quantity']);

const isCartOpen = ref(false);

// 计算属性
const total = computed(() => {
    try {
        return props.cartItems.reduce((sum, item) => {
            const price = Number(item.dishPrice) || 0
            const qty = Number(item.quantity) || 0
            return sum + (price * qty)
        }, 0)
    } catch (e) {
        console.error('计算总价失败:', e)
        return 0
    }
})

// 交互方法
const toggleCart = () => {
    isCartOpen.value = !isCartOpen.value;
};

const clearCart = () => {
    emit('clear-cart');
};

const updateQuantity = (item, newQty) => {
    if (newQty < 1) return;
    emit('update-quantity', {
        id: item.dishId,  // 确保使用dishId作为唯一标识
        quantity: newQty
    });
};

const decreaseQuantity = (item) => {
    const newQty = item.quantity - 1;
    if (newQty === 0) {
        // 触发删除请求
        emit('remove-from-cart', item.dishId);
    } else {
        updateQuantity(item, newQty);
    }
};

const increaseQuantity = (item) => {
    updateQuantity(item, item.quantity + 1);
};


const closeOverlay = () => {
    isCartOpen.value = false
}
const goToSubmitOrder = () => {
    const dishIdList = props.cartItems.map(item => item.dishId);
    router.push({
        name: 'SubmitDishOrder_app',
        query: {
            dishIdList: JSON.stringify(dishIdList),
        }
    })
    // clearCart();
}
onMounted(() => {
})
</script>

<style scoped>
.shopping-cart {
    --primary-color: #00c853;
    --danger-color: #ff5252;
    --text-dark: #333;
    --text-light: #666;
    --border-color: #eee;
}

/* 触发按钮 */
.cart-trigger {
    position: fixed;
    bottom: 90px;
    right: 20px;
    background: #fff;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    transition: transform 0.2s;
    z-index: 5;
}

.cart-trigger:hover {
    transform: scale(1.1);
}

.cart-icon {
    font-size: 28px;
    color: white;
}

.cart-badge {
    position: absolute;
    top: -8px;
    right: -8px;
    background: var(--danger-color);
    color: white;
    width: 24px;
    height: 24px;
    border-radius: 50%;
    font-size: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    animation: bounce 0.3s ease-in-out;
}

@keyframes bounce {

    0%,
    100% {
        transform: scale(1);
    }

    50% {
        transform: scale(1.2);
    }
}

/* 购物车面板 */
.cart-panel {
    position: fixed;
    bottom: 60px;
    left: 0;
    right: 0;
    width: 100%;
    height: 35vh;
    max-height: 40vh;
    background: white;
    box-shadow: 0 -4px 12px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    z-index: 100;
}

.cart-header {
    padding: 5px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid var(--border-color);
}

.cart-header h3 {
    margin: 0;
    font-size: 14px;
    font-weight: 400;
    padding-left: 20px;
}

.header-actions {
    display: flex;
    align-items: center;
    gap: 12px;
}

.clear-btn {
    color: var(--text-light);
    border: 1px solid var(--border-color);
    border-radius: 20px;
    background: transparent;
    font-size: 12px;
    padding: 3px 12px;
    margin-right: 10px;
}

.close-btn {
    font-size: 16px;
    color: red;
    padding: 0 8px;
    font-weight: 900;
    display: flex;
    align-items: center;
    justify-content: center;
}

.cart-body {
    flex: 1;
    overflow-y: auto;
    /* 只在内容区域显示滚动条 */
    padding: 0 16px;
}



.cart-item {
    display: flex;
    gap: 16px;
    padding: 16px 0;
    border-bottom: 1px solid var(--border-color);
    transition: all 0.3s;
}

.item-image {
    width: 80px;
    height: 70px;
}

.item-info {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 30px;
}

.item-name {
    font-weight: 500;
    margin-bottom: 8px;
}

.item-price {
    color: #d25c4a;
    font-weight: bold;
}

.quantity-control {
    display: flex;
    align-items: center;
    gap: 8px;
}

.qty-btn {
    width: 28px;
    height: 28px;
    border-radius: 50%;
    border: 1px solid var(--border-color);
    background: transparent;
}

.qty-btn:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}

.quantity {
    min-width: 30px;
    text-align: center;
}

.remove-btn {
    /* color: var(--text-light); */
    font-size: 24px;
    align-self: flex-start;
    padding: 0 8px;
    border: none;
}

/* 空状态 */
.empty-cart {
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: var(--text-light);
}

.empty-icon {
    font-size: 48px;
    margin-bottom: 16px;
}

/* 底部结算 */
.cart-footer {
    padding: 10px 30px;
    box-shadow: 0 -2px 6px rgba(0, 0, 0, 0.05);
    display: flex;
    align-items: center;
    /* justify-content: center; */
    justify-content: space-between;

}

.total-section {
    display: flex;
    justify-content: space-between;
    font-size: 18px;
    align-items: center;
}

.total-price {
    color: #d25c4a;
    font-weight: bold;
}

.checkout-btn {
    margin: 0 auto;
    width: 100%;
    padding: 5px 10px;
    background-color: #e0594c;
    color: #fff;
    border: none;
    border-radius: 8px;
    font-size: 13px;

}

/* 动画 */
.slide-up-enter-active,
.slide-up-leave-active {
    transition: transform 0.3s ease;
}

.slide-up-enter-from,
.slide-up-leave-to {
    transform: translateY(100%);
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