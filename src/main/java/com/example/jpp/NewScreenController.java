package com.example.jpp;

import com.almasb.fxgl.entity.action.Action;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.sql.*;

public class NewScreenController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/netflix?user=root&password=";
    private Connection conn;

    public NewScreenController() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private Font x1;@FXML private Color x2;@FXML private Font x3;@FXML private Color x4;

    @FXML private Button disconnectBtn;@FXML private Button watchBtn;@FXML private Button backBtn;

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
    private void displayVideos() {
        try {
            // create a Statement object
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT titre FROM videos");

            VBox vBox = new VBox();
            vBox.setPadding(new Insets(10));
            vBox.setSpacing(10);

            while (resultSet.next()) {
                Label label = new Label(resultSet.getString("titre"));
                label.setOnMouseClicked(event -> {
                    System.out.println(label.getText() + " selected");
                    try {
                        ResultSet resultSet1 = statement.executeQuery("SELECT * FROM videos WHERE titre='" + label.getText() + "'");
                        resultSet1.next();
                        String titre = resultSet1.getString("titre");
                        String resume = resultSet1.getString("resume");
                        String categorie = resultSet1.getString("categorie");
                        String date = resultSet1.getString("annee");
                        String duree = resultSet1.getString("duree");
                        String realisateur = resultSet1.getString("realisateur");


                        System.out.println("Titre: " + titre);
                        System.out.println("Résumé: " + resume);
                        System.out.println("Catégorie: " + categorie);
                        System.out.println("Date: " + date);
                        System.out.println("Durée: " + duree);
                        System.out.println("Réalisateur: " + realisateur);

                        // display those details

                        ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox);
                        VBox vBox1 = new VBox();
                        vBox1.setPadding(new Insets(10));
                        vBox1.setSpacing(10);
                        vBox1.getChildren().add(new Label("Titre: " + titre));
                        vBox1.getChildren().add(new Label("Résumé: " + resume));
                        vBox1.getChildren().add(new Label("Catégorie: " + categorie));
                        vBox1.getChildren().add(new Label("Date: " + date));
                        vBox1.getChildren().add(new Label("Durée: " + duree));
                        vBox1.getChildren().add(new Label("Réalisateur: " + realisateur));
                        // display the VBox in the second pane of the split pane
                        ((AnchorPane)watchBtn.getParent()).getChildren().add(vBox1);

                        ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox);

                        // create a WebView
                        WebView webView = new WebView();
                        webView.setPrefSize(800, 450);
                        webView.getEngine().load("https://www.youtube.com/embed/" + resultSet1.getString("teaser"));
                        ((AnchorPane)disconnectBtn.getParent()).getChildren().add(webView);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                vBox.getChildren().add(label);
            }

            // display the VBox in the first pane
            ((AnchorPane)disconnectBtn.getParent()).getChildren().add(vBox);

        } catch (SQLException e) {
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
    private void Watch(ActionEvent event) {
        System.out.println("User watching a video.");
        //close the current window
        Stage stage = (Stage) watchBtn.getScene().getWindow();
        stage.close();
        //change fxml file
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("watch-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Watch!");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Compte(ActionEvent event){
        System.out.println("User going to his account.");
        Stage stage = (Stage) watchBtn.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("compte-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Compte!");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Search(ActionEvent event){
        System.out.println("User searching a video.");
        Stage stage = (Stage) watchBtn.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Search!");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("User searching a video.");
    }
}