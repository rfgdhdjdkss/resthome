<template>
    <div class="recharge-container">
        <header class="header">
            <h1>充值</h1>
        </header>
        <div class="content">
            <div class="amount-input">
                <el-input
                    v-model="amount"
                    type="number"
                    placeholder="请输入充值金额"
                    prefix-icon="el-icon-money"
                />
            </div>
            <div class="amount-options">
                <el-button v-for="option in options" :key="option" @click="setAmount(option)">
                    {{ option }}元
                </el-button>
            </div>
            <div class="summary">
                <h2>充值摘要</h2>
                <p>充值金额: {{ amount }} 元</p>
                <p>到账金额: {{ amount }} 元</p>
            </div>
            <el-button type="primary" @click="submitRecharge">确认充值</el-button>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            amount: '',
            options: [10, 20, 50, 100, 200]
        };
    },
    methods: {
        setAmount(option) {
            this.amount = option;
        },
        submitRecharge() {
            // 模拟充值逻辑
            console.log(`充值金额: ${this.amount} 元`);
            this.$message.success('充值成功！');
        }
    }
};
</script>

<style scoped>
.recharge-container {
    padding: 16px;
    background-color: #f5f5f5;
    height: 100vh;
    display: flex;
    flex-direction: column;
}

.header {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
}

.content {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.amount-input {
    margin-bottom: 20px;
}

.amount-options {
    display: flex;
    justify-content: space-around;
    margin-bottom: 20px;
}

.summary {
    margin-bottom: 20px;
    background-color: #fff;
    padding: 10px;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style> 