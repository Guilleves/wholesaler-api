<script>
// CommitChart.js
import { HorizontalBar, mixins } from 'vue-chartjs';

export default {
    extends: HorizontalBar,
    mixins: [mixins.reactiveProp],
    props: ['options'],
    mounted () {
        this.renderChart(this.data, this.options);
    }
}
</script>
