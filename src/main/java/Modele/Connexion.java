package Modele;

import java.sql.*;

public class Connexion extends DAO implements ConnexionDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    private String rang = "Client";

    public Connexion() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void Inscription(String identifiant, String mdp) {
        String query = "INSERT INTO compte (identifiant,mdp) VALUES (?,?)"; //on insère les données dans la table
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, identifiant); //on remplace les ? par les données
            statement.setString(2, mdp); //on remplace les ? par les données
            int rows = statement.executeUpdate();
            if (rows > 0) { //si l'insertion s'est bien passée
                System.out.println("User registered successfully");
            } else { //sinon
                System.out.println("Error: User already exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int ConnexionNet(String identifiant, String mdp) {
        String query = "SELECT * FROM compte WHERE identifiant=? AND mdp=?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, identifiant); //on remplace les ? par les données
            statement.setString(2, mdp); //on remplace les ? par les données
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                resultSet = statement.executeQuery("SELECT compte.id, compte.admin FROM compte WHERE compte.identifiant ='" + identifiant + "';"); //on récupère l'id et le rang de l'utilisateur
                resultSet.next();
                int idd = resultSet.getInt("id");
                String status = resultSet.getString("admin");

                System.out.println("status="+status);
                rang = status;
                if (status.equals("Admin")) { //si l'utilisateur est admin
                    //update status table
                    statement.executeUpdate("UPDATE status SET status = 'Admin', id_current="+idd+" WHERE status.id = 1;"); //on met à jour la table status
                }
                else {
                    statement.executeUpdate("UPDATE status SET status = 'Client', id_current="+idd+" WHERE status.id = 1;"); //on met à jour la table status
                }
                //update status
                System.out.println("User logged in successfully");
                return 1;
            }
            else {
                System.out.println("Error: Invalid username or password");
                return 0;
            }
        }catch (SQLException e)  {
            e.printStackTrace();
        }
        return 0;
    }

}
