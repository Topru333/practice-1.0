const express = require('express')
const bodyParser = require('body-parser')
require('dotenv').config();
const axios = require('axios')
fs = require('fs');

const app = express()
const router = express.Router()
router.use(bodyParser.urlencoded({ extended: false }))
router.use(bodyParser.json())

// CORS middleware
const allowCrossDomain = function (req, res, next) {
  res.header('Access-Control-Allow-Origin', '*')
  res.header('Access-Control-Allow-Methods', '*')
  res.header('Access-Control-Allow-Headers', '*')
  next()
}

app.use(allowCrossDomain)

let users = {};

function makeState(length) {
   var result           = '';
   var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
   var charactersLength = characters.length;
   for ( var i = 0; i < length; i++ ) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
   }
   return result;
}

router.post('/authorize', (req, res) => {
  if(!users[req.body.login]) users[req.body.login] = {};
  users[req.body.login].state = makeState(8);
  axios.get('https://github.com/login/oauth/authorize', {
    params: {
      client_id	: process.env.CLIENT_ID,
      redirect_uri: 'http://localhost:8080/#/login',
      login: req.body.login,
      state: users[req.body.login].state,
      allow_signup: true

    }
  }).then((response) => {
      res.status(200).send({url: response.request.res.responseUrl });
  }).catch((error) => {
      res.status(404).send({error: error });
      console.log(error);
  })
})

router.post('/token', (req, res) => {
  if(!req.body.login||!users[req.body.login]||!req.body.code) {
    res.status(404).send({msg:'cant find user'});
    console.log('wrong user');
    return
  }
  axios.post('https://github.com/login/oauth/access_token', {
    params: {
      client_id	: process.env.CLIENT_ID,
      client_secret	: process.env.CLIENT_SECRET,
      code: req.body.code,
      redirect_uri: 'http://localhost:8080/login',
      state: users[req.body.login].state,
    }
  }).then((response) => {
    console.log(response.access_token);
    res.status(200).send({access_token: response.access_token });
      //res.status(200).send({url: response.request.res.responseUrl });
  }).catch((error) => {
      res.status(404).send({error: error });
      console.log(error);
      fs.writeFile('lasterror.txt', JSON.stringify(error), function (err) {
        if (err) return console.log(err);
      });
      //console.log(error);
  })
})

app.use(router)

let port = process.env.PORT || 3000

let server = app.listen(port, function () {
  console.log('Express server listening on port ' + port)
})
