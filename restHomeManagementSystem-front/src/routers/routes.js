import { createRouter, createWebHashHistory } from 'vue-router'
import { useRouterStore } from '../stores/routerStore';
const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        //app 路由
        {
            children:
                [
                    {
                        path: '/login_app',
                        name: 'Login_app',
                        meta: { title: ' app端登录' },
                        component: () => import('../pages/appPages/loginAndRegister/Login.vue'),
                    },
                    {
                        path: '/register_app',
                        name: 'Register_app',
                        meta: { title: ' app端注册' },
                        component: () => import('../pages/appPages/loginAndRegister/Register.vue'),
                    },
                    {
                        path: '/pensionAgreement',
                        name: 'PensionAgreement',
                        meta: { title: ' 养老协议' },
                        component: () => import('../pages/appPages/loginAndRegister/PensionAgreement.vue'),
                    },
                    {
                        path: '/privacyPolicy',
                        name: 'PrivacyPolicy',
                        meta: { title: ' 养老隐私政策' },
                        component: () => import('../pages/appPages/loginAndRegister/PrivacyPolicy.vue'),
                    }, {
                        path: '/layout',
                        name: 'Layout',
                        meta: { title: ' 布局' },
                        component: () => import('../pages/appPages/Layout.vue'),
                    }, {
                        path: '/home_app',
                        name: 'Home_app',
                        meta: { title: ' 首页' },
                        component: () => import('../pages/appPages/home/Home.vue'),
                    }, {
                        path: '/goodsDetail_app/:gid',
                        name: 'GoodsDetail_app',
                        meta: { title: ' 商品详情页' },
                        component: () => import('../pages/appPages/home/GoodsDetail.vue'),
                    }, {
                        path: '/healthList_app',
                        name: 'HealthList_app',
                        meta: { title: ' 健康' },
                        component: () => import('../pages/appPages/health/HealthList.vue'),
                    }, {
                        path: '/healthList_app/healthDetail_app/:eid',
                        name: 'HealthDetail_app',
                        meta: { title: ' 健康详情' },
                        component: () => import('../pages/appPages/health/HealthDetail.vue'),
                    }, {
                        path: '/healthRecordMore_app',
                        name: 'HealthRecordMore_app',
                        meta: { title: '查看更多健康记录' },
                        component: () => import('../pages/appPages/health/HealthRecordMore.vue'),
                    }, {
                        path: '/mine_app',
                        name: 'Mine_app',
                        meta: { title: ' 我的' },
                        component: () => import('../pages/appPages/mine/Mine.vue'),
                    }, {
                        path: '/reserve_app',
                        name: 'Reserve_app',
                        meta: { title: ' 预订' },
                        component: () => import('../pages/appPages/Reserve.vue'),
                    }, {
                        path: '/signCheckInList_app',
                        name: 'SignCheckInList_app',
                        meta: { title: ' 入住签约列表' },
                        component: () => import('@/pages/appPages/signCheckIn/SignCheckInList.vue'),

                    }, {
                        path: '/signCheckInDetail_app/:eid',
                        name: 'SignCheckInDetail_app',
                        meta: { title: ' 入住签约列表' },
                        component: () => import('@/pages/appPages/signCheckIn/SignCheckInDetail.vue'),
                    },
                    {
                        path: '/chat_app',
                        name: 'Chat_app',
                        meta: { title: '聊天' },
                        component: () => import('../pages/appPages/chat/Chat.vue'),
                    },
                    {
                        path: '/personalCenter_app',
                        name: 'PersonalCenter_app',
                        meta: { title: '个人中心' },
                        component: () => import('../pages/appPages/mine/PersonalCenter.vue'),
                    },
                    {
                        path: '/setting_app',
                        name: 'Setting_app',
                        meta: { title: '设置' },
                        component: () => import('../pages/appPages/mine/Setting.vue'),
                    }, {
                        path: '/modifyPhone_app',
                        name: 'ModifyPhone_app',
                        meta: { title: '修改手机号' },
                        component: () => import('../pages/appPages/mine/ModifyPhone.vue'),
                    }, {
                        path: '/modifyPassword_app',
                        name: 'ModifyPassword_app',
                        meta: { title: '修改密码' },
                        component: () => import('../pages/appPages/mine/ModifyPassword.vue'),
                    }, {
                        path: '/aboutResthome_app',
                        name: 'AboutResthome_app',
                        meta: { title: '关于养老生活' },
                        component: () => import('../pages/appPages/mine/AboutResthome.vue'),
                    }, {
                        path: '/shippingAddressList_app',
                        name: 'ShippingAddressList_app',
                        meta: { title: '收货地址' },
                        component: () => import('../pages/appPages/mine/shippingAddress/ShippingAddressList.vue'),
                    }, {
                        path: '/selectShippingAddressList_app',
                        name: 'SelectShippingAddressList_app',
                        meta: { title: '收货地址' },
                        component: () => import('../pages/appPages/mine/shippingAddress/SelectShippingAddressList.vue'),
                    }, {
                        path: '/createNewAddress_app',
                        name: 'CreateNewAddress_app',
                        meta: { title: '新增收货地址' },
                        component: () => import('../pages/appPages/mine/shippingAddress/CreateNewAddress.vue'),
                    },
                    {
                        path: '/shippingAddressDetail_app/:raid',
                        name: 'ShippingAddressDetail_app',
                        meta: { title: '编辑收货地址' },
                        component: () => import('../pages/appPages/mine/shippingAddress/ShippingAddressDetail.vue'),
                    }, {
                        path: '/shippingCart_app',
                        name: 'ShippingCart_app',
                        meta: { title: '购物车' },
                        component: () => import('../pages/appPages/home/ShippingCart.vue'),
                    },
                    {
                        path: '/registrationApplication_app',
                        name: 'RegistrationApplication_app',
                        meta: { title: '申请：来访/外出' },
                        component: () => import('@/pages/appPages/registration/RegistrationApplication.vue'),
                    }, {
                        path: '/inRegistration_app',
                        name: 'InRegistration_app',
                        meta: { title: '来访登记' },
                        component: () => import('../pages/appPages/registration/InRegistration.vue'),
                    }, {
                        path: '/outRegistration_app',
                        name: 'OutRegistration_app',
                        meta: { title: '外出登记' },
                        component: () => import('../pages/appPages/registration/OutRegistration.vue'),
                    }, {
                        path: '/myApplication_app',
                        name: 'MyApplication_app',
                        meta: { title: '我的申请' },
                        component: () => import('../pages/appPages/registration/MyApplication.vue'),
                    },
                    {
                        path: '/recharge_app',
                        name: 'Recharge_app',
                        meta: { title: '充值' },
                        component: () => import('../pages/appPages/recharge/Recharge.vue'),
                    }, {
                        path: '/submitOrder_app',
                        name: 'SubmitOrder_app',
                        meta: { title: '提交订单' },
                        component: () => import('../pages/appPages/order/SubmitOrder.vue'),
                    },
                    {
                        path: '/checkOut_app',
                        name: 'CheckOut_app',
                        meta: { title: '退住' },
                        component: () => import('../pages/appPages/home/CheckOut.vue'),
                    }, {
                        path: '/elderlyDocumentList_app',
                        name: 'ElderlyDocumentList_app',
                        meta: { title: '老人档案' },
                        component: () => import('../pages/appPages/home/elderlyDocument/ElderlyDocumentList.vue'),
                    }, {
                        path: '/createNewDocument_app',
                        name: 'CreateNewDocument_app',
                        meta: { title: '新建老人档案' },
                        component: () => import('../pages/appPages/home/elderlyDocument/CreateNewDocument.vue'),
                    },
                    {
                        path: '/testPay',
                        name: 'testPay',
                        meta: { title: '测试支付' },
                        component: () => import('../pages/appPages/testPay.vue'),
                    }, {
                        path: '/payment-success',
                        name: 'PaymentSuccess',
                        meta: { title: '支付成功' },
                        component: () => import('../pages/appPages/PaymentSuccess.vue'),
                    },
                    {
                        path: '/pay_app/:oid',
                        name: 'Pay_app',
                        meta: { title: '支付订单' },
                        component: () => import('../pages/appPages/order/Pay.vue'),
                    },
                    {
                        path: '/myOrder_app',
                        name: 'MyOrder_app',
                        meta: { title: '我的订单' },
                        component: () => import('../pages/appPages/order/MyOrder.vue'),
                    },
                    {
                        path: '/paymentSuccess',
                        name: 'paymentSuccess',
                        meta: { title: '我的订单' },
                        component: () => import('../pages/appPages/PaymentSuccess.vue'),
                    }, {
                        path: '/goods-comment',
                        name: 'GoodsComment',
                        component: ()=>import('../pages/appPages/order/GoodsCommentPage.vue')
                    },
                    {
                        path: '/transactionRecord_app',
                        name: 'TransactionRecordPage_app',
                        component: ()=>import('../pages/appPages/mine/TransactionRecordPage.vue')
                    }
                    
                ]
        },
        // 后台管理路由
        {
            children: [
                //登录
                {
                    path: '/',
                    name: 'Login',
                    meta: { title: '登录' },
                    component: () => import('../pages/managementPages/Login.vue'),
                },
                //注册
                {
                    path: '/register',
                    name: 'Register',
                    meta: { title: '注册' },
                    component: () => import('../pages/managementPages/Register.vue'),
                },
                // 首页Home
                {
                    path: '/home',
                    name: 'Home',
                    meta: {
                        title: '首页'
                    },
                    component: () => import('../pages/managementPages/Home.vue'),

                },
                //我的申请路由
                {
                    path: '/myApplication',
                    component: () => import('../pages/managementPages/Person/MyApplication.vue'),
                    name: 'MyApplication',
                    meta: {
                        title: '我的申请'
                    }
                },
                // 营销管理
                {
                    path: '/marketing',
                    name: 'Marketing',
                    meta: {
                        title: '营销管理'
                    },
                    redirect: '/managementPages/marketing/reserve',
                    children: [
                        {
                            //咨询管理路由(普通用户)
                            path: '/marketing/consult/:whichUidConsult',
                            component: () => import('../pages/managementPages/Marketing/Consult.vue'),
                            name: 'Consult',
                            meta: {
                                title: '咨询管理'
                            },
                        },
                        {
                            //咨询管理路由（管理员）
                            path: '/marketing/consultCollection',
                            component: () => import('../pages/managementPages/Marketing/ConsultCollection.vue'),
                            name: 'ConsultCollection',
                            meta: {
                                title: '咨询管理'
                            }
                        },
                        {
                            //预定管理路由
                            path: '/marketing/reserve',
                            component: () => import('../pages/managementPages/Marketing/Reserve.vue'),
                            name: 'Reserve',
                            meta: {
                                title: '预定入住'
                            }
                        },
                        {
                            //预定管理路由
                            path: '/marketing/reserveManagement',
                            component: () => import('../pages/managementPages/Marketing/ReserveManagement.vue'),
                            name: 'ReserveManagement',
                            meta: {
                                title: '预定管理'
                            }
                        }
                    ]
                },
                //入住管理
                {
                    path: '/checkIn',
                    name: 'CheckIn',
                    meta: {
                        title: '入住管理'
                    },
                    redirect: { name: 'SignCheckInManagement' }, // 使用命名路由进行重定向
                    children: [
                        {
                            path: 'signCheckInManagement', // 修改为相对路径
                            component: () => import('../pages/managementPages/CheckIn/SignCheckInManagement.vue'),
                            name: 'SignCheckInManagement',
                            meta: {
                                title: '入住签约管理'
                            },
                        },
                        {
                            path: '/checkIn/signCheckIn/:eid',
                            component: () => import('../pages/managementPages/CheckIn/SignCheckIn.vue'),
                            name: 'SignCheckIn',
                            meta: {
                                title: '入住签约'
                            },
                        },
                        {
                            //外出登记路由
                            path: '/checkIn/outRegistration',
                            component: () => import('../pages/managementPages/CheckIn/OutRegistration.vue'),
                            name: 'OutRegistration',
                            meta: {
                                title: '外出登记'
                            }
                        },
                        {
                            //来访登记路由
                            path: '/checkIn/inRegistration',
                            component: () => import('../pages/managementPages/CheckIn/InRegistration.vue'),
                            name: 'InRegistration',
                            meta: {
                                title: '来访登记'
                            }
                        },
                        {
                            //退住申请路由
                            path: '/checkIn/checkOut',
                            component: () => import('../pages/managementPages/CheckIn/CheckOut.vue'),
                            name: 'CheckOut',
                            meta: {
                                title: '退住申请'
                            }
                        },
                    ]
                },
                //人员管理
                {
                    path: '/person',
                    name: 'Person',
                    meta: {
                        title: '人员管理'
                    },
                    redirect: '/person/elderlyInformation',
                    children: [
                        {
                            //老人信息路由
                            path: '/person/elderlyInformation',
                            component: () => import('../pages/managementPages/Person/ElderlyInformation.vue'),
                            name: 'ElderlyInformation',
                            meta: {
                                title: '老人信息'
                            }
                        },
                        {
                            //老人信息管理路由（管理员+员工）
                            path: '/person/elderlyInformationManagement',
                            component: () => import('../pages/managementPages/Person/ElderlyInformationManagement.vue'),
                            name: 'ElderlyInformationManagement',
                            meta: {
                                title: '老人信息管理'
                            }
                        },
                        {
                            //员工管理路由
                            path: '/person/userManagement',
                            component: () => import('../pages/managementPages/Person/UserManagement.vue'),
                            name: 'UserManagement',
                            meta: {
                                title: '用户管理'
                            }
                        },
                        {
                            //个人中心路由
                            path: '/person/personalCenter',
                            component: () => import('../pages/managementPages/Person/PersonalCenter.vue'),
                            name: 'PersonalCenter',
                            meta: {
                                title: '个人中心'
                            }
                        },
                    ]
                },
                //餐饮管理
                {
                    path: '/catering',
                    name: 'Catering',
                    meta: {
                        title: '餐饮管理'
                    },
                    redirect: '/catering/dishes',
                    children: [
                        {
                            //菜品管理路由
                            path: '/catering/dishes',
                            component: () => import('../pages/managementPages/Catering/Dishes.vue'),
                            name: 'Dishes',
                            meta: {
                                title: '菜品管理'
                            }
                        },
                        {
                            //餐饮套餐路由
                            path: '/catering/combo',
                            component: () => import('../pages/managementPages/Catering/Combo.vue'),
                            name: 'Combo',
                            meta: {
                                title: '餐饮套餐'
                            }
                        },
                        {
                            //点餐路由
                            path: '/catering/order',
                            component: () => import('../pages/managementPages/Catering/Order.vue'),
                            name: 'Order',
                            meta: {
                                title: '点餐'
                            }
                        },
                    ]
                },
                //费用管理
                {
                    path: '/cost',
                    name: 'Cost',
                    meta: {
                        title: '费用管理'
                    },
                    redirect: '/cost/recharge ',
                    children: [
                        {
                            //预付充值路由
                            path: '/cost/recharge',
                            component: () => import('../pages/managementPages/Cost/Recharge.vue'),
                            name: 'Recharge',
                            meta: {
                                title: '预付充值'
                            }
                        },
                        {
                            //消费记录路由
                            path: '/cost/costRecord',
                            component: () => import('../pages/managementPages/Cost/CostRecord.vue'),
                            name: 'CostRecord',
                            meta: {
                                title: '消费记录'
                            }
                        },
                        {
                            //退住费用审核路由
                            path: '/cost/costAudit',
                            component: () => import('../pages/managementPages/Cost/CostAudit.vue'),
                            name: 'CostAudit',
                            meta: {
                                title: '退住费用审核'
                            }
                        },
                    ]
                },
            ]
        }
    ]
})
router.beforeEach((to, from) => {
    const routerStore = useRouterStore();
    routerStore.setFromRoute(from); // 存储来源路由
});
// 对外暴露路由对象
export default router;