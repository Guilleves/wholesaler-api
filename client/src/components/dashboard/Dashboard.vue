<template>
    <section class="section" style="padding-top:75px">
        <div class="container">
            <div class="tile is-ancestor">
                <div class="tile is-4 is-vertical is-parent">
                    <div class="tile is-child box">
                        <p class="title">Proposals</p>
                        <router-link to="/proposals">View, create or remove proposals</router-link>
                    </div>
                    <div class="tile is-child box">
                        <p class="title">Orders</p>
                        <p>View, create or remove orders</p>
                    </div>
                    <div class="tile is-child box">
                        <p class="title">Products</p>
                        <router-link to="/products">View, edit, create or remove products</router-link>
                    </div>
                </div>
                <div class="tile is-parent">
                    <div class="tile is-child box">
                        <p class="title">Products review</p>
                        <p><router-link to="/products/ranking">More...</router-link></p>
                        <ws-line-chart :chart-data="chartData" :height="250" />
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import WsLineChart from "@/components/ws-framework/WsLineChart.vue";
import API from './../../helpers/api.js';

export default {
    name: 'HelloWorld',
    data: () => {
        return {
            chartData: null
        };
    },
    components: {
        WsLineChart
    },
    methods: {
        fillChart() {
            let labels = [];
            let data = [];
            let self = this;

            new API().get("/products/ranking", { supplierId: 1, amount: 10 }).then(response => {
                response.data.forEach(product => {
                    labels.push(product.name);
                    data.push(product.count);
                });

                self.chartData = {
                    labels: labels,
                    datasets: [
                        {
                            label: "Amount in proposals.",
                            backgroundColor: '#714dd2',
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
