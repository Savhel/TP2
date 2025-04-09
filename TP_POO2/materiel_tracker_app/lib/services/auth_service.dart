import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/models.dart';
import 'user_service.dart';

class AuthService {
  static const String _userKey = 'user_data';
  
  // Méthode pour connecter un utilisateur
  static Future<bool> login(String email, String password) async {
    try {
      final user = await UserService.login(email, password);
      if (user != null) {
        print('User before save: ${user.toJson()}'); // Debug print
        await _saveUserToLocal(user);
        
        final currentUser = await getCurrentUser();
        print('Raw current user: $currentUser'); // Debug print
        print('Current user JSON: ${currentUser?.toJson()["id_user"]}'); // Debug print
        print('Current user: ${currentUser?.id ?? "null mince"}');
        print('Connexion réussie pour: ${user.email}');
        return true;
      }
      print('Échec de connexion: identifiants incorrects');
      return false;
    } catch (e) {
      print('Erreur de connexion: $e');
      return false;
    }
  }
  
  // Méthode pour inscrire un nouvel utilisateur
  static Future<bool> register(User user, String password) async {
    try {
      // Créer l'utilisateur et récupérer l'objet User avec l'ID généré
      final createdUser = await UserService.createUser(user, password);
      
      // Sauvegarder l'utilisateur créé localement
      await _saveUserToLocal(createdUser);
      return true;
    } catch (e) {
      print('Erreur d\'inscription: $e');
      return false;
    }
  }
  
  // Méthode pour déconnecter l'utilisateur
  static Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_userKey);
  }
  
  // Méthode pour vérifier si un utilisateur est connecté
  static Future<bool> isLoggedIn() async {
    final user = await getCurrentUser();
    return user != null;
  }
  
  // Méthode pour récupérer l'utilisateur actuellement connecté
  static Future<User?> getCurrentUser() async {
  try {
    final prefs = await SharedPreferences.getInstance();
    final userJson = prefs.getString(_userKey);
    
    if (userJson == null) return null;

    final userMap = jsonDecode(userJson) as Map<String, dynamic>;
    return User.fromJson(userMap);
    
  } catch (e) {
    print('Erreur getCurrentUser: $e');
    return null;
  }
}
  
  // Méthode privée pour sauvegarder l'utilisateur localement
  static Future<void> _saveUserToLocal(User user) async {
    try {
      final prefs = await SharedPreferences.getInstance();
      final userJson = jsonEncode(user.toJson());
      print('Saving user JSON: $userJson'); // Debug print
      await prefs.setString(_userKey, userJson);
    } catch (e) {
      print('Erreur _saveUserToLocal: $e');
    }
  }
}