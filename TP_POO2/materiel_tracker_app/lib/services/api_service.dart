import 'dart:convert';
import 'package:http/http.dart' as http;

class ApiService {
  // URL de base de l'API Java
  static const String baseUrl = 'http://192.168.1.101:9000/api';
  // Note: 10.0.2.2 est l'adresse IP spéciale qui permet à l'émulateur Android 
  // d'accéder à l'hôte local (localhost) de la machine hôte

  // Headers communs pour les requêtes
  static Map<String, String> headers = {
    'Content-Type': 'application/x-www-form-urlencoded',
  };

  // Méthode GET générique
  static Future<dynamic> get(String endpoint) async {
    try {
      print('Envoi requête GET à: $baseUrl$endpoint');
      
      final response = await http.get(
        Uri.parse('$baseUrl$endpoint'),
        headers: headers,
      );

      print('Statut de réponse: ${response.statusCode}');
      print('Corps de réponse: ${response.body}');

      if (response.statusCode >= 200 && response.statusCode < 300) {
        if (response.body.isEmpty) {
          return {}; // Retourner un objet vide si le corps est vide
        }
        return jsonDecode(response.body);
      } else {
        throw Exception('Échec de la requête GET: ${response.statusCode}, Corps: ${response.body}');
      }
    } catch (e) {
      print('Exception lors de la requête GET: $e');
      throw Exception('Échec de la requête GET: $e');
    }
  }

  // Méthode POST générique
  static Future<dynamic> post(String endpoint, Map<String, String> body) async {
    try {
      print('Envoi requête POST à: $baseUrl$endpoint');
      print('Corps de la requête: $body');
      
      final response = await http.post(
        Uri.parse('$baseUrl$endpoint'),
        headers: headers,
        body: body,
      );

      print('Statut de réponse: ${response.statusCode}');
      print('Corps de réponse: ${response.body}');
      print(response.statusCode == 201);

      if (response.statusCode >= 200 && response.statusCode < 300) {
        if (response.body.isEmpty) {
          return {}; // Retourner un objet vide si le corps est vide
        }
        return jsonDecode(response.body);
      } else {
        throw Exception('Échec de la requête POST: ${response.statusCode}, Corps: ${response.body}');
      }
    } catch (e) {
      print('Exception lors de la requête POST: $e');
      // throw Exception('Échec de la requête POST: $e');
    }
  }

  // Méthode PUT générique
  static Future<dynamic> put(String endpoint, Map<String, String> body) async {
    try {
      print('Envoi requête PUT à: $baseUrl$endpoint');
      print('Corps de la requête: $body');
      
      final response = await http.put(
        Uri.parse('$baseUrl$endpoint'),
        headers: headers,
        body: body,
      );

      print('Statut de réponse: ${response.statusCode}');
      print('Corps de réponse: ${response.body}');

      if (response.statusCode >= 200 && response.statusCode < 300) {
        if (response.body.isEmpty) {
          return {}; // Retourner un objet vide si le corps est vide
        }
        return jsonDecode(response.body);
      } else {
        throw Exception('Échec de la requête PUT: ${response.statusCode}, Corps: ${response.body}');
      }
    } catch (e) {
      print('Exception lors de la requête PUT: $e');
      throw Exception('Échec de la requête PUT: $e');
    }
  }

  // Méthode DELETE générique
  static Future<dynamic> delete(String endpoint) async {
    try {
      print('Envoi requête DELETE à: $baseUrl$endpoint');
      
      final response = await http.delete(
        Uri.parse('$baseUrl$endpoint'),
        headers: headers,
      );

      print('Statut de réponse: ${response.statusCode}');
      print('Corps de réponse: ${response.body}');

      if (response.statusCode >= 200 && response.statusCode < 300) {
        if (response.body.isEmpty) {
          return {}; // Retourner un objet vide si le corps est vide
        }
        return jsonDecode(response.body);
      } else {
        throw Exception('Échec de la requête DELETE: ${response.statusCode}, Corps: ${response.body}');
      }
    } catch (e) {
      print('Exception lors de la requête DELETE: $e');
      throw Exception('Échec de la requête DELETE: $e');
    }
  }
}