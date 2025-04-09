package services.Delete;

import Interface.DeleteContact;
import core.Agent;
import core.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteAgent implements DeleteContact {
    Connection connection ;
    Agent agent;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public DeleteAgent(Agent agent) {
        this.agent = agent;
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String deleteContact() throws Exception {
        try{
            sql = "DELETE FROM Agents where Code = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1,agent.getCode());
            pst.executeUpdate();
            return (agent.getNom() + " a été supprimé avec success");
        }catch (Exception e){
            throw new SQLException();
        }
    }
}
