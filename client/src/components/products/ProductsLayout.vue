<template id="">
  <div>
    <ws-hero title="Products" description="View, create or delete products"/>
    <router-view />
  </div>
</template>

<script>
import WsHero from "@/components/ws-framework/WsHero.vue";
import * as Session from "@/helpers/session.js";

export default {
  components: {
    WsHero
  },
  mounted() {
    if (Session.get().organization && Session.get().organization.role !== "supplier")
      this.$router.push("/unauthorized");
  }
}
</script>
