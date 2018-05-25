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
                <b-table
                :data="formattedProposals"
                :columns="columns"
                :loading="loading"
                backend-pagination
                :paginated="true"
                :per-page="pageSize"
                :total="total"
                @page-change="onPageChange"></b-table>
            </div>
        </section>
    </div>
</template>

<script>
import API from '@/helpers/api.js';
import OptionFilter from "@/components/products/OptionFilter.vue";
import KeywordSearch from "@/components/products/KeywordSearch.vue";

export default {
    name: 'proposals-index',
    data: () => {
        return {
            total: 0,
            loading: false,
            pageIndex: 1,
            pageSize: 10,
            searchCriteria: {},
            proposals: [],
            formattedProposals: [],
            columns: [
                {
                    field: 'id',
                    label: 'ID',
                    width: '40',
                    numeric: true
                },
                {
                    field: 'title',
                    label: 'Title',
                },
                {
                    field: 'description',
                    label: 'Description',
                },
                {
                    field: 'beginDate',
                    label: 'Begin Date',
                },
                {
                    field: 'endDate',
                    label: 'End Date'
                }
            ]
        }
    },
    components: {
        OptionFilter,
        KeywordSearch
    },
    watch: {
        proposals: function(val) {
            this.formattedProposals = val.map(proposal => {
                return {
                    id: proposal.id,
                    title: proposal.title,
                    description: proposal.description,
                    beginDate: proposal.beginDate,
                    endDate: proposal.endDate
                }
            });
        }
    },
    methods: {
        getProposals() {
            let self = this;

            this.loading = true;

            return new API().get('/proposals', this.searchCriteria).then((response) => {
                self.proposals = response.data.items;
                self.total = response.data.size;
                self.loading = false;
            }).catch(() => {
                self.$toast.open("Couldn't find proposals.");
                self.proposals = [];
                self.total = 0;
                self.loading = false;
            });
        },
        buildSearchCriteria(param) {
            Object.assign(this.searchCriteria, param);
            this.getProposals();
        },
        onPageChange(page) {
            this.pageIndex = page - 1;
            this.buildSearchCriteria({
                "pageSize": this.pageSize,
                "pageIndex": this.pageIndex
            });
        }
    },
    mounted : function() {
        this.getProposals();
    }
}
</script>
