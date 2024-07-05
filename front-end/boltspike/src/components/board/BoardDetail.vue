<template>
  <div v-if="showModal" class="modal" @click="closeModal">
    <div class="modal-content" @click.stop>
      <p>{{ modalMessage }}</p>
      <button @click="closeModal" class="closeModalButton">닫기</button>
    </div>
  </div>
  <div v-if="showDeleteConfirmation">
    <div class="modal">
      <div class="modal-content">
        <p>게시글을 삭제하시겠습니까?</p>
        <button class="deleteConfirmButton" @click="deleteBoard">예</button>
        <button class="cancelDeleteButton" @click="cancelDelete">아니요</button>
      </div>
    </div>
  </div>
  <div class="board-layout">
    <div class="image-section">
      <div v-if="board">
        <div class="DataOnImage">
          <div class="userIdAndFollow">
            <router-link :to="{ name: 'userDetail', params: { id: board.board.userId } }">
              <span class="userId">@{{ board.userId }}</span>
            </router-link>
            <button v-if="!isOwner()" @click="toggleFollow"
              :class="['followButton', isFollowing ? 'following' : 'not-following']">
              <span v-if="isFollowing">팔로잉</span>
              <span v-else>팔로우</span>
            </button>
          </div>
          <div class="regDateAndButton">
            <span class="regDate">{{ formatDate(board.board.regDate) }}</span>
            <div v-if="isOwner()" class="dropdown">
              <button class="btn btn-secondary dropdown-toggle no-arrow" type="button" id="dropdownMenuButton"
                data-bs-toggle="dropdown" aria-expanded="false">
                ⋯
              </button>
              <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <li><a class="dropdown-item" @click="UpdatePost">수정</a></li>
                <li><a class="dropdown-item" @click="confirmDelete">삭제</a></li>
              </ul>
            </div>
          </div>
        </div>

        <Carousel :per-page="1" :loop="true">
          <Slide v-for="(image, index) in board.images" :key="index">
            <div class="img-container">
              <div class="apparel-info">
                <p v-if="board.apparel?.outer" class="outer">{{ board.apparel.outer }}</p>
                <p v-if="board.apparel?.top" class="top">{{ board.apparel.top }}</p>
                <p v-if="board.apparel?.bottom" class="bottom">{{ board.apparel.bottom }}</p>
                <p v-if="board.apparel?.shoes" class="shoes">{{ board.apparel.shoes }}</p>
              </div>
              <img :src="`http://localhost:8080/img/${image.imagePath}`" class="board-img" />
            </div>
          </Slide>
          <template #addons>
            <Navigation />
          </template>
        </Carousel>

        <div class="DataUnderImage">
          <span class="locationDetail">📍 {{ board.board.locationDetail }}</span>
          <span class="heartAndComments">
            <span @click="toggleHeart" style="cursor: pointer" class="heartCnt">
              <span v-if="isHeart">❤️</span>
              <span v-else>🤍</span>
              {{ board.board.heartCnt }}
            </span>
            <span class="commentsCnt">&#x1F4AC;
              {{ board.comments.length }}
            </span>
            <span class="viewCnt">👁️‍🗨️
              {{ board.board.viewCnt }}
            </span>
          </span>
        </div>
      </div>
    </div>
    <div class="form-section">
      <div v-if="board">
        <div class=boardContentUserIdAndboardContent>
          <router-link :to="{ name: 'userDetail', params: { id: board.board.userId } }">
            <p class="boardContentUserId">@{{ board.userId }}</p>
          </router-link>
          <p class="boardContentTitle"> <strong> {{ board.board.title }}</strong> </p>
          <p class="boardContent">{{ board.board.content }}</p>
        </div>
        <div class="comment-box">
          <div v-if="board.comments && board.comments.length === 0">
            <p>첫 번째로 댓글을 입력하세요.</p>
          </div>
          <div v-else>
            <div v-for="(comment, index) in board.comments" :key="index" class="comment">
              <template v-if="index === editingIndex">
                <textarea v-model="editedContent" class="commentTextarea1"></textarea>
                <div class="editButtons">
                  <button @click="saveEdit(index)" class="SaveButton">저장</button>
                  <button @click="cancelEdit" class="CancelButton">취소</button>
                </div>
              </template>
              <template v-else>
                <p class="comment-content">
                  <router-link :to="{ name: 'userDetail', params: { id: comment.loginUser }}">
                  <span class="user-id">@{{ comment.loginUser }}</span>
                </router-link>
                  <span class="content">{{ comment.content }}</span>
                  <span class="comment-buttons">
                    <div class="dropdown">
                      <button class="btn btn-secondary dropdown-toggle no-arrow" type="button"
                        id="dropdownCommentButton" data-bs-toggle="dropdown" aria-expanded="false">
                        ⋯
                      </button>
                      <ul class="dropdown-menu" aria-labelledby="dropdownCommentButton">
                        <li><a class="dropdown-item" @click="startEdit(index)">수정</a></li>
                        <li><a class="dropdown-item" @click="deleteComment(index)">삭제</a></li>
                      </ul>
                    </div>
                  </span>
                </p>
              </template>
            </div>
          </div>
          <div class="comment-form">
            <textarea v-model="newComment" class="commentTextarea2" placeholder="댓글을 입력하세요..."></textarea>
            <button @click="submitComment" class="newComment">댓글 작성</button>
          </div>
        </div>


        <div class="similar-locations">
          <h6>비슷한 지역의 사진들을 둘러보세요.</h6>
          <div class="similar-locations-container">
            <div v-for="(data, index) in filteredMapData" :key="index" class="similar-location-item">
              <router-link :to="{ name: 'boardDetail', params: { id: data.boardId } }">
                <img :src="`http://localhost:8080/img/${data.imagePath}`" class="similar-img" />
              </router-link>
            </div>
          </div>
        </div>




      </div>
    </div>
  </div>
</template>






<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useOasisStore } from "@/stores/oasis";
import { Carousel, Navigation, Slide, Pagination } from 'vue3-carousel'
import 'vue3-carousel/dist/carousel.css'
import axios from "axios";

const oasisStore = useOasisStore();
const route = useRoute();
const router = useRouter();
const board = ref(null);
const newComment = ref("");
const showDropdown = ref(false);
const showDeleteConfirmation = ref(false);
const mapData = ref([]);
const showModal = ref(false);
const modalMessage = ref("");
const editingIndex = ref(-1);
const editedContent = ref("");
const isHeart = ref(false);
const isFollowing = ref(false);
const imageLength = ref(0);
//게시글 정보 호출
const loadBoard = (boardId) => {
  axios
    .get(`http://localhost:8080/board/${boardId}`)
    .then((response) => {
      imageLength.value = response.data.images.length;
      board.value = response.data;
      if (!board.value.board.images) {
        board.value.board.images = [];
      }
      if (!board.value.hearts) {
        board.value.hearts = [];
      }
      checkHeartStatus(); //좋아요 상태 확인 호출
      checkFollowStatus(); //팔로우 상태 확인
    })
    .catch((error) => {
      console.error("오류:", error);
    });
};
//비슷한 지역의 게시글들 필터걸기
const filteredMapData = computed(() => {
  if (!board.value || !mapData.value) return [];

  const currentLocation = board.value.board.locationDetail;

  return mapData.value.filter(data => data.locationDetail === currentLocation);
});

//좋아요 상태 확인
const checkHeartStatus = () => {
  const userId = oasisStore.userId;
  if (userId && board.value && Array.isArray(board.value.hearts)) {
    const heart = board.value.hearts.find((h) => h.userId === userId);
    isHeart.value = !!heart;
  } else {
    isHeart.value = false; // 기본값 설정
  }
};

//팔로우 상태 확인
const checkFollowStatus = () => {
  if (!oasisStore.userId) return;
  const userId = oasisStore.userId;
  const boardUserId = board.value.board.userId;

  axios
    .get(`http://localhost:8080/user/${userId}/following`)
    .then((response) => {
      isFollowing.value = response.data.some(
        (following) => following.id === boardUserId
      );
    })
    .catch((error) => {
      console.error("팔로우 상태 오류:", error);
    });
};

onMounted(() => {
  const boardId = route.params.id;
  loadBoard(boardId);
  mapData.value = oasisStore.mapData;
});

//워치로 route.params.id 가 바뀔때마다 게시글정보 다시 호출
watch(
  () => route.params.id,
  (newId, oldId) => {
    if (newId !== oldId) {
      loadBoard(newId);
    }
  }
);

const toggleHeart = () => {
  if (!oasisStore.userId) {
    router.push("/user/login");
    alert("로그인을 해주세요.");
    return;
  }
  const boardId = route.params.id;
  const userId = oasisStore.userId;
  axios
    .post(`http://localhost:8080/board/${boardId}/heart`, {
      boardId: boardId,
      userId: userId,
    })
    .then((response) => {
      if (response.status === 200) {
        isHeart.value = !isHeart.value;
        board.value.board.heartCnt += isHeart.value ? 1 : -1;
      }
    })
    .catch((error) => {
      console.error("좋아요 오류:", error);
    });
};

//팔로우 토글
const toggleFollow = () => {
  if (!oasisStore.userId) {
    router.push("/user/login");
    alert("로그인을 해주세요.");
    return;
  }
  const followerId = oasisStore.userId;
  const followingId = board.value.board.userId;

  if (isFollowing.value) {
    axios
      .delete(`http://localhost:8080/user/${followerId}/following`, {
        data: { followerId, followingId },
      })
      .then((response) => {
        isFollowing.value = false;
      })
      .catch((error) => {
        console.error("팔로우 취소 오류:", error);
      });
  } else {
    axios
      .post(`http://localhost:8080/user/${followerId}/following`, {
        followerId,
        followingId,
      })
      .then((response) => {
        isFollowing.value = true;
      })
      .catch((error) => {
        console.error("팔로우 오류:", error);
      });
  }
};

//날짜 정리
const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}년 ${month}월 ${day}일`;
};

//댓글 등록
const submitComment = () => {
  if (!oasisStore.userId) {
    router.push("/user/login");
    alert("로그인을 해주세요.");
    return;
  }
  const boardId = route.params.id;
  const userId = oasisStore.userId;
  const loginUser = oasisStore.loginUser;
  axios
    .post(`http://localhost:8080/board/${boardId}/comment`, {
      loginUser: loginUser,
      userId: userId,
      content: newComment.value,
    })
    .then((response) => {
      loadBoard(boardId);
      newComment.value = "";
    })
    .catch((error) => {
      console.error("댓글 오류:", error);
    });
};

// 게시글 수정
const UpdatePost = () => {
  const boardId = route.params.id;
  router.push({ name: "boardUpdate", params: { id: boardId } });
};

//모달로 삭제 2차확인
const confirmDelete = () => {
  showDeleteConfirmation.value = true;
};

//모달 취소
const cancelDelete = () => {
  showDeleteConfirmation.value = false;
};

//게시글 삭제
const deleteBoard = () => {
  const boardId = route.params.id;
  axios
    .delete(`http://localhost:8080/board/${boardId}`)
    .then((response) => {
      router.push({ name: "main" });
    })
    .catch((error) => {
      console.error("게시글 삭제 오류:", error);
    });
};

//댓글 삭제
const deleteComment = (index) => {
  const boardId = route.params.id;
  const loginUser = oasisStore.userId;
  const comment = board.value.comments[index];

  if (comment.userId !== loginUser) {
    modalMessage.value = "접근 권한이 없습니다.";
    showModal.value = true;
    return;
  }

  axios
    .delete(`http://localhost:8080/board/${boardId}/comment/${comment.commentId}`)
    .then((response) => {
      board.value.comments.splice(index, 1);
      modalMessage.value = "댓글이 삭제되었습니다.";
      showModal.value = true;
    })
    .catch((error) => {
      console.error("댓글 삭제 오류:", error);
      modalMessage.value = "댓글 삭제 오류가 발생했습니다.";
      showModal.value = true;
    });
};

const closeModal = () => {
  showModal.value = false;
};

//내가 게시글의 주인인지 확인
const isOwner = () => {
  return board.value && oasisStore.userId === board.value.board.userId;
};

//댓글 수정 창 호출
const startEdit = (index) => {
  const comment = board.value.comments[index];
  if (comment.userId !== oasisStore.userId) {
    modalMessage.value = "접근 권한이 없습니다.";
    showModal.value = true;
    return;
  }

  editingIndex.value = index;
  editedContent.value = comment.content;
};

// 댓글 수정을 취소하는 함수
const cancelEdit = () => {
  editingIndex.value = -1;
  editedContent.value = "";
};

// 댓글 수정을 db에저장
const saveEdit = async (index) => {
  const commentId = board.value.comments[index].commentId;
  const userId = oasisStore.userId;

  try {
    const response = await axios.patch(
      `http://localhost:8080/board/${route.params.id}/comment/${commentId}`,
      {
        userId: userId,
        content: editedContent.value,
      }
    );

    if (response.status === 200) {
      board.value.comments[index].content = editedContent.value;
      cancelEdit();
    }
  } catch (error) {
    console.error("저장 오류:", error);
  }
};
</script>




<style scoped>
.board-layout {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: space-between;
  margin: 20px auto;
  padding: 10px;
  max-width: 100%;
  overflow-x: auto;
}

.image-section,
.form-section {
  flex: 1;
  width: 45%;
  display: flex;
  flex-direction: column;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.img-container {
  width: 100%;
  position: relative;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.board-img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.DataOnImage,
.DataUnderImage {
  width: 100%;
  color: black;
  text-align: center;
  font: var(--base-pre-font);
}

.DataUnderImage {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
}

.DataOnImage {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
}

.form-section {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.comment-box,
.comment-form {
  background-color: white;
  padding: 10px;
  border-radius: 8px;
  margin-top: 10px;
  font: var(--lg-pre-font);
}

.similar-locations {
  overflow: hidden;
  background-color: white;
  padding: 10px;
  border-radius: 8px;
  margin-top: 10px;
  text-align: center;
  font: var(--lg-pre-font);
}

.similar-locations-container {
  display: flex;
  gap: 10px;
  overflow-x: auto;
  white-space: nowrap;
  max-height: 120px;
  min-width: 100%;
}

.similar-location-item {
  flex: 0 0 auto;
}

.similar-img {
  width: 150px;
  height: 100%;
  object-fit: contain;
  border: 2px solid white;
  border-radius: 10px;
}

.comment-box {
  border: 3px solid #E9F4FF;
}

.comment {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.comment-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.userId {
  flex: 1;
  text-align: left;
  min-width: 0;
  max-width: 100%;
  font: var(--lg-pre-font);
}

.content {
  flex: 2;
  text-align: left;
  min-width: 0;
  margin-left: 10%;
  font: var(--base-pre-font);
}

.comment-buttons {
  display: flex;
  text-align: right;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  width: 300px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.dropdown-toggle {
  background-color: white;
  border: none;
  color: black;
}

.newComment,
.updateComment,
.deleteComment {
  background-color: var(--main-color);
  color: black;
  border: none;
  border-radius: 6px;
  padding: 8px 16px;
  cursor: pointer;
  margin-left: 10px;
  box-shadow: 0 4px 5px -4px black;
}

.newComment {
  font: var(--xs-pre-font);
}

.deleteConfirmButton,
.cancelDeleteButton {
  background-color: white;
  padding: 5px;
  margin-bottom: 10px;
  border: 1px solid black;
}

.deleteConfirmButton:hover {
  background-color: var(--main-color);
}

.cancelDeleteButton:hover {
  background-color: var(--main-color);
}

.SaveButton,
.CancelButton {
  flex-direction: row;
  flex-grow: 1;
  margin: 0 5px;
  background-color: white;
  color: black;
  border: 1px solid #ccc;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 4px;
  text-align: justify;
}

.closeModalButton {
  background-color: white;
  color: black;
  border: 1px solid black;
  border-radius: 6px;
  padding: 8px 16px;
  cursor: pointer;
  box-shadow: 0 4px 5px -4px black;
}

.followButton {
  margin-left: 20px;
  background-color: white;
  border: 1px solid black;
  padding: 1px 15px;
  border-radius: 8px;
  font: var(--sm-pre-font);
  box-shadow: 0 4px 5px -4px black;
}

.followButton.following {
  background-color: var(--main-color);
  color: black;
}

.followButton.not-following {
  background-color: white;
  color: black;
}

button:hover {
  background-color: var(--main-color);
}

.commentTextarea2 {
  width: 100%;
  padding: 8px;
  border: 1px solid #ced4da;
  border-radius: 4px;
  margin-bottom: 10px;
  border-radius: 10px;
}

.apparel-info {
  position: absolute;
  top: 0;
  left: 0;
  background-color: none;
  color: black;
  z-index: 10;
  font-weight: bold;
  font: var(--base-pre-font);

}

.outer,
.top,
.bottom,
.shoes {
  border: 1px solid white;
  border-radius: 10px;
  padding: 2px 8px;
  margin: 3px 5px;
  width: fit-content;
  background-color: rgba(255, 255, 255, 0.2);
}

.userIdAndFollow,
.regDateAndButton {
  display: flex;
  align-items: center;
}

.userIdAndFollow {
  justify-content: flex-start;
}

.regDateAndButton {
  justify-content: flex-end;
}

.regDate {
  border: 1px solid #d9d9d9;
  padding: 5px 20px;
  border-radius: 12px;
  font: var(--xs-pre-font);
}

.locationDetail {
  flex: 1;
  text-align: left;
}

.heartAndComments {
  flex: 1;
  text-align: right;
  justify-content: space-evenly;
}

.heartCnt,
.viewCnt,
.commentsCnt {
  margin-right: 5%;
}

.no-arrow::after {
  display: none;
}

.comment-form {
  display: flex;
  flex-direction: column;
}

.comment-form textarea {
  width: 100%;
  margin-bottom: 10px;
}

.newComment {
  font-size: var(--xxs-pre-font);
  align-self: flex-end;
  background-color: white;
  border: 1px solid black;
}

button:hover {
  opacity: 0.8;
}

a {
  text-decoration: none;
  color: black;
}

.boardContentUserIdAndboardContent {
  padding-left: 20px;
  border: 3px solid #E9F4FF;
  font: var(--base-pre-font);
  text-align: left;
  padding-right: 20px;
}

.boardContent {
  font: var(--sm-pre-font);
}

.dropdown-item {
  font: var(--base-pre-font);
}

.deleteConfirmButton,
.cancelDeleteButton {
  border: 1px solid black;
  background-color: white;
  color: black;
  border-radius: 10px;
}

.editButtons {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.commentTextarea1 {
  width: 74%;
  padding: 5px;
  border: 1px solid #000000;
  border-radius: 8px;
  margin-bottom: 2%;
}

.boardContentUserId {
  padding-top: 20px;
  font: var(--base-pre-font);
  text-align: left;
}

.boardContentTitle {
  font: var(--base-pre-font);
}
</style>
