<template lang="html">
    <div>
        <b-table
            hoverable
            selectable
            :data="formattedData"
            :columns="columns"
            :loading="loading"
            backend-pagination
            :paginated="!dontPaginate"
            :per-page="pageSize"
            :total="total"
            @page-change="onPageChange"
            @select="onSelect">
        </b-table>
    </div>
</template>

<script>
import * as Notifier from "@/helpers/notifier.js";

export default {
    name: "ws-table",
    data() {
        return {
            total: 0,
            loading: false,
            pageIndex: 1,
            pageSize: 5,
            data: [],
            formattedData: [],
            selected: {},
            error: false
        }
    },
    props: {
        filters: Object,
        format: Function,
        columns: Array,
        fetch: Function,
        rawData: Boolean,
        dontPaginate: Boolean
    },
    watch: {
        data(val) {
            this.formattedData = val.map(d => this.format(d));
        },
        filters(val) {
            this.getData(Object.assign(val, this.filters));
        }
    },
    methods: {
        getData(filters) {
            let self = this;

            this.loading = true;

            this.fetch(filters).then((response) => {
                if (self.rawData)
                    self.data = response.data;
                else
                    self.data = response.data.items;

                self.total = response.data.size;
                self.loading = false;
                self.error = false;
            }).catch(() => {
                Notifier.error("Couldn't find any item.");
                self.data = [];
                self.total = 0;
                self.pageIndex = 1;
                self.loading = false;
                self.error = true;
            });
        },
        onPageChange(page) {
            if (this.error)
                return;

            this.pageIndex = page - 1;
            Object.assign(this.filters, {
                "pageSize": this.pageSize,
                "pageIndex": this.pageIndex
            });

            this.getData(this.filters);
        },
        onSelect(selected) {
            this.$emit('select', selected);
        }
    },
    mounted() {
        this.getData(this.filters);
    }
}
</script>
