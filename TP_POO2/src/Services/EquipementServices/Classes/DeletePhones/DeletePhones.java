package Services.EquipementServices.Classes.DeletePhones;

import Models.EquipementsModel;
import Models.PhonesModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.DeleteInterfaces.DeleteInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeletePhones implements DeleteInterface {
    PhonesModel phone;

    public DeletePhones(PhonesModel phone) {
        this.phone = phone;
    }

    @Override
    public String delete(Integer id) throws Exception {
        String sql = "DELETE FROM Phones where IMEI = ?";
        
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, phone.getIMEI());
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                return (phone.getNom() + " a été supprimé avec success");
            } else {
                return "Aucun téléphone trouvé avec cet IMEI";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la suppression du téléphone: " + e.getMessage());
        }
    }
}
