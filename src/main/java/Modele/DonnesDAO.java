package Modele;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public interface DonnesDAO {
    public void ChargerData(ChoiceBox<Object> genre, ObservableList<Object> genreList, ChoiceBox<Object> trier, ObservableList<Object> trierList);

}
