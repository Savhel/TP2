package services.Delete;

import Interface.DeleteContact;
import core.Agent;
import core.DatabaseConnection;
import core.Etudiant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteEtudiant implements DeleteContact {
    Connection connection ;
    Etudiant etudiant;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public DeleteEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String deleteContact() throws Exception {
        try{
            sql = "DELETE FROM Etudiants where Code = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1,etudiant.getCode());
            pst.executeUpdate();
            return (etudiant.getNom() + " a été supprimé avec success");
        }catch (Exception e){
            throw new RuntimeException("Une erreur lors la suppression du contact de cet etudiant");
        }
    }
}
