package com.example.jpp;

import Modele.Client;
import Modele.ClientFonction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/** Page d'accueil*/
public class NewScreenController {
    @FXML private Button disconnectBtn;@FXML private Button watchBtn;@FXML private Button backBtn;@FXML private Button SupprimerBtn;
    @FXML private Button profileBtn; @FXML private Button displayBtn; @FXML private Button AjouterBtn; @FXML private Button noteBtn; @FXML private MenuButton noteMenu;
    @FXML private TextField noteField; @FXML private Label noteLabel;
    @FXML private ScrollPane scrollPane;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;
    public NewScreenController() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // displayVideos method
    /** initialize*/
    @FXML
    private void initialize() {
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


                // GridPane
                GridPane gridPane = new GridPane();
                gridPane.setHgap(20);
                gridPane.setVgap(25);

                int row = 0;
                int col = 0;
                int videosDisplayed = 0;
                while (resultSet.next() && videosDisplayed < 12) {
                    Label label = new Label(resultSet.getString("titre"));
                    String title = resultSet.getString("titre");
                    File file = new File("src\\main\\images\\" + title + ".jpg");
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(130);imageView.setFitHeight(176.15);
                    gridPane.add(label, col, row);
                    gridPane.add(imageView, col, row+1);
                    col++;
                    if (col == 4) {
                        col = 0;
                        row += 2;
                    }
                    videosDisplayed++;
                    /////////////////////////////
                    label.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        scrollPane.setVvalue(0);
                        watchBtn.setVisible(true);

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

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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

                    imageView.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        scrollPane.setVvalue(0);
                        watchBtn.setVisible(true);

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

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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
                    /////////////////////////////
                }
                vBox.getChildren().add(gridPane);
                // End of GridPane




                // Prio 1
                vBox.getChildren().add(new Label(" "));
                vBox.getChildren().add(new Label(" "));
                Label label123 = new Label("Tendance du moment");
                label123.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
                vBox.getChildren().add(label123);
                ResultSet resultSetPrio = statement.executeQuery("SELECT titre FROM videos WHERE prio='1'");

                GridPane gridPanePrio = new GridPane();
                gridPanePrio.setHgap(20);
                gridPanePrio.setVgap(25);

                int rowPrio = 0;
                int colPrio = 0;
                int videosDisplayedPrio = 0;
                while (resultSetPrio.next() && videosDisplayedPrio < 12) {
                    Label label = new Label(resultSetPrio.getString("titre"));
                    String title = resultSetPrio.getString("titre");
                    File file = new File("src\\main\\images\\" + title + ".jpg");
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(130);imageView.setFitHeight(176.15);
                    gridPanePrio.add(label, colPrio, rowPrio);
                    gridPanePrio.add(imageView, colPrio, rowPrio+1);
                    colPrio++;
                    if (colPrio == 4) {
                        colPrio = 0;
                        rowPrio += 2;
                    }
                    videosDisplayedPrio++;
                    /////////////////////////////
                    label.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        scrollPane.setVvalue(0);
                        watchBtn.setVisible(true);

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

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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

                    imageView.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        scrollPane.setVvalue(0);
                        watchBtn.setVisible(true);

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

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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
                    /////////////////////////////
                }
                vBox.getChildren().add(gridPanePrio);





                // Display videos with genre="Drame"
                vBox.getChildren().add(new Label(" "));
                vBox.getChildren().add(new Label(" "));
                Label labelDrame = new Label("Drame");
                labelDrame.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
                vBox.getChildren().add(labelDrame);
                // Display videos with genre="Drame"
                ResultSet resultSetDrame = statement.executeQuery("SELECT DISTINCT videos.titre FROM videos, genre, definit WHERE videos.id= definit.id__Videos AND definit.id=10;");

                GridPane gridPaneDrame = new GridPane();
                gridPaneDrame.setHgap(20);
                gridPaneDrame.setVgap(25);

                int rowDrame = 0;
                int colDrame = 0;
                int videosDisplayedDrame = 0;
                while (resultSetDrame.next() && videosDisplayedDrame < 12) {
                    Label label = new Label(resultSetDrame.getString("titre"));
                    String title = resultSetDrame.getString("titre");
                    File file = new File("src\\main\\images\\" + title + ".jpg");
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(130);imageView.setFitHeight(176.15);
                    gridPaneDrame.add(label, colDrame, rowDrame);
                    gridPaneDrame.add(imageView, colDrame, rowDrame+1);
                    colDrame++;
                    if (colDrame == 4) {
                        colDrame = 0;
                        rowDrame += 2;
                    }
                    videosDisplayedDrame++;
                    /////////////////////////////
                    label.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        //return at the beginning of the scrollpane
                        scrollPane.setVvalue(0);
                        watchBtn.setVisible(true);

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
                            System.out.println("Teaser: " + teaser);

                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox);
                            VBox vBox1 = new VBox();
                            vBox1.setPadding(new Insets(10));
                            vBox1.setSpacing(10);
                            vBox1.getChildren().add(new Label("Titre: " + titre));

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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

                    imageView.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        scrollPane.setVvalue(0);
                        watchBtn.setVisible(true);

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
                            System.out.println("Teaser: " + teaser);

                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox);
                            VBox vBox1 = new VBox();
                            vBox1.setPadding(new Insets(10));
                            vBox1.setSpacing(10);
                            vBox1.getChildren().add(new Label("Titre: " + titre));

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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
                    /////////////////////////////
                }
                vBox.getChildren().add(gridPaneDrame);



                // Display videos with genre="Action"
                vBox.getChildren().add(new Label(" "));
                vBox.getChildren().add(new Label(" "));
                Label labelAction = new Label("Action");
                labelAction.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
                vBox.getChildren().add(labelAction);
                // Display videos with genre="Action"
                ResultSet resultSetAction = statement.executeQuery("SELECT DISTINCT videos.titre FROM videos, genre, definit WHERE videos.id= definit.id__Videos AND definit.id=1;");

                GridPane gridPaneAction = new GridPane();
                gridPaneAction.setHgap(20);
                gridPaneAction.setVgap(25);

                int rowAction = 0;
                int colAction = 0;
                int videosDisplayedAction = 0;
                while (resultSetAction.next() && videosDisplayedAction < 12) {
                    Label label = new Label(resultSetAction.getString("titre"));
                    String title = resultSetAction.getString("titre");
                    File file = new File("src\\main\\images\\" + title + ".jpg");
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(130);imageView.setFitHeight(176.15);
                    gridPaneAction.add(label, colAction, rowAction);
                    gridPaneAction.add(imageView, colAction, rowAction+1);
                    colAction++;
                    if (colAction == 4) {
                        colAction = 0;
                        rowAction += 2;
                    }
                    videosDisplayedAction++;
                    /////////////////////////////
                    label.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        //return at the beginning of the scrollpane
                        scrollPane.setVvalue(0);
                        watchBtn.setVisible(true);

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
                            System.out.println("Teaser: " + teaser);

                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox);
                            VBox vBox1 = new VBox();
                            vBox1.setPadding(new Insets(10));
                            vBox1.setSpacing(10);
                            vBox1.getChildren().add(new Label("Titre: " + titre));

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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

                    imageView.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        scrollPane.setVvalue(0);
                        watchBtn.setVisible(true);

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
                            System.out.println("Teaser: " + teaser);

                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox);
                            VBox vBox1 = new VBox();
                            vBox1.setPadding(new Insets(10));
                            vBox1.setSpacing(10);
                            vBox1.getChildren().add(new Label("Titre: " + titre));

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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
                    /////////////////////////////
                }
                vBox.getChildren().add(gridPaneAction);





                // Display videos with genre="Aventure"
                vBox.getChildren().add(new Label(" "));
                vBox.getChildren().add(new Label(" "));
                Label labelAventure = new Label("Aventure");
                labelAventure.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
                vBox.getChildren().add(labelAventure);
                // Display videos with genre="Aventure"
                ResultSet resultSetAventure = statement.executeQuery("SELECT DISTINCT videos.titre FROM videos, genre, definit WHERE videos.id= definit.id__Videos AND definit.id=9;");

                GridPane gridPaneAventure = new GridPane();
                gridPaneAventure.setHgap(20);
                gridPaneAventure.setVgap(25);

                int rowAventure = 0;
                int colAventure = 0;
                int videosDisplayedAventure = 0;
                while (resultSetAventure.next() && videosDisplayedAventure < 12) {
                    Label label = new Label(resultSetAventure.getString("titre"));
                    String title = resultSetAventure.getString("titre");
                    File file = new File("src\\main\\images\\" + title + ".jpg");
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(130);imageView.setFitHeight(176.15);
                    gridPaneAventure.add(label, colAventure, rowAventure);
                    gridPaneAventure.add(imageView, colAventure, rowAventure+1);
                    colAventure++;
                    if (colAventure == 4) {
                        colAventure = 0;
                        rowAventure += 2;
                    }
                    videosDisplayedAventure++;
                    /////////////////////////////
                    label.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        //return at the beginning of the scrollpane
                        scrollPane.setVvalue(0);

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
                            System.out.println("Teaser: " + teaser);

                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox);
                            VBox vBox1 = new VBox();
                            vBox1.setPadding(new Insets(10));
                            vBox1.setSpacing(10);
                            vBox1.getChildren().add(new Label("Titre: " + titre));

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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

                    imageView.setOnMouseClicked(event -> {
                        AjouterBtn.setVisible(true);
                        SupprimerBtn.setVisible(true);
                        noteBtn.setVisible(true);
                        noteField.setVisible(true);
                        noteLabel.setVisible(true);
                        scrollPane.setVvalue(0);

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
                            System.out.println("Teaser: " + teaser);

                            ((AnchorPane)disconnectBtn.getParent()).getChildren().remove(vBox);
                            VBox vBox1 = new VBox();
                            vBox1.setPadding(new Insets(10));
                            vBox1.setSpacing(10);
                            vBox1.getChildren().add(new Label("Titre: " + titre));

                            Label resumeLabel = new Label("Résumé: " + resume);
                            resumeLabel.setTooltip(new Tooltip(resume));
                            resumeLabel.setMaxWidth(190);
                            resumeLabel.setWrapText(true);
                            vBox1.getChildren().add(resumeLabel);

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
                    /////////////////////////////
                }
                vBox.getChildren().add(gridPaneAventure);





                ((AnchorPane)disconnectBtn.getParent()).getChildren().add(vBox);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException h) {
            h.printStackTrace();
        }
    }

    /** Déconnection*/
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

    /** Retour à la liste des vidéos*/
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

    /** Aller regarder la vidéo*/
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

    /** Aller sur son compte*/
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

    /** Aller sur la page de recherche*/
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

    /** Ajouter à la playlist*/
    @FXML
    private void AjouterPlaylist(ActionEvent actionEvent) {
        ClientFonction tech = new ClientFonction();
        tech.AjouterFilmPlaylist();
    }

    /** Noter la vidéo*/
    @FXML
    private void Noter(ActionEvent event) {
        String notation = noteField.getText();
        float note = Float.parseFloat(notation);

        ClientFonction tech = new ClientFonction();
        tech.Notage(note);
    }

    /** Supprimer de la playlist*/
    @FXML
    private void SupprimerPlaylist(ActionEvent event) {
        ClientFonction tech = new ClientFonction();
        tech.SupprimerFilmPlaylist();
    }
}