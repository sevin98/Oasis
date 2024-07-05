<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <header>
          <div class="search-bar-wrapper">
            <div class="search-bar">
              <div class="search-input-wrapper">
                <input class="search-input" type="text" placeholder="검색어를 입력하세요.." v-model="searchKeyword"
                  @keyup.enter="search" />
                <button @click="search" class="btn btn-secondary search-button">🔍</button>
              </div>
              <div>
                <select v-model="searchOption" class="search-option">
                  <option value="title">제목</option>
                  <option value="userId">유저 ID</option>
                  <option value="content">내용</option>
                </select>
              </div>
            </div>
          </div>
          <div class="order-container">
            <div class="orderBy">
              <button @click="toggleDropdown"
                class="btn btn-secondary dropdown-toggle no-arrow"><strong>⋮</strong></button>
              <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton" v-show="showDropdown">
                <li><a class="dropdown-item" @click="sortBy('boardId')">기본순</a></li>
                <li><a class="dropdown-item" @click="sortBy('viewCnt')">조회순</a></li>
                <li><a class="dropdown-item" @click="sortBy('heartCnt')">좋아요순</a></li>
                <li><a class="dropdown-item" @click="sortBy('comments')">댓글순</a></li>
              </ul>
            </div>
            <div class="order-buttons">
              <button @click="toggleOrder('asc')" class="order-buttons-asc">
                ↑
              </button>
              <button @click="toggleOrder('desc')" class="order-buttons-desc">
                ↓
              </button>
            </div>
          </div>
        </header>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="row">


          <div class="col-md-4 mb-4" v-for="(board, index) in filteredBoardList" :key="index">
            <router-link :to="{ name: 'boardDetail', params: { id: board.board.boardId } }">
              <div class="text-center">
                <img :src="getFirstImage(board.images)" class="img-fluid"
                  style="max-width: 100%; max-height: 100%; min-width: 100%;" alt="...">
              </div>
            </router-link>
            <div class="board-Title text-start">
              <span>{{ board.board.title }}</span>
            </div>
            <div class="content">
              <div class="d-flex justify-content-between align-items-center mb-2">
                <span>@{{ board.userId }}</span>
                <div>
                  <span>❤️{{ board.board.heartCnt }}</span>
                  <span>&#x1F4AC;{{ board.comments.length }}</span>
                  <span>👁️‍🗨️{{ board.board.viewCnt }}</span>
                </div>
              </div>
            </div>
          </div>


        </div>
      </div>
    </div>
    <div class="newPost">
      <router-link :to="{ name: 'boardCreate' }" class="btn btn-primary newPostbutton">게시글 등록</router-link>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const REST_BOARD_API = "http://localhost:8080/board";

export default {
  setup() {
    const ascOrder = ref(true);
    const boardList = ref([]);
    const sortByCriteria = ref('boardId');
    const showDropdown = ref(false);
    const searchKeyword = ref('');
    const searchOption = ref('title');
    const filteredBoardList = ref([]);

    const sortBoardList = () => {
      boardList.value.sort((a, b) => {
        const order = ascOrder.value ? 1 : -1;
        switch (sortByCriteria.value) {
          case 'boardId':
            return order * (a.board.boardId - b.board.boardId);
          case 'viewCnt':
            return order * (b.board.viewCnt - a.board.viewCnt);
          case 'heartCnt':
            return order * (b.board.heartCnt - a.board.heartCnt);
          case 'comments':
            return order * (b.comments.length - a.comments.length);
          default:
            return 0;
        }
      });
    };

    const sortBy = (criteria) => {
      sortByCriteria.value = criteria;
      sortBoardList();
      search();
      showDropdown.value = !showDropdown.value;
    };

    const toggleDropdown = () => {
      showDropdown.value = !showDropdown.value;
    };

    const toggleOrder = () => {
      ascOrder.value = !ascOrder.value;
      sortBoardList();
      search();
    };

    const search = () => {
      if (!searchKeyword.value) {
        filteredBoardList.value = boardList.value;
        return;
      }

      filteredBoardList.value = boardList.value.filter(board => {
        const searchText = searchKeyword.value.toLowerCase();
        switch (searchOption.value) {
          case 'title':
            return board.board.title.toLowerCase().includes(searchText);
          case 'userId':
            return board.userId.toLowerCase().includes(searchText);
          case 'content':
            return board.board.content.toLowerCase().includes(searchText);
          default:
            return false;
        }
      });
    };

    onMounted(() => {
      axios.get(REST_BOARD_API)
        .then(response => {
          boardList.value = response.data;
          filteredBoardList.value = boardList.value;
          sortBoardList();
        })
        .catch(error => {
          console.error('Error fetching board data:', error);
        });
    });

    const getFirstImage = (images) => {
      if (images && images.length > 0) {
        return `http://localhost:8080/img/${images[0].imagePath}`;
      } else {
        return '';
      }
    };

    return {
      searchKeyword,
      searchOption,
      filteredBoardList,
      toggleDropdown,
      sortBy,
      showDropdown,
      toggleOrder,
      search,
      getFirstImage
    };
  },
};
</script>

<style scoped>
* {
  font: var(--base-pre-font);
}

.board-Title {
  font: var(--lg-pre-font);
}

.container {
  padding: 20px;
}

.search-bar-wrapper {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.search-bar {
  display: flex;
  justify-content: center;

  width: 100%;
  max-width: 600px;
}

.search-button {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: 10px;
  background-color: white;
  border: none;
}

.search-input-wrapper {
  min-width: 450px;
  position: relative;
  display: flex;
}

.search-input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 10px
}

.search-option {
  margin-left: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-left: none;
  border-radius: 10px
}

.order-container {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 20px;
  margin-right: 5%;
}

.orderBy {
  position: relative;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  display: flex;
  flex-direction: column;
}

.order-buttons-asc,
.order-buttons-desc {
  background-color: white;
  border: none;
}

.order-buttons {
  margin-left: 10px;

}

.newPost {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.newPostbutton {
  border-radius: 10px;
  background-color: white;
  color: black;
  border: 1px solid black;
  font: var(--base-pre-font);
  margin-right: 5%;
  position: fixed;
  right: 20px;
  bottom: 20px;
  z-index: 999;

}

.newPostbutton:hover {
  opacity: 0.8;
  background-color: var(--main-color);
}

.row {
  align-content: center;
}

.no-arrow::after {
  display: none;
}

.dropdown-toggle {
  background-color: white;
  color: black;
  border: none;
}
</style>
