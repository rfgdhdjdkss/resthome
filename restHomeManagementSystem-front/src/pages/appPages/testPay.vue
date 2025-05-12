<template>
  <div>
    <h1>支付宝支付</h1>
    <button @click="handlePay">支付 0.01 元</button>
  </div>
</template>

<script setup>
import { createAlipayPayment, submitAlipayForm } from '@/api/alipay';
import { definedUser } from '../../stores';
let loginUser=definedUser()
const handlePay = async () => {
  try {
    const orderId = `Recharge_${Date.now()}${loginUser.uid}`;
    const amount = 20;
    const subject = 'sasc';

    const htmlData = await createAlipayPayment(orderId, amount, subject);
    submitAlipayForm(htmlData);
  } catch (error) {
    console.error('支付失败:', error);
  }
};
</script>