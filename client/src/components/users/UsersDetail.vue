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
          <b-field>
            <b-tag class="is-primary" rounded>{{ status }}</b-tag>
          </b-field>
          <b-field grouped position="is-right" expanded>
            <p class="control">
              <a class="button is-danger is-outlined" @click="remove" v-if="editing">
                <span class="icon">
                  <i class="fas fa-trash"></i>
                </span>
              </a>
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
        organization: null
      },
      editing: false,
      errors: [],
      loading: false,
      status: "new",
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
      this.$router.push(`/users`);
    },
    getOrganizations() {
      return new API().get("/organizations");
    },
    remove() {
      this.$dialog.confirm({
        title: 'Deleting user',
        message: 'Are you sure you want to <b>delete</b> this user?',
        confirmText: 'Delete user',
        type: 'is-danger',
        hasIcon: true,
        onConfirm: () => {
          this.loading = true;

          new API().delete("/users/" + this.user.id).then(() => {
            Notifier.success("User was deleted.");
            this.errors = [];
            this.loading = false;
            this.goBack();
          })
          .catch(error => {
            this.loading = false;

            if (error.response && error.response.data)
            this.errors = error.response.data;
          });
        }
      });
    },
    getUser(id) {
      return new API().get("/users/" + id);
    }
  },
  mounted() {
    let id = this.$route.params.id;

    if (!id || Number.isNaN(Number(id)))
      return;

    this.editing = true;
    this.loading = true;
    this.status = "editing"

    this.getUser(id).then(response => {
      this.loading = false;
      this.errors = [];
      this.user = response.data;
    })
    .catch(error => {
      this.loading = false;
      this.errors = error.response.data;
    });
  }
}
</script>
