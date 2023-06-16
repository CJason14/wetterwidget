
  <template>
    <main class="aligncenter">
      <a class="headlinedesign" v-bind:href="'/city/' +  standort.openWeatherId ">
        <h1 class="headline" id="aktuellerstandort">
          {{ standort.stadt }}
        </h1>
        <p class="fontmain">AktuellerStandort</p>
      </a>
      <br>
      <h3 class="fontmain">Favoriten</h3>
      <RouterLink class="fontmain link unterheadline" :to="{name:'edit'}">
        <p>Bearbeiten</p>
      </RouterLink>
      <div v-for="response in responses">
        <temperatur v-if="response.widgetnumber == 'Temperatur'" v-bind:weatherid="response.openweatherid_stadt" />
        <city v-if="response.widgetnumber == 'Stadt'" v-bind:weatherid="response.openweatherid_stadt"/>
        <wind v-if="response.widgetnumber == 'Wind'" v-bind:weatherid="response.openweatherid_stadt"/>
        <cityweather v-if="response.widgetnumber == 'cityweather'" v-bind:weatherid="response.openweatherid_stadt"/>
        <air  v-if="response.widgetnumber == 'Luft'" v-bind:weatherid="response.openweatherid_stadt"/>

      </div>
      <popup v-if="this.popupdata" v-bind:info="this.popupdata" />
    </main>
  </template>

<script setup>
import city from './../components/city.vue'
import temperatur from './../components/temperatur.vue'
import wind from './../components/wind.vue'
import cityweather from './../components/cityweather.vue'
import air from './../components/air.vue'
import popup from './../components/popup.vue'
import axios from 'axios'
</script>
<script>
export default {
  name: "city",


  data: () => ({
    url: window.location.protocol +
      "//" +
      window.location.hostname +
      ":8081/wetterdata/wetterdaten/",
    standort: "Nicht Verfügbar",
    responses: {},
    popupdata: ""
  }),

  created() {
    let urlParams = new URLSearchParams(window.location.search);
    this.popupdata = urlParams.get('popupdata');
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          };
          axios
            .put(window.location.protocol +
              "//" +
              window.location.hostname +
              ":8081/wetterdata/findcity", {
                lat: pos.lat,
                lon: pos.lng
              }
            )
            .then(response => {
              (this.standort = response.data)
            })
        },
        () => {

        }
      );
    } 
    if (this.$cookies.get("USERUUID")){
      axios
      .put(window.location.protocol +
            "//" +
            window.location.hostname +
            ":8081/wetterdata/getfavorit", {
            useruuid : this.$cookies.get("USERUUID"),
      })
      .then(responses => {
            (this.responses = responses.data)  

      })
    }
  }
} 
</script>
  <style>
  
  .aligncenter {
  display: flex;
  flex-direction: column;
  align-items: center;
  }
  .headlinedesign{
    padding: 15px;
    padding-bottom: 2px;
    border-radius: 10px;
  }
  .link {
    padding: 10px;
    border-radius: 10px;
  }

  .link p {
    margin: 0;
  }

  .unterheadline {
    margin-top: -20px;
  }
  </style>