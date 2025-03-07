<template>
    <div class="chat-container">
        <!-- 聊天消息区域 -->
        <div class="message-area" ref="messageArea" style="overflow-y: auto; max-height: 70vh;">
            <div v-for="(message, index) in messages" :key="index" :class="['message', message.type]">
                <div class="message-content">
                    <div class="avatar">
                        <el-avatar :size="40" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                            v-if="message.type == 'received'" />
                        <el-avatar :size="40"
                            :src="`http://localhost:8999/images/upload/headPortrait/${loginUser.headImgUrl}`" v-else />
                    </div>
                    <div class="text">{{ message.chatContent }}</div>
                </div>
                <div class="time">{{ formatTime(message.sendTime) }}</div>
            </div>
        </div>
        <!-- 发送消息区域 -->
        <div class="input-area" style="">
            <el-input v-model="newMessage" type="textarea" :rows="3" placeholder="请输入消息..."
                @keyup.enter.exact="sendMessage" />
            <el-button type="primary" @click="sendMessage">发送</el-button>
        </div>
    </div>
    <Footer :menuType="menuType" />
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import dayjs from 'dayjs'
import Footer from '../components/Footer.vue'
import axios from '@/api/request'
import { definedUser } from '@/stores'
let loginUser = definedUser()
const messages = ref([])
const newMessage = ref('')
const messageArea = ref(null)
const menuType = ref('chat')

// 模拟一些初始消息
onMounted(() => {
    fetchChat()
})
const fetchChat = async () => {
    //发送get请求
    const response = await axios.get("/consult/getChat", {
        params: {
            //请求参数：当权限为普通用户时传入当前登录用户的id，否则传入路由中带的id参数（管理员或员工使用时才会传）
            uid: loginUser.permission === '普通用户' ? loginUser.uid : routeWhichUidConsult,
        }
    })
    const convertedData = response.data.data.consults.map(item => ({
        ...item,
        type: loginUser.permission === item.permission ? 'sent' : 'received',
    }));
    messages.value = convertedData;
    messages.value.push({
        chatContent: '您好，请问有什么可以帮助您的？',
        type: 'received',
        time: new Date(),
        avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
    })
    nextTick(() => {
        messageArea.value.scrollTop = messageArea.value.scrollHeight;
    })
    console.log(messages);


}
// 发送消息
const sendMessage = async () => {
    if (!newMessage.value.trim()) return
    axios.post("/consult/addChat", {
        //请求参数：
        chatContent: newMessage.value, //聊天内容
        uid: loginUser.uid, //哪个用户id发送的聊天
        permission: loginUser.permission,//用户的权限
        //与哪个uid所在的咨询，当权限为普通用户时传入当前登录用户的id，否则传入路由中带的id参数（管理员或员工使用时才会传）
        whichUidConsult: loginUser.permission === '普通用户' ? loginUser.uid : routeWhichUidConsult,
    })
    // 添加用户消息
    messages.value.push({
        chatContent: newMessage.value,
        type: 'sent',
        time: new Date(),
        avatar: `http://localhost:8999/images/upload/headPortrait/${loginUser.headImgUrl}`
    })

    newMessage.value = ''

    // 确保DOM更新后再设置滚动条位置
    await nextTick(() => {
        messageArea.value.scrollTop = messageArea.value.scrollHeight;
    });

    // 模拟服务器响应
    setTimeout(() => {
        messages.value.push({
            chatContent: '收到您的消息，我们会尽快处理。',
            type: 'received',
            time: new Date(),
            avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
        });
        // 再次确保DOM更新后设置滚动条位置
        nextTick(() => {
            messageArea.value.scrollTop = messageArea.value.scrollHeight;
        });
    }, 1000);
}

// 格式化时间
const formatTime = (time) => {
    return dayjs(time).format('MM-DD HH:mm')
}
</script>

<style scoped>
.chat-container {
    min-height: 89vh;
    display: flex;
    flex-direction: column;
    padding: 20px;
    background-color: #f5f7fa;
    background-image: url('@/assets/images/home_app_background.jpg');
}

.message-area {
    flex: 1;
    overflow-y: auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    margin-bottom: 20px;
    min-height: 74vh;
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
    margin-top: auto;
}

.input-area .el-button {
    align-self: flex-end;
}
</style>