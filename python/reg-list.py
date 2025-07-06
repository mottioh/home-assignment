from flask import Flask, jsonify
import docker
import os
import sys

# checks if docker socket is not mountes, for example running on wsl environment
if not os.path.exists('/var/run/docker.sock'):
    print("Docker socket not found! Is it mounted?")
    sys.exit(1)

app = Flask(__name__)
client = docker.from_env()

@app.route('/')
def list_containers():
    containers = client.containers.list()
    return jsonify([container.name for container in containers])

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
