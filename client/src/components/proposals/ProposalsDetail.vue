<template>
    <section class="section">
        <div class="container">
            <form @submit.prevent="signup()">
                <div class="columns">
                    <div class="column">
                        <b-field label="Title">
                            <b-input
                            v-model="title"/>
                        </b-field>

                        <b-field grouped>
                            <b-field label="Begin Date" expanded>
                                <b-datepicker
                                placeholder="Click to select..."
                                icon="fam fa-calendar"
                                v-model="beginDate" />
                            </b-field>

                            <b-field label="End Date" expanded>
                                <b-datepicker
                                placeholder="Click to select..."
                                icon="fam fa-calendar"
                                v-model="endDate" />
                            </b-field>
                        </b-field>

                        <b-field label="Description">
                            <b-input
                            v-model="description" type="textarea"/>
                        </b-field>
                    </div>
                    <div class="column">
                        <products-selector />
                    </div>
                </div>
                <b-field grouped position="is-right">
                    <p class="control">
                        <button class="button">
                            Cancel
                        </button>
                        <button class="button is-primary">
                            Submit
                        </button>
                    </p>
                </b-field>
            </form>
        </div>
    </section>
</template>

<script>
import WsKeywordSearch from "@/components/ws-framework/WsKeywordSearch.vue";
import ProductsSelector from "@/components/proposals/ProductsSelector.vue";

export default {
    data: () => {
        return {
            title: null,
            beginDate: null,
            endDate: null,
            description: null
        }
    },
    components: {
        WsKeywordSearch,
        ProductsSelector
    }
}
</script>
