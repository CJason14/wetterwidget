<template>
    <RouterLink :to="{ name: 'city', params: { weatherid: '' + wetter.openWeatherId  + '' } }" class="Preview">
        <div class="Info">
            <div class="StadtName">
                {{ wetter.stadt }}
            </div>
            <div class="StadtInfo">
                Niederschlag: {{ wetter.niederschlag }}
                <br>
                Luftfeuchtigkeit: {{ wetter.luftfeuchtigkeit }}
            </div>
        </div>
        <div class="Inforight">
            {{ wetter.aktuelle_temperatur }}°
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
                    ":8081/wetterdata/city",
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