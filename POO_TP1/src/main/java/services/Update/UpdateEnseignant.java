package services.Update;

import Interface.UpdateContact;
import core.DatabaseConnection;
import core.Enseignant;

import java.sql.*;

public class UpdateEnseignant implements UpdateContact {
    Connection connection ;
    Enseignant enseignant;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public UpdateEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String updateContact(String code) throws Exception {
        try{
            sql = "UPDATE Enseignants SET Code = ?, Nom = ?, DateNaissance = ?, Adresse = ?, Email = ?, TelNumber = ?, Statut = ?  where Code = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, enseignant.getCode());
            pst.setString(2, enseignant.getNom());
            pst.setDate(3, Date.valueOf(enseignant.getDateNaissance()));
            pst.setString(4, enseignant.getAddress());
            pst.setString(5, enseignant.getEmail());
            pst.setString(6, enseignant.getTelNumber());
            pst.setString(7, enseignant.getStatut());
            pst.setString(8,enseignant.getCode());
            pst.executeUpdate();
            return (enseignant.getNom() + " a été modifié avec success");
        }catch (Exception e){
            throw new SQLException();
        }
    }
}
