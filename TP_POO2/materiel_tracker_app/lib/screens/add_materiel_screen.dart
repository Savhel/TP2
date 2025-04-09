import 'package:flutter/material.dart';
import 'dart:io';
import 'package:image_picker/image_picker.dart';
import '../models/models.dart';
import '../services/materiel_service.dart';

class AddMaterielScreen extends StatefulWidget {
  final int? userId;

  const AddMaterielScreen({Key? key, this.userId}) : super(key: key);

  @override
  _AddMaterielScreenState createState() => _AddMaterielScreenState();
}

class _AddMaterielScreenState extends State<AddMaterielScreen> {
  final _formKey = GlobalKey<FormState>();
  bool _isLoading = false;
  String? _errorMessage;
  String _materielType = 'phone'; // 'phone' ou 'equipment'
  File? _imageFile;
  String? _imageUrl;

  // Contrôleurs pour les champs communs
  final _nomController = TextEditingController();
  final _marqueController = TextEditingController();
  final _modeleController = TextEditingController();
  final _memoireROMController = TextEditingController();
  final _memoireRAMController = TextEditingController();
  final _numeroSerieController = TextEditingController();
  final _couleurController = TextEditingController();

  // Contrôleurs pour les champs spécifiques
  final _imeiController = TextEditingController();
  final _macAddressController = TextEditingController();

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

  Future<void> _saveMateriel() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() {
      _isLoading = true;
      _errorMessage = null;
    });

    try {
      // TODO: Dans une implémentation réelle, il faudrait d'abord uploader l'image
      // et récupérer l'URL avant de créer le matériel
      // Pour l'instant, on simule une URL d'image
      _imageUrl = _imageFile != null ? 'https://example.com/images/placeholder.jpg' : null;

      if (_materielType == 'phone') {
        final phone = Phone(
          nom: _nomController.text,
          marque: _marqueController.text,
          modele: _modeleController.text,
          memoireROM: int.tryParse(_memoireROMController.text) ?? 0,
          memoireRAM: int.tryParse(_memoireRAMController.text) ?? 0,
          numeroSerie: _numeroSerieController.text,
          idProprietaire: widget.userId,
          couleur: _couleurController.text,
          etatMateriel: 'normal', // État par défaut
          imei: _imeiController.text,
          photoUrl: _imageUrl,
        );

        await MaterielService.createPhone(phone);
      } else {
        final equipment = Equipment(
          nom: _nomController.text,
          marque: _marqueController.text,
          modele: _modeleController.text,
          memoireROM: int.tryParse(_memoireROMController.text) ?? 0,
          memoireRAM: int.tryParse(_memoireRAMController.text) ?? 0,
          numeroSerie: _numeroSerieController.text,
          idProprietaire: widget.userId,
          couleur: _couleurController.text,
          etatMateriel: 'normal', // État par défaut
          addressMAC: _macAddressController.text,
          photoUrl: _imageUrl,
        );

        await MaterielService.createEquipment(equipment);
      }

      if (!mounted) return;

      // Afficher un message de succès et retourner à l'écran précédent
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Matériel ajouté avec succès')),
      );
      Navigator.of(context).pop();
    } catch (e) {
      setState(() {
        _errorMessage = 'Erreur lors de l\'ajout du matériel: $e';
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Ajouter un matériel'),
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
                    // Sélection du type de matériel
                    Card(
                      elevation: 2,
                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                      child: Padding(
                        padding: const EdgeInsets.all(16),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              'Type de matériel',
                              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                            ),
                            const SizedBox(height: 8),
                            Row(
                              children: [
                                Expanded(
                                  child: RadioListTile<String>(
                                    title: const Text('Téléphone'),
                                    value: 'phone',
                                    groupValue: _materielType,
                                    onChanged: (value) {
                                      setState(() {
                                        _materielType = value!;
                                      });
                                    },
                                  ),
                                ),
                                Expanded(
                                  child: RadioListTile<String>(
                                    title: const Text('Équipement'),
                                    value: 'equipment',
                                    groupValue: _materielType,
                                    onChanged: (value) {
                                      setState(() {
                                        _materielType = value!;
                                      });
                                    },
                                  ),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(height: 16),
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
                                    border: Border.all(color: Colors.grey),
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
                                      : Column(
                                          mainAxisAlignment: MainAxisAlignment.center,
                                          children: const [
                                            Icon(Icons.add_a_photo, size: 50, color: Colors.grey),
                                            SizedBox(height: 8),
                                            Text('Ajouter une photo'),
                                          ],
                                        ),
                                ),
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
                              validator: (value) {
                                if (value == null || value.isEmpty) {
                                  return 'Veuillez entrer une marque';
                                }
                                return null;
                              },
                            ),
                            const SizedBox(height: 16),
                            TextFormField(
                              controller: _modeleController,
                              decoration: const InputDecoration(
                                labelText: 'Modèle',
                                border: OutlineInputBorder(),
                              ),
                              validator: (value) {
                                if (value == null || value.isEmpty) {
                                  return 'Veuillez entrer un modèle';
                                }
                                return null;
                              },
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
                              validator: (value) {
                                if (value == null || value.isEmpty) {
                                  return 'Veuillez entrer un numéro de série';
                                }
                                return null;
                              },
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
                            Row(
                              children: [
                                Expanded(
                                  child: TextFormField(
                                    controller: _memoireROMController,
                                    decoration: const InputDecoration(
                                      labelText: 'Mémoire ROM (Go)',
                                      border: OutlineInputBorder(),
                                    ),
                                    keyboardType: TextInputType.number,
                                  ),
                                ),
                                const SizedBox(width: 16),
                                Expanded(
                                  child: TextFormField(
                                    controller: _memoireRAMController,
                                    decoration: const InputDecoration(
                                      labelText: 'Mémoire RAM (Go)',
                                      border: OutlineInputBorder(),
                                    ),
                                    keyboardType: TextInputType.number,
                                  ),
                                ),
                              ],
                            ),
                            const SizedBox(height: 16),
                            // Champ spécifique selon le type de matériel
                            if (_materielType == 'phone')
                              TextFormField(
                                controller: _imeiController,
                                decoration: const InputDecoration(
                                  labelText: 'IMEI',
                                  border: OutlineInputBorder(),
                                ),
                                validator: (value) {
                                  if (value == null || value.isEmpty) {
                                    return 'Veuillez entrer un code IMEI';
                                  }
                                  return null;
                                },
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
                    // Bouton d'enregistrement
                    SizedBox(
                      width: double.infinity,
                      child: ElevatedButton(
                        onPressed: _saveMateriel,
                        style: ElevatedButton.styleFrom(
                          padding: const EdgeInsets.symmetric(vertical: 16),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                        child: const Text(
                          'Enregistrer',
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