import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    { 
      canReuse: false,
      path: '/city/:weatherid',
      name: 'city',
      component: () => import('../views/CityView.vue')
    },
    {
      path: '/edit',
      name: 'edit',
      component: () => import('../views/EditView.vue')
    },
    { 
      canReuse: false,
      path: '/historie/:weatherid',
      name: 'historie',
      component: () => import('../views/HistorieView.vue')
    }
  ]
})

export default router
