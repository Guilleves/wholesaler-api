<template id="">
  <ws-table
  :columns="columns"
  :fetch="getOrders"
  :format="format"
  :rawData="true"
  :dontPaginate="true" />
</template>

<script>
import API from '@/helpers/api.js';
import WsTable from "@/components/ws-framework/WsTable.vue";
import WsHero from "@/components/ws-framework/WsHero.vue";

export default {
  name: 'products-index',
  data: () => {
    return {
      amount: 100,
      columns: [
        { field: 'month', label: 'Month' },
        { field: 'count', label: 'Amount of orders in month', numeric: true }
      ]};
    },
    components: {
      WsTable,
      WsHero
    },
    methods: {
      format(order) {
        return {
          month: order.date,
          count: order.count
        }
      },
      getOrders() {
        return new API().get("/rankings/orders");
      }
    }
  }
  </script>
