<template>
    <div id="box">
        <el-table :data="sortedTableData" style="width: 100%; font-size: 14px;" size="large">
            <el-table-column label="老人姓名" prop="name" />
            <el-table-column label="联系电话" prop="phone" />
            <el-table-column label="申请人" prop="bookerName" />
            <el-table-column label="时间" width=220 prop="time" />
            <el-table-column label="申请类型">
                <template #default="{ row }">
                    <el-tag :type="row.type === '外出申请' ? 'warning' : 'primary'">{{ row.type }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="是否有效">
                <template #default="{ row }">
                    <el-tag round :type="row.time <= currentDateStr ? 'info' : 'success'">{{
                        row.time <= currentDateStr ? '失效' : '有效' }}</el-tag>
                </template>
            </el-table-column>
            
        </el-table>

    </div>
</template>

<script lang="ts" setup>
import { computed, ref, onMounted, reactive, watch, } from 'vue'
import axios from '@/api/request.js';
import { definedUser } from '@/stores/index.js';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router';
let loginUser = definedUser()
interface Application {
    name: string;
    sex: string;
    phone: string;
    bookerName: string;
    time: string;
    type: string;
}
const tableData = ref<Application>([{
}])

const fetchData = () => {
    axios.get(`/application/selectAllApplicationByUid/${loginUser.uid}`, {
    }).then(response => {
        tableData.value = response.data.data
        
    }).catch(error => {
        console.error('查询失败:', error);
    })
}
const search = ref()
const currentDate = new Date();
const year = currentDate.getFullYear();
const month = currentDate.getMonth() + 1; // 月份从0开始，所以需要加1 
const day = currentDate.getDate();
const currentDateStr = `${year}-${month}-${day}`;
const sortedTableData = computed(() => {
    return tableData.value.sort((a, b) => {
        // 根据时间判断“是否有效”，将“失效”的行放到最后
        const isValidA = a.time > currentDateStr;
        const isValidB = b.time > currentDateStr;
        return isValidB - isValidA; // 将有效的行放在前面，失效的行放在后面
    })
});

onMounted(() => {
    fetchData()
})
</script>

<style>
#box {
    background-color: white;
    display: flex;
    align-items: center;
    flex-direction: column;
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
