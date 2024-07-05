<template>
  <div id="container" class="d-flex align-content-start flex-wrap">
    <div class="service-description-container p-2 flex-fill">
      <p class="service-name">Oas:is</p>
      <p class="service-description">전국의 OOTD를 한 눈에</p>
    </div>

    <div class="signup-form p-2 flex-fill">
      <!-- 아이디 -->
      <div class="col-md-12">
        <label for="inputId4" class="form-label">ID</label>
        <input
          type="text"
          class="form-control"
          id="inputId4"
          v-model="user.userId"
        />
      </div>

      <!-- 비밀번호 -->
      <div class="col-md-12 password">
        <label for="inputPassword4" class="form-label">PASSWORD</label>
        <input
          type="password"
          class="form-control"
          id="inputPassword4"
          v-model="user.password"
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
          {{ inputGender }}
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

      <!-- 회원가입 버튼 -->
      <div class="col-md-12 signup">
        <button @click="signup" class="btn">SIGN UP</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useOasisStore } from "@/stores/oasis";
import axios from "axios";

const router = useRouter();
const store = useOasisStore();

const user = ref({
  userId: "",
  password: "",
  email: "",
  age: 0,
  gender: "성별을 선택해주세요",
  profileImage: "default.png",
});

const genderToggle = ref(false);
const inputGender = ref("성별을 선택해주세요");
const showGender = function () {
  genderToggle.value = !genderToggle.value;
};
const changeGender = function (gender) {
  user.value.gender = gender;
  inputGender.value = user.value.gender == "male" ? "남" : "여";
  genderToggle.value = false;
};

const signup = function () {
  axios({
    url: "http://localhost:8080/user/signup",
    method: "POST",
    data: user.value,
  })
    .then(() => {
      router.push({ name: "userLogin" });
    })
    .catch((err) => {
      console.log(err);
    });
};
</script>

<style scoped>
.service-name {
  font: var(--xl-pre-font);
  font-size: 70px;
}
.service-description {
  font: var(--base-pre-font);
  font-size: 25px;
  margin-top: 40px;
}
.service-description-container {
  align-content: center;
}
.signup-form {
  font: var(--base-pre-font);
  margin: 20px 0;
}
.form-control {
  border-width: 0 0 1px;
}
input:focus {
  outline: none;
}
.password,
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

.signup {
  margin-top: 40px;
}
button {
  border: none;
  box-shadow: 0 4px 5px -4px black;
  border-radius: 10px;
  background-color: var(--main-color);
  font-size: large;
  float: right;
  margin-right: 10px;
}
</style>
