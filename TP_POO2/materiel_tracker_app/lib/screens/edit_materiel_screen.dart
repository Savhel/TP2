import 'package:flutter/material.dart';
import 'dart:io';
import 'package:image_picker/image_picker.dart';
import '../models/models.dart';
import '../services/materiel_service.dart';

class EditMaterielScreen extends StatefulWidget {
  final String materielType; // 'phone' ou 'equipment'
  final dynamic materiel; // Phone ou Equipment

  const EditMaterielScreen({
    Key? key,
    required this.materielType,
    required this.materiel,
  }) : super(key: key);

  @override
  _EditMaterielScreenState createState() => _EditMaterielScreenState();
}

class _EditMaterielScreenState extends State<EditMaterielScreen> {
  final _formKey = GlobalKey<FormState>();
  bool _isLoading = false;
  String? _errorMessage;
  File? _imageFile;
  String? _imageUrl;

  // Contrôleurs pour les champs communs
  late TextEditingController _nomController;
  late TextEditingController _marqueController;
  late TextEditingController _modeleController;
  late TextEditingController _memoireROMController;
  late TextEditingController _memoireRAMController;
  late TextEditingController _numeroSerieController;
  late TextEditingController _couleurController;

  // Contrôleurs pour les champs spécifiques
  late TextEditingController _imeiController;
  late TextEditingController _macAddressController;

  @override
  void initState() {
    super.initState();
    _initializeControllers();
  }

  void _initializeControllers() {
    final materiel = widget.materiel;
    
    // Initialiser les contrôleurs avec les valeurs existantes
    _nomController = TextEditingController(text: materiel.nom ?? '');
    _marqueController = TextEditingController(text: materiel.marque ?? '');
    _modeleController = TextEditingController(text: materiel.modele ?? '');
    _memoireROMController = TextEditingController(text: materiel.memoireROM?.toString() ?? '0');
    _memoireRAMController = TextEditingController(text: materiel.memoireRAM?.toString() ?? '0');
    _numeroSerieController = TextEditingController(text: materiel.numeroSerie ?? '');
    _couleurController = TextEditingController(text: materiel.couleur ?? '');
    
    // Initialiser l'URL de l'image
    _imageUrl = materiel.photoUrl;
    
    // Initialiser les contrôleurs spécifiques selon le type de matériel
    if (widget.materielType == 'phone') {
      final phone = materiel as Phone;
      _imeiController = TextEditingController(text: phone.imei ?? '');
      _macAddressController = TextEditingController(); // Vide pour les téléphones
    } else {
      final equipment = materiel as Equipment;
      _macAddressController = TextEditingController(text: equipment.addressMAC ?? '');
      _imeiController = TextEditingController(); // Vide pour les équipements
    }
  }

  @override
  void dispose() {
    _nomController.dispose();
    _marqueController.dispose();
    _modeleController.dispose();
    _memoireROMController.dispose();
    _memoireRAMController.dispose();
    _numeroSerieController.dispose();
    _couleurController.dispose();
    _imeiController.dispose();
    _macAddressController.dispose();
    super.dispose();
  }

  Future<void> _pickImage() async {
    final picker = ImagePicker();
    final pickedFile = await picker.pickImage(source: ImageSource.gallery);

    if (pickedFile != null) {
      setState(() {
        _imageFile = File(pickedFile.path);
      });
    }
  }

  Future<void> _updateMateriel() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() {
      _isLoading = true;
      _errorMessage = null;
    });

    try {
      final updatedImageUrl = _imageFile != null 
          ? 'https://example.com/images/updated_placeholder.jpg' 
          : _imageUrl;

      if (widget.materielType == 'phone') {
        final phone = Phone(
          nom: _nomController.text,
          marque: _marqueController.text,
          modele: _modeleController.text,
          memoireROM: int.tryParse(_memoireROMController.text) ?? 0,
          memoireRAM: int.tryParse(_memoireRAMController.text) ?? 0,
          numeroSerie: _numeroSerieController.text,
          idProprietaire: widget.materiel.idProprietaire,
          couleur: _couleurController.text,
          etatMateriel: widget.materiel.etatMateriel, // Conserver l'état actuel
          imei: _imeiController.text,
          photoUrl: updatedImageUrl,
        );

        await MaterielService.updatePhone(phone);
      } else {
        final equipment = Equipment(
          nom: _nomController.text,
          marque: _marqueController.text,
          modele: _modeleController.text,
          memoireROM: int.tryParse(_memoireROMController.text) ?? 0,
          memoireRAM: int.tryParse(_memoireRAMController.text) ?? 0,
          numeroSerie: _numeroSerieController.text,
          idProprietaire: widget.materiel.idProprietaire,
          couleur: _couleurController.text,
          etatMateriel: widget.materiel.etatMateriel, // Conserver l'état actuel
          addressMAC: _macAddressController.text,
          photoUrl: updatedImageUrl,
        );

        await MaterielService.updateEquipment(equipment);
      }

      if (!mounted) return;

      // Afficher un message de succès et retourner à l'écran précédent
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Matériel mis à jour avec succès')),
      );
      Navigator.of(context).pop();
    } catch (e) {
      setState(() {
        _errorMessage = 'Erreur lors de la mise à jour du matériel: $e';
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    final isPhone = widget.materielType == 'phone';
    
    return Scaffold(
      appBar: AppBar(
        title: Text('Modifier ${isPhone ? "le téléphone" : "l\'équipement"}'),
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
              padding: const EdgeInsets.all(16),
              child: Form(
                key: _formKey,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Photo du matériel
                    Card(
                      elevation: 2,
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                      child: Padding(
                        padding: const EdgeInsets.all(16),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              'Photo du matériel',
                              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                            ),
                            const SizedBox(height: 16),
                            Center(
                              child: GestureDetector(
                                onTap: _pickImage,
                                child: Container(
                                  width: 200,
                                  height: 200,
                                  decoration: BoxDecoration(
                                    color: Colors.grey[200],
                                    borderRadius: BorderRadius.circular(12),
                                  ),
                                  child: _imageFile != null
                                      ? ClipRRect(
                                          borderRadius: BorderRadius.circular(12),
                                          child: Image.file(
                                            _imageFile!,
                                            width: 200,
                                            height: 200,
                                            fit: BoxFit.cover,
                                          ),
                                        )
                                      : _imageUrl != null && _imageUrl!.isNotEmpty
                                          ? ClipRRect(
                                              borderRadius: BorderRadius.circular(12),
                                              child: Image.network(
                                                _imageUrl!,
                                                width: 200,
                                                height: 200,
                                                fit: BoxFit.cover,
                                                errorBuilder: (context, error, stackTrace) {
                                                  return const Icon(
                                                    Icons.image_not_supported,
                                                    size: 50,
                                                  );
                                                },
                                              ),
                                            )
                                          : const Icon(
                                              Icons.add_a_photo,
                                              size: 50,
                                            ),
                                ),
                              ),
                            ),
                            const SizedBox(height: 8),
                            Center(
                              child: TextButton.icon(
                                icon: const Icon(Icons.photo_library),
                                label: const Text('Choisir une image'),
                                onPressed: _pickImage,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(height: 16),
                    // Informations générales
                    Card(
                      elevation: 2,
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                      child: Padding(
                        padding: const EdgeInsets.all(16),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              'Informations générales',
                              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                            ),
                            const SizedBox(height: 16),
                            TextFormField(
                              controller: _nomController,
                              decoration: const InputDecoration(
                                labelText: 'Nom',
                                border: OutlineInputBorder(),
                              ),
                              validator: (value) {
                                if (value == null || value.isEmpty) {
                                  return 'Veuillez entrer un nom';
                                }
                                return null;
                              },
                            ),
                            const SizedBox(height: 16),
                            TextFormField(
                              controller: _marqueController,
                              decoration: const InputDecoration(
                                labelText: 'Marque',
                                border: OutlineInputBorder(),
                              ),
                            ),
                            const SizedBox(height: 16),
                            TextFormField(
                              controller: _modeleController,
                              decoration: const InputDecoration(
                                labelText: 'Modèle',
                                border: OutlineInputBorder(),
                              ),
                            ),
                            const SizedBox(height: 16),
                            TextFormField(
                              controller: _couleurController,
                              decoration: const InputDecoration(
                                labelText: 'Couleur',
                                border: OutlineInputBorder(),
                              ),
                            ),
                            const SizedBox(height: 16),
                            TextFormField(
                              controller: _numeroSerieController,
                              decoration: const InputDecoration(
                                labelText: 'Numéro de série',
                                border: OutlineInputBorder(),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(height: 16),
                    // Spécifications techniques
                    Card(
                      elevation: 2,
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                      child: Padding(
                        padding: const EdgeInsets.all(16),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              'Spécifications techniques',
                              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                            ),
                            const SizedBox(height: 16),
                            TextFormField(
                              controller: _memoireROMController,
                              decoration: const InputDecoration(
                                labelText: 'Mémoire ROM (Go)',
                                border: OutlineInputBorder(),
                              ),
                              keyboardType: TextInputType.number,
                            ),
                            const SizedBox(height: 16),
                            TextFormField(
                              controller: _memoireRAMController,
                              decoration: const InputDecoration(
                                labelText: 'Mémoire RAM (Go)',
                                border: OutlineInputBorder(),
                              ),
                              keyboardType: TextInputType.number,
                            ),
                            const SizedBox(height: 16),
                            if (isPhone)
                              TextFormField(
                                controller: _imeiController,
                                decoration: const InputDecoration(
                                  labelText: 'IMEI',
                                  border: OutlineInputBorder(),
                                ),
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'Veuillez entrer un IMEI';
                                  }
                                  return null;
                                },
                                enabled: false, // L'IMEI ne peut pas être modifié
                              )
                            else
                              TextFormField(
                                controller: _macAddressController,
                                decoration: const InputDecoration(
                                  labelText: 'Adresse MAC',
                                  border: OutlineInputBorder(),
                                ),
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'Veuillez entrer une adresse MAC';
                                  }
                                  return null;
                                },
                                enabled: false, // L'adresse MAC ne peut pas être modifiée
                              ),
                          ],
                        ),
                      ),
                    ),
                    if (_errorMessage != null) ...[  
                      const SizedBox(height: 16),
                      Container(
                        padding: const EdgeInsets.all(12),
                        decoration: BoxDecoration(
                          color: Colors.red[100],
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Text(
                          _errorMessage!,
                          style: const TextStyle(color: Colors.red),
                        ),
                      ),
                    ],
                    const SizedBox(height: 24),
                    SizedBox(
                      width: double.infinity,
                      child: ElevatedButton(
                        onPressed: _updateMateriel,
                        style: ElevatedButton.styleFrom(
                          padding: const EdgeInsets.symmetric(vertical: 16),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                        child: const Text(
                          'Mettre à jour',
                          style: TextStyle(fontSize: 16),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
    );
  }
}