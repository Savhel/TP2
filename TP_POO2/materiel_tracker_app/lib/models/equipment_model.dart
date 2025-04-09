import 'materiel_model.dart';

class Equipment extends Materiel {
  final String? addressMAC;

  Equipment({
    this.addressMAC,
    super.nom,
    super.marque,
    super.modele,
    super.memoireROM,
    super.memoireRAM,
    super.numeroSerie,
    super.idProprietaire,
    super.couleur,
    super.etatMateriel,
    super.photoUrl,
  });

  factory Equipment.fromJson(Map<String, dynamic> json) {
    return Equipment(
      addressMAC: json['address_MAC'],
      nom: json['Nom'],
      marque: json['Marque'],
      modele: json['Modele'],
      memoireROM: json['memoire_ROM']?.toInt(),
      memoireRAM: json['memoire_RAM']?.toInt(),
      numeroSerie: json['numero_serie'],
      idProprietaire: json['IdProprietaire']?.toString() == "null" ? null : int.parse(json['IdProprietaire'].toString()),
      couleur: json['Couleur'],
      etatMateriel: json['etat_Materiel'],
      photoUrl: json['photoUrl'],
    );
  }

  @override
  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = super.toJson();
    data['address_MAC'] = addressMAC;
    return data;
  }
}