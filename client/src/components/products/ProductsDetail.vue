<template>
  <form @submit.prevent="submitForm()" >
    <section class="section">
      <div class="container">
        <input type="hidden" name="product-id" :value="this.productId">
        <div class="columns">
          <div class="column">
            <b-field label="Name" message="Please provide a clear and descriptive name">
                <b-input v-model="productData.name" name="name" expanded></b-input>
            </b-field>
          </div>
          <div class="column">
            <b-field label="GTIN">
                <b-input v-model="productData.gtin" name="gtin" expanded></b-input>
            </b-field>
          </div>
        </div>
        <div class="columns">
          <div class="column ">
            <b-field label="Brand">
                <ws-option-filter placeholder="Select a brand" option-type="brandId" @selected="brandSelected" :getOptions="getBrands" :format="formatBrands"></ws-option-filter>
            </b-field>
          </div>
          <div class="column ">
            <b-field label="Category">
                <ws-option-filter placeholder="Select a category" v-model="productData.category" :getOptions="getCategories" filter="categories"></ws-option-filter>
            </b-field>
          </div>
        </div>

        <b-field label="Description">
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
    </section>
  </form>
</template>

<script>
import WsOptionFilter from "@/components/ws-framework/WsOptionFilterByJuan.vue";
import API from "@/helpers/api.js";
import * as Notifier from "@/helpers/notifier.js";

export default {
  name: 'new-product',
  components: {
    WsOptionFilter
  },
  watch:{
    productData(val){
      debugger
    }
  },
  data(){
    return {
      productData: {
        name: null,
        gtin: null,
        brand: null,
        category: null,
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
    brandSelected: function (event) {
      console.log(event);
    },
    submitForm() {
      debugger
      let req;
      let preparedData = {
        name: this.productData.name,
        gtin: this.productData.gtin,
        brandId: this.productData.brand.id,
        categoryId: this.productData.category.id,
        description: this.productData.description
      };
      if (this.edit) {
        req = new API().put('/products/' + this.productId, preparedData );
        let message = "Product successfully updated.";
      } else {
        req = new API().post('/products', preparedData);
        let message = "Product created successfully.";
      }
      req.then((response) => {
         Notifier.success(message);
         this.errors = [];
         this.loading = false;
         this.$router.push("/products/");
       }).catch((error) => {
         this.errors = error.response.data;
         this.loading = false;
       });
    }
  }
}
</script>
