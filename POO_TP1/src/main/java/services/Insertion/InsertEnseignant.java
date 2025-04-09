package services.Insertion;

import Interface.InsertContact;
import core.DatabaseConnection;
import core.Enseignant;

import java.sql.*;

public class InsertEnseignant implements InsertContact {
    Connection connection ;
    Enseignant enseignant;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public InsertEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String insertContact() throws SQLException {
        try{
            sql = "insert into Enseignants (Code, Nom, DateNaissance , Adresse, Email, TelNumber, Statut) VALUES (?,?,?,?,?,?,?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1,"ENS"+enseignant.getCode());
            pst.setString(2,enseignant.getNom());
            pst.setDate(3, Date.valueOf(enseignant.getDateNaissance()));
            pst.setString(4,enseignant.getAddress());
            pst.setString(5,enseignant.getEmail());
            pst.setString(6,enseignant.getTelNumber());
            pst.setString(7,enseignant.getStatut());
            pst.executeUpdate();
            return (enseignant.getNom() + " a été ajjouté avec success");
        }catch (Exception e){
            System.out.println(e);
            throw new SQLException();
        }
    }
}
