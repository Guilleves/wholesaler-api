<template id="">
    <section class="section">
        <div class="container">
            <b-field grouped position="is-right">
                <p class="control">
                    <router-link class="button is-rounded is-primary" to="/proposals/new">
                        <span class="icon">
                            <i class="fas fa-plus"></i>
                        </span>
                        <span>New proposal</span>
                    </router-link>
                </p>
            </b-field>
            <div class="columns">
                <div class="column">
                    <option-filter
                    option-type="status"
                    :static-options="[{'id': 'scheduled', 'name': 'Schduled'},
                    {'id': 'active', 'name': 'Active'},
                    {'id': 'finished', 'name': 'Finished'}]"
                    filter="status"
                    placeholder="Select a status"
                    @selected="buildSearchCriteria($event)" />
                </div>
                <div class="column">
                    <option-filter
                    option-type="supplierId"
                    filter="organizations/suppliers"
                    placeholder="Select a supplier"
                    @selected="buildSearchCriteria($event)" />
                </div>
            </div>
        </div>
        <div class="container">
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
import OptionFilter from "@/components/ws-framework/WsOptionFilter.vue";
import KeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";
import WsTable from "@/components/ws-framework/WsTable.vue";

export default {
    name: 'proposals-index',
    data: () => {
        return {
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
            { field: 'endDate', label: 'End Date'}]
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
                beginDate: new Date(proposal.beginDate),
                endDate: new Date(proposal.endDate)
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
        }
    }
}
</script>
