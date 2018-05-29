<template>
    <section class="hero is-primary">
        <!-- Hero head: will stick at the top -->
        <div class="hero-head">
            <nav class="navbar">
                <div class="container">
                    <div class="navbar-brand">
                        <a class="navbar-item">
                        </a>
                        <span class="navbar-burger burger" data-target="navbarMenuHeroA">
                            <span></span>
                            <span></span>
                            <span></span>
                        </span>
                    </div>
                </div>
            </nav>
        </div>

        <!-- Hero content: will be in the middle -->
        <div class="hero-body">
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
