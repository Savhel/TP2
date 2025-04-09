import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;
import Controller.UserController;
import Controller.PhoneController;
import Controller.equipementController;

// Supprimez cet import statique qui n'est pas approprié
// import static java.sql.DriverManager.println;

public class App {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(9000), 0);
//        PersonController personController = new PersonController();
//        TaskController taskController = new TaskController();

        // Contexte global pour /api
        server.createContext("/api", exchange -> {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();

            // Route GET /api/persons/{id}
            if ("GET".equals(method) && path.matches("/api/persons/\\d+")) {
                UserController.getUser.handle(exchange);
                System.out.println("GET " + path);
            }
            // Route POST /api/persons
            else if ("POST".equals(method) && "/api/persons".equals(path)) {
                UserController.createUser.handle(exchange);
                System.out.println("POST /api/persons");
            }
            // Route DELETE /api/persons/{id}
            else if ("DELETE".equals(method) && path.matches("/api/persons/\\d+")) {
                UserController.deleteUser.handle(exchange);
                System.out.println("DELETE " + path);
            }
            // Route PUT /api/persons/{id} (for updating user data)
            else if ("PUT".equals(method) && path.matches("/api/persons/\\d+")) {
                UserController.updateUser.handle(exchange);
                System.out.println("PUT " + path);
            }
            // Route POST /api/login (pour l'authentification)
            else if ("POST".equals(method) && "/api/login".equals(path)) {
                UserController.loginUser.handle(exchange);
                System.out.println("POST /api/login");
            }
                // Routes pour les téléphones
            else if ("POST".equals(method) && "/api/phones".equals(path)) {
                PhoneController.createPhone.handle(exchange);
                System.out.println("POST /api/phones");
            }
            else if ("GET".equals(method) && path.matches("/api/phones/\\d+")) {
                PhoneController.getAllPhones.handle(exchange);
                System.out.println("GET /api/phones");
            }
            else if ("GET".equals(method) && path.matches("/api/phones/[^/]+")) {
                PhoneController.getPhoneByIMEI.handle(exchange);
                System.out.println("GET " + path);
            }
            else if ("PUT".equals(method) && path.matches("/api/phones/[^/]+")) {
                PhoneController.updatePhone.handle(exchange);
                System.out.println("PUT " + path);
            }
            else if ("PUT".equals(method) && path.matches("/api/phones/[^/]+/status")) {
                PhoneController.updatePhoneStatus.handle(exchange);
                System.out.println("PUT " + path);
            }
            else if ("DELETE".equals(method) && path.matches("/api/phones/[^/]+")) {
                PhoneController.deletePhone.handle(exchange);
                System.out.println("DELETE " + path);
            }
            else if ("GET".equals(method) && path.startsWith("/api/phones/search")) {
                PhoneController.searchPhoneByIMEI.handle(exchange);
                System.out.println("GET " + path);
            }
            
            // Routes pour les équipements
            else if ("POST".equals(method) && "/api/equipments".equals(path)) {
                equipementController.createEquipment.handle(exchange);
                System.out.println("POST /api/equipments");
            }
            else if ("GET".equals(method) && path.matches("/api/equipments/\\d+")) {
                equipementController.getAllEquipments.handle(exchange);
                System.out.println("GET /api/equipments");
            }
            else if ("GET".equals(method) && path.startsWith("/api/equipments/search")) {
                System.out.println("GET " + path);
                equipementController.searchEquipmentByMAC.handle(exchange);
            }
            else if ("GET".equals(method) && path.matches("/api/equipments/[^/]+")) {
                equipementController.getEquipmentByMAC.handle(exchange);
                System.out.println("GET " + path);
            }
            else if ("PUT".equals(method) && path.matches("/api/equipments/[^/]+")) {
                equipementController.updateEquipment.handle(exchange);
                System.out.println("PUT " + path);
            }
            else if ("PUT".equals(method) && path.matches("/api/equipments/[^/]+/status")) {
                equipementController.updateEquipmentStatus.handle(exchange);
                System.out.println("PUT " + path);
            }
            else if ("DELETE".equals(method) && path.matches("/api/equipments/[^/]+")) {
                equipementController.deleteEquipment.handle(exchange);
                System.out.println("DELETE " + path);
            }

            
            // Route non trouvée
            else {
                // Remplacer l'appel à controller.UserController.sendResponse par une méthode locale
                sendResponse(exchange, 404, "Endpoint not found");
            }
        });

        server.start();
        System.out.println("Server started on port 9000");
    }
    
    // Ajout d'une méthode pour envoyer des réponses HTTP
    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        // Set Content-Type header to application/json
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}