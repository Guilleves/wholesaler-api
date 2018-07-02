<template>
    <section class="section" style="padding-top:75px">
        <div class="container">
            <div class="columns">
                <div class="column is-one-fifth">
                    <aside class="menu">
                        <p class="menu-label">
                            General
                        </p>
                        <ul class="menu-list">
                            <li><router-link to="/home">Dashboard</router-link></li>
                        </ul>
                        <p class="menu-label">
                            Administration
                        </p>
                        <ul class="menu-list">
                            <li><router-link to="/proposals">Proposals</router-link></li>
                            <li><router-link to="/orders">Orders</router-link></li>
                            <li><router-link to="/products">Products</router-link></li>
                            <li><router-link to="/users">Users</router-link></li>
                        </ul>
                        <p class="menu-label">
                            Transactions
                        </p>
                        <ul class="menu-list">
                            <li><router-link to="/proposals/new">New proposal</router-link></li>
                            <li><router-link to="/products/new">New product</router-link></li>
                        </ul>
                    </aside>
                </div>
                <div class="column">
                    <div class="tile is-ancestor is-vertical">
                        <div class="tile is-parent">
                            <div class="tile is-child box">
                                <div class="columns">
                                    <div class="column is-three-fifths">
                                        <p class="title">1254</p>
                                    </div>
                                    <div class="column">
                                        <b-icon type="is-light" icon="fa fa-receipt fa-3x" />
                                    </div>
                                </div>
                                <router-link to="/proposals">Proposals created</router-link>
                            </div>
                            <div class="tile is-child box">
                                <div class="columns">
                                    <div class="column is-three-fifths">
                                        <p class="title">3241</p>
                                    </div>
                                    <div class="column">
                                        <b-icon type="is-light" icon="fa fa-shopping-cart fa-3x" />
                                    </div>
                                </div>
                                <p>Orders done</p>
                            </div>
                            <div class="tile is-child box">
                                <div class="columns">
                                    <div class="column is-three-fifths">
                                        <p class="title">3142</p>
                                    </div>
                                    <div class="column">
                                        <b-icon type="is-light" icon="fa fa-box fa-3x" />
                                    </div>
                                </div>
                                <router-link to="/products">Products created</router-link>
                            </div>
                            <div class="tile is-child box">
                                <div class="columns">
                                    <div class="column is-three-fifths">
                                        <p class="title">324</p>
                                    </div>
                                    <div class="column">
                                        <b-icon type="is-light" icon="fa fa-users fa-3x" />
                                    </div>
                                </div>
                                <router-link to="/users">Users registered</router-link>
                            </div>
                        </div>
                        <div class="tile is-parent">
                            <div class="tile is-child">
                                <ws-chart :data="chartData" type="ColumnChart" :options="options" />
                                <p><router-link to="/products/ranking">More...</router-link></p>
                            </div>
                            <div class="tile is-child">
                                <ws-chart :data="chartData" type="PieChart" :options="options" />
                                <p><router-link to="/products/ranking">More...</router-link></p>
                            </div>
                        </div>
                        <div class="tile is-parent">
                            <div class="tile is-child">
                                <ws-chart :data="chartData" type="BarChart" :options="options" />
                                <p><router-link to="/products/ranking">More...</router-link></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </section>
</template>

<script>
import WsChart from "@/components/ws-framework/WsChart.vue";
import API from './../../helpers/api.js';

export default {
    name: 'HelloWorld',
    data: () => {
        return {
            options: {
                title: "Amount of products in proposals",
                pieSliceTextStyle: {
                    color: 'white',
                },
                pieHole: 0.25,
                legend: {position: 'left', textStyle: {color: 'blue', fontSize: 10}}
            },
            chartData: null
        };
    },
    components: {
        WsChart
    },
    methods: {
        fillChart() {
            let data = [["Product name", "Amount in proposal"]];
            let self = this;

            new API().get("/rankings/products", { amount: 10, orderBy: "proposal" }).then(response => {
                response.data.forEach(product => {
                    data.push([product.name, product.count]);
                });

                self.chartData = data;
            });
        }
    },
    mounted() {
        this.fillChart();
    }
}
</script>
