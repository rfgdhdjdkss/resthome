import { createRouter, createWebHashHistory } from 'vue-router'
const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        //登录
        {
            path: '/',
            name: 'Login',
            meta: { title: '登录' },
            component: () => import('../pages/Login.vue'),
        },
        //注册
        {
            path: '/register',
            name: 'Register',
            meta: { title: '注册' },
            component: () => import('../pages/Register.vue'),
        },
        // 首页Home
        {
            path: '/home',
            name: 'Home',
            meta: {
                title: '首页'
            },
            component: () => import('../pages/Home.vue'),

        },
        //我的申请路由
        {
            path: '/myApplication',
            component: () => import('../pages/Person/MyApplication.vue'),
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
            redirect: '/marketing/reserve',
            children: [
                {
                    //咨询管理路由(普通用户)
                    path: '/marketing/consult/:whichUidConsult',
                    component: () => import('../pages/Marketing/Consult.vue'),
                    name: 'Consult',
                    meta: {
                        title: '咨询管理'
                    },
                },
                {
                    //咨询管理路由（管理员）
                    path: '/marketing/consultCollection',
                    component: () => import('../pages/Marketing/ConsultCollection.vue'),
                    name: 'ConsultCollection',
                    meta: {
                        title: '咨询管理'
                    }
                },
                {
                    //预定管理路由
                    path: '/marketing/reserve',
                    component: () => import('../pages/Marketing/Reserve.vue'),
                    name: 'Reserve',
                    meta: {
                        title: '预定入住'
                    }
                },
                {
                    //预定管理路由
                    path: '/marketing/reserveManagement',
                    component: () => import('../pages/Marketing/ReserveManagement.vue'),
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
                    component: () => import('../pages/CheckIn/SignCheckInManagement.vue'),
                    name: 'SignCheckInManagement',
                    meta: {
                        title: '入住签约管理'
                    },
                },
                {
                    path: '/checkIn/signCheckIn/:eid',
                    component: () => import('../pages/CheckIn/SignCheckIn.vue'),
                    name: 'SignCheckIn',
                    meta: {
                        title: '入住签约'
                    },
                },
                {
                    //外出登记路由
                    path: '/checkIn/outRegistration',
                    component: () => import('../pages/CheckIn/OutRegistration.vue'),
                    name: 'OutRegistration',
                    meta: {
                        title: '外出登记'
                    }
                },
                {
                    //来访登记路由
                    path: '/checkIn/inRegistration',
                    component: () => import('../pages/CheckIn/InRegistration.vue'),
                    name: 'InRegistration',
                    meta: {
                        title: '来访登记'
                    }
                },
                {
                    //退住申请路由
                    path: '/checkIn/checkOut',
                    component: () => import('../pages/CheckIn/CheckOut.vue'),
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
                    component: () => import('../pages/Person/ElderlyInformation.vue'),
                    name: 'ElderlyInformation',
                    meta: {
                        title: '老人信息'
                    }
                },
                {
                    //老人信息管理路由（管理员+员工）
                    path: '/person/elderlyInformationManagement',
                    component: () => import('../pages/Person/ElderlyInformationManagement.vue'),
                    name: 'ElderlyInformationManagement',
                    meta: {
                        title: '老人信息管理'
                    }
                },
                {
                    //员工管理路由
                    path: '/person/userManagement',
                    component: () => import('../pages/Person/UserManagement.vue'),
                    name: 'UserManagement',
                    meta: {
                        title: '用户管理'
                    }
                },
                {
                    //个人中心路由
                    path: '/person/personalCenter',
                    component: () => import('../pages/Person/PersonalCenter.vue'),
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
                    component: () => import('../pages/Catering/Dishes.vue'),
                    name: 'Dishes',
                    meta: {
                        title: '菜品管理'
                    }
                },
                {
                    //餐饮套餐路由
                    path: '/catering/combo',
                    component: () => import('../pages/Catering/Combo.vue'),
                    name: 'Combo',
                    meta: {
                        title: '餐饮套餐'
                    }
                },
                {
                    //点餐路由
                    path: '/catering/order',
                    component: () => import('../pages/Catering/Order.vue'),
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
                    component: () => import('../pages/Cost/Recharge.vue'),
                    name: 'Recharge',
                    meta: {
                        title: '预付充值'
                    }
                },
                {
                    //消费记录路由
                    path: '/cost/costRecord',
                    component: () => import('../pages/Cost/CostRecord.vue'),
                    name: 'CostRecord',
                    meta: {
                        title: '消费记录'
                    }
                },
                {
                    //退住费用审核路由
                    path: '/cost/costAudit',
                    component: () => import('../pages/Cost/CostAudit.vue'),
                    name: 'CostAudit',
                    meta: {
                        title: '退住费用审核'
                    }
                },
            ]
        },

    ]
})

// 对外暴露路由对象
export default router;