<template>
  <div class="home" @click="showItemList = false">
    <div class="option">
      <b-icon
        icon="gear-fill"
        class="h3 einstellungen"
        @click="zeigeEinstellungen = !zeigeEinstellungen"
      ></b-icon>
      <options v-if="zeigeEinstellungen"></options>
    </div>

    <form class="search-box" @submit.prevent="ueberpruefeListe()">
      <b-icon
        icon="geo-alt"
        class="search-geo"
        @click="geoFind"
      ></b-icon>
      <input
        autocomplete="on"
        type="search"
        class="search-field"
        placeholder="Suche einen Ort"
        v-model="eingabeOrt"
        required
        tabindex="0"
        @input="ortsvorschlaegeOnTyping"
        @keydown.down="onArrowDown"
        @keydown.up="onArrowUp"
        @keydown.enter="onEnter"
      />

      <div class="list-container">
        <ul ref="scrollContainer" role="menu" class="ul vorschlaege-item" v-if="showItemList">
          <li
            ref="options"
            class="list-group-item list-group-item-action list-item"
            v-for="(o, index) in ortsvorschlaege"
            :class="{ 'is-active': index === arrowCounter }"
            :key="index"
            @click="stadtListeOnClick(o.openWeatherId, o.stadt)"
          >{{ o.stadt }}</li>
        </ul>
      </div>
    </form>

    <div
      aria-live="polite"
      aria-atomic="true"
      style="
        padding-left: 30px;
  padding-right: 30px;"
      class="d-flex justify-content-center align-items-center"
    >
      <b-card
        v-if="zeigeInfo"
        id="info-card"
        border-variant="danger"
        header="Primary"
        header-bg-variant="danger"
        header-text-variant="white"
        bg-variant
        class="mx-auto"
        style="
        margin-top:2rem;
        width: 100%;
        max-width: 600px;"
        header-tag="header"
      >
        <template #header>
          <b-iconstack font-scale="2">
            <b-icon stacked icon="exclamation-circle" scale="0.80"></b-icon>
          </b-iconstack>
          <h4 class="d-inline">Eingabefehler</h4>
        </template>
        <b-card-text>
          {{fehlertext}}
         
        </b-card-text>
      </b-card>
    </div>

    <div
      aria-live="polite"
      aria-atomic="true"
      style="
        padding-left: 30px;
        padding-right: 30px;"
      class="d-flex justify-content-center align-items-center"
    >
      <b-card
        v-if="zeigeWetterInfo"
        @click="$router.push('wetterdaten/' + wetter.aktWetterinfo.openWeatherId)"
        no-body
        text-variant="white"
        class="mx-auto mt-5"
        style="background-color:#313131;
  width: 100%; max-width: 600px; cursor: pointer"
      >
        <b-row no-gutters v-if="zeigeWetterInfo">
          <b-col>
            <b-card-body class="text-center">
              <h4 class="card-title">{{ wetter.aktWetterinfo.stadt }}</h4>
              <b-card-text>
                {{
                wetter.aktWetterinfo.aktWetterBeschreibung
                }}
              </b-card-text>
            </b-card-body>
          </b-col>
          <b-col class="d-flex justify-content-end">
            <b-card-img :src="getImgUrl(wetter)" style="max-width: 40%;"></b-card-img>
          </b-col>
        </b-row>
      </b-card>
    </div>

    <FavoritenCard></FavoritenCard>

    <WeatherMap></WeatherMap>
  </div>
</template>

<script>
import Options from "../components/Options.vue";
import WeatherMap from "../components/WeatherMap.vue";
import FavoritenCard from "../components/FavoritenCard.vue";

export default {
  name: "Home",
  data: () => ({
    
    url:
      window.location.protocol +
      "//" +
      window.location.hostname +
      ":8081/wetterinfo/",

    eingabeOrt: "",
    eingabeId:"",

    wetter: {},
    ortsvorschlaege: {},

    zeigeTreffer: false,
    zeigeInfo: false,
    zeigeWetterInfo: false,
    zeigeEinstellungen: false,
    showItemList: false,

    fehlertext: "",  
    auswahlObjekt: "",

    timer: null,
    focus: null,
   
    arrowCounter: -1
  }),

  components: {
    Options,
    WeatherMap,
    FavoritenCard
  },
  

  methods: {

    geoFind() {
      if(!navigator || !navigator.geolocation) {
        console.error("No navigator found!");
        return;
      }
      
      let app = this;

      navigator.geolocation.getCurrentPosition(function(position) {
        let lat = position.coords.latitude;
        let lon = position.coords.longitude;
        
        console.log("Lat: " + lat + ", Lon: " + lon);

        fetch(`${app.url}find?lat=${lat}&lon=${lon}`, {
          headers : { 
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          }
        })
            .then(response => response.json())
            .then(data => {
              console.log(data);
              if(data.errorMessage && data.errorMessage != ''){
                console.error("ERROR: " + data.errorMessage);
              }
              else {
                app.eingabeId = data.openWeatherId;
                app.holWetterInfoOhneCheck();
              }
            });
      });
    },

    /**
     * Wird aufgerufen, wenn ein Ort aus der Liste ausgewählt wird
     */
    stadtListeOnClick(id, stadt) {
      this.eingabeOrt = stadt;
      this.eingabeId = id;
      this.showItemList = false;
      this.holWetterinfo();
    },

  /**
   * Wird aufgerufen, wenn eine Suche per enter ausgeführt wird
   */
  ueberpruefeListe() {
  
    if(this.ortsvorschlaege.length == 1) {
      this.eingabeOrt = this.ortsvorschlaege[0].stadt;
      this.eingabeId = this.ortsvorschlaege[0].openWeatherId;
      this.holWetterinfo();
    }
    else if(this.ortsvorschlaege.length == 0) {
          this.zeigeWetterInfo = false;
          this.zeigeInfo = true;
          this.fehlertext = "Der eingegebene Ort wurde nicht gefunden."
    }
    else if(this.ortsvorschlaege.length > 1) {
        this.zeigeWetterInfo = false;
          this.zeigeInfo = true;
          this.showItemList = false;
          this.fehlertext = "Bitte spezifizieren Sie Ihre Suche."
    }
   
  },

  /**
   * Lädt die möglichen Orte der Suche während des Tippens
   */
    ortsvorschlaegeOnTyping() {
      this.arrowCounter = -1;
      if (this.timer) {
        window.clearTimeout(this.timer);
      }
      if (this.eingabeOrt.length > 0) {
        
        this.timer = window.setTimeout(() => {
         fetch(`${this.url}vorschlaege_${this.eingabeOrt}`)
            .then(response => response.json())
            .then(data => {
              this.ortsvorschlaege = data;
              this.showItemList = true;
            });
        }, 500);
      } else {
        this.ortsvorschlaege = null;
      }
    },

    /**
     * Methoden zur Ortsauswahl mit Tastatur
     */
    onArrowDown(ev) {
      ev.preventDefault();
      if (
        this.arrowCounter < this.ortsvorschlaege.length - 1 &&
        this.showItemList == true
      ) {
        this.arrowCounter = this.arrowCounter + 1;
        this.fixScrolling();
      }
    },
    onArrowUp(ev) {
      ev.preventDefault();
      if (this.arrowCounter > 0 && this.showItemList == true) {
        this.arrowCounter = this.arrowCounter - 1;
        this.fixScrolling();
      }
    },
    onEnter() {
      if(this.arrowCounter >= 0) {
      this.eingabeOrt = this.ortsvorschlaege[this.arrowCounter].stadt;
      this.eingabeId = this.ortsvorschlaege[this.arrowCounter].openWeatherId;
      this.holWetterinfo();
      this.arrowCounter = -1;
      }
    },
    fixScrolling() {
      const liH = this.$refs.options[this.arrowCounter].clientHeight;
      this.$refs.scrollContainer.scrollTop = liH * this.arrowCounter;
    },

    /**
     * Holt die Wetterinfo zu einem Ort via eingabeId
     */
   async holWetterinfo() {
      if (this.eingabeOrt != "") {
        await fetch(`${this.url}ort?openWeatherId=${this.eingabeId}`)
          .then(response => response.json())
          .then(data => {
            this.wetter = data;
            this.zeigeWetterInfo = true;
            this.zeigeInfo = false;   
            this.showItemList = false;
          });
          
      }
    },

    holWetterInfoOhneCheck() {
      fetch(`${this.url}ort?openWeatherId=${this.eingabeId}`)
          .then(response => response.json())
          .then(data => {
            this.wetter = data;
            this.zeigeWetterInfo = true;
            this.zeigeInfo = false;   
            this.showItemList = false;
          });
    },

    getImgUrl(wetter) {
      var images = require.context(
        "../assets/wettergrafiken/",
        false,
        /\.(png|jpe?g|svg)$/
      );

      if (!wetter.aktWetterinfo) {
        return "../assets/wetterafiken/01d@2x.png";
      }

      return images("./" + wetter.aktWetterinfo.aktWetterSymbol + "@2x.png");
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../assets/styles/home.scss";
</style>
