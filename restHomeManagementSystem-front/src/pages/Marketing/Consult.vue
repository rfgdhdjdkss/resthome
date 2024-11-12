<!-- 咨询管理 -->
<template>
    <div id="box">
        <div id="chatBox">
            <div id="chatHeader">
                <div id="headerTitle">咨询</div>
            </div>
            <div id="chatMain">
                <el-scrollbar ref="myScrollbar" :wrap-style="{ height: '600px' }">
                    <div id="chatContainer" v-for="item in chatList">
                        <div id="chatLeft_box" v-if="item.type === 'left'">
                            <div class="portrait">
                                <img src="../../images/tou.JPG" alt="" style="max-width: 100%;height: auto;">
                            </div>
                            <div class="chat-bubble left">
                                {{ item.chatContent }}
                            </div>
                            <div id="sendTimeBox1">
                                {{ item.sendTime }}
                            </div>
                        </div>

                        <div v-if="item.type === 'right'" id="chatRight_box">
                            <div id="sendTimeBox2">
                                {{ item.sendTime }}
                            </div>
                            <div class="chat-bubble right">
                                {{ item.chatContent }}
                            </div>
                            <div class="portrait">
                                <img src="../../images/tou.JPG" alt="" style="max-width: 100%;height: auto;">
                            </div>
                        </div>
                    </div>

                </el-scrollbar>
            </div>
            {{ chatList.content }}
            <div id="chatFooter">
                <textarea id="chatInput" v-model="textarea" placeholder="请输入...">
                </textarea>
                <el-button id="chatSubmitButton" type="primary" @click="sendChat">发送</el-button>
            </div>
        </div>

    </div>

</template>

<script lang="ts" setup>

import axios from '../../api/request.js';

import { onMounted, reactive, ref } from 'vue'
import { definedUser } from '../../stores/index.js';
import { useRoute } from 'vue-router';

let route = useRoute()
//保存路由传参中的参数，（与哪个uid所在的咨询）
const routeWhichUidConsult = route.params.whichUidConsult

//登录用户，保存了当前登录用户的一些信息
let loginUser = definedUser()
//存放输入的聊天框的内容
const textarea = ref("")
// 存储聊天消息的数组
let chatList = reactive<chat[]>([]);
//chat接口，定义一个聊天的所有属性
interface chat {
    chatid: string
    uid: string,
    type: string,
    chatContent: string,
    sendTime: Date,
    isUser: boolean,
    whichUidConsult: string,
}
//发送聊天事件
function sendChat() {
    if (textarea.value.trim() !== '') {
        //去除两端的空格，当输入框不为空时
        //发送post请求
        axios.post("/consult/addChat", {
            //请求参数：
            chatContent: textarea.value, //聊天内容
            uid: loginUser.uid, //哪个用户id发送的聊天
            permission: loginUser.permission,//用户的权限
            //与哪个uid所在的咨询，当权限为普通用户时传入当前登录用户的id，否则传入路由中带的id参数（管理员或员工使用时才会传）
            whichUidConsult: loginUser.permission === '普通用户' ? loginUser.uid : routeWhichUidConsult,
        }).then(function (response) {
            //将响应的数据push到存储聊天消息的数组中显示在页面
            chatList.push({
                chatid: response.data.data.consult.chatid,
                uid: response.data.data.consult.uid,
                type: 'right',
                chatContent: response.data.data.consult.chatContent,
                sendTime: formatSendTime(response.data.data.consult.sendTime),
                isUser: Boolean(response.data.data.consult.isUser),
                whichUidConsult: response.data.data.consult.whichUidConsult,
            });
            setTimeout(() => {
                //在显示完成后，将滚动条滚动到最下方
                scrollToBottom();
            }, 1);
            console.log(chatList)
        }).catch(function (error) {
            console.log(error);
        })
        if (loginUser.permission != '普通用户') {
            //当当前用户不为普通用户（即为管理员或普通员工时）发送消息时，更新是否回复状态
            updateIsReply()
        }
    }
    //发送完清空输入框内容
    textarea.value = '';
}
// 获取聊天记录并显示在窗口中(普通用户)
async function fetchChat() {
    //发送get请求
    const result = await axios.get("/consult/getChat", {
        params: {
            //请求参数：当权限为普通用户时传入当前登录用户的id，否则传入路由中带的id参数（管理员或员工使用时才会传）
            uid: loginUser.permission === '普通用户' ? loginUser.uid : routeWhichUidConsult,
        }
    }).then(function (response) {
        //将返回的数据转换为chat[]类型并保存到存储聊天消息的数组中显示在窗口中
        const convertedData: Chat[] = response.data.data.consults.map(item => ({
            ...item,
            // type: item.whichUidConsult === item.uid ? 'right' : 'left',
            type: loginUser.permission === item.permission ? 'right' : 'left',
            sendTime: formatSendTime(item.sendTime),
        }));
        chatList.splice(0, chatList.length, ...convertedData);
        console.log(chatList);

    }).catch(function (error) {
        console.log(error);
    })
}
// 封装格式化聊天消息的发送时间
function formatSendTime(time) {
    //定义一个Date对象
    const sendTime = new Date(time);
    //返回想要的时间格式
    return (sendTime.getMonth()+1) + "/" + sendTime.getDate() + " " + sendTime.getHours() + ":" + sendTime.getMinutes().toString().padStart(2, '0')

}
const myScrollbar = ref(null);
//将滚动条滚到底部的事件
const scrollToBottom = () => {
    if (myScrollbar.value && myScrollbar.value.$el.querySelector('.el-scrollbar__wrap')) {
        const wrap = myScrollbar.value.$el.querySelector('.el-scrollbar__wrap');
        wrap.scrollTop = wrap.scrollHeight;
    }
}
const params = new URLSearchParams();
params.append('whichUidConsult', routeWhichUidConsult);
// 点击回复时，将 isReply 置为 true
function updateIsReply() {
    axios.put("/consult/updateIsReply", params).then(function (response) {
        console.log(response)
    }).catch(function (error) {
        console.log(error);
    })
}
//挂载
onMounted(async () => {
    //获取数据
    await fetchChat();
    // 等待数据加载完成
    // 使用 setTimeout 作为延迟机制，给 Vue 和 el-scrollbar 一些额外的时间，再将滚动条调整到最下端
    setTimeout(() => {
        scrollToBottom();
    }, 1);
});

</script>

<style scoped>
#box {
    background-color: white;
    display: flex;
    align-items: center;
    flex-direction: column;
}

#chatBox {
    width: 100%;
    height: 900px;
    border: 1px solid #e0e0e0;
}

#chatHeader {
    height: 60px;
    width: 100%;
    border-bottom: 1px solid #e0e0e0;
    display: flex;
    align-items: center;
    background-color: #f3f3f3;
}

#headerTitle {
    margin-left: 10px;
}

#chatMain {
    height: 600px;
    width: 100%;
}

#chatFooter {
    height: 230px;
    width: 100%;
    border-top: 1px solid #e0e0e0;

}

#chatInput {
    height: 190px;
    width: 99%;
    border: 0;
    box-shadow: 0;
    margin-top: 1px;
    resize: none;
    font-size: 18px;
    margin-left: 2px;
}

textarea:focus {
    outline: none;
    box-shadow: 0;
}


#chatContainer {
    width: 100%;
}

#chatLeft_box {
    display: flex;
    align-items: center;
    width: 800px;
    float: left;
}


#chatRight_box {
    display: flex;
    justify-content: flex-end;
    margin-right: 10px;
    width: 800px;
    float: right;
}

.portrait {
    width: 50px;
    height: 50px;
}

.chat-bubble {
    position: relative;
    padding: 10px 15px;
    border-radius: 10px;
    margin: 10px 0;
    max-width: 70%;
    line-height: 1.5;
}

.chat-bubble.left {
    background-color: #e5e5ea;
    align-self: flex-start;
    margin-left: 10px;
    margin-top: 10px;
}

.chat-bubble.left::before {
    content: '';
    position: absolute;
    top: 15px;
    left: -10px;
    width: 0;
    height: 0;
    border: 10px solid transparent;
    border-right-color: #e5e5ea;
    border-left: 0;
    border-top: 0;
    margin-top: -5px;
}


.chat-bubble.right {
    background-color: #007aff;
    color: white;
    align-self: flex-end;
    margin-right: 10px;
}

.chat-bubble.right::before {
    content: '';
    position: absolute;
    top: 15px;
    right: -10px;
    width: 0;
    height: 0;
    border: 10px solid transparent;
    border-left-color: #007aff;
    border-right: 0;
    border-top: 0;
    margin-top: -5px;
}

#sendTimeBox1 {
    display: flex;
    align-items: flex-end;
    margin-left: 10px;
    font-size: 14px;
    color: #aaaaaa;
    margin-top: 20px;
}

#sendTimeBox2 {
    display: flex;
    align-items: flex-end;
    margin-bottom: 10px;
    margin-right: 10px;
    font-size: 14px;
    color: #aaaaaa;
}

#chatSubmitButton {
    display: flex;
    float: right;
    margin-right: 10px;
}
</style>
