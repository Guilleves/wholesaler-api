<template>
  <form @submit="submitForm()" >
    <section class="section">
      <div class="container">
        <input type="hidden" name="product-id" :value="this.productId">
        <div class="columns">
          <div class="column is-three-quarters">
            <b-field horizontal label="Name" message="Please provide a clear and descriptive name">
                <b-input v-model="name" name="name" expanded></b-input>
            </b-field>
          </div>
          <div class="column is-three-quarters">
            <b-field horizontal label="GTIN">
                <b-input v-model="gtin" name="gtin" expanded></b-input>
            </b-field>
          </div>
        </div>
        <div class="columns">
          <div class="column is-half">
            <b-field horizontal label="Brand">
                <ws-option-filter v-model="brandId" filter="brands" :format="formatBrands"></ws-option-filter>
            </b-field>
          </div>
          <div class="column is-half">
            <b-field horizontal label="Category">
                <ws-option-filter v-model="categoryId" filter="categories"></ws-option-filter>
            </b-field>
          </div>
        </div>
        <div class="columns">
          <div class="column is-10">
            <b-field horizontal label="Description">
                <b-input v-model="description" type="textarea"></b-input>
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

export default {
  name: 'new-product',
  props: {
    productId: Number
  },
  components: {
    WsOptionFilter
  },
  data: function(){
    return {
      name: null,
      gtin: null,
      brandId: null,
      categoryId: null,
      description: null
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
      if (this.productId) {
        new API()
        .put('/' + this.productId, )
        .then((response) => {
          self.options = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
      } else {
        debugger

        new API()
        .post('/')
        .then((response) => {
          self.options = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
      }
    }
  }
}
</script>
