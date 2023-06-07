<script setup>
import { ref, onMounted } from 'vue'

const day = ref('')

onMounted(() => {
  day.value = getDayOfWeek() + ', '+ date + ' ' + getMonth() + ' ' + year
})
const current = new Date();
const date = current.getDate();
const year = current.getFullYear();
const getDayOfWeek = () => {
  const weekDays = [
    'Son',
    'Mon',
    'Die',
    'Mit',
    'Don',
    'Fre',
    'Sam'
  ]
  return weekDays[new Date().getDay()]
}
const getMonth = () => {
  const Months = [
    'Jan',
    'Feb',
    'Mär',
    'Apr',
    'Mai',
    'Jun',
    'Jul',
    'Aug',
    'Sep',
    'Okt',
    'Nov',
    'Dez'
  ]
  return Months[new Date().getMonth()]
}
</script>

<template>
  <div>
      <p>{{ day }}</p>
  </div>
</template>

<style scoped>
p{
color: #505b62;
}
div{
    background-color: transparent;
}
</style>