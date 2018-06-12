<template lang="html">
    <div>
        <GChart :type="type" :options="options" :data="data" :settings="{ packages: ['corechart'] }" @ready="onChartReady"/>
        <b-loading :is-full-page="false" :active.sync="loading" :canCancel="false" />
    </div>
</template>

<script>
import { GChart } from 'vue-google-charts';

export default {
    data() {
        return {
            loading: true
        };
    },
    props: ['options', 'data', 'type'],
    components: { GChart },
    methods: {
        onChartReady() {
            this.loading = false;
        }
    },
    beforeMount() {
        let options = {
            titleTextStyle: {
                fontSize: 15
            },
            colors: ['#7957d5', '#8566d8', '#9175dc', '#9d84e0', '#a994e4', 'b5a3e8']
        };

        Object.assign(this.options, options);
    }
}
</script>
