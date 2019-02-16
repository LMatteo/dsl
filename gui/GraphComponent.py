from matplotlib.figure import Figure
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg


windowSize = 20000

class GraphComponent:
    def __init__(self,master,name):
        self.fig = Figure()

        self.fig.suptitle(name)
        self.sub = self.fig.add_subplot(111)
        self.xdata = []
        self.ydata = []
        line, = self.sub.plot(self.xdata, self.ydata,'-r')
        self.sub.set_ylim(-0.1,1.1)
        self.sub.set_xlim(0,windowSize)
        self.line = line
        self.fig.axes[0].set_xlabel('time(ms)')
        self.fig.axes[0].set_ylabel('value')
        self.canvas = FigureCanvasTkAgg(self.fig, master=master)
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


        maxlim = self.xdata[len(self.xdata)-1] if self.xdata[len(self.xdata)-1] > windowSize else windowSize
        lowerlim = maxlim-windowSize if maxlim > windowSize else 0
        self.sub.set_xlim(lowerlim,maxlim)
        self.line.set_data(self.xdata, self.ydata)
        self.canvas.draw()
        self.canvas.flush_events()


    @staticmethod
    def name():
        return 'graph'
