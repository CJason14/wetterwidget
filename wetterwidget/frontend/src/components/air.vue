<template>
    <a v-bind:href="'/city/' + this.weatherid" class="PreviewTemp">
        <div class="StadtNameForecast">
            {{ wetter.stadt }}
            <br>
            <small><small>Luft</small></small>
        </div>
        <div class="flexhorizontal airmargintop">
            <div class="flexvertical textaligncenter font">
                Feuchtigkeit
                <br>
                {{ wetter.luftfeuchtigkeit }}%
            </div>
            <div class="flexvertical textaligncenter font">
                Druck
                <br>
                {{ wetter.luftdruck }} hPa
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
        name: "air",

        data: () => ({
                wetter: {}
                }),

        async created() {
            await axios
                .get(window.location.protocol +
                    "//" +
                    window.location.hostname +
                    ":8081/wetterdata/air/" + this.weatherid)
                .then(response => {(this.wetter = response.data)})
        }
        }
</script>


<style>
    @import url('https://fonts.googleapis.com/css2?family=Geologica:wght@500&display=swap');

    .airmargintop {
        margin-top: 15px;
    }

    .font {
        font-family: 'Geologica', sans-serif;
        font-size: 14px;
        color: #84A4FC;
    }
</style>