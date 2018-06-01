<template id="">
    <div>
        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column">
                        <b-field grouped position="is-right">
                            <p class="control">
                                <router-link class="button is-rounded is-primary" to="/brands/new">
                                    <span class="icon">
                                        <i class="fas fa-plus"></i>
                                    </span>
                                    <span>New brand</span>
                                </router-link>
                            </p>
                        </b-field>                        <ws-table
                        :columns="columns"
                        :fetch="getBrands"
                        :filters="searchCriteria"
                        :format="format"
                        @select="onSelect"/>
                    </div>
                    <div class="column">
                        <p>Acá deberíamos rellenar con algo... </p>
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
