import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import SimpleTypeahead from 'vue3-simple-typeahead';
import VueCookies from 'vue3-cookies'

const app = createApp(App)

app.use(router);
app.use(SimpleTypeahead);

app.use(VueCookies, {
    expireTimes: "30d",
    path: "/",
    domain: "",
    secure: true,
    sameSite: "None"
});


app.mount('#app')
