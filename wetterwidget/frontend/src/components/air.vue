<template>
    <RouterLink :to="{ name: 'city', params: { weatherid: '' + wetter.openWeatherId  + '' } }" class="PreviewTemp">
        <div class="StadtNameForecast">
            {{ wetter.stadt }}
            <br>
            <small><small>Luft</small></small>
        </div>
        <div class="flexhorizontal airmargintop">
            <div class="flexvertical textaligncenter font">
                Feuchtigkeit
                <br>
                {{ wetter.luftfeuchtigkeit }}
            </div>
            <div class="flexvertical textaligncenter font">
                Druck
                <br>
                {{ wetter.luftdruck }}
            </div>
        </div>

    </RouterLink>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "air",

        data: () => ({
                url: window.location.protocol +
                    "//" +
                    window.location.hostname +
                    ":8081/wetterdata/air",
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

    .airmargintop {
        margin-top: 15px;
    }

    .font {
        font-family: 'Geologica', sans-serif;
        font-size: 14px;
        color: #84A4FC;
    }
</style>