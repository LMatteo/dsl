import threading
import time
from SerialReader import SerialReader

class DataUpdater(threading.Thread):
    def __init__(self, portname,config):
        threading.Thread.__init__(self)
        self.proceed = True
        self.serial = SerialReader(portname)
        self.sensors = config['watchables']
        self.data = dict()
        self.data['sensors'] = dict()

        for sensor in self.sensors:
            self.data['sensors'][sensor] = ([],[])


    def getData(self):
        return self.data

    def updateData(self):
        update = self.serial.getData()
        self.data['mode'] = update['Mode']
        self.data['state'] = update['State']
        time = int(update['Time'])
        for sensor in self.sensors:
            self.data['sensors'][sensor][0].append(time)
            self.data['sensors'][sensor][1].append(int(update[sensor]))

    def run(self):
        while self.proceed:
            try:
                self.updateData()
            except Exception as e:
                pass

    def stop(self):
        self.proceed = False

