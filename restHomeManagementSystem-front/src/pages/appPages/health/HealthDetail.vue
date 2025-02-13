<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
    </div>
    <div class="health-page">

        <!-- 健康信息卡片 -->
        <div class="health-info">
            <el-card class="basic-info">
                <template #header>
                    <div class="card-header">
                        <span>老人基本信息</span>
                    </div>
                </template>
                <div class="info-content">
                    <div class="info-item">
                        <span class="label">姓名：</span>
                        <span class="value">{{ elderInfo.elderlyName }}</span>
                    </div>
                    <div class="info-item">
                        <span class="label">年龄：</span>
                        <span class="value">{{ elderInfo.elderlyAge }}岁</span>
                    </div>
                    <div class="info-item">
                        <span class="label">房间号：</span>
                        <span class="value">{{ elderInfo.bedroom }}</span>
                    </div>
                </div>
            </el-card>

            <el-card class="vital-signs">
                <template #header>
                    <div class="card-header">
                        <span>生命体征</span>
                        <el-tag size="small" type="info">{{ formatTime }}</el-tag>
                    </div>
                </template>
                <div class="signs-grid">
                    <div class="sign-item">
                        <i class="iconfont icon-heart"></i>
                        <div class="sign-value">{{ vitalSigns.heartRate }}</div>
                        <div class="sign-label">心率 (次/分)</div>
                    </div>
                    <div class="sign-item">
                        <i class="iconfont icon-blood-pressure"></i>
                        <div class="sign-value">{{ vitalSigns.bloodPressure }}</div>
                        <div class="sign-label">血压 (mmHg)</div>
                    </div>
                    <div class="sign-item">
                        <i class="iconfont icon-temperature"></i>
                        <div class="sign-value">{{ vitalSigns.temperature }}</div>
                        <div class="sign-label">体温 (℃)</div>
                    </div>
                    <div class="sign-item">
                        <i class="iconfont icon-oxygen"></i>
                        <div class="sign-value">{{ vitalSigns.oxygen }}</div>
                        <div class="sign-label">血氧 (%)</div>
                    </div>
                </div>
            </el-card>

            <el-card class="health-records">
                <template #header>
                    <div class="card-header">
                        <span>健康记录</span>
                        <span class="view-more" @click="viewMore">查看更多</span> <!-- 新增：查看更多链接 -->
                    </div>
                </template>
                <el-timeline>
                    <el-timeline-item v-if="formattedHealthRecords.length === 0">
                        暂无健康记录
                    </el-timeline-item>
                    <el-timeline-item v-for="(record, index) in formattedHealthRecords" :key="index"
                        :timestamp="record.time" :type="record.type" v-else>
                        {{ record.content }}
                    </el-timeline-item>
                </el-timeline>
            </el-card>
        </div>
    </div>
    <div class="footer-text">
            要养老，上养老生活 ®
        </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import dayjs from 'dayjs';
import axios from '@/api/request';
import { useRoute, useRouter } from 'vue-router';
import { useHealthRecordStore } from '@/stores/healthRecordStore.js'
let route = useRoute()
let router = useRouter()
const routeEid = route.params.eid

const fetchData = () => {
    axios.get(`/eHealth/getHealthDetail/${routeEid}`, {
    }).then(function (response) {
        console.log(response);
        elderInfo.value = response.data.data.elderly
        vitalSigns.value = response.data.data.elderlyhealth
        healthRecords.value = response.data.data.healthrecordList;
        displayedRecords.value = healthRecords.value.slice(-3); // 只保留最新的3条记录
    }).catch(function (error) {
        console.log(error);
    })
}
//时间戳转换为YYYY-MM-DD HH:mm
const formattedHealthRecords = computed(() => {
    return displayedRecords.value.map(record => {
        const newRecord = { ...record };
        if (newRecord.time) {
            newRecord.time = dayjs(newRecord.time).format('YYYY-MM-DD HH:mm');
        }
        return newRecord;
    });
});
// 老人基本信息
const elderInfo = ref({});
// 生命体征数据
const vitalSigns = ref({});

// 健康记录
const healthRecords = ref([{}]);
const displayedRecords = ref([{}]);

// 格式化当前时间
const formatTime = computed(() => {
    return dayjs().format('YYYY-MM-DD HH:mm');
});
const healthRecordStore = useHealthRecordStore();
const viewMore = () => {
    healthRecordStore.setHealthRecords(healthRecords.value);
    router.push({ name: 'HealthRecordMore_app' })
};
onMounted(() => {
    fetchData()
})
</script>

<style scoped>
.health-page {
    padding: 20px;
    padding-bottom: 60px;
    padding-top: 0;
    background-image: url(@/assets/images/home_app_background.jpg);
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    min-height: 80vh;
}

.header {
    display: flex;
    align-items: center;
    /* margin-bottom: 10px; */
    position: sticky;
    top: 0;
    z-index: 10;
    background-image: url(@/assets/images/home_app_background.jpg);
    padding: 20px;
    padding-bottom: 10px;

}

.back-arrow {
    font-size: 24px;
    cursor: pointer;
}

.view-more {
    margin-left: auto;
    color: #c7c5c5;
    cursor: pointer;
    font-size: 12px;
}

.health-info {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: bold;
}

.info-content {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.info-item {
    display: flex;
    align-items: center;
}

.info-item .label {
    width: 80px;
    color: #666;
}

.info-item .value {
    color: #333;
    font-weight: 500;
}

.signs-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
    padding: 10px;
}

.sign-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 15px;
    background-color: #f8f9fa;
    border-radius: 8px;
}

.sign-item i {
    font-size: 24px;
    color: #409EFF;
    margin-bottom: 8px;
}

.sign-value {
    font-size: 20px;
    font-weight: bold;
    color: #333;
    margin: 5px 0;
}

.sign-label {
    font-size: 12px;
    color: #666;
}

:deep(.el-timeline-item__content) {
    color: #333;
}

:deep(.el-card) {
    border-radius: 8px;
}

:deep(.el-card__header) {
    padding: 15px 20px;
    border-bottom: 1px solid #ebeef5;
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