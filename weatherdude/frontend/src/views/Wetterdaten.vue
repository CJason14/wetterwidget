<template>
  <div>
    <div class="wetterdaten-fav-icon">
      <img :src="image" @click="switchImage()" width="30" height="30" />
    </div>
    <ForecastCard :curWetter="wetter"></ForecastCard>

    <div id="wetterdaten" class="wetterdaten-container">
      <wetterdaten-card class="wetterdaten-card" :wetter="getCurMesszeitpunktData()" />
      <slider :values="messzeitpunkte" @index-change="onHistoryIndexChanged" />
    </div>
  </div>
</template>
<script>
import WetterdatenCard from "./../components/WetterdatenCard";
import Slider from "./../components/Slider";
import ForecastCard from "./../components/ForecastCard";

export default {
  name: "wetterdaten",

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
    image: " ",
    image1: require("../assets/heart.png"),
    image2: require("../assets/filled_heart.png"),
    ortsHistorieInitialized: false,
    ortsHistorie: [],
    messzeitpunkte: [],
    curMesszeitpunkt: null
  }),

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

    await fetch(`${this.url}fav`)
      .then(response => response.json())
      .then(data => {
        this.fav = data;
      });
    this.istFavorit();
    this.loadHistorie();
  },

  components: {
    WetterdatenCard,
    Slider,
    ForecastCard
  },

  template: "<div>Wetterdaten {{ $route.params.stadt }}</div>",

  methods: {
    switchImage() {
      if (this.fav.length == 5 && this.index == 0) {
        this.$bvToast.toast("Es sind schon fünf Favoriten vorhanden", {
          title: `Error`,
          variant: "danger",
          solid: true
        });
      } else if (this.index == 0) {
        this.image = this.image2;
        this.index = 1;
        fetch(`${this.url}fav`, {
          method: "PUT",
          body: this.wetter.aktWetterinfo.openWeatherId
        });
      } else {
        this.image = this.image1;
        this.index = 0;
        fetch(
          `${this.url}fav?openWeatherId=${this.wetter.aktWetterinfo.openWeatherId}`,
          {
            method: "DELETE",
            body: this.wetter.aktWetterinfo.openWeatherId
          }
        );
        this.fav.length--;
      }
    },

    istFavorit() {
      for (var i = 0; i < this.fav.length; i++) {
        if (
          this.fav[i].openWeatherId == this.wetter.aktWetterinfo.openWeatherId
        ) {
          this.image = this.image2;
          this.index = 1;
          return;
        }
      }

      this.image = this.image1;
    },
    loadHistorie() {
      if (!this.ortsHistorieInitialized) {
        this.ortsHistorieInitialized = true;

        fetch(
          this.url +
            "historie?openWeatherId=" +
            this.wetter.aktWetterinfo.openWeatherId +
            "&page=0&size=10"
        )
          .then(response => response.json())
          .then(data => {
            this.ortsHistorie = data;
            data.forEach(d => {
              var sliderValue = this.dateToSliderDate(d.messzeitpunkt);
              this.messzeitpunkte.push(sliderValue);
            });

            this.curMesszeitpunkt = data[0];
          });
      }
    },
    onHistoryIndexChanged(index) {
      this.curMesszeitpunkt = this.ortsHistorie[index];
    },
    getCurMesszeitpunktData: function() {
      if (this.curMesszeitpunkt) {
        return {
          ort: this.wetter.aktWetterinfo.stadt,
          info: this.curMesszeitpunkt.wetterinfo,
          data: this.curMesszeitpunkt.wetterdaten,
          zeitpunkt: this.curMesszeitpunkt.messzeitpunkt
        };
      } else {
        return this.curMesszeitpunkt;
      }
    },
    dateToSliderDate(value) {
      var d = new Date(value);
      const options = { day: "numeric", month: "long", year: "numeric" };
      return d.toLocaleDateString("de-DE", options);
    }
  }
};
</script>
<style lang="scss" scoped>
@import "../assets/styles/wetterdaten.scss";
</style>