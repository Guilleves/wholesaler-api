<template>
  <section class="section">
    <div class="container">
      <b-loading :is-full-page="false" :active.sync="loading" :canCancel="false" />
      <form @submit.prevent="save()">
        <ws-error v-model="errors" />
        <div class="columns">
          <div class="column">
            <b-field label="Username">
              <b-input
              disabled
              placeholder="Type a username"
              v-model="user.username"/>
            </b-field>

            <b-field grouped>
              <b-field label="First Name" expanded>
                <b-input
                placeholder="Type the first name"
                v-model="user.firstName"/>
              </b-field>

              <b-field label="Last Name" expanded>
                <b-input
                placeholder="Type the last name"
                v-model="user.lastName"/>
              </b-field>
            </b-field>

            <b-field grouped>
              <b-field label="Reset Password" expanded>
                <b-input
                type="password"
                placeholder="Type the password"
                v-model="user.password"/>
              </b-field>

              <b-field label="Repeat Password" expanded>
                <b-input
                type="password"
                placeholder="Type again the password"
                v-model="user.repeatPassword"/>
              </b-field>
            </b-field>

            <b-field label="Email">
              <b-input
              placeholder="Type an email"
              v-model="user.email"/>
            </b-field>

            <b-field label="Organization">
              <ws-option-filter
              v-model="user.organization"
              :getOptions="getOrganizations"
              placeholder="Select an organization" />
            </b-field>
          </div>
        </div>
        <b-field grouped>
          <b-field grouped position="is-right" expanded>
            <p class="control">
              <button class="button is-outlined is-dark" type="button" @click="goBack()">
                Cancel
              </button>
              <button class="button is-success is-outlined">
                Save
              </button>
            </p>
          </b-field>
        </b-field>
      </form>
    </div>
  </section>
</template>

<script>
import WsError from "@/components/ws-framework/WsError.vue";
import API from "@/helpers/api.js";
import * as Notifier from "@/helpers/notifier.js";
import WsOptionFilter from "@/components/ws-framework/WsOptionFilter.vue";

export default {
  data() {
    return {
      user: {
        id: null,
        username: null,
        email: null,
        firstName: null,
        lastName: null,
        organization: null,
        password: "I'm a super real password",
        repeatPassword: "I'm a super real password"
      },
      errors: [],
      loading: false,
    }
  },
  components: {
    WsError,
    WsOptionFilter
  },
  methods: {
    save() {
      this.loading = true;

      let data = {
        id: this.user.id,
        username: this.user.username,
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        email: this.user.email,
        organizationId: this.user.organization.id
      };

      if (this.user.password !== "I'm a super real password" && this.user.repeatPassword !== "I'm a super real password") {
        data.password = this.user.password;
        data.repeatPassword = this.user.repeatPassword;
      }

      new API().put("/users", data).then(() => {
        Notifier.success("User was updated.");
        this.errors = [];
        this.loading = false;
        this.goBack();
      })
      .catch(error => {
        this.loading = false;

        Notifier.error("Couldn't save user.");

        if (error.response && error.response.data)
        this.errors = error.response.data;
      });
    },
    goBack() {
      this.$router.push(`/`);
    },
    getOrganizations() {
      return new API().get("/organizations");
    },
    getUser() {
      return new API().get("/users/logged");
    }
  },
  mounted() {
    this.loading = true;

    this.getUser().then(response => {
      this.loading = false;
      this.errors = [];
      this.user = response.data;

      this.user.password = "I'm a super real password";
      this.user.repeatPassword = "I'm a super real password";
    })
    .catch(error => {
      this.loading = false;
      this.errors = error.response.data;
    });
  }
}
</script>
