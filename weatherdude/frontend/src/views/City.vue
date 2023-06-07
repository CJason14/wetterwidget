<template>
<div class="maindiv">
    <div class="backgroundcenter">
        <h1>{{ wetter.aktWetterinfo.stadt }}</h1>
        <Date class="currentdate"></Date>
        <p class="tempinfo">{{ wetter.aktWetterinfo.temperatur_aktuell.toFixed(0) }}°</p>
        <p> {{ wetter.aktWetterinfo.aktWetterBeschreibung }} </p>
        <img class="mainimage" :src="getImgUrl(wetter.aktWetterinfo.aktWetterSymbol)" id="img" img-alt="Card image" />
    </div>
    <div class="bottomforecast">
        <Forecast></Forecast>
    </div>
</div>
</template>

<script>
import Date from "./../components/Date";
import Forecast from "./../components/Forecast";

export default{
data: () => ({
    url:
      window.location.protocol +
      "//" +
      window.location.hostname +
      ":8081/wetterinfo/",

    ortData: {},
    fav: [],
    wetter: {},
    index: 0,
    ortsHistorieInitialized: false,
    ortsHistorie: [],
    messzeitpunkte: [],
    curMesszeitpunkt: null
  }),
   methods: {
      getStadt() {
        var curWetter = this.$props.curWetter;

        if(curWetter){
          var aktWetterinfo = curWetter.aktWetterinfo;
          if(aktWetterinfo){
            return curWetter.aktWetterinfo.stadt;
          }
        }
        else {
          return "Loading";
        }
      },
      getImgUrl(f) {
        console.log(this.curWetter);
        var images = require.context(
          "../assets/wettergrafiken/",
          false,
          /\.(png|jpe?g|svg)$/
        );
        if (f == undefined) {
          return "../assets/wettergrafiken/01d@2x.png";
        }
        return images("./01d@2x.png");
      }
    },
  async created() {
    await fetch(`${this.url}ort?openWeatherId=` + this.$route.params.stadt)
      .then(response => response.json())
      .then(data => {
        this.wetter = data;
      });

    await fetch(
      `${this.url}ort?openWeatherId=` + this.wetter.aktWetterinfo.openWeatherId
    )
      .then(response => response.json())
      .then(data => {
        this.ortData = data;
      });

  },

  components: {
    Date,
    Forecast
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/styles/city.scss";
</style>
