<template>
  <div id="container">
    <footer class="footer">
      <nav id="nav" class="footer-nav">
        <Router-link to="/">Oas:is</Router-link>
        <p>Contact us</p>
        <p>Help</p>
        <p>2567, Nambusunhwan-ro, Seocho-gu, Seoul, Republic of Korea</p>
        <p>Who We Are ?</p>
      </nav>
    </footer>
  </div>
</template>

<script setup></script>

<style scoped>
#container {
  padding-left: 20px;
  width: 100%;
  padding: 0;
  font: var(--sm-pre-font);
  background-color: var(--main-color);
}

a {
  text-decoration: none;
  color: black;
}

.footer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 89px;
  background-color: var(--main-color);
}

.footer-nav {
  display: flex;
  gap: 130px;
  justify-content: space-between;
  align-items: center;
}

.footer-nav p,
.footer-nav Router-link {
  margin: 0;
  padding: 0 10px;
  text-align: center;
}
</style>
