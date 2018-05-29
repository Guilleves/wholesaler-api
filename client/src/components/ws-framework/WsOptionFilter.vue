<template lang="html">
    <div class="">
        <b-field>
            <b-select expanded :placeholder="this.placeholder" rounded @input="selectOption($event)">
                <option
                v-for="option in options"
                :value="option.id"
                :key="option.id">
                {{ option.name }}
            </option>
        </b-select>
    </b-field>
</div>
</template>

<script>
import API from './../../helpers/api.js';

export default {
    name: "option-filter",
    props: {
        filter: String,
        optionType: String,
        placeholder: String,
        staticOptions: Array,
        format: Function
    },
    data: function() {
        return {
            options: [],
            selectedOption: ""
        }
    },
    methods: {
        selectOption: function(event){
            this.selected = event;
            this.$emit('selected', {[this.optionType]: event});
        }
    },
    mounted: function(){
        if (this.staticOptions) {
            this.options = this.staticOptions;
            return;
        }

        var self = this;
        new API()
        .get('/' + self.filter, )
        .then((response) => {
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
