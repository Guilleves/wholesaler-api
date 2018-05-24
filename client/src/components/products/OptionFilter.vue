<template lang="html">
  <div class="">
    <b-field :label="this.placeholder" >
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
    placeholder: String
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
    var self = this;
    new API()
    .get('/' + self.filter, )
    .then((response) => {
      self.options = response.data;
    })
    .catch((error) => {
      console.log(error);
    });
  }
}
</script>
