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

    //Fonction pour détecter la nature de l'utilisateur
    public int AdminCommand(){
        String quoi;
        String query = "SELECT status FROM status";
        ResultSet resultSet1;

        //Requete SQL
        try {
            resultSet1 = conn.createStatement().executeQuery(query);

            System.out.println("status repris avec succès");
            resultSet1.next();

            quoi = resultSet1.getString("status");

            //Test si l'utilisateur est admin
            if (quoi.equals("Admin")) {
                System.out.println("Vous êtes admin");
                //Enable all buttons

                return 1;

            }
            else {
                System.out.println("Vous n'êtes pas admin");
                return 0;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    };

    //Fonction pour ajouter un nouveau utilisateur
    public void AjouterClient(Label resultat,String identifiant, String mdp1, int administrateur){
        String sorte;
        if (administrateur==1) sorte = "Admin";
        else sorte = "Client";

        String query = "INSERT INTO compte (identifiant, mdp, admin, id_Liste, id_Historique, selected_video) VALUES ('" +identifiant+ "','" +mdp1+ "','" +sorte+ "', 2, 2, 'a');";

        //requete SQL pour ajouter l'utilisateur à la base de donnée
        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Compte ajoutée avec succès");
            System.out.println("Compte ajoutée avec succès");

        }catch (SQLException e) {
            e.printStackTrace();
        }
    };

    //Fonction pour supprimer un utilisateur de la base de donnée
    public void SupprimerClient(Label resultat,String identifiant){

        String query = "DELETE FROM compte WHERE identifiant = '" + identifiant + "';";
        System.out.println();

        //requete SQL pour la suppresion
        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Compte supprimé avec succès");
            System.out.println("Compte supprimé avec succès");

        }catch (SQLException e) {
            e.printStackTrace();
        }
    };
    //Ajouter un film a la base de donnée
    public void AjouterFilm(Label resultat, String title, String director, int year, int duration, String resume, String link,int note, String genre){
        String query = "INSERT INTO videos (titre, realisateur, annee, duree, resume, teaser, note, prio) VALUES ('" + title + "', '" + director + "', '" + year + "', '" + duration + "', '" + resume + "', '" + link + "', '" + note + "','0')";
        //requete SQL
        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Video ajoutée avec succès");


            int id_neutral;
            int id_genre;
            //Recupération de l'id video
            query = "SELECT videos.id FROM videos WHERE videos.titre ='"+title+"';";
            try {
                ResultSet resultSet1 = conn.createStatement().executeQuery(query);

                resultSet1.next();

                id_neutral = resultSet1.getInt("id");
                //Recuperation de l'id genre du film
                query = "SELECT genre.id FROM genre WHERE genre.type ='"+genre+"';";

                try {
                    resultSet1 = conn.createStatement().executeQuery(query);

                    resultSet1.next();

                    id_genre = resultSet1.getInt("id");
                    //Lien entre le film et son genre
                    query = "INSERT INTO definit (id, id__Videos) VALUES ("+id_genre+","+id_neutral+");";

                    try {
                        conn.createStatement().executeUpdate(query);
                        System.out.println();
                        System.out.println("Film ajoutée avec succès");
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
    //Supprimer le film de la base de donnée
    public void SupprimerFilm(Label resultat, String title){

        int id_film;

        String query = "SELECT videos.id FROM videos WHERE videos.titre='"+title+"';";
        //Recup de l'id du film
        try {
            ResultSet resultSet1 = conn.createStatement().executeQuery(query);
            resultSet1.next();

            id_film = resultSet1.getInt("id");

            //Suppression du film qui a cet id
            query = "DELETE FROM definit WHERE id__Videos="+id_film+";";
            try {
                conn.createStatement().executeUpdate(query);


                query = "DELETE FROM videos WHERE titre = '" + title + "';";
                try {
                    conn.createStatement().executeUpdate(query);
                    System.out.println();
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
    };
}
