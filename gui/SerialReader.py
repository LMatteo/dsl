from serial import Serial


class SerialReader:
    def __init__(self,serialport):
        self.serial = Serial(serialport,9600)

    def getData(self):
        line = str(self.serial.readline())
        res = {}
        try:
            line = line[1:len(line)]
            line = line.replace("'",'')
            line = line.replace("\\r\\n",'')
            for i in line.split(","):
                res[i.split(":")[0]] = i.split(":")[1]
            return res
        except:
            raise IOError

