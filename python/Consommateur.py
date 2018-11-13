from threading import Thread
import time

class Consommateur(Thread) :

    def __init__(self, queue, temps, label, num_consommateur) :
        Thread.__init__(self)
        self.queue = queue
        self.temps = temps
        self.label = label
        self.num_consommateur = num_consommateur
        self.interrupted = False

    def setInterrupted(self,bool) :
        self.interrupted = bool

    def run(self) :
        while not self.interrupted :
            self.label.config(text="Consommateur"+str(self.num_consommateur)+" : Retrait d'un entier en cours...")
            n = self.queue.defiler()
            self.label.config(text="Consommateur"+str(self.num_consommateur)+" : retrait de l'entier "+str(n)+" effectue.")
            time.sleep(self.temps/1000)
