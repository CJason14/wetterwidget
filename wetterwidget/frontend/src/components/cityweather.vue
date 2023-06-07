<template>
    <RouterLink :to="{ name: 'city', params: { weatherid: '' + wetter.openWeatherId  + '' } }" class="Preview">
        <div class="Infocityweather">
            <div class="StadtName">
                {{ wetter.stadt }}
            </div>
            <div class="StadtInfo">
                {{ wetter.kurzBeschreibung }}
                <br>

            </div>
        </div>
        <div class="Inforightcityweather">
            {{ wetter.aktuelle_temperatur }}°
        </div>

    </RouterLink>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "cityweather",

        data: () => ({
                url: window.location.protocol +
                    "//" +
                    window.location.hostname +
                    ":8081/wetterdata/cityweather",
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

    .Preview {
        display: block;
        margin: 20px;
        width: 200px;
        height: 70px;
        background-color: #CCD0D8;
        border-radius: 20px;
        padding: 10px;
        overflow: hidden;
    }

    .Infocityweather {
        position: relative;
        top: 15px;
        left: 10px;

    }

    .Inforightcityweather {
        position: relative;
        left: 137px;
        bottom: 25px;
        font-size: 35px;
        font-weight: bold;
        color: #1463F3;
        font-family: 'Geologica', sans-serif;
    }

    .StadtName {
        font-size: 22px;
        margin-left: -2px;
        max-width: 110px;
        overflow: hidden;
        color: #1463F3;
        font-family: 'Geologica', sans-serif;
    }

    .StadtInfo {
        color: grey;
        font-size: 10px;
    }
</style>