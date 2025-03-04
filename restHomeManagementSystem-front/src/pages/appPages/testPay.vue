<template>
  <div>
    <h1>支付宝支付</h1>
    <button @click="handlePay">支付 0.01 元</button>
  </div>
</template>

<script setup>
import axios from '@/api/request';
const handlePay = async () => {
  try {
    // 调用后端接口，获取支付宝支付表单
    const response = await axios.get('/alipay/create-payment', {
      params: {
        orderId: 'TEST_' + Date.now(), // 订单号
        amount: 0.01,                  // 金额
        subject: '测试订单'            // 订单标题
      }
    });

    // 将支付宝返回的 HTML 表单渲染到页面并自动提交
    const div = document.createElement('div');
    div.innerHTML = response.data;
    document.body.appendChild(div);
    document.forms[0].submit(); // 自动提交表单
  } catch (error) {
    console.error('支付失败:', error);
  }
};
</script>