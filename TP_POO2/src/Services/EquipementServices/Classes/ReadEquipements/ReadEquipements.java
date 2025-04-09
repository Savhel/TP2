package Services.EquipementServices.Classes.ReadEquipements;

import Models.EquipementsModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.ReadInterfaces.ReadByMac;
import Services.EquipementServices.Interfaces.ReadInterfaces.ReadInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadEquipements implements ReadInterface, ReadByMac {
    private EquipementsModel equipement;

    public ReadEquipements(EquipementsModel equipement) {
        this.equipement = equipement;
    }

    @Override
    public ResultSet read(Integer id) throws Exception {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            String query = "SELECT * FROM Equipements WHERE IdProprietaire = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            // Fermer la connexion en cas d'erreur
            if (conn != null) conn.close();
            throw e;
        }
    }

    public ResultSet getByMAC() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            String query = "SELECT * FROM Equipements WHERE address_MAC = ? AND etat_Materiel = 'vole'";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, equipement.getAddress_MAC());
            // Note: La connexion sera fermée par l'appelant
            return pstmt.executeQuery();
        } catch (SQLException e) {
            // Fermer la connexion en cas d'erreur
            if (conn != null) conn.close();
            throw e;
        }
    }

}
