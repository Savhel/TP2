import 'package:flutter/material.dart';
import '../models/models.dart';
import '../services/search_service.dart';
import 'materiel_detail_screen.dart';

class SearchScreen extends StatefulWidget {
  const SearchScreen({Key? key}) : super(key: key);

  @override
  _SearchScreenState createState() => _SearchScreenState();
}

class _SearchScreenState extends State<SearchScreen> {
  final _searchController = TextEditingController();
  bool _isLoading = false;
  String? _errorMessage;
  dynamic _searchResult;
  String _searchType = 'imei'; // 'imei' ou 'mac'

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  Future<void> _search() async {
    final query = _searchController.text.trim();
    if (query.isEmpty) {
      setState(() {
        _errorMessage = 'Veuillez entrer un code IMEI ou une adresse MAC';
      });
      return;
    }

    setState(() {
      _isLoading = true;
      _errorMessage = null;
      _searchResult = null;
    });

    try {
      // Utiliser le nouveau service de recherche
      final result = await SearchService.searchMateriel(query, _searchType);
      
      setState(() {
        _searchResult = result;
        _isLoading = false;
      });

      if (_searchResult == null) {
        setState(() {
          _errorMessage = 'Aucun résultat trouvé pour cette recherche';
        });
      }
    } catch (e) {
      setState(() {
        _errorMessage = 'Erreur lors de la recherche: $e';
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Recherche'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Sélection du type de recherche
            Row(
              children: [
                Expanded(
                  child: RadioListTile<String>(
                    title: const Text('IMEI'),
                    value: 'imei',
                    groupValue: _searchType,
                    onChanged: (value) {
                      setState(() {
                        _searchType = value!;
                        _searchResult = null;
                        _errorMessage = null;
                      });
                    },
                  ),
                ),
                Expanded(
                  child: RadioListTile<String>(
                    title: const Text('MAC'),
                    value: 'mac',
                    groupValue: _searchType,
                    onChanged: (value) {
                      setState(() {
                        _searchType = value!;
                        _searchResult = null;
                        _errorMessage = null;
                      });
                    },
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            // Champ de recherche
            TextField(
              controller: _searchController,
              decoration: InputDecoration(
                labelText: _searchType == 'imei' ? 'Code IMEI' : 'Adresse MAC',
                hintText: _searchType == 'imei' ? 'Entrez le code IMEI' : 'Entrez l\'adresse MAC',
                prefixIcon: const Icon(Icons.search),
                border: const OutlineInputBorder(),
                suffixIcon: IconButton(
                  icon: const Icon(Icons.clear),
                  onPressed: () {
                    _searchController.clear();
                    setState(() {
                      _searchResult = null;
                      _errorMessage = null;
                    });
                  },
                ),
              ),
              onSubmitted: (_) => _search(),
            ),
            const SizedBox(height: 16),
            // Bouton de recherche
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: _isLoading ? null : _search,
                style: ElevatedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(vertical: 12),
                ),
                child: _isLoading
                    ? const SizedBox(
                        height: 20,
                        width: 20,
                        child: CircularProgressIndicator(strokeWidth: 2),
                      )
                    : const Text('Rechercher'),
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
            // Résultat de la recherche
            if (_searchResult != null) ...[  
              const Text(
                'Résultat de la recherche:',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 16),
              _buildSearchResultCard(),
            ],
          ],
        ),
      ),
    );
  }

  Widget _buildSearchResultCard() {
    final materiel = _searchResult;
    final isPhone = _searchType == 'imei';
    final statusColor = _getStatusColor(materiel.etatMateriel);

    return Card(
      elevation: 3,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: InkWell(
        borderRadius: BorderRadius.circular(12),
        onTap: () {
          Navigator.of(context).push(
            MaterialPageRoute(
              builder: (context) => MaterielDetailScreen(
                materielType: isPhone ? 'phone' : 'equipment',
                materiel: materiel,
              ),
            ),
          ).then((_) => _search()); // Recharger le résultat après modification
        },
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (materiel.photoUrl != null && materiel.photoUrl!.isNotEmpty)
              ClipRRect(
                borderRadius: const BorderRadius.vertical(top: Radius.circular(12)),
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
            Padding(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Expanded(
                        child: Text(
                          materiel.nom ?? 'Sans nom',
                          style: const TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                      Container(
                        padding: const EdgeInsets.symmetric(
                          horizontal: 8,
                          vertical: 4,
                        ),
                        decoration: BoxDecoration(
                          color: statusColor,
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: Text(
                          materiel.etatMateriel ?? 'normal',
                          style: TextStyle(
                            color: statusColor.computeLuminance() > 0.5
                                ? Colors.black
                                : Colors.white,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 8),
                  Text(
                    '${materiel.marque ?? ''} ${materiel.modele ?? ''}',
                    style: TextStyle(fontSize: 16, color: Colors.grey[700]),
                  ),
                  const SizedBox(height: 16),
                  _buildInfoRow('Numéro de série', materiel.numeroSerie ?? 'Non spécifié'),
                  _buildInfoRow('Mémoire ROM', '${materiel.memoireROM ?? 0} Go'),
                  _buildInfoRow('Mémoire RAM', '${materiel.memoireRAM ?? 0} Go'),
                  _buildInfoRow(
                    isPhone ? 'IMEI' : 'Adresse MAC',
                    isPhone
                        ? (materiel as Phone).imei ?? 'Non spécifié'
                        : (materiel as Equipment).addressMAC ?? 'Non spécifié',
                  ),
                  const SizedBox(height: 16),
                  const Text(
                    'Appuyez pour voir plus de détails ou modifier l\'état',
                    style: TextStyle(fontStyle: FontStyle.italic, fontSize: 12),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 100,
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

  Color _getStatusColor(String? status) {
    switch (status) {
      case 'volé':
        return Colors.red[300]!;
      case 'retrouvé':
        return Colors.green[300]!;
      case 'normal':
      default:
        return Colors.blue[300]!;
    }
  }
}