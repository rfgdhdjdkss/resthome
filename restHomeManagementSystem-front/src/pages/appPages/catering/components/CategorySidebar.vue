<template>
    <div class="category-sidebar">
        <div v-for="category in categories" :key="category.id" class="category-item"
            :class="{ active: isActive(category) }" @click="selectCategory(category)">
            <div class="category-icon">{{ category.icon }}</div>
            <div class="category-name">{{ category.name }}</div>
        </div>
    </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
    categories: {
        type: Array,
        required: true
    }
});

const emit = defineEmits(['category-change']);

const activeCategory = ref(null);

const isActive = (category) => {
    return activeCategory.value === category;
};

const selectCategory = (category) => {
    activeCategory.value = category;
    emit('category-change', category);
};
</script>

<style scoped>
.category-sidebar {
    position: fixed;
    left: 0;
    top: 60px;
    /* 根据导航栏高度调整 */
    bottom: 0;
    width: 95px;
    overflow-y: auto;
    z-index: 2;
}

.category-item {
    padding: 10px;
    text-align: center;
    cursor: pointer;
    transition: background-color 0.3s;
}

.category-item:hover {
    background-color: #f0f0f0;
}

.category-item.active {
    background-color: #fff3e0;
    border-left: 3px solid #ff9800;
}

.category-icon {
    font-size: 20px;
    margin-bottom: 5px;
}

.category-name {
    font-size: 14px;
}
</style>