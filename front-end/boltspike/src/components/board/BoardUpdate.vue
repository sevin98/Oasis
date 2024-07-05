<template>
  <div class="board-update">
    <div class="content-layout">
      <div class="image-upload-section">
        <label for="images" class="changeImagebutton">이미지 업로드</label>
        <input type="file" id="images" @change="handleFileUpload" multiple class="changeImage">
        <br>
        <br>
        <h5>현재 이미지</h5>
        <br>
        <div v-if="currentImages.length" class="current-images">
          <div v-for="(image, index) in currentImages" :key="index" class="image-preview">
            <img :src="image.url" alt="Current Image">
            <button type="button" @click="removeImage(index, image.imagePath)">삭제</button>
          </div>
        </div>
        <br>
        <br>
        <h5>새로 추가된 이미지</h5>
        <br>
        <div v-if="imagePreviews.length" class="image-previews">
          <div v-for="(image, index) in imagePreviews" :key="index" class="image-preview">
            <img :src="image" alt="Uploaded Image">
            <button type="button" @click="removePreviewImage(index)">삭제</button>
          </div>
        </div>
      </div>
      <div class="form-section">
        <form @submit.prevent="updateBoard">
          <input type="text" id="title" class="boardTitle" v-model="board.title" placeholder="제목을 입력하세요">
          <textarea id="content" class="boardContent" v-model="board.content" placeholder="내용을 입력하세요"></textarea>
          <select id="location" class="boardLocation" v-model="board.location">
            <option disabled value="">위치를 선택하세요</option>
            <option value="서울">서울</option>
            <option value="부산">부산</option>
            <option value="인천">인천</option>
            <option value="대전">대전</option>
            <option value="대구">대구</option>
            <option value="울산">울산</option>
            <option value="광주">광주</option>
            <option value="제주">제주</option>
            <option value="경기도">경기도</option>
            <option value="강원도">강원도</option>
            <option value="경상북도">경상북도</option>
            <option value="경상남도">경상남도</option>
            <option value="충청북도">충청북도</option>
            <option value="충청남도">충청남도</option>
            <option value="전라북도">전라북도</option>
            <option value="전라남도">전라남도</option>
          </select>
          <select id="locationDetail" class="boardLocationDetail" v-model="board.locationDetail">
            <option disabled value="">세부 지역을 선택하세요</option>
            <option v-for="detail in locationDetails" :key="detail" :value="detail">{{ detail }}</option>
          </select>
          <div class="apparel-row">
            <input type="text" id="outer" v-model="apparel.outer" class="apparel-input" placeholder="아우터 정보를 입력하세요">
            <input type="text" id="top" v-model="apparel.top" class="apparel-input" placeholder="상의 정보를 입력하세요">
          </div>
          <div class="apparel-row">
            <input type="text" id="bottom" v-model="apparel.bottom" class="apparel-input" placeholder="하의 정보를 입력하세요">
            <input type="text" id="shoes" v-model="apparel.shoes" class="apparel-input" placeholder="신발 정보를 입력하세요">
          </div>
          <div class="moveSubmitButton">
            <button type="button" @click="confirmDelete" class="comfirmDeleteButton">삭제</button>
            <button type="submit" class="submitButton">수정</button>
          </div>
        </form>
      </div>
    </div>
    <div v-if="showDeleteConfirmation" class="delete-confirmation">
      <p>정말로 이 게시글을 삭제하시겠습니까?</p>
      <button @click="deleteBoard" class="deleteYes">예</button>
      <button @click="cancelDelete" class="deleteNo">아니오</button>
    </div>
  </div>
</template>



<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();

const board = ref({
  title: '',
  content: '',
  location: '',
  locationDetail: '',
  images: [],
});

const apparel = ref({
  outer: '',
  top: '',
  bottom: '',
  shoes: '',
});

// 업로드할 이미지 파일
const imageFiles = ref([]);

// 현재 게시글 이미지
const currentImages = ref([]);

// 새로 업로드할 이미지 미리보기
const imagePreviews = ref([]);

// 게시글 삭제 확인 모달
const showDeleteConfirmation = ref(false);

// 삭제할 이미지 경로
const imagesToDelete = ref([]);

// 위치별 세부 위치 목록
const locationDetails = ref([]);

// 파일 업로드 핸들러 함수
const handleFileUpload = (event) => {
  const files = Array.from(event.target.files);
  imageFiles.value.push(...files);
  imagePreviews.value.push(...files.map(file => URL.createObjectURL(file)));
};

// 업로드할 이미지 미리보기 삭제
const removePreviewImage = (index) => {
  imageFiles.value.splice(index, 1);
  imagePreviews.value.splice(index, 1);
};

// 게시글 수정 함수
const updateBoard = () => {
  const boardId = route.params.id;
  const formData = new FormData();
  board.value.apparel = apparel.value;

  // 게시글 데이터를 JSON 형식으로 FormData에 담기
  formData.append('board', new Blob([JSON.stringify(board.value)], { type: 'application/json' }));

  // 옷 데이터를 JSON 형식으로 FormData에 담기
  formData.append('apparel', new Blob([JSON.stringify(apparel.value)], { type: 'application/json' }));

  // 이미지 파일들을 FormData에 담기
  if (imageFiles.value.length > 0) {
    imageFiles.value.forEach(file => {
      formData.append('file', file);
    });
  }

  // 삭제할 이미지 경로 FormData에 담기
  if (imagesToDelete.value.length > 0) {
    formData.append('imagesToDelete', new Blob([JSON.stringify(imagesToDelete.value)], { type: 'application/json' }));
  }

  // 게시글 수정
  axios.patch(`http://localhost:8080/board/${boardId}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
    .then(response => {
      router.push({ name: 'boardDetail', params: { id: boardId } });
      console.log("옷정보", apparel.value);
    })
    .catch(error => {
      console.error('게시글 수정 오류:', error);
    });
};

// 이미지 삭제
const removeImage = (index, imagePath) => {
  currentImages.value.splice(index, 1);
  imagesToDelete.value.push(imagePath);
};

// 게시글 삭제 확인
const confirmDelete = () => {
  showDeleteConfirmation.value = true;
};

// 게시글 삭제 취소
const cancelDelete = () => {
  showDeleteConfirmation.value = false;
};

// 게시글 삭제
const deleteBoard = () => {
  const boardId = route.params.id;
  axios.delete(`http://localhost:8080/board/${boardId}`)
    .then(response => {
      router.push({ name: 'main' });
    })
    .catch(error => {
      console.error('게시글 삭제 오류:', error);
    });
};

// 컴포넌트 마운트될 때 게시글 정보를 불러오기
onMounted(() => {
  const boardId = route.params.id;
  axios.get(`http://localhost:8080/board/${boardId}`)
    .then(response => {
      board.value = response.data.board || {};
      apparel.value = response.data.apparel || {};
      if (Array.isArray(response.data.images)) {
        currentImages.value = response.data.images.map(img => ({
          url: `http://localhost:8080/img/${img.imagePath}`,
          imagePath: img.imagePath
        }));
      }
    })
    .catch(error => {
      console.error('게시글 불러오기 오류:', error);
    });
});

// 위치가 변경될 때마다 세부 위치를 업데이트
watch(() => board.value.location, (newLocation) => {
  if (newLocation === '서울') {
    locationDetails.value = ['강남', '북촌', '서촌', '성수', '신사', '신촌', '압구정', '양재천', '여의도', '이대', '잠실', '종로', '청담', '한남', '합정', '홍대'];
  } else if (newLocation === '부산') {
    locationDetails.value = ['광안리', '기장', '전포', '해운대'];
  } else if (newLocation === '인천') {
    locationDetails.value = ['부평', '을왕리', '인천공항'];
  } else if (newLocation === '대전') {
    locationDetails.value = ['대전역', '둔산동', '성심당'];
  } else if (newLocation === '대구') {
    locationDetails.value = ['동성로', '안지랑곱창골목', '이월드'];
  } else if (newLocation === '울산') {
    locationDetails.value = ['간절곶', '삼산', '성남동', '태화강'];
  } else if (newLocation === '광주') {
    locationDetails.value = ['광주송정역', '상무지구', '펭귄마을'];
  } else if (newLocation === '제주') {
    locationDetails.value = ['서귀포', '애월', '우도', '제주공항'];
  } else if (newLocation === '강원도') {
    locationDetails.value = ['강릉', '속초', '양양', '춘천', '평창'];
  } else if (newLocation === '경기도') {
    locationDetails.value = ['강화도', '광교', '분당', '수원', '야탑', '의정부', '파주'];
  } else if (newLocation === '경상북도') {
    locationDetails.value = ['경주', '안동', '영덕', '영양', '울진', '포항'];
  } else if (newLocation === '경상남도') {
    locationDetails.value = ['거제', '김해', '창원', '통영'];
  } else if (newLocation === '전라북도') {
    locationDetails.value = ['군산', '익산', '전주'];
  } else if (newLocation === '전라남도') {
    locationDetails.value = ['목포', '여수'];
  } else if (newLocation === '충청북도') {
    locationDetails.value = ['청주', '충주'];
  } else if (newLocation === '충청남도') {
    locationDetails.value = ['공주', '당진', '세종'];
  } else {
    locationDetails.value = [];
  }
});


</script>



<style scoped>
* {
  font: var(--base-pre-font);
}

div {
  box-sizing: border-box;
}

.board-update {
  display: flex;
  flex-direction: column;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.boardTitle,
.boardContent,
.boardLocation,
.boardLocationDetail {
  width: 100%;
  margin-bottom: 30px;
  border-radius: 10px;
}

.content-layout {
  display: flex;
  justify-content: space-between;
}

.image-upload-section {
  width: 40%;
}

.form-section {
  width: 58%;
}

form {
  display: flex;
  flex-direction: column;
}

form div {
  margin-bottom: 15px;
}

form label {
  display: block;
  margin-bottom: 5px;
}

form input,
form textarea,
form select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submitButton:hover,
.deleteButton:hover,
.comfirmDeleteButton:hover {
  opacity: 0.8;
  background-color: var(--main-color);
}

.comfirmDeleteButton,
.submitButton {
  background-color: white;
  border: 1px solid black;
  box-shadow: 0 4px 5px -4px black;
  width: 100px;
  border-radius: 10px;
  margin-top: 30px;
  height: 40px;
  font-size: 16px;
  float: right;
  margin-right: 10px;
}

.image-previews,
.current-images {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.image-preview {
  position: relative;
}

.image-preview img {
  width: 100px;
  height: 100px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.image-preview button {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: rgba(255, 0, 0, 0.7);
  color: white;
  border: none;
  border-radius: 4px;
  padding: 2px 5px;
  cursor: pointer;
}

select.boardLocation option[disabled],
select.boardLocationDetail option[disabled] {
  color: gray;
}

select {
  padding: 10px;
  border: 1px solid #ccc;
}

.apparel-row {
  display: flex;
  justify-content: space-between;
  gap: 40px;
}

.apparel-input {
  border-radius: 10px;
  width: 50%;
  height: 50px;
  padding: 0px 10px;
}

.delete-confirmation {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90%;
  max-width: 500px;
  padding: 20px;
  background-color: #fff;
  border: 1px solid #ccc;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  text-align: center;
  border-radius: 8px;
}

.delete-confirmation p {
  margin-bottom: 20px;
  font-size: 18px;
  color: #333;
}

.delete-confirmation button {
  padding: 10px 20px;
  margin: 0 10px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.deleteYes,
.deleteNo {
  color: black;
  background-color: white;
  border: 1px solid black;
}

.deleteYes:hover {
  background-color: rgb(255, 176, 176);
}

.deleteNo:hover {
  background-color: var(--main-color);
}

.changeImage {
  display: none;
}

.changeImagebutton {
  color: black;
  background-color: white;
  border: 1px solid black;
  border-radius: 10px;
  margin: 5px;
  padding: 5px;
}

.changeImagebutton:hover {
  background-color: var(--main-color);
}
</style>
