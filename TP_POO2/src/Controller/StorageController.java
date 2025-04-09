package Controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class StorageController {
    // Dossier où les images seront stockées
    private static final String UPLOAD_DIRECTORY = "uploads";
    
    // URL de base pour accéder aux images
    private static final String BASE_URL = "http://10.0.2.2:9000/uploads";
    
    // Handler pour télécharger une image
    public static HttpHandler uploadImage = exchange -> {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                // Vérifier si le dossier d'upload existe, sinon le créer
                File uploadDir = new File(UPLOAD_DIRECTORY);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                // Extraire les données de la requête multipart
                String boundary = extractBoundary(exchange);
                if (boundary == null) {
                    sendResponse(exchange, 400, "{\"error\": \"Requête multipart invalide\"}");
                    return;
                }
                
                // Lire le contenu de la requête
                InputStream inputStream = exchange.getRequestBody();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                byte[] requestData = baos.toByteArray();
                
                // Traiter les données multipart
                String fileName = processMultipartData(requestData, boundary, uploadDir);
                
                if (fileName != null) {
                    // Construire l'URL de l'image
                    String imageUrl = BASE_URL + "/" + fileName;
                    
                    // Envoyer la réponse avec l'URL de l'image
                    sendResponse(exchange, 201, "{\"url\": \"" + imageUrl + "\"}");
                } else {
                    sendResponse(exchange, 400, "{\"error\": \"Aucun fichier trouvé dans la requête\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
        }
    };
    
    // Méthode pour extraire la boundary d'une requête multipart
    private static String extractBoundary(HttpExchange exchange) {
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        if (contentType != null && contentType.startsWith("multipart/form-data")) {
            int boundaryIndex = contentType.indexOf("boundary=");
            if (boundaryIndex != -1) {
                return contentType.substring(boundaryIndex + 9);
            }
        }
        return null;
    }
    
    // Méthode pour traiter les données multipart et sauvegarder le fichier
    private static String processMultipartData(byte[] data, String boundary, File uploadDir) throws IOException {
        String dataString = new String(data, StandardCharsets.UTF_8);
        String boundaryMarker = "--" + boundary;
        
        // Trouver les parties de la requête multipart
        String[] parts = dataString.split(boundaryMarker);
        
        for (String part : parts) {
            // Ignorer les parties vides ou la fin de la requête
            if (part.trim().isEmpty() || part.trim().equals("--")) {
                continue;
            }
            
            // Vérifier si cette partie contient un fichier
            if (part.contains("Content-Disposition: form-data") && part.contains("filename=")) {
                // Extraire le nom du fichier original
                int filenameStart = part.indexOf("filename=\"")
                        + "filename=\"".length();
                int filenameEnd = part.indexOf("\"", filenameStart);
                String originalFilename = part.substring(filenameStart, filenameEnd);
                
                // Générer un nom de fichier unique
                String fileExtension = "";
                int lastDot = originalFilename.lastIndexOf('.');
                if (lastDot > 0) {
                    fileExtension = originalFilename.substring(lastDot);
                }
                String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
                
                // Trouver le début du contenu du fichier
                int contentStart = part.indexOf("\r\n\r\n") + 4;
                if (contentStart == -1) {
                    continue;
                }
                
                // Trouver la fin du contenu du fichier
                int contentEnd = part.length();
                
                // Extraire le contenu du fichier
                String fileContent = part.substring(contentStart, contentEnd);
                byte[] fileBytes = fileContent.getBytes(StandardCharsets.UTF_8);
                
                // Sauvegarder le fichier
                Path filePath = Paths.get(uploadDir.getPath(), uniqueFilename);
                Files.write(filePath, fileBytes);
                
                return uniqueFilename;
            }
        }
        
        return null;
    }
    
    // Méthode pour envoyer une réponse HTTP
    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}