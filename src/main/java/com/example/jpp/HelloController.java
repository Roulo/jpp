package com.example.jpp;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

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
        String query = "INSERT INTO compte (identifiant,mdp) VALUES (?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, identifiant);
            statement.setString(2, mdp);
            if (identifiant.isEmpty() || mdp.isEmpty()) {
                System.out.println("Error: Empty fields");
                return;
            }
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
        String identifiant = Username.getText();
        String mdp = Password.getText();

        String query = "SELECT * FROM compte WHERE identifiant=? AND mdp=?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, identifiant);
            statement.setString(2, mdp);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                resultSet = statement.executeQuery("SELECT compte.admin FROM compte WHERE compte.identifiant ='"+identifiant+"';");
                resultSet.next();

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