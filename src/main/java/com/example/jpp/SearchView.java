package com.example.jpp;

import Modele.Donnes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SearchView {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public SearchView() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private Button backBtn; @FXML private Button searchBtn;

    @FXML
    private void Back(ActionEvent event){
        System.out.println("Back to the main page.");
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Hello!");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private TextField titre; @FXML private TextField real; @FXML private TextField acteurs; @FXML private TextField annee;
    ObservableList<Object> genreList = FXCollections.observableArrayList();
    ObservableList<Object> trierList = FXCollections.observableArrayList();
    @FXML private ChoiceBox<Object> genre; @FXML private ChoiceBox<Object> trier;
    @FXML private Button loadBtn;

    @FXML
    private void LoadData(ActionEvent event) {
        genreList.removeAll(genreList);
        trierList.removeAll(trierList);

        Donnes dona = new Donnes();
        dona.ChargerData(genre,genreList,trier,trierList);
    }

    @FXML
    private void Search(ActionEvent event) {
        //if the user didn't choose a genre, the genre will be "Tous"
        if (this.genre.getValue() == null){
            genre.setValue("Tous");
        }
        //if the user didn't choose a trier, the trier will be "Tous"
        if (this.trier.getValue() == null){
            trier.setValue("annee DESC");
        }
        String title = titre.getText();
        String director = real.getText();
        String year = annee.getText();
        String genre = this.genre.getValue().toString();
        String trier = this.trier.getValue().toString();

        Donnes dona = new Donnes();
        dona.Search();
        dona.Recherche(title,director,year,genre,trier);
    }
}
