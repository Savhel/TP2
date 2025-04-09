package services.Insertion;

import Interface.InsertContact;
import core.DatabaseConnection;
import core.Etudiant;

import java.sql.*;

public class InsertEtudiant implements InsertContact {
    Connection connection ;
    Etudiant etudiant;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public InsertEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String insertContact() throws SQLException {
        try{
            sql = "insert into Etudiants (Code, Nom, DateNaissance , Adresse, Email, TelNumber, Cycle, Niveau) VALUES (?,?,?,?,?,?,?,?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1,"ETU"+etudiant.getCode());
            pst.setString(2,etudiant.getNom());
            pst.setDate(3, Date.valueOf(etudiant.getDateNaissance()));
            pst.setString(4,etudiant.getAddress());
            pst.setString(5,etudiant.getEmail());
            pst.setString(6,etudiant.getTelNumber());
            pst.setString(7,etudiant.getCycle());
            pst.setInt(8,etudiant.getNiveau());
            pst.executeUpdate();
            return (etudiant.getNom() + " a été ajouté avec success");
        }catch (Exception e){
            System.out.println(e);
            throw new SQLException();
        }
    }
}
