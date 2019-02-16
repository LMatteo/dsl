from matplotlib.figure import Figure
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg



class GraphComponent:
    def __init__(self,master):
        fig = Figure()
        self.sub = fig.add_subplot(111)
        self.sub.set_ylim(-0.1,1.1)

        self.xdata = []
        self.ydata = []
        line, = self.sub.plot(self.xdata, self.ydata,'-r')
        self.line = line
        self.canvas = FigureCanvasTkAgg(fig, master=master)
        self.canvas.draw()

    def getWidget(self):
            return self.canvas.get_tk_widget()

    def update(self,data):
        self.ydata.extend(data[1])
        self.xdata.extend(data[0])

        if len(self.xdata) > len(self.ydata):
            self.xdata = self.xdata[0:len(self.ydata)]
        else :
            self.ydata = self.ydata[0:len(self.xdata)]

        self.line.set_data(self.xdata, self.ydata)
        #self.line.set_ydata(ydata.append(1))
        maxlim = self.xdata[len(self.xdata)-1]
        lowerlim = maxlim-10000 if maxlim > 10000 else 0

        self.sub.set_xlim(lowerlim, maxlim)
        self.canvas.draw()
        self.canvas.flush_events()

    @staticmethod
    def name():
        return 'graph'
