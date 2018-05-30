<template id="">
    <div>
        <ws-hero title="Products" description="View, create, edit or delete products"/>
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
import WsLineChart from "@/components/ws-framework/WsLineChart.vue";
import WsHero from "@/components/ws-framework/WsHero.vue";

export default {
    name: 'products-index',
    data: () => {
        return {
            searchCriteria: {},
            chartData: null,
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
        WsTable,
        WsLineChart,
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
        },
        fillChart() {
            let labels = [];
            let data = [];
            let self = this;

            new API().get("/products/ranking", { supplierId: 1 }).then(response => {
                response.data.forEach(product => {
                    labels.push(product.name);
                    data.push(product.count);
                });

                self.chartData = {
                    labels: labels,
                    datasets: [
                        {
                            label: "Amount in proposals.",
                            backgroundColor: '#f87979',
                            data: data
                        }
                    ]
                };
            });
        }
    },
    mounted() {
        this.fillChart();
    }
}
</script>
