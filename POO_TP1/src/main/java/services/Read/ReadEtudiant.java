package services.Read;

import Interface.ReadContact;
import core.Agent;
import core.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class ReadEtudiant implements ReadContact {
    Connection connection ;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public ReadEtudiant() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public ResultSet readContact(String code) throws Exception {
        try{
            if (!code.isEmpty()){
                sql = "SELECT * FROM Etudiants where Code LIKE ? or Nom LIKE ? or DateNaissance = ? or Adresse LIKE ? or Email LIKE ? or TelNumber LIKE ? or Cycle LIKE ? or Niveau LIKE ?";
                pst = connection.prepareStatement(sql);
                try {
                    Date date = java.sql.Date.valueOf(LocalDate.parse(code));
                    pst.setDate(3, date);
                } catch (Exception e) {
                    pst.setNull(3, Types.DOUBLE);
                }
                String searchTerm = "%" + code + "%";
                pst.setString(1,searchTerm);
                pst.setString(2,searchTerm);
                pst.setString(4,searchTerm);
                pst.setString(5,searchTerm);
                pst.setString(6,searchTerm);
                pst.setString(7,searchTerm);
                pst.setString(8,searchTerm);
            }else {
                sql = "SELECT * FROM Etudiants ";
                pst = connection.prepareStatement(sql);
            }
            return pst.executeQuery();
        }catch (Exception e){
            System.out.println("Erreur dans read Etudiant "+e);
            throw new SQLException();
        }
    }

    @Override
    public ResultSet findContact(String searchTerm) throws Exception {
        try{
            sql = "SELECT * FROM Etudiants WHERE Code = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1,searchTerm);
            return pst.executeQuery();
        }catch (Exception e){
            System.out.println("Erreur dans find Etudiant "+e);
            throw new SQLException();
        }
    }
}
