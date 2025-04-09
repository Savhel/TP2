import '../models/models.dart';
import 'api_service.dart';

class UserService {
  // Méthode pour créer un nouvel utilisateur (inscription)
  static Future<User> createUser(User user, String password) async {
    try {
      final Map<String, String> body = {
        'name': user.nom,
        'prenom': user.prenom,
        'email': user.email,
        'password': password,
        'NumTel': user.numtel,
        'address': user.address,
      };

      final response = await ApiService.post('/persons', body);
      
      // Vérifier si la réponse contient l'ID utilisateur
      if (response is Map<String, dynamic> && response.containsKey('id_user')) {
        // Créer un nouvel objet User avec l'ID généré
        return User.fromJson(response);
      } else if (response is List && response.isNotEmpty && response[0] is Map<String, dynamic>) {
        // Si la réponse est une liste, prendre le premier élément
        return User.fromJson(response[0]);
      } else {
        // Si la structure de la réponse est différente, essayer de l'adapter
        print('Structure de réponse inattendue: $response');
        // Créer un nouvel utilisateur avec les données existantes
        // et essayer d'extraire l'ID si possible
        int? userId;
        if (response is Map<String, dynamic>) {
          // Chercher une clé qui pourrait contenir l'ID
          userId = response['id'] ?? response['id_user'] ?? response['user_id'];
        }
        return User(
          id: userId,
          nom: user.nom,
          prenom: user.prenom,
          email: user.email,
          numtel: user.numtel,
          address: user.address,
        );
      }
    } catch (e) {
      print('Erreur lors de la création de l\'utilisateur: $e');
      throw Exception('Échec de la création de l\'utilisateur: $e');
    }
  }

  // Méthode pour récupérer un utilisateur par son ID
  static Future<User> getUserById(int id) async {
    final data = await ApiService.get('/persons/$id');
    return User.fromJson(data);
  }

  // Méthode pour mettre à jour un utilisateur
  static Future<dynamic> updateUser(User user) async {
    if (user.id == null) {
      throw Exception('ID utilisateur requis pour la mise à jour');
    }

    final Map<String, String> body = {
      'name': user.nom,
      'prenom': user.prenom,
      'email': user.email,
      'NumTel': user.numtel,
      'address': user.address,
    };

    return await ApiService.put('/persons/${user.id}', body);
  }

  // Méthode pour supprimer un utilisateur
  static Future<dynamic> deleteUser(int id) async {
    return await ApiService.delete('/persons/$id');
  }

  // Méthode pour l'authentification
  static Future<User?> login(String email, String password) async {
    try {
      final Map<String, String> body = {
        'email': email,
        'password': password,
      };

      final response = await ApiService.post('/login', body);
      
      // Traiter différents formats de réponse possibles
      if (response is List && response.isNotEmpty) {
        // Si la réponse est une liste, prendre le premier élément
        return User.fromJson(response[0]);
      } else if (response is Map<String, dynamic>) {
        // Si la réponse est un objet JSON
        if (response.containsKey('error') || response.containsKey('message')) {
          // Si la réponse contient une erreur
          print('Erreur d\'authentification: ${response['error'] ?? response['message']}');
          return null;
        }
        // Sinon, c'est probablement un objet utilisateur
        return User.fromJson(response);
      }
      
      print('Format de réponse non reconnu: $response');
      return null;
    } catch (e) {
      print('Erreur de connexion: $e');
      return null;
    }
  }

  static Future<User?> getUser(int id) async {
    try {
      final Map<String, String> body = {
        'id': id.toString(),
      };

      final response = await ApiService.post('/getUser', body);
      
      // Traiter différents formats de réponse possibles
      if (response is List && response.isNotEmpty) {
        // Si la réponse est une liste, prendre le premier élément
        return User.fromJson(response[0]);
      } else if (response is Map<String, dynamic>) {
        // Si la réponse est un objet JSON
        if (response.containsKey('error') || response.containsKey('message')) {
          // Si la réponse contient une erreur
          print('Erreur d\'authentification: ${response['error'] ?? response['message']}');
          return null;
        }
        // Sinon, c'est probablement un objet utilisateur
        return User.fromJson(response);
      }
      
      print('Format de réponse non reconnu: $response');
      return null;
    } catch (e) {
      print('Erreur de connexion: $e');
      return null;
    }
  }
}