<template>
  <div>
    <b-navbar fixed-top type="is-dark" >
        <template slot="brand">
            <b-navbar-item href="/">
                <router-link to="/" class="navbar-item">
                  <!--img src="@/assets/brand-logo-2.svg" alt="wholesaler" style="height: 3rem; max-height: 3rem;"-->
                  <span class="logo">Wholesaler</span>
                </router-link>
            </b-navbar-item>
        </template>
        <template slot="end">
            <b-navbar-item tag="div">
                <div class="buttons">
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
                </div>
            </b-navbar-item>
        </template>
    </b-navbar>
    <router-view />
  </div>
</template>

<script>
import * as Session from '@/helpers/session.js';
import * as Notifier from '@/helpers/notifier.js';

export default {
  name: 'Main',
  data: () => {
    return {
      loggedRole: Session.get().organization.role,
      expandedMenu: false
    }
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

    if (this.$router.history.current.path === "/")
    this.expandedMenu = false;
    else
    this.expandedMenu = true;
  },
  beforeRouteUpdate (to, from, next) {
    if (to.path === "/")
    this.expandedMenu = false;
    else
    this.expandedMenu = true;

    next();
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
