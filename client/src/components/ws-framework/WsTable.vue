<template lang="html">
    <div>
        <b-table
            focusable
            selectable
            :data="formattedData"
            :columns="columns"
            :loading="loading"
            backend-pagination
            :paginated="true"
            :per-page="pageSize"
            :total="total"
            @page-change="onPageChange"
            @select="onSelect" />
    </div>
</template>

<script>
export default {
    name: "ws-table",
    data: function() {
        return {
            total: 0,
            loading: false,
            pageIndex: 1,
            pageSize: 5,
            data: [],
            formattedData: [],
            selected: {}
        }
    },
    props: {
        filters: Object,
        format: Function,
        columns: Array,
        fetch: Function
    },
    watch: {
        data(val) {
            this.formattedData = val.map(d => this.format(d));
        },
        filters(val) {
            this.getData(val);
        }
    },
    methods: {
        getData(filters) {
            let self = this;

            this.loading = true;

            this.fetch(filters).then((response) => {
                self.data = response.data.items;
                self.total = response.data.size;
                self.loading = false;
            }).catch(() => {
                self.$toast.open("Couldn't find any item.");
                self.data = [];
                self.total = 0;
                self.loading = false;
            });
        },
        onPageChange(page) {
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
    mounted: function() {
        this.getData(this.filters);
    }
}
</script>
