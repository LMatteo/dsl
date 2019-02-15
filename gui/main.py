from Display import Display
from DataUpdater import DataUpdater
import time
import threading

mods = dict()
mods['init'] = ['in','off']
sensors = ['led']
updater = DataUpdater()
data = []
display = Display(mods,sensors)
updater.start()
display.init(updater.getData(),1000)
updater.stop()
updater.join()
print("finished")



