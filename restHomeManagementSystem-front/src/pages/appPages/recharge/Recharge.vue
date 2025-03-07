<template>
    <div class="header">
        <span class="back-arrow" @click="router.push('Mine_app')">←</span>
        <h4>账户充值</h4>
    </div>
    <div class="recharge-page">
        <div class="recharge-container">
            <form @submit.prevent="submitForm" class="recharge-form">
                <div class="form-item">
                    <label for="amount">充值金额</label>
                    <div class="amount-buttons">
                        <button type="button" v-for="(amount, index) in presetAmounts" :key="index"
                            @click="selectAmount(amount)"
                            :class="['amount-btn', { 'selected': form.amount === amount }]">
                            {{ amount }} 元
                        </button>
                        <button type="button" @click="showCustomAmountInput = true" class="amount-btn custom-btn">
                            自定义
                        </button>
                    </div>
                    <div v-if="showCustomAmountInput" class="custom-amount-input">
                        <input type="number" v-model="form.amount" placeholder="请输入自定义金额" min="0" class="input"
                            @input="validateAmount" />
                    </div>
                </div>
                <div class="form-item">
                    <label for="paymentMethod">支付方式</label>
                    <div class="radio-group">
                        <label class="radio-item">
                            <input type="radio" id="alipay" value="支付宝" v-model="form.paymentMethod" class="radio" />
                            <span class="label">支付宝</span>
                        </label>
                        <label class="radio-item">
                            <input type="radio" id="wechat" value="微信支付" v-model="form.paymentMethod" class="radio" />
                            <span class="label">微信支付</span>
                        </label>
                        <label class="radio-item">
                            <input type="radio" id="unionpay" value="银联支付" v-model="form.paymentMethod" class="radio" />
                            <span class="label">银联支付</span>
                        </label>
                    </div>
                </div>
                <div class="form-item">
                    <button type="submit" class="submit-btn">立即充值</button>
                </div>
            </form>
        </div>
    </div>
    <div class="footer-text">
        要养老，上养老生活 ®
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import axios from '@/api/request';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import { createAlipayPayment, submitAlipayForm } from '@/api/alipay';
import { definedUser } from '@/stores';
let loginUser = definedUser()
const router = useRouter();

const form = reactive({
    amount: '',
    paymentMethod: '支付宝',
});

const presetAmounts = [50, 100, 200, 500, 1000];
const showCustomAmountInput = ref(false);

const selectAmount = (amount) => {
    form.amount = amount;
    showCustomAmountInput.value = false;
};

const validateAmount = () => {
    if (form.amount <= 0) {
        ElMessage.error('充值金额不能小于等于0');
        form.amount = ''; // 清空输入框
    }
};
const addIn = () => {
    axios.post("/transaction/addIn", {
        uid: loginUser.uid,
        transactionMoney: form.amount,
        transactionType: '支付宝',
        description: `为${loginUser.nickname}账户充值`,
        inOrOut: 1
    }).then(function (response) {
        console.log(response);
    }).catch(function (error) {
        console.log(error);
    })
}
function getBalance() {
    axios.get(`/user/selectBalance/${loginUser.uid}`, {
    })
        .then(function (response) {
            loginUser.balance = response.data.data.balance
        }
        ).catch(function (error) {
            console.log(error);
        });
}
const addRecharge = () => {
    axios.put("/user/rechargeBalance", {
        uid: loginUser.uid,
        money: form.amount,
    }).then(function (response) {
        console.log(response)
        getBalance()
        addIn()
    }).catch(function (error) {
        console.log(error);
    })
}
const submitForm = async () => {
    if (!form.amount || form.amount <= 0) {
        ElMessage.error('请输入有效的充值金额');
        return;
    }
    if (!form.paymentMethod) {
        ElMessage.error('请选择支付方式');
        return;
    }
    // 调用支付接口
    try {
        const orderId = `RECHARGE_${Date.now().toString()}`;
        const amount = form.amount;
        const subject = '账户充值订单';

        const htmlData = await createAlipayPayment(orderId, amount, subject);
        submitAlipayForm(htmlData);
        addRecharge()
    } catch (error) {
        console.error('支付失败:', error);
    }
};
</script>

<style scoped>
.recharge-page {
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

.recharge-container {
    background-color: #ffffff;
    padding: 40px;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    max-width: 600px;
}

.recharge-form {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.form-item {
    margin-bottom: 20px;
}

.form-item label {
    display: block;
    margin-bottom: 10px;
    font-weight: 500;
    color: #333333;
}

.amount-buttons {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
}

.amount-btn {
    padding: 10px 20px;
    border: 1px solid #e7e7e7;
    border-radius: 6px;
    background-color: #ffffff;
    color: #333333;
    cursor: pointer;
    transition: border-color 0.3s, background-color 0.3s;
}

.amount-btn:hover {
    border-color: #409eff;
    background-color: #f5f8fa;
}

.amount-btn.selected {
    border-color: #409eff;
    background-color: #f5f8fa;
}

.custom-btn {
    background-color: #f5f5f5;
    color: #666666;
}

.custom-amount-input {
    margin-top: 10px;
}

.input {
    width: 100%;
    padding: 12px;
    border: 1px solid #e7e7e7;
    border-radius: 6px;
    font-size: 16px;
    transition: border-color 0.3s;
}

.input:focus {
    border-color: #409eff;
    box-shadow: 0 0 5px rgba(64, 158, 255, 0.3);
}

.radio-group {
    display: flex;
    gap: 20px;
}

.radio-item {
    display: flex;
    align-items: center;
    cursor: pointer;
}

.radio {
    margin-right: 5px;
}

.label {
    font-weight: 500;
    color: #333333;
}

.submit-btn {
    width: 100%;
    padding: 12px;
    background-color: #409eff;
    color: #ffffff;
    border: none;
    border-radius: 6px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.submit-btn:hover {
    background-color: #66b1ff;
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