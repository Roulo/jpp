package Modele;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.ArrayList;

public class Compte extends DAO implements CompteDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;


    public Compte() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Affichage des genres dans la liste
    public void Charger(ObservableList<Object> genreList, ChoiceBox<Object> genre) {
        genreList.removeAll(genreList);
        String query = "SELECT type FROM genre;";
        //Requete SQL pour récupérer les genres
        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                genreList.add(rs.getString("type"));
                //Ajout au container pour l'affichage
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

        int id_now;
        ArrayList<Integer> liste = new ArrayList<>();
        int taille;

        ResultSet resultSet;
        String query = "SELECT status.id_current FROM status WHERE status.id = 1;";

        try {
            resultSet = conn.createStatement().executeQuery(query);
            resultSet.next();

            id_now = resultSet.getInt("id_current");
            query = "SELECT regarde.id__Videos FROM regarde WHERE regarde.id ="+id_now+";";

            try {
                resultSet = conn.createStatement().executeQuery(query);

                while(resultSet.next()) {
                    liste.add(resultSet.getInt("id__Videos"));
                }


                for (int i=0;i<liste.size();i++) {

                    query = "SELECT videos.titre FROM videos WHERE videos.id="+liste.get(i)+";";

                    try {
                        resultSet = conn.createStatement().executeQuery(query);
                        resultSet.next();

                        Label label = new Label(resultSet.getString("titre"));
                        vBox.getChildren().add(label);
                    }catch (SQLException j) {
                        j.printStackTrace();
                    }

                }


            }catch (SQLException f) {
                f.printStackTrace();
            }
        }catch (SQLException h) {
            h.printStackTrace();
        }
        /*
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
        */

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
