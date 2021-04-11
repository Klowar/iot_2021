import psutil
import alsaaudio
import multiprocessing
from playsound import playsound
from NetworkListener import MqttListener
from Downloader import Downloader

HOST = "broker.hivemq.com"
TOPIC = "radio/raspberry_itis_orion"
SETTINGS = "radio/raspberry_itis_orion/settings"

PLAYBACKS = {
    STOP: "stop",
    PLAY: "play"
}

def daemon(path, block):
    p = multiprocessing.current_process()
    playsound(path, block)

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
        # Todo listen rfid, mqtt next

    def applySettings(self, json):
        if (json.volume != None):
            self.mixer.setvolume(json.volume)
        if (json.playback == PLAYBACKS.STOP and self.musicProcess != None):
            process = psutil.Process(self.musicProcess.pid)
            process.suspend()
        if (json.playback == PLAYBACKS.PLAY and self.musicProcess != None):
            process = psutil.Process(self.musicProcess.pid)
            process.resume()



    def play(self, json):
        print("Callback {}".format(json))
        if (self.musicProcess != None and self.musicProcess.is_alive()):
            self.musicProcess.kill()
        path = Downloader.download(json.music)
        self.musicProcess = multiprocessing.Process(target=daemon, args=(path, True))
        self.musicProcess.start()
