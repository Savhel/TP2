package Controller;

import Models.UsersModel;
import Services.UserServices.InsertionUsers.InsertionUsers;
import Services.UserServices.ReadUsers.ReadUsers;
import Services.UserServices.UpdateUsers.UpdateUsers;
import Services.UserServices.DeleteUsers.DeleteUsers;
import Services.Utils.ResultSetToJson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;

import static java.net.URLDecoder.decode;

public class UserController {
//    private final UserService UserService = new UserService();

    // Handler pour l'authentification (login)
    public static HttpHandler loginUser = exchange -> {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                // Lire et parser le corps de la requête
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String[] params = requestBody.split("&");
                String email = decode(params[0].split("=")[1], StandardCharsets.UTF_8);
                String password = decode(params[1].split("=")[1], StandardCharsets.UTF_8);


                ReadUsers readUsers = new ReadUsers();
                String jsonResponse = readUsers.login(email, password);

                
                // Vérifier si un utilisateur a été trouvé
                if (jsonResponse == null) {
                    sendResponse(exchange, 401, "{\"error\": \"Email ou mot de passe incorrect\"}");
                } else {
                    System.out.println(jsonResponse == null);
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

    public static HttpHandler createUser = exchange -> {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                // Parse request body (ex: "name=John&age=30")
                String[] params = requestBody.split("&");
                String nom = decode(params[0].split("=")[1], StandardCharsets.UTF_8);
                String prenom = decode(params[1].split("=")[1], StandardCharsets.UTF_8);
                String email = decode(params[2].split("=")[1], StandardCharsets.UTF_8);
                String password = decode(params[3].split("=")[1], StandardCharsets.UTF_8);
                String NumTel = decode(params[4].split("=")[1], StandardCharsets.UTF_8);
                String address = decode(params[5].split("=")[1], StandardCharsets.UTF_8);

                UsersModel user = new UsersModel();
                user.setNom(nom);
                user.setPrenom(prenom);
                user.setEmail(email);
                user.setPassword(password);
                user.setNumtel(NumTel);
                user.setAddress(address);

                InsertionUsers insertionUsers = new InsertionUsers(user);
                int userId = insertionUsers.InsertionUser();

                if (userId > 0) {
                    // Créer une réponse JSON avec l'ID généré
                    String jsonResponse = String.format("{\"id_user\":%d,\"nom\":\"%s\",\"prenom\":\"%s\",\"email\":\"%s\",\"numtel\":\"%s\",\"address\":\"%s\"}", 
                        userId, nom, prenom, email, NumTel, address);
                    sendResponse(exchange, 201, jsonResponse);
                } else {
                    sendResponse(exchange, 500, "{\"error\": \"Échec de la création de l'utilisateur\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "Method Not Allowed");
        }
    };

    public static HttpHandler getUser = exchange -> {
        if ("GET".equals(exchange.getRequestMethod())) {
            String[] path = exchange.getRequestURI().getPath().split("/");
            int id = Integer.parseInt(path[path.length - 1]);

            ReadUsers readUsers = new ReadUsers();
            String jsonResponse = "";
            try {
                jsonResponse = readUsers.read(id);

            } catch (Exception e) {
                System.out.println(STR."erreur : \{e.getMessage()}");
            }

            if (jsonResponse != null) {
                sendResponse(exchange, 200, jsonResponse);
            } else {
                sendResponse(exchange, 404, "User not found");
            }
        }else {
            sendResponse(exchange, 405, "Method Not Allowed");
        }
    };

    // Add this handler to your UserController class
    
    public static HttpHandler deleteUser = exchange -> {
        if ("DELETE".equals(exchange.getRequestMethod())) {
            try {
                // Extract the ID from the path
                String[] path = exchange.getRequestURI().getPath().split("/");
                int id = Integer.parseInt(path[path.length - 1]);

                boolean success = DeleteUsers.deleteUser(id);
                
                if (success) {
                    sendResponse(exchange, 200, "{\"success\": true, \"message\": \"User deleted successfully\"}");
                } else {
                    sendResponse(exchange, 404, "{\"success\": false, \"message\": \"User not found\"}");
                }
            } catch (NumberFormatException e) {
                sendResponse(exchange, 400, "{\"error\": \"Invalid user ID\"}");
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };

    public static HttpHandler updateUser = exchange -> {
        if ("PUT".equals(exchange.getRequestMethod())) {
            try {
                // Extract the ID from the path
                String[] path = exchange.getRequestURI().getPath().split("/");
                int id = Integer.parseInt(path[path.length - 1]);
                
                // Read and parse the request body
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String[] params = requestBody.split("&");
                String nom = decode(params[0].split("=")[1], StandardCharsets.UTF_8);
                String prenom = decode(params[1].split("=")[1], StandardCharsets.UTF_8);
                String email = decode(params[2].split("=")[1], StandardCharsets.UTF_8);
                String numTel = decode(params[3].split("=")[1], StandardCharsets.UTF_8);
                String address = decode(params[4].split("=")[1], StandardCharsets.UTF_8);
                
                // Create user model with updated data
                UsersModel user = new UsersModel();
                user.setId(id);
                user.setNom(nom);
                user.setPrenom(prenom);
                user.setEmail(email);
                user.setNumtel(numTel);
                user.setAddress(address);
                
                // Call service to update the user
                UpdateUsers updateUsers = new UpdateUsers(user);
                boolean success = updateUsers.updateUser();
                
                if (success) {
                    sendResponse(exchange, 200, "{\"success\": true, \"message\": \"User updated successfully\"}");
                } else {
                    sendResponse(exchange, 404, "{\"success\": false, \"message\": \"User not found or update failed\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };

    public static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}