package Services.UserServices.UpdateUsers;

import Models.UsersModel;
import Services.DatabaseServices.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUsers {
    UsersModel user;

    public UpdateUsers(UsersModel user){
        this.user=user;
    }

    public boolean updateUser(){
        String requete="UPDATE Users SET nom=?,prenom=?,email=?,numtel=?,address=? WHERE id_user=?";
        
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            
            stmt.setInt(6,user.getId());
            stmt.setString(1,user.getNom());
            stmt.setString(2,user.getPrenom());
            stmt.setString(3,user.getEmail());
            stmt.setString(4,user.getNumtel());
            stmt.setString(5,user.getAddress());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
