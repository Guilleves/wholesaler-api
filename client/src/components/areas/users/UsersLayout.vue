<template id="">
    <div>
        <ws-hero title="Users" description="View, edit or delete users"/>
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
