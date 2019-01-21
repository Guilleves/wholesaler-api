<template id="">
  <div>
    <ws-hero title="Rankings" description="View reports for products, orders and organizations."/>
    <div>
      <section class="section">
        <div class="container">
          <b-field>
            <b-radio-button native-value="products" v-model="selectedRanking">
              <b-icon icon="fa fa-box" />
              <span>Top seller products</span>
            </b-radio-button>
            <b-radio-button native-value="organizations" v-model="selectedRanking">
              <b-icon icon="fa fa-users" />
              <span v-if="loggedRole === 'supplier'">Profits by retailer</span>
              <span v-else>Money spent on suppliers</span>
            </b-radio-button>
            <b-radio-button native-value="orders" v-model="selectedRanking">
              <b-icon icon="fa fa-shopping-cart" />
              <span>Orders per month</span>
            </b-radio-button>
          </b-field>
          <router-view />
          <br />
          <router-link class="button is-default" to="/">
            <span class="icon">
              <i class="fas fa-arrow-left"></i>
            </span>
            <span>Go back</span>
          </router-link>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import WsHero from "@/components/ws-framework/WsHero.vue";
import * as Session from "@/helpers/session.js";

export default {
  data() {
    return {
      loggedRole: Session.get().organization.role,
      selectedRanking: null
    }
  },
  watch: {
    selectedRanking(val) {
      if (val)
        this.$router.push(`/rankings/${val}`);
    }
  },
  beforeMount() {
    var pathArray = this.$route.path.split("/");
    this.selectedRanking = pathArray[2];
  },
  components: {
    WsHero
  }
}
</script>
