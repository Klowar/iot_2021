from NetworkListener import MqttListener

HOST="broker.hivemq.com"
TOPIC="topic/listener"

class App:

    def __init__(self):
        self.name = "app"
        self.listener = MqttListener(HOST, TOPIC)

    def listen(self):
        self.listener.start()
        # Todo listen rfid, mqtt next
        print("Listening")


