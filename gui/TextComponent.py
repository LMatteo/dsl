from tkinter import *


class TextComponent:
    def __init__(self,master,name):
        self.root = PanedWindow(master,orient=VERTICAL)
        self.root.config(height=320,width=400)
        self.root.add(Label(master=self.root,text = name,anchor='w'),width=400 )

        timePanel = PanedWindow(self.root,orient=HORIZONTAL)
        timePanel.add(Label(master=timePanel,text = "Current Time (s) : ",anchor='w'))
        self.time = StringVar()
        timePanel.add(Label(master=timePanel,textvariable=self.time,anchor='w'))
        self.root.add(timePanel,width=400)

        valuePanel = PanedWindow(self.root,orient=HORIZONTAL)
        valuePanel.add(Label(master=valuePanel,text = "Current Value : ",anchor='w'))
        self.val = StringVar()
        valuePanel.add(Label(master=valuePanel,textvariable=self.val,anchor='w'))
        self.root.add(valuePanel,width=400)

    def getWidget(self):
        return self.root

    def update(self,data):
        self.time.set(data[0][len(data[0])-1])
        self.val.set(data[1][len(data[1])-1])

    @staticmethod
    def name():
        return 'text'
