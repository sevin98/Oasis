<template>
  <div id="container" class="d-flex align-content-start flex-wrap">
    <div class="profile-image p-2 flex-fill">
      <!-- 프로필 이미지 -->
      <div>
        <img :src="imagePath" />
        <input type="file" id="file" class="file" @change="fileSrc" />
        <label for="file" class="file-label">프로필 이미지 바꾸기</label>
      </div>
    </div>

    <div class="update-form p-2 flex-fill">
      <div class="col-md-12">
        <!-- 아이디 -->
        <div class="d-flex justify-content-between">
          <label for="inputId4" class="form-label">ID</label>
          <div>
            <!-- chatGPT 버튼 -->
            <button class="chatgpt-btn" @click="openModal">
              아이디 추천 받기
              <img
                src="https://cdn.oaistatic.com/_next/static/media/favicon-16x16.a052137e.png"
                style="
                  width: 20px;
                  height: 20px;
                  border: 1px solid lightgray;
                  border-radius: 5px;
                "
              />
            </button>
          </div>
        </div>
        <input
          type="text"
          class="form-control"
          id="inputId4"
          v-model="user.userId"
        />
      </div>

      <!-- 이메일 -->
      <div class="col-md-12 email">
        <label for="inputEmail4" class="form-label">EMAIL</label>
        <input
          type="email"
          class="form-control"
          id="inputEmail4"
          v-model="user.email"
        />
      </div>

      <!-- 나이 -->
      <div class="col-md-12 age">
        <label for="inputNumber4" class="form-label">AGE</label>
        <input
          type="number"
          class="form-control"
          id="inputNumber4"
          v-model="user.age"
        />
      </div>

      <!-- 성별 -->
      <div class="col-md-12 gender">
        <p>GENDER</p>
        <p @click="showGender" class="form-control">
          {{ user.gender == "male" ? "남" : "여" }}
        </p>
        <ul
          v-if="genderToggle"
          class="form-select"
          aria-label="Default select example"
          id="gender"
        >
          <li @click="changeGender('male')">남</li>
          <li @click="changeGender('female')">여</li>
        </ul>
      </div>
      <div class="col-md-12 update">
        <button @click="update" class="btn update-btn">UPDATE</button>
      </div>
    </div>

    <div class="chatgpt-modal">
      <ChatGpt
        :show="showModal"
        @close="closeModal"
        @change-name="changeName"
      />
    </div>
  </div>
</template>

<script setup>
import ChatGpt from "../chatgpt/ChatGpt.vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useOasisStore } from "@/stores/oasis";
import axios from "axios";

const router = useRouter();
const store = useOasisStore();

const genderToggle = ref(false);
const showGender = function () {
  genderToggle.value = !genderToggle.value;
};
const changeGender = function (gender) {
  user.value.gender = gender;
  genderToggle.value = false;
};

const showModal = ref(false);

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const changeName = function (args) {
  user.value.userId = args;
  showModal.value = false;
};

const id = ref(store.userId);
const user = ref({
  userId: "",
  email: "",
  age: 0,
  gender: "성별을 선택해주세요",
  profileImage: "",
});

let file = ref(null);
const imagePath = ref("");

const fileSrc = function (event) {
  file.value = event.target.files[0];
  imagePath.value = window.URL.createObjectURL(file.value);
};
const update = function () {
  const formData = new FormData();
  const json = JSON.stringify(user.value);
  const blob = new Blob([json], { type: "application/json" });

  formData.append("user", blob);
  if (file.value) {
    formData.append("file", file.value);
  }

  axios({
    url: `http://localhost:8080/user/${id.value}`,
    method: "PATCH",
    headers: {
      "Content-Type": "multipart/form-data",
    },
    data: formData,
  })
    .then(() => {
      store.setLoginUser(user.value.userId);
      router.push({ name: "userDetail", params: { id: id.value } });
    })
    .catch((err) => {
      alert("아이디가 중복됩니다. 다른 아이디를 입력해주세요.");
      console.log(err);
    });
};

onMounted(() => {
  axios({
    url: `http://localhost:8080/user/${id.value}`,
    method: "GET",
  })
    .then((res) => {
      user.value = res.data.user;
      imagePath.value = `http://localhost:8080/img/${user.value.profileImage}`;
    })
    .catch((err) => {
      console.log(err);
    });
});
</script>

<style scoped>
.profile-image {
  align-content: center;
}

.file {
  display: none;
}

.file-label {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 200px;
  height: 30px;
  border-radius: 10px;
  background-color: var(--main-color);
  margin-top: 20px;
  margin-left: 80px;
  box-shadow: 0 4px 5px -4px black;
  font: var(--base-pre-font);
}

.update-form {
  font: var(--base-pre-font);
  margin: 20px 0;
}
.form-control {
  border-width: 0 0 1px;
}
input:focus {
  outline: none;
}

.email,
.age,
.gender {
  margin-top: 20px;
}

.gender-value {
  border: 1px solid lightgray;
  box-shadow: 0 2px 3px -4px black;
  border-width: 0 0 1px;
}

li {
  list-style-type: none;
}

.update {
  margin-top: 40px;
}
.chatgpt-btn {
  box-shadow: 0 4px 5px -4px black;
  border-radius: 10px;
  background-color: var(--main-color);
  font-size: medium;
  padding: 5px 5px;
  border: 0px;
}
.update-btn {
  border-radius: 10px;
  background-color: var(--main-color);
  font-size: large;
  float: right;
  margin-right: 10px;
  box-shadow: 0 4px 5px -4px black;
}

img {
  width: 350px;
  height: 350px;
  border-radius: 50%;
}

.file-label:hover,
button:hover {
  background-color: white;
}
</style>
