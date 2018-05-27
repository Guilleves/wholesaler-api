<template>
    <div style="height: 100%">
        <b-field label="Search product">
            <b-autocomplete
                rounded
                :data="[]"
                placeholder="e.g. Keyboard"
                icon="fas fa-search"
                @select="option => {}">
                <template slot="empty">No results found</template>
            </b-autocomplete>
        </b-field>
        <div class="box scrollable disabled">
            <p>You haven't selected any product yet.</p>
        </div>
    </div>
</template>

<script>
export default {
    name: "products-selector"
}
</script>

<style lang="css">
div .scrollable {
    overflow-y: scroll;
    min-height: 75%;
    max-height: 0;
}

div .scrollable.disabled {
    background-color: #F8F8F8;
    display: flex;
    text-align: center;
    flex-direction: column;
    justify-content: center;
}
</style>
