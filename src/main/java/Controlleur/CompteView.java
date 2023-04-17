package Controlleur;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;

/** CompteView*/
public class CompteView{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;
    @FXML private AnchorPane Pane; @FXML private AnchorPane Pane2;

    public void initialize(){
        Pane.setStyle("-fx-background-color: rgb(50,50,50)");
    }

    /** Nouveau CompteView*/
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

    /** Charger les genre de film depuis la BDD*/
    @FXML
    private void LoadData(ActionEvent event) {
        Compte Don = new Compte();
        Don.Charger(genreList,genre); //charger les données
    }

    /** Ouvrir l'interface de playlist*/
    @FXML
    private void Liste(){
        Stage stage = (Stage) listeBtn.getScene().getWindow(); //je prend la page
        stage.close(); //je la ferme
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("compte-view-liste.fxml")); //je charge la page de playlist
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

    /** Charger et afficher la playlist*/
    @FXML
    private void ChargerListe(){
        chargerlistBtn.setVisible(false);
        //je cache le bouton pck sinon le gars peux rechager les videos et ça se superpose

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        Compte com = new Compte();
        com.ListePerso(vBox); //je charge la liste perso

        ((AnchorPane)resultat.getParent()).getChildren().add(vBox);
    }



    /** Ouvrir l'interface d'ajout de vidéo*/
    @FXML
    private void Ajouter(ActionEvent event){
        AdminFonction tech = new AdminFonction();

        int stat = tech.AdminCommand();

        if (stat == 1) {
            Stage stage = (Stage) listeBtn.getScene().getWindow(); //je prend la page
            stage.close(); //je la ferme
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("compte-view-addvideos.fxml")); //je charge la page d'ajout de vidéo
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

    /** Ajouter une vidéo*/
    @FXML
    private void Add(ActionEvent event){
        //récupérer les données
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

        //si vide on affiche un message d'erreur
        if ((title.isEmpty()) || (director.isEmpty()) || (anee.isEmpty()) || (resume.isEmpty()) || (link.isEmpty()) || (genre.isEmpty())) {
            System.out.println("Un des champs est vide, Veuillez la compléter");
        }
        else { //sinon on ajoute la vidéo
            tech.AjouterFilm(resultat, title, director, year, duration, resume, link, note, genre);
        }
    }


    /** Ouvrir l'interface de suppression de vidéo*/
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

    /** Supprimer une vidéo*/
    @FXML
    private void Delete(ActionEvent event){
        String title = titre.getText();
        AdminFonction tech = new AdminFonction();

        if (title.isEmpty()) { //si vide on affiche un message d'erreur
            System.out.println("Le champ est vide, Veuillez remplir le champ");
        }
        else { //sinon on supprime la vidéo
            tech.SupprimerFilm(resultat,title);
        }
    }


    /** Ouvrir l'interface d'ajout de compte*/
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

    /** Ajouter un compte*/
    @FXML
    private void AddCompte(ActionEvent actionEvent) {
        // à coder
        String identifiant = username.getText();
        String mdp1 = mdp.getText();
        int administrateur=0;

        if (adminBox.isSelected()) { //si l'admin est coché
            administrateur=1;
        }

        AdminFonction tech = new AdminFonction();

        if ((identifiant.isEmpty()) || (mdp1.isEmpty())) { //si vide on affiche un message d'erreur
            System.out.println("Des champs sont vides, Veuillez remplir les champs");
        }
        else { //sinon on ajoute le compte
            tech.AjouterClient(resultat,identifiant,mdp1,administrateur);
        }

    }


    /** Ouvrir l'interface de suppression de compte*/
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

    /** Supprimer un compte*/
    @FXML
    private void DeleteCompte(ActionEvent actionEvent) {
        // à coder
        String identifiant = titre.getText();


        AdminFonction tech = new AdminFonction();

        if (identifiant.isEmpty()) { //si vide on affiche un message d'erreur
            System.out.println("Des champs sont vides, Veuillez remplir les champs");
        }
        else { //sinon on supprime le compte
            tech.SupprimerClient(resultat,identifiant);
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

    @FXML
    private Image image;

    @FXML
    private ImageView imageView;

    /** Ajouter une image dans src/main/images*/
    @FXML
    private void Image(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")); //choper que les images
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) { //si le fichier est valide
            System.out.println(selectedFile.getName());
            System.out.println(selectedFile.getAbsolutePath());
            System.out.println(selectedFile.toURI().toString());
            System.out.println(selectedFile.toURI().toString().substring(6));

            image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);

            String path = "src\\main\\images\\" + selectedFile.getName(); //le mettre dans le dossier images
            System.out.println(path);

            try {
                Files.copy(selectedFile.toPath(), new File(path).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File is not valid.");
        }
    }


    /** Reprendre une vidéo*/
    @FXML
    private void Reprendre(ActionEvent event){
        Stage stage = (Stage) listeBtn.getScene().getWindow(); //je prend la page
        stage.close(); //je la ferme
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("watch-view-reprendre.fxml")); //je charge la page
            Parent root = fxmlLoader.load(); //je la charge
            Stage stage1 = new Stage();
            stage1.setTitle("Reprise");
            stage1.setScene(new Scene(root));
            stage1.show(); //je l'affiche
            //youhou on a rechargé la page
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

