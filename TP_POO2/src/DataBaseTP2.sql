-- Création de la base de données
CREATE DATABASE IF NOT EXISTS TP2;
USE TP2;

-- Table des utilisateurs
CREATE TABLE Users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    numtel VARCHAR(20),
    address VARCHAR(255)
);

-- Table des téléphones
CREATE TABLE Phones (
    IMEI VARCHAR(100) PRIMARY KEY,
    IdProprietaire INT,
    etat_Materiel VARCHAR(100),
    Couleur VARCHAR(100),
    Nom VARCHAR(100),
    Marque VARCHAR(100),
    Modele VARCHAR(100),
    memoire_ROM INT,
    memoire_RAM INT,
    numero_serie VARCHAR(100) UNIQUE,
    photoUrl VARCHAR(255)
);

-- Table des équipements
CREATE TABLE Equipements (
    address_MAC VARCHAR(100) PRIMARY KEY,
    IdProprietaire INT,
    Couleur VARCHAR(100),
    etat_Materiel VARCHAR(100),
    Nom VARCHAR(100),
    Marque VARCHAR(100),
    Modele VARCHAR(100),
    memoire_ROM INT,
    memoire_RAM INT,
    numero_serie VARCHAR(100) UNIQUE,
    photoUrl VARCHAR(255)
);
