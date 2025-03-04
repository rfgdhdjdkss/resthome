<template>
    <div>
      <h1>支付成功！</h1>
      <p>订单号：{{ orderId }}</p>
      <p>支付金额：{{ amount }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  
  const route = useRoute();
  const orderId = ref('');
  const amount = ref('');
  
  onMounted(() => {
    orderId.value = route.query.orderId; // 从 URL 中获取订单号
    amount.value = route.query.amount;   // 从 URL 中获取支付金额
  });
  </script>