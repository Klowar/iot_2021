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
        self.listener.subscribe(self.play)
        self.listener.start()
        # Todo listen rfid, mqtt next

    def play(self, json):
        print("Callback {}".format(json))
        path = Downloader.download(json.music)
        playsound(path, True)
