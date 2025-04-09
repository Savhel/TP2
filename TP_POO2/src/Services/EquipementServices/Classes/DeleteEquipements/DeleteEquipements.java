package Services.EquipementServices.Classes.DeleteEquipements;

import Models.EquipementsModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.DeleteInterfaces.DeleteInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteEquipements implements DeleteInterface {

    EquipementsModel equipement;

    public DeleteEquipements(EquipementsModel equipement) {
        this.equipement = equipement;
    }

    @Override
    public String delete(Integer id) throws Exception {
        String sql = "DELETE FROM Equipements where address_MAC = ?";
        
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, equipement.getAddress_MAC());
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                return (equipement.getNom() + " a été supprimé avec success");
            } else {
                return "Aucun équipement trouvé avec cette adresse MAC";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la suppression de l'équipement: " + e.getMessage());
        }
    }
}
