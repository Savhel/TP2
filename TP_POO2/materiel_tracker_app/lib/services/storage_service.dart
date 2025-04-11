import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;

class StorageService {
  // URL de base pour le service de stockage
  static const String baseUrl = 'http://192.168.1.101:9000/api/storage';

  // Méthode pour télécharger une image
  static Future<String> uploadImage(File imageFile) async {
    try {
      // Créer une requête multipart
      var request = http.MultipartRequest('POST', Uri.parse('$baseUrl/upload'));
      
      // Ajouter le fichier à la requête
      var fileStream = http.ByteStream(imageFile.openRead());
      var fileLength = await imageFile.length();
      
      var multipartFile = http.MultipartFile(
        'file',
        fileStream,
        fileLength,
        filename: imageFile.path.split('/').last,
      );
      
      request.files.add(multipartFile);
      
      // Envoyer la requête
      var response = await request.send();
      
      // Vérifier le statut de la réponse
      if (response.statusCode >= 200 && response.statusCode < 300) {
        // Lire la réponse
        var responseData = await response.stream.bytesToString();
        var data = jsonDecode(responseData);
        
        // Retourner l'URL de l'image téléchargée
        return data['url'];
      } else {
        throw Exception('Échec du téléchargement de l\'image: ${response.statusCode}');
      }
    } catch (e) {
      print('Exception lors du téléchargement de l\'image: $e');
      throw Exception('Échec du téléchargement de l\'image: $e');
    }
  }
}