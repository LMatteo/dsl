from serial import Serial

ser = Serial("COM4", 9600)
while True:
    line = str(ser.readline())
    res = {}
    for i in line.split(","):
        res[i.split(":")[0]] = i.split(":")[1]

    print("Mode", res["Mode"])
    print("State", res["State"])
    print("Time", res["Time"])
    for k, v in res.items():
        if k not in {"Mode", "State", "Time"}:
            print(k, v)
