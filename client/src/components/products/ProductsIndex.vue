<template id="">
  <div class="">
    holis somos los productos
    <div v-for="product in products" v-bind:key="product.id">
      {{ product.name }}
    </div>
    <b-table :data="products" columns="name"></b-table>

  </div>
</template>

<script>
    import API from './../../helpers/api.js';
    import * as session from "./../../helpers/session.js";

    export default {
      name: 'products-index',
      data: () => {
        return {
          products: []
        }
      },
      mounted: function() {
          // This must be done on login
          session.set({
              token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImlkIjo1fQ.GR8v-RyugBdtq21_XliVpG6DJypCkFxr1zI7YcwIntE"
          });

          new API()
            .get('/products')
            .then((response) => {
                this.products = response.data;
            })
            .catch((error) => {
                console.log(error);
            });
      }
    }
</script>
