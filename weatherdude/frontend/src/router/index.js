import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import Wetterdaten from '../views/Wetterdaten.vue';
import About from '../views/About.vue';
import City from '../views/City.vue'

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/wetterdaten/:stadt',
    name: 'Wetterdaten',
    component: Wetterdaten,
 
  },
  {
      path: '/city/:stadt',
      name: 'city',
      component: City,

    }
];

const router = new VueRouter({
  routes,
});

export default router;
