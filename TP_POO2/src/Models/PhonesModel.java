package Models;

import Services.DatabaseServices.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhonesModel extends MaterielsModel {
    private String IMEI;

    public PhonesModel() {
        super();
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }
    
    // Méthode pour enregistrer un nouveau téléphone dans la base de données
    public boolean save() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "INSERT INTO Phones (IMEI, IdProprietaire, etat_Materiel, Couleur, Nom, Marque, Modele, memoire_ROM, memoire_RAM, numero_serie, photoUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.getIMEI());
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
    
    // Méthode pour mettre à jour un téléphone existant
    public boolean update() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "UPDATE Phones SET IdProprietaire = ?, etat_Materiel = ?, Couleur = ?, Nom = ?, Marque = ?, Modele = ?, memoire_ROM = ?, memoire_RAM = ?, numero_serie = ?, photoUrl = ? WHERE IMEI = ?";
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
            pstmt.setString(11, this.getIMEI());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour mettre à jour uniquement le statut d'un téléphone
    public boolean updateStatus() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "UPDATE Phones SET etat_Materiel = ? WHERE IMEI = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.getEtat_Materiel());
            pstmt.setString(2, this.getIMEI());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour supprimer un téléphone
    public boolean delete() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            String query = "DELETE FROM Phones WHERE IMEI = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.getIMEI());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Méthode pour récupérer tous les téléphones
    public ResultSet getAll(int id) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            String query = "SELECT * FROM Phones WHERE IdProprietaire = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            if (conn != null) conn.close();
            throw e;
        }
    }
    
    // Méthode pour récupérer un téléphone par son IMEI
    public ResultSet getByIMEI() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            String query = "SELECT * FROM Phones WHERE IMEI = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.getIMEI());
            // Note: La connexion sera fermée par l'appelant
            return pstmt.executeQuery();
        } catch (SQLException e) {
            // Fermer la connexion en cas d'erreur
            if (conn != null) conn.close();
            throw e;
        }
    }
}
