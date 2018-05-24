import Vue from 'vue';
import Router from 'vue-router';
import HelloWorld from '@/components/dashboard/HelloWorld.vue';
import ProductsIndex from '@/components/products/ProductsIndex.vue';
import NewProduct from '@/components/products/NewProduct.vue';
import Login from '@/components/users/Login.vue';
import Signup from '@/components/users/Signup.vue';

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
        path: "/signup",
        name: "Signup",
        component: Signup
    }, {
      path: '/products',
      name: 'ProductsIndex',
      component: ProductsIndex
    }, {
      path: '/products/new_product',
      name: 'NewProduct',
      component: NewProduct
    }
  ]
});
