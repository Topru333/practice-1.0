const app = require('express')();
const http = require('http').Server(app);
const io = require('socket.io')(http);
let users = [];
const messages = [];

function addMessage(data) {
  messages.push(data);
  if (messages.length > 5) {
    messages.shift();
  }
}

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html')
});


http.listen(3000, () => {
    console.log('Listening on port *: 3000');
});

io.on('connection', (socket) => {

    socket.emit('connections', Object.keys(io.sockets.connected).length);
    socket.emit('users', users);
    socket.emit('messages', messages);

    socket.on('chat-message', (data) => {
      addMessage(data);
      socket.broadcast.emit('chat-message', (data));
    });

    socket.on('typing', (data) => {
      socket.broadcast.emit('typing', (data));
    });

    socket.on('stopTyping', () => {
        socket.broadcast.emit('stopTyping');
    });

    socket.on('joined', (data) => {
      users.push({name: data});
      socket.emit('users', users);
      socket.broadcast.emit('joined', (data));
    });

    socket.on('leave', (data) => {
      users =  users.filter(function( obj ) {
        return obj.name !== data;
      });
      socket.emit('users', users);
      socket.broadcast.emit('leave', (data));
      console.log("A user left the chat: " + data);
    });

});
