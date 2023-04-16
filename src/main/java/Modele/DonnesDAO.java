package Modele;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
/** DonnesDAO*/
public interface DonnesDAO {
    /** Fonction qui réalise le filtrage vidéo*/
    public void ChargerData(ChoiceBox<Object> genre, ObservableList<Object> genreList, ChoiceBox<Object> trier, ObservableList<Object> trierList);

}
