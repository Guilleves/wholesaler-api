<template>
    <section class="section">
        <div class="container">
            <b-loading :is-full-page="false" :active.sync="loading" :canCancel="false" />
            <form @submit.prevent="save()">
                <ws-error v-model="errors" />
                <div class="columns">
                    <div class="column">
                        <b-field label="Selected proposal">
                            <b-input v-model="selectedProposal.title" disabled />
                        </b-field>
                        <b-field label="Date ordered">
                            <b-datepicker
                            placeholder="Click to select..."
                            icon="fam fa-calendar"
                            v-model="order.dateOrdered" />
                        </b-field>
                        <b-field label="Detail">
                            <div class="box">
                                <b-table :data="productPrices">
                                    <template slot-scope="props">
                                        <b-table-column field="amount" label="Amt." width="100">
                                            <b-input type="number" v-model="props.row.amount" @input="selectAmount(props.row.id, props.row.amount)" />
                                        </b-table-column>
                                        <b-table-column field="name" label="Product name">
                                            {{ props.row.name }}
                                        </b-table-column>
                                        <b-table-column field="name" label="Sub total">
                                            {{ "$" + props.row.subtotal }}
                                        </b-table-column>
                                        <b-table-column field="name" label="Total">
                                            {{ "$" + props.row.total }}
                                        </b-table-column>
                                    </template>
                                    <template slot="empty">
                                            <div class="has-text-grey has-text-centered">
                                            <p>Select at least a product.</p>
                                        </div>
                                </template>
                                <template slot="footer">
                                    <div class="has-text-right">
                                        Total: ${{total}}
                                    </div>
                                </template>
                            </b-table>
                        </div>
                    </b-field>
                </div>
                <div class="column">
                    <products-selector @selected="onProductSelect" :search="getProducts" v-model="selectedProducts"/>
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
                        <b-tooltip label="Can't edit orders" :active="editing" class="is-success" position="is-top">
                            <button class="button is-success is-outlined" :disabled="editing">
                                Save
                            </button>
                        </b-tooltip>
                    </p>
                </b-field>
            </b-field>
        </form>
    </div>
</section>
</template>

<script>
import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";
import ProductsSelector from "@/components/ws-framework/WsProductsSelector.vue";
import WsError from "@/components/ws-framework/WsError.vue";
import API from "@/helpers/api.js";
import * as Notifier from "@/helpers/notifier.js";

export default {
    data() {
        return {
            order: {
                id: null,
                dateOrdered: new Date()
            },
            editing: false,
            errors: [],
            loading: false,
            selectedProducts: [],
            selectedProposal: {},
            productPrices: [],
            total: 0
        }
    },
    components: {
        WsKeywordSearch,
        ProductsSelector,
        WsError
    },
    methods: {
        getRetails() {
            return new API().get("/organizations/retails/");
        },
        getProposals() {
            return new API().get("/proposals");
        },
        getProducts(query) {
            if (!this.selectedProposal)
            return null;

            return new Promise((resolve) => {
                let products = this.selectedProposal.proposalLines.map(line => {
                    return Object.assign(line.product, { price: line.price, lineId: line.id });
                });

                products = products.filter(p => {
                    return (p.name.indexOf(query.keyword) > -1 || p.brand.name.indexOf(query.keyword) > -1 || p.category.name.indexOf(query.keyword) > -1 )
                });

                resolve({data: { items: products }});
            });
        },
        onProductSelect(product) {
            this.productPrices.push({
                id: this.productPrices.length,
                lineId: product.lineId,
                amount: 0,
                name: product.name,
                subtotal: product.price,
                total: 0
            });
        },
        selectAmount(id, amount) {
            this.productPrices[id].total = amount * this.productPrices[id].subtotal;
            this.total = this.productPrices.map(p => {
                return p.total
            }).reduce((a, b) => {
                return a + b;
            });
        },
        formatProposals(data) {
            return data.items.map(prop => {
                return {
                    id: prop.id,
                    name: prop.title
                }
            });
        },
        save() {
            if (this.editing)
            return;

            let data = {
                dateOrdered: this.order.dateOrdered,
                lines: this.productPrices.map(line => {
                    return {
                        proposalLineId: line.lineId,
                        quantity: line.amount
                    }
                })
            };

            let vm = this;
            debugger;
            new API().post("/orders", data).then(() => {
                Notifier.success("Order was created.");
                vm.errors = [];
                vm.loading = false;
                vm.goBack();
            })
            .catch(error => {
                vm.loading = false;
                vm.errors = error.response.data;
            });
        },
        goBack() {
            let proposalId = this.$route.params.proposalId;

            if (proposalId)
            this.$router.push(`/proposals/${proposalId}/orders`);
            else
            this.$router.push('/orders');
        },
        remove() {
            this.$dialog.confirm({
                title: 'Deleting order',
                message: 'Are you sure you want to <b>delete</b> this order?',
                confirmText: 'Delete Order',
                type: 'is-danger',
                hasIcon: true,
                onConfirm: () => {
                    this.loading = true;

                    new API().delete("/orders/" + this.order.id).then(() => {
                        Notifier.success("Order was deleted.");
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
        let orderId = this.$route.params.orderId;
        let proposalId = this.$route.params.proposalId;

        let vm = this;

        if (proposalId && !Number.isNaN(Number(proposalId))) {
            new API().get("/proposals/" + proposalId).then(response => {
                vm.selectedProposal = response.data;
            });
        }

        if (!orderId || Number.isNaN(Number(orderId)))
            return;

        this.editing = true;

        new API().get("/orders/" + orderId).then(response => {
            let order = response.data;

            vm.order.id = order.id;
            vm.order.dateOrdered = new Date(order.dateOrdered);

            vm.selectedProducts = order.orderLines.map(line => {
                return Object.assign(line.proposalLine.product, { price: line.proposalLine.price, lineId: line.proposalLine.id });
            });

            if (!proposalId) {
                let proposals = order.orderLines.map(line => {
                    return line.proposalLine.proposal;
                });

                vm.selectedProposal = proposals[0];
            }

            vm.productPrices = order.orderLines.map((line, index) => {
                return {
                    id: index,
                    amount: line.quantity,
                    name: line.proposalLine.product.name,
                    subtotal: line.proposalLine.price,
                    total: line.proposalLine.price * line.quantity
                }
            });

            vm.total = vm.productPrices.map(p => {
                return p.total
            }).reduce((a, b) => {
                return a + b;
            });
        });
    }
}
</script>
