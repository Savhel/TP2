package Controller;

import Models.PhonesModel;
import Services.Utils.ResultSetToJson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.Arrays;

import static java.net.URLDecoder.decode;

public class PhoneController {

    // Handler pour créer un nouveau téléphone
    public static HttpHandler createPhone = exchange -> {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String[] params = requestBody.split("&");
                
                // Créer un nouveau modèle de téléphone
                PhonesModel phone = new PhonesModel();
                
                // Extraire les paramètres du corps de la requête
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0];
                        String value = decode(keyValue[1], StandardCharsets.UTF_8);
                        
                        switch (key) {
                            case "Nom" -> phone.setNom(value);
                            case "Marque" -> phone.setMarque(value);
                            case "Modele" -> phone.setModele(value);
                            case "memoire_ROM" -> phone.setMemoire_ROM(Integer.parseInt(value));
                            case "memoire_RAM" -> phone.setMemoire_RAM(Integer.parseInt(value));
                            case "numero_serie" -> phone.setNumero_serie(value);
                            case "IdProprietaire" -> phone.setIdPropretaire(Integer.parseInt(value));
                            case "Couleur" -> phone.setCouleur(value);
                            case "etat_Materiel" -> phone.setEtat_Materiel(value);
                            case "IMEI" -> phone.setIMEI(value);
                            case "photoUrl" -> phone.setPhotoUrl(value);
                        }
                    }
                }
                
                // Insérer le téléphone dans la base de données
                boolean success = phone.save();
                
                if (success) {
                    // Créer une réponse JSON avec les données du téléphone
                    String jsonResponse = String.format(
                        "{\"IMEI\":\"%s\",\"IdProprietaire\":%d,\"etat_Materiel\":\"%s\",\"Couleur\":\"%s\",\"Nom\":\"%s\",\"Marque\":\"%s\",\"Modele\":\"%s\",\"memoire_ROM\":%f,\"memoire_RAM\":%f,\"numero_serie\":\"%s\",\"photoUrl\":\"%s\"}", 
                        phone.getIMEI(), phone.getIdProprietaire(), phone.getEtat_Materiel(), phone.getCouleur(), 
                        phone.getNom(), phone.getMarque(), phone.getModele(), phone.getMemoire_ROM(), 
                        phone.getMemoire_RAM(), phone.getNumero_serie(), phone.getPhotoUrl());
                    
                    sendResponse(exchange, 201, jsonResponse);
                } else {
                    sendResponse(exchange, 500, "{\"error\": \"Échec de la création du téléphone\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour récupérer tous les téléphones
    public static HttpHandler getAllPhones = exchange -> {
        String[] path = exchange.getRequestURI().getPath().split("/");
        int id = Integer.parseInt(path[path.length - 1]);
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                PhonesModel phoneModel = new PhonesModel();
                ResultSet phones = phoneModel.getAll(id);
                
                String jsonResponse = ResultSetToJson.phonesResultSetToJson(phones);
                sendResponse(exchange, 200, jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour récupérer un téléphone par IMEI
    public static HttpHandler getPhoneByIMEI = exchange -> {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                String[] path = exchange.getRequestURI().getPath().split("/");
                String imei = path[path.length - 1];
                System.out.println(exchange.getRequestURI().getPath());
                PhonesModel phoneModel = new PhonesModel();
                phoneModel.setIMEI(imei.split("=")[1]);
                ResultSet phone = phoneModel.getByIMEI();
                
                String jsonResponse = ResultSetToJson.phonesResultSetToJson(phone);

                if (jsonResponse.equals("[]")) {
                    sendResponse(exchange, 404, "{\"error\": \"Téléphone non trouvé\"}");
                } else {
                    sendResponse(exchange, 200, jsonResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour rechercher un téléphone par IMEI
    public static HttpHandler searchPhoneByIMEI = exchange -> {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                String query = exchange.getRequestURI().getPath();
                String imei = query.split("=")[1];
                
                PhonesModel phoneModel = new PhonesModel();
                phoneModel.setIMEI(imei);
                ResultSet phone = phoneModel.getByIMEI();

                
                String jsonResponse = ResultSetToJson.phonesResultSetToJson(phone);

                if (jsonResponse.equals("[]")) {
                    sendResponse(exchange, 404, "{\"error\": \"Téléphone non trouvé\"}");
                } else {
                    sendResponse(exchange, 200, jsonResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour mettre à jour l'état d'un téléphone
    public static HttpHandler updatePhoneStatus = exchange -> {
        if ("PUT".equals(exchange.getRequestMethod())) {
            try {
                String[] path = exchange.getRequestURI().getPath().split("/");
                String imei = path[path.length - 2]; // L'IMEI est avant /status dans le chemin
                
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String[] params = requestBody.split("&");
                String newStatus = "";
                
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2 && "etat_Materiel".equals(keyValue[0])) {
                        newStatus = decode(keyValue[1], StandardCharsets.UTF_8);
                        break;
                    }
                }
                
                if (newStatus.isEmpty()) {
                    sendResponse(exchange, 400, "{\"error\": \"Le statut est requis\"}");
                    return;
                }
                
                PhonesModel phoneModel = new PhonesModel();
                phoneModel.setIMEI(imei);
                phoneModel.setEtat_Materiel(newStatus);
                boolean success = phoneModel.updateStatus();
                
                if (success) {
                    sendResponse(exchange, 200, "{\"success\": true, \"message\": \"Statut du téléphone mis à jour avec succès\"}");
                } else {
                    sendResponse(exchange, 404, "{\"success\": false, \"message\": \"Téléphone non trouvé ou mise à jour échouée\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour mettre à jour un téléphone
    public static HttpHandler updatePhone = exchange -> {
        if ("PUT".equals(exchange.getRequestMethod())) {
            try {
                String[] path = exchange.getRequestURI().getPath().split("/");
                String imei = path[path.length - 1];
                
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String[] params = requestBody.split("&");
                
                PhonesModel phone = new PhonesModel();
                phone.setIMEI(imei);
                
                // Extraire les paramètres du corps de la requête
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0];
                        String value = decode(keyValue[1], StandardCharsets.UTF_8);
                        
                        switch (key) {
                            case "Nom" -> phone.setNom(value);
                            case "Marque" -> phone.setMarque(value);
                            case "Modele" -> phone.setModele(value);
                            case "memoire_ROM" -> phone.setMemoire_ROM(Integer.parseInt(value));
                            case "memoire_RAM" -> phone.setMemoire_RAM(Integer.parseInt(value));
                            case "numero_serie" -> phone.setNumero_serie(value);
                            case "IdProprietaire" -> phone.setIdPropretaire(Integer.parseInt(value));
                            case "Couleur" -> phone.setCouleur(value);
                            case "etat_Materiel" -> phone.setEtat_Materiel(value);
                            case "photoUrl" -> phone.setPhotoUrl(value);
                        }
                    }
                }
                
                boolean success = phone.update();
                
                if (success) {
                    sendResponse(exchange, 200, "{\"success\": true, \"message\": \"Téléphone mis à jour avec succès\"}");
                } else {
                    sendResponse(exchange, 404, "{\"success\": false, \"message\": \"Téléphone non trouvé ou mise à jour échouée\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour supprimer un téléphone
    public static HttpHandler deletePhone = exchange -> {
        if ("DELETE".equals(exchange.getRequestMethod())) {
            try {
                String[] path = exchange.getRequestURI().getPath().split("/");
                String imei = path[path.length - 1];
                
                PhonesModel phoneModel = new PhonesModel();
                phoneModel.setIMEI(imei);
                boolean success = phoneModel.delete();
                
                if (success) {
                    sendResponse(exchange, 200, "{\"success\": true, \"message\": \"Téléphone supprimé avec succès\"}");
                } else {
                    sendResponse(exchange, 404, "{\"success\": false, \"message\": \"Téléphone non trouvé\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Méthode pour envoyer une réponse HTTP
    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, 0); // Utiliser une longueur 0 pour les réponses chunked
        
        try (OutputStream os = exchange.getResponseBody()) {
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            int bufferSize = 4096; // Taille du buffer
            int offset = 0;
            
            while (offset < bytes.length) {
                int length = Math.min(bufferSize, bytes.length - offset);
                os.write(bytes, offset, length);
                offset += length;
            }
        }
    }
}
