import Vue from 'vue';
import App from './App.vue';
import Buefy from 'buefy';

// Routing
import VueRouter from 'vue-router';
import router from './router';

// Select
import Multiselect from 'vue-multiselect';

import IEcharts from 'vue-echarts-v3/src/full';

// Styles
import 'buefy/lib/buefy.css';
import "vue-multiselect/dist/vue-multiselect.min.css";

Vue.config.productionTip = false;

Vue.use(VueRouter);
Vue.use(Buefy, {
    defaultIconPack: 'fas'
});

Vue.component('multiselect', Multiselect);
Vue.component('chart', IEcharts);

new Vue({
  render: h => h(App),
  router
}).$mount('#app');
