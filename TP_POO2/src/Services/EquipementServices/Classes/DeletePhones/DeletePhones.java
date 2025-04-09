package Services.EquipementServices.Classes.DeletePhones;

import Models.PhonesModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.DeleteInterfaces.DeleteInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeletePhones implements DeleteInterface {
    PhonesModel phone;

    public DeletePhones(PhonesModel phone) {
        this.phone = phone;
    }

    @Override
    public boolean delete() throws Exception {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "DELETE FROM Phones WHERE IMEI = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, phone.getIMEI());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
