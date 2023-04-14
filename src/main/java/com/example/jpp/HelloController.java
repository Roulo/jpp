package com.example.jpp;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloController {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/netflix?user=root&password=";
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
        String nom_utilisateur = Username.getText();
        String mot_de_passe = Password.getText();
        String query = "INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe) VALUES (?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nom_utilisateur);
            statement.setString(2, mot_de_passe);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("User registered successfully");
            } else {
                System.out.println("Error: User already exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Connection(ActionEvent event) {
        String nom_utilisateur = Username.getText();
        String mot_de_passe = Password.getText();
        String query = "SELECT * FROM utilisateurs WHERE nom_utilisateur=? AND mot_de_passe=?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nom_utilisateur);
            statement.setString(2, mot_de_passe);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("User logged in successfully");
                Stage stage1 = (Stage) Username.getScene().getWindow();
                stage1.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("NewScreen.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage2 = new Stage();
                    stage2.setTitle("New Screen");
                    stage2.setScene(new Scene(root));
                    stage2.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error: Invalid username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}