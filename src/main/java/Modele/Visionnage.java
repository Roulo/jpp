package Modele;

import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.sql.*;

import javafx.scene.control.*;

public class Visionnage extends DAO implements VisionnageDAO {


    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public Visionnage() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void LancerVideo(Button backBtn) {
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = "SELECT selected_video FROM compte WHERE compte.id = 3";
            ResultSet result = statement.executeQuery(sqlStatement);
            result.next();
            String video = result.getString("selected_video");
            System.out.println(video);

            sqlStatement = "SELECT status FROM status";
            result = statement.executeQuery(sqlStatement);
            result.next();
            String status = result.getString("status");
            System.out.println(status);

            WebView webView = new WebView();
            webView.setPrefSize(((AnchorPane)backBtn.getParent()).getWidth(), ((AnchorPane)backBtn.getParent()).getHeight() * 0.95);
            webView.getEngine().load("https://www.youtube.com/embed/" + video);
            ((AnchorPane)backBtn.getParent()).getChildren().add(webView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
