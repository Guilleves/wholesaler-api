<template lang="html">
  <div class="">
    <b-field :label="'Select a ' + this.filter" >
      <b-select :placeholder="this.placeholder" rounded @input="selectOption($event)">
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
import * as session from "./../../helpers/session.js";
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
    session.set({
      token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImlkIjo1fQ.GR8v-RyugBdtq21_XliVpG6DJypCkFxr1zI7YcwIntE"
    });
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
