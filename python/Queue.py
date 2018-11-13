from threading import Condition

class Queue() :

    def __init__(self, capacite, l_liste, l_taille) :
        self.valeurs = []
        self.capacite = capacite
        self.l_liste = l_liste
        self.l_taille = l_taille
        self.verrou = Condition()
        self.l_liste.config(text="File = "+self.createString())


    def majLabel(self) :
        self.l_liste.config(text="File = "+self.createString())
        self.l_liste.update()
        self.l_taille.update()

    def defiler(self) :
        with self.verrou :
            while len(self.valeurs) == 0 : self.verrou.wait()
            n = self.valeurs[0]
            del self.valeurs[0]
            self.majLabel()
            self.verrou.notifyAll()
            return n

    def enfiler(self, n) :
        with self.verrou :
            while len(self.valeurs) == self.capacite : self.verrou.wait()
            self.valeurs.append(n)
            self.majLabel()
            self.verrou.notifyAll()

    def createString(self) :
        with self.verrou :
            s = "<<< "
            for i in range(0,len(self.valeurs)) :
              s+=str(self.valeurs[i])
              if i != len(self.valeurs)-1 :  s+=", "
            s+=" <<<"
            self.l_taille.config(text="Longueur de la file : "+ str(len(self.valeurs)) );
            return s;
