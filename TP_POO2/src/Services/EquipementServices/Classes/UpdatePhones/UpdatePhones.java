package Services.EquipementServices.Classes.UpdatePhones;


import Models.PhonesModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.UpdateInterfaces.UpdateInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdatePhones implements UpdateInterface {

    PhonesModel p;

    public UpdatePhones(PhonesModel p){
        this.p=p;
    }

    @Override
    public void UpdateMaterial(){
        String requete="UPDATE Equipements SET IdProprietaire=?,Couleur=?,etat_Materiel=?,Nom=?,Marque=?,Modele=?,memoire_ROM=?,memoire_RAM=?,numero_serie=? WHERE IMEI=?";
        
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            
            stmt.setString(10,p.getIMEI());
            stmt.setInt(1,p.getIdProprietaire());
            stmt.setString(3,p.getCouleur());
            stmt.setString(2,p.getEtat_Materiel());
            stmt.setString(4,p.getNom());
            stmt.setString(5,p.getMarque());
            stmt.setString(6,p.getModele());
            stmt.setFloat(7,p.getMemoire_ROM());
            stmt.setFloat(8,p.getMemoire_RAM());
            stmt.setString(9,p.getNumero_serie());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
