import json
import socket
from threading import Thread

from flask import Flask, send_from_directory, request
from flask_socketio import SocketIO

import eventlet

eventlet.monkey_patch()

app = Flask(__name__)
server = SocketIO(app)

scala_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
scala_socket.connect(('localhost', 6000))

delimiter = "~"

def scala_listener(the_socket):
    buffer = ""
    while True:
        buffer += the_socket.recv(1000).decode()
        while delimiter in buffer:
            message = buffer[:buffer.find(delimiter)]
            buffer = buffer[buffer.find(delimiter) + 1:]
            get_from_scala(message)

def get_from_scala(data):
    server.emit('gameState', data, broadcast=True)


def send_to_scala(data):
    scala_socket.sendall((json.dumps(data) + delimiter).encode())


Thread(target=scala_listener, args=(scala_socket,)).start()

@server.on('connect')
def got_message():
    print(request.id + " connected")
    message = {"username": request.id, "action": "connected"}
    send_to_scala(message)

@server.on('disconnect')
def disconnect():
    print(request.id + " disconnected")
    message = {"username": request.id, "action": "disconnected"}
    send_to_scala(message)

@server.on('keys')
def key_state(jsonKeyStates):
    key_states = json.loads(jsonKeyStates)
    x = 0.0
    if key_states["a"] and not key_states["d"]:
        x = -1.0
    elif not key_states["a"] and key_states["d"]:
        x = 1.0
    y = 0.0
    if key_states["w"] and not key_states["s"]:
        y = -1.0
    elif not key_states["w"] and key_states["s"]:
        y = 1.0
    message = {"username": request.id, "action": "move", "x": x, "y": y}
    send_to_scala(message)

@app.route('/')
def index():
    return send_from_directory('web', 'index.html')


@app.route('/<path:filename>')
def static_files(filename):
    return send_from_directory('web', filename)


print("Listening on port 6010")
server.run(app, port=6010)

if __name__ == '__main__':
    server
