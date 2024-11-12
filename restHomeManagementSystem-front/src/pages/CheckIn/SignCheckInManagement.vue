<template>
    <div id="box">
        <el-table :data="tableData" style="width: 100%" size="large">
            <el-table-column label="老人姓名" prop="elderlyName" />
            <el-table-column label="老人性别" prop="elderlySex" />
            <el-table-column label="家庭住址" prop="elderlyAddress" />
            <el-table-column label="预定人姓名" prop="bookerName" />
            <el-table-column label="预定时间" prop="bookerTime" />
            <el-table-column align="right">
                <template #default="scope">
                    <el-button :disabled="scope.row.isReserved" size="default"
                        @click="toSignCheckIn(scope.$index, scope.row)">
                        <label v-if="scope.row.isReserved"> 已签约</label>
                        <label v-else> 去预定</label>

                    </el-button>
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
    </div>
</template>

<script lang="ts" setup>
import { computed, ref, onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router';
let router = useRouter()

import axios from '../../api/request.js';

import { definedUser } from '../../stores/index.js';
import { InfoFilled } from '@element-plus/icons-vue'

let loginUser = definedUser()
interface Reserve {
    eid: Number;
    elderlyName: string,
    elderlySex: string,
    elderlyAddress: string,
    bookerName: string,
    bookerTime: String,
    isReserved: boolean,
}
const tableData = reactive<Reserve[]>([])
const fetchData = () => {
    axios.get(`/reserve/selectReserveByUid/${loginUser.uid}`)
        .then(function (response) {
            console.log(response.data.data[0]);
            console.log(typeof (response.data.data[0].bookerTime));
            //表格数据
            const convertedData: Reserve[] = response.data.data.map(item => ({
                ...item,
                bookerTime: item.bookerTime.split('T')[0],
                //三元表达式，isReply为true时返回已回复
                elderlySex: item.elderlySex === 'male' ? '男' : '女',
                isReserved: item.isReserved === 0 ? false : true,
            }
            ))
            //清空原数组，加入响应回的数组
            tableData.splice(0, tableData.length, ...convertedData);
        }
        ).catch(function (error) {
            console.log(error);
        });
}

// 删除事件
const handleDelete = (index: number, row: User) => {
    // 用户点击了确定按钮
    axios.delete(`/reserve/deleteReserve/${row.eid}`)
        .then(response => {
            // 服务器成功响应删除请求
            // 从本地数组中移除用户
            tableData.splice(index, 1);
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
const toSignCheckIn = (index: number, row: User) => {
    router.push({ name: 'SignCheckIn', params: { eid: row.eid } });
}



onMounted(() => {
    fetchData()
})

</script>