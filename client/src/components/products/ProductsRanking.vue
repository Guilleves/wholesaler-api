<template id="">
    <div>
        <ws-hero title="Products" description="Most used in proposals"/>
        <section class="section">
            <div class="container">
                <ws-table
                :columns="columns"
                :fetch="getProducts"
                :format="format"
                :rawData="true"
                :dontPaginate="true" />
            </div>
        </section>
    </div>
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
            columns: [{ field: 'count', label: 'Amt.', numeric: true },
            { field: 'name', label: 'Name' },
            { field: 'description', label: 'Description' },
            { field: 'brand', label: 'Brand' },
            { field: 'category', label: 'Category' }]
        };
    },
    components: {
        WsTable,
        WsHero
    },
    methods: {
        format(product) {
            return {
                id: product.id,
                name: product.name,
                description: product.description,
                brand: product.brand.name,
                category: product.category.name,
                count: product.count
            }
        },
        getProducts() {
            return new API().get("/products/ranking", { supplierId: 1, amount: 10 });
        }
    }
}
</script>
