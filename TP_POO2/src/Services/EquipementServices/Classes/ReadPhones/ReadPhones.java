package Services.EquipementServices.Classes.ReadPhones;

import Models.PhonesModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.ReadInterfaces.ReadByIMEI;
import Services.EquipementServices.Interfaces.ReadInterfaces.ReadInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReadPhones implements ReadInterface, ReadByIMEI {
    private PhonesModel phone;

    public ReadPhones(PhonesModel phone) {
        this.phone = phone;
    }

    @Override
    public ResultSet read(Integer id) throws Exception {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            String query = "SELECT * FROM Phones WHERE IdProprietaire = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            if (conn != null) conn.close();
            throw e;
        }
    }

    public ResultSet getByIMEI() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            String query = "SELECT * FROM Phones WHERE IMEI = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, phone.getIMEI());
            // Note: La connexion sera fermée par l'appelant
            return pstmt.executeQuery();
        } catch (SQLException e) {
            // Fermer la connexion en cas d'erreur
            if (conn != null) conn.close();
            throw e;
        }
    }
}
