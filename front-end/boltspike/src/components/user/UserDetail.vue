<template>
  <!-- 탈퇴 모달 -->
  <div v-if="deleteModalActive" @click="showDeleteModal">
    <div class="delete-modal">
      <div class="delete-modal-content" @click.stop>
        <p class="delete-modal-text">정말로 탈퇴하시겠습니까?</p>
        <div class="delete-modal-btns">
          <button @click="deleteUser" type="button" class="delete-modal-ok-btn">
            확인
          </button>
          <button @click="showDeleteModal" class="delete-modal-cancel-btn">
            취소
          </button>
        </div>
      </div>
    </div>
  </div>

  <div id="container">
    <div class="user-detail d-flex justify-content-between">
      <!-- 프로필 이미지 -->
      <div class="profile-image-div">
        <img
          :src="`http://localhost:8080/img/${user.profileImage}`"
          class="profile-image"
        />
      </div>
      <!-- 이름/팔로우/팔로워 -->
      <div class="name-follow-div">
        <div class="name-follow-btn-div">
          <p class="userId">@{{ user.userId }}</p>
          <button v-if="canAddFollow" @click="addFollow" class="follower-btn">
            팔로우
          </button>
          <button
            v-if="canDeleteFollow"
            @click="deleteFollow"
            class="following-btn"
          >
            팔로잉
          </button>
        </div>
        <div class="follower-and-following d-flex justify-content-column">
          <div class="follower" @click="showFollowers">
            <p>팔로워 {{ followersCnt }}</p>
          </div>
          <div class="following" @click="showFollowings">
            <p>팔로잉 {{ followingsCnt }}</p>
          </div>
        </div>

        <!-- 팔로워/팔로잉 모달 -->
        <div class="follow-modal" v-if="followModalActive" @click="showFollow">
          <div class="follow-modal-content" @click.stop>
            <p class="follow-modal-title">
              <b>{{ isFollowerOfFollowing }}</b>
            </p>
            <div class="follow-list">
              <div v-for="data in modalData" :key="data.id">
                <p>{{ data.userId }}</p>
              </div>
            </div>
            <button @click="showFollow" type="button" class="follow-modal-btn">
              확인
            </button>
          </div>
        </div>
      </div>

      <!-- 수정/탈퇴 버튼 -->
      <div class="btns" v-if="isLoginUser">
        <div class="modify-delete-btn">
          <button @click="updateForm" class="modify-btn">수정</button> |
          <button @click="showDeleteModal" class="delete-btn">탈퇴</button>
        </div>
      </div>
    </div>

    <!-- 캐러셀 추가 -->
    <p class="my-board-text">나의 게시글</p>
    <div class="no-board-list">
      <p v-if="images.length == 0">작성한 게시글이 없습니다.</p>
      <button @click="goBoardCreate" class="no-board-list-btn">
        게시글 작성하러가기
      </button>
    </div>
    <div v-if="images.length > 0">
      <Carousel
        class="board-images"
        :items-to-show="itemToShowCnt"
        :wrap-around="true"
      >
        <Slide v-for="image in images" :key="image.imageId">
          <div class="carousel__item">
            <img
              class="board-image"
              :src="`http://localhost:8080/img/${image.imagePath}`"
              @click="goBoardDetail(image.boardId)"
            />
          </div>
        </Slide>

        <template #addons>
          <Navigation />
        </template>
      </Carousel>
      <!-- 캐러셀 끝 -->
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";
import { useOasisStore } from "@/stores/oasis";
import axios from "axios";

import { Carousel, Pagination, Slide, Navigation } from "vue3-carousel";
import "vue3-carousel/dist/carousel.css";

const store = useOasisStore();
const router = useRouter();
const route = useRoute();
onBeforeRouteUpdate((to, from) => {
  route.params.id = to.params.id;
  axios({
    url: `http://localhost:8080/user/${route.params.id}`,
    method: "GET",
  })
    .then((res) => {
      user.value = res.data.user;
      followings.value = res.data.followings;
      followers.value = res.data.followers;

      followingsCnt.value = computed(() => {
        return followings.value.length;
      });
      followersCnt.value = computed(() => {
        return followers.value.length;
      });

      images.value = res.data.images;

      follow.value = {
        followingId: route.params.id,
        followerId: store.userId,
      };
      // 팔로잉 하지 않은 사람이라면 팔로우 버튼이 존재함
      isFollow();
      isLoginUser.value = route.params.id == store.userId ? true : false;
    })
    .catch((err) => {
      console.log(err);
    });

  const follow = ref({
    followingId: route.params.id,
    followerId: store.userId,
  });

  followModalActive.value = false;
});

const canAddFollow = ref(false);
const canDeleteFollow = ref(false);
const isLoginUser = ref(false);
const isFollow = function () {
  axios({
    url: `http://localhost:8080/user/${store.userId}/following`,
    method: "GET",
  })
    .then((res) => {
      if (store.userId == route.params.id) {
        canAddFollow.value = false;
        canDeleteFollow.value = false;
      } else if (
        res.data.filter((follow) => follow.id == route.params.id).length > 0
      ) {
        canAddFollow.value = false;
        canDeleteFollow.value = true;
      } else {
        canAddFollow.value = true;
        canDeleteFollow.value = false;
      }
    })
    .catch((err) => {
      console.log(err);
    });
};
const user = ref({
  id: 0,
  userId: "",
  email: "",
  age: 0,
  gender: "",
  profileImage: "",
});

// 팔로워 팔로잉
const followings = ref([]);
const followingsCnt = ref(0);
const followers = ref([]);
const followersCnt = ref(0);
const isFollowerOfFollowing = ref("");

const followModalActive = ref(false);
const modalData = ref([]);
const showFollowers = function () {
  isFollowerOfFollowing.value = "팔로워 목록";
  followModalActive.value = !followModalActive.value;
  modalData.value = followers.value;
};
const showFollowings = function () {
  isFollowerOfFollowing.value = "팔로잉 목록";
  followModalActive.value = !followModalActive.value;
  modalData.value = followings.value;
};

const showFollow = function () {
  followModalActive.value = !followModalActive.value;
};

const follow = ref({
  followingId: route.params.id,
  followerId: store.userId,
});

const addFollow = function () {
  axios({
    url: `http://localhost:8080/user/${store.userId}/following`,
    method: "POST",
    data: follow.value,
  })
    .then(() => {
      isFollow();
    })
    .catch((err) => {
      console.log(err);
    });
};
const deleteFollow = function () {
  axios({
    url: `http://localhost:8080/user/${store.userId}/following`,
    method: "DELETE",
    data: follow.value,
  })
    .then(() => {
      isFollow();
    })
    .catch((err) => {
      console.log(err);
    });
};

const images = ref([]);
const goBoardDetail = function (boardId) {
  router.push({ name: "boardDetail", params: { id: boardId } });
};
const itemToShowCnt = computed(() => {
  return images.value.length <= 4 ? images.value.length : 4;
});

const goBoardCreate = function () {
  router.push({ name: "boardCreate" });
};

// 수정
const updateForm = function () {
  router.push({ name: "userUpdate", params: { id: store.userId } });
};

// 탈퇴

const deleteModalActive = ref(false);
const showDeleteModal = () => {
  deleteModalActive.value = !deleteModalActive.value;
};

const deleteUser = function () {
  axios({
    url: `http://localhost:8080/user/${store.userId}`,
    method: "DELETE",
  })
    .then(() => {
      store.setLoginUser(null);
      store.setUserId(0);
      deleteModalActive.value = false;
      router.replace({ name: "main" });
    })
    .catch((err) => {
      console.log(err);
    });
};

onMounted(() => {
  axios({
    url: `http://localhost:8080/user/${route.params.id}`,
    method: "GET",
  })
    .then((res) => {
      user.value = res.data.user;
      followings.value = res.data.followings;
      followers.value = res.data.followers;

      followingsCnt.value = computed(() => {
        return followings.value.length;
      });
      followersCnt.value = computed(() => {
        return followers.value.length;
      });

      images.value = res.data.images;

      // 팔로잉 하지 않은 사람이라면 팔로우 버튼이 존재함
      isFollow();
      isLoginUser.value = route.params.id == store.userId ? true : false;
    })
    .catch((err) => {
      console.log(err);
    });
});
</script>

<style scoped>
button {
  font: var(--xs-pre-font);
}

.profile-image-div {
  text-align: end;
  margin-left: 130px;
}

.profile-image {
  border-radius: 50%;
  width: 150px;
}

.userId {
  font: var(--base-pre-font);
}

/* 팔로우 관련 */
.name-follow-div {
  align-content: center;
  margin-top: 20px;
  margin-left: 20px;
  display: flex;
  flex-direction: column;
  flex-grow: 3;
}

.name-follow-btn-div {
  display: flex;
  flex-direction: row;
}

.follower-btn,
.following-btn {
  border: none;
  border-radius: 8px;
  box-shadow: 0 4px 4px -4px black;
  margin-left: 10px;
  margin-bottom: 15px;
}

.follower-btn {
  background-color: var(--gray100-color);
}
.following-btn {
  background-color: var(--main-color);
}

.follower,
.following {
  font: var(--sm-pre-font);
}

.following {
  margin-left: 10px;
}

/* 팔로워/팔로잉 모달 */
.follow-modal-content {
  background-color: var(--gray100-color);
  padding: 20px;
  border-radius: 10px;
  max-width: 170px;
  box-shadow: 0 4px 5px -4px black;
  border: none;
  font: var(--sm-pre-font);
  text-align: center;
}

.follow-list {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-evenly;
  gap: 2px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.follow-modal-btn {
  background-color: white;
  box-shadow: 0 4px 5px -4px black;
  border: 1px solid black;
  padding: 4px;
  border-radius: 5px;
}
.follow-modal-btn:hover {
  background-color: var(--main-color);
}

.board-images {
  margin: 0 auto;
  width: 1000px;
}

.modify-delete-btn {
  float: right;
  margin-top: 50px;
  margin-right: 150px;
  
}

.modify-btn,
.delete-btn {
  border: none;
  background-color: white;
}

/* 탈퇴 모달 */
.delete-modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  /* background-color: rgba(0, 0, 0, 0.5); */
  text-align: center;
  z-index: 100;
}

.delete-modal-title {
  margin-bottom: 10px;
  color: black;
  opacity: 0.8;
  text-align: center;
  font: var(--sm-pre-font);
}

.delete-modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 300px;
  max-width: 90%;
  box-shadow: 0 4px 5px -4px black;
  border: none;
}

.delete-modal-text {
  font: var(--base-pre-font);
}

.delete-modal-btns {
  margin: 0 10px;
}

.delete-modal-ok-btn,
.delete-modal-cancel-btn {
  background-color: white;
  font: var(--sm-pre-font);
  border: 1px solid black;

  border-radius: 8px;
  box-shadow: 0 4px 5px -4px black;
  padding: 3px 4px;
}

.delete-modal-ok-btn:hover,
.delete-modal-cancel-btn:hover {
  background-color: var(--main-color);
}

.delete-modal-ok-btn {
  margin-right: 10px;
}

.board-image {
  box-shadow: 0 4px 4px -4px black;
  max-width: 200px;
  border-radius: 10px;
}

.my-board-text {
  text-align: center;
  margin-top: 80px;
  margin-bottom: 30px;
  font-weight: bold;
  font: var(--xl-pre-font);
}

.no-board-list {
  text-align: center;
  font: var(--base-pre-font);
}

.no-board-list-btn {
  border-radius: 10px;
  border: none;
  background-color: var(--main-color);
  box-shadow: 0 4px 4px -4px black;
  padding: 8px 10px;
}

/* 캐러셀 추가 */
.carousel__slide {
  padding: 5px;
}

.carousel__viewport {
  perspective: 1000px;
}

.carousel__track {
  transform-style: preserve-3d;
}

.carousel__slide--sliding {
  transition: 0.5s;
}
</style>
