import json
from types import SimpleNamespace
import paho.mqtt.client as mqtt

class MqttListener:

    def __init__(self, host, topic):
        self.host = host
        self.topic = topic
        self.callbacks = []
        self.client = client = mqtt.Client()
        client.on_connect = self.on_connect
        client.on_message = self.on_message

    def start(self):
        self.client.connect(self.host, 1883, 60)
        print("started")

    def on_connect(self, client, userdata, flags, rc):
        if rc == 0:
            print("Connected")
            client.subscribe(self.topic)
            self.client.loop_start()
        else:
            print("Rejected")
            client.connect(self.host, 1883, 60)

    def on_message(self, client, userdata, msg):
        print("Received {}".format(msg.payload))
        x = json.loads(msg.payload, object_hook=lambda d: SimpleNamespace(**d))
        for callback in self.callbacks:
            callback(x)

    def subscribe(self, callback):
        self.callbacks.append(callback)
