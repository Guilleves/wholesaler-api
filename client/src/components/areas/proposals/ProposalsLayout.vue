<template id="">
    <div>
        <ws-hero title="Proposals" description="View, create or delete proposals"/>
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
