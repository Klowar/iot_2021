from time import sleep
from threading import Thread
import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522

class RFIDListener:

    def __init__(self):
        self.callbacks = []
        self.reader = SimpleMFRC522()

    def start(self):
        self.rfidThread = Thread(target=self.listen)
        self.rfidThread.start()

    def listen(self):
        id, text = None, None
        while(text == None and id == None):
            try:
                id, text = self.reader.read()
                if (text != None): self.notify(text.strip())
            except BaseException:
                sleep(0.2)
            finally:
                GPIO.cleanup()

    def subscribe(self, callback):
        print("Subscribed {}".format(callback))
        self.callbacks.append(callback)

    def notify(self, message):
        for callback in self.callbacks:
            callback(message)