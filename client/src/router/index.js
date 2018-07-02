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
        {
            path: '/',
            name: 'Main',
            component: Main,
            children: [{
                path: "home",
                name: "Home",
                component: Dashboard
            }, {
                path: 'products',
                component: ProductsLayout,
                children: [{
                    path: "",
                    name: 'ProductsIndex',
                    component: ProductsIndex
                }, {
                    path: ":id",
                    name: 'EditProduct',
                    component: ProductsDetail
                }, {
                    path: 'products/ranking',
                    name: 'ProductsRanking',
                    component: ProductsRanking
                }]
            }, {
                path: 'proposals',
                component: ProposalsLayout,
                children: [{
                    path: "",
                    name: "ProposalsIndex",
                    component: ProposalsIndex
                },
                {
                    path: ":id",
                    name: "ProposalsDetail",
                    component: ProposalsDetail
                }]
            }, {
                path: 'brands',
                component: BrandsLayout,
                children: [{
                    path: "",
                    name: 'BrandsIndex',
                    component: BrandsIndex
                }, {
                    path: ":id",
                    name: 'EditBrand',
                    component: BrandsDetail
                }]
            }]
        }, {
            path: "/login",
            name: "Login",
            component: Login
        }, {
            path: "/signup",
            name: "Signup",
            component: Signup
        }
    ]
});
