package Controlleur;

import Modele.Connexion;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;


/** Page de connexion*/
public class HelloController extends HelloApplication {

    @FXML private Button Inscription;
    @FXML private Button Connexion;
    @FXML private CheckBox afficherBtn;
    @FXML private TextField Passwordok;
    @FXML private AnchorPane Pane;
    @FXML private Label Label1; @FXML private Label Label2;

    /** initialize*/
    public void initialize() {

        // Change Inscription button color
        Inscription.setStyle("-fx-background-color: rgba(140,230,110,0.7)");

        // Change Connexion button color
        Connexion.setStyle("-fx-background-color: rgba(173,216,230,0.7)");

        Connexion.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Connexion.setEffect(new DropShadow()); // Change button color when mouse enters
                        Connexion.setStyle("-fx-background-color: rgb(130,200,230)");
                    }
                });

        Connexion.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Connexion.setEffect(null); // Change button color when mouse exits
                        Connexion.setStyle("-fx-background-color: rgba(170,200,230,0.7)");
                    }
                });

        Inscription.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Inscription.setEffect(new DropShadow()); // Change button color when mouse enters
                        Inscription.setStyle("-fx-background-color: rgb(127,231,92)");
                    }
                });

        Inscription.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Inscription.setEffect(null); // Change button color when mouse exits
                        Inscription.setStyle("-fx-background-color: rgb(160,230,130)");
                    }
                });

        //set background color
        Pane.setStyle("-fx-background-color: rgb(50,50,50)");

        //set text color to white
        Label1.setStyle("-fx-text-fill: white");
        Label2.setStyle("-fx-text-fill: white");
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";

    public HelloController() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private TextField Username; @FXML private PasswordField Password;


    /** Inscription*/
    @FXML
    private void Sinscrire(ActionEvent event) {
        String identifiant = Username.getText(); //identifiant
        String mdp = Password.getText(); //mot de passe
        Connexion connect = new Connexion();
        connect.Inscription(identifiant,mdp); //inscription
    }

    /** Connexion*/
    @FXML
    private void Connection(ActionEvent event) {
        String identifiant = Username.getText(); //identifiant
        String mdp = Password.getText(); //mot de passe
        int testo;
        Scanner scan = new Scanner(System.in);

        Connexion connect = new Connexion();

        System.out.println("Connexion impossible");
        testo = connect.ConnexionNet(identifiant,mdp); //connexion

        if (testo==1) {
            Stage stage1 = (Stage) Username.getScene().getWindow();
            stage1.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewScreen.fxml")); //si ok, on va à la page d'accueil
            try {
                Parent root = loader.load();
                Stage stage2 = new Stage();
                stage2.setTitle("New Screen");
                stage2.setScene(new Scene(root));
                stage2.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}