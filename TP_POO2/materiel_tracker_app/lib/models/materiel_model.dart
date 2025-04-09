abstract class Materiel {
  final String? nom;
  final String? marque;
  final String? modele;
  final int? memoireROM;
  final int? memoireRAM;
  final String? numeroSerie;
  final int? idProprietaire;
  final String? couleur;
  final String? etatMateriel;
  final String? photoUrl;

  Materiel({
    this.nom,
    this.marque,
    this.modele,
    this.memoireROM,
    this.memoireRAM,
    this.numeroSerie,
    this.idProprietaire,
    this.couleur,
    this.etatMateriel,
    this.photoUrl,
  });

  Map<String, dynamic> toJson() {
    return {
      'Nom': nom,
      'Marque': marque,
      'Modele': modele,
      'memoire_ROM': memoireROM,
      'memoire_RAM': memoireRAM,
      'numero_serie': numeroSerie,
      'IdProprietaire': idProprietaire,
      'Couleur': couleur,
      'etat_Materiel': etatMateriel,
    };
  }
}