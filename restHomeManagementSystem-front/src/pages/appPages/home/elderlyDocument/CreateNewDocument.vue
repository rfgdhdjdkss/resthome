<template>
    <div class="header">
        <span class="back-arrow" @click="router.go(-1)">←</span>
        <h4>创建档案</h4>
    </div>
    <div class="add-elderly-page">

        <form class="elderly-form">
            <!-- 姓名 -->
            <div class="form-item">
                <div style="width: 120px;">
                    <span>姓 名</span>
                </div>
                <input type="text" v-model="elderlyForm.elderlyName" placeholder="请填写老人姓名" id="elderlyName">
            </div>
            <!-- 性别 -->
            <div class="form-item">
                <div style="width: 120px;">
                    <span>性 别</span>
                </div>
                <div style="display: flex;">
                    <div style="display: flex; align-items: center;">
                        <div style="display: flex;align-items: center; justify-content: center;">
                            <input type="radio" id="male" value="male" v-model="elderlyForm.elderlySex">
                        </div>
                        <div style="height: 19px;">
                            <span>男性</span>

                        </div>
                    </div>
                    <div style="display: flex; align-items: center;">

                        <input type="radio" id="female" value="female" v-model="elderlyForm.elderlySex">
                        <div style="height: 19px;">

                            <span>女性</span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 出生年月 -->
            <div class="form-item">
                <div style="width: 120px;">
                    <span>出生年月</span>
                </div>
                <input type="date" v-model="elderlyForm.elderlyBirth" aria-label="请选择出生年月" placeholder="请选择出生年月"
                    id="birth">
            </div>
            <!-- 身份证号码 -->
            <div class="form-item">
                <div style="width: 120px;">
                    <span>身份证号码</span>
                </div>
                <input type="text" v-model="elderlyForm.elderlyIdCard" placeholder="请填写身份证号码" id="idCard">
            </div>
            <!-- 联系电话 -->
            <div class="form-item">
                <div style="width: 120px;">
                    <span>手机号码</span>
                </div>
                <input type="text" v-model="elderlyForm.elderlyPhone" placeholder="请填写老人手机号码" id="elderlyPhone">
            </div>
            <!-- 家庭地址 -->
            <div class="form-item">
                <div style="width: 120px;">
                    <span>家庭地址</span>
                </div>
                <input type="text" v-model="elderlyForm.elderlyAddress" placeholder="请填写家庭地址" id="address">
            </div>
            <!-- 健康状况 -->
            <div class="form-item">
                <div style="width: 120px;">
                    <span>健康描述</span>
                </div>
                <el-select v-model="elderlyForm.isHealth" placeholder="请选择健康状况">
                    <el-option label="健康" value="healthy" />
                    <el-option label="有疾病史或有其他健康问题" value="subHealthy" />
                </el-select>
            </div>

            <!-- 关系标签 -->
            <div class="form-item">
                <div style="width: 120px;">
                    <span>关系标签</span>
                </div>
                <div class="tag-group">
                    <span v-for="(tag, index) in relationshipTags" :key="index" class="tag"
                        :class="{ 'active': selectedRelationship === tag }" @click="selectRelationship(tag)">{{ tag
                        }}</span>
                </div>
            </div>
            <div>
                <button type="submit" class="submit-button" @click="saveElderly">保存档案</button>
            </div>
        </form>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from '@/api/request';
import { definedUser } from '@/stores';
import { ElMessage } from 'element-plus';

const router = useRouter();
let loginUser = definedUser();
const elderlyForm = ref({
    elderlyName: '',
    elderlySex: '',
    elderlyIdCard: '',
    isHealth: '',
    isCheckined: 0,
    elderlyBirth: '',
    elderlyPhone: '',
    elderlyAddress: '',
    relationship: ''
});
const relationshipTags = ['自己', '父母', '长辈', '老伴', '老友', '其它'];
const selectedRelationship = ref('');

// 验证身份证号码格式
const validateIdCard = (idCard) => {
    const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    return reg.test(idCard);
};

// 验证手机号码格式
const validatePhone = (phone) => {
    const reg = /^1[3-9]\d{9}$/;
    return reg.test(phone);
};

const saveElderly = async () => {
    elderlyForm.value.relationship = selectedRelationship.value;

    // 验证姓名
    if (!elderlyForm.value.elderlyName) {
        ElMessage.error('姓名不能为空');
        return;
    }

    // 验证性别
    if (!elderlyForm.value.elderlySex) {
        ElMessage.error('请选择性别');
        return;
    }

    // 验证出生年月
    if (!elderlyForm.value.elderlyBirth) {
        ElMessage.error('请选择出生年月');
        return;
    }

    // 验证身份证号码
    if (!elderlyForm.value.elderlyIdCard) {
        ElMessage.error('身份证号码不能为空');
        return;
    }
    if (!validateIdCard(elderlyForm.value.elderlyIdCard)) {
        ElMessage.error('身份证号码格式不正确');
        return;
    }

    // 验证手机号码
    if (!elderlyForm.value.elderlyPhone) {
        ElMessage.error('手机号码不能为空');
        return;
    }
    if (!validatePhone(elderlyForm.value.elderlyPhone)) {
        ElMessage.error('手机号码格式不正确');
        return;
    }

    // 验证家庭地址
    if (!elderlyForm.value.elderlyAddress) {
        ElMessage.error('家庭地址不能为空');
        return;
    }

    // 验证健康状况
    if (!elderlyForm.value.isHealth) {
        ElMessage.error('请选择健康状况');
        return;
    }

    // 验证关系标签
    if (!selectedRelationship.value) {
        ElMessage.error('请选择关系标签');
        return;
    }

    try {
        const response = await axios.post(`/elderly/addElderlyByUid/${loginUser.uid}`, elderlyForm.value);

        if (response.data.code === 200) {
            ElMessage.success('创建成功');
            router.push({ name: 'ElderlyDocumentList_app' });
        } else {
            ElMessage.error('创建失败');
        }
    } catch (error) {
        console.error('保存出错:', error);
        ElMessage.error('保存出错，请稍后重试');
    }
};

const selectRelationship = (tag) => {
    selectedRelationship.value = tag;
};
</script>

<style scoped>
.add-elderly-page {
    padding: 16px;
    background-color: #fff;

    min-height: 91vh;
    padding-top: 70px;
}

.header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    justify-content: center;
    display: flex;
    align-items: center;
    z-index: 10;
    color: #000;
    height: 55px;
    background-color: #f5f5f5;
    border-bottom: 1px solid #ebeef5;
    background-image: url(@/assets/images/home_app_background.jpg);

}

.back-arrow {
    position: absolute;
    left: 15px;
    margin: 10px;
    top: 10px;
}

.header h4 {
    margin: 0;
    font-size: 15px;
}

.elderly-form {
    background-color: #fff;
}

.form-item {
    margin-bottom: 20px;
    display: flex;
    align-items: center;
    padding-bottom: 10px;
    border-bottom: 1px solid #ebeef5
}

.form-item label {
    display: block;
    margin-bottom: 5px;
    font-weight: 500;
    color: #333;

}

.form-item input[type="text"],
.form-item input[type="date"] {
    width: 85%;
    border-radius: 4px;
    padding: 10px;
    box-sizing: border-box;
    border: none;
}

.form-item input[type="radio"] {
    margin-right: 5px;
}

.tag-group {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 5px;
    width: 300px;
}

.tag {
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 6px 12px;
    cursor: pointer;
    color: #333;
}

.tag:hover {
    background-color: #409EFF;
    border-color: #409EFF;
    color: #fff;
}

input::placeholder {
    color: #999;
    font-size: 14px;
}

.submit-button {
    width: 100%;
    padding: 12px;
    font-size: 16px;
    background-color: #00bfa5;
    border: none;
    border-radius: 4px;
    color: white;
    cursor: pointer;
}
</style>