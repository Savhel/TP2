package Services.Utils;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetToJson {
    public static String usersResultSetToJson(ResultSet rs) throws SQLException {
        StringBuilder jsonBuilder = new StringBuilder("[");

        boolean first = true;
        while (rs.next()) {
            if (!first) {
                jsonBuilder.append(",");
            }
            first = false;

            jsonBuilder.append("{");
            jsonBuilder.append("\"id_user\":").append(rs.getInt("id_user")).append(",");
            jsonBuilder.append("\"nom\":\"").append(rs.getString("nom")).append("\",");
            jsonBuilder.append("\"prenom\":\"").append(rs.getString("prenom")).append("\",");
            jsonBuilder.append("\"email\":\"").append(rs.getString("email")).append("\",");
            jsonBuilder.append("\"numtel\":\"").append(rs.getString("numtel")).append("\",");
            jsonBuilder.append("\"address\":\"").append(rs.getString("address")).append("\"");
            jsonBuilder.append("}");
        }

        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
    
    public static String phonesResultSetToJson(ResultSet rs) throws SQLException {

        StringBuilder jsonBuilder = new StringBuilder("[");

        boolean first = true;
        while (rs.next()) {
            if (!first) {
                jsonBuilder.append(",");
            }
            first = false;
            System.out.println("bonjour");

            jsonBuilder.append("{");
            jsonBuilder.append("\"IMEI\":\"").append(rs.getString("IMEI")).append("\",");
            jsonBuilder.append("\"IdProprietaire\":").append(rs.getInt("IdProprietaire")).append(",");
            jsonBuilder.append("\"etat_Materiel\":\"").append(rs.getString("etat_Materiel")).append("\",");
            jsonBuilder.append("\"Couleur\":\"").append(rs.getString("Couleur")).append("\",");
            jsonBuilder.append("\"Nom\":\"").append(rs.getString("Nom")).append("\",");
            jsonBuilder.append("\"Marque\":\"").append(rs.getString("Marque")).append("\",");
            jsonBuilder.append("\"Modele\":\"").append(rs.getString("Modele")).append("\",");
            jsonBuilder.append("\"memoire_ROM\":").append(rs.getFloat("memoire_ROM")).append(",");
            jsonBuilder.append("\"memoire_RAM\":").append(rs.getFloat("memoire_RAM")).append(",");
            jsonBuilder.append("\"numero_serie\":\"").append(rs.getString("numero_serie")).append("\",");
            jsonBuilder.append("\"photoUrl\":\"").append(rs.getString("photoUrl") != null ? rs.getString("photoUrl") : "").append("\"");
            jsonBuilder.append("}");
        }

        jsonBuilder.append("]");
        System.out.println(jsonBuilder.toString());
        return jsonBuilder.toString();
    }
    
    public static String equipmentsResultSetToJson(ResultSet rs) throws SQLException {
        StringBuilder jsonBuilder = new StringBuilder("[");

        boolean first = true;
        while (rs.next()) {
            if (!first) {
                jsonBuilder.append(",");
            }
            first = false;

            jsonBuilder.append("{");
            jsonBuilder.append("\"address_MAC\":\"").append(rs.getString("address_MAC")).append("\",");
            jsonBuilder.append("\"IdProprietaire\":").append(rs.getInt("IdProprietaire")).append(",");
            jsonBuilder.append("\"etat_Materiel\":\"").append(rs.getString("etat_Materiel")).append("\",");
            jsonBuilder.append("\"Couleur\":\"").append(rs.getString("Couleur")).append("\",");
            jsonBuilder.append("\"Nom\":\"").append(rs.getString("Nom")).append("\",");
            jsonBuilder.append("\"Marque\":\"").append(rs.getString("Marque")).append("\",");
            jsonBuilder.append("\"Modele\":\"").append(rs.getString("Modele")).append("\",");
            jsonBuilder.append("\"memoire_ROM\":").append(rs.getFloat("memoire_ROM")).append(",");
            jsonBuilder.append("\"memoire_RAM\":").append(rs.getFloat("memoire_RAM")).append(",");
            jsonBuilder.append("\"numero_serie\":\"").append(rs.getString("numero_serie")).append("\",");
            jsonBuilder.append("\"photoUrl\":\"").append(rs.getString("photoUrl") != null ? rs.getString("photoUrl") : "").append("\"");
            jsonBuilder.append("}");
        }

        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}
