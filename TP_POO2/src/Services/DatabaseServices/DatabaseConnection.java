package Services.DatabaseServices;

import java.sql.*;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    // Configuration de la base de données
    private final String URL = "jdbc:mysql://localhost:3306/TP2";
    private final String USER = "root";
    private final String PASSWORD = "12345";

    // Constructeur privé
    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de connexion à la base de données");
        }
    }

    // Méthode pour obtenir l'instance unique
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Récupérer la connexion
    public Connection getConnection() {
        try {
            // Vérifier si la connexion est fermée ou nulle, et la recréer si nécessaire
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la vérification ou recréation de la connexion");
        }
        return connection;
    }

    // Méthode pour fermer la connexion
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM Phones WHERE IMEI = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, "45123");
//        System.out.println("emie: " );
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            System.out.println("emie: " + rs.getString("nom"));
        }
    }
}
