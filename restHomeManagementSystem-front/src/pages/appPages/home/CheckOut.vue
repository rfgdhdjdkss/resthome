<template>
    <div class="elder-list-page">
        <div class="header">
            <span class="back-arrow" @click="router.go(-1)">←</span>

            <h2>退住</h2>
            <el-input v-model="searchKey" placeholder="搜索老人姓名" prefix-icon="Search" clearable @clear="handleSearch"
                @input="handleSearch" />
        </div>

        <div class="elder-cards">
            <h3 v-if="elders.length!=0">已签约老人</h3>
            <h3 v-else>暂无入住老人</h3>
            <el-card v-for="elder in filteredElders.isCheckined" :key="elder.hid" class="elder-card">
                <div class="elder-info">
                    <el-avatar :size="60" :src="elder.avatar" />
                    <div class="info-content">
                        <div class="name-age">
                            <span class="name">{{ elder.elderlyName }}</span>
                            <span class="age">{{ elder.elderlyAge }}岁</span>
                            <span class="name">{{ elder.elderlySex }}</span>
                        </div>
                        <div class="status-tags">
                            <el-tag type="success" size="small">已入住</el-tag>
                        </div>
                    </div>

                    <div>
                        <button
                            style="background-color: #d79d9b;border: none; padding: 5px; font-size: 12px; color: #fff;"
                            @click="confirmCheckOut(elder.eid)">
                            退住申请
                        </button>
                    </div>
                </div>
                <div class="vital-signs">
                    <div class="sign-item">
                        <span class="label">家庭住址</span>
                        <span class="value">{{ elder.elderlyAddress }}</span>
                    </div>
                    <div class="sign-item">
                        <span class="label">家属</span>
                        <span class="value">{{ elder.bookerName }}</span>
                    </div>
                    <div class="sign-item">
                        <span class="label">余额</span>
                        <span class="value">¥{{ elder.balance }}</span>
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
import { ElMessageBox } from 'element-plus';
let loginUser = definedUser()
const router = useRouter();
const elders = ref([])

const searchKey = ref('');
const fetchData = () => {
    axios.get(`/elderly/selectIsCheckinElderlyByUid/${loginUser.uid}`, {
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
    const isCheckined = elders.value.filter(elder => elder.isCheckined);
    return { isCheckined };
});
// 封装格式化预定时间
function formatBookerTime(time) {
    const bookerTime = new Date(time);
    return bookerTime.getFullYear() + "-" + bookerTime.getMonth() + "-" + bookerTime.getDate()
}
// 搜索处理
const handleSearch = () => {
    // 可以添加额外的搜索逻辑
    axios.get("/elderly/selectForSearch", {
        params: {
            //搜索框的值
            searchValue: searchKey.value
        }
    }).then(response => {
        elders.value = response.data.data.map(item => ({
            ...item,
            bookerTime: formatBookerTime(new Date(item.bookerTime)),
            elderlySex: item.elderlySex === 'male' ? '男' : '女',
            isReserved: item.isReserved === 0 ? false : true,
            elderlyAge: calcAge(item.elderlyIdCard)
        }))

    }).catch(error => {
        console.error('查询失败:', error);
    })
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

const confirmCheckOut = async (eid) => {
    try {
        await ElMessageBox.confirm(
            '你确定要提交退住申请吗？',
            '提示',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }
        );
        // 用户点击了确定，执行退住申请的逻辑
        console.log('用户已确认退住申请');
        // 这里可以添加实际的退住申请 API 请求代码
        const response = await axios.put(`/elderly/checkOut/${eid}`)
        if (response.data.code == 200) {
            fetchData()
            ElMessage.success('退住申请成功，等待审核')

        }
    } catch (error) {
        // 用户点击了取消
        console.log('用户取消了退住申请');
    }
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
