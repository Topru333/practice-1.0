import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/users',
    name: 'Users',
    component: () => import( '../views/Users.vue')
  },
  {
    path: '/login',
    name: 'LoginCode',
    props: route => ({ code: route.query.code }),
    component: () => import( '../views/Login.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import( '../views/Login.vue')
  }
]

const router = new VueRouter({
  base: process.env.BASE_URL,
  routes
})

export default router
