<template>
  <main class="aligncenter">
    <popup v-if="this.popupdata" v-bind:info="this.popupdata" />
    <div class="aligncenter">
      <h1 class="headline">Bearbeiten</h1>
      <RouterLink class="link" :to="{name: 'home'}">
        <p class="fontmain">Zurück</p>
      </RouterLink>
      <div>
        <h3 class="fontmain">Hinzufügen</h3>
      </div>
      <div>
        <vue3-simple-typeahead id="typeahead_id" placeholder="Suche nach Stadt..." :items="this.cities"
          :minInputLength="1" v-on:selectItem="checkcity" v-on:keyup.enter="checkcity" v-model="search"
          class="searchbar fontmain">
        </vue3-simple-typeahead>

      </div>
      <br>
      <br>

      <div v-if="answer.name" class="PreviewTemp">
        <h3 class="fontmain">{{ answer.name }}</h3>
        <select class="fontmain searchbar" v-model="componentselect">
          <option value="Luft">Luft</option>
          <option value="Stadt">Stadt</option>
          <option value="cityweather">Stadt - Wetter</option>
          <option value="Temperatur">Temperatur</option>
          <option value="Wind">Wind</option>
        </select>
      </div>
      <button v-on:click="setcity" class="button">Speichern</button>
      <br>
      <br>
    </div>

    <div>
      <div>
        <h3 class="fontmain">Entfernen</h3>
      </div>
      <p v-for="response in responses" class="fontmain PreviewTemp">
        {{ response.stadt }}
        <br>
        {{ response.widgetnumber }}
        <br>
        <button
          v-on:click="deletecity(response.widgetnumber, response.openweatherid_stadt, response.cookieid, response.count)"
          class="button">Löschen</button>
      </p>
    </div>
  </main>
</template>

<script setup>
  import city from './../components/city.vue'
  import popup from './../components/popup.vue'
  import 'vue3-simple-typeahead/dist/vue3-simple-typeahead.css'; //Optional default CSS
</script>

<script>
  var pathArray = window.location.pathname.split('/');
  import {
    v4 as uuidv4
  } from "uuid";
  import axios from 'axios'

  export default {
    name: "edit",

    data: () => ({
      url: window.location.protocol +
        "//" +
        window.location.hostname +
        ":8081/wetterdata/checkcity",
      answer: {},
      search: "",
      cities: {},
      responses: {},
      searchcity: "",
      popupdata: ""
    }),

    methods: {
      checkcity: function (item) {
        if (typeof item === "string"){
          this.search = item
        }
        console.log(this.search)
        this.searchcity = this.search;
        axios
          .put(this.url, {
            stadt: this.search
          })
          .then(response => {
            (this.answer = response.data)
          })
        
      },
      setcity: function (event) {
        if (this.componentselect){
        if (this.$cookies.get("USERUUID")) {} else {
          this.$cookies.set("USERUUID", uuidv4(), "100000d")
        }
        axios
          .post(window.location.protocol +
            "//" +
            window.location.hostname +
            ":8081/wetterdata/setfavorit", {
              useruuid: this.$cookies.get("USERUUID"),
              city: this.searchcity,
              component: this.componentselect
            })
          .then(response => {
            document.location.href="/?popupdata=Erfolgreich erstellt!";
          })
        }
        else{
          alert("Kein Component ausgewählt!")
        }
      
      },
      deletecity: function (component, stadt, cookieid, count) {
        axios
          .put(window.location.protocol +
            "//" +
            window.location.hostname +
            ":8081/wetterdata/deletefavorit", {
              cookieid: cookieid,
              widgetnumber: component,
              openweatheridstadt: stadt,
              count: count
            })
          .then(responses => {
            (this.responses = responses.data)
            document.location.href="/edit?popupdata=Erfolgreich gelöscht!";
          })
      },
    },
    

    async created() {
      await axios
        .get(this.url)
        .then(response => {
          (this.cities = response.data)
        })
      let urlParams = new URLSearchParams(window.location.search);
      this.popupdata = urlParams.get('popupdata');
      axios
        .put(window.location.protocol +
          "//" +
          window.location.hostname +
          ":8081/wetterdata/getfavorit", {
            useruuid: this.$cookies.get("USERUUID"),
          })
        .then(responses => {
          (this.responses = responses.data)
        })
    }
  }
</script>

<style>
  .link {
    margin-top: 10px;
    padding: 10px;
    border-radius: 10px;
  }

  .link p {
    margin: 0;
  }

  .searchbar {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
  }

  #typeahead_id_wrapper div {

    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    width: 100%;
    font-family: 'Geologica', sans-serif;
    color: #84A4FC;
  }

  #typeahead_id_wrapper div.simple-typeahead-list{
    overflow: hidden;
        
  }
</style>