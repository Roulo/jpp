package com.example.jpp;

import Modele.ClientFonction;
import Modele.Visionnage;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/** Page de visionnage*/
public class WatchView extends CompteView {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public WatchView() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private Button backBtn; @FXML Button startBtn;
    @FXML private TextField temps;

    /** Démarrer la vidéo*/
    @FXML
    private void StartVideo(){
        Visionnage vision = new Visionnage();
        vision.LancerVideo(backBtn);
    }

    /** Reprendre la vidéo*/
    @FXML
    private void ReprendreVid(){
        Visionnage vision = new Visionnage();
        vision.Reprendre(backBtn);
    }

    /** Retourner à la page d'accueil*/
    @FXML
    private void Back(ActionEvent event) {
        System.out.println("User disconnected.");
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Netflix");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    /** Enregistrer le temps de visionnage*/
    private void EnregistrerTemps(ActionEvent actionEvent) {
        String timer = temps.getText(); //choper le temps de visionnage en secondes en un String
        int time = Integer.parseInt(timer);
        ClientFonction tech = new ClientFonction();

        tech.SauvegardeWatchTime(time);
    }
}
