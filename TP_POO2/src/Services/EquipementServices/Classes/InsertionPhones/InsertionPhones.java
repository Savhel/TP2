package Services.EquipementServices.Classes.InsertionPhones;

import Models.PhonesModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.InsertionInterfaces.InsertionInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertionPhones implements InsertionInterface {

    PhonesModel phone;

    public InsertionPhones(PhonesModel phone) {
        this.phone = phone;
    }

    @Override
    public boolean Insertion() throws SQLException {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "INSERT INTO Phones (IMEI, IdProprietaire, etat_Materiel, Couleur, Nom, Marque, Modele, memoire_ROM, memoire_RAM, numero_serie, photoUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, phone.getIMEI());
            pstmt.setInt(2, phone.getIdProprietaire());
            pstmt.setString(3, phone.getEtat_Materiel());
            pstmt.setString(4, phone.getCouleur());
            pstmt.setString(5, phone.getNom());
            pstmt.setString(6, phone.getMarque());
            pstmt.setString(7, phone.getModele());
            pstmt.setFloat(8, phone.getMemoire_ROM());
            pstmt.setFloat(9, phone.getMemoire_RAM());
            pstmt.setString(10, phone.getNumero_serie());
            pstmt.setString(11, phone.getPhotoUrl());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
    }
}
