<template>
    <Header-web></Header-web>
    <Menu-web></Menu-web>
    <Footer-web></Footer-web>
    <div id="box">
        <el-table :data="tableData" style="width: 100%" size="large">
            <el-table-column label="老人编号" prop="eid" />
            <!-- 老人姓名列 -->
            <el-table-column label="老人姓名" prop="elderlyName" />

            <el-table-column label="床位号" prop="bedroom" />

            <el-table-column label="年龄" prop="elderlyAge" />
            <el-table-column label="心率(次/分)" prop="heartRate" />
            <el-table-column label="体温(°C)" prop="temperature" />
            <el-table-column label="血压(mmHg)" prop="bloodPressure" />
            <el-table-column label="血氧" prop="oxygen" />
            <el-table-column label="是否需要关注">
                <template #default="{ row }">
                    <el-tag :type="row.status ? 'success' : 'warning'">{{ row.status ? '正常' : '需要关注'
                        }}</el-tag>
                    <el-switch v-model="row.status" @click="updateStatus(row)" style="margin-left: 2px;" />

                </template>
            </el-table-column>
            <el-table-column align="right">
                <template #header>
                    <el-input v-model="search" size="default" placeholder="输入老人姓名/编号/房间号搜索" />
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
                        <el-dropdown-item @click="changePageSize(7)">7条/页</el-dropdown-item>
                        <el-dropdown-item @click="changePageSize(10)">10条/页</el-dropdown-item>
                        <el-dropdown-item @click="changePageSize(12)">12条/页</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
            <div id="changePageDiv">
                <el-icon style="cursor: pointer;" @click="changePrevPage">
                    <ArrowLeftBold />
                </el-icon>
                <div id="pageDiv">
                    <span class="pageNum" v-if="pageInfo.hasPrevious && pageInfo.currentPage > 2"
                        @click="gotoPageByNum(pageInfo.currentPage - 2)">{{ pageInfo.currentPage - 2 }}</span>
                    <span class="pageNum" v-if="pageInfo.hasPrevious"
                        @click="gotoPageByNum(pageInfo.currentPage - 1)">{{
                            pageInfo.currentPage - 1 }}</span>
                    <span class="pageNum" @click="gotoPageByNum(pageInfo.currentPage)" style="color: #409EFF;">{{
                        pageInfo.currentPage }}</span>
                    <span class="pageNum" v-if="pageInfo.hasNext" @click="gotoPageByNum(pageInfo.currentPage + 1)">{{
                        pageInfo.currentPage + 1 }}</span>
                    <span class="pageNum" v-if="Number(pageInfo.pages) - pageInfo.currentPage >= 2"
                        @click="gotoPageByNum(pageInfo.currentPage + 2)">{{ pageInfo.currentPage + 2 }}</span>
                </div>
                <el-icon style="cursor: pointer;" @click="changeNextPage">
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



    <el-dialog v-model="editDialogFormVisible" title="编辑老人健康信息" width="500">
        <el-form :model="editForm">
            <el-form-item label="老人姓名" :label-width="formLabelWidth">
                <el-input v-model="editForm.elderlyName" autocomplete="off" disabled />
            </el-form-item>
            <el-form-item label="床位号" :label-width="formLabelWidth">
                <el-input v-model="editForm.bedroom" autocomplete="off" disabled />
            </el-form-item>
            <el-form-item label="年龄" :label-width="formLabelWidth">
                <el-input v-model="editForm.elderlyAge" autocomplete="off" disabled />
            </el-form-item>
            <el-form-item label="心率(次/分)" :label-width="formLabelWidth">
                <el-input v-model="editForm.heartRate" autocomplete="off" />
            </el-form-item>
            <el-form-item label="体温(°C)" :label-width="formLabelWidth">
                <el-input v-model="editForm.temperature" autocomplete="off" />
            </el-form-item>
            <el-form-item label="血压(mmHg)" :label-width="formLabelWidth">
                <el-input v-model="editForm.bloodPressure" autocomplete="off" />
            </el-form-item>
            <el-form-item label="血氧" :label-width="formLabelWidth">
                <el-input v-model="editForm.oxygen" autocomplete="off" />
            </el-form-item>

        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="doNotSave">返回</el-button>
                <el-button type="primary" @click="saveEdit">
                    保存
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script lang="ts" setup>
import axios from '@/api/request.js';
import { computed, ref, onMounted, reactive, watch } from 'vue';
import { ArrowDownBold, ArrowLeftBold, ArrowRightBold, InfoFilled } from '@element-plus/icons-vue';
import { definedUser } from '@/stores/index.js';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

let router = useRouter();
let loginUser = definedUser();

const search = ref('');
const pageInfo = reactive({
    currentPage: 1,
    pageSize: 10,
    total: '',
    pages: '',
    hasPrevious: false,
    hasNext: false,
});
const tableData = reactive([]);

// 获取老人健康信息
const fetchData = () => {
    axios.get(`/eHealth/selectAllElderlyWithHealth`, {
        params: {
            currentPage: pageInfo.currentPage,
            pageSize: pageInfo.pageSize,
        },
    }).then(function (response) {
        const convertedData = response.data.data.records.map(item => ({
            ...item,
            status: item.status === 1,
        }));
        tableData.splice(0, tableData.length, ...convertedData);
        pageInfo.total = response.data.data.total;
        pageInfo.currentPage = response.data.data.currentPage;
        pageInfo.pageSize = response.data.data.pageSize;
        pageInfo.pages = response.data.data.pages;
        pageInfo.hasPrevious = response.data.data.hasPrevious;
        pageInfo.hasNext = response.data.data.hasNext;
    }).catch(function (error) {
        console.log(error);
    });
};

// 封装搜索框模糊查询事件
// 封装搜索框模糊查询事件
function searchTableData(searchValue) {
    axios.get("/eHealth/selectForSearchHealthManagement", {
        params: {
            currentPage: pageInfo.currentPage,
            pageSize: pageInfo.pageSize,
            searchValue: searchValue,
        },
    }).then(response => {
        const convertedData = response.data.data.records.map(item => ({
            ...item,
            status: item.status === 1, // 将 status 转换为布尔值
        }));
        tableData.splice(0, tableData.length, ...convertedData);
        pageInfo.total = response.data.data.total;
        pageInfo.currentPage = response.data.data.currentPage;
        pageInfo.pageSize = response.data.data.pageSize;
        pageInfo.pages = response.data.data.pages;
        pageInfo.hasPrevious = response.data.data.hasPrevious;
        pageInfo.hasNext = response.data.data.hasNext;
    }).catch(error => {
        console.error('查询失败:', error);
    });
}

// 监听搜索框是否有输入
watch(search, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        searchTableData(newVal);
    }
});

// 前往第几页的页数定义
const gotoPageNum = ref(1);

// 监听当输入框页数发送变化时，向服务器发送请求，将对应页码的记录显示
watch(gotoPageNum, (newPage, oldPage) => {
    if (newPage >= 1 && newPage <= Number(pageInfo.pages)) {
        pageInfo.currentPage = newPage;
        if (search.value === null) {
            fetchData();
        } else {
            searchTableData(search.value);
        }
    }
});

// 页面下方单击页码数翻页的事件
function gotoPageByNum(pageNum) {
    pageInfo.currentPage = pageNum;
    if (search.value === null) {
        fetchData();
    } else {
        searchTableData(search.value);
    }
}

// 页面下方选择每页有几条数据的事件
function changePageSize(pageSize) {
    pageInfo.pageSize = pageSize;
    if (search.value === null) {
        fetchData();
    } else {
        searchTableData(search.value);
    }
}

// 页面下方'<'向前翻页的事件
function changePrevPage() {
    if (pageInfo.hasPrevious) {
        pageInfo.currentPage--;
        if (search.value === null) {
            fetchData();
        } else {
            searchTableData(search.value);
        }
    }
}

// 页面下方'>'向后翻页的事件
function changeNextPage() {
    if (pageInfo.hasNext) {
        pageInfo.currentPage++;
        if (search.value === null) {
            fetchData();
        } else {
            searchTableData(search.value);
        }
    }
}

// 删除事件
const handleDelete = (index, row) => {
    axios.delete(`/elderly/delete/${row.eid}`)
        .then(response => {
            tableData.splice(index, 1);
            fetchData();
        })
        .catch(error => {
            console.error('删除失败:', error);
        });
};

// 点击删除后确认，触发事件，删除老人信息
const confirmEvent = (index, row) => {
    handleDelete(index, row);
};

const addDialogFormVisible = ref(false);
const editDialogFormVisible = ref(false);
const formLabelWidth = '140px';
const editForm = reactive({
    eid: '',
    elderlyName: '',
    bedroom: '',
    elderlyAge: '',
    heartRate: '',
    temperature: '',
    bloodPressure: '',
    oxygen: ''
});

// 编辑事件
function editInfo(index, row) {
    editForm.eid = row.eid;
    editForm.elderlyName = row.elderlyName;
    editForm.bedroom = row.bedroom;
    editForm.elderlyAge = row.elderlyAge;
    editForm.heartRate = row.heartRate;
    editForm.temperature = row.temperature;
    editForm.bloodPressure = row.bloodPressure;
    editForm.oxygen = row.oxygen;

    editDialogFormVisible.value = true;
}

// 编辑状态下，保存，并向服务器发送请求更新数据库中的数据
const saveEdit = async () => {
    const response = await axios.put('/eHealth/updateElderlyHealth', {
        eid: editForm.eid,
        elderlyName: editForm.elderlyName,
        bedroom: editForm.bedroom,
        elderlyAge: editForm.elderlyAge,
        heartRate: editForm.heartRate,
        temperature: editForm.temperature,
        bloodPressure: editForm.bloodPressure,
        oxygen: editForm.oxygen
    });

    if (response.data.code === 200) {
        fetchData();
        ElMessage.success('保存成功');
        editDialogFormVisible.value = false;
    } else {
        ElMessage.error('保存失败');
    }
}

// 编辑状态下：不保存，返回
const doNotSave = () => {
    editDialogFormVisible.value = false;
    ElMessage({
        message: '取消保存，未成功修改数据',
        type: 'warning',
    });
};

const updateStatus = (row) => {
    axios.put(`/eHealth/updateStatus`, {
        eid: row.eid,
        status: row.status === true ? 1 : 0
    }).then(function (response) {
        ElMessage.success('修改成功');
    }).catch(function (error) {
        console.log(error);
    });
}

onMounted(() => {
    fetchData();
});
</script>

<style scoped>
#box {
    background-color: #fff;
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
    justify-content: center;
    align-items: center;
    font-size: var(--el-font-size-base);
    color: var(--el-text-color-regular);
    margin-right: 20px;
}

#sizeDiv {
    height: 25px;
    border: 1px solid var(--el-border-color);
    width: 80px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    cursor: pointer;
}

#changePageDiv {
    display: flex;
    align-items: center;
    width: auto;
    height: 27px;
    margin-left: 20px;
    color: var(--el-text-color-regular);
    margin-right: 20px;
}

.pageNum {
    font-size: var(--el-font-size-base);
    color: var(--el-text-color-regular);
    margin-left: 15px;
    margin-right: 15px;
    cursor: pointer;
}

#gotoPageNumDiv {
    display: flex;
    align-items: center;
    width: auto;
    height: 27px;
    color: var(--el-text-color-regular);
}

#inputDiv {
    height: 25px;
    width: 15px;
    text-align: center;
    font-size: var(--el-font-size-base);
    color: var(--el-text-color-regular);
}

.pageNumDiv_span {
    font-size: var(--el-font-size-base);
    color: var(--el-text-color-regular);
}

/* 编辑对话框 */
.edit-dialog {
    width: 500px;
    margin: 0 auto;
}

.edit-form-item {
    margin-bottom: 20px;
}

.edit-form-item label {
    display: block;
    margin-bottom: 10px;
}

.edit-form-item input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
</style>