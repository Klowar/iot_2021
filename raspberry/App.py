from playsound import playsound
from NetworkListener import MqttListener
from Downloader import Downloader

HOST = "broker.hivemq.com"
TOPIC = "radio/raspberry_itis_orion"
SETTINGS = "radio/raspberry_itis_orion/settings"


class App:

    def __init__(self):
        self.name = "app"
        self.listener = MqttListener(HOST, TOPIC)

    def listen(self):
        self.listener.start()
        self.listener.subscribe(self.play)
        # Todo listen rfid, mqtt next
        print("Listening")

    def play(self, json):
        path = Downloader.download(json.music)
        playsound(path, True)
