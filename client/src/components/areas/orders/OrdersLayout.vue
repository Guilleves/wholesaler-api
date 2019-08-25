<template id="">
    <div>
        <ws-hero title="Orders" description="View, create or delete orders"/>
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
