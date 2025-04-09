package Services.EquipementServices.Classes.DeleteEquipements;

import Models.EquipementsModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.DeleteInterfaces.DeleteInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteEquipements implements DeleteInterface {

    EquipementsModel equipement;

    public DeleteEquipements(EquipementsModel equipement) {
        this.equipement = equipement;
    }

    @Override
    public boolean delete() throws Exception {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "DELETE FROM Equipements WHERE address_MAC = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, equipement.getAddress_MAC());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
