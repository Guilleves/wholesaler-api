import Vue from 'vue';
import Router from 'vue-router';
import Main from '@/components/layouts/Main.vue';
import ProductsIndex from '@/components/areas/products/ProductsIndex.vue';
import ProductsLayout from '@/components/areas/products/ProductsLayout.vue';
import ProductsDetail from '@/components/areas/products/ProductsDetail.vue';
import ProposalsLayout from '@/components/areas/proposals/ProposalsLayout.vue';
import ProposalsIndex from '@/components/areas/proposals/ProposalsIndex.vue';
import ProposalsDetail from '@/components/areas/proposals/ProposalsDetail.vue';
import OrdersLayout from '@/components/areas/orders/OrdersLayout.vue';
import OrdersIndex from '@/components/areas/orders/OrdersIndex.vue';
import OrdersDetail from '@/components/areas/orders/OrdersDetail.vue';
import UsersLayout from '@/components/areas/users/UsersLayout.vue';
import UsersIndex from '@/components/areas/users/UsersIndex.vue';
import UsersDetail from '@/components/areas/users/UsersDetail.vue';
import Login from '@/components/areas/users/Login.vue';
import Signup from '@/components/areas/users/Signup.vue';
import Dashboard from '@/components/areas/dashboard/Dashboard.vue';
import NotFound from '@/components/notfound/NotFound.vue';
import Unauthorized from '@/components/unauthorized/Unauthorized.vue';
import MyAccountIndex from '@/components/areas/my-account/MyAccountIndex.vue';
import MyAccountLayout from '@/components/areas/my-account/MyAccountLayout.vue';
import RankingIndex from '@/components/areas/rankings/RankingIndex.vue';
import ProductsRanking from '@/components/areas/rankings/ProductsRanking.vue';
import OrganizationsRanking from '@/components/areas/rankings/OrganizationsRanking.vue';
import OrdersRanking from '@/components/areas/rankings/OrdersRanking.vue';

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
