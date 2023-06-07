<template>
  <b-card id="min">
    <b-row no-gutters>
      <b-col md="1">
        <img :src="getImgUrl(wetter)" id="img" img-alt="Card image" />
      </b-col>
      <b-col md="1">
        <h2 style="font-size:250%; text-align: center;">{{wetter.temp_max}}°C</h2>
      </b-col>
      <b-col md="7">
        <div id="details">
          Niederschlag: {{wetter.rain}}
          <br />
          Luftfeuchte: {{wetter.humidity}}%
          <br />
          Wind: {{wetter.wind_speed}}km/h
        </div>
      </b-col>
      <b-col md="3">
        <h2 id="beschreibung" style=" bottom:10%;">{{getStadt()}}</h2>
        <h4 id="beschreibung" style="bottom:20%;">
          <small class="text-muted">{{wetter.messzeitpunkt | dateToTitleDate}}</small>
        </h4>
        <h4 id="beschreibung" style="bottom:30%;">
          <small class="text-muted">{{wetter.beschreibung}}</small>
        </h4>
      </b-col>
    </b-row>
  </b-card>
</template>
<script>
export default {
  name: "wetterdaten-card-min",
  data() {
    return {};
  },
  props: {
    wetter: {
      required: true
    },
    curWetter: {
      required: true
    }
  },
  filters: {
    dateToTitleDate(value) {
      var d = new Date(value);
      const options = { weekday: "long" };
      return d.toLocaleDateString("de-DE", options);
    }
  },
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
      if (!f) {
        return "../assets/wettergrafiken/01d@2x.png";
      }
      return images("./" + f.symbol + "@2x.png");
    }
  }
};
</script>

<style>
.wetterdaten-card-min-row {
  margin-left: 50px;
  margin-bottom: 20px;
}

#details {
  line-height: 130%;
  position: relative;
  top: 3%;
  left: 2%;
  font-size: 90%;
}
#img {
  position: relative;
  bottom: 20%;
}

#beschreibung {
  text-align: right;
  position: relative;
}

#min{
  width: 100%;
  background-color: #9ae6b7;
  border:none;
}
</style>