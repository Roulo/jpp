package Modele;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

/** CompteDAO*/
public interface CompteDAO {
    /** Fonction qui permet de charger les choiceBox*/
    public void Charger(ObservableList<Object> genreList, ChoiceBox<Object> genre);
    /** Fonction qui permet de afficher sa playlist de film*/
    public void ListePerso(VBox vBox);
    /** */
    public void SupprimerFonc(VBox vBox);

}
