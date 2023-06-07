<template>
  <div v-if="dataLoaded" style="padding-right:30px; padding-left:30px; width:100%;">
    <b-card
      no-body
      class="overflow-hidden gmap"
      style=" max-width:1000px; width:100%; background-color: #9ae6b7"
      border-variant="success"
    >
      
        <div class="btn-group" role="group" style="width:50%;" id="btnBar">
          <button type="button" class="btn btn-light" @click="layer('clouds')">Wolken</button>
          <button stype="button" class="btn btn-light" @click="layer('temp')">Temperatur</button>
          <button type="button" class="btn btn-light" @click="layer('precipitation')">Niederschlag</button>
          <button type="button" class="btn btn-light" @click="layer('wind')">Wind Geschwindigkeit</button>
        </div>
     
      <b-row no-gutters>
        <b-col md="6">
          <GmapMap
            ref="mapRef"
            style="height:650px;"
            :center="{ lat: 51.8, lng: 10.5 }"
            :options="{
          styles: mapStyle,
          zoom: 6,
          gestureHandling: 'none',
          zoomControl: false,
          mapTypeControl: false,
          scaleControl: false,
          streetViewControl: false,
          fullscreenControl: false,
          disableDefaultUi: true,
        
        }"
          >
            <GmapMarker
              v-for="(m, index) in markers"
              :key="index"
              :position="getPosition(m)"
              :clickable="true"
              :draggable="false"
              :icon="{
            url: require('../assets/wettergrafiken/' +
              m.wetterinfo.aktWetterinfo.aktWetterSymbol +
              '@2x.png'),
          }"
              @click="openInfoWindowTemplate(m)"
            />
            <gmap-info-window
              :options="{
            maxWidth: 300,
            pixelOffset: {
              width: 0,
              height: -50,
            },
          }"
              :position="infoWindow.position"
              :opened="infoWindow.open"
              @closeclick="infoWindow.open = false"
            >
              <div
                @click="$router.push('wetterdaten/' + currentMarker.openWeatherId)"
                v-html="infoWindow.template"
              ></div>
            </gmap-info-window>
          </GmapMap>
        </b-col>

        <b-col md="6">
          <b-card-body>
            <b-card-text>
              <ul class="list-group list-group-flush" style="width:100%">
                <li
                  @click="$router.push('wetterdaten/' + m.openWeatherId)"
                  id="deList"
                  class="list-group-item list-group-item-action list-group-item-success"
                  v-for="(m, index) in markersList"
                  :key="index"
                >
                  Wetter {{m.name}}
                  <img :src="holeIconFuerMarker(m)" style="width:15%" />
                  {{m.wetterinfo.aktWetterinfo.temperatur_max}}°C/
                  <small>{{m.wetterinfo.aktWetterinfo.temperatur_min}}°C</small>
                </li>
              </ul>
            </b-card-text>
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>

<script>
import { gmapApi } from "vue2-google-maps";
export default {
  name: "WeatherMap",
  computed: {
    google: gmapApi
  },
  data() {
    return {
      currentMarker: null,
      dataLoaded: false,
      url:
        window.location.protocol +
        "//" +
        window.location.hostname +
        ":8081/wetterinfo/",
      mapStyle: require("../assets/map_style.JSON"),
      markers: require("../assets/markers.JSON"),
      markersList: require("../assets/markersList.JSON"),
      infoWindow: {
        position: { lat: 0, lng: 0 },
        open: false,
        template: ""
      },
      overlay: null
    };
  },

  async created() {
    for (var i = 0; i < this.markers.length; i++) {
      await fetch(
        `${this.url}ort?openWeatherId=${this.markers[i].openWeatherId}`
      )
        .then(response => response.json())
        .then(data => {
          this.markers[i].wetterinfo = data;
        });
    }

    for (var j = 0; j < this.markersList.length; j++) {
      await fetch(
        `${this.url}ort?openWeatherId=${this.markersList[j].openWeatherId}`
      )
        .then(response => response.json())
        .then(data => {
          this.markersList[j].wetterinfo = data;
        });
    }
    this.dataLoaded = true;
  },
  methods: {
    openInfoWindowTemplate(marker) {
      this.currentMarker = marker;

      this.infoWindow.position = this.getPosition(marker);
      this.infoWindow.open = true;
      this.infoWindow.template =
        "<h6>" +
        marker.name +
        "</h6> <b>" +
        marker.wetterinfo.aktWetterinfo.aktWetterBeschreibung +
        "</b>";
    },

    layer(type) {
      if (this.overlay != null) {
        this.overlay.setMap(null);
      }
      this.$refs.mapRef.$mapPromise.then(map => {
        let bounds = map.getBounds();
        let image =
          "https://tile.openweathermap.org/map/" +
          type +
          "_new/6/33/21.png?appid=e1e5544a2e8795ae9fcbd9cb6d1c0d10";
        this.overlay = new this.google.maps.GroundOverlay(image, bounds);

        this.overlay.setMap(map);
      });
    },

    holeIconFuerMarker(m) {
      var images = require.context(
        "../assets/wettergrafiken/",
        false,
        /\.(png|jpe?g|svg)$/
      );
      return images(
        "./" + m.wetterinfo.aktWetterinfo.aktWetterSymbol + "@2x" + ".png"
      );
    },
    getPosition: function(marker) {
      return {
        lat: parseFloat(marker.lat),
        lng: parseFloat(marker.lng)
      };
    }
  }
};
</script>
<style lang="scss" scoped>
@import "../assets/styles/home.scss";
</style>