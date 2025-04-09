import 'materiel_model.dart';

class Phone extends Materiel {
  final String? imei;

  Phone({
    this.imei,
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

  factory Phone.fromJson(Map<String, dynamic> json) {
    return Phone(
      imei: json['IMEI'],
      nom: json['Nom'],
      marque: json['Marque'],
      modele: json['Modele'],
      memoireROM: json['memoire_ROM']?.toInt(),
      memoireRAM: json['memoire_RAM']?.toInt(),
      numeroSerie: json['numero_serie'],
      idProprietaire: json['IdProprietaire'],
      couleur: json['Couleur'],
      etatMateriel: json['etat_Materiel'],
      photoUrl: json['photoUrl'],
    );
  }

  @override
  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = super.toJson();
    data['IMEI'] = imei;
    return data;
  }
}