<template>
  <div id="container" class="position-sticky">
    <div class="nav justify-content-between flex-nowrap">
      <ul class="nav flex-nowrap">
        <li class="nav-item">
          <Home @click="goLink('main')" />
          <RouterLink
            :to="{ name: 'main' }"
            class="nav-link"
            aria-current="page"
            >HOME</RouterLink
          >
        </li>
        <li class="nav-item">
          <Feeds @click="goLink('boardList')" />
          <RouterLink :to="{ name: 'boardList' }" class="nav-link"
            >FEEDS</RouterLink
          >
        </li>
      </ul>
      <ul class="nav">
        <li class="nav-item">
          <RouterLink :to="{ name: 'main' }" class="nav-link title-container">
            <span class="title">Oas:is</span><br />
            <span class="title-description">outfit and style in scape</span>
          </RouterLink>
        </li>
      </ul>

      <ul class="nav flex-nowrap" v-if="store.loginUser !== null">
        <li class="nav-item">
          <My @click="goLink('userDetail')" />
          <RouterLink
            :to="{ name: 'userDetail', params: { id: store.userId } }"
            class="nav-link"
            >MY</RouterLink
          >
        </li>
        <li class="nav-item">
          <Logout @click="logout" />
          <a href="" @click="logout" class="nav-link">LOGOUT</a>
        </li>
      </ul>

      <ul class="nav flex-nowrap" v-else>
        <li class="nav-item">
          <Login @click="goLink('userLogin')" />
          <RouterLink :to="{ name: 'userLogin' }" class="nav-link"
            >LOGIN</RouterLink
          >
        </li>
        <li class="nav-item">
          <Signup @click="goLink('userSignup')" />
          <RouterLink :to="{ name: 'userSignup' }" class="nav-link"
            >SIGN UP</RouterLink
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useOasisStore } from "@/stores/oasis";
import { RouterLink, useRouter } from "vue-router";
import Home from "@/assets/svg/home.svg";
import Feeds from "@/assets/svg/feeds.svg";
import Login from "@/assets/svg/login.svg";
import Logout from "@/assets/svg/logout.svg";
import My from "@/assets/svg/my.svg";
import Signup from "@/assets/svg/signup.svg";

const store = useOasisStore();
const router = useRouter();

const goLink = (data) => {
  if (data == "userDetail") {
    router.push({ name: data, params: { id: store.userId } });
  } else router.push({ name: data });
};
const logout = function () {
  sessionStorage.clear();
  store.setLoginUser(null);
  store.setUserId(0);
  router.replace({ name: "main" });
};
</script>

<style scoped>
#container {
  width: 100%;
  padding: 10px 0;
  text-align: center;
  opacity: 0.8;
  font: var(--sm-pre-font);
  background-color: var(--main-color);
}

a {
  text-decoration: none;
  color: black;
}

a:active {
  color: black;
}

a:visited {
  color: black;
}

.nav-item {
  margin: 0 10px;
}

.title {
  font-size: xx-large;
  letter-spacing: 1px;
}

.title-description {
  font-size: 1vw;
}

.title-container {
  opacity: 0.9;
  font-weight: bold;
  text-shadow: 2px 2px 2px gray;
  letter-spacing: 0.5px;
}
</style>
