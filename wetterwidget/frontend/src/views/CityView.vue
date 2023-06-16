<template>
  <main class="aligncenter">
    <h1 class="headline">{{ wetter.stadt }}</h1>
    <RouterLink class="link" :to="{name: 'home'}">
      <p class="fontmain">Zurück</p>
    </RouterLink>
    <div class="container">
      <div class="PreviewCity"><iframe class="RainView"
          style="width: 100%; border: none; height: 100%; background-color: transparent; border-radius: 10px;"
          v-bind:src="'https://www.wetter.de/widget/rain/#m=7/' + wetter.lat + '/' + wetter.lon + '/'"></iframe></div>
      <div class="PreviewCity"><iframe class="WindView" width="100%" height="100%"
          v-bind:src="'https://embed.windy.com/embed2.html?lat=' + wetter.lat + '&lon=' + wetter.lon + '&detailLat=' + wetter.lat + '&detailLon=' + wetter.lon + '&width=650&height=450&zoom=4&level=surface&overlay=wind&product=ecmwf&menu=&message=&marker=&calendar=now&pressure=&type=map&location=coordinates&detail=&metricWind=default&metricTemp=default&radarRange=-1'"
          frameborder="0"></iframe>
      </div>
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
      <div class="PreviewCity">
        <canvas id="line-chart" width="800" height="450"></canvas>
      </div>
      <div class="PreviewCity">
        <canvas id="line-chart-hum" width="800" height="450"></canvas>
      </div>
    </div>
  </main>
</template>

<script>
  var pathArray = window.location.pathname.split('/');
  import Chart from 'chart.js/auto';
  import axios from 'axios'
  export default {

    name: "city",


    data: () => ({
      url: window.location.protocol +
        "//" +
        window.location.hostname +
        ":8081/wetterdata/wetterdaten/" + pathArray[2],
      wetter: {},
      forecast: {}
    }),

    async mounted() {
      await axios
        .get(this.url)
        .then(response => {
          (this.wetter = response.data)
        })
      await axios
        .get(window.location.protocol +
          "//" +
          window.location.hostname +
          ":8081/wetterdata/forecast/" + pathArray[2])
        .then(response => {
          (this.forecast = response.data)
          const heute = new Date();
          let stunde = heute.getHours()
          new Chart(document.getElementById("line-chart"), {
              type: 'line',
              data: {
                labels: [stunde % 24 + " Uhr", (stunde + 3) % 24 + " Uhr", (stunde + 6) % 24 + " Uhr", (stunde + 9) % 24 + " Uhr", (stunde + 12) % 24 + " Uhr",
                  (stunde + 15) % 24 + " Uhr", (stunde + 18) % 24 + " Uhr"
                ],
                datasets: [{
                  data: response.data[0],
                  label: "Temperatur",
                  borderColor: "#3e95cd",
                  fill: false
                }, {
                  data: response.data[2],
                  label: "Min",
                  borderColor: "#008800",
                  fill: false
                }, {
                  data: response.data[3],
                  label: "Max",
                  borderColor: "Red",
                  fill: false
                }]
              },
              options: {
                title: {
                  display: true,
                  text: 'Temperatur'
                },
                scales: {
                  y: {
                    display: true,
                    ticks: {
                      callback: function (value, index, values) {
                        return value + "°";
                      }
                    },

                  },
                  x: {
                    ticks: {
                      callback: function (val, index) {
                        return index % 2 == 0 ? this.getLabelForValue(val) : '';
                      },
                      maxRotation: 0,
                    }
                  }
                }
              }
            }),
            new Chart(document.getElementById("line-chart-hum"), {
              type: 'line',
              data: {
                labels: [stunde % 24 + " Uhr", (stunde + 3) % 24 + " Uhr", (stunde + 6) % 24 + " Uhr", (stunde + 9) % 24 + " Uhr", (stunde + 12) % 24 + " Uhr",
                  (stunde + 15) % 24 + " Uhr", (stunde + 18) % 24 + " Uhr"
                ],
                datasets: [{
                  data: response.data[1],
                  label: "Feuchtigkeit",
                  borderColor: "red",
                  fill: false
                },{
                  data: response.data[4],
                  label: "Bewölkung",
                  borderColor: "#3e95cd",
                  fill: false
                }
              ]
              },
              options: {
                title: {
                  display: true,
                  text: 'Feuchtigkeit'
                },
                scales: {
                  y: {
                    display: true,
                    ticks: {
                      callback: function (value, index, values) {
                        return value + "%";
                      }
                    },

                  },
                  x: {
                    ticks: {
                      callback: function (val, index) {
                        return index % 2 == 0 ? this.getLabelForValue(val) : '';
                      },
                      maxRotation: 0,
                    }
                  }
                }
              }
            })
        })


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