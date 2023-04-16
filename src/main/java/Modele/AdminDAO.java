package Modele;

import javafx.scene.control.Label;

/**AdminDAO */
public interface AdminDAO {
    /** Constructeur*/
    public int AdminCommand();
    /** Fonction qui ajoute un client à la base de donnée*/
    public void AjouterClient(Label resultat,String identifiant, String mdp1, int administrateur);
    /** Fonction qui supprime un client de la base de donnée*/
    public void SupprimerClient(Label resultat,String identifiant);
    /** Fonction qui ajoute un film à la base de données*/
    public void AjouterFilm(Label resultat, String title, String director, int year, int duration, String resume, String link,int note, String genre);
    /** Fonction qui supprime un film de la base de données*/
    public void SupprimerFilm(Label resultat, String title);
}