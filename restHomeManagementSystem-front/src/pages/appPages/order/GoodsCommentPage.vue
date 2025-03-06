<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
        <h4>商品评价</h4>
    </div>
    <div class="goods-comment-page">
        <div>
            <div class="goods-title-image-price">
                <div class="goods-image">
                    <img :src="`http://localhost:8999/images/upload/goodsImg/${goodsInfo.image}`" alt=""
                        v-if="goodsInfo.image != undefined">

                </div>
                <div
                    style="display: flex; flex-direction: column; height: auto;  width: 50%; justify-content: start; align-items: start; padding-bottom: 100px;">
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
                    <div class="goods-title">
                        <span>
                            ¥{{ goodsInfo.price }}
                        </span>
                    </div>
                </div>


            </div>
        </div>
        <div class="comment-form">
            <label for="content" class="now-price">评价内容：</label>
            <textarea v-model="commentContent" id="content" placeholder="请输入你的评价内容"></textarea>

            <button @click="submitComment">提交评价</button>
        </div>
    </div>
    <div class="footer-text">
        要养老，上养老生活 ®
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import axios from '@/api/request'
import { useRoute, useRouter } from 'vue-router';
import { definedUser } from '@/stores';
import { ElMessage } from 'element-plus';
// 获取商品 ID，这里假设通过路由传递
const route = useRoute();
const router = useRouter();
const goodsInfo = ref({})
const gid = ref(route.query.gid);
const oid = ref(route.query.oid);
const loginUser = definedUser()
const fetchGoodsInfo = async () => {
    const response = await axios.get(`/goods/getGoodsDetailByGid/${gid.value}`)
    console.log(response);

    if (response.data.code === 200) {
        goodsInfo.value = response.data.data
        console.log(goodsInfo.value);

    } else {
        ElMessage.error(response.data.message)
    }
}
onMounted(() => {
    fetchGoodsInfo()
})
// 评价内容
const commentContent = ref('');

const submitComment = async () => {
    try {
        const response = await axios.post('/goodsComment/addComment', {
            uid: loginUser.uid,
            gid: gid.value,
            content: commentContent.value,
        });
        if (response.data.code === 200) {
            ElMessage.success('评价成功')
            updateOrderStatus('finished')
        } else {
            ElMessage.error('评价失败')

        }
    } catch (error) {
        console.error('评价请求出错', error);
    }
};
const updateOrderStatus = async (status) => {
    try {
        const response = await axios.put(`/order/updateOrderStatus/${oid.value}`, {
            orderStatus: status // 假设更新为已取消状态
        });
        console.log('订单状态更新成功', response.data);
        // 可以在这里添加一些提示信息或跳转逻辑
    } catch (error) {
        console.error('订单状态更新失败', error);
    }
};
</script>

<style scoped>
.goods-comment-page {
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

.comment-form {
    margin-top: 20px;
}

textarea {
    width: 95%;
    height: 150px;
    padding: 10px;
    margin-bottom: 10px;
    border: none;
    border-radius: 20px;
    resize: vertical;
    margin-top: 10px;
}

.rating {
    margin-bottom: 10px;
}

button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    position: relative;
    left: 270px;
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
    width: 96%;
    border-radius: 20px;
    display: flex;
    padding: 10px;
    align-items: center;
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
    width: 40%;
    height: 200px;
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
    font-size: 18px;
    padding: 10px;
}

.orginal-price {
    color: #50504e;
    font-size: 14px;
}

.footer-text {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    text-align: center;
    margin: 10px 0;
    font-size: 14px;
    color: #008080;
    padding: 10px 0;
}
</style>