<!-- <template>
    <div>
      <h1>支付成功！</h1>
      <p>订单号：{{ orderId }}</p>
      <p>支付金额：{{ amount }}</p>
    </div>
  </template>
  
   -->
<template>
  <div class="pay-success-page">
    <div class="header">
      <div class="header-row-1">
        <div>
          <svg t="1741083571274" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="6218" width="25" height="25">
            <path d="M512 512m-512 0a512 512 0 1 0 1024 0 512 512 0 1 0-1024 0Z" fill="#ffffff" p-id="6219"
              data-spm-anchor-id="a313x.search_index.0.i3.62543a81IaPQHA" class=""></path>
            <path
              d="M351.232 448.256c-12.032 0-13.568-1.536-12.8-13.568 0.256-6.656 1.536-13.056 3.584-19.456 3.072-8.96 6.4-11.52 15.872-11.52h95.744c10.24 0 10.496-0.256 10.496-10.24v-31.488c0-11.52 0-11.52-11.264-11.52h-151.808c-8.448-0.256-10.752-2.816-10.752-11.52 0-6.912 1.536-14.08 3.328-20.736 2.816-10.24 7.168-13.312 17.92-13.568h134.656c20.736 0 17.92 2.304 18.176-18.176 0.256-8.96 0.256-17.664 0-26.624-0.256-13.312 6.656-22.016 17.92-27.904 15.104-7.68 31.488-8.704 47.872-10.24 4.864-0.512 9.984-0.256 14.848-0.256 5.632-0.256 8.448 2.56 8.96 7.936 0.256 2.304 0.512 4.352 0.512 6.912-0.256 20.736-0.256 41.728-0.512 62.208 0 4.352 1.536 6.144 5.632 6.144h153.856c12.8 0 15.104 3.072 13.568 15.616-0.768 5.888-1.536 11.776-3.584 17.408-3.072 8.96-6.4 11.52-16.128 11.52-44.544 0-89.088-0.256-133.376 0.256-23.296 0.256-19.2-3.072-20.224 19.968-0.256 8.704 0.256 17.408-0.256 26.112-0.256 5.888 2.048 7.936 7.936 7.936 33.024-0.256 66.048-0.256 98.816 0 2.304 0 4.864 0 7.424 0.256 7.168 0.512 10.752 5.12 10.752 12.288-0.512 51.2-14.08 99.072-37.12 144.384-4.352 8.704-8.96 17.152-13.568 25.6-2.56 4.608-2.048 7.168 3.072 9.472 19.968 8.448 39.936 16.384 60.672 22.784 23.296 7.424 47.104 12.8 71.424 16.64 15.36 2.304 31.232 3.328 46.592 4.352 14.08 1.024 18.176 5.12 18.176 19.2 0 19.712-3.84 38.912-11.264 57.088-6.912 17.408-19.2 29.696-37.888 34.048-4.352 1.024-8.448 0.256-12.8 0.256-47.36-2.816-88.32-23.04-128-47.104-21.248-12.8-42.24-25.6-62.208-39.936-4.608-3.072-7.68-1.536-10.752 1.536-10.496 10.24-22.016 19.456-33.792 27.904-21.76 15.104-45.056 27.392-69.632 37.12-21.76 8.704-44.544 14.336-67.328 17.664-41.984 6.144-83.456 4.864-121.856-16.64-31.744-18.176-54.272-44.544-61.44-81.152-7.424-36.864 1.024-70.912 26.112-99.584 22.016-25.344 50.432-39.936 83.456-45.056 33.536-5.376 66.816-3.072 99.84 4.096s64.768 18.944 95.744 32.512c5.888 2.56 11.52 5.12 17.408 7.68 3.584 1.536 5.888 0.512 7.424-3.072 8.448-18.688 15.36-38.144 19.968-58.368 2.304-10.752 5.632-20.992 6.144-32 0.256-7.168 0.256-7.68-7.168-7.68-35.84 0.512-174.336 0.512-208.384 0.512z m-8.96 112.384c-9.984-0.256-19.712 0.512-29.44 2.816-22.272 5.376-42.752 13.824-56.32 33.792-15.36 22.784-12.032 46.08 4.352 65.536 13.568 16.128 32.512 24.064 52.992 27.136 35.584 5.632 69.632-0.512 102.4-15.616 28.928-13.568 53.76-32.512 75.264-55.808 4.096-4.352 3.328-6.656-1.536-9.472-16.896-9.728-33.792-18.688-51.712-26.112-30.72-13.056-62.208-22.272-96-22.272z"
              fill="#4a88d2" p-id="6220" data-spm-anchor-id="a313x.search_index.0.i5.62543a81IaPQHA" class="selected">
            </path>
          </svg>
        </div>
        <div>
          订单展示
        </div>
      </div>
      <div class="header-row-2">
        <div style="background-color: #fff; border-radius: 50%; width: 50px;height: 50px;">
          <img style="max-width: 100%; border-radius: 50%;" src="@/assets/images/logo.jpg" alt="">
        </div>
        <div class="store-title">
          <span>
            {{ subject }}
          </span>
        </div>
      </div>
    </div>

    <div class="order-info">
      <div style="display: flex;flex-direction: column;align-items: center;">
        <div class="order-info-title"><span> 订单金额</span></div>
        <div class="order-price"><span>¥{{ amount }}</span></div>
      </div>
      <div style="display: flex; flex-direction: column; gap: 10px; padding-top:10px ;">
        <div class="info-ex">
          <span>顾客实付款</span>
          <span>¥{{ amount }}</span>
        </div>
        <div class="info-ex">
          <span>交易方式</span>
          <span>余额</span>
        </div>
      </div>

    </div>
    <div class="footer">
      <div class="footer-info">
        <span>——实际金额以商家实际到账为准——</span>
      </div>
      <div>
        <button class="back-button" @click="handleBackToReserve">返回</button>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
let router = useRouter()
const route = useRoute();
const orderId = ref('');
const amount = ref('');
const subject = ref('');
const handleBackToReserve = () => {
  if (subject.value.endsWith('商品订单')) {
    router.push({
      name: 'MyOrder_app',
      query: {
        tab: 'all'
      }
    });
  } else if (subject.value.endsWith('充值订单')) {
    router.push({
      name: 'Recharge_app'
    });
  } else if (subject.value === '养老院签约定金') {
    router.push({
      name: 'SignCheckInDetail_app',
      params:{
        eid:localStorage.getItem('eid')
      }
    });
  } else {
    router.push({
      name: 'Home_app',

    });
  }
};
onMounted(() => {
  orderId.value = route.query.orderId; // 从 URL 中获取订单号
  amount.value = route.query.amount;   // 从 URL 中获取支付金额
  subject.value = route.query.subject;   // 从 URL 中获取支付金额
});
</script>

<style scoped>
.pay-success-page {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.header {
  padding-top: 20px;
  height: 230px;
  width: 100%;
  background-color: #3479f6;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #fff;
  gap: 50px;
}

.header-row-1 {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.header-row-2 {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  gap: 10px;
}

.order-info {
  width: 86%;
  height: auto;
  background-color: #fff;
  position: relative;
  margin: 0 auto;
  top: -50px;
  border-radius: 10px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.info-ex {
  color: #787878;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
}

.order-info-title {
  font-size: 15px;
  color: #c4c4c4;
  font-weight: 600;
}

.order-price {
  color: #000;
  font-weight: 700;
  font-size: 40px;
}

.footer {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  position: fixed;
  bottom: 40px;
  left: 0;
  right: 0;
}

.store-title {
  font-weight: 300;
}

.footer-info {
  font-size: 13px;
  color: #a6a6a6;
}

.back-button {
  border: none;
  background-color: #3479f6;
  color: #fff;
  width: 220px;
  height: 40px;
  border-radius: 5px;
  padding: 5px;
}
</style>