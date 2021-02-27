<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >

      <v-app-bar-nav-icon @click="drawer=true"></v-app-bar-nav-icon>

      <v-toolbar-title>Github test</v-toolbar-title>

      <v-spacer></v-spacer>

    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
    >
      <v-list
        nav
        dense
      >
        <v-list-item-group
          v-model="group"
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item v-for="link in links" :key="link.name" :to="link.to">
            <v-list-item-icon>
              <v-icon>{{link.icon}}</v-icon>
            </v-list-item-icon>
            <v-list-item-title>{{link.name}}</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </v-main>
  </v-app>
</template>

<script>


export default {
  name: 'App',

  components: {
  },
  methods: {
     greet: async function (event) {
       // `this` inside methods points to the Vue instance
       // `event` is the native DOM event
       if (event) {
         //alert(this.$store.state.$id);
       }
       this.axios.get('https://api.github.com/users/defunkt').then((response) => {
         console.log(response.data)
       })
    }
  },
  data() {
    return {
      drawer: false,
      group: null,
      links: [
        {name:'Home',icon:'mdi-home',to:'/'},
        {name:'Users',icon:'mdi-account',to:'users'},
        {name:'Login',icon:'mdi-account',to:'login'}
      ]
    }
  }
};
</script>
