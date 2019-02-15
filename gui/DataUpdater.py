import threading
import time

class DataUpdater(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.proceed = True
        self.data = dict()
        self.data['sensors'] = dict()

    def getData(self):
        return self.data

    def updateData(self,toParse):
        self.data['mod'] = "init"
        self.data['state'] = "in"
        self.data['sensors']['led'].append(1)


    def run(self):
        self.data['sensors']['led'] = []
        while self.proceed:
            self.updateData("salut")
            time.sleep(2)

    def stop(self):
        self.proceed = False

