package Modele;

public interface ConnexionDAO {

    public void Inscription(String identifiant, String mdp);
    public int ConnexionNet(String identifiant, String mdp);
}
