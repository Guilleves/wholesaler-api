<template lang="html">
  <div>
    <b-field>
      <b-input
        icon-pack="fas"
        icon="search"
        placeholder="Product's name"
        @input="addKeyword($event)"
        rounded></b-input>
    </b-field>
  </div>
</template>

<script>
export default {
  name: "keyword-search",
  data: function(){
    return {
      name: ""
    }
  },
  methods: {
    addKeyword: function(event) {
      this.$emit('input', {keyword: event})
    }
  }
}
</script>
