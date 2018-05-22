import Vue from 'vue';
import Router from 'vue-router';
import HelloWorld from '@/components/dashboard/HelloWorld.vue';
import ProductsIndex from '@/components/products/ProductsIndex.vue';
import Login from '@/components/login/Login.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    }, {
        path: "/login",
        name: "Login",
        component: Login
    }, {
      path: '/products',
      name: 'ProductsIndex',
      component: ProductsIndex
    }
  ]
});
