package Modele;

public class Client {

    private String identifiant;
    private String mdp;

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getMdp() {
        return mdp;
    }

    public String getIdentifiant() {
        return identifiant;
    }
}
