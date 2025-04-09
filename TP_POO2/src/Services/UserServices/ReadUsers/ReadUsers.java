package Services.UserServices.ReadUsers;


import Models.UsersModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.Utils.ResultSetToJson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReadUsers {

    public ReadUsers() {
        // Pas besoin d'initialiser la connexion dans le constructeur
    }

    public String read(Integer id) throws Exception {
        String sql = "SELECT * FROM Users WHERE id_user = ?";
        UsersModel user = null;
        
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            pst.setInt(1, id);

            return ResultSetToJson.usersResultSetToJson(pst.executeQuery());
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la lecture des users: " + e.getMessage());
        }
    }
    
    public String findByEmail(String email) throws Exception {
        String sql = "SELECT * FROM Users WHERE email = ?";
        UsersModel user = null;
        
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, email);

            return ResultSetToJson.usersResultSetToJson(pst.executeQuery());
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la recherche d'utilisateur par email: " + e.getMessage());
        }
    }
    
    public String login(String email, String password) throws Exception {
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
        System.out.println(sql);
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.setString(2, password);
            System.out.println(ResultSetToJson.usersResultSetToJson(pst.executeQuery()));
            return ResultSetToJson.usersResultSetToJson(pst.executeQuery());
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'authentification: " + e.getMessage());
        }
    }
    public String getUser(String id) throws Exception {
        String sql = "SELECT * FROM Users WHERE id_user = ?";
        System.out.println(sql);
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(id));
            System.out.println(ResultSetToJson.usersResultSetToJson(pst.executeQuery()));
            return ResultSetToJson.usersResultSetToJson(pst.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'authentification: " + e.getMessage());
        }
    }
}