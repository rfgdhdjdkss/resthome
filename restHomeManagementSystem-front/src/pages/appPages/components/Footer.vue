<template>
    <div class="footer">
        <div class="menu-item" :class="{ active: selectedMenu === 'home' }"
            @click="selectMenu('home'); router.push({ name: 'Home_app' })">
            <i class="iconfont icon-home">
                <svg t="1739000584545" class="icon" viewBox="0 0 1092 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="6193" width="35" height="25">
                    <path
                        d="M885.963619 938.597149V369.526974L546.200872 97.621203 206.301592 369.526974v569.070175h153.804595V630.305293c-0.068267-23.620235 19.319441-42.734876 43.281008-42.734876h285.559086c23.893301 0 43.349276 19.114641 43.281009 42.734876v308.291856h153.736329z m-439.295415 0h199.065335v-265.55698H446.668204v265.55698z m629.554361-526.744898a42.25701 42.25701 0 0 1 6.348792 60.074586 43.212742 43.212742 0 0 1-33.518889 15.769579 42.803143 42.803143 0 0 1-27.30663-9.55732l-49.220201-39.389814v542.514476a43.007943 43.007943 0 0 1-43.281009 42.734877H163.020583a43.007943 43.007943 0 0 1-43.281009-42.734877V438.749282l-49.220201 39.389814a43.690608 43.690608 0 0 1-60.825519-6.212259 42.25701 42.25701 0 0 1 6.348791-60.074586L519.030775 9.557321a43.622342 43.622342 0 0 1 54.40846 0l502.78333 402.29493z m-418.542375 61.439918A43.007943 43.007943 0 0 1 614.399181 430.489026a43.007943 43.007943 0 0 1 43.281009-42.734876h96.255871a43.007943 43.007943 0 0 1 43.281009 42.734876 43.007943 43.007943 0 0 1-43.281009 42.66661h-96.255871z"
                        p-id="6194" :fill="selectedMenu === 'home' ? '#00bfa5' : '#000'"></path>
                </svg>
            </i>
            <p :style="{ color: selectedMenu === 'home' ? '#00bfa5' : '#000' }">首页</p>
        </div>
        <div class="menu-item" :class="{ active: selectedMenu === 'health' }"
            @click="selectMenu('health'); router.push({ name: 'HealthList_app' })">
            <i class="iconfont icon-square">
                <svg t="1739001003950" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="15269" width="35" height="25">
                    <path
                        d="M511.823448 927.073103L122.703448 575.911724C30.366897 481.633103 9.004138 363.873103 64.088276 252.468966c46.609655-94.455172 142.124138-155.365517 243.111724-155.365518h1.412414c71.856552 0 156.601379 46.08 205.153103 76.8 56.32-33.544828 141.594483-76.8 203.917242-76.8h1.235862c102.223448 0 195.266207 59.674483 242.75862 155.542069 51.906207 105.048276 31.77931 219.10069-56.849655 321.324138l-3.884138 3.884138c-130.471724 117.583448-359.635862 322.913103-359.635862 322.913104l-29.484138 26.306206zM184.673103 513.05931L512 808.253793c65.677241-58.791724 225.986207-202.504828 327.856552-294.311724 44.491034-52.082759 87.216552-132.413793 42.725517-222.278621-32.485517-65.677241-95.337931-106.46069-164.016552-106.460689h-0.529655c-34.251034 0-106.46069 31.073103-180.25931 77.329655l-3.177931 1.942069-21.539311 12.182069-21.53931-12.182069c-0.353103-0.176552-2.118621-1.235862-4.943448-3.177931-54.201379-37.075862-130.118621-76.270345-178.670345-76.093793-68.148966 0-132.413793 41.842759-164.546207 106.460689-37.958621 76.446897-23.128276 155.012414 41.313103 221.395862z"
                        :fill="selectedMenu === 'health' ? '#00bfa5' : '#000'" p-id="15270"></path>
                    <path
                        d="M670.543448 469.627586h-123.586207v-132.413793h-70.620689v132.413793h-123.586207v52.965517h123.586207v132.413794h70.620689v-132.413794h123.586207z"
                        :fill="selectedMenu === 'health' ? '#00bfa5' : '#000'" p-id="15271"></path>
                </svg>
            </i>
            <p :style="{ color: selectedMenu === 'health' ? '#00bfa5' : '#000' }">健康</p>
        </div>
        <div class="menu-item" :class="{ active: selectedMenu === 'order' }" @click="selectMenu('order');router.push({name:'FoodsList_app'})">
            <i class="iconfont icon-community">
                <svg t="1739000886351" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="9184" width="35" height="25">
                    <path
                        d="M870.4 716.8H153.6a51.2 51.2 0 0 1-51.2-51.2v-51.2a409.6 409.6 0 0 1 307.2-396.288V204.8a102.4 102.4 0 0 1 204.8 0v13.312A409.6 409.6 0 0 1 921.6 614.4v51.2a51.2 51.2 0 0 1-51.2 51.2z m-358.4-409.6a307.2 307.2 0 0 0-307.2 307.2h614.4a307.2 307.2 0 0 0-307.2-307.2zM153.6 819.2h716.8a51.2 51.2 0 0 1 0 102.4H153.6a51.2 51.2 0 0 1 0-102.4z"
                        :fill="selectedMenu === 'order' ? '#00bfa5' : '#000'" p-id="9185"></path>
                </svg>
            </i>
            <p :style="{ color: selectedMenu === 'order' ? '#00bfa5' : '#000' }">点餐</p>
        </div>
        <div class="menu-item" :class="{ active: selectedMenu === 'chat' }"
            @click="selectMenu('chat'); router.push({ name: 'Chat_app' })">
            <i class="iconfont icon-chat">
                <svg t="1739000927471" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="11185" width="35" height="25">
                    <path
                        d="M810.65 469.15V213.13c0-23.59-19.11-42.67-42.67-42.67H127.94c-14.79 0-28.52 7.67-36.29 20.25s-8.48 28.29-1.88 41.5l38.17 76.34v160.59c0 23.59 19.11 42.67 42.67 42.67h597.38c23.56 0 42.66-19.08 42.66-42.66z m-85.34-42.67H213.27V298.47c0-6.63-1.54-13.17-4.5-19.08l-11.79-23.59h528.33v170.68zM932.29 574.74A42.662 42.662 0 0 0 896 554.49H383.95c-23.56 0-42.67 19.08-42.67 42.67V810.5c0 23.59 19.11 42.67 42.67 42.67h469.37c23.56 0 42.67-19.08 42.67-42.67V692.58l38.17-76.34a42.69 42.69 0 0 0-1.87-41.5z m-117.14 88.67a42.685 42.685 0 0 0-4.5 19.08v85.34H426.62V639.82h400.32l-11.79 23.59z"
                        :fill="selectedMenu === 'chat' ? '#00bfa5' : '#000'" p-id="11186"></path>
                </svg>
            </i>
            <p :style="{ color: selectedMenu === 'chat' ? '#00bfa5' : '#000' }">聊天</p>
        </div>
        <div class="menu-item" :class="{ active: selectedMenu === 'profile' }"
            @click="selectMenu('profile'); router.push({ name: 'Mine_app' })">
            <i class="iconfont icon-user">
                <svg t="1739000961325" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="12387" width="35" height="25">
                    <path
                        d="M895.2 960.8H154.4c-70.4 0-128-56.8-128-128v-76.8c0-127.2 103.2-229.6 229.6-229.6h144.8C315.2 484.8 256 397.6 256 296.8c0.8-140.8 115.2-255.2 256-255.2s255.2 114.4 255.2 255.2c0 101.6-59.2 188.8-144.8 229.6h144.8c127.2 0 229.6 103.2 229.6 229.6v102.4c0.8 56.8-45.6 102.4-101.6 102.4z m-229.6-664C665.6 212.8 596.8 144 512 144s-153.6 68.8-153.6 153.6S427.2 450.4 512 450.4s153.6-68.8 153.6-153.6z m229.6 460c0-70.4-56.8-128-128-128H256.8c-70.4 0-128 56.8-128 128v51.2c0 28 23.2 51.2 51.2 51.2h664c28 0 51.2-23.2 51.2-51.2v-51.2z"
                        :fill="selectedMenu === 'profile' ? '#00bfa5' : '#000'" p-id="12388"></path>
                </svg>
            </i>
            <p :style="{ color: selectedMenu === 'profile' ? '#00bfa5' : '#000' }">我的</p>
        </div>
    </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';
let router = useRouter()

const props = defineProps({
    menuType: String,
});
const selectedMenu = ref(props.menuType);

const selectMenu = (menu) => {
    selectedMenu.value = menu;
};
</script>

<style scoped>
.footer {
    display: flex;
    justify-content: space-around;
    align-items: center;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    max-width: 430px;
    height: 60px;
    background-color: #fff;
    border-top: 1px solid #e0e0e0;
    box-shadow: 0 -1px 5px rgba(0, 0, 0, 0.1);
    z-index: 1000;
}

.menu-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 12px;
    color: #666;
}

.iconfont {
    margin-bottom: 5px;
}
</style>
