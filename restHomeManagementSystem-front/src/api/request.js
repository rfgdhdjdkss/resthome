import axios from "axios";
import router from "../routers/routes";
import { definedUser } from '../stores/index.js';
import { ElMessage } from 'element-plus';

// 定义一个标志变量，用于标记是否已经显示过登录失效提示信息，初始化为false，表示还未显示过
let hasShownLoginExpiredMessage = false;

const _axios = axios.create({
    baseURL: 'http://localhost:8999/',
    timeout: 5000
});

// 请求拦截器
_axios.interceptors.request.use((config) => {
    const requestPath = config.url;
    const isLoginRequest = requestPath.startsWith('/user/login');
    const isRegisterRequest = requestPath.startsWith('/user/register');
    let loginUser = definedUser();

    if (!isLoginRequest && !isRegisterRequest) {
        const token = loginUser.token;
        if (token) {
            config.headers.Authorization = token;
        } else {
            // 如果没有token，可以根据实际情况进行处理，比如跳转到登录页等
            if (!hasShownLoginExpiredMessage) {

                console.error('没有找到认证token，请先登录');
                ElMessage({
                    message: '请先登录',
                    type: 'error',
                });
                hasShownLoginExpiredMessage = true
                router.push({ name: 'Login_app' });
            }
        }
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

// 响应拦截器（可根据需要添加，用于处理响应相关的情况，比如根据不同状态码做不同处理等）
_axios.interceptors.response.use((response) => {
    return response;
}, (error) => {
    // 可以在这里对响应错误进行处理，比如根据不同的状态码给出提示等
    if (error.response) {
        // 获取后端返回的状态码
        const statusCode = error.response.status;
        if (statusCode === 401) {
            if (!hasShownLoginExpiredMessage) {
                let loginUser = definedUser();
                ElMessage({
                    message: '登录状态失效,请先登录',
                    type: 'error',
                });
                localStorage.removeItem('token');
                loginUser.$reset();
                router.push({ name: 'Login_app' });
                // 将标志变量置为true，表示已经显示过登录失效提示信息了
                hasShownLoginExpiredMessage = true;
            }
        }
    }
    console.error('请求出错：', error);
    return Promise.reject(error);
});

export default _axios;