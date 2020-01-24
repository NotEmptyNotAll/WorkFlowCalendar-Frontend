<template>
  <div id="app">
    <div class="container shadow-lg">
      <div class="text-center">
        <span class="spinner-border spinner-border-sm" v-show="loading"></span>
      </div>
      <br />
      <h1>your activity</h1>
      <br />
      <table class="table table-hover">
        <tr>
          <th>Category</th>
          <th>Activity</th>
          <th>Time( in minutes)</th>
          <th>Action</th>
        </tr>
        <tr v-for="currency in todos " v-bind:key="currency.nameAct">
          <td>
            <h5>{{currency.nameCateg}}</h5>
          </td>
          <td>
            <h5>{{currency.nameAct}}</h5>
          </td>
          <td>
            <input type="number" min="1" max="420" :id="currency.nameAct" />
          </td>

          <td v-if="currency.status==='WAIT'">
            <input class="btn btn-success disabled" type="button" value="done" />
          </td>
          <td v-else>
            <input class="btn btn-primary" type="button" value="send" @click="addTodo(currency)" />
          </td>
        </tr>
      </table>
      <br />
    </div>
  </div>
</template>


<script>
import axios from "axios";
import UserService from "../services/user.service";
const API_URL = "http://localhost:5050/api/test/";
export default {
  name: "user",
  data() {
    return {
      todos: [],
      todoName: "",
      loading: false
    };
  },

  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },

  async created() {
    try {
      this.loading = true;
      const res = await axios.post(API_URL + "getUserAct", {
        userName: this.currentUser.username
      });

      this.todos = res.data;
      this.loading = false;
    } catch (e) {
      UserService.getPublicContent.then(error => {
        this.todoName = error.response.data.message;
      });
    }
  },
  methods: {
    async addTodo(currency) {
      try {
        this.loading = true;
        const res2 = await axios.post(API_URL + "addStatistics", {
          time: document.getElementById(currency.nameAct).value,
          nameActivity: currency.nameAct,
          nameUser: this.currentUser.username
        });
        this.todos = res2.data;
        this.loading = false;
      } catch (e) {
        UserService.getPublicContent.then(error => {
          this.todoName = error.response.data.message;
        });
      }
    }
  }
};
</script>
<style>
.container {
  margin-top: 10%;
  width: 80%;
  transition: 1s;
  text-align: center;
}
.container:hover {
  width: 100%;
  transition: 1.5s;
}
</style>