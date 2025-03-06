<!-- 员工管理 -->
<template>
    <Header-web></Header-web>
    <Menu-web></Menu-web>
    <Footer-web></Footer-web>
    <div id="box">
        <div>
            <el-table :data="filterTableData" style="width: 100%" size="large">
                <el-table-column label="老人姓名" prop="elderlyName" />
                <el-table-column label="老人性别" prop="elderlySex" />
                <el-table-column label="身份证号码" prop="elderlyIdCard" />
                <el-table-column label="联系电话" prop="elderlyPhone" />
                <el-table-column label="地址" prop="elderlyAddress" />
                <el-table-column label="健康状况" prop="isHealth" />
                <el-table-column label="预定人姓名" prop="bookerName" />
                <el-table-column label="预定时间" prop="bookerTime" />
                <el-table-column label="是否自愿入住" prop="isVoluntaryOccupancy" />
                <el-table-column label="备注" prop="note" />
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" size="default" placeholder="搜索" />
                    </template>
                    <template #default="scope">
                        <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled"
                            icon-color="#626AEF" title="您确定要删除这条记录吗?" @confirm="confirmEvent(scope.$index, scope.row)"
                            @cancel="ElMessage({
                                message: '取消删除，未成功删除数据',
                                type: 'warning',
                            })">
                            <template #reference>
                                <el-button type="danger">Delete</el-button>
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
const filterTableData = ref<RuleForm[]>([])

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
    await axios.get("/reserve/selectAllReserve", {
        params: {
            //当前页
            currentPage: pageInfo.currentPage,
            //每页有几条数据
            pageSize: pageInfo.pageSize,
        }
    })
        .then(function (response) {
            console.log(response);
            //表格数据
            filterTableData.value = processTableData(response.data.data.records, {}, {})
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
            console.log(filterTableData.value);

        }
        ).catch(function (error) {
            console.log(error);
        });
}




//RuleForm接口，定义一个预定的所有属性
interface RuleForm {
    eid: string
    elderlyName: string
    elderlySex: string
    birth: string
    elderlyIdCard: string
    elderlyPhone: string
    elderlyAddress: string
    isHealth: string
    bookerName: string
    bookerTime: string
    isVoluntaryOccupancy: boolean
    note: string
}

// 格式化数据
function processTableData(data: RuleForm[], filters: any, formatters: any): RuleForm[] {
    data = data.map(item => {
        const formatElderlySex = item.elderlySex === 'male' ? '男' : '女'
        const formatIsHealth = item.isHealth === 'healthy' ? '健康' : '有疾病史或有其他健康问题'
        const formattedBookerTime = convertTimestamp(item.bookerTime);
        const formattedIsVoluntaryOccupancy = item.isVoluntaryOccupancy === 1 ? '自愿' : '非自愿'
        return {
            ...item,
            elderlySex: formatElderlySex,
            isHealth: formatIsHealth,
            isVoluntaryOccupancy: formattedIsVoluntaryOccupancy,
            bookerTime: formattedBookerTime,
        };
    });
    return data;
}

// 删除事件
const handleDelete = (index: number, row: User) => {
    // 用户点击了确定按钮
    axios.delete(`/reserve/deleteReserve/${row.eid}`)
        .then(response => {
            // 服务器成功响应删除请求
            // 从本地数组中移除用户
            filterTableData.value.splice(index, 1);
            //更新数据，保证分页功能
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
    axios.get("/reserve/selectForSearch", {
        params: {
            //当前页
            currentPage: pageInfo.currentPage,
            //页大小
            pageSize: pageInfo.pageSize,
            //搜索框的值
            searchValue: searchValue
        }
    }).then(response => {
        filterTableData.value = processTableData(response.data.data.records, {}, {})
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
const convertTimestamp = (timeStamp) => {
    const date = new Date(parseInt(timeStamp));
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

   return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
   
};

//挂载时，第一次向服务器请求所有用户信息
onMounted(() => {
    fetchData()
    
})


</script>
<style>
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
</style>
