<template>
    <div class="footer-page">
        <p id="create_p">created by JinYang</p>
    </div>
</template>
<script lang="ts" setup>

</script>

<style scoped>
.footer-page {
    display: flex;
    float: right;
    width: 83%;
    height: 40px;
    position: fixed;
    right: 0;
    bottom: 0;
    z-index: 9;
    border-top: 1px solid var(--el-border-color);
    justify-items: center;
    align-items: center;
    background-color: rgb(238, 238, 238);
}

#create_p {
    font-size: 16px;
    margin-left: 20px;
}
</style>