import paho.mqtt.client as mqtt

class MqttListener:

    def __init__(self, host, topic):
        self.host = host
        self.topic = topic
        self.client = client = mqtt.Client()
        client.on_connect = self.on_connect
        client.on_message = self.on_message

    def start(self):
        self.client.connect(self.host, 1883, 60)
        self.client.loop_forever()
        print("started")

    def on_connect(self, client, userdata, flags, rc):
        if rc == 0:
            print("Connected")
            client.subscribe(self.topic)
        else:
            print("Rejected")
            client.connect(self.host, 1883, 60)

    def on_message(self, client, userdata, msg):
        print(msg.topic + " " + str(msg.payload))

