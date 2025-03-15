<template>
    <div class="dishes-item">
        <div class="dishes-image">
            <img :src="`http://localhost:8999/images/upload/addNewDish/dishImg/${dishes.dishImg}`"
                alt="dishes.dishName" />
        </div>
        <div class="dishes-info">
            <div>
                <div>
                    <h3 class="dishes-name">{{ dishes.dishName }}</h3>
                </div>
                <div>
                    <h3 class="dishes-description">{{ dishes.description }}</h3>
                </div>
            </div>
            <div class="dishes-price">
                <span class="current-price">¥{{ dishes.dishPrice }}</span>
            </div>
            <button class="add-to-cart" @click="addToCart">加入购物车</button>
        </div>
    </div>

</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
    dishes: {
        type: Object,
        required: true
    }
});

const emit = defineEmits(['add-to-cart']);

const addToCart = () => {
    emit('add-to-cart', props.dishes);
};
</script>

<style scoped>
.dishes-item {
    display: flex;
    margin-bottom: 5px;
    padding: 15px;
    border-radius: 5px;
    background-color: white;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    width: 300px;
    margin-top: 10px; 
}

.dishes-image {
    width: 120px;
    height: 120px;
    margin-right: 15px;
    border-radius: 8px;
    overflow: hidden;
}

.dishes-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.dishes-info {
    flex: 1;
    padding-left: 20px;
}

.dishes-info h3 {
    margin-bottom: 10px;
    margin-top: 0;
}

.dishes-name {
    font-size: 16px;
    margin-bottom: 8px;
    color: #333;
}
.dishes-description{
    font-size: 12px;
    color: #50504e;

}
.dishes-price {
    display: flex;
    align-items: baseline;
    margin-bottom: 10px;
}

.current-price {
    font-size: 18px;
    color: #ff4d4f;
    font-weight: bold;
}

.add-to-cart {
    margin-left: 40px;
    background-color: #00bfa5;
    color: #fff;
    border: none;
    padding: 5px 15px;
    border-radius: 20px;
    cursor: pointer;
    font-size: 14px;
}
</style>