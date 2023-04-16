package Modele;

import javafx.scene.control.Button;

/** VisionnageDAO*/
public interface VisionnageDAO {
    /** Fonction qui permet de lancer une lecture de vidéo*/
    public void LancerVideo(Button backBtn);
    /** Fonction qui permet de reprendre une lecture de vidéo*/
    public void Reprendre(Button backBtn);
    }
