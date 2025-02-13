<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
    </div>
    <div class="health-records-more-page">

        <el-card class="health-records">
            <template #header>
                <div class="card-header">
                    <span>健康记录</span>
                </div>
            </template>
            <el-timeline>
                <el-timeline-item v-if="healthRecords.length === 0">
                    暂无健康记录
                </el-timeline-item>
                <div v-else>
                    <el-timeline-item v-for="(record, index) in formattedHealthRecords" :key="index" :timestamp="record.time"
                        :type="record.type">
                        {{ record.content }}
                    </el-timeline-item>
                    暂无更多记录
                </div>

            </el-timeline>
        </el-card>

        <div class="footer-text">
            要养老，上养老生活 ®
        </div>
    </div>

</template>

<script setup>
import { onMounted, ref,computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import dayjs from 'dayjs';
import { useHealthRecordStore } from '@/stores/healthRecordStore.js'
const healthRecordStore = useHealthRecordStore();

let route = useRoute()
let router = useRouter()
const healthRecords = ref([]); // 健康记录数组
const formattedHealthRecords = computed(() => {
    return healthRecords.value.map(record => {
        const newRecord = { ...record };
        if (newRecord.time) {
            newRecord.time = dayjs(newRecord.time).format('YYYY-MM-DD HH:mm');
        }
        return newRecord;
    });
});
onMounted(() => {
    healthRecords.value=healthRecordStore.healthRecords
})
</script>


<style scoped>
.health-records-more-page {
    padding: 20px;
    padding-bottom: 60px;
    background-image: url(@/assets/images/home_app_background.jpg);
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    min-height: 80vh;
}

.health-records {
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.card-header {
    font-size: 18px;
    font-weight: bold;
    color: #333;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.el-timeline-item {
    padding: 10px 15px;
    border-radius: 5px;
    margin-bottom: 10px;
}

.el-timeline-item.info {
    background-color: #e7f3fe;
    border-left: 4px solid #2196F3;
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
