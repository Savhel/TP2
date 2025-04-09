class User {
  final int? id;
  final String nom;
  final String prenom;
  final String email;
  final String numtel;
  final String address;

  User({
    this.id,
    required this.nom,
    required this.prenom,
    required this.email,
    required this.numtel,
    required this.address,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id_user'],
      nom: json['nom'],
      prenom: json['prenom'],
      email: json['email'],
      numtel: json['numtel'],
      address: json['address'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id_user': id,
      'nom': nom,
      'prenom': prenom,
      'email': email,
      'numtel': numtel,
      'address': address,
    };
  }
}