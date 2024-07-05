<template>
    <div v-if="board">
      <h1>{{ board.title }}</h1>
      <p>{{ board.content }}</p>
      <p><strong>Outer:</strong> {{ board.apparel.outer }}</p>
      <p><strong>Top:</strong> {{ board.apparel.top }}</p>
      <p><strong>Bottom:</strong> {{ board.apparel.bottom }}</p>
      <p><strong>Shoes:</strong> {{ board.apparel.shoes }}</p>
      <p><strong>Location:</strong> {{ board.location }}</p>
      <p><strong>Location Detail:</strong> {{ board.locationDetail }}</p>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      board: {
        type: Object,
        required: true,
      },
    },
  };
  </script>
  
  <style scoped>
  </style>
  