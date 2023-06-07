<template>
  <div id="app">
    <div id="nav">
      <h1>
        <router-link class="myRouter" to="/" style="text-decoration: none;">
        </router-link>
      </h1>
    </div>
    <router-view />
  </div>
</template>

<style lang="scss">
html,body{
    background-color: #c2e1fe !important;
}
#app{
    height: 100vh;
}
</style>
