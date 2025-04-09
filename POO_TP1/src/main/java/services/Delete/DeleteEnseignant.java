package services.Delete;

import Interface.DeleteContact;
import core.Agent;
import core.DatabaseConnection;
import core.Enseignant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteEnseignant implements DeleteContact {
    Connection connection ;
    Enseignant enseignant;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public DeleteEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String deleteContact() throws Exception {
        try{
            sql = "DELETE FROM Enseignants where Code = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1,enseignant.getCode());
            pst.executeUpdate();
            return (enseignant.getNom() + " a été supprimé avec success");
        }catch (Exception e){
            throw new SQLException();
        }
    }
}
