package com.example.jpp;

import Modele.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CompteView{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public CompteView() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private Button disconnectBtn; @FXML private Button backBtn; @FXML private Button historiqueBtn; @FXML private Button listeBtn; @FXML private Button supprBtn;
    @FXML private Button ajouterBtn; @FXML private Button chargerlistBtn;
    @FXML private Label resultat;
    ObservableList<Object> genreList = FXCollections.observableArrayList();
    @FXML private ChoiceBox<Object> genre;
    @FXML private Button loadBtn; @FXML private Button addBtn;
    @FXML private TextField titre;@FXML private TextField real;@FXML private TextField acteurs;@FXML private TextField annee;
    @FXML private TextField resume;@FXML private TextField lien;@FXML private TextField duree;
    @FXML private Button addcompteBtn;@FXML private Button delcompteBtn ;@FXML private TextField username;@FXML private TextField mdp;@FXML private CheckBox adminBox;

    @FXML
    private void LoadData(ActionEvent event) {
        Compte Don = new Compte();
        Don.Charger(genreList,genre);
    }

    @FXML
    private void Liste(){
        Stage stage = (Stage) listeBtn.getScene().getWindow(); //je prend la page
        stage.close(); //je la ferme
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("compte-view-liste.fxml")); //je charge la page
            Parent root = fxmlLoader.load(); //je la charge
            Stage stage1 = new Stage();
            stage1.setTitle("Liste des vidéos");
            stage1.setScene(new Scene(root));
            stage1.show(); //je l'affiche
            //youhou on a rechargé la page
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ChargerListe(){
        chargerlistBtn.setVisible(false);
        //je cache le bouton pck sinon le gars peux rechager les videos et ça se superpose

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        Compte com = new Compte();
        com.ListePerso(vBox);

        ((AnchorPane)resultat.getParent()).getChildren().add(vBox);
    }




    @FXML
    private void Ajouter(ActionEvent event){
        AdminFonction tech = new AdminFonction();

        int stat = tech.AdminCommand();

        if (stat == 1) {
            Stage stage = (Stage) listeBtn.getScene().getWindow(); //je prend la page
            stage.close(); //je la ferme
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("compte-view-addvideos.fxml")); //je charge la page
                Parent root = fxmlLoader.load(); //je la charge
                Stage stage1 = new Stage();
                stage1.setTitle("UPDATE des vidéos");
                stage1.setScene(new Scene(root));
                stage1.show(); //je l'affiche
                //youhou on a rechargé la page
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void Add(ActionEvent event){
        String title = titre.getText();
        String director = real.getText();
        String actor = acteurs.getText();
        int duration = 0;
        int year=0;

        String anee = annee.getText();
        if (anee.isEmpty()) {
            anee = "0";
        }
        else {
            year = Integer.parseInt(anee);
        }

        String resume = this.resume.getText();
        String link = lien.getText();

        String durationn = duree.getText();
        if (durationn.isEmpty()) {
            durationn = "0";
        }
        else {
            duration = Integer.parseInt(durationn);
        }

        if (this.genre.getValue() == null){
            genre.setValue("");
        }

        String genre = this.genre.getValue().toString();
        int note = 0;

        AdminFonction tech = new AdminFonction();

        if ((title.isEmpty()) || (director.isEmpty()) || (anee.isEmpty()) || (resume.isEmpty()) || (link.isEmpty()) || (genre.isEmpty())) {
            System.out.println("Un des champs est vide, Veuillez la compléter");
        }
        else {
            tech.AjouterFilm(resultat, title, director, year, duration, resume, link, note, genre);
        }
    }



    @FXML
    private void Supprimer(){
        AdminFonction tech = new AdminFonction();

        int stat = tech.AdminCommand();

        if (stat == 1) {

            Stage stage = (Stage) listeBtn.getScene().getWindow(); //je prend la page
            stage.close(); //je la ferme
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("compte-view-delvideos.fxml")); //je charge la page
                Parent root = fxmlLoader.load(); //je la charge
                Stage stage1 = new Stage();
                stage1.setTitle("UPDATE des vidéos");
                stage1.setScene(new Scene(root));
                stage1.show(); //je l'affiche
                //youhou on a rechargé la page
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };

    @FXML
    private void Delete(ActionEvent event){
        // à coder
        String title = titre.getText();
        AdminFonction tech = new AdminFonction();

        if (title.isEmpty()) {
            System.out.println("Le champ est vide, Veuillez remplir le champ");
        }
        else {
            tech.SupprimerFilm(resultat,title);
        }
    }



    @FXML
    private void AjouterCompte(){

        AdminFonction tech = new AdminFonction();

        int stat = tech.AdminCommand();

        if (stat == 1) {
            Stage stage = (Stage) listeBtn.getScene().getWindow(); //je prend la page
            stage.close(); //je la ferme
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("compte-view-addcompte.fxml")); //je charge la page
                Parent root = fxmlLoader.load(); //je la charge
                Stage stage1 = new Stage();
                stage1.setTitle("UPDATE des Comptes");
                stage1.setScene(new Scene(root));
                stage1.show(); //je l'affiche
                //youhou on a rechargé la page
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void AddCompte(ActionEvent actionEvent) {
        // à coder
        String identifiant = username.getText();
        String mdp1 = mdp.getText();
        int administrateur=0;

        if (adminBox.isSelected()) {
            administrateur=1;

        }

        AdminFonction tech = new AdminFonction();

        if ((identifiant.isEmpty()) || (mdp1.isEmpty())) {
            System.out.println("Des champs sont vides, Veuillez remplir les champs");
        }
        else {
            tech.AjouterClient(resultat,identifiant,mdp1,administrateur);
        }

    }



    @FXML
    private void SupprimerCompte(){


        AdminFonction tech = new AdminFonction();

        int stat = tech.AdminCommand();
        System.out.println(stat);

        if (stat == 1) {
            Stage stage = (Stage) listeBtn.getScene().getWindow(); //je prend la page
            stage.close(); //je la ferme
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("compte-view-delcompte.fxml")); //je charge la page
                Parent root = fxmlLoader.load(); //je la charge
                Stage stage1 = new Stage();
                stage1.setTitle("UPDATE des Comptes");
                stage1.setScene(new Scene(root));
                stage1.show(); //je l'affiche
                //youhou on a rechargé la page
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void DeleteCompte(ActionEvent actionEvent) {
        // à coder
        String identifiant = titre.getText();


        AdminFonction tech = new AdminFonction();

        if (identifiant.isEmpty()) {
            System.out.println("Des champs sont vides, Veuillez remplir les champs");
        }
        else {
            tech.SupprimerClient(resultat,identifiant);
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
}


//    @FXML
//    private void Historique(){
//        ((AnchorPane)resultat.getParent()).getChildren().clear();
//        try {
//            Statement statement = conn.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM videos");
//
//            VBox vBox = new VBox();
//            vBox.setPadding(new Insets(10));
//            vBox.setSpacing(10);
//
//            while (resultSet.next()) {
//                Label label = new Label(resultSet.getString("titre"));
//                vBox.getChildren().add(label);
//            }
//            ((AnchorPane)resultat.getParent()).getChildren().add(vBox);
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

