package Services.EquipementServices.Classes.ReadEquipements;

import Models.EquipementsModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.ReadInterfaces.ReadInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReadEquipements implements ReadInterface {
    private EquipementsModel equipement;

    public ReadEquipements(EquipementsModel equipement) {
        this.equipement = equipement;
    }

    @Override
    public ArrayList<EquipementsModel> read(Integer id) throws Exception {
        ArrayList<EquipementsModel> equipements = new ArrayList<>();
        String sql = "SELECT * FROM Agents WHERE Code = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);
            ) {
            
            pst.setString(1, equipement.getAddress_MAC());

            // Exécution de la requête
            try (ResultSet rsl = pst.executeQuery()) {
                // Parcours des résultats et création des objets EquipementsModel
                while (rsl.next()) {
                    EquipementsModel equipementsModel = new EquipementsModel();
                    equipementsModel.setAddress_MAC(rsl.getString("address_MAC"));
                    equipementsModel.setNom(rsl.getString("nom"));
                    equipementsModel.setModele(rsl.getString("Modele"));
                    equipementsModel.setMemoire_ROM(rsl.getInt("Memoire_ROM"));
                    equipementsModel.setMemoire_RAM(rsl.getInt("Memoire_RAM"));
                    equipementsModel.setNumero_serie(rsl.getString("Memoire_serie"));
                    equipementsModel.setIdPropretaire(rsl.getInt("IdPropiétaire"));
                    equipementsModel.setCouleur(rsl.getString("Couleur"));
                    equipementsModel.setEtat_Materiel(rsl.getString("Etat_Matériel"));

                    equipements.add(equipementsModel); // Ajouter l'objet à la liste
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la lecture des équipements: " + e.getMessage());
        }

        return equipements;
    }
}
