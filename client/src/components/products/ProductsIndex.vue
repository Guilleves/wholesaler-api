<template id="">
    <section class="section">
      <div class="container">
        <div class="columns">
          <div class="column is-two-fifths">
            <ws-keyword-search @input="buildSearchCriteria($event)"></ws-keyword-search>
          </div>
          <div class="column">
            <ws-option-filter
            option-type="brandId"
            filter="brands"
            placeholder="Select a brand"
            @selected="buildSearchCriteria($event)"></ws-option-filter>
          </div>
          <div class="column">
            <ws-option-filter
              option-type="categoryId"
              filter="categories"
              placeholder="Select a category"
              @selected="buildSearchCriteria($event)"></ws-option-filter>
          </div>
          <div class="column">
            <button class="button is-primary" slot="trigger" @click="newProduct()">New product</button>
          </div>
        </div>
      </div>
      <div class="container">
        <b-table :data="formattedProducts" :columns="columns"></b-table>
      </div>
    </section>
</template>

<script>
import API from './../../helpers/api.js';
import WsOptionFilter from "@/components/ws-framework/WsOptionFilter.vue";
import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";

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
    WsOptionFilter,
    WsKeywordSearch
  },
  methods: {
    buildSearchCriteria: function(param) {
      Object.assign(this.searchCriteria, param);
      getProducts(this.searchCriteria).then((response) => {
        this.products = response.data;
        this.formattedProducts = this.formatResponseIntoTable(this.products);
      })
      .catch((error) => {
        this.formattedProducts = [];
        console.log(error);
      });
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
    newProduct: function(){
      this.$router.push("products/new_product")
    }
  },
  mounted : function() {
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
