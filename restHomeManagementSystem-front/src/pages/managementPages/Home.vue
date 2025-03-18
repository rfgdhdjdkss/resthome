<template>
    <Header-web></Header-web>
    <Menu-web></Menu-web>
    <Footer-web></Footer-web>

    <!-- 轮播图 -->
    <div class="home-page">

        <div id="carousel_div">
            <!-- <el-carousel indicator-position="none" height="330px">
                <el-carousel-item v-for="item in itemPath" :key="item">
                    <img :src="item" alt="carousel item">
                </el-carousel-item>
            </el-carousel> -->
            <el-carousel :interval="2000" type="card" height="330px">
                <el-carousel-item v-for="item in itemPath" :key="item">
                    <img :src="item" alt="carousel item">
                </el-carousel-item>
            </el-carousel>
        </div>
        <!-- 今日概览 -->
        <div id="main_middle">
            <h3 style="margin-left: 10px;">今日概览</h3>
            <div id="overview">
                <!-- 今日新增入住 -->
                <div id="box1" class="overview_box">
                    <!-- 新增数量 -->
                    <div class="box_head">
                        <div class="newQuantity_div">
                            <p class="newQuantity_p">0</p>
                        </div>
                        <div class="message_div">
                            <el-icon color="white" size="35px">
                                <Message />
                            </el-icon>
                        </div>
                    </div>
                    <p class="new_p">
                        今日新增入住
                    </p>
                </div>
                <!-- 今日新增合同 -->
                <div id="box2" class="overview_box">
                    <!-- 新增数量 -->
                    <div class="box_head">
                        <div class="newQuantity_div">
                            <p class="newQuantity_p">0</p>
                        </div>
                        <div class="message_div">
                            <el-icon color="white" size="35px">
                                <Message />
                            </el-icon>
                        </div>
                    </div>
                    <p class="new_p">
                        今日新增合同
                    </p>
                </div>
                <!-- 今日退住申请 -->
                <div id="box3" class="overview_box">
                    <!-- 新增数量 -->
                    <div class="box_head">
                        <div class="newQuantity_div">
                            <p class="newQuantity_p">0</p>
                        </div>
                        <div class="message_div">
                            <el-icon color="white" size="35px">
                                <Message />
                            </el-icon>
                        </div>
                    </div>
                    <p class="new_p">
                        今日退住申请
                    </p>
                </div>
                <!-- 合同到期提醒 -->
                <div id="box4" class="overview_box">
                    <!-- 新增数量 -->
                    <div class="box_head">
                        <div class="newQuantity_div">
                            <p class="newQuantity_p">0</p>
                        </div>
                        <div class="message_div">
                            <el-icon color="white" size="35px">
                                <Message />
                            </el-icon>
                        </div>
                    </div>
                    <p class="new_p">
                        合同到期提醒
                    </p>
                </div>
            </div>

        </div>
        <!-- 可售床位 -->
        <div id="available_bed">
            <h3 style="margin-bottom: 10px; margin-left: 10px;">可售床位</h3>
            <div id="bed_div">
                <div class="bed_box">
                    <p class="bed_num">{{ spaceRoomNumber }}</p>
                    <p class="bed_title">空闲房间</p>
                </div>
                <div class="bed_box">
                    <p class="bed_num">{{ spaceBedNumber }}</p>
                    <p class="bed_title">空闲床位</p>
                </div>
            </div>
        </div>
    </div>
</template>


<script lang="ts" setup>
import {
    Message
} from '@element-plus/icons-vue'
import axios from '@/api/request.js';
import { definedUser } from '@/stores/index.js';
let loginUser = definedUser()

import { ref, onMounted } from 'vue';
const itemPath = ref([
    '/src/images/carousel_1.JPG',
    '/src/images/carousel_2.JPG',
    '/src/images/carousel_3.JPG',
    '/src/images/carousel_4.JPG',
    '/src/images/carousel_5.JPG',
])
const spaceRoomNumber = ref()
const spaceBedNumber = ref()
const getSpaceRoomNumber = () => {
    axios.get("/rooms/getSpaceRoomNumber", {
        // headers: {
        //     token: loginUser.token,
        // }
    }
    ).then(function (response) {

        spaceRoomNumber.value = response.data.data
    }).catch(function (error) {
        console.log(error);
    }
    )
}
const getSpaceBedNumber = () => {
    axios.get("/beds/getSpaceBedNumber").then(function (response) {
        spaceBedNumber.value = response.data.data
    }).catch(function (error) {
        console.log(error);
    }
    )
}
onMounted(() => {
    getSpaceRoomNumber()
    getSpaceBedNumber()
})
</script>
<style scoped>
/* start --- 轮播图 */
#carousel_div {
    background-color: white;
    margin: 10px;

}

#carousel_div img {
    width: 100%;

}

.home-page {
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

.el-carousel__item {
    display: flex;
    line-height: 330px;
    justify-content: center;
    height: 330px;
}

/* end --- 轮播图 */


/* start --- 今日概览 */
#main_middle {
    background-color: white;
    margin: 10px;
}

#overview {
    width: 98%;
    height: 110px;
    display: flex;
    justify-content: space-around;
    position: relative;
    background-color: white;
    margin: 10px;
}

.overview_box {
    margin-left: 10px;
    margin-right: 10px;
    width: 300px;
    height: 100px;
}

.message_div {
    display: inline-block;
    margin-right: 20px;
}

.box_head {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 50px;
}

.newQuantity_div {
    display: inline-block;
    margin-left: 20px;
    font-size: 22px;
    margin-bottom: 0;
    text-align: center;
    height: 50px;
    margin-top: 15px;
    color: white;
}

.newQuantity_p {
    margin: 0;
}

.new_p {
    font-size: 20px;
    margin-left: 20px;
    color: white;
    margin-top: 10px;
    margin-bottom: 0;
}

#overview #box1 {
    background-color: rgb(243, 204, 73);
}

#overview #box2 {
    background-color: rgb(119, 218, 137);
}

#overview #box3 {
    background-color: rgb(111, 165, 243);
}

#overview #box4 {
    background-color: rgb(232, 121, 117);
}

/* end --- 今日概览 */

/* start --- 可售床位 */
#available_bed {
    background-color: white;
    margin: 10px;
}

#bed_div {
    width: 98%;
    height: 130px;
    display: flex;
    justify-content: space-around;
    position: relative;
    background-color: white;
    margin: 10px;

}

.bed_box {
    margin-left: 10px;
    margin-right: 10px;
    width: 500px;
    height: 120px;
    display: flex;
    flex-direction: column;
    align-items: center;
    border: 1px solid #909399;

}

.bed_num {
    font-size: 26px;
    color: #409EFF;
    font-weight: 900;
    margin-top: 10px;
}

.bed_title {
    font-size: 22px;
    color: black;
    margin-top: 15px;
    font-weight: 900;
    margin-bottom: 0;
}

/* end --- 可售床位 */
</style>