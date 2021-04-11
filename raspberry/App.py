from NetworkListener import MqttListener

HOST="broker.hivemq.com"
TOPIC="radio/raspberry_itis_orion"

class App:

    def __init__(self):
        self.name = "app"
        self.listener = MqttListener(HOST, TOPIC)

    def listen(self):
        self.listener.start()
        # Todo listen rfid, mqtt next
        print("Listening")


