<template>
  course re-edit  {{courseId}}
</template>

<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()
let courseId = route.params.id
</script>

<style scoped>

</style>