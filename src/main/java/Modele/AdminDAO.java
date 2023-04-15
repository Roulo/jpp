package Modele;

import javafx.scene.control.Label;


public interface AdminDAO {

    public void AdminCommand();
    public void AjouterClient();
    public void SupprimerClient();
    public void AjouterFilm(Label resultat, String title, String director, String actor, String year, String resume, String link, String duration, String genre);
    public void SupprimerFilm(Label resultat, String title);
}