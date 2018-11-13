import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener{
  //[Constantes
  private final int MIN_TEMPS_CONSO = 3000;
  private final int MAX_TEMPS_CONSO = 4000;
  private final int MIN_TEMPS_PROD = 1500;
  private final int MAX_TEMPS_PROD = 2500;
  private final int TAILLE_MAX_FILE = 20;
  //]

  private final Queue file;
  private final int nb_producteurs;
  private final int nb_consommateurs;
  private Consommateur[] l_cons;
  private Producteur[] l_prod;

  //[Elements graphiques
  private final JLabel l_liste, l_taille;
  private final JPanel p_threads, p_file;
  private final JButton b_start;
  //]

  public Fenetre(int nb_producteurs, int nb_consommateurs){

    setDefaultCloseOperation(EXIT_ON_CLOSE); //Permet de fermer la fenêtre quand on appuie sur la croix

    this.nb_producteurs = nb_producteurs;
    this.nb_consommateurs = nb_consommateurs;

    Container contentPane = getContentPane();
    contentPane.setLayout( new BorderLayout());

    //[Initialisation des elements graphiques
    this.b_start = new JButton("Start");
    this.p_file = new JPanel();
    this.l_liste = new JLabel();
    this.l_taille = new JLabel();
    this.p_threads = new JPanel();
    //]

    //[Personnalisation
    this.l_liste.setForeground(Color.BLUE);
    this.l_taille.setForeground(Color.DARK_GRAY);
    //]

    //[Mode de placement des éléments
    this.p_file.setLayout(new BorderLayout());
    this.p_threads.setLayout(new GridLayout(this.nb_producteurs+this.nb_consommateurs,1));
    //]

    this.l_cons = new Consommateur[nb_consommateurs];
    this.l_prod = new Producteur[nb_producteurs];
    this.file = new Queue(TAILLE_MAX_FILE,this.l_liste, this.l_taille);

    //[Ajout des éléments graphiques à la fenêtre
    this.p_file.add(l_liste, BorderLayout.WEST);
    this.p_file.add(l_taille, BorderLayout.EAST);
    contentPane.add(p_file, BorderLayout.NORTH);
    contentPane.add(b_start, BorderLayout.SOUTH);
    contentPane.add(p_threads, BorderLayout.CENTER);
    //]

    //[Evenements
    b_start.addActionListener(this);
    //]

    //[Affichage
    setLocation(100,100);
    setSize(650,500);
    setVisible(true);
    //]

  }
  //[Creation des Threads
  public void  create_process(){
    for(int i=0; i<this.nb_producteurs; i++){
      JLabel label_p = new JLabel();
      label_p.setForeground(Color.GREEN);
      int t_p = MIN_TEMPS_PROD + (int)(Math.random() * ((MAX_TEMPS_PROD - MIN_TEMPS_PROD) + 1));
      l_prod[i] = new Producteur(file, t_p, label_p, i);
      p_threads.add(label_p);
    }
    for(int i=0; i<this.nb_consommateurs; i++){
      JLabel label_c = new JLabel();
      label_c.setForeground(Color.RED);
      int t_c = MIN_TEMPS_CONSO + (int)(Math.random() * ((MAX_TEMPS_CONSO - MIN_TEMPS_CONSO) + 1));
      l_cons[i] = new Consommateur(file, t_c, label_c, i);
      p_threads.add(label_c);
    }
  }
  //]

  //[Lancement des Threads
  public void start_process(){
    for(int i=0; i<this.nb_producteurs; i++) l_prod[i].start();

    for(int i=0; i<this.nb_consommateurs; i++) l_cons[i].start();
  }
  //]

  //[Arrêt des Threads
  public void stop_process(){
    for(int i=0; i<this.nb_producteurs; i++) l_prod[i].interrupt();

    for(int i=0; i<this.nb_consommateurs; i++) l_cons[i].interrupt();
  }
  //]


  //[méthode de l'interface ActionListener
  public void actionPerformed(ActionEvent e){ //Ce qui se passe quand on appuie sur le boutton
    Object source = e.getSource(); //Contient l'indentité de l'objet qui a enclenché l'évènement
    if(source instanceof JButton){
      JButton bt_source = (JButton) source;
      String titre = bt_source.getText();

      switch(titre){
        case "Start" : //actions lors de l'appuie sur le bouton start
          create_process();
          start_process();
          bt_source.setText("Stop");
          break;
        case "Stop" : //actions lors de l'appuie sur le bouton stop
          stop_process();
          bt_source.setText("Start");
          bt_source.setEnabled(false);
          break;
      }
    }
  }
  //]

  public static void main(String[] args) {
    new Fenetre(4,2); // Fenetre(nb_producteurs, nb_consommateurs)
  }

}
