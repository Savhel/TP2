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
    public void Insertion() throws SQLException {
            String requete="insert into Phones(IMEI,IdProprietaire,Nom,Marque,Modele,memoire_ROM,memoire_RAM,numero_serie) VALUES (?,?,?,?,?,?,?,?)";
            try (Connection connection = DatabaseConnection.getInstance().getConnection();
                 PreparedStatement stmt = connection.prepareStatement(requete)) {
                stmt.setString(1,phone.getIMEI());
                stmt.setInt(2,phone.getIdProprietaire());
                stmt.setString(3,phone.getEtat_Materiel());
                stmt.setString(4,phone.getCouleur());
                stmt.setString(5,phone.getNom());
                stmt.setString(6,phone.getMarque());
                stmt.setString(7, phone.getModele());
                stmt.setFloat(8,phone.getMemoire_ROM());
                stmt.setFloat(9,phone.getMemoire_RAM());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
    }
}
