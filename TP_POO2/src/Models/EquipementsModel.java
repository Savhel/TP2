package Models;

import Services.DatabaseServices.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipementsModel extends MaterielsModel{
    private String address_MAC;


    //setter


    public EquipementsModel() {
    }

    public EquipementsModel(String address_MAC) {
        this.address_MAC = address_MAC;
    }

    public void setAddress_MAC(String address_MAC) {
        this.address_MAC = address_MAC;
    }

    // getter

    public String getAddress_MAC() {
        return address_MAC;
    }
    
    // Méthode pour enregistrer un nouvel équipement dans la base de données
    }
