package com.example.jpp;

import com.almasb.fxgl.entity.action.Action;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CompteView {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/netflix?user=root&password=";
    private Connection conn;

    public CompteView() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            //if the user is not admin, then the button to add a new video is not visible
//            if (HelloApplication.userType.equals("admin")) {
//                ajouterBtn.setVisible(true);
//            } else {
//                ajouterBtn.setVisible(false);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private Button disconnectBtn; @FXML private Button backBtn; @FXML private Button historiqueBtn; @FXML private Button listeBtn; @FXML private Button ajouterBtn;

    @FXML
    private void Deconnection(ActionEvent event) {
        System.out.println("User disconnected.");
        Stage stage = (Stage) disconnectBtn.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Hello!");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Back(ActionEvent event){
        System.out.println("User going back to the list of videos.");
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("New Screen!");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Historique(ActionEvent event){

    }

    @FXML
    private void Liste(ActionEvent event){

    }

    @FXML
    private void Ajouter(ActionEvent event){

    }
}
