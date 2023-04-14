package com.example.jpp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CompteView {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/netflix?user=root&password=";
    private Connection conn;

    public CompteView() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private Button disconnectBtn; @FXML private Button backBtn; @FXML private Button historiqueBtn; @FXML private Button listeBtn;
    @FXML private Button ajouterBtn;
    @FXML private Label resultat;
    ObservableList<Object> genreList = FXCollections.observableArrayList();
    @FXML private ChoiceBox<Object> genre;
    @FXML private Button loadBtn; @FXML private Button addBtn;
    @FXML private TextField titre;
    @FXML private TextField real;
    @FXML private TextField acteurs;
    @FXML private TextField annee;
    @FXML private TextField resume;
    @FXML private TextField lien;
    @FXML private TextField duree;

    @FXML
    private void LoadData(ActionEvent event) {
        genreList.removeAll(genreList);
        String query = "SELECT DISTINCT categorie FROM videos;";
        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                genreList.add(rs.getString("categorie"));
            }
            genre.setItems(genreList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Ajouter(ActionEvent event){
        //if the user is not an admin, show an error message
    }

    @FXML
    private void Add(ActionEvent event){
        String title = titre.getText();
        String director = real.getText();
        String actor = acteurs.getText();
        String year = annee.getText();
        String resume = this.resume.getText();
        String link = lien.getText();
        String duration = duree.getText();
        String genre = this.genre.getValue().toString();
        String query = "INSERT INTO videos (titre, realisateur, acteurs, annee, resume, lien, duree, categorie) VALUES ('" + title + "', '" + director + "', '" + actor + "', '" + year + "', '" + resume + "', '" + link + "', '" + duration + "', '" + genre + "')";
        try {
            conn.createStatement().executeUpdate(query);
            resultat.setText("Video ajoutée avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
    private void Historique(){
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM videos");

            VBox vBox = new VBox();
            vBox.setPadding(new Insets(10));
            vBox.setSpacing(10);

            while (resultSet.next()) {
                Label label = new Label(resultSet.getString("titre"));
                vBox.getChildren().add(label);
            }
            ((AnchorPane)resultat.getParent()).getChildren().add(vBox);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Liste(){
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM videos");

            VBox vBox = new VBox();
            vBox.setPadding(new Insets(10));
            vBox.setSpacing(10);

            //insert where the label resultat is the title of the videos
            while (resultSet.next()) {
                Label label = new Label(resultSet.getString("titre"));
                vBox.getChildren().add(label);
            }
            ((AnchorPane)resultat.getParent()).getChildren().add(vBox);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
