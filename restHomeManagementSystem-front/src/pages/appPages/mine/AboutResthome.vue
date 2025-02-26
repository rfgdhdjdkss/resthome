<template>
    <div class="about-resthome-page">
        <div class="header">
            <span class="back-arrow" @click="router.go(-1)">←</span>
            <h4>关于养老生活</h4>
        </div>
        <div class="logo-item">
            <div class="logo-img-div">
                <img src="@/assets/images/logo.jpg" alt="" width="140" height="140" style="border-radius: 20px;">
            </div>
            <div class="logo-version">
                <span>版本 v1.9.0</span>
            </div>
        </div>
        <div class="profile-item">
            <div class="p-i">
                <span>检查更新</span>
                <div class="item-container">
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="p-i" style="border-top: 1px solid #ebeef5;border-bottom: 1px solid #ebeef5;">
                <span>服务条款</span>
                <div class="item-container">
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
            <div class="p-i" @click="gotoModifyPasswordPage">
                <span>隐私政策</span>
                <div class="item-container">
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
        </div>
        <div class="about-us-item">
            <div class="p-i" style="height: 40px;">
                <span>关于我们</span>
                <div class="item-container">
                    <el-icon>
                        <ArrowRight />
                    </el-icon>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-text">
        要养老，上养老生活 ®
    </div>
</template>
<script setup>
import { useRouter } from 'vue-router';
import { ArrowRight } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
let router = useRouter()
</script>



<style scoped>
.about-resthome-page {
    padding: 16px;
    background-color: #f5f5f5;
    min-height: 100vh;
}

.header {
    display: flex;
    align-items: center;
    gap: 140px;
    margin-bottom: 20px;
    margin-top: 20px;
}

.header h4 {
    padding: 0;
    margin: 0;
}

.logo-item {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-top: 70px;
    gap: 15px;
}

.logo-version {
    font-size: 14px;
    color: #535353;
}

.profile-item {
    background-color: white;
    padding: 16px;
    border-radius: 20px;
    margin-bottom: 50px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    color: #38475c;
    font-size: 14px;
    margin-top: 50px;
}

.p-i {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: space-between;

}

.item-container {
    display: flex;
    align-items: center;

}

.item-info {
    margin-right: 8px;

}

.about-us-item {
    background-color: white;
    padding: 16px;
    border-radius: 20px;
    margin-bottom: 50px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
    color: #38475c;
    font-size: 14px;
    margin-top: 50px;
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