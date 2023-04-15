package Modele;

import javafx.scene.control.Label;


public interface AdminDAO {

    public void AdminCommand();
    public void AjouterClient();
    public void SupprimerClient();
    public void AjouterFilm(Label resultat, String title, String director, int year, int duration, String resume, String link,int note, String genre);
    public void SupprimerFilm(Label resultat, String title);
}