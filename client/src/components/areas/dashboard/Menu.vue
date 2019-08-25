<template lang="html">
    <b-menu>
        <b-menu-list label="Menu">
            <b-menu-item icon="cogs" active expanded>
                <template slot="label" slot-scope="props">
                    Admin
                    <b-icon
                        class="is-pulled-right"
                        :icon="props.expanded ? 'caret-down' : 'caret-up'">
                    </b-icon>
                </template>
                <b-menu-item
                    label="Proposals"
                    tag="router-link"
                    to="/proposals" />
                <b-menu-item
                    label="Orders"
                    tag="router-link"
                    to="/orders" />
                <b-menu-item
                    label="Products"
                    tag="router-link"
                    to="/products" />
                <b-menu-item
                    label="Users"
                    tag="router-link"
                    to="/users" />
            </b-menu-item>
            <b-menu-item icon="plus">
                <template slot="label" slot-scope="props">
                    Actions
                    <b-icon
                        class="is-pulled-right"
                        :icon="props.expanded ? 'caret-down' : 'caret-up'">
                    </b-icon>
                </template>
                <b-menu-item
                    label="New Proposal"
                    tag="router-link"
                    to="/proposals/new" />
                <b-menu-item
                    label="New Order"
                    tag="router-link"
                    to="/orders/new" />
            </b-menu-item>
            <b-menu-item
                label="Rankings"
                icon="trophy"
                tag="router-link"
                to="/rankings" />
        </b-menu-list>
    </b-menu>
</template>

<script>
export default {
}
</script>

<style lang="css">
</style>
