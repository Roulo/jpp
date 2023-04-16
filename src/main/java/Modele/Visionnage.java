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
        int id_now;
        String query = "SELECT status.id_current FROM status WHERE status.id = 1;";

        try {
            ResultSet resultSet1 = conn.createStatement().executeQuery(query);
            resultSet1.next();
            id_now = resultSet1.getInt("id_current");

            try {
                Statement statement = conn.createStatement();
                String sqlStatement = "SELECT selected_video FROM compte WHERE compte.id ="+id_now+";";
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

        }catch (SQLException h) {
            h.printStackTrace();
        }

    }

    public void Reprendre(Button backBtn){


        int id_now;
        String query = "SELECT status.id_current FROM status WHERE status.id = 1;";

        try {
            ResultSet resultSet1 = conn.createStatement().executeQuery(query);
            resultSet1.next();
            id_now = resultSet1.getInt("id_current");

            try {
                Statement statement = conn.createStatement();
                String sqlStatement = "SELECT linknow FROM watchnow WHERE watchnow.id_compte ="+id_now+";";
                ResultSet result = statement.executeQuery(sqlStatement);
                result.next();
                String video = result.getString("linknow");


                WebView webView = new WebView();
                webView.setPrefSize(((AnchorPane)backBtn.getParent()).getWidth(), ((AnchorPane)backBtn.getParent()).getHeight() * 0.95);
                webView.getEngine().load("https://www.youtube.com/embed/" + video);

                ((AnchorPane)backBtn.getParent()).getChildren().add(webView);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }catch (SQLException h) {
            h.printStackTrace();
        }
    }

    public void EnregistrerTemps(){
        //ici
    }
}
