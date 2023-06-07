<template>
  <b-card no-body border-variant="success" id="forecastCard">
    <b-row no-gutters>
      <WetterdatenCardMin :wetter="selectedDay" :curWetter="curWetter"></WetterdatenCardMin>
    </b-row>
    <b-row style="text-align:center;">
      <b-col md="3">
        <h1 id="tageszeit" class="display-6">Morgens</h1>
        <br />
        <div id="tageszeitTemp">
          Temperatur: {{selectedDay.temp_morn}}°
          <br />
          Gefühlt: {{selectedDay.feels_like_morn}}°
        </div>
      </b-col>
      <b-col md="3">
        <h1 id="tageszeit" class="display-6">Mittags</h1>
        <br />
        <div id="tageszeitTemp" class="border-left border-success">
          Temperatur: {{selectedDay.temp_day}}°
          <br />
          Gefühlt: {{selectedDay.feels_like_day}}°
        </div>
      </b-col>
      <b-col md="3">
        <h1 id="tageszeit" class="display-6">Abends</h1>
        <br />
        <div id="tageszeitTemp" class="border-left border-success">
          Temperatur: {{selectedDay.temp_eve}}°
          <br />
          Gefühlt: {{selectedDay.feels_like_eve}}°
        </div>
      </b-col>
      <b-col md="3">
        <h1 id="tageszeit" class="display-6">Nachts</h1>
        <br />
        <div id="tageszeitTemp" class="border-left border-success">
          Temperatur: {{selectedDay.temp_night}}°
          <br />
          Gefühlt: {{selectedDay.feels_like_night}}°
        </div>
      </b-col>
    </b-row>
    <b-row no-gutters>
      <b-list-group horizontal="xl" id="forecastList">
        <b-list-group-item
          v-bind:class="{'active' : isSelected(index)}"
          v-on:click="selected=index"
          id="forecastListItem"
          class="list-group-item list-group-item-action"
          v-for="(f, index) in forecast"
          :key="index"
          @click="changeDay(f)"
        >
          <div class="flex-column" id="txt">
            <h5>{{f.messzeitpunkt | dateToTitleDate}}</h5>
          </div>
          <div class="flex-column">
            <img :src="getImgUrl(f)" img-alt="Card image" id="img" />
          </div>
          <div id="txt">{{f.temp_max}}°/{{f.temp_min}}°</div>
        </b-list-group-item>
      </b-list-group>
    </b-row>
  </b-card>
</template>

<script>
import WetterdatenCardMin from "./../components/WetterdatenCardMin";
export default {
  name: "forecastCard",
  data: () => ({
    url:
      window.location.protocol +
      "//" +
      window.location.hostname +
      ":8081/wetterinfo/",
    forecast: [],
    selectedDay:"",
    selected: "",
    name: ""
  }),

  components: {
    WetterdatenCardMin
  },
  async created() {
    await fetch(
      `${this.url}forecast?openWeatherId=` +
        this.$route.params.stadt +
        `&page=0&size=8`
    )
      .then(response => response.json())
      .then(data => {
        this.forecast = data;
      });
    this.selectedDay = this.forecast[0];
  },
  props: {
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
    changeDay(f) {
      this.selectedDay = f;
    },

    isSelected(i) {
      return i === this.selected;
    },
    getImgUrl(f) {
      console.log(f);
      var images = require.context(
        "../assets/wettergrafiken/",
        false,
        /\.(png|jpe?g|svg)$/
      );
      if (!f) {
        return "../assets/wetterafiken/01d@2x.png";
      }
      return images("./" + f.symbol + "@2x.png");
    }
  }
};
</script>
<style lang="scss" scoped>
#zeit {
  margin: auto;
  max-width: 100%;
}
#forecastList {
  background-color: #9ae6b7;
  width: auto;
  max-width: 100%;

  margin: auto;
  line-height: 20%;
}
#forecastListItem {
  background-color: #9ae6b7;
}

#tageszeit {
  position: relative;
  font-size: 2.2vw;
  font-weight: bold;
  bottom: 15%;
}

.active {
  color: black;
  border-color: green;
}

#tageszeitTemp {
  position: relative;
  bottom: 25%;
  font-size: 2vw;
  line-height: 250%;
}

#forecastCard {
  margin-top: 5%;
  margin-left: 10%;
  margin-right: 10%;
  background-color: #9ae6b7;
}

#img {
  margin-left: 5%;
  width: 70%;
  margin: auto;
  display: block;
}

#txt {
  text-align: center;
}

</style>