package services.Utils.PasswordUtils;

import core.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckConnexion {
    public static Boolean checkConnexion(String User, String pwd) throws SQLException {
        // verifier si le user existe et si le mot de passe correspond
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Users Where Nom = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,User);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String pass = resultSet.getString("PwdHash");
            if (VerifyPassword.verifyPassword(pwd,pass)){
                return true;
            }
        }
        return false;

    }
}
