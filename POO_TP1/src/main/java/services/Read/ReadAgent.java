package services.Read;

import Interface.ReadContact;
import core.Agent;
import core.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class ReadAgent implements ReadContact {
    Connection connection ;
    PreparedStatement pst;
    ResultSet rsl;
    String sql;

    public ReadAgent() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public ResultSet readContact(String code) throws Exception {
        try{
            if (!code.isEmpty()){
                sql = "SELECT * FROM Agents WHERE Code LIKE ? OR Nom LIKE ? OR DateNaissance = ? OR Adresse LIKE ? OR Email LIKE ? OR TelNumber LIKE ? OR IndiceSalaire = ? OR Salaire = ? OR Statut LIKE ? OR Categorie LIKE ? OR Occupation LIKE ?";
                String searchTerm = "%" + code + "%";
                pst = connection.prepareStatement(sql);
                try {
                    double salaire = Double.parseDouble(code);
                    pst.setDouble(8, salaire);
                } catch (Exception e) {
                    pst.setNull(8, Types.DOUBLE);
                }
                try {
                    Date date = java.sql.Date.valueOf(LocalDate.parse(code));
                    pst.setDate(3, date);
                } catch (Exception e) {
                    pst.setNull(3, Types.DOUBLE);
                }
                try {
                    int indce = Integer.parseInt(code);
                    pst.setInt(7, indce);
                } catch (Exception e) {
                    pst.setNull(7, Types.INTEGER);
                }
                pst.setString(1,searchTerm);
                pst.setString(2,searchTerm);
                pst.setString(4,searchTerm);
                pst.setString(5,searchTerm);
                pst.setString(6,searchTerm);
                pst.setString(9,searchTerm);
                pst.setString(10,searchTerm);
                pst.setString(11,searchTerm);
            }else {
                sql = "SELECT * FROM Agents";
                pst = connection.prepareStatement(sql);
            }
            ResultSet rs = pst.executeQuery();
            return rs;
        }catch (Exception e){
            System.out.println("Erreur dans readAgent "+e);
            throw new SQLException();
        }
    }

    @Override
    public ResultSet findContact(String searchTerm) throws Exception {
        try{
            sql = "SELECT * FROM Agents WHERE Code = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1,searchTerm);

            return pst.executeQuery();
        }catch (Exception e){
            System.out.println("Erreur dans read Agent "+e);
            throw new SQLException();
        }
    }

}
