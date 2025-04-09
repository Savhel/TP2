package Services.EquipementServices.Classes.UpdatePhones;


import Models.PhonesModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.UpdateInterfaces.UpdateInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatePhones implements UpdateInterface {

    PhonesModel p;

    public UpdatePhones(PhonesModel p){
        this.p=p;
    }

    @Override
    public boolean UpdateMaterial(){
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "UPDATE Phones SET IdProprietaire = ?, etat_Materiel = ?, Couleur = ?, Nom = ?, Marque = ?, Modele = ?, memoire_ROM = ?, memoire_RAM = ?, numero_serie = ?, photoUrl = ? WHERE IMEI = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, p.getIdProprietaire());
            pstmt.setString(2, p.getEtat_Materiel());
            pstmt.setString(3, p.getCouleur());
            pstmt.setString(4, p.getNom());
            pstmt.setString(5, p.getMarque());
            pstmt.setString(6, p.getModele());
            pstmt.setFloat(7, p.getMemoire_ROM());
            pstmt.setFloat(8, p.getMemoire_RAM());
            pstmt.setString(9, p.getNumero_serie());
            pstmt.setString(10, p.getPhotoUrl());
            pstmt.setString(11, p.getIMEI());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
