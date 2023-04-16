package Modele;

/** ClientDAO */
public interface ClientDAO {
    /** Fonction qui permet à un utilisateur de noter une vidéo*/
    public void Notage(float note);
    /** Fonction qui permet de sauvegarder le watchtime d'une vidéo*/
    public void SauvegardeWatchTime(int time);
    /** Fonction qui permet de supprimer un film de la playlist*/
    public void SupprimerFilmPlaylist();
    /** Fonction qui permet d'ajouter un film à la playlist*/
    public void AjouterFilmPlaylist();
}
