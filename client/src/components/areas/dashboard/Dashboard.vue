<template>
    <section class="section" style="padding-top:100px">
        <div class="container">
            <div class="columns">
                <div class="column is-one-fifth">
                    <Menu />
                </div>
                <div class="column">
                    <div class="tile is-ancestor is-vertical">
                        <div class="tile is-parent">
                            <div class="tile is-child box">
                                <div class="columns">
                                    <div class="column is-three-fifths">
                                        <p class="is-size-2" v-if="counts.proposal.loading"><i class="fa fa-spinner fa-spin" /></p>
                                        <p class="is-size-2" v-else>{{ counts.proposal.value }}</p>
                                    </div>
                                    <div class="column">
                                        <br />
                                        <b-icon type="is-dark" icon="fa fa-receipt fa-3x" />
                                    </div>
                                </div>
                                <router-link to="/proposals">Proposals created</router-link>
                            </div>
                            <div class="tile is-child box">
                                <div class="columns">
                                    <div class="column is-three-fifths">
                                        <p class="is-size-2" v-if="counts.order.loading"><i class="fa fa-spinner fa-spin" /></p>
                                        <p class="is-size-2" v-else>{{ counts.order.value }}</p>
                                    </div>
                                    <div class="column">
                                        <br />
                                        <b-icon type="is-dark" icon="fa fa-shopping-cart fa-3x" />
                                    </div>
                                </div>
                                <router-link to="/orders">Orders placed</router-link>
                            </div>
                            <div class="tile is-child box">
                                <div class="columns">
                                    <div class="column is-three-fifths">
                                        <p class="is-size-2" v-if="counts.product.loading"><i class="fa fa-spinner fa-spin" /></p>
                                        <p class="is-size-2" v-else>{{ counts.product.value }}</p>
                                    </div>
                                    <div class="column">
                                        <br />
                                        <b-icon type="is-dark" icon="fa fa-box fa-3x" />
                                    </div>
                                </div>
                                <router-link to="/products">Products</router-link>
                            </div>
                            <div class="tile is-child box">
                                <div class="columns">
                                    <div class="column is-three-fifths">
                                        <p class="is-size-2" v-if="counts.user.loading"><i class="fa fa-spinner fa-spin" /></p>
                                        <p class="is-size-2" v-else>{{ counts.user.value }}</p>
                                    </div>
                                    <div class="column">
                                        <br />
                                        <b-icon type="is-dark" icon="fa fa-users fa-3x" />
                                    </div>
                                </div>
                                <router-link to="/users">Users in organization</router-link>
                            </div>
                        </div>
                        <div class="tile is-parent">
                            <div class="tile is-child is-6 has-text-centered">
                                <ws-bar-chart />
                                <p><router-link to="/rankings/products">More...</router-link></p>
                            </div>
                            <div class="tile is-child is-6 has-text-centered">
                                <ws-bar-chart />
                                <p><router-link to="/rankings/organizations">More...</router-link></p>
                            </div>
                        </div>
                        <div class="tile is-parent">
                            <div class="tile is-child is-3 has-text-centered">
                                <!-- AWEFUL SOLUTION :) -->
                            </div>
                            <div class="tile is-child is-6 has-text-centered">
                                <ws-bar-chart />
                                <p><router-link to="/rankings/orders">More...</router-link></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import WsBarChart from "@/components/ws-framework/WsBarChart.vue";
import WsPieChart from "@/components/ws-framework/WsPieChart.vue";
import API from '@/helpers/api.js';
import * as Session from '@/helpers/session.js';
import Menu from "./Menu.vue";

export default {
    name: 'HelloWorld',
    data: () => {
        return {
            loggedRole: Session.get().organization.role,
            mostUsedProducts: {
                names: [],
                count: []
            },
            profitByRetailer: {
                data: []
            },
            ordersByMonth: {
                names: [],
                count: []
            },
            counts: {
                product: {
                    value: 0,
                    loading: true
                },
                proposal: {
                    value: 0,
                    loading: true
                },
                order: {
                    value: 0,
                    loading: true
                },
                user: {
                    value: 0,
                    loading: true
                }
            }
        };
    },
    components: {
        WsBarChart,
        WsPieChart,
        Menu
    },
    methods: {
        fillProductsChart() {
            let self = this;

            new API().get("/rankings/products", { amount: 7, orderBy: "proposal" }).then(response => {
                self.mostUsedProducts.names = response.data.map(ranking => ranking.name);
                self.mostUsedProducts.count = response.data.map(ranking => ranking.count);
            });
        },
        fillOrdrsChart() {
            let self = this;

            new API().get("/rankings/orders", null).then(response => {
                self.ordersByMonth.names = response.data.map(ranking => ranking.date);
                self.ordersByMonth.count = response.data.map(ranking => ranking.count);
            });
        },
        fillProfitChart() {
            let self = this;

            new API().get("/rankings/organizations", { amount: 7, type: "profit" }).then(response => {
                self.profitByRetailer.data = response.data.map(ranking => {
                    return { name: ranking.organization.name, value: ranking.sum}
                });
            });
        },
        countProducts() {
            let self = this;
            self.counts.product.loading = true;

            new API().get("/products/count").then(response => {
                self.counts.product.value = response.data;
                self.counts.product.loading = false;
            });
        },
        countUsers() {
            let self = this;
            self.counts.user.loading = true;

            new API().get("/users/count").then(response => {
                self.counts.user.value = response.data;
                self.counts.user.loading = false;
            });
        },
        countOrders() {
            let self = this;
            self.counts.order.loading = true;

            new API().get("/orders/count").then(response => {
                self.counts.order.value = response.data;
                self.counts.order.loading = false;
            });
        },
        countProposals() {
            let self = this;
            self.counts.proposal.loading = true;

            new API().get("/proposals/count").then(response => {
                self.counts.proposal.value = response.data;
                self.counts.proposal.loading = false;
            });
        }
    },
    mounted() {
        if (this.loggedRole === "supplier")
        this.countProposals();

        this.fillOrdrsChart();
        this.fillProductsChart();
        this.fillProfitChart();
        this.countProducts();
        this.countOrders();
        this.countUsers();
    }
}
</script>
