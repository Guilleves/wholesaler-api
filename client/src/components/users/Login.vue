<template>
  <body>
    <b-loading :is-full-page="false" :active.sync="loading" :canCancel="false" />
    <section class="hero is-primary">
      <div class="hero-body">
        <div class="container">
          <h1 class="title">
            Welcome!
          </h1>
          <h2 class="subtitle">
            Please complete with your credentials
          </h2>
        </div>
      </div>
    </section>
    <section class="section">
      <div class="container">
        <h1 class="title">Login</h1>
        <section>
          <form @submit.prevent="login()">
            <b-notification type="is-danger" has-icon :active.sync="showNotification">
              <ul v-bind:key="index" v-for="(notification, index) in notifications">
                <li>{{ notification }}</li>
              </ul>
            </b-notification>
            <b-field label="Username">
              <b-input
              v-model="username"/>
            </b-field>

            <b-field label="Password">
              <b-input type="password"
              v-model="password"
              password-reveal />
            </b-field>

            <b-field grouped position="is-left">
              <p class="control" position="is-right">
                <button class="button" type="button" @click="redirectSignup()">
                  Create an account
                </button>
              </p>
            </b-field>

            <b-field grouped position="is-right">
              <p class="control">
                <button class="button is-primary">
                  Submit
                </button>
              </p>
            </b-field>
          </form>
        </section>
      </div>
    </section>
  </body>
</template>

<script>
import API from "@/helpers/api.js";
import * as Session from "@/helpers/session.js";
import * as Notifier from "@/helpers/notifier.js";

export default {
  name: 'login',
  data: () => {
    return {
      loading: false,
      username: null,
      password: null,
      showNotification: false,
      notifications: []
    }
  },
  watch: {
    notifications: function(val) {
      this.showNotification = val.length != 0;
    }
  },
  methods: {
    login: function() {
      let vm = this;
      vm.loading = true;

      new API().post("/users/login", { username: this.username, password: this.password })
      .then((response) => {
        Session.set(response.data);
        vm.notifications = [];
        Notifier.info("Logged in");
        vm.$router.push("/");
        vm.loading = false;
      })
      .catch((error) => {
        vm.loading = false;
        Notifier.error("Please try again");

        if (error.response && error.response.data)
          vm.notifications = error.response.data;
      }); // No entiendo por qué no me anda el finally acá...
    },
    redirectSignup: function() {
      this.$router.push("/signup/");
    }
  }
}
</script>
