package Modele;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import java.sql.*;

public class Donnes implements DonnesDAO{
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
            ResultSet rs = conn.createStatement().executeQuery(query); //on récupère les genres
            while (rs.next()) {
                genreList.add(rs.getString("type"));    //on ajoute les genres dans la liste
            }
            genre.setItems(genreList); //on ajoute les genres dans la liste déroulante
            String b = "note ASC";
            String c = "note DESC";
            String d = "annee ASC";
            String e = "annee DESC";
            trierList.addAll(b, c, d, e); //on ajoute les options de tri dans la liste déroulante
            trier.setItems(trierList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
