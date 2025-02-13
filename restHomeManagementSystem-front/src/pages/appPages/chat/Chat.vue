<template>
    <div class="chat-container">
        <!-- 聊天消息区域 -->
        <div class="message-area" ref="messageArea">
            <div v-for="(message, index) in messages" 
                 :key="index" 
                 :class="['message', message.type]">
                <div class="message-content">
                    <div class="avatar">
                        <el-avatar :size="40" :src="message.avatar"/>
                    </div>
                    <div class="text">{{ message.content }}</div>
                </div>
                <!-- <div class="time">{{ formatTime(message.time) }}</div> -->
            </div>
        </div>

        <!-- 发送消息区域 -->
        <div class="input-area">
            <el-input
                v-model="newMessage"
                type="textarea"
                :rows="3"
                placeholder="请输入消息..."
                @keyup.enter.exact="sendMessage"
            />
            <el-button type="primary" @click="sendMessage">发送</el-button>
        </div>
    </div>
    <Footer :menuType="menuType" />
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
// import dayjs from 'dayjs'
import Footer from '../components/Footer.vue'
const messages = ref([])
const newMessage = ref('')
const messageArea = ref(null)
const menuType = ref('chat')
// 模拟一些初始消息
onMounted(() => {
    messages.value = [
        {
            content: '您好，请问有什么可以帮助您的？',
            type: 'received',
            // time: new Date(),
            avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
        }
    ]
})

// 发送消息
const sendMessage = async () => {
    if (!newMessage.value.trim()) return

    // 添加用户消息
    messages.value.push({
        content: newMessage.value,
        type: 'sent',
        // time: new Date(),
        avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
    })

    newMessage.value = ''

    // 滚动到底部
    await nextTick()
    messageArea.value.scrollTop = messageArea.value.scrollHeight

    // 模拟服务器响应
    setTimeout(() => {
        messages.value.push({
            content: '收到您的消息，我们会尽快处理。',
            type: 'received',
            // time: new Date(),
            avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
        })
        messageArea.value.scrollTop = messageArea.value.scrollHeight
    }, 1000)
}

// 格式化时间
// const formatTime = (time) => {
//     return dayjs(time).format('HH:mm')
// }
</script>

<style scoped>
.chat-container {
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 20px;
    background-color: #f5f7fa;
}

.message-area {
    flex: 1;
    overflow-y: auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    margin-bottom: 20px;
}

.message {
    margin-bottom: 20px;
}

.message-content {
    display: flex;
    align-items: flex-start;
    gap: 10px;
}

.text {
    padding: 10px 15px;
    border-radius: 8px;
    max-width: 70%;
    word-break: break-word;
}

.time {
    font-size: 12px;
    color: #999;
    margin-top: 5px;
    margin-left: 50px;
}

.received .text {
    background-color: #f4f4f5;
}

.sent {
    flex-direction: row-reverse;
}

.sent .message-content {
    flex-direction: row-reverse;
}

.sent .text {
    background-color: #ecf5ff;
    color: #409eff;
}

.sent .time {
    text-align: right;
    margin-right: 50px;
    margin-left: 0;
}

.input-area {
    display: flex;
    gap: 10px;
}

.input-area .el-button {
    align-self: flex-end;
}
</style>
