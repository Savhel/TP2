package Services.EquipementServices.Classes.ReadPhones;

import Models.PhonesModel;
import Services.DatabaseServices.DatabaseConnection;
import Services.EquipementServices.Interfaces.ReadInterfaces.ReadInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReadPhones implements ReadInterface {
    private PhonesModel phone;

    public ReadPhones(PhonesModel phone) {
        this.phone = phone;
    }

    @Override
    public ArrayList<PhonesModel> read(Integer id) throws Exception {
        ArrayList<PhonesModel> phones = new ArrayList<>();
        String sql = "SELECT * FROM Agents WHERE Code = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, phone.getIMEI());

            // Exécution de la requête
            try (ResultSet rsl = pst.executeQuery()) {
                // Parcours des résultats et création des objets PhonesModel
                while (rsl.next()) {
                    PhonesModel phonesModel = new PhonesModel();
                    phonesModel.setIMEI(rsl.getString("MEI"));
                    phonesModel.setNom(rsl.getString("nom"));
                    phonesModel.setModele(rsl.getString("Modele"));
                    phonesModel.setMemoire_ROM(rsl.getInt("Memoire_ROM"));
                    phonesModel.setMemoire_RAM(rsl.getInt("Memoire_RAM"));
                    phonesModel.setNumero_serie(rsl.getString("Memoire_serie"));
                    phonesModel.setIdPropretaire(rsl.getInt("IdPropiétaire"));
                    phonesModel.setCouleur(rsl.getString("Couleur"));
                    phonesModel.setEtat_Materiel(rsl.getString("Etat_Matériel"));

                    phones.add(phonesModel); // Ajouter l'objet à la liste
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la lecture des phones: " + e.getMessage());
        }

        return phones;
    }
}
