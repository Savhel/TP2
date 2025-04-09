package Services.EquipementServices.Classes.UpdatePhonesState;

import Models.PhonesModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.UpdateStateInterface.UpdateStateInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatePhonesState implements UpdateStateInterface {

    private PhonesModel phone;

    public UpdatePhonesState(PhonesModel phone) {
        this.phone = phone;
    }

    @Override
    public boolean UpdateState() throws Exception {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "UPDATE Phones SET etat_Materiel = ? WHERE IMEI = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, phone.getEtat_Materiel());
            pstmt.setString(2, phone.getIMEI());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
