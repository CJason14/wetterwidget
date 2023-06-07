
<template>
    <main class="aligncenter">
        <h1 class="headline">Dortmund</h1>
        <RouterLink :to="{name: 'home'}"><p class="fontmain">Zurück</p></RouterLink>
        <div class="container">
            <div>{{ $route.params.weatherid }}</div>
            <div>Test</div>
            <div>Test</div>
            <div>Test</div>
        </div>
    </main>
</template>

<style>
.container {
  display: grid; 
  grid-template-columns: 1fr 1fr; 
  grid-template-rows: 1fr 1fr 1fr; 
  gap: 10px 10px; 
  grid-template-areas: 
    ". ."
    ". ."
    ". ."; 
  justify-content: center; 
  align-content: center; 
  justify-items: center; 
  align-items: center; 
  width: 400px;
  margin-top: 50px;;
}
</style>