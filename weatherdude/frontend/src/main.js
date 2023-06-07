 import '@babel/polyfill';
import 'mutationobserver-shim';
import Vue from 'vue';
import './plugins/bootstrap-vue';
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue';
import App from './App.vue';
import router from './router';
import store from './store';
import * as VueGoogleMaps from 'vue2-google-maps';


Vue.config.productionTip = false;
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyB_fuabzNgrciXVT2PvBaZFgfnJCl3kLss'
  }
})

new Vue({
  router,
  store,
  render: (h) => h(App),
//  created: function () {
//    init();
//  },
}).$mount('#app');

export var publicSigningKey = null;

if ("serviceWorker" in navigator) {
  try {
    
    init();
  } catch (e) {
    console.error('error init(): ' + e);
  }
}

async function init() {
  var url = window.location.protocol + "//" + window.location.hostname + ":8080/pushMessage/publicSigningKey";
  fetch(url)
     .then(response => response.arrayBuffer())
     .then(key => publicSigningKey = key)
     .finally(() => console.info('Application Server Public Key fetched from the server'));

  await navigator.serviceWorker.register("/serviceWorker.js", {
    scope: "/"
  });
 
  await navigator.serviceWorker.ready;
  console.info('Service Worker has been installed and is ready');

}







