<template>
    <main class="aligncenter">
        <h1 class="headline">{{ historie.data[0].stadt }}</h1>
        <RouterLink class="link" :to="{name: 'home'}">
        <p class="fontmain">Zurück</p>
        </RouterLink>
        <br>
        <br>
        <select placeholder="Zeitpunkt" class="searchbar fontmain" v-on:change="changedata" v-on:select="changedata" v-model="zeitpunkt">
            <option v-for="entry, key in historie.data" v-bind:value="++key">
                {{ new Date(entry.unix_timestamp  * 1000).getDate() + "." +  new Date(entry.unix_timestamp  * 1000).getMonth() + "." + new Date(entry.unix_timestamp  * 1000).getFullYear() + "    " + new Date(entry.unix_timestamp  * 1000).getHours() + ":" + new Date(entry.unix_timestamp  * 1000).getMinutes()}}
            </option>
        </select>
        <br>
        <div class="container">
            <div class="PreviewCity">
                <div class="StadtNameForecast">
                    Luft
                </div>
                <div class="flexhorizontal align">
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
            </div>
            <div class="PreviewCity">
                <div class="StadtNameForecast">
                    Wind
                </div>
                <div class="flexhorizontal align">
                    <div class="flexvertical textaligncenter font">
                        Geschwindigkeit
                        <br>
                        {{ wetter.wind_geschwindigkeit }} km/h
                    </div>
                    <div class="flexvertical textaligncenter font">
                        Richtung
                        <br>
                        {{ wetter.wind_richtung }}°
                    </div>
                </div>
            </div>
            <div class="PreviewCity">
                <div class="StadtNameForecast">
                    Temperatur
                </div>
                <br>
                <div class="flexvertical">
                    <div class="flexhorizontal">
                        <div class="flexvertical textaligncenter small">
                            Gefühlt:
                            <br>
                            {{ wetter.temperatur_gefuehlt }}°
                            <br>
                        </div>
                        <div class="flexvertical textaligncenter small">
                            Aktuell:
                            <br>
                            {{ wetter.temperatur_aktuell }}°
                            <br>
                            <br>
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
            </div>
            <div class="PreviewCity">
                <div class="StadtNameForecast">
                    Allgemein
                </div>
                <br>
                <div class="fontmain">
                    {{ wetter.kurzBeschreibung }}
                </div>
                <br>
                <div class="fontmain">
                    {{ wetter.temperatur_aktuell }}°
                </div>
            </div>
        </div>
    </main>
</template>

<script>
    var pathArray = window.location.pathname.split('/');
    import Chart from 'chart.js/auto';
    import axios from 'axios'
    export default {

        name: "historie",


        data: () => ({
            url: window.location.protocol +
                "//" +
                window.location.hostname +
                ":8081/wetterdata/historie/" + pathArray[2],
            wetter: {},
            historie: {},
            id: pathArray[2],
            zeitpunkt: ""
        }),

        async created() {
            await axios
                .get(this.url)
                .then(response => {
                    this.historie = response;
                    console.log(response);
                    this.wetter = response.data[0]
                })



        },

        methods: {
            changedata: function () {
                let val = this.zeitpunkt - 1;
                this.wetter = this.historie.data[val];
            }
        }
    }
</script>

<style>
    .link {
        padding: 10px;
        border-radius: 10px;
        margin-top: 10px;
    }

    .link p {
        margin: 0;
    }

    .container {
        display: grid;
        grid-auto-rows: 1fr;
        grid-template-columns: repeat(auto-fit, minmax(300px, 400px));
        grid-template-rows: 1fr 1fr 1fr;
        gap: 10px 10px;
        justify-content: center;
        align-content: center;
        justify-items: center;
        align-items: center;
        min-width: 50vw;
    }

    .PreviewCity {
        display: block;
        margin: 20px;
        width: 250px;
        height: 150px;
        background-color: #CCD0D8;
        border-radius: 20px;
        padding: 10px;
        overflow: hidden;
    }

    .WindView {
        border-radius: 10px;
    }

    .align {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 150px;
        margin-top: -22px;
        gap: 10px;
    }
</style>