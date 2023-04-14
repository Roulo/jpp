package com.example.jpp;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.sql.*;

public class WatchView {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
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
    @FXML
    Button startBtn;

    @FXML
    private void StartVideo(){
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = "SELECT selected_video FROM compte WHERE compte.id = 3";
            ResultSet result = statement.executeQuery(sqlStatement);
            result.next();
            String video = result.getString("selected_video");
            System.out.println(video);

            WebView webView = new WebView();
            webView.setPrefSize(((AnchorPane)backBtn.getParent()).getWidth(), ((AnchorPane)backBtn.getParent()).getHeight() * 0.95);
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
}
