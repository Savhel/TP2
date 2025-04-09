import '../models/models.dart';
import 'api_service.dart';

class SearchService {
  // Méthode pour rechercher un téléphone par IMEI
  static Future<Phone?> searchPhoneByIMEI(String imei) async {
    try {
      print('Recherche du téléphone avec IMEI: $imei');
      final data = await ApiService.get('/phones/search/imei=$imei');
      
      // Vérifier si la réponse est une liste ou un objet unique
      if (data is List && data.isNotEmpty) {
        return Phone.fromJson(data[0]);
      } else if (data is Map<String, dynamic>) {
        return Phone.fromJson(data);
      }
      return null;
    } catch (e) {
      print('Erreur lors de la recherche du téléphone: $e');
      return null;
    }
  }

  // Méthode pour rechercher un équipement par adresse MAC
  static Future<Equipment?> searchEquipmentByMAC(String macAddress) async {
    try {
      final data = await ApiService.get('/equipments/search/mac=$macAddress');
      
      // Vérifier si la réponse est une liste ou un objet unique
      if (data is List && data.isNotEmpty) {
        return Equipment.fromJson(data[0]);
      } else if (data is Map<String, dynamic>) {
        return Equipment.fromJson(data);
      }
      return null;
    } catch (e) {
      print('Erreur lors de la recherche de l\'équipement: $e');
      return null;
    }
  }

  // Méthode générique pour rechercher un matériel (téléphone ou équipement)
  static Future<dynamic> searchMateriel(String query, String type) async {
    if (type == 'imei') {
      return await searchPhoneByIMEI(query);
    } else if (type == 'mac') {
      return await searchEquipmentByMAC(query);
    }
    return null;
  }
}