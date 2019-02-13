from matplotlib.figure import Figure
from tkinter import *
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
import time

class Display:
    def __init__(self, mods, sensors):
        self.window = Tk()
        self.window.geometry("1280x720")
        panel = PanedWindow(self.window, orient=VERTICAL)

        self.modPanel = PanedWindow(panel,orient=HORIZONTAL)
        self.mods = dict()

        for mod in mods:
            self.modPanel.add(Label(self.modPanel,text=mod))
            self.mods[mod] = mods[mod]

        self.modPanel.pack()
        panel.add(self.modPanel)

        self.statePanel = PanedWindow(panel,orient=HORIZONTAL)
        self.statePanel.pack()
        panel.add(self.statePanel)

        self.sensorLine = dict()
        graphPanel = PanedWindow(panel, orient=HORIZONTAL)
        for sensor in sensors:
            fig = Figure(figsize=(3,3), dpi=100)
            sub = fig.add_subplot(111)
            line, = sub.plot([1,1,1])
            self.sensorLine[sensor] = (line, FigureCanvasTkAgg(fig, master=graphPanel))
            self.sensorLine[sensor][1].draw()
            graphPanel.add(self.sensorLine[sensor][1].get_tk_widget())

        graphPanel.pack()
        panel.add(graphPanel)
        panel.pack()

    def init(self,data, refreshFrequence):
        self.window.after(refreshFrequence,self.update, data,refreshFrequence)
        self.window.mainloop()

    def update(self,data, freq):
        print(data)
        print(freq)

        #for sensor in self.sensorLine:
        #    print(self.sensorLine[sensor][0])
        #    self.sensorLine[sensor][0].set_ydata([0,0,1])
        #    self.sensorLine[sensor][1].draw()
        self.window.after(freq,self.update,data,freq)

