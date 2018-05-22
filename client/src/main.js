import Vue from 'vue';
import App from './App.vue';
import Buefy from 'buefy';
import VueRouter from 'vue-router';
import 'buefy/lib/buefy.css';
import router from './router';

Vue.config.productionTip = false;
// Vue.http.options.crossOrigin = true

Vue.use(VueRouter);
Vue.use(Buefy);

new Vue({
  render: h => h(App),
  router
}).$mount('#app');
