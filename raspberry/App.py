import pygame
import psutil
import alsaaudio
import multiprocessing
# from playsound import playsound
from NetworkListener import MqttListener
from Downloader import Downloader

HOST = "broker.hivemq.com"
TOPIC = "radio/raspberry_itis_orion"
SETTINGS = "radio/raspberry_itis_orion/settings"
# manipulate commands
STOP= "stop"
PLAY= "play"

def daemon(path, block):
    print("Started music daemon")
    p = multiprocessing.current_process()
    pygame.mixer.init()
    pygame.mixer.music.load(path)
    pygame.mixer.music.play()

class App:

    def __init__(self):
        self.mixer = alsaaudio.Mixer()
        self.name = "app"
        self.musicProcess = None
        self.listener = MqttListener(HOST, TOPIC)
        self.settingListener = MqttListener(HOST, SETTINGS)

    def listen(self):
        self.listener.subscribe(self.play)
        self.settingListener.subscribe(self.applySettings)
        self.listener.start()
        self.settingListener.start()
        # Todo listen rfid, mqtt next

    def applySettings(self, json):
        if (json.volume != None):
            self.mixer.setvolume(json.volume)
        if (json.playback == STOP and self.musicProcess != None):
            process = psutil.Process(self.musicProcess.pid)
            process.suspend()
        if (json.playback == PLAY and self.musicProcess != None):
            process = psutil.Process(self.musicProcess.pid)
            process.resume()

    def play(self, json):
        print("Callback {}".format(json))
        if (self.musicProcess != None and self.musicProcess.is_alive()):
            self.musicProcess.kill()
        path = Downloader.download(json.music)
        self.musicProcess = multiprocessing.Process(target=daemon, args=(path, True))
        self.musicProcess.start()
