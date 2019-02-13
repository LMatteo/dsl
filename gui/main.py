from Display import Display
from UpdateThread import UpdateThread
import time
import threading


def update():
    i =0
    while True :
        if i % 2 == 0 :
            data.append(1)
        else :
            data.append(0)
        time.sleep(1)

mods = dict()
mods['init'] = ['in','off']
sensors = ['led','button']

data = []
thr = threading.Thread(target=update)
thr.start()
display = Display(mods,sensors)
display.init(data,5000)
print("finished")



