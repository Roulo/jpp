package Modele;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.sql.*;
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

    }
}
