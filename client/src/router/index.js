import Vue from 'vue';
import Router from 'vue-router';
import Main from '@/components/layouts/Main.vue';
import ProductsIndex from '@/components/products/ProductsIndex.vue';
import ProductsLayout from '@/components/products/ProductsLayout.vue';
import ProductsDetail from '@/components/products/ProductsDetail.vue';
import ProposalsLayout from '@/components/proposals/ProposalsLayout.vue';
import ProposalsIndex from '@/components/proposals/ProposalsIndex.vue';
import ProposalsDetail from '@/components/proposals/ProposalsDetail.vue';
import OrdersLayout from '@/components/orders/OrdersLayout.vue';
import OrdersIndex from '@/components/orders/OrdersIndex.vue';
import OrdersDetail from '@/components/orders/OrdersDetail.vue';
import UsersLayout from '@/components/users/UsersLayout.vue';
import UsersIndex from '@/components/users/UsersIndex.vue';
import UsersDetail from '@/components/users/UsersDetail.vue';
import Login from '@/components/users/Login.vue';
import Signup from '@/components/users/Signup.vue';
import Dashboard from '@/components/dashboard/Dashboard.vue';
import NotFound from '@/components/notfound/NotFound.vue';
import Unauthorized from '@/components/unauthorized/Unauthorized.vue';
import MyAccountIndex from '@/components/my-account/MyAccountIndex.vue';
import MyAccountLayout from '@/components/my-account/MyAccountLayout.vue';
import RankingIndex from '@/components/rankings/RankingIndex.vue';
import ProductsRanking from '@/components/rankings/ProductsRanking.vue';
import OrganizationsRanking from '@/components/rankings/OrganizationsRanking.vue';
import OrdersRanking from '@/components/rankings/OrdersRanking.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        { path: '/', component: Main, children: [
            {path: "", component: Dashboard },
            { path: 'products', component: ProductsLayout, children: [
                { path: "", component: ProductsIndex },
                { path: ":id", component: ProductsDetail }
            ]},
            { path: 'proposals', component: ProposalsLayout, children: [
                { path: "", component: ProposalsIndex },
                { path: ":id", component: ProposalsDetail },
                { path:":proposalId/orders/", component: OrdersIndex },
                { path:":proposalId/orders/:orderId", component: OrdersDetail }
            ]},
            { path: 'orders', component: OrdersLayout, children: [
                { path: "", component: OrdersIndex },
                { path: ":orderId", component: OrdersDetail }
            ]},
            { path: 'users', component: UsersLayout, children: [
                { path: "", component: UsersIndex },
                { path: ":id", component: UsersDetail }
            ]},
            { path: 'rankings', component: RankingIndex, children: [
              { path: "products", component: ProductsRanking },
              { path: "organizations", component: OrganizationsRanking },
              { path: "orders", component: OrdersRanking }
            ]},
            { path: 'my-account', component: MyAccountLayout, children: [
              { path: "", component: MyAccountIndex }
            ]},
        ]},
        { path: "/unauthorized", component: Unauthorized },
        { path: "/login", component: Login },
        { path: "/signup", component: Signup },
        { path: "*", component: NotFound }
    ]
});
