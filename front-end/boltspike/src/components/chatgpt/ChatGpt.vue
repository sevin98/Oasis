<template>
  <div class="modal" v-if="show" @click="closeModal">
    <div class="modal-content" @click.stop>
      <span class="close" @click="closeModal">&times;</span>
      <h2 class="modal-title fs-5">ChatGPT</h2>
      <div class="input-content">
        <textarea
          v-model="userInput"
          placeholder="내용을 입력하세요..."
          @keyup.enter="sendMessage"
        ></textarea>
      </div>
      <div v-if="response" class="response">
        <p>{{ response }}</p>
      </div>
      <div class="input-recommended-name">
        <input
          type="text"
          v-model="changedName"
          @keyup.enter="changeName"
          class="change-name-input"
        />
        <button @click="changeName" class="change-btn">use this ID</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import axios from "axios";

const emit = defineEmits(["close", "changeName"]);
defineProps({ show: Boolean });
const closeModal = () => {
  userInput.value = "";
  response.value = null;
  changedName.value = "";
  emit("close");
};
const userInput = ref("");
const response = ref(null);
const changedName = ref("");
const changeName = () => {
  emit("changeName", changedName.value);
  userInput.value = "";
  response.value = null;
  changedName.value = "";
};

const sendMessage = async () => {
  if (!userInput.value) return;

  const apiKey = import.meta.env.VITE_CHATGPT_API_KEY;
  const apiUrl = "https://api.openai.com/v1/chat/completions";

  try {
    const res = await axios.post(
      apiUrl,
      {
        model: "gpt-3.5-turbo",
        messages: [{ role: "user", content: userInput.value }],
      },
      {
        headers: {
          Authorization: `Bearer ${apiKey}`,
          "Content-Type": "application/json",
        },
      }
    );

    response.value = res.data.choices[0].message.content;
  } catch (error) {
    console.error(error);
    response.value = "Error: Could not get response from ChatGPT";
  }
};
</script>

<style scoped>
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-title {
  margin-bottom: 10px;
  color: black;
  opacity: 0.8;
  text-align: center;
  font: var(--sm-pre-font);
}
.modal-content {
  background-color: var(--gray200-color);
  padding: 20px;
  border-radius: 10px;
  width: 500px;
  max-width: 90%;
  box-shadow: 0 4px 5px -4px black;
  border: none;
  font: var(--base-pre-font);
}
.close {
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}
textarea {
  font: var(--base-pre-font);
  width: 100%;
  height: 100px;
  padding-top: 5px;
  padding-left: 5px;
  border-radius: 10px;
  border: none;
  box-shadow: 0 4px 5px -4px black;
  outline: none;
}

.input-recommended-name {
  display: flex;
  justify-content: center;
  margin-top: 5px;
  gap: 20px;
}
.response {
  font: var(--sm-pre-font);
  margin-top: 10px;
  text-align: center;
}

.change-name-input {
  width: 50%;
  height: 35px;
  border: none;
  border-radius: 8px;
  box-shadow: 0 4px 5px -4px black;
  outline: none;
}

.change-btn {
  background-color: white;
  font: var(--sm-pre-font);
  font-size: large;
  margin-left: 10px;
  border-radius: 10px;
  border: none;
  box-shadow: 0 4px 5px -4px black;
}
</style>
