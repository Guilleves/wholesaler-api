<template>
  <div id="app">
    <router-view msg="welcome to huhehehue" ></router-view>
    <router-link to='/products'>Products</router-link>
  </div>
</template>

<script>
import HelloWorld from './components/dashboard/HelloWorld.vue'

export default {
  name: 'app'
}
</script>
