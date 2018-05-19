<template id="">
  <div class="">
    holis somos los productos
    <div v-for="product in products">
      {{ product.name }}
    </div>
  </div>
</template>

<script>
import Axios from 'axios';

export default {
  name: 'products-index',
  data: () => {
    return {
      products: []
    }
  },
  mounted: function() {
    // var self = this;
    var config = {
      headers: {
        "Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImlkIjo1fQ.GR8v-RyugBdtq21_XliVpG6DJypCkFxr1zI7YcwIntE",
        "Content-Type": "application/json"
      }
    }
    Axios.get('/products', {}, config)
    .then(function(response) {
      this.products = response.data;
      console.log(response);
    }.bind(this))
    .catch(function(error) {
      console.log(error);
    })
  }
}
</script>
