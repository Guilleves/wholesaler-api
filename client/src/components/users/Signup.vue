<template>
    <body>
        <section class="hero is-primary">
            <div class="hero-body">
                <div class="container">
                    <h1 class="title">
                        Welcome aboard!
                    </h1>
                    <h2 class="subtitle">
                        Please complete with your personal information
                    </h2>
                </div>
            </div>
        </section>
        <section class="section">
            <div class="container">
                <h1 class="title">Signup</h1>
                <section>
                    <form @submit.prevent="signup()">
                        <b-notification type="is-danger" has-icon :active.sync="showNotification">
                            <ul v-bind:key="index" v-for="(notification, index) in notifications">
                                <li>{{ notification }}</li>
                            </ul>
                        </b-notification>
                        <b-field label="Username">
                            <b-input
                            v-model="username"/>
                        </b-field>

                        <b-field grouped>
                            <b-field label="Password" expanded>
                                <b-input type="password"
                                v-model="password"
                                password-reveal />
                            </b-field>

                            <b-field label="Repeat password" expanded>
                                <b-input type="password"
                                v-model="repeatPassword"
                                password-reveal />
                            </b-field>
                        </b-field>

                        <b-field label="Email">
                            <b-input
                            v-model="email"/>
                        </b-field>

                        <b-field grouped>
                            <b-field label="Firstname" expanded>
                                <b-input
                                v-model="firstName"/>
                            </b-field>

                            <b-field label="Lastname" expanded>
                                <b-input
                                v-model="lastName"/>
                            </b-field>
                        </b-field>

                        <b-field grouped position="is-left">
                            <p class="control" position="is-right">
                                <button class="button" type="button" @click="redirectLogin()">
                                    Already have an account
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

export default {
    name: 'signup',
    data: () => {
        return {
            username: null,
            password: null,
            repeatPassword: null,
            firstName: null,
            lastName: null,
            email: null,
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
        signup: function() {
            new API().post("/users/signup", { firstName: this.firstName, lastName: this.lastName, email: this.email, username: this.username, password: this.password, repeatPassword: this.repeatPassword })
            .then((response) => {
                Session.set(response.data);
                this.notifications = [];
                this.$router.push("/home/");
            })
            .catch((error) => {
              if (error.response)
                this.notifications = error.response.data;
              console.log(error);
            });
        },
        redirectLogin: function() {
            this.$router.push("/login/");
        }
    }
}
</script>
