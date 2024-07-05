<template>
    <div class="board-list-item">
      <router-link :to="{ name: 'BoardDetail', params: { id: board.boardId }}">
        <h3>{{ board.title }}</h3>
        <p>{{ board.content }}</p>
        <p><strong>User ID:</strong> {{ board.userId }}</p>
        <p><strong>Likes:</strong> {{ board.heartCnt }}</p>
        <p><strong>Comments:</strong> {{ board.comments.length }}</p>
        <p><strong>Views:</strong> {{ board.viewCnt }}</p>
      </router-link>
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
  .board-list-item {
    border: 1px solid #ddd;
    padding: 16px;
    margin-bottom: 16px;
    border-radius: 8px;
    transition: background-color 0.3s;
  }
  
  .board-list-item:hover {
    background-color: #f5f5f5;
  }
  </style>
  