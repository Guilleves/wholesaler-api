<template>
    <div style="height: 100%">
        <b-field label="Search product" >
            <multiselect
            v-model="selectedProduct"
            label="name"
            track-by="id"
            placeholder="Type to search"
            open-direction="bottom"
            :options="products"
            :searchable="true"
            :loading="isLoading"
            :internal-search="false"
            :clear-on-select="true"
            :close-on-select="true"
            :options-limit="300"
            :limit="3"
            :max-height="600"
            :show-no-results="false"
            :hide-selected="true"
            @search-change="getProducts"/>
        </b-field>
        <div v-bind:class="{ 'disabled': !value || value.length === 0 }" class="box scrollable">
            <p v-if="!value || value.length === 0">
                You haven't selected any product yet.
            </p>

            <div class="columns box" v-bind:key="product.id" v-for="(product, index) in value" style="padding:0" >
                <div class="column is-three-fifths">
                    {{ "[" + product.id + "] " + product.name + " - " + product.brand.name }}
                </div>
                <div class="column is-one-quarter" style="text-align: right;">
                    <b-field>
                        <b-input icon-pack="fas" v-model="value[index].price"
                        icon="dollar-sign">
                    </b-input>
                </b-field>
            </div>
            <div class="column" style="text-align: right;">
                <button class="button is-danger" type="button" @click="removeProduct(index)"><b-icon icon="fas fa-times" /></button>
            </div>
        </div>
    </div>
</div>
</template>

<script>
export default {
    name: "ws-products-selector",
    data() {
        return {
            selectedProduct: {},
            products: [],
            isLoading: false
        };
    },
    props: ['value', 'search'],
    watch: {
        selectedProduct(val) {
            if (val)
            this.value.push(val);

            this.selectedProduct = null;
        }
    },
    methods: {
        getProducts(query) {
            this.isLoading = true;
            this.search({ "keyword": query }).then(response => {
                this.products = response.data.items.filter(p => {
                    let index = this.value.findIndex(sp => {
                        return sp.id === p.id
                    });

                    return index === -1;
                });

                this.isLoading = false;
            });
        },
        clearAll() {
            this.value = [];
        },
        removeProduct(index) {
            this.value.splice(index, 1);
        },
        show() {
            console.log(this.value);
        }
    }
}
</script>

<style lang="css">
div .scrollable {
    overflow-y: scroll;
    min-height: 75%;
    max-height: 0;
}

div .scrollable.disabled {
    background-color: #F8F8F8;
    display: flex;
    text-align: center;
    flex-direction: column;
    justify-content: center;
}
</style>
