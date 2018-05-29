<template>
    <section class="section">
        <div class="container">
            <b-loading :is-full-page="false" :active.sync="loading" :canCancel="false" />
            <form @submit.prevent="save()">
                <b-notification type="is-danger" has-icon :active.sync="showNotification">
                    <ul v-bind:key="index" v-for="(notification, index) in notifications">
                        <li>{{ notification }}</li>
                    </ul>
                </b-notification>
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
                        <a class="button is-danger is-outlined" @click="remove" :disabled="!editing">
                            <span class="icon">
                                <i class="fas fa-trash"></i>
                            </span>
                        </a>
                        <button class="button is-outlined is-dark" type="button" @click="goBack()">
                            Cancel
                        </button>
                        <button class="button is-success is-outlined" :disabled="editing">
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
import * as Notifier from "@/helpers/notifier.js";

export default {
    data: () => {
        return {
            id: null,
            title: null,
            beginDate: null,
            endDate: null,
            description: null,
            selectedProducts: [],
            editing: false,
            showNotification: false,
            notifications: [],
            loading: false
        }
    },
    components: {
        WsKeywordSearch,
        ProductsSelector
    },
    watch:{
        notifications() {
            this.showNotification = this.notifications.length != 0;
        }
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

            this.loading = true;

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
                Notifier.success("Proposal was created.");
                this.notifications = [];
                this.loading = false;
                this.goBack();
            })
            .catch(error => {
                this.loading = false;
                this.notifications = error.response.data;
            });
        },
        goBack() {
            this.$router.go(-1);
        },
        remove() {
            this.$dialog.confirm({
                title: 'Deleting proposal',
                message: 'Are you sure you want to <b>delete</b> this proposal?',
                confirmText: 'Delete Account',
                type: 'is-danger',
                hasIcon: true,
                onConfirm: () => {
                    this.loading = true;

                    new API().delete("/proposals/" + this.id).then(() => {
                        Notifier.success("Proposal was deleted.");
                        this.notifications = [];
                        this.loading = false;
                        this.goBack();
                    })
                    .catch(error => {
                        this.loading = false;
                        this.notifications = error.response.data;
                    });
                }
            });
        }
    },
    mounted() {
        let id = this.$route.params.id;

        if (!id || Number.isNaN(Number(id)))
        return;

        this.editing = true;
        this.loading = true;

        this.getProposal(id).then(response => {
            this.loading = false;
            this.notifications = [];
            let proposal = response.data;

            this.id = proposal.id;
            this.title = proposal.title;
            this.beginDate = new Date(proposal.beginDate);
            this.endDate = new Date(proposal.endDate);
            this.description = proposal.description;
            this.selectedProducts = proposal.proposalLines.map(line => {
                return Object.assign(line.product, { "price": line.price });
            });
        })
        .catch(error => {
            this.loading = false;
            this.notifications = error.response.data;
        });
    }
}
</script>
