package Models;

public abstract class MaterielsModel {

    private String Nom;
    private String Marque;
    private String Modele;
    private int memoire_ROM;
    private int memoire_RAM;
    private String numero_serie;
    private Integer IdProprietaire;
    private String Couleur;
    private String etat_Materiel;
    private String photoUrl;

    public MaterielsModel() {

    }


    public String getEtat_Materiel() {
        return etat_Materiel;
    }

    public String getCouleur() {
        return Couleur;
    }

    public void setEtat_Materiel(String etat_Materiel) {
        this.etat_Materiel = etat_Materiel;
    }

    public void setCouleur(String couleur) {
        Couleur = couleur;
    }

    public Integer getIdProprietaire() {
        return IdProprietaire;
    }

    public void setIdPropretaire(Integer idPropretaire) {
        IdProprietaire = idPropretaire;
    }

    public int getMemoire_RAM() {
        return memoire_RAM;
    }

    public int getMemoire_ROM() {
        return memoire_ROM;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public void setMemoire_RAM(int memoire_RAM) {
        this.memoire_RAM = memoire_RAM;
    }

    public void setMemoire_ROM(int memoire_ROM) {
        this.memoire_ROM = memoire_ROM;
    }

    public void setNumero_serie(String numero_serie) {
        this.numero_serie = numero_serie;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getModele() {
        return Modele;
    }

    public void setModele(String modele) {
        Modele = modele;
    }

    public String getMarque() {
        return Marque;
    }

    public void setMarque(String marque) {
        Marque = marque;
    }

    public MaterielsModel(String nom, String marque, String modele, int memoire_ROM, int memoire_RAM, String numero_serie, Integer idPropretaire, String couleur, String etat_Materiel, String photoUrl) {
        Nom = nom;
        Marque = marque;
        Modele = modele;
        this.memoire_ROM = memoire_ROM;
        this.memoire_RAM = memoire_RAM;
        this.numero_serie = numero_serie;
        IdProprietaire = idPropretaire;
        Couleur = couleur;
        this.etat_Materiel = etat_Materiel;
        this.photoUrl = photoUrl;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
