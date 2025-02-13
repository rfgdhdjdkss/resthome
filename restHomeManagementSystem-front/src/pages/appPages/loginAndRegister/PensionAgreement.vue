<template>
  <div class="agreement-page">
    <div class="header">
      <span class="close-button" @click="closeThisPage">×</span>
      <h1>养老用户协议</h1>
    </div>
    <div class="content">
      <div style="width: 100%; display: flex; align-items: center;justify-content: center;">
        <p style="margin-bottom: 0; font-size: 20px; font-weight: 700;" >养老用户协议</p>
      </div>
      <h2>特别提示：</h2>
      <p>欢迎您使用养老相关产品及服务！</p>
      <p>
        为使用养老平台服务，您应当阅读并遵守《养老用户协议》（以下简称“本协议”）。
        请您在进一步操作之前仔细阅读、充分理解本协议的各个条款，特别是以加粗形式提示您注意的条款。
        您有权选择同意或不同意本协议，您点击“我已阅读并同意”按钮即视为您已阅读并接受本协议的所有条款。
      </p>
      <p>
        您完成注册程序或使用第三方账号登录养老平台，使用养老平台服务，即视为您已充分阅读、理解本协议，
        并自愿受本协议的约束。阅读本协议过程中，如果您不同意本协议或其中任何条款，请立即停止注册程序并停止使用养老平台服务。
      </p>
      <p>本“特别提示”是本协议正文的组成部分。</p>
      <h2>
        一、服务内容与标准
      </h2>
      <p>
        机构将按照双方约定，为您提供包括但不限于生活照料、医疗护理、康复保健、文化娱乐等服务。服务质量应符合国家相关标准和行业规范，确保您在机构内得到安全、舒适、周到的照顾。
      </p>
      <h2>
        二、费用与支付
      </h2>
      <p>
        费用明细：您需按照机构公布的收费标准或双方另行约定，支付各项服务费用，包括但不限于床位费、护理费、餐饮费、医疗费等。机构有权根据实际情况调整收费标准，但应提前一定时间通知您。
        支付方式：您应按照约定的支付周期和方式按时支付费用。如逾期未支付，机构有权按照一定比例收取滞纳金，情节严重的，机构有权暂停或终止服务。
      </p>
      <h2>
        三、双方权利与义务
      </h2>
      <h4>
        （一）您的权利
      </h4>
      <p>
        享有平等接受机构服务的权利，不受歧视。
        对机构提供的服务有知情权、建议权和监督权，有权要求机构对服务内容、质量等进行说明。
        有权自主选择适合自己的服务项目和服务方式。
        个人隐私和合法权益受法律保护，机构不得擅自泄露您的个人信息。
      </p>
      <h4>
        （二）您的义务
      </h4>
      <p>
        如实提供个人基本信息、健康状况等相关资料，不得隐瞒或虚报。
        遵守机构的各项规章制度，服从机构的管理和安排。
        与其他住养人员友好相处，不得从事影响机构正常秩序和他人合法权益的行为。
        按照约定及时支付服务费用。
      </p>
      <h4>
        （三）机构的权利

      </h4>
      <p>
        按照本协议约定收取服务费用。
        根据您的健康状况和服务需求，合理调整服务内容和方式。
        对违反机构规章制度的行为进行制止和处理。
      </p>
      <h4>
        （四）机构的义务

      </h4>
      <p>
        按照约定为您提供符合质量标准的养老服务。
        配备必要的设施设备和专业服务人员，保障服务的正常开展。
        建立健全安全管理制度，保障您的人身和财产安全。
        定期对服务质量进行评估，不断改进服务。
      </p>
      <h2>
        四、服务变更与终止

      </h2>
      <p>
        服务变更：如因您的健康状况变化、服务需求调整等原因，需要变更服务内容或方式的，双方应协商一致，并签订书面变更协议。
        服务终止：有下列情形之一的，本协议终止：
        双方协商一致解除协议。
        您因自身原因要求提前终止服务，应提前一定时间书面通知机构，并按照约定办理相关手续。
        机构因不可抗力等原因无法继续提供服务的，应及时通知您，并协助做好善后处理工作。
        您严重违反机构规章制度，经劝阻无效的，机构有权单方面解除协议。
      </p>
      <h2>
        五、争议解决

      </h2>
      <p>
        如双方在本协议履行过程中发生争议，应首先通过友好协商解决；协商不成的，可依法向有管辖权的人民法院提起诉讼。

      </p>
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
