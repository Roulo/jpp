package Modele;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.*;

public class Compte extends DAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public Compte() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void Charger(ObservableList<Object> genreList, ChoiceBox<Object> genre) {
        genreList.removeAll(genreList);
        String query = "SELECT type FROM genre;";
        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                genreList.add(rs.getString("type"));
            }
            genre.setItems(genreList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    public void AjouterFilm(Label resultat, String title, String director, String actor, String year, String resume, String link, String duration, String genre) {
        String query = "INSERT INTO videos (titre, realisateur, acteurs, annee, resume, lien, duree, categorie) VALUES ('" + title + "', '" + director + "', '" + actor + "', '" + year + "', '" + resume + "', '" + link + "', '" + duration + "', '" + genre + "')";
        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Video ajoutée avec succès");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

     */

    /*
    public void SupprimerFilm(Label resultat,String title) {
        String query = "DELETE FROM videos WHERE titre = '" + title + "'";
        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Video supprimée avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
         */

    public void ListePerso(VBox vBox) {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM videos");


            while (resultSet.next()) {
                Label label = new Label(resultSet.getString("titre"));
                vBox.getChildren().add(label);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void SupprimerFonc(VBox vBox) {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM videos");


            while (resultSet.next()) {
                Label label = new Label(resultSet.getString("titre"));
                vBox.getChildren().add(label);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
