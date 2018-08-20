import Vue from 'vue';
import Router from 'vue-router';
import Main from '@/components/layouts/Main.vue';
import ProductsIndex from '@/components/products/ProductsIndex.vue';
import ProductsRanking from '@/components/products/ProductsRanking.vue';
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

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        { path: '/', component: Main, children: [
            {path: "", component: Dashboard },
            { path: 'products', component: ProductsIndex },
            { path: 'products/ranking', component: ProductsRanking },
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
                { path: ":id(\\d+)", component: UsersDetail }
            ]}
        ]},
        { path: "/login", component: Login },
        { path: "/signup", component: Signup },
        { path: "*", component: NotFound }
    ]
});
