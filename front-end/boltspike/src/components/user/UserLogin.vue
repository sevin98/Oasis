<template>
  <div id="container" class="d-flex align-content-start flex-wrap">
    <div class="service-description-container p-2 flex-fill">
      <p class="service-name">Oas:is</p>
      <p class="service-description">전국의 OOTD를 한 눈에</p>
    </div>

    <div class="login-form p-2 flex-fill">
      <div class="col-md-12">
        <label for="inputEmail4" class="form-label">ID</label>
        <input
          type="text"
          class="form-control"
          id="inputEmail4"
          v-model="user.userId"
        />
      </div>
      <div class="col-md-12 password">
        <label for="inputPassword4" class="form-label">PASSWORD</label>
        <input
          type="password"
          class="form-control"
          id="inputPassword4"
          v-model="user.password"
          @keyup.enter="login"
        />
      </div>
      <div class="col-md-12 login">
        <button @click="login" class="btn">LOGIN</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useOasisStore } from "@/stores/oasis";
import axios from "axios";

const store = useOasisStore();
const router = useRouter();
const user = ref({
  userId: "",
  password: "",
});

const login = function () {
  axios({
    url: "http://localhost:8080/user/login",
    method: "POST",
    data: user.value,
  })
    .then((res) => {
      store.setLoginUser(user.value.userId);
      store.setUserId(res.data);
    })
    .then(() => {
      router.push({ name: "main" });
    })
    .catch((error) => {
      console.log(error);
      alert("로그인 정보가 잘못되었습니다. 다시 로그인해주세요.");
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
.login-form {
  font: var(--base-pre-font);
  margin: 15% 0;
}
input {
  border-width: 0 0 1px;
}
input:focus {
  outline: none;
}
.password {
  margin-top: 20px;
}
.login {
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
