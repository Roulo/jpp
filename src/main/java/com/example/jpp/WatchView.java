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

public class WatchView {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/netflix?user=root&password=";
    private Connection conn;

    public WatchView() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button backBtn;

    @FXML Button startBtn;

    @FXML
    private void StartVideo(){
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = "SELECT selected_video FROM utilisateurs WHERE id = 1";
            ResultSet result = statement.executeQuery(sqlStatement);
            result.next();
            String video = result.getString("selected_video");
            System.out.println(video);
            //webview
            WebView webView = new WebView();
            webView.getEngine().load("https://www.youtube.com/embed/" + video);
            ((AnchorPane)backBtn.getParent()).getChildren().add(webView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Back(ActionEvent event) {
        System.out.println("User disconnected.");
        Stage stage = (Stage) backBtn.getScene().getWindow();
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
}
