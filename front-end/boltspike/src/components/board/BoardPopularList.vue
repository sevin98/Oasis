<template>
  <div id="container">
    <div class="container-content">
      <div>
        <p class="title">전국의 인기 많은 장소들을 둘러보세요.</p>
      </div>

      <div id="list">
        <div v-for="datas in store.top3Data" class="location">
          <p class="list-location">📍 {{ datas[0].locationDetail }}</p>
          <!-- 캐러셀 추가 -->
          <Carousel
            :itemsToShow="itemToShowCnt"
            :wrapAround="true"
            :transition="300"
          >
            <Slide v-for="data in datas" :key="data.boardId">
              <div class="carousel__item">
                <img
                  :src="`http://localhost:8080/img/${data.imagePath}`"
                  @click="goBoardDetail(data.boardId)"
                  class="top3-img"
                />
              </div>
            </Slide>

            <template #addons>
              <Navigation />
              <Pagination />
            </template>
          </Carousel>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useOasisStore } from "@/stores/oasis";

import { Carousel, Pagination, Slide, Navigation } from "vue3-carousel";

import "vue3-carousel/dist/carousel.css";

const store = useOasisStore();
const router = useRouter();
const itemToShowCnt = computed(() => {
  return store.top3Data.length <= 4 ? store.top3Data.length : 4;
});

const goBoardDetail = function (boardId) {
  router.push({ name: "boardDetail", params: { id: boardId } });
};

onMounted(() => {
  store.getTop3Data();
});
</script>

<style scoped>
#container {
  text-align: center;
  align-content: center;
  font: var(--sm-pre-font);
}

.container-content {
  border: none;
  border-radius: 30px;
  box-shadow: 4px 4px 4px -4px black;
  padding: 10px 0;
}

.list-location {
  margin-bottom: 5px;
}

.location {
  margin-bottom: 10px;
}

.title {
  margin: 20px;
}

.top3-img {
  width: 60px;
  object-fit: cover;
  border-radius: 5px;
  box-shadow: 0 4px 5px -4px black;
}

/* 캐러셀 추가 */
.carousel__slide {
  padding: 5px;
}

.carousel__viewport {
  perspective: 2000px;
}

.carousel__track {
  transform-style: preserve-3d;
}

.carousel__slide--sliding {
  transition: 0.5s;
}
</style>
