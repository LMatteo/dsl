from matplotlib.figure import Figure
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

xdata = []
ydata = []

class GraphComponent:
    def __init__(self,master):
        fig = Figure()
        sub = fig.add_subplot(111)
        sub.set_ylim(-0.1,1.1)
        sub.set_xlim(0)
        line, = sub.plot(xdata, ydata,'-r')
        self.line = line
        self.canvas = FigureCanvasTkAgg(fig, master=master)
        self.canvas.draw()

    def getWidget(self):
            return self.canvas.get_tk_widget()

    def update(self,data):
        self.line.set_ydata(ydata.append(1))
        self.canvas.draw()
        self.canvas.flush_events()

    @staticmethod
    def name():
        return 'graph'
