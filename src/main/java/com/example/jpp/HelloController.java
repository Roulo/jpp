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

public class HelloController extends CompteView {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public HelloController() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private TextField Username; @FXML private TextField Password;



    @FXML
    private void Sinscrire(ActionEvent event) {
        String identifiant = Username.getText();
        String mdp = Password.getText();
        //String query = "INSERT INTO compte (identifiant,mdp) VALUES (?,?)";

        Connexion connect = new Connexion();
        connect.Inscription(identifiant,mdp);

    }

    @FXML
    private void Connection(ActionEvent event) {
        String identifiant = Username.getText();
        String mdp = Password.getText();
        int testo;
        Scanner scan = new Scanner(System.in);

        Connexion connect = new Connexion();


        System.out.println("Connexion impossible");
        testo = connect.ConnexionNet(identifiant,mdp);


        if (testo==1) {
            Stage stage1 = (Stage) Username.getScene().getWindow();
            stage1.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("com/NewScreen.fxml"));
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