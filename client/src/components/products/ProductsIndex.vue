<template id="">
    <div>
        <ws-hero title="Products" description="View, create, edit or delete products"/>
        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-two-fifths">
                        <ws-keyword-search placeholder="Search products" @input="buildSearchCriteria($event)" />
                    </div>
                    <div class="column">
                        <ws-option-filter
                        option-type="brandId"
                        placeholder="Select a brand"
                        :getOptions="getBrands"
                        :format="formatBrands"
                        @selected="buildSearchCriteria($event)" />
                    </div>
                    <div class="column">
                        <ws-option-filter
                        option-type="categoryId"
                        :getOptions="getCategories"
                        placeholder="Select a category"
                        @selected="buildSearchCriteria($event)" />
                    </div>
                </div>
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
import WsOptionFilter from "@/components/ws-framework/WsOptionFilterByJuan.vue";
import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearchByJuan.vue";
import WsTable from "@/components/ws-framework/WsTable.vue";
import WsHero from "@/components/ws-framework/WsHero.vue";

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
