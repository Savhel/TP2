import 'package:flutter/material.dart';
import '../models/models.dart';
import '../services/materiel_service.dart';
import 'edit_materiel_screen.dart';

class MaterielDetailScreen extends StatefulWidget {
  final String materielType; // 'phone' ou 'equipment'
  final dynamic materiel; // Phone ou Equipment

  const MaterielDetailScreen({
    Key? key,
    required this.materielType,
    required this.materiel,
  }) : super(key: key);

  @override
  _MaterielDetailScreenState createState() => _MaterielDetailScreenState();
}

class _MaterielDetailScreenState extends State<MaterielDetailScreen> {
  bool _isLoading = false;
  String? _errorMessage;
  String _currentStatus = 'normal';

  @override
  void initState() {
    super.initState();
    _currentStatus = widget.materiel.etatMateriel ?? 'normal';
  }

  Future<void> _updateStatus(String newStatus) async {
    setState(() {
      _isLoading = true;
      _errorMessage = null;
    });

    try {
      if (widget.materielType == 'phone') {
        final phone = widget.materiel as Phone;
        await MaterielService.updatePhoneStatus(phone.imei ?? '', newStatus);
      } else {
        final equipment = widget.materiel as Equipment;
        await MaterielService.updateEquipmentStatus(equipment.addressMAC ?? '', newStatus);
      }

      setState(() {
        _currentStatus = newStatus;
        _isLoading = false;
      });

      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('État mis à jour avec succès')),
      );
    } catch (e) {
      setState(() {
        _errorMessage = 'Erreur lors de la mise à jour: $e';
        _isLoading = false;
      });
    }
  }

  // Méthode pour supprimer le matériel
  Future<void> _deleteMateriel() async {
    // Afficher une boîte de dialogue de confirmation
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirmation de suppression'),
        content: Text(
          'Êtes-vous sûr de vouloir supprimer ce ${widget.materielType == 'phone' ? "téléphone" : "équipement"} ? Cette action est irréversible.'
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false),
            child: const Text('Annuler'),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true),
            child: const Text('Supprimer', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    ) ?? false;

    if (!confirmed) return;

    setState(() {
      _isLoading = true;
      _errorMessage = null;
    });

    try {
      if (widget.materielType == 'phone') {
        final phone = widget.materiel as Phone;
        await MaterielService.deletePhone(phone.imei ?? '');
      } else {
        final equipment = widget.materiel as Equipment;
        await MaterielService.deleteEquipment(equipment.addressMAC ?? '');
      }

      if (!mounted) return;

      // Afficher un message de succès et retourner à l'écran précédent
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Matériel supprimé avec succès')),
      );
      Navigator.of(context).pop();
    } catch (e) {
      setState(() {
        _errorMessage = 'Erreur lors de la suppression: $e';
        _isLoading = false;
      });
    }
  }

  // Méthode pour modifier le matériel
  void _editMateriel() {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => EditMaterielScreen(
          materielType: widget.materielType,
          materiel: widget.materiel,
        ),
      ),
    ).then((_) {
      // Recharger les données après modification
      setState(() {
        _isLoading = true;
      });
      // Simuler un rechargement des données
      Future.delayed(const Duration(milliseconds: 500), () {
        if (mounted) {
          setState(() {
            _isLoading = false;
          });
        }
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    final materiel = widget.materiel;
    final isPhone = widget.materielType == 'phone';

    return Scaffold(
      appBar: AppBar(
        title: Text(materiel.nom ?? 'Détail du matériel'),
        actions: [
          IconButton(
            icon: const Icon(Icons.edit),
            tooltip: 'Modifier',
            onPressed: _editMateriel,
          ),
          IconButton(
            icon: const Icon(Icons.delete),
            tooltip: 'Supprimer',
            onPressed: _deleteMateriel,
          ),
        ],
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  if (materiel.photoUrl != null && materiel.photoUrl!.isNotEmpty)
                    Center(
                      child: ClipRRect(
                        borderRadius: BorderRadius.circular(12),
                        child: Image.network(
                          materiel.photoUrl!,
                          height: 200,
                          width: double.infinity,
                          fit: BoxFit.cover,
                          errorBuilder: (context, error, stackTrace) {
                            return Container(
                              height: 200,
                              color: Colors.grey[300],
                              child: const Icon(Icons.image_not_supported, size: 50),
                            );
                          },
                        ),
                      ),
                    ),
                  const SizedBox(height: 24),
                  _buildInfoCard(
                    title: 'Informations générales',
                    children: [
                      _buildInfoRow('Nom', materiel.nom ?? 'Non spécifié'),
                      _buildInfoRow('Marque', materiel.marque ?? 'Non spécifié'),
                      _buildInfoRow('Modèle', materiel.modele ?? 'Non spécifié'),
                      _buildInfoRow('Numéro de série', materiel.numeroSerie ?? 'Non spécifié'),
                      _buildInfoRow('Couleur', materiel.couleur ?? 'Non spécifié'),
                    ],
                  ),
                  const SizedBox(height: 16),
                  _buildInfoCard(
                    title: 'Spécifications techniques',
                    children: [
                      _buildInfoRow('Mémoire ROM', '${materiel.memoireROM ?? 0} Go'),
                      _buildInfoRow('Mémoire RAM', '${materiel.memoireRAM ?? 0} Go'),
                      if (isPhone)
                        _buildInfoRow('IMEI', (materiel as Phone).imei ?? 'Non spécifié'),
                      if (!isPhone)
                        _buildInfoRow('Adresse MAC', (materiel as Equipment).addressMAC ?? 'Non spécifié'),
                    ],
                  ),
                  const SizedBox(height: 16),
                  _buildInfoCard(
                    title: 'État actuel',
                    children: [
                      _buildStatusSelector(),
                    ],
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
                ],
              ),
            ),
    );
  }

  Widget _buildInfoCard({required String title, required List<Widget> children}) {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              title,
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const Divider(),
            ...children,
          ],
        ),
      ),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 120,
            child: Text(
              label,
              style: TextStyle(fontWeight: FontWeight.w500, color: Colors.grey[700]),
            ),
          ),
          Expanded(
            child: Text(
              value,
              style: const TextStyle(fontWeight: FontWeight.w500),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildStatusSelector() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text('État actuel:'),
        const SizedBox(height: 8),
        Wrap(
          spacing: 8,
          children: [
            _buildStatusChip('normal', 'Normal'),
            _buildStatusChip('volé', 'Volé'),
            _buildStatusChip('retrouvé', 'Retrouvé'),
          ],
        ),
      ],
    );
  }

  Widget _buildStatusChip(String status, String label) {
    final isSelected = _currentStatus == status;
    return FilterChip(
      selected: isSelected,
      label: Text(label),
      onSelected: (selected) {
        if (selected && !isSelected) {
          _updateStatus(status);
        }
      },
      backgroundColor: Colors.grey[200],
      selectedColor: Theme.of(context).primaryColor.withOpacity(0.2),
      checkmarkColor: Theme.of(context).primaryColor,
    );
  }
}