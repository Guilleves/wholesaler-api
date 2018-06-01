<template>
    <b-notification type="is-danger" has-icon :active.sync="showErrors">
        <ul v-bind:key="index" v-for="(error, index) in value">
            <li>{{ error }}</li>
        </ul>
    </b-notification>
</template>

<script>
export default {
    name: "ws-error",
    props: ["value"],
    data: () => {
        return {
            showErrors: false
        }
    },
    watch: {
        value(val) {
            this.showErrors = val.length != 0;
        }
    }
}
</script>
