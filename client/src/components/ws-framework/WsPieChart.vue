<template lang="html">
  <div class="echart">
    <chart :option="options" :loading="loading" />
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: true,
      options: {}
    };
  },
  props: ['data', 'title', 'barname', 'color'],
  watch: {
    data(val) {
      this.options.series[0].data = val;
      this.loading = false;
    }
  },
  beforeMount() {
    if (this.data)
    this.loading = false;

    this.options = {
      title: {
        text: this.title
      },
      lazyUpdate: true,
      tooltip: {},
      series: [{
        name: this.barname,
        type: 'pie',
        data: this.data
      }]
    }
  }
}
</script>

<style scoped>
.echart {
  width: 30em;
  height: 22em;
}
</style>
