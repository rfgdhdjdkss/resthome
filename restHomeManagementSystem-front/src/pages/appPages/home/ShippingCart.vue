<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
        <h3>购物车</h3>
        <div class="header-edit" @click="showEdit" v-show="!ifedit">
            <span>编辑</span>
        </div>
        <div class="header-edit" @click="closeEdit" v-show="ifedit">
            <span>完成</span>
        </div>
    </div>
    <div v-if="cartInfoLength != 0">

        <div class="shipping-cart-page">
            <div class="container" style="margin-top: 50px;">
                <div class="goods-item" v-for="item in cartInfo">
                    <div class="checked-button">
                        <input type="checkbox" v-model="item.checked" class="goods-checkbox">
                    </div>
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
                            <div class="goods-quantitiy-button">
                                <button @click="minusGoodsQuantity(item)" class="minusButton"
                                    v-if="item.quantity > 1">-</button>
                                <div class="goods-quantity-value">{{ item.quantity }}</div>
                                <button @click="addGoodsQuantity(item)" class="addButton">+</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="cart">
            <div class="cart-price">
                <div>
                    <input class="goods-checkbox" type="checkbox" v-model="isAllChecked">
                </div>
                <div>
                    <span style="color: #50504e;font-size: 16px;">全选</span>
                </div>
                <div v-show="!ifedit">
                    <span style="color: #000;font-size: 16px; font-weight: 900;">合计：¥{{ totalPrice.toFixed(2) }}</span>
                </div>
            </div>
            <div class="cart-button">
                <button v-show="!ifedit" class="buy-button" @click=goToSubmitOrder>去结算({{ selectedCount }})</button>
                <button v-show="ifedit" class="delete-button" @click="deleteGoods">删除</button>
            </div>
        </div>
    </div>
    <div v-else class="cart-empty">
        <img src="@/assets/images/cart_empty.jpg" alt="">
    </div>

</template>
<script lang="ts" setup>
import { useRouter } from 'vue-router';
import axios from '@/api/request';
import { definedUser } from '@/stores';
import { computed, onMounted, ref } from 'vue';
import { ElMessage } from 'element-plus';
let router = useRouter()
let loginUser = definedUser()
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

// 计算选中的商品数量
const selectedCount = computed(() => {
    return cartInfo.value.filter(item => item.checked).length;
});

// 计算合计金额
const totalPrice = computed(() => {
    return cartInfo.value.reduce((sum, item) => {
        if (item.checked) {
            return sum + item.price * item.quantity;
        }
        return sum;
    }, 0);
});

// 全选状态
const isAllChecked = computed({
    get() {
        return cartInfo.value.length > 0 && cartInfo.value.every(item => item.checked);
    },
    set(value) {
        cartInfo.value.forEach(item => item.checked = value);
    }
});
const addGoodsQuantity = async (goods) => {
    const response = await axios.put('/cart/addGoodsQuantity', {
        gid: goods.gid,
        uid: goods.uid
    })
    goods.quantity += 1
}
const minusGoodsQuantity = async (goods) => {
    const response = await axios.put('/cart/minusGoodsQuantity', {
        gid: goods.gid,
        uid: goods.uid
    })
    goods.quantity -= 1
}
const ifedit = ref(false)
const showEdit = () => {
    ifedit.value = true
}
const closeEdit = () => {
    ifedit.value = false
}

const deleteGoods = async () => {
    const selectedGids = cartInfo.value.filter(item => item.checked).map(item => item.gid);

    console.log(selectedGids);

    if (selectedGids.length === 0) {
        return;
    }
    try {
        const response = await axios.delete('/cart/deleteGoods', {
            data: {
                gids: selectedGids,
                uid: loginUser.uid
            }
        });
        if (response.data.code == 200) {
            ElMessage.success('删除成功')
            cartInfo.value = cartInfo.value.filter(item => !selectedGids.includes(item.gid));
            ifedit.value = false
        }

    } catch (error) {
        console.error('删除失败:', error);
    }
};
const cartInfoLength = computed(() => {
    return cartInfo.value.length
})
const goToSubmitOrder = () => {
    // 跳转到提交订单页面，并传递 gidList
    const selectedGids = cartInfo.value.filter(item => item.checked).map(item => item.gid);
    const quantityList = cartInfo.value.filter(item => item.checked).map(item => item.quantity);
    console.log(selectedGids);

    router.push({
        name: 'SubmitOrder_app',
        query: {
            gidList: JSON.stringify(selectedGids),
        }
    });
};
onMounted(() => {
    fetchCartInfo()
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

.header h3 {
    margin: 0;
    font-size: 15px;
}

.header-edit {
    position: absolute;
    right: 15px;
    margin: 10px;
    top: 8px;
    font-size: 14px;
}

.shipping-cart-page {
    background-color: #f5f5f5;
    min-height: 90vh;
    padding: 16px;
}

.goods-item {
    background-color: #fff;
    display: flex;
    align-items: center;
    gap: 20px;
    padding: 15px;
    margin-bottom: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);

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

.goods-checkbox {
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    width: 15px;
    height: 15px;
    border: 1px solid #ccc;
    border-radius: 4px;
    outline: none;
    cursor: pointer;
    position: relative;
}

.goods-checkbox:checked {
    background-color: #e0594c;
    border-color: #e0594c;
}

.goods-checkbox:checked::after {
    content: '\2713';
    display: block;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: white;
    font-size: 12px;
}

.cart-empty {
    width: 100%;
    min-height: 93vh;
    background-color: #f5f5f5;
    padding-top: 65px;
}

.cart-empty img {
    max-width: 100%;
}
</style>