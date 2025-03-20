<template>
    <Header-web></Header-web>
    <Menu-web></Menu-web>
    <Footer-web></Footer-web>
    <div id="box">
        <!-- 添加新增餐品按钮 -->
        <div>
            <el-button type="primary" @click="openAddDialog">新增餐品</el-button>
        </div>
        <div>
            <el-table :data="filterTableData" style="width: 100%" size="large" height="800">
                <el-table-column label="餐品名称" prop="dishName" />
                <el-table-column label="餐品图片">
                    <template #default="scope">
                        <div
                            style="width: 100px; height: 100px; background-color: #f0f0f0; display: flex; align-items: center;justify-content: center;">
                            <img :src="`http://localhost:8999/images/upload/addNewDish/dishImg/${scope.row.dishImg}`"
                                alt="餐品图片" style="width: 80px; height: 80px;">
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="餐品描述" prop="description" />
                <el-table-column label="餐品价格" prop="dishPrice" />
                <el-table-column label="餐品库存" prop="dishQuantity" />
                <el-table-column label="类别" prop="tab" />
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" size="default" placeholder="搜索" />
                    </template>
                    <template #default="scope">
                        <el-button size="default" @click="editInfo(scope.$index, scope.row)">
                            编辑
                        </el-button>
                        <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled"
                            icon-color="#626AEF" title="您确定要删除这条记录吗?" @confirm="confirmEvent(scope.$index, scope.row)"
                            @cancel="ElMessage({
                                message: '取消删除，未成功删除数据',
                                type: 'warning',
                            })">
                            <template #reference>
                                <el-button type="danger">删除</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
            <div id="pageChangeDiv">
                <div id="countDiv">
                    <span>共{{ pageInfo.total }}条</span>
                </div>
                <div id="countDiv">
                    <span>共{{ pageInfo.pages }}页</span>
                </div>
                <el-dropdown trigger="click" id="sizeDiv">
                    <div>
                        <span>{{ pageInfo.pageSize }}条/页</span>
                        <el-icon>
                            <ArrowDownBold />
                        </el-icon>
                    </div>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click="handlePageSizeChange(7)">7条/页</el-dropdown-item>
                            <el-dropdown-item @click="handlePageSizeChange(10)">10条/页</el-dropdown-item>
                            <el-dropdown-item @click="handlePageSizeChange(12)">12条/页</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
                <div id="changePageDiv">
                    <el-icon style="cursor: pointer;" @click="handlePageChange(pageInfo.currentPage - 1)">
                        <ArrowLeftBold />
                    </el-icon>
                    <div id="pageDiv">
                        <span v-for="page in pageNumbers" :key="page"
                            :class="['pageNum', { active: page === pageInfo.currentPage }]"
                            @click="handlePageChange(page)">
                            {{ page }}
                        </span>
                    </div>
                    <el-icon style="cursor: pointer;" @click="handlePageChange(pageInfo.currentPage + 1)">
                        <ArrowRightBold />
                    </el-icon>
                </div>
                <div id="gotoPageNumDiv">
                    <div>
                        <span class="pageNumDiv_span">前往</span>
                    </div>
                    <div style="margin-left: 5px; margin-right: 5px;">
                        <el-input id="inputDiv" v-model="gotoPageNum" />
                    </div>
                    <div>
                        <span class="pageNumDiv_span">页</span>
                    </div>
                </div>
            </div>
        </div>


        <el-dialog v-model="dialogFormVisible" title="编辑餐品信息" width="500">
            <el-form :model="form">
                <el-form-item label="餐品名称" :label-width="formLabelWidth">
                    <el-input v-model="form.dishName" autocomplete="off" />
                </el-form-item>
                <el-form-item label="餐品图片" :label-width="formLabelWidth">
                    <el-upload class="avatar-uploader" :action="getActionUrl(form.dishId)" :show-file-list="false"
                        :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="headImgUrl" :src="headImgUrl" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                        <div class="changeHead">修改餐品图片</div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="餐品描述" :label-width="formLabelWidth">
                    <el-input v-model="form.description" autocomplete="off" />
                </el-form-item>
                <el-form-item label="餐品价格" :label-width="formLabelWidth">
                    <el-input v-model="form.dishPrice" autocomplete="off" />
                </el-form-item>
                <el-form-item label="餐品库存" :label-width="formLabelWidth">
                    <el-input v-model="form.dishQuantity" autocomplete="off" />
                </el-form-item>
                <el-form-item label="类别" :label-width="formLabelWidth">
                    <el-select v-model="form.tab" placeholder="餐品类别">
                        <el-option label="主食" value="主食" />
                        <el-option label="荤菜" value="荤菜" />
                        <el-option label="素菜" value="素菜" />
                        <el-option label="汤品" value="汤品" />
                        <el-option label="特色小吃" value="特色小吃" />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="doNotSave">返回</el-button>
                    <el-button type="primary" @click="save">
                        保存
                    </el-button>
                </div>
            </template>
        </el-dialog>
        <el-dialog v-model="addDialogFormVisible" title="新增餐品信息" width="500">
            <el-form :model="form">
                <el-form-item label="餐品名称" :label-width="formLabelWidth">
                    <el-input v-model="form.dishName" autocomplete="off" />
                </el-form-item>
                <el-form-item label="餐品图片" :label-width="formLabelWidth">
                    <el-upload class="avatar-uploader" :action="`http://localhost:8999/files/upload/addNewDish`"
                        :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="form.dishImg"
                            :src="`http://localhost:8999/images/upload/addNewDish/dishImg/${form.dishImg}`"
                            class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                        <div class="changeHead">修改餐品图片</div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="餐品描述" :label-width="formLabelWidth">
                    <el-input v-model="form.description" autocomplete="off" />
                </el-form-item>
                <el-form-item label="餐品价格" :label-width="formLabelWidth">
                    <el-input v-model="form.dishPrice" autocomplete="off" />
                </el-form-item>
                <el-form-item label="餐品库存" :label-width="formLabelWidth">
                    <el-input v-model="form.dishQuantity" autocomplete="off" />
                </el-form-item>
                <el-form-item label="类别" :label-width="formLabelWidth">
                    <el-select v-model="form.tab" placeholder="餐品类别">
                        <el-option label="主食" value="主食" />
                        <el-option label="荤菜" value="荤菜" />
                        <el-option label="素菜" value="素菜" />
                        <el-option label="汤品" value="汤品" />
                        <el-option label="特色小吃" value="特色小吃" />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="doNotSave">返回</el-button>
                    <el-button type="primary" @click="addDishes">
                        保存
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import axios from '@/api/request.js';
import { computed, ref, onMounted, reactive, watch } from 'vue';
import { ArrowDownBold, ArrowLeftBold, ArrowRightBold, InfoFilled, User, Plus } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';

// pageInfo 接收服务器响应的分页属性
const pageInfo = reactive({
    currentPage: 1,
    pageSize: 10,
    total: 0,
    pages: 0,
    hasPrevious: false,
    hasNext: false,
});

// 表格的响应式数据
const filterTableData = ref<dishes[]>([]);

// 前往第几页的页数定义
const gotoPageNum = ref(1);

// 动态计算显示的页码
const pageNumbers = computed(() => {
    const pages = Number(pageInfo.pages);
    const current = pageInfo.currentPage;
    const result = [];

    // 添加当前页前两页（如果存在）
    if (current - 2 >= 1) {
        result.push(current - 2);
    }
    if (current - 1 >= 1) {
        result.push(current - 1);
    }

    // 添加当前页
    result.push(current);

    // 添加当前页后两页（如果存在）
    if (current + 1 <= pages) {
        result.push(current + 1);
    }
    if (current + 2 <= pages) {
        result.push(current + 2);
    }

    return result;
});

// 分页器点击事件
function handlePageChange(page: number) {
    if (page < 1 || page > Number(pageInfo.pages)) return;

    pageInfo.currentPage = page;
    if (search.value === '') {
        fetchData();
    } else {
        searchTableData(search.value);
    }
}

// 每页显示条数变更
function handlePageSizeChange(size: number) {
    pageInfo.pageSize = size;
    handlePageChange(1);
}

// 监听跳转页码变更
watch(gotoPageNum, (newPage) => {
    if (newPage >= 1 && newPage <= Number(pageInfo.pages)) {
        handlePageChange(newPage);
    }
});

// 封装向服务器请求所有餐品信息的事件
async function fetchData() {
    await axios.get('/dishes/selectAlldishes', {
        params: {
            // 当前页
            currentPage: pageInfo.currentPage,
            // 每页有几条数据
            pageSize: pageInfo.pageSize,
        },
    })
        .then((response) => {
            // 表格数据
            filterTableData.value = response.data.data.records;
            // 接收到的数据中共有几条数据
            pageInfo.total = response.data.data.total;
            // 当前为第几页
            pageInfo.currentPage = response.data.data.currentPage;
            // 接收到的数据中一页有几条数据
            pageInfo.pageSize = response.data.data.pageSize;
            // 接收到的数据中共有几页
            pageInfo.pages = response.data.data.pages;
            // 是否有前一页
            pageInfo.hasPrevious = response.data.data.hasPrevious;
            // 是否有后一页
            pageInfo.hasNext = response.data.data.hasNext;
            console.log(response);
        })
        .catch((error) => {
            console.log(error);
        });
}

const handleAvatarSuccess = (response) => {
    headImgUrl.value = `http://localhost:8999/images/upload/addNewDish/dishImg/${response}`;
    form.dishImg = response;
};

const beforeAvatarUpload = (rawFile) => {
    if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
        ElMessage.error('上传头像图片只能是 jpg 或者 png 格式');
        return false;
    } else if (rawFile.size / 1024 / 1024 > 10) {
        ElMessage.error('上传的头像图片大小不能超过 2MB!');
        return false;
    }
    return true;
};

const getActionUrl = (dishId) => {
    return `http://localhost:8999/files/upload/addNewDish/${dishId}`;
};

// 挂载时，第一次向服务器请求所有餐品信息
onMounted(() => {
    fetchData();
});

interface dishes {
    dishId: string;
    dishName: string;
    dishPrice: number;
    description: string;
    dishImg: string;
    dishQuantity: string;
    tab: string;
}

// 编辑事件
const handleEdit = (index: number, row: dishes) => {
    console.log(index, row);
};

// 删除事件
const handleDelete = async (index: number, row: dishes) => {
    const response = await axios.delete(`/dishes/deleteDishes/${row.dishId}`);

    if (response.data.code === 200) {
        // 服务器成功响应删除请求
        // 从本地数组中移除餐品
        filterTableData.value.splice(index, 1);
        fetchData();
        ElMessage.success('删除成功');
    } else {
        ElMessage.error('删除失败');
    }
};

// 点击删除后确认，触发事件，删除餐品
const confirmEvent = (index: number, row: dishes) => {
    handleDelete(index, row);
};

// 封装搜索框模糊查询事件
function searchTableData(searchValue) {
    axios.get('/dishes/selectForSearch', {
        params: {
            // 当前页
            currentPage: pageInfo.currentPage,
            // 页大小
            pageSize: pageInfo.pageSize,
            // 搜索框的值
            searchValue,
        },
    })
        .then((response) => {
            filterTableData.value = response.data.data.records;
            pageInfo.total = response.data.data.total;
            pageInfo.currentPage = response.data.data.currentPage;
            pageInfo.pageSize = response.data.data.pageSize;
            pageInfo.pages = response.data.data.pages;
            pageInfo.hasPrevious = response.data.data.hasPrevious;
            pageInfo.hasNext = response.data.data.hasNext;
        })
        .catch((error) => {
            console.error('查询失败:', error);
        });
}

// 与搜索框进行双向数据绑定
const search = ref('');
// 监听搜索框是否有输入
watch(search, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        searchTableData(newVal);
    }
});

// 是否显示编辑框
const dialogFormVisible = ref(false);
const formLabelWidth = '140px';
// 存储编辑框内的响应式数据
const form = reactive({
    dishId: '',
    dishName: '',
    dishPrice: '',
    description: '',
    dishImg: '',
    dishQuantity: '',
    tab: '',
});
// 编辑事件
const headImgUrl = ref('');

function editInfo(index: number, row: dishes) {
    form.dishId = row.dishId;
    form.dishName = row.dishName;
    form.dishPrice = row.dishPrice;
    form.description = row.description;
    form.dishImg = row.dishImg;
    form.dishQuantity = row.dishQuantity;
    form.tab = row.tab;
    headImgUrl.value = `http://localhost:8999/images/upload/addNewDish/dishImg/${form.dishImg}`;

    dialogFormVisible.value = true;
}
const addDialogFormVisible = ref(false)
// 打开新增餐品对话框
function openAddDialog() {
    form.dishId = '',
        form.dishName = '',
        form.dishPrice = '',
        form.description = '',
        form.dishImg = '',
        form.dishQuantity = '',
        form.tab = '',
        addDialogFormVisible.value = true;
}

// 编辑状态下：不保存，返回
const doNotSave = () => {
    dialogFormVisible.value = false;
    ElMessage({
        message: '取消保存，未成功修改数据',
        type: 'warning',
    });
};

// 编辑状态下，保存，并向服务器发送请求更新数据库中的数据
const save = async () => {
    console.log(form.dishImg);

    // 编辑餐品
    const response = await axios.put('/dishes/updateDishes', {
        dishId: form.dishId,
        dishName: form.dishName,
        dishPrice: form.dishPrice,
        dishQuantity: form.dishQuantity,
        dishImg: form.dishImg,
        description: form.description,
        tab: form.tab,
    })
    if (response.data.code === 200) {
        fetchData()
        ElMessage.success('保存成功')
        dialogFormVisible.value = false
    } else {
        ElMessage.error('保存失败')
    }
}
const addDishes = async () => {
    // 新增餐品
    const response = await axios.post('/dishes/addDishes', {
        dishName: form.dishName,
        dishPrice: form.dishPrice,
        dishQuantity: form.dishQuantity,
        dishImg: form.dishImg,
        description: form.description,
        tab: form.tab,
    })
    if (response.data.code === 200) {
        fetchData()
        ElMessage.success('新增成功')
        addDialogFormVisible.value = false
    } else {
        ElMessage.error('新增失败')
    }
}
</script>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
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

#pageChangeDiv {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 20px;
}

.example-showcase .el-dropdown-link {
    cursor: pointer;
    color: var(--el-color-primary);
    display: flex;
    align-items: center;
}

#countDiv {
    height: 27px;
    display: flex;
    align-items: center;
}

#sizeDiv {
    height: 25px;
    border: 1px solid var(--el-border-color);
    width: 80px;
    display: flex;
    justify-content: space-around;
    align-items: center;
}

#changePageDiv {
    display: flex;
    align-items: center;
    gap: 10px;
}

.pageNum {
    font-size: var(--el-font-size-base);
    color: var(--el-text-color-regular);
    margin: 0 5px;
    cursor: pointer;
}

.pageNum.active {
    color: #409EFF;
}

#gotoPageNumDiv {
    display: flex;
    align-items: center;
    gap: 5px;
}

#inputDiv {
    width: 40px;
    text-align: center;
}

.pageNumDiv_span {
    font-size: var(--el-font-size-base);
    color: var(--el-text-color-regular);
}

.avatar-uploader .avatar {
    width: 100px;
    height: 100px;
    display: block;
}

.changeHead {
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 70%;
    padding: 5px 0;
    transform: translateX(-50%);
    text-align: center;
    color: #676767;
    opacity: 0;
    transition: opacity 0.3s ease;
    font-size: 11px;
    font-weight: 400;
    cursor: pointer;
}

.changeHead:hover {
    opacity: 0.7;
}

.avatar:hover+.changeHead {
    opacity: 0.7;
}
</style>