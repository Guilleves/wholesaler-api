<template>
  <form @submit="submitForm()" >
    <section class="section">
      <div class="container">
        <input type="hidden" name="product-id" :value="this.productId">
        <div class="columns">
          <div class="column is-three-quarters">
            <b-field horizontal label="Name" message="Please provide a clear and descriptive name">
                <b-input v-model="productData.name" name="name" expanded></b-input>
            </b-field>
          </div>
          <div class="column is-three-quarters">
            <b-field horizontal label="GTIN">
                <b-input v-model="productData.gtin" name="gtin" expanded></b-input>
            </b-field>
          </div>
        </div>
        <div class="columns">
          <div class="column is-half">
            <b-field horizontal label="Brand">
                <ws-option-filter v-model="productData.brandId" filter="brands" :format="formatBrands"></ws-option-filter>
            </b-field>
          </div>
          <div class="column is-half">
            <b-field horizontal label="Category">
                <ws-option-filter v-model="productData.categoryId" filter="categories"></ws-option-filter>
            </b-field>
          </div>
        </div>
        <div class="columns">
          <div class="column is-10">
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
        brandId: null,
        categoryId: null,
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
        self.productData.name = response.data.name;
        self.productData.description = response.data.description;
        self.productData.gtin = response.data.gtin;
        self.productData.brandId = response.data.brand.id;
        self.productData.categoryId = response.data.category.id;
      }).catch((error) => {
        self.errors = ["Product not found"];
      })
    }
  },
  methods: {
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
          this.$router.go(-1);
        }).catch((error) => {
          this.errors = error.response.data;
          this.loading = false;
        });
      } else {
        debugger
        new API().post('/products', this.productData).then((response) => {
          Notifier.success("Product successfully created.");
          this.errors = [];
          this.loading = false;
          this.$router.go(-1);
        }).catch((error) => {
          debugger
          console.log(error);
        });
      }
    }
  }
}
</script>
