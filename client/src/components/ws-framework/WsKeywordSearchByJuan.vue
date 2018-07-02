<template lang="html">
  <div>
    <b-field>
      <b-input
        icon-pack="fas"
        icon="search"
        v-model="keyword"
        :placeholder="placeholder"
        :loading="loading"
          />
    </b-field>
  </div>
</template>

<script>
export default {
  name: "ws-keyword-search",
  data() {
    return {
      keyword: null,
      typingTimer: null,
      loading: false
    }
  },
  props: [ "placeholder" ],
  watch: {
    keyword() {
        this.loading = true;
        clearTimeout(this.typingTimer);
        this.typingTimer = setTimeout(this.doneTyping, 500);
    }
  },
  methods: {
      doneTyping() {
          this.loading = false;
          this.$emit('input', {keyword: this.keyword});
      }
  }
}
</script>
