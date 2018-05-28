<template>
    <section class="section">
        <div class="container">
            <form @submit.prevent="save()">
                <div class="columns">
                    <div class="column">
                        <b-field label="Title">
                            <b-input
                            placeholder="Type a title"
                            v-model="title"/>
                        </b-field>

                        <b-field grouped>
                            <b-field label="Begin Date" expanded>
                                <b-datepicker
                                :maxDate="endDate"
                                placeholder="Click to select..."
                                icon="fam fa-calendar"
                                v-model="beginDate" />
                            </b-field>

                            <b-field label="End Date" expanded>
                                <b-datepicker
                                :minDate="beginDate"
                                placeholder="Click to select..."
                                icon="fam fa-calendar"
                                v-model="endDate" />
                            </b-field>
                        </b-field>

                        <b-field label="Description">
                            <b-input
                            placeholder="Type a desription"
                            v-model="description" type="textarea"/>
                        </b-field>
                    </div>
                    <div class="column">
                        <products-selector :search="getProducts" v-model="selectedProducts"/>
                    </div>
                </div>
                <b-field grouped position="is-right">
                    <p class="control">
                        <button class="button" type="button" @click="goBack()">
                            Cancel
                        </button>
                        <button class="button is-primary" :disabled="editing">
                            Save
                        </button>
                    </p>
                </b-field>
            </form>
        </div>
    </section>
</template>

<script>
import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";
import ProductsSelector from "@/components/ws-framework/WsProductsSelector.vue";
import API from "@/helpers/api.js";

export default {
    data: () => {
        return {
            title: null,
            beginDate: null,
            endDate: null,
            description: null,
            selectedProducts: [],
            editing: false
        }
    },
    components: {
        WsKeywordSearch,
        ProductsSelector
    },
    methods: {
        getProposal(id) {
            return new API().get("/proposals/" + id, null);
        },
        getProducts(query) {
            return new API().get("/products", query);
        },
        save() {
            if (this.editing)
                return;

            let data = {
                title: this.title,
                beginDate: this.beginDate ? this.beginDate.getTime() : null,
                endDate: this.endDate ? this.endDate.getTime() : null,
                description: this.description,
                lines: this.selectedProducts.map(product => {
                    return {
                        productId: product.id,
                        price: product.price
                    }
                })
            };
            new API().post("/proposals", data).then(() => {
                this.goBack();
            })
            .catch(error => {
                console.log(error);
            });
        },
        goBack() {
            this.$router.go(-1);
        }
    },
    mounted() {
        let id = this.$route.params.id;

        if (!id || Number.isNaN(Number(id)))
            return;

        this.editing = true;

        this.getProposal(id).then(response => {
            let proposal = response.data;

            this.title = proposal.title;
            this.beginDate = new Date(proposal.beginDate);
            this.endDate = new Date(proposal.endDate);
            this.description = proposal.description;
            this.selectedProducts = proposal.proposalLines.map(line => {
                return Object.assign(line.product, { "price": line.price });
            });
        })
        .catch(error => {
            console.log(error);
        });
    }
}
</script>
