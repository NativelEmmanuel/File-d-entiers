from tkinter import *
from Queue import Queue
from Consommateur import Consommateur
from Producteur import Producteur
from random import randint

class Fenetre(Frame) :
    MIN_TEMPS_PROD = 2000
    MAX_TEMPS_PROD = 2500
    MIN_TEMPS_CONSO = 1700
    MAX_TEMPS_CONSO = 1900
    TAILLE_MAX_FILE = 20

    def __init__(self, fenetre, nb_producteurs, nb_consommateurs) :
        Frame.__init__(self)
        self.pack(fill=BOTH)
        self.nb_consommateurs = nb_consommateurs
        self.nb_producteurs = nb_producteurs
        self.liste_prod = []
        self.liste_cons = []

        self.f_liste = Frame(self)
        self.f_liste.pack(side=TOP, fill=BOTH)

        self.l_liste = Label(self.f_liste, text="< <", fg="blue")
        self.l_liste.pack(side=LEFT, fill=BOTH)

        self.l_taille = Label(self.f_liste, text="taille de la liste : 0", fg="gray")
        self.l_taille.pack(side=RIGHT, fill=BOTH)

        self.b_start = Button(self, text="Start", command=self.cliquer)
        self.b_start.pack(side=BOTTOM, fill=BOTH)

        self.p_threads = PanedWindow(self, orient=VERTICAL)
        self.p_threads.pack(side=BOTTOM, fill=BOTH)

        self.file = Queue(self.TAILLE_MAX_FILE, self.l_liste, self.l_taille)

    def create_process(self) :
        for i in range(self.nb_producteurs) :
            label_p = Label(self.p_threads, text="Consommateur"+str(i), fg="green")
            t_p = randint(self.MIN_TEMPS_PROD,self.MAX_TEMPS_PROD)
            self.liste_prod.append(Producteur(self.file, t_p, label_p, i))
            self.p_threads.add(label_p)

        for i in range(self.nb_consommateurs) :
            label_c = Label(self.p_threads, text="Producteur"+str(i), fg="red")
            t_c = randint(self.MIN_TEMPS_CONSO,self.MAX_TEMPS_CONSO)
            self.liste_cons.append(Consommateur(self.file, t_c, label_c, i))
            self.p_threads.add(label_c)

    def start_process(self) :
        for i in range(self.nb_producteurs) : self.liste_prod[i].start()
        for i in range(self.nb_consommateurs) : self.liste_cons[i].start()

    def stop_process(self) :
        for i in range(self.nb_producteurs) : self.liste_prod[i].setInterrupted(True)
        for i in range(self.nb_consommateurs) : self.liste_cons[i].setInterrupted(True)



    def cliquer(self) :
        if self.b_start.cget("text") == "Start" :
            self.create_process()
            self.start_process()
            self.b_start.config(text="Stop")
        elif self.b_start.cget("text") == "Stop" :
            self.stop_process()
            self.b_start.config(state=DISABLED)

if __name__ == '__main__':
    fenetre = Tk()
    fenetre.geometry('650x500')
    Fenetre = Fenetre(fenetre, 4,2)
    Fenetre.mainloop()
