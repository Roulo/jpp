package com.example.jpp;

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

    //the url for the MySQL database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/netflix?user=root&password=";
    private Connection conn;

    public NewScreenController() {
        try {
            //establish a connection to the MySQL database
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private Font x1;@FXML private Color x2;@FXML private Font x3;@FXML private Color x4;

    @FXML
    private Button disconnectBtn;
    public Button watchBtn;

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
                        webView.getEngine().load("https://www.youtube.com/embed/" + resultSet1.getString("teaser"));
                        ((AnchorPane)disconnectBtn.getParent()).getChildren().add(webView);

                        // create a button to go back to the list of videos
                        Button backBtn = new Button("Go back");
                        backBtn.setOnAction(event1 -> {
                            // remove the video from the third pane
                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(webView);
                            // remove the button from the third pane
                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(backBtn);
                            // display the list of videos in the second pane
                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox1);
                            ((AnchorPane)disconnectBtn.getParent()).getChildren().add(vBox);
                        });

                        ((AnchorPane)disconnectBtn.getParent()).getChildren().add(backBtn);

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
}