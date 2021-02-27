<template>
  <div class="Users">
    <v-container>
      <v-form ref="form">
        <v-text-field
                v-model="name"
                label="Name"
                :color="this.text_color"
                outlined
                clearable>
        </v-text-field>
      </v-form>
      <v-card width="100%" height="100%" v-if="data">
        <v-img
          height="200px"
          src="@/assets/github-universe.jpg"
        >
          <v-card-title class="white--text mt-8">
            <v-avatar size="56">
              <img
                alt="user"
                :src="data.avatar_url"
              >
            </v-avatar>
            <p class="ml-3">
              {{data.login}}
            </p>
          </v-card-title>
        </v-img>
        <v-card-title>{{this.data.name?this.data.name:this.data.login}}</v-card-title>
        <v-card-text>
          <div class="grey--text">
            Public repos: {{this.data.public_repos}}
          </div>

          <div class="grey--text">
            Followers: {{this.data.followers}}
          </div>

          <div class="my-4 subtitle-1" v-if="this.data.location">
            📍{{this.data.location}}
          </div>

          <div v-if="this.data.bio">
            💬{{this.data.bio}}
          </div>
        </v-card-text>
      </v-card>
    </v-container>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Users',
  components: {

  },
  methods: {
    search:  function () {
       console.log('search')
    }
  },
  data() {
    return {
        name: '',
        awaitingSearch: false,
        timeout: null,
        data: undefined,
        text_color: ''
    }
  },
  watch: {
    name: function () {
      this.text_color = '';
      if (this.awaitingSearch && this.timeout) {
        window.clearTimeout(this.timeout);
        this.awaitingSearch = false;
      }
      this.timeout = window.setTimeout(() => {
        if (!this.name) return;
        this.axios.get(`https://api.github.com/users/${this.name}`).then((response) => {
          this.data = response.data;
          console.log(this.data);
          this.text_color = 'success';
        }).catch((error) => {
            console.log(error) //Logs a string: Error: Request failed with status code 404
            this.text_color = 'error';
        })
        this.awaitingSearch = false;
      }, 1000); // 1 sec delay
      this.awaitingSearch = true;
    },
  }
}
</script>
