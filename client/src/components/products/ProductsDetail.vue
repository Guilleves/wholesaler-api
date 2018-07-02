<template>
  <form @submit="submitForm()" >
    <section class="section">
      <div class="container">
        <input type="hidden" name="product-id" :value="this.productId">
        <div class="columns">
          <div class="column ">
            <b-field horizontal label="Name" message="Please provide a clear and descriptive name">
                <b-input v-model="productData.name" name="name" expanded></b-input>
            </b-field>
          </div>
          <div class="column">
            <b-field horizontal label="GTIN">
                <b-input v-model="productData.gtin" name="gtin" expanded></b-input>
            </b-field>
          </div>
        </div>
        <div class="columns">
          <div class="column ">
            <b-field horizontal label="Brand">
                <ws-option-filter :placeholder="this.productData.brand.name"  v-model="productData.brand.id" :getOptions="getBrands" :format="formatBrands"></ws-option-filter>
            </b-field>
          </div>
          <div class="column ">
            <b-field horizontal label="Category">
                <ws-option-filter :placeholder="this.productData.category.name" v-model="productData.category.id" :getOptions="getCategories" filter="categories"></ws-option-filter>
            </b-field>
          </div>
        </div>
        <div class="columns">
          <div class="column is-full ">
            <b-field horizontal label="Description">
                <b-input v-model="productData.description" type="textarea"></b-input>
            </b-field>

            <b-field horizontal><!-- Label left empty for spacing -->
                <p class="control">
                    <button type="submit" class="button is-primary">
                      Save
                    </button>
                </p>
            </b-field>
          </div>
        </div>
      </div>
    </section>
  </form>
</template>

<script>
import WsOptionFilter from "@/components/ws-framework/WsOptionFilter.vue";
import API from "@/helpers/api.js";
import * as Notifier from "@/helpers/notifier.js";

export default {
  name: 'new-product',
  components: {
    WsOptionFilter
  },
  data: function(){
    return {
      productData: {
        name: null,
        gtin: null,
        brand: {},
        category: {},
        description: null
      },
      productId: null,
      loading: false,
      errors: [],
      edit: false
    }
  },
  mounted: function() {
    if (this.$route.params.id){
      this.productId = this.$route.params.id;
      this.edit = true;
      let self = this;
      new API().get('/products/' + self.productId).then((response) => {
        self.productData = response.data;
      }).catch((error) => {
        self.errors = ["Product not found"];
      })
    }
  },
  methods: {
    getBrands: function(){
      return new API().get('/brands')
    },
    getCategories: function(){
      return new API().get('/categories')
    },
    formatBrands(data) {
      return data.items.map(brand => {
        return {
          id: brand.id,
          name: brand.name
        }
      });
    },
    submitForm: function(){
      if (this.edit) {
        new API().put('/products/' + this.productId, this.productData ).then((response) => {
          Notifier.success("Product successfully updated.");
          this.errors = [];
          this.loading = false;
          this.$router.push("/products/");
        }).catch((error) => {
          this.errors = error.response.data;
          this.loading = false;
        });
      } else {
        new API().post('/products', this.productData).then((response) => {
          Notifier.success("Product successfully created.");
          this.errors = [];
          this.loading = false;
          this.$router.push("/products/");
        }).catch((error) => {
          console.log(error);
        });
      }
    }
  }
}
</script>
