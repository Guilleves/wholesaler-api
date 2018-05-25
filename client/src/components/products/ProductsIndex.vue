<template id="">
    <div>
        <section class="hero is-primary">
            <!-- Hero head: will stick at the top -->
            <div class="hero-head">
                <nav class="navbar">
                    <div class="container">
                        <div class="navbar-brand">
                            <a class="navbar-item">
                            </a>
                            <span class="navbar-burger burger" data-target="navbarMenuHeroA">
                                <span></span>
                                <span></span>
                                <span></span>
                            </span>
                        </div>
                    </div>
                </nav>
            </div>

            <!-- Hero content: will be in the middle -->
            <div class="hero-body">
                <div class="container has-text-centered">
                    <h1 class="title">
                        Products
                    </h1>
                    <h2 class="subtitle">
                        View, create or delete products
                    </h2>
                </div>
            </div>
        </section>
        <section class="section">
            <div class="container">
                <div class="columns">
                    <div class="column is-two-fifths">
                        <ws-keyword-search @input="buildSearchCriteria($event)" />
                        </div>
                        <div class="column">
                            <ws-option-filter
                            option-type="brandId"
                            filter="brands"
                            placeholder="Select a brand"
                            @selected="buildSearchCriteria($event)" />
                        </div>
                        <div class="column">
                            <ws-option-filter
                            option-type="categoryId"
                            filter="categories"
                            placeholder="Select a category"
                            @selected="buildSearchCriteria($event)" />
                        </div>
                    </div>
                </div>
                <div class="container">
                    <b-table
                    :data="formattedProducts"
                    :columns="columns"
                    :loading="loading"
                    backend-pagination
                    :paginated="true"
                    :per-page="pageSize"
                    :total="total"
                    @page-change="onPageChange"/>
                </div>
            </section>
        </div>
    </template>

    <script>
    import API from './../../helpers/api.js';
    import WsOptionFilter from "@/components/ws-framework/WsOptionFilter.vue";
    import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";

    export default {
        name: 'products-index',
        data: () => {
            return {
                total: 0,
                loading: false,
                pageIndex: 1,
                pageSize: 5,
                searchCriteria: {},
                products: [],
                formattedProducts: [],
                columns: [
                    {
                        field: 'id',
                        label: 'ID',
                        width: '40',
                        numeric: true
                    },
                    {
                        field: 'name',
                        label: 'Name',
                    },
                    {
                        field: 'gtin',
                        label: 'GTIN',
                    },
                    {
                        field: 'brand',
                        label: 'Brand',
                        centered: true
                    },
                    {
                        field: 'category',
                        label: 'Category',
                    }
                ]
            };
        },
        components: {
            WsOptionFilter,
            WsKeywordSearch
        },
        watch: {
            products: function(val) {
                this.formattedProducts = val.map(product => {
                    return {
                        id: product.id,
                        name: product.name,
                        gtin: product.gtin,
                        brand: product.brand.name,
                        category: product.category.name
                    }
                });
            }
        },
        methods: {
            buildSearchCriteria(param) {
                Object.assign(this.searchCriteria, param);
                this.getProducts();
            },
            getProducts() {
                let self = this;

                this.loading = true;

                return new API().get('/products', this.searchCriteria).then((response) => {
                    self.products = response.data.items;
                    self.total = response.data.size;
                    self.loading = false;
                }).catch(() => {
                    self.$toast.open("Couldn't find products.");
                    self.products = [];
                    self.total = 0;
                    self.loading = false;
                });
            },
            onPageChange(page) {
                this.pageIndex = page - 1;
                this.buildSearchCriteria({
                    "pageSize": this.pageSize,
                    "pageIndex": this.pageIndex
                });
            }
        },
        mounted : function() {
            this.getProducts();
        }
    }
    </script>
