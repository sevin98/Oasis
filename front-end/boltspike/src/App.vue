<script setup>
import { RouterLink, RouterView } from "vue-router";
import TheHeaderNav from "./components/common/TheHeaderNav.vue";
import TheFooterNav from "./components/common/TheFooterNav.vue";
</script>

<template>
  <TheHeaderNav class="HeaderNav" />
  <div class="container">
    <RouterView />
  </div>
  <TheFooterNav />
</template>

<style scoped>
.HeaderNav {
  height: 89px;
  top: 0;
}

.container {
  min-height: calc(100vh - 178px);
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
