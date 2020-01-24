<template>
  <div id="app">
    <div class="container shadow-lg">
      <div class="text-center">
        <span class="spinner-border spinner-border-sm" v-show="loadCateg"></span>
      </div>
      <h2>category</h2>
      <div class="col-3">
        <div
          class="nav flex-column nav-pills"
          id="v-pills-tab"
          role="tablist"
          aria-orientation="vertical"
        >
          <a
            v-for="todo of todos"
            :key="todo.nameAct"
            class="nav-link"
            data-toggle="pill"
            :href="'#' + todo.nameCategory"
            role="tab"
            aria-controls="v-pills-home"
            aria-selected="true"
          >{{todo.nameCategory}}</a>
        </div>
      </div>
      <div class="col-9">
        <div class="tab-content" id="v-pills-tabContent">
          <div
            v-for="todo of todos"
            :key="todo.nameCategory"
            class="tab-pane fade show"
            :id="todo.nameCategory"
            role="tabpanel"
            aria-labelledby="v-pills-home-tab"
          >
            <div class="card-group">
              <div v-for="activity of todo.activities" :key="activity.nameAct">
                <div v-if="activity.status==='WAIT'">
                  <div
                    v-if="!activity.userHas"
                    class="card text-white bg-warning mb-3"
                    style="max-width: 9rem;"
                  >
                    <div class="card-header">waiting</div>
                    <div class="card-body">
                      <h5 class="card-title">{{activity.nameAct}}</h5>
                    </div>
                  </div>
                </div>
                <div v-else>
                  <div
                    v-if="!activity.userHas"
                    class="card text-white bg-primary mb-3"
                    style="max-width: 9rem;"
                  >
                    <div class="card-header">Can adding</div>
                    <div class="card-body">
                      <h5 class="card-title">{{activity.nameAct}}</h5>
                      <button
                        type="button"
                        @click="addActivity(activity)"
                        class="btn btn-success"
                      >add</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container shadow-lg">
      <div class="text-center">
        <span class="spinner-border spinner-border-sm" v-show="loadAct"></span>
      </div>
      <br />
      <h2>activities</h2>
      <br />

      <div v-for="todo of todos" :key="todo.nameCategory">
        <div class="alert alert-secondary" role="alert">{{todo.nameCategory}}</div>
        <div class="card-group">
          <div v-for="activity of todo.activities" :key="activity.nameAct">
            <div v-if="activity.status==='WAIT'">
              <div
                v-if="activity.userHas"
                class="card text-white bg-warning mb-3"
                style="max-width: 9rem;"
              >
                <div class="card-header">waiting</div>
                <div class="card-body">
                  <h5 class="card-title">{{activity.nameAct}}</h5>
                </div>
              </div>
            </div>
            <div v-else>
              <div
                v-if="activity.userHas"
                class="card text-white bg-success mb-3"
                style="max-width: 9rem;"
              >
                <div class="card-header">adding</div>
                <div class="card-body">
                  <h5 class="card-title">{{activity.nameAct}}</h5>
                  <button
                    type="button"
                    @click="deleteActivity(activity)"
                    class="btn btn-danger"
                  >delete</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container shadow-lg">
      <div class="text-center">
        <span class="spinner-border spinner-border-sm" v-show="loadStat"></span>
      </div>
      <br />
      <h2>statistics</h2>
      <br />
      <input class="btn btn-primary" type="button" value="Click me" @click="showStat()" />

      <table class="table">
        <tr>
          <th>Activities</th>
          <th>Date</th>
          <th>Time</th>
        </tr>
        <tr v-for="currency in info " v-bind:key="currency.nameAct">
          <td>
            <h5>{{currency.name}}</h5>
          </td>
          <td>
            <h5>{{currency.time}}</h5>
          </td>
          <td>
            <h5>{{currency.date}}</h5>
          </td>
        </tr>
      </table>
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
      info: [],
      todoName: "",
      loadCateg: false,
      loadAct: false,
      loadStat: false
    };
  },

  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },

  async created() {
    try {
      this.loadCateg = true;
      this.loadAct = true;
      this.loadStat = true;
      const res = await axios.post(API_URL + "getAllActivityByUser", {
        userName: this.currentUser.username
      });
      this.todos = res.data;
      this.loadCateg = false;
      this.loadAct = false;
      this.loadStat = false;
    } catch (e) {
      UserService.getPublicContent.then(error => {
        this.todoName = error.response.data.message;
      });
    }
  },
  methods: {
    async showStat() {
      try {
        this.loadStat = true;
        const res2 = await axios.post(API_URL + "getStatUser", {
          userName: this.currentUser.username
        });
        this.info = res2.data;
        this.loadStat = false;
      } catch (e) {
        UserService.getPublicContent.then(error => {
          this.todoName = error.response.data.message;
        });
      }
    },
    async addActivity(currency) {
      try {
        this.loadCateg = true;
        const res3 = await axios.post(API_URL + "addActForUser", {
          action: "ADD",
          nameAct: currency.nameAct,
          userName: this.currentUser.username
        });
        this.todos = res3.data;
        this.loadCateg = false;
      } catch (e) {
        UserService.getPublicContent.then(error => {
          this.todoName = error.response.data.message;
        });
      }
    },
    async deleteActivity(currency) {
      try {
        this.loadAct = true;
        const res4 = await axios.post(API_URL + "addActForUser", {
          action: "DELETE",
          nameAct: currency.nameAct,
          userName: this.currentUser.username
        });
        this.todos = res4.data;
        this.loadAct = false;
      } catch (e) {
        UserService.getPublicContent.then(error => {
          this.todoName = error.response.data.message;
        });
      }
    }
  }
};
</script>
 <style scoped>
.card {
  margin: 30px;
}
.container {
  text-align: center;
  margin-top: 10px;
  margin-bottom: 30px;
  width: 80%;
  transition: 1s;
}
.container:hover {
  width: 100%;
  transition: 1.5s;
}
.main {
  text-align: center;
  width: 100%;
}
button {
  margin-top: 20px;
}
.card:hover {
  background-color: green;
}
table {
  margin-top: 20px;
}
</style>