from threading import Thread
from random import randint
import time

class Producteur(Thread) :

    NB_MIN = 1
    NB_MAX = 100

    def __init__(self, queue, temps, label, num_producteur) :
        Thread.__init__(self)
        self.queue = queue
        self.n = randint(self.NB_MIN,self.NB_MAX)
        self.temps = temps
        self.label = label
        self.num_producteur = num_producteur
        self.interrupted = False

    def setInterrupted(self,bool) :
        self.interrupted = bool

    def run(self) :
        while not self.interrupted :
            self.label.config(text="Producteur"+str(self.num_producteur)+" : Ajout de l'entier "+str(self.n)+"...")
            self.queue.enfiler(self.n)
            self.label.config(text="Producteur"+str(self.num_producteur)+" : Ajout de l'entier "+str(self.n)+" effectue.")
            self.n = randint(self.NB_MIN,self.NB_MAX)
            time.sleep(self.temps/1000)
