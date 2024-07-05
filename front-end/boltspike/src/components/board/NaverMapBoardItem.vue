<template>
    <div>

    </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue';
    import { useOasisStore } from '@/stores/oasis';
    const store = useOasisStore()

    onMounted(() => {
        store.getMapData()
    })
</script>

<style scoped>

</style>