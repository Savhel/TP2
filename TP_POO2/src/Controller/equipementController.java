package Controller;

import Models.EquipementsModel;
import Services.EquipementServices.Classes.DeleteEquipements.DeleteEquipements;
import Services.EquipementServices.Classes.InsertionEquipements.InsertionEquipements;
import Services.EquipementServices.Classes.ReadEquipements.ReadEquipements;
import Services.EquipementServices.Classes.UpdateEquipements.UpdateEquipements;
import Services.EquipementServices.Classes.UpdateEquipementsState.UpdateEquipementsState;
import Services.Utils.ResultSetToJson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;

import static java.net.URLDecoder.decode;

public class equipementController {

    // Handler pour créer un nouvel équipement
    public static HttpHandler createEquipment = exchange -> {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String[] params = requestBody.split("&");
                
                // Créer un nouveau modèle d'équipement
                EquipementsModel equipment = new EquipementsModel();
                
                // Extraire les paramètres du corps de la requête
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0];
                        String value = decode(keyValue[1], StandardCharsets.UTF_8);
                        
                        switch (key) {
                            case "Nom" -> equipment.setNom(value);
                            case "Marque" -> equipment.setMarque(value);
                            case "Modele" -> equipment.setModele(value);
                            case "memoire_ROM" -> equipment.setMemoire_ROM(Integer.parseInt(value));
                            case "memoire_RAM" -> equipment.setMemoire_RAM(Integer.parseInt(value));
                            case "numero_serie" -> equipment.setNumero_serie(value);
                            case "IdProprietaire" -> equipment.setIdPropretaire(Integer.parseInt(value));
                            case "Couleur" -> equipment.setCouleur(value);
                            case "etat_Materiel" -> equipment.setEtat_Materiel(value);
                            case "address_MAC" -> equipment.setAddress_MAC(value);
                            case "photoUrl" -> equipment.setPhotoUrl(value);
                        }
                    }
                }
                
                // Insérer l'équipement dans la base de données
                boolean success = new InsertionEquipements(equipment).Insertion();
                
                if (success) {
                    // Créer une réponse JSON avec les données de l'équipement
                    String jsonResponse = String.format(
                        "{\"address_MAC\":\"%s\",\"IdProprietaire\":%d,\"etat_Materiel\":\"%s\",\"Couleur\":\"%s\",\"Nom\":\"%s\",\"Marque\":\"%s\",\"Modele\":\"%s\",\"memoire_ROM\":%f,\"memoire_RAM\":%f,\"numero_serie\":\"%s\",\"photoUrl\":\"%s\"}", 
                        equipment.getAddress_MAC(), equipment.getIdProprietaire(), equipment.getEtat_Materiel(), equipment.getCouleur(), 
                        equipment.getNom(), equipment.getMarque(), equipment.getModele(), equipment.getMemoire_ROM(), 
                        equipment.getMemoire_RAM(), equipment.getNumero_serie(), equipment.getPhotoUrl());
                    
                    sendResponse(exchange, 201, jsonResponse);
                } else {
                    sendResponse(exchange, 500, "{\"error\": \"Échec de la création de l'équipement\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour récupérer tous les équipements
    public static HttpHandler getAllEquipments = exchange -> {
        String[] path = exchange.getRequestURI().getPath().split("/");
        int id = Integer.parseInt(path[path.length - 1]);
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                EquipementsModel equipmentModel = new EquipementsModel();
                ResultSet equipments = new ReadEquipements(equipmentModel).read(id);
                
                String jsonResponse = ResultSetToJson.equipmentsResultSetToJson(equipments);
                sendResponse(exchange, 200, jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour récupérer un équipement par adresse MAC
    public static HttpHandler getEquipmentByMAC = exchange -> {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                String[] path = exchange.getRequestURI().getPath().split("/");
                String macAddress = path[path.length - 1];
                
                EquipementsModel equipmentModel = new EquipementsModel();
                equipmentModel.setAddress_MAC(macAddress);

                ResultSet equipment = new ReadEquipements(equipmentModel).getByMAC();
                
                String jsonResponse = ResultSetToJson.equipmentsResultSetToJson(equipment);
                if (jsonResponse.equals("[]")) {
                    sendResponse(exchange, 404, "{\"error\": \"Équipement non trouvé\"}");
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
    
    // Handler pour rechercher un équipement par adresse MAC
    public static HttpHandler   searchEquipmentByMAC = exchange -> {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                String query = exchange.getRequestURI().getQuery();
                String macAddress = query.split("=")[1];
                
                EquipementsModel equipmentModel = new EquipementsModel();
                System.out.println(macAddress);
                equipmentModel.setAddress_MAC(macAddress);
                ResultSet equipment = new ReadEquipements(equipmentModel).getByMAC();
                
                String jsonResponse = ResultSetToJson.equipmentsResultSetToJson(equipment);
                if (jsonResponse.equals("[]")) {
                    sendResponse(exchange, 404, "{\"error\": \"Équipement non trouvé\"}");
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
    
    // Handler pour mettre à jour l'état d'un équipement
    public static HttpHandler updateEquipmentStatus = exchange -> {
        if ("PUT".equals(exchange.getRequestMethod())) {
            try {
                String[] path = exchange.getRequestURI().getPath().split("/");
                String macAddress = path[path.length - 2]; // L'adresse MAC est avant /status dans le chemin
                
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
                
                EquipementsModel equipmentModel = new EquipementsModel();
                equipmentModel.setAddress_MAC(macAddress);
                equipmentModel.setEtat_Materiel(newStatus);
                boolean success = new UpdateEquipementsState(equipmentModel).UpdateState();
                
                if (success) {
                    sendResponse(exchange, 200, "{\"success\": true, \"message\": \"Statut de l'équipement mis à jour avec succès\"}");
                } else {
                    sendResponse(exchange, 404, "{\"success\": false, \"message\": \"Équipement non trouvé ou mise à jour échouée\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour mettre à jour un équipement
    public static HttpHandler updateEquipment = exchange -> {
        if ("PUT".equals(exchange.getRequestMethod())) {
            try {
                String[] path = exchange.getRequestURI().getPath().split("/");
                String macAddress = path[path.length - 1];
                
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String[] params = requestBody.split("&");
                
                EquipementsModel equipment = new EquipementsModel();
                equipment.setAddress_MAC(macAddress);
                
                // Extraire les paramètres du corps de la requête
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0];
                        String value = decode(keyValue[1], StandardCharsets.UTF_8);
                        
                        switch (key) {
                            case "Nom" -> equipment.setNom(value);
                            case "Marque" -> equipment.setMarque(value);
                            case "Modele" -> equipment.setModele(value);
                            case "memoire_ROM" -> equipment.setMemoire_ROM(Integer.parseInt(value));
                            case "memoire_RAM" -> equipment.setMemoire_RAM(Integer.parseInt(value));
                            case "numero_serie" -> equipment.setNumero_serie(value);
                            case "IdProprietaire" -> equipment.setIdPropretaire(Integer.parseInt(value));
                            case "Couleur" -> equipment.setCouleur(value);
                            case "etat_Materiel" -> equipment.setEtat_Materiel(value);
                            case "photoUrl" -> equipment.setPhotoUrl(value);
                        }
                    }
                }
                
                boolean success = new UpdateEquipements(equipment).UpdateMaterial();
                
                if (success) {
                    sendResponse(exchange, 200, "{\"success\": true, \"message\": \"Équipement mis à jour avec succès\"}");
                } else {
                    sendResponse(exchange, 404, "{\"success\": false, \"message\": \"Équipement non trouvé ou mise à jour échouée\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Handler pour supprimer un équipement
    public static HttpHandler deleteEquipment = exchange -> {
        if ("DELETE".equals(exchange.getRequestMethod())) {
            try {
                String[] path = exchange.getRequestURI().getPath().split("/");
                String macAddress = path[path.length - 1];
                
                EquipementsModel equipmentModel = new EquipementsModel();
                equipmentModel.setAddress_MAC(macAddress);
                boolean success = new DeleteEquipements(equipmentModel).delete();
                
                if (success) {
                    sendResponse(exchange, 200, "{\"success\": true, \"message\": \"Équipement supprimé avec succès\"}");
                } else {
                    sendResponse(exchange, 404, "{\"success\": false, \"message\": \"Équipement non trouvé\"}");
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
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
