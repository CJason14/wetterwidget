<template>
  <div
    aria-live="polite"
    aria-atomic="true"
    style="
        padding-left: 30px;
  padding-right: 30px;"
    class="d-flex justify-content-center align-items-center"
  >
    <b-card title="Favoriten" class="favorit" border-variant="success">
      <b-row no-gutters>
        <b-col style="text-align:center;" v-for="(o, index) in orte" :key="index">
          <b-card-body style="color: black;">
            <b-card-title
              @click="$router.push('wetterdaten/' + o.openWeatherId)"
              style="width:100%; max-width:200px; display: inline-block; cursor:pointer"
            >{{ o.stadt }}</b-card-title>
          </b-card-body>
          <b-card-img
            @click="$router.push('wetterdaten/' + o.openWeatherId)"
            :src="holeIconFuerFavoriten(o)"
            style="max-width: 100px; cursor:pointer;"
          ></b-card-img>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "FavoritenCard",
  data() {
    return {
      url:
        window.location.protocol +
        "//" +
        window.location.hostname +
        ":8081/wetterinfo/",
      fav: [],
      orte: []
    
    };
  },
 async created() {
     console.log("fsfdafasdf")
   await fetch(`${this.url}fav`)
      .then(response => response.json())
      .then(data => {
        this.fav = data;

      });

    for(var f of this.fav) {
       
        await fetch(`${this.url}ort?openWeatherId=`+f.openWeatherId)
            .then(response => response.json())
            .then(data => {
                this.orte.push(data.aktWetterinfo);

      });
     
    }
     console.log(this.orte)
  },

  methods: {

    holeIconFuerFavoriten(o) {
     
       var images = require.context(
        "../assets/wettergrafiken/",
        false,
        /\.(png|jpe?g|svg)$/
      );
      return images(
        "./" + o.aktWetterSymbol + "@2x" + ".png"
      );
   
    }
  }
};
</script>

<style lang="scss" scoped>
@import "../assets/styles/home.scss";
</style>