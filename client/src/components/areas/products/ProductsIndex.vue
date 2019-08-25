<template id="">
  <div>
    <section class="section">
      <div class="container">
        <div class="columns">
          <div class="column is-two-fifths">
            <ws-keyword-search placeholder="Search products" @input="buildSearchCriteria($event)" />
          </div>
          <div class="column">
            <ws-option-filter
            placeholder="Select a brand"
            :getOptions="getBrands"
            :format="formatBrands"
            v-model="selectedBrand" />
          </div>
          <div class="column">
            <ws-option-filter
            :getOptions="getCategories"
            placeholder="Select a category"
            v-model="selectedCategory" />
          </div>
          <div class="column">
            <b-field grouped position="is-right">
              <p class="control">
                <router-link class="button is-rounded is-primary" to="/products/new/">
                  <span class="icon">
                    <i class="fas fa-plus"></i>
                  </span>
                  <span>New product</span>
                </router-link>
              </p>
            </b-field>
          </div>
        </div>
        <ws-table
        :columns="columns"
        :fetch="getProducts"
        :filters="searchCriteria"
        @select="onSelect"
        :format="format" />
        <router-link class="button is-dark" to="/">
          <span class="icon">
            <i class="fas fa-arrow-left"></i>
          </span>
          <span>Go back</span>
        </router-link>
      </div>
    </section>
  </div>
</template>

<script>
import API from '@/helpers/api.js';
import WsOptionFilter from "@/components/ws-framework/WsOptionFilter.vue";
import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearchByJuan.vue";
import WsTable from "@/components/ws-framework/WsTable.vue";
import WsHero from "@/components/ws-framework/WsHero.vue";

export default {
  name: 'products-index',
  data: () => {
    return {
      selectedBrand: null,
      selectedCategory: null,
      searchCriteria: {},
      columns: [{
        field: 'id',
        label: 'ID',
        width: '40',
        numeric: true
      },
      { field: 'name', label: 'Name' },
      { field: 'gtin', label: 'GTIN' },
      { field: 'brand', label: 'Brand' },
      { field: 'category', label: 'Category' }]
    };
  },
  components: {
    WsOptionFilter,
    WsKeywordSearch,
    WsTable,
    WsHero
  },
  watch: {
    selectedBrand(val) {
      this.buildSearchCriteria({ brandId: val ? val.id : null })
    },
    selectedCategory(val) {
      this.buildSearchCriteria({ categoryId: val ? val.id : null })
    }
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
    getBrands() {
      return new API().get("/brands");
    },
    getCategories() {
      return new API().get("/categories");
    },
    onSelect(product) {
      this.$router.push("/products/" + product.id);
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
}
</script>
