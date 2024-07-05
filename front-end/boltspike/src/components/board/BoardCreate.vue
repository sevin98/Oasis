<template>
  <div class="board-create">
    <div class="content-layout">
      <div class="image-upload-section">
        <label for="images" class="imageUpload">이미지 업로드</label>
        <input type="file" id="images" @change="handleFileUpload" multiple class="fileUpload">
        <div v-if="imagePreviews.length" class="image-previews">
          <div v-for="(image, index) in imagePreviews" :key="index" class="image-preview">
            <img :src="image" alt="Uploaded Image">
            <button type="button" @click="removeImage(index)">삭제</button>
          </div>
        </div>
      </div>
      <div class="form-section">
        <form @submit.prevent="createBoard">

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
          <!-- 의류 관련 입력 필드 -->
          <div class="apparel-row">
            <input type="text" id="outer" v-model="apparel.outer" class="apparel-input" placeholder="아우터 정보를 입력하세요">
            <input type="text" id="top" v-model="apparel.top" class="apparel-input" placeholder="상의 정보를 입력하세요">
          </div>
          <div class="apparel-row">
            <input type="text" id="bottom" v-model="apparel.bottom" class="apparel-input" placeholder="하의 정보를 입력하세요">
            <input type="text" id="shoes" v-model="apparel.shoes" class="apparel-input" placeholder="신발 정보를 입력하세요">
          </div>
          <div class="moveSubmitButton">
            <button type="submit" class="submitButton">등록</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>




<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { useOasisStore } from '@/stores/oasis';

const router = useRouter();
const { userId } = useOasisStore(); // 직접적으로 필요한 데이터만 추출

const board = ref({
  title: '',
  userId: userId, // 직접적으로 스토어에서 userId를 사용
  content: '',
  location: '',
  locationDetail: '',
  images: []
});

const apparel = ref({
  outer: '',
  top: '',
  bottom: '',
  shoes: ''
});

const imageFiles = ref([]);
const imagePreviews = ref([]);
const locationDetails = ref([]);

watch(() => board.value.location, (newLocation) => {
  switch (newLocation) {
    case '서울':
      locationDetails.value = ['강남', '북촌', '서촌', '성수', '신사', '신촌', '압구정', '양재천', '여의도', '이대', '잠실', '종로', '청담', '한남', '합정', '홍대'];
      break;
    case '부산':
      locationDetails.value = ['광안리', '기장', '전포', '해운대'];
      break;
    case '인천':
      locationDetails.value = ['부평', '을왕리', '인천공항'];
      break;
    case '대전':
      locationDetails.value = ['대전역', '둔산동', '성심당'];
      break;
    case '대구':
      locationDetails.value = ['동성로', '안지랑곱창골목', '이월드'];
      break;
    case '울산':
      locationDetails.value = ['간절곶', '삼산', '성남동', '태화강'];
      break;
    case '광주':
      locationDetails.value = ['광주송정역', '상무지구', '펭귄마을'];
      break;
    case '제주':
      locationDetails.value = ['서귀포', '애월', '우도', '제주공항'];
      break;
    case '강원도':
      locationDetails.value = ['강릉', '속초', '양양', '춘천', '평창'];
      break;
    case '경기도':
      locationDetails.value = ['강화도', '광교', '분당', '수원', '야탑', '의정부', '파주'];
      break;
    case '경상북도':
      locationDetails.value = ['경주', '안동', '영덕', '영양', '울진', '포항'];
      break;
    case '경상남도':
      locationDetails.value = ['거제', '김해', '창원', '통영'];
      break;
    case '전라북도':
      locationDetails.value = ['군산', '익산', '전주'];
      break;
    case '전라남도':
      locationDetails.value = ['목포', '여수'];
      break;
    case '충청북도':
      locationDetails.value = ['청주', '충주'];
      break;
    case '충청남도':
      locationDetails.value = ['공주', '당진', '세종'];
      break;
    default:
      locationDetails.value = []; // 기본값으로 빈 배열 설정
  }
});


// 파일 업로드 처리
const handleFileUpload = event => {
  imageFiles.value = Array.from(event.target.files);
  imagePreviews.value = imageFiles.value.map(file => URL.createObjectURL(file));
};

// 이미지 삭제 처리
const removeImage = index => {
  imageFiles.value.splice(index, 1);
  imagePreviews.value.splice(index, 1);
};

// 게시글 생성 처리
const createBoard = async () => {
  try {
    const formData = new FormData();
    formData.append('board', new Blob([JSON.stringify(board.value)], { type: 'application/json' }));
    formData.append('apparel', new Blob([JSON.stringify(apparel.value)], { type: 'application/json' }));
    imageFiles.value.forEach(file => formData.append('file', file));

    const response = await axios.post(`http://localhost:8080/board`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    router.push({ name: 'boardDetail', params: { id: response.data } });
  } catch (error) {
    console.error('게시글 등록 오류:', error);
    alert('권한이 없습니다. 로그인 페이지로 이동합니다.');
    router.push("/user/login");
  }
};
</script>





<style scoped>
* {
  font: var(--base-pre-font);
}

div {
  box-sizing: border-box;
}

.board-create {
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
  bottom: 20px
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

form button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

form button:hover {
  opacity: 0.85;
}

.image-previews {
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

.apparel-row {

  width: 100%;
  /* margin: 0 30px; */
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

.submitButton {
  font-size: var(--base-pre-font);
  float: right;
  margin-right: 10px;
  background-color: white;
  color: black;
  background-color: white;
  border: 1px solid black;
  box-shadow: 0 4px 5px -4px black;
  width: 100px;
  border-radius: 10px;
}

.submitButton:hover {
  background-color: var(--main-color);
}

.fileUpload {
  display: none;
}

.imageUpload {
  color: black;
  background-color: white;
  border: 1px solid black;
  border-radius: 10px;
  margin: 5px;
  padding: 5px;
}

.imageUpload:hover {
  background-color: var(--main-color);

}
</style>
