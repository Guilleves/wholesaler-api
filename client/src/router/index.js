import Vue from 'vue';
import Router from 'vue-router';
import Main from '@/components/layouts/Main.vue';
import ProductsIndex from '@/components/products/ProductsIndex.vue';
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
            },  {
                path: 'products',
                name: 'ProductsIndex',
                component: ProductsIndex
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
