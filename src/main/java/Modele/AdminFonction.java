package Modele;

import java.sql.SQLException;

import javafx.scene.control.Label;

import java.sql.*;

public class AdminFonction implements AdminDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public AdminFonction() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AjouterClient(){

    };
    public void SupprimerClient(){

    };
    public void AjouterFilm(Label resultat, String title, String director, String actor, String year, String resume, String link, String duration, String genre){
        String query = "INSERT INTO videos (titre, realisateur, acteurs, annee, resume, lien, duree, categorie) VALUES ('" + title + "', '" + director + "', '" + actor + "', '" + year + "', '" + resume + "', '" + link + "', '" + duration + "', '" + genre + "')";
        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Video ajoutée avec succès");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    };

    public void SupprimerFilm(Label resultat, String title){
        String query = "DELETE FROM videos WHERE titre = '" + title + "'";
        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Video supprimée avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    };


}
