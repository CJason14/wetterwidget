<template>
    <RouterLink :to="{ name: 'city', params: { weatherid: '' + wetter.openWeatherId  + '' } }" class="PreviewTemp">
        <div class="StadtNameForecast">
            {{ wetter.stadt }}
            <br>
            <small><small>Wind</small></small>
        </div>
        <div class="flexhorizontal airmargintop">
            <div class="flexvertical textaligncenter font">
                Geschwindigkeit
                <br>
                {{ wetter.windgeschwindigkeit }}
            </div>
            <div class="flexvertical textaligncenter font">
                Richtung
                <br>
                {{ wetter.windrichtung }}
            </div>
        </div>

    </RouterLink>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "city",

        data: () => ({
                url: window.location.protocol +
                    "//" +
                    window.location.hostname +
                    ":8081/wetterdata/wind",
                wetter: {}
                }),

        async created() {
            await axios
                .get(this.url)
                .then(response => {(this.wetter = response.data)})
        }
        }
</script>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Geologica:wght@500&display=swap');
</style>