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

    public void AdminCommand(){
        String query = "SELECT status FROM status";
        if (query == "Admin") {
            System.out.println("Vous êtes admin");
            //Enable all buttons

        }
        else {
            System.out.println("Vous n'êtes pas admin");
        }
    };

    public int AdminStatus(){
        return 0;
    }


    public void AjouterClient(Label resultat,String identifiant, String mdp1, int administrateur){
        String sorte;
        if (administrateur==1) {
            sorte = "Admin";
        }
        else {
            sorte = "Client";
        }

        String query = "INSERT INTO compte (identifiant, mdp, admin, qualite, sous_titres, id_Liste, id_Historique, selected_video) VALUES ('" +identifiant+ "','" +mdp1+ "','" +sorte+ "', 0, 'nul', 2, 2, 'a');";

        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Compte ajoutée avec succès");
            System.out.println("Compte ajoutée avec succès");

        }catch (SQLException e) {
            e.printStackTrace();
        }
    };
    public void SupprimerClient(Label resultat,String identifiant){

        String query = "DELETE FROM compte WHERE identifiant = '" + identifiant + "';";

        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Compte supprimé avec succès");
            System.out.println("Compte supprimé avec succès");

        }catch (SQLException e) {
            e.printStackTrace();
        }

    };
    public void AjouterFilm(Label resultat, String title, String director, int year, int duration, String resume, String link,int note, String genre){
        String query = "INSERT INTO videos (titre, realisateur, annee, duree, resume, teaser, note) VALUES ('" + title + "', '" + director + "', '" + year + "', '" + duration + "', '" + resume + "', '" + link + "', '" + note + "')";
        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Video ajoutée avec succès");
            System.out.println("Video ajoutée avec succès");

            int id_neutral;
            int id_genre;

            query = "SELECT videos.id FROM videos WHERE videos.titre ='"+title+"';";
            try {
                ResultSet resultSet1 = conn.createStatement().executeQuery(query);
                System.out.println("id_video récupéré avec succès");
                resultSet1.next();

                id_neutral = resultSet1.getInt("id");
                //System.out.println("id_vid="+id_neutral);
                query = "SELECT genre.id FROM genre WHERE genre.type ='"+genre+"';";

                try {
                    resultSet1 = conn.createStatement().executeQuery(query);
                    System.out.println("id_genre récupéré avec succès");
                    resultSet1.next();

                    id_genre = resultSet1.getInt("id");
                    //System.out.println("id_genre="+id_genre);
                    query = "INSERT INTO definit (id, id__Videos) VALUES ("+id_genre+","+id_neutral+");";

                    try {
                        conn.createStatement().executeUpdate(query);
                        System.out.println("Genre inséré avec succès");
                    } catch (SQLException h) {
                        h.printStackTrace();
                    }

                } catch (SQLException g) {
                    g.printStackTrace();
                }


            } catch (SQLException f) {
                f.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    };

    public void SupprimerFilm(Label resultat, String title){

        int id_film;

        String query = "SELECT videos.id FROM videos WHERE videos.titre='"+title+"';";

        try {
            ResultSet resultSet1 = conn.createStatement().executeQuery(query);
            resultSet1.next();

            id_film = resultSet1.getInt("id");
            System.out.println("Id_film récup avec succès");

            query = "DELETE FROM definit WHERE id__Videos="+id_film+";";
            try {
                conn.createStatement().executeUpdate(query);
                System.out.println("Suppresion des genres avec succès");

                query = "DELETE FROM videos WHERE titre = '" + title + "';";
                try {
                    conn.createStatement().executeUpdate(query);
                    System.out.println("Suppresion de la video avec succès");
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }catch (SQLException g) {
                g.printStackTrace();
            }
        }catch (SQLException f) {
            f.printStackTrace();
        }

        /*
        String query1 = "DELETE FROM videos WHERE titre = '" + title + "';";
        try {
            conn.createStatement().executeUpdate(query1);
            resultat.setText("Video supprimée avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }

         */

    };


}
