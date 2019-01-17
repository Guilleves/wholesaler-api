<template>
  <section class="section" style="padding-top:75px">
    <div class="container">
      <div class="columns">
        <div class="column is-one-fifth">
          <aside class="menu" :style="{backgroundColor: 'hsl(0, 0%, 96%)', padding: '20px', borderRadius: '10px'}">
            <p class="menu-label">
              General
            </p>
            <ul class="menu-list">
              <li><router-link to="/">Dashboard</router-link></li>
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
              <div class="tile is-child box" v-if="loggedRole === 'supplier'">
                <div class="columns">
                  <div class="column is-three-fifths">
                    <p class="title" v-if="counts.proposal.loading"><i class="fa fa-spinner fa-spin" /></p>
                    <p class="title" v-else>{{ counts.proposal.value }}</p>
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
                    <p class="title" v-if="counts.order.loading"><i class="fa fa-spinner fa-spin" /></p>
                    <p class="title" v-else>{{ counts.order.value }}</p>
                  </div>
                  <div class="column">
                    <b-icon type="is-light" icon="fa fa-shopping-cart fa-3x" />
                  </div>
                </div>
                <p>Orders placed</p>
              </div>
              <div class="tile is-child box">
                <div class="columns">
                  <div class="column is-three-fifths">
                    <p class="title" v-if="counts.product.loading"><i class="fa fa-spinner fa-spin" /></p>
                    <p class="title" v-else>{{ counts.product.value }}</p>
                  </div>
                  <div class="column">
                    <b-icon type="is-light" icon="fa fa-box fa-3x" />
                  </div>
                </div>
                <router-link to="/products">Products</router-link>
              </div>
              <div class="tile is-child box">
                <div class="columns">
                  <div class="column is-three-fifths">
                    <p class="title" v-if="counts.user.loading"><i class="fa fa-spinner fa-spin" /></p>
                    <p class="title" v-else>{{ counts.user.value }}</p>
                  </div>
                  <div class="column">
                    <b-icon type="is-light" icon="fa fa-users fa-3x" />
                  </div>
                </div>
                <router-link to="/users">Users in organization</router-link>
              </div>
            </div>
            <div class="tile is-parent">
              <div class="tile is-child">
                <ws-bar-chart title="Top sellers" barname="Amount of uses" :names="mostUsedProducts.names" :amount="mostUsedProducts.count" color="#c23531" />
                <p><router-link to="/products/ranking">More...</router-link></p>
              </div>
              <div class="tile is-child">
                <ws-pie-chart title="Profits by retailer" barname="Profit ($)" :data="profitByRetailer.data" color="#2f4554"/>
                <p>More...</p>
              </div>
            </div>
            <div class="tile is-parent">
              <div class="tile is-child">
                <ws-bar-chart title="Orders by month" barname="Amount" :names="ordersByMonth.names" :amount="ordersByMonth.count" color="#4a4a4a"/>
                <p>More...</p>
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
import API from './../../helpers/api.js';
import * as Session from '@/helpers/session.js'

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
    WsPieChart
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
