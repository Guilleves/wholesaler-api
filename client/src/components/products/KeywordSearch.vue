<template lang="html">
  <div class="">
    <b-field label="Search by name">
      <b-input placeholder="Product's name" v-model="name" rounded></b-input>
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
  }
}
</script>
