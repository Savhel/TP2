package services.Update;

import Interface.UpdateContact;
import core.Agent;
import core.DatabaseConnection;
import core.Etudiant;

import java.sql.*;

public class UpdateEtudiant implements UpdateContact {
    Connection connection ;
    Etudiant etudiant;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public UpdateEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String updateContact(String code) throws Exception {
        try{
            System.out.println("test " + etudiant.getDateNaissance());
            sql = "UPDATE Etudiants SET Code = ?, Nom = ?, DateNaissance = ?, Adresse = ?, Email = ?, TelNumber = ?, Cycle = ?, Niveau = ? where Code = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1,etudiant.getCode());
            pst.setString(2,etudiant.getNom());
            pst.setDate(3, Date.valueOf(etudiant.getDateNaissance()));
            pst.setString(4,etudiant.getAddress());
            pst.setString(5,etudiant.getEmail());
            pst.setString(6,etudiant.getTelNumber());
            pst.setString(7,etudiant.getCycle());
            pst.setInt(8,etudiant.getNiveau());
            pst.setString(9,etudiant.getCode());
            pst.executeUpdate();
            return (etudiant.getNom() + " a été modifié avec success");
        }catch (Exception e){
            System.out.println(e + Date.valueOf(etudiant.getDateNaissance()).toString() );
            throw new SQLException();
        }
    }
}
