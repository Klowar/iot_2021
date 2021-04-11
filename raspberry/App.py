import multiprocessing
from playsound import playsound
from NetworkListener import MqttListener
from Downloader import Downloader

HOST = "broker.hivemq.com"
TOPIC = "radio/raspberry_itis_orion"
SETTINGS = "radio/raspberry_itis_orion/settings"

def daemon(path, block):
    p = multiprocessing.current_process()
    playsound(path, block)

class App:

    def __init__(self):
        self.name = "app"
        self.processThread = None
        self.listener = MqttListener(HOST, TOPIC)

    def listen(self):
        self.listener.subscribe(self.play)
        self.listener.start()
        # Todo listen rfid, mqtt next

    def play(self, json):
        print("Callback {}".format(json))
        if (self.processThread != None and self.processThread.is_alive()):
            self.processThread.kill()
        path = Downloader.download(json.music)
        self.processThread = multiprocessing.Process(target=daemon, args=(path, True))
        self.processThread.start()
