import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;

public class Queue{
  private final int capacite;
  private final LinkedList<Integer> valeurs = new LinkedList<Integer>();
  private final JLabel l_liste, l_taille;

  public Queue(int capacite, JLabel l_liste, JLabel l_taille){
    this.capacite = capacite;
    this.l_liste = l_liste;
    this.l_taille = l_taille;
    this.l_liste.setText("File = "+this.createString());
  }
  //[Mise à jouer du text des JLabels
  private void majLabel(){
    this.l_liste.setText("File = "+this.createString());
    this.l_liste.repaint();
    this.l_taille.repaint();
  }
  //]

  //[Suppression du premier élément de la liste
  public synchronized int defiler() throws InterruptedException {
    while(this.valeurs.size() == 0) wait();
    int n = this.valeurs.removeFirst();
    this.majLabel();
    notifyAll();
    return n;
  }
  //]

  //[Ajout d'un élément à la liste
  public synchronized void enfiler(int n) throws InterruptedException{
    while(this.valeurs.size() == this.capacite) wait();
    this.valeurs.add(n);
    this.majLabel();
    notifyAll();
  }
  //]

  //[Création de la chaîne de caractères de la liste + longueur de la liste
  public synchronized String createString(){
    String s = "<<< ";
    for(int i=0; i<this.valeurs.size(); i++){
      s+=String.valueOf(this.valeurs.get(i));
      if(i!=this.valeurs.size()-1) s+=", ";
    }
    s+=" <<<";
    this.l_taille.setText("Longueur de la file : "+this.valeurs.size());
    return s;
  }
  //]
}
