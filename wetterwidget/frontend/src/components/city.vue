<template>
    <a v-bind:href="'/city/' + this.weatherid" class="Preview">
        <div class="Info">
            <div class="StadtName">
                {{ wetter.stadt }}
            </div>
            <div class="StadtInfo">
                Niederschlag: {{ wetter.niederschlag }}
                <br>
                Luftfeuchtigkeit: {{ wetter.luftfeuchtigkeit }}%
            </div>
        </div>
        <div class="Inforight">
            {{ wetter.aktuelle_temperatur }}°
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
        name: "city",

        data: () => ({
                wetter: {}
                }),

        async created() {
            await axios
                .get(window.location.protocol +
                    "//" +
                    window.location.hostname +
                    ":8081/wetterdata/city/" + this.weatherid)
                .then(response => {(this.wetter = response.data)})
        }
        }
</script>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Geologica:wght@500&display=swap');


    .Info {
        position: relative;
        top: 10px;
        left: 10px;

    }

    .Inforight {
        position: relative;
        left: 137px;
        bottom: 36px;
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