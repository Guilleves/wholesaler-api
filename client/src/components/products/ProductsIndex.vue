<template id="">
    <div>
        <section class="hero is-primary">
            <!-- Hero head: will stick at the top -->
            <div class="hero-head">
                <nav class="navbar">
                    <div class="container">
                        <div class="navbar-brand">
                            <a class="navbar-item">
                            </a>
                            <span class="navbar-burger burger" data-target="navbarMenuHeroA">
                                <span></span>
                                <span></span>
                                <span></span>
                            </span>
                        </div>
                    </div>
                </nav>
            </div>

            <!-- Hero content: will be in the middle -->
            <div class="hero-body">
                <div class="container has-text-centered">
                    <h1 class="title">
                        Products
                    </h1>
                    <h2 class="subtitle">
                        View, create or delete products
                    </h2>
                </div>
            </div>
        </section>
        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-two-fifths">
                        <ws-keyword-search @input="buildSearchCriteria($event)" />
                    </div>
                    <div class="column">
                        <ws-option-filter
                        option-type="brandId"
                        filter="brands"
                        placeholder="Select a brand"
                        :format="formatBrands"
                        @selected="buildSearchCriteria($event)" />
                    </div>
                    <div class="column">
                        <ws-option-filter
                        option-type="categoryId"
                        filter="categories"
                        placeholder="Select a category"
                        @selected="buildSearchCriteria($event)" />
                    </div>
                </div>
            </div>
            <div class="container">
                <ws-table
                :columns="columns"
                :fetch="getProducts"
                :filters="searchCriteria"
                :format="format" />
            </div>
        </section>
    </div>
</template>

<script>
import API from './../../helpers/api.js';
import WsOptionFilter from "@/components/ws-framework/WsOptionFilter.vue";
import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";
import WsTable from "@/components/ws-framework/WsTable.vue";

export default {
    name: 'products-index',
    data: () => {
        return {
            searchCriteria: {},
            columns: [{
                field: 'id',
                label: 'ID',
                width: '40',
                numeric: true
            },
            { field: 'name', label: 'Name' },
            { field: 'gtin', label: 'GTIN' },
            { field: 'brand', label: 'Brand', centered: true },
            { field: 'category', label: 'Category' }]
        };
    },
    components: {
        WsOptionFilter,
        WsKeywordSearch,
        WsTable
    },
    methods: {
        format(product) {
            return {
                id: product.id,
                name: product.name,
                gtin: product.gtin,
                brand: product.brand.name,
                category: product.category.name
            }
        },
        getProducts(searchCriteria) {
            return new API().get('/products', searchCriteria);
        },
        buildSearchCriteria(param) {
            this.searchCriteria = Object.assign({}, this.searchCriteria, param);
        },
        formatBrands(data) {
            return data.items.map(brand => {
                return {
                    id: brand.id,
                    name: brand.name
                }
            });
        }
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
