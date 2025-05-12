import axios from '@/api/request';
import { definedUser } from '@/stores';
let loginUser = definedUser()
// 封装支付宝创建支付请求
export const createAlipayPayment = async (orderId, amount, subject) => {
  try {
    const uid = loginUser.uid
    const response = await axios.get('/alipay/create-payment', {
      params: { orderId, amount, subject, uid }
    });
    console.log(response);

    return response.data;
  } catch (error) {
    console.error('创建支付宝支付请求失败:', error);
    throw error;
  }
};

// 封装提交表单的函数
export const submitAlipayForm = (htmlData) => {
  if (!htmlData) {
    console.error('未获取到支付表单数据');
    alert('支付初始化失败，请重试');
    return;
  }

  const parser = new DOMParser();
  const doc = parser.parseFromString(htmlData, 'text/html');
  const form = doc.querySelector('form');

  if (!form) {
    console.error('未找到支付表单');
    alert('支付表单解析失败，请重试');
    return;
  }

  const dynamicForm = document.createElement('form');
  dynamicForm.method = form.method || 'post';
  dynamicForm.action = form.action;

  form.querySelectorAll('input').forEach(input => {
    const newInput = document.createElement('input');
    newInput.type = 'hidden';
    newInput.name = input.name;
    newInput.value = input.value;
    dynamicForm.appendChild(newInput);
  });

  document.body.appendChild(dynamicForm);
  dynamicForm.submit();
};