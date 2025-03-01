<template>
  <div>
    <button @click="handlePay">支付</button>
  </div>
</template>

<script setup>
import axios from '@/api/request';

const handlePay = async () => {
  try {
    // 1. 调用后端接口，获取支付宝支付表单
    const response = await axios.get('/create-payment', {
      params: {
        orderId: 'TEST123',
        amount: 0.01,
        subject: '测试订单'
      }
    });

    // 2. 渲染支付宝返回的 HTML 表单并自动提交
    const div = document.createElement('div');
    div.innerHTML = response.data;
    document.body.appendChild(div);
    document.forms[0].submit();
  } catch (error) {
    console.error('支付失败:', error);
  }
};
</script>
  