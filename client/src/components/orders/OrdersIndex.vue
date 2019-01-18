<template id="">
  <section class="section">
    <div class="container">
      <article class="message is-dark" v-if="selectedProposal">
        <div class="message-body">
          <b>Selected proposal:</b> {{ selectedProposal.title }}
        </div>
      </article>
      <div class="columns">
        <div class="column is-three-quarters">
          <div class="columns">
            <div class="column">
              <b-field>
                <b-datepicker
                placeholder="From"
                icon="fam fa-calendar"
                @input="buildSearchCriteria({ fromDate: $event.getTime() })" />
              </b-field>
            </div>
            <div class="column">
              <b-field>
                <b-datepicker
                placeholder="To"
                icon="fam fa-calendar"
                @input="buildSearchCriteria({ toDate: $event.getTime() })" />
              </b-field>
            </div>
            <div class="column">
              <ws-option-filter
              :getOptions="getRetails"
              placeholder="Select a retailer"
              v-model="selectedRetail" />
            </div>
            <div class="column">
              <b-switch v-model="showDeleted">
                Show deleted orders
              </b-switch>
            </div>
          </div>
        </div>
        <div class="column">
          <b-field grouped position="is-right" v-if="proposalId && currentRole === 'retail'">
            <p class="control">
              <router-link class="button is-rounded is-primary" :to="`/proposals/${proposalId}/orders/new/`">
                <span class="icon">
                  <i class="fas fa-plus"></i>
                </span>
                <span>New order</span>
              </router-link>
            </p>
          </b-field>
        </div>
      </div>
      <ws-table
      :columns="columns"
      :fetch="getOrders"
      :filters="searchCriteria"
      :format="format"
      @select="onSelect"/>

      <router-link class="button is-default" :to="`/proposals/${proposalId}`" v-if="proposalId">
        <span class="icon">
          <i class="fas fa-arrow-left"></i>
        </span>
        <span>Go back</span>
      </router-link>
      <router-link class="button is-default" to="/" v-else>
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
import WsOptionFilter from "@/components/ws-framework/WsOptionFilter.vue";
import KeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";
import WsTable from "@/components/ws-framework/WsTable.vue";
import * as Session from '@/helpers/session.js';

export default {
  name: 'proposals-index',
  data() {
    return {
      selectedRetail: null,
      selectedProposal: null,
      proposalId: this.$route.params.proposalId,
      currentRole: Session.get().organization.role,
      showDeleted: false,
      searchCriteria: {},
      columns: [{
        field: 'id',
        label: 'ID',
        width: '40',
        numeric: true
      },
      { field: 'retail', label: 'Retailer' },
      { field: 'dateOrdered', label: 'Date ordered' }]
    };
  },
  watch: {
    selectedRetail(val) {
      this.buildSearchCriteria({ retailId: val ? val.id : null })
    },
    showDeleted(val) {
      this.buildSearchCriteria({ showDeleted: val })
    }
  },
  components: {
    WsOptionFilter,
    KeywordSearch,
    WsTable
  },
  methods: {
    format(proposal) {
      return {
        id: proposal.id,
        dateOrdered: new Date(proposal.dateOrdered).toLocaleDateString(),
        retail: proposal.retail.name
      }
    },
    getOrders(searchCriteria) {
      if (this.proposalId)
      return new API().get('/orders', Object.assign(searchCriteria, { proposalId: this.proposalId }));
      else
      return new API().get('/orders', searchCriteria);
    },
    buildSearchCriteria(param) {
      this.searchCriteria = Object.assign({}, this.searchCriteria, param);
    },
    onSelect(order) {
      if (this.proposalId)
      this.$router.push("/proposals/" + this.proposalId + "/orders/" + order.id);
      else
      this.$router.push("/orders/" + order.id);
    },
    getRetails() {
      return new API().get("/organizations/retails");
    }
  },
  beforeMount() {
    let vm = this;

    if (vm.proposalId && !Number.isNaN(Number(vm.proposalId))) {
      new API().get("/proposals/" + vm.proposalId).then(response => {
        vm.selectedProposal = response.data;
      });
    }
  }
}
</script>
