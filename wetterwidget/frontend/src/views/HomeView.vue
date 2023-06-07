<script setup>
        import city from './../components/city.vue'
        import forecast from './../components/forecast.vue'
        import temperatur from './../components/temperatur.vue'
        import air from './../components/air.vue'
        import wind from './../components/wind.vue'
</script>

<template>
  <main class="aligncenter">
    <city>
    </city>
    <forecast>
    </forecast>
    <temperatur>
    </temperatur>
    <air>
    </air>
    <wind>
    </wind>
  </main>
</template>

<style>
.aligncenter{
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>