<template>
    <div class="elder-list-page">
        <div class="header">
            <span class="back-arrow" @click="router.go(-1)">←</span>

            <h2>我的预定</h2>
            <el-input v-model="searchKey" placeholder="搜索老人姓名" prefix-icon="Search" clearable @clear="handleSearch"
                @input="handleSearch" />
        </div>

        <div class="elder-cards">
            <h3>未签约老人</h3>
            <el-card v-for="elder in filteredElders.notReserved" :key="elder.hid" class="elder-card"
                @click="goToSignCheckInDetail(elder.eid)">
                <div class="elder-info">
                    <div class="info-content">
                        <div class="name-age">
                            <span class="name">{{ elder.elderlyName }}</span>
                            <span class="age">{{ elder.elderlyAge }}岁</span>
                            <span class="name">{{ elder.elderlySex }}</span>
                        </div>
                        <div class="status-tags">
                            <el-tag type="warning" size="small">未签约</el-tag>
                        </div>
                    </div>
                    <el-icon class="arrow-icon">
                        <ArrowRight />
                    </el-icon>
                </div>
                <div class="vital-signs">
                    <div class="sign-item" style="max-width: 100px;">
                            <span class="label">家庭住址</span>
                            <span class="value" style="font-size: 13px;">{{ elder.elderlyAddress }}</span>

                    </div>

                    <div class="sign-item">
                        <span class="label">预定人姓名</span>
                        <span class="value">{{ elder.bookerName }}</span>
                    </div>
                    <div class="sign-item">
                        <span class="label">预定时间</span>
                        <span class="value">{{ elder.bookerTime }}</span>
                    </div>
                </div>
            </el-card>

            <h3>已签约老人</h3>
            <el-card v-for="elder in filteredElders.reserved" :key="elder.hid" class="elder-card" @click="showMessage">
                <div class="elder-info">
                    <div class="info-content">
                        <div class="name-age">
                            <span class="name">{{ elder.elderlyName }}</span>
                            <span class="age">{{ elder.elderlyAge }}岁</span>
                            <span class="name">{{ elder.elderlySex }}</span>
                        </div>
                        <div class="status-tags">
                            <el-tag type="success" size="small">已签约</el-tag>
                        </div>
                    </div>
                    <el-icon class="arrow-icon">
                        <ArrowRight />
                    </el-icon>
                </div>
                <div class="vital-signs">
                    <div class="sign-item" style="max-width: 100px;">

                        <span class="label">家庭住址</span>
                        <span class="value" style="font-size: 13px;">{{ elder.elderlyAddress }}</span>
                    </div>
                    <div class="sign-item">
                        <span class="label">预定人姓名</span>
                        <span class="value">{{ elder.bookerName }}</span>
                    </div>
                    <div class="sign-item">
                        <span class="label">预定时间</span>
                        <span class="value">{{ elder.bookerTime }}</span>
                    </div>
                </div>
            </el-card>
        </div>

    </div>
    <div class="footer-text">
        要养老，上养老生活 ®
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ArrowRight, Search } from '@element-plus/icons-vue';
import axios from '@/api/request.js'
import { definedUser } from '@/stores/index.js';
import { ElMessage } from 'element-plus'

let loginUser = definedUser()
const router = useRouter();
const elders = ref([])

const searchKey = ref('');
const fetchData = () => {
    axios.get(`/reserve/selectReserveByUid/${loginUser.uid}`, {
    }).then(function (response) {
        console.log(response);
        elders.value = response.data.data.map(item => ({
            ...item,
            bookerTime: formatBookerTime(new Date(item.bookerTime)),
            elderlySex: item.elderlySex === 'male' ? '男' : '女',
            isReserved: item.isReserved === 0 ? false : true,
            elderlyAge: calcAge(item.elderlyIdCard)
        }))
    }).catch(function (error) {
        console.log(error);
    })
}
// 搜索过滤
const filteredElders = computed(() => {
    const notReserved = elders.value.filter(elder => !elder.isReserved);
    const reserved = elders.value.filter(elder => elder.isReserved);
    return { notReserved, reserved };
});
// 封装格式化预定时间
function formatBookerTime(time) {
    const bookerTime = new Date(time);
    return bookerTime.getFullYear() + "-" + bookerTime.getMonth() + "-" + bookerTime.getDate()
}
// 搜索处理
const handleSearch = () => {
    // 可以添加额外的搜索逻辑
};

// 跳转到健康详情页
const goToSignCheckInDetail = (elderId) => {
    router.push({
        name: 'SignCheckInDetail_app',
        params: { eid: elderId }
    });
};
const showMessage = () => {
    ElMessage({
        message: '已签约，不可重复签约',
        type: 'warning',
    })
}
//根据身份证计算年龄
const calcAge = (idCard) => {

    const birthYear = parseInt(idCard.slice(6, 10), 10);
    const birthMonth = parseInt(idCard.slice(10, 12), 10);
    const birthDay = parseInt(idCard.slice(12, 14), 10);
    const now = new Date();
    const currentYear = now.getFullYear();
    const currentMonth = now.getMonth() + 1;
    const currentDay = now.getDate();
    let age = currentYear - birthYear;
    if (currentMonth < birthMonth || (currentMonth === birthMonth && currentDay < birthDay)) {
        age--;
    }
    return age;
}


onMounted(() => {
    fetchData()
})
</script>

<style scoped>
.elder-list-page {
    padding: 20px;
    padding-bottom: 60px;
    background-image: url(../../../assets/images/home_app_background.jpg);
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    min-height: 80vh;
}

.back-arrow {
    font-size: 24px;
    cursor: pointer;
}

.header {
    margin-bottom: 20px;
}

.header h2 {
    margin-bottom: 15px;
    color: #303133;
}

.elder-cards {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.elder-card {
    cursor: pointer;
    transition: all 0.3s;
}

.elder-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.elder-info {
    display: flex;
    align-items: center;
    gap: 15px;
    padding-bottom: 15px;
    border-bottom: 1px solid #ebeef5;
    position: relative;
}

.info-content {
    flex: 1;
}

.name-age {
    margin-bottom: 5px;
}

.name {
    font-size: 18px;
    font-weight: bold;
    margin-right: 10px;
}

.age {
    color: #909399;
    margin-right: 10px;

}

.sex {
    color: #606266;
    margin-bottom: 8px;
}

.status-tags {
    display: flex;
    gap: 8px;
}

.arrow-icon {
    color: #909399;
    font-size: 20px;
}

.vital-signs {
    display: flex;
    justify-content: space-between;
    margin-top: 15px;
}

.sign-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 5px;
}

.sign-item .label {
    color: #909399;
    font-size: 13px;
}

.sign-item .value {
    color: #303133;
    font-weight: 500;
}

:deep(.el-card) {
    border-radius: 8px;
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
