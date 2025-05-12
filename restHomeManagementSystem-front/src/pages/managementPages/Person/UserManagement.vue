<!-- 员工管理 -->
<template>
    <Header-web></Header-web>
    <Menu-web></Menu-web>
    <Footer-web></Footer-web>
    <div id="box">
        <div>
            <el-table :data="filterTableData" style="width: 100%" size="large">
                <el-table-column label="用户名" prop="username" />
                <el-table-column label="昵称" prop="nickname" />
                <el-table-column label="性别" prop="sex" />
                <el-table-column label="权限" prop="permission" />
                <el-table-column label="最近登录时间" prop="lastLoginTime" />
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
                        <span class="pageNum" v-if="pageInfo.hasNext"
                            @click="gotoPageByNum(pageInfo.currentPage + 1)">{{
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


        <el-dialog v-model="dialogFormVisible" title="编辑用户信息" width="500">
            <el-form :model="form">
                <el-form-item label="用户名" :label-width="formLabelWidth">
                    <el-input v-model="form.username" autocomplete="off" disabled />
                </el-form-item>
                <el-form-item label="昵称" :label-width="formLabelWidth">
                    <el-input v-model="form.nickname" autocomplete="off" />
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth">
                    <el-radio-group v-model="form.sex">
                        <el-radio value="男">男</el-radio>
                        <el-radio value="女">女</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="权限" :label-width="formLabelWidth">
                    <el-radio-group v-model="form.permission">
                        <el-radio value="普通用户">普通用户</el-radio>
                        <el-radio value="员工">员工</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="最近登录时间" :label-width="formLabelWidth">
                    <el-input v-model="form.lastLoginTime" autocomplete="off" disabled />
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

    </div>

</template>

<script lang="ts" setup>
import axios from '@/api/request.js';

import { computed, ref, onMounted, reactive, watch } from 'vue'
import { ArrowDownBold, ArrowLeftBold, ArrowRightBold, InfoFilled, User } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

//pageInfo 接收服务器响应的分页属性
const pageInfo = reactive(
    {
        currentPage: 1,
        pageSize: 10,
        total: '',
        pages: '',
        hasPrevious: false,
        hasNext: false,
    }
)

//表格的响应式数据
const filterTableData = ref<User[]>([])

//前往第几页的页数定义
const gotoPageNum = ref(1)

//监听当输入框页数发送变化时，向服务器发送请求，将对应页码的记录显示
watch(gotoPageNum, (newPage, oldPage) => {
    if (newPage >= 1 && newPage <= Number(pageInfo.pages)) {
        pageInfo.currentPage = newPage
        if (search == null) {
            fetchData()
        } else {
            searchTableData(search.value)
        }
    }
})

//页面下方单击页码数翻页的事件
function gotoPageByNum(pageNum) {
    pageInfo.currentPage = pageNum
    if (search == null) {
        fetchData()
    } else {
        searchTableData(search.value)
    }
}
//页面下方选择每页有几条数据的事件
function changePageSize(pageSize) {
    pageInfo.pageSize = pageSize
    if (search == null) {
        fetchData()
    } else {
        searchTableData(search.value)
    }
}
//页面下方'<'向前翻页的事件
function changePrevPage() {
    if (pageInfo.hasPrevious) {
        pageInfo.currentPage--
        if (search == null) {
            fetchData()
        } else {
            searchTableData(search.value)
        }
    }
}
//页面下方'>'向后翻页的事件
function changeNextPage() {
    if (pageInfo.hasNext) {
        pageInfo.currentPage++
        if (search == null) {
            fetchData()
        } else {
            searchTableData(search.value)
        }
    }
}
//封装向服务器请求所有用户信息的事件
async function fetchData() {
    await axios.get("/user/selectAllUser", {
        params: {
            //当前页
            currentPage: pageInfo.currentPage,
            //每页有几条数据
            pageSize: pageInfo.pageSize,
        }
    })
        .then(function (response) {
            //表格数据
            filterTableData.value = response.data.data.records
            // 接收到的数据中共有几条数据
            pageInfo.total = response.data.data.total;
            // 当前为第几页
            pageInfo.currentPage = response.data.data.currentPage;
            // 接收到的数据中一页有几条数据
            pageInfo.pageSize = response.data.data.pageSize;
            // 接收到的数据中共有几页
            pageInfo.pages = response.data.data.pages;
            //是否有前一页
            pageInfo.hasPrevious = response.data.data.hasPrevious
            //是否有后一页
            pageInfo.hasNext = response.data.data.hasNext;
            console.log(response)
        }
        ).catch(function (error) {
            console.log(error);
        });
}

//挂载时，第一次向服务器请求所有用户信息
onMounted(() => {
    fetchData()
})


interface User {
    uid: string
    username: string
    nickname: string
    sex: string
    permission: string
    lastLoginTime: string
}

// 编辑事件
const handleEdit = (index: number, row: User) => {
    console.log(index, row)
}
// 删除事件
const handleDelete = (index: number, row: User) => {
    // if (window.confirm('您确定要删除用户 ' + row.username + ' 吗？')) {
    // 用户点击了确定按钮
    axios.delete(`/user/deleteUser/${row.uid}`)
        .then(response => {
            // 服务器成功响应删除请求
            // 从本地数组中移除用户
            filterTableData.value.splice(index, 1);
            fetchData()
        })
        .catch(error => {
            console.error('删除用户失败:', error);
        });

}
//点击删除后确认，触发事件，删除用户
const confirmEvent = (index: number, row: User) => {
    handleDelete(index, row)
}


//封装搜索框模糊查询事件
function searchTableData(searchValue) {
    axios.get("/user/selectForSearch", {
        params: {
            //当前页
            currentPage: pageInfo.currentPage,
            //页大小
            pageSize: pageInfo.pageSize,
            //搜索框的值
            searchValue: searchValue
        }
    }).then(response => {
        filterTableData.value = response.data.data.records
        pageInfo.total = response.data.data.total;
        pageInfo.currentPage = response.data.data.currentPage;
        pageInfo.pageSize = response.data.data.pageSize;
        pageInfo.pages = response.data.data.pages;
        pageInfo.hasPrevious = response.data.data.hasPrevious
        pageInfo.hasNext = response.data.data.hasNext;
    }).catch(error => {
        console.error('查询失败:', error);
    })
}
//与搜索框进行双向数据绑定
const search = ref('')
//监听搜索框是否有输入
watch(search, (newVal, oldVal) => {
    if (newVal != oldVal) {
        searchTableData(newVal)
    }
})

//是否显示编辑框
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'
//存储编辑框内的响应式数据
const form = reactive({
    uid: '',
    username: '',
    nickname: '',
    sex: '',
    permission: '',
    lastLoginTime: '',
})
//编辑事件
function editInfo(index: number, row: User) {
    form.uid = row.uid
    form.username = row.username
    form.nickname = row.nickname
    form.sex = row.sex
    form.permission = row.permission
    form.lastLoginTime = row.lastLoginTime
    dialogFormVisible.value = true
}
//编辑状态下：不保存，返回
const doNotSave = () => {
    dialogFormVisible.value = false
    ElMessage({
        message: '取消保存，未成功修改数据',
        type: 'warning',
    })
}


//编辑状态下，保存，并向服务器发送请求更新数据库中的数据
const save = () => {
    axios.put("/user/updateUser", {
        uid: form.uid,
        username: form.username,
        nickname: form.nickname,
        sex: form.sex,
        permission: form.permission,
    }).then(function (response) {
        console.log(response)
        fetchData()
    }).catch(function (error) {
        console.log(error);
    }),
        dialogFormVisible.value = false
    ElMessage({
        message: '保存成功',
        type: 'success',
    })
}
</script>
<style>
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
</style>
