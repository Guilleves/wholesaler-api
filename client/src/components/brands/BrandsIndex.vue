<template id="">
    <div>
        <ws-hero title="Brands" description="View, create or delete brands" />
        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-three-fifths">
                        <ws-table
                        :columns="columns"
                        :fetch="getBrands"
                        :filters="searchCriteria"
                        :format="format"
                        @select="onSelect"/>
                    </div>
                    <div class="column">
                        <router-view />
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<script>
import API from '@/helpers/api.js';
import OptionFilter from "@/components/ws-framework/WsOptionFilter.vue";
import KeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";
import WsTable from "@/components/ws-framework/WsTable.vue";
import WsHero from "@/components/ws-framework/WsHero.vue";

export default {
    name: 'brands-index',
    data: () => {
        return {
            showDeleted: false,
            searchCriteria: {},
            columns: [{
                field: 'id',
                label: 'ID',
                width: '40',
                numeric: true
            },
            { field: 'name', label: 'Name' }]
        };
    },
    components: {
        OptionFilter,
        KeywordSearch,
        WsTable,
        WsHero
    },
    methods: {
        format(brand) {
            return {
                id: brand.id,
                name: brand.name
            }
        },
        getBrands(searchCriteria) {
            return new API().get('/brands', searchCriteria);
        },
        buildSearchCriteria(param) {
            this.searchCriteria = Object.assign({}, this.searchCriteria, param);
        },
        onSelect(brand) {
            this.$router.push("/brands/" + brand.id);
        }
    }
}
</script>
