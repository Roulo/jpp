package Modele;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Donnes {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public Donnes() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ChargerData(ChoiceBox<Object> genre, ObservableList<Object> genreList, ChoiceBox<Object> trier,ObservableList<Object> trierList) {
        String query = "SELECT DISTINCT type FROM genre;";
        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                genreList.add(rs.getString("type"));
            }
            genre.setItems(genreList);
            String b = "note ASC";
            String c = "note DESC";
            String d = "annee ASC";
            String e = "annee DESC";
            trierList.addAll(b, c, d, e);
            trier.setItems(trierList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Search(){

    }

    public void Recherche(String title,String director,String year,String genre,String trier) {
        if (title.equals("") && director.equals("") && year.equals("") && genre.equals("Tous") && trier.equals("annee DESC")){
            String query = "SELECT * FROM videos ORDER BY annee DESC;";
            try {
                ResultSet rs = conn.createStatement().executeQuery(query);
                while (rs.next()) {
                    System.out.println();
                    System.out.println(rs.getString("id"));
                    System.out.print(" ");
                    System.out.print(rs.getString("titre"));
                    System.out.print(" ");
                    System.out.print(rs.getString("realisateur"));
                    System.out.print(" ");
                    System.out.print(rs.getString("annee"));
                    System.out.print(" ");
                    System.out.print(rs.getString("note"));
                    System.out.print(" ");
                    System.out.println(rs.getString("duree"));
                    System.out.println(rs.getString("resume"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "SELECT id FROM videos WHERE titre LIKE '%" + title + "%';";
            //insert the value in a string
            String id = "";
            try {
                ResultSet rs = conn.createStatement().executeQuery(query);
                while (rs.next()) {
                    id = rs.getString("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String query2 = "SELECT DISTINCT genre.type FROM genre, videos, definit WHERE genre.id = definit.id AND definit.id__Videos = videos.id AND videos.id = " + id + ";";
            List<String> genre2 = new ArrayList<>();
            try {
                ResultSet rs = conn.createStatement().executeQuery(query2);
                while (rs.next()) {
                    genre2.add(rs.getString("type"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String query3 = "SElECT * FROM videos WHERE titre LIKE '%" + title + "%' AND realisateur LIKE '%" + director + "%' AND annee LIKE '%" + year + "%' ORDER BY " + trier + ";";
            try {
                ResultSet rs = conn.createStatement().executeQuery(query3);

                while (rs.next()) {
                    System.out.println();
                    System.out.print(id);
                    System.out.print(" ");
                    System.out.print(rs.getString("titre"));
                    System.out.print(" ");
                    for (int i = 0; i < genre2.size(); i++) {
                        System.out.print(genre2.get(i));
                        System.out.print(" ");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
