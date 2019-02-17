from Display import Display
from DataUpdater import DataUpdater
import json


def getConfig(configFileName):
    file = open(configFileName,'r')
    config = json.loads(file.read())
    file.close()
    return config

config = getConfig('config.json')
display = Display(config)
updater = DataUpdater("/dev/ttyACM0",config)
data = []
updater.start()
display.init(updater.getData(),100)
updater.stop()
updater.join()
print("finished")




