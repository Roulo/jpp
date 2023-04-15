package Modele;

import java.sql.*;

public class Connexion extends DAO implements ConnexionDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/temporaire?user=root&password=";
    private Connection conn;

    public Connexion() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void Inscription(String identifiant, String mdp) {
        String query = "INSERT INTO compte (identifiant,mdp) VALUES (?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, identifiant);
            statement.setString(2, mdp);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("User registered successfully");
            } else {
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
            statement.setString(1, identifiant);
            statement.setString(2, mdp);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                resultSet = statement.executeQuery("SELECT compte.admin FROM compte WHERE compte.identifiant ='" + identifiant + "';");
                resultSet.next();
                String status = resultSet.getString("admin");
                System.out.println("status="+status);
                if (status.equals("Admin")) {
                    //update status table
                    statement.executeUpdate("UPDATE status SET status = 'Admin' WHERE status.id = 1;");
                }
                else {
                    statement.executeUpdate("UPDATE status SET status = 'Client' WHERE status.id = 1;");
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
