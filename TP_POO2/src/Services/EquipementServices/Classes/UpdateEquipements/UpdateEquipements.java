package Services.EquipementServices.Classes.UpdateEquipements;

import Models.EquipementsModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.UpdateInterfaces.UpdateInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateEquipements implements UpdateInterface {
    EquipementsModel e;

    public UpdateEquipements(EquipementsModel e){
        this.e=e;
    }

    @Override
    public boolean UpdateMaterial(){
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "UPDATE Equipements SET IdProprietaire = ?, etat_Materiel = ?, Couleur = ?, Nom = ?, Marque = ?, Modele = ?, memoire_ROM = ?, memoire_RAM = ?, numero_serie = ?, photoUrl = ? WHERE address_MAC = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, e.getIdProprietaire());
            pstmt.setString(2, e.getEtat_Materiel());
            pstmt.setString(3, e.getCouleur());
            pstmt.setString(4, e.getNom());
            pstmt.setString(5, e.getMarque());
            pstmt.setString(6, e.getModele());
            pstmt.setFloat(7, e.getMemoire_ROM());
            pstmt.setFloat(8, e.getMemoire_RAM());
            pstmt.setString(9, e.getNumero_serie());
            pstmt.setString(10, e.getPhotoUrl());
            pstmt.setString(11, e.getAddress_MAC());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
