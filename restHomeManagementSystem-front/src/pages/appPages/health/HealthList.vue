<template>
    <div class="elder-list-page">
        <div class="header">
            <h2>我的关注</h2>
            <el-input v-model="searchKey" placeholder="搜索老人姓名" prefix-icon="Search" clearable @clear="handleSearch"
                @input="handleSearch" />
        </div>

        <div class="elder-cards">
            <el-card v-for="elder in filteredElders" :key="elder.hid" class="elder-card"
                @click="goToHealthDetail(elder.eid)">
                <div class="elder-info">
                    <el-avatar :size="60" :src="`http://localhost:8999/images/upload/elderlyImages/${elder.image}`" />
                    <div class="info-content">
                        <div class="name-age">
                            <span class="name">{{ elder.elderlyName }}</span>
                            <span class="age">{{ elder.elderlyAge }}岁</span>
                        </div>
                        <div class="room">房间号：{{ elder.bedroom }}</div>
                        <div class="status-tags">
                            <el-tag :type="elder.status === '正常' ? 'success' : 'warning'" size="small">
                                {{ elder.status }}
                            </el-tag>
                            <!-- <el-tag v-if="elder.needCare" type="danger" size="small">
                                需要特别关注
                            </el-tag> -->
                        </div>
                    </div>
                    <el-icon class="arrow-icon">
                        <ArrowRight />
                    </el-icon>
                </div>
                <div class="vital-signs">
                    <div class="sign-item">
                        <span class="label">心率</span>
                        <span class="value">{{ elder.heartRate }}次/分</span>
                    </div>
                    <div class="sign-item">
                        <span class="label">血压</span>
                        <span class="value">{{ elder.bloodPressure }}</span>
                    </div>
                    <div class="sign-item">
                        <span class="label">体温</span>
                        <span class="value">{{ elder.temperature }}℃</span>
                    </div>
                </div>
            </el-card>
        </div>

        <Footer :menuType="menuType" />
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ArrowRight, Search } from '@element-plus/icons-vue';
import Footer from '@/pages/appPages/components/Footer.vue';
import axios from '@/api/request.js'
import { definedUser } from '@/stores/index.js';
let loginUser = definedUser()
const router = useRouter();
const menuType = ref('health');
const elders = ref([])

const searchKey = ref('');
const fetchData = () => {
    axios.get(`/eHealth/getHealthList/${loginUser.uid}`, {
    }).then(function (response) {
        console.log(response);
        elders.value = response.data.data
    }).catch(function (error) {
        console.log(error);
    })
}



// 搜索过滤
const filteredElders = computed(() => {
    if (!searchKey.value) return elders.value;
    return elders.value.filter(elder =>
        elder.elderlyName.includes(searchKey.value) ||
        elder.bedroom.includes(searchKey.value)
    );
});

// 搜索处理
const handleSearch = () => {
    // 可以添加额外的搜索逻辑
};

// 跳转到健康详情页
const goToHealthDetail = (elderId) => {
    router.push({
        name: 'HealthDetail_app',
        params: { eid: elderId }
    });
};
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
}

.room {
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
    justify-content: space-around;
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
</style>
