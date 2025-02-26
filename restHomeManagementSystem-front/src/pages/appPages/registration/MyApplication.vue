<template>
    <div class="elder-list-page">

        <div class="header">
            <span class="back-arrow" @click="router.go(-1)">←</span>

            <h2>我的申请</h2>

        </div>
        <h3>来访申请</h3>
        <div v-for="application in effectiveVisitingApplications" class="application-card">
            <div class="application-info">
                <p><strong class="mar-right">老人姓名:</strong> {{ application.name }}</p>
                <p><strong class="mar-right">联系电话:</strong> {{ application.phone }}</p>
                <p><strong class="mar-right">申请人:</strong> {{ application.bookerName }}</p>
                <p><strong class="mar-right">时间:</strong> {{ application.time }}</p>
                <p><strong class="mar-right">申请类型:</strong>
                    <el-tag :type="application.type === '来访登记' ? 'info' : 'primary'">{{ application.type }}</el-tag>
                </p>
                <p><strong class="mar-right">是否有效:</strong>
                    <el-tag round :type="application.isEffective ? 'success' : 'info'">
                        {{ application.isEffective ? '有效' : '失效' }}
                    </el-tag>
                </p>
            </div>

        </div>
        <div class="elder-cards">
            <h3>外出登记</h3>
            <div v-for="application in effectiveOutgoingApplications" class="application-card">
                <div class="application-info">
                    <p><strong class="mar-right">老人姓名:</strong> {{ application.name }}</p>
                    <p><strong class="mar-right">联系电话:</strong> {{ application.phone }}</p>
                    <p><strong class="mar-right">申请人:</strong> {{ application.bookerName }}</p>
                    <p><strong class="mar-right">时间:</strong> {{ application.time }}</p>
                    <p><strong class="mar-right">申请类型:</strong>
                        <el-tag :type="application.type === '外出申请' ? 'warning' : 'primary'">{{ application.type
                            }}</el-tag>
                    </p>
                    <p><strong class="mar-right">是否有效:</strong>
                        <el-tag round :type="application.isEffective ? 'success' : 'info'">
                            {{ application.isEffective ? '有效' : '失效' }}
                        </el-tag>
                    </p>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-text">
        要养老，上养老生活 ®
    </div>
</template>

<script lang="ts" setup>
import { computed, ref, onMounted } from 'vue';
import axios from '../../../api/request.js';
import { definedUser } from '../../../stores/index.js';
import { useRouter } from 'vue-router';

let loginUser = definedUser();
const router = useRouter();

interface Application {
    id: number;
    name: string;
    phone: string;
    bookerName: string;
    time: string;
    type: string;
}

const tableData = ref<Application[]>([]);

const fetchData = () => {
    axios.get(`/application/selectAllApplicationByUid/${loginUser.uid}`)
        .then(response => {
            console.log(response);
            tableData.value = response.data.data;
        })
        .catch(error => {
            console.error('查询失败:', error);
        });
};

const currentDate = new Date();
const year = currentDate.getFullYear();
const month = currentDate.getMonth() + 1;
const day = currentDate.getDate();
const currentDateStr = `${year}-${month}-${day}`;

const sortedTableData = computed(() => {
    const outgoing = tableData.value.filter(app => app.type === '外出申请');
    const visiting = tableData.value.filter(app => app.type === '来访申请');

    // 处理外出申请数据的有效性判断
    const processedOutgoing = outgoing.map(app => {
        // 提取时间段的起始日期
        const startDateStr = app.time.split(' -- ')[0];
        const appDate = new Date(startDateStr);
        const current = new Date(currentDateStr);
        return {
            ...app,
            isEffective: appDate > current
        };
    });

    // 处理来访申请数据的有效性判断
    const processedVisiting = visiting.map(app => {
        const appDate = new Date(app.time);
        const current = new Date(currentDateStr);
        return {
            ...app,
            isEffective: appDate > current
        };
    });

    return {
        outgoing: processedOutgoing,
        visiting: processedVisiting
    };
});
const effectiveVisitingApplications = computed(() => {
    return sortedTableData.value.visiting.filter(app => app.isEffective);
});
const effectiveOutgoingApplications = computed(() => {
    return sortedTableData.value.outgoing.filter(app => app.isEffective);
});
const viewHistory = () => {
    router.push('/history');
};

onMounted(() => {
    fetchData();
});
</script>

<style scoped>
.back-arrow {
    font-size: 24px;
    cursor: pointer;
}

.header {
    margin-bottom: 20px;
    position: relative;
}

.header h2 {
    color: #303133;
    margin-bottom: 0;
    left: 16px;

}

.elder-list-page {
    padding: 20px;
    padding-bottom: 60px;
    background-image: url(../../../assets/images/home_app_background.jpg);
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    min-height: 80vh;
}


.elder-cards {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

h2 {
    margin-bottom: 20px;
    font-size: 24px;
    color: #000;
}

h3 {
    margin-top: 20px;
    font-size: 20px;
    color: #000;
}

.application-card {
    background-color: #f9f9f9;
    border-radius: 8px;
    padding: 15px;
    margin-bottom: 15px;
    width: 94%;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s;
}

.application-card:hover {
    transform: scale(1.02);
}

.application-info {
    font-size: 14px;
    color: #333;
}

.application-info p {
    margin: 5px 0;
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

.mar-right {
    margin-right: 20px;
}
</style>