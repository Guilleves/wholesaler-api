<template>
    <div>
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
                        Dashboard
                    </h1>
                    <h2 class="subtitle">
                        a description??
                    </h2>
                </div>
            </div>
            <!-- Hero footer: will stick at the bottom -->
            <div class="hero-foot">
                <nav class="tabs">
                    <div class="container">
                        <ul>
                            <li is-selected><a>Home</a></li>
                            <li><a>Products</a></li>
                            <li><a>Users</a></li>
                            <li><a>Proposals</a></li>
                        </ul>
                    </div>
                </nav>
            </div>
        </section>
        <p>Esto no se parece ni un poquito a un dashboard :)</p>
        <p>Pero bueno, la idea está</p>
        <footer class="footer">
            <div class="container">
                <div class="content has-text-centered">
                    <p>
                        <strong>Wholesaler</strong> by <a href="https://tuvieja.com">Guillermina Véscovo & Juan Grasso</a>. The source code is licensed
                        <a href="http://opensource.org/licenses/mit-license.php">MIT</a>. The website content
                        is licensed <a href="http://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY NC SA 4.0</a>.
                    </p>
                </div>
            </div>
        </footer>
    </div>
</template>

<script>
export default {
    name: 'HelloWorld',
    props: {
        msg: String
    }
}
</script>
