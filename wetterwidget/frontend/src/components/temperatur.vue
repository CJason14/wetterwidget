<template>
    <RouterLink :to="{ name: 'city', params: { weatherid: '' + wetter.openWeatherId  + '' } }" class="PreviewTemp">
        <div class="StadtNameForecast">
            {{ wetter.stadt }}
        </div>
        <div class="flexvertical">
            <div class="flexhorizontal">
                <div class="flexvertical textaligncenter small">
                    Gefühlt:
                    <br>
                    {{ wetter.temperatur_gefuehlt }}°
                </div>
                <div class="flexvertical textaligncenter small">
                    Aktuell:
                    <br>
                    {{ wetter.temperatur_aktuell }}°
                </div>
            </div>
            <div class="flexhorizontal">
                <div class="flexvertical textaligncenter small">
                    Min:
                    <br>
                    {{ wetter.temperatur_min }}°
                </div>
                <div class="flexvertical textaligncenter small">
                    Max:
                    <br>
                    {{ wetter.temperatur_max }}°
                </div>
            </div>
        </div>

    </RouterLink>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "temperatur",

        data: () => ({
                url: window.location.protocol +
                    "//" +
                    window.location.hostname +
                    ":8081/wetterdata/temperatur",
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

    .small {
        font-family: 'Geologica', sans-serif;
        font-size: 14px;
        width: 80px;
        overflow: hidden;
        margin-top: 4px;
        color: #84A4FC;
    }

    .textaligncenter {
        text-align: center;
    }

    .flexvertical {
        display: flex;
        flex-direction: column;
    }

    .flexhorizontal {
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;

    }

    .pink {
        background: pink;
        min-width: 20px;
        height: 20px;
        margin: 5px;
    }

    .PreviewTemp {
        display: block;
        margin: 20px;
        width: 200px;
        height: 102px;
        background-color: #CCD0D8;
        border-radius: 20px;
        padding: 10px;
        overflow: hidden;
    }
</style>