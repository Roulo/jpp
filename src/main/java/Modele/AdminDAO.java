package Modele;

import javafx.scene.control.Label;


public interface AdminDAO {
    public int AdminCommand();
    public void AjouterClient(Label resultat,String identifiant, String mdp1, int administrateur);
    public void SupprimerClient(Label resultat,String identifiant);
    public void AjouterFilm(Label resultat, String title, String director, int year, int duration, String resume, String link,int note, String genre);
    public void SupprimerFilm(Label resultat, String title);
}