package services.Insertion;

import Interface.InsertContact;
import core.Agent;
import core.DatabaseConnection;

import java.sql.*;

public class InsertAgent implements InsertContact {
    Connection connection ;
    Agent agent;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public InsertAgent(Agent agent) {
        this.agent = agent;
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String insertContact() throws SQLException {
        try{
            sql = "insert into Agents (Code, Nom, DateNaissance , Adresse, Email, TelNumber, IndiceSalaire, Salaire, Statut, Categorie, Occupation) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1,"AGE" + agent.getCode());
            pst.setString(2,agent.getNom());
            pst.setDate(3,Date.valueOf(agent.getDateNaissance()));
            pst.setString(4,agent.getAddress());
            pst.setString(5,agent.getEmail());
            pst.setString(6,agent.getTelNumber());
            pst.setString(7,agent.getIndiceSalaire());
            pst.setInt(8,agent.getSalaire());
            pst.setString(9,agent.getStatut());
            pst.setString(10,agent.getCategorie());
            pst.setString(11,agent.getOccupation());
            pst.executeUpdate();
            return (agent.getNom() + " a été ajjouté avec success");
        }catch (Exception e){
            System.out.println(e);
            throw new SQLException();
        }
    }
}
