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
                        Proposals
                    </h1>
                    <h2 class="subtitle">
                        View, create or delete proposals
                    </h2>
                </div>
            </div>
        </section>
        <section class="section">
            <div class="container">
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
                :format="format" />
            </div>
        </section>
    </div>
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
                beginDate: proposal.beginDate,
                endDate: proposal.endDate
            }
        },
        getProposals(searchCriteria) {
            return new API().get('/proposals', searchCriteria);
        },
        buildSearchCriteria(param) {
            this.searchCriteria = Object.assign({}, this.searchCriteria, param);
        }
    }
}
</script>
