package com.example.jpp;

import Modele.Connexion;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

/** Page de connexion*/
public class HelloController extends HelloApplication {

    @FXML private Button Inscription;
    @FXML private Button Connexion;

    /** initialize*/
    public void initialize() {
        // Change Inscription button color
        Inscription.setStyle("-fx-background-color: lightblue");

        // Change Connexion button color
        Connexion.setStyle("-fx-background-color: lightgreen");
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";

    public HelloController() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private TextField Username; @FXML private TextField Password;


    /** Inscription*/
    @FXML
    private void Sinscrire(ActionEvent event) {
        String identifiant = Username.getText(); //identifiant
        String mdp = Password.getText(); //mot de passe
        Connexion connect = new Connexion();
        connect.Inscription(identifiant,mdp); //inscription
    }

    /** Connexion*/
    @FXML
    private void Connection(ActionEvent event) {
        String identifiant = Username.getText(); //identifiant
        String mdp = Password.getText(); //mot de passe
        int testo;
        Scanner scan = new Scanner(System.in);

        Connexion connect = new Connexion();

        System.out.println("Connexion impossible");
        testo = connect.ConnexionNet(identifiant,mdp); //connexion

        if (testo==1) {
            Stage stage1 = (Stage) Username.getScene().getWindow();
            stage1.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewScreen.fxml")); //si ok, on va à la page d'accueil
            try {
                Parent root = loader.load();
                Stage stage2 = new Stage();
                stage2.setTitle("New Screen");
                stage2.setScene(new Scene(root));
                stage2.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}