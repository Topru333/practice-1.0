<template>
  <div class="Login">
    <v-container>
      <v-form ref="form" style="maxWidth: 700px; margin: auto;" align="center">
        <v-text-field
                v-model="login"
                :label="this.code?'Please wait':'Enter your login'"
                :loading="this.code?true:false"
                :disabled="this.code?true:false"
                :rules="[rules.required, rules.min]"
                :color="this.login_color"
                class="outlined"
                outlined
                clearable
                required
        ></v-text-field>
        <v-btn class="mr-4" @click="submit">
          submit
        </v-btn>
        <v-btn @click="clear">
          clear
        </v-btn>
      </v-form>
    </v-container>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Login',
  props: ['code'],
  components: {

  },
  mounted: function() {
    if(this.code) {
      this.axios.post('http://localhost:3000/token', {
        code: this.code,
        login: localStorage.getItem('lastLogin')
      }).then(response => {
        console.log(response);
      });
      console.log(this.code);
    }
  },
  methods: {
    submit () {
      localStorage.setItem('lastLogin', this.login);
      this.axios.post('http://localhost:3000/authorize', {
        login: this.login
      }).then(response => {
        window.location = response.data.url;
      });
    },
    clear () {
      this.login = ''
      this.password = ''
    }
  },
  data() {
    return {
      login_color: '',
      password_color: '',
      login: '',
      password: '',
      show_password: false,
      rules: {
          required: value => !!value || 'Required.',
          min: v => v.length >= 8 || 'Min 8 characters',
          emailMatch: () => (`The email and password you entered don't match`),
      }
    }
  }
}
</script>
