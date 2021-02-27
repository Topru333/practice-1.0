module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],
  devServer: {
    proxy: 'https://api.github.com',
  }
}
