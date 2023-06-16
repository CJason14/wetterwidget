<template>
    <a  v-bind:href="'/city/' + this.weatherid" class="PreviewTemp">
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
        name: "temperatur",

        data: () => ({
                wetter: {}
                }),

        async created() {
            await axios
                .get(window.location.protocol +
                    "//" +
                    window.location.hostname +
                    ":8081/wetterdata/temperatur/" + this.weatherid)
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

    .pink {
        background: pink;
        min-width: 20px;
        height: 20px;
        margin: 5px;
    }

</style>