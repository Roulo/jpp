package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientFonction implements ClientDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public ClientFonction() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Maj de la note dans la base de donnée
    public void Notage(float note){
        int id_now;
        String video_now;
        int id_video_now;
        float note_tempo;
        float moy;


        //recup de l'identité de l'utilisateur
        ResultSet resultSet;
        String query = "SELECT status.id_current FROM status WHERE status.id = 1;";

        try {
            resultSet = conn.createStatement().executeQuery(query);
            resultSet.next();

            id_now = resultSet.getInt("id_current");
            query = "SELECT compte.selected_video FROM compte WHERE compte.id = "+id_now+";";
            //On retrouve la video qu'il souhaite noter
            try {
                resultSet = conn.createStatement().executeQuery(query);
                resultSet.next();
                video_now = resultSet.getString("selected_video");
                query = "SELECT videos.id FROM videos WHERE videos.teaser='"+video_now+"';";

                try {
                    resultSet = conn.createStatement().executeQuery(query);
                    resultSet.next();
                    id_video_now = resultSet.getInt("id");

                    query = "UPDATE notage SET note="+note+"WHERE id_compte="+id_now+" AND id_video="+id_video_now+";";
                    //On update sa note dans la base de donnée
                    try {
                        conn.createStatement().executeUpdate(query);
                        System.out.println();
                        System.out.println("Note ajoutée avec succès");
                        query = "SELECT videos.note FROM videos WHERE videos.id="+id_video_now+";";

                        try {
                            resultSet = conn.createStatement().executeQuery(query);
                            resultSet.next();
                            note_tempo = resultSet.getFloat("note");

                            moy = (note_tempo+note)/2;

                            query = "UPDATE videos SET note="+moy+"WHERE videos.id="+id_video_now+";";
                            //On modifie la note moyenne de la video
                            try {
                                conn.createStatement().executeUpdate(query);
                                System.out.println();
                                System.out.println("Note update");
                            }catch (SQLException m) {
                                m.printStackTrace();
                            }
                        }catch (SQLException l) {
                            l.printStackTrace();
                        }
                    }catch (SQLException h) {
                        h.printStackTrace();
                    }
                }catch (SQLException g) {
                    g.printStackTrace();
                }
            }catch (SQLException f) {
                f.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    };

    //On ajoute un film a la playlist
    public void AjouterFilmPlaylist() {

        int id_now;
        String video_now;
        int id_video_now;
        ResultSet resultSet;
        String query = "SELECT status.id_current FROM status WHERE status.id = 1;";
        //Recup de l'identité de l'utilisateur
        try {
            resultSet = conn.createStatement().executeQuery(query);
            resultSet.next();

            id_now = resultSet.getInt("id_current");
            query = "SELECT compte.selected_video FROM compte WHERE compte.id = "+id_now+";";
            //recup du film correspondant dans la base de donnée
            try {
                resultSet = conn.createStatement().executeQuery(query);
                resultSet.next();
                video_now = resultSet.getString("selected_video");
                query = "SELECT videos.id FROM videos WHERE videos.teaser='"+video_now+"';";

                try {
                    resultSet = conn.createStatement().executeQuery(query);
                    resultSet.next();
                    id_video_now = resultSet.getInt("id");

                    query = "INSERT INTO regarde (id, id__Videos) VALUES ("+id_now+","+id_video_now+");";
                    //Ajout du film à la playlist de l'utilisateur
                    try {
                        conn.createStatement().executeUpdate(query);
                        System.out.println();
                        System.out.println("Video ajoutée à la playlist avec succès");
                    }catch (SQLException h) {
                        h.printStackTrace();
                    }
                }catch (SQLException g) {
                    g.printStackTrace();
                }

            }catch (SQLException f) {
                f.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Suppresion d'un film de la playlist
    public void SupprimerFilmPlaylist() {
        int id_now;
        String video_now;
        int id_video_now;
        ResultSet resultSet;
        String query = "SELECT status.id_current FROM status WHERE status.id = 1;";
        //Recup de l'identité de l'utilisateur
        try {
            resultSet = conn.createStatement().executeQuery(query);
            resultSet.next();

            id_now = resultSet.getInt("id_current");
            query = "SELECT compte.selected_video FROM compte WHERE compte.id = "+id_now+";";
            //recup de la donnée du film correspondant
            try {
                resultSet = conn.createStatement().executeQuery(query);
                resultSet.next();
                video_now = resultSet.getString("selected_video");
                query = "SELECT videos.id FROM videos WHERE videos.teaser='"+video_now+"';";

                try {
                    resultSet = conn.createStatement().executeQuery(query);
                    resultSet.next();
                    id_video_now = resultSet.getInt("id");

                    query = "DELETE FROM regarde WHERE regarde.id="+id_now+" AND regarde.id__Videos="+id_video_now+";";
                    //Suppresion du film de la table playlist de l'utilisateur
                    try {
                        conn.createStatement().executeUpdate(query);
                        System.out.println();
                        System.out.println("Video supprimé de la playlist avec succès");
                    }catch (SQLException h) {
                        h.printStackTrace();
                    }
                }catch (SQLException g) {
                    g.printStackTrace();
                }

            }catch (SQLException f) {
                f.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Sauvegarde du watchtime d'une video
    public void SauvegardeWatchTime(int time) {

        int id_now;
        String video_now;
        int id_video_now;
        ResultSet resultSet;
        String query = "SELECT status.id_current FROM status WHERE status.id = 1;";
        //recup de l'identité de l'utilisateur
        try {
            resultSet = conn.createStatement().executeQuery(query);
            resultSet.next();

            id_now = resultSet.getInt("id_current");
            query = "SELECT compte.selected_video FROM compte WHERE compte.id = "+id_now+";";
            //recup de la video correspondante
            try {
                resultSet = conn.createStatement().executeQuery(query);
                resultSet.next();
                video_now = resultSet.getString("selected_video");
                query = "SELECT videos.id FROM videos WHERE videos.teaser='"+video_now+"';";

                try {
                    resultSet = conn.createStatement().executeQuery(query);
                    resultSet.next();
                    id_video_now = resultSet.getInt("id");

                    String video_en_cours = video_now+"?start="+time;
                    query = "UPDATE watchnow SET id_compte="+id_now+", linknow='"+video_en_cours+"' WHERE id="+id_now+";";
                    //Creation et enregistrement du lien de video modifie pour le watchtime
                    try {
                        conn.createStatement().executeUpdate(query);
                        System.out.println();
                        System.out.println("WatchTime sauvegardé avec succès");
                    }catch (SQLException h) {
                        h.printStackTrace();
                    }
                }catch (SQLException g) {
                    g.printStackTrace();
                }

            }catch (SQLException f) {
                f.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
