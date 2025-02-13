<template>
    <div class="agreement-page">
        <div class="header">
            <span class="close-button" @click="closeThisPage">×</span>
            <h1>养老隐私政策</h1>
        </div>
        <div class="content">
            <div style="width: 100%; display: flex; align-items: center;justify-content: center;">
                <p style="margin-bottom: 0; font-size: 20px; font-weight: 700;">养老隐私政策</p>
            </div>
            <h2>
                一、引言
            </h2>
            <p>
                本隐私政策旨在向您说明 养老生活 在提供养老服务过程中，对您个人信息的收集、使用、存储和保护等相关事宜。
            </p>
            <h2>
                二、个人信息的收集
            </h2>
            <h4>
                （一）基本信息
            </h4>
            <p>我们会收集您的姓名、性别、年龄、联系方式、身份证号码等基本信息，以便于为您提供服务和进行日常管理。</p>
            <h4>
                （二）健康信息
            </h4>
            <p>为了更好地照顾您的健康状况，我们会收集您的既往病史、过敏史、当前健康指标（如血压、血糖等）、用药情况等健康信息。这些信息将由专业医护人员进行管理和使用。</p>
            <h4>
                （三）居住信息
            </h4>
            <p>包括您的居住房间号、入住时间等与居住相关的信息，用于合理安排居住资源和提供针对性服务。</p>
            <h2>
                三、个人信息的使用
            </h2>
            <h4>
                （一）提供服务
            </h4>
            <p>我们使用收集到的个人信息为您提供个性化的养老服务，如根据您的健康状况安排护理计划、根据您的兴趣爱好组织文化娱乐活动等。</p>
            <h4>
                （二）安全保障
            </h4>
            <p>用于保障机构内的安全管理，如通过您的联系方式在紧急情况下与您的家属取得联系等。</p>
            <h4>
                （三）服务改进
            </h4>
            <p>分析您的信息以改进我们的服务质量和服务内容，满足您的需求。</p>
            <h2>
                四、个人信息的存储与保护
            </h2>
            <h4>
                （一）存储方式
            </h4>
            <p>我们将采用安全的数据库和存储设备对您的个人信息进行存储，设置严格的访问权限，确保信息不被未经授权的人员访问。</p>
            <h4>
                （二）安全措施
            </h4>
            <p>我们采取多种安全技术和管理措施，如数据加密、防火墙、定期备份等，保护您的个人信息免受丢失、泄露、篡改等风险。同时，对员工进行严格的保密培训，确保他们遵守隐私保护规定。</p>
            <h2>
                五、个人信息的共享与披露
            </h2>
            <h4>
                （一）共享原则
            </h4>
            <p>我们不会将您的个人信息随意共享给第三方，除非获得您的明确同意或法律法规有明确要求。在必要情况下，如与医疗机构合作进行医疗服务时，我们会确保第三方对您的信息同样采取严格的保密措施。</p>
            <h4>
                （二）披露情形
            </h4>
            <p>只有在法律要求或为了保护机构及其他住养人员的合法权益等特定情形下，我们才会披露您的个人信息。</p>
            <h2>
                六、您的权利
            </h2>
            <h4>
                （一）查询权
            </h4>
            <p>您有权随时查询我们收集的关于您的个人信息。</p>
            <h4>
                （二）更正权
            </h4>
            <p>如果您发现我们收集的个人信息存在错误，您有权要求我们进行更正。</p>
            <h4>
                （三）删除权
            </h4>
            <p>在符合法律法规规定的情形下，您有权要求我们删除您的个人信息。</p>
            <h2>
                七、政策变更
            </h2>
            <p>我们可能会根据法律法规的变化或业务发展的需要对本隐私政策进行变更。我们会在机构内显著位置或通过合适的方式通知您变更后的政策内容。</p>
            <h2>
                八、联系方式
            </h2>
            <p>如果您对本隐私政策有任何疑问、意见或建议，您可以通过以下方式与我们联系：联系电话：18357787035 电子邮箱：18357787035@163.com。</p>
        </div>
    </div>
</template>
<script setup>
import { useRouter } from 'vue-router';
let router = useRouter()
const closeThisPage = () => {
    router.go(-1)
}
</script>
<style scoped>
.agreement-page {
    box-sizing: border-box;
    width: 100%;
    max-width: 430px;
    margin: 0 auto;
}

.header {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
    background-color: #56b4b0;
    height: 80px;
    padding-left: 20px;
    color: #fff;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 999;
}

.close-button {
    font-size: 24px;
    cursor: pointer;
}

h1 {
    font-size: 16px;
    margin: 0;
    margin-left: 130px;
    font-weight: 300;
}

.content {
    font-size: 16px;
    line-height: 1.5;
    padding: 1%;
    position: relative;
    top: 80px;
}

h2 {
    font-size: 18px;
    margin-top: 20px;
}

p {
    margin: 10px 0;
}

a {
    color: #00bfa5;
    text-decoration: none;
}
</style>