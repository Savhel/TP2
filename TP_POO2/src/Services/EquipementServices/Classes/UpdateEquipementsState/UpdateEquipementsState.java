package Services.EquipementServices.Classes.UpdateEquipementsState;

import Models.EquipementsModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.UpdateStateInterface.UpdateStateInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateEquipementsState implements UpdateStateInterface {

    private EquipementsModel equipement;

    public UpdateEquipementsState(EquipementsModel equipement) {
        this.equipement = equipement;
    }

    @Override
    public boolean UpdateState() throws Exception {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "UPDATE Equipements SET etat_Materiel = ? WHERE address_MAC = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, equipement.getEtat_Materiel());
            pstmt.setString(2, equipement.getAddress_MAC());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
