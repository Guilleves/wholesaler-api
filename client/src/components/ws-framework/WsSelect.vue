<template lang="html">
    <div class="">
        <b-field>
            <multiselect
            style="z-index: 10;"
            v-model="selected"
            label="name"
            track-by="id"
            :placeholder="placeholder"
            open-direction="bottom"
            :options="options"
            :searchable="true"
            :internal-search="true"
            :clear-on-select="true"
            :close-on-select="true"
            :options-limit="300"
            :limit="3"
            :max-height="600"
            :show-no-results="true" />
    </b-field>
</div>
</template>

<script>
export default {
    name: "option-filter",
    props: {
        optionType: String,
        placeholder: String,
        staticOptions: Array,
        format: Function,
        getOptions: Function
    },
    data: function() {
        return {
            options: [],
            selected: ""
        }
    },
    watch: {
        selected(val) {
            let id = val ? val.id : null;
            this.$emit("selected", {[this.optionType]: id});
        }
    },
    mounted: function(){
        if (this.staticOptions) {
            this.options = this.staticOptions;
            return;
        }

        var self = this;
        this.getOptions().then((response) => {
            if (self.format)
                self.options = self.format(response.data);
            else
                self.options = response.data;
        })
        .catch((error) => {
            console.log(error);
        });
    }
}
</script>

<style>
    .multiselect__tags {
        border-radius: 20px !important;
    }
</style>
