import Vue from 'vue'
import Vuex from 'vuex'
require('dotenv').config();

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    '$id': process.env.VUE_APP_GITHUB_CLIENT_ID,
    '$client_secret': process.env.VUE_APP_GITHUB_CLIENT_SECRET
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
