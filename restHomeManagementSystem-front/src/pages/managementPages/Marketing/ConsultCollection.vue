<template>
    <Header-web></Header-web>
    <Menu-web></Menu-web>
    <Footer-web></Footer-web>
    <div id="box">
        <el-table :data="tableData" :default-sort="{ prop: 'isReply', order: 'descending' }" style="width: 100%"
            size="large">
            <el-table-column prop="sendTime" label="最近咨询时间" sortable width="180" />
            <<el-table-column prop="whichUidConsult" label="用户id" width="180" />
            <el-table-column prop="nickName" label="昵称" />
            <el-table-column prop="isReply" label="是否回复" sortable />
            <el-table-column align="right">
                <template #default="scope">
                    <el-button size="default" @click="answer(scope.$index, scope.row)">
                        答复
                    </el-button>
                </template>
            </el-table-column>
        </el-table>


    </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router';


import axios from '@/api/request.js';

let router = useRouter()

//Consult接口，定义一个咨询的所有属性
interface Consult {
    sendTime: Date,
    whichUidConsult: number,
    nickName: string,
    isReply: string
}

//存储所有咨询的数组
let tableData = reactive<Consult[]>([])
// 封装格式化聊天消息的发送时间
function formatSendTime(time) {
    const sendTime = new Date(time);
    return sendTime.getFullYear() + "-" + sendTime.getMonth() + "-" + sendTime.getDate() + " " + sendTime.getHours() + ":" + sendTime.getMinutes().toString().padStart(2, '0')
}
//获取数据的事件
function fetchData() {
    //发送get请求
    axios.get("/consult/getConsultCollection"
    ).then(function (response) {
        //将返回的数据转换为Consult[]类型并保存到存储咨询信息的数组中显示在窗口中
        const convertedData: Consult[] = response.data.data.consultCollection.map(item => ({
            sendTime: formatSendTime(item.sendTime),
            whichUidConsult: item.whichUidConsult,
            nickName: item.nickName,
            //三元表达式，isReply为true时返回已回复
            isReply: item.isReply === 'true' ? '已回复' : '未回复'
        }));
        //清空原数组，加入响应回的数组
        tableData.splice(0, tableData.length, ...convertedData);
    }).catch(function (error) {
        console.log(error);
    }
    )
}
//回复按钮点击事件
function answer(index: number, row: Consult) {
    //跳转到咨询详情页并传参
    router.push({ name: 'Consult', params: { whichUidConsult: row.whichUidConsult } });

}
//挂载
onMounted(() => {
    //获取数据并在窗口中显示
    fetchData()
})

</script>
<style scoped>
#box {
    background-color: white;
    display: flex;
    align-items: center;
    flex-direction: column;
    width: 82%;
    height: auto;
    float: right;
    position: relative;
    top: 80px;
    margin-right: 10px;
    z-index: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-color: rgb(243, 244, 246);
    border-radius: 10px;
}

#container {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

#consult_container {
    width: 80%;
    height: 250px;
    border-radius: 50px;
    border: 1px solid #e0e0e0;

}
</style>