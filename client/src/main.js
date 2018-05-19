import Vue from 'vue';
import App from './App.vue';
import Buefy from 'buefy';
import VueRouter from 'vue-router';
import 'buefy/lib/buefy.css';

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(Buefy);

new Vue({
  render: h => h(App)
}).$mount('#app');
