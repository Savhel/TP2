package Services.EquipementServices.Classes.UpdateEquipements;

import Models.EquipementsModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.UpdateInterfaces.UpdateInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateEquipements implements UpdateInterface {
    EquipementsModel e;

    public UpdateEquipements(EquipementsModel e){
        this.e=e;
    }

    @Override
    public void UpdateMaterial(){
        String requete="UPDATE Equipements SET IdProprietaire=?,Couleur=?,etat_Materiel=?,Nom=?,Marque=?,Modele=?,memoire_ROM=?,memoire_RAM=?,numero_serie=? WHERE address_MAC=?";
        
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            
            stmt.setString(10,e.getAddress_MAC());
            stmt.setInt(1,e.getIdProprietaire());
            stmt.setString(2,e.getCouleur());
            stmt.setString(3,e.getEtat_Materiel());
            stmt.setString(4,e.getNom());
            stmt.setString(5,e.getMarque());
            stmt.setString(6,e.getModele());
            stmt.setFloat(7,e.getMemoire_ROM());
            stmt.setFloat(8,e.getMemoire_RAM());
            stmt.setString(9,e.getNumero_serie());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
