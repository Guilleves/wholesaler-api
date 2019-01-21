<template>
  <section class="hero is-light is-fullheight">
    <div class="hero-body">
      <div class="container align-center">
        <h1 class="title">
          UNAUTHORIZED
        </h1>
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpjmzPElyoG8zZcSOa6q1z8qZPKdbL5K6s-QJac5oNMhstb7Mg2w" alt="">
        <h2 class="subtitle">You have not enaugh permissions to do this.</h2>
        <h3><b>Navigate back to <router-link to="/">home</router-link> or go <a @click="$router.go(-2)">back</a></b></h3>
      </div>
    </div>
  </section>
</template>

<script>
export default {
}
</script>

<style>
.container.align-center {
  text-align: center;
}
</style>
