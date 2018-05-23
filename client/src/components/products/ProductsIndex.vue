<template id="">
  <div>
    <div>
      <keyword-search></keyword-search>
      <option-filter option-type="brandId" filter="brands" @selected="buildSearchCriteria($event)"></option-filter>
      <option-filter option-type="categoryId" filter="categories" @selected="buildSearchCriteria($event)"></option-filter>
    </div>
    <div>
      <b-table :data="formattedProducts" :columns="columns"></b-table>
    </div>
  </div>
</template>

<script>
import API from './../../helpers/api.js';
import * as session from "./../../helpers/session.js";
import OptionFilter from "./OptionFilter.vue";
import KeywordSearch from "./KeywordSearch.vue";

function getProducts(data){
  return new API().get('/products', data);
}

export default {
  name: 'products-index',
  data: () => {
    return {
      searchCriteria: {},
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
    OptionFilter,
    KeywordSearch
  },
  methods: {
    buildSearchCriteria: (param) => {
      debugger
      Object.assign(this.searchCriteria, param);
      getProducts({brandId: 2}).then((response) => {
        this.products = response.data;
        this.formattedProducts = this.formatResponseIntoTable(self.products);
      })
      .catch((error) => {
        console.log(error);
      });;
    },
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
    },
  },
  mounted : function() {
    // This must be done on login
    session.set({
      token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImlkIjo1fQ.GR8v-RyugBdtq21_XliVpG6DJypCkFxr1zI7YcwIntE"
    });
    var self = this;
    getProducts().then((response) => {
      self.products = response.data;
      self.formattedProducts = self.formatResponseIntoTable(self.products);
    })
    .catch((error) => {
      console.log(error);
    });
  }
}
</script>
