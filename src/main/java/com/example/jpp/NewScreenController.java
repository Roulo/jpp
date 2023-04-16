package com.example.jpp;

import Modele.ClientFonction;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class NewScreenController {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;
    public NewScreenController() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private Button disconnectBtn;@FXML private Button watchBtn;@FXML private Button backBtn;
    @FXML private Button profileBtn; @FXML private Button displayBtn;

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
        int id_now;
        String query = "SELECT status.id_current FROM status WHERE status.id = 1;";

        try {
            ResultSet resultSet2 = conn.createStatement().executeQuery(query);
            resultSet2.next();
            id_now = resultSet2.getInt("id_current");

            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT titre FROM videos");

                VBox vBox = new VBox();
                vBox.setPadding(new Insets(10));
                vBox.setSpacing(10);

                displayBtn.setVisible(false);


                GridPane gridPane = new GridPane();
                int row = 0;
                int col = 0;
                while (resultSet.next()) {
                    Label label = new Label(resultSet.getString("titre"));
                    String title = resultSet.getString("titre");
                    File file = new File("C:\\Users\\zheng\\Bureau\\Bureau\\jpp\\src\\main\\images\\" + title + ".jpg");
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);

                    label.setOnMouseClicked(event -> {
                        System.out.println(label.getText() + " selected");
                        try {
                            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM videos WHERE titre='" + label.getText() + "'");

                            resultSet1.next();
                            String titre = resultSet1.getString("titre");
                            String resume = resultSet1.getString("resume");
                            String date = resultSet1.getString("annee");
                            String duree = resultSet1.getString("duree");
                            String realisateur = resultSet1.getString("realisateur");
                            String note = resultSet1.getString("note");
                            String id = resultSet1.getString("id");
                            String teaser = resultSet1.getString("teaser");

                            WebView webView = new WebView();
                            webView.setPrefSize(800, 450);
                            webView.getEngine().load("https://www.youtube.com/embed/" + teaser);
                            ((AnchorPane)disconnectBtn.getParent()).getChildren().add(webView);

                            resultSet1 = statement.executeQuery("SELECT DISTINCT genre.type FROM genre, videos, definit WHERE genre.id = definit.id AND definit.id__Videos = '" + id + "'" );
                            resultSet1.next();
                            String categorie = resultSet1.getString("type");

                            statement.executeUpdate("UPDATE compte SET selected_video = '" + teaser + "' WHERE id = "+id_now+";");

                            System.out.println("Titre: " + titre);
                            System.out.println("Résumé: " + resume);
                            System.out.println("Catégorie: " + categorie);
                            System.out.println("Date: " + date);
                            System.out.println("Durée: " + duree);
                            System.out.println("Réalisateur: " + realisateur);
                            System.out.println("Note: " + note);

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
                            vBox1.getChildren().add(new Label("Note: " + note));
                            ((AnchorPane)watchBtn.getParent()).getChildren().add(vBox1);

                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
///
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(135.5);
                    gridPane.add(label, col, row);
                    gridPane.add(imageView, col, row+1);
                    col++;
                    if (col == 4) {
                        col = 0;
                        row += 2;
                    }
                }
                vBox.getChildren().add(gridPane);
                ((AnchorPane)disconnectBtn.getParent()).getChildren().add(vBox);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }catch (SQLException h) {
            h.printStackTrace();
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
        Stage stage = (Stage) watchBtn.getScene().getWindow();
        stage.close();
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
    }

    @FXML
    private void AjouterPlaylist(ActionEvent actionEvent) {
        //ici
        ClientFonction tech = new ClientFonction();

        tech.AjouterFilmPlaylist();

    }
}