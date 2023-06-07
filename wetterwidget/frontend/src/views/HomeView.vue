<script setup>
  import city from './../components/city.vue'
  import forecast from './../components/forecast.vue'
  import temperatur from './../components/temperatur.vue'
  import wind from './../components/wind.vue'
  import cityweather from './../components/cityweather.vue'
  import air from './../components/air.vue'


</script>

<template>
  <main class="aligncenter">
    <div>
      <h1 class="headline">Dortmund</h1>
      <p class="fontmain">Aktueller Standort</p>
    </div>
    <br>
    <p class="fontmain">Favoriten</p>
    <RouterLink class="fontmain" :to="{name: 'edit'}">Bearbeiten</RouterLink>
    <div>
      <temperatur>
      </temperatur>
      <city>
      </city>
      <wind>
      </wind>
      <cityweather>
      </cityweather>
      <air>
      </air>
    </div>
  </main>
</template>

<style>
  .aligncenter {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
</style>