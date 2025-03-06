<template>
  <div>
    <h1>支付宝支付</h1>
    <button @click="handlePay">支付 0.01 元</button>
  </div>
</template>

<script setup>
import { createAlipayPayment, submitAlipayForm } from '@/api/alipay';

const handlePay = async () => {
  try {
    const orderId = '20221321416102935425';
    const amount = 20;
    const subject = '测试订单';

    const htmlData = await createAlipayPayment(orderId, amount, subject);
    submitAlipayForm(htmlData);
  } catch (error) {
    console.error('支付失败:', error);
  }
};
</script>