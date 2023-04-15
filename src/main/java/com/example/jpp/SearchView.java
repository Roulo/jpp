package com.example.jpp;

import Modele.Donnes;
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
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchView {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";

    private Connection conn;

    public SearchView() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private Button backBtn; @FXML private Button searchBtn;

    @FXML
    private void Back(ActionEvent event){
        System.out.println("Back to the main page.");
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Hello!");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private TextField titre; @FXML private TextField real; @FXML private TextField acteurs; @FXML private TextField annee;
    ObservableList<Object> genreList = FXCollections.observableArrayList();
    ObservableList<Object> trierList = FXCollections.observableArrayList();
    @FXML private ChoiceBox<Object> genrebox; @FXML private ChoiceBox<Object> trierbox;
    @FXML private Button loadBtn; @FXML private Button watchBtn;
    @FXML private Label resultat; @FXML private Label a; @FXML private Label b; @FXML private Label c; @FXML private Label d; @FXML private Label z;

    @FXML
    private void LoadData(ActionEvent event) {
        genreList.removeAll(genreList);
        trierList.removeAll(trierList);

        Donnes dona = new Donnes();
        dona.ChargerData(genrebox,genreList,trierbox,trierList);
    }

    @FXML
    private void Retry(ActionEvent event) {
        System.out.println("restart.");
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("search-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Netflix");
            stage1.setScene(new Scene(root));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Search() throws SQLException {
        //if the user didn't choose a genre, the genre will be "Tous"
        if (this.genrebox.getValue() == null){
            genrebox.setValue("Tous");
        }
        //if the user didn't choose a trier, the trier will be "Tous"
        if (this.trierbox.getValue() == null){
            trierbox.setValue("annee DESC");
        }
        String title = titre.getText();
        String director = real.getText();
        String year = annee.getText();
        String genre = this.genrebox.getValue().toString();
        String trier = this.trierbox.getValue().toString();

        searchBtn.setVisible(false);

        Donnes dona = new Donnes();
        if (title.equals("") && director.equals("") && year.equals("") && genre.equals("Tous") && trier.equals("annee DESC")){
            String query1 = "SELECT * FROM videos ORDER BY annee DESC;";
            try {
                ResultSet rs = conn.createStatement().executeQuery(query1);
                while (rs.next()) {
                    System.out.println();
                    System.out.println(rs.getString("id"));
                    System.out.print(" ");
                    System.out.print(rs.getString("titre"));
                    System.out.print(" ");
                    System.out.print(rs.getString("realisateur"));
                    System.out.print(" ");
                    System.out.print(rs.getString("annee"));
                    System.out.print(" ");
                    System.out.print(rs.getString("note"));
                    System.out.print(" ");
                    System.out.println(rs.getString("duree"));
                    System.out.println(rs.getString("resume"));
                }
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query1);

                VBox vBox = new VBox();
                vBox.setPadding(new Insets(10));
                vBox.setSpacing(10);

                while (resultSet.next()) {
                    Label label = new Label(resultSet.getString("titre"));
                    label.setOnMouseClicked(event -> {
                        System.out.println(label.getText() + " selected");
                        titre.setVisible(false);
                        real.setVisible(false);
                        annee.setVisible(false);
                        searchBtn.setVisible(false);
                        loadBtn.setVisible(false);
                        a.setVisible(false);
                        b.setVisible(false);
                        c.setVisible(false);
                        d.setVisible(false);
                        z.setVisible(false);
                        genrebox.setVisible(false);
                        trierbox.setVisible(false);

                        try {
                            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM videos WHERE titre='" + label.getText() + "'");

                            resultSet1.next();
                            String titre = resultSet1.getString("titre");
                            String resume = resultSet1.getString("resume");
                            String date = resultSet1.getString("annee");
                            String duree = resultSet1.getString("duree");
                            String realisateur = resultSet1.getString("realisateur");
                            String note = resultSet1.getString("note");
                            String idd = resultSet1.getString("id");
                            String teaser = resultSet1.getString("teaser");

                            WebView webView = new WebView();
                            webView.setPrefSize(800, 450);
                            webView.getEngine().load("https://www.youtube.com/embed/" + teaser);
                            ((AnchorPane)resultat.getParent()).getChildren().add(webView);

                            resultSet1 = statement.executeQuery("SELECT DISTINCT genre.type FROM genre, videos, definit WHERE genre.id = definit.id AND definit.id__Videos = '" + idd + "'" );
                            resultSet1.next();
                            String categorie = resultSet1.getString("type");

                            statement.executeUpdate("UPDATE compte SET selected_video = '" + teaser + "' WHERE id = '3'");

                            ((AnchorPane)resultat.getParent()).getChildren().remove(vBox);
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
                            ((AnchorPane)backBtn.getParent()).getChildren().add(vBox1);

                            ((AnchorPane)resultat.getParent()).getChildren().remove(vBox);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    vBox.getChildren().add(label);
                }
                ((AnchorPane)resultat.getParent()).getChildren().add(vBox);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String query = "SELECT id FROM videos WHERE titre LIKE '%" + title + "%';";
            //insert the value in a string
            String id = "";
            try {
                ResultSet rs = conn.createStatement().executeQuery(query);
                while (rs.next()) {
                    id = rs.getString("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String query2 = "SELECT DISTINCT genre.type FROM genre, videos, definit WHERE genre.id = definit.id AND definit.id__Videos = videos.id AND videos.id = " + id + ";";
            List<String> genre2 = new ArrayList<>();
            try {
                ResultSet rs = conn.createStatement().executeQuery(query2);
                while (rs.next()) {
                    genre2.add(rs.getString("type"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String query3 = "SElECT * FROM videos WHERE titre LIKE '%" + title + "%' AND realisateur LIKE '%" + director + "%' AND annee LIKE '%" + year + "%' ORDER BY " + trier + ";";
            try {
                ResultSet rs = conn.createStatement().executeQuery(query3);

                while (rs.next()) {
                    System.out.println();
                    System.out.print(id);
                    System.out.print(" ");
                    System.out.print(rs.getString("titre"));
                    System.out.print(" ");
                    for (int i = 0; i < genre2.size(); i++) {
                        System.out.print(genre2.get(i));
                        System.out.print(" ");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query3);

                VBox vBox = new VBox();
                vBox.setPadding(new Insets(10));
                vBox.setSpacing(10);

                while (resultSet.next()) {
                    Label label = new Label(resultSet.getString("titre"));
                    label.setOnMouseClicked(event -> {
                        System.out.println(label.getText() + " selected");
                        titre.setVisible(false);real.setVisible(false);annee.setVisible(false);searchBtn.setVisible(false);loadBtn.setVisible(false);a.setVisible(false);b.setVisible(false);c.setVisible(false);d.setVisible(false);z.setVisible(false);genrebox.setVisible(false);trierbox.setVisible(false);
                        try {
                            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM videos WHERE titre='" + label.getText() + "'");

                            resultSet1.next();
                            String titre = resultSet1.getString("titre");
                            String resume = resultSet1.getString("resume");
                            String date = resultSet1.getString("annee");
                            String duree = resultSet1.getString("duree");
                            String realisateur = resultSet1.getString("realisateur");
                            String note = resultSet1.getString("note");
                            String idd = resultSet1.getString("id");
                            String teaser = resultSet1.getString("teaser");

                            WebView webView = new WebView();
                            webView.setPrefSize(800, 450);
                            webView.getEngine().load("https://www.youtube.com/embed/" + teaser);
                            ((AnchorPane) resultat.getParent()).getChildren().add(webView);

                            resultSet1 = statement.executeQuery("SELECT DISTINCT genre.type FROM genre, videos, definit WHERE genre.id = definit.id AND definit.id__Videos = '" + idd + "'");
                            resultSet1.next();
                            String categorie = resultSet1.getString("type");

                            statement.executeUpdate("UPDATE compte SET selected_video = '" + teaser + "' WHERE id = '3'");

                            ((AnchorPane) resultat.getParent()).getChildren().remove(vBox);
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
                            ((AnchorPane) backBtn.getParent()).getChildren().add(vBox1);

                            ((AnchorPane) resultat.getParent()).getChildren().remove(vBox);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    vBox.getChildren().add(label);
                }
                ((AnchorPane) resultat.getParent()).getChildren().add(vBox);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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


}
