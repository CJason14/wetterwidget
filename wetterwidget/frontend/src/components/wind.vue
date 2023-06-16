<template>
    <a  v-bind:href="'/city/' + this.weatherid" class="PreviewTemp">
        <div class="StadtNameForecast">
            {{ wetter.stadt }}
            <br>
            <small><small>Wind</small></small>
        </div>
        <div class="flexhorizontal airmargintop">
            <div class="flexvertical textaligncenter font">
                Geschwindigkeit
                <br>
                {{ wetter.windgeschwindigkeit }} km/h
            </div>
            <div class="flexvertical textaligncenter font">
                Richtung
                <br>
                {{ wetter.windrichtung }}°
            </div>
        </div>

    </a>
</template>

<script>
    var pathArray = window.location.pathname.split('/');
    var secondLevelLocation = pathArray[2];

    import axios from 'axios'
    export default {
        props: {
            weatherid: {
                type: String,
                required: true
            }
        },
        name: "wind",

        data: () => ({
                wetter: {}
                }),

        async created() {
            await axios
                .get(window.location.protocol +
                    "//" +
                    window.location.hostname +
                    ":8081/wetterdata/wind/" + this.weatherid)
                .then(response => {(this.wetter = response.data)})
        }
        }
</script>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Geologica:wght@500&display=swap');
</style>