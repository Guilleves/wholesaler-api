<template>
    <div>
        <nav class="navbar is-transparent is-fixed-top is-dark">
            <div class="navbar-brand">
                <router-link to="/" class="navbar-item">
                    <!--img src="@/assets/brand-logo-2.svg" alt="wholesaler" style="height: 3rem; max-height: 3rem;"-->
                    <span class="logo">Wholesaler</span>
                </router-link>
                <div class="navbar-burger burger" data-target="navbarExampleTransparentExample">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>

            <div id="navbarExampleTransparentExample" class="navbar-menu">
                <div class="navbar-start">
                    <div class="navbar-item has-dropdown is-hoverable">
                        <router-link class="navbar-link" to="/">
                            Admin
                        </router-link>
                        <div class="navbar-dropdown is-boxed">
                            <router-link class="navbar-item" to="/products">
                                Products
                            </router-link>
                            <router-link class="navbar-item" to="/proposals">
                                Proposals
                            </router-link>
                            <router-link class="navbar-item" to="/orders">
                                Orders
                            </router-link>
                            <router-link class="navbar-item" to="/users">
                                Users
                            </router-link>
                        </div>
                    </div>
                </div>

                <div class="navbar-end">
                    <div class="navbar-item">
                        <div class="field is-grouped">
                            <p class="control">
                                <router-link class="button is-primary" to="/my-account">
                                    <span class="icon">
                                        <i class="fas fa-user"></i>
                                    </span>
                                    <span>My account</span>
                                </router-link>
                                <a class="button is-light" @click="logout()">
                                    <span class="icon">
                                        <i class="fas fa-power-off"></i>
                                    </span>
                                </a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
        <router-view />
    </div>
</template>

<script>
import * as Session from '@/helpers/session.js';
import * as Notifier from '@/helpers/notifier.js';

export default {
    name: 'Main',
    props: {
        msg: String
    },
    methods: {
        logout() {
            Session.clearToken();
            this.$router.push("/login/");
            Notifier.info("Logged out");
        }
    },
    beforeMount() {
        if (!Session.getToken())
        this.$router.push("/login/");
    }
}
</script>

<style>
.logo {
    font: bold 21px "Segoe UI";
    color: #747273;
    line-height: 1.5em;
    padding-left: 1.7em;
}

.logo:before {
    content: '\2006';
    position: absolute;
    height: 0.095em;
    left: 10px;
    box-shadow: 0.35em 0.35em 0 0.25em #efebfa,
    1.05em 0.35em 0 0.25em #7957d5,
    0.35em 0.97em 0 0.25em #7957d5,
    1.05em 0.97em 0 0.25em #7957d5;
}
</style>
