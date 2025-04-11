import 'package:flutter/material.dart';
import 'package:materiel_tracker_app/utils/animations.dart';
import '../models/models.dart';
import '../services/auth_service.dart';
import '../services/materiel_service.dart';
import 'materiel_detail_screen.dart';
import 'add_materiel_screen.dart';
import 'search_screen.dart';


class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with SingleTickerProviderStateMixin {
  late TabController _tabController;
  bool _isLoading = true;
  String? _errorMessage;
  List<Phone> _phones = [];
  List<Equipment> _equipments = [];
  User? _currentUser;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 2, vsync: this);
    _loadData();
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  Future<void> _loadData() async {
    setState(() {
      _isLoading = true;
      _errorMessage = null;
    });

    try {
      // Récupérer l'utilisateur connecté
      final user = await AuthService.getCurrentUser();
      
      // Récupérer les téléphones et équipements
      final phones = await MaterielService.getAllPhones();
      final equipments = await MaterielService.getAllEquipments();

      setState(() {
        _currentUser = user;
        _phones = phones;
        _equipments = equipments;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _errorMessage = 'Erreur lors du chargement des données: $e';
        _isLoading = false;
      });
    }
  }

  Future<void> _logout() async {
    await AuthService.logout();
    if (!mounted) return;
    Navigator.of(context).pushReplacementNamed('/auth');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Matériel Tracker'),
        actions: [
          IconButton(
            icon: const Icon(Icons.search),
            onPressed: () {
              Navigator.of(context).push(
                MaterialPageRoute(
                  builder: (context) => const SearchScreen(),
                ),
              );
            },
          ),
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: _logout,
          ),
        ],
        bottom: TabBar(
          controller: _tabController,
          tabs: const [
            Tab(text: 'Téléphones'),
            Tab(text: 'Équipements'),
          ],
        ),
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _errorMessage != null
              ? Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        _errorMessage!,
                        style: const TextStyle(color: Colors.red),
                        textAlign: TextAlign.center,
                      ),
                      const SizedBox(height: 16),
                      ElevatedButton(
                        onPressed: _loadData,
                        child: const Text('Réessayer'),
                      ),
                    ],
                  ),
                )
              : RefreshIndicator(
                  onRefresh: _loadData,
                  child: TabBarView(
                    controller: _tabController,
                    children: [
                      // Tab des téléphones
                      _buildMaterielList(_phones, 'phone'),
                      // Tab des équipements
                      _buildMaterielList(_equipments, 'equipment'),
                    ],
                  ),
                ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.of(context).push(
            MaterialPageRoute(
              builder: (context) => AddMaterielScreen(userId: _currentUser?.id),
            ),
          ).then((_) => _loadData()); // Recharger les données après l'ajout
        },
        child: const Icon(Icons.add),
      ),
    );
  }

  Widget _buildMaterielList(List<dynamic> materiels, String type) {
    if (materiels.isEmpty) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.devices_other, size: 64, color: Colors.grey),
            const SizedBox(height: 16),
            Text(
              type == 'phone'
                  ? 'Aucun téléphone trouvé'
                  : 'Aucun équipement trouvé',
              style: const TextStyle(fontSize: 18, color: Colors.grey),
            ),
          ],
        ),
      );
    }

    return ListView.builder(
      padding: const EdgeInsets.all(8),
      itemCount: materiels.length,
      itemBuilder: (context, index) {
        final materiel = materiels[index];
        final statusColor = _getStatusColor(materiel.etatMateriel);
        
        return AnimatedListItem(
          index: index,
          child:

        Card(
          margin: const EdgeInsets.symmetric(vertical: 8),
          elevation: 2,
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
          child: InkWell(
            borderRadius: BorderRadius.circular(12),
            onTap: () {
              Navigator.of(context).push(
                MaterialPageRoute(
                  builder: (context) => MaterielDetailScreen(
                    id: type == 'equipement'? materiel.addressMAC : materiel.imei,
                    isHisPhone: materiel.idProprietaire == _currentUser?.id,
                    idProprietaire: materiel.idProprietaire,
                    userId: _currentUser?.id,
                    materielType: type,
                    materiel: materiel,
                  ),
                ),
              ).then((_) => _loadData()); // Recharger les données après modification
            },
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                if (materiel.photoUrl != null && materiel.photoUrl!.isNotEmpty)
                  ClipRRect(
                    borderRadius: const BorderRadius.vertical(top: Radius.circular(12)),
                    child: Image.network(
                      materiel.photoUrl!,
                      height: 150,
                      width: double.infinity,
                      fit: BoxFit.cover,
                      errorBuilder: (context, error, stackTrace) {
                        return Container(
                          height: 150,
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
                                fontSize: 18,
                                fontWeight: FontWeight.bold,
                              ),
                              maxLines: 1,
                              overflow: TextOverflow.ellipsis,
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
                                fontSize: 12,
                              ),
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 8),
                      Text(
                        '${materiel.marque ?? ''} ${materiel.modele ?? ''}',
                        style: TextStyle(color: Colors.grey[700]),
                      ),
                      const SizedBox(height: 8),
                      Text(
                        type == 'phone'
                            ? 'IMEI: ${(materiel as Phone).imei ?? 'Non spécifié'}'
                            : 'MAC: ${(materiel as Equipment).addressMAC ?? 'Non spécifié'}',
                        style: const TextStyle(fontSize: 12),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
        );
      },
    );
  }

  Color _getStatusColor(String? status) {
    switch (status) {
      case 'vole':
        return Colors.red[300]!;
      case 'retrouve':
        return const Color.fromARGB(255, 154, 122, 7);
      case 'normal':
        return Colors.green[300]!;
      default:

        return Colors.blue[300]!;
    }
  }
}