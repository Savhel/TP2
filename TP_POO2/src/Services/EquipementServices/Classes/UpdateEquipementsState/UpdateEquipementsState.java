package Services.EquipementServices.Classes.UpdateEquipementsState;

import Models.EquipementsModel;
import Models.MaterielsModel;
import Models.PhonesModel;
import Models.UsersModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.UpdateStateInterface.UpdateStateInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateEquipementsState implements UpdateStateInterface {

    private EquipementsModel equipement;

    public UpdateEquipementsState(EquipementsModel equipement) {
        this.equipement = equipement;
    }

    @Override
    public String UpdateState(String Mac) throws Exception {
        String requete="UPDATE Equipements SET etat_Materiel = ? WHERE address_MAC=?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            stmt.setString(2, Mac);
            stmt.setString(1, "retrouvé");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la mise à jour de l'état de l'équipement: " + e.getMessage());
        }
        return "Équipement marqué comme retrouvé";
    }
}
