from threading import Thread
import time


class UpdateThread(Thread):

    def run(self):
        i = 0
        while True:
            print(i)
            time.sleep(1)
            i += 1
