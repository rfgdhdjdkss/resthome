<template>
    <div id="menu_div">
        <div id="logo_sysName_div">
            <!-- <div>
                <span>
                     <img id="logo" src="@/assets/images/logo.jpg" width="70px" height="70px" style="padding-bottom: 1px;" alt="">
                </span>
            </div> -->
            <div>
                <span id="sysName">
                    养老生活--养老院管理系统
                </span>
            </div>

        </div>
        <el-row class="tac">
            <el-col :span="12">
                <el-menu class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
                    <!-- 首页 -->
                    <router-link to="/home">
                        <el-menu-item index="1">
                            <el-icon>
                                <House />
                            </el-icon>
                            <span>首页</span>
                        </el-menu-item>
                    </router-link>
                    <!-- 营销管理 -->

                    <!-- <el-menu-item index="6">
                        
                        <el-icon>
                            <DishDot />
                        </el-icon>
                        <span>商品管理</span>
                        <router-link to="/catering/goodsManagementPage">
                            <el-sub-menu index="6-1">
                                <template #title>
                                    商品管理
                                </template>
</el-sub-menu>
</router-link>

</el-menu-item> -->


                    <el-sub-menu index="2">
                        <template #title>
                            <el-icon>
                                <IconMenu />
                            </el-icon>
                            <span>营销管理</span>
                        </template>
                        <!-- <router-link :to="{ name: 'Consult', params: { whichUidConsult: loginUser.uid } }"
                            v-if="loginUser.permission === '普通用户'">
                            <el-menu-item index="2-1">咨询管理</el-menu-item></router-link> -->
                        <router-link to="/marketing/consultCollection"> <el-menu-item
                                index="2-1">咨询管理</el-menu-item></router-link>
                        <!-- <router-link to="/marketing/reserve">
                            <el-menu-item index="2-2">预定入住</el-menu-item>
                        </router-link> -->
                        <router-link to="/marketing/reserveManagement" v-if="loginUser.permission === '管理员'">
                            <el-menu-item index="2-3">预定管理</el-menu-item>
                        </router-link>
                    </el-sub-menu>
                    <!-- 入住管理 -->
                    <!-- <el-sub-menu index="3">
                        <template #title>
                            <el-icon>
                                <CircleCheck />
                            </el-icon>
                            <span>入住管理</span>
                        </template>
                        <router-link to="/checkIn/signCheckInManagement">
                            <el-menu-item index="3-1">入住签约</el-menu-item>
                        </router-link>
                         <router-link to="/checkIn/outRegistration">
                            <el-menu-item index="3-2">外出登记</el-menu-item>
                        </router-link>
                        <router-link to="/checkIn/inRegistration">
                            <el-menu-item index="3-3">来访登记</el-menu-item>
                        </router-link> 
                        <router-link to="/checkIn/checkOut">
                            <el-menu-item index="3-4">退住申请</el-menu-item>
                        </router-link>
                    </el-sub-menu> -->
                    <!-- 人员管理 -->
                    <el-sub-menu index="4"  v-if="loginUser.permission === '管理员'">
                        <template #title>
                            <el-icon>
                                <User />
                            </el-icon>
                            <span>人员管理</span>
                        </template>
                        <!-- <router-link to="/person/elderlyInformation">
                            <el-menu-item index="4-1">老人信息</el-menu-item>
                        </router-link> -->
                        <router-link :to="{ name: 'ElderlyInformationManagement' }">
                            <el-menu-item index="4-1">老人信息管理</el-menu-item>
                        </router-link>
                        <router-link to="/person/userManagement">
                            <el-menu-item index="4-2">用户管理</el-menu-item>
                        </router-link>
                    </el-sub-menu>
                    <!-- 服务管理 -->
                    <el-sub-menu index="5">
                        <template #title>
                            <el-icon>
                                <FirstAidKit />
                            </el-icon>
                            <span>服务管理</span>
                        </template>
                        <router-link to="/service/elderLyHealthManagement">
                            <el-menu-item index="5-1">老人健康管理</el-menu-item>

                        </router-link>
                    </el-sub-menu>
                    <!-- 餐饮管理 -->


                    <!-- <router-link to="/catering/combo">
                            <el-menu-item index="6-2">餐饮套餐</el-menu-item>
                        </router-link> -->

                    <el-sub-menu index="6"  v-if="loginUser.permission === '管理员'">

                        <template #title>
                            <el-icon>
                                <DishDot />
                            </el-icon>
                            商品管理
                        </template>
                        <router-link to="/catering/goodsManagementPage">
                            <el-menu-item index="6-1">

                                <span>商品管理</span>
                            </el-menu-item>
                        </router-link>
                        <router-link to="/catering/dishes">
                            <el-menu-item index="6-2">菜品管理</el-menu-item>
                        </router-link>
                    </el-sub-menu>


                    <!-- 费用管理
                    <el-sub-menu index="7">
                        <template #title>
                            <el-icon>
                                <CreditCard />
                            </el-icon>
                            <span>费用管理</span>
                        </template>
                        <router-link to="/cost/recharge">
                            <el-menu-item index="7-1">预存充值</el-menu-item>
                        </router-link>
                        <router-link to="/cost/costRecord">
                            <el-menu-item index="7-2">消费记录</el-menu-item>
                        </router-link>
                        <router-link to="/cost/costAudit">
                            <el-menu-item index="7-3">退住费用审核</el-menu-item>
                        </router-link>

                    </el-sub-menu>
                     基础设置 
                    <el-sub-menu index="8">
                        <template #title>
                            <el-icon>
                                <setting />
                            </el-icon>
                            <span>基础设置</span>
                        </template>
                        <el-menu-item-group title="Group One">
                            <el-menu-item index="8-1">item one</el-menu-item>
                            <el-menu-item index="8-2">item two</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group title="Group Two">
                            <el-menu-item index="8-3">item three</el-menu-item>
                        </el-menu-item-group>
                        <el-sub-menu index="8-4">
                            <template #title>item four</template>
                            <el-menu-item index="8-4-1">item one</el-menu-item>
                        </el-sub-menu>
                    </el-sub-menu> -->

                </el-menu>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import {
    Document,
    Menu as IconMenu,
    House,
    CircleCheck,
    User,
    FirstAidKit,
    DishDot,
    CreditCard,
    Setting,
} from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
let router = useRouter()
import { definedUser } from '../stores/index.js'
let loginUser = definedUser()

const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
</script>

<style>
#menu_div {
    /* width: 305px;
    height: auto;
    border-bottom: 1px solid var(--el-border-color); */
    display: flex;
    float: left;
    flex-direction: column;
    width: 17%;
    height: 100vh;
    position: fixed;
    border-right: 1px solid var(--el-menu-border-color);
    background-color: rgb(58, 61, 72);

}

#logo_sysName_div {
    padding: 10px;
    width: 284px;
    height: 70px;
    display: flex;
    /* justify-content: space-between; */
    justify-content: center;
    align-items: center;
    gap: 10px;
}

#logo {
    width: 70px;
    height: 70px;
    float: left;
    border-radius: 50%;
    /* margin-left: 20px; */
}

#sysName {
    font-size: 22px;
    font-weight: bold;
    line-height: 40px;
    color: white;
}

.el-col-12 {
    max-width: 305px;
}

.el-menu {
    border: 0px;
    --el-menu-bg-color: rgb(58, 61, 72);
    --el-menu-text-color: #C0C4CC;
    --el-menu-active-color: white;
}

.el-row {
    display: block;
}

.el-menu-item:hover {
    background-color: #E6E8EB;
    color: black;
}

.el-sub-menu__title:hover {
    background-color: #E6E8EB;
    color: black;
}
</style>