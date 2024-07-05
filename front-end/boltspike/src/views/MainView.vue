<template>
  <div>
    <div class="service-description">
      <span>
        Oas:is는 일상 속의 쉼터가 되기를 추구합니다.
        <br />
        쉼터 안에서 여러분의 스타일을 발견해 보세요.
      </span>
      <div class="horizon"><hr /></div>
    </div>
    <div id="main">
      <NaverMap class="map" />
      <BoardPopularList class="top3-list" />
    </div>
  </div>
</template>

<script setup>
import NaverMap from "@/components/naver/NaverMap.vue";
import BoardPopularList from "@/components/board/BoardPopularList.vue";
</script>

<style scoped>
.service-description {
  margin-top: 40px;
  font: var(--lg-pre-font);
  text-shadow: 0 2px 2px var(--gray600-color);
  text-align: center;
}

.horizon {
  margin-top: 25px;
}

#main {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
.map,
.top3-list {
  margin: 30px 10px;
}
</style>
