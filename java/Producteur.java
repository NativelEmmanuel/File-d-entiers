import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Producteur extends Thread{

  private final int NB_MIN = 1;
  private final int NB_MAX = 100;

  private Queue queue;
  private int temps, n, num_producteur;
  private JLabel label;

  public Producteur(Queue queue, int temps, JLabel label, int num_producteur){
    this.queue = queue;
    this.n = NB_MIN + (int)(Math.random() * ((NB_MAX - NB_MIN) + 1));
    this.temps = temps;
    this.label = label;
    this.num_producteur = num_producteur;
  }

  public void run(){
    try{
      while(!interrupted()){
        this.label.setText("Producteur"+this.num_producteur+" : Ajout de l'entier "+this.n+"...");
        this.queue.enfiler(this.n);
        this.label.setText("Producteur"+this.num_producteur+" : Ajout de l'entier "+this.n+" effectue.");
        this.n = NB_MIN + (int)(Math.random() * ((NB_MAX - NB_MIN) + 1));
        sleep(this.temps);
      }
    }catch(Exception e){}
  }

}
