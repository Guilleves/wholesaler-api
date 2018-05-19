import Vue from 'vue';
import Axios from 'axios';
import App from './App.vue';
import Buefy from 'buefy';
import VueRouter from 'vue-router';
import 'buefy/lib/buefy.css';
import router from './router';


Vue.config.productionTip = false;
// Vue.http.options.crossOrigin = true
// Vue.prototype.$http = Axios;
Axios.defaults.baseURL = 'http://localhost:9090';
Axios.defaults.headers.common['Authorization'] = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImlkIjo1fQ.GR8v-RyugBdtq21_XliVpG6DJypCkFxr1zI7YcwIntE";
// Axios.defaults.headers.common['Content-Type'] = 'application/json';

Vue.use(VueRouter);
Vue.use(Buefy);

new Vue({
  render: h => h(App),
  router
}).$mount('#app');
