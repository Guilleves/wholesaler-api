<template id="">
    <section class="section">
        <div class="container">

            <div class="columns">
                <div class="column is-three-fifths">
                    <div class="columns">
                        <div class="column">
                            <option-filter
                            option-type="status"
                            :static-options="[{'id': 'scheduled', 'name': 'Schduled'},
                            {'id': 'active', 'name': 'Active'},
                            {'id': 'finished', 'name': 'Finished'}]"
                            placeholder="Select a status"
                            @selected="buildSearchCriteria($event)" />
                        </div>
                        <div class="column">
                            <option-filter
                            option-type="supplierId"
                            :getOptions="getSuppliers"
                            placeholder="Select a supplier"
                            @selected="buildSearchCriteria($event)" />
                        </div>
                        <div class="column">
                            <b-switch @input="toggleShowMine($event)">
                                Show only from my supplier
                            </b-switch>
                        </div>
                    </div>
                </div>
                <div class="column">
                    <b-field grouped position="is-right">
                        <p class="control">
                            <router-link class="button is-rounded is-primary" to="/proposals/new/">
                                <span class="icon">
                                    <i class="fas fa-plus"></i>
                                </span>
                                <span>New proposal</span>
                            </router-link>
                        </p>
                    </b-field>
                </div>
            </div>
            <ws-table
            :columns="columns"
            :fetch="getProposals"
            :filters="searchCriteria"
            :format="format"
            @select="onSelect"/>
        </div>
    </section>
</template>

<script>
import API from '@/helpers/api.js';
import * as Session from '@/helpers/session.js';
import OptionFilter from "@/components/ws-framework/WsOptionFilterByJuan.vue";
import KeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";
import WsTable from "@/components/ws-framework/WsTable.vue";

export default {
    name: 'proposals-index',
    data() {
        return {
            currentSupplier: Session.get().organization.id,
            searchCriteria: {},
            columns: [{
                field: 'id',
                label: 'ID',
                width: '40',
                numeric: true
            },
            { field: 'title', label: 'Title' },
            { field: 'description', label: 'Description' },
            { field: 'beginDate', label: 'Begin Date' },
            { field: 'endDate', label: 'End Date'},
            { field: 'status', label: 'Status' }]
        };
    },
    components: {
        OptionFilter,
        KeywordSearch,
        WsTable
    },
    methods: {
        format(proposal) {
            return {
                id: proposal.id,
                title: proposal.title,
                description: proposal.description,
                beginDate: new Date(proposal.beginDate).toLocaleDateString(),
                endDate: new Date(proposal.endDate).toLocaleDateString(),
                status: proposal.status
            }
        },
        getProposals(searchCriteria) {
            return new API().get('/proposals', searchCriteria);
        },
        buildSearchCriteria(param) {
            this.searchCriteria = Object.assign({}, this.searchCriteria, param);
        },
        onSelect(proposal) {
            this.$router.push("/proposals/" + proposal.id);
        },
        getSuppliers() {
            return new API().get("/organizations/suppliers");
        },
        toggleShowMine(show) {
            if (show)
                this.buildSearchCriteria({ supplierId: this.currentSupplier });
            else
                this.buildSearchCriteria({ supplierId: null });
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
