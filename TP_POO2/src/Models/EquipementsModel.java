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
    public boolean save() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "INSERT INTO Equipements (address_MAC, IdProprietaire, etat_Materiel, Couleur, Nom, Marque, Modele, memoire_ROM, memoire_RAM, numero_serie, photoUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.getAddress_MAC());
            pstmt.setInt(2, this.getIdProprietaire());
            pstmt.setString(3, this.getEtat_Materiel());
            pstmt.setString(4, this.getCouleur());
            pstmt.setString(5, this.getNom());
            pstmt.setString(6, this.getMarque());
            pstmt.setString(7, this.getModele());
            pstmt.setFloat(8, this.getMemoire_ROM());
            pstmt.setFloat(9, this.getMemoire_RAM());
            pstmt.setString(10, this.getNumero_serie());
            pstmt.setString(11, this.getPhotoUrl());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour mettre à jour un équipement existant
    public boolean update() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "UPDATE Equipements SET IdProprietaire = ?, etat_Materiel = ?, Couleur = ?, Nom = ?, Marque = ?, Modele = ?, memoire_ROM = ?, memoire_RAM = ?, numero_serie = ?, photoUrl = ? WHERE address_MAC = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, this.getIdProprietaire());
            pstmt.setString(2, this.getEtat_Materiel());
            pstmt.setString(3, this.getCouleur());
            pstmt.setString(4, this.getNom());
            pstmt.setString(5, this.getMarque());
            pstmt.setString(6, this.getModele());
            pstmt.setFloat(7, this.getMemoire_ROM());
            pstmt.setFloat(8, this.getMemoire_RAM());
            pstmt.setString(9, this.getNumero_serie());
            pstmt.setString(10, this.getPhotoUrl());
            pstmt.setString(11, this.getAddress_MAC());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour mettre à jour uniquement le statut d'un équipement
    public boolean updateStatus() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "UPDATE Equipements SET etat_Materiel = ? WHERE address_MAC = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.getEtat_Materiel());
            pstmt.setString(2, this.getAddress_MAC());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour supprimer un équipement
    public boolean delete() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "DELETE FROM Equipements WHERE address_MAC = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.getAddress_MAC());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour récupérer tous les équipements
    public ResultSet getAll(int id) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            String query = "SELECT * FROM Equipements WHERE IdProprietaire = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            // Fermer la connexion en cas d'erreur
            if (conn != null) conn.close();
            throw e;
        }
    }
    
    // Méthode pour récupérer un équipement par son adresse MAC
    public ResultSet getByMAC() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            String query = "SELECT * FROM Equipements WHERE address_MAC = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.getAddress_MAC());
            // Note: La connexion sera fermée par l'appelant
            return pstmt.executeQuery();
        } catch (SQLException e) {
            // Fermer la connexion en cas d'erreur
            if (conn != null) conn.close();
            throw e;
        }
        // Note: Il est recommandé de modifier cette méthode pour retourner un objet EquipementsModel
        // plutôt qu'un ResultSet pour une meilleure gestion des ressources
    }
}
