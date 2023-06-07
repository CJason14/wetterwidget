<template>
   <div>
    <h1>Test</h1>
    <h2 style="font-size:250%; text-align: center;">{{wetter.temp_max}}°C</h2>
   </div>
</template>
<script>
export default {
  name: "currenttemp",
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
  }
};
</script>

<style>
</style>