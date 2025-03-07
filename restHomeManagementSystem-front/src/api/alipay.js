import axios from '@/api/request';
import { definedUser } from '@/stores';
let loginUser = definedUser()
// 封装支付宝创建支付请求
export const createAlipayPayment = async (orderId, amount, subject) => {
  try {
const uid=loginUser.uid
    
    const response = await axios.get('/alipay/create-payment', {
      params: { orderId, amount, subject, uid }
    });
    return response.data;
  } catch (error) {
    console.error('创建支付宝支付请求失败:', error);
    throw error;
  }
};

// 封装提交表单的函数
export const submitAlipayForm = (htmlData) => {
  // 解析返回的 HTML
  const parser = new DOMParser();
  const doc = parser.parseFromString(htmlData, 'text/html');
  const form = doc.querySelector('form');

  // 动态创建表单并提交
  const dynamicForm = document.createElement('form');
  dynamicForm.method = form.method;
  dynamicForm.action = form.action;

  // 添加所有隐藏的 input 字段
  form.querySelectorAll('input').forEach(input => {
    const newInput = document.createElement('input');
    newInput.type = 'hidden';
    newInput.name = input.name;
    newInput.value = input.value;
    dynamicForm.appendChild(newInput);
  });

  document.body.appendChild(dynamicForm);
  dynamicForm.submit(); // 提交表单
};