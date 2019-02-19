from Display import Display
from DataUpdater import DataUpdater
import json
import sys


if len(sys.argv) != 3:
    print("need 2 arguments exactly : <serial port> <config file name>",file=sys.stderr)
    sys.exit(1)


configFilename = sys.argv[2]
port = sys.argv[1]
def getConfig(configFileName):
    file = open(configFileName,'r')
    config = json.loads(file.read())
    file.close()
    return config

config = getConfig(configFilename)
display = Display(config)
updater = DataUpdater(port,config)
data = []
updater.start()
display.init(updater.getData(),100)
updater.stop()
updater.join()
print("finished")




