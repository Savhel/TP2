package Services.EquipementServices.Classes.InsertionEquipements;

import Models.EquipementsModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.InsertionInterfaces.InsertionInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertionEquipements implements InsertionInterface {

    private EquipementsModel equipement;

    public InsertionEquipements(EquipementsModel equipement) {
        this.equipement = equipement;
    }

    @Override
    public boolean Insertion() throws SQLException {

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "INSERT INTO Equipements (address_MAC, IdProprietaire, etat_Materiel, Couleur, Nom, Marque, Modele, memoire_ROM, memoire_RAM, numero_serie, photoUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, equipement.getAddress_MAC());
            pstmt.setInt(2, equipement.getIdProprietaire());
            pstmt.setString(3, equipement.getEtat_Materiel());
            pstmt.setString(4, equipement.getCouleur());
            pstmt.setString(5, equipement.getNom());
            pstmt.setString(6, equipement.getMarque());
            pstmt.setString(7, equipement.getModele());
            pstmt.setFloat(8, equipement.getMemoire_ROM());
            pstmt.setFloat(9, equipement.getMemoire_RAM());
            pstmt.setString(10, equipement.getNumero_serie());
            pstmt.setString(11, equipement.getPhotoUrl());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
