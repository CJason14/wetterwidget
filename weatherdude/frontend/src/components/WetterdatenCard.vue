<template>
    <b-card no-body border-variant="success" v-if="wetter" style="max-width: 70%; margin: auto;" >
        <b-card-title class="wetterdaten-card-title">Wetter in {{wetter.ort}} am {{wetter.zeitpunkt | dateToTitleDate}}</b-card-title>

        <b-row class="wetterdaten-card-row">
          <b-col>
            <b-card-text>Beschreibung</b-card-text>
            <b>{{wetter.info.beschreibung}}</b>
          </b-col>
          <b-col>
            <b-card-text>Temperatur</b-card-text>
            <b>{{wetter.data.temperatur_aktuell}} °C</b>
          </b-col>
          <b-col>
            <b-card-text>Feuchtigkeit</b-card-text>
            <b>{{wetter.data.luftfeuchtigkeit}} %</b>
          </b-col>
          <b-col>
            <b-card-text>Bewölkung</b-card-text>
            <b>{{wetter.data.bewölkung}} %</b>
          </b-col>
        </b-row>

        <b-row class="wetterdaten-card-row">
          <b-col>
            <b-card-text>Gefühlt</b-card-text>
            <b>{{wetter.data.temperatur_gefuehlt}} °C</b>
          </b-col>
          <b-col>
            <b-card-text>Min</b-card-text>
            <b>{{wetter.data.temperatur_min}} °C</b>
          </b-col>
          <b-col>
            <b-card-text>Max</b-card-text>
            <b>{{wetter.data.temperatur_max}} °C</b>
          </b-col>
          <b-col>
            <b-card-text>Luftdruck</b-card-text>
            <b>{{wetter.data.luftdruck}} hPa</b>
          </b-col>
        </b-row>

        <b-row class="wetterdaten-card-row">
          <b-col>
            <b-card-text>Wind Richtung</b-card-text>
            <b>{{wetter.data.wind_richtung}} °</b>
          </b-col>
          <b-col>
            <b-card-text>Wind Geschwindigkeit</b-card-text>
            <b>{{wetter.data.wind_geschwindigkeit}} m/s</b>
          </b-col>
          <b-col>
            <b-card-text>Niederschlag</b-card-text>
            <b>
              {{wetter.data.luftfeuchtigkeit}} l/m
              <sup>2</sup>
            </b>
          </b-col>
          <b-col>
            <b-card-text>Uhrzeit</b-card-text>
            <b>{{wetter.zeitpunkt | dateToCardDate}}</b>
          </b-col>
        </b-row>

    </b-card>
</template>
<script>
export default {
    name: "wetterdaten-card",
    props: {
        wetter: {
            required: true
        }
    },
    filters: {
      dateToTitleDate(value){
        var d = new Date(value);
        const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
        return d.toLocaleDateString('de-DE', options);
      },
      dateToCardDate(value){
        var d = new Date(value);
        const options = { hour: 'numeric', minute: 'numeric' };
        return d.toLocaleTimeString('de-DE', options);
      }
    }
}
</script>

<style scoped>
.wetterdaten-card-title {
  margin:auto;  
  margin-bottom:50px;
  margin-top:30px;
  color:#42b983;
  
}

.wetterdaten-card-row {
    margin-left: 50px;
    margin-bottom: 50px;
    text-align: center;
}

</style>