package Modele;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

public interface CompteDAO {
    public void Charger(ObservableList<Object> genreList, ChoiceBox<Object> genre);
    public void ListePerso(VBox vBox);
    public void SupprimerFonc(VBox vBox);
}
