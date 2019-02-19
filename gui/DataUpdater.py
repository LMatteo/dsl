import threading
import time
from SerialReader import SerialReader

maxLength = 50

class DataUpdater(threading.Thread):
    def __init__(self, portname,config):
        threading.Thread.__init__(self)
        self.proceed = True
        self.serial = SerialReader(portname)
        self.sensors = []

        for watchable in config['watchables']:
            self.sensors.append(watchable)

        self.data = dict()
        self.data['sensors'] = dict()
        self.data['mode'] = ""
        self.data['state'] = ""

        for sensor in self.sensors:
            self.data['sensors'][sensor] = ([],[])

        for i in range(0,20):
            try:
                update = self.serial.getData()
            except:
                pass

    def getData(self):
        return self.data

    def updateData(self):

        update = self.serial.getData()
        self.data['mode'] = update['Mode']
        self.data['state'] = update['State']
        time = int(update['Time'])/1000
        for sensor in self.sensors:
            self.data['sensors'][sensor][0].append(time)
            value = int(update[sensor])
            if value > 1 :
                value = value / 1023
            self.data['sensors'][sensor][1].append(value)

            if len(self.data['sensors'][sensor][1]) > maxLength:
                del self.data['sensors'][sensor][1][0]

            if len(self.data['sensors'][sensor][0]) > maxLength:
                del self.data['sensors'][sensor][0][0]

    def run(self):
        while self.proceed:
            try:
                self.updateData()
            except Exception as e:
                pass

    def stop(self):
        self.proceed = False

