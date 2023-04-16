package Modele;

import java.sql.Connection;
/** ConnexionDA0*/
public interface ConnexionDAO {
    /** Fonction qui permet de s'inscrire*/
    public void Inscription(String identifiant, String mdp);
    /** Fonction qui permet de se connecter*/
    public int ConnexionNet(String identifiant, String mdp);
}
