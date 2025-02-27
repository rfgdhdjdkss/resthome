<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
    </div>
    <div class="goods-detail-page">

        <div class="container">
            <div class="goods-title-image-price">
                <div class="goods-title">
                    <span>
                        {{ goodsInfo.title }}
                    </span>
                </div>
                <div class="goods-sub-title">
                    <span>
                        {{ goodsInfo.description }}
                    </span>
                </div>
                <div class="goods-image">
                    <img :src="`http://localhost:8999/images/upload/goodsImg/${goodsInfo.image}`" alt=""
                        v-if="goodsInfo.image != undefined">

                </div>
                <div class="goods-price">
                    <div class="now-price">
                        <span>¥{{ nowPrice }}</span>
                    </div>
                    <div class="orginal-price">
                        门店价{{ goodsInfo.price }}
                    </div>
                    <div class="discount-tag" v-if="goodsInfo.discount !== null">
                        <el-tag>{{ goodsInfo.discount }}折</el-tag>
                    </div>
                </div>
            </div>
            <div class="sale-service-deliver">
                <div class="s-s-d-item">
                    <div class="s-s-d-item-lable">
                        <span>售后</span>
                    </div>
                    <div class="s-s-d-item-value">
                        <span>{{ goodsInfo.afterSale }}</span>
                    </div>
                </div>
                <div class="s-s-d-item">
                    <div class="s-s-d-item-lable">
                        <span>服务</span>
                    </div>
                    <div class="s-s-d-item-value">
                        <span>{{ goodsInfo.service }}</span>
                    </div>
                </div>
                <div class="s-s-d-item">
                    <div class="s-s-d-item-lable">
                        <span>发货</span>
                    </div>
                    <div>
                        <span class="s-s-d-item-value">{{ goodsInfo.deliver }} {{ goodsInfo.fee }}</span>
                    </div>
                </div>

            </div>
        </div>
        <div class="goods-comment">
            <div class="comment-header">
                <div class="c-h-left">
                    <span>商品评价</span>
                </div>
                <div class="c-h-right">
                    <span>更多评价（{{ goodsComment.length }}条）</span>
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="comment-content" v-for="item in goodsComment" v-if="goodsComment.length !== 0">
                <div class="c-c-header">
                    <div class="user-head">
                        <img :src="`http://localhost:8999/images/upload/headPortrait/${item.headImgUrl}`" alt=""
                            v-if="item.headImgUrl != undefined">
                    </div>
                    <div class="user-nickname">
                        <span>{{ item.nickName }}</span>
                    </div>
                </div>
                <div class="c-c-content">
                    <span>{{ item.content }}</span>
                </div>
            </div>
            <div class="comment-content2" v-else>
                <span>暂无用户评价</span>
            </div>
        </div>
    </div>
    <div class="cart">
        <div class="cart-price">
            <span>¥{{ nowPrice }}</span>
        </div>
        <div class="cart-button">
            <div class="cart-button-item">
                <button class="add-button" @click="addCart">加入购物车</button>
            </div>
            <div class="cart-button-item">
                <button class="buy-button" @click="goToSubmitOrder">立即抢购</button>
            </div>
        </div>
    </div>
    <div class="cart-icon" v-if="cartQuantitySum > 0" @click="gotoShippingCartPage">
        <div>
            <i>
                <svg t="1740316067120" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="5222" width="20" height="20">
                    <path
                        d="M789.93 865.904c-31.278 0-56.722 25.388-56.722 56.71s25.442 56.735 56.721 56.735c31.293 0 56.709-25.414 56.709-56.735s-25.416-56.71-56.709-56.71m-362.94 0c-31.253 0-56.738 25.388-56.738 56.71s25.486 56.735 56.737 56.735c31.279 0 56.666-25.414 56.666-56.735s-25.386-56.71-56.666-56.71M294.63 677.84l-55.887-347.462C203.045 125.82 29.349 123.568 27.639 123.568l-0.166 54.083c5.294 0.069 130.23 3.376 157.8 161.704l55.61 344.392c2.849 46.216 34.74 133.69 135.276 133.69h531.385v-54.192H376.158c-73.203 0-80.623-74.482-81.528-85.406m625.989-371.334c-101.509 0-539.82-54.082-539.82-54.082s-108.33-22.039-89.976 94.797c18.232 116.822 45.787 281.986 45.787 281.986s22.15 89.014 115.25 89.014h379.062c84.654 0 114.166-102.187 114.166-102.187l47.398-222.748s29.684-86.78-71.867-86.78"
                        p-id="5223"></path>
                </svg>
            </i>
        </div>
        <div class="cart-number">
            <span>{{ cartQuantitySum }}</span>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ArrowRight, Plus } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router';
import axios from '@/api/request';
import { onMounted, ref, computed } from 'vue';
import { ElMessage } from 'element-plus';
import { definedUser } from '@/stores';
let loginUser = definedUser()
let route = useRoute()
let router = useRouter()
const gid = route.params.gid
interface GoodsInfo {
    gid: number,
    title: string,
    image: string,
    afterSale: string,
    deliver: string,
    price: number,
    quantity: number,
    dscription: string,
    fee: string,
    service: string,
}
const goodsInfo = ref<GoodsInfo>({})
const goodsComment = ref([])
const fetchGoodsInfo = async () => {
    const response = await axios.get(`/goods/getGoodsDetailByGid/${gid}`)
    console.log(response);

    if (response.data.code === 200) {
        goodsInfo.value = response.data.data
        console.log(goodsInfo.value);

    } else {
        ElMessage.error(response.data.message)
    }
}
const nowPrice = computed(() => {
    // 使用toFixed方法保留两位小数
    if (!goodsInfo.value.discount == null) {
        return (goodsInfo.value.price * goodsInfo.value.discount * 0.1).toFixed(2);
    }
    return goodsInfo.value.price
});
const fetchGoodsComment = async () => {
    const response = await axios.get(`/goodsComment/getAllComment/${gid}`)
    if (response.data.code === 200) {
        goodsComment.value = response.data.data
        console.log(goodsComment.value);
    }
}
const addCart = async () => {
    const response = await axios.post("/cart/addCart", {
        uid: loginUser.uid,
        gid: gid,
        quantity: 1
    })
    if (response.data.code == 200) {
        ElMessage.success('加入购物车成功')
    }
    fetchCartQuantity()

}
const cartQuantitySum = ref()
const fetchCartQuantity = async () => {
    const response = await axios.get(`/cart/getCartQuantity/${loginUser.uid}`)
    cartQuantitySum.value = response.data.data
}
const gotoShippingCartPage = () => {
    router.push({ name: 'ShippingCart_app' })
}

const goToSubmitOrder = () => {

    router.push({
        name: 'SubmitOrder_app',
        query: {
            gidList: JSON.stringify([goodsInfo.value.gid]),
        }
    })
}

onMounted(() => {
    window.scrollTo(0, 0);
    fetchGoodsInfo()
    fetchGoodsComment()
    fetchCartQuantity()

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

.goods-detail-page {
    padding: 16px;
    background-color: #f5f5f5;
    min-height: 90vh;
}

.container {
    margin-top: 50px;
    background-color: #fff;
    border-radius: 20px;
    width: 95%;
    padding: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.goods-title-image-price {
    background-color: #fff;
    width: 100%;
    border-radius: 20px 20px 0 0;
}

.goods-title {
    color: #000;
    padding: 10px;
    font-size: 18px;
}

.goods-sub-title {
    color: #e37844;
    padding: 10px;
    padding-top: 0;
    font-size: 14px;
}

.goods-image {
    width: 95%;
    height: 220px;
    padding: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.goods-image img {
    max-width: 100%;
    height: 220px;
}

.goods-price {
    display: flex;
    align-items: center;
    gap: 10px;
    width: 100%;
    padding-top: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #ebeef5;

}

.now-price {
    color: #e25242;
    font-size: 20px;
    padding: 10px;
}

.orginal-price {
    color: #50504e;
    font-size: 14px;
}

.sale-service-deliver {
    padding: 10px;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.s-s-d-item {
    display: flex;
    align-items: center;
    gap: 10px;
}

.s-s-d-item-lable {
    color: #7f7f7f;
    font-size: 14px;
}

.s-s-d-item-value {
    color: #000;
    font-size: 14px;
}

.goods-comment {
    background-color: #fff;
    width: 100%;
    margin-top: 20px;
    border-radius: 20px;
    width: 95%;
    padding: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.comment-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px;
    border-bottom: 1px solid #ebeef5;

}

.c-h-left {
    font-size: 18px;
    color: #000;
}

.c-h-right {
    font-size: 12px;
    display: flex;
    align-items: center;
    color: #50504e;
}

.comment-content {
    display: flex;
    flex-direction: column;
    padding: 10px;
    min-height: 150px;

}

.comment-content2 {
    display: flex;
    flex-direction: column;
    padding: 10px;
    min-height: 150px;
    align-items: center;
    justify-content: center;
    color: #7f7f7f;
    font-size: 16px;
}

.c-c-header {
    display: flex;
    align-items: center;
    gap: 10px;
}

.user-head {
    width: 30px;
    height: 30px;
    border-radius: 50%;
}

.user-head img {
    max-width: 100%;
    height: 30px;
    border-radius: 50%;
}

.user-nickname {
    font-size: 14px;
}

.c-c-content {
    font-size: 14px;
    padding-left: 40px;
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
    color: #e25242;
    font-size: 22px;
}

.cart-button {
    display: flex;
}

.cart-button-item {
    border: none;
    border-radius: 25px 0 0 25px;
    color: #fff;
    font-size: 14px;
}

.add-button {
    border: none;
    border-radius: 25px 0 0 25px;
    padding: 10px;
    background-color: #51a3af;

}

.buy-button {
    border: none;
    border-radius: 0 25px 25px 0;
    padding: 10px;
    background-color: #e0594c;
}

.cart-icon {
    position: fixed;
    bottom: 100px;
    right: 10px;
    background-color: #fff;
    border-radius: 50%;
    width: 50px;
    height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    transition: all 0.3s ease;
}

.cart-number {
    position: absolute;
    top: 0px;
    right: 0px;
    background-color: red;
    color: white;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 12px;
}
</style>