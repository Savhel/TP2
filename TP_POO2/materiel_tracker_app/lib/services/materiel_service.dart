import '../models/models.dart';
import 'api_service.dart';
import 'auth_service.dart';

class MaterielService {
  // Méthode pour créer un nouveau téléphone
  static Future<dynamic> createPhone(Phone phone) async {
    // Récupérer l'utilisateur connecté si aucun propriétaire n'est spécifié
    int? proprietaireId = phone.idProprietaire;
    if (proprietaireId == null || proprietaireId == 0) {
      final currentUser = await AuthService.getCurrentUser();
      print("\n le proprietaire est : ${currentUser?.toJson()["id_user"]} \n");
      proprietaireId = currentUser?.toJson()["id_user"];
    }
    
    final Map<String, String> body = {
      'Nom': phone.nom ?? '',
      'Marque': phone.marque ?? '',
      'Modele': phone.modele ?? '',
      'memoire_ROM': phone.memoireROM?.toString() ?? '0',
      'memoire_RAM': phone.memoireRAM?.toString() ?? '0',
      'numero_serie': phone.numeroSerie ?? '',
      'IdProprietaire': proprietaireId.toString() ,
      'Couleur': phone.couleur ?? '',
      'etat_Materiel': phone.etatMateriel ?? 'normal',
      'IMEI': phone.imei ?? '',
      'photoUrl': phone.photoUrl ?? '',
    };

    return await ApiService.post('/phones', body);
  }

  // Méthode pour créer un nouvel équipement
  static Future<dynamic> createEquipment(Equipment equipment) async {
    // Récupérer l'utilisateur connecté si aucun propriétaire n'est spécifié
    int? proprietaireId = equipment.idProprietaire;
    if (proprietaireId == null || proprietaireId == 0) {
      final currentUser = await AuthService.getCurrentUser();
      proprietaireId = currentUser?.toJson()["id_user"];
    }
    
    final Map<String, String> body = {
      'Nom': equipment.nom ?? '',
      'Marque': equipment.marque ?? '',
      'Modele': equipment.modele ?? '',
      'memoire_ROM': equipment.memoireROM?.toString() ?? '0',
      'memoire_RAM': equipment.memoireRAM?.toString() ?? '0',
      'numero_serie': equipment.numeroSerie ?? '',
      'IdProprietaire': proprietaireId.toString(),
      'Couleur': equipment.couleur ?? '',
      'etat_Materiel': equipment.etatMateriel ?? 'normal',
      'address_MAC': equipment.addressMAC ?? '',
      'photoUrl': equipment.photoUrl ?? '',
    };

    return await ApiService.post('/equipments', body);
  }

  // Méthode pour récupérer tous les téléphones
  static Future<List<Phone>> getAllPhones() async {
    int? proprietaireId = 0;
    if (proprietaireId == null || proprietaireId == 0) {
      final currentUser = await AuthService.getCurrentUser();
      proprietaireId = currentUser?.toJson()["id_user"];
    }
    final data = await ApiService.get('/phones/$proprietaireId');
    if (data is List) {
      return data.map((item) => Phone.fromJson(item)).toList();
    }
    return [];
  }

  // Méthode pour récupérer tous les équipements
  static Future<List<Equipment>> getAllEquipments() async {
    int? proprietaireId = 0;
    if (proprietaireId == null || proprietaireId == 0) {
      final currentUser = await AuthService.getCurrentUser();
      proprietaireId = currentUser?.toJson()["id_user"];
    }
    final data = await ApiService.get('/equipments/$proprietaireId');
    if (data is List) {
      return data.map((item) => Equipment.fromJson(item)).toList();
    }
    return [];
  }

  // Méthode pour mettre à jour l'état d'un téléphone
  static Future<dynamic> updatePhoneStatus(String imei, String newStatus) async {
    final Map<String, String> body = {
      'etat_Materiel': newStatus,
    };

    return await ApiService.put('/phones/$imei/status', body);
  }

  // Méthode pour mettre à jour l'état d'un équipement
  static Future<dynamic> updateEquipmentStatus(String macAddress, String newStatus) async {
    final Map<String, String> body = {
      'etat_Materiel': newStatus,
    };

    return await ApiService.put('/equipments/$macAddress/status', body);
  }

  // Méthode pour rechercher un téléphone par IMEI
  static Future<Phone?> searchPhoneByIMEI(String imei) async {
    try {
      final data = await ApiService.get('/phones/search/imei=$imei');
      return Phone.fromJson(data);
    } catch (e) {
      return null;
    }
  }

  // Méthode pour rechercher un équipement par adresse MAC
  static Future<Equipment?> searchEquipmentByMAC(String macAddress) async {
    try {
      final data = await ApiService.get('/equipments/search/mac=$macAddress');
      return Equipment.fromJson(data);
    } catch (e) {
      return null;
    }
  }

  // Méthode pour mettre à jour un téléphone
  static Future<dynamic> updatePhone(Phone phone) async {
    if (phone.imei == null || phone.imei!.isEmpty) {
      throw Exception('IMEI requis pour la mise à jour du téléphone');
    }

    // Récupérer l'utilisateur connecté si aucun propriétaire n'est spécifié
    int? proprietaireId = phone.idProprietaire;
    if (proprietaireId == null || proprietaireId == 0) {
      final currentUser = await AuthService.getCurrentUser();
      proprietaireId = currentUser?.toJson()["id_user"];
    }
    
    final Map<String, String> body = {
      'Nom': phone.nom ?? '',
      'Marque': phone.marque ?? '',
      'Modele': phone.modele ?? '',
      'memoire_ROM': phone.memoireROM?.toString() ?? '0',
      'memoire_RAM': phone.memoireRAM?.toString() ?? '0',
      'numero_serie': phone.numeroSerie ?? '',
      'IdProprietaire': proprietaireId.toString(),
      'Couleur': phone.couleur ?? '',
      'etat_Materiel': phone.etatMateriel ?? 'normal',
      'photoUrl': phone.photoUrl ?? '',
    };

    return await ApiService.put('/phones/${phone.imei}', body);
  }

  // Méthode pour mettre à jour un équipement
  static Future<dynamic> updateEquipment(Equipment equipment) async {
    if (equipment.addressMAC == null || equipment.addressMAC!.isEmpty) {
      throw Exception('Adresse MAC requise pour la mise à jour de l\'équipement');
    }

    // Récupérer l'utilisateur connecté si aucun propriétaire n'est spécifié
    int? proprietaireId = equipment.idProprietaire;
    if (proprietaireId == null || proprietaireId == 0) {
      final currentUser = await AuthService.getCurrentUser();
      proprietaireId = currentUser?.toJson()["id_user"];
    }
    
    final Map<String, String> body = {
      'Nom': equipment.nom ?? '',
      'Marque': equipment.marque ?? '',
      'Modele': equipment.modele ?? '',
      'memoire_ROM': equipment.memoireROM?.toString() ?? '0',
      'memoire_RAM': equipment.memoireRAM?.toString() ?? '0',
      'numero_serie': equipment.numeroSerie ?? '',
      'IdProprietaire': proprietaireId.toString(),
      'Couleur': equipment.couleur ?? '',
      'etat_Materiel': equipment.etatMateriel ?? 'normal',
      'photoUrl': equipment.photoUrl ?? '',
    };

    return await ApiService.put('/equipments/${equipment.addressMAC}', body);
  }

  // Méthode pour supprimer un téléphone
  static Future<dynamic> deletePhone(String imei) async {
    if (imei.isEmpty) {
      throw Exception('IMEI requis pour la suppression du téléphone');
    }
    return await ApiService.delete('/phones/$imei');
  }

  // Méthode pour supprimer un équipement
  static Future<dynamic> deleteEquipment(String macAddress) async {
    if (macAddress.isEmpty) {
      throw Exception('Adresse MAC requise pour la suppression de l\'équipement');
    }
    return await ApiService.delete('/equipments/$macAddress');
  }
}