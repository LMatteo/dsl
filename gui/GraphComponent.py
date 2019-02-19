from matplotlib.figure import Figure
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg


windowSize = 20000

class GraphComponent:
    def __init__(self,master,name,sensors):
        self.fig = Figure()

        self.fig.suptitle(name)
        self.sub = self.fig.add_subplot(111)
        
        self.color = {}
        self.color['blue'] = 'b'
        self.color['green'] = 'g'
        self.color['red'] = 'r'
        self.color['cyan'] = 'c'
        self.color['magenta'] = 'm'
        self.color['yellow'] = 'y'
        self.color['black'] = 'k'

        self.datas = {}
        self.lines = {}
        for sensor in sensors:
            self.datas[sensor['brickId']] = []
            self.datas[sensor['brickId']].append([])
            self.datas[sensor['brickId']].append([])            
            line, = self.sub.plot(self.datas[sensor['brickId']][0], self.datas[sensor['brickId']][1],'-%s' % self.color[sensor['color']])
            self.lines[sensor['brickId']] = line
        self.sub.set_ylim(-0.1,1.1)
        self.sub.set_xlim(0,windowSize)
        self.fig.axes[0].set_xlabel('time(ms)')
        self.fig.axes[0].set_ylabel('value')
        self.canvas = FigureCanvasTkAgg(self.fig, master=master)
        self.canvas.draw()

    def getWidget(self):
            return self.canvas.get_tk_widget()

    def update(self,data):
        maxlim = 0
        for entry in data:
            if entry in self.datas:
                self.datas[entry][0].extend(data[entry][0])
                self.datas[entry][1].extend(data[entry][1])
                if len(self.datas[entry][0]) > len(self.datas[entry][1]):
                    self.datas[entry][0] = self.datas[entry][0][0:len(self.datas[entry][1])]
                else :
                    self.datas[entry][1] = self.datas[entry][1][0:len(self.datas[entry][0])]                
                tmpMaxlim = self.datas[entry][0][len(self.datas[entry][0])-1] if self.datas[entry][0][len(self.datas[entry][0])-1] > windowSize else windowSize
                if maxlim < tmpMaxlim:
                    maxlim = tmpMaxlim 
                    lowerlim = maxlim-windowSize if maxlim > windowSize else 0
                self.lines[entry].set_data(self.datas[entry][0], self.datas[entry][1])
                
                print('##############' + entry + '#############' )
                print('########## x ###########')
                print(self.datas[entry][0])
                print('########## y ###########')
                print(self.datas[entry][1])
        self.sub.set_xlim(lowerlim,maxlim)
        self.canvas.draw()
        self.canvas.flush_events()


    @staticmethod
    def name():
        return 'graph'
