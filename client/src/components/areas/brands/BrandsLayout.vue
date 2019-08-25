<template id="">
    <div>
        <ws-hero title="Brands" description="View, create, edit or delete brands"/>
        <router-view />
    </div>
</template>

<script>
    import WsHero from "@/components/ws-framework/WsHero.vue";

    export default {
        components: {
            WsHero
        }
    }
</script>
