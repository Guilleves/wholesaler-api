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
  props: ['names', 'amount', 'title', 'barname', 'color'],
  watch: {
    // Awful solution...
    names(val) {
      this.options.xAxis.data = val;
      this.loading = false;
    },
    amount(val) {
      this.options.series[0].data = val;
      this.loading = false;
    }
  },
  beforeMount() {
    if ((this.names && this.amount) && (this.names.length != 0 || this.amount.length != 0))
      this.loading = false;

    this.options = {
      color: this.color,
      title: {
        text: this.title
      },
      lazyUpdate: true,
      tooltip: {},
      xAxis: {
        data: this.names || []
      },
      yAxis: {},
      series: [{
        name: this.barname,
        type: 'bar',
        data: this.amount || []
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
