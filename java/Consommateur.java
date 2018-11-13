import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Consommateur extends Thread{

  private Queue queue;
  private int temps, num_consommateur;
  private JLabel label;

  public Consommateur(Queue queue, int temps, JLabel label, int num_consommateur){
    this.queue = queue;
    this.temps = temps;
    this.label = label;
    this.num_consommateur = num_consommateur;
  }

  public void run(){
    try{
      while(!interrupted()){
        this.label.setText("Consommateur"+this.num_consommateur+" : Retrait d'un entier en cours...");
        int n = this.queue.defiler();
        this.label.setText("Consommateur"+this.num_consommateur+" : retrait de l'entier "+n+" effectue.");
        sleep(this.temps);
      }
    }catch(Exception e){}
  }

}
