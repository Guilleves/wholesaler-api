<template>
  <section class="section">
    <div class="container">
      <div class="columns">
        <div class="column is-three-fifths">
          <div class="columns">
            <div class="column">
              <ws-keyword-search placeholder="Search users" @input="buildSearchCriteria($event)" />
            </div>
            <div class="column">
              <b-switch @input="toggleIncludeInactive($event)">
                Include inactive
              </b-switch>
            </div>
          </div>
        </div>
      </div>
      <ws-table
      :columns="columns"
      :fetch="getUsers"
      :filters="searchCriteria"
      :format="format"
      @select="onSelect"/>
      <router-link class="button is-dark" to="/">
        <span class="icon">
          <i class="fas fa-arrow-left"></i>
        </span>
        <span>Go back</span>
      </router-link>
    </div>
  </section>
</template>

<script>
import API from '@/helpers/api.js';
import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearchByJuan.vue";
import WsTable from "@/components/ws-framework/WsTable.vue";

export default {
  name: 'users-index',
  data() {
    return {
      includeInactive: false,
      searchCriteria: {},
      columns: [{
        field: 'id',
        label: 'ID',
        width: '40',
        numeric: true
      },
      { field: 'username', label: 'Username' },
      { field: 'fullname', label: 'Name' },
      { field: 'email', label: 'Email' }]
    };
  },
  components: {
    WsKeywordSearch,
    WsTable
  },
  methods: {
    format(user) {
      return {
        id: user.id,
        fullname: user.firstName + " " + user.lastName,
        username: user.username,
        email: user.email
      }
    },
    getUsers(searchCriteria) {
      return new API().get('/users', searchCriteria);
    },
    buildSearchCriteria(param) {
      this.searchCriteria = Object.assign({}, this.searchCriteria, param);
    },
    onSelect(user) {
      this.$router.push("/users/" + user.id);
    },
    toggleIncludeInactive(show) {
      if (show)
      this.buildSearchCriteria({ inactive: this.includeInactive });
      else
      this.buildSearchCriteria({ inactive: null });
    }
  }
}
</script>
