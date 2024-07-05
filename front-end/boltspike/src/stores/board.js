import { defineStore } from "pinia";
import axios from "axios";

export const useBoardStore = defineStore("board", {
  state: () => ({
    apparel: {
      outer: "",
      top: "",
      bottom: "",
      shoes: "",
    },
  }),
  actions: {
    setApparel(apparel) {
      this.apparel = apparel;
    },
    updateApparel(apparel) {
      this.apparel = apparel;
    },
    async fetchBoardList() {
      try {
        const response = await axios.get("http://localhost:8080/board");
        this.boardList = response.data;
      } catch (error) {
        console.error("Error fetching board list:", error);
      }
    },
    async fetchBoardDetail(id) {
      try {
        const response = await axios.get(`http://localhost:8080/board/${id}`);
        this.boardDetail = response.data;
      } catch (error) {
        console.error("Error fetching board detail:", error);
      }
    },
    async createBoard(boardData) {
      try {
        await axios.post("http://localhost:8080/board", boardData);
        this.fetchBoardList();
      } catch (error) {
        console.error("Error creating board:", error);
      }
    },
  },
  persist: true,
});
