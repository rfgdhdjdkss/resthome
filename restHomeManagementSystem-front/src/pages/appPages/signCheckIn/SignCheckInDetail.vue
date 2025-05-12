<template>
    <div class="container">
        <div class="header">
            <span class="back-arrow" @click="router.push({ name: 'Home_app' })">←</span>
            <h2>签约详情</h2>
        </div>
        <div
            style="background: white;border-radius: 10px 10px 0px 0px;padding: 15px;box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); padding-bottom: 0;">

            <el-steps :active="active" finish-status="success" align-center>
                <el-step title="签约" />
                <el-step title="选择床位" />
                <el-step title="缴纳定金" />
            </el-steps>
        </div>

        <div class="step-box">
            <transition name="el-zoom-in-center">
                <div v-show="signShow" class="step-container">
                    <el-form :model="ruleForm" :rules="rules" class="demo-ruleForm" :size="formSize">
                        <el-form-item label="姓名" prop="elderlyName">
                            <el-input v-model="ruleForm.elderlyName" :disabled="disabled" />
                        </el-form-item>
                        <el-form-item label="性别" prop="elderlySex">
                            <el-radio-group v-model="ruleForm.elderlySex" :disabled="disabled">
                                <el-radio value="male">男</el-radio>
                                <el-radio value="female">女</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="出生年月" prop="elderlyBirth">
                            <el-date-picker v-model="ruleForm.elderlyBirth" type="date" placeholder="请选择出生年月"
                                :disabled="disabled" />
                        </el-form-item>
                        <el-form-item label="身份证号码" prop="elderlyIdCard">
                            <el-input v-model="ruleForm.elderlyIdCard" :disabled="disabled" />
                        </el-form-item>
                        <el-form-item label="联系电话" prop="elderlyPhone">
                            <el-input v-model="ruleForm.elderlyPhone" :disabled="disabled" />
                        </el-form-item>
                        <el-form-item label="家庭住址" prop="elderlyAddress">
                            <el-input v-model="ruleForm.elderlyAddress" :disabled="disabled" />
                        </el-form-item>
                        <el-form-item label="健康状况" prop="isHealth">
                            <el-select v-model="ruleForm.isHealth" placeholder="请选择健康状况" :disabled="disabled">
                                <el-option label="健康" value="healthy" />
                                <el-option label="有疾病史或有其他健康问题" value="subHealthy" />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="预约人姓名" prop="bookerName">
                            <el-input v-model="ruleForm.bookerName" :disabled="disabled" />
                        </el-form-item>
                        <el-form-item label="预计入住时间" prop="bookerTime">
                            <el-date-picker v-model="ruleForm.bookerTime" type="date" placeholder="请选择预计入住时间"
                                :disabled="disabled" />
                        </el-form-item>
                        <el-form-item label="是否为自愿入住" prop="isVoluntaryOccupancy">
                            <el-switch v-model="ruleForm.isVoluntaryOccupancy" :disabled="disabled" />
                        </el-form-item>
                        <el-form-item label="备注信息" prop="note">
                            <el-input v-model="ruleForm.note" type="textarea" :disabled="disabled" />
                        </el-form-item>
                        <el-form-item prop="check">
                            <el-radio-group v-model="ruleForm.check">
                                <el-checkbox v-model="isChecked" @click="openDeal">本人同意并知晓全部内容</el-checkbox>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="editInfo()" v-if="disabled">修改信息</el-button>
                            <el-button @click="cancel()" v-if="!disabled">取消修改</el-button>
                            <el-button @click="save()" v-if="!disabled">保存</el-button>
                        </el-form-item>
                    </el-form>
                    <el-dialog v-model="dialogFormVisible" title="养老院入住协议" width="90%" :modal="true">
                        <el-scrollbar ref="myScrollbar" :wrap-style="{ height: '400px' }" style="padding-left: 10px;">
                            <p>一、服务内容</p>
                            <p class="deal_p">
                                日常生活服务：养老院将为入住者提供全面的日常生活照料，包括但不限于定时膳食、房间清洁、衣物洗涤及熨烫服务。
                                健康医疗服务：定期进行健康检查，提供必要的医疗护理服务，确保入住者的基本健康需求得到满足。对于突发疾病或紧急情况，养老院将提供及时的医疗援助并联系专业医疗机构。
                                休闲娱乐与文化教育：组织丰富多彩的娱乐活动和文化讲座，以促进入住者的身心健康和社交互动。
                                个性化服务（如适用）：根据入住者的具体需求，提供个性化的护理、康复及其他服务。
                            </p>
                            <p>二、居住条件与设施</p>
                            <p class="deal_p">居住房间：提供安全、舒适的居住环境，房间设施包括但不限于床铺、衣柜、储物空间及紧急呼叫设备。
                                公共区域：配备餐厅、活动室、浴室等公共设施，供入住者共同使用。
                                安全措施：养老院将采取必要的安全措施，包括但不限于视频监控、门禁系统及紧急疏散预案，以确保入住者的安全。
                            </p>
                            <p>三、费用与支付</p>
                            <p class="deal_p">
                                费用结构：明确列出各项服务的费用，包括但不限于入住费、服务费、医疗费等。
                                支付方式：详细说明费用的支付方式、时间及调整机制。
                                退款政策：规定提前退住、去世等特殊情况下的退款政策及流程。
                            </p>
                            <p>四、权利与义务</p>
                            <p class="deal_p">
                                入住者权利：享受协议约定的服务，提出合理建议和投诉，参与养老院组织的活动等。
                                入住者义务：遵守养老院的规定和制度，按时支付费用，尊重其他入住者及工作人员的权益。
                                养老院权利：对入住者进行管理，调整服务内容，维护养老院的正常运营秩序。
                                养老院义务：按照协议约定提供服务，保障入住者的基本生活需求和安全。
                            </p>
                            <p>五、违约责任</p>
                            <p class="deal_p">
                                双方应严格遵守协议约定，如任何一方违反协议条款，应承担相应的违约责任，并赔偿因此给对方造成的损失。
                            </p>
                            <p>六、争议解决</p>
                            <p class="deal_p">
                                对于因执行本协议而发生的任何争议，双方应首先通过友好协商解决；协商不成的，可提交至当地仲裁委员会进行仲裁或依法向人民法院提起诉讼。
                            </p>
                            <p>七、其他条款</p>
                            <p class="deal_p">
                                本协议自双方签字盖章之日起生效，有效期至入住者离院之日止。
                                本协议一式两份，甲乙双方各执一份，具有同等法律效力。
                            </p>
                            <p>八、本人声明</p>
                            <p class="deal_p">
                                本人已仔细阅读并理解本协议的全部内容，自愿接受养老院提供的服务，并同意遵守本协议的各项规定。
                            </p>

                            <template #footer>
                                <div class="dialog-footer">
                                    <el-button type="primary" @click="save">保存</el-button>
                                </div>
                            </template>
                        </el-scrollbar>
                    </el-dialog>
                </div>
            </transition>

            <transition name="el-zoom-in-center">
                <div v-show="bedShow" class="step-container">
                    <div class="bed-selection">
                        <el-card v-for="(item, index) in bedInfo" :key="index" class="roomCard"
                            :class="{ 'selected-card': selectedCardIndex === index }" @click="selectCard(index)">
                            <template #header>{{ item.roomType }}</template>
                            <img src="../../../images/singleRoom.png" class="room-image"
                                v-if="item.roomType === '单人间'" />
                            <img src="../../../images/doubleRoom.png" class="room-image"
                                v-if="item.roomType === '双人间'" />
                            <img src="../../../images/trioRoom.png" class="room-image" v-if="item.roomType === '多人间'" />
                            <div class="footer_info">
                                <label>价格: ¥{{ item.price }}元/月</label>
                                <label>剩余床位数: {{ item.spaceBedNumber }}</label>
                            </div>
                        </el-card>
                    </div>
                </div>
            </transition>
            <transition name="el-zoom-in-center">
                <div id="payDeposit_container" class="step_container " v-show="payShow">
                    <div class="reserve_elderly_info">
                        <h3>预定老人信息</h3>
                        <div class="info-item">
                            <span class="info-label">老人姓名:</span>
                            <span class="info-value">{{ ruleForm.elderlyName }}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">联系电话:</span>
                            <span class="info-value">{{ ruleForm.elderlyPhone }}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">家庭住址:</span>
                            <span class="info-value">{{ ruleForm.elderlyAddress }}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">选择的房间床位:</span>
                            <span class="info-value">{{ selectedBedType }}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">账户余额:</span>
                            <span class="info-value">¥{{ balance }}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">预缴金额:</span>
                            <span class="info-value">¥{{ earnest }}</span>
                        </div>
                        <div style="display: flex; align-items: center; justify-content: center; padding-top: 20px;">
                            <el-text v-if="balance < earnest" class="mx-1" type="warning"
                                size="large">账户余额不足，预定前请充值</el-text>
                            <el-text v-else class="mx-1" type="success" size="large">账户余额足够支付定金，可以直接预定</el-text>
                            <el-button round @click="openDialog" style="margin-left: 30px;"
                                v-if="balance < earnest">立即充值</el-button>
                        </div>
                    </div>


                    <transition name="el-fade-in-linear">
                        <div v-if="showDialog" class="pay-dialog">
                            <div class="pay-dialog-content">
                                <label class="recharge-label">请输入充值金额
                                    <el-text class="mx-1" type="info">（预定成功后充值金额将存入预定老人的账户）</el-text>
                                </label>
                                <el-input-number v-model="recharge" class="recharge-input" :min="minValue" type="number"
                                    @input="validateInput" placeholder="请输入支付金额" />
                            </div>
                            <div id="paymentContainer" class="payment-container">
                                <el-card shadow="hover" v-for="(payment, index) in paymentList" :key="index"
                                    class="paymentBox" :class="{ 'selected-payment': selectedPaymentIndex === index }"
                                    @click="selectPayment(index)">
                                    <div class="payment-content">
                                        <img :src="payment.imgSrc" class="payment-image">
                                        <h4>{{ payment.name }}</h4>
                                    </div>
                                </el-card>
                            </div>
                            <div id="rechargeContainer" class="recharge-buttons">
                                <el-button type="success" round @click="payHandle">确认充值</el-button>
                                <el-button type="danger" round @click="cancelPay">取消充值</el-button>
                            </div>
                        </div>
                    </transition>
                </div>
            </transition>
        </div>

        <div class="navigation-buttons">
            <el-button @click="last" v-show="active != 0">上一步</el-button>
            <el-button @click="next" v-show="active < 2">下一步</el-button>
            <el-button @click="handelPerform" v-show="active >= 2">缴费完成签约</el-button>
        </div>

        <div class="footer-text">
            要养老，上养老生活 ®
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted, computed } from 'vue'
import axios from '@/api/request.js';
import { createAlipayPayment, submitAlipayForm } from '@/api/alipay';

import { useRoute } from 'vue-router';
import { ElMessage } from 'element-plus'
import { definedUser } from '@/stores/index.js';
import { useRouter } from 'vue-router';

let router = useRouter()
let loginUser = definedUser()
let route = useRoute()
//eid--->路由传参
const routeEid = route.params.eid
//用于切换步骤
const active = ref(0)
//定义是否展示签约步骤
const signShow = ref(true)
//定义是否展示选择床位步骤
const bedShow = ref(false)
//定义是否展示缴费步骤
const payShow = ref(false)
//定义下一步方法，用于下一步步骤条
const next = () => {
    //签约页勾选协议
    if (isChecked.value) {
        //切换到选择床位页
        active.value = 1
        //当选择了床位
        if (active.value == 1 && selectedBedType.value != null) {
            //切换到缴费页
            active.value = 2
        } else {
            ElMessage.info('请选择房间床位')
        }
        // 当当前步骤条到哪步时显示哪个页
        signShow.value = active.value === 0;
        bedShow.value = active.value === 1;
        payShow.value = active.value >= 2;
    }
    else {
        ElMessage.error('请同意协议并勾选同意协议')
    }
}
const handelPerform = () => {
    if (balance.value < earnest.value) {
        ElMessage({
            message: '余额不足，请先充值',
            type: 'error',
        })
    }
    //当余额大于预缴金额
    if (active.value >= 2 && balance.value >= earnest.value) {
        active.value = 3

        //成功签约
        signCheckIn()

        //展示成功信息
        ElMessage({
            message: '签约成功，将在3秒后跳转至老人信息页',
            type: 'success',
        })
        //3秒后跳转至老人信息页
        setTimeout(() => {
            router.push({ name: "ElderlyDocumentList_app" });

        }, 3000);

    }
}
//定义上一步事件
const last = () => {
    //向前一页
    active.value--
    signShow.value = active.value === 0;
    bedShow.value = active.value === 1;
    payShow.value = active.value === 2;
    //点击上一页清空已选择的房间床位
    selectedCardIndex.value = 4

}
//RuleForm接口，定义一个预定的所有属性
interface RuleForm {
    elderlyName: string,
    elderlySex: string,
    elderlyBirth: string,
    elderlyIdCard: string,
    elderlyPhone: string,
    elderlyAddress: string,
    isHealth: string,
    bookerName: string,
    bookerTime: string,
    isVoluntaryOccupancy: boolean
    note: string
}
//定义表单大小
const formSize = ref<ComponentSize>('default')
//定义 FormInstance，用来操作 form 实例
const ruleFormRef = ref<FormInstance>()
//定义 ruleForm 初始值
const ruleForm = reactive<RuleForm>({
    elderlyName: '',
    elderlySex: '',
    elderlyBirth: '',
    elderlyIdCard: '',
    elderlyPhone: '',
    elderlyAddress: '',
    isHealth: '',
    bookerName: '',
    bookerTime: '',
    isVoluntaryOccupancy: false,
    note: '',
})
//定义 rules，用来定义 form 验证规则
const rules = reactive<FormRules<RuleForm>>({
    elderlyName: [
        { required: true, message: '请填写老人姓名', trigger: 'blur' },
        { min: 2, max: 5, message: '请确保姓名长度在2-7个之间', trigger: 'blur' },
        { pattern: /^[\u4e00-\u9fa5]{0,}$/, message: '请填写汉字', trigger: 'change' },
    ],
    elderlySex: [
        {
            required: true,
            message: '请选择老人的性别',
            trigger: 'change',
        },
    ],
    elderlyBirth: [
        {
            type: 'date',
            required: true,
            message: '请选择出生日期',
            trigger: 'change',
        },
    ],
    elderlyIdCard: [
        {
            required: true,
            pattern: /^.{18}$/,
            message: '请填写18位的身份证号码',
            trigger: 'blur',
        },
    ],
    elderlyPhone: [
        {
            required: true,
            pattern: /^.{11}$/,
            message: '请填写11位的手机号',
            trigger: 'blur',
        },
    ],
    elderlyAddress: [
        {
            required: true,
            message: '请填写地址',
            trigger: 'blur',
        },
    ],
    isHealth: [
        {
            required: true,
            message: '请选择健康状况',
            trigger: 'change',
        },
    ],
    bookerName: [
        {
            required: true,
            message: '请填写预定人姓名',
            trigger: 'blur',
        },
        { min: 2, max: 5, message: '请确保姓名长度在2-7个之间', trigger: 'blur' },
        { pattern: /^[\u4e00-\u9fa5]{0,}$/, message: '请填写汉字', trigger: 'change' },
    ],
    bookerTime: [
        { type: 'date', required: true, message: '请选择预定时间', trigger: 'change' },
    ],
    isVoluntaryOccupancy: [
        {
            required: true,
            message: '请选择是否自愿入住',
            trigger: 'change',
        }
    ],
    note: [
        {
            required: false,
        }]
})
//根据老人id获取预定老人信息
async function fetchData(eid) {
    await axios.get(`/reserve/selectReserveByEid/${eid}`, {
    })
        .then(function (response) {
            //表格数据
            console.log(response);
            const data = response.data.data
            ruleForm.elderlyName = data.elderlyName
            ruleForm.elderlySex = data.elderlySex
            ruleForm.elderlyBirth = data.elderlyBirth
            ruleForm.elderlyIdCard = data.elderlyIdCard
            ruleForm.elderlyPhone = data.elderlyPhone
            ruleForm.elderlyAddress = data.elderlyAddress
            ruleForm.isHealth = data.isHealth
            ruleForm.bookerName = data.bookerName
            ruleForm.bookerTime = data.bookerTime
            ruleForm.isVoluntaryOccupancy = data.isVoluntaryOccupancy === 1 ? true : false
            ruleForm.note = data.note
        }
        ).catch(function (error) {
            console.log(error);
        });
}
//用于判断是否在签约页勾选协议
const isChecked = ref(false)
//用于判断按钮（修改信息、保存、取消修改）是否显示
const disabled = ref(true)
//编辑信息点击事件
function editInfo() {
    //不显示修改信息按钮
    disabled.value = false
}
//返回点击事件
function cancel() {
    //显示取消修改按钮
    disabled.value = true
    ElMessage({
        message: '取消保存，未成功修改数据',
        type: 'warning',
    })
}
//定义保存事件
const save = () => {
    //发送put请求，更新数据库预定老人的信息
    axios.put("/reserve/updateReserveByEid", {
        eid: routeEid,
        elderlyName: ruleForm.elderlyName,
        elderlySex: ruleForm.elderlySex,
        elderlyBirth: ruleForm.elderlyBirth,
        elderlyIdCard: ruleForm.elderlyIdCard,
        elderlyPhone: ruleForm.elderlyPhone,
        elderlyAddress: ruleForm.elderlyAddress,
        isHealth: ruleForm.isHealth,
        bookerName: ruleForm.bookerName,
        bookerTime: ruleForm.bookerTime,
        isVoluntaryOccupancy: ruleForm.isVoluntaryOccupancy,
        note: ruleForm.note,
    }).then(function (response) {
        //保存后重新获取数据
        fetchData(routeEid)
    }).catch(function (error) {
        console.log(error);
    }),
        //显示保存按钮
        disabled.value = true
    ElMessage({
        message: '保存成功',
        type: 'success',
    })
}
//用于判断是否显示同意协议框
const dialogFormVisible = ref(false)
//定义点击事件，单击"本人同意并知晓全部内容"时触发
const openDeal = () => {
    //当未被选中时打开
    if (!isChecked.value) {
        dialogFormVisible.value = true
    }
}

//定义Bed接口，接收床位信息的基本数据
interface Bed {
    roomType: string,
    spaceBedNumber: number,
    price: number,
}
//定义 bedInfo，接收床位信息的 reactive 数组
const bedInfo = reactive<Bed[]>([])
//获取数据，发送get请求，获取房间信息
function fetchBedInfo() {
    axios.get(`/bedroom/selectBedInfo`, {
    })
        .then(function (response) {
            console.log(response);
            //表格数据
            const convertedBedInfo: Bed[] = response.data.data.bedroomList.map(item => ({
                ...item,
            }));
            //清空原数组，加入响应回的数组
            bedInfo.splice(0, bedInfo.length, ...convertedBedInfo);
            console.log(bedInfo);
        }
        ).catch(function (error) {
            console.log(error);
        });
}
//被选中的卡片索引
const selectedCardIndex = ref(Number)
//点击哪个卡片，将卡片索引赋值给被选中的卡片索引
function selectCard(index) {
    selectedCardIndex.value = index; // 更新当前选中的卡片索引
    console.log(selectedCardIndex.value);

}
//计算属性：根据被选中的卡片索引确定被选中的床位的类型
const selectedBedType = computed(() => {
    return selectedCardIndex.value === 0 ? "单人间" : (selectedCardIndex.value === 1 ? "双人间" : (selectedCardIndex.value === 2 ? "多人间" : null))
})
//计算属性：根据被选中的卡片索引确定预缴金额
const earnest = computed(() => {
    return selectedCardIndex.value === 0 ? 1700 : (selectedCardIndex.value === 1 ? 1400 : 1000)
})
//定义账户余额
const balance = ref()
//计算属性：根据预缴金额和余额确定最低预缴金额
const minValue = computed(() => {
    return Math.max(0, earnest.value - balance.value); // 确保最小值不小于0
});
//判断输入值是否有效
function validateInput(value) {
    const numberValue = Number(value);
    if (isNaN(numberValue) || numberValue < minValue.value) {
        recharge.value = minValue.value; // 如果输入无效或小于 minValue，则重置为 minValue
    } else {
        recharge.value = numberValue; // 否则，更新 recharge 的值
    }
}
//发送get请求获取当前用户的账户余额
function getBalance() {
    axios.get(`/user/selectBalance/${loginUser.uid}`, {
    })
        .then(function (response) {
            //表格数据
            balance.value = response.data.data.balance
            loginUser.balance = balance.value
        }
        ).catch(function (error) {
            console.log(error);
        });
}

const showDialog = ref(false) // 控制支付弹框的显示与隐藏
const recharge = ref() // 充值金额
const rechargeType = ref()//充值方式
function openDialog() {
    showDialog.value = true; // 显示支付弹框
    recharge.value = minValue.value; // 重置支付金额为最小值
}
function closeDialog() {
    showDialog.value = false; // 隐藏支付弹框
}

// 修改支付函数，保存状态
const payHandle = async () => {
    // 余额不足，跳转到支付页面并传递当前进度
    if (paymentList.value[selectedPaymentIndex.value].name === '支付宝') {
        // 调用支付宝支付接口，传递必要参数
        localStorage.setItem('eid', routeEid)
        try {
            const orderId = `RECHARGE_${Date.now()}${loginUser.uid}`
            const amount = recharge.value
            const subject = '养老院签约定金'
            const htmlData = await createAlipayPayment(orderId, amount, subject);
            submitAlipayForm(htmlData);

        } catch (error) {
            console.error('支付失败:', error);
        }

    }
    else if (selectedPaymentIndex.value != null) {
        axios.put("/user/rechargeBalance", {
            uid: loginUser.uid,
            money: recharge.value,
        }).then(function (response) {
            console.log(response)
            getBalance()
            addIn()
        }).catch(function (error) {
            console.log(error);
        }),
            closeDialog()
        ElMessage({
            message: '充值成功',
            type: 'success',
        })
    }
    else {
        ElMessage({
            message: '请选择支付方式',
            type: 'error',
        })
    }

}
//取消充值事件
function cancelPay() {
    closeDialog()
    ElMessage({
        message: '充值已取消',
        type: 'warning',
    })
}

const selectedPaymentIndex = ref<number | null>(null)
function selectPayment(index: number) {
    selectedPaymentIndex.value = index; // 更新选中索引
    rechargeType.value = paymentList.value[index].name;

}
//支付方式图标列表
const paymentList = ref([
    { imgSrc: "/src/images/AliPay.png", name: '支付宝' },
    { imgSrc: "/src/images/WeChatPay.png", name: '微信支付' },
    { imgSrc: "/src/images/yunShanFuPay.png", name: '云闪付' },
]);
interface Elderly {
    bookerName: string;
    bookerTime: string;
    eid: number;
    elderlyAddress: string;
    elderlyBirth: string;
    elderlyIdCard: string;
    elderlyName: string;
    elderlyPhone: string;
    elderlySex: string;
    isHealth: string;
    isReserved: number;
    isVoluntaryOccupancy: number;
    note: string;
    uid: number;
}
const elderly = reactive<Elderly>({})

async function signCheckIn() {
    try {
        // 1. 更新预订状态
        const updateRes = await axios.put(`/reserve/updateReserveIsReserved/${routeEid}`);
        const elderData = updateRes.data.data
        elderly.elderlyName = elderData.elderlyName,
            elderly.elderlySex = elderData.elderlySex,
            elderly.elderlyBirth = elderData.elderlyBirth,
            elderly.elderlyIdCard = elderData.elderlyIdCard,
            elderly.elderlyPhone = elderData.elderlyPhone,
            elderly.elderlyAddress = elderData.elderlyAddress,
            elderly.isHealth = elderData.isHealth,
            elderly.bookerName = elderData.bookerName,
            elderly.bookerTime = elderData.bookerTime,
            elderly.isVoluntaryOccupancy = elderData.isVoluntaryOccupancy,
            elderly.note = elderData.note,
            elderly.isReserved = elderData.isReserved
        // 2. 扣除余额
        await axios.put(`/user/deductBalance`, { uid: loginUser.uid, money: earnest.value });

        // 3. 记录交易
        await transactionDeduct();

        // 4. **创建老人信息（核心合同操作）**
        await axios.post(`/elderly/addNewElderly`, {
            eid: routeEid,
            elderlyName: elderly.elderlyName,
            elderlySex: elderly.elderlySex,
            elderlyBirth: elderly.elderlyBirth,
            elderlyIdCard: elderly.elderlyIdCard,
            elderlyPhone: elderly.elderlyPhone,
            elderlyAddress: elderly.elderlyAddress,
            isHealth: elderly.isHealth,
            bookerName: elderly.bookerName,
            bookerTime: elderly.bookerTime,
            isVoluntaryOccupancy: elderly.isVoluntaryOccupancy,
            note: elderly.note,
            isReserved: elderly.isReserved,
            balance: earnest.value,
            uid: loginUser.uid,
            roomType: selectedBedType.value
        });
        const now = new Date();
        const isoDateTime = now.toISOString();
        const response = await axios.put('/today/incrementNewContracts', {
            date: isoDateTime
        });
        // 5. 更新合同计数（本地 + 后端）
        getBalance()

        ElMessage.success('签约成功！');
    } catch (error) {
        ElMessage.error('签约失败：' + error.message);
    }
}


const addIn = () => {
    axios.post("/transaction/addIn", {
        uid: loginUser.uid,
        transactionMoney: recharge.value,
        transactionType: rechargeType.value,
        description: `为${loginUser.nickname}账户充值`,
        inOrOut: 1
    }).then(function (response) {
        console.log(response);
    }).catch(function (error) {
        console.log(error);
    })
}
const transactionDeduct = () => {
    axios.post("/transaction/addIn", {
        uid: loginUser.uid,
        transactionMoney: earnest.value,
        transactionType: '账户余额',
        description: `为${elderly.elderlyName}的老人账户充值`,
        inOrOut: 0
    }).then(function (response) {
        console.log(response);
    }).catch(function (error) {
        console.log(error);
    })
}

//挂载
onMounted(() => {
    if (localStorage.getItem('eid')) {
        fetchData(localStorage.getItem('eid'))
        localStorage.removeItem('eid')
    }
    fetchData(routeEid)
    fetchBedInfo()
    getBalance()
})
</script>
<style scoped>
.container {
    padding: 20px;
    padding-bottom: 60px;
    background-image: url(../../../assets/images/home_app_background.jpg);
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    min-height: 80vh;
}

.header {
    display: flex;
    align-items: center;
    gap: 20px;
    margin-bottom: 20px;
}

.header h2 {
    padding: 0;
    margin: 0;
}

.step-box {
    margin-bottom: 20px;
}

.step-container {
    background: white;
    border-radius: 0 0 10px 10px;
    padding: 15px;
    margin-bottom: 15px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.navigation-buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.dialog-footer {
    display: flex;
    justify-content: flex-end;
}

.footer-text {

    z-index: 10;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    text-align: center;
    font-size: 14px;
    color: #008080;
    padding: 10px 0;
}

.back-arrow {
    font-size: 24px;
    cursor: pointer;
}

/* .bed-selection {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
} */

.roomCard {
    /* flex: 0 0 80%; */
    margin-bottom: 10px;
}

/* .roomCard:nth-child(n+2) {
    flex: 0 0 48%;
    margin: 0 1px;
} */

.roomCard.selected-card {
    border: 2px solid #409EFF;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.room-image {
    width: 250px;
    height: 150px;
    display: block;
    margin: 0 auto;
    margin-bottom: 10px;
}

.reserve_elderly_info {
    border-radius: 0 0 10px 10px;
    background-color: #f9f9f9;
    padding: 20px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.info-item {
    display: flex;
    justify-content: space-between;
    padding: 10px 0;
    border-bottom: 1px solid #e0e0e0;
}

.info-item:last-child {
    border-bottom: none;
}

.info-label {
    font-weight: bold;
    color: #333;
}

.info-value {
    color: #666;
}

.footer_info {
    display: flex;
    justify-content: space-between;
}

/* .roomCard:nth-child(n+2) .footer_info {
    display: flex;
    flex-direction: column;
} */

.pay-dialog {
    padding: 20px;
    /* 内边距 */
    background-color: #f9f9f9;
    /* 背景色 */
    border-radius: 10px;
    /* 圆角 */
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    /* 阴影效果 */
}

.pay-dialog-content {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 20px;
    /* 内容底部间距 */
}

.recharge-label {
    margin-right: 30px;
    /* 标签右边距 */
    font-weight: bold;
    /* 标签加粗 */
}

.recharge-input {
    width: 240px;
    /* 输入框宽度 */
}

.payment-container {
    display: flex;
    justify-content: center;
    /* 居中对齐 */
    margin-bottom: 20px;
    /* 底部间距 */
    gap: 5px;
}

.paymentBox {
    display: flex;
    flex-direction: column;
    /* 垂直排列 */
    align-items: center;
    /* 水平居中 */
    text-align: center;
    /* 文字居中 */
    padding: 10px;
    /* 内边距 */
    transition: border 0.3s;
    /* 添加过渡效果 */
}

.selected-payment {
    border: 2px solid #409EFF;
    /* 选中时的边框颜色 */
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    /* 选中时的阴影效果 */
}

.payment-image {
    width: 50px;
    /* 图片宽度 */
    height: auto;
    /* 保持比例 */
    margin-bottom: 10px;
    /* 图片与文字之间的间距 */
}

.recharge-buttons {
    display: flex;
    justify-content: space-between;
    /* 按钮之间的间距 */
}
</style>
