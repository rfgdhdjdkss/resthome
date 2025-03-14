<!-- 菜品管理 -->
<template>
    <Header-web></Header-web>
    <Menu-web></Menu-web>
    <Footer-web></Footer-web>
    <div id="box">
        <div style="margin-bottom: 10px;">
            <el-row>
                <el-col :span="24">
                    <el-button type="primary" @click="addDish">新增菜品</el-button>
                    <el-button type="danger" @click="selectAll">删除</el-button>
                </el-col>
            </el-row>
        </div>
        <el-table :data="dishesData" border style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="菜品图片">
                <template #default="scope">
                    <div
                        style="width: 100px; height: 100px; background-color: #f0f0f0; display: flex; align-items: center; justify-content: center;">
                        <img :src="`http://localhost:8999/images/upload/addNewDish/dishImg/${scope.row.dishImg}`"
                            alt="菜品图片" style="width: 80px; height: 80px;">
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="菜品名称" prop="dishName"></el-table-column>
            <el-table-column label="价格（元）" prop="dishPrice"></el-table-column>
            <el-table-column label="数量（份）" prop="dishQuantity"></el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button type="primary" size="small" @click="editDish(scope.row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="deleteDish(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <transition name="el-zoom-in-top">
            <el-dialog v-model="showAdd" title="新增菜品" width="500">
                <el-form :model="newDish">
                    <el-form-item label="菜品名称">
                        <el-input v-model="newDish.dishName" autocomplete="off" />
                    </el-form-item>
                    <el-form-item label="菜品图片">
                        <el-upload class="avatar-uploader" :action="`http://localhost:8999/files/upload/addNewDish`"
                            :show-file-list="false" :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload">
                            <el-icon v-if="newDish.dishImg" class="avatar-uploader-icon">
                                <img :src="`http://localhost:8999/images/upload/addNewDish/dishImg/${newDish.dishImg}`"
                                    class="avatar" />
                            </el-icon>
                            <el-icon v-else class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="菜品价格">
                        <el-input v-model="newDish.dishPrice" autocomplete="off" />
                    </el-form-item>
                    <el-form-item label="菜品数量">
                        <el-input v-model="newDish.dishQuantity" autocomplete="off" />
                    </el-form-item>
                </el-form>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="doNotSave">返回</el-button>
                        <el-button type="primary" @click="doAddNewDishes">添加</el-button>
                    </div>
                </template>
            </el-dialog>
        </transition>
    </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue';
import { Plus } from '@element-plus/icons-vue';

import type { UploadProps } from 'element-plus';
const showAdd = ref(false)
const dishesData = reactive<Dishes[]>([])
interface Dishes {
    dishId: number,
    dishImg: string,
    dishName: string,
    dishQuantity: number,
    dishPrice: number,
}
const newDish = reactive({
    dishId: '',
    dishImg: '',
    dishName: '',
    dishQuantity: '',
    dishPrice: '',
})
const addDish = () => {
    showAdd.value = true;
    newDish.dishId = '';
    newDish.dishImg = '';
    newDish.dishName = '';
    newDish.dishQuantity = '';
    newDish.dishPrice = '';
}
const fetchData = () => {
    axios.get('/dishes/findAllDishes').then(response => {

        const convertedData: Dishes[] = response.data.data.map(item => ({
            ...item,
        }));
        dishesData.splice(0, dishesData.length, ...convertedData);

    }).catch(error => {
        console.log(error);
    });
}
const handleAvatarSuccess = (response) => {
    fetchData()

}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
    if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
        ElMessage.error('上传头像图片只能是 jpg 或者 png 格式')
        return false
    } else if (rawFile.size / 1024 / 1024 > 2) {
        ElMessage.error('上传的头像图片大小不能超过 2MB!')
        return false
    }
    return true
}
const doAddNewDishes = () => {
    axios.post('/dishes/addDish', newDish).then(response => {
        console.log(response);
        fetchData()

    }).catch(error => {
        console.log(error);
    });
}


onMounted(() => {
    fetchData()
})
</script>

<style scoped>
#box {
    display: flex;
    flex-direction: column;
    width: 82%;
    height: auto;
    float: right;
    position: relative;
    top: 80px;
    margin-right: 10px;
    z-index: 1;
    display: flex;
    justify-content: center;
    border-radius: 10px;

}

.avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>
<style>
.avatar-uploader .el-upload {
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
}
</style>
