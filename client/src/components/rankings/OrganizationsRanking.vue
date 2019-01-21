<template id="">
  <ws-table
  :columns="columns"
  :fetch="getOrganizations"
  :format="format"
  :rawData="true"
  :dontPaginate="true" />
</template>

<script>
import API from './../../helpers/api.js';
import WsTable from "@/components/ws-framework/WsTable.vue";
import WsHero from "@/components/ws-framework/WsHero.vue";

export default {
  name: 'products-index',
  data: () => {
    return {
      amount: 100,
      columns: [{ field: 'sum', label: 'Profit' },
        { field: 'name', label: 'Organization Name' },
        { field: 'legalName', label: 'Legal Name' },
        { field: 'cuit', label: 'Cuit' }
      ]};
  },
  components: {
    WsTable,
    WsHero
  },
  methods: {
    format(res) {
      return {
        id: res.organization.id,
        name: res.organization.name,
        legalName: res.organization.legalName,
        cuit: res.organization.cuit,
        sum: '$ ' + res.sum
      }
    },
    getOrganizations() {
      return new API().get("/rankings/organizations", { type: "profit" });
    }
  }
}
</script>
