import Vue from 'vue';
import Router from 'vue-router';
import Main from '@/components/layouts/Main.vue';
import ProductsLayout from '@/components/products/ProductsLayout.vue';
import ProductsIndex from '@/components/products/ProductsIndex.vue';
import ProductsDetail from '@/components/products/ProductsDetail.vue';
import ProductsRanking from '@/components/products/ProductsRanking.vue';
import ProposalsLayout from '@/components/proposals/ProposalsLayout.vue';
import ProposalsIndex from '@/components/proposals/ProposalsIndex.vue';
import ProposalsDetail from '@/components/proposals/ProposalsDetail.vue';
import OrdersLayout from '@/components/orders/OrdersLayout.vue';
import OrdersIndex from '@/components/orders/OrdersIndex.vue';
import OrdersDetail from '@/components/orders/OrdersDetail.vue';
import BrandsLayout from '@/components/brands/BrandsLayout.vue';
import BrandsIndex from '@/components/brands/BrandsIndex.vue';
import BrandsDetail from '@/components/brands/BrandsDetail.vue';
import Login from '@/components/users/Login.vue';
import Signup from '@/components/users/Signup.vue';
import Dashboard from '@/components/dashboard/Dashboard.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        { path: '/', component: Main, children: [
            {path: "home", component: Dashboard },
            { path: 'products', component: ProductsIndex },
            { path: 'products/ranking', component: ProductsRanking },
            { path: 'products/new_product', component: ProductsDetail },
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
            { path: 'brands', component: BrandsLayout, children: [
                { path: "", component: BrandsIndex },
                { path: ":id", component: BrandsDetail }
            ]}
        ]},
        { path: "/login", component: Login },
        { path: "/signup", component: Signup }
    ]
});
