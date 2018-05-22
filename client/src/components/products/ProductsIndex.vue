<template id="">
  <div class="">
    <div class="">
      <option-filter filter="brands"></option-filter>
    </div>
    <div class="">
      <b-table :data="formattedProducts" :columns="columns"></b-table>
    </div>
  </div>
</template>

<script>
import API from './../../helpers/api.js';
import * as session from "./../../helpers/session.js";
import OptionFilter from "./OptionFilter.vue";

export default {
  name: 'products-index',
  data: () => {
    return {
      products: [],
      formattedProducts: [],
      columns: [
        {
          field: 'id',
          label: 'ID',
          width: '40',
          numeric: true
        },
        {
          field: 'name',
          label: 'Name',
        },
        {
          field: 'gtin',
          label: 'GTIN',
        },
        {
          field: 'brand',
          label: 'Brand',
          centered: true
        },
        {
          field: 'category',
          label: 'Category',
        }
      ]
    }
  },
  components: {
    OptionFilter
  },
  methods: {
    formatResponseIntoTable: function(products) {
      let formattedResponse = products.map(product => {
        return {
          id: product.id,
          name: product.name,
          gtin: product.gtin,
          brand: product.brand.name,
          category: product.category.name
        }
      });
      return formattedResponse;
    }
  },
  mounted : function() {
    // This must be done on login
    session.set({
      token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImlkIjo1fQ.GR8v-RyugBdtq21_XliVpG6DJypCkFxr1zI7YcwIntE"
    });
    var self = this;
    new API()
    .get('/products')
    .then((response) => {
      self.products = response.data;
      self.formattedProducts = self.formatResponseIntoTable(self.products);
    })
    .catch((error) => {
      console.log(error);
    });
  }
}
</script>
