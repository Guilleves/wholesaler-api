<template>
    <section class="hero is-primary">
        <!-- Hero content: will be in the middle -->
        <div class="hero-body">
            <div style="height:60px;" />
            <div class="container has-text-centered">
                <h1 class="title">
                    {{ title }}
                </h1>
                <h2 class="subtitle">
                    {{ description }}
                </h2>
            </div>
        </div>
    </section>
</template>

<script>
export default {
    name: "ws-hero",
    props: ["title", "description"]
}
</script>
