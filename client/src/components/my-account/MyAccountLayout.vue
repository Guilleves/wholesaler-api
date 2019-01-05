<template id="">
    <div>
        <ws-hero title="My Account" description="Edit your account settings"/>
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
