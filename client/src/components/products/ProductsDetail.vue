<template>
  <section class="section">
    <div class="container">
      <b-loading :is-full-page="false" :active.sync="loading" :canCancel="false" />
      <form @submit.prevent="save()">
        <ws-error v-model="errors" />
        <div class="columns">
          <div class="column">
            <b-field label="Name">
              <b-input
              placeholder="Type a product name"
              v-model="product.name"/>
            </b-field>

            <b-field label="Description">
              <b-input
              placeholder="Type a product description"
              type="textarea"
              v-model="product.description"/>
            </b-field>
          </div>
          <div class="column">
            <b-field label="GTIN">
              <b-input
              placeholder="Type the GTIN"
              v-model="product.gtin"/>
            </b-field>
            <b-field label="Brand">
              <ws-option-filter
              option-type="brandId"
              placeholder="Select a brand"
              v-model="product.brand"
              :getOptions="getBrands"
              :format="formatBrands"/>
            </b-field>
            <b-field label="Category">
              <ws-option-filter
              option-type="categoryId"
              v-model="product.category"
              :getOptions="getCategories"
              placeholder="Select a category" />
            </b-field>
          </div>
        </div>
        <b-field grouped>
          <b-field v-if="!editing">
            <b-tag class="is-success" rounded>new</b-tag>
          </b-field>
          <b-field grouped position="is-right" expanded>
            <p class="control">
              <a class="button is-danger is-outlined" @click="remove" :disabled="!editing">
                <span class="icon">
                  <i class="fas fa-trash"></i>
                </span>
              </a>
              <button class="button is-outlined is-dark" type="button" @click="goBack()">
                Cancel
              </button>
              <button class="button is-success is-outlined">
                Save
              </button>
            </p>
          </b-field>
        </b-field>
      </form>
    </div>
  </section>
</template>

<script>
import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";
import WsError from "@/components/ws-framework/WsError.vue";
import WsOptionFilter from "@/components/ws-framework/WsOptionFilter.vue";
import API from "@/helpers/api.js";
import * as Notifier from "@/helpers/notifier.js";

export default {
  data() {
    return {
      product: {
        id: null,
        name: null,
        gtin: null,
        description: null,
        brand: null,
        category: null
      },
      editing: false,
      errors: [],
      loading: false
    }
  },
  components: {
    WsKeywordSearch,
    WsOptionFilter,
    WsError
  },
  methods: {
    getBrands() {
        return new API().get("/brands");
    },
    getCategories() {
        return new API().get("/categories");
    },
    formatBrands(data) {
        return data.items.map(brand => {
            return {
                id: brand.id,
                name: brand.name
            }
        });
    },
    save() {
      let data = {
        id: this.product.id,
        name: this.product.name,
        description: this.product.description,
        gtin: this.product.gtin,
        brandId: this.product.brand ? this.product.brand.id : null,
        categoryId: this.product.category ? this.product.category.id : null
      };

      let vm = this;

      if (this.editing) {
        new API().put(`/products/${vm.product.id}`, data).then(() => {
          Notifier.success("Product was created.");
          vm.errors = [];
          vm.loading = false;
        })
        .catch(error => {
          vm.loading = false;
          vm.errors = error.response.data;
        });
      } else {
        new API().post("/products", data).then(() => {
          Notifier.success("Product was created.");
          vm.errors = [];
          vm.loading = false;
        })
        .catch(error => {
          vm.loading = false;
          vm.errors = error.response.data;
        });
      }
    },
    goBack() {
      this.$router.push('/products');
    },
    remove() {
      this.$dialog.confirm({
        title: 'Deleting product',
        message: 'Are you sure you want to <b>delete</b> this product?',
        confirmText: 'Delete Product',
        type: 'is-danger',
        hasIcon: true,
        onConfirm: () => {
          this.loading = true;

          new API().delete("/products/" + this.product.id).then(() => {
            Notifier.success("Product was deleted.");
            this.errors = [];
            this.loading = false;
            this.goBack();
          })
          .catch(error => {
            this.loading = false;
            this.errors = error.response.data;
          });
        }
      });
    }
  },
  mounted() {
    let productId = this.$route.params.id;

    if (!productId || Number.isNaN(Number(productId)))
      return;

    this.editing = true;
    let vm = this;

    // Product search.
    new API().get("/products/" + productId).then(response => {
      let product = null;

      if (response && response.data)
        product = response.data;

      vm.product.id = product.id;
      vm.product.name = product.name;
      vm.product.description = product.description;
      vm.product.gtin = product.gtin;
      vm.product.brand = product.brand;
      vm.product.category = product.category;
    });
  }
}
</script>
